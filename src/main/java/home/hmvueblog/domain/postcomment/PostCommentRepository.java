package home.hmvueblog.domain.postcomment;

import home.hmvueblog.domain.postcomment.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PostCommentRepository extends JpaRepository<PostComment, Long>,
                                            QuerydslPredicateExecutor<PostComment>, PostCommentRepositoryCustom {

    Integer countByPostId(Long id);
}
