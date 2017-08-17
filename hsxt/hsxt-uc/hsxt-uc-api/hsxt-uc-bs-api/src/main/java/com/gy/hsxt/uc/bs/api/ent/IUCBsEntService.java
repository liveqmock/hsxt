/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.bs.api.ent;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.bs.bean.common.BsBankAcctInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntAllInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntBaseInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntRegInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntStatusInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntTaxRate;
import com.gy.hsxt.uc.bs.bean.ent.BsHkEntRegInfo;

/**
 * 用户中心对业务BS系统提供企业相关服务
 * 
 * @Package: com.gy.hsxt.uc.bs.api.ent
 * @ClassName: IUCBsEntService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-20 上午9:06:58
 * @version V1.0
 */
public interface IUCBsEntService {

	/**
	 * 注册企业
	 * 
	 * @param regInfo
	 *            企业注册信息
	 * @throws HsException
	 */
	public String addEnt(BsEntRegInfo regInfo) throws HsException;

	/**
	 * 注册香港企业
	 * 
	 * @param regInfo
	 *            企业注册信息
	 * @throws HsException
	 */
	public String addEnt(BsHkEntRegInfo regInfo) throws HsException;

	/**
	 * 查询企业重要信息
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @return
	 * @throws HsException
	 */
	public BsEntMainInfo searchEntMainInfo(String entCustId) throws HsException;

	/**
	 * 根据企业互生号获取企业重要信息
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空
	 * @return
	 */
	public BsEntMainInfo getMainInfoByResNo(String entResNo) throws HsException;

	/**
	 * 修改企业重要信息（实名认证通过后）
	 * 
	 * @param entMainInfo
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 */
	public void updateEntMainInfo(BsEntMainInfo entMainInfo, String operator)
			throws HsException;

	/**
	 * 修改企业状态信息
	 * 
	 * @param entStatusInfo
	 *            企业状信息
	 * @param operator
	 *            操作者客户号
	 */
	public void updateEntStatusInfo(BsEntStatusInfo entStatusInfo,
			String operator) throws HsException;

	/**
	 * 查询企业所有信息
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @return
	 * @throws HsException
	 */
	public BsEntAllInfo searchEntAllInfo(String entCustId) throws HsException;

	/**
	 * 查询企业所有信息通过企业互生号
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @return
	 * @throws HsException
	 */
	public BsEntAllInfo searchEntAllInfoByResNo(String entResNo)
			throws HsException;

	/**
	 * 更改企业重要信息变更状态
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @param status
	 *            是否重要信息变更期间1:是 0：否
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 */
	public void changeEntMainInfoStatus(String entCustId, String status,
			String operator) throws HsException;

	/**
	 * 查询企业税率
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @return
	 * @throws HsException
	 */
	public String findTaxRate(String entCustId) throws HsException;

	/**
	 * 修改企业税率
	 * 
	 * @param entCustId
	 *            企业客户号 不能为空
	 * @param taxRate
	 *            企业税率
	 * @throws HsException
	 */
	public void updateTaxRate(String entCustId, String taxRate, String operator)
			throws HsException;

	/**
	 * 查询企业客户号通过企业互生号
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空
	 * @return
	 * @throws HsException
	 */
	public String findEntCustIdByEntResNo(String entResNo) throws HsException;

	/**
	 * 查询企业状态信息
	 * 
	 * @param entCustId
	 *            企业客户号 ，必传
	 * @return
	 * @throws HsException
	 */
	public BsEntStatusInfo searchEntStatusInfo(String entCustId)
			throws HsException;

	/**
	 * 查询企业一般信息
	 * 
	 * @param entCustId
	 *            企业客户号 ，必传
	 * @return
	 * @throws HsException
	 */

	public BsEntBaseInfo searchEntBaseInfo(String entCustId) throws HsException;

	/**
	 * 修改企业一般信息
	 * 
	 * @param entBaseInfo
	 *            企业基本信息
	 * @param operator
	 *            操作和客户号
	 * @throws HsException
	 */
	public void updateEntBaseInfo(BsEntBaseInfo entBaseInfo, String operator)
			throws HsException;
	
	/**
	 * 通过企业的客户号类型查询企业列表
	 * @param custType (企业客户类型1 持卡人;2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台;7 总平台; 8 其它地区平台
                    11：操作员 21：POS机；22：积分刷卡器；23：消费刷卡器；24：平板；25：云台51 非持卡人; 52 非互生格式化企业)
	 * @return
	 */
	public List<BsEntInfo> listEntInfo(int custType);
	
	/**
	 * 修改企业的重要信息（重要信息变更申请通过后）
	 * @param entMainInfo	企业的重要信息
	 * @param operCustId	操作员客户号
	 * @throws HsException
	 */
	public void updateMainInfoApplyInfo(BsEntMainInfo entMainInfo,
			String operCustId) throws HsException;
	

	 /**
	 * 新增企业注册接口
	 * @param regInfo	企业注册信息
	 * @param bsBankAcctInfo 企业银行账户信息 可为空
	  * @return		企业客户号
	  * @throws HsException
	  */
	public String regEntAddBankCard(BsEntRegInfo regInfo,BsBankAcctInfo bsBankAcctInfo)throws HsException;
	
	/**
	 * 企业修改税率审核通过
	 * @param entCustId		企业客户号
	 * @throws HsException
	 */
	public void updateNoEffectEntTaxRateInfo(BsEntTaxRate entTaxRate,String operCustId) throws HsException;
	
	/**
	 * 批量修改企业状态
	 * @param list
	 * @throws HsException
	 */
	public void batchUpdateEntStatusInfo(List<BsEntStatusInfo> list)throws HsException;
	
	/**
	 * 检查企业名称是否存在，可附加条件：客户类型
	 * 最新业务要求允许企业名称及营业执照重复
	 * @param entName
	 *            企业名称
	 * @param custType
	 *            企业类型 2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台; null 则忽略该条件
	 * @return
	 */
	@Deprecated
	public boolean isEntExist(String entName, Integer custType);
	
	/**
	 * 检查企业营业执照号是否存在，可附加条件：客户类型
	 * 最新业务要求允许企业名称及营业执照重复
	 * @param busiLicenseNo
	 *            营业执照号
	 * @param custType
	 *            企业类型 2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台; null 则忽略该条件
	 * @return
	 */
	@Deprecated
	public boolean isEntBusiLicenseNoExist(String busiLicenseNo, Integer custType);
	
	/**
	 * 企业注销
	 * @param entCustId		企业客户号
	 * @param operCustId	发起企业注销的操作员
	 * @throws HsException
	 */
	public void entCancel(String entCustId,String operCustId) throws HsException;
}
