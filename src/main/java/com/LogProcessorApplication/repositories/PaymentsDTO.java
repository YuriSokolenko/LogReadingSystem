package com.LogProcessorApplication.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.LogProcessorApplication.model.PaymentRecord;

@Repository
public interface PaymentsDTO extends CrudRepository<PaymentRecord, Long> {

	List<PaymentRecord> findByUserRecordId(String userRecordId);
	
	
	
	
}
