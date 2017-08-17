define(["text!fckr_safetySetTpl/setPasswordQuestion.html"], function (tpl) {
	var setPasswordQuestion = {
		show : function(dataModule){
			//加载页面
			$("#myhs_zhgl_box").html(tpl);

			
			//加载所有密保问题
			dataModule.getAllPwdQuestion(null,function (response) {
				$("#question_select").html("");
				$("#question_select").append('<option value="">请选择</option>');
				$.each(response.data, function (k, v) {
					$("#question_select").append('<option value="' + v.question + '">' + v.question + '</option>');
				});
			});
			
			//确认提交按钮
			$('#PwdQuestion_set_submit').click(function(){
				var valid = setPasswordQuestion.validateData();
				if (!valid.form()) {
					return;
				}
				
				//询问框确认
				comm.i_confirm(comm.lang("safetySet").pwdQuestionAffirmInstall, function () { 
					
					var answer = $("#question_answer").val();
				
					//封装传递json参数
					var jsonData ={
							question : $("#question_select").val(), 		//温饱问题id
	                        answer: CryptoJS.MD5(answer).toString()			//密保问题答案
					};
					
					//执行添加的方法
					dataModule.setPwdQuestion(jsonData, function (response) {
						//提示成功
						comm.yes_alert(comm.lang("safetySet").savePwdQuestionSuccess);
						
						//清空上次记录
						$("#question_answer").val("");
					});
				});
			});
		},
		validateData : function(){
			return $("#PwdQuestion_set_form").validate({
				rules : {
					question_select : {
						required : true
					},
					question_answer : {
						required : true,
						minlength : 1,
						maxlength : 50
					}
				},
				messages : {
					question_select : {
						required : comm.lang("safetySet").setPasswordQuestion.required
					},
					question_answer : {
						required : comm.lang("safetySet")[30116],
						maxlength : comm.lang("safetySet").setPasswordQuestion.maxlength,
						minlength:comm.lang("safetySet").setPasswordQuestion.minlength
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
		}
	};
	return setPasswordQuestion
});
