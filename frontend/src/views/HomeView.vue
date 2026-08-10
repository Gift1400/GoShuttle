<template>
  <div class="page home-page">
    <div class="container">

      <!-- Hero -->
      <section class="hero">
        <span id="section-eyebrow">GoShuttle</span>
        <h1>Good morning, Samu</h1>
        <p class="hero-sub">Track your bus, check schedules and manage your pass — all in one place.</p>
        <div class="hero-actions">
          <router-link to="/track" class="btn btn-primary">Track my bus</router-link>
          <router-link to="/schedule" class="btn btn-ghost">View schedule</router-link>
        </div>
      </section>

      <!-- Live trip card -->
      <section class="glass live-card">
        <div class="live-top">
          <span class="live-pill">
            <span class="live-dot"></span>
            LIVE
          </span>
          <router-link to="/track" class="live-link">Open live map →</router-link>
        </div>

        <h2>Khayelitsha → CPUT Bellville</h2>
        <p class="next-stop">Next stop: Site C Taxi Rank</p>

        <div class="live-stats">
          <div class="live-stat">
            <span class="live-stat-label">Arrives in</span>
            <span class="live-stat-value">6 min</span>
          </div>
          <div class="live-stat">
            <span class="live-stat-label">To campus</span>
            <span class="live-stat-value">28 min</span>
          </div>
          <div class="live-stat">
            <span class="live-stat-label">Status</span>
            <span class="live-stat-value status-on-time">On Time</span>
          </div>
        </div>
      </section>

      <!-- Upcoming buses -->
      <section class="upcoming">
        <h2 class="section-title">Upcoming buses</h2>
        <div class="bus-grid">
          <router-link v-for="bus in upcomingBuses" :key="bus.code" to="/schedule" class="glass bus-card">
            <span class="badge" :class="bus.badgeClass">{{ bus.code }}</span>
            <div class="bus-card-body">
              <h3>{{ bus.title }}</h3>
              <p>{{ bus.stops }}</p>
              <span class="bus-status" :class="bus.status">
                <span class="status-dot"></span>
                {{ bus.eta }} · {{ bus.status === 'on-time' ? 'On Time' : 'Delayed' }}
              </span>
            </div>
          </router-link>
        </div>
      </section>

    </div>
  </div>
</template>

<script setup>
const upcomingBuses = [
  {
    code: 'G1',
    badgeClass: 'badge-g1',
    title: 'Khayelitsha → Bellville',
    stops: 'Site B → Site C → Delft → CPUT',
    eta: '6 min',
    status: 'on-time'
  },
  {
    code: 'G2',
    badgeClass: 'badge-g2',
    title: 'Mitchells Plain → Cape Town',
    stops: 'Town Centre → Strandfontein → CPUT CT',
    eta: '14 min',
    status: 'delayed'
  },
  {
    code: 'G3',
    badgeClass: 'badge-g3',
    title: 'Kraaifontein → Bellville',
    stops: 'Station → Bellair → CPUT Bellville',
    eta: '22 min',
    status: 'on-time'
  }
]
</script>

<style scoped>
.hero {
  max-width: 620px;
  margin-bottom: 32px;
}

#section-eyebrow {
  font-size: 20px;
  font-weight: 700;
  color: var(--green-600);
  letter-spacing: 0.05em;
  text-transform: uppercase;
  margin-bottom: 6px;
}

.hero h1 {
  font-size: 36px;
  font-weight: 800;
  margin: 4px 0 10px;
}

.hero-sub {
  font-size: 15.5px;
  line-height: 1.6;
  margin-bottom: 22px;
}

.hero-actions {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
}

/* Live card */
.live-card {
  padding: 26px 28px;
  margin-bottom: 40px;
}

.live-top {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 14px;
}

.live-pill {
  display: inline-flex;
  align-items: center;
  gap: 7px;
  background: rgba(19, 217, 48, 0.391);
  color: rgb(0, 255, 0);
  font-size: 11.5px;
  font-weight: 600;
  letter-spacing: 0.05em;
  padding: 6px 12px;
  border-radius: var(--radius-full);
}

.live-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: rgb(0, 255, 0);
  animation: pulse 1.6s ease-in-out infinite;
}

@keyframes pulse {

  0%,
  100% {
    opacity: 1;
    transform: scale(1);
  }

  50% {
    opacity: 0.5;
    transform: scale(1.3);
  }
}

.live-link {
  font-size: 16px;
  font-weight: 500;
  color: white;
}

.live-link:hover {
  text-decoration: underline;
}

.live-card h2 {
  font-size: 20px;
  font-weight: 800;
  margin-bottom: 6px;
}

.next-stop {
  font-size: 14px;
  color: white;
  margin-bottom: 22px;
}

.live-stats {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 14px;
  border-top: 1px solid white;
  padding-top: 18px;
}

.live-stat {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.live-stat-label {
  font-size: 12px;
  color: white;
}

.live-stat-value {
  font-size: 16px;
  font-weight: 700;
  color: rgb(9, 72, 145);
}

.status-on-time {
  color: rgb(6, 208, 6);
}

/* Upcoming */
.section-title {
  font-size: 20px;
  font-weight: 800;
  margin-bottom: 16px;
  color: white;
}

.bus-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  gap: 16px;
}

.bus-card {
  display: flex;
  gap: 14px;
  align-items: flex-start;
  padding: 20px;
  transition: transform 0.15s var(--ease), box-shadow 0.15s var(--ease);
}

.bus-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 16px 30px rgba(14, 34, 70, 0.14);
}

.badge {
  width: 38px;
  height: 38px;
  font-size: 13px;
  flex-shrink: 0;
}

.bus-card-body h3 {
  font-size: 15px;
  font-weight: 700;
  margin-bottom: 4px;
}

.bus-card-body p {
  font-size: 12.5px;
  color: white;
  margin-bottom: 10px;
}

.bus-status {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  font-size: 12px;
  font-weight: 700;
}

.bus-status .status-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: currentColor;
}

.bus-status.on-time {
  color: rgb(13, 208, 13);
}

.bus-status.delayed {
  color: #ec9411;
}

@media (max-width: 560px) {
  .live-stats {
    grid-template-columns: 1fr;
    gap: 10px;
  }
}
</style>
