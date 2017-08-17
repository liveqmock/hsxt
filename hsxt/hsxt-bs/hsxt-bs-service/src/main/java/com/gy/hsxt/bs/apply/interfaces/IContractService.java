/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.interfaces;

import com.gy.hsxt.bs.bean.apply.Contract;
import com.gy.hsxt.bs.bean.apply.ContractContent;
import com.gy.hsxt.bs.bean.apply.DeclareInfo;
import com.gy.hsxt.bs.bean.apply.DeclareLinkman;
import com.gy.hsxt.bs.bean.apply.VarContent;
import com.gy.hsxt.bs.bean.entstatus.EntChangeInfo;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @Package: com.gy.hsxt.bs.apply.interfaces
 * @ClassName: IContractService
 * @Description: 合同接口
 * 
 * @author: xiaofl
 * @date: 2015-12-14 下午4:52:42
 * @version V1.0
 */
public interface IContractService {

    /**
     * 生成合同
     * 
     * @param contract
     *            合同内容
     * @param resType
     *            启用资源类型
     * @throws HsException
     */
    public void genContract(Contract contract, VarContent varContect, Integer resType) throws HsException;

    /**
     * 查询合同内容
     * 
     * @param contractNo
     *            合同唯一识别码
     * @param entResNo
     *            合同编号
     * @return 合同内容
     */
    public ContractContent queryContractContent(String contractNo, String entResNo);

    /**
     * 重要信息变更后修改合同
     * @param entChangeInfo 重要信息
     * @throws HsException
     */
    void updateContractForChangeInfo(EntChangeInfo entChangeInfo) throws HsException;

    /**
     * 生成合同占位符替换内容
     * @param declareInfo 申报信息
     * @param declareLinkman 企业联系人信息
     * @return 占位符替换内容对象
     */
    VarContent buildVarContent(DeclareInfo declareInfo, DeclareLinkman declareLinkman);
}
