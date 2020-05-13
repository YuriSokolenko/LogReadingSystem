package com.LogProcessorApplication.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.LogProcessorApplication.model.UserRecord;

@Repository
public interface UsersDTO extends CrudRepository<UserRecord, String> {

	public UserRecord findUserRecordByUserId(String id);
}
