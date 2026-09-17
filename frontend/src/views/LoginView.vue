<template>
  <div class="page auth-page">
    <div class="container">
      <section class="glass auth-card desktop-layout">
        <div class="auth-hero">
          <div class="auth-logo">
            <img
                :src="logo"
                alt="GoShuttle Logo"
                class="logo-image"
            />
          </div>
          <h1>Welcome back</h1>
          <p>Sign in to track buses &amp; manage your pass</p>
        </div>

        <form class="auth-form" novalidate @submit.prevent="handleSubmit">
          <div class="field">
            <label for="studentId">Student Number / Email</label>
            <div class="input-wrap" :class="{ error: errors.studentId }">
              <span class="field-icon">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M20 21a8 8 0 0 0-16 0"/>
                  <circle cx="12" cy="8" r="4"/>
                </svg>
              </span>
              <input
                id="studentId"
                v-model.trim="form.studentId"
                type="text"
                placeholder="e.g. 219344639"
                autocomplete="username"
              />
            </div>
            <p class="error-msg" :class="{ show: errors.studentId }">Please enter your student number or email.</p>
          </div>

          <div class="field">
            <label for="password">Password</label>
            <div class="input-wrap" :class="{ error: errors.password }">
              <span class="field-icon">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                  <rect x="5" y="11" width="14" height="9" rx="2"/>
                  <path d="M8 11V7a4 4 0 0 1 8 0v4"/>
                </svg>
              </span>
              <input
                id="password"
                v-model="form.password"
                type="password"
                placeholder="Enter your password"
                autocomplete="current-password"
              />
            </div>
            <p class="error-msg" :class="{ show: errors.password }">Please enter your password.</p>
          </div>

          <div class="forgot-row">
            <button type="button" @click="showToast('Password reset isn’t available in this demo yet')">
              Forgot password?
            </button>
          </div>

          <button type="submit" class="btn btn-primary">Sign In</button>

          <div class="divider">or continue with</div>

          <div class="oauth-row">
            <button type="button" class="btn-oauth" @click="showToast('Google sign-in coming soon')">
              <svg width="16" height="16" viewBox="0 0 24 24"><path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/><path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.85C3.99 20.53 7.7 23 12 23z"/><path fill="#FBBC05" d="M5.84 14.1c-.22-.66-.35-1.36-.35-2.1s.13-1.44.35-2.1V7.05H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.95l3.66-2.85z"/><path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.05l3.66 2.85c.87-2.6 3.3-4.52 6.16-4.52z"/></svg>
              Google
            </button>
            <button type="button" class="btn-oauth" @click="showToast('CPUT SSO coming soon')">
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                <path d="M12 3 3 8v2h18V8z"/>
                <path d="M5 10v8M9 10v8M15 10v8M19 10v8"/>
                <path d="M3 21h18"/>
              </svg>
              CPUT Email
            </button>
          </div>

          <div class="switch-row">
            Don't have an account? <router-link class="link" to="/register">Register</router-link>
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
import logo from '../assets/images/logo.jpeg'
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { supabase } from '../supabaseClient'

const router = useRouter()

const form = reactive({ studentId: '', password: '' })
const errors = reactive({ studentId: false, password: false })
const toast = reactive({ visible: false, message: '' })
let toastTimer = null

function showToast(message) {
  toast.message = message
  toast.visible = true
  clearTimeout(toastTimer)
  toastTimer = setTimeout(() => (toast.visible = false), 2200)
}

async function handleSubmit() {
  errors.studentId = !form.studentId.trim()
  errors.password = !form.password

  if (errors.studentId || errors.password) return

  const loginValue = form.studentId.trim()

  const query = supabase
      .from('users')
      .select('*')
      .eq('password', form.password)

  const { data, error } = loginValue.includes('@')
      ? await query.eq('email', loginValue).single()
      : await query.eq('student_number', loginValue).single()

  if (error || !data) {
    showToast('Invalid student number/email or password')
    return
  }

  // Save logged-in user
  localStorage.setItem('user', JSON.stringify(data))

  showToast(`Welcome back, ${data.full_name.split(' ')[0]}`)

  setTimeout(() => {
    router.push({ name: 'home' })
  }, 1000)
}
</script>

<style scoped src="./auth.css"></style>
