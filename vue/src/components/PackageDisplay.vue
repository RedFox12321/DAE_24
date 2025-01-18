<script setup>
import { usePackageStore } from "@/stores/packages.js";
import { useRoute, useRouter } from "vue-router"
import List from "./Utils/List.vue";
import { useAuthStore } from "@/stores/auth";
import { useVolumeStore } from "@/stores/volumes";
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';

const router = useRouter()
const code = useRoute().params.code
const packageStore = usePackageStore()
const volumeStore = useVolumeStore()
const authStore = useAuthStore()

const cancelPackage = async (code) => {
    const result = await packageStore.cancelPackage(code)
    if (result) {
        packageStore.packagesByStatus = packageStore.packagesByStatus.filter(pck => pck.code != code)
        toast.info("Package canceled successfully")
        router.back()
    }
}

const deliverVolume = async (volume) => {
    const result = await volumeStore.deliverVolume(volume.code)
    if (result) {
        toast.info("Volume delivered successfully")
        volumeStore.curVolume.status = "DELIVERED"
        volume.status = "DELIVERED"
        if(packageStore.curPackage.volumes.filter(volume => volume.status == "ACTIVE").length == 0)
          packageStore.curPackage.status = "DELIVERED"
    }
}

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
          <h1 class="text-2xl font-bold">Package #{{ code }}</h1>
          <p class="text-m">Ordered By: {{
            packageStore.curPackage.clientUsername }}</p>
          <p class="text-m mb-6">Status: {{
            packageStore.curPackage.status }}</p>
        </div>
        <button v-if="authStore.userType == 'Logistic' && packageStore.curPackage.status == 'ACTIVE'"
          @click="cancelPackage(code)"
          class="flex h-fit w-fit items-center bg-red-600 hover:bg-red-500 text-white px-3 py-2 rounded shadow">
          Cancel
        </button>
      </div>
      <div v-if="packageStore.curPackage.volumes && packageStore.curPackage.volumes.length != 0">
        <h1 class="text-xl font-bold mb-6">Volumes</h1>
        <List :items="packageStore.curPackage.volumes">
          <template #default="{ item, index }">
            <div class="flex justify-between items-center w-full">
              <div>
                <RouterLink :to="{name : 'volume', params : {code: item.code}}">
                  <h3 class="text-lg font-semibold">Volume #{{ item.code }}</h3>
                </RouterLink>
                <p class="text-sm text-gray-400">Number: {{ item.number }}</p>
                <p class="text-sm text-gray-400">Status: {{ item.status }}</p>
                <p class="text-sm text-gray-400">Package Type: {{ item.packageType }}</p>
              </div>
              <button v-if="authStore.userType == 'Logistic' && item.status == 'ACTIVE'" @click="deliverVolume(item)"
                class="flex h-fit w-fit items-center bg-lime-600 hover:bg-lime-500 text-white px-3 py-2 rounded shadow">
                Deliver
              </button>
            </div>
          </template>
        </List>
      </div>
      <h1 v-else class="text-xl font-bold mb-6">This package has no volumes that you
        can see...</h1>
    </div>
  </div>
</template>

<style scoped>
html,
body {
  margin: 0;
  padding: 0;
  height: 100%;
  background-color: #1e293b;
  /* Dark background color */
}

.min-h-screen {
  min-height: 100vh;
  /* Ensure the container takes full viewport height */
}
</style>
