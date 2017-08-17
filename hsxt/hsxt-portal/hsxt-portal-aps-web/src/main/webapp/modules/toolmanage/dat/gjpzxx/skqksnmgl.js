define(function () {
	return {
		/**
		 * 工具配置信息-查询刷卡器KSN生成记录
		 * @param params 参数对象
		 * @param detail 自定义函数1
		 * @param del 自定义函数2
		 */
		findMcrKsnRecordList:function(params, detail, del,add){
			return comm.getCommBsGrid("searchTable", "findMcrKsnRecordList", params, comm.lang("toolmanage"), detail, del,add);
		},
		
		/**
		 * 工具配置信息-查询刷卡器导出记录
		 * @param params 参数对象
		 * @param detail 自定义函数1
		 * @param del 自定义函数2
		 */
		queryKsnExportRecordList:function(params, callback){
			return comm.requestFun("queryKsnExportRecord", params, callback, comm.lang("toolmanage"));
		},
		
		/**
		 * 工具配置信息-查看积分KSN信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findPointKSNInfo : function(params, callback){
			comm.requestFun("findPointKSNInfo", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 工具配置信息-查看消费KSN信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findConsumeKSNInfo : function(params, callback){
			comm.requestFun("findConsumeKSNInfo", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 工具配置信息-导出积分KSN信息
		 * @param batchNo 批次号
		 * @param fileName 文件名称
		 * @param remark 导出说明
		 */
		exportPointKSNInfo : function(batchNo, fileName, remark){
			var url = "?bahctNo="+batchNo+"&fileName="+fileName+"&remark="+remark+"&custId="+comm.getRequestParams()['custId']+"&custName="+comm.getCookieOperNoName()+"&loginToken="+comm.getSysCookie('token')+"&channelType=1";
			return comm.domainList[comm.getProjectName()]+comm.UrlList["exportPointKSNInfo"]+url;
		},
		/**
		 * 工具配置信息-导出消费KSN信息
		 * @param batchNo 批次号
		 * @param fileName 文件名称
		 * @param remark 导出说明
		 */
		exportConsumeKSNInfo : function(batchNo, fileName, remark){
			var url = "?bahctNo="+batchNo+"&fileName="+fileName+"&remark="+remark+"&custId="+comm.getRequestParams()['custId']+"&custName="+comm.getCookieOperNoName()+"&loginToken="+comm.getSysCookie('token')+"&channelType=1";
			return comm.domainList[comm.getProjectName()]+comm.UrlList["exportConsumeKSNInfo"]+url;
		},
		/**
		 * 工具配置信息-生成积分KSN信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		createPointKSNInfo : function(params, callback){
			comm.requestFun("createPointKSNInfo", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 工具配置信息-生解析消费刷卡器KSN文件
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		parseKSNSeq : function(params, callback){
			comm.requestFun("parseKSNSeq", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 工具配置信息-导入消费刷卡器KSN
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		importConsumeKSNInfo : function(params, callback){
			comm.requestFun("importConsumeKSNInfo", params, callback, comm.lang("toolmanage"));
		}
	};
});