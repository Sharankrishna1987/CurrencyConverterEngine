package com.org.anz.currencyconverter.util;

import java.math.BigDecimal;

import com.org.anz.currencyconverter.model.ConsoleRequestData;

public interface CurrencyCalculator {

	BigDecimal calculateConversionRateForDirectFeed(ConsoleRequestData consoleRequestData);

	BigDecimal calculateConversionRateForInverted(ConsoleRequestData consoleRequestData);

	BigDecimal calculateConversionRateForCrossViaCurrency(ConsoleRequestData consoleRequestData, String crossCurrency);

}