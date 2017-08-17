define(['text!userpasswordTpl/dlmmcz/xfzdlmmcz.html',
		'text!userpasswordTpl/dlmmcz/xfzdlmmcz_dialog.html',
		 "userpasswordDat/dlmmcz/userpwd",
		 "userpasswordLan"
		], function(xfzdlmmczTpl, xfzdlmmcz_dialogTpl,userpwd){
	return {
		showPage : function(){
			$('#busibox').html(_.template(xfzdlmmczTpl));	
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
			var perResNo=$("#perResNo").val();
			var realName=$("#realName").val();
			if(perResNo==null||perResNo=="undefined"){
				perResNo="";
			}
			if(realName==null||realName=="undefined"){
				realName="";
			}
			var param = {"search_perResNo":perResNo,"search_realName":realName};
			var gridObj=userpwd.listconsumer_pwd(autoLoad,param,function(record, rowIndex, colIndex, options){
				
				if(colIndex == 4){
					return comm.lang("consumerInfo").baseStatusEnum[record["baseStatus"]];
					//return comm.getNameByEnumId(record['cardStatus'], {3:'挂失',1:'启用',2:'停用'});
				} 
				
				var link1 = null;
				if(colIndex == 6){
					    link1 = $('<a>密码重置</a>').click(function(e) {
						var obj = gridObj.getRecord(rowIndex);
						self.mmcz(obj);
					}.bind(this) ) ;
				}
				return link1;
				
			});
		},
		chaKan : function(){},
		mmcz : function(obj){
			var self=this;
			  $('#dialogBox').html(_.template(xfzdlmmcz_dialogTpl, obj));	
			/*弹出框*/	
				//判断一下，是否绑定手机号码
				if(obj.mobile==""||obj.mobile=="null"||obj.mobile==null){
					 comm.alert({
						 content: comm.lang("consumerInfo").nomobile_info,
						 callOk: function(){
						 }
					 });
					return false;
				}
			  
			  
			$( "#sq_dialogTpl").dialog({
				title:"消费者登录密码重置",
				modal:true,
				width:"380",
				height:'330',
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
						comm.getToken(null,function(response){
						//非空数据验证
						 var randomToken = response.data;	//获取随机token
						 var word = comm.encrypt(dealPwd,randomToken);		//加密
							var approvalParam={
									   "userName":userName,
										"loginPwd":word,
										"perCustId":obj.custId,
	                                    "randomToken":randomToken
							 };
							 userpwd.consumerdl_pwd(approvalParam,function(rsp){
								 if(rsp.success){
									 comm.alert({
										 content: comm.lang("consumerInfo").reset_login_pwd_success,
										 callOk: function(){
											 $("#sq_dialogTpl").dialog( "destroy" );
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
			$("#verificationMode_xfz").selectList({
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
						$('#fhy_userName_xfz, #fhy_password_xfz').removeClass('none');
						$('#verificationMode_prompt_xfz').addClass('none');
						break;	
						
					case '2':
						$('#fhy_userName_xfz, #fhy_password_xfz').addClass('none');
						$('#verificationMode_prompt_xfz').removeClass('none');
						break;
						
					case '3':
						$('#fhy_userName_xfz, #fhy_password_xfz').addClass('none');
						$('#verificationMode_prompt_xfz').removeClass('none');
						break;
				}
			});
			/*end*/	
					
		},
		/**验证复核员信息*/
		pwdValidate : function(){
			return $("#valid_pwd_form").validate({
				rules : {
					verificationMode_xfz : {
						required : true,
					},
				},
				messages : {
					verificationMode_xfz:{
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