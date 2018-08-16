package com.jpmc;

public class TradeDomain {

	
	private String instruction;
	private String entity;
	private String instructionDate;
	private String settlementDate ;
	private double pricePerUnit;
	private int units ; 
	private double agreedFx;
	private String currency;
	private double usdAmountOfTrade;
	private int rank;
	private String inComingAmount;
	private String outGoingAmount;
	private String inComingRank;
	private String outGoingRank;
	
	
	public TradeDomain(String instruction, String entity, double agreedFx, String currency, String instructionDate, String settlementDate,
			 int units,double pricePerUnit) {
		super();
		this.instruction = instruction;
		this.entity = entity;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;
		this.pricePerUnit = pricePerUnit;
		this.units = units;
		this.agreedFx = agreedFx;
		this.currency = currency;
	}
	
	
	
	
	public String getInComingRank() {
		return inComingRank;
	}




	public void setInComingRank(String inComingRank) {
		this.inComingRank = inComingRank;
	}




	public String getOutGoingRank() {
		return outGoingRank;
	}




	public void setOutGoingRank(String outGoingRank) {
		this.outGoingRank = outGoingRank;
	}

	public String getInComingAmount() {
		return inComingAmount;
	}




	public void setInComingAmount(String inComingAmount) {
		this.inComingAmount = inComingAmount;
	}




	public String getOutGoingAmount() {
		return outGoingAmount;
	}




	public void setOutGoingAmount(String outGoingAmount) {
		this.outGoingAmount = outGoingAmount;
	}




	public int getRank() {
		return rank;
	}


	public void setRank(int rank) {
		this.rank = rank;
	}


	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getInstructionDate() {
		return instructionDate;
	}
	public void setInstructionDate(String instructionDate) {
		this.instructionDate = instructionDate;
	}
	public String getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}
	public double getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public int getUnits() {
		return units;
	}
	public void setUnits(int units) {
		this.units = units;
	}
	public double getAgreedFx() {
		return agreedFx;
	}
	public void setAgreedFx(double agreedFx) {
		this.agreedFx = agreedFx;
	}
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getUsdAmountOfTrade() {
		return usdAmountOfTrade;
	}
	public void setUsdAmountOfTrade(double usdAmountOfTrade) {
		this.usdAmountOfTrade = usdAmountOfTrade;
	}
	 
	 
	 
}
