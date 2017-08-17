package com.gy.hsxt.keyserver.storebase;

/***************************************************************************
 * <PRE>
 *  Project Name    : KeyServer
 * 
 *  Package Name    : com.gy.kms.storebase
 * 
 *  File Name       : store.java
 * 
 *  Creation Date   : 2014-7-4
 * 
 *  Author          : fandi
 * 
 *  Purpose         : 数据源使用接口 
 * 
 * </PRE>
 ***************************************************************************/
public interface store {
	/**
	 * 保存PMK
	 * 不更新时效
	 * @param POSID 企业资源号加POS编号 作为KEY
	 * @PMK
	 */
	void SetPMK(String POSID,byte PMK[]);
/**
 * 获取PMK
 * 不效验时效
 * @param POSID 企业资源号加POS编号 作为KEY
 * @return PMK
 */
	byte []GetPMK(String POSID);
/**
 * 保存PIK和MAK， 设置时效为当前
 * @param POSID
 * @param PIK
 * @param MAK
 */
	void SetPIKMAK(String POSID, byte PIK[],byte MAK[]);
/**
 * 在数据源中寻找Key数据
 * @param POSID
 * @return  效验时效，KEY不存在和失效返回NULL，成功则返回PMK、PIK、MAK
 */
	CashData getPIKPMK(String POSID);
/**
 * 返回数据源数据数量
 * @return
 */
 	int GetSize();	
 
/**
 * 添加数据
 * 内部使用,恢复cash用，不修改改时效
 * @param POSID
 * @param data
 */
	void SetALL(String POSID, CashData data);

/**
 * 获取数据源数据
 * 不效验时效,内部使用
 * @param POSID
 * @return
 */
	CashData getALL(String POSID);
}
