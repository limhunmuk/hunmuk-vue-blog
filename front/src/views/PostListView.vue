
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axiosRequest from "@/stores/jwtStore.ts";

const router = useRouter()

interface Post {
  postId: number
  title: string
  content: string
  authorName: string
  createdAt: string
  views?: number
  likes?: number
  type: string
  commentCnt?: number
}


const posts = ref<Post[]>([])

const apiClient = axiosRequest.create({
  baseURL: '/api',
  headers: {
    "Content-Type": "application/json",
  },
});


const getPosts = () => {

  apiClient.get('/post', {
  })
    .then((response) => {
    posts.value = response.data
    console.log('게시물 목록:', posts.value)
  })
  .catch(error => {
    console.error('Error fetching posts:', error)
    alert('게시물 목록을 불러오는 데 실패했습니다.')
  })
}

const goToDetail = (postId: number) => {
  router.push({ name: 'postDetail', params: { postId } })
}

const formatDate = (dateStr: string) => {
  const date = new Date(dateStr)
  return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
}

onMounted(() => {
  getPosts()
})
</script>

<template>
  <div class="posts-container">
    <div class="posts-header">
      <h1>끄적이기</h1>
      <a href="" class="write-btn btn" id="writeBtn" style="">
        <i class="fas fa-pen"></i>
        글쓰기
      </a>
    </div>
    <div id="postsList" class="posts-list">
      <!-- 게시물이 없을 때 -->
      <div v-if="posts.length === 0" class="empty-state">
        <p>아직 작성된 게시물이 없습니다.</p>
        <router-link to="/write-post" class="btn btn-primary" style="margin-top: 1rem;">
          첫 게시물 작성하기
        </router-link>
      </div>

      <!-- 게시물이 있을 때 -->
      <article
          v-for="post in posts.content"
          :key="post.postId"
          class="post-item"
          @click="goToDetail(post.postId)"
      >
        <div class="post-item-header">
          <h2 class="post-item-title">{{ post.title }}</h2>
          <div class="post-item-meta">
            <div>{{ post.regId }}</div>
            <div>{{ formatDate(post.regDt) }}</div>
          </div>
        </div>
        <p class="post-item-content">{{ post.content }}</p>
        <div class="post-item-stats">
          <div class="post-stats-left">
            <div class="stat-item">
              <i class="fas fa-eye"></i>
              <span>{{ post.views || 0 }}</span>
            </div>
            <div class="stat-item">
              <i class="fas fa-comment"></i>
              <span>{{ post.commentCnt || 0 }}</span>
            </div>
          </div>
  <!--        <div class="stat-item">
            <i class="fas fa-heart"></i>
            <span>{{ post.likes || 0 }}</span>
          </div>-->
        </div>
      </article>
    </div>
  </div>
</template>

<style scoped>
.posts-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1.5rem;
}

.posts-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 2rem;
  padding: 0 0.5rem;
}

.posts-header h1 {
  font-size: 2rem;
  font-weight: bold;
  color: white;
}

.posts-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding: 0 0.5rem;
}

.post-item {
  background-color: #1f2937;
  border-radius: 0.5rem;
  padding: 1.5rem;
  transition: background-color 0.2s;
  cursor: pointer;
}

.post-item:hover {
  background-color: #111827;
}

.post-item-header {
  display: flex;
  align-items: start;
  justify-content: space-between;
  margin-bottom: 0.5rem;
}

.post-item-title {
  font-size: 1.25rem;
  font-weight: 500;
  color: white;
  text-decoration: none;
  transition: color 0.2s;
}

.post-item-title:hover {
  color: #60a5fa;
}

.post-item-meta {
  text-align: right;
  font-size: 0.875rem;
  color: #9ca3af;
}

.post-item-content {
  color: #d1d5db;
  margin-bottom: 0.75rem;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.post-item-stats {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 0.875rem;
  color: #6b7280;
}

.post-stats-left {
  display: flex;
  gap: 1rem;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 0.25rem;
}

.empty-state {
  text-align: center;
  padding: 3rem;
  color: #9ca3af;
}

.write-btn {
  background-color: #2563eb;
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

.write-btn:hover {
  background-color: #1d4ed8;
}

@media (max-width: 768px) {
  .posts-container {
    padding: 0 1rem;
  }

  .posts-header {
    flex-direction: column;
    gap: 1rem;
    align-items: stretch;
  }

  .posts-header h1 {
    text-align: center;
  }

  .write-btn {
    align-self: center;
  }
}
</style>