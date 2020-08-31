package com.example.demo.jpa.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity<U> implements Serializable {
    @CreatedDate
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createDate;

    @CreatedBy
    @Column(name = "CREATE_BY")
    protected String createBy;

    @LastModifiedBy
    @Column(name = "LAST_MODIFY_BY")
    protected String lastModifyBy;

    @LastModifiedDate
    @Column(name = "LAST_MODIFY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastModifyDate;

    public Optional<Date> getCreateDate() {
        return Optional.ofNullable(createDate);
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Optional<String> getCreateBy() {
        return Optional.ofNullable(createBy);
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Optional<String> getLastModifyBy() {
        return Optional.ofNullable(lastModifyBy);
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    public Optional<Date> getLastModifyDate() {
        return Optional.ofNullable(lastModifyDate);
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }
}
