<script setup>

</script>

<template>
  <div id="app" class="min-h-screen bg-gray-100">
    <header class="bg-green-500 text-white py-4 text-center">
      <h1 class="text-2xl font-bold">Packaging Creator</h1>
    </header>

    <div class="p-6 space-y-8">
      <!-- Package Creator Section -->
      <section class="bg-white shadow rounded-lg p-6">
        <h2 class="text-xl font-semibold mb-4">Create a Package</h2>
        <form class="space-y-4">
          <div>
            <label for="volume" class="block text-sm font-medium text-gray-700">Package Volume (in liters):</label>
            <input type="number" id="volume"
              class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-green-500 focus:border-green-500 sm:text-sm"
              required min="1" />
          </div>
          <button type="submit"
            class="inline-flex justify-center px-4 py-2 bg-green-500 text-white font-medium text-sm rounded-md hover:bg-green-600">Add
            Package</button>
        </form>
      </section>

      <!-- Packages List Section -->
      <section class="bg-white shadow rounded-lg p-6">
        <h2 class="text-xl font-semibold mb-4">Packages</h2>
        <div v-if="false" class="text-gray-500">No packages created yet.</div>
        <ul class="space-y-4">
          <li class="p-4 bg-gray-50 shadow rounded-lg">

            <!-- Products Section for each Package -->
            <div class="mt-4">
              <h3 class="text-lg font-medium">Products</h3>
              <form class="space-y-4 mt-2">
                <div>
                  <label for="product" class="block text-sm font-medium text-gray-700">Add Product:</label>
                  <input type="text"
                    class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-green-500 focus:border-green-500 sm:text-sm"
                    placeholder="Product Name" required />
                </div>
                <button type="submit"
                  class="inline-flex justify-center px-4 py-2 bg-green-500 text-white font-medium text-sm rounded-md hover:bg-green-600">Add</button>
              </form>

              <ul class="mt-4 space-y-2">
                <li class="flex items-center justify-between p-2 bg-gray-100 rounded-md">
                  <span></span>
                  <span></span>
                </li>
              </ul>
            </div>
          </li>
        </ul>
      </section>
    </div>
  </div>
</template>