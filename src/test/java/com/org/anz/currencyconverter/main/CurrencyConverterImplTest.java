package com.org.anz.currencyconverter.main;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.org.anz.currencyconverter.cache.CrossViaMatrixDataProvider;
import com.org.anz.currencyconverter.exception.ValidationException;
import com.org.anz.currencyconverter.model.ConsoleRequestData;
import com.org.anz.currencyconverter.util.ConsoleInputParser;
import com.org.anz.currencyconverter.util.CurrencyCalculator;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConverterImplTest {
	
	@Rule
	public final ExpectedException expectedException = ExpectedException.none();
	
	@Mock
	private ConsoleInputParser consoleInputParser;
	
	@Mock
	private CurrencyCalculator currencyCalculator;
	
	@Mock
	private CrossViaMatrixDataProvider crossViaMatrixDataProvider;

	@InjectMocks
	private CurrencyConverterImpl currencyConverterImpl;
	
	@Test
	public void testConvertForUnity() {
		ConsoleRequestData consoleRequestData = new ConsoleRequestData.Builder().base("AUD").terms("AUD").amount(new BigDecimal("100.00")).build();
		when(consoleInputParser.parse("AUD 100.00 in AUD")).thenReturn(consoleRequestData);
		when(crossViaMatrixDataProvider.getCalculationModel("AUDAUD")).thenReturn("1:1");
		Assert.assertEquals("AUD 100.00 = AUD 100.00", currencyConverterImpl.convert("AUD 100.00 in AUD"));
	}
	
	@Test
	public void testConvertForDirectFeed() {
		ConsoleRequestData consoleRequestData = new ConsoleRequestData.Builder().base("AUD").terms("USD").amount(new BigDecimal("100.00")).build();
		when(consoleInputParser.parse("AUD 100.00 in USD")).thenReturn(consoleRequestData);
		when(crossViaMatrixDataProvider.getCalculationModel("AUDUSD")).thenReturn("D");
		when(currencyCalculator.calculateConversionRateForDirectFeed(consoleRequestData)).thenReturn(new BigDecimal("83.71"));
		Assert.assertEquals("AUD 100.00 = USD 83.71", currencyConverterImpl.convert("AUD 100.00 in USD"));
	}
	
	@Test
	public void testConvertForInverted() {
		ConsoleRequestData consoleRequestData = new ConsoleRequestData.Builder().base("JPY").terms("USD").amount(new BigDecimal("100")).build();
		when(consoleInputParser.parse("JPY 100 in USD")).thenReturn(consoleRequestData);
		when(crossViaMatrixDataProvider.getCalculationModel("JPYUSD")).thenReturn("Inv");
		when(currencyCalculator.calculateConversionRateForInverted(consoleRequestData)).thenReturn(new BigDecimal("0.83"));
		Assert.assertEquals("JPY 100 = USD 0.83", currencyConverterImpl.convert("JPY 100 in USD"));
	}
	
	@Test
	public void testConvertForCrossCurrency() {
		ConsoleRequestData consoleRequestData = new ConsoleRequestData.Builder().base("AUD").terms("DKK").amount(new BigDecimal("100.00")).build();
		when(consoleInputParser.parse("AUD 100.00 in DKK")).thenReturn(consoleRequestData);
		when(crossViaMatrixDataProvider.getCalculationModel("AUDDKK")).thenReturn("EUR");
		when(currencyCalculator.calculateConversionRateForCrossViaCurrency(consoleRequestData, "EUR")).thenReturn(new BigDecimal("505.76"));
		Assert.assertEquals("AUD 100.00 = DKK 505.76", currencyConverterImpl.convert("AUD 100.00 in DKK"));
	}
	
	@Test
	public void testConvertForInvalidCalculationModel() {
		ConsoleRequestData consoleRequestData = new ConsoleRequestData.Builder().base("KRW").terms("FJD").amount(new BigDecimal("1000.00")).build();
		when(consoleInputParser.parse("KRW 1000.00 in FJD")).thenReturn(consoleRequestData);
		when(crossViaMatrixDataProvider.getCalculationModel("KRWFJD")).thenReturn(null);
		expectedException.expect(ValidationException.class);
		expectedException.expectMessage("Unable to find rate for KRW/FJD");
		currencyConverterImpl.convert("KRW 1000.00 in FJD");
	}

}