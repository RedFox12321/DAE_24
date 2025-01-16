<script setup>
import { ref } from "vue";
import { usePackageStore } from "@/stores/packages.js";
import List from "./Utils/List.vue";

const packageStore = usePackageStore();
packageStore.getPackages();
console.log(packageStore.packages);

const cancelPackage = (code) => {
  packageStore.cancelPackage(code);
}

</script>

<template>
  <div class="min-h-screen bg-gray-900 text-gray-200 p-6">
    <div class="max-w-4xl mx-auto">
      <h1 class="text-2xl font-bold mb-6">Packages</h1>
      <List :items="packageStore.packages">
        <template #default="{ item, index }">
          <div class="flex justify-between items-center w-full">
            <div>
              <h3 class="text-lg font-semibold">Code: {{ item.code }}</h3>
              <p class="text-sm text-gray-400">Ordered by: {{
                item.clientUsername }}</p>
              <p class="text-sm text-gray-400">Status: {{ item.status }}</p>
            </div>

            <button
              v-if="item.status == ACTIVE"
              @click="cancelPackage(item.code)"
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
              Cancel
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
