/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.entstatus.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.ChangeItem;
import com.gy.hsxt.bs.bean.entstatus.ChangeInfoQueryParam;
import com.gy.hsxt.bs.bean.entstatus.EntChangeBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.EntChangeInfo;
import com.gy.hsxt.bs.bean.entstatus.PerChangeBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.PerChangeInfo;
import com.gy.hsxt.bs.bean.entstatus.SysBizRecord;

/**
 * 
 * @Package: com.gy.hsxt.bs.entstatus.mapper
 * @ClassName: ChangeInfoMapper
 * @Description: 重要信息变更Mapper
 * 
 * @author: xiaofl
 * @date: 2015-12-16 下午4:26:31
 * @version V1.0
 */
@Repository
public interface ChangeInfoMapper {

    /**
     * 消费者是否存在重要信息变更
     * 
     * @param perCustId
     *            消费者客户号
     * @return true则存在，false不存在
     */
    boolean existPerChange(@Param("perCustId") String perCustId);

    /**
     * 是否存在变更项
     * 
     * @param applyId
     *            申请编号
     * @param property
     *            属性
     * @return true则存在，false不存在
     */
    boolean existPerChangeItem(@Param("applyId") String applyId, @Param("property") String property);

    /**
     * 创建消费者重要信息变更
     * 
     * @param perChangeInfo
     *            消费者重要信息变更信息
     */
    int createPerChangeApp(PerChangeInfo perChangeInfo);

    /**
     * 更新消费者重要信息变更
     * 
     * @param perChangeInfo
     *            消费者重要信息变更信息
     */
    void updatePerChangeApp(PerChangeInfo perChangeInfo);

    /**
     * 审批消费者重要信息变更
     * 
     * @param applyId
     *            申请编号
     * @param status
     *            状态
     * @param apprRemark
     *            审批意见
     * @param optCustId
     *            操作员客户号
     */
    void updatePerChangeStatus(@Param("applyId") String applyId, @Param("status") Integer status,
            @Param("apprRemark") String apprRemark, @Param("optCustId") String optCustId);

    /**
     * 创建消费者重要信息变更具体变更项
     * 
     * @param applyId
     *            申请编号
     * @param itemList
     *            变更项列表
     */
    void createPerChangeData(@Param("applyId") String applyId, @Param("itemList") List<ChangeItem> itemList);

    /**
     * 更新消费者重要信息变更具体变更项
     * 
     * @param applyId
     *            申请编号
     * @param updateList
     *            变更项列表
     */
    void updatePerChangeData(@Param("applyId") String applyId, @Param("updateList") List<ChangeItem> updateList);

    /**
     * 查询消费者重要信息变更
     * 
     * @param changeInfoQueryParam
     *            查询参数
     * @return 消费者重要信息变更列表
     */
    List<PerChangeBaseInfo> queryPerChangeListPage(ChangeInfoQueryParam changeInfoQueryParam);

    /**
     * 关联工单查询消费者重要信息变更审批
     * 
     * @param changeInfoQueryParam
     *            查询参数
     * @return 消费者重要信息变更列表
     */
    List<PerChangeBaseInfo> queryPerChange4ApprListPage(ChangeInfoQueryParam changeInfoQueryParam);

    /**
     * 通过申请编号查询消费者重要信息变更
     * 
     * @param applyId
     *            申请编号
     * @return 消费者重要信息变更
     */
    PerChangeInfo queryPerChangeById(String applyId);

    /**
     * 通过客户号查询消费者重要信息变更
     * 
     * @param perCustId
     *            消费者客户号
     * @return 消费者重要信息变更
     */
    PerChangeInfo queryPerChangeByCustId(String perCustId);

    /**
     * 通过申请编号查询消费者重要信息变更项
     * 
     * @param applyId
     *            申请编号
     * @return 消费者重要信息变更项列表
     */
    List<ChangeItem> queryPerChangeDataByApplyId(String applyId);

    /**
     * 是否存在企业重要信息变更
     * 
     * @param entCustId
     *            企业客户号
     * @return true则存在，false不存在
     */
    boolean existEntChange(@Param("entCustId") String entCustId);

    /**
     * 是否存在企业重要信息变更项
     * 
     * @param applyId
     *            申请编号
     * @param property
     *            变更项属性
     * @return true则存在，false不存在
     */
    boolean existEntChangeItem(@Param("applyId") String applyId, @Param("property") String property);

    /**
     * 查询个人重要信息变更记录
     * 
     * @param perCustId
     *            个人客户号
     * @param startDate
     *            开始时间
     * @param endDate
     *            结束时间
     * @return 个人重要信息变更记录列表
     */
    public List<SysBizRecord> queryPerChangeRecordListPage(@Param("perCustId") String perCustId,
            @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 创建企业重要信息变更申请
     * 
     * @param entChangeInfo
     *            企业重要信息变更申请
     */
    int createEntChangeApp(EntChangeInfo entChangeInfo);

    /**
     * 创建企业重要信息变具体变更项
     * 
     * @param applyId
     *            申请编号
     * @param itemList
     *            企业重要信息变具体变更项列表
     */
    void createEntChangeData(@Param("applyId") String applyId, @Param("itemList") List<ChangeItem> itemList);

    /**
     * 更新企业重要信息变具体变更项
     * 
     * @param applyId
     *            申请编号
     * @param updateList
     *            企业重要信息变具体变更项列表
     */
    void updateEntChangeData(@Param("applyId") String applyId, @Param("updateList") List<ChangeItem> updateList);

    /**
     * 查询企业重要信息变更
     * 
     * @param changeInfoQueryParam
     *            查询参数
     * @return 企业重要信息变更列表
     */
    List<EntChangeBaseInfo> queryEntChangeListPage(ChangeInfoQueryParam changeInfoQueryParam);

    /**
     * 关联工单查询企业重要信息变更
     * 
     * @param changeInfoQueryParam
     *            查询参数
     * @return 企业重要信息变更列表
     */
    List<EntChangeBaseInfo> queryEntChange4ApprListPage(ChangeInfoQueryParam changeInfoQueryParam);

    /**
     * 通过申请编号查询企业重要信息变更
     * 
     * @param applyId
     *            申请编号
     * @return 企业重要信息变更
     */
    EntChangeInfo queryEntChangeById(String applyId);

    /**
     * 通过企业客户号查询企业重要信息变更
     * 
     * @param entCustId
     *            企业客户号
     * @return 企业重要信息变更
     */
    EntChangeInfo queryEntChangeByCustId(String entCustId);

    /**
     * 通过申请编号查询企业重要变更项
     * 
     * @param applyId
     *            申请编号
     * @return 企业重要变更项列表
     */
    List<ChangeItem> queryEntChangeDataByApplyId(String applyId);

    /**
     * 更新企业重要信息变更状态
     * 
     * @param applyId
     *            申请编号
     * @param status
     *            状态
     * @param apprRemark
     *            审批意见
     * @param optCustId
     *            审批操作员客户号
     */
    void updateEntChangeStatus(@Param("applyId") String applyId, @Param("status") Integer status,
            @Param("apprRemark") String apprRemark, @Param("optCustId") String optCustId);

    /**
     * 是否存在正在审批的重要信息变更
     * 
     * @param entCustId
     *            企业客户号
     * @return true则存在，false不存在
     */
    Boolean isExistApplyingEntChange(String entCustId);

    /**
     * 更新重要信息变更业务办理申请书扫描件
     * 
     * @param applyId
     *            申请编号
     * @param imptappPic
     *            业务办理申请书扫描件文件Id
     */
    void updateEntChangeImptappPic(@Param("applyId") String applyId, @Param("imptappPic") String imptappPic);

}
