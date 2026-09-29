<template>
  <div class="page driver-page">
    <div class="container">
      <div class="driver-grid">

        <section class="glass driver-card">
          <h1>Driver Live Tracking</h1>
          <p class="subtitle">This page sends your phone's GPS location to the system.</p>

          <!-- Route selector -->
          <div class="field">
            <label for="routeSelect">Select route</label>
            <select id="routeSelect" v-model="selectedRoute">
              <option value="g1">G1 – Khayelitsha → CPUT Bellville</option>
              <option value="g2">G2 – Mitchells Plain → CPUT Cape Town</option>
              <option value="g3">G3 – Kraaifontein → CPUT Bellville</option>
            </select>
          </div>

          <div class="status-box" :class="statusClass">
            {{ statusText }}
          </div>

          <div class="coords" v-if="lat && lng">
            <p><strong>Latitude:</strong> {{ lat.toFixed(6) }}</p>
            <p><strong>Longitude:</strong> {{ lng.toFixed(6) }}</p>
          </div>

          <button class="btn btn-primary" @click="startTracking" :disabled="tracking">
            {{ tracking ? 'Tracking…' : 'Start Sharing Location' }}
          </button>

          <button class="btn btn-ghost" @click="stopTracking" v-if="tracking" style="margin-top: 12px;">
            Stop Sharing
          </button>

          <button class="btn btn-ghost" @click="clearTrip" style="margin-top: 12px;">
            Start New Trip
          </button>
        </section>

        <section class="glass manual-card">
          <h1>Manual Location Update</h1>
          <p class="subtitle">Set the bus's current coordinates directly.</p>

          <!-- Same route selector for manual -->
          <div class="field">
            <label for="manualRouteSelect">Select route</label>
            <select id="manualRouteSelect" v-model="selectedRoute">
              <option value="g1">G1 – Khayelitsha → CPUT Bellville</option>
              <option value="g2">G2 – Mitchells Plain → CPUT Cape Town</option>
              <option value="g3">G3 – Kraaifontein → CPUT Bellville</option>
            </select>
          </div>

          <div class="status-box" :class="manualStatusClass">
            {{ manualStatusText }}
          </div>

          <div class="field">
            <label for="manualLat">Latitude</label>
            <input id="manualLat" v-model="manualLat" type="number" step="0.000001" placeholder="-33.95" />
          </div>

          <div class="field">
            <label for="manualLng">Longitude</label>
            <input id="manualLng" v-model="manualLng" type="number" step="0.000001" placeholder="18.65" />
          </div>

          <button class="btn btn-primary" @click="updateManualLocation" :disabled="manualSaving">
            {{ manualSaving ? 'Saving…' : 'Update Location' }}
          </button>
        </section>

      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { supabase } from '../supabaseClient'

const STORAGE_KEY = 'goshuttle_driver_route'

const tracking = ref(false)
const lat = ref(null)
const lng = ref(null)
const statusText = ref('Not sharing location')
const statusClass = ref('status-off')

const selectedRoute = ref(localStorage.getItem(STORAGE_KEY) || 'g1')

const routeMap = {
  g1: { bus_id: 1, route_id: 1, label: 'Khayelitsha → CPUT Bellville' },
  g2: { bus_id: 2, route_id: 2, label: 'Mitchells Plain → CPUT Cape Town' },
  g3: { bus_id: 3, route_id: 3, label: 'Kraaifontein → CPUT Bellville' }
}

const current = computed(() => routeMap[selectedRoute.value])

watch(selectedRoute, (value) => {
  localStorage.setItem(STORAGE_KEY, value)
})

let watchId = null

async function startTracking() {
  if (!navigator.geolocation) {
    statusText.value = 'Geolocation is not supported on this device'
    statusClass.value = 'status-error'
    return
  }

  statusText.value = 'Requesting permission…'
  statusClass.value = 'status-pending'

  watchId = navigator.geolocation.watchPosition(
      async (position) => {
        lat.value = position.coords.latitude
        lng.value = position.coords.longitude
        tracking.value = true
        statusText.value = `Sharing live location (${current.value.label})`
        statusClass.value = 'status-on'

        const { error } = await supabase
            .from('live_trip')
            .insert({
              bus_id: current.value.bus_id,
              route_id: current.value.route_id,
              lat: lat.value,
              lng: lng.value,
              updated_at: new Date().toISOString()
            })

        if (error) {
          console.error(error)
          statusText.value = 'Error saving location'
          statusClass.value = 'status-error'
        }
      },
      (error) => {
        tracking.value = false
        statusText.value = 'Error: ' + error.message
        statusClass.value = 'status-error'
      },
      {
        enableHighAccuracy: true,
        maximumAge: 3000,
        timeout: 15000
      }
  )
}

async function clearTrip() {
  const { data, error } = await supabase
      .from('live_trip')
      .delete()
      .eq('bus_id', current.value.bus_id)
      .eq('route_id', current.value.route_id)
      .select()

  if (error) {
    console.error(error)
    manualStatusText.value = 'Error clearing trip: ' + error.message
    manualStatusClass.value = 'status-error'
    return
  }

  const count = data?.length ?? 0
  manualStatusText.value =
      count > 0
          ? `Cleared ${count} point(s) for ${current.value.label}`
          : `No points found for ${current.value.label}`
  manualStatusClass.value = count > 0 ? 'status-on' : 'status-pending'
}

function stopTracking() {
  if (watchId !== null) {
    navigator.geolocation.clearWatch(watchId)
    watchId = null
  }
  tracking.value = false
  statusText.value = 'Stopped sharing location'
  statusClass.value = 'status-off'
}

const manualLat = ref(null)
const manualLng = ref(null)
const manualSaving = ref(false)
const manualStatusText = ref('No location set yet')
const manualStatusClass = ref('status-off')

async function updateManualLocation() {
  if (!manualLat.value || !manualLng.value) {
    manualStatusText.value = 'Please enter both latitude and longitude'
    manualStatusClass.value = 'status-error'
    return
  }

  manualSaving.value = true
  manualStatusText.value = 'Saving…'
  manualStatusClass.value = 'status-pending'

  const { error } = await supabase
      .from('live_trip')
      .insert({
        bus_id: current.value.bus_id,
        route_id: current.value.route_id,
        lat: Number(manualLat.value),
        lng: Number(manualLng.value),
        updated_at: new Date().toISOString()
      })

  manualSaving.value = false

  if (error) {
    console.error(error)
    manualStatusText.value = 'Error saving location: ' + error.message
    manualStatusClass.value = 'status-error'
    return
  }

  manualStatusText.value = `Location updated for ${current.value.label}`
  manualStatusClass.value = 'status-on'
}
</script>

<style scoped>
.driver-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px 20px;
}

.driver-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
  max-width: 900px;
  width: 100%;
}

.driver-card,
.manual-card {
  padding: 32px 28px;
  text-align: center;
}

.driver-card h1,
.manual-card h1 {
  font-size: 22px;
  font-weight: 800;
  margin-bottom: 8px;
}

.subtitle {
  font-size: 14px;
  color: #666;
  margin-bottom: 24px;
}

.status-box {
  padding: 12px 16px;
  border-radius: 12px;
  font-weight: 600;
  margin-bottom: 20px;
}

.status-off { background: #f1f1f1; color: #555; }
.status-pending { background: #fff3cd; color: #856404; }
.status-on { background: #d4edda; color: #155724; }
.status-error { background: #f8d7da; color: #721c24; }

.coords {
  text-align: left;
  margin-bottom: 24px;
  font-size: 14px;
}

.field {
  text-align: left;
  margin-bottom: 16px;
}

.field label {
  display: block;
  font-size: 13px;
  font-weight: 700;
  margin-bottom: 6px;
}

.field input,
.field select {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid rgba(23, 34, 63, 0.12);
  border-radius: 10px;
  font-size: 14px;
  background: #fff;
}

.btn { width: 100%; }

@media (max-width: 820px) {
  .driver-grid {
    grid-template-columns: 1fr;
  }
}
</style>