define(function () {
	return {
		/**
		 * 合同查询
		 * @param params 参数对象
		 * @param detail 自定义函数
		 */
		findDownloadList : function(params, detail){
			comm.getCommBsGrid(null, "findDownloadList", params, comm.lang("fileDownload"), detail);
		}
	};
});