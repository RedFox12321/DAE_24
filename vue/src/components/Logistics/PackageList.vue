<script setup>
import { usePackageStore } from '@/stores/packages';
import { onActivated, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import List from '../Utils/List.vue';

const packageStore = usePackageStore()

const router = useRouter()

const packageButton = async (code) => {
  const result = await packageStore.getPackage(code);
  if (result) router.push({ name: "package", params: { code: code } });
}

const fetchPackagesByStatus = async () => {
  await packageStore.getPackagesByStatus("ACTIVE") //Logistics sees only the ACTIVE packages
}

onMounted(() => {
  fetchPackagesByStatus()
})

onActivated(() => {
    fetchPackagesByStatus()
})

</script>

<template>
    <div class="w-full h-full flex flex-col justify-between items-center">
        <button @click.prevent="fetchPackagesByStatus"
                class="mb-4 p-3 bg-blue-500 text-white font-semibold rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
                Fetch packages
        </button>
        <span class="text-gray-300 text-xl font-semibold mb-2">Currently active packages</span>
        <div v-if="packageStore.packagesByStatus !== null && packageStore.packagesByStatus.length !== 0"
            class="max-w-4xl w-full">
            <List :items="packageStore.packagesByStatus">
                <template #default="{ item, index }">
                    <div class="flex justify-between items-center w-full">
                        <div>
                            <button @click="packageButton(item.code)">
                                <h3 class="text-gray-300 text-lg font-semibold">Package #{{ item.code }}</h3>
                            </button>
                            <p class="text-sm text-gray-400">Ordered by: {{ item.clientUsername }}</p>
                            <p class="text-sm text-gray-400">Status: {{ item.status }}</p>
                        </div>
                    </div>
                </template>
            </List>
        </div>
        <div v-else class="max-w-4xl mx-auto">
            <h1 class="text-2xl font-bold mb-6">There are no active packages currently.</h1>
        </div>
    </div>
</template>