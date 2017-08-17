package com.gy.hsxt.access.web.common.service;

import java.util.Map;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;

/***************************************************************************
 * <PRE>
 * Description 		: Simple to Introduction
 * 
 * Project Name   	: hsxt-access-web-person
 * 
 * Package Name     : com.gy.hsxt.access.web.service
 * 
 * File Name        : BaseServiceImpl
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-8-18 下午3:00:09  
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-8-18 下午3:00:09
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
public class BaseServiceImpl<T> implements IBaseService {

    /**
     * 根据默认的请求参数进行分页查询。
     * 
     * 
     * @param no        当前第几页
     * @param size      每页显示条数
     * @param filterMap 过滤条件，如：filterMap.put("search_id", 12)。 可以为null,条件必须“search_”开头
     * @param sortMap   排序条件，如：sortMap.put("sort_id", "desc"); asc为正序，desc为倒序。
     *                  可以为null,条件必须“sort_”开头
     */
    @Override
    public PageData<?> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object findById(Object id) throws HsException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String save(String entityJsonStr)throws HsException {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public void checkVerifiedCode(String custId, String verifiedCode) throws HsException {
        System.out.println("["+verifiedCode+"]验证码校验.....");
    }

}
