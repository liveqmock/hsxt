package com.gy.hsxt.uc.as.bean.enumerate;

/**
 * 接入渠道类型 
 * @category Simple to Introduction
 * @projectName hsxt-uc-bs-api
 * @package com.gy.hsxt.uc.enumerate.ChannelTypeEnum.java
 * @className ChannelTypeEnum
 * @description 接入渠道类型，如POS、PAD、手机等
 * @author lixuan
 * @createDate 2015-10-14 上午10:21:17
 * @updateUser lixuan
 * @updateDate 2015-10-14 上午10:21:17
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public enum ChannelTypeEnum {
 
    /** 网页接入*/
    WEB(1, "w_"),
    /** POS接入 */
    POS(2, "pos_"),
    /** 刷卡器 接入方*/
    MCR(3, "mcr_"),
    /** 移动APP接入*/
    MOBILE(4, "m_"),
    /** 互生平板 接入 */
    HSPAD(5, "pd_"),
    /** 互生平台接入 */
    SYS(6, "sys_"),
    /** IVR接入 */
    IVR(7, "ivr_"),
    /** 第三方接入 */
    THIRDPART(8, "tp_");

    int type;

    String perfix;

    private ChannelTypeEnum(int type, String perfix) {
        this.type = type;
        this.perfix = perfix;
    }

    public int getType() {
        return type;
    }

    public String getPerfix() {
        return perfix;
    }

    public static ChannelTypeEnum get(int type) {
        ChannelTypeEnum[] values = ChannelTypeEnum.values();
        for (ChannelTypeEnum v : values)
        {
            if (v.type == type)
            {
                return v;
            }
        }
        return null;
    }
    
    public static ChannelTypeEnum get(String type) {
        ChannelTypeEnum[] values = ChannelTypeEnum.values();
        for (ChannelTypeEnum v : values)
        {
            if (v.perfix.equals(type))
            {
                return v;
            }
        }
        return null;
    }
}
