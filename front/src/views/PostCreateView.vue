<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axiosRequest from '@/stores/jwtStore.ts'
const router = useRouter()
let routeInfo = useRoute()

interface PostForm {
  title: string
  content: string
}
const postForm = ref<PostForm>({
  title: '',
  content: '',
})

interface MemberForm {
  id: number
  loginId: string
  name: string
  nickname: string
  memType: string
  joinDt: string | null
  phoneNo: string
  addr: string
  addrDtl: string
}

const goToList = () => {
  router.push({ name: 'post' })
}

const createPost = async () => {

  if (!isLoggedIn()) {
    alert(localStorage.getItem('accessToken'))
    alert('로그인이 필요합니다.')
    return
  }

  try {
    axiosRequest.post('/post', {
      title: postForm.value.title,
      content: postForm.value.content,
    }).then(response => {

      console.log("========로그인 응답 ========== :");
      console.log(response);
      console.log("========로그인 응답 ========== :");

      if(response.status === 200) {
        alert("등록되었습니다")
        goToList()
      }else{
        alert("게시물 등록에 실패하였습니다")
      }
    })
  } catch (error) {
    console.error('게시물 등록 실패:', error)
    alert('게시물 등록 중 오류가 발생했습니다.')
  }
}

const isLoggedIn = () => {
  return !!localStorage.getItem('accessToken')
}
</script>

<template>
  <div class="container">
    <div class="page-header">
      <h1>새 게시글 작성</h1>
      <router-link :to="{ name: 'post' }" class="btn btn-outline">
        <i class="fas fa-arrow-left"></i>
        목록으로
      </router-link>
    </div>

    <form id="writePostForm" class="write-form">
      <div class="form-card">
        <div class="form-group">
          <label for="postTitle">제목</label>
          <input
            type="text"
            id="postTitle"
            v-model="postForm.title"
            placeholder="게시글 제목을 입력하세요"
            required
          />
        </div>

        <div class="form-group">
          <label for="postContent">내용</label>
          <textarea
            id="postContent"
            placeholder="게시글 내용을 입력하세요"
            v-model="postForm.content"
            required
          ></textarea>
        </div>
      </div>

      <div class="form-actions">
        <router-link :to="{ name: 'post' }" class="btn btn-outline">취소</router-link>
        <button type="submit" class="btn btn-primary" @click.prevent="createPost">
          <i class="fas fa-save"></i>
          게시글 등록
        </button>
      </div>
    </form>
  </div>
</template>

<style scoped></style>