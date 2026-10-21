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
let trail = null
let startMarker = null
let refreshInterval = null
let hasFittedBounds = false
let trackingToken = 0
let lastRouteKey = ''
let routedCoords = null
let latestKey = ''
let userMovedAt = 0

const MAX_ACCURACY_M = 50
const MAX_SPEED_MPS = 35 // ~125 km/h; faster = GPS glitch

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

function cleanPoints(rows) {
  const out = []
  for (const p of rows) {
    // manual updates have no accuracy value
    const manual = p.accuracy == null
    if (!manual && p.accuracy > MAX_ACCURACY_M) continue
    const last = out[out.length - 1]
    if (last) {
      const d = distanceMetres(last.lat, last.lng, p.lat, p.lng)
      if (d < 5) continue // standing-still jitter / duplicate
      // speed check only between two GPS points; typed-in points are
      // entered seconds apart but can be far apart on the map
      if (!manual && last.accuracy != null) {
        const dt = (new Date(p.updated_at) - new Date(last.updated_at)) / 1000
        if (dt > 0 && d / dt > MAX_SPEED_MPS) continue // teleport
      }
    }
    out.push(p)
  }
  return out
}

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

function clearLayers() {
  if (!map) return
  if (busMarker) { map.removeLayer(busMarker); busMarker = null }
  if (trail) { map.removeLayer(trail); trail = null }
  if (startMarker) { map.removeLayer(startMarker); startMarker = null }
  hasFittedBounds = false
  lastRouteKey = ''
  routedCoords = null
}

function routeKey(coordinates) {
  return coordinates
      .slice(-25)
      .map(p => `${p.lat.toFixed(6)},${p.lng.toFixed(6)}`)
      .join('|')
}

// Ask OSRM for the driving route through the points so the line follows streets.
// Only called when the points change, so the 10 s refresh doesn't spam the server.
async function snapToRoads(coordinates, key, token) {
  if (key === lastRouteKey) return

  const path = coordinates
      .slice(-25)
      .map(p => `${p.lng},${p.lat}`)
      .join(';')

  try {
    const res = await fetch(
        `https://router.project-osrm.org/route/v1/driving/${path}?overview=full&geometries=geojson`
    )
    const json = await res.json()

    // a newer startTracking() ran, or the page was left, while we were waiting
    if (token !== trackingToken || !map || !trail) return
    if (key !== latestKey) return // newer points arrived while OSRM was answering
    if (json.code !== 'Ok' || !json.routes?.length) return

    routedCoords = json.routes[0].geometry.coordinates.map(([lng, lat]) => [lat, lng])
    lastRouteKey = key
    trail.setLatLngs(routedCoords)

    // keep the bus on the road, at the end of the snapped line
    if (busMarker) busMarker.setLatLng(routedCoords[routedCoords.length - 1])
    followBus()
  } catch (err) {
    console.warn('Road snapping failed, showing straight line:', err)
  }
}

// Keep the bus in view as it moves. Pauses for 30 s if the user drags the map.
function followBus() {
  if (!map || !busMarker) return
  if (Date.now() - userMovedAt < 30000) return
  const pos = busMarker.getLatLng()
  if (!map.getBounds().pad(-0.25).contains(pos)) {
    map.panTo(pos, { animate: true })
  }
}

async function loadCurrentPosition(token) {
  if (!map) return

  const { data, error } = await supabase
      .from('live_trip')
      .select('lat,lng,accuracy,session_id,updated_at')
      .eq('bus_id', bus.value.bus_id)
      .eq('route_id', bus.value.route_id)
      .order('updated_at', { ascending: false })
      .limit(300)

  // a newer startTracking() ran, or the page was left, while we were waiting
  if (token !== trackingToken || !map) return

  if (error) {
    console.error('Could not load live position:', error)
    return
  }
  if (!data?.length) return

  const latest = data[0].session_id
  const rows = data.filter(r => r.session_id === latest).reverse() // oldest → newest
  const points = cleanPoints(rows)
  if (!points.length) return

  const coordinates = points.map(p => L.latLng(p.lat, p.lng))
  const currentPos = coordinates[coordinates.length - 1]
  const popupText = `${bus.value.number} • ${bus.value.status}`

  if (!busMarker) {
    busMarker = L.marker(currentPos, { icon: busIcon })
        .addTo(map)
        .bindPopup(popupText)
        .openPopup()
  } else {
    busMarker.setLatLng(currentPos)
    busMarker.setPopupContent(popupText)
  }

  if (!trail) {
    trail = L.polyline([], { color: '#1B3B73', weight: 5, opacity: 0.85 }).addTo(map)
  }
  const key = routeKey(coordinates)
  latestKey = key
  const haveRoute = key === lastRouteKey && routedCoords
  trail.setLatLngs(haveRoute ? routedCoords : coordinates) // straight line until snapped
  if (haveRoute) busMarker.setLatLng(routedCoords[routedCoords.length - 1])
  if (coordinates.length > 1) snapToRoads(coordinates, key, token)

  if (!startMarker && coordinates.length > 1) {
    startMarker = L.circleMarker(coordinates[0], {
      radius: 7, color: '#22c55e', fillColor: '#22c55e', fillOpacity: 0.9, weight: 5
    }).addTo(map).bindPopup('Start')
  } else if (startMarker) {
    startMarker.setLatLng(coordinates[0])
  }

  if (!hasFittedBounds) {
    if (coordinates.length > 1) {
      map.fitBounds(trail.getBounds(), { padding: [40, 40] })
    } else {
      map.setView(currentPos, 15)
    }
    hasFittedBounds = true
  } else {
    followBus()
  }
}

async function startTracking() {
  if (!map) return

  const myToken = ++trackingToken
  clearInterval(refreshInterval)
  refreshInterval = null
  clearLayers()

  await loadCurrentPosition(myToken)
  if (myToken !== trackingToken) return

  refreshInterval = setInterval(() => loadCurrentPosition(myToken), 5000)
}

onMounted(async () => {
  if (!route.params.id) {
    const lastId = localStorage.getItem(STORAGE_KEY) || 'g1'
    router.replace({ name: 'Track', params: { id: lastId } })
  }

  map = L.map('map').setView([-33.93, 18.64], 12)
  map.on('dragstart', () => { userMovedAt = Date.now() })

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
  trackingToken++
  clearInterval(refreshInterval)
  refreshInterval = null
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