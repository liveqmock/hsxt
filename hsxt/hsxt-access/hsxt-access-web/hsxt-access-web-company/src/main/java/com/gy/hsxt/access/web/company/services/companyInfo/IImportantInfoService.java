/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.services.companyInfo;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.entstatus.EntChangeInfo;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

/***
 * 企业的重要信息
 * 
 * @Package: com.gy.hsxt.access.web.business.systemInfo.service  
 * @ClassName: IImportantInfoService 
 * @Description: TODO
 *
 * @author: liuxy 
 * @date: 2015-9-29 下午7:11:45 
 * @version V3.0.0
 */
public interface IImportantInfoService extends IBaseService{
	/***
	 * 提交重要信息
	 * @param pageId 页面的唯一标识
	 * @return
	 * @throws HsException
	 */
	public Object submitChangeApply(EntChangeInfo info) throws HsException;
	
	/***
	 * 查找变更 的详细记录
	 * @param applyId
	 * @return
	 * @throws HsException
	 */
	public EntChangeInfo findApplyDetail(String applyId) throws HsException;
	
	 
	 /***
	  * 查找申请单信息
	  * @param custId
	  * @return
	  */
	 public EntChangeInfo findApplyOrder(String entCustId) throws HsException;
}
