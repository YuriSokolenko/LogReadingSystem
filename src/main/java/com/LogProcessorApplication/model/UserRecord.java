package com.LogProcessorApplication.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="USER_RECORD")
public class UserRecord  implements Comparable<UserRecord>, Serializable{
	
	private static final long serialVersionUID = 8987076319438716913L;
	
	
	@Id
	@Column(name="USER_ID", length=50, nullable=false, unique=true)
	private String userId;
	@Column(name="FIRST_NAME", length=50, nullable=false, unique=false)
	private String firstName;
	@Column(name="LAST_NAME", length=50, nullable=false, unique=false)
	private String lastName;
	@Column(name="TOTAL_AMOUNT_OF_PAYMENTS", length=50, nullable=true, unique=false)
	private int totalAmountOfPayments;
	
	@OneToMany(
	        mappedBy = "userRecord",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true,
	        fetch = FetchType.LAZY
	    )
	private List <PaymentRecord> paymentRecords = new ArrayList<PaymentRecord>();
	
	
	public UserRecord() {
	}
	
	public List<PaymentRecord> getPaymentRecords() {
		return paymentRecords;
	}

	public void setPaymentRecords(List<PaymentRecord> paymentRecords) {
		this.paymentRecords = paymentRecords;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getTotalAmountOfPayments() {
		return totalAmountOfPayments;
	}

	public void setTotalAmountOfPayments(int totalAmountOfPayments) {
		this.totalAmountOfPayments += totalAmountOfPayments;
	}
	
	@Override
	public int compareTo(UserRecord user) {
		return (user.getTotalAmountOfPayments()-this.getTotalAmountOfPayments());
	}
	
	
	
}

