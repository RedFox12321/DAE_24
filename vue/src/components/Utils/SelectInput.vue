<script setup>

const model = defineModel()

const props = defineProps({
    label: {
        type: String,
        default: ""
    },
    blankOption: {
        type: [Object, Boolean],
        default: false
    },
    blankOptionLabel: {
        type: String,
        default: ""
    },
    multipleChoice: {
        type: Boolean,
        default: false
    },
    options: {
        type: Array,
        required: true
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

</script>

<template>
    <div :class="`flex ${ props.inRow ? 'flex-row justify-center items-center' : 'flex-col' }`">
        <label class="block text-gray-400 font-semibold m-2">{{ props.label }}</label>
        <select v-model="model"
            class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                :multiple="multipleChoice" :required="props.required">
            <option v-if="!multipleChoice && props.blankOption != false" :value="props.blankOption" disabled selected>{{ blankOptionLabel }}</option>
            <option v-for="option, index in options" :key="index" :value="option.value">{{ option.label }}</option>
        </select>
    </div>
</template>