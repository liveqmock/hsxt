define(['text!fileDownloadTpl/business/business_cn.html',
        'fileDownloadDat/filedownload',
        'fileDownloadLan'],function(businessTpl, dataModoule){
	return {
		showPage : function(){
			$('#contentWidth_2').html(_.template(businessTpl));
			dataModoule.findDownloadList({'search_docStatus':2}, this.detail);
		},
		/**
		 * 详细信息
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 0){
				return "DOC";
			}
			return $('<a href="'+comm.getDocServerUrl({1001:{fileId:record.fileId, docName:record.docName}}, 1001)+'" >下载</a>');
		}
	}
}); 

 