package com.gy.hsxt.access.web.aps.services.codeclaration;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.base.OptInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.codeclaration
 * @className     : IEntDeclareService.java
 * @description   : 企业申报接口
 * @author        : maocy
 * @createDate    : 2015-12-22
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public interface IEntDeclareService extends IBaseService{
	
	/**
	 * 方法名称：依据互生号查询企业信息
     * 方法描述：企业申报-依据互生号查询企业信息
	 * @param resNo 企业互生号
	 * @return 企业信息
	 * @throws HsException
	 */
    public BsEntMainInfo findEntInfo(String resNo) throws HsException;
    
    /**
     * 
     * 方法名称：申报提交
     * 方法描述：企业申报-申报提交
     * @param applyId 申请编号
     * @throws HsException
     */
    public void submitDeclare(String applyId, OptInfo operator) throws HsException;
    
    /**
     * 
     * 方法名称：查询服务公司可用互生号
     * 方法描述：企业申报-查询服务公司可用互生号
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findSerResNos(Map filterMap, Map sortMap, Page page) throws HsException;
    
    /**
     * 
     * 方法名称：查询成员企业、托管企业可用互生号
     * 方法描述：企业申报-查询成员企业、托管企业可用互生号
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findEntResNos(Map filterMap, Map sortMap, Page page) throws HsException;

    /**
     * 查询推广服务公司互生号资源详情
     * @param resNo 互生号
     * @return
     * @throws HsException
     */
    AsEntExtendInfo findResNoDetail(String resNo) throws HsException;

    /**
     * 校验互生号是否可用
     * @param entResNo 企业资源号
     * @return
     * @throws HsException
     */
    Boolean checkValidEntResNo( String entResNo) throws HsException;
}
