package com.gy.hsxt.ws.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.ws.bean.ApplyQueryCondition;
import com.gy.hsxt.ws.bean.WelfareApplyInfo;

/**
 * 积分福利申请记录信息
 * 
 * @Package: com.gy.hsxt.ws.mapper
 * @ClassName: WelfareApplyRecordMapper
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 下午3:45:10
 * @version V1.0
 */
public interface WelfareApplyRecordMapper {

	/**
	 * 插入申请单信息
	 * 
	 * @param record
	 *            申请单记录
	 * @return
	 */
	int insertSelective(WelfareApplyInfo record);

	/**
	 * 查询申请单信息 通过申请单编号（主键）
	 * 
	 * @param applyWelfareNo
	 *            申请单编号
	 * @return
	 */
	WelfareApplyInfo selectByPrimaryKey(String applyWelfareNo);

	/**
	 * 更新申请单信息
	 * 
	 * @param record
	 *            申请单记录
	 * @return
	 */
	int updateByPrimaryKeySelective(WelfareApplyInfo record);

	/**
	 * 分页查询申请单记录
	 * 
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页参数
	 * @return
	 */
	List<WelfareApplyInfo> pageWelfareApply(@Param("condition") ApplyQueryCondition condition,
			@Param("page") Page page);

	/**
	 * 分页查询 统计总条数
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	int countWelfareApply(@Param("condition") ApplyQueryCondition condition);

	/**
	 * 查看是否已存在申请单
	 * 
	 * @param hsResNO
	 *            互生卡号
	 * @param welfareType
	 *            福利类型 0 意外伤害 1 免费医疗 2 他人身故
	 * @return
	 */
	int countApplyingOrder(@Param("hsResNo") String hsResNo,
			@Param("welfareType") Integer welfareType);

	/**
	 * 查询身故人互生号查询申请记录
	 * 
	 * @param deathResNo
	 *            身故人互生号
	 * @return
	 */
	List<WelfareApplyInfo> queryApplyRecordByDeathPersonResNo(String deathResNo);

	/**
	 * 查询申请记录通过互生号和申请类型
	 * 
	 * @param hsResNo
	 *            互生卡号
	 * @param welfareType
	 *            福利类型 0 意外伤害 1 免费医疗 2 他人身故
	 * @return
	 */
	List<WelfareApplyInfo> queryApplyRecord(@Param("hsResNo") String hsResNo,
			@Param("welfareType") Integer welfareType);

}