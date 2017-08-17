package com.gy.hsxt.ws.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.ws.bean.WelfareQualification;

/**
 * 福利资格信息 Mapper
 * 
 * @Package: com.gy.hsxt.ws.mapper
 * @ClassName: WelfareQualificationMapper
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 下午3:58:43
 * @version V1.0
 */
public interface WelfareQualificationMapper {
	/**
	 * 生成一条福利资格记录
	 * 
	 * @param record
	 *            福利资格
	 * @return
	 */
	int insertSelective(WelfareQualification record);

	/**
	 * 查询福利资格信息 通过福利资格编号（主键）
	 * 
	 * @param welfareId
	 *            福利资格编号（
	 * @return
	 */
	WelfareQualification selectByPrimaryKey(String welfareId);

	/**
	 * 更新福利资格
	 * 
	 * @param record
	 *            福利资格
	 * @return
	 */
	int updateByPrimaryKeySelective(WelfareQualification record);

	/**
	 * 批量插入福利资格
	 * 
	 * @param list
	 * @return
	 */
	int batchInsertWelfareQualification(List<WelfareQualification> list);

	/**
	 * 检查处理累计消费积分是否符合福利资格
	 * 
	 * @param thresholdPoint
	 *            消费积分累计阀值
	 * @param durInvalidDays
	 *            连续失效阀值
	 * @param maxPayAmount
	 *            意外保障最大补贴
	 * @param executeDate
	 *            执行日期
	 */
	void handConsumePoint(@Param("thresholdPoint") Integer thresholdPoint,
			@Param("durInvalidDays") Integer durInvalidDays,
			@Param("maxPayAmount") Integer maxPayAmount, @Param("executeDate") Date executeDate);

	/**
	 * 检查处理累计投资积分是否符合福利资格
	 * 
	 * @param thresholdPoint
	 *            投资积分累计阀值
	 * @param executeDate
	 *            执行日期
	 */
	void handInvestPoint(@Param("thresholdPoint") Integer thresholdPoint,
			@Param("executeDate") Date executeDate);

	/**
	 * 检查处理已到期的福利资格
	 * 
	 * @param thresholdPoint
	 *            消费积分累计阀值
	 * @param maxPayAmount
	 *            意外保障最大补贴
	 * @param executeDate
	 *            执行日期
	 */
	void handExpierdWelfare(@Param("thresholdPoint") Integer thresholdPoint,
			@Param("maxPayAmount") Integer maxPayAmount, @Param("executeDate") Date executeDate);

	/**
	 * 查询客户福利资格信息
	 * 
	 * @param hsResNo
	 *            互生号
	 * @return
	 */
	WelfareQualification findWelfareQualify(@Param("hsResNo") String hsResNo);

	/**
	 * 统计客户享受福利资格
	 * 
	 * @param hsResNo
	 *            互生号
	 * @param welfareType
	 *            福利分类 0 一年意外保障 1免费医疗补贴
	 * @return
	 */
	int countWelfareQualify(@Param("hsResNo") String hsResNo,
			@Param("welfareType") Integer welfareType);

	/**
	 * 分页查询福利资格记录
	 * 
	 * @param hsResNo
	 *            互生号
	 * @param welfareType
	 *            福利分类 0 一年意外保障 1免费医疗补贴
	 * @param page
	 *            分页参数
	 * @return
	 */
	List<WelfareQualification> pageWelfareQualify(@Param("hsResNo") String hsResNo,
			@Param("welfareType") Integer welfareType, @Param("page") Page page);

	/**
	 * 分页查询 统计查询记录数
	 * 
	 * @param hsResNo
	 *            互生号
	 * @param welfareType
	 *            福利分类 0 一年意外保障 1免费医疗补贴
	 * @return
	 */
	int totalSize(@Param("hsResNo") String hsResNo, @Param("welfareType") Integer welfareType);

	/**
	 * 
	 * @param hsResNo
	 * @return
	 */
	List<WelfareQualification> queryHisWelfareQualify(@Param("hsResNo") String hsResNo);

}