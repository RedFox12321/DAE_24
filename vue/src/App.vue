<script setup>


</script>

<template>
  <div class="flex flex-col h-screen">
    <header>
      <div class="bg-gray-800 text-white">
        <nav class="container mx-auto px-4 py-4 flex justify-between items-center">
          <ul class="hidden md:flex space-x-6">
            <li>
              <RouterLink to="/" class="text-gray-500 hover:text-gray-300" active-class="text-gray-300">
                Home
              </RouterLink>
            </li>
            <li>
              <RouterLink to="/packaging" class="text-gray-500 hover:text-gray-300" active-class="text-gray-300">
                Packaging
              </RouterLink>
            </li>
          </ul>
        </nav>
      </div>
    </header>

    <main class="flex-1 p-2">
      <RouterView />
    </main>

  </div>
</template>