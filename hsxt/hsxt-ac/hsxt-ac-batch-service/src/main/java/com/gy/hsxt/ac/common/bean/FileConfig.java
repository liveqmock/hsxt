/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.ac.common.bean;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 文件配置实体
 *
 * @Package : com.gy.hsxt.bs.disconf
 * @ClassName : FileConfig
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/25 11:21
 * @Version V3.0.0.0
 */
@Service
public class FileConfig {

    /**
     * 默认字符集 - UTF-8
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 数据分割符
     */
    public static final String DATA_SEPARATOR = "|";

    /**
     * 数据结束标识
     */
    public static final String DATA_END = "END";

    /**
     * 业务系统上传文件给用户中心同步的文件加名称
     */
    public static final String BS_TO_UC = "BS_UC";

    /**
     * 业务系统上传文件给账务系统同步的文件夹名称
     */
    public static final String BS_TO_AC = "BS_AC";

    /**
     * 业务系统上传文件给用户中心同步税率的文件夹名称
     */
    public static final String BS_TAX_RATE = "TAX";

    /**
     * 日终记账文件夹名称
     */
    public static final String BS_DAY_ACCT = "DAY_ACCT";

    /**
     * 给支付系统的对账文件夹名称
     */
    public static final String BS_TO_GP = "BS_GP";

    /**
     * 总记录文件的后缀
     */
    public static final String FILE_CHECK_TAIL = "_CHECK.TXT";

    /**
     * 平台共享文件根路径
     */
    @Value("${dir.root}")
    private String dirRoot;


    public String getDirRoot() {
        return dirRoot;
    }


    /**
     * 拼接文件夹路径
     * 如：/opt/share/BS/BS_AC/yyyyMMdd/
     *
     * @param desDir 目标文件夹
     * @return 文件夹路径
     */
    public String joinPreDirPath(String desDir) {
        return dirRoot + File.separator + desDir + File.separator;
    }
}
