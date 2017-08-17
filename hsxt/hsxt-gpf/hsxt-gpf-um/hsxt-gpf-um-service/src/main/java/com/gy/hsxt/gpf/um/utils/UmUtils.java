/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.utils;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.MD5Utils;
import com.gy.hsxt.gpf.um.bean.Operator;

/**
 * UM系统工具
 *
 * @Package : com.gy.hsxt.gpf.um.utils
 * @ClassName : UmUtils
 * @Description : UM系统工具
 * @Author : chenm
 * @Date : 2016/1/27 11:10
 * @Version V3.0.0.0
 */
public abstract class UmUtils {

    /**
     * 系统节点编号
     */
    public static final String SYSTEM_INSTANCE_NO = "system.instance.no";

    /**
     * 子系统代码
     */
    public static final String SYSTEM_ID = "system.id";


    /**
     * 生成主键
     *
     * @return key
     */
    public static String generateKey() {

        return GuidAgent.getStringGuid(getSystemId() + HsPropertiesConfigurer.getProperty(SYSTEM_INSTANCE_NO));
    }

    /**
     * 获取子系统代码
     *
     * @return systemId
     */
    public static String getSystemId() {
        return HsPropertiesConfigurer.getProperty(SYSTEM_ID);
    }

    /**
     * 生成密码
     *
     * @param operator 操作者
     * @return password
     */
    public static String generatePwd(Operator operator) {
        return MD5Utils.toMD5code(operator.getOperatorId() + operator.getLoginUser() + operator.getLoginPwd());
    }

    /**
     * 生成Token
     *
     * @param operator 操作者
     * @return token
     */
    public static String getToken(Operator operator) {
        return MD5Utils.toMD5code(operator.getOperatorId() + operator.getLoginUser() + DateUtil.getCurrentDateTime());
    }
}
