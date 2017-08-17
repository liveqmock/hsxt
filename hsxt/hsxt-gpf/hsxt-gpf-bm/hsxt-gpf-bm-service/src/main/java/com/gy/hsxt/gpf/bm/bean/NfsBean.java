/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.bean;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.constant.FssPurpose;
import com.gy.hsxt.gpf.fss.utils.DirPathBuilder;
import com.gy.hsxt.gpf.fss.utils.FileNameBuilder;
import com.gy.hsxt.gpf.fss.utils.FssDateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Calendar;

/**
 * nfs相关配置类
 *
 * @Package :com.gy.hsxt.gpf.bm.bean
 * @ClassName : NfsBean
 * @Description : nfs相关配置类
 * @Author : chenm
 * @Date : 2015/10/12 19:29
 * @Version V3.0.0.0
 */
@Service
public class NfsBean implements Serializable {

    private static final long serialVersionUID = -7456099474719116637L;

    /**
     * 本平台代码
     */
    @Value("${plat.code}")
    private String platCode;

    /**
     * 本系统代码
     */
    @Value("${system.id}")
    private String sysCode;

    /**
     *
     */
    @Value("${to.sys.code}")
    private String toSysCode;

    /**
     * 根目录
     */
    @Value("${dir.root}")
    private String dirRoot;


    /**
     * 再增值业务获取上传数据所在目录 (数据为业务系统所用)
     *
     * @param platCode
     * @return
     * @throws HsException
     */
    public String buildBmlmUploadAddress(String platCode) throws HsException {
        String pmeDay = FssDateUtil.obtainMonthLastDay(FssDateUtil.PREVIOUS_MONTH, FssDateUtil.SHORT_DATE_FORMAT);//上月最后一天
        //文件夹路径
        String dir = DirPathBuilder.uploadDir(sysCode, pmeDay, FssPurpose.BM_BMLM, platCode);
        //文件名称
        String name = FileNameBuilder.build(FssPurpose.BM_BMLM, pmeDay, sysCode);
        return dirRoot + dir + name;
    }

    /**
     * 增值业务获取上传数据所在目录 (数据为业务系统所用)
     *
     * @param platCode
     * @return
     * @throws HsException
     */
    public String buildMlmUploadAddress(String platCode) throws HsException {
        String pwsDay = FssDateUtil.obtainWeekDay(Calendar.SUNDAY, FssDateUtil.PREVIOUS_WEEK, FssDateUtil.SHORT_DATE_FORMAT);//上周日日期
        //文件夹路径
        String dir = DirPathBuilder.uploadDir(sysCode, pwsDay, FssPurpose.BM_MLM, platCode);
        //文件名称
        String name = FileNameBuilder.build(FssPurpose.BM_MLM, pwsDay, sysCode);
        return dirRoot + dir + name;
    }

    public String getPlatCode() {
        return platCode;
    }

    public String getSysCode() {
        return sysCode;
    }

    public String getToSysCode() {
        return toSysCode;
    }
}
