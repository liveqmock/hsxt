package com.gy.hsxt.gpf.um.utils;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.um.enums.UMRespCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * 验证码生成工具
 *
 * @Package : com.gy.hsxt.gpf.um.utils
 * @ClassName : ValidCodeUtils
 * @Description : 验证码生成工具
 * @Author : chenm
 * @Date : 2016/1/27 11:10
 * @Version V3.0.0.0
 */
public abstract class ValidCodeUtils {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(ValidCodeUtils.class);

    /**
     * 获取验证码对象
     *
     * @return {@link ValidatorCode}
     */
    public static ValidatorCode getCode() {
        // 验证码图片的宽度。
        int width = 80;
        // 验证码图片的高度。
        int height = 30;
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();

        // 创建一个随机数生成器类。
        Random random = new Random();

        // 设定图像背景色(因为是做背景，所以偏淡)
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("微软雅黑", Font.ITALIC, 28);
        // 设置字体。
        g.setFont(font);

        // 画边框。
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, width - 1, height - 1);
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到。
        /* g.setColor(getRandColor(160, 200));
         for (int i = 0; i < 155; i++) {
         int x = random.nextInt(width);
         int y = random.nextInt(height);
         int xl = random.nextInt(12);
         int yl = random.nextInt(12);
         g.drawLine(x, y, x + xl, y + yl);
         }*/

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuilder randomCode = new StringBuilder();

        // 设置默认生成4个验证码
        int length = 4;
        // 设置备选验证码:包括"a-z"和数字"0-9"
//        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String base = "0123456789";

        int size = base.length();

        // 随机产生4位数字的验证码。
        for (int i = 0; i < length; i++) {
            // 得到随机产生的验证码数字。
            int start = random.nextInt(size);
            String strRand = base.substring(start, start + 1);

            // 用随机产生的颜色将验证码绘制到图像中。
            // 生成随机颜色(因为是做前景，所以偏深)
            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
//            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.setColor(new Color(0, 0, 0));
            g.drawString(strRand, 15 * i + 6, 24);

            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }

        // 图象生效
        g.dispose();
        //图片缓存转二进制
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] image = null;
        try {
            boolean b = ImageIO.write(buffImg, "jpg", byteArrayOutputStream);
            if (b) {
                image = byteArrayOutputStream.toByteArray();
            }
        } catch (IOException e) {
            logger.error("========图片缓存转二进制异常========", e);
            throw new HsException(UMRespCode.UM_GENERATE_VALID_CODE_ERROR, "图片缓存转二进制异常");
        }
        return new ValidatorCode(image, String.valueOf(randomCode));
    }

    /**
     * 给定范围获得随机颜色
     *
     * @param fc 起始
     * @param bc 结束
     * @return 颜色
     */
    @SuppressWarnings("unused")
    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 验证码图片封装
     *
     * @Package : com.gy.hsxt.gpf.um.utils
     * @ClassName : ValidCodeUtils
     * @Description : 验证码图片封装
     * @Author : chenm
     * @Date : 2016/1/27 11:10
     * @Version V3.0.0.0
     */
    public static class ValidatorCode {
        /**
         * 图片二进制流
         */
        private byte[] image;
        /**
         * 验证码
         */
        private String code;

        public ValidatorCode(byte[] image, String code) {
            this.image = image;
            this.code = code;
        }

        public byte[] getImage() {
            return image;
        }

        public void setImage(byte[] image) {
            this.image = image;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}