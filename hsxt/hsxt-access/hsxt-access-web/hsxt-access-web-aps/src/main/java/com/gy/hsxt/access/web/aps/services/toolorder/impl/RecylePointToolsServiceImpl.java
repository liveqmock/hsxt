/**
 * 
 */
package com.gy.hsxt.access.web.aps.services.toolorder.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.toolorder.IRecylePointToolsService;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.api.tool.IBSToolAfterService;
import com.gy.hsxt.bs.bean.tool.AfterService;
import com.gy.hsxt.bs.bean.tool.AfterServiceDetail;
import com.gy.hsxt.bs.bean.tool.ImportAfterService;
import com.gy.hsxt.bs.bean.tool.resultbean.EntDeviceInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;

/**
 * @author weiyq
 *
 */
@Service
public class RecylePointToolsServiceImpl implements IRecylePointToolsService{
	
	@Autowired
	private IBSToolAfterService bsToolAfterService;
	
	@Autowired
	private IUCAsPwdService iucasPwdService;
	
	/**
	 * 分页查询企业设备信息
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	@Override
	public PageData<EntDeviceInfo> queryEntDeviceInfoPage(Map filterMap, Map sortMap, Page page) 
			throws HsException{
		
		String seqNo = (String)filterMap.get("seqNo");
		String entResNo = (String)filterMap.get("entResNo");
		try
		{
			return bsToolAfterService.queryEntDeviceInfoPage(seqNo, entResNo, page);
      } catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryEntDeviceInfoPage", "分页查询企业设备信息失败", ex);
			return null;
		}
	}
	
	/**
	 * 验证批量导入设备序列号
	 * @param list
	 * @return
	 * @throws HsException
	 */
	@Override
	public List<ImportAfterService> validBatchImportSeqNo(List<String> list)throws HsException{
		try
		{
			return bsToolAfterService.validBatchImportSeqNo(list);
       } catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "validBatchImportSeqNo", "验证批量导入设备序列号失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}
	
	/**
	 * 批量导入售后
	 * @param paramList
	 * @throws HsException
	 */
	@Override
	public void batchImportAfterService(List<String> deviceList)throws HsException{
		
		List<ImportAfterService> paramList = new LinkedList<ImportAfterService>();
		for(String str : deviceList){
			String[] ary = str.split(",");
			ImportAfterService obj = new ImportAfterService();
			obj.setEntResNo(ary[0]);
			obj.setTerminalNo(ary[1]);
			obj.setDeviceSeqNo(ary[2]);
			Integer deviceFalg = Integer.parseInt(ary[3]);
			obj.setDeviceFalg(deviceFalg);
			paramList.add(obj);
		}
		
		try
		{
			bsToolAfterService.batchImportAfterService(paramList);
       } catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "batchImportAfterService", "批量导入售后失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}
	
	/**
	 * 添加售后单
	 * @param obj
	 * @throws HsException
	 */
	@Override
	public void addAfterService(String entCustId,String entResNo,String entCustName,String reqRemark,String reqOperator,String listDetail )throws HsException{

		RequestUtil.verifyParamsIsNotEmpty(
				new Object[]{entCustId,ASRespCode.SW_APPLY_ID_NOT_NULL.getCode(),ASRespCode.SW_APPLY_ID_NOT_NULL.getDesc()},
				new Object[]{entResNo,ASRespCode.SW_APPLY_ID_NOT_NULL.getCode(),ASRespCode.SW_APPLY_ID_NOT_NULL.getDesc()},
				new Object[]{entCustName,ASRespCode.SW_APPLY_ID_NOT_NULL.getCode(),ASRespCode.SW_APPLY_ID_NOT_NULL.getDesc()},
				new Object[]{reqOperator,ASRespCode.SW_APPLY_ID_NOT_NULL.getCode(),ASRespCode.SW_APPLY_ID_NOT_NULL.getDesc()},
				new Object[]{listDetail,ASRespCode.SW_APPLY_ID_NOT_NULL.getCode(),ASRespCode.SW_APPLY_ID_NOT_NULL.getDesc()}
				);
		AfterService asObj = new AfterService();
		asObj.setEntCustId(entCustId);
		asObj.setEntResNo(entResNo);
		asObj.setEntCustName(entCustName);
		asObj.setReqRemark(reqRemark);
		asObj.setReqOperator(reqOperator);
		List<String> deviceList;
		try {
			deviceList = JSON.parseArray(URLDecoder.decode(listDetail, "UTF-8"),String.class);
		} catch (UnsupportedEncodingException e) {
			throw new HsException();
		}
		List<AfterServiceDetail> detail = new LinkedList<AfterServiceDetail>();
		for(String str : deviceList){
			String[] ary = str.split(";");
			AfterServiceDetail asd = new AfterServiceDetail();
			asd.setDeviceSeqNo(ary[0]);
			asd.setTerminalNo(ary[1]);
			if(ary.length>2){
				try{
					Integer disposeType = null;
					if(ary[2]!=null&&!ary[2].equals("")){
						disposeType = Integer.parseInt(ary[2]);
					}
					asd.setDisposeType(disposeType);
				}catch(Exception e){
					
				}
				try{
					asd.setDisposeAmount(ary[3]);
				}catch(Exception e){
					
				}
			}
			detail.add(asd);
		}
		asObj.setDetail(detail);
		try
		{
			bsToolAfterService.addAfterService(asObj);
       } catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "addAfterService", "添加售后单失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	@Override
	public PageData findScrollResult(Map filterMap, Map sortMap, Page page)
			throws HsException {
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
	public void checkVerifiedCode(String custId, String verifiedCode)
			throws HsException {
		// TODO Auto-generated method stub
		
	}
}
