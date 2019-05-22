package com.org.anz.currencyconverter.cache;

import java.util.List;

import com.org.anz.currencyconverter.model.ConsoleRequestData;

public interface CrossViaMatrixDataProvider {

	List<String> getCalculationSteps(ConsoleRequestData consoleRequestData);

}
