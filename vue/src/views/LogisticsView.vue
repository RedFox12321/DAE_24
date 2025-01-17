<script setup>
import VolumeItem from "@/components/Logistics/VolumeItem.vue"; 
import SimpleInput from "@/components/Utils/SimpleInput.vue";
import { useClientStore } from "@/stores/clients";
import { useOrderPackageStore } from "@/stores/orderPackage";
import { onMounted, ref } from "vue";

const clientStore = useClientStore()
const orderPackageStore = useOrderPackageStore()

const searchQuery = ref('')

const resetClients = async () => {
    await clientStore.getClients()
    const client0 = clientStore.clients[0]
}
onMounted(() => resetClients())

</script>

<template>
    <div class="p-6 space-y-8 flex flex-col items-center w-full h-full shadow rounded-lg">
        <h2 class="text-2xl text-gray-200 font-semibold mb-4">Create an Order/Package</h2>
        <div class="flex flex-row space-x-10">
            <SimpleInput v-model="orderPackageStore.orderPackage.code" label="Order/package code" type="number"
                :required="true" />

            <QuerySearch v-model="searchQuery" :items="clientStore.clients" filter-key="name"
                @item-selected="itemSelected" class="w-2/5">
                <template v-slot="client">
                    <span class="text-gray-300 font-semibold text-lg p-2 w-full text-center">{{ client }}</span>
                </template>
            </QuerySearch>

            <SimpleInput v-model="orderPackageStore.orderPackage.clientUsername" label="Order/package client username"
                type="text" :required="true" />
        </div>
        <div class="flex flex-col items-center">
            <h2 class="text-xl text-gray-200">Order/Package volumes:</h2>
            <span class="font-light text-red-400 text-sm">Warning: Current volume number might not be the same as the
                final volume number.</span>
        </div>
        <div class="grid grid-cols-1 xl:grid-cols-2 2xl:grid-cols-3 w-full h-full">
            <div v-for="(volume, index) in orderPackageStore.orderPackage.volumes" :key="index"
                class="flex flex-col space-y-5 items-center p-6 w-full h-full">
                <VolumeItem v-model="orderPackageStore.orderPackage.volumes[index]" :index="index" />
            </div>
        </div>
        <div class="mb-4 max-w-lg">
            <button @click.prevent="orderPackageStore.addVolume()"
                class="w-full py-3 bg-blue-500 text-white font-semibold rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
                Add a volume
            </button>
        </div>
    </div>
</template>
