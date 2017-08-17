/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.services.goodsManagement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.bean.goodsManage.GoodsType;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;


/**
 * 商品分类service
 * 
 * @Package: com.gy.hsxt.access.web.business.accountManage.service  
 * @ClassName: IIntegralAccountService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-10-13 下午6:22:27 
 * @version V3.0.0
 */

@SuppressWarnings("rawtypes")
@Service
public interface IGoodsTypeService extends IBaseService {

    public List<GoodsType> findAllGoodsType(String custId) throws HsException;
}
