package home.hmvueblog.domain.member.dto;

import home.hmvueblog.domain.common.enums.YNCode;
import home.hmvueblog.domain.member.entity.Member;
import home.hmvueblog.domain.notice.entity.Notice;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class MemberFormDto {

    private Long id;
    private String email;
    private String password;
    private String confirmPassword;

    private String name;
    private String nickName;
    private String memberType;

    private LocalDate joinDt;
    private String phoneNumber;
    private String address;
    private String addressDetail;

    private String ipAddress;
    private LocalDateTime regDt;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .id(id)
                .loginId(email)
                .name(name)
                .password(passwordEncoder.encode(password))
                .nickName(nickName)
                .memType(memberType)
                .joinDt(joinDt)
                .phoneNo(phoneNumber)
                .addr(address)
                .addrDtl(addressDetail)
                .regDt(LocalDateTime.now())
                .regIp(ipAddress)
                .regId(email)
                .delYn(YNCode.N)
                .build();
    }
}
