package com.gy.hsxt.uc.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.uc.common.bean.PwdQuestion;

/**
 * 密保问题数据库操作接口
 * 
 * @Package: com.gy.hsxt.uc.common.mapper  
 * @ClassName: PwdQuestionMapper 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-23 下午5:15:43 
 * @version V1.0
 */
public interface PwdQuestionMapper {

	/**
	 * 根据主键删除密保问题
	 * @param questionId
	 * @return
	 */
    int deleteByPrimaryKey(Short questionId);

    /**
     * 添加密保问题
     * @param record
     * @return
     */
    int insert(PwdQuestion record);

    /**
     * 添加密保问题
     * @param record
     * @return
     */
    int insertSelective(PwdQuestion record);

    /**
     * 根据主键获取密保问题
     * @param questionId
     * @return
     */
    PwdQuestion selectByPrimaryKey(int questionId);

    /**
     * 根据客户号查询密保列表
     * @param custId
     * @return
     */
    List<PwdQuestion> selectByCustId(String custId);

    /**
     * 根据主键更新密保问题
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(PwdQuestion record);

    /**
     * 根据主键更新密保问题
     * @param record
     * @return
     */
    int updateByPrimaryKey(PwdQuestion record);
    
    /**
     * 批量添加
     * @param record 密保问题
     * @return
     */
    int batchInsertSelective(List<PwdQuestion> list);
    
    /**
     * 批量删除（物理删除）
     * @param record 密保问题
     * @return
     */
    int batchDeleteByPrimaryKey(@Param("list") List<String> list);
}