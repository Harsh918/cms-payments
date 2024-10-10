package com.cms.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Transaction {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @SequenceGenerator(name = "transaction_seq", sequenceName = "BATCH_JOB_EXECUTION_SEQ", allocationSize = 1)
	private Long id;

	private String srno;
	private String debitAccountNum;
	private String creditAccountNum;
	private BigDecimal amount;
	private String ifscCode;
	private String payeeName;
	private String payerName;
	private String status;

	

	public Transaction(String srno, String debitAccountNum, String creditAccountNum, BigDecimal amount,
			String ifscCode, String payeeName, String payerName, String status) {
		super();
		//this.id = id;
		this.srno = srno;
		this.debitAccountNum = debitAccountNum;
		this.creditAccountNum = creditAccountNum;
		this.amount = amount;
		this.ifscCode = ifscCode;
		this.payeeName = payeeName;
		this.payerName = payerName;
		this.status = status;
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSrno() {
		return srno;
	}

	public void setSrno(String srno) {
		this.srno = srno;
	}

	public String getDebitAccountNum() {
		return debitAccountNum;
	}

	public void setDebitAccountNum(String debitAccountNum) {
		this.debitAccountNum = debitAccountNum;
	}

	public String getCreditAccountNum() {
		return creditAccountNum;
	}

	public void setCreditAccountNum(String creditAccountNum) {
		this.creditAccountNum = creditAccountNum;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
