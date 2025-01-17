import { defineStore } from 'pinia';
import { ref, reactive } from 'vue';
import axios from "axios";

export const useSensorStore = defineStore('sensor', () => {
  const sensors = ref(JSON.parse(localStorage.getItem('sensors')) || []);
  const curSensor = ref({})

  const generateSensorValue = (type) => {
    switch (type) {
      case "Acceleration":
        return (Math.random() * 2 - 1).toFixed(2);
      case "Atmospheric pressure":
        return (Math.random() * 2 - 1 + 1013).toFixed(2);
      case "Geographical Location":
        return {
          lat: (Math.random() * 0.02 - 0.01 + 40).toFixed(6),
          lon: (Math.random() * 0.02 - 0.01 - 74).toFixed(6),
        };
      case "Temperature":
        return (Math.random() * 2 - 1 + 22).toFixed(2);
      default:
        return 0;
    }
  };

  const addSensor = (newSensor) => {
    const sensor = reactive({
      id: newSensor.id,
      type: newSensor.type,
      value: generateSensorValue(newSensor.type),
      interval: newSensor.interval,
      timer: null,
    });

    sensor.timer = setInterval(() => {
      updateSensorValueGradually(sensor);
      sendSensorData(sensor.id);
    }, sensor.interval);

    sensors.value.push(sensor);
    saveSensorsToLocalStorage();
  };

  const updateSensorValueGradually = (sensor) => {
    const change = (Math.random() > 0.5 ? 1 : -1) * (Math.random() * 0.5);
    if (sensor.type === "Geographical Location") {
      sensor.value.lat = (parseFloat(sensor.value.lat) + change).toFixed(6);
      sensor.value.lon = (parseFloat(sensor.value.lon) + change).toFixed(6);
    } else {
      sensor.value = (parseFloat(sensor.value) + change).toFixed(2);
    }
  };

  const removeSensor = (index) => {
    clearInterval(sensors.value[index].timer);
    sensors.value.splice(index, 1);
    saveSensorsToLocalStorage();
  };

  const saveSensorsToLocalStorage = () => {
    localStorage.setItem('sensors', JSON.stringify(sensors.value));
  };

  const sendSensorData = async (sensorId) => {
    const sensor = sensors.value.find((s) => s.id === sensorId);
    if (!sensor) {
      console.error("Sensor not found!");
      return;
    }

    const payload = {
      sensorId: sensor.id,
      value: sensor.value,
      // type: sensor.type,
      // timestamp: new Date().toISOString(),
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/dae24/api/sensor-history",
        payload
      );
      console.log("Data sent successfully:", response.data);
    } catch (error) {
      console.error("Error sending sensor data:", error);
    }
  };

    const getSensor = async (id) => {
        try {
            const result = await axios.get('sensors/' + id)
          curSensor.value = result.data
            return curSensor.value
        } catch (e) {
            return false
        }
    }

  return {
    sensors,
    addSensor,
    removeSensor,
    sendSensorData,
    getSensor,
    curSensor
  };
});
