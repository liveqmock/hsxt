package com.gy.hsxt.access.web.person.services.consumer;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.apply.DeclareEntBaseInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.bean.AccidentSecurityApply;
import com.gy.hsxt.ws.bean.MedicalSubsidiesApply;
import com.gy.hsxt.ws.bean.OthersDieSecurityApply;
import com.gy.hsxt.ws.bean.WelfareApplyDetail;
import com.gy.hsxt.ws.bean.WelfareApplyRecord;
import com.gy.hsxt.ws.bean.WelfareQualify;

/**
 * 
 * 积分福利服务接口
 * 
 * @category 积分福利服务接口
 * @projectName hsxt-access-web-person
 * @package com.gy.hsxt.access.web.person.services.consumer.IWelfareService.java
 * @className IWelfareService
 * @description 积分福利服务接口
 * @author leiyt
 * @createDate 2015-12-29 下午4:52:53
 * @updateUser leiyt
 * @updateDate 2015-12-29 下午4:52:53
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public interface IWelfareService extends IBaseService {
	/**
	 * 查询当前用户是否具有福利资格
	 * 
	 * @param hsResNo
	 *            互生号
	 * @param welfareType
	 *            福利类型 0 意外伤害 1 免费医疗
	 * @return True:有资格 False：没有资格
	 * @throws HsException
	 */
	public WelfareQualify findWelfareQualify(String hsResNo) throws HsException;

	/**
	 * 意外伤害保障申请
	 * 
	 * @param record
	 * @throws HsException
	 */
	public void applyAccidentSecurity(AccidentSecurityApply record) throws HsException;

	/**
	 * 免费医疗补贴申请
	 * 
	 * @param record
	 * @throws HsException
	 */
	public void applyMedicalSubsidies(MedicalSubsidiesApply record) throws HsException;
	
	/**
	 * 代他人申请身故保障金
	 * 
	 * @param record
	 * @throws HsException
	 */
	public void applyDieSecurity(OthersDieSecurityApply record) throws HsException;
	/**
	 * 积分福利申请记录详情查询
	 * 
	 * @param applyWelfareNo
	 * @return
	 * @throws HsException
	 */
	public WelfareApplyDetail queryWelfareApplyDetail(String applyWelfareNo) throws HsException;
	
}
