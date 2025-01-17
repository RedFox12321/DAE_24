<script setup>
  import { ref } from "vue";
import { usePackageStore } from "@/stores/packages.js";
import { useVolumeStore } from "@/stores/volumes.js";
import { useRoute } from "vue-router"
import List from "./Utils/List.vue";

const code = useRoute().params.code;
const packageStore = usePackageStore();
const volumeStore = useVolumeStore();

</script>

<template>
  <div class="min-h-screen text-gray-200 p-6">
    <div class="max-w-4xl mx-auto">
      <h1 class="text-2xl font-bold">Package #{{code}}</h1>
      <p class="text-m">Ordered By: {{
        packageStore.curPackage.clientUsername }}</p>
      <p class="text-m mb-6">Status: {{
        packageStore.curPackage.status }}</p>
      <div v-if="packageStore.curPackage.volumes !== 0 &&
        packageStore.curPackage.volume !== null ">
        <h1 class="text-xl font-bold mb-6">Volumes</h1>
        <List :items="packageStore.curPackage.volumes">
        <template #default="{ item, index }">
          <div class="flex justify-between items-center w-full">
            <div>
              <RouterLink :to="{name : 'volume', params : {code: code}}">
                <h3 class="text-lg font-semibold">Volume #{{ item.code }}</h3>
              </RouterLink>
              <p class="text-sm text-gray-400">Number: {{ item.number }}</p>
              <p class="text-sm text-gray-400">Status: {{ item.status }}</p>
              <p class="text-sm text-gray-400">Package Type: {{ item.packageType }}</p>
            </div>
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
