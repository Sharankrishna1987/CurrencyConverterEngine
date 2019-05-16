package com.org.anz.currencyconverter.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);
	
	@Before("execution(* com.org.anz..currencyconverter..*(..))")
	public void logBefore(JoinPoint joinPoint) {
		LOGGER.info(String.format("Inside method: %s() of class: %s", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName()));
	}

	@After("execution(* com.org.anz..currencyconverter..*(..))")
	public void logAfter(JoinPoint joinPoint) {
		LOGGER.info(String.format("Exiting method: %s() of class: %s", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName()));
	}
	
}