/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.es.distribution.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.es.common.Constants;
import com.gy.hsxt.es.common.ListPage;
import com.gy.hsxt.es.distribution.bean.Alloc;
import com.gy.hsxt.es.distribution.handle.AllocHandle;
import com.gy.hsxt.es.distribution.handle.GenerateFile;
import com.gy.hsxt.es.distribution.mapper.PointAllocMapper;
import com.gy.hsxt.es.points.bean.PointDetail;
import com.gy.hsxt.es.points.mapper.PointMapper;

/**
 * 电商批量结单、确认收货
 * 
 * @Package: com.gy.hsxt.ps.distribution.service
 * @ClassName: EcBatchStatement
 * @Description: TODO
 * 
 * @author: chenhongzhi
 * @date: 2015-10-19 下午7:34:25
 * @version V3.0
 */
@Service
public class ConfirmReceiptService implements IConfirmReceiptService
{

	@Autowired
	private PointMapper pointMapper;
	@Autowired
	private PointAllocMapper pointAllocMapper;
	@Autowired
	private PointAllocService pointAllocService;

	/**
	 * 通过相互传递文件数据来处理批量预扣结单(电商)
	 * 
	 * @throws HsException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void batConfirmReceipt() throws HsException
	{
		List<Map<String, String>> allocList = new ArrayList<Map<String, String>>();
		int succeed = 0;
		int fail = 0;
		String[] array = null;
		String path = Constants.EC_SETTLEMENT_FILE_PATH + File.separator;
		List<String> fileList = GenerateFile.readDirFileName(path);

		if (fileList.size() > 0)
		{
			for (int n = 0; n < fileList.size(); n++)
			{
				String filePath = path + fileList.get(n);
				List<PointDetail> sublist = GenerateFile.readTxtFile(filePath, array);
				pointAllocMapper.ecSettleDataTemp(sublist);
				List<PointDetail> failList = pointAllocMapper.getFailData();

				pointMapper.batchUpdateReserveOrder();

				List<PointDetail> queryList = (List<PointDetail>) pointMapper.batchQueryReserveOrder();

				ListPage listPage = new ListPage(queryList, Constants.EC_BAT_UPDATE_MAXSUM);
				int pageCount = listPage.getTotalPages();
				if (pageCount >= 1)
				{
					for (int i = 1; i <= pageCount; i++)
					{
						List<PointDetail> dataList = (List<PointDetail>) listPage.getObjects(i);
						List<Alloc> batchList = this.allocSettle(dataList);
						this.allocDispose(batchList);
					}
				} else
				{
					List<Alloc> batchList = this.allocSettle(queryList);
					this.allocDispose(batchList);
				}
				succeed = succeed + queryList.size();
				if (failList.size() > 0)
				{
					fail = fail + failList.size();
					for (int f = 0; f < failList.size(); f++)
					{
						Map<String, String> map = new HashMap<String, String>();
						PointDetail pointDetail = failList.get(f);
						map.put("sourceTransNo", pointDetail.getSourceTransNo());
						map.put("reason", "找不到对应的数据");
						allocList.add(map);
					}
				}
			}
		} else
		{
			throw new HsException(RespCode.PS_FILE_NOT_FOUND.getCode(), "找不到文件");
		}
		AllocHandle.generationEcFile(allocList, succeed, fail, Constants.SETTLEMENT_EC);
	}

	/**
	 * 批量分配预扣结单
	 * 
	 * @param pdList
	 * @return
	 * @throws HsException
	 */
	public List<Alloc> allocSettle(List<PointDetail> pdList) throws HsException
	{
		List<Alloc> dataList = new ArrayList<Alloc>();
		if (pdList.size() > 0)
		{
			for (int i = 0; i < pdList.size(); i++)
			{
				PointDetail pointDetail = pdList.get(i);
				pointDetail.setBatchNo(DateUtil.DateToString(DateUtil.today(), "yyyyMMdd"));
				dataList.addAll(pointAllocService.allocPoint(pointDetail));
			}
		} else
		{
			throw new HsException(RespCode.PS_ORDER_NOT_FOUND.getCode(), "找不到原订单");
		}
		return dataList;
	}

	/**
	 * 分批分页预扣结单处理
	 * 
	 * @param list
	 * @return
	 * @throws HsException
	 */
	@SuppressWarnings("unchecked")
	public void allocDispose(List<Alloc> dataList) throws HsException
	{
		ListPage listPage = new ListPage(dataList, Constants.EC_BAT_INSERT_MAXSUM);
		int pageCount = listPage.getTotalPages();
		for (int i = 1; i <= pageCount; i++)
		{
			List<Alloc> allocList = (List<Alloc>) listPage.getObjects(i);
//			pointAllocService.getAllocDetail(allocList);
			pointAllocMapper.insertAlloc(allocList);
		}
	}

}
