<script setup lang="ts">

import { ref, onMounted } from 'vue';
import {useRoute, useRouter} from "vue-router";
import axiosRequest from "@/stores/jwtStore.ts";
const router = useRouter();
let routeInfo = useRoute();

interface Post {
  postId: number | string;
  title: string;
  content: string;
  regId: string;
  regDt: string;
  modId: string;
  modDt: string;
  likeCnt: number;
  viewCnt: number;
  memId: number;
  logInId: string;
}

const postDetail = ref<Post | null>(null);

const fetchPostDetail = async () => {
  axiosRequest.get('/post/' + routeInfo.params.postId, {
  })
    .then((response) => {
      postDetail.value = response.data;
      console.log("끄적이기 상세:", postDetail.value);
    })
    .catch((error) => {
      console.error('끄적이기 상세 조회 실패:', error);
      alert('끄적이기 상세 조회 실패했습니다.');
    });
};

onMounted(() => {
  fetchPostDetail();
});

const goToEdit = (id: number) => {
  router.push({ name: 'postEdit', params: { postId: id } });
};

const goToList = () => {
  router.push({ name: 'post' });
};

const deletePost = async (id: number) => {
  if (confirm('정말로 이 끄적이기을 삭제하시겠습니까?')) {
    try {
      const response = await fetch(`/api/post/${id}`, {
        method: 'DELETE',
      });
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      alert('끄적이기가 삭제되었습니다.');
      goToList();
    } catch (error) {
      console.error('Error deleting notice:', error);
      alert('끄적이기가 삭제에 실패했습니다.');
    }
  }
};

const handleLike = async () => {
  if (!postDetail.value) return;

  try {
    const response = await apiClient.post(`/post/${postDetail.value.postId}/like`, {}, {
      headers: {
      }
    });

    if (response.status === 200) {
      postDetail.value.likeCnt = response.data.likeCnt;
      alert('좋아요가 반영되었습니다.');
    } else {
      alert('좋아요 반영에 실패했습니다.');
    }
  } catch (error) {
    console.error('좋아요 처리 중 오류 발생:', error);
    alert('좋아요 처리 중 오류가 발생했습니다.');
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

function formatDateTime(date: string | Date): string {
  return new Date(date).toLocaleString('ko-KR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
  })
}

</script>

<template>
  <div class="container">

    <div class="page-header">
      <a href="javascript:history.back()" class="btn btn-outline">
        <i class="fas fa-arrow-left"></i>
        목록으로
      </a>

      <div class="post-actions" id="postActions" style="display: none;">
        <button class="btn btn-outline" id="editBtn" @click="goToEdit(postDetail?.postId)">
          <i class="fas fa-edit"></i>
          수정
        </button>
        <button class="btn btn-danger" id="deleteBtn" @click="deletePost(postDetail?.postId)">
          <i class="fas fa-trash"></i>
          삭제
        </button>
      </div>
    </div>

    <article class="post-detail" id="postDetail">
      <header>
        <h1>{{ postDetail?.title }}</h1>
        <div class="post-meta">
          <div class="meta-left">
            <span>{{ postDetail?.regId }}</span>
            <span>{{ formatDateTime(postDetail?.regDt) }}</span>
            <span v-if="postDetail?.modDt !== postDetail?.regDt">(수정됨)</span>
          </div>
          <div class="meta-right">
            <span><i class="fas fa-eye"></i> {{ postDetail?.viewCnt || 0 }}</span>
            <span><i class="fas fa-heart"></i> {{ postDetail?.likeCnt || 0 }}</span>
          </div>
        </div>
      </header>

      <div class="post-content-detail" v-html="postDetail?.content"></div>

      <footer class="post-footer">
        <button class="btn btn-outline" @click="handleLike">
          <i class="fas fa-heart"></i>
          좋아요 ({{ postDetail?.likeCnt || 0 }})
        </button>
      </footer>
    </article>

    <!-- 댓글 섹션 -->
    <section class="comments-section" id="commentsSection">
      <h3><i class="fas fa-comments"></i> 댓글 <span id="commentCount">0</span></h3>

      <!-- 댓글 작성 폼 -->
      <div class="comment-form" id="commentForm" style="display: none;">
        <form id="addCommentForm">
          <div class="form-group">
            <textarea id="commentContent" placeholder="댓글을 입력하세요..." required></textarea>
          </div>
          <div class="form-actions">
            <button type="submit" class="btn btn-primary">
              <i class="fas fa-paper-plane"></i>
              댓글 등록
            </button>
          </div>
        </form>
      </div>

      <div class="login-required" id="loginRequired">
        <p>댓글을 작성하려면 로그인이 필요합니다.</p>
      </div>

      <!-- 댓글 목록 -->
      <div class="comments-list" id="commentsList">
        <!-- 댓글들이 JavaScript로 동적 생성됩니다 -->
      </div>
    </section>
  </div>
</template>

<style scoped>

</style>