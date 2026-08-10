<template>
  <div class="page track-page">
    <div class="container">

      <section class="page-head">
        <router-link to="/" class="back-link">← Back to Home</router-link>
        <span class="section-eyebrow">Live Tracking</span>
        <h1>{{ bus.route }} <span class="live-pill"><span class="live-dot"></span>Live</span></h1>
      </section>

      <div class="track-grid">

        <!-- Route progress -->
        <section class="glass progress-card">
          <h2 class="card-title">Route progress</h2>
          <ol class="progress-track">
            <li v-for="(stop, i) in bus.stops" :key="stop.name" class="progress-stop"
              :class="{ passed: i < currentIndex, active: i === currentIndex }">
              <span class="progress-node">
                <svg v-if="i === currentIndex" width="14" height="14" viewBox="0 0 24 24" fill="none"
                  stroke="currentColor" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round">
                  <rect x="3" y="7" width="15" height="10" rx="2" />
                  <path d="M18 10h2.5a1.5 1.5 0 0 1 1.34.83L23 13v4h-2" />
                  <circle cx="7.5" cy="18.5" r="1.4" />
                  <circle cx="17.5" cy="18.5" r="1.4" />
                </svg>
              </span>
              <span class="progress-label">
                <span class="progress-name">{{ stop.name }}</span>
                <span class="progress-time">{{ stop.time }}</span>
              </span>
            </li>
          </ol>
        </section>

        <!-- Bus info -->
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
import { computed } from 'vue'

const bus = {
  number: 'GS-112',
  route: 'Khayelitsha → CPUT Bellville',
  status: 'On Time',
  driver: 'Thabo M.',
  capacity: 62,
  stops: [
    { name: 'Site B Taxi Rank', time: '07:20' },
    { name: 'Site C Taxi Rank (current)', time: '07:28' },
    { name: 'Delft Main Road', time: '07:35' },
    { name: 'CPUT Bellville Campus', time: '07:45' }
  ]
}

const currentIndex = computed(() => 1)
</script>

<style scoped>
.page-head {
  margin-bottom: 28px;
}

.back-link {
  display: inline-block;
  font-size: 13.5px;
  font-weight: 600;
  color: var(--muted);
  margin-bottom: 14px;
}

.back-link:hover {
  color: var(--navy-800);
}

.page-head h1 {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 30px;
  font-weight: 800;
}

.live-pill {
  display: inline-flex;
  align-items: center;
  gap: 7px;
  background: rgba(23, 160, 79, 0.12);
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
  font-size: 15px;
  font-weight: 800;
  margin-bottom: 22px;
}

.progress-card,
.info-card {
  padding: 26px 28px;
}

/* Progress track */
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
  left: 15px;
  top: 18px;
  bottom: 18px;
  width: 2px;
  background: var(--divider);
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
  color: var(--ink);
}

.progress-stop.active .progress-name {
  color: var(--navy-800);
}

.progress-time {
  display: block;
  font-size: 12px;
  color: var(--muted);
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
  padding: 12px 0;
  border-bottom: 1px solid var(--divider);
}

.info-row:last-of-type {
  border-bottom: none;
}

.info-label {
  font-size: 13px;
  color: var(--muted);
}

.info-value {
  font-size: 14px;
  font-weight: 700;
  color: var(--ink);
}

.status-on-time {
  color: rgb(0, 208, 38);
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
  background: rgba(23, 34, 63, 0.08);
  overflow: hidden;
}

.capacity-fill {
  height: 100%;
  border-radius: 999px;
  background: linear-gradient(90deg, var(--navy-700), var(--green-500));
  transition: width 0.6s var(--ease);
}

@media (max-width: 820px) {
  .track-grid {
    grid-template-columns: 1fr;
  }
}
</style>
