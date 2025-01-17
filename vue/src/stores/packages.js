import axios from "axios";
import { defineStore } from "pinia";
import { ref } from "vue"
import {useAuthStore} from './stores/auth.js'

export const usePackageStore = defineStore('packages', () => {
  const authStore = useAuthStore()
  const packages = ref([])
  const activePackages = ref([])

    const getPackages = async () => {
        try {
            const result = await axios.get('packages')
            packages.value = result.data
            return packages
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
      getPackages,
      getMyActivePackages,
      cancelPackage
    }
})
