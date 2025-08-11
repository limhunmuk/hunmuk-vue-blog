package home.hmvueblog.domain.member;

import home.hmvueblog.domain.member.dto.MemberDetailDto;
import home.hmvueblog.domain.member.dto.MemberSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {

    Page<MemberDetailDto> searchMemberList(MemberSearchDto condition);

    MemberDetailDto searchMemberDetail(MemberSearchDto memberSearch);
}
