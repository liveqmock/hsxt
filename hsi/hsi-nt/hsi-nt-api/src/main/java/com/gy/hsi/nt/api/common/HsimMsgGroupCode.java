package com.gy.hsi.nt.api.common;
/**
 * 
 * @className:HsimMsgGroupCode
 * @author:likui
 * @date:2015年7月29日
 * @desc:互动消息群组枚举
 * @company:gyist
 */
public enum HsimMsgGroupCode {

	ALL("HSGKX30101_", "关联的全体客户组合(管理公司、服务公司、托管企业、成员企业、消费者)"),

	ALL_M("HSGKX30102_", "全体下级管理公司"),

	ALL_S("HSGKX30103_", "全体下级服务公司"),

	ALL_T("HSGKX30104_", "全体下级托管企业"),

	ALL_B("HSGKX30105_", "全体下级成员企业"),

	ALL_P("HSGKX30106_", "全体下级消费者"),

	M_ALL_S("HSGKX30201_", "全体下级服务公司"),

	S_ALL_T("HSGKX30301_", "全体下级托管企业"),

	S_ALL_B("HSGKX30302_", "全体下级成员企业"),

	S_ALL("HSGKX30303_", " 全体下级企业(托管、成员)");

	private String hsimCode;

	HsimMsgGroupCode(String hsimCode, String desc) {
		this.hsimCode = hsimCode;
	}

	public static String getHsimCode(String enumValue) {
		return HsimMsgGroupCode.valueOf(enumValue).hsimCode;
	}

	public static HsimMsgGroupCode getHsimMsgGroupCode(String code)
	{
		HsimMsgGroupCode[] groups = HsimMsgGroupCode.values();
		for (HsimMsgGroupCode group : groups)
		{
			if (group.name().equals(code))
			{
				return group;
			}
		}
		return null;
	}
}
