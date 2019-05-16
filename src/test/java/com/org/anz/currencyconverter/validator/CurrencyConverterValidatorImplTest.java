package com.org.anz.currencyconverter.validator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.org.anz.currencyconverter.exception.ValidationException;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConverterValidatorImplTest {
	
	@Rule
	public final ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void testValidateWithCorrectData() {
		CurrencyConverterValidatorImpl currencyConverterValidatorImpl = new CurrencyConverterValidatorImpl();
		currencyConverterValidatorImpl.validate("AUD 100.00 in USD");
	}
	
	@Test
	public void testValidateWithNullData() {
		CurrencyConverterValidatorImpl currencyConverterValidatorImpl = new CurrencyConverterValidatorImpl();
		expectedException.expect(ValidationException.class);
		expectedException.expectMessage("Input cannot be empty");
		currencyConverterValidatorImpl.validate(null);
	}
	
	@Test
	public void testValidateWithEmptyData() {
		CurrencyConverterValidatorImpl currencyConverterValidatorImpl = new CurrencyConverterValidatorImpl();
		expectedException.expect(ValidationException.class);
		expectedException.expectMessage("Input cannot be empty");
		currencyConverterValidatorImpl.validate(" ");
	}
	
	@Test
	public void testValidateWithIncorrectData() {
		CurrencyConverterValidatorImpl currencyConverterValidatorImpl = new CurrencyConverterValidatorImpl();
		expectedException.expect(ValidationException.class);
		expectedException.expectMessage("Input format is incorrect. The correct format is '<FromCurrency> <amount> in <ToCurrency>'");
		currencyConverterValidatorImpl.validate("AUD 100.00 in");
	}
	
	@Test
	public void testValidateWithIncorrectBaseCurrency() {
		CurrencyConverterValidatorImpl currencyConverterValidatorImpl = new CurrencyConverterValidatorImpl();
		expectedException.expect(ValidationException.class);
		expectedException.expectMessage("Currency code should be 3 in characters. Ex. USD, AUD");
		currencyConverterValidatorImpl.validate("AUDS 100.00 in USD");
	}
	
	@Test
	public void testValidateWithIncorrectTermsCurrency() {
		CurrencyConverterValidatorImpl currencyConverterValidatorImpl = new CurrencyConverterValidatorImpl();
		expectedException.expect(ValidationException.class);
		expectedException.expectMessage("Currency code should be 3 in characters. Ex. USD, AUD");
		currencyConverterValidatorImpl.validate("AUD 100.00 in USDS");
	}
	
	@Test
	public void testValidateWithZeroAmount() {
		CurrencyConverterValidatorImpl currencyConverterValidatorImpl = new CurrencyConverterValidatorImpl();
		expectedException.expect(ValidationException.class);
		expectedException.expectMessage("Amount cannot be zero");
		currencyConverterValidatorImpl.validate("AUD 0 in USD");
	}
	
	@Test
	public void testValidateWithIncorrectAmount() {
		CurrencyConverterValidatorImpl currencyConverterValidatorImpl = new CurrencyConverterValidatorImpl();
		expectedException.expect(ValidationException.class);
		expectedException.expectMessage("Invalid currency amount");
		currencyConverterValidatorImpl.validate("AUD -100.00a in USD");
	}

}