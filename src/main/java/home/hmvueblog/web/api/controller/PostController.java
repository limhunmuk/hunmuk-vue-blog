package home.hmvueblog.web.api.controller;

import home.hmvueblog.domain.notice.dto.NoticeDetailDto;
import home.hmvueblog.domain.notice.dto.NoticeSearchDto;
import home.hmvueblog.domain.post.PostRepository;
import home.hmvueblog.domain.post.dto.PostDetailDto;
import home.hmvueblog.domain.post.dto.PostFormDto;
import home.hmvueblog.domain.post.dto.PostSearchDto;
import home.hmvueblog.domain.post.entity.Post;
import home.hmvueblog.web.api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author hunmuk
 */
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 끄적이기 목록 조회
     *
     * @param condition
     * @return
     */
    @GetMapping("/api/post")
    @ResponseBody
    public ResponseEntity<?> getPost(PostSearchDto condition) {

        Page<?> postList = postService.searchPostList(condition);

        if(postList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(postList);
    }

    /**
     * 끄적이기 상세 조회
     *
     * @param id
     * @param condition
     * @return
     */
    @GetMapping("/api/post/{id}")
    @ResponseBody
    public ResponseEntity<?> getPostDetail(@PathVariable("id") String id, PostSearchDto condition) {

        condition.setId(Long.valueOf(id));
        PostDetailDto postDetail = postService.searchPostDetail(condition);

        if(postDetail == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(postDetail);
    }

    /**
     * 끄적이기 등록
     *
     * @param form
     * @return
     */
    @PostMapping("/api/post")
    @ResponseBody
    public ResponseEntity<?> createPost(@RequestBody @Validated PostFormDto form) {

        Post post = postService.savePost(form);

        if(post == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(post.getId());
    }

    /**
     * 끄적이기 수정
     *
     * @param id
     * @param form
     * @return
     */
    @PutMapping("/api/post/{id}")
    @ResponseBody
    public ResponseEntity<?> updatePost(@PathVariable("id") String id, @RequestBody @Validated PostFormDto form) {

        try {
            postService.updatePost(id, form);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(id);
    }


}
