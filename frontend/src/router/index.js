import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import('../views/HomeView.vue'),
    meta: { title: 'Home' }
  },
  {
    path: '/track',
    name: 'track',
    component: () => import('../views/TrackView.vue'),
    meta: { title: 'Live Tracking' }
  },
  {
    path: '/schedule',
    name: 'schedule',
    component: () => import('../views/ScheduleView.vue'),
    meta: { title: 'Bus Schedule' }
  },
  {
    path: '/stops-routes',
    name: 'stops-routes',
    component: () => import('../views/StopsRoutesView.vue'),
    meta: { title: 'Stops & Routes' }
  },
  {
    path: '/pass',
    name: 'pass',
    component: () => import('../views/MyPassView.vue'),
    meta: { title: 'My Pass' }
  },
  {
    path: '/sign-in',
    name: 'sign-in',
    component: () => import('../views/LoginView.vue'),
    meta: { title: 'Sign In' }
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/RegisterView.vue'),
    meta: { title: 'Create Account' }
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'not-found',
    component: () => import('../views/NotFoundView.vue'),
    meta: { title: 'Page not found' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior() {
    return { top: 0 }
  }
})

router.afterEach((to) => {
  document.title = to.meta?.title ? `GoShuttle — ${to.meta.title}` : 'GoShuttle'
})

export default router
