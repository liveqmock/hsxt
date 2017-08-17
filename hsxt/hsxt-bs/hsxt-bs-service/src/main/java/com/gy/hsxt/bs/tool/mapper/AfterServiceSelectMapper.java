/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.AfterServiceDetail;
import com.gy.hsxt.bs.bean.tool.resultbean.AfterDeviceDetail;
import com.gy.hsxt.bs.bean.tool.resultbean.SecretKeyOrderInfoPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolConfigPage;

/**
 * 售后服务查询Mapper(用于查询其他表的数据)
 * 
 * @Package: com.gy.hsxt.bs.tool.mapper
 * @ClassName: AfterServiceSelectMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年11月6日 下午3:16:49
 * @company: gyist
 * @version V3.0.0
 */
@Repository(value = "afterServiceSelectMapper")
public interface AfterServiceSelectMapper {

	/**
	 * 分页查询售后刷卡工具制作配置单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月6日 下午3:41:59
	 * @param bean
	 * @return
	 * @return : List<ToolConfigPage>
	 * @version V3.0.0
	 */
	public List<ToolConfigPage> selectToolAfterConfigListPage(BaseParam bean);

	/**
	 * 分页查询秘钥订单--售后灌秘钥
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月7日 上午11:23:56
	 * @param bean
	 * @return
	 * @return : List<SecretKeyOrderInfoPage>
	 * @version V3.0.0
	 */
	public List<SecretKeyOrderInfoPage> selectAfterSecretKeyOrderByListPage(BaseParam bean);

	/**
	 * 查询售后秘钥设备配置清单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月7日 下午2:07:12
	 * @param confNo
	 * @return
	 * @return : List<AfterDeviceDetail>
	 * @version V3.0.0
	 */
	public List<AfterDeviceDetail> selectAfterSecretKeyConfigDetail(String confNo);

	/**
	 * 分页查询售后工具配送配送单(生成发货单)
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月7日 下午2:16:59
	 * @param bean
	 * @return
	 * @return : List<ToolConfigPage>
	 * @version V3.0.0
	 */
	public List<ToolConfigPage> selectToolConfigShippingAfterListPage(BaseParam bean);

	/**
	 * 根据订单号查询售后清单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月19日 下午5:23:59
	 * @param orderNo
	 * @return
	 * @return : List<AfterServiceDetail>
	 * @version V3.0.0
	 */
	public List<AfterServiceDetail> selectAfterServiceDetailByOrderNo(String orderNo);
}
