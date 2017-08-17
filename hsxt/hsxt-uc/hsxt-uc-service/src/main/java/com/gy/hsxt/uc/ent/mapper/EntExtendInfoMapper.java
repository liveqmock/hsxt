package com.gy.hsxt.uc.ent.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.uc.as.bean.ent.AsEntContactInfo;
import com.gy.hsxt.uc.as.bean.ent.AsQueryBelongEntCondition;
import com.gy.hsxt.uc.as.bean.ent.AsQueryEntCondition;
import com.gy.hsxt.uc.ent.bean.BelongEntInfo;
import com.gy.hsxt.uc.ent.bean.EntExtendInfo;

/**
 * 企业扩展信息 Mapper
 * 
 * @Package: com.gy.hsxt.uc.ent.mapper
 * @ClassName: EntExtendInfoMapper
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-11 上午9:29:37
 * @version V1.0
 */
public interface EntExtendInfoMapper {

	/**
	 * 删除企业扩展信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @return
	 */
	int deleteByPrimaryKey(String entCustId);

	/**
	 * 插入企业扩展信息
	 * 
	 * @param record
	 *            企业扩展信息
	 * @return
	 */
	int insert(EntExtendInfo record);

	/**
	 * 插入企业扩展信息（有值的属性插入）
	 * 
	 * @param record
	 *            企业扩展信息
	 * @return
	 */
	int insertSelective(EntExtendInfo record);

	/**
	 * 查询企业扩展信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @return
	 */
	EntExtendInfo selectByPrimaryKey(String entCustId);

	/**
	 * 通过企业的客户类型查询企业的信息
	 * 
	 * @param custType
	 * @return
	 */
	List<EntExtendInfo> listEntendsInfo(Integer custType);

	/**
	 * 通过城市编号列表查询企业列表
	 * @param cityList
	 * @return
	 */
	List<EntExtendInfo> listCityListEntInfo(List<String> cityList);
	
	/**
	 * 查询企业数量 通过企业的名称
	 * 
	 * @param entName
	 * @return
	 */
	int countEntByEntName(String entName);
	/**
	 * 查询企业数量 通过企业的名称及类型
	 * 
	 * @param entName
	 * @return
	 */
	int countEntByEntNameAndType(String entName,Integer custType);
	
	int countEntLn(String entName );
	/**
	 * 检查企业营业执照号是否存在，可附加条件：客户类型
	 * 
	 * @param busiLicenseNo
	 *            营业执照号
	 * @param custType
	 *            企业类型 2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台;
	 * @return
	 */
	int countEntLnByType(String busiLicenseNo,Integer custType);

	/**
	 * 更新企业扩展信息（有值的属性更新）
	 * 
	 * @param record
	 *            企业扩展信息
	 * @return
	 */
	int updateByPrimaryKeySelective(EntExtendInfo record);

	/**
	 * 更新企业扩展信息
	 * 
	 * @param record
	 *            企业扩展信息
	 * @return
	 */
	int updateByPrimaryKey(EntExtendInfo record);

	/**
	 * 分页查询隶属企业信息
	 * 
	 * @param condition
	 *            分页查询条件
	 * @param page
	 *            分页参数
	 * @return
	 */
	List<BelongEntInfo> listBelongEnt(@Param("condition") AsQueryBelongEntCondition condition,
			@Param("entCustType") Integer entCustType, @Param("page") Page page);

	/**
	 * 分页查询隶属企业信息
	 * 
	 * @param condition
	 *            分页查询条件
	 * @param page
	 *            分页参数
	 * @return
	 */
	List<BelongEntInfo> listAllBelongEnt(@Param("condition") AsQueryBelongEntCondition condition,
			@Param("entCustType") Integer entCustType, @Param("page") Page page);

	
	/**
	 * 统计隶属企业的总数量
	 * 
	 * @param condition
	 *            分页查询条件
	 * @return
	 */
	int countBlongEnt(@Param("condition") AsQueryBelongEntCondition condition,
			@Param("entCustType") Integer entCustType);

	/**
	 * 统计隶属企业的总数量
	 * 
	 * @param condition
	 *            分页查询条件
	 * @return
	 */
	int countAllBlongEnt(@Param("condition") AsQueryBelongEntCondition condition,
			@Param("entCustType") Integer entCustType);
	
	
	
	List<AsEntContactInfo> selectEntContactInfo(String custId);
	
	/**
	 * 批量查询企业的客户号列表
	 * @param list	企业互生号列表
	 */
	List<EntExtendInfo>  batchSelectEntTaxRate(List<String> list);
	
	/**
	 * 根据条件统计查询到的企业数量
	 * @param param
	 * @return
	 */
	int selectEntCountByCondition(@Param("param") AsQueryEntCondition param,  @Param("page") Page page);
	
	/**
	 * 根据条件查询企业
	 * @param param 参数
	 * @param page
	 * @return
	 */
	List<BelongEntInfo> selectEntByCondition(@Param("param") AsQueryEntCondition param,  @Param("page") Page page);
	
}