package com.service.banking.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Json serialize
// https://stackoverflow.com/questions/15261456/how-do-i-disable-fail-on-empty-beans-in-jackson
//@JsonSerialize

// creation and update timestamps annotations
//https://stackoverflow.com/questions/42366763/hibernate-creationtimestamp-updatetimestamp-for-calendar

@Entity 
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "customer_id")
	private Long custId;

	@Column(name = "customer_first_n")
	private String custFirstName;

	@Column(name = "customer_last_n")
	private String custLastName;

	@Column(name = "customer_city")
	private String custCity;

	@Column(name = "customer_phone")
	private String custPhone;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id")
	private BankAccount bankAcc;
	
	// lazy collection == false
//	https://stackoverflow.com/questions/4334970/hibernate-throws-multiplebagfetchexception-cannot-simultaneously-fetch-multipl
	
	// https://www.baeldung.com/hibernate-one-to-many
//	@EqualsAndHashCode.Exclude
//	@ToString.Exclude
//	@JsonBackReference
//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name = "offer_id")
//	private Offer offer;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="customer_id")
	private List<Offer> offers;
	
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(Long custId, String custFirstName, String custLastName, String custCity, String custPhone,
			BankAccount bankAcc, List<Offer> offers) {
		super();
		this.custId = custId;
		this.custFirstName = custFirstName;
		this.custLastName = custLastName;
		this.custCity = custCity;
		this.custPhone = custPhone;
		this.bankAcc = bankAcc;
		this.offers = offers;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getCustFirstName() {
		return custFirstName;
	}

	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}

	public String getCustLastName() {
		return custLastName;
	}

	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}

	public String getCustCity() {
		return custCity;
	}

	public void setCustCity(String custCity) {
		this.custCity = custCity;
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

	public BankAccount getBankAcc() {
		return bankAcc;
	}

	public void setBankAcc(BankAccount bankAcc) {
		this.bankAcc = bankAcc;
	}

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bankAcc, custCity, custFirstName, custId, custLastName, custPhone, offers);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(bankAcc, other.bankAcc) && Objects.equals(custCity, other.custCity)
				&& Objects.equals(custFirstName, other.custFirstName) && Objects.equals(custId, other.custId)
				&& Objects.equals(custLastName, other.custLastName) && Objects.equals(custPhone, other.custPhone)
				&& Objects.equals(offers, other.offers);
	}

	@Override
	public String toString() {
		return "Customer [custId=" + custId + ", custFirstName=" + custFirstName + ", custLastName=" + custLastName
				+ ", custCity=" + custCity + ", custPhone=" + custPhone + ", bankAcc=" + bankAcc + ", offers=" + offers
				+ "]";
	}

}