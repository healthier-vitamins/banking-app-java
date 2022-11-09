package com.service.banking.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

// ignorejson properties
// https://jtuto.com/java/solved-how-to-ignore-handler-hibernatelazyinitializer-in-json-jackson-in-spring-hibernate-project/

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "offer_id")
	private Long offerId;

	@Column(name = "offer_name")
	private String offerName;

	@Column(name = "loan_amnt")
	private Float loanAmnt;

	@Column(name = "interest_rate_percent")
	private Float interestRatePercent;

	@Column(name = "interest_free_withdraw")
	private Float interestFreeCashWithdrawal;

	@Column(name = "annual_fee")
	private Float annualFee;

	@Column(name = "preclosure_charges")
	private Float preclosureCharges;

	// lazy collection == false
//		https://stackoverflow.com/questions/4334970/hibernate-throws-multiplebagfetchexception-cannot-simultaneously-fetch-multipl
	
	// https://www.baeldung.com/hibernate-one-to-many
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "offer", fetch = FetchType.EAGER)
//	@JsonManagedReference
//	private Set<Customer> customers;
	
	@Column(name="customer_id")
	private Long custId;

	public Offer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Offer(Long offerId, String offerName, Float loanAmnt, Float interestRatePercent,
			Float interestFreeCashWithdrawal, Float annualFee, Float preclosureCharges, Long custId) {
		super();
		this.offerId = offerId;
		this.offerName = offerName;
		this.loanAmnt = loanAmnt;
		this.interestRatePercent = interestRatePercent;
		this.interestFreeCashWithdrawal = interestFreeCashWithdrawal;
		this.annualFee = annualFee;
		this.preclosureCharges = preclosureCharges;
		this.custId = custId;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public Float getLoanAmnt() {
		return loanAmnt;
	}

	public void setLoanAmnt(Float loanAmnt) {
		this.loanAmnt = loanAmnt;
	}

	public Float getInterestRatePercent() {
		return interestRatePercent;
	}

	public void setInterestRatePercent(Float interestRatePercent) {
		this.interestRatePercent = interestRatePercent;
	}

	public Float getInterestFreeCashWithdrawal() {
		return interestFreeCashWithdrawal;
	}

	public void setInterestFreeCashWithdrawal(Float interestFreeCashWithdrawal) {
		this.interestFreeCashWithdrawal = interestFreeCashWithdrawal;
	}

	public Float getAnnualFee() {
		return annualFee;
	}

	public void setAnnualFee(Float annualFee) {
		this.annualFee = annualFee;
	}

	public Float getPreclosureCharges() {
		return preclosureCharges;
	}

	public void setPreclosureCharges(Float preclosureCharges) {
		this.preclosureCharges = preclosureCharges;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(annualFee, custId, interestFreeCashWithdrawal, interestRatePercent, loanAmnt, offerId,
				offerName, preclosureCharges);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Offer other = (Offer) obj;
		return Objects.equals(annualFee, other.annualFee) && Objects.equals(custId, other.custId)
				&& Objects.equals(interestFreeCashWithdrawal, other.interestFreeCashWithdrawal)
				&& Objects.equals(interestRatePercent, other.interestRatePercent)
				&& Objects.equals(loanAmnt, other.loanAmnt) && Objects.equals(offerId, other.offerId)
				&& Objects.equals(offerName, other.offerName)
				&& Objects.equals(preclosureCharges, other.preclosureCharges);
	}

	@Override
	public String toString() {
		return "Offer [offerId=" + offerId + ", offerName=" + offerName + ", loanAmnt=" + loanAmnt
				+ ", interestRatePercent=" + interestRatePercent + ", interestFreeCashWithdrawal="
				+ interestFreeCashWithdrawal + ", annualFee=" + annualFee + ", preclosureCharges=" + preclosureCharges
				+ ", custId=" + custId + "]";
	}

}