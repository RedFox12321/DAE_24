import axios from "axios";
import { defineStore } from "pinia";
import { ref } from "vue"
import {useAuthStore} from './auth.js'

export const usePackageStore = defineStore('packages', () => {
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
            return false
        }
    }

    const getPackage = async (code) => {
        try {
            const result = await axios.get('packages/' + code)
          curPackage.value = result.data
            return curPackage.value
        } catch (e) {
            return false
        }
    }

  const getMyActivePackages = async () => {
    try {
      const result = await axios.get('packages/my/'+ authStore.username)
      activePackages.value = result.data
      activePackages.value = activePackages.value.filter(pkg => pkg.status == "ACTIVE")
      return activePackages
    } catch (e) {
      return false
    }
  };

  const getPackagesByStatus = async (status) =>{
    try {
      const result = await axios.get('packages/status/'+ status)
      packagesByStatus.value = result.data
      return packagesByStatus
    } catch (e) {
      return false
    }
  }

  const cancelPackage = async (code) => {
    try {
      await axios.patch('packages/' + code)
      return true
    } catch(e) {
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
