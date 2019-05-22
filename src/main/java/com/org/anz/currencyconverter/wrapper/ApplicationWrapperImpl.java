package com.org.anz.currencyconverter.wrapper;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class ApplicationWrapperImpl implements ApplicationWrapper {

	@Override
	public InputStream createInputStream(String fileLocation) throws FileNotFoundException {
		return new BufferedInputStream(this.getClass().getClassLoader().getResourceAsStream(fileLocation));
	}

	@Override
	public XSSFWorkbook createXSSFWorkbook(InputStream inputStream) throws IOException {
		return new XSSFWorkbook(inputStream);
	}
	
	@Override
	public Scanner getScanner() {
		return new Scanner(System.in);
	}
	
}
