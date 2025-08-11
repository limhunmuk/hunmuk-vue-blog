package home.hmvueblog.domain.member.entity;

import home.hmvueblog.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static blog.hmbrunch.web.util.IPUtils.getClientIP;

@Entity
@Getter
@Setter
@Table(name = "member")
@NoArgsConstructor
@SuperBuilder
public class Member extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(name = "login_id", length = 50)
    private String loginId;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "mem_nm", length = 50)
    private String name;

    @Column(name = "nick_nm", length = 50)
    private String nickName;

    @Column(name = "mem_type", length = 5)
    @Comment("회원구분")
    private String memType;

    @Column(name = "join_dt")
    private LocalDate joinDt;

    @Column(name = "phone_no", length = 20)
    private String phoneNo;

    @Column(name = "addr", length = 50)
    private String addr;

    @Column(name = "addr_detail", length = 100)
    private String addrDtl;

    public Member(String loginId, String password, String name, String nickName, String memberType,
                  LocalDate joinDateTime, String phoneNumber, String address, String addressDetail, String regId, String regIp) {

        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.memType = memberType;
        this.joinDt = joinDateTime;
        this.phoneNo = phoneNumber;
        this.addr = address;
        this.addrDtl = addressDetail;
        this.regId = regId;
        this.regIp = regIp;
    }

    public void updateMember(String nickName,
                           String phoneNumber, String address, String addressDetail, String modId, String modIp) {

        this.nickName = nickName;
        this.phoneNo = phoneNumber;
        this.addr = address;
        this.addrDtl = addressDetail;
        this.modId = modId;
        this.modDt = LocalDateTime.now();
        this.modIp = modIp;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
