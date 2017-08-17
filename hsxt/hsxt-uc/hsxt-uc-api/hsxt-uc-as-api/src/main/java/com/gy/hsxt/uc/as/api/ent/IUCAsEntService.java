/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.as.api.ent;
import java.util.List;
import java.util.Map;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsBelongEntInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntContactInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntTaxRate;
import com.gy.hsxt.uc.as.bean.ent.AsEntUpdateLog;
import com.gy.hsxt.uc.as.bean.ent.AsQueryBelongEntCondition;
import com.gy.hsxt.uc.as.bean.ent.AsQueryEntCondition;

/**
 * 用户中心对接入提供企业相关服务接口
 * 
 * @Package: com.gy.hsxt.uc.as.api.ent
 * @ClassName: IUCAsEntService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-19 下午12:10:43
 * @version V1.0
 */
public interface IUCAsEntService {
	/**
	 * 查询企业一般信息
	 * 
	 * @param entCustId
	 *            企业客户号 ，必传
	 * @return
	 * @throws HsException
	 */

	public AsEntBaseInfo searchEntBaseInfo(String entCustId) throws HsException;

	/**
	 * 查询企业重要信息
	 * 
	 * @param entCustId
	 *            企业客户号 ，必传
	 * @return
	 * @throws HsException
	 */
	public AsEntMainInfo searchEntMainInfo(String entCustId) throws HsException;

	/**
	 * 查询企业所有信息
	 * 
	 * @param entCustId
	 *            企业客户号 ，必传
	 * @return
	 * @throws HsException
	 */
	public AsEntAllInfo searchEntAllInfo(String entCustId) throws HsException;

	/**
	 * 查询企业状态信息
	 * 
	 * @param entCustId
	 *            企业客户号 ，必传
	 * @return
	 * @throws HsException
	 */
	public AsEntStatusInfo searchEntStatusInfo(String entCustId) throws HsException;

	/**
	 * 查询隶属企业信息
	 * 
	 * @param entResNo
	 *            企业互生号， 必传
	 * 
	 * @param blongEntCustType
	 *            隶属企业的客户类型：2 成员企业、3 托管企业、4 服务公司、5 管理公司，必传
	 * 
	 * @param belongEntResNo
	 *            隶属企业的互生号 ，可选
	 * @param belongEntName
	 *            隶属企业的名称 ，可选
	 * @param belongEntContacts
	 *            隶属企业的联系人，可选
	 * @param page
	 *            分页信息 ， 必传
	 * @return
	 * @throws HsException
	 */
	public PageData<AsBelongEntInfo> listBelongEntInfo(String entResNo, Integer blongEntCustType,
			String belongEntResNo, String belongEntName, String belongEntContacts, Page page)
			throws HsException;

	/**
	 * 查询隶属企业信息
	 * 
	 * @param condition
	 *            查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 */
	public PageData<AsBelongEntInfo> listBelongEntInfo(AsQueryBelongEntCondition condition,
			Page page) throws HsException;

	/**
	 * 查询隶属企业信息
	 * 
	 * @param condition
	 *            查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 */
	public PageData<AsBelongEntInfo> listAllBelongEntInfo(
			AsQueryBelongEntCondition condition, Page page) throws HsException;
	/**
	 * 修改企业一般信息
	 * 
	 * @param entBaseInfo
	 *            企业基本信息
	 * @param operator
	 *            操作和客户号
	 * @throws HsException
	 */
	public void updateEntBaseInfo(AsEntBaseInfo entBaseInfo, String operator) throws HsException;

	/**
	 * 修改企业状态信息
	 * 
	 * @param entStatusInfo
	 *            企业状态信息
	 * @param operator
	 *            操作和客户号
	 * @throws HsException
	 */
	public void updateEntStatusInfo(AsEntStatusInfo entStatusInfo, String operator)
			throws HsException;

	/**
	 * 绑定手机 手机信息入库
	 * 
	 * @param entCustId
	 *            企业客户号 ，必传
	 * @param mobile
	 *            企业联系手机 ，必传
	 * @throws HsException
	 */
	public void bindMobile(String entCustId, String mobile) throws HsException;

	/**
	 * 手机认证
	 * 
	 * @param entCustId
	 *            企业客户号 ，必传
	 * @param mobile
	 *            企业联系手机 ，必传
	 * @param smsCode
	 *            手机认证码 ，必传
	 * @throws HsException
	 */
	public void authMobile(String entCustId, String mobile, String smsCode) throws HsException;

	/**
	 * 绑定邮箱 邮箱信息入库
	 * 
	 * @param entCustId
	 *            企业客户号 ，必传
	 * @param email
	 *            企业邮箱 ，必传
	 * @throws HsException
	 */
	public void bindEmail(String entCustId, String email) throws HsException;

	/**
	 * 更改企业重要信息变更状态
	 * 
	 * @param entCustId
	 *            企业客户号 ，必传
	 * @param status
	 *            是否重要信息变更期间1:是 0：否 ，必传
	 * 
	 * @param operator
	 *            操作和客户号
	 * 
	 * @throws HsException
	 */
	public void changeEntMainInfoStatus(String entCustId, String status, String operator)
			throws HsException;

	/**
	 * 修改企业积分活动状态（参与、停止积分活动）
	 * 
	 * @param entCustId
	 *            企业客户号 ，必传
	 * @param pointStatus
	 *            积分活动状态 ，必传
	 * @param operator
	 *            操作者客户号 ，必传
	 * @throws HsException
	 */
	public void updateEntPointStatus(String entCustId, String pointStatus, String operator)
			throws HsException;

	/**
	 * 查询企业客户号 ，必传通过操作员互生号
	 * 
	 * @param operResNo
	 *            操作员客户号 ，必传
	 * @return
	 * @throws HsException
	 */
	public String findEntCustIdByOperResNo(String operResNo) throws HsException;

	/**
	 * 查询企业客户号 ，必传通过企业互生号
	 * 
	 * @param entResNo
	 *            企业互生号 ，必传
	 * @return
	 * @throws HsException
	 */
	public String findEntCustIdByEntResNo(String entResNo) throws HsException;

	/**
	 * 企业状态是否为激活状态，如果是正常或是预警状态返回true，其他返回false
	 * 
	 * @param entResNo
	 *            企业互生号 ，必传
	 * @return
	 * @throws HsException
	 */
	public boolean isActived(String entResNo) throws HsException;

	/**
	 * 对比企业版本号，如果一样，返回true， 反之返回false
	 * 
	 * @param entResNo
	 *            企业客户号 ，必传
	 * @param entVersion
	 *            企业版本号
	 * @return
	 * @throws HsException
	 */
	public boolean matchEntVersion(String entResNo, String entVersion) throws HsException;

	/**
	 * 根据企业资源号获取企业重要信息
	 * 
	 * @param entResNo
	 *            企业互生号 ，必传
	 * @return
	 */
	public AsEntMainInfo getMainInfoByResNo(String entResNo) throws HsException;

	/**
	 * 设置企业的交易密码 （初始化）
	 * 
	 * @param entCustId
	 *            企业客户号 ，必传
	 * @param tradePwd
	 *            交易密码 ，必传
	 * @throws HsException
	 */
	public void setTradePwd(String entCustId, String tradePwd) throws HsException;

	/**
	 * 修改企业的交易密码
	 * 
	 * @param entCustId
	 *            企业客户号 ，必传
	 * @param oldTradePwd
	 *            旧交易密码 ，必传
	 * @param newTradePwd
	 *            新交易密码 ，必传
	 * @throws HsException
	 */
	public void updateTradePwd(String entCustId, String oldTradePwd, String newTradePwd)
			throws HsException;

	/**
	 * 上传企业联系人授权委托书
	 * 
	 * @param entCustId
	 *            企业客户号 ，必传
	 * @param authProxyFile
	 *            授权委托书附件 id，必传
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 */
	public void uploadAuthProxyFile(String entCustId, String authProxyFile, String operator)
			throws HsException;

	/**
	 * 通过企业的客户号类型查询企业列表
	 * 
	 * @param custType
	 *            (企业客户类型1 持卡人;2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台;7 总平台; 8
	 *            其它地区平台 11：操作员 21：POS机；22：积分刷卡器；23：消费刷卡器；24：平板；25：云台51 非持卡人; 52
	 *            非互生格式化企业)
	 * @return
	 */
	public List<AsEntInfo> listEntInfo(int custType) throws HsException;

	/**
	 * 查询地区平台信息
	 * 
	 * @return
	 */
	public AsEntInfo searchRegionalPlatform() throws HsException;

	/**
	 * 查询企业的联系信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @return
	 */
	public AsEntContactInfo searchEntContactInfo(String entCustId) throws HsException;
	
	/**
	 * 查询企业扩展信息
	 * @param entCustId 企业客户号
	 * @return 企业扩展信息
	 * @throws HsException
	 */
	public AsEntExtendInfo searchEntExtInfo(String entCustId) throws HsException;
	
	/**
	 * 更新企业扩展信息(管理公司及地区平台使用)
	 * @param entExtInfo 企业扩展信息
	 * @param operator 操作用户id
	 * @throws HsException
	 */
	public void updateEntExtInfo(AsEntExtendInfo entExtInfo, String operator) throws HsException;

	
	/**
	 * 批量查询企业的客户号（在数据库中查询，未在文档中公开）
	 * @param list	企业互生号列表
	 * @return	企业的客户号列表
	 * @throws HsException
	 */
	public Map<String,String> listEntCustId(List<String> list) throws HsException;
	
	/**
	 * 批量查询企业的客户号
	 * @param entResnoList	list	企业互生号列表
	 * @return 企业的客户号列表
	 * @throws HsException
	 */
	public Map<String,String> findEntCustIdByEntResNo(List<String> entResnoList) throws HsException;
	
	
	/**
	 *  批量查询企业的税率
	 * @param entResnoList entResnoList	list	企业互生号列表
	 * @return 企业的税率列表
	 * @throws HsException
	 */
	public Map<String,String> listEntTaxRate(List<String> entResnoList) throws HsException;
	
	
	/**
	 * 企业修改税率审核通过
	 * @param entTaxRate	企业税率信息
	 * @param operCustId	操作员客户号
	 * @throws HsException
	 */
	public void updateNoEffectEntTaxRateInfo(AsEntTaxRate entTaxRate,String operCustId) throws HsException;
	
	

	/**
	 * 获取企业修改日志列表
	 * 
	  * @param entCustId
	 *            企业客户号
	 * @param updateFieldName
	 *            修改信息
	 * @param page
	 *            分页条件，null则查询全部
	 * @return
	 */
	public PageData<AsEntUpdateLog> listEntUpdateLog(String entCustId,String updateFieldName, Page page)
			throws HsException ;
	
	/**地区平台修改关联企业信息并记录修改日志
	 * 
	* @param asEntExtendInfo
	 *            企业信息
	 * @param logList
	 *            修改字段列表
	 * @param operCustId
	 *            操作员客户号
	 * @param confirmerCustId
	 *            复核员客户号
	 * @return 企业客户号
	 */
	public String UpdateEntAndLog(AsEntExtendInfo asEntExtendInfo,
			List<AsEntUpdateLog> logList, String operCustId,
			String confirmerCustId);
	/**
	 * 地区平台修改企业银行账户并记录修改日志
	 * 
	 * @param asBankAcctInfo
	 *            银行账户信息
	 * @param logList
	 *            修改字段列表
	 * @param logType
	 *            修改类型 0删除银行账号，1新增银行账号
	 * @param operCustId
	 *            操作员客户号
	 * @param confirmerCustId
	 *            复核员客户号
	 * @return 企业客户号
	 */
	public String UpdateEntAccountAndLog(AsBankAcctInfo asBankAcctInfo,
			List<AsEntUpdateLog> logList, Integer logType, String operCustId,
			String confirmerCustId);
	
	/**
	 * 检查企业名称是否存在，可附加条件：客户类型
	 * 
	 * @param entName
	 *            企业名称
	 * @param custType
	 *            企业类型 2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台; null 则忽略该条件
	 * @return
	 */
	public boolean isEntExist(String entName, Integer custType);
	/**
	 * 检查企业营业执照号是否存在，可附加条件：客户类型
	 * 
	 * @param busiLicenseNo
	 *            营业执照号
	 * @param custType
	 *            企业类型 2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台; null 则忽略该条件
	 * @return
	 */
	public boolean isEntBusiLicenseNoExist(String busiLicenseNo, Integer custType);
	
	/**
	 * 根据条件查询企业信息
	 * @param condition 查询条件
	 * @param page 分页
	 * @return
	 * @throws HsException
	 */
	public PageData<AsBelongEntInfo> listEntInfoByCondition(
			AsQueryEntCondition condition, Page page) throws HsException;
	
	/**
	 * 清除企业redis缓存
	 * @param entCustId
	 */
	public void cleanEntCache(String entCustId);
	
	/**
	 * 获取企业交易密码失败次数
	 * @param entResNo	企业互生号
	 * @return
	 */
	public Integer getTransFailTimes(String entResNo);
	
	/**
	 * 获取企业的密保失败次数
	 * @param entResNo	企业互生号
	 * @return
	 */
	public Integer getPwdQuestionFailTimes(String entResNo);
	
	/**
	 * 通过企业的客户号类型查询服务公司列表(专门给李葵官网调用)
	 * @param cityList
	 * @return
	 * @throws HsException
	 */
	public List<AsEntInfo> listCityListEntInfo(List<String> cityList) throws HsException ;
}
