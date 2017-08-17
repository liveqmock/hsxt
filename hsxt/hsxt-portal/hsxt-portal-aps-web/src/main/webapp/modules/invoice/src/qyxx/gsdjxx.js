define(['text!invoiceTpl/qyxx/gsdjxx.html',
        'invoiceDat/invoice',
		'invoiceLan'], function(gsdjxxTpl,dataModoule){
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
				obj.buildDate=baseInfor.buildDate;
				obj.endDate=baseInfor.endDate;
				obj.businessScope=baseInfor.businessScope;
				obj.nature=comm.removeNull(allInfor.extendInfo.nature);
				obj.officeTel=comm.removeNull(allInfor.extendInfo.officeTel);
				obj.creTypeName=comm.lang("invoice").creType[obj.creType];
				$('#infobox').html(_.template(gsdjxxTpl,obj));
				$('#yyzhzhsmj').attr('src', comm.getFsServerUrl(comm.removeNull(extend.busiLicenseImg)));

				$("#yyzhzhsmj").click(function(){//企业营业执照扫描件图片预览
					comm.initTmpPicPreview("#yyzhzhsmj_a",comm.getFsServerUrl(comm.removeNull(extend.busiLicenseImg)),"营业执照扫描件预览");
				});
			});			
		}
	}	
});