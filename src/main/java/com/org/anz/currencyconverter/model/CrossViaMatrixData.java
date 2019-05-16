package com.org.anz.currencyconverter.model;

public class CrossViaMatrixData {

	private String base;
	private String terms;
	private String calculationModel;

	public CrossViaMatrixData(String base, String terms, String calculationModel) {
		this.base = base;
		this.terms = terms;
		this.calculationModel = calculationModel;
	}

	public String getBase() {
		return base;
	}

	public String getTerms() {
		return terms;
	}

	public String getCalculationModel() {
		return calculationModel;
	}

	@Override
	public String toString() {
		return "SpreadSheetContent [base=" + base + ", terms=" + terms + ", calculationModel=" + calculationModel
				+ "]";
	}

}