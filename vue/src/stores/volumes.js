import axios from "axios";
import { defineStore } from "pinia";
import { ref } from "vue"

export const useVolumeStore = defineStore('volumes', () => {
  const volumes = ref([])
  const curVolume = ref({})

  const getVolumes = async () => {
    try {
      const result = await axios.get('volumes')
      volumes.value = result.data
      return volumes.value
    } catch (e) {
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
      return false
    }
  }

  return {
    volumes,
    getVolumes,
    getVolume,
    curVolume
  }
})
