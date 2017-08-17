/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.tool.ConsumeKSN;
import com.gy.hsxt.bs.bean.tool.KsnExportRecord;
import com.gy.hsxt.bs.bean.tool.McrKsnRecord;
import com.gy.hsxt.bs.bean.tool.PointKSN;
import com.gy.hsxt.bs.tool.bean.KsnEnterInfo;

/**
 * 刷卡器Mapper
 * 
 * @Package: com.hsxt.bs.btool.mapper
 * @ClassName: KsnInfoMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月30日 下午3:16:53
 * @company: gyist
 * @version V3.0.0
 */
@Repository(value = "ksnInfoMapper")
public interface KsnInfoMapper {

	/**
	 * 批量插入积分刷卡器数据
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午3:18:17
	 * @param ponints
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertPointKsnInfo(@Param("ponints") List<PointKSN> ponints);

	/**
	 * 批量插入消费刷卡器数据
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午3:19:11
	 * @param comsumes
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertConsumeKsnInfo(@Param("comsumes") List<ConsumeKSN> comsumes);

	/**
	 * 根据设备客户号查询积分刷卡器KSN信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月9日 上午10:09:35
	 * @param deviceCustId
	 * @return
	 * @return : PointKSN
	 * @version V3.0.0
	 */
	public PointKSN selectPointKsnInfoById(String deviceCustId);

	/**
	 * 根据设备客户号查询消费刷卡器KSN信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月9日 上午10:09:57
	 * @param deviceCustId
	 * @return
	 * @return : ConsumeKSN
	 * @version V3.0.0
	 */
	public ConsumeKSN selectConsumeKsnInfoById(String deviceCustId);

	/**
	 * 根据批次号查询积分刷卡器数据
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午3:20:19
	 * @param batchNo
	 * @return
	 * @return : List<PointKSN>
	 * @version V3.0.0
	 */
	public List<PointKSN> selectPointKsnInfoByNo(String batchNo);

	/**
	 * 根据批次号查询消费刷卡器数据
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午3:20:35
	 * @param batchNo
	 * @return
	 * @return : List<ConsumeKSN>
	 * @version V3.0.0
	 */
	public List<ConsumeKSN> selectConsumeKsnByNo(String batchNo);

	/**
	 * 入库时查询积分刷卡器数据
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午3:21:45
	 * @param batchNo
	 * @return
	 * @return : List<KsnEnterInfo>
	 * @version V3.0.0
	 */
	public List<KsnEnterInfo> selectPointKsnInfoByEnter(String batchNo);

	/**
	 * 入库时查询消费刷卡器数据
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午3:21:57
	 * @param batchNo
	 * @return
	 * @return : List<KsnEnterInfo>
	 * @version V3.0.0
	 */
	public List<KsnEnterInfo> selectConsumeKsnByEnter(String batchNo);

	/**
	 * 查询最大的积分刷卡器的序列号
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月8日 上午11:45:26
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public String selectMaxPointDeviceSeqNo();

	/**
	 * 插入刷卡器KSN记录
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月23日 下午6:17:58
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertMcrKsnRecord(McrKsnRecord entity);

	/**
	 * 修改刷卡器KSN生成记录入库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月23日 下午6:25:52
	 * @param batchNo
	 * @param operNo
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateMcrKsnRecordByNo(@Param("batchNo") String batchNo, @Param("operNo") String operNo);

	/**
	 * 分页查询刷卡器KSN生成记录
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月23日 下午6:21:05
	 * @param storeStatus
	 * @param mcrType
	 * @return
	 * @return : List<McrKsnRecord>
	 * @version V3.0.0
	 */
	public List<McrKsnRecord> selectMcrKsnRecordListPage(@Param("storeStatus") Integer storeStatus,
			@Param("mcrType") Integer mcrType);

	/**
	 * 插入导出积分刷卡器记录
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月12日 下午2:50:42
	 * @param bean
	 * @return : int
	 * @version V3.0.0
	 */
	public int insertPointKsnExportRecord(KsnExportRecord bean);

	/**
	 * 插入导出消费刷卡器记录
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月12日 下午2:51:02
	 * @param bean
	 * @return : int
	 * @version V3.0.0
	 */
	public int insertConsumeKsnExportRecord(KsnExportRecord bean);

	/**
	 * 查询刷卡器导出记录
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月12日 下午4:32:56
	 * @param batchNo
	 * @return
	 * @return : List<KsnExportRecord>
	 * @version V3.0.0
	 */
	public List<KsnExportRecord> selectKsnExportRecord(String batchNo);

	/**
	 * 查询序列号是否已存在
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月19日 下午3:09:07
	 * @param seqNos
	 * @return
	 * @return : List<String>
	 * @version V3.0.0
	 */
	public List<String> selectConsumeKsnBySeqNo(@Param("seqNos") String seqNos);
}
