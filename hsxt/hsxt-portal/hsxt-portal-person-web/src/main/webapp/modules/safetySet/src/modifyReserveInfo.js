define(["text!safetySetTpl/modifyReserveInfo.html","safetySetLan"], function (tpl) {
	var modifyReserveInfo = {
		show : function(dataModule){
			//加载页面
			$("#myhs_zhgl_box").html(tpl);
			
			
			//确认提交按钮
			$('#modfiy_reservedInfo_submit').click(function(){
				var valid = modifyReserveInfo.validateData();
				if (!valid.form()) {
					return;
				}
				
				//确认设置
				comm.i_confirm(comm.lang("safetySet").modReserveInfo.affirmInstall, function () {          
					var jsonData ={
							 custId     : $.cookie('cookie_custId'),  
	                         pointNo    : $.cookie('cookie_pointNo'),	
	                         token 	    : $.cookie('cookie_token'),
	                         reservInfo : $("#reservedInfo_content").val()
					};
					dataModule.setReserveInfo(jsonData, function (response) {
							$("#nav_reserver_info").text($("#reservedInfo_content").val());
							$("#reservedInfo_content").val("");
							comm.yes_alert(comm.lang("safetySet").modReserveInfo.changeInformationSucc);
					});
				});
			});
		},
		validateData : function(){
			return $("#modReservedInfo_from").validate({
				rules : {
					reservedInfo_content : {
						required : true,
						maxlength : 20,
						minlength:2
					}
				},
				messages : {
					reservedInfo_content : {
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
	return modifyReserveInfo
});
