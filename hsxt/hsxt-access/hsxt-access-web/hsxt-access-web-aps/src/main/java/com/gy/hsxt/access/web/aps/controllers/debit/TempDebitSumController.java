/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.access.web.aps.controllers.debit;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsxt.access.web.aps.services.debit.ITempDebitSumService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tempacct.Debit;
import com.gy.hsxt.bs.bean.tool.CardInfo;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.controllers.debit
 * @className : TempDebitSumController.java
 * @description : 临账统计
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Controller
@RequestMapping("tempDebitSum")
public class TempDebitSumController extends
		BaseController<ITempDebitSumService> {
	@Autowired
	private ITempDebitSumService service;

	/**
	 * 获取临账户统计详细
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/getSumDetail" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findById(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);

			// 设置服务名和方法名，调用父类查询分页方法
			request.setAttribute("serviceName", service);
			request.setAttribute("methodName", "queryDebitCountDetailListPage");
			return super.doList(request, response);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 导出临账户统计详细
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("exporTempDebitSum")
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response) {
		// 生成提示信息，
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			// 收款账户ID
			String receiveAccountName = request.getParameter("receiveAccountName");
			// 查询开始时间
			String startDate = request.getParameter("startDate");
			// 查询结束时间
			String endDate = request.getParameter("endDate");
			
			// 进行转码，使其支持中文文件名
			codedFileName = java.net.URLEncoder.encode(
					System.currentTimeMillis() + "密码", "UTF-8");
			response.setHeader("content-disposition", "attachment;filename="
					+ codedFileName + ".xls");
			// 产生工作簿对象
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 产生工作表对象
			HSSFSheet sheet = workbook.createSheet();

			List<Debit> debitList = service.exportDebitData(receiveAccountName, startDate, endDate);
			if (debitList != null && debitList.size() > 0) {
				HSSFRow row = sheet.createRow((int) 0);// 创建一行

				HSSFCell cell = row.createCell((int) 0);// 创建一列
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("收款账户");

				cell = row.createCell((int) 1);// 创建一列
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("收款账户银行名称");

				cell = row.createCell((int) 2);// 创建一列
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("收款账户号");

				cell = row.createCell((int) 3);// 创建一列
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("付款企业");
				
				cell = row.createCell((int) 4);// 创建一列
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("付款时间");

				cell = row.createCell((int) 5);// 创建一列
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("收款总金额");

				cell = row.createCell((int) 6);// 创建一列
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("已用金额");

				cell = row.createCell((int) 7);// 创建一列
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("退款金额");

				cell = row.createCell((int) 8);// 创建一列
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("余额");

				codedFileName = java.net.URLEncoder.encode(debitList.get(0)
						.getReceiveAccountName() + "收款详情", "UTF-8");
				response.setHeader("content-disposition",
						"attachment;filename=" + codedFileName + ".xls");
				for (int i = 0; i < debitList.size(); i++) {
					row = sheet.createRow((int) (i + 1));

					cell = row.createCell((int) 0);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(debitList.get(i).getReceiveAccountName());

					cell = row.createCell((int) 1);// 创建一列
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(debitList.get(i).getBankBranchName());

					cell = row.createCell((int) 2);// 创建一列
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(debitList.get(i).getReceiveAccountNo());

					cell = row.createCell((int) 3);// 创建一列
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(debitList.get(i).getPayEntCustName());
					
					cell = row.createCell((int) 4);// 创建一列
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(debitList.get(i).getPayTime());

					cell = row.createCell((int) 5);// 创建一列
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(debitList.get(i).getPayAmount());

					cell = row.createCell((int) 6);// 创建一列
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(debitList.get(i).getLinkAmount());

					cell = row.createCell((int) 7);// 创建一列
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(debitList.get(i).getRefundAmount());

					cell = row.createCell((int) 8);// 创建一列
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(debitList.get(i).getUnlinkAmount());

				}
			}

			fOut = response.getOutputStream();
			workbook.write(fOut);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fOut != null) {
					fOut.flush();
					fOut.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected IBaseService getEntityService() {
		return service;
	}
}
