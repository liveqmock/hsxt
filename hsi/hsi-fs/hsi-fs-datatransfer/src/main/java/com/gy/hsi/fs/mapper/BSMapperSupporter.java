/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.mapper;

import javax.annotation.Resource;

import com.gy.hsi.fs.mapper.dbbs01.TBsCardStyleMapper;
import com.gy.hsi.fs.mapper.dbbs01.TBsCompanyStopMapper;
import com.gy.hsi.fs.mapper.dbbs01.TBsDebitMapper;
import com.gy.hsi.fs.mapper.dbbs01.TBsDeclareCorpAptitudeMapper;
import com.gy.hsi.fs.mapper.dbbs01.TBsDeclareEntLinkmanMapper;
import com.gy.hsi.fs.mapper.dbbs01.TBsEntChangeInfoAppMapper;
import com.gy.hsi.fs.mapper.dbbs01.TBsEntChangeInfoDataMapper;
import com.gy.hsi.fs.mapper.dbbs01.TBsEntRealnameAuthMapper;
import com.gy.hsi.fs.mapper.dbbs01.TBsFilingAptitudeMapper;
import com.gy.hsi.fs.mapper.dbbs01.TBsPerChangeInfoAppMapper;
import com.gy.hsi.fs.mapper.dbbs01.TBsPerChangeInfoDataMapper;
import com.gy.hsi.fs.mapper.dbbs01.TBsPerRealnameAuthMapper;
import com.gy.hsi.fs.mapper.dbbs01.TBsPointgameMapper;
import com.gy.hsi.fs.mapper.dbbs01.TBsResetTranpwdapplyMapper;
import com.gy.hsi.fs.mapper.dbbs01.TBsShippingMethodMapper;
import com.gy.hsi.fs.mapper.dbbs01.TBsTaxrateChangeMapper;
import com.gy.hsi.fs.mapper.dbbs01.TBsToolProductMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.common.framework.mybatis
 * 
 *  File Name       : MapperSupporter.java
 * 
 *  Creation Date   : 2015年5月20日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : none
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class BSMapperSupporter {

	@Resource(name = "bs_mapperDoor")
	private MapperDoor mapperDoor;

	public TBsCardStyleMapper getTBsCardStyleMapper() {
		return mapperDoor.getMapper(TBsCardStyleMapper.class);
	}

	public TBsDebitMapper getTBsDebitMapper() {
		return mapperDoor.getMapper(TBsDebitMapper.class);
	}

	public TBsDeclareCorpAptitudeMapper getTBsDeclareCorpAptitudeMapper() {
		return mapperDoor.getMapper(TBsDeclareCorpAptitudeMapper.class);
	}

	public TBsDeclareEntLinkmanMapper getTBsDeclareEntLinkmanMapper() {
		return mapperDoor.getMapper(TBsDeclareEntLinkmanMapper.class);
	}

	public TBsEntChangeInfoAppMapper getTBsEntChangeInfoAppMapper() {
		return mapperDoor.getMapper(TBsEntChangeInfoAppMapper.class);
	}

	public TBsEntRealnameAuthMapper getTBsEntRealnameAuthMapper() {
		return mapperDoor.getMapper(TBsEntRealnameAuthMapper.class);
	}

	public TBsFilingAptitudeMapper getTBsFilingAptitudeMapper() {
		return mapperDoor.getMapper(TBsFilingAptitudeMapper.class);
	}

	public TBsPerChangeInfoAppMapper getTBsPerChangeInfoAppMapper() {
		return mapperDoor.getMapper(TBsPerChangeInfoAppMapper.class);
	}

	public TBsPerRealnameAuthMapper getTBsPerRealnameAuthMapper() {
		return mapperDoor.getMapper(TBsPerRealnameAuthMapper.class);
	}

	public TBsPointgameMapper getTBsPointgameMapper() {
		return mapperDoor.getMapper(TBsPointgameMapper.class);
	}

	public TBsResetTranpwdapplyMapper getTBsResetTranpwdapplyMapper() {
		return mapperDoor.getMapper(TBsResetTranpwdapplyMapper.class);
	}

	public TBsShippingMethodMapper getTBsShippingMethodMapper() {
		return mapperDoor.getMapper(TBsShippingMethodMapper.class);
	}

	public TBsTaxrateChangeMapper getTBsTaxrateChangeMapper() {
		return mapperDoor.getMapper(TBsTaxrateChangeMapper.class);
	}

	public TBsToolProductMapper getTBsToolProductMapper() {
		return mapperDoor.getMapper(TBsToolProductMapper.class);
	}
	
	public TBsCompanyStopMapper getTBsCompanyStopMapper() {
		return mapperDoor.getMapper(TBsCompanyStopMapper.class);
	}
	
	public TBsEntChangeInfoDataMapper getTBsEntChangeInfoDataMapper() {
		return mapperDoor.getMapper(TBsEntChangeInfoDataMapper.class);
	}
	
	public TBsPerChangeInfoDataMapper getTBsPerChangeInfoDataMapper() {
		return mapperDoor.getMapper(TBsPerChangeInfoDataMapper.class);
	}

}
