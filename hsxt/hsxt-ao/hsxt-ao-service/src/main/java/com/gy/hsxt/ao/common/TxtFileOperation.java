/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.gy.hsxt.ao.AOErrorCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * TXT文档读写操作
 * 
 * @Package: com.hsxt.bs.common
 * @ClassName: txtFileOperation
 * @Description:
 * 
 * @author: liuhq
 * @date: 2015-9-23 上午9:09:03
 * @version V1.0
 */
public class TxtFileOperation {
    private TxtFileOperation() {
    }

    /**
     * 获取目录下的所有文件名
     * 
     * @param f
     *            目录路径
     * @return 文件数组
     */
    public static File[] getFiles(File f) {
        // 判断传入对象是否为一个文件夹对象
        if (!f.isDirectory())
        {
            throw new HsException(AOErrorCode.AO_NOT_DIR_ERROR, "你输入的不是一个文件夹，请检查路径是否有误：" + f.getAbsolutePath());
        }
        else
        {
            File[] t = f.listFiles();
            for (int i = 0; i < t.length; i++)
            {
                // 判断文件列表中的对象是否为文件夹对象，如果是则执行tree递归，直到把此文件夹中所有文件输出为止
                if (t[i].isDirectory())
                {
                    getFiles(t[i]);
                }
            }
            return t;
        }
    }

    /**
     * 校验文件内容是否完整
     * 
     * @param path
     *            目录路径
     * @return 记录开始位置
     */
    public static int checkData(String path) {
        // 记帐规则列表
        List<String> acRuleList = null;// 创建空对象
        // 获取记帐文件数组
        try
        {
            // 获取目录下所有文件名称
            File[] arrayFiles = TxtFileOperation.getFiles(new File(path));
            String fileName = "";
            if (arrayFiles.length > 0)
            {
                for (int i = 0; i < arrayFiles.length; i++)
                {
                    fileName = path + arrayFiles[i];
                    // 读取文件内容到list
                    acRuleList = TxtFileOperation.readTxtFile(fileName);

                    // 非null执行遍历
                    if (acRuleList != null && acRuleList.size() > 0)
                    {
                        // 校验文件是否完整
                        if (!acRuleList.get(acRuleList.size()).equals("END"))
                        {
                            // 截取文件名最后的数据开始位置
                            String[] arrName = fileName.split("_");
                            // 获取文件名称最后一段为记录开始位
                            int dataStart = Integer.valueOf(arrName[arrName.length - 1]);
                            // 文件不完整返回记录开始位置
                            return dataStart;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new HsException(AOErrorCode.AO_NOT_DIR_ERROR, "你输入的不是一个文件夹，请检查路径是否有误");
        }
        // 文件完整返回1
        return 1;
    }

    /**
     * 根据List数据源生成一个TXT文档
     * 
     * @param List
     *            <T> 数据列表 泛型支持javaBean
     * @param path
     *            文档夹路径
     * @param fileName
     *            文档名称
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @SuppressWarnings("rawtypes")
    public static <T> void WriteTxtFileT(List<T> List, String path, String fileName) throws IOException,
            IllegalArgumentException, IllegalAccessException {
        OutputStreamWriter writer = null;

        BufferedWriter bw = null;

        OutputStream os = null;

        // 创建文件对象
        File ph = new File(path);

        // 如果文件夹不存在则创建
        if (!ph.isDirectory())
        {
            ph.mkdirs();
        }

        // 创建File对象
        File file = new File(ph + "\\" + fileName);

        // 创建文件字节输出流对象
        os = new FileOutputStream(file);

        // 创建文件字节写入流对象
        writer = new OutputStreamWriter(os);

        // 缓冲FileWriter
        bw = new BufferedWriter(writer);

        int count = List.size();// 列表总数

        if (count > 0)
        {
            Class classType = List.get(0).getClass();
            for (int i = 0; i < count; i++)
            {

                // 获取实体中属性组
                Field[] fields = classType.getDeclaredFields();
                String val = "";
                for (Field field : fields)
                {
                    // 判断该对象是否可以访问
                    if (!field.isAccessible())
                    {
                        field.setAccessible(true);// 设置为可访问
                    }
                    String name = field.getName();// 获取属于名称
                    if (!"serialVersionUID".equals(name))
                    {
                        // 过滤javaBean中的serialVersionUID
                        Object obj = field.get(List.get(i));
                        if (obj != null)
                            val += obj + "|";
                    }
                }

                // 写入内容,每次写入一行,去掉最后一个“|”
                bw.write(val.substring(0, val.length() - 1));
                bw.newLine();
            }
            bw.flush();
            os.flush();
        }

        // 释放资源
        if (bw != null)
        {
            bw.close();
        }

        if (os != null)
        {
            os.close();
        }

        if (writer != null)
        {
            writer.close();
        }
    }

    /**
     * 根据List数据源生成一个TXT文档
     * 
     * @param List
     *            <String> 数据列表 一组一行数据（推荐）
     * @param path
     *            文档夹路径
     * @param fileName
     *            文档名称
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public synchronized static void WriteTxtFile(List<String> List, String path, String fileName) throws IOException {
        OutputStreamWriter writer = null;

        BufferedWriter bw = null;

        OutputStream os = null;

        try
        {
            File ph = new File(path);

            // 如果文件夹不存在则创建
            if (!ph.isDirectory())
            {
                ph.mkdirs();
            }

            // 创建File对象
            File file = new File(ph + "\\" + fileName);

            // 创建文件字节输出流对象
            os = new FileOutputStream(file);

            // 创建文件字节写入流对象
            writer = new OutputStreamWriter(os);

            // 缓冲FileWriter
            bw = new BufferedWriter(writer);

            int count = List.size();// 列表总数

            for (int i = 0; i < count; i++)
            {
                String val = List.get(i);

                // 写入内容,每次写入一行
                bw.write(val);
                bw.newLine();
            }

            bw.flush();

            os.flush();
        }
        catch (Exception e)
        {
            throw new HsException(AOErrorCode.AO_WRITE_FILE_ERROR.getCode(), "写入文件失败");
        }
        finally
        {
            // 释放资源
            if (bw != null)
            {
                bw.close();
            }

            if (os != null)
            {
                os.close();
            }

            if (writer != null)
            {
                writer.close();
            }
        }
    }

    /**
     * 把一个StringBuilder生成一个TXT文档
     * 
     * @param sbString
     *            字符串 一组一行数据
     * @param path
     *            文档夹路径
     * @param fileName
     *            文档名称
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static void WriteTxtFile(StringBuilder sbString, String path, String fileName) throws IOException {
        OutputStreamWriter writer = null;

        BufferedWriter bw = null;

        OutputStream os = null;

        try
        {
            // 创建File对象
            File ph = new File(path);

            // 如果文件夹不存在则创建
            if (!ph.isDirectory())
            {
                ph.mkdirs();
            }

            // 创建文件对象
            File file = new File(ph + "\\" + fileName);

            // 创建文件字节输出流对象
            os = new FileOutputStream(file);
            // 创建文件字节写入流对象
            writer = new OutputStreamWriter(os);

            // 缓冲FileWriter
            bw = new BufferedWriter(writer);

            // 写入内容,每次写入一行
            bw.write(sbString.toString());

            bw.flush();

            os.flush();
        }
        catch (Exception e)
        {
            throw new HsException(AOErrorCode.AO_WRITE_FILE_ERROR.getCode(), "写入文件失败");
        }
        finally
        {
            // 释放资源
            if (bw != null)
            {
                bw.close();
            }

            if (os != null)
            {
                os.close();
            }

            if (writer != null)
            {
                writer.close();
            }
        }
    }

    /**
     * 读取txt文档 返回一个List
     * 
     * @param filePath
     * @return 返回一个List
     * @throws IOException
     */
    public static List<String> readTxtFile(String filePath) throws IOException {
        BufferedReader br = null;

        // 创建List对象
        List<String> list = null;
        try
        {
            // 读取文件到缓冲区
            br = new BufferedReader(new FileReader(filePath));

            String s = null;

            list = new ArrayList<String>();

            // 每次读取一行
            while ((s = (br.readLine())) != null)
            {
                list.add(s);
            }
        }
        catch (Exception e)
        {
            throw new HsException(AOErrorCode.AO_READ_FILE_ERROR.getCode(), "读取文件失败");
        }
        finally
        {
            // 关闭流
            if (br != null)
            {
                br.close();
            }
        }

        return list;// 返回列表
    }

    /**
     * 按行追加写入文件夹
     * 
     * @param file
     * @param conent
     */
    public static void writeAndAppendFile(String filePath, String fileName, String conent) {
        BufferedWriter out = null;
        // 创建目录
        createFileDir(filePath);
        try
        {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath + "\\" + fileName, true)));
            out.write(conent + "\r\n");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建根目录
     * 
     * @param destDirName
     * @return
     */
    public static boolean createFileDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists())
        {
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator))
        {
            destDirName = destDirName + File.separator;
        }
        // 创建目录
        if (dir.mkdirs())
        {
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        }
        else
        {
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }

    /**
     * 测试调用例子
     * 
     * @param args
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws HsException
     */
    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, IOException,
            HsException {
        File f = new File("C:/ACC");
        File[] arrFile = getFiles(f);
        System.out.println("等记帐文件总数：" + arrFile.length);

        // txtFileOperation t = new txtFileOperation();

        /**
         * 结束 s1443150365785 总耗时为：3354毫秒 执行第二次的时间
         */
        // long startMili = System.currentTimeMillis();// 当前时间对应的毫秒数
        // List<AnnualFeePrice> list = new ArrayList<AnnualFeePrice>();
        // for (int i = 0; i < 100000; i++) {
        // AnnualFeePrice m = new AnnualFeePrice();
        // m.setPrice(new BigDecimal("21." + i));
        // m.setApprRemark("备注" + i);
        // m.setApprTime(new Date());
        // m.setApprOperator("申请人" + i);
        // list.add(m);
        // }
        // t.WriteTxtFileT(list, "C:\\txtTest", "test.txt");
        // long endMili = System.currentTimeMillis();
        // System.out.println("结束 s" + endMili);
        // System.out.println("总耗时为：" + (endMili - startMili) + "毫秒");

        /**
         * 结束 s1443150445017 总耗时为：3068毫秒 执行第二次的时间
         */
        // long startMili = System.currentTimeMillis();// 当前时间对应的毫秒数
        // List<String> listS = new ArrayList<>();
        // for (int i = 0; i < 100000; i++) {
        // String s = GuidAgent.getStringGuid("bs") + "|"
        // + new BigDecimal("21." + i) + "|备注" + i + "|" + new Date()
        // + "|申请人" + i;
        // listS.add(s);
        // }
        // t.WriteTxtFile(listS, "C:\\txtTest", "test.txt");
        // long endMili = System.currentTimeMillis();
        // System.out.println("结束 s" + endMili);
        // System.out.println("总耗时为：" + (endMili - startMili) + "毫秒");

        /**
         * 结束 s1443150965555 总耗时为：3103毫秒 执行第二次的时间
         */
        // long startMili = System.currentTimeMillis();// 当前时间对应的毫秒数
        // StringBuilder sbString = new StringBuilder();
        // for (int i = 0; i < 100000; i++) {
        // String s = GuidAgent.getStringGuid("bs") + "|"
        // + new BigDecimal("21." + i) + "|备注" + i + "|" + new Date()
        // + "|申请人" + i + "\r\n";
        // sbString.append(s);
        // }
        // t.WriteTxtFile(sbString, "C:\\txtTest", "test.txt");
        // long endMili = System.currentTimeMillis();
        // System.out.println("结束 s" + endMili);
        // System.out.println("总耗时为：" + (endMili - startMili) + "毫秒");

        /**
		 *
		 */
        // List<String> lists = t.readTxtFile("C:\\txtTest\\test.txt");
        // int count = lists.size();
        // for (int i = 0; i < count; i++)
        // {
        // System.out.println("" + lists.get(i).toString());
        //
        // }

    }
}
