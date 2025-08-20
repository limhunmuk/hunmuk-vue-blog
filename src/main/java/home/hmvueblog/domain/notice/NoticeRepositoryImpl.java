package home.hmvueblog.domain.notice;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import home.hmvueblog.domain.common.enums.YNCode;
import home.hmvueblog.domain.notice.dto.NoticeDetailDto;
import home.hmvueblog.domain.notice.dto.NoticeSearchDto;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static home.hmvueblog.domain.member.entity.QMember.member;
import static home.hmvueblog.domain.notice.entity.QNotice.notice;


@Repository
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public NoticeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<NoticeDetailDto> searchNoticeList(NoticeSearchDto condition) {
        List<NoticeDetailDto> list = queryFactory
                .select(Projections.fields(
                        NoticeDetailDto.class,
                        notice.id.as("noticeId"),
                        notice.title,
                        notice.content,
                        notice.viewCnt,
                        notice.likeCnt,
                        notice.regDt,
                        notice.regId,
                        notice.delYn,
                        notice.importantYn,
                        member.id.as("memId"),
                        member.nickName,
                        member.loginId
                ))
                .from(notice)
                .leftJoin(member)
                .on(notice.regId.eq(member.id.stringValue()))
                .where(
                    notice.delYn.eq(YNCode.N)
                )
                .orderBy(notice.id.desc())
                .fetch();

        Long totCount = queryFactory
                    .select(
                        notice.count()
                    )
                    .from(notice)
                    .where(
                        notice.delYn.eq(YNCode.N)
                    )
                    .fetchOne();

        return PageableExecutionUtils.getPage(list, condition.toPageable(), () -> totCount);
    }

    @Override
    public NoticeDetailDto searchNoticeDetail(NoticeSearchDto condition) {
        return queryFactory
                .select(Projections.fields(
                        NoticeDetailDto.class,
                        notice.id.as("noticeId"),
                        notice.title,
                        notice.content,
                        notice.viewCnt,
                        notice.likeCnt,
                        notice.regDt,
                        notice.regId,
                        member.id.as("memId"),
                        member.nickName
                ))
                .from(notice)
                .leftJoin(member)
                .on(notice.regId.eq(member.id.stringValue()))
                .where(
                        notice.id.eq(condition.getId()),
                        notice.delYn.eq(YNCode.N)
                )
                .fetchOne();
    }
}
