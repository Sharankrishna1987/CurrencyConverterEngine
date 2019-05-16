package com.org.anz.currencyconverter.validator;

import org.springframework.stereotype.Component;

import com.org.anz.currencyconverter.exception.ValidationException;

@Component
public class CurrencyConverterValidatorImpl implements CurrencyConverterValidator {
	
	@Override
	public void validate(String input) {

		if (input == null || "".equals(input.trim())) {
			throw new ValidationException("Input cannot be empty");
		}

		String[] inputData = input.split("\\s+");

		if (inputData.length != 4) {
			throw new ValidationException(
					"Input format is incorrect. The correct format is '<FromCurrency> <amount> in <ToCurrency>'");
		}

		String fromCurrency = inputData[0];
		String toCurrency = inputData[3];
		String amount = inputData[1];

		if (fromCurrency.length() != 3 || toCurrency.length() != 3) {
			throw new ValidationException("Currency code should be 3 in characters. Ex. USD, AUD");
		}
		
		if (amount.equals("0")) {
			throw new ValidationException("Amount cannot be zero");
		}

		if (amount.matches("((\\d+)((\\.\\d{1,2})?))$") == false) {
			throw new ValidationException("Invalid currency amount");
		}

	}

}