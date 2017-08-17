/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.tm.bean.BizTypeAuth;

/**
 * 业务办理授权mapper dao映射
 * 
 * @Package: com.gy.hsxt.tm.mapper
 * @ClassName: BizTypeAuth
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-12 下午4:01:50
 * @version V3.0.0
 */
public interface BizTypeAuthMapper {

    /**
     * 新增值班员授权记录
     * 
     * @param bizTypeAuth
     *            授权信息
     * @return 成功记录数
     */
    public int insertBizTypeAuth(BizTypeAuth bizTypeAuth);

    /**
     * 删除值班员授权
     * 
     * @param bizTypeCode
     *            业务类型代码
     * @param optCustId
     *            值班员编号
     * @return 成功记录数
     */
    public int deleteOptCustAuth(@Param("bizTypeCodes") Set<String> bizTypeCodes, @Param("optCustId") String optCustId);

    /**
     * 删除值班员授权
     * 
     * @param bizTypeCode
     *            业务类型代码
     * @return 成功记录数
     */
    public int deleteOptAuthList(@Param("optCustIds") List<String> optCustIds);

    /**
     * 删除值班员授权
     * 
     * @param optCustId
     *            操作员编号
     * @return 成功记录数
     */
    public int deleteAuth(@Param("optCustId") String optCustId);

    /**
     * 删除值班员授权
     * 
     * @param groupId
     *            值班组编号
     * @param optCustId
     *            值班员编号
     * @return 成功记录数
     */
    public int deleteOptAuth(@Param("groupId") String groupId, @Param("optCustId") String optCustId);

    /**
     * 查询值班员授权列表
     * 
     * @param optCustId
     *            值班员编号
     * @return 值班员授权列表
     */
    public List<BizTypeAuth> findOptCustAuthList(@Param("optCustId") String optCustId);

    /**
     * 查询值班员是否已授权
     * 
     * @param bizType
     *            业务编号
     * @param optCustId
     *            值班员编号
     * @return 授权信息
     */
    public BizTypeAuth findOptCustAuth(@Param("bizType") String bizType, @Param("optCustId") String optCustId);
}
