package home.hmvueblog.domain.post;

import home.hmvueblog.domain.post.dto.PostDetailDto;
import home.hmvueblog.domain.post.dto.PostFormDto;
import home.hmvueblog.domain.post.dto.PostSearchDto;
import org.springframework.data.domain.Page;

public interface PostRepositoryCustom {

    Page<PostDetailDto> searchPostList(PostSearchDto condition);

    PostDetailDto searchPostDetail(PostSearchDto condition);

}
