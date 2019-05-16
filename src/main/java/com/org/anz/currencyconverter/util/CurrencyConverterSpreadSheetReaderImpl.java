package com.org.anz.currencyconverter.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.anz.currencyconverter.constant.ApplicationConstant;
import com.org.anz.currencyconverter.exception.FileUtilException;
import com.org.anz.currencyconverter.model.CrossViaMatrixData;
import com.org.anz.currencyconverter.wrapper.ApplicationWrapper;

@Component
public class CurrencyConverterSpreadSheetReaderImpl implements CurrencyConverterSpreadSheetReader {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConverterSpreadSheetReaderImpl.class);

	@Autowired
	private ApplicationWrapper applicationWrapper;

	@Override
	public List<CrossViaMatrixData> readCrossViaMatrixData() {

		try {

			List<CrossViaMatrixData> crossViaMatrixData = new LinkedList<CrossViaMatrixData>();

			Sheet sheet;
			InputStream inputStream = applicationWrapper.createInputStream(ApplicationConstant.CROSS_VIA_MATRIX_FILE_LOCATION);

			try (Workbook workbook = applicationWrapper.createXSSFWorkbook(inputStream)) {
				sheet = workbook.getSheetAt(0);
			}

			for (int currentRow = 1; currentRow <= sheet.getRow(0).getPhysicalNumberOfCells(); currentRow++) {

				for (int currentCell = 1; currentCell < sheet.getPhysicalNumberOfRows(); currentCell++) {

					Cell cell = sheet.getRow(currentRow).getCell(currentCell);

					String base = sheet.getRow(currentRow).getCell(0).getStringCellValue();
					String terms = sheet.getRow(0).getCell(currentCell).getStringCellValue();
					String calculationMethod = cell.getStringCellValue();

					CrossViaMatrixData spreadSheetContent = new CrossViaMatrixData(base, terms, calculationMethod);
					crossViaMatrixData.add(spreadSheetContent);

				}

			}
			
			return crossViaMatrixData;

		} catch (FileNotFoundException e) {
			LOGGER.error(ApplicationConstant.APP_ERROR_MESSAGE, e);
			throw new FileUtilException(e);
		} catch (IOException e) {
			LOGGER.error(ApplicationConstant.APP_ERROR_MESSAGE, e);
			throw new FileUtilException(e);
		}

	}
	
	//This call is not cached as the currency conversion rates changes everyday, hence retrieving from direct feed data everytime
	@Override
	public Map<String, BigDecimal> readDirectFeed() {

		try {

			Map<String, BigDecimal> directFeed = new HashMap<>();

			Sheet sheet;
			InputStream inputStream = applicationWrapper.createInputStream(ApplicationConstant.DIRECT_FEED_FILE_LOCATION);

			try (Workbook workbook = applicationWrapper.createXSSFWorkbook(inputStream)) {
				sheet = workbook.getSheetAt(0);
			}

			for (int currentRow = 1; currentRow < sheet.getPhysicalNumberOfRows(); currentRow++) {

					String currencyPair = sheet.getRow(currentRow).getCell(0).getStringCellValue();
					String conversionRate = sheet.getRow(currentRow).getCell(1).getStringCellValue();
					
					directFeed.put(currencyPair, new BigDecimal(conversionRate));
			}
			
			return directFeed;

		} catch (FileNotFoundException e) {
			LOGGER.error(ApplicationConstant.APP_ERROR_MESSAGE, e);
			throw new FileUtilException(e);
		} catch (IOException e) {
			LOGGER.error(ApplicationConstant.APP_ERROR_MESSAGE, e);
			throw new FileUtilException(e);
		}

	}

}