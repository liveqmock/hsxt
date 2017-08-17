package com.gy.hsxt.es.distribution.handle;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.es.common.Constants;
import com.gy.hsxt.es.common.EsTools;
import com.gy.hsxt.es.distribution.bean.Alloc;

public class AllocHandle
{

	private static List<Alloc> monthList = new ArrayList<Alloc>();

	public AllocHandle()
	{

	}

	public static List<Alloc> getMonthServiceFee()
	{
		return monthList;
	}


	/**
	 * 城市扣税
	 * 
	 * @param aList
	 * @return
	 */
	public static List<Alloc> cityTax(List<Alloc> aList)
	{
		List<Alloc> list = new ArrayList<Alloc>();
		for (int i = 0; i < aList.size(); i++)
		{
			Alloc alloc = aList.get(i);
			if (alloc.getAccType().equals(AccountType.ACC_TYPE_POINT10110.getCode()))
			{
				String custId = alloc.getCustId();
				if (EsTools.isEquals(custId, 2))
				{
					alloc.setTaxNo(EsTools.GUID(Constants.NO_POINT_TAX22));
					alloc.setTaxAmount(AllocItem.getTaxPoint(EsTools.formatBigDec(alloc.getAddAmount()), Constants.HS_MANAGE));
					alloc.setTaxAccType(AccountType.ACC_TYPE_POINT10510.getCode());
					list.add(alloc);

				} else if (!EsTools.isEquals(custId, 2) && EsTools.isEquals(custId, 5))
				{
					alloc.setTaxNo(EsTools.GUID(Constants.NO_POINT_TAX22));
					alloc.setTaxAmount(AllocItem.getTaxPoint(EsTools.formatBigDec(alloc.getAddAmount()), Constants.HS_SERVICE));
					alloc.setTaxAccType(AccountType.ACC_TYPE_POINT10510.getCode());
					list.add(alloc);

				} else if (!EsTools.isEquals(custId, 2) && !EsTools.isEquals(custId, 5) && EsTools.isEquals(custId, 7))
				{
					alloc.setTaxNo(EsTools.GUID(Constants.NO_POINT_TAX22));
					alloc.setTaxAmount(AllocItem.getTaxPoint(EsTools.formatBigDec(alloc.getAddAmount()), Constants.HS_TRUSTEE));
					alloc.setTaxAccType(AccountType.ACC_TYPE_POINT10510.getCode());
					list.add(alloc);
				}
			}
		}
		return list;
	}

	/**
	 * 日终暂存商业服务费
	 * 
	 * @param aList
	 * @return
	 */
	public static List<Alloc> serviceDayCsc(List<Alloc> aList)
	{
		List<Alloc> list = new ArrayList<Alloc>();
		for (int i = 0; i < aList.size(); i++)
		{
			Alloc alloc = aList.get(i);
			alloc.setCscNo(EsTools.GUID(Constants.NO_SERVICE_FEE32));
			alloc.setRelAllocNo(EsTools.GUID(Constants.NO_HSB_ONLINE_SUM30));
			alloc.setSourceBatchNo(alloc.getBatchNo());
			alloc.setBatchNo(DateUtil.DateToString(DateUtil.getCurrentDate(), "yyyyMMdd"));
			alloc.setCscAmount(AllocItem.getBusinessServiceFee(EsTools.formatBigDec(alloc.getAddAmount())));
			alloc.setTaxAccType(AccountType.ACC_TYPE_POINT20421.getCode());
			list.add(alloc);
		}
		return list;
	}

	/**
	 * 扣税后汇总
	 * 
	 * @param aList
	 * @param dList
	 * @return
	 */
	public static List<Alloc> taxAfterSum(List<Alloc> aList, List<Alloc> dList)
	{
		BigDecimal sum = new BigDecimal(0.00);
		for (int j = 0; j < aList.size(); j++)
		{
			Alloc aAlloc = aList.get(j);
			for (int i = 0; i < dList.size(); i++)
			{
				Alloc dAlloc = dList.get(i);
				if (aAlloc.getCustId().equals(dAlloc.getCustId()) && dAlloc.getTaxAccType().equals(AccountType.ACC_TYPE_POINT20610.getCode()))
				{
					sum = AllocItem.getSumSubTax(aAlloc.getAddAmount(), dAlloc.getTaxAmount());
					aAlloc.setAddAmount(sum);
				}
			}
		}
		return aList;
	}

	/**
	 * 待清算账户减(减商业服务费)
	 * 
	 * @param hsbList
	 *            , bsfList
	 * @return
	 */
	public static List<Alloc> hsbSumSub(List<Alloc> hsbList, List<Alloc> bsfList)
	{
		for (int i = 0; i < hsbList.size(); i++)
		{
			Alloc subAlloc = hsbList.get(i);
			subAlloc.setHsbSumNo(EsTools.GUID(Constants.NO_HSB_ONLINE_SUM30));
			subAlloc.setAccType(AccountType.ACC_TYPE_POINT20111.getCode());
			subAlloc.setSourceBatchNo(subAlloc.getBatchNo());
			String batchNo = DateUtil.DateToString(DateUtil.getCurrentDate(), "yyyyMMdd");
			subAlloc.setBatchNo(batchNo);
			for (int j = 0; j < bsfList.size(); j++)
			{
				Alloc cscAlloc = bsfList.get(j);
				if (subAlloc.getCustId().equals(cscAlloc.getCustId()) && subAlloc.getAccType().equals(cscAlloc.getAccType()))
				{
					BigDecimal bigD = AllocItem.getSumSubTax(subAlloc.getAddAmount(), subAlloc.getSubAmount());
					BigDecimal sub = AllocItem.getSumSubTax(bigD, cscAlloc.getCscAmount());
					subAlloc.setSubAmount(sub);
					subAlloc.setHsbSumNo(cscAlloc.getRelAllocNo());
				}
			}
			subAlloc.setAddAmount(new BigDecimal(0.00));
		}
		return hsbList;
	}

	/**
	 * 待清算账户减商业服务费后汇总到流通币账户
	 * 
	 * @param hsbList
	 *            , bsfList
	 * @return
	 */
	public static List<Alloc> hsbSumSubList(List<Alloc> hsbList, List<Alloc> bsfList, List<Map<String, String>> dataList)
	{
		for (int i = 0; i < hsbList.size(); i++)
		{
			Alloc subAlloc = hsbList.get(i);
			Map<String, String> map = new HashMap<String, String>();
			String hsbSumNo = EsTools.GUID(Constants.NO_HSB_ONLINE_SUM30);
			String accType = AccountType.ACC_TYPE_POINT20110.getCode();
			String batchNo = DateUtil.DateToString(DateUtil.getCurrentDate(), "yyyyMMdd");
			map.put("hsbSumNo", hsbSumNo);
			map.put("custId", subAlloc.getCustId());
			map.put("hsResNo", subAlloc.getHsResNo());
			map.put("accType", accType);
			map.put("sourceBatchNo", subAlloc.getBatchNo());
			map.put("batchNo", batchNo);
			map.put("addCount", subAlloc.getAddCount().toString());

			subAlloc.setSourceBatchNo(subAlloc.getBatchNo());
			subAlloc.setBatchNo(batchNo);
			subAlloc.setHsbSumNo(hsbSumNo);
			subAlloc.setAccType(accType);
			for (int j = 0; j < bsfList.size(); j++)
			{
				Alloc cscAlloc = bsfList.get(j);
				if (subAlloc.getCustId().equals(cscAlloc.getCustId()))
				{
					BigDecimal bigD = AllocItem.getSumSubTax(subAlloc.getAddAmount(), subAlloc.getSubAmount());
					BigDecimal sub = AllocItem.getSumSubTax(bigD, cscAlloc.getCscAmount());
					System.out.println("sub: " + sub);
					subAlloc.setAddAmount(sub);
					map.put("addAmount", sub.toString());
					map.put("subAmount", "0.00");
				}
			}
			subAlloc.setSubAmount(new BigDecimal(0.00));
			dataList.add(map);
		}
		return hsbList;
	}

	/**
	 * 日终互生币汇总(待清算账户转流通币账户)
	 * 
	 * @param hsbList
	 * @return
	 */
	public static List<Alloc> hsbSumAdd(List<Alloc> hsbList, List<Map<String, String>> dataList)
	{
		for (int i = 0; i < hsbList.size(); i++)
		{
			Alloc addAlloc = hsbList.get(i);
			Map<String, String> map = new HashMap<String, String>();

			String hsbSumUid = EsTools.GUID(Constants.NO_HSB_ONLINE_SUM30);
			String accType = AccountType.ACC_TYPE_POINT20110.getCode();

			map.put("hsbSumNo", hsbSumUid);
			map.put("custId", addAlloc.getCustId());
			map.put("hsResNo", addAlloc.getHsResNo());
			map.put("accType", accType);
			map.put("sourceBatchNo", addAlloc.getBatchNo());
			map.put("batchNo", addAlloc.getBatchNo());
			map.put("addAmount", addAlloc.getSubAmount().toString());
			map.put("subAmount", "0.00");
			map.put("addCount", addAlloc.getAddCount().toString());

			addAlloc.setHsbSumNo(hsbSumUid);
			addAlloc.setAccType(accType);
			addAlloc.setAddAmount(addAlloc.getSubAmount());
			addAlloc.setSubAmount(new BigDecimal(0.00));
			dataList.add(map);
		}
		return hsbList;
	}

	/**
	 * 月终服务公司的商业服务费结算
	 * 
	 */
	public static List<Alloc> serviceMonthServiceFee(List<Alloc> aList)
	{
		for (int i = 0; i < aList.size(); i++)
		{
			Alloc alloc = aList.get(i);
			alloc.setCscNo(EsTools.GUID(Constants.NO_SERVICE_FEE32));
			String custId = alloc.getCustId().substring(0, 5) + "000000";
			alloc.setCustId(custId);
			alloc.setCscAmount(AllocItem.getSumSubServiceMonthFee(alloc.getTaxAmount()));
			alloc.setTaxAccType(AccountType.ACC_TYPE_POINT20110.getCode());
			monthList.add(alloc);
		}
		return aList;
	}

	/**
	 * 月结商业服务费先扣城市税收
	 * 
	 * @param list
	 * @return
	 */
	public static List<Alloc> monthBuckleCityTax(List<Alloc> list)
	{
		for (int i = 0; i < list.size(); i++)
		{
			Alloc alloc = list.get(i);
			alloc.setTaxNo(EsTools.GUID(Constants.NO_POINT_TAX22));
			alloc.setTaxAmount(AllocItem.cityTaxService(alloc.getTaxAmount()));
			alloc.setTaxAccType(AccountType.ACC_TYPE_POINT20610.getCode());
		}
		return list;
	}

	/**
	 * 月终平台的商业服务费结算
	 */
	public static List<Alloc> paasMonthServiceFee(List<Alloc> aList)
	{
		for (int i = 0; i < aList.size(); i++)
		{
			Alloc alloc = aList.get(i);
			String custId = alloc.getCustId().substring(0, 5) + "000000";
			for (int j = 0; j < monthList.size(); j++)
			{
				Alloc monthAlloc = monthList.get(i);
				String mCustId = monthAlloc.getCustId();
				if (custId.equals(mCustId))
				{
					alloc.setCscNo(EsTools.GUID(Constants.NO_SERVICE_FEE32));
					String paasCustId = alloc.getCustId().substring(0, 2) + "000000000";
					alloc.setCustId(paasCustId);
					alloc.setCscAmount(AllocItem.getSumSubPaasMonthFee(alloc.getTaxAmount(), monthAlloc.getCscAmount()));
					alloc.setTaxAccType(AccountType.ACC_TYPE_POINT20420.getCode());
				}
			}
		}
		return aList;
	}

	/**
	 * 组装积分汇总(税后)
	 * 
	 * @param list
	 * @return
	 */
	public static StringBuffer getPointListData(List<Alloc> list)
	{
		StringBuffer dataBuf = new StringBuffer();

		for (int i = 0; i < list.size(); i++)
		{
			Alloc alloc = (Alloc) list.get(i);
			dataBuf.append(alloc.getSumNo().trim());
			dataBuf.append("|");
			dataBuf.append(alloc.getCustId().trim());
			dataBuf.append("|");
			dataBuf.append(alloc.getHsResNo().trim());
			dataBuf.append("|");
			dataBuf.append(alloc.getAccType().trim());
			dataBuf.append("|");
			dataBuf.append(EsTools.amountDecimalFormat(alloc.getAddAmount()));
			dataBuf.append("|");
			dataBuf.append(alloc.getAddCount());
			dataBuf.append("|");
			dataBuf.append(alloc.getBatchNo().trim());
			dataBuf.append("|");
			dataBuf.append(System.currentTimeMillis());
			dataBuf.append("|");
			dataBuf.append(EsTools.getDateLong(alloc.getBatchNo() + " 23:59:59"));
			dataBuf.append("|");
			dataBuf.append("PS");
			dataBuf.append(System.getProperty("line.separator"));
		}
		dataBuf.append("END");
		return dataBuf;
	}

	/**
	 * 组装城市税收
	 * 
	 * @param list
	 * @return
	 */
	public static StringBuffer getTaxListData(List<Alloc> list)
	{
		StringBuffer dataBuf = new StringBuffer();

		for (int i = 0; i < list.size(); i++)
		{
			Alloc alloc = (Alloc) list.get(i);
			dataBuf.append(alloc.getTaxNo().trim());
			dataBuf.append("|");
			dataBuf.append(alloc.getCustId().trim());
			dataBuf.append("|");
			dataBuf.append(alloc.getHsResNo().trim());
			dataBuf.append("|");
			dataBuf.append(alloc.getTaxAccType().trim());
			dataBuf.append("|");
			dataBuf.append(EsTools.amountDecimalFormat(alloc.getTaxAmount()));
			dataBuf.append("|");
			dataBuf.append(alloc.getBatchNo().trim());
			dataBuf.append("|");
			dataBuf.append(System.currentTimeMillis());
			dataBuf.append("|");
			dataBuf.append(EsTools.getDateLong(alloc.getBatchNo() + " 23:59:59"));
			dataBuf.append("|");
			dataBuf.append("PS");
			dataBuf.append(System.getProperty("line.separator"));
		}
		dataBuf.append("END");
		return dataBuf;
	}

	/**
	 * 组装互生币汇总、(已减商业服务费)
	 * 
	 * @param list
	 * @return
	 */
	public static StringBuffer getHsbListData(List<Map<String, String>> list)
	{
		StringBuffer dataBuf = new StringBuffer();
		for (int i = 0; i < list.size(); i++)
		{
			Map<String, String> map = (Map<String, String>) list.get(i);

			dataBuf.append(map.get("hsbSumNo"));
			dataBuf.append("|");
			dataBuf.append(map.get("custId"));
			dataBuf.append("|");
			dataBuf.append(map.get("hsResNo"));
			dataBuf.append("|");
			dataBuf.append(map.get("accType"));
			dataBuf.append("|");
			dataBuf.append(map.get("addAmount"));
			dataBuf.append("|");
			dataBuf.append(map.get("subAmount"));
			dataBuf.append("|");
			dataBuf.append(map.get("addCount"));
			dataBuf.append("|");
			dataBuf.append(map.get("batchNo"));
			dataBuf.append("|");
			dataBuf.append(map.get("batchNo") + " 23:59:59");
			dataBuf.append("|");
			dataBuf.append(System.currentTimeMillis());
			dataBuf.append("|");
			dataBuf.append("PS");

			dataBuf.append(System.getProperty("line.separator"));
		}
		dataBuf.append("END");
		return dataBuf;
	}

	/**
	 * 组装商业服务费
	 * 
	 * @param list
	 * @return
	 */
	public static StringBuffer getCscListData(List<Alloc> list)
	{
		StringBuffer dataBuf = new StringBuffer();

		for (int i = 0; i < list.size(); i++)
		{
			Alloc alloc = (Alloc) list.get(i);
			dataBuf.append(alloc.getCscNo().trim());
			dataBuf.append("|");
			dataBuf.append(alloc.getCustId().trim());
			dataBuf.append("|");
			dataBuf.append(alloc.getHsResNo().trim());
			dataBuf.append("|");
			dataBuf.append(alloc.getTaxAccType().trim());
			dataBuf.append("|");
			dataBuf.append(EsTools.amountDecimalFormat(alloc.getCscAmount()));
			dataBuf.append("|");
			dataBuf.append(alloc.getBatchNo().trim());
			dataBuf.append("|");
			dataBuf.append(System.currentTimeMillis());
			dataBuf.append("|");
			dataBuf.append(EsTools.getDateLong(alloc.getBatchNo() + " 23:59:59"));
			dataBuf.append("|");
			dataBuf.append("PS");
			dataBuf.append(System.getProperty("line.separator"));
		}
		dataBuf.append("END");
		return dataBuf;
	}

	/**
	 * 结算后通过文件反馈结果给电商
	 * 
	 * @param list
	 * @param succeed
	 * @param fail
	 */
	public static StringBuffer feedbackData(List<Map<String, String>> list, int succeed, int fail)
	{
		StringBuffer sbData = new StringBuffer();
		sbData.append(String.valueOf(succeed));
		sbData.append("|");
		sbData.append(String.valueOf(fail));
		sbData.append(System.getProperty("line.separator"));
		if (list.size() > 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				Map<String, String> map = list.get(i);
				sbData.append(map.get("sourceTransNo"));
				sbData.append("|");
				sbData.append(map.get("reason"));
				sbData.append(System.getProperty("line.separator"));
			}
		}
		sbData.append("END");
		return sbData;
	}

	/**
	 * 生成check文件
	 * 
	 * @param fileName
	 * @param list
	 * @return
	 */
	public static StringBuffer checkFileData(String fileName, List<?> list)
	{
		StringBuffer sbData = new StringBuffer();
		sbData.append(fileName);
		sbData.append("|");
		sbData.append(list.size());
		sbData.append(System.getProperty("line.separator"));
		return sbData;
	}

	/**
	 * 生成积分分配数据文件
	 * 
	 * @param list
	 * @param count
	 */
	public static void generationPointFile(List<Alloc> list, String typeName, int count)
	{
		String data = "";
		String fileName = typeName + "_" + EsTools.getBeforeDay() + "_" + count;
		data = GenerateFile.pointColumn() + AllocHandle.getPointListData(list).toString().trim();
		writeFile(fileName, data);
		String checkFileName = EsTools.getBeforeDay() + "_CHK";
		String size = AllocHandle.checkFileData(fileName, list).toString().trim();
		writeFile(checkFileName, size);
		writeFile(checkFileName, "END");
	}

	/**
	 * 生成积分税收数据文件
	 * 
	 * @param list
	 * @param count
	 */
	public static void generationPointTaxFile(List<Alloc> list, String typeName, int count)
	{
		String data = "";
		String fileName = typeName + "_" + EsTools.getBeforeDay() + "_" + count;
		data = GenerateFile.taxColumn() + AllocHandle.getTaxListData(list).toString().trim();
		writeFile(fileName, data);
		String checkFileName = EsTools.getBeforeDay() + "_CHK";
		String size = AllocHandle.checkFileData(fileName, list).toString().trim();
		writeFile(checkFileName, size);
	}

	/**
	 * 生成互生币分配数据文件
	 * 
	 * @param list
	 * @param count
	 */
	public static void generationHsbFile(List<Map<String, String>> list, String typeName, int count)
	{
		String data = "";
		String fileName = typeName + "_" + EsTools.getBeforeDay() + "_" + count;
		data = GenerateFile.hsbColumn() + AllocHandle.getHsbListData(list).toString().trim();
		writeFile(fileName, data);
		String checkFileName = EsTools.getBeforeDay() + "_CHK";
		String size = AllocHandle.checkFileData(fileName, list).toString().trim();
		writeFile(checkFileName, size);
	}

	/**
	 * 生成企业互生币商业服务费数据文件
	 * 
	 * @param list
	 * @param count
	 */
	public static void generationHsbCscFile(List<Alloc> list, String typeName, int count)
	{
		String data = "";
		String fileName = typeName + "_" + EsTools.getBeforeDay() + "_" + count;
		data = GenerateFile.cscColumn() + AllocHandle.getCscListData(list).toString().trim();
		writeFile(fileName, data);
		String checkFileName = EsTools.getBeforeDay() + "_CHK";
		String size = AllocHandle.checkFileData(fileName, list).toString().trim();
		writeFile(checkFileName, size);
	}

	/**
	 * 生成电商结单的回馈文件
	 * 
	 * @param list
	 * @param typeName
	 * @param count
	 */
	public static void generationEcFile(List<Map<String, String>> list, int succeed, int fail, String typeName)
	{
		String data = "";
		String fileName = typeName + "_" + DateUtil.DateToString(DateUtil.today());
		data = AllocHandle.feedbackData(list, succeed, fail).toString().trim();
		writeFile(fileName, data);
	}

	/**
	 * 创建并写入文件
	 * 
	 * @param fileName
	 * @param data
	 */
	public static void writeFile(String fileName, String data)
	{
		try
		{
			GenerateFile.creatTxtFile(fileName);
			GenerateFile.writeTxtFile(data);
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new HsException(RespCode.PS_FILE_READ_WRITE_ERROR.getCode(), "文件读写错误");
		}
	}

	/**
	 * list中数据写入文件(积分结算, 默认每10万生成1个文件)
	 * 
	 * @param list
	 */
	public static void writeFile(List<Alloc> list, String typeName)
	{
		int index = 0;
		int count = 0;
		List<Alloc> fileList = new ArrayList<Alloc>();
		for (int i = 0; i < list.size(); i++)
		{
			fileList.add(list.get(i));
			count++;
			if (count == Constants.DATA_COUNT)
			{
				index++;
				if (typeName.equals(Constants.SETTLEMENT_POINT))
				{
					generationPointFile(fileList, typeName, index);

				} else if (typeName.equals(Constants.SETTLEMENT_TAX))
				{
					generationPointTaxFile(fileList, typeName, index);

				} else if (typeName.equals(Constants.SETTLEMENT_CSC))
				{
					generationHsbCscFile(fileList, typeName, index);
				}
				fileList.clear();
				count = 0;
			}
		}
		if (count > 0)
		{
			index += 1;
			if (typeName.equals(Constants.SETTLEMENT_POINT))
			{
				generationPointFile(fileList, typeName, index);

			} else if (typeName.equals(Constants.SETTLEMENT_TAX))
			{
				generationPointTaxFile(fileList, typeName, index);

			} else if (typeName.equals(Constants.SETTLEMENT_CSC))
			{
				generationHsbCscFile(fileList, typeName, index);
			}
		}
	}

	/**
	 * list中数据写入文件(互生币结算,默认每10万生成1个文件)
	 * 
	 * @param list
	 */
	public static void writeHsbFile(List<Map<String, String>> list, String typeName)
	{
		int index = 0;
		int count = 0;
		List<Map<String, String>> fileList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < list.size(); i++)
		{
			fileList.add(list.get(i));
			count++;
			if (count == Constants.DATA_COUNT)
			{
				index++;
				generationHsbFile(fileList, typeName, index);
				fileList.clear();
				count = 0;
			}
		}
		if (count > 0)
		{
			index += 1;
			generationHsbFile(fileList, typeName, index);
		}
	}

}
