package com.org.anz.currencyconverter;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.org.anz.currencyconverter.exception.ValidationException;
import com.org.anz.currencyconverter.main.CurrencyConverter;
import com.org.anz.currencyconverter.wrapper.ApplicationWrapper;

@SpringBootApplication
public class CurrencyConverterEngineApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConverterEngineApplication.class);

	@Autowired
	private CurrencyConverter currencyConverter;
	
	@Autowired
	private ApplicationWrapper applicationWrapper;

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConverterEngineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		try (Scanner scanner = applicationWrapper.getScanner()) {

			while (true) {
				
				try {
					LOGGER.info("Please enter you request:");
					String input = scanner.nextLine();

					if ("exit".equals(input)) {
						LOGGER.info("Currency Converter exit!!!");
						break;
					}

					LOGGER.info(String.format("Converted rate response: %s", currencyConverter.convert(input)));
					LOGGER.info("-------------------------------------------------------");
				} catch (ValidationException validatorException) {
					LOGGER.error(validatorException.getMessage());
					LOGGER.info("-------------------------------------------------------");
				} catch (Exception exception) {
					LOGGER.info("-------------------------------------------------------");
				}

			}
		}

	}

}
