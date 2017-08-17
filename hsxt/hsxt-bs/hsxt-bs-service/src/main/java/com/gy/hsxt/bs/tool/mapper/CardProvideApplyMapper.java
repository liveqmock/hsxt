/**
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
* <p>
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
*/
package com.gy.hsxt.bs.tool.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.tool.CardProvideApply;

import java.util.List;

/**
 * 互生卡发放申请Mapper
 * 
 * @Package: com.gy.hsxt.bs.tool.mapper.mapping
 * @ClassName: CardProvideApplyMapper
 * @Description:
 * @author: likui
 * @date: 2016/6/23 16:54
 * @company: gyist
 * @version V3.0.0
 */
@Repository(value = "cardProvideApplyMapper")
public interface CardProvideApplyMapper {

    /**
     * 插入互生卡发放申请
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/23 17:22
     * @param: entity
     * @return: int
     * @company: gyist
     * @version V3.0.0
     */
    public int insertCardProvideApply(CardProvideApply entity);

    /**
     * 分页查询互生卡发放申请
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/23 17:23
     * @param: receiver
     * @param: mobile
     * @return: List<CardProvideApply>
     * @company: gyist
     * @version V3.0.0
     */
    public List<CardProvideApply> selectCardProvideApplyListPage(@Param("receiver") String receiver,
                                                                @Param("mobile") String mobile);
}
