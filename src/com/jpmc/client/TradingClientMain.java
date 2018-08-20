package com.jpmc.client;

import java.util.ArrayList;
import java.util.List;
import com.jpmc.trade.TradeSettlementService;
import com.jpmc.trade.TradeSettlementServiceImpl;
import com.jpmc.trade.model.TradingDomain;

/**
 * TradingClientMain is a client which calls TradeSettlementService and
 * simply displays trading details to the standard output in simple text format.
 * @author  Mohammad Sayeed
 * @since   8/20/2018
 */

public class TradingClientMain {


	/**
	 * This is the main method which calls TradeSettlementService .
	 * @param args Unused.
	 * @return Nothing.
	 */
	public static void main(String[] args) {

		List<TradingDomain> tradeDomainList = new ArrayList<>();
		TradingDomain tradeDomain1 = new TradingDomain("B", "foo", 0.50,"SGP","01-Jan-2016", "02-Jan-2016",200, 100.25);
		TradingDomain tradeDomain2 = new TradingDomain("S", "bar", 0.22, "AED", "05-Jan-2016", "07-Jan-2016", 450, 150.5);
		TradingDomain tradeDomain3 = new TradingDomain("B", "foo1", 0.12, "SAR", "07-Jan-2016", "12-Jan-2016", 300, 110.5);
		TradingDomain tradeDomain4 = new TradingDomain("B", "foo2", 0.32, "USD", "04-Aug-2018", "05-Aug-2018", 700, 105.5);
		TradingDomain tradeDomain5 = new TradingDomain("S", "bar2", 0.62, "SAR", "17-Aug-2018", "18-Aug-2018", 100, 750.5);

		tradeDomainList.add(tradeDomain1);
		tradeDomainList.add(tradeDomain2);
		tradeDomainList.add(tradeDomain3);
		tradeDomainList.add(tradeDomain4);
		tradeDomainList.add(tradeDomain5);
		
		TradeSettlementService tradeService = new TradeSettlementServiceImpl();
		//Method calling by passing list of parameter for different entity
		tradeService.generateTradeSettllementDetails(tradeDomainList);
	}

}
