// 게시물 상세 페이지 전용 스크립트

let currentPost = null
let viewsIncremented = false
const currentUser = null // Declare currentUser variable

function getUrlParameter(name) {
    // Dummy implementation for getUrlParameter
    return new URLSearchParams(window.location.search).get(name)
}

function getPost(postId) {
    // Dummy implementation for getPost
    return {
        id: postId,
        type: "post",
        title: "Sample Title",
        authorName: "Author",
        authorId: "1",
        createdAt: new Date(),
        updatedAt: new Date(),
        views: 0,
        likes: 0,
        content: "Sample content",
    }
}

function incrementViews(postId) {
    // Dummy implementation for incrementViews
    console.log(`Incrementing views for post ${postId}`)
}

function escapeHtml(text) {
    // Dummy implementation for escapeHtml
    return text
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;")
}

function formatDateTime(date) {
    // Dummy implementation for formatDateTime
    return date.toLocaleString()
}

function getCommentsByPostId(postId) {
    // Dummy implementation for getCommentsByPostId
    return []
}

function addComment(comment) {
    // Dummy implementation for addComment
    console.log(`Adding comment: ${comment.content}`)
}

function deleteComment(commentId) {
    // Dummy implementation for deleteComment
    console.log(`Deleting comment with id: ${commentId}`)
}

function toggleLike(postId) {
    // Dummy implementation for toggleLike
    console.log(`Toggling like for post ${postId}`)
}

function deletePost(postId) {
    // Dummy implementation for deletePost
    console.log(`Deleting post with id: ${postId}`)
}

document.addEventListener("DOMContentLoaded", () => {
    const postId = getUrlParameter("id")
    if (!postId) {
        alert("잘못된 접근입니다.")
        location.href = ""
        return
    }

    loadPostDetail(postId)
})

function loadPostDetail(postId) {
    currentPost = getPost(postId)

    if (!currentPost || currentPost.type !== "post") {
        showNotFound()
        return
    }

    // 조회수 증가 (한 번만)
    if (!viewsIncremented) {
        incrementViews(postId)
        viewsIncremented = true
    }

    displayPost()
    updatePostActions()
    loadComments()
    updateCommentForm()
}

function showNotFound() {
    const container = document.querySelector(".container")
    container.innerHTML = `
    <div class="empty-state">
      <h2>게시물을 찾을 수 없습니다</h2>
      <p>요청하신 게시물이 존재하지 않거나 삭제되었습니다.</p>
      <a href="post.html" class="btn btn-primary">목록으로 돌아가기</a>
    </div>
  `
}

function displayPost() {
    const postDetail = document.getElementById("postDetail")
    if (!postDetail) return

    postDetail.innerHTML = `
    <header>
      <h1>${escapeHtml(currentPost.title)}</h1>
      <div class="post-meta">
        <div class="meta-left">
          <span>${escapeHtml(currentPost.authorName)}</span>
          <span>${formatDateTime(currentPost.createdAt)}</span>
          ${currentPost.updatedAt !== currentPost.createdAt ? "<span>(수정됨)</span>" : ""}
        </div>
        <div class="meta-right">
          <span><i class="fas fa-eye"></i> ${currentPost.views || 0}</span>
          <span><i class="fas fa-heart"></i> ${currentPost.likes || 0}</span>
        </div>
      </div>
    </header>
    
    <div class="post-content-detail">
      ${escapeHtml(currentPost.content).replace(/\n/g, "<br>")}
    </div>
    
    <footer class="post-footer">
      <button class="btn btn-outline" onclick="handleLike()">
        <i class="fas fa-heart"></i>
        좋아요 (${currentPost.likes || 0})
      </button>
    </footer>
  `
}

function updatePostActions() {
    const postActions = document.getElementById("postActions")
    const editBtn = document.getElementById("editBtn")
    const deleteBtn = document.getElementById("deleteBtn")

    if (currentUser && currentUser.id === currentPost.authorId) {
        postActions.style.display = "flex"

        if (editBtn) {
            editBtn.onclick = () => (location.href = `edit-post.html?id=${currentPost.id}`)
        }

        if (deleteBtn) {
            deleteBtn.onclick = handleDelete
        }
    } else {
        postActions.style.display = "none"
    }
}

function updateCommentForm() {
    const commentForm = document.getElementById("commentForm")
    const loginRequired = document.getElementById("loginRequired")

    if (currentUser) {
        commentForm.style.display = "block"
        loginRequired.style.display = "none"

        const form = document.getElementById("addCommentForm")
        if (form) {
            form.addEventListener("submit", handleAddComment)
        }
    } else {
        commentForm.style.display = "none"
        loginRequired.style.display = "block"
    }
}

function loadComments() {
    const postComments = getCommentsByPostId(currentPost.id)
    const commentCount = document.getElementById("commentCount")
    const commentsList = document.getElementById("commentsList")

    if (commentCount) {
        commentCount.textContent = postComments.length
    }

    if (!commentsList) return

    if (postComments.length === 0) {
        commentsList.innerHTML = `
      <div class="empty-state">
        <p>아직 댓글이 없습니다. 첫 댓글을 작성해보세요!</p>
      </div>
    `
        return
    }

    commentsList.innerHTML = postComments
        .map(
            (comment) => `
    <div class="comment-item">
      <div class="comment-header">
        <div>
          <span class="comment-author">${escapeHtml(comment.authorName)}</span>
          <span class="comment-date">${formatDateTime(comment.createdAt)}</span>
        </div>
        ${
                currentUser && currentUser.id === comment.authorId
                    ? `
          <div class="comment-actions">
            <button class="btn btn-ghost" onclick="handleDeleteComment('${comment.id}')">
              <i class="fas fa-trash"></i>
            </button>
          </div>
        `
                    : ""
            }
      </div>
      <div class="comment-content">${escapeHtml(comment.content).replace(/\n/g, "<br>")}</div>
    </div>
  `,
        )
        .join("")
}

async function handleAddComment(event) {
    event.preventDefault()

    const content = document.getElementById("commentContent").value.trim()

    if (!content) {
        alert("댓글 내용을 입력해주세요.")
        return
    }

    if (!currentUser) {
        alert("로그인이 필요합니다.")
        return
    }

    const submitBtn = event.target.querySelector('button[type="submit"]')
    const originalText = submitBtn.innerHTML
    submitBtn.disabled = true
    submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> 등록 중...'

    try {
        await new Promise((resolve) => setTimeout(resolve, 500))

        addComment({
            postId: currentPost.id,
            authorId: currentUser.id,
            authorName: currentUser.nickname,
            content,
        })

        document.getElementById("commentContent").value = ""
        loadComments()
    } catch (error) {
        console.error("댓글 작성 오류:", error)
        alert("댓글 작성 중 오류가 발생했습니다.")
    } finally {
        submitBtn.disabled = false
        submitBtn.innerHTML = originalText
    }
}

function handleDeleteComment(commentId) {
    if (confirm("댓글을 삭제하시겠습니까?")) {
        deleteComment(commentId)
        loadComments()
    }
}

function handleLike() {
    if (!currentPost) return

    toggleLike(currentPost.id)
    currentPost.likes = (currentPost.likes || 0) + 1
    displayPost()
}

function handleDelete() {
    if (!currentPost || !currentUser) return

    if (currentPost.authorId !== currentUser.id) {
        alert("삭제 권한이 없습니다.")
        return
    }

    if (confirm("정말로 이 게시물을 삭제하시겠습니까?")) {
        deletePost(currentPost.id)
        alert("게시물이 삭제되었습니다.")
        location.href = "post.html"
    }
}
