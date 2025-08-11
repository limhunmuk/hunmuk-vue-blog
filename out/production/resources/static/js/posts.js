// 게시물 목록 페이지 전용 스크립트

const currentUser = null // Declare currentUser variable
const posts = [] // Declare posts variable
const comments = [] // Declare comments variable

function escapeHtml(unsafe) {
    return unsafe
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;")
}

function formatDate(dateString) {
    const date = new Date(dateString)
    return date.toLocaleDateString()
}

document.addEventListener("DOMContentLoaded", () => {
    loadPostsPage()
})

function loadPostsPage() {
    updateWriteButton()
    loadPostsList()
}

function updateWriteButton() {
    const writeBtn = document.getElementById("writeBtn")
    if (writeBtn) {
        if (currentUser) {
            writeBtn.style.display = "inline-flex"
        } else {
            writeBtn.style.display = "none"
        }
    }
}

function loadPostsList() {
    const regularPosts = posts
        .filter((post) => post.type === "post")
        .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))

    const postsContainer = document.getElementById("postsList")
    if (!postsContainer) return

    if (regularPosts.length === 0) {
        postsContainer.innerHTML = `
      <div class="empty-state">
        <p>아직 작성된 게시물이 없습니다.</p>
        ${currentUser ? '<p><a href="write-post.html" class="btn btn-primary" style="margin-top: 1rem;">첫 게시물 작성하기</a></p>' : ""}
      </div>
    `
        return
    }

    postsContainer.innerHTML = regularPosts
        .map((post) => {
            const postComments = comments.filter((comment) => comment.postId === post.id)
            return `
      <article class="post-item" onclick="location.href='post-detail.html?id=${post.id}'">
        <div class="post-item-header">
          <h2 class="post-item-title">${escapeHtml(post.title)}</h2>
          <div class="post-item-meta">
            <div>${escapeHtml(post.authorName)}</div>
            <div>${formatDate(post.createdAt)}</div>
          </div>
        </div>
        <p class="post-item-content">${escapeHtml(post.content)}</p>
        <div class="post-item-stats">
          <div class="post-stats-left">
            <div class="stat-item">
              <i class="fas fa-eye"></i>
              <span>${post.views || 0}</span>
            </div>
            <div class="stat-item">
              <i class="fas fa-comment"></i>
              <span>${postComments.length}</span>
            </div>
          </div>
          <div class="stat-item">
            <i class="fas fa-heart"></i>
            <span>${post.likes || 0}</span>
          </div>
        </div>
      </article>
    `
        })
        .join("")
}
