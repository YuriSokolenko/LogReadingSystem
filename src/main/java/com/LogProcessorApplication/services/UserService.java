package com.LogProcessorApplication.services;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LogProcessorApplication.model.UserRecord;
import com.LogProcessorApplication.repositories.UsersDTO;

@Service
public class UserService {

	@Autowired
	private UsersDTO userRepo;
	
	@Autowired
	private DSLContext dsl;
	
	public Map<UserRecord, Integer> topPayingUsersCalc (){
		
			Result<Record> topTenPayingUsers = dsl.fetch("SELECT UR_ID, \r\n" + 
					"SUM (AMOUNT) \r\n" + 
					"FROM PAYMENT_RECORD \r\n" + 
					"GROUP BY UR_ID\r\n" + 
					"ORDER BY SUM (AMOUNT) DESC fetch first 10 rows only");
			
		Map<UserRecord, Integer> resultMap = new HashMap<UserRecord, Integer>();
		
			for (Record record : topTenPayingUsers) {	
				UserRecord user = userRepo.findUserRecordByUserId(record.get("UR_ID").toString());
				Integer amount = new Integer(record.get("2").toString());
			resultMap.put(user, amount);	
			}
			
			resultMap = sortMap(resultMap);
			
		return resultMap;
		}
	
	
	
	private Map<UserRecord, Integer> sortMap(Map<UserRecord, Integer> resultMap){
		
		return	resultMap.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
	}
	
	
	
}
