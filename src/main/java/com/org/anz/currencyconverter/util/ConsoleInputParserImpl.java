package com.org.anz.currencyconverter.util;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.anz.currencyconverter.model.ConsoleRequestData;
import com.org.anz.currencyconverter.validator.CurrencyConverterValidator;

@Component
public class ConsoleInputParserImpl implements ConsoleInputParser {

	@Autowired
	private CurrencyConverterValidator currencyConverterValidator;

	@Override
	public ConsoleRequestData parse(String input) {

		currencyConverterValidator.validate(input);

		String[] inputData = input.split("\\s+");

		String fromCurrency = inputData[0];
		String toCurrency = inputData[3];
		String amount = inputData[1];

		return new ConsoleRequestData.Builder().base(fromCurrency).terms(toCurrency).amount(new BigDecimal(amount))
				.build();

	}

}