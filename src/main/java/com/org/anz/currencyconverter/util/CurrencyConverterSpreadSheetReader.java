package com.org.anz.currencyconverter.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.org.anz.currencyconverter.model.CrossViaMatrixData;

public interface CurrencyConverterSpreadSheetReader {

	List<CrossViaMatrixData> readCrossViaMatrixData();

	Map<String, BigDecimal> readDirectFeed();

}