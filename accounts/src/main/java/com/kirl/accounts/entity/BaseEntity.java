package com.kirl.accounts.entity;

import com.kirl.accounts.audit.AuditAwareImpl;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass //these fields will be in all tables of these modules. that's why we are creating a base entity.
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	//created fields won't get updated because it is written as a timestamp at initialization.
	//updated fields won't be insertable at the creation because it's getting created not updated.

	@CreatedDate
	@Column(updatable = false)
	LocalDateTime createdAt;

	@CreatedBy
	@Column(updatable = false)
	String createdBy;

	@LastModifiedDate
	@Column(insertable = false)
	LocalDateTime updatedAt;

	@LastModifiedBy
	@Column(insertable = false)
	String updatedBy;
}
