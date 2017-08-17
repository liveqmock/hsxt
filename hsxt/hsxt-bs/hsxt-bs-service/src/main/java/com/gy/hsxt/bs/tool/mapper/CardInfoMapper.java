/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.tool.CardInfo;
import com.gy.hsxt.bs.tool.bean.BatchOpenCard;
import com.gy.hsxt.bs.tool.bean.MakingCardInfo;
import com.gy.hsxt.uc.bs.bean.consumer.BsHsCard;

/**
 * 互生卡信息Mapper
 * 
 * @Package: com.hsxt.bs.btool.mapper
 * @ClassName: CardInfoMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:41:02
 * @company: gyist
 * @version V3.0.0
 */
@Repository(value = "cardInfoMapper")
public interface CardInfoMapper {

	/**
	 * 批量插入卡信息
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午4:07:47
	 * @param cardInfos
	 * @return : void
	 * @version V3.0.0
	 */
	public void batchInsertCardInfo(@Param("cardInfos") List<CardInfo> cardInfos);

	/**
	 * 插入卡信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月2日 上午9:47:15
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertCardInfo(CardInfo entity);

	/**
	 * 调用存储过程批量开卡
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 上午10:19:09
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void callBatchOpenCard(BatchOpenCard entity);

	/**
	 * 根据卡号查询卡信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月2日 上午10:22:32
	 * @param cardId
	 * @return
	 * @return : CardInfo
	 * @version V3.0.0
	 */
	public CardInfo selectCardInfoById(String cardId);

	/**
	 * 导出暗码
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午4:08:33
	 * @param confNo
	 * @return
	 * @return : List<CardInfo>
	 * @version V3.0.0
	 */
	public List<CardInfo> selectCardInfoRarkCode(String confNo);

	/**
	 * 导出初始化密码
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午4:24:52
	 * @param confNo
	 * @return
	 * @return : List<CardInfo>
	 * @version V3.0.0
	 */
	public List<CardInfo> selectCardInfoInitPwd(String confNo);

	/**
	 * 导出配置单卡信息
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午4:25:21
	 * @param confNo
	 * @return
	 * @return : List<CardInfo>
	 * @version V3.0.0
	 */
	public List<CardInfo> selectCardInfoByConfNo(String confNo);

	/**
	 * 查询配置单的最大和最小消费者互生号
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午6:23:16
	 * @param confNo
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public String selectConfMaxAndMinPreResNo(@Param("confNo") String confNo);

	/**
	 * 插入重做卡信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月5日 下午2:43:44
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertMakingCardInfo(MakingCardInfo entity);

	/**
	 * 批量插入重做卡信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月5日 下午3:04:31
	 * @param makings
	 * @return : void
	 * @version V3.0.0
	 */
	public void batchInsertMakingCardInfo(
			@Param("makings") List<MakingCardInfo> makings);

	/**
	 * 根据配置单号查询重做卡信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月5日 下午2:45:44
	 * @param confNo
	 * @return
	 * @return : List<MakingCardInfo>
	 * @version V3.0.0
	 */
	public List<MakingCardInfo> selectMakingCardInfoByNo(String confNo);

	/**
	 * 查询同步到UC卡信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月9日 上午11:37:34
	 * @param confNo
	 * @return
	 * @return : List<BsHsCard>
	 * @version V3.0.0
	 */
	public List<BsHsCard> selectCardInfoByUcSync(String confNo);
}
