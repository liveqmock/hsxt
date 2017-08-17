define(['text!safeSetTpl/xgylxx/xgylxx.html',"safeSetDat/safeSet","safeSetLan"],function(tpl,safeSet){
	return {
		showPage : function(){
			var self = this;
			$('#busibox').html(_.template(tpl));
			
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
						$("#txtReserveInfo").val("");
						//展示预留信息
						self.shownewylxx();
						comm.alert({
							content: comm.lang("safeSet").update_reserve_info_success
						});
				});
				
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#xgylxx_form',
				rules : {
					txtReserveInfo : {
						required : true,
						minlength : 2
					}
				},
				messages : {
					txtReserveInfo : {
						required : comm.lang("safeSet").input_reserve_info,
						maxlength:comm.lang("safeSet").maxlength,
						minlength:comm.lang("safeSet").mixlength
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
			});
		}
	}
});