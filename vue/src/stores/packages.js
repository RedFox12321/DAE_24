import axios from "axios";
import { defineStore } from "pinia";
import { ref } from "vue"

export const usePackageStore = defineStore('packages', () => {
  const packages = ref([])

    const getPackages = async () => {
        try {
            const result = await axios.get('packages')
            packages.value = result.data
            return packages
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
        getPackages,
      cancelPackage
    }
})
