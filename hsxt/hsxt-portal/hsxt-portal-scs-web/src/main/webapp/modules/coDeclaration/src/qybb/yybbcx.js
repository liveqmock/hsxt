define(['text!coDeclarationTpl/qybb/yybbcx.html',
			'text!coDeclarationTpl/qybb/yybbcx/yybbcx_dialog_1.html',
			'text!coDeclarationTpl/qybb/yybbcx/yybbcx_dialog_2.html',
			'coDeclarationDat/qybb/yybbcx',
			'coDeclarationLan'
],function(yybbcxTpl, yybbcx_dialog_1Tpl, yybbcx_dialog_2Tpl ,dataModoule){
	var yybbcx_page = {
		yybbcx_dialog_1Tpl:yybbcx_dialog_1Tpl,
		yybbcx_dialog_2Tpl:yybbcx_dialog_2Tpl,
		showPage : function(){
			$('#contentWidth_2').html(_.template(yybbcxTpl));
			$('#contentWidth_2').show();
			$('#contentWidth_1').empty();
			$('#contentWidth_1').hide();
			comm.initSelect('#quickDate', comm.lang("coDeclaration").quickDateEnum);
			comm.initSelect('#search_status', comm.lang("coDeclaration").raiseStatusEnum, null, null, {name:'全部', value:''});
		 	$('#queryBtn').click(function(){
				yybbcx_page.query();
			});
		 	yybbcx_page.initData();
		},
		initData : function(){
			$("#search_startDate, #search_endDate").datepicker({//jquery UI 日期选择插件
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd",
				maxDate : comm.getCurrDate()
			});
			//快捷选择日期事件
			$("#quickDate").bind("change", function () {
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE'
				}[$(this).attr('optionValue')];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_startDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			});
		
		},
		query : function(){
			var valid = comm.queryDateVaild("search_form");
			if(!valid.form()){
				return false;
			}
			var jsonParam = {
					search_startDate:$("#search_startDate").val(),
					search_endDate:$("#search_endDate").val(),
					search_linkman:$("#search_linkman").val(),
					search_entName:$("#search_entName").val(),
					search_status:$("#search_status").attr('optionValue'),
			};
			dataModoule.findDissEntFilingList(jsonParam, this.detail);
		},
		detail : function(record, rowIndex, colIndex, options){
			var status_ = comm.getNameByEnumId(record['status'], comm.lang("coDeclaration").raiseStatusEnum);
			if(colIndex == 5){
				return status_;
			}
			var link1 =  $('<a data-type="'+ status_ +'" >查看</a>').click(function(e) {
				var params = {
						custId:$.cookie('cookie_custId'),
						pointNo:$.cookie('cookie_pointNo'),
						token:$.cookie('cookie_token'),
						applyId:record['applyId'],
					};
				//显示详情	
				var data_type=$(this).attr('data-type');
			//	if(data_type == "待审核" || data_type == "预约面谈" || data_type == "预约培训" || data_type == "通知打款"){
					if(data_type == ""){
					//查看详情
					dataModoule.findFilingById(params, function(res_){
						$('#yybbcx_dialog > p').html(_.template(yybbcx_page.yybbcx_dialog_1Tpl, res_.data.FILING_APP));
						//获取地区信息
						cacheUtil.syncGetRegionByCode(res_.data.FILING_APP['countryNo'], res_.data.FILING_APP['provinceNo'], res_.data.FILING_APP['cityNo'], "", function(resdata){
							$("#region").html(resdata);
						});
						$("#legalTypeText").html(comm.getNameByEnumId(res_.data.FILING_APP.legalType, comm.lang("coDeclaration").idCardTypeEnum));
						$('#yybbcx_dialog').dialog({width:815,title:data_type+'详情',closeIcon:true,buttons:{'确定':function(){$(this).dialog('destroy')} } }); 
						comm.getEasyBsGrid("tableDetail1", res_.data.FILING_SH, function(record_, rowIndex_, colIndex_, options_){
							if(colIndex_ == 0){
								return (options_.curPage-1)*options_.settings.pageSize+rowIndex_+1;
							}else if(colIndex_ == 3){
								return comm.getNameByEnumId(record_['idType'], comm.lang("coDeclaration").idTypeEnum);
							}else if(colIndex_ == 2){
								return comm.getNameByEnumId(record_['shType'], comm.lang("coDeclaration").shTypeEnum);
							}
						});
						var files = res_.data.FILING_APT;
						var aptType = {};
						for(var k in files){
							var title = comm.getNameByEnumId(files[k].aptType, comm.lang("coDeclaration").aptTypeEnum);
							comm.initPicPreview("#picFileId-"+files[k].aptType, files[k].fileId, title);
							if(files[k].aptType==4){
								aptType['picFileId4']='1';
							}
							if(files[k].aptType==5){ 
								aptType['picFileId5']='1';
							}
						}
						if(aptType['picFileId4']==null){
							$('#picFileId-4').hide();
						}
						if(aptType['picFileId5']==null){
							$('#picFileId-5').hide();
						}
					 });
				}else{
					//查看详情
					dataModoule.findFilingById(params, function(res_){
						$('#yybbcx_dialog > p').html(_.template(yybbcx_page.yybbcx_dialog_2Tpl, res_.data.FILING_APP));
						
						//获取地区信息
						cacheUtil.syncGetRegionByCode(res_.data.FILING_APP['countryNo'], res_.data.FILING_APP['provinceNo'], res_.data.FILING_APP['cityNo'], "", function(resdata){
							$("#region").html(resdata);
						});
						
						//获取操作员
						cacheUtil.searchOperByCustId(res_.data.FILING_APP.updatedBy, function(res){
							$("#updatedBy").html(comm.getOperNoName(res));
						});
						
						$("#legalTypeText").html(comm.getNameByEnumId(res_.data.FILING_APP.legalType, comm.lang("coDeclaration").idCardTypeEnum));
						$("#status").html(comm.getNameByEnumId(res_.data.FILING_APP.status, comm.lang("coDeclaration").raiseStatusEnum));
						$('#yybbcx_dialog').dialog({width:815,title:data_type+'详情',closeIcon:true,buttons:{'确定':function(){$(this).dialog('destroy')} }  }); 
						comm.getEasyBsGrid("tableDetail1", res_.data.FILING_SH, function(record_, rowIndex_, colIndex_, options_){
							if(colIndex_ == 0){
								return (options_.curPage-1)*options_.settings.pageSize+rowIndex_+1;
							}else if(colIndex_ == 3){
								return comm.getNameByEnumId(record_['idType'], comm.lang("coDeclaration").idTypeEnum);
							}else if(colIndex_ == 2){
								return comm.getNameByEnumId(record_['shType'], comm.lang("coDeclaration").shTypeEnum);
							}
						});
						var files = res_.data.FILING_APT;
						
						
						var aptType = {};
						for(var k in files){
							var title = comm.getNameByEnumId(files[k].aptType, comm.lang("coDeclaration").aptTypeEnum);
							comm.initPicPreview("#picFileId-"+files[k].aptType, files[k].fileId, title);
							if(files[k].aptType==4){
								aptType['picFileId4']='1';
							}
							if(files[k].aptType==5){ 
								aptType['picFileId5']='1';
							}
						}
						if(aptType['picFileId4']==null){
							$('#picFileId-4').hide();
						}
						if(aptType['picFileId5']==null){
							$('#picFileId-5').hide();
						}
					});
				}
			});
			return   link1 ;
		}
	};
	return yybbcx_page
}); 
