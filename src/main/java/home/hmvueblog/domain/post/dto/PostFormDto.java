package home.hmvueblog.domain.post.dto;

import home.hmvueblog.domain.common.enums.StatusCode;
import home.hmvueblog.domain.common.enums.YNCode;
import home.hmvueblog.domain.notice.entity.Notice;
import home.hmvueblog.domain.post.entity.Post;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostFormDto {

    @NotBlank(message = "제목은 필수 입력입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력입니다.")
    private String content;

    @Convert(converter = StatusCode.ConverterImpl.class)
    private StatusCode statusCd;

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .statusCd(StatusCode.ACTIVE)
                .delYn(YNCode.N)
                .regDt(LocalDateTime.now())
                .regId("system")
                .regIp("0.0.0.0")
                .build();
    }
}
