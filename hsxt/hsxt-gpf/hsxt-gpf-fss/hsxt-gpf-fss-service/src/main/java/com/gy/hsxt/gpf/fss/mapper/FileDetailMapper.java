/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.mapper;

import com.gy.hsxt.gpf.fss.bean.FileDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Package :com.gy.hsxt.gpf.fss.mapper
 * @ClassName : FileDetailMapper
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/23 10:47
 * @Version V3.0.0.0
 */
@Repository("fileDetailMapper")
public interface FileDetailMapper extends BaseMapper<FileDetail> {

    /**
     * 批量保存文件详情
     *
     * @param details
     * @return
     */
    int batchInsert(@Param("details") List<FileDetail> details);

    /**
     * 根据通知编号查询对应的所有文件详情
     *
     * @param notifyNo
     * @return
     */
    List<FileDetail> selectByNotifyNo(@Param("notifyNo") String notifyNo);
}
