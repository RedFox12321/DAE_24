<script>
import { ref, onMounted, computed } from "vue";

export default {
  name: "SensorList",
  setup() {
    const sensors = ref([]);
    const loading = ref(true);
    const error = ref(null);
    const filter = ref("all"); // 'all' or 'active'
    const showModal = ref(false); // Controls the popup visibility
    const newSensorType = ref(""); // Selected sensor type in the popup

    // Fetch sensors
    onMounted(async () => {
      try {
        const response = await fetch("http://localhost:8080/dae24/api/sensors");
        if (!response.ok) throw new Error("Failed to fetch sensors");
        sensors.value = await response.json();
      } catch (err) {
        error.value = err.message;
      } finally {
        loading.value = false;
      }
    });

    // Filtered sensors (computed based on the filter value)
    const filteredSensors = computed(() => {
      return filter.value === "active"
        ? sensors.value.filter((sensor) => sensor.active)
        : sensors.value;
    });

    // Open the create sensor modal
    const openCreateSensorModal = () => {
      showModal.value = true;
    };

    // Create new sensor action
    const createSensor = () => {
      if (!newSensorType.value) {
        alert("Please select a sensor type.");
        return;
      }
      alert(`Create new sensor of type: ${newSensorType.value} (connect to API)`);
      showModal.value = false; // Close the modal
    };

    // Delete sensor
    const deleteSensor = (id) => {
      alert(`Delete sensor with ID: ${id} (connect this to API call)`);
    };

    return {
      sensors,
      loading,
      error,
      filter,
      filteredSensors,
      showModal,
      openCreateSensorModal,
      createSensor,
      deleteSensor,
      newSensorType,
    };
  },
};
</script>

<template>
  <div class="min-h-screen bg-gray-900 text-gray-200 p-6">
    <div class="max-w-4xl mx-auto">
      <!-- Page Header -->
      <div class="flex justify-between items-center mb-6">
        <h1 class="text-2xl font-bold">Sensor List</h1>

        <!-- Filter Dropdown -->
        <select
          v-model="filter"
          class="bg-gray-800 text-gray-200 px-4 py-2 rounded shadow"
        >
          <option value="all">All Sensors</option>
          <option value="active">Active Sensors</option>
        </select>

        <!-- Create New Sensor Button -->
        <button
          @click="openCreateSensorModal"
          class="flex items-center bg-green-600 hover:bg-green-500 text-white px-4 py-2 rounded shadow"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            class="h-5 w-5 mr-2"
            viewBox="0 0 20 20"
            fill="currentColor"
          >
            <path
              fill-rule="evenodd"
              d="M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z"
              clip-rule="evenodd"
            />
          </svg>
          Create New Sensor
        </button>
      </div>

      <!-- Loading State -->
      <div v-if="loading" class="text-center">
        <span class="text-lg">Loading sensors...</span>
      </div>

      <!-- Error State -->
      <div v-if="error" class="text-center text-red-500">
        <span>Error: {{ error }}</span>
      </div>

      <!-- Sensors List -->
      <div v-if="!loading && !error">
        <ul class="space-y-4">
          <li
            v-for="sensor in filteredSensors"
            :key="sensor.id"
            class="bg-gray-800 rounded-lg shadow p-4 flex justify-between items-center"
          >
            <!-- Sensor Info -->
            <div>
              <h2 class="text-lg font-semibold">ID: {{ sensor.id }}</h2>
              <p class="text-sm text-gray-400">{{ sensor.type }}</p>
              <span
                :class="sensor.active ? 'text-green-400' : 'text-red-400'"
              >
                {{ sensor.active ? 'active' : 'disabled' }}
              </span>
            </div>

            <!-- Delete Button -->
            <button
              @click="deleteSensor(sensor.id)"
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
          </li>
        </ul>
      </div>
    </div>

    <!-- Create Sensor Modal -->
    <div
      v-if="showModal"
      class="fixed inset-0 bg-black bg-opacity-75 flex items-center justify-center"
    >
      <div class="bg-gray-800 text-gray-200 p-6 rounded shadow-lg w-96">
        <h2 class="text-lg font-bold mb-4">Create New Sensor</h2>
        <label for="sensorType" class="block mb-2">Sensor Type:</label>
        <select
          id="sensorType"
          v-model="newSensorType"
          class="bg-gray-700 text-gray-200 w-full p-2 rounded mb-4"
        >
          <option value="">Select Sensor Type</option>
          <option value="Acceleration">Acceleration</option>
          <option value="Atmospheric pressure">
            Atmospheric Pressure
          </option>
          <option value="Geographical Location">
            Geographical Location
          </option>
          <option value="Temperature">Temperature</option>
        </select>
        <div class="flex justify-end">
          <button
            @click="showModal = false"
            class="bg-gray-600 hover:bg-gray-500 text-white px-4 py-2 rounded mr-2"
          >
            Cancel
          </button>
          <button
            @click="createSensor"
            class="bg-green-600 hover:bg-green-500 text-white px-4 py-2 rounded"
          >
            Create
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Optional styles for modal transitions or finer details */
</style>
