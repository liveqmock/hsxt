package com.gy.hsxt.access.pos.service;

import java.math.BigDecimal;

public interface PlatformParamService {

	public BigDecimal getPointRatMin();
	public BigDecimal getPointRatMax();
	public BigDecimal getReChargeNotAuthSingleMin();
	public BigDecimal getReChargeNotAuthSingleMax();
	public BigDecimal getReChargeAuthSingleMin();
	public BigDecimal getReChargeAuthSingleMax();
	public BigDecimal getReChargeBSingleMin();
	public BigDecimal getReChargeBSingleMax();
	public BigDecimal getReChargeTSingleMin();
	public BigDecimal getReChargeTSingleMax();
	public String getLocalCurrencyCode();
	public BigDecimal getFreeEntPointRatMin();
	
	
}
