<script setup>
  import {useAuthStore} from './stores/auth.js'
  import {computed, ref, render} from 'vue'
  const storeAuth = useAuthStore();

const links = [
  { label: "Home", to: "/", render: true },
  { label: "Logistics", to: "/logistics", render: true },
  { label: "Sensors", to: "/sensors", render: true },
  { label: "Packages", to: "/packages", render: true },
  { label: "Customer Support", to: "/customerSupport", render: true },
  //{ label: "", to: "", render: true },
]

const filteredLinks = computed(() => {
  return links.filter(link => link.render);
})

</script>

<template>
  <div class="flex flex-col min-h-screen">
    <header>
      <div class="bg-gray-800 text-white">
        <nav class="container mx-auto px-4 py-4 flex justify-between items-center">
          <ul class="hidden md:flex space-x-6">
            <li v-for="link in filteredLinks" :key="link" class="px-5">
              <RouterLink :to="link.to" class="text-gray-200 hover:text-gray-100" active-class="text-gray-300">
                {{ link.label }}
              </RouterLink>
            </li>
          </ul>
          <div>
              <RouterLink v-if="!storeAuth.isLoggedIn" to="/login" class="text-gray-500 hover:text-gray-300" active-class="text-gray-300">
                Login
              </RouterLink>
              <span v-else class="text-gray-500">
                User {{storeAuth.username}}
              </span>
          </div>
        </nav>
        <div class="h-2 w-screen bg-gray-900" />
      </div>
    </header>

    <main class="flex-1 p-2 bg-gray-700">
      <RouterView />
    </main>
  </div>
</template>
