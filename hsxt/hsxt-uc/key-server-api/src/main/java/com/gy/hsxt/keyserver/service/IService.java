/***************************************************************************
 * <PRE>
 *  Project Name    : KeyServer-API
 * 
 *  Package Name    : com.gy.hsxt.keyServer.service
 * 
 *  File Name       : IService.java
 * 
 *  Creation Date   : 2015年6月15日
 * 
 *  Author          : fandi
 * 
 *  Purpose         : keyServe
 *  				1获得PMK
 *  				2效验PMK
 *  				3获得MAK和PIK
 * 					4获得数字签名MAC
 * 					5数据加密
 * 					6数据解密
 * 					7获得数据签名MAC并解密
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
package com.gy.hsxt.keyserver.service;

import com.gy.hsxt.keyserver.beans.OutData;

public interface IService {
	/*
	 * 获得PMK
	 * object = byte[]
	 */
	OutData GetPMK(String EntNo,String POSNo,String OperatorID);
	/*
	 *效验PMK
	 * object = boolean
	 */
	OutData CheckPMK(String EntNo,String POSNo,String OperatorID,String Code);
	/*
	 *获得MAK和PIK
	 * object = PikMak
	 */
	OutData GetPIKMAK(String EntNo,String POSNo,String OperatorID);
	
	/*
	 *获得数字签名MAC
	 * object = String
	 */
	OutData GetMac(String EntNo,String POSNo,byte []data);
	/*
	 *数据加密
	 * object = byte[]
	 */
	OutData EnCode(String EntNo,String POSNo,byte []data);
	/*
	 *数据解密
	 * object = byte[]
	 */
	OutData Decode(String EntNo,String POSNo,byte []data);
	
	/*
	 *获得数据签名MAC并解密
	 * data需要签名的数据
	 * encodedata需要解密的数据
	 * object = MacDecode
	 */
	OutData GetMacDecode(String EntNo,String POSNo,byte []data,byte []encodedata);
}
