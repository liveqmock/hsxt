package com.gy.hsxt.uc.common.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.alibaba.fastjson.JSONObject;

/**
 * 网络信息对象
 * 
 * @Package: com.gy.hsxt.uc.common.bean  
 * @ClassName: NetworkInfo 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-9 下午6:53:54 
 * @version V1.0
 */
public class NetworkInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4856637383484513776L;
	/**
	 * 客户号
	 */
    private String perCustId;
    /**
	 * 昵称
	 */
    private String nickname;
    /**
	 * 名称
	 */
    private String name;
    /**
	 * 头像
	 */
    private String headShot;
    /**
	 * 性别
	 */
    private Integer sex;
    /**
	 * 年龄
	 */
    private Integer age;
    /**
	 * 国家代码
	 */
    private String countryNo;
    /**
	 * 省份代码
	 */
    private String provinceNo;
    /**
	 * 城市代码
	 */
    private String cityNo;
    /**
	 * 区域
	 */
    private String area;
    /**
	 * 住址
	 */
    private String address;
    /**
	 * 个人签名
	 */
    private String individualSign;
    /**
	 * 工作
	 */
    private String job;
    /**
	 * 爱好
	 */
    private String hobby;
    /**
	 * 血型
	 */
    private Integer blood;
    /**
	 * 邮编
	 */
    private String postcode;
    /**
	 * Email
	 */
    private String email;
    /**
	 * 毕业院校
	 */
    private String graduateSchool;
    /**
	 * 微信
	 */
    private String weixin;
    /**
	 * QQ
	 */
    private String qqNo;
    /**
	 * 手机号
	 */
    private String telNo;
    /**
	 * 手机号
	 */
    private String mobile;
    /**
	 * 生日
	 */
    private String birthday;
    /**
	 * 家庭住址
	 */
    private String homeAddr;
    /**
	 * 家庭电话
	 */
    private String homePhone;
    /**
	 * 传真电话
	 */
    private String officePhone;
    /**
	 * 传真
	 */
    private String officeFax;
    /**
	 * 是否显示
	 */
    private Integer isShow;
    /**
	 * 修改次数
	 */
    private Integer modifyAcount;
    /**
	 * 是否有效，Y为有效，N为无效
	 */
    private String isactive;
    /**
	 * 创建日期
	 */
    private Timestamp createDate;
    /**
	 * 创建人
	 */
    private String createdby;
    /**
	 * 更新日期
	 */
    private Timestamp updateDate;
    /**
	 * 更新人
	 */
    private String updatedby;
	/**
	 * @return   客户号
	 */
	public String getPerCustId() {
		return perCustId;
	}
	/**
	 * @param 客户号
	 */
	public void setPerCustId(String perCustId) {
		this.perCustId = perCustId;
	}
	/**
	 * @return 昵称
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * @param 昵称
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * @return 名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param 名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return 头像
	 */
	public String getHeadShot() {
		return headShot;
	}
	/**
	 * @param 头像
	 */
	public void setHeadShot(String headShot) {
		this.headShot = headShot;
	}
	/**
	 * @return 性别
	 */
	public Integer getSex() {
		return sex;
	}
	/**
	 * @param 性别
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	/**
	 * @return 年龄
	 */
	public Integer getAge() {
		return age;
	}
	/**
	 * @param 年龄
	 */
	public void setAge(Integer age) {
		this.age = age;
	}
	/**
	 * @return 国家代码
	 */
	public String getCountryNo() {
		return countryNo;
	}
	/**
	 * @param 国家代码 
	 */
	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}
	/**
	 * @return 省份代码
	 */
	public String getProvinceNo() {
		return provinceNo;
	}
	/**
	 * @param 省份代码
	 */
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}
	/**
	 * @return the 城市代码
	 */
	public String getCityNo() {
		return cityNo;
	}
	/**
	 * @param 城市代码 the cityNo to set
	 */
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}
	/**
	 * @return the 区域
	 */
	public String getArea() {
		return area;
	}
	/**
	 * @param 区域 the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * @return the 住址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param 住址 the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the 个人签名
	 */
	public String getIndividualSign() {
		return individualSign;
	}
	/**
	 * @param 个人签名 the individualSign to set
	 */
	public void setIndividualSign(String individualSign) {
		this.individualSign = individualSign;
	}
	/**
	 * @return the 工作
	 */
	public String getJob() {
		return job;
	}
	/**
	 * @param 工作 the job to set
	 */
	public void setJob(String job) {
		this.job = job;
	}
	/**
	 * @return the 爱好
	 */
	public String getHobby() {
		return hobby;
	}
	/**
	 * @param 爱好 the hobby to set
	 */
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	/**
	 * @return the 血型
	 */
	public Integer getBlood() {
		return blood;
	}
	/**
	 * @param 血型 the blood to set
	 */
	public void setBlood(Integer blood) {
		this.blood = blood;
	}
	/**
	 * @return the 邮编
	 */
	public String getPostcode() {
		return postcode;
	}
	/**
	 * @param 邮编 the postcode to set
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	/**
	 * @return the Email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param Email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the 毕业院校
	 */
	public String getGraduateSchool() {
		return graduateSchool;
	}
	/**
	 * @param 毕业院校 the graduateSchool to set
	 */
	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}
	/**
	 * @return the 微信
	 */
	public String getWeixin() {
		return weixin;
	}
	/**
	 * @param 微信 the weixin to set
	 */
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	/**
	 * @return the QQ
	 */
	public String getQqNo() {
		return qqNo;
	}
	/**
	 * @param QQ the qqNo to set
	 */
	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}
	/**
	 * @return the 手机号
	 */
	public String getTelNo() {
		return telNo;
	}
	/**
	 * @param 手机号 the telNo to set
	 */
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	/**
	 * @return the 手机号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param 手机号 the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the 生日
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * @param 生日 the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the 家庭住址
	 */
	public String getHomeAddr() {
		return homeAddr;
	}
	/**
	 * @param 家庭住址 the homeAddr to set
	 */
	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}
	/**
	 * @return the 家庭电话
	 */
	public String getHomePhone() {
		return homePhone;
	}
	/**
	 * @param 家庭电话 the homePhone to set
	 */
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	/**
	 * @return the 传真电话
	 */
	public String getOfficePhone() {
		return officePhone;
	}
	/**
	 * @param 传真电话
	 */
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	/**
	 * @return the 传真
	 */
	public String getOfficeFax() {
		return officeFax;
	}
	/**
	 * @param 传真 the officeFax to set
	 */
	public void setOfficeFax(String officeFax) {
		this.officeFax = officeFax;
	}
	/**
	 * @return the 是否显示
	 */
	public Integer getIsShow() {
		return isShow;
	}
	/**
	 * @param 是否显示 the isShow to set
	 */
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	/**
	 * @return the 修改次数
	 */
	public Integer getModifyAcount() {
		return modifyAcount;
	}
	/**
	 * @param 修改次数 the modifyAcount to set
	 */
	public void setModifyAcount(Integer modifyAcount) {
		this.modifyAcount = modifyAcount;
	}
	/**
	 * @return the 是否有效，Y为有效，N为无效
	 */
	public String getIsactive() {
		return isactive;
	}
	/**
	 * @param 是否有效，Y为有效，N为无效 the isactive to set
	 */
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	/**
	 * @return the 创建日期
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}
	/**
	 * @param 创建日期 the createDate to set
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return   创建人
	 */
	public String getCreatedby() {
		return createdby;
	}
	/**
	 * @param 创建人
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	/**
	 * @return the 更新日期
	 */
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param 更新日期
	 */
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the 更新人
	 */
	public String getUpdatedby() {
		return updatedby;
	}
	/**
	 * @param 更新人
	 */
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public String toString(){
		return JSONObject.toJSONString(this);
	}
}