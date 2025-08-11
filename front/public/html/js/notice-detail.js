// 공지사항 상세 페이지 전용 스크립트

let currentNotice = null
let viewsIncremented = false

// Declare getUrlParameter function
function getUrlParameter(name) {
    name = name.replace(/[[]/, "\\[").replace(/[\]]/, "\\]")
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)")
    var results = regex.exec(location.search)
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "))
}

// Declare getPost function
function getPost(noticeId) {
    // Placeholder for fetching notice details
    return {
        id: noticeId,
        type: "notice",
        title: "Sample Notice",
        authorName: "Admin",
        createdAt: new Date(),
        updatedAt: new Date(),
        views: 0,
        likes: 0,
    }
}

// Declare incrementViews function
function incrementViews(noticeId) {
    // Placeholder for incrementing views
    console.log(`Views incremented for notice ID: ${noticeId}`)
}

// Declare escapeHtml function
function escapeHtml(text) {
    var map = {
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': "&quot;",
        "'": "&#039;",
    }
    return text.replace(/[&<>"']/g, (m) => map[m])
}

// Declare formatDateTime function
function formatDateTime(date) {
    return date.toLocaleString()
}

// Declare isAdmin function
function isAdmin() {
    // Placeholder for checking if user is admin
    return true
}

// Declare toggleLike function
function toggleLike(noticeId) {
    // Placeholder for toggling like
    console.log(`Like toggled for notice ID: ${noticeId}`)
}

// Declare deletePost function
function deletePost(noticeId) {
    // Placeholder for deleting notice
    console.log(`Notice deleted with ID: ${noticeId}`)
}

document.addEventListener("DOMContentLoaded", () => {
    const noticeId = getUrlParameter("id")
    if (!noticeId) {
        alert("잘못된 접근입니다.")
        location.href = "notice.html"
        return
    }

    loadNoticeDetail(noticeId)
})

function loadNoticeDetail(noticeId) {
    currentNotice = getPost(noticeId)

    if (!currentNotice || currentNotice.type !== "notice") {
        showNotFound()
        return
    }

    // 조회수 증가 (한 번만)
    if (!viewsIncremented) {
        incrementViews(noticeId)
        viewsIncremented = true
    }

    displayNotice()
    updateAdminActions()
}

function showNotFound() {
    const container = document.querySelector(".container")
    container.innerHTML = `
    <div class="empty-state">
      <h2>공지사항을 찾을 수 없습니다</h2>
      <p>요청하신 공지사항이 존재하지 않거나 삭제되었습니다.</p>
      <a href="notice.html" class="btn btn-danger">목록으로 돌아가기</a>
    </div>
  `
}

function displayNotice() {
    const noticeDetail = document.getElementById("noticeDetail")
    if (!noticeDetail) return

    noticeDetail.innerHTML = `
    <header>
      <div style="display: flex; align-items: center; justify-content: space-between; margin-bottom: 1rem;">
        <h1>${escapeHtml(currentNotice.title)}</h1>
        <span class="important-badge">중요 공지</span>
      </div>
      <div class="notice-meta">
        <div class="meta-left">
          <span>${escapeHtml(currentNotice.authorName)}</span>
          <span>${formatDateTime(currentNotice.createdAt)}</span>
          ${currentNotice.updatedAt !== currentNotice.createdAt ? "<span>(수정됨)</span>" : ""}
        </div>
        <div class="meta-right">
          <span><i class="fas fa-eye"></i> ${currentNotice.views || 0}</span>
          <span><i class="fas fa-heart"></i> ${currentNotice.likes || 0}</span>
        </div>
      </div>
    </header>
    
    <div class="notice-content-detail">
      ${escapeHtml(currentNotice.content).replace(/\n/g, "<br>")}
    </div>
    
    <footer class="notice-footer">
      <button class="btn btn-outline" onclick="handleLike()">
        <i class="fas fa-heart"></i>
        좋아요 (${currentNotice.likes || 0})
      </button>
    </footer>
  `
}

function updateAdminActions() {
    const adminActions = document.getElementById("adminActions")
    const editBtn = document.getElementById("editNoticeBtn")
    const deleteBtn = document.getElementById("deleteNoticeBtn")

    if (isAdmin()) {
        adminActions.style.display = "flex"

        if (editBtn) {
            editBtn.onclick = () => (location.href = `edit-notice.html?id=${currentNotice.id}`)
        }

        if (deleteBtn) {
            deleteBtn.onclick = handleDelete
        }
    } else {
        adminActions.style.display = "none"
    }
}

function handleLike() {
    if (!currentNotice) return

    toggleLike(currentNotice.id)
    currentNotice.likes = (currentNotice.likes || 0) + 1
    displayNotice()
}

function handleDelete() {
    if (!currentNotice || !isAdmin()) return

    if (confirm("정말로 이 공지사항을 삭제하시겠습니까?")) {
        deletePost(currentNotice.id)
        alert("공지사항이 삭제되었습니다.")
        location.href = "notice.html"
    }
}
