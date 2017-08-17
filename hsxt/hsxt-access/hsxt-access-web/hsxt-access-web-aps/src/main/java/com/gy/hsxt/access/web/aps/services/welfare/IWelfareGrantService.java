package com.gy.hsxt.access.web.aps.services.welfare;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.bean.GrantDetail;
import com.gy.hsxt.ws.bean.GrantQueryCondition;
import com.gy.hsxt.ws.bean.GrantRecord;

/**
 * 
 * 积分福利--福利发放 接口
 * @category      积分福利福利发放
 * @projectName   hsxt-access-web-aps
 * @package       com.gy.hsxt.access.web.aps.services.welfare.IWelfareGrantService.java
 * @className     IWelfareGrantService
 * @description   积分福利--福利发放
 * @author        leiyt
 * @createDate    2015-12-31 下午5:02:58  
 * @updateUser    leiyt
 * @updateDate    2015-12-31 下午5:02:58
 * @updateRemark 	说明本次修改内容
 * @version       v0.0.1
 */
public interface IWelfareGrantService  extends IBaseService {
	/**
	 * 查询待发放记录
	 * @category 				查询待发放记录
	 * @param queryCondition	查询条件对象
	 * @param page				分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<GrantRecord> listPendingGrant(GrantQueryCondition queryCondition, Page page) throws HsException;
	/**
	 * 查询待已发放记录
	 * @category 				查询待已发放记录
	 * @param queryCondition	查询条件对象
	 * @param page				分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<GrantRecord> listHistoryGrant(GrantQueryCondition queryCondition, Page page) throws HsException;
	
	/**
	 * 查询发放详情
	 * @category 		查询发放详情
	 * @param grantId	发放流水号
	 * @return
	 * @throws HsException
	 */
	public GrantDetail queryGrantDetail(String grantId) throws HsException;
	
	/**
	 * 发放操作
	 * @category 		发放操作
	 * @param grantId	发放流水号
	 * @param remark	发放备注信息
	 * @throws HsException
	 */
	public void grantWelfare(String grantId,  String remark)throws HsException;
}
