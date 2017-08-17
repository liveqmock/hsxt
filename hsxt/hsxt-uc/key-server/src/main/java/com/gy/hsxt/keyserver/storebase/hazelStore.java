package com.gy.hsxt.keyserver.storebase;

import java.util.Date;

import com.gy.hsxt.keyserver.tools.*;
import com.gy.kms.keyserver.CoDecode;
// import com.gy.mgrid.client.api.CacheApi;


/***************************************************************************
 * <PRE>
 *  Project Name    : KeyServer
 * 
 *  Package Name    : com.gy.kms.storebase
 * 
 *  File Name       : hazelStore.java
 * 
 *  Creation Date   : 2014-7-4
 * 
 *  Author          : fandi
 * 
 *  Purpose         : HazelCast数据源
 *
 *  @deprecated
 *  版本由2.0升级至3.0，缓存使用redis替代，该类不使用
 *  修改人：李璇
 *  修改日期：2015/10/29
 * </PRE>
 ***************************************************************************/

public class hazelStore implements store{

	@Override
	public void SetPMK(String POSID, byte[] PMK) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] GetPMK(String POSID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void SetPIKMAK(String POSID, byte[] PIK, byte[] MAK) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CashData getPIKPMK(String POSID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int GetSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void SetALL(String POSID, CashData data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CashData getALL(String POSID) {
		// TODO Auto-generated method stub
		return null;
	}
/**
 * 初始化先激活一下，不然后面会很慢
 */
	// public hazelStore(){
	// GetSize();
	// }
	// private static final String CACHE = "map-keyServer";
	// public int GetSize() {
	// return CacheApi.size(CACHE);
	// }
	//
	// public void SetPMK(String POSID, byte[] PMK) {
	// CashData tmp = new CashData(0, CoDecode.encryptPMK(PMK), null, null);
	// CacheApi.put(CACHE, POSID, tmp);
	// }
	//
	// public byte[] GetPMK(String POSID) {
	// CashData tmp = CacheApi.get(CACHE, POSID, CashData.class);
	// if(tmp != null){
	// return CoDecode.decryptPMK(tmp.getPmk());
	// }
	// return null;
	// }
	// public void SetPIKMAK(String POSID, byte[] PIK, byte[] MAK) {
	// CashData tmp = CacheApi.get(CACHE, POSID, CashData.class);
	// if(tmp != null){
	// byte pmk[] = CoDecode.decryptPMK(tmp.getPmk());
	// byte MAK_enc[] = new byte[8];
	// byte PIK_enc[] = new byte[16];
	// CoDecode.DES3encrypt(pmk, MAK, 8, MAK_enc);
	// CoDecode.DES3encrypt(pmk, PIK, 16, PIK_enc);
	// tmp.setMak(MAK_enc);
	// tmp.setPik(PIK_enc);
	// tmp.setTime((new Date()).getTime() / 1000);
	// CacheApi.put(CACHE, POSID, tmp);
	// }
	// else
	// {
	//
	// }
	// }
	// public CashData getPIKPMK(String POSID) {
	// CashData rc = null;
	// CashData tmp = CacheApi.get(CACHE, POSID, CashData.class);
	//
	// if(tmp != null && tmp.Check()){
	// byte pmk[] = CoDecode.decryptPMK(tmp.getPmk());
	// byte MAK_src[] = new byte[8];
	// byte PIK_src[] = new byte[16];
	// CoDecode.DES3decrypt(pmk, tmp.getMak(), 8, MAK_src);
	// CoDecode.DES3decrypt(pmk, tmp.getPik(), 16, PIK_src);
	// rc = new CashData(0, pmk, PIK_src, MAK_src);
	// }
	// return rc;
	// }
	//
	//
	// public void SetALL(String POSID, CashData data) {
	// CacheApi.put(CACHE, POSID, data);
	// }
	//
	// public CashData getALL(String POSID) {
	// CashData rc = null;
	// CashData tmp = CacheApi.get(CACHE, POSID, CashData.class);
	//
	// if(tmp != null){
	// byte pmk[] = CoDecode.decryptPMK(tmp.getPmk());
	// byte MAK_src[] = null;
	// byte PIK_src[] = null;
	// if (tmp.getMak() != null &&tmp.getPik() != null){
	// MAK_src = new byte[8];
	// PIK_src = new byte[16];
	// CoDecode.DES3decrypt(pmk, tmp.getMak(), 8, MAK_src);
	// CoDecode.DES3decrypt(pmk, tmp.getPik(), 16, PIK_src);
	// }
	// rc = new CashData(tmp.getTime(), pmk, PIK_src, MAK_src);
	// }
	// return rc;
	// }
}
