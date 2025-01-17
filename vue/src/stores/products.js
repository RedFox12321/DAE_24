import axios from "axios";
import { defineStore } from "pinia";
import { ref } from "vue";
import { useErrorStore } from "./error";


export const useProductsStore = defineStore('products', () => {
    const errorStore = useErrorStore()
    
    
    const products = ref([])

    const getProducts = async () => {
        try {
            errorStore.resetErrorMessage()
            const result = await axios.get('products')
            products.value = result.data
            return products
        } catch (e) {
            errorStore.setErrorMessage(
                e.response.status,
                e.response.statusText,
                " Could not fetch products"
            )
            return false
        }
    }

    return {
        products,
        getProducts
    }
})