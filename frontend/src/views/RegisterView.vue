<template>
  <div class="page auth-page">
    <div class="container">
      <section class="glass auth-card">
        <div class="auth-hero">
          <div class="auth-logo">
            <svg width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="#ffffff" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
              <rect x="4" y="5" width="16" height="12" rx="2.2"/>
              <line x1="4" y1="11" x2="20" y2="11"/>
              <line x1="8" y1="5" x2="8" y2="11"/>
              <line x1="16" y1="5" x2="16" y2="11"/>
              <circle cx="7.5" cy="18.4" r="1.3" fill="#ffffff" stroke="none"/>
              <circle cx="16.5" cy="18.4" r="1.3" fill="#ffffff" stroke="none"/>
            </svg>
          </div>
          <h1>Create account</h1>
          <p>Join GoShuttle with your CPUT details</p>
        </div>

        <form class="auth-form" novalidate @submit.prevent="handleSubmit">
          <div class="field">
            <label for="fullName">Full Name</label>
            <div class="input-wrap" :class="{ error: errors.fullName }">
              <span class="field-icon">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M20 21a8 8 0 0 0-16 0"/>
                  <circle cx="12" cy="8" r="4"/>
                </svg>
              </span>
              <input id="fullName" v-model.trim="form.fullName" type="text" placeholder="Samukelisiwe Maphumulo" autocomplete="name" />
            </div>
            <p class="error-msg" :class="{ show: errors.fullName }">Please enter your full name.</p>
          </div>

          <div class="field">
            <label for="studentNumber">Student Number</label>
            <div class="input-wrap" :class="{ error: errors.studentNumber }">
              <span class="field-icon">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                  <rect x="3" y="4" width="18" height="16" rx="2"/>
                  <circle cx="8" cy="10" r="2"/>
                  <path d="M5 17c.5-2 2-3 3-3s2.5 1 3 3"/>
                  <line x1="13" y1="9" x2="18" y2="9"/>
                  <line x1="13" y1="13" x2="18" y2="13"/>
                </svg>
              </span>
              <input id="studentNumber" v-model.trim="form.studentNumber" type="text" placeholder="219344639" inputmode="numeric" />
            </div>
            <p class="error-msg" :class="{ show: errors.studentNumber }">Please enter a valid student number.</p>
          </div>

          <div class="field">
            <label for="email">Email (CPUT)</label>
            <div class="input-wrap" :class="{ error: errors.email }">
              <span class="field-icon">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                  <rect x="3" y="5" width="18" height="14" rx="2"/>
                  <path d="m3 7 9 6 9-6"/>
                </svg>
              </span>
              <input id="email" v-model.trim="form.email" type="email" placeholder="219344639@mycput.ac.za" autocomplete="email" />
            </div>
            <p class="error-msg" :class="{ show: errors.email }">Please use your mycput.ac.za email address.</p>
          </div>

          <div class="field">
            <label for="campus">Campus</label>
            <div class="input-wrap" :class="{ error: errors.campus }">
              <span class="field-icon">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M3 21h18"/>
                  <path d="M5 21V9l7-5 7 5v12"/>
                  <path d="M9 21v-6h6v6"/>
                </svg>
              </span>
              <select id="campus" v-model="form.campus">
                <option value="">Select your campus</option>
                <option value="bellville">Bellville Campus</option>
                <option value="district-six">District Six Campus</option>
                <option value="cape-town">Cape Town Campus</option>
                <option value="mowbray">Mowbray Campus</option>
                <option value="wellington">Wellington Campus</option>
              </select>
            </div>
            <p class="error-msg" :class="{ show: errors.campus }">Please select your campus.</p>
          </div>

          <div class="field">
            <label for="regPassword">Password</label>
            <div class="input-wrap" :class="{ error: errors.password }">
              <span class="field-icon">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                  <rect x="5" y="11" width="14" height="9" rx="2"/>
                  <path d="M8 11V7a4 4 0 0 1 8 0v4"/>
                </svg>
              </span>
              <input id="regPassword" v-model="form.password" type="password" placeholder="Create a password" autocomplete="new-password" />
            </div>
            <p class="error-msg" :class="{ show: errors.password }">Password must be at least 8 characters.</p>
          </div>

          <div class="checkbox-row">
            <input id="terms" v-model="form.terms" type="checkbox" />
            <label for="terms">I agree to the Terms of Service and confirm I am a registered CPUT student.</label>
          </div>
          <p class="error-msg" :class="{ show: errors.terms }" style="margin-bottom: 18px;">You must accept the terms to continue.</p>

          <button type="submit" class="btn btn-primary">Create Account</button>

          <div class="switch-row" style="margin-top: 18px;">
            Already have an account? <router-link class="link" to="/sign-in">Sign In</router-link>
          </div>
        </form>
      </section>
    </div>

    <transition name="auth-toast-fade">
      <div v-if="toast.visible" class="auth-toast">{{ toast.message }}</div>
    </transition>
  </div>
</template>

<script setup>
import { reactive } from 'vue'

const form = reactive({
  fullName: '',
  studentNumber: '',
  email: '',
  campus: '',
  password: '',
  terms: false
})

const errors = reactive({
  fullName: false,
  studentNumber: false,
  email: false,
  campus: false,
  password: false,
  terms: false
})

const toast = reactive({ visible: false, message: '' })
let toastTimer = null

function showToast(message) {
  toast.message = message
  toast.visible = true
  clearTimeout(toastTimer)
  toastTimer = setTimeout(() => (toast.visible = false), 2400)
}

function handleSubmit() {
  errors.fullName = form.fullName.trim().length < 2
  errors.studentNumber = !/^\d{6,10}$/.test(form.studentNumber.trim())
  errors.email = !/^[\w.+-]+@mycput\.ac\.za$/i.test(form.email.trim())
  errors.campus = !form.campus
  errors.password = form.password.length < 8
  errors.terms = !form.terms

  const hasError = Object.values(errors).some(Boolean)
  if (hasError) return

  showToast(`Account created — welcome, ${form.fullName.split(' ')[0]}`)
}
</script>

<style scoped src="./auth.css"></style>
