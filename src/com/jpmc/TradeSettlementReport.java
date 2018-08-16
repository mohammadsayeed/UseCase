package com.jpmc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TradeSettlementReport {
	
	public static String NOT_APPLICABLE = "NA";
	public static String SELLING = "S";
	public static String BUYING = "B";
	public static String SAR = "SAR";
	public static String AED = "AED"; 

	public static void main(String[] args) {

		TradeSettlementReport tradeReport = new TradeSettlementReport();

		List<TradingDomain> tradeDomainList = new ArrayList<>();
		TradingDomain tradeDomain1 = new TradingDomain("B", "foo", 0.50,"SGP","01-Jan-2016", "02-Jan-2016",200, 100.25);
		TradingDomain tradeDomain2 = new TradingDomain("S", "bar", 0.22, "AED", "05-Jan-2016", "07-Jan-2016", 450, 150.5);
		TradingDomain tradeDomain3 = new TradingDomain("B", "foo1", 0.12, "SAR", "07-Jan-2016", "12-Jan-2016", 300, 110.5);
		TradingDomain tradeDomain4 = new TradingDomain("B", "foo2", 0.32, "USD", "08-Jan-2016", "23-Jan-2016", 700, 105.5);
		TradingDomain tradeDomain5 = new TradingDomain("S", "bar2", 0.62, "SAR", "09-Jan-2016", "16-Jan-2016", 100, 750.5);

		tradeDomainList.add(tradeDomain1);
		tradeDomainList.add(tradeDomain2);
		tradeDomainList.add(tradeDomain3);
		tradeDomainList.add(tradeDomain4);
		tradeDomainList.add(tradeDomain5);

		tradeReport.generateTradeReport(tradeDomainList);
	}

	public void generateTradeReport(List<TradingDomain> tradingDomainsList)
	{
		int sellingRank = 1;
		int buyingRank = 1;
		List<TradingDomain> list= new ArrayList<>();
		tradingDomainsList.forEach(domain->{

			domain.setUsdAmountOfTrade(domain.getPricePerUnit()*domain.getAgreedFx()* domain.getUnits());

			domain.setSettlementDate(checkWorkingDayAccordingTocurrency(domain.getCurrency(),domain.getSettlementDate()));

			list.add(domain);
		});

		// Sorting by USD amount of Trading
		
		list.sort(Comparator.comparing(TradingDomain ::getUsdAmountOfTrade).reversed());

		//System.out.println( "Entity      "+ "Instruction       "  +"AgreedFx    " +"Currency      "+ "Instruction Date     "+ "SettlementDate    " +"Units    " +"PricePerUnit   "+"UsdAmountOfTrade    " +  "Rank");
		System.out.println( "Entity      "+ "Selling/Buying       "+"IncommingAmount       "  +"outGoingAmount      "+ "SettlementDate        " + "Incomming Rank         " + "OutGoing Rank");
		System.out.println("----------------------------------------------------------------------------------------------------------------------------");

		for( TradingDomain tradingDomain : list)
		{
			if(tradingDomain.getInstruction().equalsIgnoreCase(BUYING))
			{
				//a.setRank(buyingRank);
				tradingDomain.setOutGoingAmount(Double.toString( tradingDomain.getUsdAmountOfTrade()));
				tradingDomain.setInComingAmount(NOT_APPLICABLE);
				tradingDomain.setOutGoingRank(Integer.toString(buyingRank));
				tradingDomain.setInComingRank(NOT_APPLICABLE);
				buyingRank++;

			}
			if(tradingDomain.getInstruction().equalsIgnoreCase(SELLING))
			{
				//a.setRank(sellingRank);
				tradingDomain.setInComingAmount(Double.toString(tradingDomain.getUsdAmountOfTrade()));
				tradingDomain.setOutGoingAmount(NOT_APPLICABLE);
				tradingDomain.setInComingRank(Integer.toString(sellingRank));
				tradingDomain.setOutGoingRank(NOT_APPLICABLE);
				sellingRank ++;
			}
			System.out.println(tradingDomain.getEntity() + "          "+tradingDomain.getInstruction()+ "                   " +tradingDomain.getInComingAmount() +"                   "+tradingDomain.getOutGoingAmount() +"                  "+tradingDomain.getSettlementDate()+ "        " + tradingDomain.getInComingRank()+ "                     "+tradingDomain.getOutGoingRank());
		}
	}

	public String checkWorkingDayAccordingTocurrency(String currency , String settlementDate)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");

		//convert String to LocalDate
		LocalDate localDate = LocalDate.parse(settlementDate, formatter);

		DayOfWeek dayOfweek = localDate.getDayOfWeek();

		LocalDate settlementDay= null;

		try
		{
			if(currency.equalsIgnoreCase(SAR) || currency.equalsIgnoreCase(AED))
			{
				switch (dayOfweek) {
				case FRIDAY:settlementDay = localDate.plusDays(2);
				break;
				case SATURDAY:settlementDay = localDate.plusDays(1);
				break;
				default:settlementDay =localDate;
				break;
				}		
			}
			else
			{
				switch (dayOfweek) {
				case SATURDAY:settlementDay = localDate.plusDays(2);
				break;
				case SUNDAY:settlementDay = localDate.plusDays(1);
				break;
				default:settlementDay=localDate;
				break;
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return settlementDay.toString();
	}
}
