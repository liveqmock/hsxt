define(["text!fckr_safetySetTpl/setReserveInfo.html"], function (tpl) {
	var setReserveInfo = {
			show : function(dataModule){
				//加载页面
				$("#myhs_zhgl_box").html(tpl);
				//测试cookie读取操作
				$.cookie('cookie_custId','cookie_custId');
				$.cookie('cookie_pointNo','cookie_pointNo');
				$.cookie('cookie_token','cookie_token');
				
				//确认提交按钮
				$('#reserveInfo_set_submit').click(function(){
					var valid = setReserveInfo.validateData();
					if (!valid.form()) {
						return;
					}
					comm.i_confirm('确认设置吗？', function () {
						
						var jsonData ={
		                         reservInfo : $("#reservedInfo_content_set").val()
						};
						
						dataModule.setReserveInfo(jsonData, function (response) {
								$("#nav_reserver_info").text($("#reservedInfo_content_set").val());
								$("#reservedInfo_content_set").val('');
								/*$('#setReserveInfoLi').addClass("none");
								$('#modReserveInfoLi').removeClass("none");*/
								//跳转到修改预留信息
								//$('#ul_myhs_right_tab li a[data-id="4"]').trigger('click');
								comm.yes_alert(comm.lang("safetySet").setReserveInfo.ok);
								$("#side_safetySet").click();
						});
					});
				});
			},
		validateData : function(){
			return $("#reservedInfo_set_form").validate({
				rules : {
					reservedInfo_content_set : {
						required : true,
						maxlength : 20,
						minlength:2
					}
				},
				messages : {
					reservedInfo_content_set : {
						required : comm.lang("safetySet").setReserveInfo.required,
						maxlength : comm.lang("safetySet").setReserveInfo.maxlength,
						minlength:comm.lang("safetySet").setReserveInfo.minlength
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
	return setReserveInfo
});
