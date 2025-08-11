package home.hmvueblog.web.api.service;

import home.hmvueblog.domain.common.enums.StatusCode;
import home.hmvueblog.domain.member.MemberRepository;
import home.hmvueblog.domain.member.dto.MemberDetailDto;
import home.hmvueblog.domain.member.dto.MemberFormDto;
import home.hmvueblog.domain.member.dto.MemberSearchDto;
import home.hmvueblog.domain.member.entity.Member;
import home.hmvueblog.domain.notice.NoticeRepository;
import home.hmvueblog.domain.notice.dto.NoticeDetailDto;
import home.hmvueblog.domain.notice.dto.NoticeFormDto;
import home.hmvueblog.domain.notice.dto.NoticeSearchDto;
import home.hmvueblog.domain.notice.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static home.hmvueblog.domain.member.entity.QMember.member;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 회원 목록 조회
     * @param condition
     * @return
     */
    public Page<?> searchMemberList(MemberSearchDto condition){
        return memberRepository.searchMemberList(condition);
    }

    /**
     * 회원 상세 조회
     * @param condition
     * @return
     */
    public MemberDetailDto searchMemberDetail(MemberSearchDto condition){
        return memberRepository.searchMemberDetail(condition);
    }

    /**
     * 회원 등록
     * @param form
     * @retur
     */
    @Transactional
    public Member saveMember(MemberFormDto form) {

        Member member = form.toEntity(passwordEncoder);
        return memberRepository.save(member);
    }

    /**
     * 회원 수정
     * @param memberForm
     * @return
     */
    @Transactional
    public Member updateMember(String id, MemberFormDto memberForm) {

        Member member = memberRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        member.updateMember(
                memberForm.getNickName(),
                memberForm.getPhoneNumber(),
                memberForm.getAddress(),
                memberForm.getAddressDetail(),
                memberForm.getEmail(),
                memberForm.getIpAddress()
        );

        return member;
    }

    /**
     * 회원 삭제
     * @param id
     */
    public void deleteMember(Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지사항이 존재하지 않습니다."));

        memberRepository.delete(member);
    }
}
