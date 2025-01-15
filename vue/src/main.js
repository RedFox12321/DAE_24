import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import axios from 'axios'

const apiDomain = import.meta.env.VITE_API_DOMAIN

const app = createApp(App)

app.use(createPinia())
app.use(router)

axios.defaults.baseURL = `http://${apiDomain}/dae24/api`

app.mount('#app')
