<script setup>
import { ref, onMounted, onActivated } from "vue";
import { useRouter } from "vue-router";
import { usePackageStore } from "@/stores/packages.js";
import List from "@/components/Utils/List.vue";

const packageStore = usePackageStore();

const loadActivePackages = () => {
  packageStore.getMyActivePackages();
};

onMounted(() => loadActivePackages());
onActivated(() => loadActivePackages());

const cancelPackage = async (code) => {
  await packageStore.cancelPackage(code);
  loadActivePackages();
};

const router = useRouter();

const packageButton = async (code) => {
  const result = await packageStore.getPackage(code);
  if (result) {
    router.push({ name: "package", params: { code: code } });
  }
};
</script>

<template>
  <div class="min-h-screen text-gray-200 p-6">
    <div
      v-if="packageStore.activePackages.length !== 0 && packageStore.activePackages !== null"
      class="max-w-4xl mx-auto"
    >
      <h1 class="text-2xl font-bold mb-6">Packages</h1>
      <List :items="packageStore.activePackages">
        <template #default="{ item, index }">
          <div class="flex justify-between items-center w-full">
            <div>
              <button @click="packageButton(item.code)">
                <h3 class="text-lg font-semibold">Package #{{ item.code }}</h3>
              </button>
              <p class="text-sm text-gray-400">Ordered by: {{ item.clientUsername }}</p>
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
    <div v-else class="max-w-4xl mx-auto">
      <h1 class="text-2xl font-bold mb-6">No packages found...</h1>
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
}

.min-h-screen {
  min-height: 100vh;
}
</style>
