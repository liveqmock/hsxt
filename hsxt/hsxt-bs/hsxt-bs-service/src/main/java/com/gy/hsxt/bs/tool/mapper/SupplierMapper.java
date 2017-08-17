/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.tool.Supplier;

/**
 * 供应商Mapper
 * 
 * @Package: com.hsxt.bs.btool.mapper
 * @ClassName: SupplierMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午5:05:00
 * @company: gyist
 * @version V3.0.0
 */
@Repository(value = "supplierMapper")
public interface SupplierMapper {

	/**
	 * 插入供应商
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年9月29日 下午5:08:25
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertSupplier(Supplier entity);

	/**
	 * 修改供应商
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年9月29日 下午6:04:33
	 * @param entity
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateSupplier(Supplier entity);

	/**
	 * 删除供应商
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年9月29日 下午6:17:42
	 * @param supplierId
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int deleteSupplierById(@Param("supplierId") String supplierId);

	/**
	 * 根据id查询供应商
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年9月29日 下午6:21:17
	 * @param supplierId
	 * @return
	 * @return : Supplier
	 * @version V3.0.0
	 */
	public Supplier seleteSupplierById(String supplierId);

	/**
	 * 分页查询供应商
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年9月29日 下午6:25:42
	 * @param supplierName
	 * @param corpName
	 * @param linkMan
	 * @return List<Supplier>
	 * @version V3.0.0
	 */
	public List<Supplier> selectSupplierListPage(
			@Param("supplierName") String supplierName,
			@Param("corpName") String corpName, @Param("linkMan") String linkMan);

	/**
	 * 查询所有供应商
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年10月20日 上午9:24:25
	 * @return
	 * @return : List<Supplier>
	 * @version V3.0.0
	 */
	public List<Supplier> seleteSupplierByAll();

}
