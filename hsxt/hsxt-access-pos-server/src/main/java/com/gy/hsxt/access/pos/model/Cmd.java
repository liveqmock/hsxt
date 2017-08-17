/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gy.hsxt.common.constant.IRespCode;


/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: Cmd 
 * @Description: 请求报文结构
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:03:26 
 * @version V1.0
 */
public class Cmd implements Serializable {
	
	
	private static final long serialVersionUID = 76455454999462461L;

	/**
	 * tpdu：ID、目的地、源地址总计10byte长
	 */
	private byte[] tpdu;
	/**
	 * 报文头： 应用类型 2位
	 */
	private byte appType;
	/**
	 * 报文头： 软件总版本号 2位
	 */
	private byte ver;
	/**
	 * 报文头：终端状态、处理要求 各1位
	 */
	private byte statusAndProcess;
	/**
	 * 报文头：软件分版本号 6位
	 */
	private byte[] partVersion;
	/**
	 * 请求消息类型ID（MSG-TYPE-ID）4位
	 */
	private byte[] typeId;
	/**
	 * 不含消息类型ID
	 */
	private byte[] body;

	private BitMap[] bitMaps;
	
	/**
	 * 消息ID:消息类型ID-3域-25域-60域
	 */
	private String reqId;
	
	private PosReqParam posReqParam;
	
	/** 响应代码 **/
	private IRespCode respCode;

	private List<BitMap> in = new ArrayList<BitMap>();
	
	/**
	 * pos中心流水号 12位数字
	 *  37域响应
	 */
	private String pointId;
	
	/**
	 * 在ActionChain里面，前面的Action要传值给后面的，由这个tmpMap携带。
	 * 因为是Map，所以能携带多个值。
	 */
	private Map<Object, Object> tmpMap = new HashMap<>();

	public Cmd() {
	}

	public Cmd(byte[] tpdu, byte appType, byte ver, 
			byte statusAndProcess, byte[] partVersion, byte[] typeId, 
			byte[] body) {
		this.tpdu = tpdu;
		this.appType = appType;
		this.ver = ver;
		this.statusAndProcess = statusAndProcess;
		this.partVersion = partVersion;
		this.typeId = typeId;
		this.body = body;
	}

	public byte[] getTpdu() {
		return tpdu;
	}

	public void setTpdu(byte[] tpdu) {
		this.tpdu = tpdu;
	}

	public byte getAppType() {
		return appType;
	}

	public void setAppType(byte appType) {
		this.appType = appType;
	}

	public byte getVer() {
		return ver;
	}

	public void setVer(byte ver) {
		this.ver = ver;
	}

	public byte getStatusAndProcess() {
		return statusAndProcess;
	}

	public void setStatusAndProcess(byte statusAndProcess) {
		this.statusAndProcess = statusAndProcess;
	}

	public byte[] getPartVersion() {
		return partVersion;
	}

	public void setPartVersion(byte[] partVersion) {
		this.partVersion = partVersion;
	}

	public byte[] getTypeId() {
		return typeId;
	}

	public void setTypeId(byte[] typeId) {
		this.typeId = typeId;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public List<BitMap> getIn() {
		return in;
	}

	public void setIn(List<BitMap> in) {
		this.in = in;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public PosReqParam getPosReqParam() {
		return posReqParam;
	}

	public void setPosReqParam(PosReqParam posReqParam) {
		this.posReqParam = posReqParam;
	}

	public Map<Object, Object> getTmpMap() {
		return tmpMap;
	}

	public void setTmpMap(Map<Object, Object> tmpMap) {
		this.tmpMap = tmpMap;
	}

	public BitMap[] getBitMaps() {
		return bitMaps;
	}

	public void setBitMaps(BitMap[] bitMaps) {
		this.bitMaps = bitMaps;
	}

	public IRespCode getRespCode() {
		return respCode;
	}

	public void setRespCode(IRespCode respCode) {
		this.respCode = respCode;
	}

	public String getPointId() {
		return pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	
	

}
