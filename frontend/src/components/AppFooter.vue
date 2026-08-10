<template>
  <footer class="site-footer">
    <div class="container footer-inner">
      <div class="footer-brand">
        <span class="brand-mark">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#ffffff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <rect x="4" y="5" width="16" height="12" rx="2.2"/>
            <line x1="4" y1="11" x2="20" y2="11"/>
          </svg>
        </span>
        <span>GoShuttle</span>
      </div>
      <p class="footer-note">CPUT Student Bus Service — a partnership pilot with Golden Arrow Bus Services.</p>
      <p class="footer-copy">&copy; {{ year }} GoShuttle. Built for CPUT students.</p>
    </div>
  </footer>
</template>

<script setup>
const year = new Date().getFullYear()
</script>

<style scoped>
.site-footer {
  border-top: 1px solid var(--divider);
  background: rgba(255, 255, 255, 0.4);
  backdrop-filter: blur(12px);
}

.footer-inner {
  padding: 32px 24px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  text-align: center;
}

.footer-brand {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 800;
  color: var(--navy-800);
  font-size: 15px;
}

.brand-mark {
  width: 26px;
  height: 26px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, var(--navy-700), var(--green-600));
}

.footer-note {
  font-size: 13.5px;
  color: var(--muted);
  max-width: 440px;
}

.footer-copy {
  font-size: 12.5px;
  color: var(--muted);
  opacity: 0.8;
}
</style>
