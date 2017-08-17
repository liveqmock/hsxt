define(['text!cardReapplyPayTpl/qPayRegistered.html',
		'text!cardReapplyPayTpl/selectPayMethod.html',
		'text!cardReapplyPayTpl/agreement_dialog.html'
		], function(qPayRegisteredTpl, selectPayMethodTpl, agreement_dialogTpl){
	return {
		showPage : function(obj){
			$('#payment_operate').html(_.template(qPayRegisteredTpl, obj));	
			var self = this,
				selectedValue = {selectedMethod : 'quickPay'};
				selectedValue.paymentName_1 = obj.paymentName_1;
				
				/*下拉列表*/
				/*$("#certificatesType").selectList({
					width:120,
					optionWidth:125,
					borderWidth:1,
					borderColor:'#DDD',
					options:[
						{name:'身份证',value:'1'},
						{name:'港澳通行证',value:'2'}
					]
				}).change(function(e){
					console.log($(this).val() );
				});*/
				/*end*/
			$('#agreement_btn').click(function(){
				$('#agreement_dialog').html(agreement_dialogTpl);
					
				/*弹出框*/
				$( "#agreement_dialog" ).dialog({
					title:"快捷支付服务协议",
					width:"500",
					modal:true,
					closeIcon : true,
					/*buttons:{ 
						"确定":function(){
							$( this ).dialog( "destroy" );
						},
						"取消":function(){
							 $( this ).dialog( "destroy" );
						}
					}*/
				});
				/*end*/	
				
			});
			
			$('#agreeToPay_btn').click(function(){
				if(!self.validate()){
					return;	
				}

				require(['paySrc/pay'],function(pay){
					pay.showPage(obj);
				});	
				
			});
			
						
			$('#otherPayment_btn').click(function(){
				$('input').tooltip().tooltip('destroy');
				$('#select_dialog').html(_.template(selectPayMethodTpl, selectedValue));
				$( "#select_dialog" ).dialog({
					title:"选择其它支付方式",
					width:"600",
					closeIcon : true,
					modal:true,
					buttons:{ 
						"确定":function(){
							$( this ).dialog( "destroy" );
							var val = $(this).find('input[name = "paymentMethod"]:checked').val();
							switch(val){
								case 'hsbzf' :
									obj.paymentMethod = 'hsbzf';
									require(['paySrc/pay'],function(pay){
										pay.showPage(obj);
									});	
									break;
								case 'hbzhzf' :
									obj.paymentMethod = 'hbzhzf';
									require(['paySrc/pay'],function(pay){
										pay.showPage(obj);
									});	
									break;
								case 'wyzf' :
									obj.paymentMethod = 'wyzf';
									require(['paySrc/pay'],function(pay){
										pay.showPage(obj);
									});	
									break;
								case 'kjzf' :
									obj.paymentMethod = 'kjzf';
									require(['paySrc/pay'],function(pay){
										pay.showPage(obj);
									});	
									break;
							}
						},
						"取消":function(){
							 $( this ).dialog( "destroy" );
						}
					}
				});	
			});
		},
		validate : function(){
			return comm.valid({
				formID: '#registeredForm',
				rules : {
					/*fullName : {
						required : true	
					},
					certificatesType : {
						required : true	
					},*/
					certificatesNum : {
						required : true	
					},
					cardNum : {
						required : true	
					},
					phnoeNum : {
						required : true	
					},
					checkCode : {
						required : true	
					}
					
				},
				messages : {
					/*fullName : {
						required : '必填'	
					},
					certificatesType : {
						required : '必选'	
					},*/
					certificatesNum : {
						required : '必填'	
					},
					cardNum : {
						required : '必填'	
					},
					phnoeNum : {
						required : '必填'	
					},
					checkCode : {
						required : '必填'	
					}	
				}	
			});	
		}	
	}	
});