<script setup>
import { useRouter } from 'vue-router';


const router = useRouter()
const props = defineProps({
    pathList: {
        type: Array,
        required: true
    }
})

const routeToLink = (routeTo) => {
    router.push(routeTo)
}

</script>

<template>
    <div class="flex items-center space-x-2 text-gray-300">
        <div v-for="path, index in pathList" :key="path">
            <button @click.prevent="routeToLink(path.to)"
                class="underline" :disabled="index + 1 == pathList.length">{{ path.name }}</button>
            <span v-if="index + 1 < pathList.length"> → </span>
        </div>
    </div>
</template>