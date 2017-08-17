define([
"text!safeSetTpl/reset/resetDealPwd.html",
"text!safeSetTpl/reset/resetDealPwdSure.html",
"text!safeSetTpl/reset/resetDealPwdApply.html",
"text!safeSetTpl/reset/resetDealPwdApplySure.html" 
],function(resetDealPwdTpl,resetDealPwdSureTpl,resetDealPwdApplyTpl,resetDealPwdApplySureTpl){
 
	var group = {
		show:function(){
			//加载中间内容 
			$(".operationsArea").html(resetDealPwdTpl).append(resetDealPwdSureTpl).append(resetDealPwdApplyTpl).append(resetDealPwdApplySureTpl);	
			
			function allHide(){
				$("#resetDealPwd,#resetDealPwdSure,#resetDealPwdApply,#resetDealPwdApplySure").addClass("none");
			};
			
			$(".a_pwd_apply").click(function(){
				allHide();
				$("#resetDealPwdApply").remove("none");
				$(".a_pwd_back").click(function(){
					allHide();
					$("#resetDealPwd").remove("none");
				});
				$(".a_pwd_apply_sure").click(function(){
					allHide();
					$("#resetDealPwdApplySure").remove("none");
					$(".a_pwd_back").click(function(){
						allHide();
						$("#resetDealPwd").remove("none");
					});
				});	
			});
			$(".a_pwd_reset").click(function(){
				allHide();
				$("#resetDealPwdSure").remove("none");
				$(".a_pwd_back").click(function(){
					allHide();
					$("#resetDealPwd").remove("none");
				});
				//弹出框
				$( ".a_pwd_reset_sure" ).click(function() { 
					  $( "#pwdResetSure" ).dialog({
						title:"提示",
						width:"550",
						modal:true,
						buttons:{ 
							"确定":function(){
								$( this ).dialog( "destroy" );
							}
						}
					});
				});
			});	
			
		},
		
		hide:function(){
			//清空中间内容） 
			$(".operationsArea").empty();	
		}
		
	};
	
		
	return group;
	 

});