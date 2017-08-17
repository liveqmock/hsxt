/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.quota.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.quota.CityPopulation;
import com.gy.hsxt.bs.bean.quota.CityQuotaApp;
import com.gy.hsxt.bs.bean.quota.CityQuotaQueryParam;
import com.gy.hsxt.bs.bean.quota.PlatQuotaApp;
import com.gy.hsxt.bs.bean.quota.ProvinceQuotaApp;
import com.gy.hsxt.bs.bean.quota.result.AllotProvince;
import com.gy.hsxt.bs.bean.quota.result.CompanyRes;
import com.gy.hsxt.bs.bean.quota.result.CompanyResS;
import com.gy.hsxt.bs.bean.quota.result.PlatAppManage;
import com.gy.hsxt.bs.bean.quota.result.QuotaDetailOfProvince;
import com.gy.hsxt.bs.bean.quota.result.QuotaStatOfCity;
import com.gy.hsxt.bs.bean.quota.result.QuotaStatOfManager;
import com.gy.hsxt.bs.bean.quota.result.QuotaStatOfProvince;
import com.gy.hsxt.bs.bean.quota.result.ResInfo;

/**
 * 资源配额管理Mapper
 * 
 * @Package: com.gy.hsxt.bs.quota.mapper
 * @ClassName: QuotaMapper
 * @Description: TODO
 * 
 * @author: yangjianguo
 * @date: 2015-10-19 下午12:30:58
 * @version V1.0
 */
@Repository
public interface QuotaMapper {

    /**
     * 一级区域配额分配申请(管理公司列表)
     * 
     * @Description:
     * @author: likui
     * @created: 2015年11月19日 下午3:29:02
     * @return
     * @return : List<PlatAppManage>
     * @version V3.0.0
     */
    List<PlatAppManage> selectPlatAppManageList(@Param("mEntResNo") String mEntResNo);

    /**
     * 查询管理公司的二级分配详情
     * 
     * @Description:
     * @author: likui
     * @created: 2016年2月29日 下午3:18:59
     * @param mEntResNo
     * @return
     * @return : PlatAppManage
     * @version V3.0.0
     */
    PlatAppManage selectManageAppProvinceDetail(@Param("mEntResNo") String mEntResNo);

    /**
     * 
     * @author yangjianguo
     * @created:2015年8月27日上午9:39:05
     * @desc:地区平台(一级区域)资源配额申请
     * @param bean
     */
    void applyPlatQuota(PlatQuotaApp bean);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月2日下午4:33:19
     * @desc:根据id查询地区平台(一级区域)配额申请详情
     * @param applyId
     *            申请编号
     * @return PlatQuotaApp
     */
    PlatQuotaApp getPlatQuotaById(String applyId);

    /**
     * 根据条件查询一级区域配额申请列表
     * 
     * @param entResNo
     *            管理公司互生号， 完全匹配
     * @param entCustName
     *            管理公司企业客户号，模糊查询
     * @param exeCustId
     *            执行审批操作的客户号
     * @return
     */
    List<PlatQuotaApp> queryPlatQuotaListPage(@Param("entResNo") String entResNo,
            @Param("entCustName") String entCustName, @Param("exeCustId") String exeCustId);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月2日下午4:34:28
     * @desc:审批地区平台(一级区域)资源配额申请
     * @param bean
     */
    void apprPlatQuota(PlatQuotaApp bean);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月2日下午4:35:12
     * @desc:申请省级区域(二级区域)配额分配
     * @param bean
     */
    void applyProvinceQuota(ProvinceQuotaApp bean);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月2日下午4:37:17
     * @desc:根据id查询省级(二级区域)配额申请
     * @param applyId
     *            申请编号
     * @return ProvinceQuotaApp
     */
    ProvinceQuotaApp getProvinceQuotaById(String applyId);

    /**
     * 判断省份是否已进行首次配置
     * 
     * @Description:
     * @author: likui
     * @created: 2015年11月19日 下午3:56:50
     * @param provinceNo
     * @return
     * @return : int
     * @version V3.0.0
     */
    int isProvinceFristAllot(String provinceNo);

    /**
     * 判断城市是否已进行首次配置
     * 
     * @Description:
     * @author: likui
     * @created: 2015年11月19日 下午5:55:05
     * @param cityNo
     * @return
     * @return : int
     * @version V3.0.0
     */
    int isCityFristAllot(String cityNo);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月2日下午4:39:49
     * @desc:审批省级(二级区域)配额申请
     * @param bean
     */
    void apprProvinceQuota(ProvinceQuotaApp bean);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月10日下午3:16:07
     * @desc:分页查询省级配额(二级区域)分配
     * @param proviceNo
     * @param applyType
     * @param status
     * @param exeCustId
     * @return List<ProvinceQuotaApp>
     */
    List<ProvinceQuotaApp> queryProvinceQuotaListPage(@Param("proviceNo") String proviceNo,
            @Param("applyType") Integer applyType, @Param("status") Integer status, @Param("exeCustId") String exeCustId);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月2日下午5:04:48
     * @desc:申请市级(三级区域)配额
     * @param bean
     */
    void applyCityQuota(CityQuotaApp bean);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月2日下午5:05:01
     * @desc:根据id查询市级(三级区域)配额
     * @param applyId
     *            申请编号
     * @return CityQuotaApp
     */
    CityQuotaApp getCityQuotaById(String applyId);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月2日下午5:05:04
     * @desc:审批市级(三级区域)配额申请
     * @param bean
     */
    void apprCityQuota(CityQuotaApp bean);

    /**
     * 插入城市人口数
     * 
     * @Description:
     * @author: likui
     * @created: 2015年11月18日 下午6:12:41
     * @param bean
     * @return : void
     * @version V3.0.0
     */
    void insertCityPopulation(CityPopulation bean);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月10日下午3:19:16
     * @desc:分页查询市级(三级区域)配额分配
     * @param param
     * @return List<CityQuotaApp>
     */
    List<CityQuotaApp> queryCityQuotaListPage(CityQuotaQueryParam param);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月2日下午6:34:21
     * @desc:城市(三级区域)下的资源列表
     * @param cityNo
     * @param status
     * @return List<CityResPage>
     */
    List<ResInfo> listResInfoOfCity(@Param("cityNo") String cityNo, @Param("status") String[] status);

    /**
     * 
     * @author yangjianguo
     * @created 2015年9月6日下午12:04:03
     * @desc 根据管理公司互生号查询已分配下级服务资源配额的二级区域，即已确定的管理公司管辖区域
     * @param mEntResNo
     *            管理公司互生号
     * @return List<String> 省份代码列表
     */
    List<String> selectProvinceNoForAllot(String mEntResNo);

    /**
     * 查询其它管理公司下一分配资源配额的区域，即其它管理公司的管辖区域
     * 
     * @param mEntResNo
     *            本管理公司互生号
     * @return
     */
    List<String> queryProvinceNoForAllotExcludeM(String mEntResNo);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月6日下午12:05:53
     * @desc:查询二级区域（省）的总配额数量
     * @param provinceNo
     *            省代码
     * @return Integer
     */
    Integer countProvinceQuota(String provinceNo);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月6日下午2:33:09
     * @desc:查询三级区域（城市）的总配额数量
     * @param cityNo
     * @return Integer
     */
    Integer countCityQuota(String cityNo);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月6日下午2:33:34
     * @desc:统计未分配到二级区域(省)的资源配额数量
     * @return int
     */
    int countFreePlatQuota();

    /**
     * 统计管理公司下级服务资源中空闲的平台资源配额，即未分配到二级区域的服务资源号数量
     * 
     * @param mEntResNo
     * @return
     */
    Integer countFreePlatQuotaOfManager(String mEntResNo);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月6日下午2:33:38
     * @desc:查询省下未分配到城市的配额数量 查询已分配到二级区域（省份），但未分配到三级区域（城市）的资源配额数量
     * @param provinceNo
     *            省代码
     * @return Integer 二级区域全部资源中未分配到三级区域的资源数量
     */
    Integer countFreeProvinceQuota(String provinceNo);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月6日下午2:33:38
     * @desc:查询省下未分配到城市的配额数量 查询已分配到二级区域（省份），但未分配到三级区域（城市）的资源配额数量(包括三级申请待审批数量)
     * @param provinceNo
     *            省代码
     * @return Integer 二级区域全部资源中未分配到三级区域的资源数量
     */
    Integer countFreeProvinceQuotaAndW(String provinceNo);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月6日下午2:33:42
     * @desc:查询城市下未申报的配额数量 城市配额中既没有被使用，也没有被占用的资源号数量
     * @param cityNo
     *            城市编号
     * @return Integer
     */
    Integer countFreeCityQuota(String cityNo);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月2日下午6:31:52
     * @desc:统计管理公司下的资源数据
     * @param mEntResNo
     *            管理公司互生号
     * @return QuotaStatOfManager
     */
    QuotaStatOfManager statResDetailOfManager(String mEntResNo);

    /**
     * 分组统计管理公司下各省份资源配额情况
     * 
     * @param mEntResNo
     *            管理公司互生号
     * @return
     */
    List<QuotaStatOfProvince> listQuotaStatGroupByProvince(String mEntResNo);

    /**
     * 
     * @author yangjianguo
     * @created:2015年9月2日下午6:33:18
     * @desc 统计省级(二级区域)下的资源数据
     * @param provinceNo
     *            省代码
     * @return QuotaDetailOfProvince
     */
    QuotaDetailOfProvince statResDetailOfProvince(String provinceNo);

    /**
     * 分组统计省下面各城市资源配额情况
     * 
     * @param provinceNo
     *            省代码
     * @return
     */
    List<QuotaStatOfCity> listQuotaStatGroupByCity(@Param("provinceNo") String provinceNo,
            @Param("cityNo") String cityNo);

    /**
     * 查询地区平台空闲的服务资源号，没有分配到二级区域的资源号可以被释放(平台减少配额时用)
     * 
     * @return
     */
    List<String> queryFreeQuotaOfPlat(Integer apprNum);

    /**
     * 查询管理公司下可分配到二级区域的服务资源号(二级区域增加配额时用)
     * 
     * @param mEntResNo
     * @param apprNum
     * @return
     */
    List<String> queryFreeQuotaOfManager(@Param("mEntResNo") String mEntResNo, @Param("apprNum") Integer apprNum);

    /**
     * 查询二级区域下未分配到三级区域(城市)的服务资源(二级区域减少配额或者三级区域增加配额时用)
     * 
     * @param provinceNo
     * @param apprNum
     * @param isDesc
     * @return
     */
    List<String> queryFreeQuotaOfProvince(@Param("provinceNo") String provinceNo, @Param("apprNum") Integer apprNum,
            @Param("isDesc") Boolean isDesc);

    /**
     * 查询三级区域可下可释放的资源号，未使用的资源号可以被释放(减少配额)
     * 
     * @param cityNo
     * @param apprNum
     * @return
     */
    List<String> queryFreeQuotaOfCity(@Param("cityNo") String cityNo, @Param("apprNum") Integer apprNum);

    /**
     * 释放(减少配额)当前平台的指定服务资源号
     * 
     * @param resNo
     *            服务资源号
     * @return
     */
    int releaseFromPlat(String resNo);

    /**
     * 分配服务资源号到指定二级区域（省）
     * 
     * @param resNos
     * @param provinceNo
     * @return
     */
    int allotToProvince(@Param("resNos") List<String> resNos, @Param("provinceNo") String provinceNo);

    /**
     * 从指定二级区域的资源配额中释放(减少配额)指定的服务资源号
     * 
     * @param resNos
     * @param provinceNo
     * @return
     */
    int releaseFromProvince(@Param("resNos") List<String> resNos, @Param("provinceNo") String provinceNo);

    /**
     * 分配服务资源号到指定三级区域（城市）
     * 
     * @param resNos
     * @param cityNo
     * @return
     */
    int allotToCity(@Param("resNos") List<String> resNos, @Param("cityNo") String cityNo);

    /**
     * 从指定三级区域(城市)的未使用服务资源中释放(减少配额)指定的服务资源号
     * 
     * @param resNos
     * @param cityNo
     * @return
     */
    int releaseFromCity(@Param("resNos") List<String> resNos, @Param("cityNo") String cityNo);

    /**
     * 一级区域(平台)资源配额分配时插入单条服务资源号
     * 
     * @param resNos
     *            服务资源号
     * @param countryNo
     *            国家代码
     * @return
     */
    int insertPlatQuota(@Param("resNos") String[] resNos, @Param("countryNo") String countryNo);

    /**
     * 释放一级区域(平台)资源配额时地区平台需要删除服务资源号，总平台需要取消该资源号与平台的关联
     * 
     * @param resNo
     *            服务资源号
     * @return
     */
    int deletePlatQuota(List<String> resNo);

    /**
     * 查询地区平台分配了配额的省
     * 
     * @Description:
     * @author: likui
     * @created: 2015年11月25日 下午4:52:38
     * @return
     * @return : List<AllotProvince>
     * @version V3.0.0
     */
    List<AllotProvince> selectAllotProvinceList();

    /**
     * 查询管理号下申请中的省编号
     * 
     * @Description:
     * @author: likui
     * @created: 2016年1月7日 下午5:59:53
     * @param mEntResNo
     * @param provinceNo
     * @return
     * @return : List<String>
     * @version V3.0.0
     */
    List<String> selectApplyingProvince(@Param("mEntResNo") String mEntResNo, @Param("provinceNo") String provinceNo);

    /**
     * 查询申请中的城市编号
     * 
     * @Description:
     * @author: likui
     * @created: 2016年1月7日 下午5:39:07
     * @param cityNo
     * @param provinceNo
     * @return
     * @return : List<String>
     * @version V3.0.0
     */
    List<String> selectApplyingCity(@Param("provinceNo") String provinceNo, @Param("cityNo") String cityNo);

    /**
     * 统计管理公司下的企业资源
     * 
     * @Description:
     * @author: likui
     * @created: 2016年2月18日 上午10:35:37
     * @param mEntResNo
     * @return
     * @return : CompanyRes
     * @version V3.0.0
     */
    CompanyRes statResCompanyResM(@Param("mEntResNo") String mEntResNo);

    /**
     * 分页分组统计服务公司的企业资源
     * 
     * @Description:
     * @author: likui
     * @created: 2016年2月18日 上午10:35:41
     * @param mEntResNo
     * @return
     * @return : List<CompanyResS>
     * @version V3.0.0
     */
    List<CompanyResS> queryCompanyResMByListPage(@Param("mEntResNo") String mEntResNo);

    /**
     * 初始化管理公司配额
     * 
     * @param entResNo
     *            管理公司互生号
     * @param entCustName
     *            管理公司名称
     * @param totalNum
     *            最大配额数
     */
    void initManageQuota(@Param("entResNo") String entResNo, @Param("entCustName") String entCustName,
            @Param("totalNum") Integer totalNum);

    /**
     * 判断管理公司初始化最大配额是否存在，结果大于0为存在
     * 
     * @param entResNo
     *            管理公司互生号
     * @return
     */
    int existInitManageQuota(@Param("entResNo") String entResNo);

    /**
     * 更新管理公司初始化最大配额
     * 
     * @param entResNo
     *            管理公司互生号
     * @param entCustName
     *            管理公司名称
     * @param totalNum
     *            最大配额数
     */
    void updateInitManageQuota(@Param("entResNo") String entResNo, @Param("entCustName") String entCustName,
            @Param("totalNum") Integer totalNum);

    /**
     * 统计城市配额分配使用情况
     * @param provinceNo 省代码
     * @param cityNo 城市代码
     * @return
     */
    QuotaStatOfCity statQuotaByCity(@Param("provinceNo") String provinceNo, @Param("cityNo") String cityNo);

}
