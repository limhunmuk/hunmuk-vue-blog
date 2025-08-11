package home.hmvueblog.domain.member;

import home.hmvueblog.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>,
                                            QuerydslPredicateExecutor<Member>, MemberRepositoryCustom {

    @Query("SELECT a FROM Member a WHERE a.loginId = :loginId and a.delYn = 'N'")
    Optional<Member> findByLoginId(@Param("loginId") String loginId);
}
