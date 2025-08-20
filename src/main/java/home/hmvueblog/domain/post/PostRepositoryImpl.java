package home.hmvueblog.domain.post;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import home.hmvueblog.domain.common.enums.YNCode;
import home.hmvueblog.domain.post.dto.PostDetailDto;
import home.hmvueblog.domain.post.dto.PostSearchDto;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static home.hmvueblog.domain.member.entity.QMember.member;
import static home.hmvueblog.domain.post.entity.QPost.post;

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    /**
     * 끄적이기 목록 조회
     * @param condition
     * @return
     */
    @Override
    public Page<PostDetailDto> searchPostList(PostSearchDto condition) {
        List<PostDetailDto> list = queryFactory
                .select(Projections.fields(
                        PostDetailDto.class,
                        post.id.as("postId"),
                        post.title,
                        post.content,
                        post.viewCnt,
                        post.likeCnt,
                        post.regDt,
                        post.regId,
                        member.id,
                        member.nickName
                ))
                .from(post)
                .leftJoin(member)
                .on(post.regId.eq(member.id.stringValue()))
                .where(
                    post.delYn.eq(YNCode.N)
                )
                .orderBy(post.id.desc())
                .fetch();

        Long totCount = queryFactory
                    .select(
                        post.count()
                    )
                    .from(post)
                    .where(
                        post.delYn.eq(YNCode.N)
                    )
                    .fetchOne();

        return PageableExecutionUtils.getPage(list, condition.toPageable(), () -> totCount);
    }

    /**
     * 끄적이기 상세 조회
     * @param condition
     * @return
     */
    @Override
    public PostDetailDto searchPostDetail(PostSearchDto condition) {
        return queryFactory
                .select(Projections.fields(
                        PostDetailDto.class,
                        post.id,
                        post.title,
                        post.content,
                        post.viewCnt,
                        post.likeCnt,
                        post.regDt,
                        post.regId,
                        member.id,
                        member.nickName
                ))
                .from(post)
                .leftJoin(member)
                .on(post.regId.eq(member.id.stringValue()))
                .where(
                        post.id.eq(condition.getId()),
                        post.delYn.eq(YNCode.N)
                )
                .fetchOne();
    }
}
