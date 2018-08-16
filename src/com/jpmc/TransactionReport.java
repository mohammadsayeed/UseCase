package com.jpmc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TransactionReport {

	public static void main(String[] args) {

		TransactionReport tradeReport = new TransactionReport();

		List<TradeDomain> tradeDomain = new ArrayList<>();
		TradeDomain tradeDomain1 = new TradeDomain("B", "foo", 0.50,"SGP","01-Jan-2016", "02-Jan-2016",200, 100.25);
		TradeDomain tradeDomain2 = new TradeDomain("S", "bar", 0.22, "AED", "05-Jan-2016", "07-Jan-2016", 450, 150.5);
		TradeDomain tradeDomain3 = new TradeDomain("B", "foo1", 0.12, "SAR", "07-Jan-2016", "12-Jan-2016", 300, 110.5);
		TradeDomain tradeDomain4 = new TradeDomain("B", "foo2", 0.32, "USD", "08-Jan-2016", "23-Jan-2016", 700, 105.5);
		TradeDomain tradeDomain5 = new TradeDomain("S", "bar2", 0.62, "SAR", "09-Jan-2016", "16-Jan-2016", 100, 750.5);

		tradeDomain.add(tradeDomain1);
		tradeDomain.add(tradeDomain2);
		tradeDomain.add(tradeDomain3);
		tradeDomain.add(tradeDomain4);
		tradeDomain.add(tradeDomain5);

		tradeReport.generateTradeReport(tradeDomain);
	}

	public void generateTradeReport(List<TradeDomain> tradeDomains)
	{
		int sellingRank = 1;
		int buyingRank = 1;
		List<TradeDomain> ls= new ArrayList<>();
		tradeDomains.forEach(domain->{

			domain.setUsdAmountOfTrade(domain.getPricePerUnit()*domain.getAgreedFx()* domain.getUnits());

			domain.setSettlementDate(checkWorkingDayAccordingTocurrency(domain.getCurrency(),domain.getSettlementDate()));

			ls.add(domain);
		});

		ls.sort(Comparator.comparing(TradeDomain ::getUsdAmountOfTrade).reversed());

		//System.out.println( "Entity      "+ "Instruction       "  +"AgreedFx    " +"Currency      "+ "Instruction Date     "+ "SettlementDate    " +"Units    " +"PricePerUnit   "+"UsdAmountOfTrade    " +  "Rank");
		System.out.println( "Entity      "+ "Selling/Buying       "+"IncommingAmount       "  +"outGoingAmount      "+ "SettlementDate        " + "Incomming Rank         " + "OutGoing Rank");
		System.out.println("----------------------------------------------------------------------------------------------------------------------------");

		for( TradeDomain a : ls)
		{
			if(a.getInstruction().equalsIgnoreCase("B"))
			{
				//a.setRank(buyingRank);
				a.setOutGoingAmount(Double.toString( a.getUsdAmountOfTrade()));
				a.setInComingAmount("NA");
				a.setOutGoingRank(Integer.toString(buyingRank));
				a.setInComingRank("NA");
				buyingRank++;

			}
			if(a.getInstruction().equalsIgnoreCase("S"))
			{
				//a.setRank(sellingRank);
				a.setInComingAmount(Double.toString(a.getUsdAmountOfTrade()));
				a.setOutGoingAmount("NA");
				a.setInComingRank(Integer.toString(sellingRank));
				a.setOutGoingRank("NA");
				sellingRank ++;
			}
			System.out.println(a.getEntity() + "          "+a.getInstruction()+ "                   " +a.getInComingAmount() +"                   "+a.getOutGoingAmount() +"                  "+a.getSettlementDate()+ "        " + a.getInComingRank()+ "                     "+a.getOutGoingRank());
		}
	}

	public String checkWorkingDayAccordingTocurrency(String currency , String settlementDate)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");

		//convert String to LocalDate
		LocalDate localDate = LocalDate.parse(settlementDate, formatter);

		DayOfWeek dayOfweek = localDate.getDayOfWeek();

		LocalDate settelmentDay= null;

		try
		{
			if(currency.equalsIgnoreCase("SAR") || currency.equalsIgnoreCase("AED"))
			{
				switch (dayOfweek) {
				case FRIDAY:settelmentDay = localDate.plusDays(2);
				break;
				case SATURDAY:settelmentDay = localDate.plusDays(1);
				break;
				default:settelmentDay=localDate;
				break;
				}		
			}
			else
			{
				switch (dayOfweek) {
				case SATURDAY:settelmentDay = localDate.plusDays(2);
				break;
				case SUNDAY:settelmentDay = localDate.plusDays(1);
				break;
				default:settelmentDay=localDate;
				break;
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return settelmentDay.toString();
	}
}
