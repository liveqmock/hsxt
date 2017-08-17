define(['text!safeSetTpl/szylxx/szylxx.html',"safeSetDat/safeSet","safeSetLan"],function(szylxxTpl,safeSet){
	return {
		showPage : function(){
			var self=this;
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
								//设置预留信息到cookie
								comm.setCookie("reserveInfo",$("#txtReserveInfo").val());
								
								$("#040700000000_subNav_040703000000").parent().hide();
								
								//跳转至预留信息修改页面
								$("#040700000000_subNav_040704000000").parent().show();
								$("#040700000000_subNav_040704000000").trigger('click');
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

 