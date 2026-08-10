<template>
  <footer class="site-footer">
    <div class="container footer-inner">
      <div class="footer-brand">
        <span class="brand-mark">
          <img src="../assets/images/logo.jpeg" id="footer-logo" alt="GoShuttle Logo" />
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
  background: rgb(0, 0, 0);

}

.footer-inner {
  padding: 32px 24px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  text-align: center;
  color: white;
}

.footer-brand {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 800;
  color: white;
  font-size: 15px;
}

.brand-mark {
  width: 36px;
  height: 36px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, var(--navy-700), var(--green-600));
}

#footer-logo {
  width: 40px;
  height: 36px;
  border-radius: 8px;
}

.footer-note {
  font-size: 13.5px;
  color: white;
  max-width: 440px;
}

.footer-copy {
  font-size: 12.5px;
  color: white;
  opacity: 0.8;
}
</style>
