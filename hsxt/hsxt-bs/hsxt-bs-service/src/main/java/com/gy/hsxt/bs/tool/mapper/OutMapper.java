/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.tool.pageparam.ToolProductVo;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolEnterOutPage;
import com.gy.hsxt.bs.tool.bean.Out;
import com.gy.hsxt.bs.tool.bean.OutDetail;

/**
 * 工具出库Mapper
 * 
 * @Package: com.hsxt.bs.btool.mapper
 * @ClassName: EnterOutMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月30日 下午4:52:47
 * @company: gyist
 * @version V3.0.0
 */
@Repository(value = "outMapper")
public interface OutMapper {

	/**
	 * 批量插入工具出库明细
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年10月22日 下午4:49:37
	 * @param outs
	 * @return : void
	 * @version V3.0.0
	 */
	public void batchInsertOut(@Param("outs") List<Out> outs);

	/**
	 * 插入工具出库明细
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年10月26日 下午5:09:19
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertOut(Out entity);

	/**
	 * 批量插入出库清单
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年10月22日 下午4:49:31
	 * @param outDetails
	 * @return : void
	 * @version V3.0.0
	 */
	public void batchInsertOutDetail(
			@Param("outDetails") List<OutDetail> outDetails);

	/**
	 * 插入出库清单
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年10月26日 下午5:09:56
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertOutDetail(OutDetail entity);

	/**
	 * 分页查询工具出库流水
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年10月27日 下午3:47:59
	 * @param param
	 * @return
	 * @return : List<ToolEnterOutPage>
	 * @version V3.0.0
	 */
	public List<ToolEnterOutPage> selectToolOutSerialListPage(
			ToolProductVo param);

}
