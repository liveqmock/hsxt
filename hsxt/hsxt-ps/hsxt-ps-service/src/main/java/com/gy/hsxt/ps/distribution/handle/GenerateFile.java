/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.distribution.handle;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.common.Compute;
import com.gy.hsxt.ps.common.Constants;
import com.gy.hsxt.ps.common.PsException;
import com.gy.hsxt.ps.common.PsTools;
import com.gy.hsxt.ps.points.bean.PointDetail;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.math.BigDecimal;

import org.apache.commons.io.Charsets;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version V3.0
 * @Package: com.gy.hsxt.ps.distribution.handle
 * @ClassName: GenerateFile
 * @Description: 文件处理
 * @author: chenhongzhi
 * @date: 2015-12-8 上午9:20:21
 */

public class GenerateFile {

    private static String fileNameTemp;

    /**
     * 创建文件
     *
     * @throws IOException
     */
    public static boolean creatTxtFile(String name, String dirPath) throws IOException {
        boolean flag = false;
        fileNameTemp = dirPath + name.toUpperCase() + ".TXT";
        System.out.println(fileNameTemp);
        createDir(dirPath);
        System.out.println("[fileNameTemp]" + fileNameTemp);
        File fileName = new File(fileNameTemp);
        if (!fileName.exists()) {
            flag = fileName.createNewFile();
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
    public static List<String> readDirFileName(String path) throws HsException {
        List<String> list = new ArrayList<String>();
        File file = new File(path);
        if (!file.exists()) {
            throw new HsException(RespCode.PS_FILE_NOT_FOUND.getCode(), "找不到文件");
        }

        File fa[] = file.listFiles();
        if (fa != null) {
            for (int i = 0; i < fa.length; i++) {
                File fs = fa[i];
                if (fs.isDirectory()) {
                    System.out.println(fs.getName() + " [目录]");
                } else {
                    list.add(fs.getName());
                }
            }
        }
        return list;
    }

    /**
     * 读取来自电商文件(电商文件方式批结算)
     *
     * @param filePath
     * @throws HsException
     */
    public static List<PointDetail> readTxtFile(String filePath, String[] oneLineArray)
            throws HsException {
        List<PointDetail> dataList = new ArrayList<PointDetail>();
        String encoding = Charsets.UTF_8.displayName();
        BufferedReader bufferedReader = null;
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(
                        filePath), encoding));
                String lineInfo = null;
                try {
                    while ((lineInfo = bufferedReader.readLine()) != null) {
                        String[] arr = lineInfo.split("\\|");
                        if (lineInfo.equals("END")) {
                            break;
                        }
                        PointDetail point = new PointDetail();
                        point.setSourceTransNo(arr[0].trim());
                        point.setEntResNo(arr[1].trim());
                        point.setPerCustId(arr[2].trim());
                        point.setSourceTransAmount(Compute.roundFloor(
                                new BigDecimal(arr[3].trim()), Constants.SURPLUS_TWO));
                        point.setEntPoint(Compute.roundFloor(new BigDecimal(arr[4].trim()),
                                Constants.SURPLUS_TWO));
                        point.setTransType(arr[5].trim());

                        dataList.add(point);
                    }
                } catch (IOException e) {
                    throw new HsException(RespCode.PS_FILE_READ_WRITE_ERROR.getCode(), "文件读写错误");
                }
            } else {
                throw new HsException(RespCode.PS_FILE_NOT_FOUND.getCode(), "找不到指定的文件");
            }
        } catch (UnsupportedEncodingException e) {
            throw new HsException(RespCode.PS_CODE_FORMAT_ERROR.getCode(), "文件编码格式错误");
        } catch (FileNotFoundException e) {
            throw new HsException(RespCode.PS_FILE_NOT_FOUND.getCode(), "找不到指定的文件");
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                throw new HsException(RespCode.PS_FILE_READ_WRITE_ERROR.getCode(), "文件读写错误");
            }
        }
        return dataList;
    }

    /**
     * 写入文件
     *
     * @param data
     * @throws IOException
     */
    public static boolean writeTxtFile(String data) {
        // 先读取原有文件内容，然后进行写入操作
        boolean flag = false;
        // 文件路径
        File file = new File(fileNameTemp);
        try {
            FileUtils.writeStringToFile(file, data.trim(), Charsets.UTF_8, false);
            flag = true;
        } catch (IOException e) {
            throw new HsException(RespCode.PS_FILE_READ_WRITE_ERROR.getCode(), "文件读写错误");
        }

        return flag;
    }


    /**
     * 写入文件
     *
     * @param data
     * @throws IOException
     */
    public static boolean writeTxtFile11111111111111111111111(String data) {
        // 先读取原有文件内容，然后进行写入操作
        boolean flag = false;
        // String fileIn = title + data;
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            // 文件路径
            File file = new File(fileNameTemp);
            // 将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, Charset.defaultCharset());
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();
            // 保存该文件原有的内容
            while ((temp = br.readLine()) != null) {
                buf.append(temp);
                buf.append(System.getProperty("line.separator"));
            }
            buf.append(data);
            fos = new FileOutputStream(file);
            pw = new PrintWriter((new OutputStreamWriter(fos, Charset.defaultCharset())));
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (IOException e1) {
            throw new HsException(RespCode.PS_FILE_READ_WRITE_ERROR.getCode(), "文件读写错误");

        } finally {
            try {
                closePrintWriter(pw, fos, isr, br, fis);
            } catch (IOException e) {
                throw new HsException(RespCode.PS_FILE_READ_WRITE_ERROR.getCode(), "文件读写错误");
            }
        }
        return flag;
    }

    /** 积分数据文件列名信息 **/
//	public static String pointColumn()
//	{
//		return "POINT_NO|CUST_ID|HS_RES_NO|ACC_TYPE|ADD_AMOUNT|ADD_COUNT|BATCH_NO|TRANS_DATE|FISCAL_DATE|TRANS_SYS"
//				+ System.getProperty("line.separator");
//	}

    /** 互生币数据文件列名信息 **/
//	public static String hsbColumn()
//	{
//		return "HSB_NO|CUST_ID|HS_RES_NO|ACC_TYPE|ADD_AMOUNT|SUB_AMOUNT|ADD_COUNT|BATCH_NO|TRANS_DATE|FISCAL_DATE|TRANS_SYS"
//				+ System.getProperty("line.separator");
//	}

    /** 税收数据文件列名信息 **/
//	public static String taxColumn()
//	{
//		return "TAX_NO|CUST_ID|HS_RES_NO|ACC_TYPE|TAX_AMOUNT|BATCH_NO|TRANS_DATE|FISCAL_DATE|TRANS_SYS"
//				+ System.getProperty("line.separator");
//	}

    /** 商业服务费数据文件列名信息 **/
//	public static String cscColumn()
//	{
//		return "CSC_NO|CUST_ID|HS_RES_NO|ACC_TYPE|CSC_AMOUNT|BATCH_NO|TRANS_DATE|FISCAL_DATE|TRANS_SYS"
//				+ System.getProperty("line.separator");
//	}

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
    public static void closePrintWriter(PrintWriter pw, FileOutputStream fos,
                                        InputStreamReader isr, BufferedReader br, FileInputStream fis) throws IOException {
        if (pw != null) {
            pw.close();
        }
        if (fos != null) {
            fos.close();
        }
        if (br != null) {
            br.close();
        }
        if (isr != null) {
            isr.close();
        }
        if (fis != null) {
            fis.close();
        }
    }

    /**
     * 新建文件夹
     *
     * @param path
     * @return
     */
    public static void createDir(String path) {
        String tmpPath = formatPath(path);
        if (!tmpPath.endsWith(File.separator)) {
            tmpPath = tmpPath + File.separator;
        }
        File dir = new File(tmpPath);
        if (!dir.exists()) {
            boolean b = dir.mkdirs();
            if (!b) {
                PsException.psExceptionNotThrow(new Throwable().getStackTrace()[0], RespCode.FAIL.getCode(), "创建文件失败！");
            }
        }
    }

    /**
     * 格式化路径
     *
     * @param input
     * @return
     */
    public static String formatPath(String input) {
        Pattern pattern = Pattern.compile("\\\\{2,}");
        Matcher matcher = pattern.matcher(input);
        String out = matcher.replaceAll("\\\\");
        return out;
    }

}
