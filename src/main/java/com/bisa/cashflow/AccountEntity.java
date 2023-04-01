package com.bisa.cashflow;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ACCOUNT")
@Getter
@Setter
public class AccountEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	protected int status;

	@Column
	protected Date createDate;

	@Column
	protected Date changeDate;
	@Column(length = 100)
	private String accountCode;
	@Column(length = 100)
	private String firstName;
	@Column(length = 100)
	private String lastName;

	@Column(length = 100)
	private String address;
	@Column
	private Long identificationDocument;
	@Column
	private Integer currency;
	
	@Column
	private Double totalAmount;
	@PrePersist
	protected void setPrePersist() {
		this.createDate = new Date();
		this.status = StatusEnum.ACTIVE.getValue();
	}

	@PreUpdate
	protected void setPreUpdate() {
		this.changeDate = new Date();
	}
}
