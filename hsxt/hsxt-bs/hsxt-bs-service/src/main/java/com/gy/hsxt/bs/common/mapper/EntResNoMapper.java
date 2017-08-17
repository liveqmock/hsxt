/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.apply.bean.EntResNo;
import com.gy.hsxt.bs.bean.apply.ResNo;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.mapper
 * @ClassName: EntResNoMapper
 * @Description: 企业资源Mapper
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午5:29:34
 * @version V1.0
 */
@Repository
public interface EntResNoMapper {

    /**
     * 生成服务公司下的所有互生号
     * 
     * @param resNoList
     *            服务公司下的所有互生号
     */
    void createResNo(List<EntResNo> resNoList);

    /**
     * 判断企业互生号是否可用
     * 
     * @param resNo
     *            企业互生号
     * @return true可用，false不可用
     */
    boolean checkValidEntResNo(String resNo);

    /**
     * 判断服务公司互生号是否可用
     * 
     * @param resNo
     *            服务公司互生号
     * @return true可用，false不可用
     */
    boolean checkValidServiceResNo(String resNo);

    /**
     * 更新企业互生号状态
     * 
     * @param resNo
     *            企业互生号
     * @param status
     *            状态
     */
    int updateEntResNoStatus(@Param("resNo") String resNo, @Param("status") Integer status);

    /**
     * 更新服务公司互生号使用状态
     * 
     * @param resNo
     *            服务公司互生号
     * @param status
     *            状态
     * @param entCustId
     *            服务公司客户号
     * @param entCustName
     *            服务公司名称
     */
    int updateServiceResNo(@Param("resNo") String resNo, @Param("status") Integer status,
            @Param("entCustId") String entCustId, @Param("entCustName") String entCustName);

    /**
     * 查询服务公司互生号列表
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @param cityNo
     *            城市代码
     * @param resStatus
     *            互生号状态
     * @return 服务公司互生号列表
     */
    List<String> getSerResNoList(@Param("countryNo") String countryNo, @Param("provinceNo") String provinceNo,
            @Param("cityNo") String cityNo, @Param("resStatus") Integer resStatus);

    /**
     * 查询成员企业可用互生号列表
     * 
     * @param serResNoPre
     *            服务公司互生号前5位
     * @param resType
     *            资源类型
     * @return 员企业可用互生号列表
     */
    List<String> getMemResNoList(@Param("serResNoPre") String serResNoPre, @Param("resType") Integer resType);

    /**
     * 查询托管企业可用互生号列表
     * 
     * @param serResNoPre
     *            服务公司互生号前5位
     * @param resType
     *            资源类型
     * @return 托管企业可用互生号列表
     */
    List<String> getTrustResNoList(@Param("serResNoPre") String serResNoPre, @Param("resType") Integer resType);

    /**
     * 分页查询成员企业可用互生号列表
     * 
     * @param serResNoPre
     *            服务公司互生号前5位
     * @param resType
     *            资源类型
     * @return 员企业可用互生号列表
     */
    List<ResNo> getMemResNoListPage(@Param("serResNoPre") String serResNoPre, @Param("resType") Integer resType,
            @Param("resNo") String resNo);

    /**
     * 分页查询托管企业可用互生号列表
     * 
     * @param serResNoPre
     *            服务公司互生号前5位
     * @param resType
     *            资源类型
     * @return 托管企业可用互生号列表
     */
    List<ResNo> getTrustResNoListPage(@Param("serResNoPre") String serResNoPre, @Param("resType") Integer resType,
            @Param("resNo") String resNo);

    /**
     * 查询所有服务公司互生号
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @return 服务公司互生号列表
     */
    List<String> getAllSerResNoList(@Param("countryNo") String countryNo, @Param("provinceNo") String provinceNo);

    /**
     * 查询服务公司互生号列表--分页
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @param cityNo
     *            城市代码
     * @param resStatus
     *            互生号状态
     * @param resNo
     *            拟用互生号
     * @return 服务公司互生号列表
     */
    List<ResNo> getSerResNoListPage(@Param("countryNo") String countryNo, @Param("provinceNo") String provinceNo,
            @Param("cityNo") String cityNo, @Param("resStatus") Integer resStatus, @Param("resNo") String resNo);

    /**
     * 调用存储过程，生成服务公司下的99个托管企业和9999个成员企业互生号
     * 
     * @param map
     *            服务公司互生号
     */
    void createEntResNo(Map<String, Object> map);

    /**
     * 查询已占用互生号资源详情
     *
     * @param resNo 互生号
     * @return {@code ResNo}
     */
    ResNo selectUsedSerResNoByResNo(@Param("resNo") String resNo);

}
