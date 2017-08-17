define(["text!safeSetTpl/szylxx/szylxx.html","safeSetDat/safeSet","safeSetLan"],function(tpl,safeSet){
	return {
		showPage : function(){
			var self = this;
			$('#busibox').html(_.template(tpl));
			
			//设置预留信息
			$('#btn_szylxx_qr').click(function(){
				//验证合法参数
				if (!self.validateData()) {
					return false;
				}
				
				//提交设置预留信息
				var setParam={"reserveInfo":$("#txtReserveInfo").val()};
				safeSet.setylxx(setParam,function(res){
					comm.alert({
						content: comm.lang("safeSet").set_reserve_info_success,
						callOk: function(){
							comm.setCookie("reserveInfo", $("#txtReserveInfo").val());
							$("#020500000000").click();
							$("#subNav li a[menuurl='safeSetSrc/xgylxx/tab']").click();
						}
					});
				});
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