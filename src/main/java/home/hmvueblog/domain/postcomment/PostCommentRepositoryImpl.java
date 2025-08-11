package home.hmvueblog.domain.postcomment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import home.hmvueblog.domain.postcomment.dto.PostCommentDetailDto;
import home.hmvueblog.domain.postcomment.dto.PostCommentSearchDto;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;


@Repository
public class PostCommentRepositoryImpl implements PostCommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostCommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<PostCommentDetailDto> searchPostCommentList(PostCommentSearchDto condition) {
        return null;
    }

    @Override
    public PostCommentDetailDto searchPostCommentDetail(PostCommentSearchDto condition) {
        return null;
    }
}
