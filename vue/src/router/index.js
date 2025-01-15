import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import PackageMaker from '@/components/PackageMaker.vue'
import SensorList from '@/components/SensorList.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
    },
    {
      path: '/packaging',
      name: 'packaging',
      component: PackageMaker
    },
    {
      path: '/sensors',
      name: 'sensors',
      component: SensorList
    }
  ],
})

export default router
