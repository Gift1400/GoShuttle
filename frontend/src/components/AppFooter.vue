<template>
  <footer class="site-footer">
    <div class="container footer-inner">

      <div class="footer-brand">
        <span class="brand-mark">
          <img src="../assets/images/logo.jpeg" id="footer-logo" alt="GoShuttle Logo" />
        </span>
        <span>GoShuttle</span>
      </div>

      <p class="footer-note">
        CPUT Student Bus Service — a partnership pilot with Golden Arrow Bus Services.
      </p>

      <div class="footer-contact">
        <a href="mailto:support@goshuttle.co.za" class="contact-item">
          <svg class="contact-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"/>
            <polyline points="22,6 12,13 2,6"/>
          </svg>
          support@goshuttle.co.za
        </a>

        <a href="tel:+27219551234" class="contact-item">
          <svg class="contact-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"/>
          </svg>
          +27 21 955 1234
        </a>

        <span class="contact-item">
          <svg class="contact-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"/>
            <circle cx="12" cy="10" r="3"/>
          </svg>
          CPUT Bellville Campus
        </span>
      </div>

      <p class="footer-copy">
        &copy; {{ year }} GoShuttle. Built for CPUT students.
      </p>
    </div>
  </footer>
</template>

<script setup>
const year = new Date().getFullYear()
</script>

<style scoped>
.site-footer {
  border-top: 1px solid rgba(255, 255, 255, 0.12);
  background: #072A66;
}

.footer-inner {
  padding: 36px 24px 28px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 14px;
  text-align: center;
  color: white;
}

.footer-brand {
  display: flex;
  align-items: center;
  gap: 10px;
  font-weight: 800;
  font-size: 16px;
  color: white;
}

.brand-mark {
  width: 40px;
  height: 40px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, var(--navy-700), var(--green-600));
  overflow: hidden;
}

#footer-logo {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 10px;
}

.footer-note {
  font-size: 13.5px;
  color: rgba(255, 255, 255, 0.8);
  max-width: 440px;
  line-height: 1.5;
  margin: 0;
}

.footer-contact {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 18px 28px;
  margin: 6px 0 4px;
}

.contact-item {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  font-size: 13.5px;
  font-weight: 500;
  color: #e2e8f0;
  text-decoration: none;
  transition: color 0.2s ease;
}

.contact-item:hover {
  color: #4ade80;
}

.contact-icon {
  width: 16px;
  height: 16px;
  flex-shrink: 0;
  stroke: currentColor;
}

.footer-copy {
  font-size: 12.5px;
  color: rgba(255, 255, 255, 0.55);
  margin: 0;
}

@media (max-width: 480px) {
  .footer-contact {
    flex-direction: column;
    gap: 12px;
  }
}
</style>