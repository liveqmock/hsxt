define(['text!eshopBusinessTpl/tjfx/tktj.html'], function(tpl){
	return {
		showPage : function(){
			$('#busibox').html(tpl);
			
			var self = this,
			searchTable = null;
			
			/*下拉列表*/
			$("#tktj_zffs").selectList({
				options:[
					{name:'全部',value:''},
					{name:'现金支付',value:'1'},
					{name:'互生币支付',value:'2'},
					{name:'网银支付',value:'3'}
				]	
			}).change(function(e){
				var v = $(this).attr('optionValue');
				
			});
			/*日期控件*/
			$("#tktj_beginDate").datepicker({dateFormat:'yy-mm-dd'});
			$("#tktj_endDate").datepicker({dateFormat:'yy-mm-dd'});
			
			//查询单击事件
			$('#btn_cx').click(function(){
				if (!self.validateData()) {
					return;
				}
				
				searchTable = $.fn.bsgrid.init('tktj_table', {
					//url: '',
					localData: [{qyhsh:'06186010000',tkjsrq:'2014-08-28 10:11:53',tkzje:3382.00,tjfze:372.00},{qyhsh:'06186010000',tkjsrq:'2014-08-27 10:11:53',tkzje:3380.00,tjfze:370.00}],
					otherParames: {
						beginDate : $("#tktj_beginDate").val(),
						endDate : $("#tktj_endDate").val()
					},
					//不显示空白行
					displayBlankRows: false,
					//不显示无页可翻的提示
					pageIncorrectTurnAlert: false,
					//隔行变色
					stripeRows: true,
					//不显示选中行背景色
					rowSelectedColor: false,
					pageSize: 10
				});
			});
			//这一行需要去掉，此处用于显示数据
			$('#btn_cx').click();
		},
		validateData : function(){
			return comm.valid({
				formID : '#tktj_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		}
	};
});