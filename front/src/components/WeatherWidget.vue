<template>
  <section class="weather-section">
    <div class="weather-widget">
      <div class="weather-header">
        <h3><i class="fas fa-cloud"></i> 오늘의 날씨</h3>
        <button class="btn btn-ghost"><i class="fas fa-sync-alt"></i></button>
      </div>
      <div class="weather-content">
        <div class="weather-main">
          <div class="weather-info">
            <div class="weather-icon">☀️</div>
            <div class="weather-temp">
              <span class="temperature">23°C</span>
              <div class="weather-desc">맑음</div>
              <div class="weather-location">서울</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
// 실제 날씨 API 연동은 추후
</script>

<style scoped>
/* styles here */
</style>