define(['text!messageCenterTpl/wfxx/ckxq.html',
        'messageCenterDat/messageCenter'],function(ckxqTpl,messageCenter ){
	return {
		self : null,
		showPage : function(record){
			self = this;
			$('#xxzx_wfxx > a').removeClass('active');
			$('#xxzx_ckxq').css('display','').find('a').addClass('active');
			$('#xxzx_ckxq').siblings('li').not('#xxzx_wfxx').css('display','none');
			
			$('#busibox').html(_.template(ckxqTpl,record)) ;
			
			$('#message_content').xheditor({height:175});
			
			$('#btn_xzjsdw').click(function(){
				$('#jsdw_dialog').dialog({
					 buttons:{
					 	'确定':function(){
					 		var value = $("input[name='selectReceiveEnt']:checked").val();
					 		var name = $("input[name='selectReceiveEnt']:checked").next().text();
					 		$("#receiveEntValue").attr("value",value);
					 		$("#receiveEnt").attr("value",name);
					 		$(this).dialog('destroy');
					 	},
					 	'取消':function(){
					 		
					 		$(this).dialog('destroy');
					 	}
					 }
				});
			});
			
			$("#btn_send").click(function(){
				if (!self.validateData()) {
					return;
				}
				var content = $('#message_content').val();
				if(content == null || "" == content){
					comm.error_alert(comm.lang("messageCenter")[32702]);
					return;
				}
				var postData = {
						msgId : $("#msgId").val(),
						title : $("#ckxx_msgTitle").val(),						//消息标题
						msg : content,					//消息内容
						type : $("input[name='msgType']:checked").val(),	//消息类型
						level : $("input[name='msgLevel']:checked").val(),	//消息级别
						sender : '111',										//发送人
						receiptor : $("#receiveEntValue").val(), 			//接收者
						entResNo : '12393847362',							//发生企业互生号
						entCustId : '111',									//方式企业客户号
						entCustName : 'xxx服务公司' 							//发送企业名称
				};
				messageCenter.sendMessage(postData,function(res){
					$("#subNav_9_02").click();
				});
			});
			$("#btn_save").click(function(){
				if (!self.validateData()) {
					return;
				}
				var content = $('#message_content').val();
				if(content == null || "" == content){
					comm.error_alert(comm.lang("messageCenter")[32702]);
					return;
				}
				var postData = {
						msgId : $("#msgId").val(),
						title : $("#ckxx_msgTitle").val(),						//消息标题
						msg : content,					//消息内容
						type : $("input[name='msgType']:checked").val(),	//消息类型
						level : $("input[name='msgLevel']:checked").val(),	//消息级别
						sender : '111',										//发送人
						receiptor : $("#receiveEntValue").val(), 			//接收者
						entResNo : '12393847362',							//发生企业互生号
						entCustId : '111',									//方式企业客户号
						entCustName : 'xxx服务公司' 							//发送企业名称
				};
				messageCenter.sendMessage(postData,function(res){
					$("#subNav_9_03").click();
				});
			});
			$("#receiveEnt").keyup(function(){
				$("#receiveEntValue").attr("value",$("#receiveEnt").val());
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#ckxx_form',
				rules : {
					ckxx_msgTitle : {
						required : true
					},
					receiveEnt : {
						required : true
					}
				},
				messages : {
					ckxx_msgTitle : {
						required : comm.lang("messageCenter")[32701]
					},
					receiveEnt : {
						required : comm.lang("messageCenter")[32703]
					}
				}
			});

		}
	}
}); 

 