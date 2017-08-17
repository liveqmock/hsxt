define(['text!safeSetTpl/xgylxx/xgylxx.html',"safeSetDat/safeSet","safeSetLan"  ],function(xgylxxTpl,safeSet ){
	return {
		 
		showPage : function(){
			var self = this;
			$('#busibox').html(_.template(xgylxxTpl)) ;			 
			
			//展示预留信息
			self.shownewylxx();
			
			$('#btn_xgylxx_qr').click(function(){
				if (!self.validateData()) {
					return;
				}
				
				//参数
				var setParam={"reserveInfo":$("#txtReserveInfo").val()};
				safeSet.updatelxx(setParam,function(res){
					comm.setCookie('reserveInfo',$("#txtReserveInfo").val());
					comm.alert({
						content: comm.lang("safeSet").update_reserve_info_success,
						callOk: function(){
							$("#txtReserveInfo").val("");
							self.shownewylxx();
						}
					});
				});
				
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#xgylxx_form',
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
		},
		//展示最新的预留信息
		shownewylxx:function(){
			//获取预留信息
			var setParam={};
			safeSet.getylxx(setParam,function(res){
				$("#txtOldReserveInfo").val(res.data);
				comm.setCookie("reserveInfo",res.data);
			});
		}
	}
}); 

 