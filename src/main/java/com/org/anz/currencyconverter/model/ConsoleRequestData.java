package com.org.anz.currencyconverter.model;

import java.math.BigDecimal;

public class ConsoleRequestData {

	private String base;
	private String terms;
	private BigDecimal amount;

	private ConsoleRequestData(Builder builder) {
		this.base = builder.base;
		this.terms = builder.terms;
		this.amount = builder.amount;
	}

	public static class Builder {
		private String base;
		private String terms;
		private BigDecimal amount;

		public Builder base(String base) {
			this.base = base;
			return this;
		}

		public Builder terms(String terms) {
			this.terms = terms;
			return this;
		}

		public Builder amount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public ConsoleRequestData build() {
			return new ConsoleRequestData(this);
		}

	}

	public String getBase() {
		return base;
	}

	public String getTerms() {
		return terms;
	}

	public BigDecimal getAmount() {
		return amount;
	}

}