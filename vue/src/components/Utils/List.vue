<script setup>
defineProps({
  items: {
    type: Array,
    required: true,
  }
});
</script>

<template>
  <ul class="space-y-4">
    <li v-for="(item, index) in items" :key="index"
      class="bg-gray-800 rounded-lg shadow p-4 w-full">
      <div class="flex justify-between items-center w-full">
        <slot :item="item" :index="index" />
      </div>
    </li>
  </ul>
</template>
