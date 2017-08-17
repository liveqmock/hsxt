define(['text!invoiceTpl/qyxx/lxxx.html',
        'invoiceDat/invoice',
		'invoiceLan'], function(lxxxTpl,dataModoule){
	return {
		showPage : function(){
			var params = {
				customId: $("#customId").val()
			};
			dataModoule.allCompanyInfor(params,function(res){
				var allInfor=res.data;
				var baseInfor=allInfor.baseInfo;
				var obj=allInfor.mainInfo;
				var extend = allInfor.extendInfo;
				obj.contactAddr=baseInfor.contactAddr;
				obj.postCode=baseInfor.postCode;
				obj.contactEmail=baseInfor.contactEmail;
				$('#infobox').html(_.template(lxxxTpl,obj));
				$('#lxrsqwts').attr('src', comm.getFsServerUrl(comm.removeNull(extend.contactProxy)));
				$("#lxrsqwts").click(function(){//企业营业执照扫描件图片预览
					comm.initTmpPicPreview("#lxrsqwts_a",comm.getFsServerUrl(comm.removeNull(extend.contactProxy)),"联系人授权委托书预览");
				});
				if(extend.startResType&&extend.startResType==2) {
					$('#cybfxy-tr').show();
					$('#cybfxy').attr('src', comm.getFsServerUrl(comm.removeNull(extend.helpAgreement)));
					$("#cybfxy").click(function(){//企业营业执照扫描件图片预览
						comm.initTmpPicPreview("#cybfxy_a",comm.getFsServerUrl(comm.removeNull(extend.helpAgreement)),"创业帮扶协议预览");
					});
				}else{
					$('#cybfxy-tr').hide();
				}
			});
		}	
	}	
});