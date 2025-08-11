package home.hmvueblog.domain.member;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import home.hmvueblog.domain.member.dto.MemberDetailDto;
import home.hmvueblog.domain.member.dto.MemberSearchDto;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static home.hmvueblog.domain.member.entity.QMember.member;


@Repository
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 회원 목록 조회
     * @param condition
     * @return
     */
    @Override
    public Page<MemberDetailDto> searchMemberList(MemberSearchDto condition) {

        List<MemberDetailDto> list = queryFactory
                .select(Projections.fields(
                        MemberDetailDto.class,
                        member.id,
                        member.loginId,
                        member.name,
                        member.regDt,
                        member.regId
                ))
                .from(member)
                //.where(member.loginId.isNotEmpty())
                .fetch();

        Long totCount = queryFactory.select(
                        member.count())
                        .from(member)
                        .fetchOne();


        return PageableExecutionUtils.getPage(list, condition.toPageable(), () -> totCount);
    }

    /**
     * 회원 상세 조회
     * @param memberSearch
     * @return
     */
    @Override
    public MemberDetailDto searchMemberDetail(MemberSearchDto memberSearch) {

        return queryFactory
                .select(Projections.fields(
                        MemberDetailDto.class,
                        member.id,
                        member.loginId,
                        member.name,
                        member.nickName,
                        member.memType,
                        member.joinDt,
                        member.phoneNo,
                        member.addr,
                        member.addrDtl,
                        member.regDt,
                        member.regId
                ))
                .from(member)
                .where(
                        member.id.eq(memberSearch.getId())
                )
                .fetchFirst();
    }


}
