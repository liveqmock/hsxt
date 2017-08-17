/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.ao.file.bean;

import java.util.List;

/**
 * 数据文件信息
 *
 * @Package : com.gy.hsxt.bs.file.bean
 * @ClassName : DataInfo
 * @Description : 数据文件信息
 * @Author : chenm
 * @Date : 2016/2/24 14:53
 * @Version V3.0.0.0
 */
public class DataInfo<T> {

    /**
     * 文件夹路径
     */
    private String dirPath;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * MD5码
     */
    private String md5;

    /**
     * 文件生成是否完成
     */
    private boolean completed;

    /**
     * 文件内容数据
     */
    private List<T> data;

    /**
     * 文件内数据总数
     */
    private int dataSum;

    public DataInfo(String dirPath, String fileName, List<T> data) {
        this.dirPath = dirPath;
        this.fileName = fileName;
        this.data = data;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getDataSum() {
        return dataSum;
    }

    public void setDataSum(int dataSum) {
        this.dataSum = dataSum;
    }

    public static <T> DataInfo<T> bulid(String dirPath, String fileName, List<T> data) {
        return new DataInfo<>(dirPath, fileName, data);
    }
}
