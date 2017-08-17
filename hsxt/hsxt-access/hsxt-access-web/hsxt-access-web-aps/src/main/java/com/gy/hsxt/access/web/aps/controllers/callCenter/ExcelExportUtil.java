package com.gy.hsxt.access.web.aps.controllers.callCenter;


import com.gy.hsi.common.utils.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Font;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Excel导出工具类
 * @author leiyt
 * @version v0.0.1
 * @category 呼叫中心专用接口
 * @projectName hsxt-access-web-aps
 * @package com.gy.hsxt.access.web.aps.controllers.callCenter.ExcelExportUtil.java
 * @className ExcelExportUtil
 * @description 用于呼叫中心前台报表数据导出
 * @createDate 2016-3-4 下午3:44:45
 * @updateUser leiyt
 * @updateDate 2016-3-4 下午3:44:45
 * @updateRemark 说明本次修改内容
 */
public class ExcelExportUtil {

    public static void main(String[] args) {
        String title = "姓名,性别,年龄,生日";
        String json = "[{\"id\":\"牛二\",\"gender\":\"男\",\"age\":\"18\",\"birth\": \"1984-02-04\"},{\"id\":\"张三\",\"gender\":\"男\",\"age\":\"18\",\"birth\": \"1984-02-04\"},{\"id\":\"李四\",\"gender\":\"男\",\"age\":\"18\",\"birth\": \"1984-02-04\"}]";
        excelExport("单元名称", title, json);
    }

    /**
     * JSON导出Excel
//     * @param filePath  导出路径
     * @param sheetName  sheet名
     * @param title 列头用英文逗号隔开
     * @param jsonStr
     */
    public static HSSFWorkbook excelExport(String sheetName, String title, String jsonStr) {

        //创建一个excel对象
        HSSFWorkbook excel = new HSSFWorkbook();
        //创建一个sheet对象
        HSSFSheet sheet = excel.createSheet(sheetName);
        //创建一行
        HSSFRow row = sheet.createRow(0);
        //创建一个单元格
        HSSFCell cell = null;

        if (title != null) {
            String th[]=title.split(",");
            // 循环列
            for (int i = 0; i < th.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(th[i]);
            }
        }

        //添加数据
        JSONArray json = JSONArray.fromObject(jsonStr); // 首先把字符串转成 JSONArray  对象
        if (json.size() > 0) {
            for (int i = 0; i < json.size(); i++) {
                JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                row = sheet.createRow(cell != null ? (i + 1) : i);
                int col = 0;
                for (Iterator iter = job.keys(); iter.hasNext();) { //先遍历整个 Json 对象
                    String key = iter.next().toString();
                    cell = row.createCell(col++);
                    cell.setCellValue(job.getString(key));
                }
            }
        }

    return excel;
    }
}