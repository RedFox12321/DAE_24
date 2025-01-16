import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import LogisticsView from '@/views/LogisticsView.vue'
import SensorList from '@/components/SensorList.vue'
import LoginPage from '@/components/LoginPage.vue'
import PackageList from '@/components/PackageList.vue'

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
      path: '/logistics',
      name: 'logistics',
      component: LogisticsView
    },
    {
      path: '/sensors',
      name: 'sensors',
      component: SensorList
    },
    {
      path: '/packages',
      name: 'packages',
      component: PackageList
    },
    {
      path: '/login',
      name: 'login',
      component: LoginPage
    }
  ],
})

export default router
