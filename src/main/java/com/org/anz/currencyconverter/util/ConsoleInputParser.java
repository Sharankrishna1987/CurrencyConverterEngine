package com.org.anz.currencyconverter.util;

import com.org.anz.currencyconverter.model.ConsoleRequestData;

public interface ConsoleInputParser {

	ConsoleRequestData parse(String input);

}