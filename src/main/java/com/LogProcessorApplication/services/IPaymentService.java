package com.LogProcessorApplication.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface IPaymentService {

	public  void userRecordReader(String directoryAddress) throws FileNotFoundException, IOException;
	
	public  void paymentRecordReader(String directoryAddress) throws FileNotFoundException, IOException;
	
	public Map<String, Integer> lessAndMostCommonNameCalc();
	
	
}
