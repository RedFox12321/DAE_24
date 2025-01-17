<script setup>
import { computed, ref } from 'vue';

const props = defineProps({
    items: {
        type: Array,
        required: true,
    },
    filterKey: {
        type: String,
        required: true,
    }
})
const emit = defineEmits(['itemSelected'])

const searchQuery = defineModel()
const isFocused = ref(false)

const itemSelected = (item) => {
    searchQuery.value = item[props.filterKey]
    emit('itemSelected', item)
}

const filteredItems = computed(() => {
    return props.items.filter((item) =>
        item[props.filterKey].toLowerCase().includes(searchQuery.value.toLowerCase())
    )
})

const blurOnUnfocus = () => {
    setTimeout(() => {
        isFocused.value = false
    }, 250)
}
</script>

<template>
    <div class="relative">
        <input v-model="searchQuery" type="text" placeholder="Product name" @focus="isFocused = true" @blur="blurOnUnfocus"
            class="block w-full p-2 border rounded-md mb-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />

        <div v-show="isFocused" @focus="isFocused = true" @blur="blurOnUnfocus"
            class="z-10 absolute top-12 left-0 right-0 max-h-60 h-40 overflow-y-auto bg-gray-700 border-2 border-gray-300 rounded-md shadow-md">
            <ul>
                <li v-for="item in filteredItems" :key="item" class="cursor-pointer border-2 border-gray-400 w-full h-full">
                    <button @click.prevent="itemSelected(item)" class="w-full h-full hover:bg-gray-800">
                        <slot :item="item" />
                    </button>
                </li>
            </ul>
        </div>
    </div>
</template>