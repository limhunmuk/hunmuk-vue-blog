
<script setup>
/* styles here */
const props = defineProps({
  loginYn: {
    type: Boolean,
    default: false
  }
})

import  {useAuthStore} from "@/stores/authStore.js";
const authStore = useAuthStore();

</script>

<template>

  <div class="user-info">
    <div class="auth-buttons" v-if="authStore.isLoggedIn === false">
      <button class="btn btn-outline" @click="$emit('show-login')">로그인</button>
      <button class="btn btn-primary" @click="$emit('show-register')">회원가입</button>
    </div>
    <div class="user-details" v-if="authStore.isLoggedIn === true">
      <span class="username">{{ authStore.getMember?.nickName !== '' ? authStore.getMember?.nickName : authStore.getMember?.name }}</span>
      <button class="btn btn-ghost" @click="authStore.logout()">로그아웃</button>
    </div>
    <div>
      [{{ props.loginYn}}]
      {{ props.loginYn === true ? '로그인 상태' : '비로그인 상태' }}
    </div>
  </div>

</template>
