<script setup>
import VolumeItem from "@/components/Logistics/VolumeItem.vue"; 
import QuerySearch from "@/components/Utils/QuerySearch.vue";
import SimpleInput from "@/components/Utils/SimpleInput.vue";
import { useClientStore } from "@/stores/clients";
import { useOrderPackageStore } from "@/stores/orderPackage";
import { onActivated, onMounted, ref } from "vue";
import { toast } from "vue3-toastify";
import 'vue3-toastify/dist/index.css';

const clientStore = useClientStore()
const orderPackageStore = useOrderPackageStore()

const searchQuery = ref("")


const itemSelected = (client) => {
    orderPackageStore.orderPackage.clientUsername = client.username
}

const resetClients = async () => {
    const clients = await clientStore.getClients()
    if(clients){
        const client0 = clients[0]
        searchQuery.value = client0.username
        orderPackageStore.orderPackage.clientUsername = client0.username
    }
}

const sendPackageOrder = async () => {
    console.log(orderPackageStore.orderPackage)
    const result = orderPackageStore.createOrderPackage()
    if(result){
        orderPackageStore.resetOrderPackage()
        toast.info("Order/Package created successfully.")
    }
}

onMounted(() => resetClients())
onActivated(() => {
    resetClients()
    orderPackageStore.resetOrderPackage()
})

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
                <QuerySearch v-model="searchQuery" :items="clientStore.clients" filter-key="username"
                    @item-selected="itemSelected" class="w-full">
                    <template v-slot="client">
                        <span class="text-gray-300 font-semibold text-lg p-2 w-full text-center">{{ client.item.username }}</span>
                    </template>
                </QuerySearch>
            </div>
        </div>
        <div class="flex flex-col items-center">
            <h2 class="text-xl text-gray-200">Order/Package volumes:</h2>
            <span class="font-light text-red-400 text-sm">Warning: Current volume number might not be the same as the
                final volume number.</span>
        </div>
        <div class="grid grid-cols-1 xl:grid-cols-2 2xl:grid-cols-3 w-full h-full">
            <div v-for="(volume, index) in orderPackageStore.orderPackage.volumes" :key="index"
                class="flex flex-col space-y-5 items-center p-6 w-full h-full">
                <VolumeItem v-model="orderPackageStore.orderPackage.volumes[index]" :index="index" />
            </div>
        </div>
        <div class="mb-4 max-w-lg">
            <button @click.prevent="orderPackageStore.addVolume()"
                class="w-full py-3 bg-blue-500 text-white font-semibold rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
                Add a volume
            </button>
        </div>
    </div>
    <div class="flex flex-col items-center">
      <h2 class="text-xl text-gray-200">Order/Package volumes:</h2>
      <span class="font-light text-red-400 text-sm">Warning: Current volume number might not be the same as the
        final volume number.</span>
    </div>
    <div class="grid grid-cols-1 xl:grid-cols-2 2xl:grid-cols-3 w-full h-full">
      <div v-for="(volume, index) in orderPackageStore.orderPackage.volumes" :key="index"
        class="flex flex-col space-y-5 items-center p-6 w-full h-full">
        <VolumeItem v-model="orderPackageStore.orderPackage.volumes[index]" :index="index" />
      </div>
    </div>
    <div class="mb-4 max-w-lg">
      <button @click.prevent="orderPackageStore.addVolume()"
        class="w-full py-3 bg-blue-500 text-white font-semibold rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
        Add a volume
      </button>
    </div>
  </div>
</template>
