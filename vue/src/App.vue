<script setup>
import {useAuthStore} from './stores/auth.js'
import {computed} from 'vue'
import {useRouter} from 'vue-router'

const storeAuth = useAuthStore()
const router = useRouter()

const links = [
  { label: "Home", to: "/", render: true },
  { label: "Logistics", to: "/logistics", render: true },
  { label: "Sensors", to: "/sensors", render: true },
  { label: "Packages", to: "/packages", render: true },
  { label: "Customer Support", to: "/customerSupport", render: true },
  //{ label: "", to: "", render: true },
]

const filteredLinks = computed(() => {
  return links.filter(link => link.render)
})

const handleLogout = () => {
  storeAuth.logout()
  router.push('/')
}
</script>

<template>
  <div class="flex flex-col min-h-screen">
    <header>
      <div class="bg-gray-800 text-white">
        <nav class="container mx-auto px-4 py-4 flex justify-between items-center">
          <ul class="hidden md:flex space-x-6">
            <li v-for="link in filteredLinks" :key="link.label" class="px-5">
              <RouterLink :to="link.to" class="text-gray-200 hover:text-gray-100" active-class="text-gray-300">
                {{ link.label }}
              </RouterLink>
            </li>
          </ul>
          <div>
            <RouterLink v-if="!storeAuth.isLoggedIn" to="/login" class="text-gray-500 hover:text-gray-300"
                        active-class="text-gray-300">
              Login
            </RouterLink>
            <div v-else class="relative group">
              <div class="flex items-center text-gray-500 cursor-pointer group-hover:text-gray-300">
                {{ storeAuth.username }}
              </div>
              <div
                class="absolute left-0 mt-2 bg-gray-700 border border-gray-600 rounded shadow-md w-28 opacity-0 group-hover:opacity-100 group-hover:block transition-opacity"
              >
                <button
                  @click="handleLogout"
                  class="px-4 py-2 text-sm text-gray-300 hover:bg-gray-600 hover:text-white w-full text-left"
                >
                  Logout
                </button>
              </div>
            </div>
          </div>
        </nav>
        <div class="h-2 w-screen bg-gray-900"/>
      </div>
    </header>

    <main class="flex-1 p-2 bg-gray-700">
      <RouterView/>
    </main>
  </div>
</template>
