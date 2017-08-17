package com.gy.hsxt.access.web.common.service;

import java.util.Map;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/***************************************************************************
 * <PRE>
 * Description 		: BaseService超类
 * 
 * Project Name   	: hsxt-access-web-person
 * 
 * Package Name     : com.gy.hsxt.access.web.service
 * 
 * File Name        : BaseService
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-8-14 下午3:02:55    
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-8-14 下午3:02:55  
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v0.0.1
 * 
 * </PRE>
 ***************************************************************************/
public interface IBaseService<T> {
    /**
     * 根据条件查询分页数据
     * 
     * @param filterMap     查询条件
     * @param sortMap       排序条件
     * @param page          分页属性
     * @return
     */
    public abstract PageData<?> findScrollResult(Map<String, Object> filterMap, Map<String, Object> sortMap, Page page)
            throws HsException;

    /**
     * 根据id执行查询
     * 
     * @param id
     * @return
     */
    public abstract Object findById(Object id) throws HsException;

    /**
     * 添加方法
     * 
     * @param entityJsonStr     用户实体
     * @return
     */
    public abstract String save(String entityJsonStr) throws HsException;

  
    /***
     * 页面验证码校验 
     * @param custId
     * @param verifiedCode
     * @throws HsException
     */
    public void checkVerifiedCode(String custId,String verifiedCode) throws HsException;
}
