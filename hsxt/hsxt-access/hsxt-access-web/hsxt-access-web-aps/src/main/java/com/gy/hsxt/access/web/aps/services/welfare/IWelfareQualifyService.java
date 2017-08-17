package com.gy.hsxt.access.web.aps.services.welfare;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.bean.WelfareQualify;

import java.util.List;
import java.util.Map;

/**
 * 
 * 积分福利福利资格接口
 * @category      积分福利福利资格
 * @projectName   hsxt-access-web-aps
 * @package       com.gy.hsxt.access.web.aps.services.welfare.IWelfareQualifyService.java
 * @className     IWelfareQualifyService
 * @description   积分福利福利资格
 * @author        leiyt
 * @createDate    2015-12-31 下午5:03:46  
 * @updateUser    leiyt
 * @updateDate    2015-12-31 下午5:03:46
 * @updateRemark 	说明本次修改内容
 * @version       v0.0.1
 */
public interface IWelfareQualifyService extends IBaseService{
	/**
	 * 分页查询福利资格
	 * @category 			分页查询福利资格
	 * @param hsResNo		互生号
	 * @param welfareType	福利类型 0 意外伤害 1 免费医疗
	 * @param page			分页参数
	 * @return
	 * @throws HsException
	 */
//	public PageData<WelfareQualify> listWelfareQualify(String hsResNo, Integer welfareType,Page page) throws HsException;
	public PageData<WelfareQualify> queryHisWelfareQualify(Map filterMap, Map sortMap, Page page)  throws HsException;
	public List<WelfareQualify>     queryHisWelfareQualify(String resNo) throws HsException;
	/**
	 * 查询个人福利资格信息
	 * @category 			查询个人福利资格信息
	 * @param custId		客户号
	 * @param welfareType	福利类型 0 意外伤害 1 免费医疗
	 * @return
	 * @throws HsException
	 */
	public WelfareQualify findWelfareQualify(String custId) throws HsException;
	/**
	 * 查询是否有福利资格
	 * @category 			查询是否有福利资格
	 * @param custId		客户号
	 * @param welfareType	福利类型 0 意外伤害 1 免费医疗
	 * @return True:有资格 False：没有资格
	 * @throws HsException
	 */
	public boolean isHaveWelfareQualify(String custId, Integer welfareType) throws HsException;


}

	