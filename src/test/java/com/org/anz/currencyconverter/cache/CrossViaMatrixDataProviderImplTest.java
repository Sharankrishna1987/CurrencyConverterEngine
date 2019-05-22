package com.org.anz.currencyconverter.cache;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.org.anz.currencyconverter.model.ConsoleRequestData;
import com.org.anz.currencyconverter.model.CrossViaMatrixData;
import com.org.anz.currencyconverter.util.CurrencyConverterSpreadSheetReader;

@RunWith(MockitoJUnitRunner.class)
public class CrossViaMatrixDataProviderImplTest {
	
	@Mock
	private CurrencyConverterSpreadSheetReader currencyConverterSpreadSheetReader;

	@InjectMocks
	private CrossViaMatrixDataProviderImpl crossViaMatrixDataProviderImpl;
	
	@Test
	public void testGetCalculationStepsForDirect() {
		List<CrossViaMatrixData> crossViaMatrixData = Arrays.asList(new CrossViaMatrixData("AUD", "USD", "D"));
		when(currencyConverterSpreadSheetReader.readCrossViaMatrixData()).thenReturn(crossViaMatrixData);
		crossViaMatrixDataProviderImpl.readCrossViaMatrixData();
		ConsoleRequestData consoleRequestData = new ConsoleRequestData.Builder().base("AUD").terms("USD").build();
		Assert.assertEquals(Arrays.asList("AUDUSD"), crossViaMatrixDataProviderImpl.getCalculationSteps(consoleRequestData));
	}
	
	@Test
	public void testGetCalculationStepsForInverted() {
		List<CrossViaMatrixData> crossViaMatrixData = Arrays.asList(new CrossViaMatrixData("JPY", "USD", "Inv"));
		when(currencyConverterSpreadSheetReader.readCrossViaMatrixData()).thenReturn(crossViaMatrixData);
		crossViaMatrixDataProviderImpl.readCrossViaMatrixData();
		ConsoleRequestData consoleRequestData = new ConsoleRequestData.Builder().base("JPY").terms("USD").build();
		Assert.assertEquals(Arrays.asList("JPYUSD"), crossViaMatrixDataProviderImpl.getCalculationSteps(consoleRequestData));
	}
	
	@Test
	public void testGetCalculationStepsForUnity() {
		List<CrossViaMatrixData> crossViaMatrixData = Arrays.asList(new CrossViaMatrixData("AUD", "AUD", "1:1"));
		when(currencyConverterSpreadSheetReader.readCrossViaMatrixData()).thenReturn(crossViaMatrixData);
		crossViaMatrixDataProviderImpl.readCrossViaMatrixData();
		ConsoleRequestData consoleRequestData = new ConsoleRequestData.Builder().base("AUD").terms("AUD").build();
		Assert.assertEquals(null, crossViaMatrixDataProviderImpl.getCalculationSteps(consoleRequestData));
	}
	
	@Test
	public void testGetCalculationStepsForCrossCurrency() {
		List<CrossViaMatrixData> crossViaMatrixData = Arrays.asList(new CrossViaMatrixData("AUD", "DKK", "USD"), new CrossViaMatrixData("AUD", "USD", "D"),
				new CrossViaMatrixData("USD", "EUR", "Inv"), new CrossViaMatrixData("EUR", "DKK", "D"), new CrossViaMatrixData("USD", "DKK", "EUR"));
		when(currencyConverterSpreadSheetReader.readCrossViaMatrixData()).thenReturn(crossViaMatrixData);
		crossViaMatrixDataProviderImpl.readCrossViaMatrixData();
		ConsoleRequestData consoleRequestData = new ConsoleRequestData.Builder().base("AUD").terms("DKK").build();
		Assert.assertEquals(Arrays.asList("AUDUSD", "USDEUR", "EURDKK"), crossViaMatrixDataProviderImpl.getCalculationSteps(consoleRequestData));
	}

}
