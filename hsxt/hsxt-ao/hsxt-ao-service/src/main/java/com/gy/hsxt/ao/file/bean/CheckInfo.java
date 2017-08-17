/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.ao.file.bean;

import com.gy.hsxt.ao.disconf.AoConfig;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Check文件信息
 *
 * @Package : com.gy.hsxt.bs.file.bean
 * @ClassName : CheckInfo
 * @Description : Check文件信息
 * @Author : chenm
 * @Date : 2016/2/24 15:31
 * @Version V3.0.0.0
 */
public class CheckInfo {

    /**
     * 数据文件总数
     */
    private Integer checkNum;

    /**
     * 是否需要 显示文件总数
     */
    private boolean need;

    /**
     * 文件夹路径
     */
    private String dirPath;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件信息数据
     */
    private List<String> fileInfos = new ArrayList<>();

    /**
     * 原子计数器
     */
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public CheckInfo(Integer checkNum, String dirPath, String fileName) {
        this.checkNum = checkNum;
        this.dirPath = dirPath;
        this.fileName = fileName;
    }

    public Integer getCheckNum() {
        return checkNum;
    }

    public boolean isNeed() {
        return need;
    }

    public CheckInfo setNeed(boolean need) {
        this.need = need;
        return this;
    }

    public String getDirPath() {
        return dirPath;
    }

    public String getFileName() {
        return fileName;
    }

    public List<String> getFileInfos() {
        return fileInfos;
    }

    public AtomicInteger getAtomicInteger() {
        return atomicInteger;
    }

    /**
     * 添加数据文件信息
     *
     * @param dataInfo 文件信息
     * @return boolean
     */
    public boolean addFileInfo(DataInfo dataInfo) {
        String checkLine = dataInfo.getFileName() + AoConfig.DATA_SEPARATOR + dataInfo.getDataSum();
        if (StringUtils.isNotBlank(dataInfo.getMd5())) {
            checkLine = checkLine + AoConfig.DATA_SEPARATOR + dataInfo.getMd5();
        }
        return this.getFileInfos().add(checkLine + IOUtils.LINE_SEPARATOR);
    }

    /**
     * 构建方法
     *
     * @param checkNum 数据文件个数
     * @param dirPath  文件夹路径
     * @param fileName 文件名称
     * @return {@code CheckInfo}
     */
    public static CheckInfo bulid(Integer checkNum, String dirPath, String fileName) {
        return new CheckInfo(checkNum, dirPath, fileName);
    }
}
