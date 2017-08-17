/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.interfaces;

import com.gy.hsxt.bs.bean.apply.CerVarContent;
import com.gy.hsxt.bs.bean.apply.Certificate;
import com.gy.hsxt.bs.bean.apply.DeclareInfo;
import com.gy.hsxt.bs.bean.entstatus.EntChangeInfo;
import com.gy.hsxt.common.exception.HsException;

/**
 * 证书接口
 * 
 * @Package : com.gy.hsxt.bs.apply.interfaces
 * @ClassName : ICertificateService
 * @Description : 证书接口
 * @Author : xiaofl
 * @Author : 2015-12-14 下午4:52:29
 * @Version V1.0
 */
public interface ICertificateService {

    /**
     * 生成证书
     * 
     * @param certificate
     *            证书内容
     * @param cerVarContent
     *            证书占位符替换内容
     * @param declareInfo  申报信息
     * @throws HsException
     */
    void buildCertificate(Certificate certificate, CerVarContent cerVarContent,DeclareInfo declareInfo)
            throws HsException;

    /**
     * 重要信息变更之后修改证书记录
     *
     * @param entChangeInfo 证书
     * @throws HsException
     */
    void updateCertificateForChangeInfo(EntChangeInfo entChangeInfo) throws HsException;
}
