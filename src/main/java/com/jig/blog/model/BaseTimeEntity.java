package com.jig.blog.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jig.blog.config.DateTimeAttributeConverter;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 공통으로 사용하는 생성시간,수정시간 값을 추상클래스로 정의하여 사용
 *  - DateTimeAttributeConverter 를 사용하여 Entity와 DB 사이에서 데이터를 변환
 *  - LocalDateTime에는 T가 포함되기 때문에 화면단에 보여줄 때는 T를 제거한 BaseTimeEntity의 getFormattedCreatedDate, getFormattedModifiedDate를 사용
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @Column(updatable = false)
    @CreatedDate
    @Convert(converter = DateTimeAttributeConverter.class)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Convert(converter = DateTimeAttributeConverter.class)
    private LocalDateTime modifiedDate;

    public String getCreatedDate() {
        return createdDate.toString().replace("T", " ");
    }
    public String getModifiedDate() {
        return modifiedDate.toString().replace("T", " ");
    }

    //    @PrePersist
//    public void onPrePersist(){
//        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
//        this.modifiedDate = this.createdDate;
//    }
//
//    @PreUpdate
//    public void onPreUpdate(){
//        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
//    }
}
