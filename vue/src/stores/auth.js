import axios from "axios";
import { defineStore } from "pinia";
import { ref } from "vue";


export const useAuthStore = defineStore('auth', () => {
    const user = ref({})
    const token = ref()

    const login = async (credentials) => {
        try {
            const token = await axios.post('login', {
                username: credentials.username,
                password: credentials.password
            })
        token.value = token.data

        } catch (e) {

        }
    }

    return {
        user,
        login
    }
})