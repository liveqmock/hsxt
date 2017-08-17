define(['text!debitTpl/skzhgl/skzhxxgl.html',
		'text!debitTpl/skzhgl/skzhxxgl_tj.html',
		'text!debitTpl/skzhgl/skzhxxgl_xg.html',
		'debitDat/debit',
		'debitLan'], function(skzhxxglTpl, skzhxxgl_tjTpl, skzhxxgl_xgTpl,dataModoule){
	var skzhFun = {
         bankArray : null,
		 showPage : function(){
			$('#busibox').html(_.template(skzhxxglTpl));	

			//初始化银行列表
			cacheUtil.findCacheBankAll(function(bankRes){
				comm.initBankCombox("#bankId", bankRes, null, true);
				skzhFun.bankArray=bankRes;
				$("#bankSelect input").css("background","white");
				$("#bankSelect input").css("width","225px");
				$("#bankSelect input").addClass("h28_middle");
				$("#bankSelect input").addClass("w115");
				$("#bankSelect input").addClass("input-txt");
				$("#bankSelect input").attr('placeholder','请选择收款开户银行');
			});	
			$("#queryskzh").click(function(){
				var params={
						search_bankId:$("#bankId").val(),
						search_receiveAccountName:$("#receiveAccountName").val(),
						search_receiveAccountNo:$("#receiveAccountNo").val()
				};
				dataModoule.listAccounting("searchTable",params,skzhFun.grid);
			});
			$('#skzhxxglTpl').on('click', '#tjskzh_btn', function(){
				skzhFun.add();
				
			});
		},
		add : function(){
			$('#busibox').html(_.template(skzhxxgl_tjTpl));
			comm.liActive_add($('#tjskzh'));

			//初始化银行列表			
			comm.initBankCombox("#bankId", skzhFun.bankArray, null, true);
			
			//初始化账户名称下拉列表
			dataModoule.listAccountMemu(null,function(res_){
				$("#skzhmc").selectList({
					width:470,
					optionWidth:470,
					options:res_.data
				});
			});
			
			$('#tj_back').triggerWith('#skzhxxgl');
			$('#tj_save').click(function(){		
				if(!skzhFun.validateDataAdd()){
					return;
				}
				var params = $("#skzhxxgl_tj_form"). serializeJson();
				$.extend(params,{"receiveAccountId":$("#skzhmc").attr("optionvalue")});
				$.extend(params,{"bankName":comm.getBankNameByCode($("#bankId").val(),skzhFun.bankArray)});
				dataModoule.saveAccountingInfo(params,function(res){
					comm.yes_alert("新增收款账户成功",400);
					$('#tj_back').click();
				});		
			});
		},
		grid : function(record, rowIndex, colIndex, options) {
			 if(colIndex == 4){				
				return comm.lang("debit").isActive[record.isActive];
			 }else if(colIndex == 6 && record.isActive=='Y'){
					var link1 = $('<a>禁用</a>').click(function(e) {
						skzhFun.forbidAccount(record.receiveAccountInfoId);
					}.bind(this));
				return link1;
			}
		},
		forbidAccount: function(receiveAccountInfoId){
			comm.i_confirm('收款账号禁用后将无法再次启用！', function(){
				dataModoule.forbidAccountInfo({receiveAccountInfoId:receiveAccountInfoId},function(res){
					comm.yes_alert("成功禁用收款账号",400);
					$("#queryskzh").click();
				});
			});
		},
		/**
		 * 表单校验
		 */
		validateDataAdd : function(){
			return comm.valid({
				formID : '#skzhxxgl_tj_form',
				rules : {
					receiveAccountId : {
						required : true
					},
					bankId : {
						required : true
					},
					bankBranchName : {
						required : true
					},
					receiveAccountNo : {
						required : true
					}
				},
				messages : {
					receiveAccountId : {
						required : comm.lang("debit")[35005]
					},
					bankId : {
						required : comm.lang("debit")[35009]
					},
					bankBranchName : {
						required :  comm.lang("debit")[35006]
					},
					receiveAccountNo : {
						required :  comm.lang("debit")[35007]
					}
				}
			});
		}
	}	
	return skzhFun;
});