package com.org.anz.currencyconverter.wrapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface ApplicationWrapper {

	InputStream createInputStream(String fileLocation) throws FileNotFoundException;

	XSSFWorkbook createXSSFWorkbook(InputStream inputStream) throws IOException;

}
