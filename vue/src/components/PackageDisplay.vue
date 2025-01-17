<script setup>
  import { ref } from "vue";
import { usePackageStore } from "@/stores/packages.js";
import { useRoute } from "vue-router"
import List from "./Utils/List.vue";

const code = useRoute().params.code;
const packageStore = usePackageStore();
packageStore.getPackage(code);
</script>

<template>
  <div class="min-h-screen bg-gray-900 text-gray-200 p-6">
    <div class="max-w-4xl mx-auto">
      <h1 class="text-2xl font-bold mb-6">Package {{code}}</h1>
      <h1 class="text-xl font-bold mb-6">Volumes</h1>
      <List :items="packageStore.curPackage.volumes">
      <template #default="{ item, index }">
        <div class="flex justify-between items-center w-full">
          <div>
            <h3 class="text-lg font-semibold">Code: {{ item.code }}</h3>
            <p class="text-sm text-gray-400">Number: {{ item.number }}</p>
            <p class="text-sm text-gray-400">Status: {{ item.status }}</p>
            <p class="text-sm text-gray-400">Package Type: {{ item.packageType }}</p>
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
