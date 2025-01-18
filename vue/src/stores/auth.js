import axios from "axios";
import { defineStore } from "pinia";
import { ref, computed } from "vue";
import { useErrorStore } from "./error";

export const useAuthStore = defineStore('auth', () => {
  const errorStore = useErrorStore()

  const token = ref('')
  const user = ref({})

  const username = computed(() => user.value.username ?? '')
  const userFullname = computed(() => user.value.name ?? '')
  const userEmail = computed(() => user.value.email ?? '')
  const userType = computed(() => user.value.type ?? '')

  const isLoggedIn = computed(() => token.value != '')


  const login = async (credentials) => {
    try {
      errorStore.resetErrorMessage()
      const tokenResponse = await axios.post('auth/login', {
        username: credentials.username,
        password: credentials.password
      })
      token.value = tokenResponse.data
      axios.defaults.headers.common.Authorization = 'Bearer ' + token.value

      const result = await axios.get('auth/me')
      user.value = result.data
      
      return user.value
    } catch (e) {
      errorStore.setErrorMessage(
        e.response.status ?? 0,
        e,
        " Could not login with credentials"
      )
      return false
    }
  }

  const logout = () => {
    token.value = ''
    user.value = {}

    axios.defaults.headers.common.Authorization = ''

    errorStore.resetErrorMessage()
  }

  return {
    login,
    logout,
    user,
    username,
    userFullname,
    userEmail,
    userType,
    isLoggedIn
  }
})
