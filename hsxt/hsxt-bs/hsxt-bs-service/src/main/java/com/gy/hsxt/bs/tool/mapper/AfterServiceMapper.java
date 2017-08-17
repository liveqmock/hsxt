/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.AfterService;
import com.gy.hsxt.bs.bean.tool.AfterServiceDetail;

/**
 * 售后服务申请Mapper
 * 
 * @Package: com.gy.hsxt.bs.tool.mapper.mapping
 * @ClassName: AfterServiceMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年11月6日 上午10:46:30
 * @company: gyist
 * @version V3.0.0
 */
@Repository(value = "afterServiceMapper")
public interface AfterServiceMapper {

	/**
	 * 插入售后服务单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月6日 上午10:57:02
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertAfterService(AfterService entity);

	/**
	 * 批量插入售后服务单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月6日 上午10:57:05
	 * @param afters
	 * @return : void
	 * @version V3.0.0
	 */
	public void batchInsertAfterService(@Param("afters") List<AfterService> afters);

	/**
	 * 插入售后服务清单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月6日 上午10:57:15
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertAfterServiceDetail(AfterServiceDetail entity);

	/**
	 * 批量插入售后服务清单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月6日 上午10:57:19
	 * @param details
	 * @return : void
	 * @version V3.0.0
	 */
	public void batchInsertAfterServiceDetail(@Param("details") List<AfterServiceDetail> details);

	/**
	 * 分页查询售后服务单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月6日 上午11:26:28
	 * @param param
	 * @return
	 * @return : List<AfterService>
	 * @version V3.0.0
	 */
	public List<AfterService> selectAfterServiceByListPage(BaseParam param);

	/**
	 * 根据id查询售后服务单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月6日 上午11:34:57
	 * @param afterOrderNo
	 * @return
	 * @return : AfterService
	 * @version V3.0.0
	 */
	public AfterService selectAfterServiceByNo(String afterOrderNo);

	/**
	 * 修改售后服务单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月6日 上午10:59:15
	 * @param entity
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateAfterService(AfterService entity);

	/**
	 * 修改售后服务清单配置完成
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月6日 下午6:26:37
	 * @param afterOrderNo
	 * @param newDeviceSeqNo
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateAfterServiceDetailConfig(@Param("afterOrderNo") String afterOrderNo,
			@Param("deviceSeqNo") String deviceSeqNo, @Param("newDeviceSeqNo") String newDeviceSeqNo);

	/**
	 * 批量修改售后服务清单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月6日 上午10:59:22
	 * @param details
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public void batchUpdateAfterServiceDetail(@Param("details") List<AfterServiceDetail> details);

	/**
	 * 根据售后服务编号查询清单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月6日 上午11:38:14
	 * @param afterOrderNo
	 * @return
	 * @return : List<AfterServiceDetail>
	 * @version V3.0.0
	 */
	public List<AfterServiceDetail> selectAfterServiceDetailByNo(String afterOrderNo);

	/**
	 * 根据配置单查询售后服务清单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月6日 上午11:38:18
	 * @param confNo
	 * @return
	 * @return : List<AfterServiceDetail>
	 * @version V3.0.0
	 */
	public List<AfterServiceDetail> selectAfterServiceDetailByConfNo(String[] confNo);

	/**
	 * 根据发货单编号查询售后清单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月6日 下午12:11:40
	 * @param shippingId
	 * @return
	 * @return : List<AfterServiceDetail>
	 * @version V3.0.0
	 */
	public List<AfterServiceDetail> selectAfterServiceDetailByShippingId(String shippingId);

	/**
	 * 根据客户、售后订单、配置单查询售后清单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月6日 下午5:46:22
	 * @param entCustId
	 * @param afterOrderNo
	 * @param confNo
	 * @return
	 * @return : List<AfterServiceDetail>
	 * @version V3.0.0
	 */
	public List<AfterServiceDetail> selectAfterServiceDetailByNoIdNo(@Param("entCustId") String entCustId,
			@Param("afterOrderNo") String afterOrderNo, @Param("confNo") String confNo,
			@Param("isConfig") Boolean isConfig);

	/**
	 * 根据序列号查询售后清单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月19日 下午3:54:34
	 * @param seqNos
	 * @return
	 * @return : List<String>
	 * @version V3.0.0
	 */
	public List<String> selectAfterDetailBySeqNo(@Param("seqNos") String seqNos);
}
