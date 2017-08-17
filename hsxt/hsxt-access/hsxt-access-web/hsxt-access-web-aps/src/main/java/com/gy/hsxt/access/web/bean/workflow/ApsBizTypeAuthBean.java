/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.workflow;

import java.io.Serializable;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.bean.BizTypeAuth;

/***
 * 值班员业务类型实体
 * 
 * @Package: com.gy.hsxt.access.web.bean.workflow
 * @ClassName: ApsBizTypeAuthBean
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-12-23 下午6:06:22
 * @version V1.0
 */
public class ApsBizTypeAuthBean implements Serializable {
    private static final long serialVersionUID = -990855832950717271L;

    /**
     * 业务类型json
     */
    private String bizTypeJson;

    /**
     * 业务类型
     */
    private List<BizTypeAuth> btaList;

    /**
     * 验证码有效数据
     */
    public void checkData() {

        RequestUtil.verifyParamsIsNotEmpty(new Object[] { this.bizTypeJson, ASRespCode.APS_SELECT_MEMBER_BIZTYPE });

        // 值班组组员业务类型不能少于一条
        if (this.btaList == null || this.btaList.size() == 0)
        {
            throw new HsException(ASRespCode.APS_SELECT_MEMBER_BIZTYPE);
        }
    }

    /**
     * 业务节点转换为set
     * 
     * @return
     */
    public Set<String> bizTypeSet() {
        Set<String> btSet = new HashSet<String>();

        // 累加赋值
        for (BizTypeAuth bta : this.btaList)
            btSet.add(bta.getBizType());

        return btSet;
    }

    public List<BizTypeAuth> getBtaList() {
        return btaList;
    }

    public void setBtaList(List<BizTypeAuth> btaList) {
        this.btaList = btaList;
    }

    public String getBizTypeJson() {
        return bizTypeJson;
    }

    public void setBizTypeJson(String bizTypeJson) {
        this.bizTypeJson = bizTypeJson;
        if (!StringUtils.isEmptyTrim(this.bizTypeJson))
        {
            try
            {
                this.setBtaList(JSON.parseArray(URLDecoder.decode(this.bizTypeJson, "UTF-8"), BizTypeAuth.class));
            }
            catch (Exception e)
            {
                SystemLog.error("workflow", "setBtaList", "值班组组员业务类型转换异常", e);
            }
        }
    }

}
