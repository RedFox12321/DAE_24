import { defineStore } from "pinia";
import { computed, ref } from "vue";
import { useErrorStore } from "./error";
import axios from "axios";

export const useOrderPackageStore = defineStore('orderPackage', () => {
    const errorStore = useErrorStore()

    const product = {
        productCode: 1,
        quantity: 1
    }

    const sensor = {
        id: 1,
        type: "TEMPERATURE"
    }

    const volume = {
                code: 1,
                packageType: "BOX",
                productsVolume: [
                    JSON.parse(JSON.stringify(product))
                ],
                sensors: []
    }

    const orderPackage = ref({
        code: 1,
        volumes: [
            JSON.parse(JSON.stringify(volume))
        ],
        clientUsername: ""
    })

    const packageTypes = [
        {
            value: "BOX",
            label: "Box"
        },
        {
            value: "FREEZER",
            label: "Freezer"
        },
    ]

    const sensorTypes = [
        {
            value: "TEMPERATURE",
            label: "Temperature"
        },
        {
            value: "GPS",
            label: "Geographical Location"
        },
        {
            value: "ACCELERATION",
            label: "Acceleration"
        },
        {
            value: "ATMOSPHERIC_PRESSURE",
            label: "Atmospheric pressure"
        },
    ]

    const numberOfVolumes = computed(() => {
        return orderPackage.value.volumes.length
    })

    const numberOfProductsVolumes = (volumeIndex) => {
        return orderPackage.value.volumes[volumeIndex].productsVolume.length
    }

    const createOrderPackage = async () => {
        try {
            const result = await axios.post('packages', orderPackage.value)
            return result.data
        } catch (e) {
            errorStore.setErrorMessage(
                e.response.status,
                e.response.statusText,
                e.response.data
            )
            return false
        }
    }

    const addVolume = () => {
        orderPackage.value.volumes.push(JSON.parse(JSON.stringify(volume)))
    }
    const removeVolume = (volumeIndex) => {
        if(numberOfVolumes.value > 1)
            orderPackage.value.volumes.splice(volumeIndex, 1)
    }

    const addProduct = (volumeIndex) => {
        orderPackage.value.volumes[volumeIndex].productsVolume.push(JSON.parse(JSON.stringify(product)))
    }
    const removeProduct = (volumeIndex, productIndex) => {
        if(numberOfProductsVolumes(volumeIndex) > 1)
            orderPackage.value.volumes[volumeIndex].productsVolume.splice(productIndex, 1)
    }

    const addSensor = (volumeIndex) => {
        orderPackage.value.volumes[volumeIndex].sensors.push(JSON.parse(JSON.stringify(sensor)))
    }
    const removeSensor = (volumeIndex, sensorIndex) => {
        orderPackage.value.volumes[volumeIndex].sensors.splice(sensorIndex, 1)
    }

    const resetOrderPackage = () => {
        orderPackage.value = {
            code: 1,
            volumes: [
                JSON.parse(JSON.stringify(volume))
            ],
            clientUsername: ""
        }
    }

    return {
        orderPackage,
        packageTypes,
        sensorTypes,
        numberOfVolumes,
        numberOfProductsVolumes,
        resetOrderPackage,
        createOrderPackage,
        addVolume,
        removeVolume,
        addProduct,
        removeProduct,
        addSensor,
        removeSensor
    }
})
