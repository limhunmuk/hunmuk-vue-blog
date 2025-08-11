package home.hmvueblog.domain.notice.entity;

import home.hmvueblog.domain.common.entity.BaseEntity;
import home.hmvueblog.domain.common.enums.StatusCode;
import home.hmvueblog.domain.common.enums.YNCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "notice", schema = "hm-branch")
@SuperBuilder
@NoArgsConstructor
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "status_cd", length = 5)
    @Convert(converter = StatusCode.ConverterImpl.class)
    private StatusCode statusCd;

    @Column(name = "upload_file_id")
    private Long uploadFileId;

    @Column(name = "view_cnt")
    private Integer viewCnt;

    @Column(name = "like_cnt")
    private Integer likeCnt;

    @Column(name = "important_yn", length = 1)
    @Convert(converter = YNCode.ConverterImpl.class)
    private YNCode importantYn;

    public void updateNotice(String title, String content, StatusCode statusCd, Long uploadFileId) {
        this.title = title;
        this.content = content;
        this.statusCd = statusCd;
        this.uploadFileId = uploadFileId;

        this.viewCnt = this.viewCnt == null ? 0 : this.viewCnt + 1;
    }

}