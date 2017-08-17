package com.gy.hsxt.uc.common.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.alibaba.fastjson.JSONObject;

/**
 * 收货地址
 * 
 * @Package: com.gy.hsxt.uc.common.bean  
 * @ClassName: ReceiveAddr 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-24 下午2:42:38 
 * @version V1.0
 */
public class ReceiveAddr implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5175185240099289077L;

	/**
	 * ID
	 */
    private Long addrId;

    /**
     * 客户号
     */
    private String custId;
    /**
     * 收货人
     */
    private String receiver;
    /**
     * 国家编码
     */
    private String countryNo;
    /**
     * 省份编码
     */
    private String provinceNo;
    /**
     * 城市编码
     */
    private String cityNo;
    /**
     * 区域
     */
    private String area;
    /**
     * 地址
     */
    private String address;
    /**
     * 邮编
     */
    private String postCode;
    /**
     * 电话
     */
    private String telphone;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 是否默认
     */
    private Integer isDefault;
    /**
     * 是否激活
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
     * 修改日期
     */
    private Timestamp updateDate;
    /**
     * 修改人
     */
    private String updatedby;
	/**
	 * @return ID
	 */
	public Long getAddrId() {
		return addrId;
	}
	/**
	 * @param ID 
	 */
	public void setAddrId(Long addrId) {
		this.addrId = addrId;
	}
	/**
	 * @return 客户号
	 */
	public String getCustId() {
		return custId;
	}
	/**
	 * @param 客户号
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}
	/**
	 * @return 收货人
	 */
	public String getReceiver() {
		return receiver;
	}
	/**
	 * @param 收货人
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	/**
	 * @return 国家编码
	 */
	public String getCountryNo() {
		return countryNo;
	}
	/**
	 * @param 国家编码 
	 */
	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}
	/**
	 * @return 省份编码
	 */
	public String getProvinceNo() {
		return provinceNo;
	}
	/**
	 * @param 省份编码
	 */
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}
	/**
	 * @return 城市编码
	 */
	public String getCityNo() {
		return cityNo;
	}
	/**
	 * @param 城市编码 
	 */
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}
	/**
	 * @return 区域
	 */
	public String getArea() {
		return area;
	}
	/**
	 * @param 区域 
	 */
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * @return 地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param 地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return 邮编
	 */
	public String getPostCode() {
		return postCode;
	}
	/**
	 * @param 邮编
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	/**
	 * @return 电话
	 */
	public String getTelphone() {
		return telphone;
	}
	/**
	 * @param 电话
	 */
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	/**
	 * @return 电话
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param 电话
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return 是否默认
	 */
	public Integer getIsDefault() {
		return isDefault;
	}
	/**
	 * @param 是否默认
	 */
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	/**
	 * @return 是否激活
	 */
	public String getIsactive() {
		return isactive;
	}
	/**
	 * @param 是否激活 
	 */
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	/**
	 * @return 创建日期
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}
	/**
	 * @param 创建日期 
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return 创建人
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
	 * @return 修改日期
	 */
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param 修改日期
	 */
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return 修改人
	 */
	public String getUpdatedby() {
		return updatedby;
	}
	/**
	 * @param 修改人
	 */
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public String toString(){
		return JSONObject.toJSONString(this);
	}
}