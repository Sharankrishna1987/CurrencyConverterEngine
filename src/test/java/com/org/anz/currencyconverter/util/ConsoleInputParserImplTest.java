package com.org.anz.currencyconverter.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import com.org.anz.currencyconverter.model.ConsoleRequestData;
import com.org.anz.currencyconverter.validator.CurrencyConverterValidator;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleInputParserImplTest {

	@Mock
	private CurrencyConverterValidator currencyConverterValidator;

	@InjectMocks
	private ConsoleInputParserImpl consoleInputParserImpl;
	
	@Test
	public void testParse() {
		doNothing().when(currencyConverterValidator).validate("AUD 100.00 in USD");
		ConsoleRequestData consoleRequestData = consoleInputParserImpl.parse("AUD 100.00 in USD");
		Assert.assertEquals("AUD", consoleRequestData.getBase());
		Assert.assertEquals("USD", consoleRequestData.getTerms());
		Assert.assertEquals(new BigDecimal("100.00"), consoleRequestData.getAmount());
	}

}