import { createRouter, createWebHistory } from 'vue-router'
import HomeView from "@/views/HomeView.vue";
import NoticeListView from "@/views/NoticeListView.vue";
import NoticeDetailView from "@/views/NoticeDetailView.vue"
import NoticeCreateView from "@/views/NoticeCreateView.vue";
import PostListView from "@/views/PostListView.vue";
import PostDetailView from "@/views/PostDetailView.vue";
import PostCreateView from '@/views/PostCreateView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/notice',
      name: 'notice',
      component: NoticeListView
    },
    {
      path: '/notice/:noticeId',
      name: 'noticeDetail',
      component: NoticeDetailView,
      props: true
    },
    {
      path: '/notice/new',
      name: 'noticeCreate',
      component: NoticeCreateView,
    },
    {
      path: '/post',
      name: 'post',
      component: PostListView
    },
    {
      path: '/post/:postId',
      name: 'postDetail',
      component: PostDetailView,
      props: true
    },
    {
      path: '/post/new',
      name: 'postCreate',
      component: PostCreateView,
    },
  ],
})

export default router
