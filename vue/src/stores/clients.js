import axios from "axios";
import { defineStore } from "pinia";
import { ref } from "vue";
import { useErrorStore } from "./error";


export const useClientStore = defineStore('clients', () =>{
    const errorStore = useErrorStore()
    
    const clients = ref([])
    const error = ref()
    const getClients = async () => {
        try {
            errorStore.resetErrorMessage()
            const result = await axios.get('clients')
            clients.value = result.data
            return clients.value
        } catch (e) {
            errorStore.setErrorMessage(
                e.response.status,
                e.response.statusText,
                " Could not fetch clients"
            )
            return false
        }
    }

    return {
        clients,
        getClients,
        error
    }
})