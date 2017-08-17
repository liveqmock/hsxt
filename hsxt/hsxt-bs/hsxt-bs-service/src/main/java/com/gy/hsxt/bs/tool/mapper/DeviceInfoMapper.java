/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.tool.DeviceInfo;
import com.gy.hsxt.bs.bean.tool.resultbean.EntDeviceInfo;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolRelationDetail;
import com.gy.hsxt.bs.tool.bean.DeviceConfig;

/**
 * 设备通用信息Mapper
 *
 * @version V3.0.0
 * @Package: com.hsxt.bs.btool.mapper
 * @ClassName: DeviceInfoMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月30日 下午4:35:46
 * @company: gyist
 */
@Repository(value = "deviceInfoMapper")
public interface DeviceInfoMapper {

    /**
     * 批量插入设备通用信息
     *
     * @param devices
     * @return : void
     * @Desc:
     * @author: likui
     * @created: 2015年9月30日 下午4:38:20
     * @version V3.0.0
     */
    public void batchInsertDeviceInfo(@Param("devices") List<DeviceInfo> devices);

    /**
     * 修改设备使用状态
     *
     * @param deviceCustId
     * @param useStatus
     * @param storeStatus
     * @param operNo
     * @return : int
     * @Desc:
     * @author: likui
     * @created: 2015年10月26日 下午2:23:47
     * @version V3.0.0
     */
    public int updateDeviceUseStatusById(@Param("deviceCustId") String deviceCustId,
                                         @Param("useStatus") Integer useStatus, @Param("storeStatus") Integer storeStatus,
                                         @Param("operNo") String operNo);

    /**
     * 插入设备关联配置单
     *
     * @param entity
     * @return : int
     * @Desc:
     * @author: likui
     * @created: 2015年9月30日 下午4:48:36
     * @version V3.0.0
     */
    public void insertDeviceConfig(DeviceConfig entity);

    /**
     * 批量插入设备关联配置单
     *
     * @param deviceConfs
     * @return : void
     * @Desc:
     * @author: likui
     * @created: 2015年9月30日 下午4:50:16
     * @version V3.0.0
     */
    public int batchInsertDeviceConfig(@Param("deviceConfs") List<DeviceConfig> deviceConfs);

    /**
     * 修改设备关联已使用
     *
     * @param confNo
     * @param terminalNo
     * @return : int
     * @Description:
     * @author: likui
     * @created: 2015年11月3日 下午5:37:38
     * @version V3.0.0
     */
    public int updateDeviceConfigIsUse(@Param("confNo") String confNo, @Param("terminalNo") String terminalNo);

    /**
     * 根据序列号删除配置关联关系
     *
     * @param deviceSeqNo
     * @return : int
     * @Description:
     * @author: likui
     * @created: 2015年11月4日 上午10:41:59
     * @version V3.0.0
     */
    public int deleteDeviceConfigByNo(String deviceSeqNo);

    /**
     * 查询设备关联详情
     *
     * @param confNo
     * @return : List<ToolRelationDetail>
     * @Desc:
     * @author: likui
     * @created: 2015年10月15日 下午5:42:39
     * @version V3.0.0
     */
    public List<ToolRelationDetail> selectDeviceRelevanceDetail(String confNo);

    /**
     * 查询设备详情
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/15 9:27
     * @param: deviceSeqNo
     * @eturn: ToolRelationDetail
     * @copany: gyist
     * @version V3.0.0
     */
    public ToolRelationDetail selectDeviceDetail(String deviceSeqNo);

    /**
     * 根据配置单查询设备关联
     *
     * @param confNo
     * @return : List<DeviceConfig>
     * @Desc:
     * @author: likui
     * @created: 2015年10月22日 下午4:28:24
     * @version V3.0.0
     */
    public List<DeviceConfig> selectDeviceConfigByNo(String confNo);

    /**
     * 根据序列号查询设备
     *
     * @param deviceSeqNo
     * @return : DeviceInfo
     * @Desc:
     * @author: likui
     * @created: 2015年10月16日 下午12:19:11
     * @version V3.0.0
     */
    public DeviceInfo selectDeviceInfoBySeqNo(String deviceSeqNo);

    /**
     * 根据设备序列号和配置单号查询设备信息(验证配置单和设备是否同仓库)
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/13 14:21
     * @param: deviceSeqNo
     * @param: confNo
     * @eturn: int
     * @copany: gyist
     * @version V3.0.0
     */
    public int selectDeviceInfoByNoAndConfNo(@Param("deviceSeqNo") String deviceSeqNo,
                                             @Param("confNo") String confNo);

    /**
     * 根据客户号和配置单号查询设备关联
     *
     * @param deviceCustId
     * @param confNo
     * @return : DeviceConfig
     * @Desc:
     * @author: likui
     * @created: 2015年10月16日 下午12:20:35
     * @version V3.0.0
     */
    public DeviceConfig selectDeviceConfigByCustIdAndNo(@Param("deviceCustId") String deviceCustId,
                                                        @Param("confNo") String confNo);

    /**
     * 根据配置单号和终端编号查询设备关联
     *
     * @param confNo
     * @param terminalNo
     * @return : DeviceConfig
     * @Description:
     * @author: likui
     * @created: 2015年11月3日 下午5:29:54
     * @version V3.0.0
     */
    public DeviceConfig selectDeviceConfigByCongNoAndNo(@Param("confNo") String confNo,
                                                        @Param("terminalNo") String terminalNo);

    /**
     * 查询刷卡工具类别的最大终端编号(积分和消费刷卡器共用)
     *
     * @param entCustId
     * @param categoryCodes
     * @return : String
     * @Desc:
     * @author: likui
     * @created: 2015年10月16日 下午7:18:39
     * @version V3.0.0
     */
    public String selectMaxTerminalNo(@Param("entCustId") String entCustId,
                                      @Param("categoryCodes") String[] categoryCodes);

    /**
     * 批量修改设备出库
     *
     * @param deviceCustIds
     * @param operNo
     * @return : void
     * @Desc:
     * @author: likui
     * @created: 2015年10月23日 下午4:28:56
     * @version V3.0.0
     */
    public void batchUpdateDeviceOutStock(@Param("deviceCustIds") List<String> deviceCustIds,
                                          @Param("operNo") String operNo);

    /**
     * 根据批次号和序列号查询设备信息
     *
     * @param deviceSeqNo
     * @param batchNo
     * @return : DeviceInfo
     * @Desc:
     * @author: likui
     * @created: 2015年10月27日 下午2:21:49
     * @version V3.0.0
     */
    public DeviceInfo selectDeviceInfoByNo(@Param("deviceSeqNo") String deviceSeqNo, @Param("batchNo") String batchNo);

    /**
     * 查询配置单配置单的终端编号
     *
     * @param confNo
     * @return : List<String>
     * @Description:
     * @author: likui
     * @created: 2015年10月29日 下午12:11:26
     * @version V3.0.0
     */
    public List<String> selectConfigTerminalNo(String confNo);

    /**
     * 批量修改设备状态
     *
     * @param devices
     * @return : void
     * @Description:
     * @author: likui
     * @created: 2015年11月13日 上午11:54:26
     * @version V3.0.0
     */
    public void batchUpdateDeviceStatus(@Param("devices") List<DeviceInfo> devices);

    /**
     * 根据序列号查询设备入库序列号
     *
     * @param seqNos
     * @return : List<String>
     * @Description:
     * @author: likui
     * @created: 2015年12月21日 下午8:45:10
     * @version V3.0.0
     */
    public List<String> countDeviceInfoBySeqNo(@Param("seqNos") String[] seqNos);

    /**
     * 查询售后换货配置设备关联
     *
     * @param confNo
     * @return : List<DeviceInfo>
     * @Description:
     * @author: likui
     * @created: 2015年12月22日 下午7:42:06
     * @version V3.0.0
     */
    public List<DeviceInfo> selectDeviceConfigByAfterBarter(@Param("confNo") String confNo);

    /**
     * 查询企业设备信息
     *
     * @param seqNo
     * @return : EntDeviceInfo
     * @Description:
     * @author: likui
     * @created: 2016年2月27日 上午11:02:08
     * @version V3.0.0
     */
    public EntDeviceInfo selectEntDeviceInfo(@Param("seqNo") String seqNo);

    /**
     * 分页查询企业设备信息
     *
     * @param seqNo
     * @param entResNo
     * @return : List<EntDeviceInfo>
     * @Description:
     * @author: likui
     * @created: 2016年2月27日 下午12:06:46
     * @version V3.0.0
     */
    public List<EntDeviceInfo> selectEntDeviceInfoListPage(@Param("seqNo") String seqNo,
                                                           @Param("entResNo") String entResNo);

    /**
     * 验证设备类型和配送单设备类似是否一致
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/15 11:30
     * @param: seqNos
     * @param: confNo
     * @return: List<DeviceInfo>
     * @company: gyist
     * @version V3.0.0
     */
    public List<DeviceInfo> vaildDeviceTypeIsSame(@Param("seqNos") List<String> seqNos, @Param("confNo") String confNo);

    /**
     * 批量验证设备使用状态
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/15 12:02
     * @param: seqNos
     * @return: String
     * @company: gyist
     * @version V3.0.0
     */
    public String batchVaildDeviceStatus(@Param("seqNos") List<String> seqNos);
}
