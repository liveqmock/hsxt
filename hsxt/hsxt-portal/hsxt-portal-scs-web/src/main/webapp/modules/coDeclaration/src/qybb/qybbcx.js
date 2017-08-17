define(['text!coDeclarationTpl/qybb/qybbcx.html' ,
		   	'text!coDeclarationTpl/qybb/qybbcx/qybbcx_dialog_1.html' ,
		   	'text!coDeclarationTpl/qybb/qybbcx/qybbcx_dialog_2.html' ,	
		   	'text!coDeclarationTpl/qybb/qybbcx/qybbcx_dialog_tyy.html',  
		   	'coDeclarationDat/qybb/qybbcx',
		   	'coDeclarationSrc/qybb/qybb',
		   	'coDeclarationLan'
			],function(qybbcxTpl, qybbcx_dialog_1Tpl, qybbcx_dialog_2Tpl, qybbcx_dialog_tyyTpl, dataModoule, qybb){
	return {	 
		qybbcx_dialog_1Tpl: qybbcx_dialog_1Tpl,
		qybbcx_dialog_2Tpl:qybbcx_dialog_2Tpl ,
		qybbcx_dialog_tyyTpl:qybbcx_dialog_tyyTpl,
		showPage : function(){
			var self = this;
			$('#contentWidth_1').html(_.template(qybbcxTpl));
			$('#contentWidth_1').show();
			$('#contentWidth_2').empty();
			$('#contentWidth_2').hide();
			comm.initSelect('#quickDate', comm.lang("coDeclaration").quickDateEnum);
			comm.initSelect('#search_status', comm.lang("coDeclaration").declaraStatusEnum, null, null, {name:'全部', value:''});
		 	$('#queryBtn').click(function(){
				self.query();
			});
		 	self.initData();
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
			var params = {
				search_startDate:$("#search_startDate").val(),
				search_endDate:$("#search_endDate").val(),
				search_linkman:$("#search_linkman").val(),
				search_entName:$("#search_entName").val(),
				search_status:$("#search_status").attr('optionValue'),
			};
			dataModoule.findEntFilingList(params, this.detail, this.add, this.del);
		},
		detail : function (record, rowIndex, colIndex) {
			var status_ = comm.getNameByEnumId(record['status'], comm.lang("coDeclaration").declaraStatusEnum);
			if(colIndex == 5){
				return status_;
			}
			var link1 = "";
			if(record['status'] == 0){
				link1 =  $('<a data-type="'+status_+'" >编辑</a>').click(function(e) {
					dataModoule.findFilingStep({applyId:record['applyId']}, function(res){
						$("#bbMenuId").val("#qybb_qybbcx");
						qybb.showPage(record['applyId'], res.data);
					});
				});
			}else{
				link1 =  $('<a data-type="'+status_+'" >查看</a>').click(function(e) {
					var params = {applyId:record['applyId']};
					//显示详情
					var data_type = $(this).attr('data-type');
					var tpl = (data_type == '待审核')?qybbcx_dialog_1Tpl:qybbcx_dialog_2Tpl;
					//查看详情
					dataModoule.findFilingById(params, function(res_){
						//lyh
						if(res_.data.FILING_APP['existSameItem']==true){
							res_.data.FILING_APP['optRemark']="报备系统中已存在与该企业相似的资料";
						}
						$('#qybbcx_dialog > p').html(_.template(tpl, res_.data.FILING_APP));
						
						
						
						
						//获取地区信息
						cacheUtil.syncGetRegionByCode(res_.data.FILING_APP['countryNo'], res_.data.FILING_APP['provinceNo'], res_.data.FILING_APP['cityNo'], "", function(resdata){
							$("#region").html(resdata);
						});
						if(data_type != '待审核'){
							$("#status").html(comm.getNameByEnumId(res_.data.FILING_APP.status, comm.lang("coDeclaration").declaraStatusEnum));
						}
//						$("#legalTypeText").html(comm.getNameByEnumId(res_.data.FILING_APP.legalType, comm.lang("coDeclaration").idCardTypeEnum));
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
						for(var k = 0; k < files.length; k++){
							var title = comm.getNameByEnumId(files[k].aptType, comm.lang("coDeclaration").aptTypeEnum);
							comm.initPicPreview("#dialog_picFileId-"+files[k].aptType, files[k].fileId, title);
							if(files[k].aptType==4){
								aptType['picFileId4']='1';
							}
							if(files[k].aptType==5){ 
								aptType['picFileId5']='1';
							}
						}
						if(aptType['picFileId4']==null){
							$('#dialog_picFileId-4').hide();
						}
						if(aptType['picFileId5']==null){
							$('#dialog_picFileId-5').hide();
						}
						$('#qybbcx_dialog').dialog({
							width:815,
							title:data_type+'详情',
							closeIcon:true,
							buttons:{
								'确定':function(){
									$(this).dialog('destroy')
									} 
							} 
						});  
					});
				});
			}
			return  link1;
		},
		add : function (record, rowIndex, colIndex) {
			var self = this;
			if(colIndex == 5){
				return '';
			}
			var jg = comm.getNameByEnumId(record['status'], comm.lang("coDeclaration").declaraStatusEnum);
			if (jg=="报备驳回"){
				var link =  $('<a data-type="'+ record['status'] +'" >编辑</a>').click(function(e) {
					dataModoule.findFilingStep({applyId:record['applyId']}, function(res){
						$("#bbMenuId").val("#qybb_qybbcx");
						qybb.showPage(record['applyId'], res.data);
					});
				});	 				
				return link;
			}else if(jg=="待提交"){
				var link =  $('<a data-type="'+ record['status'] +'" >删除</a>').click(function(e) {
					comm.i_confirm (comm.lang("coDeclaration").confirmDel, function(){
						var params = {applyId:record['applyId']};
						dataModoule.delEntFilingById(params, function(res){
							comm.alert({content:comm.lang("coDeclaration")[22000], callOk:function(){
								$('#queryBtn').click();
							}});
						});
					}, 400);
				});	 				
				return link;
			} 
			return '';
		},
		del : function (record, rowIndex, colIndex) {
			var self = this;
			if(colIndex == 5){
				return '';
			}
			var jg = comm.getNameByEnumId(record['status'], comm.lang("coDeclaration").declaraStatusEnum);
			if (jg=="报备驳回"){
				var link =  $('<a data-type="'+ record['status'] +'" >提异议</a>').click(function(e) {
					$('#qybbcx_dialog > p').html(_.template(qybbcx_dialog_tyyTpl, {applyId:record['applyId']}));
					$('#qybbcx_dialog').dialog({width:480,title:'报备驳回详情',buttons:{
						'确定':function(){
							var params = {
									applyId:record['applyId'],
									remark:$("#remark").val(),
								};
							dataModoule.raiseDissent(params, function(res_){
								comm.alert({content:comm.lang("coDeclaration")[res_.retCode], callOk:function(){
									$("#qybbcx_dialog").dialog('destroy');
									$('#queryBtn').click();
								}});
							});
						},
						'取消':function(){$(this).dialog('destroy')},
					}}); 	
				});	 				
				return link;
			}
		}
	}
}); 
