import axios from "axios";
import router from "@/router/router";
import { useAuthStore } from "@/stores/authStore";

// Axios 인스턴스 생성
const apiClient = axios.create({
  baseURL: '/api',
  headers: {
    "Content-Type": "application/json",
  },
});

// 요청 인터셉터
apiClient.interceptors.request.use((config) => {
  const authStore = useAuthStore(); // ✅ Pinia store 인스턴스 호출
  const accessToken = authStore.accessToken;

  if (accessToken) {
    config.headers.Authorization = `Bearer ${accessToken}`;
  }
  return config;
});

// 응답 인터셉터
apiClient.interceptors.response.use(
    (response) => {
      return response;
    },
    async (error) => {
      const originalRequest = error.config;

      if ((error.response?.status === 401) && !originalRequest._retry) {
        originalRequest._retry = true;

        const authStore = useAuthStore();
        const refreshToken = authStore.refreshToken;

        if (refreshToken) {
          try {
            const response = await axios.post('/api/auth/refresh', { token: refreshToken });
            const newAccessToken = response.data.accessToken;

            // ✅ authStore로 토큰 갱신
            authStore.login(newAccessToken, refreshToken, authStore.member);

            // ✅ 원래 요청 재시도
            originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
            return apiClient(originalRequest);
          } catch (refreshError) {
            console.error("리프레시 토큰 만료:", refreshError);

            authStore.logout(); // ✅ 토큰 제거

            alert("세션이 만료되었습니다. 다시 로그인해주세요.");
            router.push({ name: "/" });
          }
        }
      }

      router.push({ name: "/" });
    }
);

export default apiClient;
