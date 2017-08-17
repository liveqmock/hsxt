define(['text!infoManageTpl/zyxxbgsp/sub_tab.html',
		'text!infoManageTpl/zyxxbgsp/shtj_dialog.html',
		'infoManageSrc/zyxxbgsp/ywblxx',
		'infoManageSrc/zyxxbgsp/blztxx',
		'infoManageDat/infoManage'
		], function(tab, shtj_dialogTpl, ywblxx, blztxx,infoManage){
	var subtabPage = {
		
		showPage : function(obj){
			
			$('#busibox2').html(_.template(tab, obj));
			comm.goDetaiPage("busibox","busibox2");
			
			var num = $('.tabList > li > a[class="active"]').attr('data-num');
			obj.num = num ;
			
			if(num == 'xfz'){
				if(obj.status == '0' || obj.status == '1'){
					$('#dialogBox').html(_.template(shtj_dialogTpl, obj));
					
					$('#shtj_btn').click(function(){
						/*弹出框*/
						$( "#dialogBox" ).dialog({
							title:"审核确认",
							modal:true,
							width:"400",	
							height:"220",
							buttons:{ 
								"确定":function(){
									if(!subtabPage.validata().form()){
										return;
									}
									var param = comm.getRequestParams(param);
									var apprve = $("input[name='apprve']:checked").val();
									var isPass = apprve == '1'?true:false;
									var dialog = $( this );
									
									//提交审核
									infoManage.apprPerImportantInfo({
										applyId : obj.per.applyId,
										optCustId : param.custId,
										optName : param.cookieOperNoName,
										optEntName : param.custEntName,
										isPass : isPass,
										apprRemark : $("#apprRemark").val()
									},function(res){
										
										comm.alert({
											content:comm.lang("infoManage").xfzzyxxspSuccess,callOk:function(){
												
												//将列表中相应记录置灰  使其不能再审批
												$("#searchTable tbody tr").each(function(){
													var td = $(this).find("td:eq(0)"); 
													var hsResNo = $(td).text();
													if(hsResNo == obj.per.perResNo){
														$(this).find("td:eq(6)").text(comm.lang("infoManage").hasAppr);
														$(this).find("td:eq(8)").empty();
														$(this).find("td:eq(9)").empty();
														$(this).find("td:eq(8)").append("<span style='color:#9D9B9B'>"+comm.lang("infoManage").apprval+"</span>");
														return false
													}
												});
												//返回列表
												comm.goDetaiPage("busibox2","busibox");
												//关闭弹出框
												dialog.dialog( "destroy" );
											}	
										});
										
									});
									
								},
								"取消":function(){
									 $( this ).dialog( "destroy" );
								}
							}
					
						  });
						/*end*/		
					});
					$('#fhtj_btn').click(function(){
						/*弹出框*/
						$( "#dialogBox" ).dialog({
							title:"复核确认",
							modal:true,
							width:"400",
							height:"220",
							buttons:{ 
								"确定":function(){
									
									if(!subtabPage.validata().form()){
										return;
									}
									var param = comm.getRequestParams(param);
									var apprve = $("input[name='apprve']:checked").val();
									var isPass = apprve == '1'?true:false;
									var dialog = $( this );
									
									//提交复核
									infoManage.reviewPerImportantInfo({
										applyId : obj.per.applyId,
										optCustId : param.custId,
										optName : param.cookieOperNoName,
										optEntName : param.custEntName,
										isPass : isPass,
										apprRemark : $("#apprRemark").val()
									},function(res){
										
										comm.alert({
											content:comm.lang("infoManage").xfzzyxxfhSuccess,callOk:function(){
												
												//将列表中相应记录置灰  使其不能再审批
												$("#searchTable tbody tr").each(function(){
													var td = $(this).find("td:eq(0)"); 
													var hsResNo = $(td).text();
													if(hsResNo == obj.per.perResNo){
														$(this).find("td:eq(6)").text(comm.lang("infoManage").hasReview);
														$(this).find("td:eq(8)").empty();
														$(this).find("td:eq(9)").empty();
														$(this).find("td:eq(8)").append("<span style='color:#9D9B9B'>"+comm.lang("infoManage").fuhe+"</span>");
														return false
													}
												});
												//返回列表
												comm.goDetaiPage("busibox2","busibox");
												//关闭弹出框
												dialog.dialog( "destroy" );
											}	
										});
										
									});
								},
								"取消":function(){
									$( this ).dialog( "destroy" );
								}
							}
						
						});
						/*end*/		
					});
				}
			}
			else{
				if(obj.status == '0'){		//审核
					$('#dialogBox').html(_.template(shtj_dialogTpl, obj));
					
					$('#shtj_btn').click(function(){
						/*弹出框*/
						$( "#dialogBox" ).dialog({
							title:"审核确认",
							modal:true,
							width:"400",
							height:"220",
							buttons:{ 
								"确定":function(){
									
									if(!subtabPage.validata().form()){
										return;
									}
									var param = comm.getRequestParams(param);
									var apprve = $("input[name='apprve']:checked").val();
									var isPass = apprve == '1'?true:false;
									var dialog = $( this );
									
									//提交审核
									infoManage.apprEntimportantinfochange({
										applyId : obj.ent.applyId,
										optCustId : param.custId,
										optName : param.cookieOperNoName,
										optEntName : param.custEntName,
										isPass : isPass,
										apprRemark : $("#apprRemark").val()
									},function(res){
										
										comm.alert({
											content:comm.lang("infoManage")[obj.num + "zyxxspSuccess"],callOk:function(){
												
												//将列表中相应记录置灰  使其不能再审批
												$("#searchTable tbody tr").each(function(){
													var td = $(this).find("td:eq(0)"); 
													var hsResNo = $(td).text();
													if(hsResNo == obj.ent.entResNo){
														$(this).find("td:eq(6)").text(comm.lang("infoManage").hasAppr);
														$(this).find("td:eq(7)").empty();
														$(this).find("td:eq(8)").empty();
														$(this).find("td:eq(7)").append("<span style='color:#9D9B9B'>"+comm.lang("infoManage").apprval+"</span>");
														return false
													}
												});
												//返回列表
												comm.goDetaiPage("busibox2","busibox");
												//关闭弹出框
												dialog.dialog( "destroy" );
											}	
										});
										
									});
								},
								"取消":function(){
									 $( this ).dialog( "destroy" );
								}
							}
					
						  });
						/*end*/		
					});
				}else if(obj.status == '1'){		//复核
					$('#dialogBox').html(_.template(shtj_dialogTpl, obj));
					
					$('#fhtj_btn').click(function(){
						/*弹出框*/
						$( "#dialogBox" ).dialog({
							title:"复核确认",
							modal:true,
							width:"400",
							height:"220",
							buttons:{ 
								"确定":function(){

									if(!subtabPage.validata().form()){
										return;
									}
									var param = comm.getRequestParams(param);
									var apprve = $("input[name='apprve']:checked").val();
									var isPass = apprve == '1'?true:false;
									var dialog = $( this );
									infoManage.reviewEntimportantinfochange({
										applyId : obj.ent.applyId,
										optCustId : param.custId,
										optName : param.cookieOperNoName,
										optEntName : param.custEntName,
										isPass : isPass,
										apprRemark : $("#apprRemark").val()
									},function(res){
										
										comm.alert({
											content:comm.lang("infoManage")[obj.num + "zyxxspSuccess"],callOk:function(){
												
												//将列表中相应记录置灰  使其不能再审批
												$("#searchTable tbody tr").each(function(){
													var td = $(this).find("td:eq(0)"); 
													var hsResNo = $(td).text();
													if(hsResNo == obj.ent.entResNo){
														$(this).find("td:eq(6)").text(comm.lang("infoManage").hasReview);
														$(this).find("td:eq(7)").empty();
														$(this).find("td:eq(8)").empty();
														$(this).find("td:eq(7)").append("<span style='color:#9D9B9B'>"+comm.lang("infoManage").fuhe+"</span>");
														return false
													}
												});
												//返回列表
												comm.goDetaiPage("busibox2","busibox");
												//关闭弹出框
												dialog.dialog( "destroy" );
											}	
										});
									});
								},
								"取消":function(){
									 $( this ).dialog( "destroy" );
								}
							}
					
						  });
						/*end*/		
					});
				}
			}	
			
			$('#ywblxx').click(function(){
				
				//消费者业务办理信息
				if(obj.num == 'xfz' || obj.num == 'xfzspcx')
				{
					obj.per.status = obj.status;
					obj.per.num = obj.num;
					obj.per.optType = obj.optType;
					obj.per.sexOldText = comm.lang("infoManage").personSex[obj.per.sexOld];
					obj.per.sexNewText = comm.lang("infoManage").personSex[obj.per.sexNew];
					obj.per.creTypeOldText = comm.lang("infoManage").realNameCreType[obj.per.creTypeOld];
					obj.per.creTypeNewText = comm.lang("infoManage").realNameCreType[obj.per.creTypeNew];
					ywblxx.showPage(obj.per);
					comm.liActive($('#ywblxx'));
					
				}//企业业务办理信息
				else
				{
					obj.ent.status = obj.status;
					obj.ent.num = obj.num;
					obj.ent.optType = obj.optType;
					ywblxx.showPage(obj.ent);
					comm.liActive($('#ywblxx'));
					
				}
			}.bind(this)).click();	

			
			$('#blztxx').click(function(){
				blztxx.showPage(obj);
				comm.liActive($('#blztxx'));
			}.bind(this));	
		},
		validata : function(){
			var validate = $("#apprve_form").validate({
				rules : {
					apprve:{
						required : true
					},
					apprRemark:{
						rangelength:[2,300]
					}
				},
				messages : {
					apprve:{
						required:comm.lang("infoManage").apprveResult
					},
					apprRemark:{
						rangelength:comm.lang("infoManage")[36021]
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+20 top+10",
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
			return validate;
		}	
	};
	return subtabPage
});