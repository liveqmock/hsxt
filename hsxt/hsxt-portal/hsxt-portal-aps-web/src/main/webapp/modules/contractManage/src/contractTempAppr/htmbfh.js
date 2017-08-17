define(['text!contractManageTpl/contractTempAppr/htmbfh.html',
		'text!contractManageTpl/contractTempAppr/xzmb.html',
		'text!contractManageTpl/contractTempAppr/htmbfh_ck.html',
		'text!contractManageTpl/contractTempAppr/htmbfh_fh.html',
		'text!contractManageTpl/contractSeal/seal.html',
		'contractManageDat/contract',
		'contractManageLan'
		], function(htmbfhTpl, xzmbTpl, htmbfh_ckTpl, htmbfh_fhTpl, htgz_dialogTpl, dataModule){
	var self= {
		searchTable : null,
		showPage : function(){
			$('#busibox').html(_.template(htmbfhTpl));	
			
			//var self = this;
			
			/*下拉列表*/
			
			comm.initSelect('#custType', comm.lang("contractManage").custType, null, null, {name:'全部', value:''});
			comm.initSelect('#status', comm.lang("contractManage").templetStatusEnum, null, null, {name:'全部', value:''});
			
			/*end*/

			/*表格数据模拟*/
			$("#qry_htmbfh_btn").click(function(){
				var params = {
						search_optCustId		: $.cookie('custId'),
						search_templetName		: $("#templetName").val().trim()
				};
				var custType = $("#custType").attr("optionvalue");
				if(custType){
					params.search_custType = custType;
				}
				var status = $("#status").attr("optionvalue");
				if(status){
					params.search_status = status;
				}
				
				self.searchTable = comm.getCommBsGrid("searchTable","find_contract_temp_appr_by_page",params,comm.lang("certificateManage"),self.detail,self.edit,self.del);
			});
			
			/*end*/
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex==1){
				return comm.lang("contractManage").custType[record.custType];
			}
			if(colIndex==2){
				return comm.lang("contractManage").resTypeEnum[record.resType];
			}
			if(colIndex==3){
				return comm.lang("contractManage").templetStatusEnum[record.apprStatus];
			}
			if(colIndex==5){
				record.custTypeName = comm.lang("contractManage").custType[record.custType];
				record.resTypeName = comm.lang("contractManage").resTypeEnum[record.resType];
				var link1 =  $('<a>查看</a>').click(function(e) {
					//this.chaKan();
					$('#htmbfh_ck').html(_.template(htmbfh_ckTpl,record));
					record.content = record.templetContent;
					$('#viewContent').click(function(){
						$('#dialogBox').html(_.template(htgz_dialogTpl,record));
						
						/*弹出框*/
						$( "#dialogBox" ).dialog({
							title:"查看模板",
							width:"1000",
							modal:true,
							closeIcon : true,
							buttons:{
								"关闭":function(){
									 $( this ).dialog( "destroy" );
								}
							}
						});
					});
					
					$( "#htmbfh_ck" ).dialog({
						title:"查看模版",
						modal:true,
						closeIcon : true,
						width:"900",
						buttons : {
							"确定" : function(){
								$(this).dialog("destroy");	
							}
						}
					  });
				});
				return link1;
			}
		},
		
		edit : function(record, rowIndex, colIndex, options){
			if(colIndex==5){
				var link2 = '';
				var obj = self.searchTable.getRecord(rowIndex);
				if(self.searchTable.getRecordIndexValue(record, 'apprStatus') == '2' || self.searchTable.getRecordIndexValue(record, 'apprStatus') == '3'){
					link2 =  $('<a>复核</a>').click(function(e) {
	
						$('#htmbfh_fh').html(htmbfh_fhTpl);
						$("#htmbfh_fh").dialog({
							title:"合同模版复核",
							modal:true,
							width:"380",
							buttons:{
								"确定":function(){
									var reqParam = {
											templetId	:	obj.templetId,
											apprStatus	:	obj.apprStatus,
											apprResult	:	$('input:radio[name="isPass"]:checked').val(),
											apprOperator:	$.cookie('custId'),
											apprName	:	$.cookie('custname'),
											optCustId	:	$.cookie('custId'),
											apprRemark	:	$("#apprRemark").val().trim()
									};
									dataModule.contractTempAppr(reqParam, function(res){
										if(res.retCode=22000){
											$( "#htmbfh_fh" ).dialog( "destroy" );
											$('#051100000000_subNav_051105000000').click();
										}
									});
								},
								"取消":function(){
									 $( "#htmbfh_fh" ).dialog( "destroy" );
								}
							}
						
						});
						
					}) ;
				}
				return link2 ;
			}
		}
	};
	return self;
});