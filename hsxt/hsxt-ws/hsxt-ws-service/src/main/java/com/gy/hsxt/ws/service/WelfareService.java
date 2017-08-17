package com.gy.hsxt.ws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.api.IWsWelfareService;
import com.gy.hsxt.ws.bean.Pager;
import com.gy.hsxt.ws.bean.WelfareQualification;
import com.gy.hsxt.ws.bean.WelfareQualify;
import com.gy.hsxt.ws.mapper.ApprovalRecordMapper;
import com.gy.hsxt.ws.mapper.WelfareQualificationMapper;

/**
 * 提供福利资格服务
 * 
 * @Package: com.gy.hsxt.ws.service
 * @ClassName: WelfareService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-21 下午3:26:47
 * @version V1.0
 */
@Service
public class WelfareService extends BasicService implements IWsWelfareService {

	@Autowired
	WelfareQualificationMapper qualifyMapper;

	@Autowired
	ApprovalRecordMapper approvalRecordMapper;

	/**
	 * 地区平台查询积分福利资格
	 * 
	 * @param hsResNo
	 *            互生号 选传
	 * @param welfareType
	 *            福利类型 0 意外伤害、1 医疗保障
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsWelfareService#listWelfareQualify(java.lang.String,
	 *      java.lang.Integer, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<WelfareQualify> listWelfareQualify(String hsResNo, Integer welfareType,
			Page page) throws HsException {
		// 数据验证
		validParamIsEmptyOrNull(welfareType, "福利类型参数[welfareType]为空");
		validParamIsEmptyOrNull(page, "分页类型参数[page]为null");

		// 查询总记录数
		int totalSize = qualifyMapper.totalSize(hsResNo, welfareType);
		if (totalSize == 0) {
			return null;
		}

		// 分页查询积分资格数据
		List<WelfareQualification> list = qualifyMapper.pageWelfareQualify(hsResNo, welfareType,
				page);

		// 构建返回数据
		List<WelfareQualify> result = new ArrayList<WelfareQualify>();
		for (WelfareQualification welfareQualification : list) {
			result.add(welfareQualification.generateWelfareQualify());
		}
		return new PageData<>(totalSize, result);
	}

	/**
	 * 查询福利资格信息
	 * 
	 * @param hsResNO
	 *            互生号 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsWelfareService#findWelfareQualify(java.lang.String)
	 */
	@Override
	public WelfareQualify findWelfareQualify(String hsResNO) throws HsException {
		// 数据验证
		validParamIsEmptyOrNull(hsResNO, "互生号参数[hsResNO]为空");

		WelfareQualification welfareQualification = qualifyMapper.findWelfareQualify(hsResNO);
		if (welfareQualification != null) {
			return welfareQualification.generateWelfareQualify();
		}
		return null;
	}

	/**
	 * 是否有资格
	 * 
	 * @param hsResNO
	 *            客户号 必传
	 * @param welfareType
	 *            福利类型 0 意外伤害(包含身故)、1 医疗保障 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsWelfareService#isHaveWelfareQualify(java.lang.String,
	 *      java.lang.Integer)
	 */
	@Override
	public boolean isHaveWelfareQualify(String hsResNO, Integer welfareType) throws HsException {
		// 数据验证
		validParamIsEmptyOrNull(hsResNO, "互生号参数[hsResNO]为空");
		validParamIsEmptyOrNull(welfareType, "福利类型参数[welfareType]为空");

		int count = qualifyMapper.countWelfareQualify(hsResNO, welfareType);
		return count > 0;
	}

	/**
	 * 查询所有的福利资格
	 * 
	 * @param hsResNO
	 *            互生号 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsWelfareService#queryHisWelfareQualify(java.lang.String)
	 */
	@Override
	public List<WelfareQualify> queryHisWelfareQualify(String hsResNO) throws HsException {
		// 数据验证
		validParamIsEmptyOrNull(hsResNO, "互生号参数[hsResNO]为空");
		List<WelfareQualification> list = qualifyMapper.queryHisWelfareQualify(hsResNO);
		if (list == null || list.isEmpty()) {
			return null;
		}
		// 构建返回数据
		List<WelfareQualify> result = new ArrayList<WelfareQualify>();
		for (WelfareQualification welfareQualification : list) {
			result.add(welfareQualification.generateWelfareQualify());
		}
		return result;
	}

	/**
	 * 分页查询消费者历史福利资格
	 * 
	 * @param hsResNO
	 *            互生号 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsWelfareService#queryHisWelfareQualify(java.lang.String,
	 *      com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<WelfareQualify> queryHisWelfareQualify(String hsResNO, Page page)
			throws HsException {
		List<WelfareQualify> list = queryHisWelfareQualify(hsResNO);
		PageData<WelfareQualify> result = new PageData<>();
		if (list == null) {
			list = new ArrayList<WelfareQualify>();
			result.setCount(0);
			result.setResult(list);
			return result;
		}
		List<WelfareQualify> data = Pager.create(list, page.getPageSize()).getPagedList(
				page.getCurPage());
		result.setCount(list.size());
		result.setResult(data);
		return result;
	}

}
