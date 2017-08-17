package com.gy.hsxt.keyserver.storebase;

//import com.gy.common.log.GyLogger;

/***************************************************************************
 * <PRE>
 *  Project Name    : KeyServer
 * 
 *  Package Name    : com.gy.kms.storebase
 * 
 *  File Name       : doubleStore.java
 * 
 *  Creation Date   : 2014-7-4
 * 
 *  Author          : fandi
 * 
 *  Purpose         : 双数据源，用于缓存数据源和持久化数据源
 * 
 * </PRE>
 ***************************************************************************/
public class doubleStore implements store{
//	private static final GyLogger LOGGER = GyLogger.getLogger(doubleStore.class);
/**
 * 缓存数据源
 */
	store cashStore;
/**
 * 持久化数据源
 */
	store permanentStore;
	public doubleStore(store cashStore,store permanentStore){
		this.cashStore = cashStore;
		this.permanentStore = permanentStore;
	}
	public void SetPMK(String POSID, byte[] PMK) {
		permanentStore.SetPMK(POSID, PMK);
		cashStore.SetPMK(POSID, PMK);
	}

	public void SetPIKMAK(String POSID, byte[] PIK, byte[] MAK) {
		permanentStore.SetPIKMAK(POSID, PIK, MAK);
		cashStore.SetPIKMAK(POSID, PIK, MAK);
	}
/**
 * 如果缓存中没有，取持久化数据，同时保存到缓存中
 */
	public byte[] GetPMK(String POSID) {
		byte[] rc = null;
		if((rc = cashStore.GetPMK(POSID)) == null){
//			LOGGER.debug("restore from oracle");
			rc = permanentStore.GetPMK(POSID);
			if (rc != null){
				cashStore.SetPMK(POSID, rc);
			}
		}
		return rc;
	}

	/**
	 * 如果缓存中没有，取持久化数据，同时保存到缓存中
	 */
	public CashData getPIKPMK(String POSID) {
		CashData rc = null;
		if((rc = cashStore.getALL(POSID)) == null){
//			LOGGER.debug("restore from oracle");
			rc = permanentStore.getALL(POSID);
			if (rc != null){
				cashStore.SetALL(POSID, rc);
			}
		}
		if (rc != null &&rc.Check())
			return rc;
		return null;
	}

	public int GetSize() {
		return permanentStore.GetSize();
	}
/**
 * never use this function
 */
	public void SetALL(String POSID, CashData data) {
	}

/**
 * never use this function
 */
	public CashData getALL(String POSID) {
		return null;
	}

}
