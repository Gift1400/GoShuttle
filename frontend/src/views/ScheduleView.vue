<template>
  <div class="page schedule-page">
    <div class="container">

      <!-- Page intro -->
      <section class="page-head">
        <span class="section-eyebrow">Timetable</span>
        <h1>Bus Schedule</h1>
        <p class="page-sub">
          Departure times for every GoShuttle route between your area and CPUT campuses.
          Search by route, stop, or area to find what you need.
        </p>
      </section>

      <!-- Search + filter -->
      <section class="glass search-panel">
        <svg class="search-icon" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor"
          stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="11" cy="11" r="7"></circle>
          <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
        </svg>
        <input v-model="query" type="text" class="search-input" placeholder="Search a route, stop or area…"
          autocomplete="off" />
        <div class="status-filters">
          <button v-for="s in statusOptions" :key="s.value" class="status-chip"
            :class="{ active: activeStatus === s.value }" @click="activeStatus = s.value">
            {{ s.label }}
          </button>
        </div>
      </section>

      <!-- Route schedule cards -->
      <section class="routes-list">
        <transition-group name="card-fade">
          <article v-for="route in filteredRoutes" :key="route.id" class="glass route-card"
            :class="{ collapsed: !openRoutes[route.id] }">
            <button class="route-header" :aria-expanded="!!openRoutes[route.id]" @click="toggleRoute(route.id)">
              <span class="badge" :class="route.badgeClass">{{ route.code }}</span>

              <span class="route-header-text">
                <span class="route-title">{{ route.title }}</span>
                <span class="route-meta">
                  {{ route.stops.length }} stops · ~{{ route.duration }} min · every {{ route.frequency }} min
                </span>
              </span>

              <span class="status-pill" :class="route.status">
                <span class="status-dot"></span>
                {{ route.status === 'on-time' ? 'On Time' : 'Delayed' }}
              </span>

              <svg class="chevron" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor"
                stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <polyline points="6 9 12 15 18 9"></polyline>
              </svg>
            </button>

            <div class="route-body">
              <div class="departures">
                <span class="departures-label">Next departures</span>
                <div class="chip-row">
                  <span v-for="t in route.nextDepartures" :key="t" class="time-chip">{{ t }}</span>
                </div>
              </div>

              <ul class="stops-list">
                <li v-for="(stop, i) in route.stops" :key="stop.name" class="stop-item">
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
            </div>
          </article>
        </transition-group>

        <p v-if="!filteredRoutes.length" class="empty-state">
          No routes or stops match “{{ query }}”. Try a different area or route name.
        </p>
      </section>

    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'

const query = ref('')
const activeStatus = ref('all')

const statusOptions = [
  { value: 'all', label: 'All routes' },
  { value: 'on-time', label: 'On time' },
  { value: 'delayed', label: 'Delayed' }
]

const routes = [
  {
    id: 'g1',
    code: 'G1',
    badgeClass: 'badge-g1',
    title: 'Khayelitsha → CPUT Bellville',
    duration: 45,
    frequency: 45,
    status: 'on-time',
    nextDepartures: ['06:00', '06:45', '07:30', '08:15', '09:00'],
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
    title: 'Mitchells Plain → CPUT Cape Town',
    duration: 52,
    frequency: 60,
    status: 'delayed',
    nextDepartures: ['06:15', '07:15', '08:15', '09:15'],
    stops: [
      { name: 'Town Centre Taxi Rank', area: 'Mitchells Plain', offset: 'Start' },
      { name: 'Strandfontein Road', area: 'Near Rocklands', offset: '+18 min' },
      { name: 'CPUT Cape Town Campus', area: 'District Six / Keizersgracht', offset: '+52 min', campus: true }
    ]
  },
  {
    id: 'g3',
    code: 'G3',
    badgeClass: 'badge-g3',
    title: 'Kraaifontein → CPUT Bellville',
    duration: 38,
    frequency: 50,
    status: 'on-time',
    nextDepartures: ['06:10', '07:00', '07:50', '08:40'],
    stops: [
      { name: 'Kraaifontein Station', area: 'Kraaifontein', offset: 'Start' },
      { name: 'Bellair Road', area: 'Near Vredekloof', offset: '+16 min' },
      { name: 'CPUT Bellville Campus', area: 'Symphony Way entrance', offset: '+38 min', campus: true }
    ]
  }
]

const openRoutes = reactive({ g1: true, g2: false, g3: false })

function toggleRoute(id) {
  openRoutes[id] = !openRoutes[id]
}

const filteredRoutes = computed(() => {
  const q = query.value.trim().toLowerCase()

  return routes.filter((route) => {
    const matchesStatus = activeStatus.value === 'all' || route.status === activeStatus.value
    if (!matchesStatus) return false
    if (!q) return true

    const haystack = [
      route.title,
      route.code,
      ...route.stops.map((s) => `${s.name} ${s.area}`)
    ]
      .join(' ')
      .toLowerCase()

    return haystack.includes(q)
  })
})
</script>

<style scoped>
.page-head {
  max-width: 640px;
  margin-bottom: 32px;
  color:white;
}

.page-head h1 {
  font-size: 34px;
  font-weight: 800;
  margin-bottom: 10px;
  color:white;
}

.page-sub {
  font-size: 15.5px;
  line-height: 1.6;
}

/* Search panel */
.search-panel {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 14px 18px;
  margin-bottom: 28px;
  flex-wrap: wrap;
}

.search-icon {
  color: white;
  flex-shrink: 2;
}

.search-input {
  flex: 1;
  min-width: 180px;
  border: none;
  outline: none;
  background: transparent;
  font-size: 15px;
  color: white;
  font-family: inherit;
}

.search-input::placeholder {
  color: white;
}

.status-filters {
  display: flex;
  gap: 8px;
}

.status-chip {
  border: 1px solid rgba(23, 34, 63, 0.12);
  background: rgb(0 0 0 / 47%);
  color: white;
  font-size: 13px;
  font-weight: 400;
  padding: 7px 14px;
  border-radius: var(--radius-full);
  cursor: pointer;
  transition: background 0.15s ease, color 0.15s ease, border-color 0.15s ease;
}

.status-chip.active {
  background: var(--navy-800);
  border-color: var(--navy-800);
  color: #fff;
}

/* Route cards */
.routes-list {
  display: flex;
  flex-direction: column;
  gap: 18px;
}

.route-card {
  padding: 0;
  overflow: hidden;
}

.route-header {
  width: 100%;
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 20px 22px;
  border: none;
  background: transparent;
  cursor: pointer;
  text-align: left;
  font-family: inherit;
}

.badge {
  width: 40px;
  height: 40px;
  font-size: 14px;
  flex-shrink: 0;
}

.route-header-text {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
  gap: 3px;
}

.route-title {
  font-weight: 700;
  font-size: 18px;
  color: white;
}

.route-meta {
  font-size: 13px;
  color: white;
}

.status-pill {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 11.5px;
  font-weight: 700;
  letter-spacing: 0.02em;
  padding: 6px 12px;
  border-radius: var(--radius-full);
  white-space: nowrap;
  flex-shrink: 0;
}

.status-pill.on-time {
  background: rgba(9, 157, 9, 0.462);
  color: rgb(0, 255, 0);
}

.status-pill.delayed {
  background: rgba(210, 128, 20, 0.12);
  color: #ffb30f;
}

.status-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: currentColor;
}

.chevron {
  color: var(--muted);
  flex-shrink: 0;
  transition: transform 0.2s ease;
}

.route-card.collapsed .chevron {
  transform: rotate(-90deg);
}

.route-body {
  overflow: hidden;
  max-height: 700px;
  opacity: 1;
  padding: 0 22px 22px;
  transition: max-height 0.3s var(--ease), opacity 0.25s ease, padding 0.3s var(--ease);
}

.route-card.collapsed .route-body {
  max-height: 0;
  opacity: 0;
  padding-top: 0;
  padding-bottom: 0;
}

.departures {
  padding-top: 4px;
  margin-bottom: 18px;
}

.departures-label {
  display: block;
  font-size: 12px;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.04em;
  color: white;
  margin-bottom: 10px;
}

.chip-row {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.time-chip {
  background: rgba(29, 67, 134, 0.526);
  color: rgb(252, 252, 254);
  font-weight: 700;
  font-size: 13px;
  padding: 6px 12px;
  border-radius: var(--radius-full);
}

.stops-list {
  position: relative;
  border-top: 1px solid var(--divider);
  padding-top: 14px;
}

.stops-list::before {
  content: '';
  position: absolute;
  left: 4px;
  top: 24px;
  bottom: 14px;
  width: 2px;
  background: var(--divider);
}

.stop-item {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  padding: 8px 0;
  position: relative;
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
  color: white;
}

.stop-sub {
  display: block;
  font-size: 12px;
  color: rgb(191, 189, 189);
  margin-top: 2px;
}

.campus-badge {
  background: rgba(24, 77, 168, 0.582);
  color: white;
  font-size: 10px;
  font-weight: 500;
  padding: 4px 10px;
  border-radius: 7px;
}

.stop-time {
  font-size: 12.5px;
  font-weight: 500;
  color: white;
  white-space: nowrap;
  flex-shrink: 0;
}

.empty-state {
  text-align: center;
  padding: 40px 20px;
  color: white;
  font-size: 14.5px;
}

/* Card enter/leave transition */
.card-fade-enter-active,
.card-fade-leave-active {
  transition: opacity 0.2s ease, transform 0.2s ease;
}

.card-fade-enter-from,
.card-fade-leave-to {
  opacity: 0;
  transform: translateY(6px);
}

@media (max-width: 560px) {
  .route-header {
    flex-wrap: wrap;
  }

  .status-pill {
    order: 3;
  }
}
</style>
