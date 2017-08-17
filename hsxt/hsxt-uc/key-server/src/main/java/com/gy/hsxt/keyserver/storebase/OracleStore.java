package com.gy.hsxt.keyserver.storebase;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


import com.alibaba.druid.util.HexBin;
//import com.gy.common.log.GyLogger;
import com.gy.hsxt.keyserver.tools.*;
import com.gy.kms.keyserver.CoDecode;


/***************************************************************************
 * <PRE>
 *  Project Name    : KeyServer
 * 
 *  Package Name    : com.gy.kms.storebase
 * 
 *  File Name       : OracleStore.java
 * 
 *  Creation Date   : 2014-7-4
 * 
 *  Author          : fandi
 * 
 *  Purpose         : Oracle数据源
 * 
 * </PRE>
 ***************************************************************************/
public class OracleStore implements store {
	public OracleStore(){
		GetSize();
	}
//	final GyLogger logger = GyLogger.getLogger(getClass());
	private JdbcTemplate jdbcTemplate;
	final String SQLQUERY = "select GY_POS_PMK from T_GY_POS_PMK t where t.gy_pos_no=?";
	final String SQLUPDATE = "update T_GY_POS_PMK t set t.GY_POS_PMK=?,t.GY_UPDATE_DATE=SYSDATE,t.GY_UPDATE_MAN=?"
			+ ",t.GY_SUB_CENTER=?,t.gy_pikmak='',t.gy_checkin_time=0 where t.gy_pos_no=?";
	final String SQLINSERT = "insert INTO T_GY_POS_PMK (GY_POS_PMK_ID,GY_POS_NO,GY_POS_PMK,GY_CREATE_DATE,GY_CREATE_MAN,"
			+ "GY_UPDATE_DATE,GY_UPDATE_MAN,GY_SUB_CENTER) VALUES (SEQ_T_GY_POS_PMK.NEXTVAL,?,?,SYSDATE,?,SYSDATE,?,?)";
	final String SQLUPDATEPIKMAK = "update T_GY_POS_PMK t set t.GY_PIKMAK=?,t.GY_CHECKIN_TIME=? where t.gy_pos_no=?";
	final String SQLQUERYALL = "select t.GY_POS_NO,t.GY_POS_PMK,t.GY_PIKMAK,t.GY_CHECKIN_TIME from T_GY_POS_PMK t";
	final String SQLQUERYALLInOne = "select t.GY_POS_NO,t.GY_POS_PMK,t.GY_PIKMAK,t.GY_CHECKIN_TIME from T_GY_POS_PMK t where t.gy_pos_no=?";
	final String SQLCOUNT = "select count(*) COUNTALL from T_GY_POS_PMK t";
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private class AssociateInfoMapper implements RowMapper<Blob> {
		public Blob mapRow(ResultSet rs, int i) throws SQLException {
			return rs.getBlob("GY_POS_PMK");
		}
	}
	private class CountAllMapper implements RowMapper<Integer> {
		public Integer mapRow(ResultSet rs, int i) throws SQLException {
			return rs.getInt("COUNTALL");
		}
	}

	private class AllData {
		String posID;
		CashData data = null;

		@SuppressWarnings("unused")
		String GetPosID() {
			return posID;
		}

		CashData GetData() {
			return data;
		}

		AllData(String posID, Blob pmk, String PIKMAK, long time) {
			byte pmk_src[] = null;
			byte pik_src[] = null;
			byte mak_src[] = null;
			this.posID = posID;
			try {
				int len = (int) pmk.length();
				if (len == 16) {
					byte pmk_enc[] = pmk.getBytes(1, len);
					pmk_src = CoDecode.decryptPMK(pmk_enc);
//					logger.debug("pmkenc:" + HexBin.encode(pmk_enc));
//					logger.debug("pmksrc:" + HexBin.encode(pmk_src));
				} else {
//					logger.debug("no PMK in this:" + posID);
					return;
				}
			} catch (Exception e) {
//				logger.debug("no PMK in this:" + posID);
				return;
			}
			if (PIKMAK != null && PIKMAK.length() == 48) {
				pik_src = new byte[16];
				mak_src = new byte[8];
				byte pik[] = HexBin.decode(PIKMAK.substring(0, 32));
				byte mak[] = HexBin.decode(PIKMAK.substring(32));
//				logger.debug("pikenc:" + HexBin.encode(pik));
//				logger.debug("macenc:" + HexBin.encode(mak));
				CoDecode.DES3decrypt(pmk_src, pik, 16, pik_src);
				CoDecode.DES3decrypt(pmk_src, mak, 8, mak_src);
//				logger.debug("piksrc:" + HexBin.encode(pik_src));
//				logger.debug("macsrc:" + HexBin.encode(mak_src));
			}
			data = new CashData(time, pmk_src, pik_src, mak_src);
		}
	}

	private class AssociateAllMapper implements RowMapper<AllData> {
		public AllData mapRow(ResultSet rs, int i) throws SQLException {
			return new AllData(rs.getString("GY_POS_NO"),
					rs.getBlob("GY_POS_PMK"), rs.getString("GY_PIKMAK"),
					rs.getLong("GY_CHECKIN_TIME"));
		}
	}
/**
 * 写PMK到数据库，对于已存在key数据更新，不存在key则插入
 * @param PosID
 * @param Operator
 * @param PMK_enc
 */
	private void WriteToDB(String PosID, String Operator,
			byte[] PMK_enc) {
		byte old[] = GetPMKfromDB(PosID);
		if (old != null && old.length == 16) // 原key已经存在
		{
//			logger.debug("go update:PosID=" + PosID
//					+ "\t operator=" + Operator + "\n" + HexBin.encode(PMK_enc));
			UpdatePMKfromDB(PosID, Operator, PMK_enc);
		} else {
//			logger.debug("go insert:PosID=" + PosID
//					+ "\t operator=" + Operator + "\n" + HexBin.encode(PMK_enc));
			InsertPMKfromDB(PosID, Operator, PMK_enc);
		}
	}

	private void UpdatePMKfromDB(String PosID, String operator,
			byte[] pmk) {
		Object[] params = { pmk, operator, Config.getSubCenter(),  PosID };
		int[] argTypes = { Types.BINARY, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR };
//		logger.debug("SubCenter:" + Config.getSubCenter());
		jdbcTemplate.update(SQLUPDATE, params, argTypes);
	}

	private AllData GetAllfromDB(String PosID) {
		AllData rc = null;
		try {
			rc = jdbcTemplate.queryForObject(SQLQUERYALLInOne,
					new Object[] {PosID }, new AssociateAllMapper());
		} catch (Exception e) {
//			logger.debug("qurey not find PosID:" + PosID);
		}
		return rc;
	}

	private void InsertPMKfromDB(String PosID, String operator,
			byte[] pmk) {
		Object[] params = { PosID, pmk, operator, operator,
				Config.getSubCenter() };
		int[] argTypes = { Types.VARCHAR, Types.BINARY, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR };
		jdbcTemplate.update(SQLINSERT, params, argTypes);
	}

	private byte[] GetPMKfromDB(String PosID) {
		byte rc[] = null;
		try {
			Blob pmk = jdbcTemplate.queryForObject(SQLQUERY,
					new Object[] { PosID }, new AssociateInfoMapper());
			int len = (int) pmk.length();
			if (len != 16)
				return null;
			rc = pmk.getBytes(1, len);
		} catch (Exception e) {
//			logger.debug("qurey not find PosID" + PosID);
		}
		return rc;
	}

	private boolean WritePikMakToDB(String PosID, String PIKMAK, long time) {
		Object[] params = { PIKMAK, time, PosID };
		int[] argTypes = { Types.VARCHAR, Types.INTEGER, Types.VARCHAR };
		jdbcTemplate.update(SQLUPDATEPIKMAK, params, argTypes);
		return true;
	}

	public void SetPMK(String POSID, byte PMK[]) {
		WriteToDB(POSID,Config.getSubCenter(),CoDecode.encryptPMK(PMK));
	}

	public void SetPIKMAK(String POSID, byte PIK[], byte MAK[]) {
		byte pmk[] = GetPMKfromDB(POSID);
		if (pmk != null) {
			byte pmk_src[] = CoDecode.decryptPMK(pmk);
			byte MAK_enc[] = new byte[8];
			byte PIK_enc[] = new byte[16];
			CoDecode.DES3encrypt(pmk_src, MAK, 8, MAK_enc);
			CoDecode.DES3encrypt(pmk_src, PIK, 16, PIK_enc);
			WritePikMakToDB(POSID,
					HexBin.encode(PIK_enc) + HexBin.encode(MAK_enc),
					(new Date()).getTime() / 1000);
		}
	}

	public int GetSize() {
		int rc = 0;
		try {
			rc = jdbcTemplate.queryForObject(SQLCOUNT,
					new Object[] {}, new CountAllMapper());
		} catch (Exception e) {
			
		}
		return rc;
	}
	public byte[] GetPMK(String POSID) {
		byte []rc = GetPMKfromDB(POSID);
		return (rc == null)?null:CoDecode.decryptPMK(rc);
	}
	public CashData getPIKPMK(String POSID) {
		CashData data = getALL(POSID);
		return (data != null && data.Check())? data:null;
	}

	public void SetALL(String POSID, CashData data) {
	}

	public CashData getALL(String POSID) {
		AllData data = GetAllfromDB(POSID);
		if (data != null){
			return data.GetData();
		}
		return null;
	}
}
