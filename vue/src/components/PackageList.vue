<script setup>
  import { ref, onMounted, onActivated } from "vue";
  import {useRouter} from "vue-router";
import { usePackageStore } from "@/stores/packages.js";
import List from "./Utils/List.vue";

const packageStore = usePackageStore();

const setup = () => {
  packageStore.getPackages();
}

onMounted(() => setup())
onActivated(() => setup())

const router = useRouter()

const packageButton = async (code) => {
  const result = await packageStore.getPackage(code);
  if(result)
    router.push({name : 'package', params : {code: code}})
}

</script>

<template>
  <div class="min-h-screen text-gray-200 p-6">
    <div v-if="packageStore.packages.length !== 0 && packageStore.packages !==
      null" class="max-w-4xl mx-auto">
      <h1 class="text-2xl font-bold mb-6">Packages</h1>
      <List :items="packageStore.packages">
      <template #default="{ item, index }">
        <div class="flex justify-between items-center w-full">
          <div>
            <button @click="packageButton(item.code)">
              <h3 class="text-lg font-semibold">Package #{{ item.code }}</h3>
            </button>
            <p class="text-sm text-gray-400">Ordered by: {{
              item.clientUsername }}</p>
            <p class="text-sm text-gray-400">Status: {{ item.status }}</p>
          </div>
        </div>
      </template>
      </List>
    </div>
    <div v-else class="max-w-4xl mx-auto">
      <h1 class ="text-2xl font-bold mb-6">No packages found...</h1>
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
