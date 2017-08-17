/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.access.pos.point.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/***************************************************************************
 * <PRE>
 *  Project Name    : point-server
 * 
 *  Package Name    : com.gy.point.tc
 * 
 *  File Name       : Pos.java
 * 
 *  Creation Date   : 2015-1-27
 * 
 *  Author          : huangfh
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "gui-definition")
public class GuiDefinition {

	@XmlElement(name = "pos")  
	private List<Pos> posList;

	public List<Pos> getPosList() {
		return posList;
	}

	public void setPosList(List<Pos> posList) {
		this.posList = posList;
	} 
	
	
}
