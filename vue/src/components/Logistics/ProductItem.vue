<script setup>
import { useProductsStore } from '@/stores/products';
import { onMounted, ref, watch } from 'vue';
import QuerySearch from '../Utils/QuerySearch.vue';
import SimpleInput from '../Utils/SimpleInput.vue';

const productsStore = useProductsStore()

const productVolume = defineModel()
const props = defineProps({
    index: {
        type: Number,
        required: true
    },
    showDelete: {
        type: Boolean,
        default: false
    }
})
const emit = defineEmits(['removeProduct'])

const removeProduct = () => {
    emit('removeProduct', props.index)
}

const itemSelected = (product) => {
    productVolume.value.productCode = product.code
}

const searchQuery = ref("")

watch(
    () => productVolume.value.quantity,
    (newValue) => {
        if (isNaN(newValue) || newValue < 1)
            productVolume.value.quantity = 1
    }
)

const resetProducts = async () => {
    const products = await productsStore.getProducts()
    if(products) {
        const product0 = products[0]
        productVolume.value.productCode = product0.code
        searchQuery.value = product0.name
    }
}

onMounted(() => resetProducts())

</script>

<template>
    <div class="flex flex-row items-center bg-gray-600 w-full h-min rounded-md p-2 max-w-md space-x-2 justify-between mb-1">
        <SimpleInput v-model="productVolume.quantity" label="Quantity:" type="text" :required="true" :in-row="true" class="w-2/5"/>

        <QuerySearch v-model="searchQuery" :items="productsStore.products" filter-key="name"
             @item-selected="itemSelected" class="w-2/5">
            <template v-slot="product">
                <span class="text-gray-300 font-semibold text-lg p-2 w-full text-center">{{ product.item.name }}</span>
            </template>
        </QuerySearch>

        <button v-show="props.showDelete" @click="removeProduct"
            class="flex items-center bg-red-600 hover:bg-red-500 text-white px-3 py-2 rounded shadow justify-center">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 mr-1" viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd"
                    d="M9 2a1 1 0 00-1 1v1H5a1 1 0 000 2h10a1 1 0 100-2h-3V3a1 1 0 00-1-1H9zm-4 7a1 1 0 011 1v7a1 1 0 001 1h6a1 1 0 001-1v-7a1 1 0 112 0v7a3 3 0 01-3 3H6a3 3 0 01-3-3v-7a1 1 0 011-1z"
                    clip-rule="evenodd" />
            </svg>
        </button>
    </div>
</template>