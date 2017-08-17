/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.file.bean;

/**
 * 解析文件信息
 *
 * @Package : com.gy.hsxt.bs.file.bean
 * @ClassName : ParseInfo
 * @Description : 解析文件信息
 * @Author : chenm
 * @Date : 2016/2/25 11:21
 * @Version V3.0.0.0
 */
public class ParseInfo {

    /**
     * 文件夹路径
     */
    private String dirPath;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 是否解析完成
     */
    private boolean completed;

    public ParseInfo(String dirPath, String fileName) {
        this.dirPath = dirPath;
        this.fileName = fileName;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * 构建函数
     *
     * @param dirPath  文件夹路径
     * @param fileName 文件名称
     * @return {@code ParseInfo}
     */
    public static ParseInfo create(String dirPath, String fileName) {
        return new ParseInfo(dirPath, fileName);
    }
}
