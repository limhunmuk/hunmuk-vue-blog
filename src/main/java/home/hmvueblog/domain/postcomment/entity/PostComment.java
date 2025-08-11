package home.hmvueblog.domain.postcomment.entity;

import home.hmvueblog.domain.common.entity.BaseEntity;
import home.hmvueblog.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "post_comment", schema = "hm-branch")
public class PostComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_comment_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id", nullable = false)
    private Post post;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "status_cd", length = 5)
    private String statusCd;

    public void updatePostComment(String title, String content, String statusCd) {
        this.title = title;
        this.content = content;
        this.statusCd = statusCd;
    }

    public void updateStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }
}

