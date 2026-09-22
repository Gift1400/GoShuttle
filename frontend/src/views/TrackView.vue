<template>
  <div class="page track-page">
    <div class="container">

      <section class="page-head">
        <router-link to="/" class="back-link">← Back to Home</router-link>
        <span class="section-eyebrow">Live Tracking</span>
        <h1>{{ bus.route }} <span class="live-pill"><span class="live-dot"></span>Live</span></h1>
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
            <span class="info-value status-on-time">{{ bus.status }}</span>
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
import { onMounted, onUnmounted } from 'vue'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'

const bus = {
  number: 'GS-112',
  route: 'Khayelitsha → CPUT Bellville',
  status: 'On Time',
  driver: 'Thabo M.',
  capacity: 62
}

let map = null
let busMarker = null

onMounted(() => {
  // Center roughly between Khayelitsha and Bellville
  map = L.map('map').setView([-33.95, 18.65], 12)

  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap'
  }).addTo(map)

  // Bus marker (you can update its position later with live data)
  busMarker = L.marker([-33.98, 18.68])
      .addTo(map)
      .bindPopup('GS-112 • On Time')
      .openPopup()
})

onUnmounted(() => {
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

  0%,
  100% {
    opacity: 1;
    transform: scale(1);
  }

  50% {
    opacity: 0.5;
    transform: scale(1.3);
  }
}

.track-grid {
  display: grid;
  grid-template-columns: minmax(0, 1.3fr) minmax(0, 1fr);
  gap: 20px;
  align-items: start;
}

.card-title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 20px;
}

.progress-card,
.info-card {
  padding: 26px 28px;
}

.progress-track {
  position: relative;
  display: flex;
  flex-direction: column;
  gap: 26px;
  margin: 0;
  padding-left: 4px;
}

.progress-track::before {
  content: '';
  position: absolute;
  left: 20px;
  top: 18px;
  bottom: 18px;
  width: 1.5px;
  background: white;
}

.progress-stop {
  display: flex;
  align-items: center;
  gap: 16px;
  position: relative;
}

.progress-node {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: #fff;
  border: 2px solid var(--divider);
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  z-index: 1;
  color: transparent;
  transition: all 0.2s ease;
}

.progress-stop.passed .progress-node {
  background: var(--green-500);
  border-color: var(--green-500);
}

.progress-stop.active .progress-node {
  background: var(--navy-700);
  border-color: var(--navy-700);
  color: #fff;
  box-shadow: 0 0 0 6px rgba(27, 59, 115, 0.14);
}

.progress-name {
  font-weight: 700;
  font-size: 14.5px;
  color: white;
}

.progress-stop.active .progress-name {
  color: rgb(0, 255, 8);
}

.progress-time {
  display: block;
  font-size: 12px;
  color: #0e2246;
  margin-top: 2px;
}

.progress-label {
  display: flex;
  flex-direction: column;
}

/* Info card */
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

@media (max-width: 820px) {
  .track-grid {
    grid-template-columns: 1fr;
  }
}
.map-card {
  padding: 20px;
}

.map-container {
  width: 100%;
  height: 420px;
  border-radius: 14px;
  overflow: hidden;
}

.track-grid {
  display: grid;
  grid-template-columns: minmax(0, 1.4fr) minmax(0, 1fr);
  gap: 20px;
  align-items: start;
}

@media (max-width: 820px) {
  .track-grid {
    grid-template-columns: 1fr;
  }
  .map-container {
    height: 320px;
  }
}
</style>
