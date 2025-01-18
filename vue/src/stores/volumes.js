import axios from "axios";
import { defineStore } from "pinia";
import { ref } from "vue"
import { useErrorStore } from "./error";

export const useVolumeStore = defineStore('volumes', () => {
  const errorStore = useErrorStore()
  const volumes = ref([])
  const curVolume = ref({})

  const getVolumes = async () => {
    try {
      const result = await axios.get('volumes')
      volumes.value = result.data
      return volumes.value
    } catch (e) {
      errorStore.setErrorMessage(
        e.response.status,
        e.response.statusText,
        e.response.data
      )
      return false
    }
  }

  const getVolume = async (code) => {
    try {
      const result = await axios.get('volumes/' + code)
      curVolume.value = result.data
      const productPromises = curVolume.value.productsVolume.map(async (pv) => {
        const product = await axios.get('products/' + pv.productCode);
        pv.productName = product.data.name; 
      });

      await Promise.all(productPromises);
      return curVolume.value
    } catch (e) {
      errorStore.setErrorMessage(
        e.response.status,
        e.response.statusText,
        e.response.data
      )
      return false
    }
  }

  const deliverVolume = async (code) => {
    try {
      await axios.patch('volumes/' + code ?? curVolume.value.code)
      curVolume.value.status = "DELIVERED"
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
    volumes,
    getVolumes,
    deliverVolume,
    getVolume,
    curVolume
  }
})
