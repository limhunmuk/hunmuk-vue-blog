// 프로필 페이지 전용 스크립트

// Declare variables before using them
const requireAuth = () => true // Placeholder for requireAuth function
const currentUser = {
    id: 1,
    nickname: "John Doe",
    email: "john@example.com",
    createdAt: "2023-01-01",
    avatar: "",
    provider: "email",
    bio: "",
} // Placeholder for currentUser object
const posts = [] // Placeholder for posts array
const comments = [] // Placeholder for comments array
const formatDate = (date) => new Date(date).toLocaleDateString() // Placeholder for formatDate function
const escapeHtml = (text) => text // Placeholder for escapeHtml function
let updateUserInterface = () => {} // Placeholder for updateUserInterface function

document.addEventListener("DOMContentLoaded", () => {
    if (!requireAuth()) return

    loadProfilePage()
})

function loadProfilePage() {
    displayProfileCard()
    loadRecentActivity()
    loadMyPosts()
}

function displayProfileCard() {
    const profileCard = document.getElementById("profileCard")
    if (!profileCard || !currentUser) return

    const joinDate = formatDate(currentUser.createdAt)
    const userPosts = posts.filter((post) => post.authorId === currentUser.id)
    const userComments = comments.filter((comment) => comment.authorId === currentUser.id)
    const totalLikes = userPosts.reduce((sum, post) => sum + (post.likes || 0), 0)

    profileCard.innerHTML = `
        <div class="profile-avatar-large">
            ${
        currentUser.avatar
            ? `<img src="${currentUser.avatar}" alt="${currentUser.nickname}">`
            : '<i class="fas fa-user"></i>'
    }
            ${
        currentUser.provider && currentUser.provider !== "email"
            ? `<span class="provider-badge">${currentUser.provider === "google" ? "Google" : "GitHub"}</span>`
            : ""
    }
        </div>
        
        <div class="profile-name">${escapeHtml(currentUser.nickname)}</div>
        
        <div class="profile-email">
            <i class="fas fa-envelope"></i>
            ${escapeHtml(currentUser.email)}
        </div>
        
        ${
        currentUser.bio
            ? `<div class="profile-bio">"${escapeHtml(currentUser.bio)}"</div>`
            : '<div class="profile-bio" style="color: #6b7280;">아직 자기소개가 없습니다.</div>'
    }
        
        <div class="profile-join-date">
            <i class="fas fa-calendar"></i>
            ${joinDate} 가입
        </div>
        
        <div class="profile-stats">
            <div class="stat-box">
                <span class="stat-number">${userPosts.length}</span>
                <div class="stat-label">게시물</div>
            </div>
            <div class="stat-box">
                <span class="stat-number">${userComments.length}</span>
                <div class="stat-label">댓글</div>
            </div>
            <div class="stat-box">
                <span class="stat-number">${totalLikes}</span>
                <div class="stat-label">좋아요</div>
            </div>
        </div>
    `
}

function loadRecentActivity() {
    const activityContainer = document.getElementById("recentActivity")
    if (!activityContainer || !currentUser) return

    // 최근 활동 생성 (게시물 작성, 댓글 작성 등)
    const userPosts = posts
        .filter((post) => post.authorId === currentUser.id)
        .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
        .slice(0, 3)

    const userComments = comments
        .filter((comment) => comment.authorId === currentUser.id)
        .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
        .slice(0, 2)

    const activities = []

    // 게시물 활동 추가
    userPosts.forEach((post) => {
        activities.push({
            type: "post",
            text: `새 게시물을 작성했습니다: "${post.title}"`,
            date: post.createdAt,
            link: `post-detail.html?id=${post.id}`,
        })
    })

    // 댓글 활동 추가
    userComments.forEach((comment) => {
        const post = posts.find((p) => p.id === comment.postId)
        if (post) {
            activities.push({
                type: "comment",
                text: `"${post.title}"에 댓글을 작성했습니다`,
                date: comment.createdAt,
                link: `post-detail.html?id=${post.id}`,
            })
        }
    })

    // 날짜순 정렬
    activities.sort((a, b) => new Date(b.date) - new Date(a.date))

    if (activities.length === 0) {
        activityContainer.innerHTML = `
            <div class="empty-state" style="padding: 2rem; text-align: center;">
                <p>아직 활동 내역이 없습니다.</p>
                <p><a href="write-post.html" style="color: #60a5fa;">첫 게시물을 작성해보세요!</a></p>
            </div>
        `
        return
    }

    activityContainer.innerHTML = activities
        .slice(0, 5)
        .map(
            (activity) => `
        <div class="activity-item" onclick="location.href='${activity.link}'">
            <div class="activity-icon"></div>
            <div class="activity-content">
                <div class="activity-text">${activity.text}</div>
                <div class="activity-date">${formatDate(activity.date)}</div>
            </div>
        </div>
    `,
        )
        .join("")
}

function loadMyPosts() {
    const postsContainer = document.getElementById("myPosts")
    if (!postsContainer || !currentUser) return

    const userPosts = posts
        .filter((post) => post.authorId === currentUser.id)
        .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
        .slice(0, 5)

    if (userPosts.length === 0) {
        postsContainer.innerHTML = `
            <div class="empty-state" style="padding: 2rem; text-align: center;">
                <p>아직 작성한 게시물이 없습니다.</p>
                <p><a href="write-post.html" style="color: #60a5fa;">첫 게시물을 작성해보세요!</a></p>
            </div>
        `
        return
    }

    postsContainer.innerHTML = userPosts
        .map((post) => {
            const postComments = comments.filter((comment) => comment.postId === post.id)
            const detailUrl = post.type === "notice" ? `notice-detail.html?id=${post.id}` : `post-detail.html?id=${post.id}`

            return `
            <div class="post-item-small" onclick="location.href='${detailUrl}'">
                <div class="post-title-small">${escapeHtml(post.title)}</div>
                <div class="post-meta-small">
                    <span>${formatDate(post.createdAt)}</span>
                    <span><i class="fas fa-eye"></i> ${post.views || 0}</span>
                    <span><i class="fas fa-comment"></i> ${postComments.length}</span>
                    <span><i class="fas fa-heart"></i> ${post.likes || 0}</span>
                </div>
            </div>
        `
        })
        .join("")
}

// 프로필 페이지에서 로그인 상태 변경 시 페이지 새로고침
const originalUpdateUserInterface = updateUserInterface
updateUserInterface = () => {
    originalUpdateUserInterface()
    if (window.location.pathname.includes("profile.html")) {
        if (currentUser) {
            loadProfilePage()
        } else {
            location.href = "index.html"
        }
    }
}
