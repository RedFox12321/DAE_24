import axios from "axios";
import { defineStore } from "pinia";
import { ref, computed } from "vue";
import { useErrorStore } from "./error";

export const useAuthStore = defineStore('auth', () => {
  const errorStore = useErrorStore()

  const token = ref('')
  const user = ref({})
  const refresher = ref(undefined)

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
      
      setTokenRefresher()

      return user.value
    } catch (e) {
      errorStore.setErrorMessage(
        0,
        e.response.statusText,
        "Username or password incorrect."
      )
      return false
    }
  }

  const setTokenRefresher = () => {
    resetRefresher()
    setInterval(async () => {
      try {
        const tokenResponse = await axios.post('auth/refreshtoken')
        token.value = tokenResponse.data
        axios.defaults.headers.common.Authorization = 'Bearer ' + token.value
        
        return true
      } catch (e) {
        resetRefresher()
        errorStore.setErrorMessage(
            e.response.status,
            e.response.statusText,
            e.response.data
        )
        return false
      }
    }, 50 * 60 * 1000) //50min * 60sec * 1000ms = 50 min in ms
  }

  const resetRefresher = () => {
    if(refresher.value)
      clearInterval(refresher.value)
    refresher.value = undefined
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
