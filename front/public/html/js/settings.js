// 설정 페이지 전용 스크립트

let userSettings = {
    allowComments: true,
    notifications: true,
    publicBlog: true,
    publicProfile: true,
    showEmail: false,
    showOnlineStatus: true,
    blogTitle: "내 블로그",
}

let currentUser = {} // Declare currentUser variable
const posts = [] // Declare posts variable
const comments = [] // Declare comments variable

function requireAuth() {
    // Placeholder for requireAuth function
    return true // Assume user is authenticated for demonstration
}

function logout() {
    // Placeholder for logout function
    currentUser = {} // Clear current user for demonstration
}

document.addEventListener("DOMContentLoaded", () => {
    if (!requireAuth()) return

    loadSettingsPage()
})

function loadSettingsPage() {
    loadUserSettings()
    setupEventListeners()
}

function loadUserSettings() {
    if (!currentUser) return

    // 저장된 설정 불러오기
    const savedSettings = localStorage.getItem(`settings_${currentUser.id}`)
    if (savedSettings) {
        try {
            userSettings = { ...userSettings, ...JSON.parse(savedSettings) }
        } catch (error) {
            console.error("설정 로드 오류:", error)
        }
    }

    // 폼에 사용자 정보 및 설정 적용
    document.getElementById("nickname").value = currentUser.nickname || ""
    document.getElementById("email").value = currentUser.email || ""
    document.getElementById("bio").value = currentUser.bio || ""
    document.getElementById("blogTitle").value = userSettings.blogTitle || "내 블로그"

    // 스위치 설정 적용
    document.getElementById("allowComments").checked = userSettings.allowComments
    document.getElementById("notifications").checked = userSettings.notifications
    document.getElementById("publicBlog").checked = userSettings.publicBlog
    document.getElementById("publicProfile").checked = userSettings.publicProfile
    document.getElementById("showEmail").checked = userSettings.showEmail
    document.getElementById("showOnlineStatus").checked = userSettings.showOnlineStatus
}

function setupEventListeners() {
    const form = document.getElementById("settingsForm")
    if (form) {
        form.addEventListener("submit", handleSaveSettings)
    }
}

async function handleSaveSettings(event) {
    event.preventDefault()

    if (!currentUser) {
        alert("로그인이 필요합니다.")
        return
    }

    const submitBtn = event.target.querySelector('button[type="submit"]')
    const originalText = submitBtn.innerHTML
    submitBtn.disabled = true
    submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> 저장 중...'

    try {
        // 시뮬레이션을 위한 지연
        await new Promise((resolve) => setTimeout(resolve, 1000))

        // 프로필 정보 업데이트
        const nickname = document.getElementById("nickname").value.trim()
        const bio = document.getElementById("bio").value.trim()

        if (nickname && nickname !== currentUser.nickname) {
            // 닉네임 중복 확인
            const users = JSON.parse(localStorage.getItem("blog_users") || "[]")
            const existingUser = users.find((u) => u.nickname === nickname && u.id !== currentUser.id)

            if (existingUser) {
                alert("이미 사용 중인 닉네임입니다.")
                return
            }

            // 사용자 정보 업데이트
            currentUser.nickname = nickname
            currentUser.bio = bio

            // localStorage 업데이트
            localStorage.setItem("blog_user", JSON.stringify(currentUser))

            // 사용자 목록 업데이트
            const userIndex = users.findIndex((u) => u.id === currentUser.id)
            if (userIndex !== -1) {
                users[userIndex] = { ...users[userIndex], nickname, bio }
                localStorage.setItem("blog_users", JSON.stringify(users))
            }

            // 게시물과 댓글의 작성자 이름 업데이트
            updateAuthorNames(currentUser.id, nickname)
        }

        // 설정 정보 업데이트
        userSettings = {
            allowComments: document.getElementById("allowComments").checked,
            notifications: document.getElementById("notifications").checked,
            publicBlog: document.getElementById("publicBlog").checked,
            publicProfile: document.getElementById("publicProfile").checked,
            showEmail: document.getElementById("showEmail").checked,
            showOnlineStatus: document.getElementById("showOnlineStatus").checked,
            blogTitle: document.getElementById("blogTitle").value.trim() || "내 블로그",
        }

        // 설정 저장
        localStorage.setItem(`settings_${currentUser.id}`, JSON.stringify(userSettings))

        // UI 업데이트
        updateUserInterface()

        alert("설정이 성공적으로 저장되었습니다!")
    } catch (error) {
        console.error("설정 저장 오류:", error)
        alert("설정 저장 중 오류가 발생했습니다.")
    } finally {
        submitBtn.disabled = false
        submitBtn.innerHTML = originalText
    }
}

function updateAuthorNames(userId, newNickname) {
    // 게시물 작성자 이름 업데이트
    const updatedPosts = posts.map((post) => {
        if (post.authorId === userId) {
            return { ...post, authorName: newNickname }
        }
        return post
    })
    localStorage.setItem("blog_posts", JSON.stringify(updatedPosts))

    // 댓글 작성자 이름 업데이트
    const updatedComments = comments.map((comment) => {
        if (comment.authorId === userId) {
            return { ...comment, authorName: newNickname }
        }
        return comment
    })
    localStorage.setItem("blog_comments", JSON.stringify(updatedComments))

    // 전역 변수 업데이트
    posts.splice(0, posts.length, ...updatedPosts)
    comments.splice(0, comments.length, ...updatedComments)
}

function resetSettings() {
    if (confirm("모든 설정을 초기값으로 되돌리시겠습니까?")) {
        // 기본 설정으로 리셋
        userSettings = {
            allowComments: true,
            notifications: true,
            publicBlog: true,
            publicProfile: true,
            showEmail: false,
            showOnlineStatus: true,
            blogTitle: "내 블로그",
        }

        // 폼 리셋
        loadUserSettings()

        alert("설정이 초기값으로 되돌려졌습니다.")
    }
}

function deleteAllPosts() {
    if (!currentUser) return

    const userPosts = posts.filter((post) => post.authorId === currentUser.id)

    if (userPosts.length === 0) {
        alert("삭제할 게시물이 없습니다.")
        return
    }

    const confirmText = `정말로 모든 게시물(${userPosts.length}개)을 삭제하시겠습니까?\n이 작업은 되돌릴 수 없습니다.`

    if (confirm(confirmText)) {
        const secondConfirm = prompt('삭제를 확인하려면 "삭제"라고 입력하세요:')

        if (secondConfirm === "삭제") {
            // 사용자의 모든 게시물 삭제
            const remainingPosts = posts.filter((post) => post.authorId !== currentUser.id)
            localStorage.setItem("blog_posts", JSON.stringify(remainingPosts))

            // 사용자의 모든 댓글 삭제
            const remainingComments = comments.filter((comment) => comment.authorId !== currentUser.id)
            localStorage.setItem("blog_comments", JSON.stringify(remainingComments))

            // 전역 변수 업데이트
            posts.splice(0, posts.length, ...remainingPosts)
            comments.splice(0, comments.length, ...remainingComments)

            alert("모든 게시물이 삭제되었습니다.")
        } else {
            alert("삭제가 취소되었습니다.")
        }
    }
}

function deleteAccount() {
    if (!currentUser) return

    const confirmText = "정말로 계정을 삭제하시겠습니까?\n모든 데이터가 영구적으로 삭제되며 복구할 수 없습니다."

    if (confirm(confirmText)) {
        const secondConfirm = prompt('계정 삭제를 확인하려면 "계정삭제"라고 입력하세요:')

        if (secondConfirm === "계정삭제") {
            // 사용자의 모든 게시물 삭제
            const remainingPosts = posts.filter((post) => post.authorId !== currentUser.id)
            localStorage.setItem("blog_posts", JSON.stringify(remainingPosts))

            // 사용자의 모든 댓글 삭제
            const remainingComments = comments.filter((comment) => comment.authorId !== currentUser.id)
            localStorage.setItem("blog_comments", JSON.stringify(remainingComments))

            // 사용자 계정 삭제
            const users = JSON.parse(localStorage.getItem("blog_users") || "[]")
            const remainingUsers = users.filter((user) => user.id !== currentUser.id)
            localStorage.setItem("blog_users", JSON.stringify(remainingUsers))

            // 사용자 설정 삭제
            localStorage.removeItem(`settings_${currentUser.id}`)

            // 로그아웃
            logout()

            alert("계정이 성공적으로 삭제되었습니다.")
            location.href = "index.html"
        } else {
            alert("계정 삭제가 취소되었습니다.")
        }
    }
}

function updateUserInterface() {
    // Placeholder for updateUserInterface function
}

// 설정 페이지에서 로그인 상태 변경 시 페이지 새로고침
const originalUpdateUserInterface = updateUserInterface
updateUserInterface = () => {
    originalUpdateUserInterface()
    if (window.location.pathname.includes("settings.html")) {
        if (currentUser) {
            loadUserSettings()
        } else {
            location.href = "index.html"
        }
    }
}
