define(["text!myHsCardTpl/hsCardDisLossReg.html"], function (tpl) {
	var hsCardDisLossReg = {
		show : function(dataModule){
			
			//加载页面
			$("#myhs_zhgl_box").html(tpl);

			//查询互生卡挂失状态
			dataModule.findHsCardStatusInfoBycustId(null,function(response){
				if (response.data) {
					
					//变量声明
					var status = '未知';//互生卡挂失状态，默认位置防止错误
					$('#hsCardDisLossReg_cardNo').val(response.data.perResNo);//互生卡号
					
					//防止js错误
					try{
						//加载随机token
						hsCardDisLossReg.getRandomToken();
						
						//获取互生卡挂失状态国际化信息
						status = comm.lang("myHsCard").cardStatusEnum[response.data.cardStatus];
					}catch(e){}
					
					//正常状态，解挂按钮灰掉
					$("#hsCardDisLossReg_status").val(status);
					$("#hsCardDisLossReg_submitBtn").css("display", "block");
					$("#hsCardDisLossReg_li_loginpwd").css("display", "block");
				}
			});
			
			//确认提交单击事件
			$('#hsCardDisLossReg_submitBtn').click(function(){
				var valid = hsCardDisLossReg.validateData();
				if (!valid.form()) {
					return;
				}
				
				
				//密码加密
				var loginPwd = $("#hsCardDisLossReg_loginPwd").val();		//获取登录密码
				var randomToken = $("#hsCardDisLossReg_randomToken").val();	//获取随机token
				var word = comm.encrypt(loginPwd,randomToken);				//进行加密
				
				//封装提交json参数
				var jsonParam = {
						loginPwd 	: word,			//密码
						randomToken : randomToken,	//随机token
				};
				
				//执行互生卡解挂操作
				dataModule.hscSolutionLinked(jsonParam ,function(response){
						if (response.retCode ==22000){
								//提示信息
							comm.alert({content:comm.lang("myHsCard").solutionLinkedCardSuccessfully, callOk:function(){
								//设置菜单选中
								$("#head_tab_4").show();
								$("#head_tab_5").hide();
								$('#ul_myhs_right_tab li a[data-id="4"]').trigger('click');
							}});
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
		getRandomToken : function (){
			//获取随机token
			comm.getToken(null,function(response){
				//非空数据验证
				if(response.data)
				{
					$("#hsCardDisLossReg_randomToken").val(response.data);
				}
				else
				{
					comm.warn_alert(comm.lang("myHsCard").randomTokenInvalid);
				}
				
			});
		},
		validateData : function(){
			return $("#hsCardDisLossReg_form").validate({
				rules : {
					hsCardDisLossReg_loginPwd : {
						required : true
					}
				},
				messages : {
					hsCardDisLossReg_loginPwd : {
						required : comm.lang("myHsCard")[30026]
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
	return hsCardDisLossReg;
});
