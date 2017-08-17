define(['text!userpasswordTpl/jymmcz/xfzjymmcz.html',
		'text!userpasswordTpl/jymmcz/xfzjymmcz_dialog.html',
		 "userpasswordDat/dlmmcz/userpwd"
		], function(xfzjymmczTpl, xfzjymmcz_dialogTpl,userpwd){
	return {
		showPage : function(){

			$('#busibox').html(_.template(xfzjymmczTpl));	
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
				//	return comm.getNameByEnumId(record['cardStatus'], {3:'挂失',1:'启用',2:'停用'});
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
			$('#dialogBox').html(_.template(xfzjymmcz_dialogTpl, obj));	
			
			//判断一下，是否绑定手机号码
			if(obj.mobile==""||obj.mobile=="null"||obj.mobile==null){
				 comm.alert({
					 content: comm.lang("consumerInfo").nomobile_info,
					 callOk: function(){
					 }
				 });
				return false;
			}
			
			/*弹出框*/	
			$( "#dialogBox").dialog({
				title:"消费者交易密码重置",
				modal:true,
				width:"380",
				height:'330',
				buttons:{ 
					"确定":function(){
						
						var verificationMode_xfz=$("#verificationMode_xfz").val();
						 if(verificationMode_xfz==""||verificationMode_xfz=="undefined"){
								comm.error_alert(comm.lang("consumerInfo").select_check_type);
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
						//验证双签
						//comm.verifyDoublePwd($("#doubleUserName").val(), $("#doublePassword").val(), 1, function(verifyRes){
							  //获取随机token
							//获取随机token
							comm.getToken({'custId':comm.getSysCookie('custId')},function(response){
								//非空数据验证
								 var randomToken = response.data;	//获取随机token
							 	 //var dealPwd = $.trim($("#doublePassword").val());	//获取复核密码
								 var word = comm.encrypt(dealPwd,randomToken);		//加密
									var approvalParam={
											   "userName":userName,
												"loginPwd":word,
												"perCustId":obj.custId,
			                                    "randomToken":randomToken
									 };
									 userpwd.consumerjy_pwd(approvalParam,function(rsp){
										 if(rsp.success){
											 comm.alert({
												 content: comm.lang("consumerInfo").reset_trade_pwd_success,
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
					
		}
	}	
});