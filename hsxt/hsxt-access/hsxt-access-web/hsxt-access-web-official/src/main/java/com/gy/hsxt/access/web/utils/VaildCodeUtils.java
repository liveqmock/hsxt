/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.bean.VaildCodeParam;

/**
 * 验证码工具类
 * 
 * @Package: com.gy.hsxt.access.web.utils
 * @ClassName: VaildCodeUtils
 * @Description: TODO
 * @author: likui
 * @date: 2015年12月25日 下午3:46:22
 * @company: gyist
 * @version V3.0.0
 */
public class VaildCodeUtils implements Serializable {

	private static final long serialVersionUID = -7187871454520721336L;

	private static int width = 76;// 定义图片的width
	private static int height = 41;// 定义图片的height
	private static int codeX = 12;// X坐标
	private static int codeY = 30;// Y坐标
	private static int fontSize = 35;// 字体大小
	// 字母数字
	private static char[] characterNum =
	{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	// 字母
	private static char[] character =
	{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	// 数字
	private static char[] num =
	{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * 生成验证码
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月25日 下午2:22:08
	 * @param codeCount
	 * @param type
	 * @return
	 * @return : Object[]
	 * @version V3.0.0
	 */
	public static Object[] createValidCodeImage(VaildCodeParam param)
	{
		// 设置参数
		setVaildCodeParam(param);
		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuffer randomCode = new StringBuffer();
		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// Graphics2D gd = buffImg.createGraphics();
		// Graphics2D gd = (Graphics2D) buffImg.getGraphics();
		Graphics gd = buffImg.getGraphics();
		// 创建一个随机数生成器类
		Random random = new Random();
		// 将图像填充为白色
		gd.setColor(Color.WHITE);
		gd.fillRect(0, 0, width, height);

		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Consolas", Font.PLAIN, fontSize);
		// 设置字体。
		gd.setFont(font);

		// 画边框。
		gd.setColor(Color.BLACK);
		gd.drawRect(0, 0, width - 1, height - 1);

		// 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
		// gd.setColor(Color.BLACK);
		// for (int i = 0; i < 40; i++)
		// {
		// int x = random.nextInt(width);
		// int y = random.nextInt(height);
		// int xl = random.nextInt(12);
		// int yl = random.nextInt(12);
		// gd.drawLine(x, y, x + xl, y + yl);
		// }

		int red = 0, green = 0, blue = 0;

		// 随机产生codeCount数字的验证码。
		int codeCount = param.getCodeCount();
		for (int i = 0; i < codeCount; i++)
		{
			// 得到随机产生的验证码数字。
			String code = String.valueOf(getCodeType(param.getCodeType(), random));
			// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);

			// 用随机产生的颜色将验证码绘制到图像中。
			gd.setColor(new Color(red, green, blue));
			gd.drawString(code, (i + 1) * codeX, codeY);

			// 将产生的四个随机数组合在一起。
			randomCode.append(code);
		}
		return new Object[]
		{ randomCode.toString(), buffImg };
	}

	/**
	 * 输出到页面
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月25日 下午3:12:29
	 * @param request
	 * @param response
	 * @param codeCount
	 * @param type
	 * @return : void
	 * @version V3.0.0
	 */
	public static String outputToPage(HttpServletResponse response, VaildCodeParam param)
	{
		// 设置参数
		setVaildCodeParam(param);
		// 获取验证码
		Object[] obj = VaildCodeUtils.createValidCodeImage(param);
		// 禁止图像缓存
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		try
		{
			// 将图像输出到Servlet输出流中。
			ServletOutputStream sos = response.getOutputStream();
			ImageIO.write((BufferedImage) obj[1], "jpeg", sos);
			sos.close();
		} catch (Exception ex)
		{
			SystemLog.error(VaildCodeUtils.class.getName(), "outputToPage", "生成验证码异常", ex);
		}
		return obj[0].toString();
	}

	/**
	 * 验证码类型
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月25日 下午2:35:56
	 * @param type
	 * @param random
	 * @return
	 * @return : char
	 * @version V3.0.0
	 */
	private static char getCodeType(int type, Random random)
	{
		switch (type)
		{
		case 1:
			return num[random.nextInt(10)];
		case 2:
			return character[random.nextInt(52)];
		case 3:
			return characterNum[random.nextInt(62)];
		default:
			return '0';
		}
	}

	/**
	 * 设置验证码参数
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月30日 下午8:40:31
	 * @param param
	 * @return : void
	 * @version V3.0.0
	 */
	private static void setVaildCodeParam(VaildCodeParam param)
	{
		width = param.getWidth() > 0 ? param.getWidth() : width;
		height = param.getHeight() > 0 ? param.getHeight() : height;
		codeX = param.getCodeX() > 0 ? param.getCodeX() : codeX;
		codeY = param.getCodeY() > 0 ? param.getCodeY() : codeY;
		fontSize = param.getFontSize() > 0 ? param.getFontSize() : fontSize;
	}

	public static void main(String[] args)
	{
		try
		{
			Object[] obj = VaildCodeUtils.createValidCodeImage(new VaildCodeParam(4, 3));
			System.out.println(obj[0]);
			ImageIO.write((BufferedImage) obj[1], "JPEG", new FileOutputStream(new File("f:a.jpg")));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
