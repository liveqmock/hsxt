package com.gy.hsxt.access.pos.constant;

/***************************************************************************
 * <PRE>
 *  Project Name    : PosServer
 * 
 *  Package Name    : com.gy.point.system.constant
 * 
 *  File Name       : ResponseErrorCodeEnum.java
 * 
 *  Creation Date   : 2014-6-17
 * 
 *  Author          : Administrator
 * 
 *  Purpose         : 企业类型
 * 
 * 
 *  History         : // XXX Huang: delete, ok.
 * 
 * </PRE>
 ***************************************************************************/
public enum EntTypeEnum {
    
    CKR('H',1,"持卡人"),

    CYQY('B',2, "成员企业"),

	TGQY('T',3, "托管企业"),
	
	FWGS('S',4, "服务公司"),
	
	GLGS('M',5, "管理公司"),
	
	DQPT('P',6,"地区平台"),
	
	ZPT('Z',7,"总平台"),
	
	QTPT('O',8,"其他地区平台"),
	
	JSGS('F',0, "结算公司");
	
	private char entType;
	private int custTypeId;
	private String entTypeName;


	EntTypeEnum(char entType, int custTypeId,String entTypeName) {
		this.entType = entType;
		this.custTypeId = custTypeId;
		this.entTypeName = entTypeName;
	}

	public char getEntType() {
		return entType;
	}

	public void setEntType(char entType) {
		this.entType = entType;
	}

	public int getCustTypeId() {
        return custTypeId;
    }

    public void setCustTypeId(int custTypeId) {
        this.custTypeId = custTypeId;
    }

    public String getEntTypeName() {
		return entTypeName;
	}

	public void setEntTypeName(String entTypeName) {
		this.entTypeName = entTypeName;
	}


	public static String getNameByEntType(char entType) {
		for (EntTypeEnum c : EntTypeEnum.values()) {
			if (entType == c.getEntType()) {
				return c.getEntTypeName();
			}
		}
		return null;
	}

	public static String getNameByCustTypeId(int custTypeId) {
        for (EntTypeEnum c : EntTypeEnum.values()) {
            if (custTypeId == c.getCustTypeId()) {
                return c.getEntTypeName();
            }
        }
        return null;
    }
	
}
