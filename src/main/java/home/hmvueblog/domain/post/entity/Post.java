package home.hmvueblog.domain.post.entity;

import home.hmvueblog.domain.common.entity.BaseEntity;
import home.hmvueblog.domain.common.enums.StatusCode;
import home.hmvueblog.domain.postcomment.entity.PostComment;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author hunmuk
 */
@Getter
@Setter
@Entity
@Table(name = "post", schema = "hm-branch")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id", nullable = false)
    private Long id;

    @Column(name = "group_cd", length = 5)
    private String groupCd;

    @Column(name = "detail_cd", length = 5)
    private String detailCd;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "status_cd", length = 5)
    private StatusCode statusCd;

    @Column(name = "upload_file_id")
    private Long uploadFileId;

    @Column(name = "view_cnt")
    private Integer viewCnt;

    @Column(name = "like_cnt")
    private Integer likeCnt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> postComments;

    public void addPostComment(PostComment postComment) {
        postComments.add(postComment);
        postComment.setPost(this);
    }

    public void removePostComment(PostComment postComment) {
        postComments.remove(postComment);
        postComment.setPost(null);
    }

    public void incrementViewCount() {
        if (viewCnt == null) {
            viewCnt = 0;
        }
        viewCnt++;
    }

    public void decrementViewCount() {
        if (viewCnt != null && viewCnt > 0) {
            viewCnt--;
        }
    }

    public void updatePost(String title, String content, StatusCode statusCd) {
        this.title = title;
        this.content = content;
        this.statusCd = statusCd;
    }

    public void updateGroupAndDetail(String groupCd, String detailCd) {
        this.groupCd = groupCd;
        this.detailCd = detailCd;
    }

    public void updateUploadFile(Long uploadFileId) {
        this.uploadFileId = uploadFileId;
    }

    public void updateStatus(StatusCode statusCd) {
        this.statusCd = statusCd;
    }

    public void updatePostComments(List<PostComment> postComments) {
        this.postComments.clear();
        for (PostComment postComment : postComments) {
            addPostComment(postComment);
        }
    }

    public void clearPostComments() {
        for (PostComment postComment : postComments) {
            postComment.setPost(null);
        }
        postComments.clear();
    }

}