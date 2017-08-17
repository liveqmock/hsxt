define(["text!hsec_shippingListTpl/saveandupdate/saveandupdate.html"
        ,"hsec_shippingListSrc/shippingCheck"
        ,"hsec_tablePointSrc/jquery.charcount"],function(tpl,shippingCheck,charcount) {
			// 运费列表修改
			var shippingUpdate = {
				dlg3Click : function(ajaxRequest, thisObj, paramList, callback) {
					var shippid = $(thisObj).attr("shippid");
					var obj = '';
//					for ( var i = 0; i < paramList.data.result.length; i++) {
//						if (paramList.data.result[i].id == shippid) {
//							obj = paramList.data.result[i];
//						}
//					}
					for ( var i = 0; i < paramList.data.length; i++) {
						if (paramList.data[i].id == shippid) {
							obj = paramList.data[i];
						}
					}
					// 查询物流
					ajaxRequest.listSysLogistic(null, function(response) {
						response["shipping"] = obj;
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
						$("#dlg2").dialog(
								{
									title : "修改运费模板",
									width : "600",
									modal : true,
									open: function() { 
										shippingUpdate.bindInputCheck();
									},
									buttons : {
										'保存' : function() {
											$("#shippingForm").submit();
											if(shippingCheck.checkBool() != false){
											var param = $(this).find(".shippingForm").serialize()+"&logisticName="+$("#logisticId").find("option:selected").text();
											 ajaxRequest.updateSalerStorage(param, function(response) {
												 callback(response.retCode);
												});
											$(this).dialog("destroy");
											$("#wsscgl_yfsz").click();//Add by zhanghh @date:2015-12-16:tips:刷新页面;
										}
									},
									'取消' : function() {
										$(this).dialog("destroy");
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
			return shippingUpdate;
		});
