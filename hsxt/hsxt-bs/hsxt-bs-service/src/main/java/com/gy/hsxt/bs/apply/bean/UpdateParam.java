/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.bean;

import java.io.Serializable;

/** 
 * 
 * @Package: com.gy.hsxt.bs.apply.bean  
 * @ClassName: UpdateParam 
 * @Description: 更新参数
 *
 * @author: xiaofl 
 * @date: 2015-12-14 下午4:50:33 
 * @version V1.0 
 

 */ 
public class UpdateParam implements Serializable {

    private static final long serialVersionUID = -7214330977245729799L;

    /** 表名 **/
    private String tableName;
    /** 更新语句 **/
    private String sqlSetStr;
    /** where语句 **/
    private String sqlWhereStr;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSqlSetStr() {
        return sqlSetStr;
    }

    public void setSqlSetStr(String sqlSetStr) {
        this.sqlSetStr = sqlSetStr;
    }

    public String getSqlWhereStr() {
        return sqlWhereStr;
    }

    public void setSqlWhereStr(String sqlWhereStr) {
        this.sqlWhereStr = sqlWhereStr;
    }

    @Override
    public String toString() {
        return "UpdateParam [tableName=" + tableName + ", sqlSetStr=" + sqlSetStr + ", sqlWhereStr=" + sqlWhereStr
                + "]";
    }

}
