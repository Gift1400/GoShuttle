<template>
  <div class="page track-page">
    <div class="container">

      <section class="page-head">
        <router-link to="/" class="back-link">← Back to Home</router-link>
        <span class="section-eyebrow">Live Tracking</span>
        <h1>
          {{ bus.route }}
          <span class="live-pill">
            <span class="live-dot"></span>
            Live
          </span>
        </h1>
      </section>

      <div class="track-grid">

        <section class="glass map-card">
          <h2 class="card-title">Live Map</h2>
          <div id="map" class="map-container"></div>
        </section>

        <section class="glass info-card">
          <h2 class="card-title">Bus details</h2>

          <div class="info-row">
            <span class="info-label">Bus number</span>
            <span class="info-value">{{ bus.number }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">Route</span>
            <span class="info-value">{{ bus.route }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">Status</span>
            <span
                class="info-value"
                :class="bus.status === 'On Time' ? 'status-on-time' : 'status-delayed'"
            >
              {{ bus.status }}
            </span>
          </div>
          <div class="info-row">
            <span class="info-label">Driver</span>
            <span class="info-value">{{ bus.driver }}</span>
          </div>

          <div class="capacity-block">
            <div class="capacity-top">
              <span class="info-label">Capacity</span>
              <span class="info-value">{{ bus.capacity }}%</span>
            </div>
            <div class="capacity-bar">
              <div class="capacity-fill" :style="{ width: bus.capacity + '%' }"></div>
            </div>
          </div>
        </section>

      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, onUnmounted, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'
import 'leaflet-routing-machine'
import 'leaflet-routing-machine/dist/leaflet-routing-machine.css'
import { supabase } from '../supabaseClient'

const route = useRoute()
const router = useRouter()

const STORAGE_KEY = 'goshuttle_last_tracked'

const busData = {
  g1: {
    number: 'GS-112',
    route: 'Khayelitsha → CPUT Bellville',
    status: 'On Time',
    driver: 'Thabo M.',
    capacity: 62,
    bus_id: 1,
    route_id: 1
  },
  g2: {
    number: 'GS-205',
    route: 'Mitchells Plain → CPUT Cape Town',
    status: 'Delayed',
    driver: 'Lerato K.',
    capacity: 48,
    bus_id: 2,
    route_id: 2
  },
  g3: {
    number: 'GS-318',
    route: 'Kraaifontein → CPUT Bellville',
    status: 'On Time',
    driver: 'Sipho N.',
    capacity: 55,
    bus_id: 3,
    route_id: 3
  }
}

function resolveBusId() {
  const fromUrl = route.params.id
  if (fromUrl && busData[fromUrl]) return fromUrl

  const fromStorage = localStorage.getItem(STORAGE_KEY)
  if (fromStorage && busData[fromStorage]) return fromStorage

  return 'g1'
}

const bus = computed(() => {
  const id = resolveBusId()
  return busData[id] || busData.g1
})

watch(
    () => route.params.id,
    (id) => {
      if (id && busData[id]) {
        localStorage.setItem(STORAGE_KEY, id)
      }
    },
    { immediate: true }
)

let map = null
let busMarker = null
let routingControl = null
let refreshInterval = null
let stopMarkers = []
let hasFittedBounds = false
let lastPointCount = 0

function distanceMetres(lat1, lng1, lat2, lng2) {
  const R = 6371000
  const toRad = (d) => (d * Math.PI) / 180
  const dLat = toRad(lat2 - lat1)
  const dLng = toRad(lng2 - lng1)
  const a =
      Math.sin(dLat / 2) ** 2 +
      Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) * Math.sin(dLng / 2) ** 2
  return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
}

async function loadCurrentPosition() {
  if (!map) return

  const busId = bus.value.bus_id
  const routeId = bus.value.route_id

  const { data, error } = await supabase
      .from('live_trip')
      .select('lat,lng')
      .eq('bus_id', busId)
      .eq('route_id', routeId)
      .order('updated_at', { ascending: true })

  if (error || !data || data.length === 0) {
    console.error('Could not load live position:', error)
    return
  }

  const coordinates = data.map(p => L.latLng(p.lat, p.lng))
  const currentPos = coordinates[coordinates.length - 1]
  const pointCount = coordinates.length

  const busIcon = L.divIcon({
    className: 'bus-marker',
    html: `
    <div class="bus-pin">
      <svg viewBox="0 0 24 24" width="28" height="28">
        <path fill="#16a34a" d="M4 16c0 .88.39 1.67 1 2.22V20c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-1h8v1c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-1.78c.61-.55 1-1.34 1-2.22V6c0-3.5-3.58-4-8-4s-8 .5-8 4v10zm3.5 1c-.83 0-1.5-.67-1.5-1.5S6.67 14 7.5 14s1.5.67 1.5 1.5S8.33 17 7.5 17zm9 0c-.83 0-1.5-.67-1.5-1.5s.67-1.5 1.5-1.5 1.5.67 1.5 1.5-.67 1.5-1.5 1.5zm1.5-5H6V6h12v6z"/>
      </svg>
    </div>
  `,
    iconSize: [36, 36],
    iconAnchor: [18, 36],
    popupAnchor: [0, -36]
  })

  if (!busMarker) {
    busMarker = L.marker(currentPos, { icon: busIcon })
        .addTo(map)
        .bindPopup(`${bus.value.number} • ${bus.value.status}`)
        .openPopup()
  } else {
    const prev = busMarker.getLatLng()
    const moved = distanceMetres(prev.lat, prev.lng, currentPos.lat, currentPos.lng)
    if (moved >= 20) {
      busMarker.setLatLng(currentPos)
    }
    busMarker.setPopupContent(`${bus.value.number} • ${bus.value.status}`)
  }

  if (pointCount < 2) {
    if (!hasFittedBounds) {
      map.setView(currentPos, 15)
      hasFittedBounds = true
    }
    lastPointCount = pointCount
    return
  }

  if (pointCount === lastPointCount && routingControl) {
    return
  }

  lastPointCount = pointCount

  stopMarkers.forEach(m => map.removeLayer(m))
  stopMarkers = []

  coordinates.forEach((pos, index) => {
    if (index === coordinates.length - 1) return
    const isFirst = index === 0
    const circle = L.circleMarker(pos, {
      radius: isFirst ? 7 : 6,
      color: isFirst ? '#22c55e' : '#3295EB',
      fillColor: isFirst ? '#22c55e' : '#3295EB',
      fillOpacity: 0.9,
      weight: 5
    })
        .addTo(map)
        .bindPopup(isFirst ? 'Start' : `Stop ${index}`)
    stopMarkers.push(circle)
  })

  if (routingControl) {
    map.removeControl(routingControl)
    routingControl = null
  }
  if (window.routeLine) {
    map.removeLayer(window.routeLine)
    window.routeLine = null
  }

  const waypoints = coordinates.slice(-20)

  routingControl = L.Routing.control({
    waypoints,
    routeWhileDragging: false,
    addWaypoints: false,
    draggableWaypoints: false,
    fitSelectedRoutes: false,
    show: false,
    lineOptions: {
      styles: [{ color: '#1B3B73', weight: 5, opacity: 0.85 }]
    },
    createMarker: () => null,
    router: L.Routing.osrmv1({
      serviceUrl: 'https://router.project-osrm.org/route/v1'
    })
  }).addTo(map)

  routingControl.on('routesfound', (e) => {
    const r = e.routes[0]
    if (r?.coordinates?.length && !hasFittedBounds) {
      map.fitBounds(L.latLngBounds(r.coordinates), { padding: [40, 40] })
      hasFittedBounds = true
    }
  })

  routingControl.on('routingerror', () => {
    if (routingControl) {
      map.removeControl(routingControl)
      routingControl = null
    }
    window.routeLine = L.polyline(coordinates, {
      color: '#1B3B73',
      weight: 5,
      opacity: 0.85
    }).addTo(map)
    if (!hasFittedBounds) {
      map.fitBounds(window.routeLine.getBounds(), { padding: [40, 40] })
      hasFittedBounds = true
    }
  })
}

async function startTracking() {
  if (!map) return

  hasFittedBounds = false
  lastPointCount = 0

  if (refreshInterval) {
    clearInterval(refreshInterval)
    refreshInterval = null
  }

  if (busMarker) {
    map.removeLayer(busMarker)
    busMarker = null
  }
  stopMarkers.forEach(m => map.removeLayer(m))
  stopMarkers = []
  if (routingControl) {
    map.removeControl(routingControl)
    routingControl = null
  }
  if (window.routeLine) {
    map.removeLayer(window.routeLine)
    window.routeLine = null
  }

  const busId = bus.value.bus_id
  const routeId = bus.value.route_id

  const { count } = await supabase
      .from('live_trip')
      .select('*', { count: 'exact', head: true })
      .eq('bus_id', busId)
      .eq('route_id', routeId)

  if (count > 0) {
    await loadCurrentPosition()
  }

  refreshInterval = setInterval(async () => {
    const { count } = await supabase
        .from('live_trip')
        .select('*', { count: 'exact', head: true })
        .eq('bus_id', bus.value.bus_id)
        .eq('route_id', bus.value.route_id)

    if (count > 0) {
      await loadCurrentPosition()
    }
  }, 10000)
}

onMounted(async () => {
  if (!route.params.id) {
    const lastId = localStorage.getItem(STORAGE_KEY) || 'g1'
    router.replace({ name: 'Track', params: { id: lastId } })
  }

  map = L.map('map').setView([-33.93, 18.64], 12)

  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap'
  }).addTo(map)

  if (route.params.id) {
    await startTracking()
  }
})

watch(
    () => route.params.id,
    async () => {
      if (map) await startTracking()
    }
)

onUnmounted(() => {
  if (refreshInterval) clearInterval(refreshInterval)
  stopMarkers.forEach(m => map.removeLayer(m))
  stopMarkers = []
  if (routingControl) map.removeControl(routingControl)
  if (window.routeLine && map) {
    map.removeLayer(window.routeLine)
    window.routeLine = null
  }
  if (map) {
    map.remove()
    map = null
  }
})
</script>

<style scoped>
.page-head {
  margin-bottom: 28px;
}

.back-link {
  display: inline-block;
  font-size: 13.5px;
  font-weight: 600;
  color: white;
  margin-bottom: 14px;
}

.back-link:hover {
  color: #6B7280;
}

.page-head h1 {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 30px;
  font-weight: 800;
  color: white;
}

.live-pill {
  display: inline-flex;
  align-items: center;
  gap: 7px;
  background: rgba(9, 254, 46, 0.63);
  color: var(--green-700);
  font-size: 11.5px;
  font-weight: 800;
  letter-spacing: 0.05em;
  text-transform: uppercase;
  padding: 6px 12px;
  border-radius: var(--radius-full);
}

.live-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: var(--green-500);
  animation: pulse 1.6s ease-in-out infinite;
}

@keyframes pulse {
  0%, 100% { opacity: 1; transform: scale(1); }
  50% { opacity: 0.5; transform: scale(1.3); }
}

.track-grid {
  display: grid;
  grid-template-columns: minmax(0, 1.4fr) minmax(0, 1fr);
  gap: 20px;
  align-items: start;
}

.card-title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 20px;
}

.info-card {
  padding: 26px 28px;
  background: linear-gradient(135deg, var(--navy-900) 0%, var(--navy-700) 45%, var(--green-600) 100%);

}

.info-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #ffffff52;
}

.info-row:last-of-type {
  border-bottom: none;
}

.info-label {
  font-size: 15px;
  color: white;
}

.info-value {
  font-size: 14px;
  font-weight: 700;
  color: white;
}

.status-on-time {
  color: rgb(5, 255, 18);
}

.status-delayed {
  color: #ffb30f;
}

.capacity-block {
  margin-top: 18px;
}

.capacity-top {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

.capacity-bar {
  height: 8px;
  border-radius: 999px;
  background: #17223f87;
  overflow: hidden;
}

.capacity-fill {
  height: 100%;
  border-radius: 999px;
  background: linear-gradient(90deg, rgb(255, 255, 255), rgb(64, 255, 6));
  transition: width 0.6s var(--ease);
}

.map-card {
  padding: 20px;

}

.map-container {
  width: 100%;
  min-height: 260px;
  height: 50vh;
  max-height: 500px;
  border-radius: 14px;
  overflow: hidden;
}

@media (max-width: 820px) {
  .track-grid {
    grid-template-columns: 1fr;
  }
  .map-container {
    height: 320px;
  }
}
@media (max-width: 1024px) {

  .track-grid {
    grid-template-columns: 1fr;
  }

  .map-card,
  .info-card {
    width: 100%;
  }

  .map-container {
    height: 420px;
  }
}
@media (max-width: 768px) {

  .page-head {
    margin-bottom: 20px;
  }

  .page-head h1 {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
    font-size: 24px;
  }

  .track-grid {
    gap: 16px;
  }

  .info-card {
    padding: 20px;
  }

  .map-card {
    padding: 16px;
  }

  .map-container {
    height: 350px;
  }

  .card-title {
    font-size: 16px;
  }
}
@media (max-width: 600px) {

  .page-head h1 {
    font-size: 22px;
  }

  .back-link {
    font-size: 12px;
  }

  .section-eyebrow {
    font-size: 11px;
  }

  .info-row {
    flex-direction: column;
    align-items: flex-start;
    gap: 4px;
    padding: 12px 0;
  }

  .capacity-top {
    flex-direction: column;
    gap: 4px;
  }

  .map-container {
    height: 300px;
  }

  .live-pill {
    font-size: 10px;
    padding: 5px 10px;
  }
}
@media (max-width: 480px) {

  .page-head h1 {
    font-size: 20px;
  }

  .info-card {
    padding: 16px;
  }

  .map-card {
    padding: 14px;
  }

  .map-container {
    height: 260px;
    border-radius: 10px;
  }

  .info-label,
  .info-value {
    font-size: 13px;
  }

  .card-title {
    font-size: 15px;
    margin-bottom: 14px;
  }
}
* {
  box-sizing: border-box;
}

.map-card,
.info-card {
  width: 100%;
}

img,
svg {
  max-width: 100%;
}
/* Custom bus marker */
.bus-marker {
  background: transparent;
  border: none;
}

.bus-pin {
  width: 36px;
  height: 36px;
  background: white;
  border-radius: 50% 50% 50% 0;
  transform: rotate(-45deg);
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.3);
  border: 2px solid #16a34a;
}

.bus-pin svg {
  transform: rotate(45deg);
}
</style>