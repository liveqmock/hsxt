define(["text!coDeclarationTpl/yxkhcx/yxkhcx.html",
        'text!coDeclarationTpl/yxkhcx/yxhkcx_ck_dialog.html',
        'coDeclarationDat/yxkhcx/yxkhcx',
		'coDeclarationLan'],function(yxkhcxTpl, yxhkcx_ck_dialogTpl, dataModoule){
	return {
		showPage : function(){
			$('#busibox').html(_.template(yxkhcxTpl));
			var self = this;
			comm.initSelect('#search_appType', comm.lang("coDeclaration").appTypeEnum, null, null, '');
			comm.initSelect('#search_status', comm.lang("coDeclaration").dealStatusEnum, null, null, '');
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
		 	$('#queryBtn').click(function(){
				self.query();
			});
		},
		query : function(){
			if(!comm.queryDateVaild("search_form").form()){
				return;
			}
			var jsonParam = {
                    search_startDate:$("#search_startDate").val(),
                    search_endDate:$("#search_endDate").val(),
                    search_appType:$("#search_appType").attr('optionValue'),
                    search_entCustName:$("#search_entCustName").val(),
                    search_status:$("#search_status").attr('optionValue'),
			};
			cacheUtil.findProvCity();
			dataModoule.findIntentionCustList(jsonParam, this.detail, null);
		},
		detail : function (record, rowIndex, colIndex, options){
			var self = this;
			if(colIndex == 5){
				var result = comm.getRegionByCode(record['countryNo'], record['provinceNo'], record['cityNo']);
				
				return "<span title='"+result+"'>" + result + "</span>"; 
			}
			if(colIndex == 6){
				return comm.getNameByEnumId(record['appType'], comm.lang("coDeclaration").appTypeEnum);
			}
			if(colIndex == 8){
				return comm.getNameByEnumId(record['status'], comm.lang("coDeclaration").dealStatusEnum);
			}
			return $('<a>查看</a>').click(function(e) {
				var params = {applyId:record['applyId']};
				dataModoule.findIntentCustById(params, function(res){
					$('#yxkhcx_dialog>p').html(_.template(yxhkcx_ck_dialogTpl, res.data));
					$("#region").html(comm.getRegionByCode(res.data['countryNo'], res.data['provinceNo'], res.data['cityNo'], "-"));
					$("#appType").html(comm.getNameByEnumId(res.data.appType, comm.lang("coDeclaration").appTypeEnum));
					$("#entType").html(comm.getNameByEnumId(res.data.entType, comm.lang("coDeclaration").entTypeEnum));
					$("#status").html(comm.getNameByEnumId(res.data.status, comm.lang("coDeclaration").dealStatusEnum));
					$('#yxkhcx_dialog').dialog({width:650,
		 				buttons:{
		 					'确定':function(){
		 						$(this).dialog('destroy');
		 					}
		 				}
		 			});
				});
			}.bind(this));
		},
	}	 

});