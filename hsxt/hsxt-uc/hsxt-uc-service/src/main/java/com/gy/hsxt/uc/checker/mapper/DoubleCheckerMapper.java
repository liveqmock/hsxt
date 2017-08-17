package com.gy.hsxt.uc.checker.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.uc.checker.bean.DoubleChecker;

public interface DoubleCheckerMapper {

    int deleteByPrimaryKey(String operCustId);

    int insertSelective(DoubleChecker record);
    /**
     * 通过客户号查询双签员信息
     * @param operCustId
     * @return
     */
    DoubleChecker selectByPrimaryKey(String operCustId);
    /**
     * 更新双签员信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(DoubleChecker record);
    
    /**
     * 通过系统平台code和子系统code查询双签员列表
     * @param platformCode
     * @param subSystemCode
     * @param page
     * @return
     */
    List<DoubleChecker> ListDoubleCheckerInfo(@Param("platformCode") String platformCode,@Param("subSystemCode") String subSystemCode,
			@Param("page") Page page);
    
    /**
     * 统计双签员的记录数
     * @param platformCode
     * @param subSystemCode
     * @return
     */
    int countDoubleChecker(@Param("platformCode") String platformCode,@Param("subSystemCode") String subSystemCode);
}