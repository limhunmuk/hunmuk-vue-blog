// 게시물 작성 페이지 전용 스크립트

// Declare variables before using them
const requireAuth = () => true // Placeholder function, replace with actual implementation
const currentUser = { id: 1, nickname: "user1" } // Placeholder object, replace with actual implementation
const createPost = (post) => console.log(post) // Placeholder function, replace with actual implementation

document.addEventListener("DOMContentLoaded", () => {
    if (!requireAuth()) return

    const form = document.getElementById("writePostForm")
    if (form) {
        form.addEventListener("submit", handleSubmit)
    }
})

async function handleSubmit(event) {
    event.preventDefault()

    const title = document.getElementById("postTitle").value.trim()
    const content = document.getElementById("postContent").value.trim()

    if (!title || !content) {
        alert("제목과 내용을 모두 입력해주세요.")
        return
    }

    if (!currentUser) {
        alert("로그인이 필요합니다.")
        return
    }

    // 버튼 비활성화
    const submitBtn = event.target.querySelector('button[type="submit"]')
    const originalText = submitBtn.innerHTML
    submitBtn.disabled = true
    submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> 저장 중...'

    try {
        // 시뮬레이션을 위한 지연
        await new Promise((resolve) => setTimeout(resolve, 1000))

        createPost({
            title,
            content,
            authorId: currentUser.id,
            authorName: currentUser.nickname,
            type: "post",
        })

        alert("게시물이 성공적으로 작성되었습니다!")
        location.href = "post.html"
    } catch (error) {
        console.error("게시물 작성 오류:", error)
        alert("게시물 작성 중 오류가 발생했습니다.")
    } finally {
        submitBtn.disabled = false
        submitBtn.innerHTML = originalText
    }
}
