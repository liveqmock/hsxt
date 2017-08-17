package com.gy.hsxt.ws.mapper;

import java.util.List;

import com.gy.hsxt.ws.bean.ImgInfo;

/**
 * 申请图片信息 Mapper
 * 
 * @Package: com.gy.hsxt.ws.mapper
 * @ClassName: ImgInfoMapper
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 下午2:13:49
 * @version V1.0
 */
public interface ImgInfoMapper {

	/**
	 * 批量插入图片信息
	 * 
	 * @param list
	 * @return
	 */
	int batchInsertImgs(List<ImgInfo> list);

	/**
	 * 查询图片信息
	 * 
	 * @param imgIds
	 * @return
	 */
	List<ImgInfo> listImgByImgIds(List<String> imgIds);
}