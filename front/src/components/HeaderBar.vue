
<script setup>
import UserInfo from './UserInfo.vue';
import MobileNav from './MobileNav.vue';

const props = defineProps({
  loginYn: {
    type: Boolean,
    default: false
  }
})

alert(`로그인 여부 > ${props.loginYn}`);
</script>

<template>
  <header class="header">
    <div class="container">
      <div class="header-content">
        <div class="logo">
          <router-link to="/">내 블로그</router-link>
        </div>

        <nav class="nav desktop-nav">
          <router-link to="/" class="nav-link">홈</router-link>
          <router-link to="/notice" class="nav-link">공지사항</router-link>
          <router-link to="/post" class="nav-link">끄적이기</router-link>
          <router-link to="/profile" class="nav-link">프로필</router-link>
          <router-link to="/settings" class="nav-link">설정</router-link>
        </nav>

        <UserInfo @show-login="$emit('show-login')" @show-register="$emit('show-register')" :loginYn="props.loginYn" />
      </div>
      <MobileNav />

    </div>
  </header>
</template>

<style scoped>
/* styles here */
</style>
