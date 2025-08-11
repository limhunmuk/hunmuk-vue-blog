package home.hmvueblog.domain.notice.dto;

import home.hmvueblog.domain.common.enums.YNCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
public class NoticeDetailDto {

    private Long noticeId;

    private String title;
    private String content;

    private Integer viewCnt;
    private Integer likeCnt;

    private LocalDateTime regDt;
    private String regId;

    private Long memId;
    private String loginId;
    private String nickName;

    private YNCode importantYn;

}
