package home.hmvueblog.domain.notice.dto;

import home.hmvueblog.domain.common.enums.StatusCode;
import home.hmvueblog.domain.common.enums.YNCode;
import home.hmvueblog.domain.notice.entity.Notice;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
public class NoticeFormDto {

    @NotBlank(message = "제목은 필수 입력입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력입니다.")
    private String content;

    @Convert(converter = StatusCode.ConverterImpl.class)
    private StatusCode statusCd;

    private YNCode importantYn;

    public Notice toEntity() {
        return Notice.builder()
                .title(title)
                .content(content)
                .statusCd(StatusCode.ACTIVE)
                .delYn(YNCode.N)
                .viewCnt(0)
                .likeCnt(0)
                .importantYn(importantYn)
                .regDt(LocalDateTime.now())
                .regId("system")
                .regIp("0.0.0.0")
                .build();
    }
}
