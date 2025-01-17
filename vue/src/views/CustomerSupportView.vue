<script setup>
import {ref} from "vue";
import {usePackageStore} from "@/stores/packages.js";
import List from "@/components/Utils/List.vue";

const packageStore = usePackageStore();
packageStore.getMyActivePackages();
console.log(packageStore.packages);

const cancelPackage = (code) => {
  packageStore.cancelPackage(code);
}

</script>

<template>
  <div class="min-h-screen bg-gray-900 text-gray-200 p-6">
    <div class="max-w-4xl mx-auto">
      <h1 class="text-2xl font-bold mb-6">Packages</h1>
      <List :items="packageStore.activePackages">
        <template #default="{ item, index }">
          <div class="flex justify-between items-center w-full">
            <div>
              <h3 class="text-lg font-semibold">Code: {{ item.code }}</h3>
              <p class="text-sm text-gray-400">Ordered by: {{
                item.clientUsername
                }}</p>
              <p class="text-sm text-gray-400">Status: {{ item.status }}</p>
            </div>
            <button
              @click="cancelPackage(item.code)"
              class="flex items-center bg-red-600 hover:bg-red-500 text-white px-3 py-2 rounded shadow"
            >
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
