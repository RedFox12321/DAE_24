<script setup>
import { computed, nextTick, onActivated, onMounted, ref } from 'vue'
import { useAuthStore } from '@/stores/auth';
import { useRouter } from 'vue-router';
import FormsInput from '../ui/FormsInput.vue';

const storeAuth = useAuthStore()
const router = useRouter()


const user = ref({
    username: null,
    password: null
})

const error = ref(false)

const submit = async () => {
    const result = await storeAuth.login(user.value)
  console.log(result)
    if (result)
        router.push('/')
    else
        error.value = true
}

const isEmpty = (field) => user.value[field] && user.value[field].length > 0 ? 0 : 1
const isPasswordValid = computed(() => user.value.password && user.value['password'].length >= 3 ? 0 : 1)

</script>

<template>
    <div class="flex items-center justify-center p-10">
        <div class="w-full max-w-md p-8 bg-white shadow-lg rounded-lg">
            <h2 class="text-2xl font-bold text-center text-primary">Welcome Back</h2>
            <p class="text-center text-gray-600">Login to your account</p>
            <form class="mt-6">
                <div class="mb-4 space-y-3">
                    <FormsInput v-model="user.username" input-type="string"
                        :error-message="null" label="Username:"
                        :as-errors="isEmpty('username')" :autofocus="true" />
                    <FormsInput v-model="user.password" input-type="password"
                        :error-message="user.password != null && isPasswordValid == 1 ? 'Password must have at least 3 characters' : null" label="Password:" :as-errors="isPasswordValid" />
                    <div v-show="error" class="text-sm italic text-red-600 ps-5">Email or password incorrect</div>
                </div>
                <button @click.prevent="submit" type="submit"
                    class="w-full px-4 py-2 mt-6 text-white bg-gray-800
                    hover:bg-gray-600 rounded-md focus:outline-none focus:ring-2 focus:ring-tertiary-light">Login</button>
            </form>
        </div>
    </div>
</template>
