package com.LogProcessorApplication.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name="PAYMENT_RECORD", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID") })
public class PaymentRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4743899984797472310L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;
	

	@Column(name="PAYMENT_ID", length=50, nullable=false, unique=false)
	private String paymentId;
	@Column(name="AMOUNT", nullable=false, unique=false)
    private int amount;
    
	@Column(name="UR_ID", length=50, nullable=false, unique=false)
	private String userRecordId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn	(name = "USER_RECORD_USER_ID")
    private UserRecord userRecord;
    
    
    
	public PaymentRecord() {
	}

	public String getPaymentId() {
		return paymentId;
	}

	
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public String getUserRecordId() {
		return userRecordId;
	}
	
	public void setUserRecordId(String userRecordId) {
		this.userRecordId = userRecordId;
	}
	
	public UserRecord getUserRecord() {
		return userRecord;
	}
	
	public void setUserRecord(UserRecord userRecord) {
		this.userRecord = userRecord;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((paymentId == null) ? 0 : paymentId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentRecord other = (PaymentRecord) obj;
		if (paymentId == null) {
			if (other.paymentId != null)
				return false;
		} else if (!paymentId.equals(other.paymentId))
			return false;
		return true;
	}


	
}
