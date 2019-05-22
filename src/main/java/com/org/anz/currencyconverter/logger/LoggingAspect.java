package com.org.anz.currencyconverter.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
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
		LOGGER.info(String.format("Inside method: %s() of class: %s", joinPoint.getSignature().getName(),
				joinPoint.getSignature().getDeclaringTypeName()));
	}

	@AfterReturning(pointcut = "execution(* com.org.anz..currencyconverter..*(..))", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		LOGGER.info(String.format("Method: %s() of class: %s returned value: %s", joinPoint.getSignature().getName(),
				joinPoint.getSignature().getDeclaringTypeName(), result));
	}

	@After("execution(* com.org.anz..currencyconverter..*(..))")
	public void logAfter(JoinPoint joinPoint) {
		LOGGER.info(String.format("Exiting method: %s() of class: %s", joinPoint.getSignature().getName(),
				joinPoint.getSignature().getDeclaringTypeName()));
	}

	@AfterThrowing(pointcut = "execution(* com.org.anz..currencyconverter..*(..))", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		LOGGER.info(String.format("Method: %s() of class: %s threw: %s", joinPoint.getSignature().getName(),
				joinPoint.getSignature().getDeclaringTypeName(), error));
	}

}
