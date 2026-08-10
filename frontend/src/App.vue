<template>
  <div class="ambient-bg" aria-hidden="true">
    <span class="blob-1"></span>
    <span class="blob-2"></span>
    <span class="blob-3"></span>
  </div>

  <AppHeader />

  <main>
    <router-view v-slot="{ Component, route }">
      <transition name="route-fade" mode="out-in">
        <component :is="Component" :key="route.path" />
      </transition>
    </router-view>
  </main>

  <AppFooter />
</template>

<script setup>
import AppHeader from './components/AppHeader.vue'
import AppFooter from './components/AppFooter.vue'
</script>
