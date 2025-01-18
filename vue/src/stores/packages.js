import axios from "axios";
import { defineStore } from "pinia";
import { ref } from "vue"
import { useAuthStore } from './auth.js'
import { useErrorStore } from "./error.js";

export const usePackageStore = defineStore('packages', () => {
  const errorStore = useErrorStore()
  const authStore = useAuthStore()
  const packages = ref([])
  const curPackage = ref({})
  const activePackages = ref([])
  const packagesByStatus = ref([])

  const getPackages = async () => {
    try {
      const result = await axios.get('packages')
      packages.value = result.data
      return packages.value
    } catch (e) {
      errorStore.setErrorMessage(
        e.response.status,
        e.response.statusText,
        e.response.data
      )
      return false
    }
  }

  const getPackage = async (code) => {
    try {
      const result = await axios.get('packages/' + code)
      curPackage.value = result.data
      return curPackage.value
    } catch (e) {
      errorStore.setErrorMessage(
        e.response.status,
        e.response.statusText,
        e.response.data
      )
      return false
    }
  }

  const getMyActivePackages = async () => {
    try {
      const result = await axios.get('packages/my/' + authStore.username)
      activePackages.value = result.data
      activePackages.value = activePackages.value.filter(pkg => pkg.status == "ACTIVE")
      return activePackages
    } catch (e) {
      errorStore.setErrorMessage(
        e.response.status,
        e.response.statusText,
        e.response.data
      )
      return false
    }
  };

  const getPackagesByStatus = async (status) => {
    try {
      const result = await axios.get('packages/status/' + status)
      packagesByStatus.value = result.data
      return packagesByStatus
    } catch (e) {
      errorStore.setErrorMessage(
        e.response.status,
        e.response.statusText,
        e.response.data
      )
      return false
    }
  }

  const cancelPackage = async (code) => {
    try {
      await axios.patch('packages/' + code, {})
      return true
    } catch (e) {
      errorStore.setErrorMessage(
        e.response.status,
        e.response.statusText,
        e.response.data
      )
      return false
    }
  }

  return {
    packages,
    activePackages,
    packagesByStatus,
    getPackages,
    getMyActivePackages,
    getPackagesByStatus,
    cancelPackage,
    getPackage,
    curPackage
  }
})
