define(["text!fckr_safetySetTpl/setDelPassword.html",
        "safetySetLan"], function (tpl) {
	var setDelPassword = {
		show : function(dataModule){
			//加载页面
			$("#myhs_zhgl_box").html(tpl);
			
			//设置随机登录密码
			setDelPassword.getRandomToken();
			
			
			//保存交易密码长度
			var delPwdLength = 8; //交易密码长度
			$("#sDelPwdLength").html(delPwdLength);
			$("#dealPwd_set_content,#dealPwd_set_content_affirm").attr("maxlength",delPwdLength);
			
			

			//确认提交按钮
			$('#dealPwd_submit').click(function(){
				var valid = setDelPassword.validateData();
				if (!valid.form()) {
					return;
				}
				
				//密码格式验证
				if (!setDelPassword.pwdFormatChk()) {
					return false;
				} 
				
				comm.i_confirm(comm.lang("safetySet").isSetDelPassword, function () {
					
					//获取界面数据
					var tradePwd 	= 	$("#dealPwd_set_content").val();		//交易密码
					var tradePwdRe 	= 	$("#dealPwd_set_content_affirm").val();	//确认交易密码
					var randomToken = 	$("#dealPwd_set_randomToken").val();	//获取随机token
					
					//密码根据随机token加密
					var tradePwdNew 	= comm.tradePwdEncrypt(tradePwd,randomToken);		//交易密码加密
					var randomTokenNew 	= comm.tradePwdEncrypt(tradePwdRe,randomToken);		//交易密码加密
					
					//封装传递的json参数
					var jsonData ={
							randomToken		:	randomToken,
							tradePwd 		: 	tradePwdNew,
							tradePwdRe  	:	randomTokenNew
					};
					
					//执行修改交易密码方法
					dataModule.setTradePwd(jsonData, function (response) {
						//提示信息
						comm.yes_alert(comm.lang("safetySet").setDealPassSuccess);
						//$("#side_safetySet").click();
						
						//设置div显示
						$('#setDealPwdLi').addClass("none");
						$('#modDealPwdLi,#reSetDealPwdLi').removeClass("none");
						$('#ul_myhs_right_tab li a[data-id="6"]').trigger('click');
						$("#dealPwd_set_content, #dealPwd_set_content_affirm").val("");
						
						//设置随机登录密码
						//setDelPassword.getRandomToken();
						
					});
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
					$("#dealPwd_set_randomToken").val(response.data);
				}
				else
				{
					comm.warn_alert(comm.lang("pointAccount").randomTokenInvalid);
				}
				
			});
		},
		//验证form表单的验证规则
		validateData : function(){
			return $("#dealPwd_set_form").validate({
				rules : {
					dealPwd_set_content : {
						required : true,
						minlength : $("#dealPwd_set_content").attr("maxlength"),
						digits : true
					},
					dealPwd_set_content_affirm : {
						required : true,
						equalTo : "#dealPwd_set_content",
						digits : true
					}
				},
				
				messages : {
					dealPwd_set_content : {
						required : comm.lang("safetySet")[30002],
						minlength : comm.lang("safetySet").setDelPasswordMinlength,
						digits : comm.lang("safetySet")[30168]
					},
					dealPwd_set_content_affirm : {
						required : comm.lang("safetySet")[30023],
						equalTo : comm.lang("safetySet")[30121],
						digits : comm.lang("safetySet")[30168]
					}
				},
				
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
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
		},
		//密码格式验证
		pwdFormatChk:function(){
			/** 密码数据格式验证 */
			var $txtNewPwd=$("#dealPwd_set_content");
			var $newPwd=$txtNewPwd.val();
			var $pwdLen=$txtNewPwd.attr("maxlength")-1;
			
			//匹配6位顺增或顺降  
			eval("var increaseReg =/^(?:(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){"+$pwdLen+"}|(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){"+$pwdLen+"})\\d$/;"); 
	        if(increaseReg.test($newPwd)){
	        	comm.alert({width:500,imgClass: 'tips_i' ,content:comm.lang("safetySet").trad_pwd_increase_error });
	        	return false;
	        }
	          
	        //匹配6位重复数字  
	        eval("var repeatedReg =/^([\\d])\\1{"+$pwdLen+"}$/;"); 
	        if(repeatedReg.test($newPwd)){
	        	comm.alert({width:500,imgClass: 'tips_i' ,content:comm.lang("safetySet").trad_pwd_number_repeated_error });
	        	return false;
	        }
	        return true;
		}
	};
	return setDelPassword
});
