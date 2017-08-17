package com.gy.hsxt.es.distribution.handle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.es.common.Compute;
import com.gy.hsxt.es.common.Constants;
import com.gy.hsxt.es.common.EsTools;
import com.gy.hsxt.es.points.bean.PointDetail;

public class GenerateFile
{

	private static String path = "C:\\PS";
	private static String fileNameTemp;

	/**
	 * 创建文件
	 * 
	 * @throws IOException
	 */
	public static boolean creatTxtFile(String name) throws IOException
	{
		boolean flag = false;
		String dirPath = path + File.separator;
		fileNameTemp = dirPath + name + ".txt";
		// createDir(dirPath);
		// System.out.println("[fileNameTemp]" + fileNameTemp);
		File fileName = new File(fileNameTemp);
		if (!fileName.exists())
		{
			fileName.createNewFile();
			flag = true;
		}
		return flag;
	}

	/**
	 * 读取该目录中的所有文件名称
	 * 
	 * @param path
	 * @return
	 * @throws HsException
	 */
	public static List<String> readDirFileName(String path) throws HsException
	{
		List<String> list = new ArrayList<String>();
		File file = new File(path);
		if (!file.exists())
		{
			System.out.println(path + " 找不到文件");
			throw new HsException(RespCode.PS_FILE_NOT_FOUND.getCode(), "找不到文件");
		}

		File fa[] = file.listFiles();
		for (int i = 0; i < fa.length; i++)
		{
			File fs = fa[i];
			if (fs.isDirectory())
			{
				System.out.println(fs.getName() + " [目录]");
			} else
			{
				list.add(fs.getName());
			}
		}
		return list;
	}

	/**
	 * 读取来自电商文件
	 * 
	 * @param filePath
	 * @throws HsException
	 */
	public static List<PointDetail> readTxtFile(String filePath, String[] oneLineArray) throws HsException
	{
		List<PointDetail> dataList = new ArrayList<PointDetail>();
		String encoding = "UTF-8";
		BufferedReader bufferedReader = null;
		try
		{
			File file = new File(filePath);
			if (file.isFile() && file.exists())
			{
				bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding));
				String lineInfo = null;
				try
				{
					String oneLine = bufferedReader.readLine();
					oneLineArray = oneLine.split("|");
					while ((lineInfo = bufferedReader.readLine()) != null)
					{
						String[] arr = lineInfo.split("\\|");
						if (lineInfo.equals("END"))
						{
							break;
						}
						PointDetail point = new PointDetail();
						point.setSourceTransNo(arr[0].trim());
						point.setEntResNo(arr[1].trim());
						point.setPerCustId(arr[2].trim());
						point.setSourceTransAmount(Compute.roundFloor(new BigDecimal(arr[3].trim()), Constants.SURPLUS_TWO));
						point.setEntPoint(Compute.roundFloor(new BigDecimal(arr[4].trim()), Constants.SURPLUS_TWO));
						point.setTransType(arr[5].trim());
						point.setBatchNo(arr[6].trim());

						dataList.add(point);
					}
				} catch (IOException e)
				{
					e.printStackTrace();
					throw new HsException(RespCode.PS_FILE_READ_WRITE_ERROR.getCode(), "文件读写错误");
				}
			} else
			{
				throw new HsException(RespCode.PS_FILE_NOT_FOUND.getCode(), "找不到指定的文件");
			}
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			throw new HsException(RespCode.PS_CODE_FORMAT_ERROR.getCode(), "文件编码格式错误");
		} catch (FileNotFoundException e)
		{
			throw new HsException(RespCode.PS_FILE_NOT_FOUND.getCode(), "找不到指定的文件");
		} finally
		{
			try
			{
				if (bufferedReader != null)
				{
					bufferedReader.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
				throw new HsException(RespCode.PS_FILE_READ_WRITE_ERROR.getCode(), "文件读写错误");
			}
		}
		return dataList;
	}

	/**
	 * 写入文件
	 * 
	 * @param info
	 * @throws IOException
	 */
	public static boolean writeTxtFile(String data)
	{
		// 先读取原有文件内容，然后进行写入操作
		boolean flag = false;
		// String fileIn = title + data;
		String temp = "";

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		FileOutputStream fos = null;
		PrintWriter pw = null;
		try
		{
			// 文件路径
			File file = new File(fileNameTemp);
			// 将文件读入输入流
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();
			// 保存该文件原有的内容
			while ((temp = br.readLine()) != null)
			{
				buf.append(temp);
				buf.append(System.getProperty("line.separator"));
			}
			buf.append(data);
			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			flag = true;
		} catch (IOException e1)
		{
			e1.printStackTrace();
		} finally
		{
			try
			{
				closePrintWriter(pw, fos, isr, br, fis);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return flag;
	}

	/** 积分数据文件列名信息 **/
	public static String pointColumn()
	{
		return "POINT_NO|CUST_ID|HS_RES_NO|ACC_TYPE|ADD_AMOUNT|ADD_COUNT|BATCH_NO|TRANS_DATE|FISCAL_DATE|TRANS_SYS"
				+ System.getProperty("line.separator");
	}

	/** 互生币数据文件列名信息 **/
	public static String hsbColumn()
	{
		return "HSB_NO|CUST_ID|HS_RES_NO|ACC_TYPE|ADD_AMOUNT|SUB_AMOUNT|ADD_COUNT|BATCH_NO|TRANS_DATE|FISCAL_DATE|TRANS_SYS"
				+ System.getProperty("line.separator");
	}

	/** 税收数据文件列名信息 **/
	public static String taxColumn()
	{
		return "TAX_NO|CUST_ID|HS_RES_NO|ACC_TYPE|TAX_AMOUNT|BATCH_NO|TRANS_DATE|FISCAL_DATE|TRANS_SYS" + System.getProperty("line.separator");
	}

	/** 商业服务费数据文件列名信息 **/
	public static String cscColumn()
	{
		return "CSC_NO|CUST_ID|HS_RES_NO|ACC_TYPE|CSC_AMOUNT|BATCH_NO|TRANS_DATE|FISCAL_DATE|TRANS_SYS" + System.getProperty("line.separator");
	}

	/**
	 * 关闭流
	 * 
	 * @param pw
	 * @param fos
	 * @param isr
	 * @param br
	 * @param fis
	 * @throws IOException
	 */
	public static void closePrintWriter(PrintWriter pw, FileOutputStream fos, InputStreamReader isr, BufferedReader br, FileInputStream fis)
			throws IOException
	{
		if (pw != null)
		{
			pw.close();
		}
		if (fos != null)
		{
			fos.close();
		}
		if (br != null)
		{
			br.close();
		}
		if (isr != null)
		{
			isr.close();
		}
		if (fis != null)
		{
			fis.close();
		}
	}

	/**
	 * 新建文件夹
	 * 
	 * @param path
	 * @return
	 */
	public static void createDir(String path)
	{
		String tmpPath = formatPath(path);
		if (!tmpPath.endsWith(File.separator))
		{
			tmpPath = tmpPath + File.separator;
		}
		System.out.println("tmpPath: " + tmpPath);
		File dir = new File(tmpPath);
		if (dir.exists())
		{
			System.out.println("创建文件夹失败，目标文件夹已存在!");
		} else
		{
			dir.mkdirs();
			System.out.println("创建文件夹成功!");
		}
	}

	/**
	 * 格式化路径
	 * 
	 * @param input
	 * @return
	 */
	public static String formatPath(String input)
	{
		Pattern pattern = Pattern.compile("\\\\{2,}");
		Matcher matcher = pattern.matcher(input);
		String out = matcher.replaceAll("\\\\");
		return out;
	}

	/**
	 * 查找结算失败的订单
	 * 
	 * @param list1
	 * @param list2
	 * @param reason
	 * @return
	 */
	public static List<Map<String, String>> getFailOrder(List<Map<String, String>> dataList, List<PointDetail> list1, List<PointDetail> list2,
			String reason)
	{
		long nanoTime = System.nanoTime();
		List<PointDetail> maxList = list1;
		List<PointDetail> minList = list2;
		if (list2.size() > list1.size())
		{
			maxList = list2;
			minList = list1;
		}
		Map<String, Integer> hm = new HashMap<String, Integer>(maxList.size());
		for (PointDetail point : maxList)
		{
			hm.put(point.getSourceTransNo(), 1);
		}
		for (PointDetail point : minList)
		{
			Map<String, String> m = new HashMap<String, String>();
			if (hm.get(point) != null)
			{
				hm.put(point.getSourceTransNo(), 2);
				continue;
			}
			// diff.add(point.getSourceTransNo());
			m.put("sourceTransNo", point.getSourceTransNo());
			m.put("reason", reason);
			dataList.add(m);
		}
		for (Map.Entry<String, Integer> entry : hm.entrySet())
		{
			Map<String, String> m = new HashMap<String, String>();
			if (entry.getValue() == 1)
			{
				// diff.add(entry.getKey());
				m.put("sourceTransNo", entry.getKey());
				m.put("reason", reason);
				dataList.add(m);
			}
		}

		EsTools.jsonString(dataList);

		System.out.println("getFailOrder total times " + (System.nanoTime() - nanoTime));
		return dataList;

	}

}
