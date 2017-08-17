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
import com.gy.hsxt.bp.api.IBusinessSysParamService;
import com.gy.hsxt.bp.bean.BusinessSysParamGroup;
import com.gy.hsxt.bp.bean.BusinessSysParamItems;
import com.gy.hsxt.bp.bean.BusinessSysParamItemsRedis;
import com.gy.hsxt.bp.common.bean.BPConstants;
import com.gy.hsxt.bp.common.bean.PageContext;
import com.gy.hsxt.bp.mapper.BusinessParamSearchMapper;
import com.gy.hsxt.bp.mapper.BusinessSysParamMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.uc.as.api.util.CommonUtil;
import com.gy.hsxt.uc.as.bean.common.AsPosBaseInfo;

/**
 * 系统业务参数业务实现类
 * 
 * @Package: com.gy.hsxt.bp.service
 * @ClassName: SysParamService
 * @Description: TODO
 * 
 * @author: weixz
 * @date: 2015-11-20 上午11:46:34
 * @version V1.0
 */
@Service
public class BusinessSysParamService implements IBusinessSysParamService {

    /**
     * 系统业务参数操作Mapper
     */
    @Autowired
    private BusinessSysParamMapper businessSysParamMapper;

    @Autowired
    private CommonUtil commonUtil;
    
    @Resource(name="fixRedisUtil")
    RedisUtil<String> fixRedisUtil;

    // 参数获取服务业务Mapper
    @Autowired
    private BusinessParamSearchMapper businessParamSearchMapper;

    /**
     * 新增系统业务参数组
     * 
     * @param sysParamGroup
     *            系统业务参数组对象
     * @throws HsException
     */
    @Override
    public void addSysParamGroup(BusinessSysParamGroup sysParamGroup) throws HsException {

        // 系统参数组编号
        String sysGroupCode = sysParamGroup.getSysGroupCode();
        if (sysGroupCode == null || "".equals(sysGroupCode))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamGroup", RespCode.BP_PARAMETER_NULL.getCode() + "sysGroupCode = "
                    + sysGroupCode + ":系统参数组编号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysGroupCode = " + sysGroupCode + ":系统参数组编号为空");
        }

        // 系统参数组编名称
        String sysGroupName = sysParamGroup.getSysGroupName();
        if (sysGroupName == null || "".equals(sysGroupName))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamGroup", RespCode.BP_PARAMETER_NULL.getCode() + "sysGroupName = "
                    + sysGroupName + ":系统参数组编名称为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysGroupName = " + sysGroupName
                    + ":系统参数组编名称为空");
        }

        // 创建者
        String createdby = sysParamGroup.getCreatedby();
        if (createdby == null || "".equals(createdby))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamGroup", RespCode.BP_PARAMETER_NULL.getCode() + "createdby = "
                    + createdby + ":创建者参数为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "createdby = " + createdby + ":创建者参数为空");
        }

        // 是否公共，Y：是；N：否
        String isPublic = sysParamGroup.getIsPublic();
        if (sysGroupName == null || "".equals(sysGroupName))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamGroup", RespCode.BP_PARAMETER_NULL.getCode() + "sysGroupName = "
                    + sysGroupName + ":是否公共参数为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysGroupName = " + sysGroupName + ":是否公共参数为空");
        }
        else if (!BPConstants.IS_PUBLIC_YES.equals(isPublic) && !BPConstants.IS_PUBLIC_NOT.equals(isPublic))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamGroup", RespCode.BP_PARAMETER_FORMAT_ERROR.getCode()
                    + "isPublic = " + isPublic + ":是否公共只能用（Y或N）表示");
            throw new HsException(RespCode.BP_PARAMETER_FORMAT_ERROR.getCode(), "isPublic = " + isPublic
                    + ":是否公共只能用（Y或N）表示");

        }

        // 激活状态:Y：是；N：否
        String isActive = sysParamGroup.getIsActive();
        if (isActive == null || "".equals(isActive))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamGroup", RespCode.BP_PARAMETER_NULL.getCode() + "isActive = "
                    + isActive + ":激活状态为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "isActive = " + isActive + ":激活状态为空");
        }
        else if (!BPConstants.IS_ACTIVE_YES.equals(isActive) && !BPConstants.IS_ACTIVE_NOT.equals(isActive))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamGroup", RespCode.BP_PARAMETER_FORMAT_ERROR.getCode()
                    + "isActive = " + isActive + ":激活状态只能用（Y或N）表示");
            throw new HsException(RespCode.BP_PARAMETER_FORMAT_ERROR.getCode(), "isActive = " + isActive
                    + ":激活状态只能用（Y或N）表示");

        }

        try
        {
            // 通过系统参数组编号检查是否在数据库已经存在
            BusinessSysParamGroup businessSysGroup = businessSysParamMapper.getSysParamGroupByCode(sysGroupCode);

            if (businessSysGroup != null)
            {
                SystemLog.debug("HSXT_BP", "方法：addSysParamGroup", RespCode.BP_PARAMETER_NULL.getCode()
                        + "sysGroupCode = " + sysGroupCode + ":该系统参数组编号已经存在");
                throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysGroupCode = " + sysGroupCode
                        + ":该系统参数组编号已经存在");
            }

            // 将系统参数组的对象插入到数据库中
            businessSysParamMapper.addSysParamGroup(sysParamGroup);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamGroup", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }

    }

    /**
     * 更新系统业务参数组
     * 
     * @param sysParamGroup
     *            系统业务参数组对象
     * @throws HsException
     */
    @Override
    public void updateSysParamGroup(BusinessSysParamGroup sysParamGroup) throws HsException {

        // 系统参数组编号
        String sysGroupCode = sysParamGroup.getSysGroupCode();
        if (sysGroupCode == null || "".equals(sysGroupCode))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamGroup", RespCode.BP_PARAMETER_NULL.getCode() + "sysGroupCode = "
                    + sysGroupCode + ":系统参数组编号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysGroupCode = " + sysGroupCode + ":系统参数组编号为空");
        }

        // 更新者ID
        String updatedby = sysParamGroup.getUpdatedby();
        if (updatedby == null || "".equals(updatedby))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamGroup", RespCode.BP_PARAMETER_NULL.getCode() + "updatedby = "
                    + updatedby + ":更新者ID为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "updatedby = " + updatedby + ":更新者ID为空");
        }

        try
        {
            // 将该系统参数组进行数据库更新
            businessSysParamMapper.updateSysParamGroup(sysParamGroup);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：updateSysParamGroup", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 删除系统业务参数组
     * 
     * @param sysGroupCode
     *            系统参数组编号
     * @throws HsException
     */
    @Override
    public void deleteSysParamGroup(String sysGroupCode) throws HsException {

        // 系统参数组编号
        if (sysGroupCode == null || "".equals(sysGroupCode))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamGroup", RespCode.BP_PARAMETER_NULL.getCode() + "sysGroupCode = "
                    + sysGroupCode + ":系统参数组编号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysGroupCode = " + sysGroupCode + ":系统参数组编号为空");
        }
        try
        {
            // 通过该系统参数组编号去删除对应数据库中的数据
            businessSysParamMapper.deleteSysParamGroup(sysGroupCode);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：deleteSysParamGroup", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 查询单个系统业务参数组
     * 
     * @param sysGroupCode
     *            系统参数组编号
     * @throws HsException
     */
    @Override
    public BusinessSysParamGroup getSysParamGroupByCode(String sysGroupCode) throws HsException {

        // 系统参数组编号
        if (sysGroupCode == null || "".equals(sysGroupCode))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamGroup", RespCode.BP_PARAMETER_NULL.getCode() + "sysGroupCode = "
                    + sysGroupCode + ":系统参数组编号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysGroupCode = " + sysGroupCode + ":系统参数组编号为空");
        }

        // 系统参数组对象初始化
        BusinessSysParamGroup sysParamGroup = null;
        try
        {
            // 通过系统参数组编号去查找数据库中对应的数据
            sysParamGroup = (BusinessSysParamGroup) businessSysParamMapper.getSysParamGroupByCode(sysGroupCode);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：getSysParamGroupByCode", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }

        // 返回的系统参数组对象
        return sysParamGroup;
    }

    /**
     * 查询多个系统业务参数组
     * 
     * @param sysParamGroup
     *            系统业务参数组对象
     * @param page
     *            分頁對象
     * @return PageData<SysParamGroup> 封裝系统业务参数组对象集合、當前條件的查詢總記錄數
     * @throws HsException
     */
    @Override
    public PageData<BusinessSysParamGroup> searchSysParamGroupPage(BusinessSysParamGroup sysParamGroup, Page page)
            throws HsException {

        // 分页信息设值
        PageContext.setPage(page);
        PageData<BusinessSysParamGroup> pageDate = null;// 公用分页查询返回参数类
        try
        {
            // 根据系统业务参数组对象中的条件去数据库查找对应的数据
            List<BusinessSysParamGroup> sysParamGroupList = businessSysParamMapper
                    .searchSysParamGroupListPage(sysParamGroup);

            // 封裝系统业务参数组对象集合、當前條件的查詢總記錄數
            pageDate = new PageData<BusinessSysParamGroup>(page.getCount(), sysParamGroupList);
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：searchSysParamGroupPage", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
        return pageDate;
    }

    /**
     * 新增系统业务参数项
     * 
     * @param BusinessSysParamItems
     *            系统业务参数项对象
     * @throws SQLException
     */
    @Override
    public void addSysParamItems(BusinessSysParamItems sysParamItems) throws HsException {

        // 系统参数组编号
        String sysGroupCode = sysParamItems.getSysGroupCode();
        if (sysGroupCode == null || "".equals(sysGroupCode))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamItems", RespCode.BP_PARAMETER_NULL.getCode() + "sysGroupCode = "
                    + sysGroupCode + ":系统参数组编号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysGroupCode = " + sysGroupCode + ":系统参数组编号为空");
        }

        // 系统参数项键
        String sysItemsKey = sysParamItems.getSysItemsKey();
        if (sysItemsKey == null || "".equals(sysItemsKey))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamItems", RespCode.BP_PARAMETER_NULL.getCode() + "sysItemsKey = "
                    + sysItemsKey + ":系统参数项键名为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsKey = " + sysItemsKey + ":系统参数项键名为空");
        }

        // 系统参数项键名称
        String sysItemsName = sysParamItems.getSysItemsName();
        if (sysItemsName == null || "".equals(sysItemsName))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamItems", RespCode.BP_PARAMETER_NULL.getCode() + "sysItemsName = "
                    + sysItemsName + ":系统参数项键名称为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsName = " + sysItemsName
                    + ":系统参数项键名称为空");
        }

        // 系统参数项键值
        String sysItemsValue = sysParamItems.getSysItemsValue();
        if (sysItemsValue == null || "".equals(sysItemsValue))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamItems", RespCode.BP_PARAMETER_NULL.getCode() + "sysItemsValue = "
                    + sysItemsValue + ":系统参数项键值为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsValue = " + sysItemsValue
                    + ":系统参数项键值为空");
        }

        // 访问级别：0-不可见；如密码，证书等,1-可查看不可修改,2-可修改不可删除,3-可修改可删除
        int accessLevel = sysParamItems.getAccessLevel();
        // 判断访问级别
        if (accessLevel != BPConstants.ACCESS_LEVEL[0] && accessLevel != BPConstants.ACCESS_LEVEL[1]
                && accessLevel != BPConstants.ACCESS_LEVEL[2] && accessLevel != BPConstants.ACCESS_LEVEL[3])
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamItems", RespCode.BP_PARAMETER_NULL.getCode() + "accessLevel = "
                    + accessLevel + ":访问级别只有四种类型，并且用数字（0,1,2,3）表示");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "accessLevel = " + accessLevel
                    + ":访问级别只有四种类型，并且用数字（0,1,2,3）表示");
        }

        // 创建者
        String createdby = sysParamItems.getCreatedby();
        if (createdby == null || "".equals(createdby))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamItems", RespCode.BP_PARAMETER_NULL.getCode() + "createdby = "
                    + createdby + ":创建者参数为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "createdby = " + createdby + ":创建者参数为空");
        }

        // 激活状态:Y：是；N：否
        String isActive = sysParamItems.getIsActive();
        if (isActive == null || "".equals(isActive))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamItems", RespCode.BP_PARAMETER_NULL.getCode() + "isActive = "
                    + isActive + ":激活状态为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "isActive = " + isActive + ":激活状态为空");
        }
        else if (!BPConstants.IS_ACTIVE_YES.equals(isActive) && !BPConstants.IS_ACTIVE_NOT.equals(isActive))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamItems", RespCode.BP_PARAMETER_FORMAT_ERROR.getCode()
                    + "isActive = " + isActive + ":激活状态只能用（Y或N）表示");
            throw new HsException(RespCode.BP_PARAMETER_FORMAT_ERROR.getCode(), "isActive = " + isActive
                    + ":激活状态只能用（Y或N）表示");

        }

        try
        {
            // 通过系统参数组编号查找对应的数据
            BusinessSysParamGroup businessSysParamGroup = businessSysParamMapper.getSysParamGroupByCode(sysGroupCode);

            // 判断该系统参数组编码数据库中是否已经存在
            if (businessSysParamGroup == null)
            {
                SystemLog.debug("HSXT_BP", "方法：addSysParamItems", RespCode.BP_PARAMETER_FORMAT_ERROR.getCode()
                        + "sysGroupCode = " + sysGroupCode + "该系统参数组编码数据库中不存在");
                throw new HsException(RespCode.BP_PARAMETER_FORMAT_ERROR.getCode(), "sysGroupCode = " + sysGroupCode
                        + sysGroupCode + ":该系统参数组编码数据库中不存在");
            }

            // 通过系统参数项编号查找对应的数据
            BusinessSysParamItems businessSysParamItems = businessSysParamMapper.getSysParamItemsByKey(sysItemsKey);

            // 判断该系统参数项键数据库中是否已经存在
            if (businessSysParamItems != null)
            {
                SystemLog.debug("HSXT_BP", "方法：addSysParamItems", RespCode.BP_PARAMETER_FORMAT_ERROR.getCode()
                        + "sysItemsKey = " + sysItemsKey + "该系统参数项j键数据库中已经存在");
                throw new HsException(RespCode.BP_PARAMETER_FORMAT_ERROR.getCode(), "sysItemsKey = " + sysItemsKey
                        + sysItemsKey + ":该系统参数项键数据库中已经存在");
            }

            // 将系统参数项对象插入到数据库中
            businessSysParamMapper.addSysParamItems(sysParamItems);

            // 将该系统参数项对象添加到Redis服务器中
            BusinessParamSearchService businessParamSearchService = new BusinessParamSearchService(fixRedisUtil,
                    businessParamSearchMapper);
            businessParamSearchService.searchSysParamItemsByCodeKey(sysGroupCode, sysItemsKey);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamItems", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 更新系统业务参数项
     * 
     * @param BusinessSysParamItems
     *            系统业务参数项对象
     * @throws SQLException
     */
    @Override
    public void updateSysParamItems(BusinessSysParamItems sysParamItems) throws HsException {

        // 系统参数项编码
        String sysItemsCode = sysParamItems.getSysItemsCode();
        if (sysItemsCode == null || "".equals(sysItemsCode))
        {
            SystemLog.debug("HSXT_BP", "方法：updateSysParamItems", RespCode.BP_PARAMETER_NULL.getCode()
                    + "sysItemsCode = " + sysItemsCode + ":系统参数项编号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsCode = " + sysItemsCode + ":系统参数项编号为空");
        }

        // 系统参数项键
        String sysItemsKey = sysParamItems.getSysItemsKey();
        if (sysItemsKey == null || "".equals(sysItemsKey))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamItems", RespCode.BP_PARAMETER_NULL.getCode() + "sysItemsKey = "
                    + sysItemsKey + ":系统参数项键名为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsKey = " + sysItemsKey + ":系统参数项键名为空");
        }

        // 系统参数组编号
        String sysGroupCode = sysParamItems.getSysGroupCode();
        if (sysGroupCode == null || "".equals(sysGroupCode))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamItems", RespCode.BP_PARAMETER_NULL.getCode() + "sysGroupCode = "
                    + sysGroupCode + ":系统参数组编号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysGroupCode = " + sysGroupCode + ":系统参数组编号为空");
        }

        try
        {
            // 通过该系统参数项对象中的条件更新对应的数据库信息
            businessSysParamMapper.updateSysParamItems(sysParamItems);

            // 将该更新的系统参数项对象更新到Redis服务器中
            BusinessParamSearchService businessParamSearchService = new BusinessParamSearchService(fixRedisUtil,
                    businessParamSearchMapper);
            Map<String, BusinessSysParamItemsRedis> businessSysParamItemsMap = businessParamSearchService
                    .searchSysParamItemsByGroup(sysGroupCode);
            BusinessSysParamItemsRedis sysParamItemsRedis = new BusinessSysParamItemsRedis();
            BeanCopierUtils.copy(sysParamItems, sysParamItemsRedis);
            businessSysParamItemsMap.put(sysItemsKey, sysParamItemsRedis);
            // 把Map装换为String
            String objectJson = JSON.toJSONString(businessSysParamItemsMap);
            fixRedisUtil.add(BPConstants.SYS_NAME, sysGroupCode, objectJson);
            
            //获取统一更新版本的业务参数
            Map<String, String> editionMap = BPConstants.getbusinessParamEdition();
            
            //判断是否需要更新版本的业务参数
            if(editionMap.get(sysParamItems.getSysItemsKey()) != null)
            {
                AsPosBaseInfo baseInfo = new AsPosBaseInfo();
                //已实名注册个人单笔兑换生币最小限额
                if(sysParamItems.getSysItemsKey().equals(BusinessParam.P_REGISTERED_SINGLE_BUY_HSB_MIN.getCode()))
                {
                    baseInfo.setCarderChargeMinValue(sysParamItems.getSysItemsValue());
                }
                //已实名注册个人单笔兑换生币最小限额
                else if(sysParamItems.getSysItemsKey().equals(BusinessParam.P_REGISTERED_SINGLE_BUY_HSB_MAX.getCode()))
                {
                    baseInfo.setCarderChargeMaxValue(sysParamItems.getSysItemsValue());
                }
                //托管企业单笔互生币汇兑最小限额
                else if(sysParamItems.getSysItemsKey().equals(BusinessParam.T_SINGLE_BUY_HSB_MIN.getCode()))
                {
                    baseInfo.setEntTChargeMinValue(sysParamItems.getSysItemsValue());
                }
                //托管企业单笔兑换互生币最大限额
                else if(sysParamItems.getSysItemsKey().equals(BusinessParam.T_SINGLE_BUY_HSB_MAX.getCode()))
                {
                    baseInfo.setEntTChargeMaxValue(sysParamItems.getSysItemsValue());
                }
                //成员企业单笔互生币汇兑最小限额
                else if(sysParamItems.getSysItemsKey().equals(BusinessParam.B_SINGLE_BUY_HSB_MIN.getCode()))
                {
                    baseInfo.setEntBChargeMinValue(sysParamItems.getSysItemsValue());
                }
                //成员企业单笔兑换互生币最大限额
                else if(sysParamItems.getSysItemsKey().equals(BusinessParam.B_SINGLE_BUY_HSB_MAX.getCode()))
                {
                    baseInfo.setEntBChargeMaxValue(sysParamItems.getSysItemsValue());
                }
                //积分比例设置最小值
                else if(sysParamItems.getSysItemsKey().equals(BusinessParam.HS_POIT_RATE_MIN.getCode()))
                {
                    baseInfo.setPointRateMinValue(sysParamItems.getSysItemsValue());
                }
                //积分比例设置最大值
                else if(sysParamItems.getSysItemsKey().equals(BusinessParam.HS_POIT_RATE_MAX.getCode()))
                {
                    baseInfo.setPointRateMaxValue(sysParamItems.getSysItemsValue());
                }
                commonUtil.updatePosBaseInfo(baseInfo);
            }
            
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：updateSysParamItems", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }

    }

    /**
     * 删除系统业务参数项
     * 
     * @param BusinessSysParamItems
     *            系统参数项编号
     * @throws SQLException
     */
    @Override
    public void deleteSysParamItems(String sysItemsCode, String sysGroupCode, String sysItemsKey) throws HsException {

        // 系统参数项编码
        if (sysItemsCode == null || "".equals(sysItemsCode))
        {
            SystemLog.debug("HSXT_BP", "方法：deleteSysParamItems", RespCode.BP_PARAMETER_NULL.getCode()
                    + "sysItemsCode = " + sysItemsCode + ":系统参数项编号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsCode = " + sysItemsCode + ":系统参数项编号为空");
        }

        // 系统参数项键
        if (sysItemsKey == null || "".equals(sysItemsKey))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamItems", RespCode.BP_PARAMETER_NULL.getCode() + "sysItemsKey = "
                    + sysItemsKey + ":系统参数项键名为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsKey = " + sysItemsKey + ":系统参数项键名为空");
        }

        // 系统参数组编号
        if (sysGroupCode == null || "".equals(sysGroupCode))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamItems", RespCode.BP_PARAMETER_NULL.getCode() + "sysGroupCode = "
                    + sysGroupCode + ":系统参数组编号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysGroupCode = " + sysGroupCode + ":系统参数组编号为空");
        }

        try
        {
            businessSysParamMapper.deleteSysParamItems(sysItemsCode);

            // 将该删除的协议对象同时删除Redis服务器对应的对象
            BusinessParamSearchService businessParamSearchService = new BusinessParamSearchService(fixRedisUtil,
                    businessParamSearchMapper);
            Map<String, BusinessSysParamItemsRedis> businessSysParamItemsMap = businessParamSearchService
                    .searchSysParamItemsByGroup(sysGroupCode);
            System.out.println("sysItemsKey===="+sysItemsKey);
            System.out.println("sssss============"+businessSysParamItemsMap);
            System.out.println(businessSysParamItemsMap.size()+",key======="+businessSysParamItemsMap.get(sysItemsKey));
            businessSysParamItemsMap.remove(sysItemsKey);
            // 把Map装换为String
            String objectJson = JSON.toJSONString(businessSysParamItemsMap);
            fixRedisUtil.add(BPConstants.SYS_NAME, sysGroupCode, objectJson);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            System.out.println("exception====="+e);
            SystemLog.debug("HSXT_BP", "方法：deleteSysParamGroup", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }

    }

    /**
     * 查询单个系统业务参数项
     * 
     * @param sysItemsCode
     *            系统参数项编号
     * @throws SQLException
     */
    @Override
    public BusinessSysParamItems getSysParamItemsByCode(String sysItemsCode) throws HsException {

        // 系统参数项编码
        if (sysItemsCode == null || "".equals(sysItemsCode))
        {
            SystemLog.debug("HSXT_BP", "方法：getSysParamItemsByCode", RespCode.BP_PARAMETER_NULL.getCode()
                    + "sysItemsCode = " + sysItemsCode + ":系统参数项编号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsCode = " + sysItemsCode + ":系统参数项编号为空");
        }

        // 系统参数项对象
        BusinessSysParamItems sysParamItems = null;
        try
        {
            // 通过系统参数项编号查询对应数据库中的数据信息
            sysParamItems = (BusinessSysParamItems) businessSysParamMapper.getSysParamItemsByCode(sysItemsCode);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：getSysParamItemsByCode", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
        return sysParamItems;
    }

    /**
     * 查询多个系统业务参数项
     * 
     * @param BusinessSysParamItems
     *            系统业务参数项对象
     * @param page
     *            分頁對象
     * @return PageData<SysParamItems> 封裝系统业务参数项对象集合、當前條件的查詢總記錄數
     * @throws SQLException
     */
    @Override
    public PageData<BusinessSysParamItems> searchSysParamItemsPage(BusinessSysParamItems sysParamItems, Page page)
            throws HsException {

        // 分页信息设值
        PageContext.setPage(page);
        PageData<BusinessSysParamItems> pageDate = null;// 公用分页查询返回参数类

        try
        {
            // 通过系统业务参数项对象中的条件查找对应数据库中的信息
            List<BusinessSysParamItems> sysParamItemsList = businessSysParamMapper
                    .searchSysParamItemsListPage(sysParamItems);

            // 封裝系统业务参数项对象集合、當前條件的查詢總記錄數
            pageDate = new PageData<BusinessSysParamItems>(page.getCount(), sysParamItemsList);
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：searchSysParamItemsPage", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
        return pageDate;
    }

    /**
     * 查询所有的系统参数组
     * @return 系统参数组集合
     * @throws HsException 
     * @see com.gy.hsxt.bp.api.IBusinessSysParamService#searchSysParamGroupList()
     */
    @Override
    public List<BusinessSysParamGroup> searchSysParamGroupList() throws HsException {
        
        List<BusinessSysParamGroup> list = null;
        try
        {
            list = businessSysParamMapper.searchSysParamGroupList();
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：searchSysParamGroupList", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
        return list;
    }

    /** 
     * 通过系统参数组编码查询该编码对应下的所有参数项
     * @param sysGroupCode
     * @return 参数项集合
     * @throws HsException 
     * @see com.gy.hsxt.bp.api.IBusinessSysParamService#searchSysParamItemsList(java.lang.String)
     */
    @Override
    public List<BusinessSysParamItems> searchSysParamItemsList(String sysGroupCode) throws HsException {
        
        List<BusinessSysParamItems> list = null;
        try
        {
            list = businessSysParamMapper.searchSysParamItemsList(sysGroupCode);
        }
        catch (SQLException e)
        {
            SystemLog.debug("HSXT_BP", "方法：searchSysParamItemsList", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
        return list;
    }

}
