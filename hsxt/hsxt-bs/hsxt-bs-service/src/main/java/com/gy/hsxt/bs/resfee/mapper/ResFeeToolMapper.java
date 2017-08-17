/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.resfee.mapper;

import com.gy.hsxt.bs.bean.resfee.ResFeeTool;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资源费包含工具 Mapper接口
 *
 * @Package: com.gy.hsxt.bs.resfee.mapper
 * @ClassName: ResFeeToolMapper
 * @Description:
 *
 * @author: liuhq
 * @date: 2015-10-14 下午5:05:15
 * @version V1.0
 */
@Repository
public interface ResFeeToolMapper {
    /**
     * 创建 资源费包含工具
     *
     * @param resFeeTool 实体类 非null
     * @return 返回true成功，false or Exception失败
     */
    int createResFeeTool(ResFeeTool resFeeTool);

    /**
     * 查询 某一个资源费包含工具
     *
     * @param resFeeId 资源费方案编号 必须 非null
     * @return 返回按条件查询的数据List
     */
    List<ResFeeTool> queryResFeeToolList(@Param("resFeeId") String resFeeId);
}
