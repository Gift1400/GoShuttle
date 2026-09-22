<template>
  <div class="page driver-page">
    <div class="container">
      <section class="glass driver-card">
        <h1>Driver Live Tracking</h1>
        <p class="subtitle">This page sends your phone’s GPS location to the system.</p>

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
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { supabase } from '../supabaseClient'

const tracking = ref(false)
const lat = ref(null)
const lng = ref(null)
const statusText = ref('Not sharing location')
const statusClass = ref('status-off')

let watchId = null

function startTracking() {
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
        statusText.value = 'Sharing live location'
        statusClass.value = 'status-on'

        // Send to Supabase
        const { error } = await supabase
            .from('live_trip')
            .upsert({
              bus_id: 1,               // change later to real bus id
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

function stopTracking() {
  if (watchId !== null) {
    navigator.geolocation.clearWatch(watchId)
    watchId = null
  }
  tracking.value = false
  statusText.value = 'Stopped sharing location'
  statusClass.value = 'status-off'
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

.driver-card {
  max-width: 420px;
  width: 100%;
  padding: 32px 28px;
  text-align: center;
}

.driver-card h1 {
  font-size: 24px;
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

.status-off {
  background: #f1f1f1;
  color: #555;
}

.status-pending {
  background: #fff3cd;
  color: #856404;
}

.status-on {
  background: #d4edda;
  color: #155724;
}

.status-error {
  background: #f8d7da;
  color: #721c24;
}

.coords {
  text-align: left;
  margin-bottom: 24px;
  font-size: 14px;
}

.btn {
  width: 100%;
}
</style>