/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.common;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsxt.access.web.aps.controllers.workflow.ScheduleExportPersonalized;
import com.gy.hsxt.access.web.aps.services.workflow.ApsScheduleExprotDataInit;
import com.gy.hsxt.access.web.bean.workflow.ApsMembersScheduleBean;

/***
 * Excel导出
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.common
 * @ClassName: ExcelExport
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-1-6 上午10:49:19
 * @version V1.0
 */
public final class ExcelExport implements Serializable {
    private static final long serialVersionUID = -8453573321411538194L;

    /**
     * 设置默认样式
     * 
     * @param style
     */
    public static void defaultCellStyle(HSSFCellStyle style) {
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中格式
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直
    }

    /**
     * 生成工作表
     * 
     * @param sheetName
     *            工作表明
     * @param cellNum
     *            总列数(行列数)
     * @param cellTitle
     *            列标题
     * @param content
     *            行数据
     * @return
     */
    public static HSSFWorkbook createExcel(String sheetName, int cellNum, String[] cellTitle, Object[][] content) {

        // 1、创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();

        // 2、在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 3、在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);

        // 4、创建单元格，并设置样式
        HSSFCellStyle style = wb.createCellStyle();
        defaultCellStyle(style);

        HSSFCell cell = null;
        if (cellTitle != null)
        {
            // 循环列
            for (int i = 0; i < cellTitle.length; i++)
            {
                cell = row.createCell((int) i);
                cell.setCellValue(StringUtils.isEmpty(cellTitle[i]) ? "" : cellTitle[i]);
                cell.setCellStyle(style);
            }
        }

        // 循环行数据
        for (int i = 0; i < content.length; i++)
        {
            row = sheet.createRow((int) (cell != null ? (i + 1) : i));

            // 第四步，创建单元格，并设置值
            for (int j = 0; j < cellNum; j++)
            {
                HSSFCell rcell = row.createCell((int) j);
                rcell.setCellValue(StringUtils.isEmpty(content[i][j]) ? "" : content[i][j] + "");
                rcell.setCellStyle(style);
            }
        }
        return wb;
    }

    public static void main(String[] args) {
        ApsMembersScheduleBean amsb = new ApsMembersScheduleBean();
        amsb.setYear("2016");
        amsb.setMonth("1");
        String xlsString = "{\"operators\":[{\"chief\":true,\"groupId\":\"120120151113080434000000\",\"operatorName\":\"张妹妹3\",\"optCustId\":\"00000000003\",\"optWorkNo\":\"03013006255\"},{\"chief\":true,\"groupId\":\"120120151113080434000000\",\"operatorName\":\"张两妹\",\"optCustId\":\"00000000004\",\"optWorkNo\":\"08255003721\"},{\"chief\":false,\"groupId\":\"120120151113080434000000\",\"operatorName\":\"张妹妹5\",\"optCustId\":\"00000000005\",\"optWorkNo\":\"06186010006\"},{\"chief\":false,\"groupId\":\"120120151113080434000000\",\"operatorName\":\"张大饼\",\"optCustId\":\"00000000006\",\"optWorkNo\":\"02705005681\"},{\"chief\":false,\"groupId\":\"120120151113080434000000\",\"operatorName\":\"张妹妹\",\"optCustId\":\"00000000007\",\"optWorkNo\":\"01816041647\"}],\"scheduleId\":\"120120160106105106000000\",\"scheduleOpts\":[{\"optCustId\":\"00000000003\",\"planDate\":\"01\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000003\",\"planDate\":\"02\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000003\",\"planDate\":\"03\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000003\",\"planDate\":\"04\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000003\",\"planDate\":\"05\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000003\",\"planDate\":\"06\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000003\",\"planDate\":\"07\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000003\",\"planDate\":\"08\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000003\",\"planDate\":\"09\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"10\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"11\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"12\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"13\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"14\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"15\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"16\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"17\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"18\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"19\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"20\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"21\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"22\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"23\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"24\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"25\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"26\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"27\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"28\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"29\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000003\",\"planDate\":\"30\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000003\",\"planDate\":\"31\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000004\",\"planDate\":\"01\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000004\",\"planDate\":\"02\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000004\",\"planDate\":\"03\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000004\",\"planDate\":\"04\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000004\",\"planDate\":\"05\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000004\",\"planDate\":\"06\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000004\",\"planDate\":\"07\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000004\",\"planDate\":\"08\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000004\",\"planDate\":\"30\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000004\",\"planDate\":\"31\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000005\",\"planDate\":\"01\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000005\",\"planDate\":\"02\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000005\",\"planDate\":\"03\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000005\",\"planDate\":\"04\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000005\",\"planDate\":\"05\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000005\",\"planDate\":\"06\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000005\",\"planDate\":\"07\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000005\",\"planDate\":\"08\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000005\",\"planDate\":\"13\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000005\",\"planDate\":\"14\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000005\",\"planDate\":\"15\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000005\",\"planDate\":\"16\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000005\",\"planDate\":\"17\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000005\",\"planDate\":\"18\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000005\",\"planDate\":\"19\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000005\",\"planDate\":\"20\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000005\",\"planDate\":\"21\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000005\",\"planDate\":\"22\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000005\",\"planDate\":\"23\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000005\",\"planDate\":\"24\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000005\",\"planDate\":\"25\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000005\",\"planDate\":\"26\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000005\",\"planDate\":\"27\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000005\",\"planDate\":\"28\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000005\",\"planDate\":\"29\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000005\",\"planDate\":\"30\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000005\",\"planDate\":\"31\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000006\",\"planDate\":\"01\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000006\",\"planDate\":\"02\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000006\",\"planDate\":\"03\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000006\",\"planDate\":\"13\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000006\",\"planDate\":\"14\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000006\",\"planDate\":\"15\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000006\",\"planDate\":\"16\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000006\",\"planDate\":\"17\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000006\",\"planDate\":\"18\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000006\",\"planDate\":\"19\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000006\",\"planDate\":\"20\",\"scheduleId\":\"120120160106105106000000\",\"workType\":1},{\"optCustId\":\"00000000006\",\"planDate\":\"21\",\"scheduleId\":\"120120160106105106000000\",\"workType\":1},{\"optCustId\":\"00000000006\",\"planDate\":\"22\",\"scheduleId\":\"120120160106105106000000\",\"workType\":1},{\"optCustId\":\"00000000006\",\"planDate\":\"23\",\"scheduleId\":\"120120160106105106000000\",\"workType\":1},{\"optCustId\":\"00000000006\",\"planDate\":\"24\",\"scheduleId\":\"120120160106105106000000\",\"workType\":1},{\"optCustId\":\"00000000006\",\"planDate\":\"25\",\"scheduleId\":\"120120160106105106000000\",\"workType\":1},{\"optCustId\":\"00000000006\",\"planDate\":\"26\",\"scheduleId\":\"120120160106105106000000\",\"workType\":1},{\"optCustId\":\"00000000006\",\"planDate\":\"27\",\"scheduleId\":\"120120160106105106000000\",\"workType\":1},{\"optCustId\":\"00000000006\",\"planDate\":\"28\",\"scheduleId\":\"120120160106105106000000\",\"workType\":1},{\"optCustId\":\"00000000006\",\"planDate\":\"29\",\"scheduleId\":\"120120160106105106000000\",\"workType\":1},{\"optCustId\":\"00000000006\",\"planDate\":\"30\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000006\",\"planDate\":\"31\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"01\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000007\",\"planDate\":\"02\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000007\",\"planDate\":\"03\",\"scheduleId\":\"120120160106105106000000\",\"workType\":3},{\"optCustId\":\"00000000007\",\"planDate\":\"04\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000007\",\"planDate\":\"05\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000007\",\"planDate\":\"06\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000007\",\"planDate\":\"07\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000007\",\"planDate\":\"08\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000007\",\"planDate\":\"09\",\"scheduleId\":\"120120160106105106000000\",\"workType\":2},{\"optCustId\":\"00000000007\",\"planDate\":\"13\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"14\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"15\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"16\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"17\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"18\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"19\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"20\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"21\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"22\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"23\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"24\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"25\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"26\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"27\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"28\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"29\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"30\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4},{\"optCustId\":\"00000000007\",\"planDate\":\"31\",\"scheduleId\":\"120120160106105106000000\",\"workType\":4}]}";
        Map<String, Object> oMap = (Map<String, Object>) JSON.parse(xlsString);
        ApsScheduleExprotDataInit asedi = new ApsScheduleExprotDataInit(amsb, oMap);

        HSSFWorkbook wb = ExcelExport.createExcel("测试", asedi.columnNum, asedi.cellTitle, asedi.content);

        new ScheduleExportPersonalized(asedi, wb);

        try
        {
            FileOutputStream fout = new FileOutputStream("D:/students.xls");
            wb.write(fout);
            fout.close();
        }
        catch (Exception e)
        {
            System.out.println("异常信息：" + e.getMessage());
        }

        System.out.println("==============" + UUID.randomUUID().toString());
    }
}
