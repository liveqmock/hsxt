package com.gy.hsxt.uc.common.mapper;

import com.gy.hsxt.uc.common.bean.CustPrivacy;

/**
 * 隐私信息数据库操作接口
 * 
 * @Package: com.gy.hsxt.uc.common.mapper  
 * @ClassName: CustPrivacyMapper 
 * @Description: TODO
 *
 * @author: lvyan 
 * @date: 2016-06-30 下午2:10:25 
 * @version V1.0
 */
public interface CustPrivacyMapper {

	/**
	 * 根据ID删除
	 * @param perCustId
	 * @return
	 */
    int deleteByPrimaryKey(String perCustId);


    /**
     * 添加
     * @param record 隐私信息
     * @return
     */
    int insertSelective(CustPrivacy record);

    /**
     * 根据ID查找隐私信息
     * @param perCustId ID
     * @return
     */
    CustPrivacy selectByPrimaryKey(String perCustId);

    /**
     * 根据ID修改隐私信息
     * @param record 隐私信息对象
     * @return
     */
    int updateByPrimaryKeySelective(CustPrivacy record);

}