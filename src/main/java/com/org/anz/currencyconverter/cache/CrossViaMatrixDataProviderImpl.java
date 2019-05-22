package com.org.anz.currencyconverter.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.anz.currencyconverter.constant.ApplicationConstant;
import com.org.anz.currencyconverter.model.ConsoleRequestData;
import com.org.anz.currencyconverter.model.CrossViaMatrixData;
import com.org.anz.currencyconverter.util.CurrencyConverterSpreadSheetReader;

//The values in this class are cached as I assume that the matrix doesn't change frequently
@Component
public class CrossViaMatrixDataProviderImpl implements CrossViaMatrixDataProvider {

	@Autowired
	private CurrencyConverterSpreadSheetReader currencyConverterSpreadSheetReader;

	private Map<String, String> crossViaMatrix;

	private Map<ConsoleRequestData, List<String>> modifiedCrossViaMatrix = new HashMap<>();

	@PostConstruct
	public void readCrossViaMatrixData() {

		List<CrossViaMatrixData> crossViaMatrixData = currencyConverterSpreadSheetReader.readCrossViaMatrixData();

		crossViaMatrix = crossViaMatrixData.stream().collect(
				Collectors.toMap(e -> e.getBase().concat(e.getTerms()), CrossViaMatrixData::getCalculationModel));

		createCalculationSteps();

	}

	@Override
	public List<String> getCalculationSteps(ConsoleRequestData consoleRequestData) {
		return modifiedCrossViaMatrix.get(consoleRequestData);
	}

	private void createCalculationSteps() {

		List<String> finalStatesInCalculationModel = Arrays.asList(ApplicationConstant.UNITY_VALUE,
				ApplicationConstant.DIRECT_FEED_VALUE, ApplicationConstant.INVERTED_VALUE);

		for (String currencyCombination : crossViaMatrix.keySet()) {
			
			String baseCurrency = currencyCombination.substring(0, 3);
			String termsCurrency = currencyCombination.substring(3);

			if (baseCurrency.equals(termsCurrency)) {
				continue;
			}

			String calculationModel = null;
			String fromCurrencyTemp = baseCurrency;
			List<String> calculationSteps = new ArrayList<String>();

			do {

				calculationModel = crossViaMatrix.get(fromCurrencyTemp + termsCurrency);

				if (finalStatesInCalculationModel.contains(calculationModel)) {
					calculationSteps.add(fromCurrencyTemp + termsCurrency);
				} else {
					calculationSteps.add(fromCurrencyTemp + calculationModel);
				}

				fromCurrencyTemp = calculationModel;

			} while (!finalStatesInCalculationModel.contains(calculationModel));

			modifiedCrossViaMatrix.put(new ConsoleRequestData.Builder().base(baseCurrency).terms(termsCurrency).build(),
					calculationSteps);
		}
		
	}

}
