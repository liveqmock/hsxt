package com.gy.hsxt.ws.bean;

import java.io.Serializable;

/**
 * 图片信息 实体
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: ImgInfo
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 下午4:54:15
 * @version V1.0
 */
public class ImgInfo implements Serializable {
	/** 图片路径 */
	private String imgPath;

	/** 图片路径ID 关联表 T_WS_APPLY_WELFARE */
	private String imgId;

	private static final long serialVersionUID = 1L;

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath == null ? null : imgPath.trim();
	}

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId == null ? null : imgId.trim();
	}
}