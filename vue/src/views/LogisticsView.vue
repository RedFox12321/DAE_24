<script setup>
import VolumeItem from "@/components/Logistics/VolumeItem.vue";
import QuerySearch from "@/components/Utils/QuerySearch.vue";
import SimpleInput from "@/components/Utils/SimpleInput.vue";
import { useClientStore } from "@/stores/clients";
import { useOrderPackageStore } from "@/stores/orderPackage";
import { onActivated, onMounted, ref } from "vue";
import { toast } from "vue3-toastify";
import 'vue3-toastify/dist/index.css';
import { usePackageStore } from "@/stores/packages.js";
import { useRouter } from "vue-router";
import List from "@/components/Utils/List.vue";

const clientStore = useClientStore();
const orderPackageStore = useOrderPackageStore();
const packageStore = usePackageStore();

const router = useRouter();

const searchQuery = ref("");
const selectedStatus = ref("DELIVERED"); // Default to DELIVERED

const itemSelected = (client) => {
  orderPackageStore.orderPackage.clientUsername = client.username;
};

const resetClients = async () => {
  const clients = await clientStore.getClients();
  if (clients) {
    const client0 = clients[0];
    searchQuery.value = client0.username;
    orderPackageStore.orderPackage.clientUsername = client0.username;
  }
};

const sendPackageOrder = async () => {
  console.log(orderPackageStore.orderPackage);
  const result = await orderPackageStore.createOrderPackage();
  if (result) {
    toast.info("Order/Package created successfully.");
  }
};

const packageButton = async (code) => {
  const result = await packageStore.getPackage(code);
  if (result) router.push({ name: "package", params: { code: code } });
};

const fetchPackagesByStatus = async () => {
  await packageStore.getPackagesByStatus(selectedStatus.value);
};

onMounted(() => {
  resetClients();
  fetchPackagesByStatus(); // Fetch initial packages based on default status
});

onActivated(() => {
  resetClients();
  orderPackageStore.resetOrderPackage();
});
</script>

<template>
  <div class="p-6 space-y-8 flex flex-col items-center w-full h-full shadow rounded-lg">
    <h2 class="text-2xl text-gray-200 font-semibold mb-4">Create an Order/Package</h2>
    <button @click.prevent="sendPackageOrder"
            class="w-max p-3 bg-blue-500 text-white font-semibold rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
      Create Order/Package
    </button>
    <div class="flex flex-row space-x-10">
      <SimpleInput v-model="orderPackageStore.orderPackage.code" label="Order/package code:" type="number"
                   :required="true" class="w-1/2" />

      <div class="w-1/2">
        <span class="block text-gray-400 font-semibold m-2">Client username:</span>
        <QuerySearch v-model="searchQuery" :items="clientStore.clients" filter-key="username" @item-selected="itemSelected"
                     class="w-full">
          <template v-slot="client">
            <span class="text-gray-300 font-semibold text-lg p-2 w-full text-center">{{ client.item.username }}</span>
          </template>
        </QuerySearch>
      </div>
    </div>
    <div class="flex flex-col items-center">
      <h2 class="text-xl text-gray-200">Order/Package volumes:</h2>
      <span class="font-light text-red-400 text-sm">Warning: Current volume number might not be the same as the final
        volume number.</span>
    </div>
    <div class="mb-4 max-w-lg">
      <button @click.prevent="orderPackageStore.addVolume()"
              class="w-full p-3 bg-blue-500 text-white font-semibold rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
        Add a volume
      </button>
    </div>
    <div class="grid grid-cols-1 xl:grid-cols-2 2xl:grid-cols-3 w-full h-full">
      <div v-for="(volume, index) in orderPackageStore.orderPackage.volumes" :key="index"
           class="flex flex-col space-y-5 items-center p-6 w-full h-full">
        <VolumeItem v-model="orderPackageStore.orderPackage.volumes[index]" :index="index" />
      </div>
    </div>

    <div class="flex flex-col items-center mb-8">
      <label for="statusDropdown" class="text-lg font-semibold text-gray-300 mb-2">Filter Packages by Status:</label>
      <select id="statusDropdown" v-model="selectedStatus"
              class="p-2 border rounded-md text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500">
        <option value="DELIVERED">DELIVERED</option>
        <option value="ACTIVE">ACTIVE</option>
        <option value="CANCELLED">CANCELLED</option>
      </select>
      <button @click="fetchPackagesByStatus"
              class="mt-4 p-3 bg-blue-500 text-white font-semibold rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
        Fetch Packages
      </button>
    </div>

    <!-- Packages List -->
    <div v-if="packageStore.packagesByStatus.length !== 0 && packageStore.packagesByStatus !== null"
         class="max-w-4xl mx-auto">
      <h1 class="text-2xl font-bold mb-6">Packages</h1>
      <List :items="packageStore.packagesByStatus">
        <template #default="{ item, index }">
          <div class="flex justify-between items-center w-full">
            <div>
              <button @click="packageButton(item.code)">
                <h3 class="text-lg font-semibold">Package #{{ item.code }}</h3>
              </button>
              <p class="text-sm text-gray-400">Ordered by: {{ item.clientUsername }}</p>
              <p class="text-sm text-gray-400">Status: {{ item.status }}</p>
            </div>
          </div>
        </template>
      </List>
    </div>
    <div v-else class="max-w-4xl mx-auto">
      <h1 class="text-2xl font-bold mb-6">No packages found...</h1>
    </div>
  </div>
</template>
