<script setup>

const model = defineModel()

const props = defineProps({
    label: {
        type: String,
        default: ""
    },
    placeholder: {
        type: String,
        default: ""
    },
    type: {
        type: String,
        default: "text"
    },
    required: {
        type: Boolean,
        default: false
    },
    inRow: {
        type: Boolean,
        default: false
    }
})

const emit = defineEmits(['inputChanged'])

</script>

<template>
    <div :class="`flex ${ props.inRow ? 'flex-row justify-center items-center' : 'flex-col' }`">
        <label class="block text-gray-400 font-semibold m-2">{{ props.label }}</label>
        <input v-model="model" :type="type" @change="emit('inputChanged')"
            class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            :placeholder="placeholder" :required="required">
    </div>
</template>