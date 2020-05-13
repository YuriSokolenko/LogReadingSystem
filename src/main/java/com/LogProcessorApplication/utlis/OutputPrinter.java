package com.LogProcessorApplication.utlis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import com.LogProcessorApplication.LogProcessorApplication;
import com.LogProcessorApplication.model.UserRecord;

public class OutputPrinter {

	public void printMostAndLessCommonNames(Map<String, Integer> mapOfNames) {
			try (FileWriter fw = new FileWriter(LogProcessorApplication.OUTPUT_FILE_PATH, true);
					BufferedWriter bw = new BufferedWriter(fw))
			{
				bw.newLine();
				bw.write("---------------------------------------------------------------------");
				bw.newLine();
				bw.newLine();
				
				int mostCommonNameInTheMap=(Collections.max(mapOfNames.values()));
				int lessCommonNameInTheMap=(Collections.min(mapOfNames.values()));
				
				for (Entry<String, Integer> mostCommonName : mapOfNames.entrySet()) {  
					if (mostCommonName.getValue()==mostCommonNameInTheMap) {
						bw.write("Most common name(s): " + mostCommonName.getKey() + " " + mostCommonName.getValue()); 
						bw.newLine();
					}
				}
				for (Entry<String, Integer> lessCommonName : mapOfNames.entrySet()) {  
					if(lessCommonName.getValue()==lessCommonNameInTheMap) {
						bw.write("Less common name(s): " + lessCommonName.getKey() + " " + lessCommonName.getValue()); 
						bw.newLine();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
			} 
			
	
	
	public void printerPaymentRation(double ratioValue, int totalNumberOfPayments, int numberOfDoubleBookings) {

		try (FileWriter fw = new FileWriter(LogProcessorApplication.OUTPUT_FILE_PATH, false);
			    BufferedWriter bw = new BufferedWriter(fw))
		 {
			bw.newLine();
			bw.write("Payments Ratio = " + ratioValue + " (" + numberOfDoubleBookings+"/" +totalNumberOfPayments+")");
		 	} catch (IOException e) {
		e.printStackTrace();
		 	}
	}

	public void printTopTenPayingUsers (Map <UserRecord, Integer> userRecords) {
		
		try (FileWriter fw = new FileWriter(LogProcessorApplication.OUTPUT_FILE_PATH, true);
			    BufferedWriter bw = new BufferedWriter(fw))
		 {
			bw.newLine();
			bw.newLine();
			bw.write("Top paying users: ");
			bw.newLine();
			bw.newLine();
			userRecords.forEach((userRecord, amount) ->
			{
				try {
					bw.write(userRecord.getFirstName() + " " + userRecord.getLastName() + "   : " + amount);
					bw.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			bw.newLine();
			
		 	} catch (IOException e) {
		e.printStackTrace();
		 	}
		}
	}

