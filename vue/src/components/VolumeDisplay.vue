<script setup>
import { onMounted, onActivated } from "vue";
import { useVolumeStore } from "@/stores/volumes.js";
import { useRoute, useRouter } from "vue-router"
import List from "./Utils/List.vue";
import { useAuthStore } from "@/stores/auth";
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import { usePackageStore } from "@/stores/packages";

const router = useRouter()
const authStore = useAuthStore()

const code = useRoute().params.code
const volumeStore = useVolumeStore()
const packageStore = usePackageStore()

const setup = () => {
  volumeStore.getVolume(code);
}

const deliverVolume = async () => {
    const result = await volumeStore.deliverVolume()
    if (result) {
        toast.info("Volume delivered successfully")
        packageStore.curPackage.volumes.find(vol => vol.code == code).status = "DELIVERED"
        router.back()
    }
}

onMounted(() => setup())
onActivated(() => setup())

</script>

<template>
  <div class="min-h-screen text-gray-200 p-6">
    <button @click.prevent="router.back()"
      class="flex h-fit w-fit items-center bg-red-500 hover:bg-red-400 text-white px-3 py-2 rounded shadow">
      Go back
    </button>
    <div class="max-w-4xl mx-auto">
      <div class="flex flex-col md:flex-row w-full h-fit justify-around items-center">
        <div class="flex flex-col w-fit h-fit">
          <h1 class="text-2xl font-bold">Volume #{{ code }}</h1>
          <p class="text-m">Number: {{ volumeStore.curVolume.number || "Loading..." }}</p>
          <p class="text-m">Status: {{ volumeStore.curVolume.status || "Loading..." }}</p>
          <p class="text-m mb-6">Package Type: {{ volumeStore.curVolume.packageType || "Loading..." }}</p>
        </div>
        <button v-if="authStore.userType == 'Logistic' && volumeStore.curVolume.status == 'ACTIVE'"
          @click="deliverVolume"
          class="flex h-fit w-fit items-center bg-lime-600 hover:bg-lime-500 text-white px-3 py-2 rounded shadow">
          Deliver
        </button>
      </div>

      <div v-if="volumeStore.curVolume.sensors && volumeStore.curVolume.sensors.length !== 0">
        <h1 class="text-xl font-bold mb-6">Sensors</h1>
        <List :items="volumeStore.curVolume.sensors">
          <template #default="{ item, index }">
            <div class="flex justify-between items-center w-full">
              <div>
                <h3 v-if="authStore.userType == 'Logistic'" class="text-lg font-semibold">Sensor #{{ item.id }}</h3>
                <RouterLink v-else :to="{ name: 'sensor', params: { id: item.id } }">
                  <h3 class="text-lg font-semibold">Sensor #{{ item.id }}</h3>
                </RouterLink>
                <p class="text-sm text-gray-400">{{ item.active ? 'Active' : 'Not active' }}</p>
                <p class="text-sm text-gray-400">{{ item.type }} Sensor</p>
              </div>
            </div>
          </template>
        </List>
      </div>
      <h1 v-else class="text-xl font-bold mb-6">This volume has no sensors that you can see...</h1>

      <div v-if="volumeStore.curVolume.productsVolume">
        <h1 class="text-xl font-bold my-6">Products</h1>
        <List :items="volumeStore.curVolume.productsVolume">
          <template #default="{ item, index }">
            <div class="flex justify-between items-center w-full">
              <div>
                <h3 class="text-lg font-semibold">{{ item.productName }} (#{{ item.productCode }})</h3>
                <p class="text-sm text-gray-400">Quantity: {{ item.quantity }}</p>
              </div>
            </div>
          </template>
        </List>
      </div>
    </div>
  </div>
</template>


<style scoped>
html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  background-color: #1e293b; /* Dark background color */
}

  .min-h-screen {
    min-height: 100vh; /* Ensure the container takes full viewport height */
  }
</style>
