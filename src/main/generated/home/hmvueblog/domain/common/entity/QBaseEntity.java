package home.hmvueblog.domain.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseEntity is a Querydsl query type for BaseEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseEntity extends EntityPathBase<BaseEntity> {

    private static final long serialVersionUID = 1132896012L;

    public static final QBaseEntity baseEntity = new QBaseEntity("baseEntity");

    public final QSimpleBaseEntity _super = new QSimpleBaseEntity(this);

    //inherited
    public final EnumPath<home.hmvueblog.domain.common.enums.YNCode> delYn = _super.delYn;

    public final DateTimePath<java.time.LocalDateTime> modDt = createDateTime("modDt", java.time.LocalDateTime.class);

    public final StringPath modId = createString("modId");

    public final StringPath modIp = createString("modIp");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    //inherited
    public final StringPath regId = _super.regId;

    //inherited
    public final StringPath regIp = _super.regIp;

    public QBaseEntity(String variable) {
        super(BaseEntity.class, forVariable(variable));
    }

    public QBaseEntity(Path<? extends BaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseEntity(PathMetadata metadata) {
        super(BaseEntity.class, metadata);
    }

}

