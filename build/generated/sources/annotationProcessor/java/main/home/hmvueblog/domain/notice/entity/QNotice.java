package home.hmvueblog.domain.notice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNotice is a Querydsl query type for Notice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotice extends EntityPathBase<Notice> {

    private static final long serialVersionUID = 1993049597L;

    public static final QNotice notice = new QNotice("notice");

    public final home.hmvueblog.domain.common.entity.QBaseEntity _super = new home.hmvueblog.domain.common.entity.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final EnumPath<home.hmvueblog.domain.common.enums.YNCode> delYn = _super.delYn;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<home.hmvueblog.domain.common.enums.YNCode> importantYn = createEnum("importantYn", home.hmvueblog.domain.common.enums.YNCode.class);

    public final NumberPath<Integer> likeCnt = createNumber("likeCnt", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDt = _super.modDt;

    //inherited
    public final StringPath modId = _super.modId;

    //inherited
    public final StringPath modIp = _super.modIp;

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

    public QNotice(String variable) {
        super(Notice.class, forVariable(variable));
    }

    public QNotice(Path<? extends Notice> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNotice(PathMetadata metadata) {
        super(Notice.class, metadata);
    }

}

