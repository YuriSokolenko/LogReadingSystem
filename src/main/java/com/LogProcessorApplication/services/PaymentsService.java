package com.LogProcessorApplication.services;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.LogProcessorApplication.HibernateConf;
import com.LogProcessorApplication.model.PaymentRecord;
import com.LogProcessorApplication.model.UserRecord;
import com.LogProcessorApplication.repositories.UsersDTO;
@Service
public class PaymentsService implements IPaymentService{

	private Set <PaymentRecord> paymentRecordsSet = new HashSet<>();
	private int duplicatePaymentsCounter = 0;
	private int totalPaymentsCounter = 0;
	
	@Autowired
	private UsersDTO userRepo;
	@Autowired
	private DSLContext dsl;

	
			public  void userRecordReader(String directoryAddress) throws FileNotFoundException, IOException {
				File folder = new File (directoryAddress);
				
				for(File file: folder.listFiles()) {
					if(!isTheFileIsARecord(file.getName())){
						continue;
					}
					singleUserFileProcessor(file);
				}
					}
				
			private  void singleUserFileProcessor(File file) throws IOException, FileNotFoundException {
				try {
		            Stream<String> lines = Files.lines(Paths.get(file.toURI()));
		            lines.forEach(line -> checkIfItUser(line));
		            lines.close();
		        } catch(IOException io) {
		            io.printStackTrace();
		        }
			}
	
			@Transactional
			public  void paymentRecordReader(String directoryAddress) throws FileNotFoundException, IOException {
				
				String fileNameIndex = null;
				String currentFileNameIndex = null;
				File folder = new File (directoryAddress);
				
				for(File file: folder.listFiles()) {
					if(!isTheFileIsARecord(file.getName())){
						continue;
					}
					if(fileNameIndex==null) {
						fileNameIndex = file.getName().substring(8, 10);
						currentFileNameIndex=fileNameIndex;
					} else {
						 currentFileNameIndex = file.getName().substring(8, 10);
					}
					if(fileNameIndex.equals(currentFileNameIndex)) {
						singlePaymentFileProcessor(file);
					} else {
						paymentRecordsSet.clear();
						fileNameIndex = file.getName().substring(8, 10);
						singlePaymentFileProcessor(file);
				          
					}
				}
				paymentRecordsSet.clear();
		}
			
			public Map<String, Integer> lessAndMostCommonNameCalc(){
				
				Result<Record> MostCommonNames = dsl.fetch("SELECT FIRST_NAME, COUNT(*) as OCCURRENCES\r\n" + 
						"FROM USER_RECORD \r\n" + 
						"GROUP BY FIRST_NAME order by COUNT(*) desc");
				
				String firstName;
				Integer numOfOccurrences;
				Integer firstNumOccurrences=0; 
				
				Map<String, Integer> resultMap = new HashMap<String, Integer>();
				for(Record record: MostCommonNames) {
					firstName = record.get("FIRST_NAME").toString();
					numOfOccurrences = new Integer (record.get("OCCURRENCES").toString());
					if(numOfOccurrences.equals(firstNumOccurrences)||numOfOccurrences.intValue()>firstNumOccurrences.intValue()) {
						resultMap.put(firstName, numOfOccurrences);
						firstNumOccurrences=numOfOccurrences;
					}
					else {
						break;
					}
				}
				
				Result<Record> lessCommonNames = dsl.fetch("SELECT FIRST_NAME, COUNT(*) as OCCURRENCES\r\n" + 
						"FROM USER_RECORD \r\n" + 
						"GROUP BY FIRST_NAME order by COUNT(*) asc");
				
				firstNumOccurrences=new Integer (lessCommonNames.getValue(0, "OCCURRENCES").toString());
				
				for(Record record: lessCommonNames) {
					firstName = record.get("FIRST_NAME").toString();
					numOfOccurrences = new Integer (record.get("OCCURRENCES").toString());
					if(numOfOccurrences.equals(firstNumOccurrences)) {
						
						resultMap.put(firstName, numOfOccurrences);
						firstNumOccurrences=numOfOccurrences;
					}
					else {
						break;
					}
				}
				return resultMap;
			}
			
			private  void singlePaymentFileProcessor(File file) throws IOException, FileNotFoundException {
				try {
		            Stream<String> lines = Files.lines(Paths.get(file.toURI()));
		            lines.forEach(line -> checkIfItPayment(line));
		            lines.close();
		        } catch(IOException io) {
		            io.printStackTrace();
		        }
			}

			private void singleUserProccessor(String currentRecord) {
				String[] userFields =  currentRecord.split(",");
				String userId=userFields[1];
				String firstName=userFields[2];
				String lastName=userFields[3];
			
				UserRecord ur = new UserRecord();
				ur.setUserId(userId);
				ur.setFirstName(firstName);
				ur.setLastName(lastName);
				ur.setTotalAmountOfPayments(0);
				
				userRepo.save(ur);
			}

			private void singlePaymentProccessor(String currentRecord) {
				
				final Session session = HibernateConf.getHibernateSession();
		        
				String[] paymentFields =  currentRecord.split(",");
				String paymentId=paymentFields[1];
				String userId=paymentFields[2];
				int amount= Integer.parseInt(paymentFields[3]);
				
				
				PaymentRecord record = new PaymentRecord();		
				record.setPaymentId(paymentId);
				record.setUserRecordId(userId);
				record.setAmount(amount);
				record.setUserRecord(userRepo.findById(userId).get());
				
				
				totalPaymentsCounter++;
				if(!(paymentRecordsSet.add(record))){			
					duplicatePaymentsCounter++;		
					}else {		
							
						try {
							Transaction tx = session.beginTransaction();
							
							session.save(record);
							tx.commit();
							session.clear();
						
							
						} catch (Exception e) {
							e.printStackTrace();
						}finally{
							session.close();
						}
						System.out.println("doublicate "+ duplicatePaymentsCounter + " Total " + totalPaymentsCounter);
			}
				
			}
			private boolean checkIfItUser(String nextLine) {
				
				String userRecordPattern = "UR";
				Pattern pattern =  Pattern.compile(userRecordPattern);
				Matcher matcher = pattern.matcher(nextLine);
				
				if(matcher.lookingAt()) {
					singleUserProccessor(nextLine);
				}
				
				return matcher.lookingAt();
			}
			
			private boolean checkIfItPayment(String nextLine) {
				
				String userRecordPattern = "UR";
				Pattern pattern =  Pattern.compile(userRecordPattern);
				Matcher matcher = pattern.matcher(nextLine);
				
				if(matcher.lookingAt()) {
				}
				else {
					singlePaymentProccessor(nextLine);
				}
				return matcher.lookingAt();
			}
			

			private boolean isTheFileIsARecord(String fileName) {
				Pattern pattern = Pattern.compile("records_.{2}_.{5}\\.log");
				Matcher matcher = pattern.matcher(fileName);
				boolean matches = matcher.matches();
				return matches;
			}

			public int getDuplicatePaymentsCounter() {
				return duplicatePaymentsCounter;
			}

			public int getTotalPaymentsCounter() {
				return totalPaymentsCounter;
			}
		} 
			
			
		

