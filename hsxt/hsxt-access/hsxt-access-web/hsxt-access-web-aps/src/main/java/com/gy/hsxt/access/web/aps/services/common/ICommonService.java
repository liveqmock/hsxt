/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.common;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntContactInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/**
 * 公共Service接口
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.common
 * @ClassName: ICommonService
 * @Description: TODO
 * @author: likui
 * @date: 2016年4月11日 上午10:24:50
 * @company: gyist
 * @version V3.0.0
 */
public interface ICommonService {

	/**
	 * 工单拒绝办理
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月11日 上午11:08:31
	 * @param bizNo
	 *            业务编号
	 * @param exeCustId
	 *            经办人
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void workTaskRefuseAccept(String bizNo, String exeCustId) throws HsException;

	/**
	 * 工单挂起
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月11日 上午11:08:47
	 * @param bizNo
	 *            业务编号
	 * @param exeCustId
	 *            经办人
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void workTaskHangUp(String bizNo, String exeCustId, String remark) throws HsException;

	/**
	 * 查询企业扩展信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 下午2:35:21
	 * @param companyCustId
	 * @return
	 * @throws HsException
	 * @return : AsEntExtendInfo
	 * @version V3.0.0
	 */
	public AsEntExtendInfo findCompanyExtInfo(String companyCustId) throws HsException;

	/**
	 * 查询企业一般信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 下午2:28:25
	 * @param companyCustId
	 * @return
	 * @throws HsException
	 * @return : AsEntBaseInfo
	 * @version V3.0.0
	 */
	public AsEntBaseInfo findCompanyBaseInfo(String entCustId) throws HsException;

	/**
	 * 查询企业重要信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 下午2:29:11
	 * @param companyCustId
	 * @return
	 * @throws HsException
	 * @return : AsEntMainInfo
	 * @version V3.0.0
	 */
	public AsEntMainInfo findCompanyMainInfo(String entCustId) throws HsException;

	/**
	 * 查询企业所有信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 下午2:29:05
	 * @param companyCustId
	 * @return
	 * @throws HsException
	 * @return : AsEntAllInfo
	 * @version V3.0.0
	 */
	public AsEntAllInfo findCompanyAllInfo(String entCustId) throws HsException;

	/**
	 * 查询企业状态信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 下午2:28:59
	 * @param companyCustId
	 * @return
	 * @throws HsException
	 * @return : AsEntStatusInfo
	 * @version V3.0.0
	 */
	public AsEntStatusInfo findCompanyStatusInfo(String entCustId) throws HsException;

	/**
	 * 根据企业资源号获取企业重要信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 下午2:30:39
	 * @param companyResNo
	 * @return
	 * @throws HsException
	 * @return : AsEntMainInfo
	 * @version V3.0.0
	 */
	public AsEntMainInfo findCompanyMainInfoByResNo(String companyResNo) throws HsException;

	/**
	 * 查询企业的联系信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 下午2:31:10
	 * @param companyCustId
	 * @return
	 * @throws HsException
	 * @return : AsEntContactInfo
	 * @version V3.0.0
	 */
	public AsEntContactInfo findCompanyContactInfo(String companyCustId) throws HsException;

	/**
	 * 查询企业客户号 ，必传通过企业互生号
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 下午2:32:20
	 * @param companyResNo
	 * @return
	 * @throws HsException
	 * @return : String
	 * @version V3.0.0
	 */
	public String findCompanyCustIdByEntResNo(String companyResNo) throws HsException;
}
