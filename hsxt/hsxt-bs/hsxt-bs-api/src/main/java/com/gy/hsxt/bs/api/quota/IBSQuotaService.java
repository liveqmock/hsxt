/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.api.quota;

import java.util.List;

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
import com.gy.hsxt.bs.bean.quota.result.ResInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 配额的申请接口
 * 
 * @Package: com.gy.hsxt.bs.api.quota
 * @ClassName: IBSQuotaService
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:24:43
 * @company: gyist
 * @version V3.0.0
 */
public interface IBSQuotaService {

	/**
	 * 一级区域配额分配申请(管理公司列表)
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月19日 下午3:26:41
	 * @return
	 * @return : List<PlatAppManage>
	 * @version V3.0.0
	 */
	public List<PlatAppManage> queryPlatAppManageList();

	/**
	 * 地区平台(一级区域)资源配额申请
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月18日 下午12:01:17
	 * @param bean
	 *            地区平台资源配额申请实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void applyPlatQuota(PlatQuotaApp bean) throws HsException;

	/**
	 * 
	 * @author:likui
	 * @created:2015年9月2日下午4:33:19
	 * @desc:根据id查询地区平台(一级区域)配额申请详情
	 * @param applyId
	 *            申请编号
	 * @return PlatQuotaApp
	 */
	public PlatQuotaApp getPlatQuotaById(String applyId);

	/**
	 * 分页条件查询一级区域资源配额分配单
	 * 
	 * @param entResNo
	 *            管理公司互生号
	 * @param entCustName
	 *            管理公司名称
	 * @param exeCustId
	 *            执行审批操作的客户号
	 * @param page
	 *            分页条件
	 * @return
	 */
	PageData<PlatQuotaApp> queryPlatQuotaPage(String entResNo, String entCustName, String exeCustId, Page page);

	/**
	 * 申请省级区域(二级区域)配额分配
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月18日 下午12:04:41
	 * @param bean
	 *            省级区域配额申请参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void applyProvinceQuota(ProvinceQuotaApp bean) throws HsException;

	/**
	 * 
	 * @author:likui
	 * @created:2015年9月10日下午3:16:07
	 * @desc:分页查询省级配额(二级区域)分配
	 * @param proviceNo
	 *            省编号
	 * @param applyType
	 *            申请类型
	 * @param status
	 *            审批状态
	 * @param exeCustId
	 *            受理人客户号
	 * @param page
	 *            分页参数
	 * @return PageData<ProvinceQuotaApp>
	 */
	public PageData<ProvinceQuotaApp> queryProvinceQuotaPage(String proviceNo, Integer applyType, Integer status,
			String exeCustId, Page page);

	/**
	 * 
	 * @author:likui
	 * @created:2015年9月2日下午4:37:17
	 * @desc:根据id查询省级(二级区域)配额申请
	 * @param applyId
	 *            申请编号
	 * @return ProvinceQuotaApp
	 */
	public ProvinceQuotaApp getProvinceQuotaById(String applyId);

	/**
	 * 
	 * @author:likui
	 * @created:2015年9月2日下午4:39:49
	 * @desc:审批省级(二级区域)配额申请
	 * @param bean
	 *            省级区域配额申请参数实体
	 * @throws HsException
	 */
	public void apprProvinceQuota(ProvinceQuotaApp bean) throws HsException;

	/**
	 * 
	 * @author:likui
	 * @created:2015年9月2日下午5:04:48
	 * @desc:申请市级(三级区域)配额
	 * @param bean
	 *            申请市级配额参数实体
	 * @throws HsException
	 */
	public void applyCityQuota(CityQuotaApp bean) throws HsException;

	/**
	 * 
	 * @author:likui
	 * @created:2015年9月2日下午5:05:01
	 * @desc:根据id查询市级(三级区域)配额
	 * @param applyId
	 *            申请编号
	 * @return CityQuotaApp
	 */
	public CityQuotaApp getCityQuotaById(String applyId);

	/**
	 * 
	 * @author:likui
	 * @created:2015年9月2日下午5:05:04
	 * @desc:审批市级(三级区域)配额申请
	 * @param bean
	 *            申请市级配额参数实体
	 * @throws HsException
	 */
	public void apprCityQuota(CityQuotaApp bean) throws HsException;

	/**
	 * 
	 * @author likui
	 * @created:2015年9月10日下午3:19:16
	 * @desc:分页查询市级(三级区域)配额分配
	 * @param param
	 *            查询条件参数
	 * @param page
	 *            查询分页参数
	 * @return PageData<CityQuotaApp>
	 */
	public PageData<CityQuotaApp> queryCityQuotaPage(CityQuotaQueryParam param, Page page);

	/**
	 * 查询地区平台分配了配额的省
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月25日 下午4:51:14
	 * @return
	 * @return : List<AllotProvince>
	 * @version V3.0.0
	 */
	public List<AllotProvince> queryAllotProvinceList();

	/**
	 * 查询省下申请城市配额分配的列表
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月25日 下午5:08:55
	 * @param provinceNo
	 *            省编号
	 * @return
	 * @return : List<QuotaStatOfCity>
	 * @version V3.0.0
	 */
	public List<QuotaStatOfCity> queryAppCityAllotList(String provinceNo);

	/**
	 * 
	 * @author:likui
	 * @created:2015年9月2日下午6:31:52
	 * @desc:统计管理公司下的资源数据
	 * @param mEntResNo
	 *            管理公司互生号
	 * @return QuotaStatOfManager
	 */
	public QuotaStatOfManager statResDetailOfManager(String mEntResNo);

	/**
	 * 
	 * @author:likui
	 * @created:2015年9月2日下午6:33:18
	 * @desc:统计省级(二级区域)下的资源数据
	 * @param provinceNo
	 *            省编号
	 * @param cityNo
	 *            城市编号
	 * @return QuotaDetailOfProvince
	 */
	public QuotaDetailOfProvince statResDetailOfProvince(String provinceNo, String cityNo);

	/**
	 * 
	 * @author:likui
	 * @created:2015年9月2日下午6:34:21
	 * @desc:城市(三级区域)下的资源列表
	 * @param cityNo
	 *            城市编号
	 * @param status
	 *            资源状态
	 * @return List<CityResPage>
	 */
	public List<ResInfo> listResInfoOfCity(String cityNo, String[] status);

	/**
	 * 
	 * @author:likui
	 * @created:2015年9月6日下午12:04:03
	 * @desc:根据管理公司互生号查询可申请二级区域分配的省份 包含已分配该管理公司下级服务资源的省份以及从未进行二级区域分配的省份
	 * @param mEntResNo
	 *            管理公司互生号
	 * @return List<String> 省份代码列表
	 */
	public List<String> listProvinceNoForAllot(String mEntResNo);

	/**
	 * 管理公司配额分配详情
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月19日 下午3:31:47
	 * @param mEntResNo
	 *            管理公司互生号
	 * @return
	 * @return : PlatAppManage
	 * @version V3.0.0
	 */
	public PlatAppManage queryManageAllotDetail(String mEntResNo);

	/**
	 * 判断省份是否已进行首次配置
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月19日 下午3:55:11
	 * @param provinceNo
	 *            省编号
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void isProvinceFristAllot(String provinceNo) throws HsException;

	/**
	 * 判断城市是否已进行首次配置
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月19日 下午5:54:14
	 * @param cityNo
	 *            城市编码
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void isCityFristAllot(String cityNo) throws HsException;

	/**
	 * 
	 * @author:likui
	 * @created:2015年9月6日下午12:05:53
	 * @desc:查询二级区域（省）的总配额数量
	 * @param provinceNo
	 *            省编号
	 * @return Integer
	 */
	public Integer countProvinceQuota(String provinceNo);

	/**
	 * 
	 * @author:likui
	 * @created:2015年9月6日下午2:33:09
	 * @desc:查询三级区域（城市）的总配额数量
	 * @param cityNo
	 *            城市编号
	 * @return Integer
	 */
	public Integer countCityQuota(String cityNo);

	/**
	 * 统计管理号未分配到二级区域(省)的资源配额数量
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月19日 下午4:14:55
	 * @param mEntResNo
	 *            管理公司互生号
	 * @return
	 * @return : Integer
	 * @version V3.0.0
	 */
	public Integer countMResNoFreeQuota(String mEntResNo);

	/**
	 * 
	 * @author:likui
	 * @created:2015年9月6日下午2:33:38
	 * @desc:查询省下未分配到城市的配额数量 查询已分配到二级区域（省份），但未分配到三级区域（城市）的资源配额数量
	 * @param provinceNo
	 *            省编号
	 * @return Integer 二级区域全部资源中未分配到三级区域的资源数量
	 */
	public Integer countFreeProvinceQuota(String provinceNo);

	/**
	 * 
	 * @author:likui
	 * @created:2015年9月6日下午2:33:42
	 * @desc:查询城市下未申报的配额数量 城市配额中既没有被使用，也没有被占用的资源号数量
	 * @param cityNo
	 *            城市编号
	 * @return Integer
	 */
	public Integer countFreeCityQuota(String cityNo);

	/**
	 * 统计管理公司下的企业资源
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年2月18日 上午9:48:01
	 * @param mEntResNo
	 * @return
	 * @return : CompanyRes
	 * @version V3.0.0
	 */
	public CompanyRes statResCompanyResM(String mEntResNo);

	/**
	 * 分页分组统计服务公司的企业资源
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年2月18日 上午10:30:35
	 * @param mEntResNo
	 * @param page
	 * @return
	 * @return : PageData<CompanyResS>
	 * @version V3.0.0
	 */
	public PageData<CompanyResS> queryCompanyResMByPage(String mEntResNo, Page page);

	/**
     * 统计城市配额分配使用情况
     * @param provinceNo 省代码
     * @param cityNo 城市代码
     * @return
     */
    QuotaStatOfCity statQuotaByCity(String provinceNo, String cityNo);
}
