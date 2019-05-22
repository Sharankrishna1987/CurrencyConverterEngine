package com.org.anz.currencyconverter.main;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;

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
	public void testConvert() {
		ConsoleRequestData consoleRequestData = new ConsoleRequestData.Builder().base("AUD").terms("USD").amount(new BigDecimal("100.00")).build();
		when(consoleInputParser.parse("AUD 100.00 in USD")).thenReturn(consoleRequestData);
		when(crossViaMatrixDataProvider.getCalculationSteps(consoleRequestData)).thenReturn(Arrays.asList("AUDUSD"));
		when(currencyCalculator.calculate(Arrays.asList("AUDUSD"), consoleRequestData)).thenReturn(new BigDecimal("83.71"));
		Assert.assertEquals("AUD 100.00 = USD 83.71", currencyConverterImpl.convert("AUD 100.00 in USD"));
	}
	
	@Test
	public void testConvertForUnity() {
		ConsoleRequestData consoleRequestData = new ConsoleRequestData.Builder().base("AUD").terms("AUD").amount(new BigDecimal("100.00")).build();
		when(consoleInputParser.parse("AUD 100.00 in AUD")).thenReturn(consoleRequestData);
		Assert.assertEquals("AUD 100.00 = AUD 100.00", currencyConverterImpl.convert("AUD 100.00 in AUD"));
	}
	
	@Test
	public void testConvertForIncorrectCurrencyPair() {
		ConsoleRequestData consoleRequestData = new ConsoleRequestData.Builder().base("KRW").terms("FJD").amount(new BigDecimal("1000.00")).build();
		when(consoleInputParser.parse("KRW 1000.00 in FJD")).thenReturn(consoleRequestData);
		when(crossViaMatrixDataProvider.getCalculationSteps(consoleRequestData)).thenReturn(null);
		expectedException.expect(ValidationException.class);
		expectedException.expectMessage("Unable to find rate for KRW/FJD");
		currencyConverterImpl.convert("KRW 1000.00 in FJD");
	}
	
}
