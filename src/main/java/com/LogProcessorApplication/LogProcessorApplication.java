package com.LogProcessorApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@SpringBootApplication
@EnableTransactionManagement
public class LogProcessorApplication {

	public static final String RECORDS_PATH = "D:\\CloudShareCodeChallenge\\Challenge";
	public static final String OUTPUT_FILE_PATH = "D:\\CloudShareCodeChallenge\\Work\\result.log";
	
	public static void main(String[] args) {
		SpringApplication.run(LogProcessorApplication.class, args);
	}

}