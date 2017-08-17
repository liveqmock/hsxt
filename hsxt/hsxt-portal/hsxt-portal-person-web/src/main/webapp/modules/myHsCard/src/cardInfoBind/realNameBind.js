define(["text!myHsCardTpl/cardInfoBind/realNameBind.html"], function (tpl) {
	var realNameBind = {
		show : function(dataModule){
			//加载页面
			$("#kxxbd_content").html(tpl);
			
			//得到互生卡号
			var hsCardId = $('#card_id').text();
			$("#cardInfoBind_ResNo").val(hsCardId);
			//获取绑定信息
			dataModule.getPersonInfoByResNo({
				resNo : hsCardId
			}, function (response) {
				if (response && response.baseInfo.custName) {
					$("#cardInfoBind_trueName").val(response.baseInfo.custName);
					$("#cardInfoBind_confirmLi,#cardInfoBind_comfireBtn").hide();
				} else {
					$("#cardInfoBind_confirmLi,#cardInfoBind_comfireBtn").show();
				}
			});

			//点击事件
			$("#cardInfoBind_comfireBtn").click(function () {
				var valid = realNameBind.validateData();
				if (!valid.form()) {
					return;
				}
				var resNo = $("#cardInfoBind_ResNo").val();
				var custName = $("#cardInfoBind_trueName").val();
				dataModule.updatePersonInfoCustName({
					resNo : resNo,
					custName : custName
				}, function (response) {
					if (response && response.code == 0) {
						comm.yes_alert('实名绑定成功');
						$("#cardInfoBind_confirmLi,#cardInfoBind_comfireBtn").hide();
					} else {
						comm.error_alert('实名绑定失败');
						$("#cardInfoBind_confirmLi,#cardInfoBind_comfireBtn").show();
					}
				});
			});
		},
		validateData : function(){
			return $("#cardInfoBind_form").validate({
				rules : {
					cardInfoBind_trueName : {
						required : true,
						maxlength : 20,
						minlength : 2
					},
					cardInfoBind_confirmName : {
						required : true,
						equalTo : "#cardInfoBind_trueName"
					}
				},
				messages : {
					cardInfoBind_trueName : {
						required : comm.lang("myHsCard").cardInfoBind.realNameBind.required,
						maxlength : comm.lang("myHsCard").cardInfoBind.realNameBind.maxlength,
						minlength : comm.lang("myHsCard").cardInfoBind.realNameBind.minlength
					},
					cardInfoBind_confirmName : {
						required : comm.lang("myHsCard").cardInfoBind.realNameBind.required2,
						equalTo : comm.lang("myHsCard").cardInfoBind.realNameBind.equalTo
					}
				},
				errorPlacement : function (error, element) {
					if (!$(element).is(":text")) {
						element = element.parent();
					}
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 2000,
						position : {
							my : "left top+35",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					if ($(element).is(":text")) {
						$(element).tooltip();
						$(element).tooltip("destroy");
					} else {
						$(element.parent()).tooltip();
						$(element.parent()).tooltip("destroy");
					}
				}
			});
		}
	};
	return realNameBind
});
