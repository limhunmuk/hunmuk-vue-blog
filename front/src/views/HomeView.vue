<script setup lang="ts">
import WeatherWidget from "@/components/WeatherWidget.vue" ;
import PostsGrid from "@/components/PostsGrid.vue";
</script>

<template>
  <div class="container">
    <section class="welcome-section">
      <h1>내 블로그에 오신 것을 환영합니다</h1>
      <p>생각과 이야기를 나누는 공간</p>
    </section>

    <WeatherWidget />
    <PostsGrid />
  </div>
</template>

