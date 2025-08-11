package home.hmvueblog.domain.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -1208635699L;

    public static final QPost post = new QPost("post");

    public final home.hmvueblog.domain.common.entity.QBaseEntity _super = new home.hmvueblog.domain.common.entity.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final EnumPath<home.hmvueblog.domain.common.enums.YNCode> delYn = _super.delYn;

    public final StringPath detailCd = createString("detailCd");

    public final StringPath groupCd = createString("groupCd");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCnt = createNumber("likeCnt", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDt = _super.modDt;

    //inherited
    public final StringPath modId = _super.modId;

    //inherited
    public final StringPath modIp = _super.modIp;

    public final ListPath<home.hmvueblog.domain.postcomment.entity.PostComment, home.hmvueblog.domain.postcomment.entity.QPostComment> postComments = this.<home.hmvueblog.domain.postcomment.entity.PostComment, home.hmvueblog.domain.postcomment.entity.QPostComment>createList("postComments", home.hmvueblog.domain.postcomment.entity.PostComment.class, home.hmvueblog.domain.postcomment.entity.QPostComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    //inherited
    public final StringPath regId = _super.regId;

    //inherited
    public final StringPath regIp = _super.regIp;

    public final EnumPath<home.hmvueblog.domain.common.enums.StatusCode> statusCd = createEnum("statusCd", home.hmvueblog.domain.common.enums.StatusCode.class);

    public final StringPath title = createString("title");

    public final NumberPath<Long> uploadFileId = createNumber("uploadFileId", Long.class);

    public final NumberPath<Integer> viewCnt = createNumber("viewCnt", Integer.class);

    public QPost(String variable) {
        super(Post.class, forVariable(variable));
    }

    public QPost(Path<? extends Post> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPost(PathMetadata metadata) {
        super(Post.class, metadata);
    }

}

