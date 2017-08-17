/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.controllers.common;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.bean.MCSDoubleOperator;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.bizfile.IBSBizFileService;
import com.gy.hsxt.bs.api.task.IBSTaskService;
import com.gy.hsxt.bs.bean.bizfile.BizDoc;
import com.gy.hsxt.bs.bean.bizfile.ImageDoc;
import com.gy.hsxt.bs.bean.bizfile.NormalDoc;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.auth.IUCAsPermissionService;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.common.IUCAsTokenService;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.as.bean.auth.AsPermission;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/***
 * 公共接口实现类
 * 
 * @Package: com.gy.hsxt.access.web.mcs.services.common  
 * @ClassName: CommServiceImpl 
 * @Description: TODO
 *
 * @author: wangjg 
 * @date: 2015-12-3 上午11:20:43 
 * @version V1.0
 */
@Service("commService")
public class CommServiceImpl extends BaseServiceImpl implements ICommService {

    @Resource
    private IUCAsTokenService ucAsTokenService;
    
    @Resource
    private IBSBizFileService bsBizFileService;//BS服务类
    
    // 密码管理服务类
    @Resource
    private IUCAsPwdService ucAsPwdService;
    
    @Resource
    private IUCAsOperatorService iUCAsOperatorService;
    
    /**
   	 * 调用互生权限接口
   	 */
   	@Resource
   	private IUCAsPermissionService iuCAsPermissionService;

	/**
	 * 工单Service
	 */
	@Resource
	private IBSTaskService iBSTaskService;
    
    /**
     * 获取随机token 未登录使用
     * 
     * @return
     */
    @Override
    public String getRandomToken() {
        return this.getRandomToken("");
    }

    /**
     * 获取随机token 用户已登录使用
     * 
     * @param custId
     * @return
     */
    @Override
    public String getRandomToken(String custId) {
        return ucAsTokenService.getRandomToken(custId);
    }

    /**
     * token验证
     * 
     * @param custId
     * @param random
     * @return
     */
    @Override
    public boolean checkRandomToken(String custId, String random) {
        return ucAsTokenService.checkRandomToken(custId, random);
    }
    
    /**
     * 
     * 方法名称：获取示例图片管理列表
     * 方法描述：获取示例图片管理列表
     * @return
     * @throws HsException
     */
	public HashMap<String, String> findImageDocList() throws HsException {
		List<ImageDoc> list = this.bsBizFileService.getImageDocList(2);
		if(list == null){
			return null;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		for(ImageDoc obj : list){
			map.put(obj.getDocCode(), obj.getFileId());
		}
    	return map;
	}
	
	/**
     * 方法名称：查询办理业务文档列表
     * 方法描述：查询办理业务文档列表
     * @return
     * @throws HsException
     */
    public HashMap<String, BizDoc> findBizDocList() throws HsException {
    	PageData<BizDoc> pd = this.bsBizFileService.getBizDocList(null, 2, new Page(0, 100));
    	if(pd == null){
    		return null;
    	}
    	HashMap<String, BizDoc> map = new HashMap<String, BizDoc>();
		for(BizDoc obj : pd.getResult()){
			map.put(obj.getDocCode(), obj);
		}
    	return map;
	}
    
    /**
     * 方法名称：查询常用业务文档列表
     * 方法描述：查询常用业务文档列表
     * @return
     * @throws HsException
     */
    public HashMap<String, NormalDoc> findNormalDocList() throws HsException {
    	PageData<NormalDoc> pd = this.bsBizFileService.getNormalDocList(null, 2, new Page(0, 100));
		if(pd == null){
    		return null;
    	}
		HashMap<String, NormalDoc> map = new HashMap<String, NormalDoc>();
		for(NormalDoc obj : pd.getResult()){
			map.put(obj.getDocCode(), obj);
		}
    	return map;
	}

    /**
     * 验证双签用户 
     * @param mdo
     * @throws HsException 
     * @see com.gy.hsxt.access.web.mcs.controllers.common.ICommService#verifyDoublePwd(com.gy.hsxt.access.web.bean.MCSDoubleOperator)
     */
    @Override
    public String verifyDoublePwd(MCSDoubleOperator mdo) throws HsException {
        // 双签验证(只针对管理公司用户验证)
        return ucAsPwdService.checkMcsReCheckerLoginPwd(mdo.getEntResNo(), mdo.getDoubleUserName(), mdo
                .getDoublePwd(), mdo.getRandomToken(), mdo.getCustId());
    }
    
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

	@Override
	public List<AsPermission> findPermByCustId(String platformCode,String subSystemCode, Short permType, String custId) throws HsException {
		//查询用户提供dubbo服务
		return iuCAsPermissionService.listPermByCustId(platformCode, subSystemCode, permType, custId);
	}
	 /**
     * 方法名称：依据客户号获取操作员信息
     * 方法描述：依据客户号获取操作员信息
     * @param custId
     * @return
     * @throws HsException
     */
    @Override
	public AsOperator searchOperByCustId(String custId) {
		return this.iUCAsOperatorService.searchOperByCustId(custId);
	}

	/**
	 * 工单拒绝办理
	 *
	 * @Description:
	 * @param bizNo
	 *            业务编号
	 * @param exeCustId
	 *            经办人
	 * @throws HsException
	 */
	@Override
	public void workTaskRefuseAccept(String bizNo, String exeCustId) throws HsException
	{
		try
		{
			iBSTaskService.updateTaskStatus(bizNo, TaskStatus.UNACCEPT.getCode(), exeCustId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "wordTaskHangUp", "调用BS工单拒绝办理失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 工单挂起
	 *
	 * @Description:
	 * @param bizNo
	 *            业务编号
	 * @param exeCustId
	 *            经办人
	 * @throws HsException
	 */
	@Override
	public void workTaskHangUp(String bizNo, String exeCustId, String remark) throws HsException
	{
		try
		{
			iBSTaskService.updateTaskStatus(bizNo, TaskStatus.HANG_UP.getCode(), exeCustId,remark);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "wordTaskHangUp", "调用BS工单挂起失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}
}
