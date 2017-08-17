define(function() {
	
		var check = {
				validate : function(check) {
					$("#formStart").validate({
						onkeyup: function(element) { $(element).valid(); },
						//设置验证规则   
						rules: {
							
							"contector": {
								required: true,
								maxlength:17
							},
							"contectWay": {
								required: true,
								phoneCN :true,
								maxlength:25
							},
							"remark": {
								required: true,
								maxlength:100
							}
						},
						//设置错误信息  
						messages: {
							"contector": {
								required: "请输入商城联系人",
								maxlength:"长度不能大于17个字符"
							},
							"contectWay": {
								required: "请输入联系电话",
								phoneCN:"电话号码格式:只能有+(加号)-(横杠)数字,中文字组合的电话号码",
								maxlength:"长度不能大于25个字符"
							},
							"remark": {
								required: "请输入商城描述",
								maxlength:"长度不能大于100个字符"
							}
						},
						submitHandler:function(){
							var bool = true;
							if($("#logoImage").attr("isBool") == "1"){
							}else{
								bool = false;
								$("#shopImg").html("请上传商城logo,建议大小不超过1024KB,尺寸大于550X550!");
							}
							if($("#xieyiYes").is(":checked")){
								$("#xieyiNo").html("");
							}else{
								bool = false;
								$("#xieyiNo").html("请同意协议，谢谢！");
							}
							if(bool){
								check.bindSubmit();
							}	
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

