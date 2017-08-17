package com.gy.hsxt.uc.batch.mapper;

import java.util.List;
import java.util.Map;

public interface BatchMapper {
    /**
     * 更新用户积分时间  map key：pvDate 最后积分变化时间，idList 客户id列表
     * @param map
     * @return
     */
    int updateCustPvDate(Map<String, Object> map);
    
    /**
     * 根据用户最后积分时间更新用户沉默状态
     * @param months0 休眠月数(预期值: 12)
     * @param months1 沉淀月数 (预期值:36)
     * @return
     */
    int updateCustStatus(Map<String, Object> map);

    /**
     * 更新企业积分时间  map key：pvDate 最后积分变化时间，idList 客户id列表
     * @param map
     * @return
     */
    int updateEntPvDate(Map<String, Object> map);
    
    /**
     * 更新企业年费状态
     * @param list
     * @return
     */
    int updateEntAnnualFee(List<Map<String, String>> list);

   /**
    * 根据企业最后积分时间更新企业沉默状态
    * @param months0 预警月数 (预期值:1)
    * @param months1 休眠月数 (预期值:12)
    * @param months2 长眠月数(预期值: 24)
    * @return
    */
    int updateEntStatus(Map<String, Object> map);
    
    /**
     * 根据企业缴年费截止日期更新企业年费欠费状态， EXPIRE_DATE < sysdate 则 set IS_OWE_FEE=1
     * @return
     */
    int updateEntIsOweFee();
    
    /**
     * 更新企业税率
     * @return
     */
    int updateEntTaxRate();
    
    /**
     * 查询需要更新税率的企业及新税率
     * @return
     */
    List<Map<String, Object> > selectUpdateEntTaxRate();
}
