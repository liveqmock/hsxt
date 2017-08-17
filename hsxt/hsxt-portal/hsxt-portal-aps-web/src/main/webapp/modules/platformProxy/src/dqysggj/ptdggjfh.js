define(['text!platformProxyTpl/dqysggj/ptdggjfh.html',
		'text!platformProxyTpl/dqysggj/ptdggjfh_ck_dialog.html',
		'text!platformProxyTpl/dqysggj/ptdggjfh_fh_dialog.html',
		'text!platformProxyTpl/dqysggj/ptdggjfh_gq_sq_dialog.html',
		'platformProxyDat/platformProxy',
		"commDat/common",
		'platformProxyLan'
		], function(ptdggjfhTpl, ptdggjfh_ck_dialogTpl, ptdggjfh_fh_dialogTpl,ptdggjfh_gq_sq_dialogTpl,dataMoudle,commonAjax){
	var self = {
		showPage : function(){
			$('#busibox').html(ptdggjfhTpl);
			
			/*下拉列表*/
			$("#sglx").selectList({
				options:[
					{name:'新增申购',value:'1'},
					{name:'申报申购',value:'2'}
				]
			});
			 comm.initSelect('#shzt', comm.lang("platformProxy").status);
			
			$("#fh_form_submit").click(function(){
				var params={
						search_companyResNo:$("#companyResNo").val()
						//search_status : $("#shzt").attr("optionvalue")
				};
				dataMoudle.tool_order_list("searchTable",params,self.hangUp,self.edit,self.detail,self.refuseAccept);
			});
		},			
		detail : function(record, rowIndex, colIndex, options){
			     if(colIndex==3){
			    	 return "新增申购";
			     }else if(colIndex==4){
			    	 return comm.lang("platformProxy").proxyOrderType[record.orderType];
			     }else if(colIndex==5){
			    	 return comm.formatMoneyNumberAps(record.orderAmount);
			     } else if(colIndex==6){
			    	 return comm.lang("platformProxy").status[record.status];
			     }else if(colIndex==7){
					var link1 = $('<a>查看</a>').click(function(e) {
							dataMoudle.orderDetail({orderNo:record.proxyOrderNo},function(res){
								self.chaKan(res.data);
							});
					}) ;
				    return link1;
		         }
		},
		edit : function(record, rowIndex, colIndex, options){
				if(colIndex==7 && record.status == '0'){
						var link2 = $('<a>复核</a>').click(function(e) {
							dataMoudle.orderDetail({orderNo:record.proxyOrderNo},function(res){
								self.fuHe(res.data);
							});
						});
						return link2;
				}
		},
		//拒绝受理
		refuseAccept : function(record, rowIndex, colIndex, options){
			if(colIndex==8 && record.status == '0'){
				var link = $('<a>'+comm.lang("platformProxy").refuseAccept+'</a>').click(function(e) {
					comm.i_confirm(comm.lang("platformProxy").optRefuseAccept,function(){
						commonAjax.workTaskRefuseAccept({bizNo:record.proxyOrderNo},function(resp){
							comm.yes_alert(comm.lang("platformProxy").workTaskRefuseAcceptSucc,null,function(){
								$('#fh_form_submit').trigger('click');
							});
						});
					});
				});
				return link;
			}
		},
		//挂起
		hangUp : function(record, rowIndex, colIndex, options){
			if(colIndex==8 && record.status == '0'){
				var link = $('<a>'+comm.lang("platformProxy").hangUp+'</a>').click(function(e) {
					comm.i_confirm(comm.lang("platformProxy").optHangUp,function(){
						$('#dialogBox_fh_sq').html(_.template(ptdggjfh_gq_sq_dialogTpl));
						$( "#dialogBox_fh_sq" ).dialog({
							title:"工单挂起",
							width:"800",
							height:"340",
							modal:true,
							buttons:{
								"挂起":function(){
									if($('#verificationMode').attr('optionValue')!='1'){
										return;
									}
									if(!self.validateGq()){
										return;
									}
									comm.verifyDoublePwd($("#gdgqUserName").val(), $("#gdgqPassWord").val(), 1, function(resp){
										if(resp){
											$( '#dialogBox_fh_sq' ).dialog( "destroy" );
											commonAjax.workTaskHangUp({bizNo:record.proxyOrderNo,remark:$('#remark').val()},function(resp){
												comm.yes_alert(comm.lang("platformProxy").workTaskHangUp,null,function(){
													$('#fh_form_submit').trigger('click');
												});
											});
										}
									});
								},
								"取消":function(){
									$('#gdgqUserName, #gdgqPassWord, #remark').tooltip().tooltip("destroy");
									$( '#dialogBox_fh_sq' ).dialog( "destroy" );
								}
							}
						});
						/*下拉列表*/
						comm.initSelect("#verificationMode",
							comm.lang("platformProxy").verificationMode).change(function(e){
							var val = $(this).attr('optionValue');
							switch(val){
								case '1':
									$('#gdgqUserName_li, #gdgqPassWord_li').removeClass('none');
									$('#verificationMode_prompt').addClass('none');
									break;

								case '2':
									$('#gdgqUserName_li, #gdgqPassWord_li').addClass('none');
									$('#verificationMode_prompt').removeClass('none');
									break;

								case '3':
									$('#gdgqUserName_li, #gdgqPassWord_li').addClass('none');
									$('#verificationMode_prompt').removeClass('none');
									break;
							}
						});
						$("#verificationMode").selectListValue("1");
					});
				});
				return link;
			}
		},
		chaKan : function(obj){
			obj.statusName=comm.lang("platformProxy").status[obj.status];
			obj.orderTypeName=comm.lang("platformProxy").proxyOrderType[obj.orderType];
			$('#dialogBox_ck').html(_.template(ptdggjfh_ck_dialogTpl, self.buyCard(obj)));
		
			$( "#dialogBox_ck" ).dialog({
				title:"平台代购工具订单详情",
				width:"800",
				modal:true,
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
		},
		fuHe : function(obj){
			obj.statusName=comm.lang("platformProxy").status[obj.status];
			obj.orderTypeName=comm.lang("platformProxy").proxyOrderType[obj.orderType];
			$('#dialogBox_fh').html(_.template(ptdggjfh_fh_dialogTpl, self.buyCard(obj)));
			/*弹出框*/
			$( "#dialogBox_fh" ).dialog({
				title:"平台代购工具订单复核",
				width:"800",
				modal:true,
				buttons:{ 
					"确定":function(){
						if(!self.fhValid().form()){return;}
						var params=$("#fhform").serializeJson();
						$( this ).dialog( "destroy" );
						dataMoudle.proxy_order_appr(params,function(res){
							comm.yes_alert(comm.lang("platformProxy").proxyOrder_appr_success,400);
							$('#fh_form_submit').click();
						});
					},
					"取消":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
		},
		//买卡
		buyCard : function (obj) {
			if(obj.orderType=='110'){
				var detail = obj.detail[0];
				var count = (detail.quantity%1000==0) ? detail.quantity/1000 : (parseInt(detail.quantity/1000) + 1 );
				detail = {
					productName : '消费者系统资源段',
					price : '20000',
					quantity : count,
					totalAmount : 20000 * count
				};
				obj.detail[0] = detail;
			}
			return obj;
		},
		fhValid : function(){
			return $("#fhform").validate({
				rules : {
					apprRemark : {
						required : true,
						rangelength:[1,300]
					},
					status : {
						required : true	
					}
				},
				messages : {
					apprRemark : {
						required : comm.lang("platformProxy").toolManage_appremarkRequire,
						rangelength: comm.lang("platformProxy")[12115]
					},
					status : {
						required : comm.lang("platformProxy").toolManage_statuRequire
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+2 top+30",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
		},
		validateGq : function(){
			return comm.valid({
				formID : '#ptdggjfh_gq_sq_form',
				rules : {
					gdgqUserName:{
						required : true
					},
					gdgqPassWord:{
						required : true
					},
					remark:{
						maxlength : 300
					}
				},
				messages : {
					gdgqUserName:{
						required : comm.lang("platformProxy").apprUserNameIsNotNull
					},
					gdgqPassWord:{
						required : comm.lang("platformProxy").apprPassWordIsNotNull
					},
					remark:{
						maxlength : comm.lang("platformProxy").remarkLength300
					}
				}
			});
		},
		
	};
	return self;
});