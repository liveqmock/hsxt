/**
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 * <p>
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;

import com.gy.hsxt.bs.tool.bean.ConfigSegment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.tool.ResSegment;

/**
 * 资源段Mapper
 *
 * @version V3.0.0
 * @Package: com.gy.hsxt.bs.tool.mapper
 * @ClassName: ResSegmentMapper
 * @Description:
 * @author: likui
 * @date: 2016/6/14 11:33
 * @company: gyist
 */
@Repository(value = "resSegmentMapper")
public interface ResSegmentMapper {

    /**
     * 批量插入资源段
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/14 11:36
     * @param: resSegments
     * @return: int
     * @company: gyist
     * @version V3.0.0
     */
    public int batchInsertResSegment(@Param("resSegments") List<ResSegment> resSegments);

    /**
     * 插入资源段
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/14 11:37
     * @param: resSegment
     * @return: int
     * @company: gyist
     * @version V3.0.0
     */
    public int insertResSegment(ResSegment resSegment);

    /**
     * 批量修改资源段
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/14 11:40
     * @param: resSegments
     * @return: void
     * @company: gyist
     * @version V3.0.0
     */
    public void batchUpdateResSegment(@Param("resSegments") List<ResSegment> resSegments);

    /**
     * 修改资源段
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/14 11:41
     * @param: resSegment
     * @return:
     * @company: gyist
     * @version V3.0.0
     */
    public int updateResSegment(ResSegment resSegment);

    /**
     * 根据id查询资源段
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/14 11:43
     * @param: segmentId
     * @return: ResSegment
     * @company: gyist
     * @version V3.0.0
     */
    public ResSegment selectResSegmentById(@Param("segmentId") String segmentId);

    /**
     * 根据客户号、配置单号、代购订单号查询资源段
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/14 11:44
     * @param: proxyOrderNo
     * @param: entCustId
     * @param: confNo
     * @return: List<ResSegment>
     * @company: gyist
     * @version V3.0.0
     */
    public List<ResSegment> selectResSegmentByIf(@Param("proxyOrderNo") String proxyOrderNo,
                                                 @Param("entCustId") String entCustId, @Param("confNo") String confNo);

    /**
     * 根据订单号修改资源段的状态
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/14 15:04
     * @param: orderNo
     * @param: status
     * @return: int
     * @company: gyist
     * @version V3.0.0
     */
    public int updateResSegmentStatusByOrderNo(@Param("orderNo") String orderNo, @Param("status") String status);

    /**
     * 根据配置单号修改资源段的状态
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/14 15:04
     * @param: confNo
     * @param: status
     * @return: int
     * @company: gyist
     * @version V3.0.0
     */
    public int updateResSegmentStatusByConfNo(@Param("confNo") String confNo, @Param("status") String status);

    /**
     * 验证资源段是否可以下单
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/16 17:22
     * @param: segmentIds
     * @return: int
     * @company: gyist
     * @version V3.0.0
     */
    public int valiResSegmentIsOrder(@Param("segmentIds") List<String> segmentIds);

    /**
     * 根据id集合修改资源段
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/16 17:40
     * @param: segmentIds
     * @param: proxyOrderNo
     * @param: confNo
     * @param: status
     * @return: int
     * @company: gyist
     * @version V3.0.0
     */
    public int updateResSegmentByIds(@Param("segmentIds") List<String> segmentIds,
                                     @Param("proxyOrderNo") String proxyOrderNo, @Param("confNo") String confNo,
                                     @Param("status") String status);

    /**
     * 根据客户号、配置单号、代购订单号查询资源段Id
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/17 9:48
     * @param: proxyOrderNo
     * @param: entCustId
     * @param: confNo
     * @eturn: List<String>
     * @copany: gyist
     * @version V3.0.0
     */
    public List<String> selectSegmentIdByIf(@Param("proxyOrderNo") String proxyOrderNo,
                                            @Param("entCustId") String entCustId, @Param("confNo") String confNo);

    /**
     * 查询配置资源段
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/24 16:05
     * @param: segmentIds
     * @return: ConfigSegment
     * @company: gyist
     * @version V3.0.0
     */
    public ConfigSegment selectConfigSegmentByNo(@Param("segmentIds") List<String> segmentIds);

    /**
     * 查询企业开始购买的段数
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/24 17:50
     * @param: entCustId
     * @return: int
     * @company: gyist
     * @version V3.0.0
     */
    public int selectStartBuySegmentByCust(@Param("entCustId") String entCustId);

    /**
     * 验证资源段列表中是否有代购中的
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/28 11:08
     * @param: segmentIds
     * @return: int
     * @company: gyist
     * @version V3.0.0
     */
    public int validResSegmentIsProxy(@Param("segmentIds") List<String> segmentIds);

    /**
     * 验证订单关联资源段是否可以关闭
     *
     * @Desc:
     * @author: likui
     * @created: 2016/7/1 17:49
     * @param: entCustId
     * @param: orderNo
     * @return: int
     * @company: gyist
     * @version V3.0.0
     */
    public int vaildOrderReleRegmentIsClose(@Param("entCustId") String entCustId, @Param("orderNo") String orderNo);
}