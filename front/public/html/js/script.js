// 전역 변수
let currentUser = null
let weatherData = null
let posts = []
let comments = []
const updateWriteButton = null
const updateAdminButton = null

// 초기화
document.addEventListener("DOMContentLoaded", () => {
    initializeApp()
})

function initializeApp() {
    loadCurrentUser()
    loadPosts()
    loadComments()
    updateUserInterface()
    updateCurrentTime()
    loadWeatherData()
    loadRecentPosts()

    // 시간 업데이트 (1초마다)
    setInterval(updateCurrentTime, 1000)

    // 날씨 업데이트 (10분마다)
    setInterval(loadWeatherData, 10 * 60 * 1000)

    // 초기 샘플 데이터 생성
    initializeSampleData()
}

// 초기 샘플 데이터 생성
function initializeSampleData() {
    const existingPosts = localStorage.getItem("blog_posts")
    if (!existingPosts) {
        const samplePosts = [
            {
                id: "1",
                title: "첫 번째 게시물",
                content: "안녕하세요! 첫 번째 게시물입니다. 이 블로그에서 다양한 이야기를 나누고 싶습니다.",
                authorId: "1",
                authorName: "관리자",
                createdAt: new Date().toISOString(),
                updatedAt: new Date().toISOString(),
                views: 15,
                likes: 3,
                type: "post",
            },
            {
                id: "2",
                title: "중요한 공지사항",
                content: "모든 사용자들이 확인해야 할 중요한 공지사항입니다. 블로그 이용 규칙과 가이드라인을 안내드립니다.",
                authorId: "1",
                authorName: "관리자",
                createdAt: new Date().toISOString(),
                updatedAt: new Date().toISOString(),
                views: 45,
                likes: 8,
                type: "notice",
            },
        ]
        localStorage.setItem("blog_posts", JSON.stringify(samplePosts))
        posts = samplePosts
    }
}

// 데이터 로드 함수들
function loadCurrentUser() {
    const savedUser = localStorage.getItem("blog_user")
    if (savedUser) {
        try {
            currentUser = JSON.parse(savedUser)
        } catch (error) {
            console.error("사용자 정보 파싱 오류:", error)
            localStorage.removeItem("blog_user")
        }
    }
}

function loadPosts() {
    const savedPosts = localStorage.getItem("blog_posts")
    if (savedPosts) {
        try {
            posts = JSON.parse(savedPosts)
        } catch (error) {
            console.error("게시물 데이터 파싱 오류:", error)
            posts = []
        }
    }
}

function loadComments() {
    const savedComments = localStorage.getItem("blog_comments")
    if (savedComments) {
        try {
            comments = JSON.parse(savedComments)
        } catch (error) {
            console.error("댓글 데이터 파싱 오류:", error)
            comments = []
        }
    }
}

// 사용자 인터페이스 업데이트
function updateUserInterface() {
    const authButtons = document.getElementById("authButtons")
    const userProfile = document.getElementById("userProfile")

    if (currentUser) {
        authButtons.classList.add("hidden")
        userProfile.classList.remove("hidden")

        // 사용자 정보 업데이트
        const userNickname = document.getElementById("userNickname")
        const userAvatar = document.getElementById("userAvatar")
        const defaultAvatar = document.getElementById("defaultAvatar")
        const userProvider = document.getElementById("userProvider")

        userNickname.textContent = currentUser.nickname

        if (currentUser.avatar) {
            userAvatar.src = currentUser.avatar
            userAvatar.style.display = "block"
            defaultAvatar.style.display = "none"
        } else {
            userAvatar.style.display = "none"
            defaultAvatar.style.display = "block"
        }

        if (currentUser.provider && currentUser.provider !== "email") {
            userProvider.textContent = currentUser.provider === "google" ? "Google" : "GitHub"
            userProvider.style.display = "inline"
        } else {
            userProvider.style.display = "none"
        }
    } else {
        authButtons.classList.remove("hidden")
        userProfile.classList.add("hidden")
    }

    // 페이지별 업데이트 함수 호출
    if (typeof updateWriteButton === "function") updateWriteButton()
    if (typeof updateAdminButton === "function") updateAdminButton()
}

function updateCurrentTime() {
    const currentTimeElement = document.getElementById("currentTime")
    if (currentTimeElement) {
        const now = new Date()
        const timeString = now.toLocaleTimeString("ko-KR", {
            hour: "2-digit",
            minute: "2-digit",
            second: "2-digit",
        })
        currentTimeElement.textContent = timeString
    }
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

async function handleLogin(event) {
    event.preventDefault()

    const email = document.getElementById("loginEmail").value
    const password = document.getElementById("loginPassword").value

    if (!email || !password) {
        showError("loginModal", "이메일과 비밀번호를 입력해주세요.")
        return
    }

    // 시뮬레이션을 위한 지연
    await new Promise((resolve) => setTimeout(resolve, 1000))

    // 저장된 사용자 목록에서 확인
    const users = JSON.parse(localStorage.getItem("blog_users") || "[]")
    const foundUser = users.find((u) => u.email === email && u.password === password)

    if (foundUser) {
        const { password: _, ...userWithoutPassword } = foundUser
        currentUser = userWithoutPassword
        localStorage.setItem("blog_user", JSON.stringify(userWithoutPassword))
        updateUserInterface()
        closeModal("loginModal")
    } else {
        showError("loginModal", "이메일 또는 비밀번호가 올바르지 않습니다.")
    }
}

async function handleRegister_exit(event) {
    event.preventDefault()

    const email = document.getElementById("registerEmail").value
    const nickname = document.getElementById("registerNickname").value
    const password = document.getElementById("registerPassword").value
    const confirmPassword = document.getElementById("registerConfirmPassword").value

    if (!email || !nickname || !password || !confirmPassword) {
        showError("registerModal", "모든 필드를 입력해주세요.")
        return
    }

    if (password !== confirmPassword) {
        showError("registerModal", "비밀번호가 일치하지 않습니다.")
        return
    }

    if (password.length < 6) {
        showError("registerModal", "비밀번호는 최소 6자 이상이어야 합니다.")
        return
    }

    if (nickname.length < 2) {
        showError("registerModal", "닉네임은 최소 2자 이상이어야 합니다.")
        return
    }

    // 시뮬레이션을 위한 지연
    await new Promise((resolve) => setTimeout(resolve, 1000))

    // 기존 사용자 확인
    const users = JSON.parse(localStorage.getItem("blog_users") || "[]")
    const existingUser = users.find((u) => u.email === email || u.nickname === nickname)

    if (existingUser) {
        showError("registerModal", "이미 존재하는 이메일 또는 닉네임입니다.")
        return
    }

    // 새 사용자 생성 (첫 번째 사용자는 관리자)
    const newUser = {
        id: Date.now().toString(),
        email,
        password,
        nickname,
        bio: "",
        createdAt: new Date().toISOString(),
        role: users.length === 0 ? "admin" : "user",
    }

    users.push(newUser)
    localStorage.setItem("blog_users", JSON.stringify(users))

    // 자동 로그인
    const { password: _, ...userWithoutPassword } = newUser
    currentUser = userWithoutPassword
    localStorage.setItem("blog_user", JSON.stringify(userWithoutPassword))

    updateUserInterface()
    closeModal("registerModal")
}

async function socialLogin(provider) {
    // 시뮬레이션을 위한 지연
    await new Promise((resolve) => setTimeout(resolve, 2000))

    const socialUserData = {
        google: {
            id: `google_${Date.now()}`,
            email: "user@gmail.com",
            nickname: "Google 사용자",
            bio: "Google로 로그인한 사용자입니다.",
            provider: "google",
            avatar: "https://lh3.googleusercontent.com/a/default-user=s96-c",
            createdAt: new Date().toISOString(),
            role: "user",
        },
        github: {
            id: `github_${Date.now()}`,
            email: "user@github.com",
            nickname: "GitHub 사용자",
            bio: "GitHub로 로그인한 사용자입니다.",
            provider: "github",
            avatar: "https://github.com/identicons/jasonlong.png",
            createdAt: new Date().toISOString(),
            role: "user",
        },
    }

    const userData = socialUserData[provider]

    // 기존 사용자 확인
    const users = JSON.parse(localStorage.getItem("blog_users") || "[]")
    let existingUser = users.find((u) => u.email === userData.email && u.provider === provider)

    if (!existingUser) {
        users.push(userData)
        localStorage.setItem("blog_users", JSON.stringify(users))
        existingUser = userData
    }

    currentUser = existingUser
    localStorage.setItem("blog_user", JSON.stringify(existingUser))
    updateUserInterface()
    closeModal("loginModal")
    closeModal("registerModal")
}

function logout() {
    currentUser = null
    localStorage.removeItem("blog_user")
    updateUserInterface()
}

function isAdmin() {
    return currentUser && currentUser.role === "admin"
}

// 날씨 관련 함수
async function loadWeatherData() {
    const weatherLoading = document.getElementById("weatherLoading")
    const weatherContent = document.getElementById("weatherContent")

    if (weatherLoading) weatherLoading.classList.remove("hidden")
    if (weatherContent) weatherContent.classList.add("hidden")

    try {
        // 시뮬레이션된 날씨 데이터
        await new Promise((resolve) => setTimeout(resolve, 1000))

        const weatherConditions = [
            { condition: "sunny", temp: 23, humidity: 45, description: "맑음", icon: "☀️" },
            { condition: "cloudy", temp: 18, humidity: 65, description: "흐림", icon: "☁️" },
            { condition: "rainy", temp: 15, humidity: 85, description: "비", icon: "🌧️" },
            { condition: "snowy", temp: -2, humidity: 70, description: "눈", icon: "❄️" },
            { condition: "partly-cloudy", temp: 20, humidity: 55, description: "구름 조금", icon: "⛅" },
        ]

        const randomWeather = weatherConditions[Math.floor(Math.random() * weatherConditions.length)]
        const now = new Date()
        const tempVariation = Math.sin((now.getHours() / 24) * Math.PI * 2) * 3
        const adjustedTemp = Math.round(randomWeather.temp + tempVariation)

        weatherData = {
            location: "서울",
            temperature: adjustedTemp,
            humidity: randomWeather.humidity + Math.floor(Math.random() * 10 - 5),
            description: randomWeather.description,
            icon: randomWeather.icon,
            condition: randomWeather.condition,
            windSpeed: Math.floor(Math.random() * 15) + 1,
            pressure: Math.floor(Math.random() * 50) + 1000,
            visibility: Math.floor(Math.random() * 5) + 5,
            uvIndex: Math.floor(Math.random() * 8) + 1,
            sunrise: "06:30",
            sunset: "19:45",
            lastUpdated: new Date().toISOString(),
        }

        updateWeatherDisplay()
    } catch (error) {
        console.error("날씨 정보 로드 오류:", error)
    } finally {
        if (weatherLoading) weatherLoading.classList.add("hidden")
        if (weatherContent) weatherContent.classList.remove("hidden")
    }
}

function updateWeatherDisplay() {
    if (!weatherData) return

    // 기본 정보 업데이트
    const elements = {
        weatherIcon: document.getElementById("weatherIcon"),
        temperature: document.getElementById("temperature"),
        weatherDesc: document.getElementById("weatherDesc"),
        weatherLocation: document.getElementById("weatherLocation"),
        humidity: document.getElementById("humidity"),
        windSpeed: document.getElementById("windSpeed"),
        pressure: document.getElementById("pressure"),
        visibility: document.getElementById("visibility"),
        sunrise: document.getElementById("sunrise"),
        sunset: document.getElementById("sunset"),
        uvIndex: document.getElementById("uvIndex"),
        uvBadge: document.getElementById("uvBadge"),
        weatherVisual: document.getElementById("weatherVisual"),
        weatherUpdated: document.getElementById("weatherUpdated"),
    }

    if (elements.weatherIcon) elements.weatherIcon.textContent = weatherData.icon
    if (elements.temperature) {
        elements.temperature.textContent = `${weatherData.temperature}°C`
        elements.temperature.className = "temperature " + getTemperatureClass(weatherData.temperature)
    }
    if (elements.weatherDesc) elements.weatherDesc.textContent = weatherData.description
    if (elements.weatherLocation) elements.weatherLocation.textContent = weatherData.location
    if (elements.humidity) elements.humidity.textContent = `${weatherData.humidity}%`
    if (elements.windSpeed) elements.windSpeed.textContent = `${weatherData.windSpeed}m/s`
    if (elements.pressure) elements.pressure.textContent = `${weatherData.pressure}hPa`
    if (elements.visibility) elements.visibility.textContent = `${weatherData.visibility}km`
    if (elements.sunrise) elements.sunrise.textContent = weatherData.sunrise
    if (elements.sunset) elements.sunset.textContent = weatherData.sunset
    if (elements.uvIndex) elements.uvIndex.textContent = weatherData.uvIndex

    if (elements.uvBadge) {
        const uvInfo = getUVInfo(weatherData.uvIndex)
        elements.uvBadge.textContent = uvInfo.text
        elements.uvBadge.className = "uv-badge " + uvInfo.class
    }

    if (elements.weatherVisual) {
        elements.weatherVisual.innerHTML = getWeatherIconHTML(weatherData.condition)
    }

    if (elements.weatherUpdated) {
        const updateTime = new Date(weatherData.lastUpdated).toLocaleString("ko-KR")
        elements.weatherUpdated.textContent = `마지막 업데이트: ${updateTime}`
    }
}

function getTemperatureClass(temp) {
    if (temp >= 30) return "temp-hot"
    if (temp >= 20) return "temp-warm"
    if (temp >= 10) return "temp-mild"
    if (temp >= 0) return "temp-cool"
    return "temp-cold"
}

function getWeatherIconHTML(condition) {
    const icons = {
        sunny: '<i class="fas fa-sun"></i>',
        cloudy: '<i class="fas fa-cloud"></i>',
        rainy: '<i class="fas fa-cloud-rain"></i>',
        snowy: '<i class="fas fa-snowflake"></i>',
        "partly-cloudy": '<i class="fas fa-cloud-sun"></i>',
    }
    return icons[condition] || '<i class="fas fa-sun"></i>'
}

function getUVInfo(uvIndex) {
    if (uvIndex <= 2) return { text: "낮음", class: "uv-low" }
    if (uvIndex <= 5) return { text: "보통", class: "uv-moderate" }
    if (uvIndex <= 7) return { text: "높음", class: "uv-high" }
    return { text: "매우 높음", class: "uv-very-high" }
}

function refreshWeather() {
    loadWeatherData()
}

// 게시물 관련 함수
function loadRecentPosts() {
    const recentPosts = posts
        .filter((post) => post.type === "post")
        .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
        .slice(0, 6)

    const postsContainer = document.getElementById("recentPosts")
    if (!postsContainer) return

    if (recentPosts.length === 0) {
        postsContainer.innerHTML = `
      <div style="grid-column: 1 / -1; text-align: center; padding: 3rem; color: #9ca3af;">
        <p>아직 작성된 게시물이 없습니다.</p>
        ${currentUser ? '<p><a href="post.html" style="color: #60a5fa;">첫 게시물을 작성해보세요!</a></p>' : ""}
      </div>
    `
        return
    }

    postsContainer.innerHTML = recentPosts
        .map(
            (post) => `
        <article class="post-card" onclick="location.href='post-detail.html?id=${post.id}'">
          <h3 class="post-title">${escapeHtml(post.title)}</h3>
          <p class="post-date">${formatDate(post.createdAt)}</p>
          <p class="post-content">${escapeHtml(post.content)}</p>
        </article>
      `,
        )
        .join("")
}

// 게시물 CRUD 함수들
function createPost(postData) {
    const newPost = {
        ...postData,
        id: Date.now().toString(),
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString(),
        views: 0,
        likes: 0,
    }

    posts.unshift(newPost)
    localStorage.setItem("blog_posts", JSON.stringify(posts))
    return newPost
}

function updatePost(id, updatedData) {
    const index = posts.findIndex((post) => post.id === id)
    if (index !== -1) {
        posts[index] = { ...posts[index], ...updatedData, updatedAt: new Date().toISOString() }
        localStorage.setItem("blog_posts", JSON.stringify(posts))
        return posts[index]
    }
    return null
}

function deletePost(id) {
    posts = posts.filter((post) => post.id !== id)
    localStorage.setItem("blog_posts", JSON.stringify(posts))

    // 관련 댓글도 삭제
    comments = comments.filter((comment) => comment.postId !== id)
    localStorage.setItem("blog_comments", JSON.stringify(comments))
}

function getPost(id) {
    return posts.find((post) => post.id === id)
}

function incrementViews(id) {
    const post = posts.find((p) => p.id === id)
    if (post) {
        post.views = (post.views || 0) + 1
        localStorage.setItem("blog_posts", JSON.stringify(posts))
    }
}

function toggleLike(id) {
    const post = posts.find((p) => p.id === id)
    if (post) {
        post.likes = (post.likes || 0) + 1
        localStorage.setItem("blog_posts", JSON.stringify(posts))
    }
}

// 댓글 관련 함수들
function addComment(commentData) {
    const newComment = {
        ...commentData,
        id: Date.now().toString(),
        createdAt: new Date().toISOString(),
    }

    comments.push(newComment)
    localStorage.setItem("blog_comments", JSON.stringify(comments))
    return newComment
}

function deleteComment(id) {
    comments = comments.filter((comment) => comment.id !== id)
    localStorage.setItem("blog_comments", JSON.stringify(comments))
}

function getCommentsByPostId(postId) {
    return comments.filter((comment) => comment.postId === postId)
}

// 유틸리티 함수
function formatDate(dateString) {
    return new Date(dateString).toLocaleDateString("ko-KR", {
        year: "numeric",
        month: "short",
        day: "numeric",
    })
}

function formatDateTime(dateString) {
    return new Date(dateString).toLocaleDateString("ko-KR", {
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "2-digit",
        minute: "2-digit",
    })
}

function escapeHtml(text) {
    const div = document.createElement("div")
    div.textContent = text
    return div.innerHTML
}

function getUrlParameter(name) {
    const urlParams = new URLSearchParams(window.location.search)
    return urlParams.get(name)
}

// 모달 외부 클릭 시 닫기
document.addEventListener("click", (event) => {
    if (event.target.classList.contains("modal")) {
        event.target.classList.remove("show")
    }
})

// ESC 키로 모달 닫기
document.addEventListener("keydown", (event) => {
    if (event.key === "Escape") {
        const modals = document.querySelectorAll(".modal.show")
        modals.forEach((modal) => modal.classList.remove("show"))
    }
})

// 페이지 로드 시 인증 확인
function requireAuth() {
    if (!currentUser) {
        alert("로그인이 필요합니다.")
        location.href = "index.html"
        return false
    }
    return true
}

function requireAdmin() {
    if (!isAdmin()) {
        alert("관리자 권한이 필요합니다.")
        location.href = "index.html"
        return false
    }
    return true
}
