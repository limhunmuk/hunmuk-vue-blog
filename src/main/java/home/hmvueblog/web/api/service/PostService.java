package home.hmvueblog.web.api.service;

import home.hmvueblog.domain.post.PostRepository;
import home.hmvueblog.domain.post.dto.PostDetailDto;
import home.hmvueblog.domain.post.dto.PostFormDto;
import home.hmvueblog.domain.post.dto.PostSearchDto;
import home.hmvueblog.domain.post.entity.Post;
import home.hmvueblog.domain.postcomment.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;

    /**
     * 끄적이기 목록 조회
     * @param condition
     * @return
     */
    public Page<?> searchPostList(PostSearchDto condition){

        Page<PostDetailDto> postList = postRepository.searchPostList(condition);

        postList.forEach(post -> {
            post.setCommentCnt(postCommentRepository.countByPostId(post.getPostId()));
        });

        return postList;
    }

    /**
     * 끄적이기 상세 조회
     * @param condition
     * @return
     */
    public PostDetailDto searchPostDetail(PostSearchDto condition){

        PostDetailDto postDetail = postRepository.searchPostDetail(condition);
        if (postDetail != null) {
            postDetail.setCommentCnt(postCommentRepository.countByPostId(postDetail.getPostId()));
        }

        return postDetail;
    }

    /**
     * 끄적이기 등록
     * @param form
     * @return
     */
    public Post savePost(PostFormDto form) {

        Post entity = form.toEntity();
        return postRepository.save(entity);
    }

    /**
     * 끄적이기 수정
     * @param form
     * @return
     */
    @Transactional
    public void updatePost(String postId, PostFormDto form) {

        Post entity = postRepository.findById(Long.valueOf(postId))
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        entity.updatePost(
                form.getTitle(),
                form.getContent(),
                form.getStatusCd()
        );
    }
}
