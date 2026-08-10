<template>
  <div class="page pass-page">
    <div class="container pass-grid">

      <!-- Pass card -->
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
          <span class="status-pill">
            <span class="status-dot"></span>
            Active
          </span>
        </div>

        <h1 class="pass-name">{{ student.name }}</h1>

        <button class="pass-id" type="button" :title="'Tap to copy Student ID'" @click="copyId">
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

      <!-- Stats grid -->
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

      <!-- How to use your pass -->
      <section class="steps-section">
        <h2 class="section-title">How to use your pass</h2>
        <ul class="steps-list">
          <li v-for="(step, index) in steps" :key="step.title" class="glass step-card"
            :class="{ completed: step.completed }" @click="step.completed = !step.completed">
            <span class="step-number">
              <svg v-if="step.completed" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor"
                stroke-width="3" stroke-linecap="round" stroke-linejoin="round">
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

    </div>

    <transition name="toast-fade">
      <div v-if="toastVisible" class="toast">Student ID copied</div>
    </transition>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'

const student = {
  name: 'Samukelisiwe M.',
  id: '219344639',
  validUntil: '31 Mar 2026',
  type: 'Monthly'
}

const stats = reactive([
  { label: 'Days Left', target: 34, display: 0 },
  { label: 'Trips Used', target: 47, display: 0 },
  { label: 'You Saved', target: 380, display: 0, prefix: 'R ' },
  { label: 'Preferred Route', target: null, display: 'G1' }
])

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

const toastVisible = ref(false)
let toastTimer = null

function copyId() {
  navigator.clipboard?.writeText(student.id).catch(() => { })
  toastVisible.value = true
  clearTimeout(toastTimer)
  toastTimer = setTimeout(() => {
    toastVisible.value = false
  }, 1800)
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

onMounted(() => {
  stats.forEach(animateCount)
})
</script>

<style scoped>
.pass-page {
  padding-top: 48px;
}

.pass-grid {
  display: grid;
  grid-template-columns: minmax(0, 1fr);
  gap: 22px;
  max-width: 640px;
}

/* Pass card */
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

.pass-id:hover {
  color: #fff;
}

.pass-id span {
  font-weight: 700;
  color: #fff;
}

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

/* Stats grid */
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
  background: rgba(23, 160, 79, 0.12);
  color: var(--green-600);
  border-radius: 11px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 6px;
}

.stat-label {
  font-size: 12.5px;
  color: var(--muted);
}

.stat-value {
  font-size: 21px;
  font-weight: 800;
  color: var(--ink);
}

/* Steps */
.section-title {
  font-size: 17px;
  font-weight: 800;
  color: rgb(111, 148, 250);
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
  transition: background 0.15s ease;
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
  color: var(--ink);
  transition: color 0.15s ease;
}

.step-card.completed .step-title {
  color: var(--muted);
  text-decoration: line-through;
}

.step-desc {
  font-size: 12.5px;
  color: var(--muted);
  line-height: 1.45;
  transition: opacity 0.15s ease;
}

.step-card.completed .step-desc {
  opacity: 0.65;
}

/* Toast */
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

.toast-fade-enter-from {
  opacity: 0;
  transform: translateX(-50%) translateY(8px);
}

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
