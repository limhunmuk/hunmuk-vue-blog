package home.hmvueblog.domain.uplodfile.entity;

import home.hmvueblog.domain.common.entity.SimpleBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hunmuk
 */
@Getter
@Setter
@Entity
@Table(name = "upload_file", schema = "hm-branch")
public class UploadFile extends SimpleBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upload_file_id", nullable = false)
    private Long id;

    @Column(name = "category", length = 5)
    private String category;

    @Column(name = "save_file_nm", length = 100)
    private String saveFileNm;

    @Column(name = "real_file_nm", length = 100)
    private String realFileNm;

    @Column(name = "extension", length = 10)
    private String extension;

    @Column(name = "size")
    private Integer size;

}