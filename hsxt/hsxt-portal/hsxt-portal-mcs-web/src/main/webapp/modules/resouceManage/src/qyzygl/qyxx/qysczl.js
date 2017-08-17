define(['text!resouceManageTpl/qyzygl/qyxx/qysczl.html'], function(qysczlTpl){
	return{
		showPage : function(obj){
			$('#infobox').html(_.template(qysczlTpl,obj));	
			//错误图片重置
			/*$("#infobox img").error(function(){
				$(this).attr("src","resources/img/noImg.gif")
			});*/     			
			
			comm.bindPicViewer("#imgHelpAgreement",$("#imgHelpAgreement").attr("src"));	
			comm.bindPicViewer("#imgTaxRegImg",$("#imgTaxRegImg").attr("src"));	
			comm.bindPicViewer("#imgOrgCodeImg",$("#imgOrgCodeImg").attr("src"));	
			comm.bindPicViewer("#imgBusiLicenseImg",$("#imgBusiLicenseImg").attr("src"));	
			comm.bindPicViewer("#imgCreBackImg",$("#imgCreBackImg").attr("src"));	
			comm.bindPicViewer("#imgCreFaceImg",$("#imgCreFaceImg").attr("src"));	
		}	
	}
});