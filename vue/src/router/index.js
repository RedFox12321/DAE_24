import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import LogisticsView from '@/views/LogisticsView.vue'
import SensorList from '@/components/SensorList.vue'
import LoginPage from '@/components/LoginPage.vue'
import PackageList from '@/components/PackageList.vue'
import CustomerSupportView from "@/views/CustomerSupportView.vue";
import { useAuthStore } from '@/stores/auth'

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
    },
    {
      path: '/customerSupport',
      name: 'customer support',
      component: CustomerSupportView,
    }
  ],
})

router.beforeEach(async (to, from, next) => {
  const storeAuth = useAuthStore()
  const anonymous = ['home', 'login', 'sensors']

  if (anonymous.includes(to.name) || storeAuth.isLoggedIn)
    next()
  else {
    if (confirm('You must be logged in to access this page!')) {
      next({ name: 'login' })
    } else {
      next(false)
    }
  }
})

export default router
