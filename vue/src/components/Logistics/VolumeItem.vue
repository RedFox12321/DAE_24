<script setup>
import { useOrderPackageStore } from '@/stores/orderPackage';
import SelectInput from '../Utils/SelectInput.vue';
import SimpleInput from '../Utils/SimpleInput.vue';
import SensorItem from './SensorItem.vue';
import ProductItem from './ProductItem.vue';
import { computed } from 'vue';

const orderPackageStore = useOrderPackageStore()

const volume = defineModel()

const props = defineProps({
    index: {
        type: Number,
        required: true
    }
})

const numberOfProductsVolumes = computed(() => {
    return orderPackageStore.numberOfProductsVolumes(props.index)
})

const removeSensor = (sensorIndex) => {
    orderPackageStore.removeSensor(props.index, sensorIndex)
}

const removeProduct = (productIndex) => {
    orderPackageStore.removeProduct(props.index, productIndex)
}

</script>

<template>
    <div class="flex flex-col items-center bg-gray-800 w-full h-full rounded-md p-6 max-w-lg">
        <span class="text-gray-200 text-2xl mb-4">Volume {{ index + 1 }}</span>
        <div class="flex flex-col items-center p-6 bg-gray-700 rounded-lg shadow-md w-full space-y-2">
            <SimpleInput v-model="volume.code" label="Volume code" type="number" :required="true" class="w-full" />

            <SelectInput v-model="volume.packageType" label="Package type" :options="orderPackageStore.packageTypes"
                :required="true" class="w-full" />

            <hr class="bg-gray-300 w-full rounded-lg">
            <div class="max-w-md flex flex-col justify-center items-center space-y-1 w-full">
                <span class="text-gray-200 text-lg">Products:</span>
                <ProductItem v-for="productVolume, index in volume.productsVolume" :key="index"
                    v-model="volume.productsVolume[index]" @remove-product="removeProduct" :index="index" :show-delete="numberOfProductsVolumes > 1"
                    class="mb-2" />
                <button @click.prevent="orderPackageStore.addProduct(index)"
                    class="w-full py-3 bg-blue-500 text-white font-semibold rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
                    Add a Product
                </button>
            </div>
            <hr class="bg-gray-300 w-full rounded-lg">
            <div class="max-w-md flex flex-col justify-center items-center space-y-1 w-full">
                <span class="text-gray-200 text-lg">Sensors:</span>
                <SensorItem v-for="sensor, index in volume.sensors" :key="index" v-model="volume.sensors[index]"
                    @remove-sensor="removeSensor" :index="index" class="mb-2" />
                <button @click.prevent="orderPackageStore.addSensor(index)"
                    class="w-full py-3 bg-blue-500 text-white font-semibold rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
                    Add a Sensor
                </button>
            </div>
            <button v-show="orderPackageStore.numberOfVolumes > 1" @click="orderPackageStore.removeVolume(index)"
                class="flex items-center bg-red-600 hover:bg-red-500 text-white px-3 py-2 rounded shadow justify-center">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 mr-1" viewBox="0 0 20 20" fill="currentColor">
                    <path fill-rule="evenodd"
                        d="M9 2a1 1 0 00-1 1v1H5a1 1 0 000 2h10a1 1 0 100-2h-3V3a1 1 0 00-1-1H9zm-4 7a1 1 0 011 1v7a1 1 0 001 1h6a1 1 0 001-1v-7a1 1 0 112 0v7a3 3 0 01-3 3H6a3 3 0 01-3-3v-7a1 1 0 011-1z"
                        clip-rule="evenodd" />
                </svg>
            </button>
        </div>
    </div>
</template>