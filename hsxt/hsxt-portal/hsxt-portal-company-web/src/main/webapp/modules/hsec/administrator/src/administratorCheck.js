define(function() {
	
		var check = {
				validate : function(check) {
					$("#add_employee_form").validate({
						onkeyup: function(element) { $(element).valid(); },
						//设置验证规则   
						rules: {
							"accountNo": {
								required: true,
								rangelength:[4,4],
								digits:true
							},
							"name": {
								required: true,
								maxlength:25
							},
							"job": {
								required: true,
								maxlength:50
							},
							"accountPwd": {
								required: true,
								rangelength:[6,6],
								equalTo:"#accountPwd"
							},
							"phone": {
								required: true,
								rangelength:[11,11],
								digits:true
							}
						},
						//设置错误信息  
						messages: {
							"accountNo": {
								required: "请输入登录用户名",
								rangelength:"登录用户名必须为4位整数",
								digits:"登录用户名必须为4位整数"
							},
							"name": {
								required: "",
								maxlength:""
							},
							"job": {
								required: "",
								maxlength:""
							},
							"accountPwd": {
								required: "请输入密码",
								rangelength:"密码必须为6位",
								equalTo:"两次密码不一致"	
							},
							"phone": {
								required: "请输入手机号码",
								rangelength:"手机号码必须为11位",
								digits:"手机号码只能为数字"
							}
						},
						submitHandler:function(){
							check.bindAdd();
							return false;
						},
						errorElement:"span",
						errorPlacement: function(error, element){
								error.addClass("red").attr("style","padding-left:10px").appendTo(element.parent());
						}
					});
					$("#add_employee_form").submit();
				},
				validateUpdate : function(check){
					$("#updata_employee_form").validate({
						onkeyup: function(element) { $(element).valid(); },
						//设置验证规则   
						rules: {
							"accountNo": {
								required: true,
								rangelength:[4,4],
								digits:true
							},
							"name": {
								required: true,
								maxlength:25
							},
							"job": {
								required: true,
								maxlength:50
							},
							"phone": {
								required: true,
								rangelength:[11,11],
								digits:true
							}
						},
						//设置错误信息  
						messages: {
							"accountNo": {
								required: "请输入登录用户名",
								rangelength:"登录用户名必须为4位整数",
								digits:"登录用户名必须为4位整数"
							},
							"name": {
								required: "请输入姓名",
								maxlength:"长度不能大于25个字符"
							},
							"job": {
								required: "请输入职务",
								maxlength:"长度不能大于50个字符"
							},
							"phone": {
								required: "请输入手机号码",
								rangelength:"手机号码必须为11位",
								digits:"手机号码只能为数字"
							}
						},
						submitHandler:function(){
							check.bindUpdate();
							return false;
						},
						errorElement:"span",
						errorPlacement: function(error, element){
								error.appendTo(element.parent().addClass("red"));
						}
					});
				}
			
			}
			return check;
});

