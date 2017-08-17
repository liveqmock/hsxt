/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.services.goodsManagement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.bean.goodsManage.GoodsType;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.exception.HsException;

@SuppressWarnings("rawtypes")
@Service
public class GoodsTypeServiceImpl extends BaseServiceImpl implements IGoodsTypeService {
    @Override
    public List<GoodsType> findAllGoodsType(String custId) throws HsException {
        List<GoodsType> result = new ArrayList<GoodsType>();
        GoodsType type1 = new GoodsType("0",null,"商品分类");
        GoodsType type2 = new GoodsType("1","0","一级目录1");
        GoodsType type3 = new GoodsType("11","1","二级目录1");
        GoodsType type4 = new GoodsType("111","11","三级目录1");
        GoodsType type5 = new GoodsType("112","11","三级目录2");
        
        result.add(type1);
        result.add(type2);
        result.add(type3);
        result.add(type4);
        result.add(type5);
        return result;
    }

}
