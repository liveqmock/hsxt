/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.settlement.service;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.ps.api.IPsSettleService;
import com.gy.hsxt.ps.bean.BatSettle;
import com.gy.hsxt.ps.bean.BatUpload;
import com.gy.hsxt.ps.common.*;
import com.gy.hsxt.ps.settlement.bean.BatchSettle;
import com.gy.hsxt.ps.settlement.bean.BatchUpload;
import com.gy.hsxt.ps.settlement.handle.PosSettleHandle;
import com.gy.hsxt.ps.settlement.mapper.PosSettleMapper;

import com.gy.hsxt.ps.validator.GeneralValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package :com.gy.hsxt.ps.reconciliation.service
 * @ClassName : SettleService
 * @Description : 对账(批结算，批上送)
 * @Date : 2015/11/2 17:06
 * @Version V3.0.0.0
 */
@Service
public class PosSettleService implements IPsSettleService {

    @Autowired
    private PosSettleMapper posSettleMapper;

    /**
     * 批结算
     *
     * @param batSettle 对象
     * @throws HsException
     */
    @Override
    public void batSettle(BatSettle batSettle) throws HsException {

        // 参数为空是抛异常
        GeneralValidator.notNull(batSettle, RespCode.PS_PARAM_ERROR, "参数错误");

        //校验框架校验参数是否传值
        ValidateModelUtil.validateModel(batSettle);
        /**
         * 查询积分、撤单、退货总数、笔数
         */
        BatchSettle bs = posSettleMapper.querySettle(batSettle);

        GeneralValidator.notNull(bs, RespCode.PS_NO_DATA_EXIST, "没有数据，请检测传的参数是否正确！");

        String id = GuidAgent.getStringGuid(Constants.NO_SETTLE40 + PsTools.getInstanceNo());
        bs.setId(id);
        // 对比批结算
        int settleResult = PosSettleHandle.settleCompare(batSettle, bs);
        // 批结算结果记录
        this.posSettleMapper.insertSettle(batSettle,bs);
        if (settleResult == 2) {
            throw new HsException(RespCode.PS_BATSETTLE_ERROR.getCode(),
                    "批结算账不平");
        }

    }

    /**
     * 批上送
     */
    @Override
    public void batUpload(List<BatUpload> batUpload) throws HsException {
        System.out.print(JSON.toJSONString(batUpload)+"====================");
        //消费积分正常单流水号
        List<String> transNoDetails = new ArrayList<>();
        // 判断参数是否为空
        if (CollectionUtils.isEmpty(batUpload)) {
            throw new HsException(RespCode.PS_PARAM_ERROR.getCode(), "参数错误,您传的对象为空！");
        } else {
            for (BatUpload batUpload1 : batUpload) {
                transNoDetails.add(batUpload1.getSourceTransNo());
            }
            String inSQL = PsTools.getOracleSQLIn(transNoDetails, 1000, "T.SOURCE_TRANS_NO");
            // 查询同批次、设备等消费订单明细
            List<BatchUpload> list = this.posSettleMapper.queryUpload(inSQL,batUpload.get(0).getEntResNo());
            // 对比批上送
            PosSettleHandle.uploadCompare(batUpload, list);
            /* 批量插入明细对比结果 */
            this.posSettleMapper.insertUpload(list);
        }
    }

}
