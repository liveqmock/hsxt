define(['text!InvestmentBonusTpl/main/main.html',
		'text!InvestmentBonusTpl/main/tzfh_dlg.html',
		'InvestmentBonusDat/InvestmentBonus',
		'InvestmentBonusLan'
		 ],function(mainTpl, tzfh_dlgTpl,investment){
	var investmentFun = { 
		showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(mainTpl)) ;
		    
			//分页查询
			investment.getInvestDividend("queryList_tzfh",function(record, rowIndex, colIndex, options){
				if(colIndex==2){
					rate = record['yearDividendRate']*100;
					return rate+'%';
				}
				if(colIndex==3){
					return comm.formatMoneyNumber(record['dividendInvestTotal']);
				}
				if(colIndex==4){
					return comm.formatMoneyNumber(record['totalDividend']);
				}
				
			});
			
			//跳转投资分红设置页面
			$('#tzfh_btn').click(function(){
				investmentFun.opt_tzfh();	
			});
		},
		opt_tzfh : function(){
			$('#tzfhDlg').html(tzfh_dlgTpl);
			var that = this;
			
			//获取年天数
			$("#investDividendYear").blur(function(){
				var $year = comm.navNull($(this).val());
				
				if($year!="" && !isNaN($year)){
					if(($year % 4 == 0) && ($year % 100 != 0 || $year % 400 == 0))
						$("#tzfh_opt_kfhts").val(366);
					else
						$("#tzfh_opt_kfhts").val(365);
				}
			});
			
			$('#tzfhDlg').dialog({
				title : '投资分红',
				width : 600,
				modal : true,
				closeIcon : true,
				buttons : {
					"确定":function(){
						var dself=this;
						
						//表单校验
						if (!that.saveValidate()) {
							return;
						}
						else{
							//获取表单参数
							var parma=$("#tzfh_optForm").serialize();
							
							//提交设置
							investment.addDividendRate(parma,function(rsp){
								$(dself).dialog("destroy");
								comm.alert({
									imgFlag : true,
									imgClass : 'tips_yes',
									width : 500,
									content : '投资分红信息已成功录入，系统将在分红日自动计算分红。'	
								});
							});
						}
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}	
				}	
			});
		},
		/**
		 * 投资分红设置表单验证
		 */
		saveValidate : function(){
			return comm.valid({
				formID : '#tzfh_optForm',
				left : 270,
				top : -1,
				rules : {
					investDividendYear : {
						required : true,
						digits : true
					},
					dividendRate : {
						required : true,
						number : true
					}
				},
				messages : {
					investDividendYear : {
						required : '请填写分红年份',
						digits : "请输入正确年份"
					},
					dividendRate : {
						required : '请填写分红比例',
						number : "请输入正确的比例"
					}
				}
			});
				
		}
	}
	return investmentFun;
}); 

 