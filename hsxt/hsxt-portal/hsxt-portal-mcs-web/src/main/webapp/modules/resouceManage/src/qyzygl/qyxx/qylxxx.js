define(['text!resouceManageTpl/qyzygl/qyxx/qylxxx.html'], function(qylxxxTpl){
	return{
		showPage : function(obj){
			$('#infobox').html(_.template(qylxxxTpl,obj));
			var helpAgreement = '';
			var contactProxy = '';
			if(obj && obj.entDetail && obj.entDetail.extendInfo){
				contactProxy = obj.entDetail.extendInfo.contactProxy;
				$("#contactProxy_img").attr("src", comm.getFsServerUrl(contactProxy));
				var custType = obj.entDetail.extendInfo.custType;
				var startResType = obj.entDetail.extendInfo.startResType;
				if(3 == custType && 2 == startResType){
					helpAgreement = obj.entDetail.extendInfo.helpAgreement;
					if(null != helpAgreement && '' != helpAgreement){
						$("#helpAgreement_img").attr("src", comm.getFsServerUrl(helpAgreement));
					}
				}
			}
			
			$("#contactProxy_img").click(function(){//托管、成员企业联系人授权委托书图片预览
		        comm.initTmpPicPreview("#contactProxy_span",comm.getFsServerUrl(contactProxy),"联系人授权委托书预览");
		    });
		    
		    $("#helpAgreement_img").click(function(){//托管企业（创业资源）创业帮扶协议图片预览
		        comm.initTmpPicPreview("#helpAgreement_span",comm.getFsServerUrl(helpAgreement),"创业帮扶协议图片预览");
		    });
//			var imgError=false;
			
			//错误图片重置
//			$("#contactProxy_img").error(function(){
//				imgError=true;
//				$(this).attr("src","resources/img/noImg.gif");
//			});
			
			//图片查看器
//			var imgPath=obj.entDetail.mainInfo.authProxyFile;
			
//			if(!comm.isEmpty(imgPath)&&!imgError){
//				comm.bindPicViewer("#contactProxy_img",$("#contactProxy_img").attr("src"));	
//			}
		}	
	}
});