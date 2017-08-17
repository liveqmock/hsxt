define(['text!resouceManageTpl/qyzygl/qyxx/qygsdjxx.html'], function(qygsdjxxTpl){
	return{
		showPage : function(obj){
			$('#infobox').html(_.template(qygsdjxxTpl,obj));
			var busiLicenseImg = '';
			if(obj){
				busiLicenseImg = obj.entDetail.extendInfo.busiLicenseImg;
				if(null != busiLicenseImg && '' != busiLicenseImg){
					$("#busi_license_img").attr("src", comm.getFsServerUrl(busiLicenseImg));
				}
				
			}
			 $("#busi_license_img").click(function(){//托管、成员企业营业执照扫描件图片预览
			        comm.initTmpPicPreview("#busi_license_span",comm.getFsServerUrl(busiLicenseImg),"营业执照扫描件预览");
			    });
		}	
	}
});