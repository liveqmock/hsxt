package com.gy.hsxt.point.client.util;


import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.Toolkit;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class PrintPosNo implements Printable {

	private String printContent;

	private PrintPosNo(String printContent) {
		this.printContent = printContent;
	}

	/**
	 * @param Graphic指明打印的图形环境
	 * @param PageFormat指明打印页格式
	 *            （页面大小以点为计量单位，1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×842点）
	 * @param pageIndex指明页号
	 **/
	@SuppressWarnings("unused")
	public int print(Graphics gra, PageFormat pf, int pageIndex)
			throws PrinterException {
		Component c = null;
		// 转换成Graphics2D
		Graphics2D g2 = (Graphics2D) gra;
		// 设置打印颜色为黑色
		g2.setColor(Color.black);
		// 打印起点坐标
		double x = pf.getImageableX();
		double y = pf.getImageableY();
		switch (pageIndex) {
		case 0:
			// 设置打印字体（字体名称、样式和点大小）（字体名称可以是物理或者逻辑名称）
			// Java平台所定义的五种字体系列：Serif、SansSerif、Monospaced、Dialog 和 DialogInput
			Font font = new Font("新宋体", Font.PLAIN, 9);
			g2.setFont(font);// 设置字体
			float heigth = font.getSize2D();// 字体高度
			Image src = Toolkit
					.getDefaultToolkit()
					.getImage(
							"D:\\EclipseWorkSpace3.1\\Kfc-wuxi\\WebRoot\\image\\KFC.jpg");
			int img_Height = src.getHeight(c);
			int img_width = src.getWidth(c);
			g2.drawString(printContent, (float) x, (float) y + 1 * heigth
					+ img_Height);
			return PAGE_EXISTS;
		default:
			return NO_SUCH_PAGE;
		}

	}

	public static void printEntPosNo(String posNO) {

		// 通俗理解就是书、文档
		Book book = new Book();
		// 设置成竖打
		PageFormat pf = new PageFormat();
		pf.setOrientation(PageFormat.PORTRAIT);
		// 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
		Paper p = new Paper();
		p.setSize(590, 840);// 纸张大小
		p.setImageableArea(10, 10, 590, 840);// A4(595 X
												// 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72
		pf.setPaper(p);
		// 把 PageFormat 和 Printable 添加到书中，组成一个页面
		book.append(new PrintPosNo(posNO), pf);
		// 获取打印服务对象
		PrinterJob job = PrinterJob.getPrinterJob();
		// 设置打印类
		job.setPageable(book);
		try {
			// 可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印
			boolean a = job.printDialog();
			if (a) {
				job.print();
			}
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//printEntPosNo();
	}

}
