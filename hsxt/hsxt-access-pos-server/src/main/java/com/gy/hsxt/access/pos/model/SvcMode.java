/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;


/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: SvcMode 
 * @Description: <p/>
 * Purpose         : 服务方式。 pos终端输入
 * <p/>
 * 00   未指明
 * 01   手工
 * 02   磁条
 * 03   条形码
 * 04   光学字符阅读
 * 05   集成电路卡
 * 07   快速 PBOC借/贷记IC卡读入（非接触式）
 * 06-60    ISO保留使用
 * 61-80    国家保留使用
 * 81-99    私有保留使用
 * 90   磁条读入信息可靠，第二磁道信息必须出现
 * 91   非接触式磁条读入（MSD）
 * 92   预约支付
 * 95   集成电路卡，卡信息不可靠
 * 96   采用非接触方式读取CUPMobile移动支付中的集成在手机中的芯片卡
 * 98   标准PBOC借/贷记IC卡读入（非接触式）
 * <p/>
 * isPin
 * 0    未指明
 * 1    交易中包含PIN
 * 2    交易中不包含PIN
 * 3-5  ISO保留使用
 * 6-7  国家保留使用
 * 8-9  私有保留使用
 * <p/>
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:10:10 
 * @version V1.0
 */
public class SvcMode {
    /**
     * 服务方式
     */
    private String svcWay;
    /**
     * 交易中是否有PIN
     */
    private String isPin;

    public SvcMode(String svcWay, String isPin) {
        this.svcWay = svcWay;
        this.isPin = isPin;
    }

    public String getSvcWay() {
        return svcWay;
    }

	//public void setSvcWay(String svcWay) {
	//    this.svcWay = svcWay;
	//}
	//
    public String getIsPin() {
        return isPin;
    }

	//public void setIsPin(String isPin) {
	//    this.isPin = isPin;
	//}
	//
}
