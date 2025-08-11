package home.hmvueblog.domain.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSimpleBaseEntity is a Querydsl query type for SimpleBaseEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QSimpleBaseEntity extends EntityPathBase<SimpleBaseEntity> {

    private static final long serialVersionUID = -934863778L;

    public static final QSimpleBaseEntity simpleBaseEntity = new QSimpleBaseEntity("simpleBaseEntity");

    public final EnumPath<home.hmvueblog.domain.common.enums.YNCode> delYn = createEnum("delYn", home.hmvueblog.domain.common.enums.YNCode.class);

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final StringPath regId = createString("regId");

    public final StringPath regIp = createString("regIp");

    public QSimpleBaseEntity(String variable) {
        super(SimpleBaseEntity.class, forVariable(variable));
    }

    public QSimpleBaseEntity(Path<? extends SimpleBaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSimpleBaseEntity(PathMetadata metadata) {
        super(SimpleBaseEntity.class, metadata);
    }

}

