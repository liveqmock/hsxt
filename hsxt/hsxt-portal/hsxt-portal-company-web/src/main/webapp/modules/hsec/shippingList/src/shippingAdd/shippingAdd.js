define(["text!hsec_shippingListTpl/saveandupdate/saveandupdate.html"
        ,"hsec_shippingListSrc/shippingCheck"
        ,"hsec_tablePointSrc/jquery.charcount"], function(tpl,shippingCheck,charcount) {

	//添加运费
	var shippingAdd ={
			dlg2Click :function(ajaxRequest,callback){
				// 查询物流
					ajaxRequest.listSysLogistic(null, function(response) {
						var html = _.template(tpl, response);
						$('#dlg2').html(html);
						shippingCheck.validate();
						$("#cbox").on("click",function(){
							if($(this).is(":checked")==true){
								$("#postageId").show();
								$("#charge").val(0);
							}else{
								$("#postageId").hide();
								$("#charge").val(0);
							}
						})
						$("#dlg2").dialog({
									title : "添加运费模板",
									width : "600",
									modal : true,
									open: function() { 
										shippingAdd.bindInputCheck();
									},
									buttons : {
										'保存' : function() {
											$("#shippingForm").submit();
											if(shippingCheck.checkBool() != false){
											var param = $("#dlg2").find(".shippingForm").serialize()+"&logisticName="+$("#logisticId").find("option:selected").text();
											ajaxRequest.saveSalerStorage(param, function(response) {
												callback(response.retCode);
											});
											
											$(this).dialog("destroy");
											//刷新页面显示添加数据David 2016-1-11
											 $("#wsscgl_yfsz").click();
										}
									},
									'取消' : function() {
										$(this).dialog("destroy");
										//刷新页面显示添加数据David 2016-1-11
										 $("#wsscgl_yfsz").click();
									}
									}
								});
					});
			},
			bindInputCheck : function(){
				$('#shippingName').charcount({
					maxLength: 20,
					preventOverage: true,
					position : 'after'
				});
			}
	}
	return shippingAdd;
})