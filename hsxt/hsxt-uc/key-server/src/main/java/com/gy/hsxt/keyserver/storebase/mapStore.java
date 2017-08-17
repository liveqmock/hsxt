package com.gy.hsxt.keyserver.storebase;

import java.util.Date;
import java.util.HashMap;


/***************************************************************************
 * <PRE>
 *  Project Name    : KeyServer
 * 
 *  Package Name    : com.gy.kms.storebase
 * 
 *  File Name       : mapStore.java
 * 
 *  Creation Date   : 2014-7-4
 * 
 *  Author          : fandi
 * 
 *  Purpose         : MAP数据源
 * 
 * </PRE>
 ***************************************************************************/
public class mapStore implements store{
	static private HashMap<String, CashData> map = new HashMap<String, CashData>();


	public CashData getPIKPMK(String POSID){
		CashData tmp = map.get(POSID);
		if (tmp!=null &&tmp.Check()){
			return tmp;
		}
		return null;
	}
	public int GetSize() {
		return map.size();
	}
	public void SetPMK(String POSID, byte[] PMK) {
		CashData tmp = new CashData(0, PMK, null, null);
		map.put(POSID, tmp);
	}

	public byte[] GetPMK(String POSID) {
		CashData tmp = map.get(POSID);
		if(tmp != null){
			return tmp.getPmk();
		}
		return null;
	}

	public void SetPIKMAK(String POSID,  byte[] PIK, byte[] MAK) {
		CashData tmp = map.get(POSID);
		if(tmp != null){
			tmp.setMak(MAK);
			tmp.setPik(PIK);
			tmp.setTime((new Date()).getTime() / 1000);
			map.put(POSID, tmp);
		}
	}

	public void SetALL(String POSID, CashData data) {
		map.put(POSID, data);
	}

	public CashData getALL(String POSID) {
		return map.get(POSID);
	}
}
