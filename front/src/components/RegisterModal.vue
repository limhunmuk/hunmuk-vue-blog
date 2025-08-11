<script setup lang="ts">
import { ref } from 'vue';
import {useRoute, useRouter} from "vue-router";
import {useAuthStore} from "@/stores/authStore.js";
const authStore = useAuthStore();
const router = useRouter();
let routeInfo = useRoute();

import apiClient from '@/stores/jwtStore.ts'

interface Member {
  id: number
  email: string
  name: string
  nickName: string
  memType: string
  joinDt: string
  phoneNo: string
  addr: string
  addrDtl: string
}

const regMember = ref<Member | null>(null)
regMember.value = {
  email: 'ihm2119@naver.com',
  name: '테스트',
  nickName: '룩엣더',
  memType: 'Normal',
  phoneNo: '010-2020-2121',
  addr: '서울특별시 성북동',
  addrDtl: '성신여대입구역 1번출구',
  password: '$gnsanr1234',
  confirmPassword: '$gnsanr1234'
}
// 인증 관련 함수
function showLoginModal() {
  const modal  = document.getElementById("loginModal")
  modal.classList.add("show")
  closeModal("registerModal")
}

function showRegisterModal() {
  const modal = document.getElementById("registerModal")
  modal.classList.add("show")
  closeModal("loginModal")
}

function closeModal(modalId) {
  const modal = document.getElementById(modalId)
  modal.classList.remove("show")

  // 에러 메시지 초기화
  const errorElement = modal.querySelector(".error-message")
  if (errorElement) {
    errorElement.classList.remove("show")
    errorElement.textContent = ""
  }

  // 폼 초기화
  const form = modal.querySelector("form")
  if (form) {
    form.reset()
  }
}

function togglePassword(inputId) {
  const input = document.getElementById(inputId)
  const button = input.nextElementSibling
  const icon = button.querySelector("i")

  if (input.type === "password") {
    input.type = "text"
    icon.className = "fas fa-eye-slash"
  } else {
    input.type = "password"
    icon.className = "fas fa-eye"
  }
}

function showError(modalId, message) {
  const modal = document.getElementById(modalId)
  const errorElement = modal.querySelector(".error-message")
  errorElement.textContent = message
  errorElement.classList.add("show")
}

async function handleRegister() {

  alert("회원가입을 진행합니다.")
  const email = regMember.value.email.trim()
  const nickname = regMember.value.nickName.trim()
  const password = regMember.value.password.trim()
  const confirmPassword = regMember.value.confirmPassword.trim()
  // 입력값 검증
  if (!email || !nickname || !password || !confirmPassword) {
      showError("registerModal", "모든 필드를 입력해주세요.")
      return false;
  }

  if (password !== confirmPassword) {
      showError("registerModal", "비밀번호가 일치하지 않습니다.")
      return false;
  }

  if (password.length < 6) {
      showError("registerModal", "비밀번호는 최소 6자 이상이어야 합니다.")
      return false;
  }
  // 이메일 형식 검증
  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!emailPattern.test(email)) {
      showError("registerModal", "유효한 이메일 주소를 입력해주세요.")
      return false;
  }

  // 닉네임 형식 검증
  const nicknamePattern = /^[가-힣a-zA-Z0-9_]{3,20}$/

  console.log("닉네임 : " + nickname)
  if (!nicknamePattern.test(nickname)) {
      showError("registerModal", "닉네임은 3자 이상 20자 이하의 영문, 숫자, 밑줄(_)만 사용할 수 있습니다.")
      return false;
  }

  apiClient.post('/signup', {
    email: email,
    name: regMember.value.name || '', // 이름은 선택 사항으로 처리
    nickName: nickname,
    password: password,
    confirmPassword: confirmPassword,
    memType: regMember.value.memType || 'Normal', // 기본값은 'Normal'
    phoneNo: regMember.value.phoneNo || '',       // 전화번호는 선택 사항으로 처리
    addr: regMember.value.addr || '',             // 주소는 선택 사항으로 처리
    addrDtl: regMember.value.addrDtl || '',       // 상세 주소는 선택 사항으로 처리
    password: regMember.value.password,
    confirmPassword: regMember.value.confirmPassword

  })
  .then(response => {
      console.log("회원가입 성공:", response.data)
      alert("회원가입이 완료되었습니다. 로그인해주세요.")
      closeModal("registerModal")
      showLoginModal()
  })
  .catch(error => {
      console.error("회원가입 실패:", error)
      if (error.response && error.response.data) {
          showError("registerModal", error.response.data.message || "회원가입에 실패했습니다.")
      } else {
          showError("registerModal", "회원가입에 실패했습니다. 다시 시도해주세요.")
      }
  })
}


</script>

<template>
  <!-- 회원가입 모달 -->
  <div class="modal" id="registerModal" v-show="true" :class="{ 'show': true }">
    <div class="modal-content">
      <div class="modal-header">
        <h1>회원가입</h1>
        <button class="modal-close" onclick="closeModal('registerModal')" aria-label="닫기">&times;</button>
      </div>
      <div class="modal-header">새 계정을 만들어 블로그를 시작하세요.</div>
      <form id="registerForm" @submit.prevent="handleRegister">
        <div class="form-group">
          <label for="registerEmail">이메일</label>
          <input type="email" id="registerEmail" placeholder="이메일을 입력하세요" required v-model="regMember.email">
        </div>
        <div class="form-group">
          <label for="registerName">이름</label>
          <input type="text" id="registerName" placeholder="이름을 입력하세요" required v-model="regMember.name">
        </div>
        <div class="form-group">
          <label for="registerNickname">닉네임</label>
          <input type="text" id="registerNickname" placeholder="닉네임을 입력하세요" required v-model="regMember.nickName">
        </div>
        <div class="form-group">
          <label for="registerPassword">비밀번호</label>
          <div class="password-input">
            <input type="password" id="registerPassword" placeholder="비밀번호를 입력하세요 (최소 6자)" required v-model="regMember.password">
            <button type="button" class="password-toggle" onclick="togglePassword('registerPassword')" aria-label="비밀번호 보기">
              <i class="fas fa-eye"></i>
            </button>
          </div>
        </div>
        <div class="form-group">
          <label for="registerConfirmPassword">비밀번호 확인</label>
          <div class="password-input">
            <input type="password" id="registerConfirmPassword" placeholder="비밀번호를 다시 입력하세요" required v-model="regMember.confirmPassword">
            <button type="button" class="password-toggle" onclick="togglePassword('registerConfirmPassword')" aria-label="비밀번호 보기">
              <i class="fas fa-eye"></i>
            </button>
          </div>
        </div>
        <div class="error-message" id="registerError"></div>
        <button type="submit" class="btn btn-primary btn-full">
          회원가입
        </button>
      </form>

      <div class="social-login">
        <div class="divider"><span>또는</span></div>
        <button class="btn btn-social google" onclick="socialLogin('google')">
          <svg width="18" height="18" viewBox="0 0 24 24">
            <path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
            <path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
            <path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/>
            <path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
          </svg>
          Google로 계속하기
        </button>
        <button class="btn btn-social github" onclick="socialLogin('github')">
          <i class="fab fa-github"></i>
          GitHub로 계속하기
        </button>
      </div>

      <div class="modal-footer">
        <p>이미 계정이 있으신가요? <a href="#" onclick="showLoginModal()">로그인</a></p>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* styles here */
</style>