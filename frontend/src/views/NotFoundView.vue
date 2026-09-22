<template>
  <div class="page placeholder-page">
    <div class="container">
      <section class="glass placeholder-card">
        <div class="auth-logo">
          <img
              :src="logo"
              alt="GoShuttle Logo"
              class="logo-image"
          />
        </div>
        <h1>Welcome </h1>
        <p>Reliable  •  Affordable  •  Built for students</p>
        <div class="placeholder-actions">
          <router-link to="/schedule" class="btn btn-primary">View Bus Schedule</router-link>
          <router-link to="/pass" class="btn btn-ghost">View My Pass</router-link>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import logo from "../assets/images/logo.jpeg";
</script>

<style scoped src="./placeholder.css"></style>
<style scoped src="./auth.css"></style>