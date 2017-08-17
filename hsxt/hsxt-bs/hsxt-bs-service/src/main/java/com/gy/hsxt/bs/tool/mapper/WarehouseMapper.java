/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.tool.Warehouse;
import com.gy.hsxt.bs.bean.tool.WhArea;

/**
 * 仓库Mapper
 * 
 * @Package: com.gy.hsxt.bs.tool.mapper
 * @ClassName: WarehouseMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月9日 下午3:26:07
 * @company: gyist
 * @version V3.0.0
 */
@Repository(value = "warehouseMapper")
public interface WarehouseMapper {

	/**
	 * 插入仓库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月9日 下午3:26:22
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertWarehouse(Warehouse entity);

	/**
	 * 插入仓库配送区域
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月9日 下午3:26:30
	 * @param params
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertWhArea(Map<String, Object> params);

	/**
	 * 修改仓库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月9日 下午3:26:38
	 * @param entity
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateWarehouse(Warehouse entity);

	/**
	 * 默认仓库修改非默认
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月9日 下午3:26:45
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateDefaultToNotDefault();

	/**
	 * 删除仓库的所有配送区域
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月9日 下午3:26:55
	 * @param whId
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int deleteWhAreaByWhId(String whId);

	/**
	 * 删除仓库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月9日 下午3:27:03
	 * @param whId
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int deleteWarehouseById(String whId);

	/**
	 * 根据id查询仓库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月9日 下午3:27:09
	 * @param whId
	 * @return
	 * @return : Warehouse
	 * @version V3.0.0
	 */
	public Warehouse selectWarehouseById(String whId);

	/**
	 * 分页查询仓库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月9日 下午3:27:16
	 * @param whName
	 * @return
	 * @return : List<Warehouse>
	 * @version V3.0.0
	 */
	public List<Warehouse> selectWarehouseListPage(@Param("whName") String whName);

	/**
	 * 查询所有的仓库
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月3日 下午2:12:18
	 * @return
	 * @return : List<Warehouse>
	 * @version V3.0.0
	 */
	public List<Warehouse> selectWarehouseAll();

	/**
	 * 根据id查询仓库配送区域
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月8日 上午9:56:54
	 * @param whId
	 * @return
	 * @return : List<WhArea>
	 * @version V3.0.0
	 */
	public List<WhArea> selectWhAreaByWhId(@Param("whId") String whId);

	/**
	 * 根据省编号查询仓库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月8日 上午10:23:12
	 * @param provinceNo
	 * @return
	 * @return : Warehouse
	 * @version V3.0.0
	 */
	public Warehouse selectWarehouseByNo(@Param("provinceNo") String provinceNo);

	/**
	 * 查询地区平台默认仓库
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月8日 上午10:30:33
	 * @return
	 * @return : Warehouse
	 * @version V3.0.0
	 */
	public Warehouse selectWarehouseByDefault();

	/**
	 * 根据省代码查询仓库区域
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月12日 下午2:40:56
	 * @param provinceNos
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int selectCountWhAreaByNos(@Param("provinceNos") String provinceNos);
}
