<script setup>
  import { ref, onMounted, onActivated } from "vue";
import { useVolumeStore } from "@/stores/volumes.js";
import { useProductsStore } from "@/stores/products.js";
import { useRoute } from "vue-router"
import List from "./Utils/List.vue";

const code = useRoute().params.code;
const volumeStore = useVolumeStore();
const productStore = useProductsStore();

const setup = () => {
  volumeStore.getVolume(code);
}

onMounted(() => setup())
onActivated(() => setup())

</script>

<template>
  <div class="min-h-screen text-gray-200 p-6">
    <div class="max-w-4xl mx-auto">
      <h1 class="text-2xl font-bold">Volume #{{code}}</h1>
      <p class="text-m">Number: {{
        volumeStore.curVolume.number }}</p>
      <p class="text-m">Status: {{
        volumeStore.curVolume.status }}</p>
      <p class="text-m mb-6">Package Type: {{
        volumeStore.curVolume.packageType }}</p>
      <div v-if="volumeStore.curVolume.sensors.length !== 0 && volumeStore.curVolume.sensor !== null">
        <h1 class="text-xl font-bold mb-6">Sensors</h1>
        <List :items="volumeStore.curVolume.sensors">
        <template #default="{ item, index }">
          <div class="flex justify-between items-center w-full">
            <div>
              <RouterLink :to="{name: 'sensor', params : {id: item.id}}">
                <h3 class="text-lg font-semibold">Sensor #{{ item.id }}</h3>
              </RouterLink>
              <p class="text-sm text-gray-400">{{ item.active ? 'Active' : 'Not active' }}</p>
              <p class="text-sm text-gray-400">{{ item.type }} Sensor</p>
            </div>
          </div>
        </template>
        </List>
      </div>
      <h1 v-else class="text-xl font-bold mb-6">This volume has no sensors that
      you can see...</h1>
      <div v-if="volumeStore.curVolume.productsVolume.length !== 0 && volumeStore.curVolume.productsVolume !== null">
        <h1 class="text-xl font-bold my-6">Products</h1>
        <List :items="volumeStore.curVolume.productsVolume">
        <template #default="{ item, index }">
          <div class="flex justify-between items-center w-full">
            <div>
              <h3 class="text-lg font-semibold">{{ item.productName }}
                (#{{item.productCode}})</h3>
              <p class="text-sm text-gray-400">Quantity: {{ item.quantity }}</p>
            </div>
          </div>
        </template>
        </List>
      </div>
      <h1 v-else class="text-xl font-bold my-6">This volume has no products that you can see...</h1>
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
