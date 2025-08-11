<script setup lang="ts">

import { ref, onMounted } from 'vue';
import { useRouter } from "vue-router";
// import axios from "axios";
import axiosRequest from '@/stores/jwtStore.ts';
const router = useRouter();

interface Notice {
  noticeId: number;
  title: string;
  content: string;
  regId: string;
  regDt: string;
  memId: number;
  logInId: string;
  nickName: string;
  viewCnt: number;
  likeCnt: number;
  importantYn: string;
}

const noticeList = ref<Notice[]>([]);

const getNotices = async () => {

  axiosRequest.get('/notice', {
  })
    .then((response) => {
      noticeList.value = response.data;
      console.log("공지사항 목록:", noticeList.value);
    })
    .catch((error) => {
      console.error('공지사항 목록 조회 실패:', error);
      alert('공지사항 목록을 불러오는 데 실패했습니다.');
    });
};

onMounted(() => {
  getNotices();
});

const goToDetail = (id: number) => {
  router.push({ name: 'noticeDetail', params: { noticeId: id } });
};

const goToEdit = (id: number) => {
  router.push({ name: 'noticeEdit', params: { noticeId: id } });
};

const deleteNotice = async (id: number) => {
  if (confirm('정말로 이 공지사항을 삭제하시겠습니까?')) {
    try {
      const response = await fetch(`/api/notice/${id}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem("accessToken")}`
        }
      });
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      alert('공지사항이 삭제되었습니다.');
      getNotices(); // Refresh the list after deletion
    } catch (error) {
      console.error('Error deleting notice:', error);
      alert('공지사항 삭제에 실패했습니다.');
    }
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
      <h1>공지사항</h1>
      <router-link :to="{name: 'noticeCreate'}" class="btn btn-danger" id="adminBtn" style="">
        <i class="fas fa-pen"></i>
        공지 작성
      </router-link>
    </div>
    <div id="noticesList">
     <div v-if="noticeList.length === 0" class="empty-state">
      <p>등록된 공지사항이 없습니다.</p>
     </div>

      <article v-for="notice in noticeList.content" :key="notice.id" class="notice-item">
        <div class="notice-item-header">
          <div style="flex: 1;" @click="goToDetail(notice.noticeId)">
            <div style="display: flex; align-items: center; justify-content: space-between; margin-bottom: 0.5rem;">
              <h2 class="notice-title">{{ notice.title }}</h2>
              <span class="important-badge" v-show='notice.importantYn === "Y"'>중요</span>
            </div>
          </div>
          <div v-if="isAdmin()" class="admin-actions">
            <button class="admin-action-btn" @click="goToEdit(notice.noticeId)" title="수정">
              <i class="fas fa-edit"></i>
            </button>
            <button class="admin-action-btn delete" @click="deleteNotice(notice.noticeId)" title="삭제">
              <i class="fas fa-trash"></i>
            </button>
          </div>
        </div>

        <div @click="goToDetail(notice.noticeId)">
          <p class="notice-date">2025-03-01</p>
          <p class="notice-content">{{ notice.content }}</p>
          <div class="notice-stats">
            <div class="notice-stats-left">
              <div class="stat-item">
                <i class="fas fa-eye"></i>
                <span>{{ notice.viewCnt || 0 }}</span>
              </div>
            </div>
            <div class="stat-item">
              <i class="fas fa-heart"></i>
              <span>{{ notice.likeCnt || 0 }}</span>
            </div>
          </div>
        </div>
      </article>
    </div>
  </div>
</template>

<style scoped>
.notice-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 2rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid #374151;
}

.notice-header h1 {
  font-size: 2rem;
  font-weight: bold;
}

.admin-btn {
  background-color: #dc2626;
  color: white;
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 0.375rem;
  font-weight: 500;
  cursor: pointer;
  text-decoration: none;
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  transition: background-color 0.2s;
}

.admin-btn:hover {
  background-color: #b91c1c;
}

.notice-item {
  background-color: #1f2937;
  border-radius: 0.5rem;
  padding: 1.5rem;
  margin-bottom: 1rem;
  transition: background-color 0.2s;
  cursor: pointer;
}

.notice-item:hover {
  background-color: #111827;
}

.notice-item-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 0.5rem;
}

.notice-title {
  font-size: 1.25rem;
  font-weight: 500;
  color: white;
  text-decoration: none;
  transition: color 0.2s;
}

.notice-title:hover {
  color: #f87171;
}

.important-badge {
  background-color: rgba(220, 38, 38, 0.2);
  color: #f87171;
  padding: 0.25rem 0.5rem;
  border-radius: 0.25rem;
  font-size: 0.75rem;
  font-weight: 500;
}

.admin-actions {
  display: flex;
  gap: 0.5rem;
  margin-left: 1rem;
}

.admin-action-btn {
  background: none;
  border: none;
  color: #9ca3af;
  cursor: pointer;
  padding: 0.25rem;
  border-radius: 0.25rem;
  transition: all 0.2s;
}

.admin-action-btn:hover {
  background-color: #374151;
  color: white;
}

.admin-action-btn.delete:hover {
  background-color: rgba(220, 38, 38, 0.2);
  color: #f87171;
}

.notice-date {
  color: #9ca3af;
  font-size: 0.875rem;
  margin-bottom: 0.75rem;
}

.notice-content {
  color: #d1d5db;
  margin-bottom: 1rem;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.notice-stats {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 0.875rem;
  color: #6b7280;
}

.notice-stats-left {
  display: flex;
  gap: 1rem;
}

.empty-state {
  text-align: center;
  padding: 3rem;
  color: #9ca3af;
}
</style>