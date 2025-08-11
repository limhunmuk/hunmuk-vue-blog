// 공지사항 목록 페이지 전용 스크립트

// Declare variables before using them
const posts = [] // Example declaration, replace with actual import or declaration
const isAdmin = () => false // Example declaration, replace with actual import or declaration
const escapeHtml = (str) => str // Example declaration, replace with actual import or declaration
const formatDate = (date) => date // Example declaration, replace with actual import or declaration
const deletePost = (id) => {} // Example declaration, replace with actual import or declaration

document.addEventListener("DOMContentLoaded", () => {
    loadNoticePage()
})

function loadNoticePage() {
    updateAdminButton()
    loadNoticesList()
}

function updateAdminButton() {
    const adminBtn = document.getElementById("adminBtn")
    if (adminBtn) {
        if (isAdmin()) {
            adminBtn.style.display = "inline-flex"
        } else {
            adminBtn.style.display = "none"
        }
    }
}

function loadNoticesList() {
    const notices = posts
        .filter((post) => post.type === "notice")
        .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))

    const noticesContainer = document.getElementById("noticesList")
    if (!noticesContainer) return

    if (notices.length === 0) {
        noticesContainer.innerHTML = `
      <div class="empty-state">
        <p>등록된 공지사항이 없습니다.</p>
      </div>
    `
        return
    }

    noticesContainer.innerHTML = notices
        .map(
            (notice) => `
    <article class="notice-item">
      <div class="notice-item-header">
        <div style="flex: 1;" onclick="location.href='notice-detail.html?id=${notice.id}'">
          <div style="display: flex; align-items: center; justify-content: space-between; margin-bottom: 0.5rem;">
            <h2 class="notice-title">${escapeHtml(notice.title)}</h2>
            <span class="important-badge">중요</span>
          </div>
        </div>
        ${
                isAdmin()
                    ? `
          <div class="admin-actions">
            <button class="admin-action-btn" onclick="location.href='edit-notice.html?id=${notice.id}'" title="수정">
              <i class="fas fa-edit"></i>
            </button>
            <button class="admin-action-btn delete" onclick="deleteNotice('${notice.id}')" title="삭제">
              <i class="fas fa-trash"></i>
            </button>
          </div>
        `
                    : ""
            }
      </div>
      <div onclick="location.href='notice-detail.html?id=${notice.id}'">
        <p class="notice-date">${formatDate(notice.createdAt)}</p>
        <p class="notice-content">${escapeHtml(notice.content)}</p>
        <div class="notice-stats">
          <div class="notice-stats-left">
            <div class="stat-item">
              <i class="fas fa-eye"></i>
              <span>${notice.views || 0}</span>
            </div>
          </div>
          <div class="stat-item">
            <i class="fas fa-heart"></i>
            <span>${notice.likes || 0}</span>
          </div>
        </div>
      </div>
    </article>
  `,
        )
        .join("")
}

function deleteNotice(noticeId) {
    if (!isAdmin()) {
        alert("관리자만 공지사항을 삭제할 수 있습니다.")
        return
    }

    if (confirm("정말로 이 공지사항을 삭제하시겠습니까?")) {
        deletePost(noticeId)
        loadNoticesList()
    }
}
