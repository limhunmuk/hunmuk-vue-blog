package home.hmvueblog.web.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginDetailDto {

    String loginId;
    String password;

    public LoginDetailDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
