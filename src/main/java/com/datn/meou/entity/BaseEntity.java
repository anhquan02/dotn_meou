package com.datn.meou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    protected Date createdDate;

    @Column(name = "updated_date")
    @LastModifiedDate
    private Date updatedDate;

    private Boolean deleted;
}
