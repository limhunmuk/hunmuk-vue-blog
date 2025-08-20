<script setup lang="ts">

import { ref, onMounted } from 'vue';
import {useRoute, useRouter} from "vue-router";
import axiosRequest from "@/stores/jwtStore.ts";
const router = useRouter();
let routeInfo = useRoute();

interface Notice {
  noticeId: number | string;
  title: string;
  content: string;
  regId: string;
  regDt: string;
  memId: number;
  nickName: string;
  logInId: string;
  viewCnt: number;
  likeCnt: number;
  importantYn: string;
}

const noticeDetail = ref<Notice | null>(null);

const fetchNotice = async () => {
  if (!routeInfo.params.noticeId) {
      alert('공지사항 ID가 없습니다.');
      return;
  }
  axiosRequest.get('/notice/' + routeInfo.params.noticeId, {
  })
    .then((response) => {
      noticeDetail.value = response.data;
      console.log("공지사항 상세:", noticeDetail.value);
    })
    .catch((error) => {
      console.error('공지사항 상세 조회 실패:', error);
      console.log(error.status);
      if( error.response && error.response.status === 403) {
        router.push({ name: '/' });
      }
    });
};

onMounted(() => {
  fetchNotice();
});

const goToDetail = (id: number) => {
  router.push({ name: 'noticeDetailView', params: { noticeId: id } });
};

const goToEdit = (id: number) => {
  router.push({ name: 'noticeEdit', params: { noticeId: id } });
};

const goToList = () => {
  router.push({ name: 'notice' });
};

const deleteNotice = async (id: number) => {
  if (confirm('정말로 이 공지사항을 삭제하시겠습니까?')) {
    axiosRequest.delete('/notice/' + id, {
    })
      .then((response) => {
        if (response.status === 204) {
          alert('공지사항이 삭제되었습니다.');
          goToList();
        } else {
          alert('공지사항 삭제에 실패했습니다.');
        }
      })
      .catch((error) => {
        console.error('공지사항 삭제 실패:', error);
        alert('공지사항 삭제에 실패했습니다.');
      });
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
      <router-link :to="{name : 'notice'}" class="btn btn-outline">
        <i class="fas fa-arrow-left"></i>
        목록으로
      </router-link>

      <div class="post-actions" id="adminActions" style="display: none;">
        <button class="btn btn-outline" id="btnEditNotice" @click="goToEdit(noticeDetail?.noticeId)">
          <i class="fas fa-edit"></i>
          수정
        </button>
        <button class="btn btn-danger" id="btnDeleteNotice" @click="deleteNotice(noticeDetail?.noticeId)">
          <i class="fas fa-trash"></i>
          삭제
        </button>
      </div>
    </div>

    <article id="noticeDetail">
      <header>
        <div style="display: flex; align-items: center; justify-content: space-between; margin-bottom: 1rem;">
          <h1>{{ noticeDetail?.title }}</h1>
          <span class="important-badge">중요 공지</span>
        </div>
        <div class="notice-meta">
          <div class="meta-left" style="margin-bottom: 1rem;">
            <span>{{ noticeDetail?.nickName }}</span>
            <span>{{ formatDate(noticeDetail?.regDt) }}</span>
          </div>
          <div class="meta-right">
            <span><i class="fas fa-eye"></i> 조회수 {{noticeDetail?.viewCnt}}</span>
            <span><i class="fas fa-heart"></i> 좋아요수 {{noticeDetail?.likeCnt}}</span>
          </div>
        </div>
      </header>

      <div class="notice-content-detail" >
        {{ noticeDetail?.content }}
      </div>

      <footer class="notice-footer">
        <button class="btn btn-outline" onclick="handleLike()">
          <i class="fas fa-heart"></i>
          좋아요 수 ({{ noticeDetail?.likeCnt || 0 }})
        </button>
      </footer>
    </article>
  </div>
</template>

<style scoped>

</style>