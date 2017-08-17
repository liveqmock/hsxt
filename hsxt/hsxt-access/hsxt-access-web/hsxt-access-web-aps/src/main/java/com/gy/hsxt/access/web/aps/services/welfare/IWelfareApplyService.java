package com.gy.hsxt.access.web.aps.services.welfare;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.bean.ApprovalDetail;
import com.gy.hsxt.ws.bean.ApprovalQueryCondition;
import com.gy.hsxt.ws.bean.ApprovalRecord;
import com.gy.hsxt.ws.bean.WelfareApplyRecord;

import java.util.Map;

/**
 * 积分福利--申请及查询
 * 
 * @category 积分福利审批及查询
 * @projectName hsxt-access-web-aps
 * @package com.gy.hsxt.access.web.aps.services.welfare.IWelfareApprovalService.java
 * @className IWsApprovalService
 * @description 积分福利--审批及查询
 * @author leiyt
 * @createDate 2015-12-29 下午3:21:05
 * @updateUser leiyt
 * @updateDate 2015-12-29 下午3:21:05
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public interface IWelfareApplyService extends IBaseService{


	/**
	 * 查询消费者积分福利申请记录
//	 * @param custId 		用户ID
//	 * @param welfareType	福利类型 0 意外伤害 1 免费医疗 2 身故保障
	 * @param page			分页参数
	 * @return
	 */
	PageData<WelfareApplyRecord> listWelfareApply(Map filterMap, Map sortMap,Page page) throws HsException;


}
