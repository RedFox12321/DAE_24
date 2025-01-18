<script setup>
import PackageList from '@/components/Logistics/PackageList.vue';
import PackageMaker from '@/components/Logistics/PackageMaker.vue';
import { onActivated, ref } from 'vue';

const inCreatorMode = ref(true)

const changeInCreatorMode = () => inCreatorMode.value = !inCreatorMode.value

onActivated(() => inCreatorMode.value = true)
</script>

<template>
  <div class="p-6 space-y-8 flex flex-col items-center w-full h-full shadow rounded-lg">
    <div class="flex flex-col items-center mb-4">
      <button @click.prevent="changeInCreatorMode"
              class="mt-4 p-3 bg-blue-500 text-white font-semibold rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
        Go to {{ inCreatorMode ? 'active Orders/Packages list' :'Order/Package creator' }}
      </button>
    </div>
    <PackageList v-show="inCreatorMode == false" />
    <PackageMaker v-show="inCreatorMode" />
  </div>
</template>
