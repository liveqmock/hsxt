/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.service;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.common.utils.HsAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.bs.bean.apply.ResNo;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.apply.ResType;
import com.gy.hsxt.bs.common.enumtype.quota.ResStatus;
import com.gy.hsxt.bs.common.interfaces.IEntResNoService;
import com.gy.hsxt.bs.common.mapper.EntResNoMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsResNoUtils;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.service
 * @ClassName: EntResNoService
 * @Description: 企业资源管理
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午5:25:15
 * @version V1.0
 */
@Service
public class EntResNoService implements IEntResNoService {

    @Autowired
    private EntResNoMapper entResNoMapper;

    /**
     * 生成服务公司下的所有互生号
     * 
     * @param serResNo
     *            互生公司互生号
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createEntResNo(String serResNo) {

        // 生成服务公司下所有成员、托管企业互生号
        if (HsResNoUtils.isServiceResNo(serResNo))
        {
            // 调用存储过程，生成服务公司下的99个托管企业和9999个成员企业互生号
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serResNo", serResNo);
            entResNoMapper.createEntResNo(map);
            // String serResNoPre = serResNo.substring(0, 5);
            // String tmpResNo = "";
            // List<EntResNo> resNoList = new ArrayList<EntResNo>();
            // // 生成成员企业互生号
            // for (int i = 1; i < 10000; i++)
            // {
            // if (i < 10)
            // {
            // tmpResNo = serResNoPre + "00000" + i;
            // }
            // else if (i < 100)
            // {
            // tmpResNo = serResNoPre + "0000" + i;
            // }
            // else if (i < 1000)
            // {
            // tmpResNo = serResNoPre + "000" + i;
            // }
            // else
            // {
            // tmpResNo = serResNoPre + "00" + i;
            // }
            // EntResNo entResNo = new EntResNo();
            // entResNo.setEntResNo(tmpResNo);
            // entResNo.setCustType(CustType.MEMBER_ENT.getCode());
            // entResNo.setResStatus(ResStatus.NOT_USED.getCode());
            // resNoList.add(entResNo);
            // }
            // // 生成托管企业互生号
            // for (int i = 1; i < 100; i++)
            // {
            // if (i < 10)
            // {
            // tmpResNo = serResNoPre + "0" + i + "0000";
            // }
            // else
            // {
            // tmpResNo = serResNoPre + i + "0000";
            // }
            // EntResNo entResNo = new EntResNo();
            // entResNo.setEntResNo(tmpResNo);
            // entResNo.setCustType(CustType.TRUSTEESHIP_ENT.getCode());
            // entResNo.setResStatus(ResStatus.NOT_USED.getCode());
            // resNoList.add(entResNo);
            // }
            // entResNoMapper.createResNo(resNoList);
        }
        else
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "参数错误 ");
        }

    }

    /**
     * 判断企业互生号是否可用
     * 
     * @param custType
     *            客户类型
     * @param entResNo
     *            企业互生号
     * @return true 可用， false 不可用
     */
    @Override
    public Boolean checkValidEntResNo(Integer custType, String entResNo) {
        if (CustType.SERVICE_CORP.getCode() == custType)
        {
            return entResNoMapper.checkValidServiceResNo(entResNo);
        }
        else if (CustType.MEMBER_ENT.getCode() == custType || CustType.TRUSTEESHIP_ENT.getCode() == custType)
        {
            return entResNoMapper.checkValidEntResNo(entResNo);
        }
        return false;
    }

    /**
     * 更新企业互生号使用状态
     * 
     * @param custType
     *            客户类型
     * @param entResNo
     *            企业互生号
     * @param status
     *            状态
     */
    @Override
    @Transactional
    public int updateEntResNoStatus(Integer custType, String entResNo, Integer status) {
        HsAssert.isTrue(CustType.checkType(custType),BSRespCode.BS_PARAMS_TYPE_ERROR,"企业客户号[custType]错误");
        HsAssert.hasText(entResNo, BSRespCode.BS_PARAMS_EMPTY, "企业互生号[entCustNo]为空");
        HsAssert.isTrue(ResStatus.checkResStatus(status),BSRespCode.BS_PARAMS_TYPE_ERROR,"互生号使用状态[status]错误");
        int count = 0;
        if (CustType.SERVICE_CORP.getCode() == custType)
        {
           count =  entResNoMapper.updateServiceResNo(entResNo, status, null, null);
        }
        else if (CustType.MEMBER_ENT.getCode() == custType || CustType.TRUSTEESHIP_ENT.getCode() == custType)
        {
            count = entResNoMapper.updateEntResNoStatus(entResNo, status);
        }
        return count;
    }

    /**
     * 更新服务公司资源
     * 
     * @param resNo
     *            互生号
     * @param status
     *            状态
     * @param entCustId
     *            服务公司客户号
     * @param entCustName
     *            服务公司名称
     */
    @Override
    @Transactional
    public int updateServiceResNo(String resNo, Integer status, String entCustId, String entCustName) {
        HsAssert.hasText(resNo, BSRespCode.BS_PARAMS_EMPTY, "服务公司互生号[resNo]为空");
        HsAssert.isTrue(ResStatus.checkResStatus(status),BSRespCode.BS_PARAMS_TYPE_ERROR,"互生号使用状态[status]错误");
       return entResNoMapper.updateServiceResNo(resNo, status, entCustId, entCustName);
    }

    /**
     * 查询服务公司配额数
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @param cityNo
     *            城市代码
     * @return 服务公司配额数
     */
    @Override
    public Integer getServiceQuota(String countryNo, String provinceNo, String cityNo) {
        List<String> list = entResNoMapper.getSerResNoList(countryNo, provinceNo, cityNo, ResStatus.NOT_USED.getCode());
        return list == null ? 0 : list.size();
    }

    /**
     * 查询成员企业、托管企业配额数
     * 
     * @param serResNo
     *            服务公司互生号
     * @param custType
     *            客户类型
     * @param resType
     *            启用资源类型
     * @return 成员企业或托管企业配额数
     */
    @Override
    public Integer getNotServiceQuota(String serResNo, Integer custType, Integer resType) {

        if (null != custType && CustType.TRUSTEESHIP_ENT.getCode() == custType)// 托管企业
        {
            List<String> list = this.getTrustResNoList(serResNo, resType);
            return list == null ? 0 : list.size();
        }
        else if (null != custType && CustType.MEMBER_ENT.getCode() == custType)// 成员企业
        {
            List<String> list = this.getMemResNoList(serResNo, resType);
            return list == null ? 0 : list.size();
        }
        return 0;
    }

    /**
     * 查询服务公司可用互生号列表
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @param cityNo
     *            城市代码
     * @param resStatus
     *            资源状态
     * @return 服务公司可用互生号列表
     */
    @Override
    public List<String> getSerResNoList(String countryNo, String provinceNo, String cityNo, Integer resStatus) {
        return entResNoMapper.getSerResNoList(countryNo, provinceNo, cityNo, resStatus);
    }

    /**
     * 查询成员企业、托管企业可用互生号列表
     * 
     * @param serResNo
     *            服务公司互生号
     * @param custType
     *            客户类型
     * @param resType
     *            启用资源类型
     * @return 成员企业或托管企业可用互生号列表
     */
    @Override
    public List<String> getNotSerResNoList(String serResNo, Integer custType, Integer resType) {
        List<String> list = null;
        if (null != custType && CustType.TRUSTEESHIP_ENT.getCode() == custType)
        {
            list = this.getTrustResNoList(serResNo, resType);
        }
        else if (null != custType && CustType.MEMBER_ENT.getCode() == custType)
        {
            list = this.getMemResNoList(serResNo, resType);
        }
        return list;
    }

    // 查询可用的托管企业互生号
    private List<String> getTrustResNoList(String serResNo, Integer resType) {
        if (isBlank(serResNo)
                || !HsResNoUtils.isServiceResNo(serResNo)
                || null == resType
                || !(ResType.ALL_RES.getCode() == resType || ResType.FIRST_SECTION_RES.getCode() == resType || ResType.ENTREPRENEURSHIP_RES
                        .getCode() == resType))
        {// 参数错误
            return null;
        }
        else
        {
            return entResNoMapper.getTrustResNoList(serResNo.substring(0, 5), resType);
        }
    }

    // 查询可用的成员企业互生号
    private List<String> getMemResNoList(String serResNo, Integer resType) {
        if (isBlank(serResNo) || !HsResNoUtils.isServiceResNo(serResNo) || null == resType
                || !(ResType.FREE_MEMBER_ENT.getCode() == resType || ResType.NORMAL_MEMBER_ENT.getCode() == resType))
        {// 参数错误
            return null;
        }
        else
        {
            return entResNoMapper.getMemResNoList(serResNo.substring(0, 5), resType);
        }
    }

    /**
     * 查询管理公司互生号
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @return 管理公司互生号
     */
    @Override
    public String getManResNo(String countryNo, String provinceNo) {
        List<String> list = entResNoMapper.getAllSerResNoList(countryNo, provinceNo);
        if (list == null || list.size() == 0)
        {
            return null;
        }
        else
        {
            return list.get(0).substring(0, 2) + "000000000";
        }
    }

    /**
     * 分页查询服务公司可用互生号列表
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @param cityNo
     *            城市代码
     * @param resStatus
     *            资源状态
     * @param resNo
     *            拟用互生号
     * @param page
     *            分页
     * @return 服务公司可用互生号列表
     */
    @Override
    public PageData<ResNo> getSerResNos(String countryNo, String provinceNo, String cityNo, Integer resStatus,
            String resNo, Page page) {
        PageContext.setPage(page);
        PageData<ResNo> pageData = null;
        List<ResNo> list = entResNoMapper.getSerResNoListPage(countryNo, provinceNo, cityNo, resStatus, resNo);
        if (null != list && list.size() > 0)
        {
            pageData = new PageData<ResNo>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 分页查询成员企业、托管企业可用互生号列表
     * 
     * @param serResNo
     *            服务公司互生号
     * @param custType
     *            客户类型
     * @param resType
     *            启用资源类型
     * @param resNo
     *            拟用互生号
     * @param page
     *            分页
     * @return 成员企业或托管企业可用互生号列表
     */
    @Override
    public PageData<ResNo> getEntResNos(String serResNo, Integer custType, Integer resType, String resNo, Page page) {
        PageContext.setPage(page);
        PageData<ResNo> pageData = null;
        List<ResNo> list = null;
        if (CustType.MEMBER_ENT.getCode() == custType)
        {
            if (isBlank(serResNo)
                    || !HsResNoUtils.isServiceResNo(serResNo)
                    || null == resType
                    || !(ResType.FREE_MEMBER_ENT.getCode() == resType || ResType.NORMAL_MEMBER_ENT.getCode() == resType))
            {// 参数错误
                list = null;
            }
            else
            {
                list = entResNoMapper.getMemResNoListPage(serResNo.substring(0, 5), resType, resNo);
            }

        }
        else if (CustType.TRUSTEESHIP_ENT.getCode() == custType)
        {
            if (isBlank(serResNo)
                    || !HsResNoUtils.isServiceResNo(serResNo)
                    || null == resType
                    || !(ResType.ALL_RES.getCode() == resType || ResType.FIRST_SECTION_RES.getCode() == resType || ResType.ENTREPRENEURSHIP_RES
                            .getCode() == resType))
            {// 参数错误
                list = null;
            }
            else
            {
                list = entResNoMapper.getTrustResNoListPage(serResNo.substring(0, 5), resType, resNo);
            }
        }

        if (null != list && list.size() > 0)
        {
            pageData = new PageData<ResNo>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }


    /**
     * 查询已占用互生号资源详情
     *
     * @param resNo 互生号
     * @return {@code ResNo}
     * @throws HsException
     */
    @Override
    public ResNo queryUsedSerResNoByResNo(String resNo) throws HsException {
        HsAssert.hasText(resNo, BSRespCode.BS_PARAMS_EMPTY, "互生号[resNo]为空");
        return entResNoMapper.selectUsedSerResNoByResNo(resNo);
    }
}
