package com.gy.hsxt.kafka.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.kafka.common.bean.SysLogInfo;
import com.gy.hsxt.kafka.common.constant.ConfigConstant;

public class ExcelWrite {
    public void writerToExcel(OutputStream os,List<SysLogInfo> list) throws WriteException,IOException{
    	int columns = ConfigConstant.EXCEL_COLUMNS;
    	String filePath = "暂定为 filepaht";
    	System.out.println("开始写excel");
    	WritableWorkbook workbook = Workbook.createWorkbook(os);
    	//创建新的一页
    	WritableSheet sheet = workbook.createSheet(ConfigConstant.MONITOR_LIST,0);
    	SysLogInfo info = null;
    	for(int i=0;i< list.size();i++){
    		info = list.get(i);
    		for(int j=0;j<columns;j++){
        		sheet.addCell(new Label(j,i,info.getTimeStamp()));
        		sheet.addCell(new Label(j,i,info.getPlatformName()));
        		sheet.addCell(new Label(j,i,info.getSystemName()));
        		sheet.addCell(new Label(j,i,info.getSystemInstanceName()));
        		sheet.addCell(new Label(j,i,info.getIpAddress()));
        		sheet.addCell(new Label(j,i,info.getHostName()));
        		sheet.addCell(new Label(j,i,info.getMoudleName()));
        		sheet.addCell(new Label(j,i,info.getFunName()));
        		sheet.addCell(new Label(j,i,getMonitorLevelName(info.getMonitorLevel())));
        		sheet.addCell(new Label(j,i,info.getLogContent()));
        		sheet.addCell(new Label(j,i,info.getExceptionMessage()));
        		sheet.addCell(new Label(j,i,filePath));
        	}
    	}
        //把创建的内容写入到输出流中，并关闭输出流
        workbook.write();
        workbook.close();
        os.close();
    }
    
    
    private String getFileUrl(){
    	HsPropertiesConfigurer.getProperty("");
    	return "";
    }
    
    private String getMonitorLevelName(String monitorLevel){
    	switch (monitorLevel) {
		case "1":
			return "低";
		case "2":
			return "中";
		case "3":
			return "高";
		default:return "低";
		}
    }
}