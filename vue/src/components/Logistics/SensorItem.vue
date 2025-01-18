<script setup>
import { useOrderPackageStore } from '@/stores/orderPackage';
import SimpleInput from '../Utils/SimpleInput.vue';
import SelectInput from '../Utils/SelectInput.vue';

const orderPackageStore = useOrderPackageStore()

const sensor = defineModel()
const props = defineProps({
    index: {
        type: Number,
        required: true
    }
})
const emit = defineEmits(['removeSensor'])

const removeSensor = () => {
    emit('removeSensor', props.index)
}

</script>

<template>
    <div class="flex flex-row items-center bg-gray-600 w-full h-min rounded-md p-2 max-w-md space-x-2 justify-between mb-1">
        <SimpleInput v-model="sensor.id" label="Id:" type="number" :required="true" :in-row="true" class="w-2/5"/>

        <SelectInput v-model="sensor.type" label="Type:" :options="orderPackageStore.sensorTypes" :required="true"
            :in-row="true" class="w-1/2" />

        <button @click="removeSensor"
            class="flex items-center bg-red-600 hover:bg-red-500 text-white px-3 py-2 rounded shadow justify-center">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 mr-1" viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd"
                    d="M9 2a1 1 0 00-1 1v1H5a1 1 0 000 2h10a1 1 0 100-2h-3V3a1 1 0 00-1-1H9zm-4 7a1 1 0 011 1v7a1 1 0 001 1h6a1 1 0 001-1v-7a1 1 0 112 0v7a3 3 0 01-3 3H6a3 3 0 01-3-3v-7a1 1 0 011-1z"
                    clip-rule="evenodd" />
            </svg>
        </button>
    </div>
</template>