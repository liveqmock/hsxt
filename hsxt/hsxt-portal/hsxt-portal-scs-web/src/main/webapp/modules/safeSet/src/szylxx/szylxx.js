define(['text!safeSetTpl/szylxx/szylxx.html',"safeSetDat/safeSet","safeSetLan"],function(szylxxTpl,safeSet){
	return {
		showPage : function(){
			var self = this;
			$('#busibox').html(_.template(szylxxTpl)) ;			 
			/** 提交 */
			$('#btn_szylxx_qr').click(function(){
				if (!self.validateData()) {
					return;
				}else{
					//参数
					var setParam={"reserveInfo":$("#txtReserveInfo").val()};
					safeSet.setylxx(setParam,function(res){
						comm.alert({
							content: comm.lang("safeSet").set_reserve_info_success,
							callOk: function(){
								
								comm.setCookie("reserveInfo", $("#txtReserveInfo").val());
								
								var parentId= "030700000000";
								$("li >a").each(function(){
									if($(this).attr("menuurl") == "safeSetSrc/szylxx/tab"){
										parentId = $(this).attr("id").split("_")[0];
										return false;
									}
								});
								
								var objParam={"parentId":parentId}
								
								comm.resetMenu(objParam,function(){
									$("li >a").each(function(){
										var arr = new Array();
										var menuurl = $(this).attr("menuurl");
										if(menuurl == "safeSetSrc/xgylxx/tab"){
											var id = $(this).attr("id");
											$("#" + id).trigger('click');
										}
									});
								});
								
								
								
//								$("#030700000000_subNav_030706000000").parent().hide();
//								
//								//跳转至预留信息修改页面
//								$("#030700000000_subNav_030707000000").parent().show();
//								$("#030700000000_subNav_030707000000").trigger('click');
							}
						});
					});
				}
			});
				
		},
		validateData : function(){
			return comm.valid({
				formID : '#szylxx_form',
				rules : {
					txtReserveInfo : {
						required : true
					}
				},
				messages : {
					txtReserveInfo : {
						required : comm.lang("safeSet").input_reserve_info,
						maxlength:comm.lang("safeSet").maxlength
					}
				}
			});
		}
	}
}); 

 