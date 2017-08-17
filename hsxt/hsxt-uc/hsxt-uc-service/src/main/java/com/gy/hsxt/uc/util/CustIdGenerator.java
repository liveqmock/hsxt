/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.RandomGuidAgent;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.Constants;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.checker.bean.DoubleChecker;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.common.bean.CustAccount;
import com.gy.hsxt.uc.common.bean.EntAccount;
import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.HsCard;
import com.gy.hsxt.uc.consumer.bean.NoCardHolder;
import com.gy.hsxt.uc.ent.bean.EntExtendInfo;
import com.gy.hsxt.uc.ent.bean.EntStatusInfo;
import com.gy.hsxt.uc.operator.bean.Operator;
import com.gy.hsxt.uc.sysoper.bean.SysOperator;

/**
 * 客户号生成器
 * 
 * @Package: com.gy.hsxt.uc.util
 * @ClassName: CustIdGenerator
 * @Description: TODO
 * 
 *               1、持卡人客户号： 消费者互生号（11位）+ 开卡日期（8位），如05001081239151019 2、企业客户号：
 *               企业互生号（11位）+ 企业注册日期（8位）,如05001080000150210 3、企业操作员客户号：
 *               企业互生号（11位）+ 操作员编号（4位）+ 操作员登记日期（8位） 050010800001002150820
 *               4、非持卡人客户号（唯一ID）： 前缀(1位，非持卡人为9) + 机器节点编号（2位）+ 随机数（15位）
 *               901160144710051863 5、POS机客户号（唯一ID）： 前缀(1位，POS机为1) +
 *               机器节点（2位）+随机数（15位） 101160144710051863 6、刷卡器客户号（唯一ID）：
 *               前缀(1位，刷卡为2) + 机器节点（2位）+随机数（15位） 2011601447100 51863 7、平板客户号
 *               （唯一ID）： 前缀(1位，平板为3) + 机器节点（2位）+随机数（15位） 301160144710051863
 *               8、系统操作员客户号（唯一ID）： 前缀（1位，系统操作员为8） + 机器节点（2位）+随机数（15位）
 *               801160144710051863
 * 
 * @author: huanggaoyang
 * @date: 2015-11-3 上午11:54:58
 * @version V1.0
 */
@Component
public class CustIdGenerator {

	@Autowired
	private SysConfig config;

	/**
	 * 生成pos机设备客户号,前缀1
	 * 
	 * @return
	 */
	public String generatePosDeviceCustId() {
		return generateCustId("1");
	}

	/**
	 * 生成平板设备客户号,前缀3
	 * 
	 * @return
	 */
	public String generatePadDeviceCustId() {
		return generateCustId("3");
	}

	/**
	 * 生成刷卡器设备客户号,前缀2
	 * 
	 * @return
	 */
	public String generateCardReaderDeviceCustId() {
		return generateCustId("2");
	}

	/**
	 * 生成系统操作员客户号,前缀8
	 * 
	 * @return
	 */
	public String generateSysCustId() {
		return generateCustId("8");
	}

	/**
	 * 返回 业务前缀（1位）+机器节点编码（2位）+15位唯一码
	 * 
	 * @param bPrefix
	 *            业务前缀 1位 :POS机为1,刷卡器为2,平板为3,系统操作员为8,非持卡人为9
	 * @return 唯一客户ID
	 */
	public String generateCustId(String bPrefix) {
		String sn = SysConfig.getSystemInstanceNo();
		String deviceCustId = RandomGuidAgent.getStringGuid(bPrefix + sn);

		return deviceCustId;
	}
	/**
	 * 生成有序id
	 * id有序增大，每次获取比前一次值增大
	 * @param preFix
	 * @return
	 */
	public static String genSeqId(String preFix){
		String id = RandomGuidAgent.getStringGuid(preFix);
		id=id+SysConfig.getSystemInstanceNo(); //加上系统实例编号避免跨系统重复
		return id;
	}

	/**
	 * 通过互生号获取用户类型
	 * 
	 * @param perResNo
	 *            互生号
	 * @return 用户类型 企业：4 持卡人：2
	 */
	public static String getUserTypeByResNo(String resNo) {
		String userType = "";
		if (StringUtils.isBlank(resNo)) {
			return userType;
		}
		String hsResNo = resNo.trim();
		if (hsResNo.length() != 11) {
			return userType;
		}
		if (HsResNoUtils.isAreaPlatResNo(resNo)
				|| HsResNoUtils.isManageResNo(resNo)
				|| HsResNoUtils.isServiceResNo(resNo)
				|| HsResNoUtils.isTrustResNo(resNo)
				|| HsResNoUtils.isMemberResNo(resNo)) {
			return Constants.USER_TYPE_ENT;
		}
		if(HsResNoUtils.isPersonResNo(resNo)){
		return Constants.USER_TYPE_CARD_HOLDER;
		}
		return Constants.USER_TYPE_NOCARD_HOLDER;
	}

	/**
	 * 通过客户号获取用户类型 1非持卡人，2持卡人，4企业
	 * 
	 * @param custId
	 * @return  1非持卡人，2持卡人，4企业
	 */
	public static String getUserTypeByCustId(String custId) {
		String userType = "";
		if (!StringUtils.isBlank(custId)) {
			String cid = custId.trim();
			int length = cid.length();
			if (19 != length && 18 != length && 23 != length) {
				return userType;
			}
			// 互生号18位规则：前缀(1位) + 机器节点编号（2位）+ 15位唯一码0600701000020151231
			String prefix = cid.substring(0, 1);
			switch (prefix) {
			case Constants.CUSTID_POS_PREFIX:
				userType = Constants.USER_TYPE_POS;// POS机
				break;
			case Constants.CUSTID_CARDREADER_PREFIX:
				userType = Constants.USER_TYPE_CARDREADER;// 刷卡器
				break;
			case Constants.CUSTID_PAD_PREFIX:
				userType = Constants.USER_TYPE_PAD;// 平板
				break;
			case Constants.CUSTID_SYS_PREFIX:
				userType = Constants.USER_TYPE_SYS;// 系统操作员
				break;
			case Constants.CUSTID_NOCARDER_PREFIX:
				userType = Constants.USER_TYPE_NOCARD_HOLDER;// 非持卡人
				break;
			default:
				String resNo = custId.substring(0, 11);
				userType = getUserTypeByResNo(resNo);
				break;
			}

			if (length >= 19) {// 持卡人or企业
				// 互生号（11位）+ 注册日期（8位）
				String resNo = custId.substring(0, 11);
				return getUserTypeByResNo(resNo);

			} 
		}
		return userType;
	}

	public static void main(String[] args) {
		String custId = "0600701000020151231";
		CustIdGenerator cg = new CustIdGenerator();

		String type = cg.getUserTypeByCustId(custId);
		System.out.println(type);
		String resNo = custId.substring(0, 11);
		if (HsResNoUtils.isAreaPlatResNo(resNo)
				|| HsResNoUtils.isManageResNo(resNo)
				|| HsResNoUtils.isServiceResNo(resNo)
				|| HsResNoUtils.isTrustResNo(resNo)
				|| HsResNoUtils.isMemberResNo(resNo)) {
			System.out.print(true);
		} else {
			System.out.println(false);
		}
	}

	public static void isOperExist(String operCustId, String entResNo,
			String userName) throws HsException {
		if (StringUtils.isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					ErrorCodeEnum.USER_NOT_FOUND.getDesc() + ",entResNo["
							+ entResNo + "],userName[" + userName + "]");
		}
	}

	public static void isOperExist(Operator oper,String operCustId) throws HsException{
		if (oper == null || oper.getIsactive().equals("N")) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					operCustId + "该操作员客户号找不到操作员信息");
		}
	}

	public static void isOperExist(String operCustId,String operResNo)throws HsException {
		if (StringUtils.isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					"查询不到操作员客户号，操作员不存在或操作员未绑定此互生号[" + operResNo+"]");
		}
	}
	
	public static void isNoCarderExist(String custId,String mobile)throws HsException{
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(), "["
					+ mobile + "]" + ErrorCodeEnum.USER_NOT_FOUND.getDesc());
		}
	}

	
	public static void isNoCarderExist(NoCardHolder noCardHolder,String perCustId)throws HsException{
		if (null == noCardHolder) {
			throw new HsException(
					ErrorCodeEnum.NOCARDHOLDER_NOT_FOUND.getValue(), perCustId
							+ "该非持卡人客户号找不到客户信息");
		}
	}

	public static void isSysOperExist(SysOperator sysOper,String sysOperCustId)throws HsException{
		if (null == sysOper) {
			throw new HsException(
					ErrorCodeEnum.NOCARDHOLDER_NOT_FOUND.getValue(),"[" +sysOperCustId
							+ "]该非持卡人客户号找不到客户信息");
		}
	}
	
	public static void isCarderExist(String custId,String perResNo)throws HsException{
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					perResNo + ErrorCodeEnum.USER_NOT_FOUND.getDesc());
		}
	}
	public static void isSysOperExist(String custId,String userName)throws HsException{
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					userName + ErrorCodeEnum.USER_NOT_FOUND.getDesc());
		}
	}
	
	public static void isCarderExist(CardHolder cardHolder,String perCustId) throws HsException{
		if (null == cardHolder) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					perCustId + "该持卡人客户号找不到对应客户信息");
		}
	}
	
	public static void isEntExtendExist(EntExtendInfo entExtendInfo,String entCustId) throws HsException{
		if (null == entExtendInfo) {
			throw new HsException(
					ErrorCodeEnum.CUST_ID_IS_NOT_FOUND.getValue(), entCustId
							+ "该企业客户号找不到企业扩展信息");
		}
	}
	
	public static void isEntExtendExist(String entCustId,String entResNo) throws HsException{
		if (StringUtils.isBlank(entCustId)) {
			throw new HsException(
					ErrorCodeEnum.CUST_ID_IS_NOT_FOUND.getValue(), entResNo
							+ "该企业客户号找不到企业扩展信息");
		}
	}
	public static void isEntStatusInfoExist(EntStatusInfo entStatusInfo,String entCustId) throws HsException{
		if (null == entStatusInfo) {
			throw new HsException(
					ErrorCodeEnum.CUST_ID_IS_NOT_FOUND.getValue(), entCustId
							+ "该企业客户号找不到企业状态信息");
		}
	}
	
	public static void isHsCardExist(HsCard hsCard,String perCustId) throws HsException{
		if (null == hsCard) {
			throw new HsException(ErrorCodeEnum.HSCARD_NOT_FOUND.getValue(),
					ErrorCodeEnum.HSCARD_NOT_FOUND.getDesc()+",custId["+perCustId+"]");
		}
	}
	
    public static void isEntBankCardExist(EntAccount entAccount,long accId) throws HsException{
		if (null == entAccount) {
			throw new HsException(
					ErrorCodeEnum.CUST_ID_IS_NOT_FOUND.getValue(), "银行账户ID["+ accId+"]"
							+ "找不到对应的企业银行账户信息");
		}
	}
	
    public static void isCustBankCardExist(CustAccount entAccount,long accId) throws HsException{
		if (null == entAccount) {
			throw new HsException(
					ErrorCodeEnum.CUST_ID_IS_NOT_FOUND.getValue(), "银行账户ID["+ accId+"]"
							+ "找不到对应的消费者银行账户信息");
		}
	}
    
    public static void isDoubleCheckerExist(DoubleChecker checker,String doubleCustId) throws HsException{
		if (null == checker) {
			throw new HsException(
					ErrorCodeEnum.CUST_ID_IS_NOT_FOUND.getValue(), "客户号["+ doubleCustId+"]"
							+ "找不到对应的双签员的信息");
		}
	}
    
    public static void isDoubleCheckerExist(String doubleCustId,String userName) throws HsException{
		if (StringUtils.isBlank(doubleCustId)) {
			throw new HsException(
					ErrorCodeEnum.CUST_ID_IS_NOT_FOUND.getValue(), "userNmae["+ userName+"]"
							+ "找不到对应的双签员的信息");
		}
	}
}
