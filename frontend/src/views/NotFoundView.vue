<template>
  <div class="page placeholder-page">
    <div class="container">
      <section class="glass placeholder-card">
        <span class="section-eyebrow">404</span>
        <h1>Page not found</h1>
        <p>That page doesn't exist yet. Head back to the Schedule or your Pass.</p>
        <div class="placeholder-actions">
          <router-link to="/schedule" class="btn btn-primary">View Bus Schedule</router-link>
          <router-link to="/pass" class="btn btn-ghost">View My Pass</router-link>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup></script>

<style scoped src="./placeholder.css"></style>
