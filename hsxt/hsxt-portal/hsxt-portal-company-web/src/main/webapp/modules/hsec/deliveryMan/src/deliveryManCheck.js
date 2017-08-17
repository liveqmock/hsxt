define(function() {
	
		var check = {
				validate : function(checks) {
					$("#submitMan,#submitManEdit").validate({
						onkeyup: function(element) { $(element).valid(); },
						//设置验证规则   
						rules: {
							"name": {
								required: true,
								maxlength:20
							},
							"phone": {
								required: true,
								maxlength:20,
								digits:true
							},
							"remark": {
								maxlength:100
							}
						},
						//设置错误信息  
						messages: {
							"name": {
								required: "",
								maxlength:""
							},
							"phone": {
								required: "",
								maxlength:"",
								digits:"请输入数字"
							},
							"remark": {
								maxlength:"长度不能大于100个字符"
							}
						},
						submitHandler:function(){
							if(checks.type == 0){
								if($(".imgLicense").attr("src") != "resources/img/defaultheadimg.png"){
									checks.bindSubmit();
								}else{
									$("#shopImg").html("请上传送货人员照片!");
								}
							}else{
								checks.bindUpdate();
							}
							return false;
						},
						errorElement:"span",
						errorPlacement: function(error, element){
								//error.addClass("red").appendTo(element.parent());
							error.appendTo(element.parent().addClass("red"));
						}
					});
				}
			
			}
			return check;
});

