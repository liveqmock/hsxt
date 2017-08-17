define(['text!userpasswordTpl/jymmcz/qyjymmczsh.html',
		'text!userpasswordTpl/jymmcz/qyjymmczsh_dialog.html',
		"userpasswordDat/dlmmcz/userpwd"
		], function(qyjymmczshTpl, qyjymmczsh_dialogTpl,userpwd){
	return {
		gridObj : null,
		showPage : function(){
			$('#busibox').html(_.template(qyjymmczshTpl));	
			var self = this; 
			
			//首次加载
		   self.pageQuery(false);
		   
		   //查询
		   $("#btnQuery").click(function(){
			   self.pageQuery(true);
		   });
		},
		/** 分页查询 */ 
		pageQuery:function(autoLoad){
			var self = this; 
			var entResNo=$("#entResNo").val();
			var entCustName=$("#entCustName").val();
			if(entResNo==null||entResNo=="undefined"){
				entResNo="";
			}
			if(entCustName==null||entCustName=="undefined"){
				entCustName="";
			}
			var param = {"search_entResNo":entResNo,"search_entCustName":entCustName};
			
			gridObj=userpwd.listentjy_pwd(autoLoad,param,function(record, rowIndex, colIndex, options){
				
				var link1 = null;
				if(colIndex == 1){
					link1 = $('<a>' + gridObj.getColumnValue(rowIndex, 'entResNo') + '</a>').click(function(e) {
							var obj = gridObj.getRecord(rowIndex);
							self.chaKan(obj);
						}.bind(this) ) ;
				}
				
				if(colIndex == 5){
					return comm.getNameByEnumId(record['status'], {0:'待审核',1:'通过',2:'驳回'});
				} 
				if(colIndex == 6){
					if(record['status']==0){
						return comm.formatDate(record['applyDate'],'yyyy-MM-dd hh:mm:ss ');
					}else{
						return comm.formatDate(record['updatedDate'],'yyyy-MM-dd hh:mm:ss ');
					}
				} 
				
				if(colIndex == 7){
					    link1 = $('<a>审核</a>').click(function(e) {
						var obj = gridObj.getRecord(rowIndex);
						var params={
								resNo:obj.entResNo	
						}
						userpwd.findAllByResNo(params,function(res){
							var allInfor=res.data;
							var statusInfo=allInfor.statusInfo;
							var baseInfor=allInfor.baseInfo;
							var objdata=allInfor.mainInfo;
							obj['openDate'] = statusInfo.openDate;
							//获取操作员
							cacheUtil.searchOperByCustId(obj.createdby, function(resp){
								var operator = resp;
								obj['operNo'] = operator.userName;
								obj['operName'] = operator.realName;
								self.shenHe(obj);
								$("#downloadId").attr("href", comm.getFsServerUrl(obj.applyPath)+"&useSavedFileName=true&fileName=交易密码重置申请函");
							});
							
						});	
					}.bind(this) ) ;
				}
				return link1;
				
			});
		},
		chaKan : function(obj){
			comm.liActive_add($('#ckqyxxxx'));
			$('#busibox').addClass('none');
			$('#ent_detail').removeClass('none');
			$("#ent_ResNo").val(obj.entResNo);
			$('#xtzcxx').click();
		},
		validateViewMarkData : function(){
			return $("#valid_pwd_form").validate({
				rules : {
					verificationMode_qy : {
						required : true,
					},
				},
				messages : {
					verificationMode_qy:{
						required : comm.lang("consumerInfo").select_check_type
					},
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
		shenHe : function(obj){
			var self=this;
			obj.applyDate=comm.formatDate(obj.applyDate,'yyyy-MM-dd');
			$('#dialogBox').html(_.template(qyjymmczsh_dialogTpl, obj));	
			
			//判断一下，是否绑定手机号码
			if(obj.mobile==""||obj.mobile=="null"||obj.mobile==null){
				 comm.alert({
					 content: comm.lang("consumerInfo").no_ent_mobile_info,
					 callOk: function(){
					 }
				 });
				return false;
			}
			
			
			
			/*弹出框*/	
			$( "#dialogBox").dialog({
				title:"企业交易密码重置审核",
				modal:true,
				width:"700",
				height:"560	",
				buttons:{ 
					"确定":function(){
						var win = $(this);
						
						var validate=self.validateViewMarkData();
						if(!validate.form()){
							return false;
						}
						
						var userName=$("#doubleUserName").val();
						var dealPwd = $.trim($("#doublePassword").val());	//获取复核密码
						
						   if(userName==""||userName=="undefined"){
				            	comm.error_alert(comm.lang("consumerInfo").userName_must);
				            	return false;
				            }
				            if(dealPwd==""||dealPwd=="undefined"){
				            	comm.error_alert(comm.lang("consumerInfo").passWord_must);
				            	return false;
				            }
						
						
						
						var status = $('input[name="status"]:checked').val(); 
						//验证双签
						comm.verifyDoublePwd($("#doubleUserName").val(), $("#doublePassword").val(), 1, function(verifyRes){
							 var approvalParam={
									   "optRemark":$("#applyRemark").val(),
										"dblOptCustId":$("#doublePassword").val(),
										"applyId":obj.applyId,
										"entCustIdjymmcz":obj.entCustId,
										"status":status,
										"applyReason":$("#applyReason").val()
							 };
							 userpwd.Entjycz(approvalParam,function(rsp){
								 if(rsp.success){
									 comm.alert({
										 content: status==1?comm.lang("consumerInfo").reset_pay_pwd_success:comm.lang("consumerInfo").reset_pay_pwd_reject,
										 callOk: function(){
											 if(gridObj){
													gridObj.refreshPage();
												}
											 
											 win.dialog('destroy');
										 }
									 });
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
			
			/*下拉列表*/
			$("#verificationMode_qy").selectList({
				width : 180,
				optionWidth : 185,
				options:[
					{name:'密码验证',value:'1'},
					{name:'指纹验证',value:'2'},
					{name:'刷卡验证',value:'3'}
				]
			}).change(function(e){
				var val = $(this).attr('optionValue');
				switch(val){
					case '1':
						$('#fhy_userName_qy, #fhy_password_qy').removeClass('none');
						$('#verificationMode_prompt_qy').addClass('none');
						break;	
						
					case '2':
						$('#fhy_userName_qy, #fhy_password_qy').addClass('none');
						$('#verificationMode_prompt_qy').removeClass('none');
						break;
						
					case '3':
						$('#fhy_userName_qy, #fhy_password_qy').addClass('none');
						$('#verificationMode_prompt_qy').removeClass('none');
						break;
				}
			});
			/*end*/		
		}
	}	
});