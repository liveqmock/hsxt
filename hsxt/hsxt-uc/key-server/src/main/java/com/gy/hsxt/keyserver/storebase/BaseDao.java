
package com.gy.hsxt.keyserver.storebase;

/***************************************************************************
 * <PRE>
 *  Project Name    : KeyServer
 * 
 *  Package Name    : com.gy.kms.keyserver.DAO
 * 
 *  File Name       : BaseDao.java
 * 
 *  Creation Date   : 2014-7-4
 * 
 *  Author          : fandi
 * 
 *  Purpose         : 业务功能接口
 * 
 * </PRE>
 ***************************************************************************/
public interface BaseDao {
	public byte[] GetNewKey(String Ent,String PosID,String Operator);
	public byte[] GetNewPMK(String ent, String posID, String operator);
	public boolean CheckPMK(String Ent, String PosID, String Operator,byte[]check);
	public String GetMAC(String Ent, String PosID, byte[] data);
	public byte []CoDecode(String Ent, String PosID, byte[] data,boolean iFencrypt);
	public store getSave();
}
