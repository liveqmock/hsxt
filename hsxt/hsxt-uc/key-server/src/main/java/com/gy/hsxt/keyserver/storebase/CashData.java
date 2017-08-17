package com.gy.hsxt.keyserver.storebase;

import java.util.Date;

import com.gy.hsxt.keyserver.tools.*;

/***************************************************************************
 * <PRE>
 *  Project Name    : KeyServer
 * 
 *  Package Name    : com.gy.kms.keyserver
 * 
 *  File Name       : CashData.java
 * 
 *  Creation Date   : 2014-7-4
 * 
 *  Author          : fandi
 * 
 *  Purpose         : 一条数据，包含key与pmk、pik、mac、时效期限
 * 
 * </PRE>
 ***************************************************************************/
public class CashData {
	long time;
	byte[] pmk = null; 
	byte[] pik = null;
	byte[] mak = null;
	protected CashData(){
		
	}

	public CashData(long time, byte[] pmk, byte[] pik, byte[] mak) {
		this.time = time;
		if (pmk != null) {
			this.pmk = pmk;
		}
		if (pik != null) {
			this.pik = pik;
		}
		if (mak != null) {
			this.mak = mak;
		}
	}
	public boolean Check(){
		if (pik != null && mak != null && (new Date().getTime() / 1000 - getTime()) < Config
				.getDueTime()) {
			return true;
		}
		return false;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public byte[] getPmk() {
		return pmk;
	}

	public void setPmk(byte[] pmk) {
		this.pmk = pmk;
	}

	public byte[] getPik() {
		return pik;
	}

	public void setPik(byte[] pik) {
		this.pik = pik;
	}

	public byte[] getMak() {
		return mak;
	}

	public void setMak(byte[] mak) {
		this.mak = mak;
	}
}
