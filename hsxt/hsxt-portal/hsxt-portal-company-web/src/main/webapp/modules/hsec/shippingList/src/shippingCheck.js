define(function() {
	
				var boolShipping = false;
		var check = {
				validate : function() {
					boolShipping = false;
					$("#shippingForm").validate({
						onkeyup: function(element) { $(element).valid(); },
						//设置验证规则   
						rules: {
							"name": {
								required: true,
								maxlength:20
							},
							"charge": {
								digits:true,
								max:999999,
								min:0
							},
							"postage": {
								required: true,
								digits:true,
								max:999999,
								min:0
							},
							"remark": {
								maxlength:50
							}
						},
						//设置错误信息  
						messages: {
							"name": {
								required: "",
								maxlength:""
							},
							"charge": {
								digits:"请输入正整数",
								max:"包邮价取值范围:0~999999",
								min:"包邮价取值范围:0~999999"
							},
							"postage": {
								required: "请输入运费",
								digits:"请输入正整数",
								max:"运费取值范围:0~999999",
								min:"运费取值范围:0~999999"
							},
							"remark": {
								maxlength:"长度不能大于50个字符"
							}
						},
						submitHandler:function(){
							boolShipping = true;
							return false;
						},
						errorElement:"span",
						errorPlacement: function(error, element){
							error.appendTo(element.parent().addClass("red"));
						}
					});
				},
				checkBool : function(){
					return boolShipping;
				}
			
			}
			return check;
});

