/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsi.fs.common.constant.FsConstant;
import com.gy.hsi.fs.common.constant.StartClassPath;
import com.gy.hsi.fs.mapper.BSMapperSupporter;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsCardStyle;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsCardStyleExample;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsCompanyStop;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsCompanyStopExample;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsDebit;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsDebitExample;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsDeclareCorpAptitude;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsDeclareCorpAptitudeExample;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsDeclareEntLinkman;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsDeclareEntLinkmanExample;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntChangeInfoApp;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntChangeInfoAppExample;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntChangeInfoData;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntChangeInfoDataExample;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntRealnameAuth;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntRealnameAuthExample;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsFilingAptitude;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsFilingAptitudeExample;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerChangeInfoApp;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerChangeInfoAppExample;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerChangeInfoData;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerChangeInfoDataExample;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerRealnameAuth;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerRealnameAuthExample;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPointgame;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPointgameExample;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsResetTranpwdapply;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsResetTranpwdapplyExample;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsShippingMethod;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsShippingMethodExample;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsTaxrateChange;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsTaxrateChangeExample;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsToolProduct;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsToolProductExample;
import com.gy.hsi.fs.service.IBSDataTransferService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-datatransfer
 * 
 *  Package Name    : com.gy.hsi.fs.service.impl
 * 
 *  File Name       : BSDataTransferServiceImpl.java
 * 
 *  Creation Date   : 2015年11月20日
 * 
 *  Author          : Administrator
 * 
 *  Purpose         : none
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service
public class BSDataTransferServiceImpl extends BSMapperSupporter implements
		IBSDataTransferService {

	@Override
	public List<TBsResetTranpwdapply> queryTBsResetTranpwdapplyList(int start,
			int end) {
		TBsResetTranpwdapplyExample example = new TBsResetTranpwdapplyExample();
		example.setStart(start);
		example.setEnd(end);

		return getTBsResetTranpwdapplyMapper().selectByExample(example);
	}

	@Override
	public List<TBsToolProduct> queryTBsToolProductList(int start, int end) {
		TBsToolProductExample example = new TBsToolProductExample();
		example.setStart(start);
		example.setEnd(end);

		return getTBsToolProductMapper().selectByExample(example);
	}

	@Override
	public List<TBsCardStyle> queryTBsCardStyleList(int start, int end) {
		TBsCardStyleExample example = new TBsCardStyleExample();
		example.setStart(start);
		example.setEnd(end);

		return getTBsCardStyleMapper().selectByExample(example);
	}

	@Override
	public List<TBsDebit> queryTBsDebitList(int start, int end) {
		TBsDebitExample example = new TBsDebitExample();
		example.setStart(start);
		example.setEnd(end);

		return getTBsDebitMapper().selectByExample(example);
	}

	@Override
	public List<TBsDeclareCorpAptitude> queryTBsDeclareCorpAptitudeList(
			int start, int end) {
		TBsDeclareCorpAptitudeExample example = new TBsDeclareCorpAptitudeExample();
		example.setStart(start);
		example.setEnd(end);

		return getTBsDeclareCorpAptitudeMapper().selectByExample(example);
	}

	@Override
	public List<TBsDeclareEntLinkman> queryTBsDeclareEntLinkmanList(int start,
			int end) {
		TBsDeclareEntLinkmanExample example = new TBsDeclareEntLinkmanExample();
		example.setStart(start);
		example.setEnd(end);

		return getTBsDeclareEntLinkmanMapper().selectByExample(example);
	}

	@Override
	public List<TBsEntChangeInfoApp> queryTBsEntChangeInfoAppList(int start,
			int end) {
		TBsEntChangeInfoAppExample example = new TBsEntChangeInfoAppExample();
		example.setStart(start);
		example.setEnd(end);

		return getTBsEntChangeInfoAppMapper().selectByExample(example);
	}

	@Override
	public List<TBsEntRealnameAuth> queryTBsEntRealnameAuthList(int start,
			int end) {
		TBsEntRealnameAuthExample example = new TBsEntRealnameAuthExample();
		example.setStart(start);
		example.setEnd(end);

		return getTBsEntRealnameAuthMapper().selectByExample(example);
	}

	@Override
	public List<TBsFilingAptitude> queryTBsFilingAptitudeList(int start, int end) {
		TBsFilingAptitudeExample example = new TBsFilingAptitudeExample();
		example.setStart(start);
		example.setEnd(end);

		return getTBsFilingAptitudeMapper().selectByExample(example);
	}

	@Override
	public List<TBsPerChangeInfoApp> queryTBsPerChangeInfoAppList(int start,
			int end) {
		TBsPerChangeInfoAppExample example = new TBsPerChangeInfoAppExample();
		example.setStart(start);
		example.setEnd(end);

		return getTBsPerChangeInfoAppMapper().selectByExample(example);
	}

	@Override
	public List<TBsPerRealnameAuth> queryTBsPerRealnameAuthList(int start,
			int end) {
		TBsPerRealnameAuthExample example = new TBsPerRealnameAuthExample();
		example.setStart(start);
		example.setEnd(end);

		return getTBsPerRealnameAuthMapper().selectByExample(example);
	}

	@Override
	public List<TBsPointgame> queryTBsPointgameList(int start, int end) {
		TBsPointgameExample example = new TBsPointgameExample();
		example.setStart(start);
		example.setEnd(end);

		return getTBsPointgameMapper().selectByExample(example);
	}

	@Override
	public List<TBsShippingMethod> queryTBsShippingMethodList(int start, int end) {
		TBsShippingMethodExample example = new TBsShippingMethodExample();
		example.setStart(start);
		example.setEnd(end);

		return getTBsShippingMethodMapper().selectByExample(example);
	}

	@Override
	public List<TBsTaxrateChange> queryTBsTaxrateChangeList(int start, int end) {
		TBsTaxrateChangeExample example = new TBsTaxrateChangeExample();
		example.setStart(start);
		example.setEnd(end);

		return getTBsTaxrateChangeMapper().selectByExample(example);
	}
	
	@Override
	public List<TBsCompanyStop> queryTBsCompanyStopList(int start, int end) {
		TBsCompanyStopExample example = new TBsCompanyStopExample();
		example.setStart(start);
		example.setEnd(end);

		return getTBsCompanyStopMapper().selectByExample(example);
	}

	@Override
	public List<TBsEntChangeInfoData> queryTBsEntChangeInfoDataList(int start, int end) {
		TBsEntChangeInfoDataExample example = new TBsEntChangeInfoDataExample();
		example.setStart(start);
		example.setEnd(end);

		return getTBsEntChangeInfoDataMapper().selectByExample(example);
	}
	
	@Override
	public List<TBsPerChangeInfoData> queryTBsPerChangeInfoDataList(int start, int end) {
		TBsPerChangeInfoDataExample example = new TBsPerChangeInfoDataExample();
		example.setStart(start);
		example.setEnd(end);

		return getTBsPerChangeInfoDataMapper().selectByExample(example);
	}

	@Override
	public boolean updateTBsCardStyle(TBsCardStyle record) {
		if (!StartClassPath.ONLY_TEST) {
		}
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}

		int row = getTBsCardStyleMapper().updateByPrimaryKeySelective(record);

		return (0 < row);
	}

	@Override
	public boolean updateTBsDeclareCorpAptitude(TBsDeclareCorpAptitude record) {
		if (!StartClassPath.ONLY_TEST) {
			record.setUpdatedby(FsConstant.DEFAULT_TRANSFER_USER);
			record.setUpdatedDate(new Date());
		}
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}

		int row = getTBsDeclareCorpAptitudeMapper().updateByPrimaryKeySelective(record);

		return (0 < row);
	}

	@Override
	public boolean updateTBsDeclareEntLinkman(TBsDeclareEntLinkman record) {
		if (!StartClassPath.ONLY_TEST) {
			record.setUpdatedby(FsConstant.DEFAULT_TRANSFER_USER);
			record.setUpdatedDate(new Date());
		}
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}

		int row = getTBsDeclareEntLinkmanMapper().updateByPrimaryKeySelective(record);

		return (0 < row);
	}

	@Override
	public boolean updateTBsEntChangeInfoApp(TBsEntChangeInfoApp record) {
		if (!StartClassPath.ONLY_TEST) {
			record.setUpdateby(FsConstant.DEFAULT_TRANSFER_USER);
			record.setUpdatedDate(new Date());
		}
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}

		int row = getTBsEntChangeInfoAppMapper().updateByPrimaryKeySelective(record);

		return (0 < row);
	}

	@Override
	public boolean updateTBsEntRealnameAuth(TBsEntRealnameAuth record) {
		if (!StartClassPath.ONLY_TEST) {
			record.setUpdateby(FsConstant.DEFAULT_TRANSFER_USER);
			record.setUpdatedDate(new Date());
		}
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}

		int row = getTBsEntRealnameAuthMapper().updateByPrimaryKeySelective(record);

		return (0 < row);
	}

	@Override
	public boolean updateTBsFilingAptitude(TBsFilingAptitude record) {
		if (!StartClassPath.ONLY_TEST) {
			record.setUpdatedby(FsConstant.DEFAULT_TRANSFER_USER);
			record.setUpdatedDate(new Date());
		}
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}

		int row = getTBsFilingAptitudeMapper().updateByPrimaryKeySelective(record);

		return (0 < row);
	}

	@Override
	public boolean updateTBsPerChangeInfoApp(TBsPerChangeInfoApp record) {
		if (!StartClassPath.ONLY_TEST) {
			record.setUpdateby(FsConstant.DEFAULT_TRANSFER_USER);
			record.setUpdatedDate(new Date());
		}
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}

		int row = getTBsPerChangeInfoAppMapper().updateByPrimaryKeySelective(record);

		return (0 < row);
	}

	@Override
	public boolean updateTBsPerRealnameAuth(TBsPerRealnameAuth record) {
		if (!StartClassPath.ONLY_TEST) {
			record.setUpdateby(FsConstant.DEFAULT_TRANSFER_USER);
			record.setUpdatedDate(new Date());
		}
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}

		int row = getTBsPerRealnameAuthMapper().updateByPrimaryKeySelective(record);

		return (0 < row);
	}

	@Override
	public boolean updateTBsPointgame(TBsPointgame record) {
		if (!StartClassPath.ONLY_TEST) {
			record.setUpdateby(FsConstant.DEFAULT_TRANSFER_USER);
			record.setUpdatedDate(new Date());
		}
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}

		TBsPointgameExample example = new TBsPointgameExample();
		example.createCriteria().andApplyIdEqualTo(record.getApplyId());

		int row = getTBsPointgameMapper().updateByExampleSelective(record,
				example);

		return (0 < row);
	}

	@Override
	public boolean updateTBsShippingMethod(TBsShippingMethod record) {
		if (!StartClassPath.ONLY_TEST) {
			record.setUpdatedby(FsConstant.DEFAULT_TRANSFER_USER);
			record.setUpdatedDate(new Date());
		}
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}

		TBsShippingMethodExample example = new TBsShippingMethodExample();
		example.createCriteria().andSmIdEqualTo(record.getSmId());

		int row = getTBsShippingMethodMapper().updateByExampleSelective(record,
				example);

		return (0 < row);
	}

	@Override
	public boolean updateTBsTaxrateChange(TBsTaxrateChange record) {
		if (!StartClassPath.ONLY_TEST) {
			record.setUpdatedby(FsConstant.DEFAULT_TRANSFER_USER);
			record.setUpdatedDate(new Date());
		}
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}

		TBsTaxrateChangeExample example = new TBsTaxrateChangeExample();
		example.createCriteria().andApplyIdEqualTo(record.getApplyId());

		int row = getTBsTaxrateChangeMapper().updateByExampleSelective(record,
				example);

		return (0 < row);
	}

	@Override
	public boolean updateTBsResetTranpwdapply(TBsResetTranpwdapply record) {
		if (!StartClassPath.ONLY_TEST) {
			record.setUpdatedby(FsConstant.DEFAULT_TRANSFER_USER);
			record.setUpdatedDate(new Date());
		}
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}

		int row = getTBsResetTranpwdapplyMapper().updateByPrimaryKeySelective(record);

		return (0 < row);
	}

	@Override
	public boolean updateTBsToolProduct(TBsToolProduct record) {
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}
		
		int row = getTBsToolProductMapper().updateByPrimaryKeySelective(record);

		return (0 < row);
	}

	@Override
	public boolean updateTBsDebit(TBsDebit record) {
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}
		
		if (!StartClassPath.ONLY_TEST) {
			record.setUpdatedby(FsConstant.DEFAULT_TRANSFER_USER);
			record.setUpdatedDate(new Date());
		}

		int row = getTBsDebitMapper().updateByPrimaryKeySelective(record);

		return (0 < row);
	}
	
	@Override
	public boolean updateTBsCompanyStop(TBsCompanyStop record) {
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}
		
		if (!StartClassPath.ONLY_TEST) {
			record.setUpdateby(FsConstant.DEFAULT_TRANSFER_USER);
			record.setUpdatedDate(new Date());
		}

		int row = getTBsCompanyStopMapper().updateByPrimaryKeySelective(record);

		return (0 < row);
	}
	
	@Override
	public boolean updateTBsEntChangeInfoData(TBsEntChangeInfoData record) {
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}

		int row = getTBsEntChangeInfoDataMapper().updateByPrimaryKeySelective(record);

		return (0 < row);
	}
	
	@Override
	public boolean updateTBsPerChangeInfoData(TBsPerChangeInfoData record) {
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}

		int row = getTBsPerChangeInfoDataMapper().updateByPrimaryKeySelective(record);

		return (0 < row);
	}
}
