package com.LogProcessorApplication.utlis;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class PaymentRatioCalc {

	public double paymentRatioCalculator(int totalNumberOfPayments, int numberOfDoubleBookings) {
		
		BigDecimal totalPayments = new BigDecimal(totalNumberOfPayments);
		BigDecimal doubleBookings = new BigDecimal(numberOfDoubleBookings*100);
		BigDecimal ratio = doubleBookings.divide(totalPayments, 4, RoundingMode.HALF_UP);
		
		
		
		 return ratio.doubleValue()*10;
	}
}
