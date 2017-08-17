define(['text!invoiceTpl/qyxx/xtdjxx.html',
        'invoiceDat/invoice',
		'invoiceLan'], function(xtdjxxTpl,dataModoule){
	return {
		showPage : function(){
			var params={
					customId:$("#customId").val()	
			};
			dataModoule.allCompanyInfor(params,function(res){
				var allInfor=res.data;
				var statusInfo=allInfor.statusInfo;
				var baseInfor=allInfor.baseInfo;
				var obj=allInfor.mainInfo;
				obj.openDate=statusInfo.openDate;
				$('#infobox').html(_.template(xtdjxxTpl,obj));
				//获取地区信息
				cacheUtil.syncGetRegionByCode(null, baseInfor.provinceCode, baseInfor.cityCode, "", function(resdata){
					$("#placeName").html(resdata);
				});
			});		
		}	
	}	
});