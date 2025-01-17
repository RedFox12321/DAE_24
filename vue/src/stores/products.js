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
            return products.value
        } catch (e) {
            errorStore.setErrorMessage(
                e.response.status,
                e.response.statusText,
                " Could not fetch products"
            )
            return false
        }
    }

    const getProduct = async (code) => {
        try {
            const result = await axios.get('products/' + code)
            return result.data
        } catch (e) {
            errorStore.setErrorMessage(
                0,//e.response.status,
                e,//e.response.statusText,
                " Could not fetch product with code " + code
            )
            return false
        }
    }

    return {
        products,
        getProducts,
      getProduct
    }
})
