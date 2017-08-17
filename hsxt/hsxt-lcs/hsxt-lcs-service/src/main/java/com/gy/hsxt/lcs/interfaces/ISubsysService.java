
package com.gy.hsxt.lcs.interfaces;

/***************************************************************************
 * <PRE>
 *  Project Name    : com.gy.hsxt.lcs
 * 
 *  Package Name    : com.gy.hsxt.lcs.interfaces
 * 
 *  File Name       : ISubsysService.java
 * 
 *  Creation Date   : 2015-7-15
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 系统代码ISubsysService接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
import java.util.List;

import com.gy.hsxt.lcs.bean.Subsys;

public interface ISubsysService {

	/**
	 * 批量更新数据
	 * 
	 * @param list
	 *            获取变的版本号的数据表示 必须
	 * @param version
	 *            版本号
	 * @return 返回int 1成功，其他失败
	 */
	public int addOrUpdateSubsys(List<Subsys> list, Long version);
}
