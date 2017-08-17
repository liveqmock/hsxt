/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.bp.api;

import java.util.List;

import com.gy.hsxt.bp.bean.BusinessAgree;
import com.gy.hsxt.bp.bean.BusinessAgreeFee;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/** 
 * 协议参数管理接口
 * @Package: com.gy.hsxt.bp.api  
 * @ClassName: IAgreeParamManagerService 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-11-24 下午3:22:09 
 * @version V1.0 
 

 */
public interface IBusinessAgreeService {

    /**
     * 新增协议参数管理
     * @param BusinessAgree 协议参数管理对象
     * @throws HsException
     */
    public void addAgree(BusinessAgree agreeParamManager) throws HsException;
    
    /**
     * 更新协议参数管理
     * @param BusinessAgree 协议参数管理对象
     * @throws HsException
     */
    public void updateAgree(BusinessAgree agreeParamManager) throws HsException;
    
    /**
     * 删除协议参数管理
     * @param agreeId 协议ID
     * @throws HsException
     */
    public void deleteAgree(String agreeId) throws HsException;
    
    /**
     * 查询单个协议参数管理
     * @param agreeId 协议ID
     * @throws HsException
     */
    public BusinessAgree getAgreeById(String agreeId) throws HsException;
    
    /**
     * 查询多个协议参数管理
     * @param BusinessAgree 协议参数管理对象
     * @param page 分頁對象
     * @return PageData<AgreeParamManager> 封裝协议参数管理对象集合、當前條件的查詢總記錄數
     * @throws HsException
     */
    public PageData<BusinessAgree> searchAgreePage(BusinessAgree agreeParamManager, Page page) throws HsException;
    
    /**
     * 查询所有协议参数管理
     * @return List<AgreeParamManager> 协议参数管理对象集合
     * @throws HsException
     */
    public List<BusinessAgree> searchAgreeList() throws HsException;
    
    
    /**
     * 新增协议费用参数管理
     * @param BusinessAgreeFee 协议费用参数管理对象
     * @throws HsException
     */
    public void addAgreeFee(BusinessAgreeFee businessAgreeFee) throws HsException;
    
    /**
     * 更新协议费用参数管理
     * @param BusinessAgreeFee 协议费用参数管理对象
     * @throws HsException
     */
    public void updateAgreeFee(BusinessAgreeFee businessAgreeFee) throws HsException;
    
    /**
     * 删除协议费用参数管理
     * @param agreeFeeId 协议费用ID
     * @param agreeCode  协议编号
     * @param agreeFeeCode 协议费用编号
     * @throws HsException
     */
    public void deleteAgreeFee(String agreeFeeId,String agreeCode,String agreeFeeCode) throws HsException;
    
    /**
     * 查询单个协议费用参数管理
     * @param agreeFeeId 协议费用ID
     * @throws HsException
     */
    public BusinessAgreeFee getAgreeFeeById(String agreeFeeId) throws HsException;
    
    /**
     * 查询多个协议费用参数管理
     * @param BusinessAgreeFee 协议费用参数管理对象
     * @param page 分頁對象
     * @return PageData<BusinessAgreeFee> 封裝协议费用参数管理对象集合、當前條件的查詢總記錄數
     * @throws HsException
     */
    public PageData<BusinessAgreeFee> searchAgreeFeePage(BusinessAgreeFee businessAgreeFee, Page page) throws HsException;
    
    /**
     * 通过协议编码查询协议费用参数管理
     * @param agreeCode 协议编码
     * @return List<BusinessAgreeFee> 协议费用参数管理对象集合
     * @throws HsException
     */
    public List<BusinessAgreeFee> searchAgreeFeeList(String agreeCode) throws HsException;
    
}
