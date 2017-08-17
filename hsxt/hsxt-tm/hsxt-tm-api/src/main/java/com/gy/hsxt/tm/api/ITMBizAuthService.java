/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.api;

import java.util.List;
import java.util.Set;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.bean.BizType;
import com.gy.hsxt.tm.bean.BizTypeAuth;

/**
 * 业务权限管理接口
 * 
 * @Package: com.gy.hsxt.tm.api
 * @ClassName: ITMBizAuthService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-12 下午5:24:28
 * @version V3.0.0
 */
public interface ITMBizAuthService {

    /**
     * 保存业务类型
     * 
     * @param bizType
     *            业务类型信息
     */
    public void saveBizType(BizType bizType) throws HsException;

    /**
     * 修改业务类型名称
     * 
     * @param bizType
     *            业务类型信息
     */
    public void updateBizTypeName(BizType bizType) throws HsException;

    /**
     * 删除业务类型
     * 
     * @param bizTypeCode
     *            类型代码
     */
    public void deleteBizType(String bizTypeCode) throws HsException;

    /**
     * 获取业务类型列表
     * 
     * @param optCustId
     *            值班员编号
     * @return 业务类型列表
     * @throws HsException
     */
    public List<BizType> getBizTypeList(String optCustId) throws HsException;

    /**
     * 保存授权记录
     * 
     * @param bizTypeAuth
     *            授权信息
     */
    public void saveBizTypeAuth(BizTypeAuth bizTypeAuth) throws HsException;

    /**
     * 删除值班员授权
     * 
     * @param bizTypeCode
     *            业务类型代码集合
     * @param optCustId
     *            值班员编号
     */
    public void deleteOptCustAuth(Set<String> bizTypeCode, String optCustId) throws HsException;

    /**
     * 获取值班员授权列表
     * 
     * @param optCustId
     *            值班员编号
     * @return 值班员授权列表
     */
    public List<BizTypeAuth> getOptCustAuthList(String optCustId) throws HsException;

    /**
     * 查询企业业务类型列表
     * 
     * @param entCustType
     *            企业类型
     * @return 业务类型列表
     * @throws HsException
     */
    public List<BizType> getBizAuthList(Integer entCustType) throws HsException;

}
