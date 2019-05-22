package com.org.anz.currencyconverter.wrapper;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationWrapperImplTest {
	
	@Test
	public void testCreateInputStream() throws FileNotFoundException {
		ApplicationWrapperImpl applicationWrapperImpl = new ApplicationWrapperImpl();
		Assert.assertNotNull(applicationWrapperImpl.createInputStream(""));
	}
	
	@Test
	public void testGetScanner() {
		ApplicationWrapperImpl applicationWrapperImpl = new ApplicationWrapperImpl();
		Assert.assertNotNull(applicationWrapperImpl.getScanner());
	}
	
}
