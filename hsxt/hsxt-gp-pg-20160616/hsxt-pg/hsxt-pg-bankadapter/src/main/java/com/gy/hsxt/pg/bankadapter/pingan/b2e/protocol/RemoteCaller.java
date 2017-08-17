package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageDTO;
/**
 * 远程调用银行银企接口，屏蔽底层实现细节
 * 
 * @author jbli
 */
public interface RemoteCaller {
	
	/**
	 * 执行
	 * 
	 * @param packageDTO
	 * @return packageDTO
	 */
	public PackageDTO performCall(PackageDTO packageDTO);
}
