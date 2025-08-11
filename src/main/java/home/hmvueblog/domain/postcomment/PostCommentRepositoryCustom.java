package home.hmvueblog.domain.postcomment;

import home.hmvueblog.domain.postcomment.dto.PostCommentDetailDto;
import home.hmvueblog.domain.postcomment.dto.PostCommentSearchDto;
import org.springframework.data.domain.Page;

public interface PostCommentRepositoryCustom {

    Page<PostCommentDetailDto> searchPostCommentList(PostCommentSearchDto condition);

    PostCommentDetailDto searchPostCommentDetail(PostCommentSearchDto condition);
}
