define(['text!userpasswordTpl/dlmmcz/qydlmmcz.html',
		'text!userpasswordTpl/dlmmcz/qydlmmcz_dialog.html',
		 "userpasswordDat/dlmmcz/userpwd",
		 "userpasswordLan"
		], function(qydlmmczTpl, qydlmmcz_dialogTpl,userpwd){
	return {
		showPage : function(){
			$('#busibox').html(_.template(qydlmmczTpl));	
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
			var belongEntResNo=$("#belongEntResNo").val();
			var belongEntName=$("#belongEntName").val();
			if(belongEntResNo==null||belongEntResNo=="undefined"){
				belongEntResNo="";
			}
			if(belongEntName==null||belongEntName=="undefined"){
				belongEntName="";
			}
			var param = {"search_belongEntResNo":belongEntResNo,"search_belongEntName":belongEntName};
			var gridObj=userpwd.listbelongent_pwd(autoLoad,param,function(record, rowIndex, colIndex, options){
				
				var link1 = null;
				if(colIndex == 1){
					link1 = $('<a>' + gridObj.getColumnValue(rowIndex, 'entResNo') + '</a>').click(function(e) {
							var obj = gridObj.getRecord(rowIndex);
							self.chaKan(obj);
						}.bind(this) ) ;
				}
				else if(colIndex == 6){
					    link1 = $('<a>密码重置</a>').click(function(e) {
						var obj = gridObj.getRecord(rowIndex);
						self.mmcz(obj);
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
		mmcz : function(obj){
			var self=this;
			$('#dialogBox').html(_.template(qydlmmcz_dialogTpl, obj));	
			
			//判断一下，是否绑定手机号码
			if(obj.contactPhone==""||obj.contactPhone=="null"||obj.contactPhone==null){
				 comm.alert({
					 content: comm.lang("consumerInfo").no_ent_mobile_info,
					 callOk: function(){
					 }
				 });
				return false;
			}
			
			
			/*弹出框*/	
			$( "#dialogBox").dialog({
				title:"企业登录密码重置",
				modal:true,
				width:"380",
				height:'360',
				buttons:{ 
					"确定":function(){
						
						var validate=self.pwdValidate();
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
						//获取随机token
						comm.getToken({'custId':comm.getSysCookie('custId')},function(response){
							//非空数据验证
							 var randomToken = response.data;	//获取随机token
							 var word = comm.encrypt(dealPwd,randomToken);		//加密
								var approvalParam={
										   "userName":userName,
											"loginPwd":word,
											"resetEntResNo":obj.entResNo,
		                                    "randomToken":randomToken
								 };
							 userpwd.entdlcz_pwd(approvalParam,function(rsp){
								 if(rsp.success){
									 comm.alert({
										 content: comm.lang("consumerInfo").reset_login_pwd_success,
										 callOk: function(){
											 $("#dialogBox").dialog( "destroy" );
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
		},
		/**验证复核员信息*/
		pwdValidate : function(){
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
		}
	}	
});