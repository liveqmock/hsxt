/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.disconf;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 文件配置实体
 *
 * @Package : com.gy.hsxt.bs.disconf
 * @ClassName : FileConfig
 * @Description : 文件配置实体
 * @Author : chenm
 * @Date : 2015/12/25 11:21
 * @Version V3.0.0.0
 */
@Service
public class FileConfig implements InitializingBean{

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
     * 年费记账文件目录名
     */
    public static final String ANNUAL_FEE_ACCT = "ANNUAL_FEE_ACCT";

    /**
     * 年费状态同步文件目录名
     */
    public static final String ANNUAL_FEE_SYNC = "ANNUAL_FEE_SYNC";

    /**
     * 普通日终记账文件夹名称
     */
    public static final String BS_DAY_ACCT = "DAY_ACCT";

    /**
     * 文件的后缀
     */
    public static final String FILE_SUFFIX = ".TXT";

    /**
     * 校验文件的后缀 例 :20160101_CHECK.TXT
     */
    public static final String FILE_CHECK_TAIL = "_CHECK" + FILE_SUFFIX;

    /**
     * 记账结果文件标识 例 :20160101_01_RETURN.TXT
     */
    public static final String FILE_RETURN_TAIL = "_RETURN" + FILE_SUFFIX;

    /**
     * 平台共享文件根路径
     */
    @Value("${dir.root}")
    private String dirRoot;

    /**
     * 文件最大记录数
     */
    @Value("${max.line.num}")
    private int maxLineNum;

    /**
     * 系统代码
     */
    @Value("${system.id}")
    private String systemId;

    public String getDirRoot() {
        return dirRoot;
    }

    public int getMaxLineNum() {
        return maxLineNum;
    }

    public String getSystemId() {
        return systemId;
    }

    /**
     * 拼接文件夹路径 如：/opt/share/BS/BS_AC/yyyyMMdd/
     *
     * @param desDir 目标文件夹
     * @return 文件夹路径
     */
    public String joinPreDirPath(String desDir,String scanDate) {
        return dirRoot + File.separator + systemId + File.separator + desDir + File.separator + scanDate
                + File.separator;
    }

    /**
     * 获取昨天日期
     *
     * @return string
     */
    public static String getYesterday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(c.getTime());
    }

    /**
     * 获取昨天最后时刻
     *
     * @return string
     */
    public static String getYesterdayTime() {
        Calendar c = Calendar.getInstance();
        c.setTime(DateUtil.getCurrentDate("yyyy-MM-dd"));
        c.add(Calendar.SECOND, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(c.getTime());
    }

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     * <p>This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an
     * exception in the event of misconfiguration.
     *
     * @throws Exception in the event of misconfiguration (such
     *                   as failure to set an essential property) or if initialization fails.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        HsAssert.isTrue(this.getMaxLineNum() > 0, RespCode.PARAM_ERROR, "文件最大记录数[max.line.num]设置必须大于0");
        HsAssert.hasText(this.getDirRoot(),RespCode.PARAM_ERROR,"根目录[dir.root]不能为空");
        HsAssert.hasText(this.getSystemId(),RespCode.PARAM_ERROR,"子系统代码[system.id]不能为空");
    }
}
