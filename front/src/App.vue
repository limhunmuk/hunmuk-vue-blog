<script setup>
import HeaderBar from './components/HeaderBar.vue';
import LoginModal from './components/LoginModal.vue';
import RegisterModal from './components/RegisterModal.vue';
import { useAuthStore } from './stores/authStore'; // authStore를 가져옵니다.
import { ref } from 'vue';

const showLogin = ref(false);
const showRegister = ref(false);

const openLogin = () => {
  console.log("값 모달 ======================" + showLogin.value)
  showLogin.value = !showLogin.value;
};

const isLogin = ref(false); // 로그인 상태를 관리하는 변수
const authStore = useAuthStore();
isLogin.value = authStore.isLoggedIn; // authStore에서 로그인 상태를 가져옵니다.

alert(isLogin.value ? "로그인 상태입니다.1" : "로그인 상태가 아닙니다.2");
</script>
<template>
  <HeaderBar @show-login="openLogin()" @show-register="showRegister = true" :loginYn="isLogin"  />
  <div>
    로그인창 활성화 > showLogin : {{showLogin}}
  </div>
  <div>
    회원가입 창 활성화 > showRegister : {{showRegister}}
  </div>
  <div>
    로그인 여부 > isLogin : {{isLogin}}
  </div>

  <main class="main">
    <router-view />
  </main>

  <LoginModal v-if="showLogin" @closeLoginPop="showLogin = false" @show-login="showLogin = true" />
  <RegisterModal v-if="showRegister" @closeJoinPop="showRegister = false" @show-register="showRegister = true" />
</template>

