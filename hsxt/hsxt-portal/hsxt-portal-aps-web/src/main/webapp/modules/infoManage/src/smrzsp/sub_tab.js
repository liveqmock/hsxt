define(['text!infoManageTpl/smrzsp/sub_tab.html',
		'text!infoManageTpl/smrzsp/shtj_dialog.html',
		'infoManageSrc/smrzsp/ywblxx',
		'infoManageSrc/smrzsp/blztxx',
		'infoManageDat/infoManage'
		], function(tab, shtj_dialogTpl, ywblxx, blztxx,infoManage){
	var subtabPage = {
		showPage : function(obj){
			
			var num = $('.tabList > li > a[class="active"]').attr('data-num');
			obj.num = num ;
			
			$('#busibox2').html(_.template(tab, obj));
			comm.goDetaiPage("busibox","busibox2");
			
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
									if(!subtabPage.validataApprve().form()){
										return;
									}
									var param = comm.getRequestParams(param);
									var apprve = $("input[name='apprve']:checked").val();
									var isPass = apprve == '1'?true:false;
									var dialog = $( this );
									
									//提交审核
									infoManage.apprPerRealNameIdentific({
										applyId : obj.per.applyId,   //申请编号
										optCustId : param.custId,    //操作员客户号
										optName : param.cookieOperNoName,	 //操作员名字
										optEntName : param.custEntName,//操作员所属公司名称
										isPass : isPass, 			//是否通过
										apprRemark : $("#apprRemark").val() //审批意见
									},function(res){
										
										comm.alert({
											content:comm.lang("infoManage").xfzsmrzspSuccess,callOk:function(){
												
												//将列表中相应记录置灰  使其不能再审批
												$("#searchTable tbody tr").each(function(){
													var td = $(this).find("td:eq(0)"); 
													var hsResNo = $(td).text();
													if(hsResNo == obj.per.perResNo){
														$(this).find("td:eq(4)").text(comm.lang("infoManage").hasAppr);
														$(this).find("td:eq(5)").empty();
														$(this).find("td:eq(6)").empty();
														$(this).find("td:eq(5)").append("<span style='color:#9D9B9B'>审批</span>");
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
									if(!subtabPage.validataApprve().form()){
										return;
									}
									var param = comm.getRequestParams(param);
									var apprve = $("input[name='apprve']:checked").val();
									var isPass = apprve == '1'?true:false;
									var dialog = $( this );
									
									//提交复核
									infoManage.reviewPerRealNameIdentific({
										applyId : obj.per.applyId,      //申请编号
										optCustId : param.custId,		//操作员客户号
										optName : param.cookieOperNoName,		//操作员姓名
										optEntName : param.custEntName, //操作员所属公司名称
										isPass : isPass,				//审核结果
										apprRemark : $("#apprRemark").val() //审核意见
									},function(res){
										
										comm.alert({
											content:comm.lang("infoManage").xfzsmrzfhSuccess,callOk:function(){
												
												//将列表中相应记录置灰  使其不能再审批
												$("#searchTable tbody tr").each(function(){
													var td = $(this).find("td:eq(0)"); 
													var hsResNo = $(td).text();
													if(hsResNo == obj.per.perResNo){
														$(this).find("td:eq(4)").text(comm.lang("infoManage").hasReview);
														$(this).find("td:eq(5)").empty();
														$(this).find("td:eq(6)").empty();
														$(this).find("td:eq(5)").append("<span style='color:#9D9B9B'>复核</span>");
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
									if(!subtabPage.validataApprve().form()){
										return;
									}
									var param = comm.getRequestParams(param);
									var apprve = $("input[name='apprve']:checked").val();
									var isPass = apprve == '1'?true:false;
									var dialog = $( this );
									
									//提交审核
									infoManage.apprEntRealNameIdentific({
										applyId : obj.ent.applyId,
										optCustId : param.custId,
										optName : param.cookieOperNoName,
										optEntName : param.custEntName,
										isPass : isPass,
										apprRemark : $("#apprRemark").val()
									},function(res){
										
										comm.alert({
											content:comm.lang("infoManage")[obj.num+"smrzspSuccess"],callOk:function(){
												
												//将列表中相应记录置灰  使其不能再审批
												$("#searchTable tbody tr").each(function(){
													var td = $(this).find("td:eq(0)"); 
													var hsResNo = $(td).text();
													if(hsResNo == obj.ent.entResNo){
														$(this).find("td:eq(6)").text(comm.lang("infoManage").hasAppr);
														$(this).find("td:eq(7)").empty();
														$(this).find("td:eq(8)").empty();
														$(this).find("td:eq(7)").append("<span style='color:#9D9B9B'>审批</span>");
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
									if(!subtabPage.validataApprve().form()){
										return;
									}
									var param = comm.getRequestParams(param);
									var apprve = $("input[name='apprve']:checked").val();
									var isPass = apprve == '1'?true:false;
									var dialog = $( this );
									infoManage.reviewEntRealNameIdentific({
										applyId : obj.ent.applyId,
										optCustId : param.custId,
										optName : param.cookieOperNoName,
										optEntName : param.custEntName,
										isPass : isPass,
										apprRemark : $("#apprRemark").val()
									},function(res){
										
										comm.alert({
											content:comm.lang("infoManage")[obj.num+"smrzfhSuccess"],callOk:function(){
												
												//将列表中相应记录置灰  使其不能再审批
												$("#searchTable tbody tr").each(function(){
													var td = $(this).find("td:eq(0)"); 
													var hsResNo = $(td).text();
													if(hsResNo == obj.ent.entResNo){
														$(this).find("td:eq(6)").text(comm.lang("infoManage").hasReview);
														$(this).find("td:eq(7)").empty();
														$(this).find("td:eq(8)").empty();
														$(this).find("td:eq(7)").append("<span style='color:#9D9B9B'>复核</span>");
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
				if(obj.num == 'xfz' || obj.num == 'xfzspcx' )
				{
					obj.per.status = obj.status;
					obj.per.num = obj.num;
					obj.per.sexText = comm.lang("infoManage").sexType[obj.per.sex];
					obj.per.certypeText = comm.lang("infoManage").realNameCreType[obj.per.certype];
					ywblxx.showPage(obj.per);
					comm.liActive($('#ywblxx'));
					
				}
				//企业业务办理信息
				else
				{
					obj.ent.status = obj.status;
					obj.ent.num = obj.num;
					obj.ent.certypeText = comm.lang("infoManage").legalCreType[obj.ent.legalCreType];
					ywblxx.showPage(obj.ent);
					comm.liActive($('#ywblxx'));
					
				}
			}.bind(this)).click();	

			$('#blztxx').click(function(){
				blztxx.showPage(obj);	
				comm.liActive($('#blztxx'));
			}.bind(this));	
		},
		validataApprve : function(){
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