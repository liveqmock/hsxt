define([ 'text!debitTpl/skzhgl/skzhmcgl.html',
		'text!debitTpl/skzhgl/skzhmcgl_tj.html',
		'text!debitTpl/skzhgl/skzhmcgl_xg.html',
		'debitDat/debit',
		'debitLan'], function(skzhmcglTpl,
		skzhmcgl_tjTpl, skzhmcgl_xgTpl,dataModoule) {
	var skzhmcglFun = {
		showPage : function() {
			$('#busibox').html(_.template(skzhmcglTpl));
			$('#tjskzhDiv').removeClass('none');
			$('#tjskzh_btn').click(function() {
			   skzhmcglFun.add();
			});
			dataModoule.listAccount("searchTable",null,skzhmcglFun.grid);
		},
		
		add : function(){
			$('#busibox').html(_.template(skzhmcgl_tjTpl));
			comm.liActive_add($('#tjskzhmc'));
			comm.initSelect('#skzhlx', comm.lang("debit").accountType);
			$('#tj_back').triggerWith('#skzhmcgl');
			$('#tj_save').click(function(){		
				if(!skzhmcglFun.validateDataAdd()){
					return;
				}
				var params = $("#skzhmcgl_tj_form"). serializeJson();
				$.extend(params,{"receiveAccountType":$("#skzhlx").attr("optionvalue")});
				dataModoule.saveAccountInfo(params,function(res){
					comm.yes_alert("新增账户名称成功",400);
					$('#tj_back').click();
				});		
			});
		},
		grid : function(record, rowIndex, colIndex, options) {
			if(colIndex == 1){
				return comm.lang("debit").accountType[record.receiveAccountType];
			}
		},
		/**
		 * 表单校验
		 */
		validateDataAdd : function(formID){
			return comm.valid({
				formID : '#skzhmcgl_tj_form',
				rules : {
					receiveAccountType : {
						required : true
					},
					receiveAccountName : {
						required : true
					},
					abbreviation : {
						required : true
					}
				},
				messages : {
					receiveAccountType : {
						required : comm.lang("debit")[35002]
					},
					receiveAccountName : {
						required : comm.lang("debit")[35003]
					},
					abbreviation : {
						required :  comm.lang("debit")[35004]
					}
				}
			});
		}
	}
	return skzhmcglFun;
});