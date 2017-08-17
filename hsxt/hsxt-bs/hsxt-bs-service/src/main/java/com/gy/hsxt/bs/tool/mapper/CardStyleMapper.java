/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.CardStyle;
import com.gy.hsxt.bs.bean.tool.resultbean.SpecialCardStyle;

/**
 * 互生卡样Mapper
 * 
 * @Package: com.hsxt.bs.btool.mapper
 * @ClassName: CardStyleMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 上午11:22:23
 * @company: gyist
 * @version V3.0.0
 */
@Repository(value = "cardStyleMapper")
public interface CardStyleMapper {

	/**
	 * 插入卡样
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:09:16
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertCardStyle(CardStyle entity);

	/**
	 * 根据id查询互生卡样
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:09:32
	 * @param cardStyleId
	 * @return
	 * @return : CardStyle
	 * @version V3.0.0
	 */
	public CardStyle selectCardStyleById(String cardStyleId);

	/**
	 * 根据订单号查询卡样
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月2日 上午10:22:07
	 * @param orderNo
	 * @return
	 * @return : CardStyle
	 * @version V3.0.0
	 */
	public CardStyle selectCardStyleByOrderNo(String orderNo);

	/**
	 * 互生卡样的启用和停用
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:09:40
	 * @param entity
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int cardStyleEnableOrStop(CardStyle entity);

	/**
	 * 互生卡样审批
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:09:46
	 * @param entity
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int cardStyleAppr(CardStyle entity);

	/**
	 * 修改标准卡的默认为非默认
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午5:01:54
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateDefaultToNotDefault();

	/**
	 * 修改标准卡样为默认卡样
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年10月30日 下午6:10:50
	 * @param cardStyleId
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateCardStyleDefault(String cardStyleId);

	/**
	 * 根据订单修改互生卡样
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月1日 下午4:46:42
	 * @param entity
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateCardStyleByNo(CardStyle entity);

	/**
	 * 根据订单号删除卡样
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月15日 下午2:26:22
	 * @param orderNo
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int deleteCardStyleByNo(@Param("orderNo") String orderNo);

	/**
	 * 分页查询互生卡样
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月12日 上午11:15:25
	 * @param cardStyleName
	 * @param exeCustId
	 * @return
	 * @return : List<CardStyle>
	 * @version V3.0.0
	 */
	public List<CardStyle> selectCardStyleListPage(@Param("cardStyleName") String cardStyleName,
			@Param("exeCustId") String exeCustId);

	/**
	 * 查询制作时企业上传的个性卡样
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月12日 下午4:56:10
	 * @param entResNo
	 * @return
	 * @return : CardStyle
	 * @version V3.0.0
	 */
	public List<CardStyle> selectCardStyleByEnt(@Param("entResNo") String entResNo);

	/**
	 * 查询默认的标准卡样
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月12日 下午4:58:29
	 * @return
	 * @return : CardStyle
	 * @version V3.0.0
	 */
	public CardStyle selectCardStyleByDefault();

	/**
	 * 查询制作时可以选择的卡样
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午3:50:01
	 * @param entResNo
	 * @param sEentResNo
	 * @return
	 * @return : List<CardStyle>
	 * @version V3.0.0
	 */
	public List<CardStyle> selectCardStyleByMark(@Param("entResNo") String entResNo,
			@Param("sEentResNo") String sEentResNo);

	/**
	 * 分页查询互生个性卡样
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月7日 下午3:58:27
	 * @param param
	 * @return
	 * @return : List<SpecialCardStyle>
	 * @version V3.0.0
	 */
	public List<SpecialCardStyle> selectSpecialCardStyleListPage(BaseParam param);
}
