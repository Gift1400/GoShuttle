<template>
  <div class="page pass-page">
    <div class="container pass-grid">

      <div v-if="loading" class="glass loading-card">
        <p>Loading your pass…</p>
      </div>

      <div v-else-if="loadError" class="glass loading-card error">
        <p>{{ loadError }}</p>
        <button class="btn btn-ghost" @click="loadPassData">Retry</button>
      </div>

      <template v-else>
        <section class="pass-card">
          <div class="pass-card-top">
            <div class="brand">
              <span class="brand-mark">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="#ffffff" stroke-width="1.9"
                     stroke-linecap="round" stroke-linejoin="round">
                  <rect x="4" y="5" width="16" height="12" rx="2.2" />
                  <line x1="4" y1="11" x2="20" y2="11" />
                </svg>
              </span>
              <span class="brand-name">GoShuttle</span>
            </div>
            <span class="status-pill" :class="passStatus.class">
              <span class="status-dot"></span>
              {{ passStatus.label }}
            </span>
          </div>

          <h1 class="pass-name">{{ student.name }}</h1>

          <button class="pass-id" type="button" title="Tap to copy Student ID" @click="copyId">
            Student ID · <span>{{ student.id }}</span>
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                 stroke-linecap="round" stroke-linejoin="round">
              <rect x="9" y="9" width="13" height="13" rx="2" />
              <path d="M5 15H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h9a2 2 0 0 1 2 2v1" />
            </svg>
          </button>

          <div class="pass-details">
            <div class="pass-detail-group">
              <div class="pass-field">
                <span class="pass-label">Valid Until</span>
                <span class="pass-value">{{ student.validUntil }}</span>
              </div>
              <div class="pass-field">
                <span class="pass-label">Type</span>
                <span class="pass-value">{{ student.type }}</span>
              </div>
            </div>
            <div class="pass-icon-box">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8"
                   stroke-linecap="round" stroke-linejoin="round">
                <rect x="4" y="5" width="16" height="12" rx="2.2" />
                <line x1="4" y1="11" x2="20" y2="11" />
                <line x1="8" y1="5" x2="8" y2="11" />
                <line x1="16" y1="5" x2="16" y2="11" />
                <circle cx="7.5" cy="18.4" r="1.3" fill="currentColor" stroke="none" />
                <circle cx="16.5" cy="18.4" r="1.3" fill="currentColor" stroke="none" />
              </svg>
            </div>
          </div>
        </section>

        <section class="pass-actions">
          <router-link to="/payment" class="btn btn-primary pay-btn">
            Buy or renew pass
          </router-link>
        </section>

        <section class="stats-grid">
          <div v-for="stat in stats" :key="stat.label" class="glass stat-card">
            <div class="stat-icon">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8"
                   stroke-linecap="round" stroke-linejoin="round">
                <rect x="4" y="5" width="16" height="12" rx="2.2" />
                <line x1="4" y1="11" x2="20" y2="11" />
                <line x1="8" y1="5" x2="8" y2="11" />
                <line x1="16" y1="5" x2="16" y2="11" />
              </svg>
            </div>
            <span class="stat-label">{{ stat.label }}</span>
            <span class="stat-value">{{ stat.prefix || '' }}{{ stat.display }}</span>
          </div>
        </section>

        <section class="steps-section">
          <h2 class="section-title">How to use your pass</h2>
          <ul class="steps-list">
            <li
                v-for="(step, index) in steps"
                :key="step.title"
                class="glass step-card"
                :class="{ completed: step.completed }"
                @click="step.completed = !step.completed"
            >
              <span class="step-number">
                <svg v-if="step.completed" width="14" height="14" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round">
                  <polyline points="20 6 9 17 4 12"></polyline>
                </svg>
                <span v-else>{{ index + 1 }}</span>
              </span>
              <span class="step-body">
                <span class="step-title">{{ step.title }}</span>
                <span class="step-desc">{{ step.desc }}</span>
              </span>
            </li>
          </ul>
        </section>
      </template>

    </div>

    <transition name="toast-fade">
      <div v-if="toastVisible" class="toast">Student ID copied</div>
    </transition>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { supabase } from '../supabaseClient'

const loading = ref(true)
const loadError = ref('')
const toastVisible = ref(false)
let toastTimer = null

const student = reactive({
  name: '',
  id: '',
  validUntil: '',
  type: ''
})

const stats = reactive([])
const steps = reactive([
  {
    title: 'Check the schedule',
    desc: 'See departure times for your route on the Schedule page.',
    completed: false
  },
  {
    title: 'Board at your stop',
    desc: 'Show this pass to the driver when you get on.',
    completed: false
  }
])

const passStatus = computed(() => {
  if (!student.validUntil || student.validUntil === '—') {
    return { label: 'No Pass', class: 'status-off' }
  }

  const today = new Date()
  today.setHours(0, 0, 0, 0)

  const expiry = new Date(student.validUntil)
  expiry.setHours(0, 0, 0, 0)

  if (expiry < today) {
    return { label: 'Expired', class: 'status-expired' }
  }

  return { label: 'Active', class: 'status-active' }
})

function copyId() {
  if (!student.id || student.id === '—') return
  navigator.clipboard?.writeText(student.id).catch(() => {})
  toastVisible.value = true
  clearTimeout(toastTimer)
  toastTimer = setTimeout(() => {
    toastVisible.value = false
  }, 1800)
}

function formatDate(dateStr) {
  if (!dateStr) return '—'
  const d = new Date(dateStr)
  if (isNaN(d.getTime())) return '—'
  return d.toLocaleDateString('en-ZA', {
    day: '2-digit',
    month: 'short',
    year: 'numeric'
  })
}

function daysRemaining(dateStr) {
  if (!dateStr) return 0
  const today = new Date()
  today.setHours(0, 0, 0, 0)
  const expiry = new Date(dateStr)
  if (isNaN(expiry.getTime())) return 0
  expiry.setHours(0, 0, 0, 0)
  const diff = Math.ceil((expiry - today) / (1000 * 60 * 60 * 24))
  return Math.max(0, diff)
}

async function loadPassData() {
  loading.value = true
  loadError.value = ''

  try {
    const { data: { session }, error: sessionError } = await supabase.auth.getSession()
    if (sessionError) throw sessionError

    if (!session) {
      loadError.value = 'Please sign in to view your pass.'
      loading.value = false
      return
    }

    // PK is user_id (not id)
    const { data: userProfile, error: userError } = await supabase
        .from('users')
        .select('user_id, full_name, student_number, email')
        .eq('email', session.user.email)
        .maybeSingle()

    if (userError) {
      console.warn('Users lookup failed:', userError.message)
    }

    const meta = session.user.user_metadata || {}
    student.name =
        userProfile?.full_name ||
        meta.full_name ||
        session.user.email?.split('@')[0] ||
        'Student'
    student.id = userProfile?.student_number || meta.student_number || '—'

    let passData = null

    if (userProfile?.user_id != null) {
      const { data: passByUser, error: passErr } = await supabase
          .from('passes')
          .select('*')
          .eq('user_id', userProfile.user_id)
          .order('valid_until', { ascending: false })
          .limit(1)
          .maybeSingle()

      if (passErr) console.warn('Pass lookup failed:', passErr.message)
      if (passByUser) passData = passByUser
    }

    if (passData) {
      student.validUntil = formatDate(passData.valid_until)
      student.type = passData.pass_type || '—'
    } else {
      student.validUntil = '—'
      student.type = 'No active pass'
    }

    const remainingDays = passData ? daysRemaining(passData.valid_until) : 0
    const tripsUsed = passData?.trips_used ?? 0
    const amountSaved = Number(passData?.amount_saved ?? 0)

    let preferredRoute = '—'
    if (passData?.route_id) {
      const { data: routeData } = await supabase
          .from('routes')
          .select('code')
          .eq('route_id', passData.route_id)
          .maybeSingle()
      if (routeData?.code) preferredRoute = routeData.code
    }

    stats.length = 0
    stats.push(
        { label: 'Days Left', target: remainingDays, display: 0 },
        { label: 'Trips Used', target: tripsUsed, display: 0 },
        { label: 'You Saved', target: amountSaved, display: 0, prefix: 'R ' },
        { label: 'Preferred Route', target: null, display: preferredRoute }
    )
    stats.forEach(animateCount)
  } catch (err) {
    console.error('Error loading pass data:', err)
    loadError.value = 'Could not load your pass. Please try again shortly.'
  } finally {
    loading.value = false
  }
}

function animateCount(stat) {
  if (typeof stat.target !== 'number') return
  const duration = 900
  const start = performance.now()

  function tick(now) {
    const progress = Math.min((now - start) / duration, 1)
    const eased = 1 - Math.pow(1 - progress, 3)
    stat.display = Math.round(stat.target * eased)
    if (progress < 1) requestAnimationFrame(tick)
  }

  requestAnimationFrame(tick)
}

onMounted(loadPassData)
</script>

<style scoped>
.pass-page {
  padding-top: 48px;
}

.pass-grid {
  display: grid;
  grid-template-columns: minmax(0, 1fr);
  gap: 22px;
  max-width: 800px;
}

.loading-card {
  padding: 40px;
  text-align: center;
  color: white;
  font-size: 15px;
}

.loading-card.error {
  color: #ffb3b3;
}

.loading-card.error .btn {
  margin-top: 16px;
  color: white;
  border-color: rgba(255, 255, 255, 0.3);
}

.pass-card {
  background: linear-gradient(135deg, var(--navy-900) 0%, var(--navy-700) 45%, var(--green-600) 100%);
  border-radius: var(--radius-lg);
  padding: 30px 28px;
  color: #fff;
  box-shadow: 0 20px 40px -16px rgba(14, 34, 70, 0.5);
  position: relative;
  overflow: hidden;
}

.pass-card::after {
  content: '';
  position: absolute;
  inset: 0;
  background: radial-gradient(circle at 85% -10%, rgba(255, 255, 255, 0.16), transparent 55%);
  pointer-events: none;
}

.pass-card-top {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 30px;
}

.brand {
  display: flex;
  align-items: center;
  gap: 10px;
}

.brand-mark {
  width: 32px;
  height: 32px;
  border-radius: 9px;
  background: rgba(255, 255, 255, 0.18);
  display: flex;
  align-items: center;
  justify-content: center;
}

.brand-name {
  font-size: 15px;
  font-weight: 700;
}

.status-pill {
  display: flex;
  align-items: center;
  gap: 6px;
  background: rgba(255, 255, 255, 0.18);
  border-radius: var(--radius-full);
  padding: 6px 14px 6px 10px;
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.03em;
  text-transform: uppercase;
}

.status-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: #4ade80;
}

.status-pill.status-active .status-dot { background: #4ade80; }
.status-pill.status-expired .status-dot { background: #f87171; }
.status-pill.status-off .status-dot { background: #9ca3af; }

.pass-name {
  font-size: 25px;
  font-weight: 800;
  margin: 0 0 8px;
  color: white;
}

.pass-id {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  background: none;
  border: none;
  border-bottom: 1px dashed rgba(255, 255, 255, 0.4);
  color: rgba(255, 255, 255, 0.8);
  font-family: inherit;
  font-size: 13.5px;
  padding: 0 0 3px;
  margin-bottom: 26px;
  cursor: pointer;
  transition: color 0.15s ease;
}

.pass-id:hover { color: #fff; }
.pass-id span { font-weight: 700; color: #fff; }

.pass-details {
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: relative;
}

.pass-detail-group {
  display: flex;
  gap: 30px;
}

.pass-field {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.pass-label {
  font-size: 11.5px;
  color: rgba(255, 255, 255, 0.65);
}

.pass-value {
  font-size: 15px;
  font-weight: 700;
}

.pass-icon-box {
  width: 54px;
  height: 54px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--navy-800);
  flex-shrink: 0;
}

.pass-actions {
  display: flex;
}

.pay-btn {
  width: 100%;
  text-align: center;
  text-decoration: none;
  padding: 14px 20px;
  font-size: 15px;
  font-weight: 700;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 14px;
}

.stat-card {
  padding: 18px;
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.stat-icon {
  width: 36px;
  height: 36px;
  background: rgba(23, 160, 80, 0.345);
  color: rgb(15, 255, 151);
  border-radius: 11px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 6px;
}

.stat-label {
  font-size: 12.5px;
  color: white;
}

.stat-value {
  font-size: 21px;
  font-weight: 800;
  color: white;
}

.section-title {
  font-size: 17px;
  font-weight: 800;
  color: rgb(21, 45, 110);
  margin-bottom: 14px;
}

.steps-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.step-card {
  display: flex;
  gap: 14px;
  align-items: flex-start;
  padding: 16px;
  cursor: pointer;
  transition: background 0.15s ease, transform 0.1s ease;
}

.step-card:hover {
  transform: translateY(-1px);
}

.step-number {
  width: 28px;
  height: 28px;
  border-radius: 50%;
  background: var(--navy-700);
  color: #fff;
  font-size: 13px;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.step-card.completed .step-number {
  background: var(--green-600);
}

.step-body {
  display: flex;
  flex-direction: column;
  gap: 3px;
}

.step-title {
  font-size: 14.5px;
  font-weight: 700;
  color: white;
}

.step-card.completed .step-title {
  color: var(--muted);
  text-decoration: line-through;
}

.step-desc {
  font-size: 12.5px;
  color: rgb(213, 212, 212);
  line-height: 1.45;
}

.step-card.completed .step-desc {
  opacity: 0.65;
}

.toast {
  position: fixed;
  left: 50%;
  bottom: 32px;
  transform: translateX(-50%);
  background: var(--navy-800);
  color: #fff;
  font-size: 13.5px;
  font-weight: 600;
  padding: 12px 20px;
  border-radius: var(--radius-full);
  box-shadow: 0 14px 26px rgba(14, 34, 70, 0.3);
  z-index: 60;
}

.toast-fade-enter-active,
.toast-fade-leave-active {
  transition: opacity 0.2s ease, transform 0.2s ease;
}

.toast-fade-enter-from,
.toast-fade-leave-to {
  opacity: 0;
  transform: translateX(-50%) translateY(8px);
}

@media (min-width: 720px) {
  .pass-grid {
    max-width: 720px;
  }
}
</style>