package com.bisa.cashflow;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MOVEMENT")
@Getter
@Setter
public class MovementEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	protected int status;

	@Column
	protected Date createDate;


	@Column(length = 100)
	private String accountCode;

	@Column
	private Double amount;
	
	@Column
	private Integer currency;

	@PrePersist
	protected void setPrePersist() {
		this.createDate = new Date();
	}
}
