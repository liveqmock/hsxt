package com.gy.hsxt.uc.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.uc.common.bean.ReceiveAddr;
/**
 * 收货地址数据库操作类
 * 
 * @Package: com.gy.hsxt.uc.common.mapper  
 * @ClassName: ReceiveAddrMapper 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-24 下午3:03:50 
 * @version V1.0
 */
public interface ReceiveAddrMapper {

	/**
	 * 删除收货地址
	 * @param addrId
	 * @return
	 */
    int deleteByPrimaryKey(Long addrId);

    /**
     * 添加收货地址
     * @param record
     * @return
     */
    int insert(ReceiveAddr record);

    /**
     * 添加收货地址
     * @param record
     * @return
     */
    int insertSelective(ReceiveAddr record);

    /**
     * 根据ID查找收货地址
     * @param addrId
     * @return
     */
    ReceiveAddr selectByPrimaryKey(Long addrId);

    /**
     * 根据ID修改收货地址
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ReceiveAddr record);

    /**
     * 根据ID修改收货地址
     * @param record
     * @return
     */
    int updateByPrimaryKey(ReceiveAddr record);
    
    /**
     * 根据客户号查找可用的收货地址
     * @param custId
     * @return
     */
    List<ReceiveAddr> selectByCustId(String custId);
    
    /**
     * 根据客户号设置收货地址为非默认地址
     * @param custId
     */
    void setNoDefaultByCustId(String custId);
    
    /**
     * 查询默认的
     * @return
     */
    ReceiveAddr selectDefaultAddr();
    
    /**
     * 根据客户号和收货地址编号ID查询收货地址信息
     * @param custId
     * @param addrId
     * @return
     */
    ReceiveAddr selectAddrInfo(@Param("custId") String custId, @Param("addrId") Long addrId );
    
    /**
     * 根据客户号查询默认的收货地址信息
     * @param custId
     * @return
     */
    ReceiveAddr selectDefaultAddrInfo(String custId);
}