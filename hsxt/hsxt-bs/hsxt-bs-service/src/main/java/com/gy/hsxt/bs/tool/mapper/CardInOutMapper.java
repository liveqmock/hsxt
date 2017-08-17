/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.tool.CardInOut;

/**
 * 积分卡出入库Mapper
 * 
 * @Package: com.hsxt.bs.btool.mapper
 * @ClassName: CardInOutMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:41:18
 * @company: gyist
 * @version V3.0.0
 */
@Repository(value = "cardInOutMapper")
public interface CardInOutMapper {

	/**
	 * 插入互生卡出入库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月8日 下午4:32:46
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertCardInOut(CardInOut entity);

	/**
	 * 修改互生卡出入库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月8日 下午4:33:03
	 * @param entity
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateCardInOut(CardInOut entity);

	/**
	 * 批量修改互生卡出库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月22日 下午6:06:54
	 * @param inOuts
	 * @return : void
	 * @version V3.0.0
	 */
	public void batchUpdateCardInOut(@Param("inOuts") List<CardInOut> inOuts);

	/**
	 * 根据订单号查询互生卡出入库流水
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月17日 下午4:23:28
	 * @param relatedOrderNo
	 * @return
	 * @return : CardInOut
	 * @version V3.0.0
	 */
	public CardInOut selectCardInOutByNo(String relatedOrderNo);
}
