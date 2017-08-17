package com.gy.hsxt.access.web.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import com.gy.hsi.lc.client.log4j.SystemLog;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/***************************************************************************
 * <PRE>
 * 
 *  Author          : weiyq
 * 
 *  Purpose         : Freemarker模板工具类
 * 
 *  Creation Date   : 2016-05-03
 * 
 * </PRE>
 ***************************************************************************/

@SuppressWarnings("deprecation")
public class FreemarkerUtils {

	/**
	 * 使用freemarker生成数据页面
	 * 
	 * @param templatePath
	 *            模板文件存放路径
	 * @param templateName
	 *            模板文件名称
	 * @param fileName
	 *            生成的文件名称
	 * @param dataModel
	 *            数据模型
	 */
	public static boolean analysisTemplate(String templatePath,
			String templateName, String fileName, Object dataModel) {
		Writer out = null;
		FileOutputStream fos = null;

		try {
			Configuration config = new Configuration();

			// 设置要解析的模板所在的目录, 并加载模板文件
			config.setDirectoryForTemplateLoading(new File(templatePath));
			// 设置包装器, 并将对象包装为数据模型
			config.setObjectWrapper(new DefaultObjectWrapper());

			// 获取模板, 并设置编码方式, 这个编码必须要与页面中的编码格式一致, 否则会出现乱码
			Template template = config.getTemplate(templateName, "UTF-8");

			// 合并数据模型与模板
			File f = new File(new File(fileName).getParent());

			//目标文件名称:" + fileName);
			//创建目标文件夹：" + f.getAbsolutePath());

			// 创建文件夹
			if (!f.exists()) {
				f.mkdirs();
			}

			//logger.debug("替换模板内容开始。。。。。。。");

			fos = new FileOutputStream(fileName);
			out = new OutputStreamWriter(fos, "UTF-8");

			template.process(dataModel, out);

			//logger.debug("替换模板内容。。。。。。。。。");

			out.flush();

			return true;
		} catch (TemplateNotFoundException e) {
			SystemLog.error(FreemarkerUtils.class.getName(),"analysisTemplate", "没有找到Freemarker模板文件", e);
		} catch (MalformedTemplateNameException e) {
			SystemLog.error(FreemarkerUtils.class.getName(),"analysisTemplate", "Freemarker模板文件不规范", e);
		} catch (ParseException e) {
			SystemLog.error(FreemarkerUtils.class.getName(),"analysisTemplate", "解析异常", e);
		} catch (FileNotFoundException e) {
			SystemLog.error(FreemarkerUtils.class.getName(),"analysisTemplate", "文件没有找到", e);
		} catch (UnsupportedEncodingException e) {
			SystemLog.error(FreemarkerUtils.class.getName(),"analysisTemplate", "编码不支持", e);
		} catch (IOException e) {
			SystemLog.error(FreemarkerUtils.class.getName(),"analysisTemplate", "IO异常", e);
		} catch (TemplateException e) {
			SystemLog.error(FreemarkerUtils.class.getName(),"analysisTemplate", "Freemarker模板异常", e);
		} catch (Exception e) {
			SystemLog.error(FreemarkerUtils.class.getName(),"analysisTemplate", "Freemarker模板异常", e);
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}

			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}

		//logger.debug("模板生成完毕。。。。");

		return false;
	}
	
	
}
