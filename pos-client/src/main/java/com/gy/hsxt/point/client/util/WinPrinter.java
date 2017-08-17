package com.gy.hsxt.point.client.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.JOptionPane;

/**
 * 
 * 打印功能组件
 * 
 * @Package: com.gy.point.client.util
 * @ClassName: WinPrinter
 * 
 * @author: liyh
 * @date: 2015-12-19 下午4:03:19
 * @version V3.0
 */
public class WinPrinter {

	public static void print(String printContent, String tempFilePath) {
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		PrintService[] pservices = PrintServiceLookup.lookupPrintServices(
				flavor, aset);
		PrintService defaultService = PrintServiceLookup
				.lookupDefaultPrintService();
		PrintService service = ServiceUI.printDialog(null, 200, 200, pservices,
				defaultService, flavor, aset);
		if (service != null) {
			try {
				DocPrintJob pj = service.createPrintJob();
				aset.add(MediaSizeName.ISO_A4);
				// 打印字符串内容转化，生成临时打印文本文件，用于打印
				boolean flag = string2File(printContent, new File(tempFilePath));
				FileInputStream fis = null;
				if (flag) {
					// 根据生成在临时打印文件，构造文件流
					fis = new FileInputStream(tempFilePath);
					// Reader
					// 构造待打印的文件流
					DocAttributeSet das = new HashDocAttributeSet();
					Doc doc = new SimpleDoc(fis, flavor, das);
					pj.print(doc, aset);
					// Thread.sleep(10 * 1000);
					System.out.println("打印成功");
					JOptionPane.showMessageDialog(null, "打印完成", "提示:",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("打印失败");
		}
	}

	/**
	 * 将字符串存储为一个文件，当文件不存在时候，自动创建该文件， 当文件已存在时候，重写文件的内容，特定情况下，还与操作系统的权限有关。
	 * 
	 * @param text
	 *            字符串
	 * @param distFile
	 *            存储的目标文件
	 * @return 当存储正确无误时返回true，否则返回false
	 * @throws IOException
	 */
	public static boolean string2File(String text, File distFile)
			throws IOException {
		// 判断上级目录是否存在
		if (!distFile.getParentFile().exists()) {
			// 创建上级目录
			distFile.getParentFile().mkdirs();
		}
		BufferedReader br = null;
		BufferedWriter bw = null;
		boolean flag = true;
		try {
			br = new BufferedReader(new StringReader(text));
			bw = new BufferedWriter(new FileWriter(distFile));
			char buf[] = new char[1024 * 64]; // 字符缓冲区
			int len;
			while ((len = br.read(buf)) != -1) {
				bw.write(buf, 0, len);
			}
			bw.flush();
			br.close();
			bw.close();
		} catch (IOException e) {
			flag = false;
			System.out.println("将字符串写入文件发生异常！" + e);
		} finally {
			// 释放资源
			if (bw != null) {
				bw.close();
			}
			if (br != null) {
				br.close();
			}
		}
		return flag;
	}

	/**
	 * * @param args
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		print("ddddddds潮桭", "C:\\print.txt");
	}
}