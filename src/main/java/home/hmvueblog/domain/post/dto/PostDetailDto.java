package home.hmvueblog.domain.post.dto;

import home.hmvueblog.domain.common.enums.StatusCode;
import home.hmvueblog.domain.common.enums.YNCode;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDetailDto {

    private Long postId;
    private String title;
    private String content;

    private StatusCode statusCd;
    private Integer viewCnt;
    private Integer likeCnt;

    private LocalDateTime regDt;
    private String regId;
    private LocalDateTime modDt;
    private String modId;

    private YNCode delYn;

    private Long memId;
    private String loginId;
    private String nickName;

    private Integer commentCnt;

}
