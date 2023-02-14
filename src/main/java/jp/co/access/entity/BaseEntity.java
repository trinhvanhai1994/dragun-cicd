package jp.co.access.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {

    // 作成日
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    // 作成者
    @CreatedBy
    private String creator;

    // 更新日
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    // 更新者
    @LastModifiedBy
    private String updater;
}
