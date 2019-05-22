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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((base == null) ? 0 : base.hashCode());
		result = prime * result + ((terms == null) ? 0 : terms.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConsoleRequestData other = (ConsoleRequestData) obj;
		if (base == null) {
			if (other.base != null)
				return false;
		} else if (!base.equals(other.base))
			return false;
		if (terms == null) {
			if (other.terms != null)
				return false;
		} else if (!terms.equals(other.terms))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ConsoleRequestData [base=" + base + ", terms=" + terms + "]";
	}
	
}
