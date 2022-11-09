package com.service.banking.model;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "account_id")
	private Long accId;

	@Column(name = "account_type")
	private String accType;

	@Column(name = "account_balance")
	private Float accBal;

	@Column(name = "account_creation_date")
	private String accCreationDate;

	public BankAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BankAccount(Long accId, String accType, Float accBal, String accCreationDate) {
		super();
		this.accId = accId;
		this.accType = accType;
		this.accBal = accBal;
		this.accCreationDate = accCreationDate;
	}

	public Long getAccId() {
		return accId;
	}

	public void setAccId(Long accId) {
		this.accId = accId;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public Float getAccBal() {
		return accBal;
	}

	public void setAccBal(Float accBal) {
		this.accBal = accBal;
	}

	public String getAccCreationDate() {
		return accCreationDate;
	}

	public void setAccCreationDate(String accCreationDate) {
		this.accCreationDate = accCreationDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accBal, accCreationDate, accId, accType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAccount other = (BankAccount) obj;
		return Objects.equals(accBal, other.accBal) && Objects.equals(accCreationDate, other.accCreationDate)
				&& Objects.equals(accId, other.accId) && Objects.equals(accType, other.accType);
	}

	@Override
	public String toString() {
		return "BankAccount [accId=" + accId + ", accType=" + accType + ", accBal=" + accBal + ", accCreationDate="
				+ accCreationDate + "]";
	}
	
}