import axios from "axios";
import { defineStore } from "pinia";
import { ref, computed } from "vue";


export const useAuthStore = defineStore('auth', () => {
    const token = ref('')
  const username = ref('')

    const login = async (credentials) => {
        try {
            const tokenResponse = await axios.post('auth/login', {
                username: credentials.username,
                password: credentials.password
            })
            token.value = tokenResponse.data
            axios.defaults.headers.common.Authorization = 'Bearer ' + token.value
          username.value = credentials.username
          return true

        } catch (e) {
          return false
        }
    }

  const isLoggedIn = computed(() => {
    return token.value != ''
  })

    return {
      login,
      username,
      isLoggedIn
    }
})
