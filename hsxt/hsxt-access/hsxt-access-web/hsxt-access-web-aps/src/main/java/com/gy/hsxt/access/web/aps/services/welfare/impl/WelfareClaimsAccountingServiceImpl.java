package com.gy.hsxt.access.web.aps.services.welfare.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.welfare.IWelfareClaimsAccountingService;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.api.IWsClaimsAccountingService;
import com.gy.hsxt.ws.bean.ClaimsAccountingDetail;
import com.gy.hsxt.ws.bean.ClaimsAccountingQueryCondition;
import com.gy.hsxt.ws.bean.ClaimsAccountingRecord;
import com.gy.hsxt.ws.bean.MedicalDetail;

/**
 * 积分福利理赔核算实现类
 * 
 * @category 积分福利理赔核算实现类
 * @projectName hsxt-access-web-aps
 * @package com.gy.hsxt.access.web.aps.services.welfare.impl.
 *          WelfareClaimsAccountingServiceImpl.java
 * @className WsClaimsAccountingServiceImpl
 * @description 积分福利理赔核算实现类
 * @author leiyt
 * @createDate 2015-12-29 下午4:15:38
 * @updateUser leiyt
 * @updateDate 2015-12-29 下午4:15:38
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
@Service
public class WelfareClaimsAccountingServiceImpl implements IWelfareClaimsAccountingService {

	/** 积分福利理赔核算 WS dubbo */
	@Autowired
	IWsClaimsAccountingService wsClaimsAccountingService;

	@Override
	public PageData<ClaimsAccountingRecord> listPendingClaimsAccounting(
			ClaimsAccountingQueryCondition queryCondition, Page page) throws HsException {
		try{
			return wsClaimsAccountingService.listPendingClaimsAccounting(queryCondition, page);
		} catch(HsException e){
			throw e;
		}catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "listPendingClaimsAccounting", "查询待核算理赔单失败", ex);
			return null;
		}
	}

	@Override
	public PageData<ClaimsAccountingRecord> listHisClaimsAccounting(
			ClaimsAccountingQueryCondition queryCondition, Page page) throws HsException {
		try{
			return wsClaimsAccountingService.listHisClaimsAccounting(queryCondition, page);

		} catch(HsException e){
			throw e;
		}catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "listHisClaimsAccounting", "查询已核算理赔单失败", ex);
			return null;
		}
	}

	@Override
	public void countMedicalDetail(List<MedicalDetail> list) throws HsException {
		try{
			wsClaimsAccountingService.countMedicalDetail(list);
		} catch(HsException e){
			throw e;
		}catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "countMedicalDetail", "理赔核算失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	@Override
	public void confirmClaimsAccounting(List<MedicalDetail> list) throws HsException {
		try{
			wsClaimsAccountingService.confrimClaimsAccounting(list);
		} catch(HsException e){
			throw e;
		}catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "confrimClaimsAccounting", "理赔核算确认失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}

	}

	public ClaimsAccountingDetail queryClaimsAccountingDetail(String accountingId)
			throws HsException {
		try{
			return wsClaimsAccountingService.queryClaimsAccountingDetail(accountingId);
		} catch(HsException e){
			throw e;
		}catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryClaimsAccountingDetail", "查询理赔核算详情失败", ex);
			return null;
		}
	}

	@Override
	public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
		// TODO Auto-generated method stub
		return null;
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
