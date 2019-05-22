package com.org.anz.currencyconverter.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.org.anz.currencyconverter.exception.FileUtilException;
import com.org.anz.currencyconverter.model.CrossViaMatrixData;
import com.org.anz.currencyconverter.wrapper.ApplicationWrapper;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConverterSpreadSheetReaderImplTest {

	@Mock
	private ApplicationWrapper applicationWrapper;

	@InjectMocks
	private CurrencyConverterSpreadSheetReaderImpl currencyConverterSpreadSheetReaderImpl;

	@Test
	public void testReadCrossViaMatrixData() throws IOException {
		InputStream mockInputStream = mock(InputStream.class);
		XSSFWorkbook mockXSSFWorkbook = mock(XSSFWorkbook.class);
		XSSFSheet mockSheet = mock(XSSFSheet.class);
		XSSFRow mockRow = mock(XSSFRow.class);
		XSSFCell mockCellBase = mock(XSSFCell.class);
		XSSFCell mockCellTerms = mock(XSSFCell.class);
		XSSFCell mockCellConversion = mock(XSSFCell.class);

		when(applicationWrapper.createInputStream("templates/cross-via-matrix.xlsx")).thenReturn(mockInputStream);
		when(applicationWrapper.createXSSFWorkbook(mockInputStream)).thenReturn(mockXSSFWorkbook);
		when(mockXSSFWorkbook.getSheetAt(0)).thenReturn(mockSheet);
		when(mockSheet.getRow(0)).thenReturn(mockRow);
		when(mockRow.getPhysicalNumberOfCells()).thenReturn(1);
		when(mockSheet.getPhysicalNumberOfRows()).thenReturn(2);

		when(mockSheet.getRow(1)).thenReturn(mockRow);
		when(mockRow.getCell(1)).thenReturn(mockCellConversion);

		when(mockSheet.getRow(1)).thenReturn(mockRow);
		when(mockRow.getCell(0)).thenReturn(mockCellBase);
		when(mockCellBase.getStringCellValue()).thenReturn("AUD");

		when(mockSheet.getRow(0)).thenReturn(mockRow);
		when(mockRow.getCell(1)).thenReturn(mockCellTerms);
		when(mockCellTerms.getStringCellValue()).thenReturn("USD");

		List<CrossViaMatrixData> crossViaMatrixData = currencyConverterSpreadSheetReaderImpl.readCrossViaMatrixData();
		Assert.assertEquals(1, crossViaMatrixData.size());
		Assert.assertEquals("AUD", crossViaMatrixData.get(0).getBase());
		Assert.assertEquals("USD", crossViaMatrixData.get(0).getTerms());
	}

	@Test
	public void testReadDirectFeed() throws IOException {
		InputStream mockInputStream = mock(InputStream.class);
		XSSFWorkbook mockXSSFWorkbook = mock(XSSFWorkbook.class);
		XSSFSheet mockSheet = mock(XSSFSheet.class);
		XSSFRow mockRow = mock(XSSFRow.class);
		XSSFCell mockCellBase = mock(XSSFCell.class);
		XSSFCell mockCellTerms = mock(XSSFCell.class);

		when(applicationWrapper.createInputStream("templates/direct-feed.xlsx")).thenReturn(mockInputStream);
		when(applicationWrapper.createXSSFWorkbook(mockInputStream)).thenReturn(mockXSSFWorkbook);
		when(mockXSSFWorkbook.getSheetAt(0)).thenReturn(mockSheet);
		when(mockSheet.getPhysicalNumberOfRows()).thenReturn(2);

		when(mockSheet.getRow(1)).thenReturn(mockRow);
		when(mockRow.getCell(0)).thenReturn(mockCellBase);
		when(mockCellBase.getStringCellValue()).thenReturn("AUDUSD");

		when(mockSheet.getRow(1)).thenReturn(mockRow);
		when(mockRow.getCell(1)).thenReturn(mockCellTerms);
		when(mockCellTerms.getStringCellValue()).thenReturn("1.34");

		Map<String, BigDecimal> directFeed = currencyConverterSpreadSheetReaderImpl.readDirectFeed();
		Assert.assertEquals(2, directFeed.size());
		Assert.assertEquals(new BigDecimal("1.34"), directFeed.get("AUDUSD"));
	}

	@Test(expected = FileUtilException.class)
	public void testReadCrossViaMatrixDataThrowFileNotFoundException() throws FileNotFoundException {
		when(applicationWrapper.createInputStream("templates/cross-via-matrix.xlsx"))
				.thenThrow(FileNotFoundException.class);
		currencyConverterSpreadSheetReaderImpl.readCrossViaMatrixData();
	}

	@Test(expected = FileUtilException.class)
	public void testReadCrossViaMatrixDataThrowIOException() throws IOException {
		InputStream mockInputStream = mock(InputStream.class);
		when(applicationWrapper.createInputStream("templates/cross-via-matrix.xlsx")).thenReturn(mockInputStream);
		when(applicationWrapper.createXSSFWorkbook(mockInputStream)).thenThrow(IOException.class);
		currencyConverterSpreadSheetReaderImpl.readCrossViaMatrixData();
	}

	@Test(expected = FileUtilException.class)
	public void testReadDirectFeedThrowFileNotFoundException() throws FileNotFoundException {
		when(applicationWrapper.createInputStream("templates/direct-feed.xlsx")).thenThrow(FileNotFoundException.class);
		currencyConverterSpreadSheetReaderImpl.readDirectFeed();
	}

	@Test(expected = FileUtilException.class)
	public void testReadDirectFeedThrowIOException() throws IOException {
		InputStream mockInputStream = mock(InputStream.class);
		when(applicationWrapper.createInputStream("templates/direct-feed.xlsx")).thenReturn(mockInputStream);
		when(applicationWrapper.createXSSFWorkbook(mockInputStream)).thenThrow(IOException.class);
		currencyConverterSpreadSheetReaderImpl.readDirectFeed();
	}

}
