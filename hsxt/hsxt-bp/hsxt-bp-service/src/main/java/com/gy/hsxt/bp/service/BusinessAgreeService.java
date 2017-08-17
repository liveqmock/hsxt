/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.bp.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.bp.api.IBusinessAgreeService;
import com.gy.hsxt.bp.bean.BusinessAgree;
import com.gy.hsxt.bp.bean.BusinessAgreeFee;
import com.gy.hsxt.bp.bean.BusinessAgreeFeeRedis;
import com.gy.hsxt.bp.common.bean.BPConstants;
import com.gy.hsxt.bp.common.bean.PageContext;
import com.gy.hsxt.bp.mapper.BusinessAgreeMapper;
import com.gy.hsxt.bp.mapper.BusinessParamSearchMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BeanCopierUtils;

/**
 * 协议参数管理业务实现类
 * 
 * @Package: com.gy.hsxt.bp.service
 * @ClassName: BusinessAgreeService
 * @Description: TODO
 * 
 * @author: weixz
 * @date: 2015-11-24 下午3:35:26
 * @version V1.0
 */

@Service
public class BusinessAgreeService implements IBusinessAgreeService {

    // 协议参数管理Mapper
    @Autowired
    private BusinessAgreeMapper businessAgreeMapper;

    @Resource(name="fixRedisUtil")
    RedisUtil<String> fixRedisUtil;

    // 参数获取服务业务Mapper
    @Autowired
    private BusinessParamSearchMapper businessParamSearchMapper;

    /**
     * 新增协议参数管理
     * 
     * @param BusinessAgree
     *            协议参数管理对象
     * @throws HsException
     */
    @Override
    public void addAgree(BusinessAgree businessAgree) throws HsException {

        // 协议代码
        String agreeCode = businessAgree.getAgreeCode();
        if (agreeCode == null || "".equals(agreeCode))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgree", RespCode.BP_PARAMETER_NULL.getCode() + "agreeCode = " + agreeCode
                    + ":协议代码为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeCode = " + agreeCode + ":协议代码为空");
        }

        // 协议名称
        String agreeName = businessAgree.getAgreeName();
        if (agreeName == null || "".equals(agreeName))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgree", RespCode.BP_PARAMETER_NULL.getCode() + "agreeName = " + agreeName
                    + ":协议名称为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeName = " + agreeName + ":协议名称为空");
        }

        // 创建者
        String createdby = businessAgree.getCreatedby();
        if (createdby == null || "".equals(createdby))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgree", RespCode.BP_PARAMETER_NULL.getCode() + "createdby = " + createdby
                    + ":创建者参数为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "createdby = " + createdby + ":创建者参数为空");
        }

        // 激活状态:Y：是；N：否
        String isActive = businessAgree.getIsActive();
        if (isActive == null || "".equals(isActive))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgree", RespCode.BP_PARAMETER_NULL.getCode() + "isActive = " + isActive
                    + ":激活状态为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "isActive = " + isActive + ":激活状态为空");
        }
        else if (!BPConstants.IS_ACTIVE_YES.equals(isActive) && !BPConstants.IS_ACTIVE_NOT.equals(isActive))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgree", RespCode.BP_PARAMETER_FORMAT_ERROR.getCode() + "isActive = "
                    + isActive + ":激活状态只能用（Y或N）表示");
            throw new HsException(RespCode.BP_PARAMETER_FORMAT_ERROR.getCode(), "isActive = " + isActive
                    + ":激活状态只能用（Y或N）表示");

        }

        try
        {
            // 判断该协议编码对应的数据是否在数据库中已经存在
            BusinessAgree busAgree = businessAgreeMapper.getAgreeByCode(agreeCode);
            if (busAgree != null)
            {
                SystemLog.debug("HSXT_BP", "方法：addAgree", RespCode.BP_PARAMETER_NULL.getCode() + "agreeCode = "
                        + agreeCode + ":该协议编码对应的数据在数据库中已经存在");
                throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeCode = " + agreeCode
                        + ":该协议编码对应的数据在数据库中已经存在");
            }

            // 将该协议参数管理对象插入到数据库中
            businessAgreeMapper.addAgree(businessAgree);

        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：addAgree", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }

    }

    /**
     * 更新协议参数管理
     * 
     * @param BusinessAgree
     *            协议参数管理对象
     * @throws HsException
     */
    @Override
    public void updateAgree(BusinessAgree businessAgree) throws HsException {

        // 协议ID
        String agreeId = businessAgree.getAgreeId();
        if (agreeId == null || "".equals(agreeId))
        {
            SystemLog.debug("HSXT_BP", "方法：updateAgree", RespCode.BP_PARAMETER_NULL.getCode() + "agreeId = " + agreeId
                    + ":协议ID为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeId = " + agreeId + ":协议ID为空");
        }
        try
        {
            // 根据协议参数对象中的条件更新数据中对应的额数据信息
            businessAgreeMapper.updateAgree(businessAgree);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：updateAgree", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }

    }

    /**
     * 删除协议参数管理
     * 
     * @param agreeId
     *            协议ID
     * @throws HsException
     */
    @Override
    public void deleteAgree(String agreeId) throws HsException {

        // 协议ID
        if (agreeId == null || "".equals(agreeId))
        {
            SystemLog.debug("HSXT_BP", "方法：deleteAgree", RespCode.BP_PARAMETER_NULL.getCode() + "agreeId = " + agreeId
                    + ":协议ID为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeId = " + agreeId + ":协议ID为空");
        }

        try
        {
            // 根据协议ID去删除数据库中对应的数据
            businessAgreeMapper.deleteAgree(agreeId);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：deleteAgree", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }

    }

    /**
     * 查询单个协议参数管理
     * 
     * @param agreeId
     *            协议ID
     * @throws HsException
     */
    @Override
    public BusinessAgree getAgreeById(String agreeId) throws HsException {

        // 协议ID
        if (agreeId == null || "".equals(agreeId))
        {
            SystemLog.debug("HSXT_BP", "方法：getAgreeById", RespCode.BP_PARAMETER_NULL.getCode() + "agreeId = " + agreeId
                    + ":协议ID为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeId = " + agreeId + ":协议ID为空");
        }
        // 协议参数对象
        BusinessAgree businessAgree = null;
        try
        {
            // 根据该协议ID查询对应数据库中的数据
            businessAgree = (BusinessAgree) businessAgreeMapper.getAgreeById(agreeId);

        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：getAgreeById", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
        return businessAgree;
    }

    /**
     * 查询多个协议参数管理
     * 
     * @param BusinessAgree
     *            协议参数管理对象
     * @param page
     *            分頁對象
     * @return PageData<AgreeParamManager> 封裝协议参数管理对象集合、當前條件的查詢總記錄數
     * @throws HsException
     */
    @Override
    public PageData<BusinessAgree> searchAgreePage(BusinessAgree businessAgree, Page page) throws HsException {

        // 分页信息设值
        PageContext.setPage(page);
        PageData<BusinessAgree> pageDate = null;// 公用分页查询返回参数类

        try
        {
            // 根据协议对象中的条件查询数据库中对应的数据信息
            List<BusinessAgree> businessAgreeList = businessAgreeMapper.searchAgreeListPage(businessAgree);

            // 封裝协议参数管理对象集合、當前條件的查詢總記錄數
            pageDate = new PageData<BusinessAgree>(page.getCount(), businessAgreeList);
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：searchAgreePage", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
        return pageDate;
    }

    /**
     * 新增协议费用参数管理
     * 
     * @param BusinessAgreeFee
     *            协议费用参数管理对象
     * @throws HsException
     */
    @Override
    public void addAgreeFee(BusinessAgreeFee businessAgreeFee) throws HsException {

        // 协议费用编号
        String agreeFeeCode = businessAgreeFee.getAgreeFeeCode();
        if (agreeFeeCode == null || "".equals(agreeFeeCode))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "agreeFeeCode = "
                    + agreeFeeCode + ":协议费用编号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeFeeCode = " + agreeFeeCode + ":协议费用编号为空");
        }

        // 协议代码
        String agreeCode = businessAgreeFee.getAgreeCode();
        if (agreeCode == null || "".equals(agreeCode))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "agreeCode = "
                    + agreeCode + ":协议代码为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeCode = " + agreeCode + ":协议代码为空");
        }

        // 协议费用名称
        String agreeFeeName = businessAgreeFee.getAgreeFeeName();
        if (agreeFeeName == null || "".equals(agreeFeeName))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "agreeFeeName = "
                    + agreeFeeName + ":协议费用名称为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeFeeName = " + agreeFeeName + ":协议费用名称为空");
        }

        // 计费类型：0：无TIMES:次DAILY：天WEEKLY：周MONTHLY：月ANNUAL：年
        String billingType = businessAgreeFee.getBillingType();
        if (billingType == null || "".equals(billingType))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "billingType = "
                    + billingType + ":计费类型为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "billingType = " + billingType + ":计费类型为空");
        }

        // 扣费类型：0：无TIMES:次DAILY：天WEEKLY：周MONTHLY：月ANNUAL：年
        String chargingType = businessAgreeFee.getChargingType();
        if (chargingType == null || "".equals(chargingType))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "chargingType = "
                    + chargingType + ":扣费类型为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "chargingType = " + chargingType + ":扣费类型为空");
        }

        // 资费类型：FIXED：固定金额资费RATIO：资费比例
        String feeType = businessAgreeFee.getFeeType();
        if (feeType == null || "".equals(feeType))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "feeType = " + feeType
                    + ":资费类型为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "feeType = " + feeType + ":资费类型为空");
        }

        // 货币代码:币种 (000：互生币,001：积分,156：人民币)
        String currencyCode = businessAgreeFee.getCurrencyCode();
        if (currencyCode == null || "".equals(currencyCode))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "currencyCode = "
                    + currencyCode + ":货币代码为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "currencyCode = " + currencyCode + ":货币代码为空");
        }
        // 判断货币代码格式是否正确
        else if (!currencyCode.equals(BPConstants.CURRENCY_CODE[0]) && !currencyCode.equals(BPConstants.CURRENCY_CODE[1])
                && !currencyCode.equals(BPConstants.CURRENCY_CODE[2]))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "currencyCode = "
                    + currencyCode + ":货币代码格式错误");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "currencyCode = " + currencyCode + ":货币代码格式错误");
        }

        // 创建者
        String createdby = businessAgreeFee.getCreatedby();
        if (createdby == null || "".equals(createdby))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "createdby = "
                    + createdby + ":创建者参数为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "createdby = " + createdby + ":创建者参数为空");
        }

        // 激活状态:Y：是；N：否
        String isActive = businessAgreeFee.getIsActive();
        if (isActive == null || "".equals(isActive))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "isActive = "
                    + isActive + ":激活状态为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "isActive = " + isActive + ":激活状态为空");
        }
        else if (!BPConstants.IS_ACTIVE_YES.equals(isActive) && !BPConstants.IS_ACTIVE_NOT.equals(isActive))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgreeFee", RespCode.BP_PARAMETER_FORMAT_ERROR.getCode() + "isActive = "
                    + isActive + ":激活状态只能用（Y或N）表示");
            throw new HsException(RespCode.BP_PARAMETER_FORMAT_ERROR.getCode(), "isActive = " + isActive
                    + ":激活状态只能用（Y或N）表示");

        }

        try
        {

            // 检查该协议编码对应的数据库中是否已经存在
            BusinessAgree busAgree = businessAgreeMapper.getAgreeByCode(agreeCode);
            if (busAgree == null)
            {
                SystemLog.debug("HSXT_BP", "方法：addAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "agreeCode = "
                        + agreeCode + ":该协议编码对应的数据库中不存在");
                throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeCode = " + agreeCode
                        + ":该协议编码对应的数据库中不存在");
            }

            // 检查该协议费用编码对应的数据库中是否已经存在
            BusinessAgreeFee busAgreeFee = businessAgreeMapper.getAgreeFeeByCode(agreeFeeCode);
            if (busAgreeFee != null)
            {
                SystemLog.debug("HSXT_BP", "方法：addAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "agreeFeeCode = "
                        + agreeFeeCode + ":该协议费用ID对应的数据库中已经存在");
                throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeFeeCode = " + agreeFeeCode
                        + ":该协议费用ID对应的数据库中已经存在");
            }

            // 将该协议费用对象插入到数据库中
            businessAgreeMapper.addAgreeFee(businessAgreeFee);
            BusinessParamSearchService businessParamSearchService = new BusinessParamSearchService(fixRedisUtil,
                    businessParamSearchMapper);
            // 将该协议对象添加到Redis服务器中
            businessParamSearchService.searchBusinessAgreeFeeByCode(agreeCode, agreeFeeCode);

        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：addAgreeFee", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 更新协议费用参数管理
     * 
     * @param BusinessAgreeFee
     *            协议费用参数管理对象
     * @throws HsException
     */
    @Override
    public void updateAgreeFee(BusinessAgreeFee businessAgreeFee) throws HsException {

        // 协议费用ID
        String agreeFeeId = businessAgreeFee.getAgreeFeeId();
        if (agreeFeeId == null || "".equals(agreeFeeId))
        {
            SystemLog.debug("HSXT_BP", "方法：updateAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "agreeFeeId = "
                    + agreeFeeId + ":协议费用ID为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeFeeId = " + agreeFeeId + ":协议费用ID为空");
        }

        // 协议费用编号
        String agreeFeeCode = businessAgreeFee.getAgreeFeeCode();
        if (agreeFeeCode == null || "".equals(agreeFeeCode))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "agreeFeeCode = "
                    + agreeFeeCode + ":协议费用编号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeFeeCode = " + agreeFeeCode + ":协议费用编号为空");
        }

        // 协议代码
        String agreeCode = businessAgreeFee.getAgreeCode();
        if (agreeCode == null || "".equals(agreeCode))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "agreeCode = "
                    + agreeCode + ":协议代码为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeCode = " + agreeCode + ":协议代码为空");
        }

        try
        {
            // 根据协议费用对象中的条件去更新数据库中对应的数据信息
            businessAgreeMapper.updateAgreeFee(businessAgreeFee);

            // 将该更新的协议对象更新到Redis服务器中
            BusinessParamSearchService businessParamSearchService = new BusinessParamSearchService(fixRedisUtil,
                    businessParamSearchMapper);
            Map<String, BusinessAgreeFeeRedis> businessAgreeFeeMap = businessParamSearchService
                    .searchBusinessAgreeFee(agreeCode);
            BusinessAgreeFeeRedis agreeFeeRedis = new BusinessAgreeFeeRedis();
            BeanCopierUtils.copy(businessAgreeFee, agreeFeeRedis);
            businessAgreeFeeMap.put(agreeFeeCode, agreeFeeRedis);
            // 把Map装换为String
            String objectJson = JSON.toJSONString(businessAgreeFeeMap);
            fixRedisUtil.add(BPConstants.SYS_NAME, agreeCode, objectJson);

        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：updateAgreeFee", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }

    }

    /**
     * 删除协议费用参数管理
     * 
     * @param agreeFeeId
     *            协议费用ID
     * @param agreeCode
     *            协议编号
     * @param agreeFeeCode
     *            协议费用编号
     * @throws HsException
     */
    @Override
    public void deleteAgreeFee(String agreeFeeId, String agreeCode, String agreeFeeCode) throws HsException {

        // 协议费用ID
        if (agreeFeeId == null || "".equals(agreeFeeId))
        {
            SystemLog.debug("HSXT_BP", "方法：deleteAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "agreeFeeId = "
                    + agreeFeeId + ":协议费用ID为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeFeeId = " + agreeFeeId + ":协议费用ID为空");
        }

        // 协议费用编号
        if (agreeFeeCode == null || "".equals(agreeFeeCode))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "agreeFeeCode = "
                    + agreeFeeCode + ":协议费用编号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeFeeCode = " + agreeFeeCode + ":协议费用编号为空");
        }

        // 协议代码
        if (agreeCode == null || "".equals(agreeCode))
        {
            SystemLog.debug("HSXT_BP", "方法：addAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "agreeCode = "
                    + agreeCode + ":协议代码为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeCode = " + agreeCode + ":协议代码为空");
        }
        try
        {
            // 根据该协议费用ID删除对应数据库中的数据
            businessAgreeMapper.deleteAgreeFee(agreeFeeId);
            // 将该删除的协议对象同时删除Redis服务器对应的对象
            BusinessParamSearchService businessParamSearchService = new BusinessParamSearchService(fixRedisUtil,
                    businessParamSearchMapper);
            Map<String, BusinessAgreeFeeRedis> businessAgreeFeeMap = businessParamSearchService
                    .searchBusinessAgreeFee(agreeCode);
            businessAgreeFeeMap.remove(agreeFeeCode);
            // 把Map装换为String
            String objectJson = JSON.toJSONString(businessAgreeFeeMap);
            fixRedisUtil.add(BPConstants.SYS_NAME, agreeCode, objectJson);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：deleteAgreeFee", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }

    }

    /**
     * 查询单个协议费用参数管理
     * 
     * @param agreeFeeId
     *            协议费用ID
     * @throws HsException
     */
    @Override
    public BusinessAgreeFee getAgreeFeeById(String agreeFeeId) throws HsException {

        // 协议费用ID
        if (agreeFeeId == null || "".equals(agreeFeeId))
        {
            SystemLog.debug("HSXT_BP", "方法：deleteAgreeFee", RespCode.BP_PARAMETER_NULL.getCode() + "agreeFeeId = "
                    + agreeFeeId + ":协议费用ID为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeFeeId = " + agreeFeeId + ":协议费用ID为空");
        }
        // 协议费用参数管理对象初始化
        BusinessAgreeFee businessAgreeFee = null;
        try
        {
            // 根据该协议费用ID查询对应的数据库信息
            businessAgreeFee = (BusinessAgreeFee) businessAgreeMapper.getAgreeFeeById(agreeFeeId);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：getAgreeFeeById", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
        return businessAgreeFee;
    }

    /**
     * 查询多个协议费用参数管理
     * 
     * @param BusinessAgreeFee
     *            协议费用参数管理对象
     * @param page
     *            分頁對象
     * @return PageData<AgreeFeeParamManager> 封裝协议费用参数管理对象集合、當前條件的查詢總記錄數
     * @throws HsException
     */
    @Override
    public PageData<BusinessAgreeFee> searchAgreeFeePage(BusinessAgreeFee businessAgreeFee, Page page)
            throws HsException {

        // 分页信息设值
        PageContext.setPage(page);
        PageData<BusinessAgreeFee> pageDate = null;// 公用分页查询返回参数类

        try
        {
            // 根据协议费用对象中的条件查询数据库中对应的数据信息
            List<BusinessAgreeFee> businessAgreeFeeList = businessAgreeMapper.searchAgreeFeeListPage(businessAgreeFee);

            // 封裝协议费用参数管理对象集合、當前條件的查詢總記錄數
            pageDate = new PageData<BusinessAgreeFee>(page.getCount(), businessAgreeFeeList);
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：searchAgreeFeePage", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
        return pageDate;
    }

    /**
     * 查询所有协议参数管理
     * @return List<AgreeParamManager> 协议参数管理对象集合
     * @throws HsException 
     * @see com.gy.hsxt.bp.api.IBusinessAgreeService#searchAgreeList()
     */
    @Override
    public List<BusinessAgree> searchAgreeList() throws HsException {
        List<BusinessAgree> list = null;
        try
        {
            list = businessAgreeMapper.searchAgreeList();
        }
        catch (SQLException e)
        {
            SystemLog.debug("HSXT_BP", "方法：searchAgreeList", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
        return list;
    }

    /**
     * 通过协议编码查询协议费用参数管理
     * @param agreeCode 协议编码
     * @returnList<BusinessAgreeFee> 协议费用参数管理对象集合
     * @throws HsException 
     * @see com.gy.hsxt.bp.api.IBusinessAgreeService#searchAgreeFeeList(java.lang.String)
     */
    @Override
    public List<BusinessAgreeFee> searchAgreeFeeList(String agreeCode) throws HsException {
       
        List<BusinessAgreeFee> list = null;
        try
        {
            list = businessAgreeMapper.searchAgreeFeeList(agreeCode);
        }
        catch (SQLException e)
        {
            SystemLog.debug("HSXT_BP", "方法：searchAgreeFeeList", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
        return list;
    }

}
