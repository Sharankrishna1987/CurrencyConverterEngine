package com.org.anz.currencyconverter.main;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.anz.currencyconverter.cache.CrossViaMatrixDataProvider;
import com.org.anz.currencyconverter.exception.ValidationException;
import com.org.anz.currencyconverter.model.ConsoleRequestData;
import com.org.anz.currencyconverter.util.ConsoleInputParser;
import com.org.anz.currencyconverter.util.CurrencyCalculator;

@Component
public class CurrencyConverterImpl implements CurrencyConverter {

	@Autowired
	private ConsoleInputParser consoleInputParser;

	@Autowired
	private CurrencyCalculator currencyCalculator;

	@Autowired
	private CrossViaMatrixDataProvider crossViaMatrixDataProvider;

	@Override
	public String convert(String input) {

		ConsoleRequestData consoleRequestData = consoleInputParser.parse(input);

		if (consoleRequestData.getBase().equals(consoleRequestData.getTerms())) {
			return buildResponse(consoleRequestData, consoleRequestData.getAmount());
		}

		List<String> calculationSteps = crossViaMatrixDataProvider.getCalculationSteps(consoleRequestData);

		if (calculationSteps == null) {
			throw new ValidationException(String.format("Unable to find rate for %s/%s", consoleRequestData.getBase(),
					consoleRequestData.getTerms()));
		}

		return buildResponse(consoleRequestData, currencyCalculator.calculate(calculationSteps, consoleRequestData));

	}

	private String buildResponse(ConsoleRequestData consoleRequestData, BigDecimal convertedValue) {
		return String.format("%s %s = %s %s", consoleRequestData.getBase(), consoleRequestData.getAmount(),
				consoleRequestData.getTerms(), convertedValue);
	}

}
