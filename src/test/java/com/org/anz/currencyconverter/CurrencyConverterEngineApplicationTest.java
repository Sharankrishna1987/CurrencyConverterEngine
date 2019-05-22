package com.org.anz.currencyconverter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.org.anz.currencyconverter.exception.ValidationException;
import com.org.anz.currencyconverter.main.CurrencyConverter;
import com.org.anz.currencyconverter.wrapper.ApplicationWrapper;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConverterEngineApplicationTest {
	
	@Rule
	public final ExpectedException expectedException = ExpectedException.none();
	
	@Mock
	private CurrencyConverter currencyConverter;
	
	@Mock
	private ApplicationWrapper applicationWrapper;

	@InjectMocks
	private CurrencyConverterEngineApplication currencyConverterEngineApplication;
	
	@Test
	public void testRun() throws Exception {
		Scanner scanner = mock(Scanner.class); 
		when(applicationWrapper.getScanner()).thenReturn(scanner);
		when(scanner.nextLine()).thenReturn("AUD 100.00 in DKK", "exit");
		when(currencyConverter.convert("AUD 100.00 in DKK")).thenReturn("AUD 100.00 = DKK 505.76");
		currencyConverterEngineApplication.run("");
	}
	
	@Test
	public void testRunThrowsValidationException() throws Exception {
		Scanner scanner = mock(Scanner.class); 
		when(applicationWrapper.getScanner()).thenReturn(scanner);
		when(scanner.nextLine()).thenReturn("AUD 100.00 in DKK", "exit");
		when(currencyConverter.convert("AUD 100.00 in DKK")).thenThrow(ValidationException.class);
		currencyConverterEngineApplication.run("");
	}
	
	@Test
	public void testRunThrowsException() throws Exception {
		Scanner scanner = mock(Scanner.class); 
		when(applicationWrapper.getScanner()).thenReturn(scanner);
		when(scanner.nextLine()).thenReturn("AUD 100.00 in DKK", "exit");
		when(currencyConverter.convert("AUD 100.00 in DKK")).thenThrow(RuntimeException.class);
		currencyConverterEngineApplication.run("");
	}

}
