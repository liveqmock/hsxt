/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.tool.DeliveryCorp;

/**
 * 快递公司Mapper
 * 
 * @Package: com.hsxt.bs.btool.mapper
 * @ClassName: DeliveryCorpMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月30日 下午4:27:43
 * @company: gyist
 * @version V3.0.0
 */
@Repository(value = "deliveryCorpMapper")
public interface DeliveryCorpMapper {

	/**
	 * 插入快递公司
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年9月30日 下午4:29:43
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertDeliveryCorp(DeliveryCorp entity);

	/**
	 * 修改快递公司
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年9月30日 下午4:30:19
	 * @param entity
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateDeliveryCorp(DeliveryCorp entity);

	/**
	 * 删除快递公司
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年9月30日 下午4:30:56
	 * @param dcId
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int deleteDeliveryCorpById(String dcId);

	/**
	 * 根据id查询快递公司
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年9月30日 下午4:31:23
	 * @param dcId
	 * @return
	 * @return : DeliveryCorp
	 * @version V3.0.0
	 */
	public DeliveryCorp selectDeliveryCorpById(String dcId);

	/**
	 * 分页查询快递公司
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年9月30日 下午4:33:25
	 * @param corpName
	 * @return
	 * @return : List<DeliveryCorp>
	 * @version V3.0.0
	 */
	public List<DeliveryCorp> selectDeliveryCorpListPage(
			@Param("corpName") String corpName);

	/**
	 * 查询所有的快递公司
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年9月30日 下午4:33:49
	 * @return
	 * @return : List<DeliveryCorp>
	 * @version V3.0.0
	 */
	public List<DeliveryCorp> selectDeliveryCorpAll();
}
