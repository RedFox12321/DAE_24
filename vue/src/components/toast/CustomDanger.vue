<template>
    <div>
        <h4>{{title}}</h4>
        <p>{{description}}</p>
    </div>
</template>
  
<script setup>
defineProps({
    title: {
        type: String,
        default: '',
    },
    description: {
        type: String,
        default: '',
    },
    background: {
        type: String,
        default: '',
    },
});
</script>