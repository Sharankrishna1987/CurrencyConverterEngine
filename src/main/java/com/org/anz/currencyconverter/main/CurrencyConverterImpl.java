package com.org.anz.currencyconverter.main;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.anz.currencyconverter.cache.CrossViaMatrixDataProvider;
import com.org.anz.currencyconverter.exception.ValidationException;
import com.org.anz.currencyconverter.model.ConsoleRequestData;
import com.org.anz.currencyconverter.util.ConsoleInputParser;
import com.org.anz.currencyconverter.util.CurrencyCalculator;

@Component
public class CurrencyConverterImpl implements CurrencyConverter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConverterImpl.class);

	@Autowired
	private ConsoleInputParser consoleInputParser;

	@Autowired
	private CurrencyCalculator currencyCalculator;

	@Autowired
	private CrossViaMatrixDataProvider crossViaMatrixDataProvider;

	@Override
	public String convert(String input) {

		ConsoleRequestData consoleRequestData = consoleInputParser.parse(input);
		String inputCurrencyPair = consoleRequestData.getBase().concat(consoleRequestData.getTerms());
		String calculationModel = crossViaMatrixDataProvider.getCalculationModel(inputCurrencyPair);

		if (calculationModel == null) {
			throw new ValidationException(String.format("Unable to find rate for %s/%s", consoleRequestData.getBase(),
					consoleRequestData.getTerms()));
		}
		
		LOGGER.info(String.format("Calculation Model: %s", calculationModel));

		switch (calculationModel) {

		case "1:1":
			return buildResponse(consoleRequestData, consoleRequestData.getAmount());

		case "D":
			return buildResponse(consoleRequestData, currencyCalculator.calculateConversionRateForDirectFeed(consoleRequestData));

		case "Inv":
			return buildResponse(consoleRequestData, currencyCalculator.calculateConversionRateForInverted(consoleRequestData));
			
		default:
			return buildResponse(consoleRequestData, currencyCalculator.calculateConversionRateForCrossViaCurrency(consoleRequestData, calculationModel));

		}

	}

	private String buildResponse(ConsoleRequestData consoleRequestData, BigDecimal convertedValue) {
		return String.format("%s %s = %s %s", consoleRequestData.getBase(), consoleRequestData.getAmount(),
				consoleRequestData.getTerms(), convertedValue);
	}

}