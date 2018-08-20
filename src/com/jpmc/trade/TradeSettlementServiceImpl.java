package com.jpmc.trade;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.jpmc.trade.model.TradingDomain;

/**
 * TradeSettlementServiceImpl program implements an application that
 * simply displays trading details to the standard output in simple text format.
 * @author  Mohammad Sayeed
 * @since   8/20/2018
 */

public class TradeSettlementServiceImpl implements TradeSettlementService{

	public static String NOT_APPLICABLE = "NA";
	public static String SELLING = "S";
	public static String BUYING = "B";
	public static String SAR = "SAR";
	public static String AED = "AED"; 


	/**
	 * The generateTradeSettllementDetails method calculate Amount of Trade in USD and displays entity rank by Selling or Buying basis .
	 * @param List<TradingDomain>
	 * @return Nothing.
	 */

	@Override
	public void generateTradeSettllementDetails(List<TradingDomain> tradingDomainsList) {


		int sellingRank = 1;
		int buyingRank = 1;
		List<TradingDomain> list= new ArrayList<>();
		if(tradingDomainsList != null && !tradingDomainsList.isEmpty() )
		{

			tradingDomainsList.forEach(domain->{

				domain.setUsdAmountOfTrade(domain.getPricePerUnit()*domain.getAgreedFx()* domain.getUnits());

				domain.setSettlementDate(setWorkingDayAccordingToCurrency(domain.getCurrency(),domain.getSettlementDate()));

				list.add(domain);
			});

			// Sorting by USD amount of Trading

			list.sort(Comparator.comparing(TradingDomain ::getUsdAmountOfTrade).reversed());

			//Displaying the details of trading and ranking 

			System.out.println( "Entity      "+ "Selling/Buying       "+"IncommingAmount       "  +"outGoingAmount      "+ "SettlementDate        " + "Incomming Rank         " + "OutGoing Rank");
			System.out.println("----------------------------------------------------------------------------------------------------------------------------");

			for( TradingDomain tradingDomain : list)
			{
				if(tradingDomain.getInstruction().equalsIgnoreCase(BUYING))
				{
					tradingDomain.setOutGoingAmount(Double.toString( tradingDomain.getUsdAmountOfTrade()));
					tradingDomain.setInComingAmount(NOT_APPLICABLE);
					tradingDomain.setOutGoingRank(Integer.toString(buyingRank));
					tradingDomain.setInComingRank(NOT_APPLICABLE);
					buyingRank++;

				}
				if(tradingDomain.getInstruction().equalsIgnoreCase(SELLING))
				{
					tradingDomain.setInComingAmount(Double.toString(tradingDomain.getUsdAmountOfTrade()));
					tradingDomain.setOutGoingAmount(NOT_APPLICABLE);
					tradingDomain.setInComingRank(Integer.toString(sellingRank));
					tradingDomain.setOutGoingRank(NOT_APPLICABLE);
					sellingRank ++;
				}
				System.out.println(tradingDomain.getEntity() + "          "+tradingDomain.getInstruction()+ "                   " +tradingDomain.getInComingAmount() +"                   "+tradingDomain.getOutGoingAmount() +"                  "+tradingDomain.getSettlementDate()+ "        " + tradingDomain.getInComingRank()+ "                     "+tradingDomain.getOutGoingRank());
			}

		}

	}


	/**
	 * The setWorkingDayAccordingToCurrency method check working days on the basis of currency if settlement date comes under weekend then it set settlement date to next working day .
	 * @param currency
	 * @param settlementDate
	 * @return settlementDay
	 */

	private String setWorkingDayAccordingToCurrency(String currency , String settlementDate)
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
