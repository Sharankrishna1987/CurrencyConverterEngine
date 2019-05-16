package com.org.anz.currencyconverter.cache;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.anz.currencyconverter.model.CrossViaMatrixData;
import com.org.anz.currencyconverter.util.CurrencyConverterSpreadSheetReader;

//The values in this class are cached as I assume that the matrix doesn't change frequently
@Component
public class CrossViaMatrixDataProviderImpl implements CrossViaMatrixDataProvider {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CrossViaMatrixDataProviderImpl.class);

	@Autowired
	private CurrencyConverterSpreadSheetReader currencyConverterSpreadSheetReader;

	private Map<String, String> crossViaMatrix;

	@PostConstruct
	public void readCrossViaMatrixData() {

		List<CrossViaMatrixData> crossViaMatrixData = currencyConverterSpreadSheetReader.readCrossViaMatrixData();

		crossViaMatrix = crossViaMatrixData.stream().collect(
				Collectors.toMap(e -> e.getBase().concat(e.getTerms()), CrossViaMatrixData::getCalculationModel));
	}

	@Override
	public String getCalculationModel(String currencyPair) {
		LOGGER.info(String.format("Currency pair: %s", currencyPair));
		return crossViaMatrix.get(currencyPair);
	}

}