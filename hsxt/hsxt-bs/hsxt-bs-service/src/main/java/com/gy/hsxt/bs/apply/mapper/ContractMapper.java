/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.mapper;

import com.gy.hsxt.bs.apply.bean.ContractContentInfo;
import com.gy.hsxt.bs.bean.apply.Contract;
import com.gy.hsxt.bs.bean.apply.ContractBaseInfo;
import com.gy.hsxt.bs.bean.apply.ContractMainInfo;
import com.gy.hsxt.bs.bean.apply.ContractSendHis;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Package: com.gy.hsxt.bs.apply.mapper
 * @ClassName: ContractMapper
 * @Description: 合同Mapper
 * @author: xiaofl
 * @date: 2015-12-14 下午8:19:44
 */
@Repository
public interface ContractMapper {

    /**
     * 查询合同列表
     *
     * @param manResNoPre 管理公司互生号前两位
     * @param serResNoPre 服务公司互生号前五位
     * @param resNo       企业互生号
     * @param entName     企业名称
     * @param linkman     联系人
     * @param status      状态
     * @param sendStatus  发放状态
     * @param inStatus    包含状态
     * @return 合同列表
     */
    List<ContractBaseInfo> queryContractListPage(@Param("manResNoPre") String manResNoPre,
                                                 @Param("serResNoPre") String serResNoPre, @Param("resNo") String resNo, @Param("entName") String entName,
                                                 @Param("linkman") String linkman, @Param("status") Integer status, @Param("sendStatus") Boolean sendStatus,
                                                 @Param("inStatus") String inStatus);

    /**
     * 查询合同基本信息
     *
     * @param contractNo 合同ID
     * @return 合同基本信息
     */
    ContractBaseInfo queryContractBaseInfoById(String contractNo);

    /**
     * 查询合同内容
     *
     * @param contractNo 合同ID
     * @return 合同内容
     */
    ContractContentInfo queryContractContent(@Param("contractNo") String contractNo);

    /**
     * 更新打印次数
     *
     * @param contractNo 合同ID
     */
    void updatePrintCount(String contractNo);

    /**
     * 更新盖章状态
     *
     * @param contractNo 合同ID
     * @param status     盖章状态
     */
    void updateSealStatus(@Param("contractNo") String contractNo, @Param("varContent") String varContent,@Param("status") Integer status);

    /**
     * 更新发放状态
     *
     * @param contractNo 合同ID
     */
    void updateSendStatus(String contractNo);

    /**
     * 创建发放历史
     *
     * @param contractSendHis 发放历史
     */
    void createSendHis(ContractSendHis contractSendHis);

    /**
     * 查询发放历史
     *
     * @param contractNo 合同ID
     * @return 发放历史
     */
    List<ContractSendHis> querySendHisListPage(String contractNo);

    /**
     * 创建合同
     *
     * @param contract 合同
     */
    void createContract(Contract contract);

    /**
     * 通过ID查询合同
     *
     * @param contractNo 合同ID
     * @return 合同
     */
    Contract queryContractById(String contractNo);

    /**
     * 查询合同主要信息
     *
     * @param contractNo 合同唯一识别码
     * @param entResNo   合同编号
     * @return 合同主要信息
     */
    ContractMainInfo queryContractMainInfo(@Param("contractNo") String contractNo, @Param("entResNo") String entResNo);

    /**
     * 查询当前有效的合同
     *
     * @param entCustId
     * @return
     */
    Contract queryEffectContractByCustId(@Param("entCustId") String entCustId);

    /**
     * 更新生效合同的状态
     *
     * @param contract    合同信息
     */
    void updateContractForChangeInfo(Contract contract);
}
