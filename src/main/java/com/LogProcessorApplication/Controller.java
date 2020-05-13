package com.LogProcessorApplication;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LogProcessorApplication.services.PaymentsService;
import com.LogProcessorApplication.services.UserService;
import com.LogProcessorApplication.utlis.OutputPrinter;
import com.LogProcessorApplication.utlis.PaymentRatioCalc;

@RestController
@Transactional
public class Controller {

	@Autowired
	private PaymentsService ps;
	
	@Autowired
	private UserService us;
	

	@RequestMapping(path="start")
	public void setDataInDb() {
		try {
			System.out.println("!!!!!!!!!!!!!!!!!!!!!START!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			ps.userRecordReader(LogProcessorApplication.RECORDS_PATH);
			ps.paymentRecordReader(LogProcessorApplication.RECORDS_PATH);
			System.out.println("!!!!!!!!!!!!!!!!!!!!!END!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		PaymentRatioCalc ratioCalc = new PaymentRatioCalc();
		double ratio = ratioCalc.paymentRatioCalculator(ps.getTotalPaymentsCounter(), ps.getDuplicatePaymentsCounter());
		
		OutputPrinter printer = new OutputPrinter();
		printer.printerPaymentRation(ratio, ps.getTotalPaymentsCounter(), ps.getDuplicatePaymentsCounter());
		printer.printMostAndLessCommonNames(ps.lessAndMostCommonNameCalc());
		printer.printTopTenPayingUsers(us.topPayingUsersCalc());
		
		
		HibernateConf.shutdown();
	}
}
