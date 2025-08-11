package home.hmvueblog.domain.member.dto;

import home.hmvueblog.domain.member.entity.Member;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MemberDetailDto {

    private Long id;
    private String loginId;
    private String name;
    private String nickName;
    private String memType;
    private LocalDate joinDt;
    private String phoneNo;
    private String addr;
    private String addrDtl;


    public static MemberDetailDto from(Member member) {

        MemberDetailDto dto = new MemberDetailDto();
        dto.setId(member.getId());
        dto.setLoginId(member.getLoginId());
        dto.setName(member.getName());
        dto.setNickName(member.getNickName());
        dto.setMemType(member.getMemType());
        dto.setJoinDt(member.getJoinDt() != null ? member.getJoinDt() : null);
        dto.setPhoneNo(member.getPhoneNo());
        dto.setAddr(member.getAddr());
        dto.setAddrDtl(member.getAddrDtl());

        return dto;
    }
}
