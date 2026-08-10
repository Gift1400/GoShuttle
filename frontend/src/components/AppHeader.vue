<template>
  <header class="site-header" :class="{ scrolled }">
    <div class="container header-inner">
      <router-link to="/" class="brand" @click="closeMenu">
        <span class="brand-mark">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#ffffff" stroke-width="1.9" stroke-linecap="round" stroke-linejoin="round">
            <rect x="4" y="5" width="16" height="12" rx="2.2"/>
            <line x1="4" y1="11" x2="20" y2="11"/>
            <line x1="8" y1="5" x2="8" y2="11"/>
            <line x1="16" y1="5" x2="16" y2="11"/>
            <circle cx="7.5" cy="18.4" r="1.3" fill="#ffffff" stroke="none"/>
            <circle cx="16.5" cy="18.4" r="1.3" fill="#ffffff" stroke="none"/>
          </svg>
        </span>
        <span class="brand-name">GoShuttle</span>
      </router-link>

      <nav class="primary-nav" :class="{ open: menuOpen }">
        <router-link
          v-for="link in navLinks"
          :key="link.to"
          :to="link.to"
          class="nav-link"
          @click="closeMenu"
        >
          {{ link.label }}
        </router-link>

        <div class="nav-actions">
          <router-link to="/sign-in" class="btn btn-ghost" @click="closeMenu">Sign In</router-link>
          <router-link to="/register" class="btn btn-primary" @click="closeMenu">Register</router-link>
        </div>
      </nav>

      <button
        class="menu-toggle"
        :class="{ open: menuOpen }"
        aria-label="Toggle navigation menu"
        :aria-expanded="menuOpen"
        @click="menuOpen = !menuOpen"
      >
        <span></span>
        <span></span>
        <span></span>
      </button>
    </div>
  </header>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

const scrolled = ref(false)
const menuOpen = ref(false)

const navLinks = [
  { to: '/', label: 'Home' },
  { to: '/track', label: 'Track' },
  { to: '/schedule', label: 'Schedule' },
  { to: '/stops-routes', label: 'Stops & Routes' },
  { to: '/pass', label: 'My Pass' }
]

function closeMenu() {
  menuOpen.value = false
}

function handleScroll() {
  scrolled.value = window.scrollY > 8
}

onMounted(() => window.addEventListener('scroll', handleScroll, { passive: true }))
onUnmounted(() => window.removeEventListener('scroll', handleScroll))
</script>

<style scoped>
.site-header {
  position: sticky;
  top: 0;
  z-index: 50;
  background: var(--header-bg);
  backdrop-filter: blur(var(--header-blur)) saturate(160%);
  -webkit-backdrop-filter: blur(var(--header-blur)) saturate(160%);
  border-bottom: 1px solid rgba(23, 34, 63, 0.06);
  transition: box-shadow 0.2s ease, background 0.2s ease;
}

.site-header.scrolled {
  box-shadow: 0 8px 24px rgba(14, 34, 70, 0.08);
  background: var(--glass-bg-strong);
}

.header-inner {
  height: 72px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
}

.brand {
  display: flex;
  align-items: center;
  gap: 10px;
  flex-shrink: 0;
}

.brand-mark {
  width: 36px;
  height: 36px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, var(--navy-700), var(--green-600));
  box-shadow: 0 6px 14px -6px rgba(20, 44, 86, 0.5);
}

.brand-name {
  font-size: 18px;
  font-weight: 800;
  color: var(--navy-800);
  letter-spacing: -0.01em;
}

.primary-nav {
  display: flex;
  align-items: center;
  gap: 8px;
  flex: 1;
  justify-content: flex-end;
}

.nav-link {
  position: relative;
  padding: 9px 14px;
  font-size: 14.5px;
  font-weight: 600;
  color: var(--ink-soft);
  border-radius: var(--radius-sm);
  transition: color 0.15s ease, background 0.15s ease;
}

.nav-link:hover {
  color: var(--navy-800);
  background: rgba(23, 34, 63, 0.05);
}

.nav-link.router-link-active {
  color: var(--navy-800);
}

.nav-link.router-link-active::after {
  content: '';
  position: absolute;
  left: 14px;
  right: 14px;
  bottom: 3px;
  height: 2px;
  border-radius: 2px;
  background: linear-gradient(90deg, var(--navy-700), var(--green-500));
}

.nav-actions {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-left: 14px;
  padding-left: 14px;
  border-left: 1px solid rgba(23, 34, 63, 0.1);
}

.nav-actions .btn {
  padding: 9px 18px;
  font-size: 13.5px;
}

.menu-toggle {
  display: none;
  flex-direction: column;
  justify-content: center;
  gap: 5px;
  width: 38px;
  height: 38px;
  border: none;
  background: transparent;
  cursor: pointer;
  padding: 0;
}

.menu-toggle span {
  height: 2px;
  border-radius: 2px;
  background: var(--navy-800);
  transition: transform 0.2s var(--ease), opacity 0.2s var(--ease);
}

.menu-toggle.open span:nth-child(1) { transform: translateY(7px) rotate(45deg); }
.menu-toggle.open span:nth-child(2) { opacity: 0; }
.menu-toggle.open span:nth-child(3) { transform: translateY(-7px) rotate(-45deg); }

@media (max-width: 860px) {
  .menu-toggle {
    display: flex;
  }

  .primary-nav {
    position: absolute;
    top: 72px;
    left: 0;
    right: 0;
    flex-direction: column;
    align-items: stretch;
    gap: 4px;
    padding: 14px 18px 20px;
    background: var(--glass-bg-strong);
    backdrop-filter: blur(var(--header-blur)) saturate(160%);
    border-bottom: 1px solid rgba(23, 34, 63, 0.08);
    box-shadow: 0 16px 30px rgba(14, 34, 70, 0.1);

    max-height: 0;
    overflow: hidden;
    opacity: 0;
    pointer-events: none;
    transition: max-height 0.25s var(--ease), opacity 0.2s ease;
  }

  .primary-nav.open {
    max-height: 420px;
    opacity: 1;
    pointer-events: auto;
  }

  .nav-link {
    padding: 12px 10px;
  }

  .nav-actions {
    margin: 8px 0 0;
    padding: 14px 0 0;
    border-left: none;
    border-top: 1px solid rgba(23, 34, 63, 0.1);
    justify-content: stretch;
  }

  .nav-actions .btn {
    flex: 1;
  }
}
</style>
