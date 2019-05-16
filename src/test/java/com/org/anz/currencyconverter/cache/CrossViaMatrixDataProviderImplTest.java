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

import com.org.anz.currencyconverter.model.CrossViaMatrixData;
import com.org.anz.currencyconverter.util.CurrencyConverterSpreadSheetReader;

@RunWith(MockitoJUnitRunner.class)
public class CrossViaMatrixDataProviderImplTest {
	
	@Mock
	private CurrencyConverterSpreadSheetReader currencyConverterSpreadSheetReader;

	@InjectMocks
	private CrossViaMatrixDataProviderImpl crossViaMatrixDataProviderImpl;
	
	@Test
	public void testGetCalculationModel() {
		List<CrossViaMatrixData> crossViaMatrixData = Arrays.asList(new CrossViaMatrixData("AUD", "USD", "Inv"));
		when(currencyConverterSpreadSheetReader.readCrossViaMatrixData()).thenReturn(crossViaMatrixData);
		crossViaMatrixDataProviderImpl.readCrossViaMatrixData();
		Assert.assertEquals("Inv", crossViaMatrixDataProviderImpl.getCalculationModel("AUDUSD"));
	}

}