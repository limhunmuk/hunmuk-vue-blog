package home.hmvueblog.domain.postcomment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostComment is a Querydsl query type for PostComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostComment extends EntityPathBase<PostComment> {

    private static final long serialVersionUID = -510680141L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostComment postComment = new QPostComment("postComment");

    public final home.hmvueblog.domain.common.entity.QBaseEntity _super = new home.hmvueblog.domain.common.entity.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final EnumPath<home.hmvueblog.domain.common.enums.YNCode> delYn = _super.delYn;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDt = _super.modDt;

    //inherited
    public final StringPath modId = _super.modId;

    //inherited
    public final StringPath modIp = _super.modIp;

    public final home.hmvueblog.domain.post.entity.QPost post;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    //inherited
    public final StringPath regId = _super.regId;

    //inherited
    public final StringPath regIp = _super.regIp;

    public final StringPath statusCd = createString("statusCd");

    public final StringPath title = createString("title");

    public QPostComment(String variable) {
        this(PostComment.class, forVariable(variable), INITS);
    }

    public QPostComment(Path<? extends PostComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostComment(PathMetadata metadata, PathInits inits) {
        this(PostComment.class, metadata, inits);
    }

    public QPostComment(Class<? extends PostComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new home.hmvueblog.domain.post.entity.QPost(forProperty("post")) : null;
    }

}

