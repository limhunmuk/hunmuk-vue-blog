package home.hmvueblog.domain.uplodfile.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUploadFile is a Querydsl query type for UploadFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUploadFile extends EntityPathBase<UploadFile> {

    private static final long serialVersionUID = 323426124L;

    public static final QUploadFile uploadFile = new QUploadFile("uploadFile");

    public final home.hmvueblog.domain.common.entity.QSimpleBaseEntity _super = new home.hmvueblog.domain.common.entity.QSimpleBaseEntity(this);

    public final StringPath category = createString("category");

    //inherited
    public final EnumPath<home.hmvueblog.domain.common.enums.YNCode> delYn = _super.delYn;

    public final StringPath extension = createString("extension");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath realFileNm = createString("realFileNm");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    //inherited
    public final StringPath regId = _super.regId;

    //inherited
    public final StringPath regIp = _super.regIp;

    public final StringPath saveFileNm = createString("saveFileNm");

    public final NumberPath<Integer> size = createNumber("size", Integer.class);

    public QUploadFile(String variable) {
        super(UploadFile.class, forVariable(variable));
    }

    public QUploadFile(Path<? extends UploadFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUploadFile(PathMetadata metadata) {
        super(UploadFile.class, metadata);
    }

}

