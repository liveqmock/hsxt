package com.gy.hsxt.uc.common.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.uc.common.bean.NetworkInfo;

/**
 * 网络信息数据库操作接口
 * 
 * @Package: com.gy.hsxt.uc.common.mapper  
 * @ClassName: NetworkInfoMapper 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-25 下午2:10:25 
 * @version V1.0
 */
public interface NetworkInfoMapper {

	/**
	 * 根据ID删除
	 * @param perCustId
	 * @return
	 */
    int deleteByPrimaryKey(String perCustId);

    /**
     * 添加
     * @param record 网络信息
     * @return
     */
    int insert(NetworkInfo record);

    /**
     * 添加
     * @param record 网络信息
     * @return
     */
    int insertSelective(NetworkInfo record);

    /**
     * 根据ID查找网络信息
     * @param perCustId ID
     * @return
     */
    NetworkInfo selectByPrimaryKey(String perCustId);

    /**
     * 根据ID修改网络信息
     * @param record 网络信息对象
     * @return
     */
    int updateByPrimaryKeySelective(NetworkInfo record);

    /**
     * 根据ID修改网络信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(NetworkInfo record);
    
    /**
     * 根据多个客户号查询网络信息
     * @param custIds 多个客户号，中间使用英文逗号分隔
     * @return
     */
    List<NetworkInfo> searchByCustIds(List<String> custIds);
    
    /**
     * 批量添加
     * @param record 网络信息对象
     * @return
     */
    int batchInsertSelective(List<NetworkInfo> list);
    
    /**
     * 批量删除（逻辑删除）
     * @param record 网络信息对象
     * @return
     */
    int batchDeleteByPrimaryKey(@Param("isactive") String isactive,
            @Param("updateDate") Timestamp updateDate,@Param("updatedby") String updatedby,@Param("list") List<String> list);
}