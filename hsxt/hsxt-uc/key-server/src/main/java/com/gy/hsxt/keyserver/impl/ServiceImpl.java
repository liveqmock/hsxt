package com.gy.hsxt.keyserver.impl;

import com.gy.hsxt.keyserver.beans.ErrorCode;
import com.gy.hsxt.keyserver.beans.MacDecode;
import com.gy.hsxt.keyserver.beans.OutData;
import com.gy.hsxt.keyserver.service.IService;
import com.gy.hsxt.keyserver.tools.FindFactoryBean;
import com.gy.hsxt.keyserver.tools.Tools;

public class ServiceImpl implements IService{
	/*
	 * 获得PMK
	 * object = byte[16]
	 */
	public OutData GetPMK(String EntNo,String POSNo,String OperatorID)
	{
		OutData rc = new OutData();
		byte []pmk = FindFactoryBean.getBaseDao().GetNewPMK(EntNo, POSNo, OperatorID);
		if (pmk != null && pmk.length == 16){
			rc.setStatus(ErrorCode.OK);
			rc.setData(pmk);
		}
		else{
			rc.setStatus(ErrorCode.DATABASEERROR);
		}
		return rc;
	}
	
	/*
	 *效验PMK
	 * object = boolean
	 */
	public OutData CheckPMK(String EntNo,String POSNo,String OperatorID,String Code)
	{
		OutData rc = new OutData();
		boolean chk = FindFactoryBean.getBaseDao().CheckPMK(EntNo, POSNo, OperatorID,Tools.StringToByte(Code));
		if (chk){
			rc.setStatus(ErrorCode.OK);
			rc.setData(chk);
		}
		else{
			rc.setStatus(ErrorCode.DATABASEERROR);
		}
		return rc;
	}	
	/*
	 *获得MAK和PIK
	 * object = byte[40]
	 */
	public OutData GetPIKMAK(String EntNo,String POSNo,String OperatorID)
	{
		OutData rc = new OutData();
		byte []makpik = FindFactoryBean.getBaseDao().GetNewKey(EntNo, POSNo, OperatorID);
		if (makpik != null && makpik.length == 40){
			rc.setStatus(ErrorCode.OK);
			rc.setData(makpik);
		}
		else{
			rc.setStatus(ErrorCode.DATABASEERROR);
		}
		return rc;
	}

	/*
	 *获得数字签名MAC
	 * object = String
	 */
	public OutData GetMac(String EntNo,String POSNo,byte []data)
	{
		OutData rc = new OutData();
		String mac = FindFactoryBean.getBaseDao().GetMAC(EntNo, POSNo, data);
		if (mac != null && mac.length() == 8){
			rc.setStatus(ErrorCode.OK);
			rc.setData(mac);
		}
		else{
			rc.setStatus(ErrorCode.DATABASEERROR);
		}
		return rc;
	}

	/*
	 *数据加密
	 * object = byte[]
	 */
	public OutData EnCode(String EntNo,String POSNo,byte []data)
	{
		OutData rc = new OutData();
		byte []bindata = FindFactoryBean.getBaseDao().CoDecode(EntNo, POSNo, data,true);
		if (bindata != null){
			rc.setStatus(ErrorCode.OK);
			rc.setData(bindata);
		}
		else{
			rc.setStatus(ErrorCode.DATABASEERROR);
		}
		return rc;
	}

	/*
	 *数据解密
	 * object = byte[]
	 */
	public OutData Decode(String EntNo,String POSNo, byte []data)
	{
		OutData rc = new OutData();
		byte []bindata = FindFactoryBean.getBaseDao().CoDecode(EntNo, POSNo, data,false);
		if (bindata != null){
			rc.setStatus(ErrorCode.OK);
			rc.setData(bindata);
		}
		else{
			rc.setStatus(ErrorCode.DATABASEERROR);
		}
		return rc;
	}

	/*
	 *获得数据签名MAC并解密
	 * data需要签名的数据
	 * encodedata需要解密的数据
	 * object = MacDecode
	 */
	public OutData GetMacDecode(String EntNo,String POSNo, byte []data,byte []encodedata)	
	{
		OutData rc = new OutData();
		String mac = FindFactoryBean.getBaseDao().GetMAC(EntNo, POSNo, data);
		if (mac != null && mac.length() == 8){
			byte []bindata = FindFactoryBean.getBaseDao().CoDecode(EntNo, POSNo, encodedata,false);
			if (bindata != null){
				MacDecode rcdata = new MacDecode(mac,bindata);
				rc.setStatus(ErrorCode.OK);
				rc.setData(rcdata);
			}
			else{
				rc.setStatus(ErrorCode.DATABASEERROR);
			}
		}
		else{
			rc.setStatus(ErrorCode.DATABASEERROR);
		}
		return rc;
	}

}