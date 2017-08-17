/**
 * 
 */
package com.gy.hsxt.access.web.person.services.common;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.bs.api.bizfile.IBSBizFileService;
import com.gy.hsxt.bs.bean.bizfile.BizDoc;
import com.gy.hsxt.bs.bean.bizfile.ImageDoc;
import com.gy.hsxt.bs.bean.bizfile.NormalDoc;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.common.IUCAsTokenService;

/**
 * @projectName : hsxt-access-web-scs
 * @package : com.gy.hsxt.access.web.scs.services.common
 * @className : PubParamService.java
 * @description :
 * @author : maocy
 * @createDate : 2015-11-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Service
public class PubParamService implements IPubParamService {

    // 公共参数接口
    @Autowired
    private LcsClient lcsClient;
    
    @Autowired
    private IBSBizFileService bsService;//BS服务类

    // 获取登陆tonken
    @Resource
    private IUCAsTokenService ucAsTokenService;

    /**
     * 
     * 方法名称：获取系统信息 方法描述：获取系统配置信息
     * 
     * @return 系统信息
     * @throws HsException
     */
    public LocalInfo findSystemInfo() throws HsException {
        return lcsClient.getLocalInfo();
    }

    /**
     * 取随机token 
     * 客户号为空    代表未登录的获取，
     * 客户号不为空    用户已登录使用
     * @param custId 客户号 
     * @return
     * @see com.gy.hsxt.access.web.person.services.common.IPubParamService#getRandomToken(java.lang.String)
     */
    @Override
    public String getRandomToken(String custId) {
        return this.ucAsTokenService.getRandomToken(custId);
    }
    
    /**
     * 
     * 方法名称：获取示例图片管理列表
     * 方法描述：获取示例图片管理列表
     * @return
     * @throws HsException
     */
	public HashMap<String, String> findImageDocList() throws HsException {
		List<ImageDoc> list = this.bsService.getImageDocList(2);
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
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public HashMap<String, BizDoc> findBizDocList() throws HsException {
    	PageData<BizDoc> pd = this.bsService.getBizDocList(null, 2, new Page(0, 100));
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
    	PageData<NormalDoc> pd = this.bsService.getNormalDocList(null, 2, new Page(0, 100));
		if(pd == null){
    		return null;
    	}
		HashMap<String, NormalDoc> map = new HashMap<String, NormalDoc>();
		for(NormalDoc obj : pd.getResult()){
			map.put(obj.getDocCode(), obj);
		}
    	return map;
	}

}
