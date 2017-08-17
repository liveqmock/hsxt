package com.gy.hsxt.uc.operator.bean;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.common.bean.NetworkInfo;
import com.gy.hsxt.uc.common.bean.UserInfo;
import com.gy.hsxt.uc.util.CSPRNG;
import com.gy.hsxt.uc.util.StringEncrypt;

/**
 * 操作员实体类
 * 
 * @Package: com.gy.hsxt.uc.operator.bean
 * @ClassName: Operator
 * @Description: TODO
 * 
 * @author: tianxh
 * @date: 2015-11-15 下午2:22:51
 * @version V1.0
 */
public class Operator extends UserInfo {
	private static final long serialVersionUID = 5912199690881790706L;
	/** 操作员客户号 */
	private String operCustId;
	/** 操作员消费者互生号 */
	private String operResNo;
	/** 所属企业的客户号 */
	private String entCustId;
	/** 所属企业的互生号 */
	private String entResNo;
	/** 所属企业资源类型?3：托管企业，2 ：成员企业 */
	private String enterpriseResourceType;
	/** 操作员账号(操作编号 */
	private String operNo;

	/** 手机号码 */
	private String phone;
	/** 邮箱 */
	private String email;
	/** 操作员姓名 */
	private String operName;
	/** 职务 */
	private String operDuty;
	/** 操作员状态 0：启用，1：禁用 2:已删除 */
	private Integer accountStatus;
	/** 是否绑定互生号1：绑定 0：未绑定,-1绑定中 */
	private Integer isBindResNo;
	/** 积分卡申请绑定时间 */
	private Timestamp applyBindDate;
	/** 是否管理员1:管理员 0：非管理员 */
	private Integer isAdmin;
	/** 上次登录时间 */
	private Timestamp lastLoginDate;
	/** 上次登录IP */
	private String lastLoginIp;
	/** 备注信息 */
	private String remark;
	/** 标记此条记录的状态Y:可用 N:不可用 */
	private String isactive;

	/** 网络信息 */
	private NetworkInfo networkInfo;

	/**
	 * @return the 网络信息
	 */
	public NetworkInfo getNetworkInfo() {
		return networkInfo;
	}

	/**
	 * @param 网络信息
	 *            the networkInfo to set
	 */
	public void setNetworkInfo(NetworkInfo networkInfo) {
		this.networkInfo = networkInfo;
	}

	/**
	 * @return the 操作员客户号
	 */
	public String getOperCustId() {
		return operCustId;
	}

	/**
	 * @param 操作员客户号
	 *            the operCustId to set
	 */
	public void setOperCustId(String operCustId) {
		this.operCustId = operCustId;
	}

	/**
	 * @return the 操作员消费者互生号
	 */
	public String getOperResNo() {
		return operResNo;
	}

	/**
	 * @param 操作员消费者互生号
	 *            the operResNo to set
	 */
	public void setOperResNo(String operResNo) {
		this.operResNo = operResNo;
	}

	/**
	 * @return the 所属企业的客户号
	 */
	public String getEntCustId() {
		return entCustId;
	}

	/**
	 * @param 所属企业的客户号
	 *            the entCustId to set
	 */
	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}

	/**
	 * @return the 所属企业的互生号
	 */
	public String getEntResNo() {
		return entResNo;
	}

	/**
	 * @param 所属企业的互生号
	 *            the entResNo to set
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	/**
	 * @return the 所属企业资源类型?T：托管企业，B：成员企业
	 */
	public String getEnterpriseResourceType() {
		return enterpriseResourceType;
	}

	/**
	 * @param 所属企业资源类型
	 *            ?T：托管企业，B：成员企业 the enterpriseResourceType to set
	 */
	public void setEnterpriseResourceType(String enterpriseResourceType) {
		this.enterpriseResourceType = enterpriseResourceType;
	}

	/**
	 * @return the 操作员账号(操作编号
	 */
	public String getOperNo() {
		return operNo;
	}

	/**
	 * @param 操作员账号
	 *            (操作编号 the operNo to set
	 */
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}

	/**
	 * @return the 手机号码
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param 手机号码
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the 邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param 邮箱
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the 操作员姓名
	 */
	public String getOperName() {
		return operName;
	}

	/**
	 * @param 操作员姓名
	 *            the operName to set
	 */
	public void setOperName(String operName) {
		this.operName = operName;
	}

	/**
	 * @return the 职务
	 */
	public String getOperDuty() {
		return operDuty;
	}

	/**
	 * @param 职务
	 *            the operDuty to set
	 */
	public void setOperDuty(String operDuty) {
		this.operDuty = operDuty;
	}

	/**
	 * @return the 状态0：激活状态，1：冻结状态，2：注销状态3:已删除状态
	 */
	public Integer getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param 状态0
	 *            ：激活状态，1：冻结状态，2：注销状态3:已删除状态 the accountStatus to set
	 */
	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * @return the 是否绑定互生号1：绑定0：未绑定-1绑定中
	 */
	public Integer getIsBindResNo() {
		return isBindResNo;
	}

	/**
	 * @param 是否绑定互生号1
	 *            ：绑定0：未绑定-1绑定中 the isBindResNo to set
	 */
	public void setIsBindResNo(Integer isBindResNo) {
		this.isBindResNo = isBindResNo;
	}

	/**
	 * @return the 积分卡申请绑定时间
	 */
	public Timestamp getApplyBindDate() {
		return applyBindDate;
	}

	/**
	 * @param 积分卡申请绑定时间
	 *            the applyBindDate to set
	 */
	public void setApplyBindDate(Timestamp applyBindDate) {
		this.applyBindDate = applyBindDate;
	}

	/**
	 * @return the 是否管理员1:管理员0：非管理员
	 */
	public Integer getIsAdmin() {
		return isAdmin;
	}

	/**
	 * @param 是否管理员1
	 *            :管理员0：非管理员 the isAdmin to set
	 */
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * @return the 上次登录时间
	 */
	public Timestamp getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @param 上次登录时间
	 *            the lastLoginDate to set
	 */
	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @return the 上次登录IP
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * @param 上次登录IP
	 *            the lastLoginIp to set
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	/**
	 * @return the 备注信息
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param 备注信息
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the 标记此条记录的状态Y:可用N:不可用
	 */
	public String getIsactive() {
		return isactive;
	}

	/**
	 * @param 标记此条记录的状态Y
	 *            :可用N:不可用 the isactive to set
	 */
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	/**
	 * @return the 返回管理员的默认帐号名称
	 */
	public static String getAdminName() {
		return SysConfig.getAdminAcctName();
	}

	/**
	 * @return 返回系统管理员的默认帐号名称
	 */
	public static String getAdminUserName() {
		return SysConfig.getAdminUserName();
	}
	
	/**
	 * 设置参数,不包括登录密码和交易密码
	 * 
	 * @param oper
	 */
	public void fillParams(AsOperator oper) {
		this.email = oper.getEmail();
		this.entCustId = oper.getEntCustId();
		this.entResNo = oper.getEntResNo();
		// this.enterpriseResourceType
		if(StringUtils.isBlank(this.operCustId)){
			this.operCustId = oper.getOperCustId();
		}
		this.operDuty = oper.getOperDuty();
		// this.pwdLoginVer
		this.operName = oper.getRealName();
		this.operNo = oper.getUserName();
		// if (null != oper.getLoginPwd()) {
		// String saltValue = CSPRNG.generateRandom(8);
		// //String md5 = StringEncrypt.string2MD5(oper.getLoginPwd().trim());
		// String loginpwd = StringEncrypt.sha256(oper.getLoginPwd().trim()
		// + saltValue);
		// this.pwdLogin = oper.getLoginPwd().trim();
		// .pwdLoginSaltValue = saltValue;
		// }
		if (null != oper.getLastLoginDate()) {
			this.lastLoginDate = Timestamp.valueOf(oper.getLastLoginDate());
		}
		this.lastLoginIp = oper.getLastLoginIp();
		this.operResNo = oper.getOperResNo();
		this.phone = oper.getMobile();
		this.remark = oper.getRemark();
		this.enterpriseResourceType = oper.getEnterpriseResourceType();
		this.accountStatus = oper.getAccountStatus();
		if(oper.getAccountStatus() != null){
			this.accountStatus = oper.getAccountStatus();
		}
		else{
			this.accountStatus = 0;
		}
		
		this.isactive="Y";
	}

	/**
	 * 把 Operator对象转换成 AsOperator 对象
	 * 
	 * @return
	 */
	public AsOperator buildAsOperator() {
		AsOperator asOper = new AsOperator();
		asOper.setEmail(this.email);
		asOper.setEntCustId(this.entCustId);
		asOper.setEntResNo(this.entResNo);
		asOper.setLoginPwd(this.getPwdLogin());
		asOper.setLoginPWdSaltValue(this.getPwdLoginSaltValue());
		if (null != this.lastLoginDate) {
			asOper.setLastLoginDate(DateUtil.timestampToString(
					this.lastLoginDate, "yyyy-MM-dd hh:mm:ss"));
		}
		if (this.isAdmin != null && 1 == this.isAdmin) {
			asOper.setAdmin(true);
		} else {
			asOper.setAdmin(false);
		}
		asOper.setBindResNoStatus(this.isBindResNo);
		asOper.setLastLoginIp(this.lastLoginIp);
		asOper.setMobile(this.phone);
		asOper.setOperCustId(operCustId);
		asOper.setOperDuty(this.operDuty);
		asOper.setOperResNo(this.operResNo);
		asOper.setRealName(this.operName);
		asOper.setRemark(this.remark);
		asOper.setUserName(this.operNo);
		asOper.setEnterpriseResourceType(this.enterpriseResourceType);
		asOper.setAccountStatus(this.accountStatus);
		// 如果网络信息不为空，设置网络信息
		if (networkInfo != null) {
			AsNetworkInfo ni = new AsNetworkInfo();
			BeanUtils.copyProperties(this.networkInfo, ni);
			asOper.setNetworkInfo(ni);
		}
		return asOper;
	}

	/**
	 * 生成管理员的 Operator 对象
	 * 
	 * @param pwd
	 * @return
	 */
	public static Operator buildAdminOperator(String pwd) {
		Operator oper = new Operator();
		oper.setIsAdmin(1);
		oper.setOperName(SysConfig.getAdminUserName());
		oper.setOperNo(SysConfig.getAdminAcctName());
		String saltValue = CSPRNG.generateRandom(8);
		String md5Pwd = StringEncrypt.string2MD5(pwd);
		String pwdLogin = StringEncrypt.sha256(md5Pwd + saltValue);
		oper.setPwdLogin(pwdLogin);
		oper.setPwdLoginSaltValue(saltValue);
		return oper;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}
}