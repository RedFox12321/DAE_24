import axios from "axios";
import { defineStore } from "pinia";
import { ref } from "vue";
import { useErrorStore } from "./error";


export const useProductsStore = defineStore('products', () => {
    const errorStore = useErrorStore()
    
    const products = ref([])

    const getProducts = async () => {
        errorStore.resetMessages()
        try {
            const result = await axios.get('products')
            const products = result.data
            return products
        } catch (e) {
            return false
        }
    }

    return {
        products,
        getProducts
    }
})