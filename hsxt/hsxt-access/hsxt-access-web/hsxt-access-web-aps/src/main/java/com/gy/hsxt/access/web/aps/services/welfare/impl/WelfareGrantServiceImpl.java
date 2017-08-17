package com.gy.hsxt.access.web.aps.services.welfare.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.welfare.IWelfareGrantService;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.api.IWsGrantService;
import com.gy.hsxt.ws.bean.GrantDetail;
import com.gy.hsxt.ws.bean.GrantQueryCondition;
import com.gy.hsxt.ws.bean.GrantRecord;

/**
 * 
 * 积分福利发放服务实现类
 * 
 * @category 积分福利发放服务实现类
 * @projectName hsxt-access-web-aps
 * @package 
 *          com.gy.hsxt.access.web.aps.services.welfare.impl.WelfareGrantServiceImpl
 *          .java
 * @className WelfareGrantServiceImpl
 * @description 积分福利发放服务实现类
 * @author leiyt
 * @createDate 2015-12-31 下午5:01:45
 * @updateUser leiyt
 * @updateDate 2015-12-31 下午5:01:45
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
@Service
public class WelfareGrantServiceImpl implements IWelfareGrantService {

	/**
	 * 积分福利发放 WS dubbo
	 */
	@Autowired
	IWsGrantService wsGrantService;

	@Override
	public PageData<GrantRecord> listPendingGrant(GrantQueryCondition queryCondition, Page page)
			throws HsException {
		try{
			return wsGrantService.listPendingGrant(queryCondition, page);
		} catch(HsException e){
			throw e;
		}catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "listPendingGrant", "查询待发放记录失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	@Override
	public PageData<GrantRecord> listHistoryGrant(GrantQueryCondition queryCondition, Page page)
			throws HsException {
		try{
			return wsGrantService.listHistoryGrant(queryCondition, page);
		} catch(HsException e){
			throw e;
		}catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "listHistoryGrant", "查询待已发放记录失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	@Override
	public GrantDetail queryGrantDetail(String grantId) throws HsException {
		try{
			return wsGrantService.queryGrantDetail(grantId);
		} catch(HsException e){
			throw e;
		}catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryGrantDetail", "查询发放详情失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	@Override
	public void grantWelfare(String grantId, String remark) throws HsException {
		try{
			wsGrantService.grantWelfare(grantId, remark);
		} catch(HsException e){
			throw e;
		}catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "grantWelfare", "发放操作失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	@Override
	public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
		GrantQueryCondition queryCondition = bulidQueryCondition(filterMap);
		try
		{
			return listPendingGrant(queryCondition, page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findScrollResult", "查询积分福利发放失败", ex);
			return null;
		}
	}

	private GrantQueryCondition bulidQueryCondition(Map filterMap) {
		GrantQueryCondition queryCondition = new GrantQueryCondition();
		// welfareType 0意外伤害1免费医疗2他人身故
		queryCondition.setWelfareType(CommonUtils.toInteger(filterMap.get("welfareType")));
		// 发放人客户号
		queryCondition.setGrantPersonCustId((String) filterMap.get("givingPersonCustId"));
		// 申请人证件号码
		queryCondition.setProposerPapersNo((String) filterMap.get("proposerPapersNo"));
		// 申请人姓名
		queryCondition.setProposerName((String) filterMap.get("proposerName"));
		// 申请人互生号
		queryCondition.setProposerResNo((String) filterMap.get("proposerResNo"));
		return queryCondition;
	}

	public PageData findScrollResult2(Map filterMap, Map sortMap, Page page) throws HsException {
		GrantQueryCondition queryCondition = bulidQueryCondition(filterMap);
		try
		{
			return listHistoryGrant(queryCondition, page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findScrollResult", "查询积分福利发放失败", ex);
			return null;
		}
	}

	@Override
	public Object findById(Object id) throws HsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save(String entityJsonStr) throws HsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkVerifiedCode(String custId, String verifiedCode) throws HsException {
		// TODO Auto-generated method stub

	}

}
