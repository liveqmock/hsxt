package com.gy.hsi.nt.server.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.nt.server.entity.ExcelResources;
import com.gy.hsi.nt.server.entity.MsgTemp;
/**
 * 
 * @className:ExcelRead
 * @author:likui
 * @date:2015年7月27日
 * @desc:读取excel文件
 * @company:gyist
 */
public class ExcelRead {
	
	private static final Logger logger = Logger.getLogger(ExcelRead.class);
	
	private ExcelRead(){
		super();
	}
	
	/**
	 * 
	* @author:likui
	* @created:2015年7月23日下午4:33:06
	* @desc:读取excel文件
	* @param:@param path
	* @param:@param clazz
	* @param:@param readCount
	* @param:@return
	* @param:List<Object>
	* @throws
	 */
	@SuppressWarnings("rawtypes")
	public static List<Object> readExcel(String path,Class clazz,Integer readCount){
		logger.info("读取Excel文件,path:"+path+",Class:"+clazz);
		Workbook workbook=null;
		List<Object> values=null;
		try {
			if(StringUtils.isNotBlank(path)){
				workbook = createWorkbook(new FileInputStream(path));
				values=readExcel(workbook,clazz,readCount);
			}
		} catch (FileNotFoundException e) {
			logger.error("读取文件异常", e);
		} catch (IOException e) {
			logger.error("IO流异常", e);
		}catch (InvalidFormatException e) {
			logger.error("创建不同版本的Workbook异常", e);
		}
		return values;
	}
	
	
	/**
	 * 
	* @author:likui
	* @created:2015年7月27日下午4:32:30
	* @desc:读取excel文件
	* @param:@param in
	* @param:@param clazz
	* @param:@param readCount
	* @param:@return
	* @param:List<Object>
	* @throws
	 */
	@SuppressWarnings("rawtypes")
	public static List<Object> readExcel(InputStream in,Class clazz,Integer readCount){
		logger.info("读取Excel文件Class:"+clazz);
		Workbook workbook=null;
		List<Object> values=null;
		try {
			if(null != in){
				workbook = createWorkbook(in);
				values=readExcel(workbook,clazz,readCount);
			}
		} catch (FileNotFoundException e) {
			logger.error("读取文件异常", e);
		} catch (IOException e) {
			logger.error("IO流异常", e);
		}catch (InvalidFormatException e) {
			logger.error("创建不同版本的Workbook异常", e);
		}
		return values;
	}
	
	/**
	 * 
	* @author:likui
	* @created:2015年7月23日下午4:33:41
	* @desc:读取excel文件
	* @param:@param workbook
	* @param:@param clazz
	* @param:@param readCount
	* @param:@return
	* @param:List<Object>
	* @throws
	 */
	@SuppressWarnings("rawtypes")
	private static List<Object> readExcel(Workbook workbook,Class clazz,Integer readCount) {
		Sheet sheet = null;
		String[] heads = null;
		List<Object> values=null;
		try {
			if(null!=workbook){
				values=new ArrayList<Object>();
				for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
					sheet = workbook.getSheetAt(sheetIndex);// 获得sheet
					if (heads == null) {
						heads=getExcelHead(sheet.getRow(readCount-1),clazz);
					}
					int firstRowNum = sheet.getFirstRowNum(); // 第一行
					int lastRowNum = sheet.getLastRowNum(); // 最后一行
					for (int rowNum = firstRowNum + readCount; rowNum <= lastRowNum; rowNum++) {
						Object obj =createObjcet(heads,clazz,sheet.getRow(rowNum));
						if(null!=obj){
							values.add(obj);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("读取文件异常", e);
		}
		return values;
	}
	
	/**
	 * 
	* @author:likui
	* @created:2015年7月23日下午4:24:06
	* @desc:根据标题名称获取属性名称
	* @param:@param row
	* @param:@param clazz
	* @param:@return
	* @param:String[]
	* @throws
	 */
	@SuppressWarnings("rawtypes")
	private static String[] getExcelHead(Row row,Class clazz){
		String[] heads = new String[row.getLastCellNum()];
		int firstCellNum = row.getFirstCellNum(); 
		int lastCellNum = row.getLastCellNum(); 
		Field[] fields=clazz.getDeclaredFields();
		for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
			String title=row.getCell(cellNum).getStringCellValue().replaceAll("\r|\n", "");;
			String head=null;
			for(Field field:fields){
				if (field.isAnnotationPresent(ExcelResources.class)) {
					ExcelResources er = field.getAnnotation(ExcelResources.class);
					if(title.indexOf(er.title())!=-1){
						head=field.getName();
						break;
					}
				}
			}
			heads[cellNum] = head;
		}
		return heads;
	}
	
	/**
	 * 
	* @author:likui
	* @created:2015年7月23日下午4:23:35
	* @desc:set值入对象
	* @param:@param head
	* @param:@param clazz
	* @param:@param row
	* @param:@return
	* @param:Object
	* @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object createObjcet(String[] head,Class clazz,Row row){
		Object obj=null;
		try{
			if(rowValueIsAllNull(head,row)){
				return obj;
			}
			obj=(Object)clazz.newInstance();
			Field fields[] = clazz.getDeclaredFields();
			for(int i=0;i<head.length;i++){
				String value=row.getCell(i).getStringCellValue();
				for(Field field:fields){
					String fieldName = field.getName();
					if(fieldName.equals(head[i])){
					    String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
						Class<?> typeClass = field.getType();
						Method method = clazz.getDeclaredMethod(setMethodName, new Class[]{typeClass});
				        method.invoke(obj, new Object[]{getClassTypeValue(typeClass,value)});
				        break;
					}
				}
			}
		}catch(Exception e){
			logger.error("set值入对象异常", e);
		}
		return obj;
	}
	
	/**
	 * 
	* @author:likui
	* @created:2015年7月23日下午4:53:34
	* @desc:判断一行的数据是否全部为空
	* @param:@param head
	* @param:@param row
	* @param:@return
	* @param:boolean
	* @throws
	 */
	private static boolean rowValueIsAllNull(String[] head,Row row){
		for(int i=0;i<head.length;i++){
			if(null!=row && row.getCell(i)!=null){
				String value=row.getCell(i).getStringCellValue();
				if(StringUtils.isNotBlank(value)){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	* @author:likui
	* @created:2015年7月23日下午4:21:48
	* @desc:根据类型转换值
	* @param:@param typeClass
	* @param:@param value
	* @param:@return
	* @param:Object
	* @throws
	 */
	private static Object getClassTypeValue(Class<?> typeClass, String value){
        if(typeClass == int.class){
            if(value.equals("")){
                return 0;
            }
            return Integer.parseInt(value);
        }else if(typeClass == short.class){
            if(value.equals("")){
                return 0;
            }
            return Short.parseShort(value);
        }else if(typeClass == byte.class){
            if(value.equals("")){
                return 0;
            }
            return Byte.parseByte(value);
        }else if(typeClass == double.class){
            if(value.equals("")){
                return 0;
            }
            return Double.parseDouble(value);
        }else if(typeClass == boolean.class){
            if(value.equals("")){
                return false;
            }
            return Boolean.parseBoolean(value);
        }else if(typeClass == float.class){
            if(value.equals("")){
                return 0;
            }
            return Float.parseFloat(value);
        }else if(typeClass == long.class){
            if(value.equals("")){
                return 0;
            }
            return Long.parseLong(value);
        }else if(typeClass==char.class){
        	if(value.equals("")){
                return 0;
            }
        	return value.charAt(0);
        } else {
            return typeClass.cast(value);
        }
    }

	/**
	 * 
	* @author:likui
	* @created:2015年7月23日下午4:20:38
	* @desc:判断excel的版本创建不同的Workbook对象
	* @param:@param in
	* @param:@return
	* @param:@throws IOException
	* @param:@throws InvalidFormatException
	* @param:Workbook
	* @throws
	 */
	private static Workbook createWorkbook(InputStream in) throws IOException,InvalidFormatException {
	    if (!in.markSupported()) {
	        in = new PushbackInputStream(in, 8);
	    }
	    if (POIFSFileSystem.hasPOIFSHeader(in)) {
	        return new HSSFWorkbook(in);
	    }
	    if (POIXMLDocument.hasOOXMLHeader(in)) {
	        return new XSSFWorkbook(OPCPackage.open(in));
	    }
	    throw new IllegalArgumentException("你的excel版本目前poi解析不了");
	}
	
	public static void main(String[] args) {
		String excelPath="/config/NoteAndEmailTemp.xls";
		String json=JSONObject.toJSONString(readExcel(ExcelRead.class.getResource(excelPath).getPath(),MsgTemp.class,2));
		List<MsgTemp> list=JSONObject.parseArray(json,MsgTemp.class);
		System.out.println(json);
		System.out.println(list.get(0).toString());
	}
}
