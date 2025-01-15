<script setup>
import { ref } from "vue";
import { useSensorStore } from "@/stores/sensorStore";
import List from "./Utils/List.vue";

const sensorStore = useSensorStore();

const newSensor = ref({
  id: "",
  type: "",
  interval: 2000,
});

const addSensor = () => {
  if (!newSensor.value.id || !newSensor.value.type) {
    alert("Please fill in all fields!");
    return;
  }

  sensorStore.addSensor(newSensor.value);

  newSensor.value = {
    id: "",
    type: "",
    interval: 2000,
  };
};

const removeSensor = (index) => {
  sensorStore.removeSensor(index);
};
</script>

<template>
  <div class="min-h-screen bg-gray-900 text-gray-200 p-6">
    <div class="max-w-4xl mx-auto">
      <h1 class="text-2xl font-bold mb-6">Sensor Manager</h1>

      <div class="bg-gray-800 rounded-lg shadow p-6 mb-6">
        <h2 class="text-lg font-bold mb-4">Create New Sensor</h2>
        <div class="grid grid-cols-3 gap-4 mb-4">
          <div>
            <label for="sensorId" class="block mb-2">Sensor ID:</label>
            <input
              id="sensorId"
              v-model="newSensor.id"
              type="text"
              class="bg-gray-700 text-gray-200 w-full p-2 rounded"
              placeholder="Enter sensor ID"
            />
          </div>

          <div>
            <label for="sensorType" class="block mb-2">Sensor Type:</label>
            <select
              id="sensorType"
              v-model="newSensor.type"
              class="bg-gray-700 text-gray-200 w-full p-2 rounded"
            >
              <option value="">Select Sensor Type</option>
              <option value="Acceleration">Acceleration</option>
              <option value="Atmospheric pressure">Atmospheric Pressure</option>
              <option value="Geographical Location">Geographical Location</option>
              <option value="Temperature">Temperature</option>
            </select>
          </div>

          <div>
            <label for="sensorInterval" class="block mb-2">Update Interval (ms):</label>
            <input
              id="sensorInterval"
              v-model="newSensor.interval"
              type="number"
              min="500"
              class="bg-gray-700 text-gray-200 w-full p-2 rounded"
              placeholder="Enter interval"
            />
          </div>
        </div>

        <button
          @click="addSensor"
          class="bg-green-600 hover:bg-green-500 text-white px-4 py-2 rounded shadow"
        >
          Add Sensor
        </button>
      </div>

      <List :items="sensorStore.sensors">
        <template #default="{ item, index }">
          <div class="flex justify-between items-center w-full">
            <div>
              <h3 class="text-lg font-semibold">ID: {{ item.id }}</h3>
              <p class="text-sm text-gray-400">Type: {{ item.type }}</p>
              <p class="text-sm text-gray-400">
                Value:
                <span v-if="item.type === 'Geographical Location'">
                  Latitude: {{ item.value.lat }}, Longitude: {{ item.value.lon }}
                </span>
                <span v-else>
                  {{ item.value }}
                </span>
              </p>
              <p class="text-sm text-gray-400">Update Interval: {{ item.interval }} ms</p>
            </div>

            <button
              @click="removeSensor(index)"
              class="flex items-center bg-red-600 hover:bg-red-500 text-white px-3 py-2 rounded shadow"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-5 w-5 mr-1"
                viewBox="0 0 20 20"
                fill="currentColor"
              >
                <path
                  fill-rule="evenodd"
                  d="M9 2a1 1 0 00-1 1v1H5a1 1 0 000 2h10a1 1 0 100-2h-3V3a1 1 0 00-1-1H9zm-4 7a1 1 0 011 1v7a1 1 0 001 1h6a1 1 0 001-1v-7a1 1 0 112 0v7a3 3 0 01-3 3H6a3 3 0 01-3-3v-7a1 1 0 011-1z"
                  clip-rule="evenodd"
                />
              </svg>
              Delete
            </button>
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
