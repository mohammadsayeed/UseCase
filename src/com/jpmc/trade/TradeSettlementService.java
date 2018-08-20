package com.jpmc.trade;

import java.util.List;

import com.jpmc.trade.model.TradingDomain;

public interface TradeSettlementService {

	public void generateTradeSettllementDetails(List<TradingDomain> tradingDomainsList); 
}
