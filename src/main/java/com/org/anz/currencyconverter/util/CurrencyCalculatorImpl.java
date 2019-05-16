package com.org.anz.currencyconverter.util;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.anz.currencyconverter.model.ConsoleRequestData;

@Component
public class CurrencyCalculatorImpl implements CurrencyCalculator {
	
	@Autowired
	private CurrencyConverterSpreadSheetReader currencyConverterSpreadSheetReader;
	
	@Override
	public BigDecimal calculateConversionRateForDirectFeed(ConsoleRequestData consoleRequestData) {
		
		Map<String, BigDecimal> directFeed = currencyConverterSpreadSheetReader.readDirectFeed();
		String inputCurrencyPair = consoleRequestData.getBase().concat(consoleRequestData.getTerms());

		Currency currency = Currency.getInstance(consoleRequestData.getTerms());

		BigDecimal convertedValue = consoleRequestData.getAmount().multiply(directFeed.get(inputCurrencyPair));
		convertedValue = convertedValue.setScale(currency.getDefaultFractionDigits(), BigDecimal.ROUND_DOWN);

		return convertedValue;

	}

	@Override
	public BigDecimal calculateConversionRateForInverted(ConsoleRequestData consoleRequestData) {
		
		Map<String, BigDecimal> directFeed = currencyConverterSpreadSheetReader.readDirectFeed();
		String inputCurrencyPair = consoleRequestData.getTerms().concat(consoleRequestData.getBase());

		Currency currency = Currency.getInstance(consoleRequestData.getTerms());

		BigDecimal convertedValue = consoleRequestData.getAmount().divide(directFeed.get(inputCurrencyPair), currency.getDefaultFractionDigits(), BigDecimal.ROUND_DOWN);

		return convertedValue;

	}

	@Override
	public BigDecimal calculateConversionRateForCrossViaCurrency(ConsoleRequestData consoleRequestData, String crossCurrency) {

		Map<String, BigDecimal> directFeed = currencyConverterSpreadSheetReader.readDirectFeed();
		
		String inputCurrencyPairForBase = consoleRequestData.getBase().concat(crossCurrency);
		BigDecimal directFeedCrossViaBaseValue = consoleRequestData.getAmount().multiply(directFeed.get(inputCurrencyPairForBase));
		
		String inputCurrencyPairForTerm = crossCurrency.concat(consoleRequestData.getTerms());
		BigDecimal directFeedCrossViaTermValue = directFeedCrossViaBaseValue.multiply(directFeed.get(inputCurrencyPairForTerm));
		
		Currency currency = Currency.getInstance(consoleRequestData.getTerms());
		directFeedCrossViaTermValue = directFeedCrossViaTermValue.setScale(currency.getDefaultFractionDigits(), BigDecimal.ROUND_DOWN);
				
		return directFeedCrossViaTermValue;

	}
	
}