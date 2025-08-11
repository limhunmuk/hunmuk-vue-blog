package home.hmvueblog.domain.common.entity;

import home.hmvueblog.domain.common.enums.YNCode;
import home.hmvueblog.web.config.IpHolder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@SuperBuilder
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleBaseEntity {

    @CreatedBy
    @Column(nullable = false, updatable = false)
    protected String regId;

    @Column(updatable = false)
    protected String regIp;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected LocalDateTime regDt;

    @Convert(converter = YNCode.ConverterImpl.class)
    private YNCode delYn;

    @PrePersist
    public void onCreate() {
        this.regIp = IpHolder.getIp();
    }
}
