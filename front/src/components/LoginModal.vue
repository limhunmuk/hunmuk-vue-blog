
<script setup>
import { ref } from 'vue';
import {useRoute, useRouter} from "vue-router";
import axiosRequest from "@/stores/jwtStore.ts";
import {useAuthStore} from "@/stores/authStore.js";
const authStore = useAuthStore();
const router = useRouter();
let routeInfo = useRoute();

const email = ref('limhunmuk@gmail.com');
const password = ref('1234');
const showPassword = ref(false);
const error = ref('');

const emit = defineEmits(['closeLoginPop']);

const closeModal = () => {
  emit('closeLoginPop', false);
};

const togglePassword = () => {
  showPassword.value = !showPassword.value;
};

const handleLogin = () => {

  if (email.value === '') {
    error.value = '이메일을 입력해주세요.';
  }else if(password.value === '') {
    error.value = '비밀번호를 입력해주세요.';
  } else {
    axiosRequest.post('/login', {
      loginId: email.value,
      password: password.value
    })
    .then(response => {

      console.log("========로그인 응답 ========== :");
      console.log(response);
      console.log("========로그인 응답 ========== :");

      if (response.status === 200) {
        closeModal();
        authStore.login(response.data.accessToken, response.data.refreshToken, response.data.member);
      } else {
        error.value = response.data.message || '로그인 실패';
      }
    })
  }
};

const socialLogin = (provider) => {
  alert(`${provider}로 로그인 시도`);
};
</script>

<template>
  <div class="modal" id="loginModal" v-show="true" :class="{ 'show': true }">
    <div class="modal-content">
      <div class="modal-header">
        <h2>로그인</h2>
        <button class="modal-close" @click="closeModal">X</button>
      </div>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="loginEmail">이메일</label>
          <input type="email" id="loginEmail" required v-model="email">
        </div>
        <div class="form-group">
          <label for="loginPassword">비밀번호</label>
          <div class="password-input">
            <input :type="showPassword ? 'text' : 'password'" v-model="password" required />
            <button type="button" class="password-toggle" @click="togglePassword">
              <i class="fas fa-eye"></i>
            </button>
          </div>
        </div>
        <div class="error-message" id="loginError"></div>
        <button type="submit" class="btn btn-primary btn-full">로그인</button>
      </form>

      <div class="social-login">
        <div class="divider"><span>또는</span></div>
        <button class="btn btn-social google" onclick="socialLogin('google')">
          <i class="fab fa-google"></i>
          Google로 계속하기
        </button>
        <button class="btn btn-social github" onclick="socialLogin('github')">
          <i class="fab fa-github"></i>
          GitHub로 계속하기
        </button>
      </div>

      <div class="modal-footer">
        <p>계정이 없으신가요? <a href="#" onclick="showRegisterModal()">회원가입</a></p>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 기존 스타일 적용 가능 */
</style>