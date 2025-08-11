<script setup lang="ts">

import { ref, onMounted } from 'vue';
import {useRoute, useRouter} from "vue-router";
import axiosRequest from "@/stores/jwtStore.ts";
const router = useRouter();
let routeInfo = useRoute();



interface NoticeForm {
  title: string;
  content: string;
}
const noticeForm = ref<NoticeForm>({
    title: '',
    content: ''
});

const apiClient = axiosRequest.create({
  baseURL: '/api',
  headers: {
    "Content-Type": "application/json",
  },
});

onMounted(() => {
});


const goToList = () => {
  router.push({ name: 'noticeListView' });
};

const createNotice = async () => {

  if (!isLoggedIn()) {
    alert("로그인이 필요합니다.");
    return;
  }

  if (!isAdmin()) {
    alert("관리자만 공지사항을 작성할 수 있습니다.");
    return;
  }

  try {
    const response = await apiClient.post('/notice', {
      title: noticeForm.value.title,
      content: noticeForm.value.content
    });

    if (response.status === 201) {
      alert("공지사항이 등록되었습니다.");
      goToList();
    } else {
      alert("공지사항 등록에 실패했습니다.");
    }
  } catch (error) {
    console.error('공지사항 등록 실패:', error);
    alert('공지사항 등록 중 오류가 발생했습니다.');
  }
};

const formatDate = (dateString: string) => {
  const date = new Date(dateString);
  return date.toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  });
};

const isAdmin = () => {
  // Assuming you have a method to check if the user is an admin
  return localStorage.getItem("isAdmin") === "true";
};

const isLoggedIn = () => {
  return !!localStorage.getItem("accessToken");
};

</script>

<template>
  <div class="container">
    <div class="page-header">
      <h1>새 공지사항 작성</h1>
      <router-link :to="{name: 'notice'}" class="btn btn-outline">
        <i class="fas fa-arrow-left"></i>
        목록으로
      </router-link>
    </div>

    <form id="writeNoticeForm" class="write-form">
      <div class="form-card">
        <div class="form-group">
          <label for="noticeTitle">제목</label>
          <input type="text" id="noticeTitle" v-model="noticeForm.title" placeholder="공지사항 제목을 입력하세요" required>
        </div>

        <div class="form-group">
          <label for="noticeContent">내용</label>
          <textarea id="noticeContent" placeholder="공지사항 내용을 입력하세요" v-model="noticeForm.content" required></textarea>
        </div>
      </div>

      <div class="form-actions">
        <router-link :to="{name: 'notice'}" class="btn btn-outline">취소</router-link>
        <button type="submit" class="btn btn-danger" @click.prevent="createNotice">
          <i class="fas fa-save"></i>
          공지 등록
        </button>
      </div>
    </form>
  </div>
</template>

<style scoped>

</style>