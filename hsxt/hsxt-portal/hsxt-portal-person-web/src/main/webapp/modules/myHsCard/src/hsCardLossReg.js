define(["text!myHsCardTpl/hsCardLossReg.html"], function (tpl) {
	var hsCardLossReg = {
		show : function(dataModule){
			//加载页面
			$("#myhs_zhgl_box").html(tpl);
			
			//查询互生卡挂失状态 挂失界面
			dataModule.findHsCardStatusInfoBycustId(null,function(response){
				//非空验证
				if (response.data) {
					//互生卡号
					$('#hsCardLossReg_cardNo').val(response.data.perResNo);
					var status = '未知';
					
					//防止js错误
					try{
						//加载随机token
						hsCardLossReg.getRandomToken();
						
						//获取互生卡挂失状态国际化信息
						status = comm.lang("myHsCard").cardStatusEnum[response.data.cardStatus];
					}catch(e){}
					
					//正常状态，解挂按钮灰掉
					$("#hsCardLossReg_status").val(status);
					
				}
			}),

			
			/**
			 * 互生卡挂失提交操作
			 */
			$('#hsCardLossReg_submitBtn').click(function(){
				//数据格式验证
				var valid = hsCardLossReg.validateData();
				if (!valid.form()) 
				{
					return;
				}
				
				//密码加密
				var loginPwd = $("#hsCardLossReg_loginPwd").val();				//获取登录密码
				var randomToken = $("#hsCardLossReg_randomToken").val();		//获取随机token
				var word = comm.encrypt(loginPwd,randomToken);					//进行加密
				
				//封装提交json参数
				var jsonParam = {
						loginPwd 	: word,									//密码
						randomToken : randomToken,							//随机token
						lossReason 	: $("#hsCardLossReg_reason").val(),		//原因
				};
				
				//执行互生卡挂失
				dataModule.hscReportLoss( jsonParam , function (response) {
					if (response.retCode ==22000){
						//设置菜单选中
						$("#head_tab_4").hide();
						$("#head_tab_5").show();
						$('#ul_myhs_right_tab li a[data-id="5"]').trigger('click');
					}else if(160359 == response.retCode || 160108 == response.retCode || 160467 == response.retCode) {
						comm.error_alert(response.resultDesc);
						$('#mailBind_loginPwd').val('');
					}else{
						comm.alertMessageByErrorCode(comm.lang("myHsCard"), response.retCode);
						$('#mailBind_loginPwd').val('');
					}
			 });
			});
		},
		/**
		 * 获取随机token数据
		 */
		getRandomToken : function(){
			
			comm.getToken(null,function(response){
				//非空数据验证
				if(response.data)
				{
					$("#hsCardLossReg_randomToken").val(response.data);
				}
				else
				{
					comm.warn_alert(comm.lang("myHsCard").randomTokenInvalid);
				}
				
			});
		},
		validateData : function(){
			return $("#hsCardLossReg_form").validate({
				rules : {
					hsCardLossReg_reason : {
						required : true
					},
					hsCardLossReg_loginPwd : {
						required : true
					},
					hsCardLossReg_randomToken:{
						required : true
					}
				},
				messages : {
					hsCardLossReg_reason : {
						required : comm.lang("myHsCard")[30153]
					},
					hsCardLossReg_loginPwd : {
						required : comm.lang("myHsCard")[30026]
					},
					hsCardLossReg_randomToken : {
						required : comm.lang("myHsCard")[22021]
					}
					
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						position : {
							my : "left+180 top+5",
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
	};
	return hsCardLossReg
});
