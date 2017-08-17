define(['text!accountManageTpl/hsbzh/zhye.html',
        'accountManageDat/hsbzh/hsbzh'],function( zhyeTpl,dataModule ){
	return {	 	
		showPage : function(){
			$('#busibox').html(_.template(zhyeTpl)) ;
			
			//Todo...
			$("#hsbFlush").click(function() {
				
				//查询互生币余额
				dataModule.findHsbBlance(null,function(response) {
					//获取数据值
					$("#htconsumeAcct").text(comm.formatMoneyNumber(response.data.directionalHsb) || '0');	//慈善救助基金
					$("#htcashAcct").text(comm.formatMoneyNumber(response.data.circulationHsb) || '0');		//流通币
				});
			});
			
			//触发刷新事件，查询积分账户余额
			$("#hsbFlush").trigger('click');
 
				
		}
	}
}); 
