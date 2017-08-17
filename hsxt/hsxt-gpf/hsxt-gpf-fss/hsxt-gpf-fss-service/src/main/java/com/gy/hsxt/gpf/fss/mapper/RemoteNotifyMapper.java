/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND FileNotifyECHNOLOGY DEVELOP CO., LFileNotifyD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
*/
package com.gy.hsxt.gpf.fss.mapper;

import com.gy.hsxt.gpf.fss.bean.QueryNotify;
import com.gy.hsxt.gpf.fss.bean.RemoteNotify;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Package :com.gy.hsxt.gpf.fss.mapper
 * @ClassName : RemoteNotifyMapper
 * @Description : FileNotifyODO
 * @Author : chenm
 * @Date : 2015/10/21 17:19
 * @Version V3.0.0.0
 */
@Repository("remoteNotifyMapper")
public interface RemoteNotifyMapper extends BaseMapper<RemoteNotify> {

    /**
     * 根据查询实体查询数据列表
     * @param queryNotify
     * @return
     */
    List<RemoteNotify> selectByOther(QueryNotify queryNotify);

    /**
     * 查询记录总数
     * @param queryNotify 查询实体
     * @return 总数
     */
    int selectCountByOther(QueryNotify queryNotify);
}
