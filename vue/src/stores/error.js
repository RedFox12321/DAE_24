import { defineStore } from "pinia"
import { ref } from "vue"
import { toast } from 'vue3-toastify'
import CustomDanger from "@/components/toast/CustomDanger.vue"
import 'vue3-toastify/dist/index.css'

export const useErrorStore = defineStore('error', () => {
    const title = ref('')
    const description = ref('')

    const setErrorMessage = (statusCode, statusText, message) => {
        title.value = "Error"
        description.value = "An unexpected error occurred."

        switch (statusCode) {
            case 401:
                title.value = "Unauthorized"
                description.value = "You are not authorized to access this resource."
                break
            case 403:
                title.value = "Forbidden"
                description.value = "You do not have permission to access this resource."
                break
            case 404:
                title.value = "Not Found"
                description.value = message
                break
            case 409:
                title.value = "Conflict"
                description.value = message
                break
            case 500:
                title.value = "Server Error"
                description.value = "An error occurred on the server. Please try again later."
                break
            default:
                title.value = statusText
                description.value = message
        }

        sendToast()
    }

    const resetErrorMessage = () => {
        title.value = ''
        description.value = ''
    }
    
    const sendToast = () => { 
        toast.error(CustomDanger, {
            autoClose: 3000,
            closeOnClick: true,
            draggable: true,
            progress: undefined,
            type: 'error',
            expandCustomProps: true,
            contentProps: {
              title: title.value,
              description: description.value,
            },
          })
    }

    return {
        title,
        description,
        setErrorMessage,
        resetErrorMessage
    }
})