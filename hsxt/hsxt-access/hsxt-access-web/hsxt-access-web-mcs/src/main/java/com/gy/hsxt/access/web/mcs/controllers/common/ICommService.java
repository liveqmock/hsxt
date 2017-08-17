/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.controllers.common;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.access.web.bean.MCSDoubleOperator;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.bizfile.BizDoc;
import com.gy.hsxt.bs.bean.bizfile.NormalDoc;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;
import com.gy.hsxt.uc.as.bean.auth.AsPermission;

/***
 * 公共接口类
 * 
 * @Package: com.gy.hsxt.access.web.mcs.services.common
 * @ClassName: ICommService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-12-3 上午11:20:55
 * @version V1.0
 */
public interface ICommService extends IBaseService {
    /**
     * 获取随机token 未登录使用
     * 
     * @return
     */
    public String getRandomToken();

    /**
     * 获取随机token 用户已登录使用
     * 
     * @param custId
     * @return
     */
    public String getRandomToken(String custId);

    /**
     * 验证tooken
     * 
     * @param custId
     * @param random
     * @return
     */
    public boolean checkRandomToken(String custId, String random);
    
    /**
     * 
     * 方法名称：获取示例图片管理列表
     * 方法描述：获取示例图片管理列表
     * @return
     * @throws HsException
     */
	public Map<String, String> findImageDocList() throws HsException;
	
	/**
     * 方法名称：查询办理业务文档列表
     * 方法描述：查询办理业务文档列表
     * @return
     * @throws HsException
     */
    public Map<String, BizDoc> findBizDocList() throws HsException;
    
    /**
     * 方法名称：查询常用业务文档列表
     * 方法描述：查询常用业务文档列表
     * @return
     * @throws HsException
     */
    public Map<String, NormalDoc> findNormalDocList() throws HsException;
    
    /**
     * 验证双签用户 
     * @param mdo
     * @throws HsException
     */
    public String verifyDoublePwd(MCSDoubleOperator mdo) throws HsException;
    
    /**
     * 查询用户拥有的权限 ,排除权限记录状态=N的记录，可附加过滤条件：平台，子系统,权限类型
     * 
     * @param platformCode 平台代码
     *            null则忽略该条件
     * @param subSystemCode 子系统代码
     *            null则忽略该条件
     * @param permType 权限类型 0：菜单  1：功能
     *            null则忽略该条件
     * @param custId 客户编号
     * @return
     * @throws HsException
     */
	public List<AsPermission> findPermByCustId(String platformCode,String subSystemCode, Short permType, String custId) throws HsException ;
    /**
     * 方法名称：依据客户号获取操作员信息
     * 方法描述：依据客户号获取操作员信息
     * @param custId
     * @return
     * @throws HsException
     */
    public AsOperator searchOperByCustId(String custId);

    /**
     * 工单拒绝办理
     *
     * @Description:
     * @author: likui
     * @created: 2016年4月11日 上午11:08:31
     * @param bizNo
     *            业务编号
     * @param exeCustId
     *            经办人
     * @throws HsException
     * @return : void
     * @version V3.0.0
     */
    public void workTaskRefuseAccept(String bizNo, String exeCustId) throws HsException;

    /**
     * 工单挂起
     *
     * @Description:
     * @author: likui
     * @created: 2016年4月11日 上午11:08:47
     * @param bizNo
     *            业务编号
     * @param exeCustId
     *            经办人
     * @throws HsException
     * @return : void
     * @version V3.0.0
     */
    public void workTaskHangUp(String bizNo, String exeCustId, String remark) throws HsException;
}
