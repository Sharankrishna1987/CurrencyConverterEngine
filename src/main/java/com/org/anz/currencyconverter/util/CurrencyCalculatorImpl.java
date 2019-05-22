package com.org.anz.currencyconverter.util;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.anz.currencyconverter.model.ConsoleRequestData;

@Component
public class CurrencyCalculatorImpl implements CurrencyCalculator {
	
	@Autowired
	private CurrencyConverterSpreadSheetReader currencyConverterSpreadSheetReader;
	
	@Override
	public BigDecimal calculate(List<String> calculationSteps, ConsoleRequestData consoleRequestData) {

		Map<String, BigDecimal> directFeed = currencyConverterSpreadSheetReader.readDirectFeed();
		BigDecimal totalAmount = BigDecimal.ONE;

		for (String calculationStep : calculationSteps) {
			totalAmount = totalAmount.multiply(directFeed.get(calculationStep));
		}

		Currency currency = Currency.getInstance(consoleRequestData.getTerms());

		BigDecimal convertedAmount = consoleRequestData.getAmount().multiply(totalAmount);
		convertedAmount = convertedAmount.setScale(currency.getDefaultFractionDigits(), BigDecimal.ROUND_DOWN);

		return convertedAmount;

	}

}
