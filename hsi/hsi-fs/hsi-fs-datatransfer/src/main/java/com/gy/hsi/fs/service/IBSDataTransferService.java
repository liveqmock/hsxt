/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.service;

import java.util.List;

import com.gy.hsi.fs.mapper.vo.dbbs01.TBsCardStyle;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsCompanyStop;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsDebit;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsDeclareCorpAptitude;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsDeclareEntLinkman;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntChangeInfoApp;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntChangeInfoData;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntRealnameAuth;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsFilingAptitude;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerChangeInfoApp;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerChangeInfoData;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerRealnameAuth;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPointgame;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsResetTranpwdapply;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsShippingMethod;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsTaxrateChange;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsToolProduct;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-datatransfer
 * 
 *  Package Name    : com.gy.hsi.fs.service
 * 
 *  File Name       : IBSDataTransferService.java
 * 
 *  Creation Date   : 2015年11月20日
 * 
 *  Author          : Administrator
 * 
 *  Purpose         : none
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IBSDataTransferService {

	public List<TBsCardStyle> queryTBsCardStyleList(int start, int end);

	public List<TBsDebit> queryTBsDebitList(int start, int end);

	public List<TBsDeclareCorpAptitude> queryTBsDeclareCorpAptitudeList(
			int start, int end);

	public List<TBsDeclareEntLinkman> queryTBsDeclareEntLinkmanList(int start,
			int end);

	public List<TBsEntChangeInfoApp> queryTBsEntChangeInfoAppList(int start,
			int end);

	public List<TBsEntRealnameAuth> queryTBsEntRealnameAuthList(int start,
			int end);

	public List<TBsFilingAptitude> queryTBsFilingAptitudeList(int start, int end);

	public List<TBsPerChangeInfoApp> queryTBsPerChangeInfoAppList(int start,
			int end);

	public List<TBsPerRealnameAuth> queryTBsPerRealnameAuthList(int start,
			int end);

	public List<TBsPointgame> queryTBsPointgameList(int start, int end);

	public List<TBsResetTranpwdapply> queryTBsResetTranpwdapplyList(int start,
			int end);

	public List<TBsShippingMethod> queryTBsShippingMethodList(int start, int end);

	public List<TBsTaxrateChange> queryTBsTaxrateChangeList(int start, int end);

	public List<TBsToolProduct> queryTBsToolProductList(int start, int end);
	
	public List<TBsCompanyStop> queryTBsCompanyStopList(int start, int end);
	
	public List<TBsEntChangeInfoData> queryTBsEntChangeInfoDataList(int start, int end);
	
	public List<TBsPerChangeInfoData> queryTBsPerChangeInfoDataList(int start, int end);

	public boolean updateTBsCardStyle(TBsCardStyle record);

	public boolean updateTBsDebit(TBsDebit record);

	public boolean updateTBsDeclareCorpAptitude(TBsDeclareCorpAptitude record);

	public boolean updateTBsDeclareEntLinkman(TBsDeclareEntLinkman record);

	public boolean updateTBsEntChangeInfoApp(TBsEntChangeInfoApp record);

	public boolean updateTBsEntRealnameAuth(TBsEntRealnameAuth record);

	public boolean updateTBsFilingAptitude(TBsFilingAptitude record);

	public boolean updateTBsPerChangeInfoApp(TBsPerChangeInfoApp record);

	public boolean updateTBsPerRealnameAuth(TBsPerRealnameAuth record);

	public boolean updateTBsPointgame(TBsPointgame record);

	public boolean updateTBsResetTranpwdapply(TBsResetTranpwdapply record);

	public boolean updateTBsShippingMethod(TBsShippingMethod record);

	public boolean updateTBsTaxrateChange(TBsTaxrateChange record);

	public boolean updateTBsToolProduct(TBsToolProduct record);
	
	public boolean updateTBsCompanyStop(TBsCompanyStop record);
	
	public boolean updateTBsEntChangeInfoData(TBsEntChangeInfoData record);
	
	public boolean updateTBsPerChangeInfoData(TBsPerChangeInfoData record);
}
