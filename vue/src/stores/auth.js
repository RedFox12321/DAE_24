import axios from "axios";
import { defineStore } from "pinia";
import { ref, computed } from "vue";
import { useErrorStore } from "./error";


export const useAuthStore = defineStore('auth', () => {
  const errorStore = useErrorStore()

  const token = ref('')
  const username = ref('')

  const login = async (credentials) => {
    try {
      errorStore.resetErrorMessage()
      const tokenResponse = await axios.post('auth/login', {
        username: credentials.username,
        password: credentials.password
      })
      token.value = tokenResponse.data
      axios.defaults.headers.common.Authorization = 'Bearer ' + token.value
      username.value = credentials.username

      return true
    } catch (e) {
      errorStore.setErrorMessage(
        //e.response.status,
        0,
        //e.response.statusText,
        e,
        " Could not login with credentials"
      )
      return false
    }
  }

  const logout = () => {
    token.value = ''
    username.value = ''

    axios.defaults.headers.common.Authorization = ''

    errorStore.resetErrorMessage()
  }

  const isLoggedIn = computed(() => token.value != '')

  return {
    login,
    logout,
    username,
    isLoggedIn
  }
})
