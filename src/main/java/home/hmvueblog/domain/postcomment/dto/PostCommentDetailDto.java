package home.hmvueblog.domain.postcomment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostCommentDetailDto {

    private Long id;
    private String loginId;
    private String name;
    private Integer age;

    private LocalDateTime regDt;
}
