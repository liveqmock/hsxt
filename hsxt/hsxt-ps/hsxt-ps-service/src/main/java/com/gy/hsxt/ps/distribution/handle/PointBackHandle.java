/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.;LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.distribution.handle;

import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ps.common.Constants;
import com.gy.hsxt.ps.common.PsTools;
import com.gy.hsxt.ps.distribution.bean.PointAllot;

/**
 * @Package: com.gy.hsxt.ps.distribution.bean
 * @ClassName: PointHandle
 * @Description: 积分分配处理
 * 
 * @author: chenhz
 * @date: 2016-2-24 下午2:05:55
 * @version V3.0
 */

public class PointBackHandle
{
	/**
	 * 退货退积分
	 * @param allot
	 * @return
	 */
	public static PointAllot setPointAllot(PointAllot allot)
	{
		PointAllot pointAllot = new PointAllot();
		BeanCopierUtils.copy(allot, pointAllot);
		pointAllot.setAllotNo(GuidAgent.getStringGuid(Constants.NO_POINT_ALLOC20+PsTools.getInstanceNo()));
		pointAllot.setBatchNo(DateUtil.DateToString(DateUtil.today(), DateUtil.DEFAULT_DATE_FORMAT));
		pointAllot.setTrusteeAddPoint(null);
        pointAllot.setServiceAddPoint(null);
        pointAllot.setManageAddPoint(null);
        pointAllot.setPaasAddPoint(null);
        pointAllot.setSurplusAddPoint(null);
		pointAllot.setTrusteeSubPoint(allot.getTrusteeAddPoint());
		pointAllot.setServiceSubPoint(allot.getServiceAddPoint());
		pointAllot.setManageSubPoint(allot.getManageAddPoint());
		pointAllot.setPaasSubPoint(allot.getPaasAddPoint());
		pointAllot.setSurplusSubPoint(allot.getSurplusSubPoint());
		pointAllot.setTransType(TransType.HSB_BUSINESS_POINT_BACK.getCode());
		pointAllot.setIsSettle(Constants.IS_SETTLE1);
		return pointAllot;
	}

}
