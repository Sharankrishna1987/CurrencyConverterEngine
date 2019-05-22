package com.org.anz.currencyconverter.util;

import java.math.BigDecimal;
import java.util.List;

import com.org.anz.currencyconverter.model.ConsoleRequestData;

public interface CurrencyCalculator {

	BigDecimal calculate(List<String> calculationSteps, ConsoleRequestData consoleRequestData);

}
