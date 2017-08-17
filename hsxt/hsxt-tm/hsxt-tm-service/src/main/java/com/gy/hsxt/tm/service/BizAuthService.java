/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.tm.TMErrorCode;
import com.gy.hsxt.tm.api.ITMBizAuthService;
import com.gy.hsxt.tm.bean.BizType;
import com.gy.hsxt.tm.bean.BizTypeAuth;
import com.gy.hsxt.tm.interfaces.IOperatorService;
import com.gy.hsxt.tm.mapper.BizTypeAuthMapper;
import com.gy.hsxt.tm.mapper.BizTypeMapper;
import com.gy.hsxt.tm.mapper.OperatorMapper;
import com.gy.hsxt.uc.as.api.auth.IUCAsPermissionService;
import com.gy.hsxt.uc.as.bean.auth.AsPermission;

/**
 * 业务权限管理dubbo service实现类
 * 
 * @Package: com.gy.hsxt.tm.service
 * @ClassName: BizAuthService
 * @Description:
 * 
 * @author: kongsl
 * @date: 2015-11-12 下午5:50:23
 * @version V3.0.0
 */
@Service
public class BizAuthService implements ITMBizAuthService, IOperatorService {
    /**
     * 注入业务类型mapper
     */
    @Autowired
    BizTypeMapper bizTypeMapper;

    /**
     * 注入业务授权mapper
     */
    @Autowired
    BizTypeAuthMapper authMapper;

    /**
     * 注入值班员mapper
     */
    @Autowired
    OperatorMapper operatorMapper;

    /**
     * 权限服务
     */
    @Autowired
    IUCAsPermissionService ucPermissionService;

    /**
     * 保存业务类型
     * 
     * @param bizType
     *            业务类型信息
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMBizAuthService#saveBizType(com.gy.hsxt.tm.bean.BizType)
     */
    @Override
    public void saveBizType(BizType bizType) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "保存业务类型,params[" + bizType + "]");
        // 输入参数为空
        HsAssert.notNull(bizType, TMErrorCode.TM_PARAMS_NULL, "保存业务类型：输入参数为空");
        try
        {
            // 查询记录是否存在
            int resultNum = bizTypeMapper.findBizTypeExists(bizType.getBizType());
            // 业务类型已存在
            HsAssert.isTrue(resultNum <= 0, TMErrorCode.TM_BIZ_TYPE_EXISTS, "保存业务类型：业务类型已存在,param=[" + bizType + "]");
            // 新增业务类型
            bizTypeMapper.insertBizType(bizType);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_SAVE_BIZ_TYPE_ERROR
                    .getCode()
                    + ":保存业务类型时异常,params[" + bizType + "]", e);
            throw new HsException(TMErrorCode.TM_SAVE_BIZ_TYPE_ERROR, "保存业务类型时异常,params[" + bizType + "]" + e);
        }
    }

    /**
     * 保存授权记录
     * 
     * @param bizTypeAuth
     *            授权信息
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMBizAuthService#saveBizTypeAuth(com.gy.hsxt.tm.bean.BizTypeAuth)
     */
    @Override
    public void saveBizTypeAuth(BizTypeAuth bizTypeAuth) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "保存授权记录,params[" + bizTypeAuth + "]");
        // 校验输入参数不能为空
        HsAssert.notNull(bizTypeAuth, TMErrorCode.TM_PARAMS_NULL, "保存授权记录：授权信息参数为空");
        try
        {
            // 新增授权
            authMapper.insertBizTypeAuth(bizTypeAuth);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    TMErrorCode.TM_SAVE_BIZ_TYPE_AUTH_ERROR.getCode() + "保存授权记录异常,params[" + bizTypeAuth + "]", e);
            throw new HsException(TMErrorCode.TM_SAVE_BIZ_TYPE_AUTH_ERROR, "保存授权记录异常,params[" + bizTypeAuth + "]" + e);
        }
    }

    /**
     * 修改业务类型名称
     * 
     * @param bizType
     *            业务类型信息
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMBizAuthService#updateBizTypeName(com.gy.hsxt.tm.bean.BizType)
     */
    @Override
    public void updateBizTypeName(BizType bizType) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "修改业务类型名称,params[" + bizType + "]");
        // 校验输入参数不能为空
        HsAssert.notNull(bizType, TMErrorCode.TM_PARAMS_NULL, "修改业务类型名称：业务类型信息参数为空");
        try
        {
            // 修改业务类型
            bizTypeMapper.updateBizTypeName(bizType);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    TMErrorCode.TM_UPDATE_BIZ_TYPE_NAME_ERROR.getCode() + "修改业务类型名称异常,params[" + bizType + "]", e);
            throw new HsException(TMErrorCode.TM_UPDATE_BIZ_TYPE_NAME_ERROR, "修改业务类型名称异常,params[" + bizType + "]" + e);
        }
    }

    /**
     * 删除业务类型
     * 
     * @param bizTypeCode
     *            类型代码
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMBizAuthService#deleteBizType(java.lang.String)
     */
    @Override
    public void deleteBizType(String bizTypeCode) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "删除业务类型,params[bizTypeCode:" + bizTypeCode + "]");
        HsAssert.hasText(bizTypeCode, TMErrorCode.TM_PARAMS_NULL, "删除业务类型：类型代码参数为空");
        try
        {
            // 删除业务类型
            bizTypeMapper.deleteBizType(bizTypeCode);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_DELETE_BIZ_TYPE_ERROR
                    .getCode()
                    + "删除业务类型异常,params[bizTypeCode:" + bizTypeCode + "]", e);
            throw new HsException(TMErrorCode.TM_DELETE_BIZ_TYPE_ERROR, "删除业务类型异常,params[bizTypeCode:" + bizTypeCode
                    + "]" + e);
        }
    }

    /**
     * 删除值班员授权
     * 
     * @param bizTypeCode
     *            业务类型代码
     * @param optCustId
     *            值班员编号
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMBizAuthService#deleteOptCustAuth(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void deleteOptCustAuth(Set<String> bizTypeCode, String optCustId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "删除值班员授权,params[bizTypeCode:" + JSON.toJSONString(bizTypeCode) + ",optCustId:" + optCustId + "]");
        HsAssert.notNull(bizTypeCode, TMErrorCode.TM_PARAMS_NULL, "删除值班员授权：业务类型代码参数为空");
        HsAssert.hasText(optCustId, TMErrorCode.TM_PARAMS_NULL, "删除值班员授权：值班员编号参数为空");
        try
        {
            // 删除授权
            authMapper.deleteOptCustAuth(bizTypeCode, optCustId);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_DELETE_OPERATOR_AUTH
                    .getCode()
                    + "删除值班员授权异常,params[bizTypeCode:"
                    + JSON.toJSONString(bizTypeCode)
                    + ",optCustId:"
                    + optCustId
                    + "]", e);
            throw new HsException(TMErrorCode.TM_DELETE_OPERATOR_AUTH, "删除值班员授权异常,params[bizTypeCode:"
                    + JSON.toJSONString(bizTypeCode) + ",optCustId:" + optCustId + "]" + e);
        }
    }

    /**
     * 获取业务类型列表
     * 
     * @param optCustId
     *            值班员编号
     * @return 业务类型列表
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMBizAuthService#getBizTypeList()
     */
    @Override
    public List<BizType> getBizTypeList(String optCustId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取业务类型列表,params[optCustId:" + optCustId + "]");
        List<BizType> bizTypeList = null;
        try
        {
            // 获取值班员权限列表
            List<String> optPermIdList = getOptPermission(optCustId);
            if (optPermIdList != null && optPermIdList.size() > 0)
            {
                // 查询工单业务类型对应的权限菜单列表
                List<String> permIdList = bizTypeMapper.findPermIdList();
                if (permIdList != null && permIdList.size() > 0)
                {
                    // 获取List交集
                    if (optPermIdList.retainAll(permIdList))
                    {
                        // 去除List中的null
                        optPermIdList.removeAll(Collections.singleton(null));
                    }
                }
                if (optPermIdList.size() > 0)
                {
                    // 查询业务类型列表
                    bizTypeList = bizTypeMapper.findBizTypeList(optPermIdList);
                }
                else
                {
                    SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1]
                            .getMethodName(), "调用UC获取操作员权限列表为空,optPermIdList=" + optPermIdList.size());
                    bizTypeList = new ArrayList<BizType>();
                }
            }
            else
            {
                SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        "调用UC获取操作员权限列表为空,optPermIdList=" + optPermIdList.size());
                bizTypeList = new ArrayList<BizType>();
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    TMErrorCode.TM_QUERY_BIZ_TYPE_LIST_ERROR.getCode() + "查询业务类型列表异常", e);
            throw new HsException(TMErrorCode.TM_QUERY_BIZ_TYPE_LIST_ERROR, "查询业务类型列表异常");
        }
        return bizTypeList;
    }

    /**
     * 获取值班员授权列表
     * 
     * @param optCustId
     *            值班员编号
     * @return 值班员授权列表
     * @see com.gy.hsxt.tm.api.ITMBizAuthService#getOptCustAuthList(java.lang.String)
     */
    @Override
    public List<BizTypeAuth> getOptCustAuthList(String optCustId) {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取值班员授权列表,params[optCustId:" + optCustId + "]");
        HsAssert.hasText(optCustId, TMErrorCode.TM_PARAMS_NULL, "获取值班员授权列表：值班员编号参数为空");
        // 值班员授权列表
        List<BizTypeAuth> optCustAuthList = null;
        try
        {
            // 查询值班员授权
            optCustAuthList = authMapper.findOptCustAuthList(optCustId);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_GET_OPT_AUTH_LIST_ERROR
                    .getCode()
                    + "获取值班员授权列表异常,params[optCustId:" + optCustId + "]", e);
            throw new HsException(TMErrorCode.TM_GET_OPT_AUTH_LIST_ERROR, "获取值班员授权列表异常,params[optCustId:" + optCustId
                    + "]" + e);
        }
        return optCustAuthList;
    }

    /**
     * 查询企业业务类型列表
     * 
     * @param entCustType
     *            企业类型
     * @return 业务类型列表
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMBizAuthService#getBizAuthList(java.lang.Integer)
     */
    @Override
    public List<BizType> getBizAuthList(Integer entCustType) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询企业业务类型列表,params[entCustType:" + entCustType + "]");
        HsAssert.notNull(entCustType, TMErrorCode.TM_PARAMS_NULL, "查询企业业务类型列表：企业类型参数为空");
        try
        {
            // 查询企业业务类型
            return bizTypeMapper.findListByCustType(entCustType);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    TMErrorCode.TM_GET_ENT_BIZ_TYPE_LIST_ERROR.getCode() + "查询企业业务类型列表异常,params[entCustType:"
                            + entCustType + "]", e);
            throw new HsException(TMErrorCode.TM_GET_ENT_BIZ_TYPE_LIST_ERROR, "查询企业业务类型列表异常,params[entCustType:"
                    + entCustType + "]" + e);
        }
    }

    /**
     * 获取值班员权限ID列表
     * 
     * @param optCustId
     *            值班员编号
     * @return 权限ID列表
     * @see com.gy.hsxt.tm.interfaces.IOperatorService#getOptPermission(java.lang.String)
     */
    public List<String> getOptPermission(String optCustId) {
        List<String> permIdList = null;
        try
        {
            String ruleType = "";
            String mcRegexp = "^([0-9]{2}0{9})$";
            Pattern pattern = Pattern.compile(mcRegexp);
            if (pattern.matcher(optCustId.subSequence(0, 11)).matches())
            {
                ruleType = "MCS";
            }
            else
            {
                ruleType = "APS";
            }
            List<AsPermission> permissionList = ucPermissionService.listPermByCustId(null, ruleType, null, null,
                    optCustId, null);
            if (permissionList != null && permissionList.size() > 0)
            {
                permIdList = new ArrayList<String>();
                for (AsPermission asPermission : permissionList)
                {
                    permIdList.add(asPermission.getPermId());
                }
            }
            else
            {
                permIdList = new ArrayList<String>();
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw e;
        }
        return permIdList;
    }

    /**
     * 查询是否值班主任
     * 
     * @param optCustId
     *            操作员编号
     * @return true:是 false:否
     * @throws HsException
     * @see com.gy.hsxt.tm.interfaces.IOperatorService#isChief(java.lang.String)
     */
    @Override
    public boolean isChief(String optCustId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询是否值班主任,params[optCustId:" + optCustId + "]");
        HsAssert.hasText(optCustId, TMErrorCode.TM_PARAMS_NULL, "查询是否值班主任：操作员编号参数为空");
        int resNum = operatorMapper.findOperatorIsChief(optCustId);
        return resNum >= 1 ? true : false;
    }
}
