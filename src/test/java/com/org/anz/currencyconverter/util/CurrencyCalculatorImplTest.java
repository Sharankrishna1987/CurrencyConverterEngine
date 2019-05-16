package com.org.anz.currencyconverter.util;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.org.anz.currencyconverter.model.ConsoleRequestData;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyCalculatorImplTest {

	@Mock
	private CurrencyConverterSpreadSheetReader currencyConverterSpreadSheetReader;

	@InjectMocks
	private CurrencyCalculatorImpl currencyCalculatorImpl;

	@Test
	public void testCalculateConversionRateForDirectFeed() {
		Map<String, BigDecimal> directFeed = new HashMap<>();
		directFeed.put("AUDUSD", new BigDecimal("0.8371"));
		ConsoleRequestData consoleRequestData = new ConsoleRequestData.Builder().base("AUD").terms("USD").amount(new BigDecimal("100.00")).build();
		when(currencyConverterSpreadSheetReader.readDirectFeed()).thenReturn(directFeed);
		Assert.assertEquals(new BigDecimal("83.71"), currencyCalculatorImpl.calculateConversionRateForDirectFeed(consoleRequestData));
	}
	
	@Test
	public void testCalculateConversionRateForInverted() {
		Map<String, BigDecimal> directFeed = new HashMap<>();
		directFeed.put("USDJPY", new BigDecimal("119.95"));
		ConsoleRequestData consoleRequestData = new ConsoleRequestData.Builder().base("JPY").terms("USD").amount(new BigDecimal("100")).build();
		when(currencyConverterSpreadSheetReader.readDirectFeed()).thenReturn(directFeed);
		Assert.assertEquals(new BigDecimal("0.83"), currencyCalculatorImpl.calculateConversionRateForInverted(consoleRequestData));
	}
	
	@Test
	public void testCalculateConversionRateForCrossViaCurrency() {
		Map<String, BigDecimal> directFeed = new HashMap<>();
		directFeed.put("AUDUSD", new BigDecimal("0.8371"));
		directFeed.put("USDJPY", new BigDecimal("119.95"));
		ConsoleRequestData consoleRequestData = new ConsoleRequestData.Builder().base("AUD").terms("JPY").amount(new BigDecimal("1")).build();
		when(currencyConverterSpreadSheetReader.readDirectFeed()).thenReturn(directFeed);
		Assert.assertEquals(new BigDecimal("100"), currencyCalculatorImpl.calculateConversionRateForCrossViaCurrency(consoleRequestData, "USD"));
	}

}