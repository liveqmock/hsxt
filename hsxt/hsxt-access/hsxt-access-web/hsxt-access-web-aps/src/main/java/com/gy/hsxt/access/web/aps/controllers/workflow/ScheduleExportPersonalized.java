/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.workflow;

import java.io.Serializable;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsxt.access.web.aps.services.workflow.ApsScheduleExprotDataInit;

/***
 * 值班计划xls个性化
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.workflow
 * @ClassName: ScheduleExportPersonalized
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-1-7 下午12:30:04
 * @version V1.0
 */
public class ScheduleExportPersonalized implements Serializable {
    private static final long serialVersionUID = 8203093682576490747L;

    /**
     * 工作表
     */
    
    public HSSFWorkbook hssfw = null;

    private ApsScheduleExprotDataInit asedi = null;

    public ScheduleExportPersonalized() {

    }

    /**
     * 构造函数
     * 
     * @param _hssfw
     *            工作表
     * @param _asedi
     *            填充数据
     */
    public ScheduleExportPersonalized(ApsScheduleExprotDataInit _asedi, HSSFWorkbook _hssfw) {
        this.hssfw = _hssfw;
        this.asedi = _asedi;

        // 载入样式
        this.loadSheetStyle();
    }

    /**
     * 载入样式
     */
    void loadSheetStyle() {
        this.mergeCells();
        this.addCellStyle();
    }

    /**
     * 合并单元格
     */
    public void mergeCells() {
        CellRangeAddress craTemp = null;
        // 标题列整行合并
        craTemp = new CellRangeAddress(0, 0, 0, asedi.columnNum - 1);
        hssfw.getSheetAt(0).addMergedRegion(craTemp);

        // 第一行和第二行，每行的一列和二列合并
        craTemp = new CellRangeAddress(1, 2, 0, 1);
        hssfw.getSheetAt(0).addMergedRegion(craTemp);

        // 第一行和第二行，最后一列合并
        craTemp = new CellRangeAddress(1, 2, asedi.columnNum - 1, asedi.columnNum - 1);
        hssfw.getSheetAt(0).addMergedRegion(craTemp);
    }

    /**
     * 添加单元格样式
     */
    public void addCellStyle() {
        // 标题列加粗
        hssfw.getSheetAt(0).getRow(0).getCell(0).setCellStyle(this.cellWithBold(14));

        // 名称日期列文本加粗
        hssfw.getSheetAt(0).getRow(1).getCell(0).setCellStyle(this.cellWithBold(10));

        // 调休天数文本加粗
        hssfw.getSheetAt(0).getRow(1).getCell(this.asedi.columnNum - 1).setCellStyle(this.cellWithBold(10));

        // 日期为周末添加背景色
        this.weekendsAddBGC();

        // 值班类型为休、旷改为红色
        this.workTypeAddColor();
    }

    /**
     * 日期为周末时添加背景色
     */
    void weekendsAddBGC() {
        for (int i = 0; i < this.asedi.content[1].length; i++)
        {
            if (this.asedi.content[1][i] != null
                    && ("六".equals(this.asedi.content[1][i].toString()) || "日".equals(this.asedi.content[1][i]
                            .toString())))
            {
                hssfw.getSheetAt(0).getRow(1).getCell(i).setCellStyle(this.cellWithBold(10, HSSFColor.RED.index));
                hssfw.getSheetAt(0).getRow(2).getCell(i).setCellStyle(this.cellWithBold(10, HSSFColor.RED.index));
            }
        }
    }

    /**
     * 值班类型为休、旷标识为红色
     */
    void workTypeAddColor() {
        int sheetRows = hssfw.getSheetAt(0).getLastRowNum(); // 总行数
        String cellValue; // 列文本值

        // 循环行数,排除标题行
        for (int i = 3; i <= sheetRows; i++)
        {
            // System.out.println(i + "===================" + sheetRows);
            // 判断从第二列开始到(最后一列-1)，排除‘序号’和‘名称’列到’调休天数‘前一列
            for (int j = 2; j < hssfw.getSheetAt(0).getRow(i).getLastCellNum() - 1; j++)
            {
                // System.out.println(i + "行" + j + "列");
                cellValue = hssfw.getSheetAt(0).getRow(i).getCell(j).getStringCellValue();
                if (!StringUtils.isEmpty(cellValue) && ("休".equals(cellValue) || "旷".equals(cellValue)))
                {
                    hssfw.getSheetAt(0).getRow(i).getCell(j).setCellStyle(this.cellWithColor(HSSFColor.RED.index));
                }
            }

        }
    }

    /**
     * 默认样式
     */
    public HSSFCellStyle defaultCellStyle() {
        HSSFCellStyle hssfcs = hssfw.createCellStyle();
        hssfcs.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        hssfcs.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        hssfcs.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        hssfcs.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        hssfcs.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中格式
        hssfcs.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直
        return hssfcs;
    }

    /**
     * 单元格背景色
     */
    public HSSFCellStyle CellStyle() {
        HSSFCellStyle hssfcs = defaultCellStyle();
        hssfcs.setFillForegroundColor((short) 13);// 设置背景色
        hssfcs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return hssfcs;
    }

    /**
     * 文本加粗
     * 
     * @return
     */
    public HSSFCellStyle cellWithBold(int boldSize, short color) {
        HSSFCellStyle hssfcs = defaultCellStyle();
        HSSFFont font = hssfw.createFont();
        font.setFontName("黑体");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
        font.setFontHeightInPoints((short) boldSize);// 设置字体大小
        font.setColor(color);// 设置字体颜色
        hssfcs.setFont(font);
        return hssfcs;
    }

    /**
     * 文本颜色
     * 
     * @return
     */
    public HSSFCellStyle cellWithColor(short color) {
        HSSFCellStyle hssfcs = defaultCellStyle();
        HSSFFont font = hssfw.createFont();
        font.setColor(color);
        hssfcs.setFont(font);
        return hssfcs;
    }

    /**
     * 文本加粗
     * 
     * @return
     */
    public HSSFCellStyle cellWithBold(int boldSize) {
        HSSFCellStyle hssfcs = defaultCellStyle();
        HSSFFont font = hssfw.createFont();
        font.setFontName("黑体");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
        font.setFontHeightInPoints((short) boldSize);// 设置字体大小
        hssfcs.setFont(font);
        return hssfcs;
    }

}
