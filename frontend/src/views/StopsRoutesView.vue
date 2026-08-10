<template>
  <div class="page stops-page">
    <div class="container">

      <section class="page-head">
        <span class="section-eyebrow">Explore</span>
        <h1>Stops &amp; Routes</h1>
        <p class="page-sub">Browse every GoShuttle route and pick your usual stop.</p>
      </section>

      <section class="glass search-panel">
        <svg class="search-icon" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="11" cy="11" r="7"></circle>
          <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
        </svg>
        <input v-model="query" type="text" class="search-input" placeholder="Search stop or area…" autocomplete="off" />
      </section>

      <section class="routes-list">
        <article
          v-for="route in filteredRoutes"
          :key="route.id"
          class="glass route-card"
        >
          <div class="route-header">
            <span class="badge" :class="route.badgeClass">{{ route.code }}</span>
            <div class="route-header-text">
              <span class="route-title">{{ route.title }}</span>
              <span class="route-meta">{{ route.stops.length }} stops · ~{{ route.duration }} min · every {{ route.frequency }} min</span>
            </div>
          </div>

          <ul class="stops-list">
            <li
              v-for="(stop, i) in route.stops"
              :key="stop.name"
              class="stop-item"
              :class="{ selected: selectedStop === stop.name }"
              @click="selectStop(stop.name)"
            >
              <span class="dot" :class="i === 0 || i === route.stops.length - 1 ? 'major' : 'minor'"></span>
              <span class="stop-info">
                <span class="stop-name-row">
                  <span class="stop-name">{{ stop.name }}</span>
                  <span v-if="stop.campus" class="campus-badge">Campus</span>
                </span>
                <span class="stop-sub">{{ stop.area }}</span>
              </span>
              <span class="stop-time">{{ stop.offset }}</span>
            </li>
          </ul>
        </article>

        <p v-if="!filteredRoutes.length" class="empty-state">No stops match your search.</p>
      </section>

      <transition name="pref-fade">
        <div v-if="selectedStop" class="glass pref-banner">
          <span>Preferred stop set to <strong>{{ selectedStop }}</strong></span>
          <router-link to="/schedule" class="link">View its schedule →</router-link>
        </div>
      </transition>

    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const query = ref('')
const selectedStop = ref('')

const routes = [
  {
    id: 'g1',
    code: 'G1',
    badgeClass: 'badge-g1',
    title: 'Khayelitsha → Bellville',
    duration: 45,
    frequency: 45,
    stops: [
      { name: 'Site B Taxi Rank', area: 'Khayelitsha', offset: 'Start' },
      { name: 'Site C Community Hall', area: 'Khayelitsha', offset: '+8 min' },
      { name: 'Delft Main Road', area: 'Near Leiden', offset: '+22 min' },
      { name: 'CPUT Bellville Campus', area: 'Symphony Way entrance', offset: '+45 min', campus: true }
    ]
  },
  {
    id: 'g2',
    code: 'G2',
    badgeClass: 'badge-g2',
    title: 'Mitchells Plain → Cape Town',
    duration: 52,
    frequency: 60,
    stops: [
      { name: 'Town Centre Taxi Rank', area: 'Mitchells Plain', offset: 'Start' },
      { name: 'Strandfontein Road', area: 'Near Rocklands', offset: '+18 min' },
      { name: 'CPUT Cape Town Campus', area: 'District Six / Keizersgracht', offset: '+52 min', campus: true }
    ]
  }
]

function selectStop(name) {
  selectedStop.value = selectedStop.value === name ? '' : name
}

const filteredRoutes = computed(() => {
  const q = query.value.trim().toLowerCase()
  if (!q) return routes

  return routes
    .map((route) => {
      const matchingStops = route.stops.filter((s) =>
        `${s.name} ${s.area}`.toLowerCase().includes(q)
      )
      const routeMatches = route.title.toLowerCase().includes(q)
      if (routeMatches) return route
      if (matchingStops.length) return { ...route, stops: matchingStops }
      return null
    })
    .filter(Boolean)
})
</script>

<style scoped>
.page-head {
  max-width: 600px;
  margin-bottom: 28px;
}

.page-head h1 {
  font-size: 34px;
  font-weight: 800;
  margin-bottom: 10px;
}

.page-sub {
  font-size: 15.5px;
  line-height: 1.6;
}

.search-panel {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 14px 18px;
  margin-bottom: 28px;
}

.search-icon {
  color: var(--muted);
  flex-shrink: 0;
}

.search-input {
  flex: 1;
  border: none;
  outline: none;
  background: transparent;
  font-size: 15px;
  color: var(--ink);
  font-family: inherit;
}

.search-input::placeholder {
  color: var(--muted);
}

.routes-list {
  display: flex;
  flex-direction: column;
  gap: 18px;
  margin-bottom: 22px;
}

.route-card {
  padding: 22px;
}

.route-header {
  display: flex;
  gap: 14px;
  align-items: flex-start;
  padding-bottom: 16px;
  margin-bottom: 8px;
  border-bottom: 1px solid var(--divider);
}

.badge {
  width: 38px;
  height: 38px;
  font-size: 13px;
  flex-shrink: 0;
}

.route-title {
  display: block;
  font-weight: 700;
  font-size: 15.5px;
  color: var(--ink);
}

.route-meta {
  display: block;
  font-size: 12.5px;
  color: var(--muted);
  margin-top: 3px;
}

.stops-list {
  position: relative;
}

.stops-list::before {
  content: '';
  position: absolute;
  left: 4px;
  top: 14px;
  bottom: 14px;
  width: 2px;
  background: var(--divider);
}

.stop-item {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  padding: 10px 8px;
  border-radius: 10px;
  cursor: pointer;
  position: relative;
  transition: background 0.15s ease;
}

.stop-item:hover {
  background: rgba(23, 34, 63, 0.03);
}

.stop-item.selected {
  background: rgba(27, 59, 115, 0.07);
}

.stop-item.selected .stop-name {
  color: var(--navy-700);
}

.dot {
  flex-shrink: 0;
  border-radius: 50%;
  margin-top: 5px;
  position: relative;
  z-index: 1;
}

.dot.major {
  width: 10px;
  height: 10px;
  background: var(--navy-700);
  box-shadow: 0 0 0 3px rgba(255, 255, 255, 0.9);
}

.dot.minor {
  width: 7px;
  height: 7px;
  margin: 6.5px 1.5px 0;
  background: var(--green-500);
  box-shadow: 0 0 0 3px rgba(255, 255, 255, 0.9);
}

.stop-info {
  flex: 1;
  min-width: 0;
}

.stop-name-row {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 6px;
}

.stop-name {
  font-weight: 600;
  font-size: 14px;
  color: var(--ink);
}

.stop-sub {
  display: block;
  font-size: 12px;
  color: var(--muted);
  margin-top: 2px;
}

.campus-badge {
  background: rgba(27, 59, 115, 0.1);
  color: var(--navy-700);
  font-size: 10px;
  font-weight: 700;
  padding: 2px 8px;
  border-radius: 7px;
}

.stop-time {
  font-size: 12.5px;
  font-weight: 700;
  color: var(--green-600);
  white-space: nowrap;
}

.empty-state {
  text-align: center;
  padding: 40px 20px;
  color: var(--muted);
  font-size: 14.5px;
}

.pref-banner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  padding: 14px 20px;
  font-size: 13.5px;
  flex-wrap: wrap;
}

.pref-fade-enter-active,
.pref-fade-leave-active {
  transition: opacity 0.2s ease, transform 0.2s ease;
}
.pref-fade-enter-from,
.pref-fade-leave-to {
  opacity: 0;
  transform: translateY(-6px);
}
</style>
