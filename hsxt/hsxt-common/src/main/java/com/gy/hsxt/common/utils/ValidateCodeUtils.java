package com.gy.hsxt.common.utils;

import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
/**   
 * 生成验证码工具类
 * @category          生成验证码工具类
 * @className       RandomValidateCode
 * @description      生成验证码工具类
 * @author              zhucy
 * @createDate       2015-3-27 下午9:31:42  
 * @updateUser      zhucy
 * @updateDate      2015-3-27 下午9:31:42
 * @updateRemark    新增
 * @version              v0.0.1
 */
public class ValidateCodeUtils {
	
    private static Random random = new Random();
    private static String randString = "0123456789";//随机产生的字符串
    
    private static int width = 60;//图片宽
    private static int height = 28;//图片高
    /*
     * 获得字体
     */
    private static Font getFont(){
        return new Font("Fixedsys",Font.CENTER_BASELINE,18);
    }
    /*
     * 获得颜色
     */
    private static Color getRandColor(int fc,int bc){
        if(fc > 255)
            fc = 255;
        if(bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc-fc-16);
        int g = fc + random.nextInt(bc-fc-14);
        int b = fc + random.nextInt(bc-fc-18);
        return new Color(r,g,b);
    }
    /**
     * 生成验证码到 out
     * @param width 验证码宽度
     * @param height 验证码高度
     * @param num 验证码长度
     * @param out 输入流
     * @return
     */
    public static String getRandcode(int width,int height,int num,OutputStream out){
    	//BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();//产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman",Font.ROMAN_BASELINE,18));
        g.setColor(getRandColor(110, 133));
        //绘制干扰线
//        for(int i=0;i<=lineSize;i++){
//            drowLine(g);
//        }
        //绘制随机字符
        String randomString = "";
        for(int i=1;i<=num;i++){
            randomString = drowString(g,randomString,i);
        }
        g.dispose();
        try {
            ImageIO.write(image, "JPEG", out);//将内存中的图片通过流动形式输出到客户端
            return randomString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 生成随机图片(默认width=68，height=28)
     */
    public static String getDefaultRandcode(int num,OutputStream out) {
        return getRandcode(width,height,num,out);
    }
    /*
     * 绘制字符串
     */
    private static String drowString(Graphics g,String randomString,int i){
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101),random.nextInt(111),random.nextInt(121)));
        String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
        randomString +=rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 9*i, 16);
        return randomString;
    }
    /*
     * 绘制干扰线
     */
    private static void drowLine(Graphics g){
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x+xl, y+yl);
    }
    /*
     * 获取随机的字符
     */
    private static String getRandomString(int num){
        return String.valueOf(randString.charAt(num));
    }
    public static void main(String[] args) {
    	try {
    		System.out.println(ValidateCodeUtils.getDefaultRandcode(4,new FileOutputStream(new File("f:a.jpg"))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

	