/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.ds.param.controller.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.gy.hsi.ds.common.thirds.dsp.common.form.RequestFormBase;

/**
 * 
 * 修改密码form类
 * @Package: com.gy.hsi.ds.param.web.service.sign.form  
 * @ClassName: PasswordForm 
 * @Description: TODO
 *
 * @author: yangyp 
 * @date: 2015年10月8日 上午10:51:43 
 * @version V3.0
 */
public class PasswordForm extends RequestFormBase {

    /**
     * 自动生成的序列化值
     */
    private static final long serialVersionUID = -8600692148189178516L;

    /**
     * 旧密码
     */
    @NotNull(message = "oldpass.empty")
    @NotEmpty(message = "oldpass.empty")
    private String oldpass;
    public static final String OLDPASS = "oldpass";
    /**
     * 新密码
     */
    @NotNull(message = "newpass.empty")
    @NotEmpty(message = "newpass.empty")
    private String newpass;
    
    public String getOldpass() {
        return oldpass;
    }

    public void setOldpass(String oldpass) {
        this.oldpass = oldpass;
    }

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }


}
