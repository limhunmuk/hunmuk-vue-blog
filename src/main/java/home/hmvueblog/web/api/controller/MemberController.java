package home.hmvueblog.web.api.controller;


import home.hmvueblog.domain.member.dto.MemberDetailDto;
import home.hmvueblog.domain.member.dto.MemberFormDto;
import home.hmvueblog.domain.member.dto.MemberSearchDto;
import home.hmvueblog.domain.member.entity.Member;
import home.hmvueblog.web.api.dto.LoginDetailDto;
import home.hmvueblog.web.api.service.MemberService;
import home.hmvueblog.web.config.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author hunmuk
 */
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberDetailsService memberDetailsService;
    private final MemberService memberService;

    @PostMapping("/api/login")
    public ResponseEntity<LoginDetailDto> loginPage(@RequestBody LoginDetailDto request) {

        System.out.println("request.getLoginId() = " + request.getLoginId());
        System.out.println("request.getPassword() = " + request.getPassword());

        return ResponseEntity.ok(request);
    }

    /**
     * 회원가입
     * @param request
     * @return
     */
    @PostMapping("/api/signup")
    public ResponseEntity<String> signup(@RequestBody MemberFormDto request) {

        Member member = memberService.saveMember(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다 > " + member.getId().toString());
    }

    /**
     * 회원 목록 조회
     * @param condition
     * @return
     */
    @GetMapping("/api/member")
    public ResponseEntity<?> memberList(MemberSearchDto condition) {
        return ResponseEntity.ok(memberService.searchMemberList(condition));
    }

    /**
     * 회원정보 조회
     * @return
     */
    @GetMapping("/api/member/{memberId}")
    public ResponseEntity<?> memberPage(@PathVariable("memberId") String memberId, MemberSearchDto condition) {

        condition.setId(Long.valueOf(memberId));
        MemberDetailDto detail = memberService.searchMemberDetail(condition);
        return ResponseEntity.ok(detail);
    }

    /**
     * 회원 정보 수정
     * @param memberId
     * @param request
     * @return
     */
    @PutMapping("/api/member/{memberId}")
    public ResponseEntity<String> updateMember(@PathVariable("memberId") String memberId, @RequestBody MemberFormDto request) {

        memberService.updateMember(memberId, request);
        return ResponseEntity.ok(memberId);
    }

}
