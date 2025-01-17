<script setup>
  import { ref, onMounted, onActivated } from "vue";
import { useSensorStore } from "@/stores/sensor.js";
import { useRoute } from "vue-router"
import List from "./Utils/List.vue";

const id = useRoute().params.id;
const sensorStore = useSensorStore();

const setup = () => {
  sensorStore.getSensor(id);
}

onMounted(() => setup())
onActivated(() => setup())

console.log(sensorStore.curSensor)

</script>

<template>
  <div class="min-h-screen text-gray-200 p-6">
    <div class="max-w-4xl mx-auto">
      <h1 class="text-2xl font-bold">Sensor #{{id}}</h1>
      <p class="text-m">{{ sensorStore.curSensor.active ? 'Active' : 'Not active' }}</p>
      <p class="text-m">{{ sensorStore.curSensor.type }} Sensor</p>
      <p class="text-m mb-6"></p>
      <h1 class="text-xl font-bold mb-6">History</h1>
      <List :items="sensorStore.curSensor.history">
      <template #default="{ item, index }">
        <div class="flex justify-between items-center w-full">
          <div>
            <h3 class="text-lg font-semibold">{{ new Date(item.datetime).toLocaleString() }}</h3>
            <p class="text-sm text-gray-400">{{ item.sensorType }} : {{ item.value }}</p>
          </div>
        </div>
      </template>
      </List>
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
