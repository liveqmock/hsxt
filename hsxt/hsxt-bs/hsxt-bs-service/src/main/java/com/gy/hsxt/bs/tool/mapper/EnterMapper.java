/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.tool.Enter;
import com.gy.hsxt.bs.bean.tool.InstorageInspection;
import com.gy.hsxt.bs.bean.tool.WhInventory;
import com.gy.hsxt.bs.bean.tool.pageparam.ToolProductVo;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolEnterOutPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolStock;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolStockWarning;
import com.gy.hsxt.bs.tool.bean.EnterDetail;

/**
 * 入库Mapper
 * 
 * @Package: com.hsxt.bs.btool.mapper
 * @ClassName: EnterMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月30日 下午2:00:25
 * @company: gyist
 * @version V3.0.0
 */
@Repository(value = "enterMapper")
public interface EnterMapper {

	/**
	 * 插入工具入库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午2:01:58
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertEnter(Enter entity);

	/**
	 * 批量插入入库清单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午3:08:21
	 * @param details
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertEnterDetail(@Param("details") List<EnterDetail> details);

	/**
	 * 根据批次号查询入库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午3:33:06
	 * @param batchNo
	 * @return
	 * @return : Enter
	 * @version V3.0.0
	 */
	public Enter selectEnterByNo(String batchNo);

	/**
	 * 查询入库的免费工具
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月22日 上午11:43:39
	 * @param productId
	 * @param whId
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public String selectToolEnterByForFree(@Param("productId") String productId, @Param("whId") String whId);

	/**
	 * 分页查询需要配置工具库存
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月26日 下午5:39:57
	 * @param categoryCode
	 * @param productId
	 * @param whId
	 * @return
	 * @return : List<ToolStock>
	 * @version V3.0.0
	 */
	public List<ToolStock> selecConfigToolStockByListPage(@Param("categoryCode") String categoryCode,
			@Param("productId") String productId, @Param("whId") String whId);

	/**
	 * 插入工具库存盘库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月26日 下午8:05:25
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertWhInventory(WhInventory entity);

	/**
	 * 插入工具入库抽检
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月26日 下午8:06:21
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertEnterInspection(InstorageInspection entity);

	/**
	 * 根据入库批次号查询POS机配置数量
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月27日 上午9:12:58
	 * @param batchNo
	 * @return
	 * @return : Integer
	 * @version V3.0.0
	 */
	public Integer selectPosConfigNumByNo(String batchNo);

	/**
	 * 根据批次号查询POS机设备序列号
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月27日 上午9:13:58
	 * @param batchNo
	 * @return
	 * @return : List<String>
	 * @version V3.0.0
	 */
	public List<String> selectPosDeviceSeqNoByNo(String batchNo);

	/**
	 * 分页查询工具库存预警
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月27日 上午11:19:07
	 * @param categoryCode
	 * @param productName
	 * @return
	 * @return : List<ToolStockWarning>
	 * @version V3.0.0
	 */
	public List<ToolStockWarning> toolEnterStockWarningListPage(@Param("productName") String productName,
			@Param("categoryCode") String categoryCode, @Param("warningStatus") Integer warningStatus);

	/**
	 * 分页查询工具入库流水
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月27日 下午3:46:24
	 * @param param
	 * @return
	 * @return : List<ToolEnterOutPage>
	 * @version V3.0.0
	 */
	public List<ToolEnterOutPage> selectToolEnterSerialListPage(ToolProductVo param);

	/**
	 * 分页统计地区平台仓库工具
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月27日 下午5:39:28
	 * @param categoryCode
	 * @return
	 * @return : List<ToolStock>
	 * @version V3.0.0
	 */
	public List<ToolStock> statisticPlatWhToolListPage(@Param("categoryCode") String categoryCode);

}
