define(['text!accountManageTpl/hbzh/zhye.html',
        'accountManageDat/accountManage'],function(tpl,accountManage){
	return hb_zhye = {
		
		showPage : function(){
		
			$('#busibox').html(_.template(tpl));
			//刷新按钮单击事件
			$('#hbPointFlush').click(function(){
				hb_zhye.bindData();
			});
			$('#hbPointFlush').click();
		},
		bindData : function(){
			accountManage.find_currency_blance({},function(res){
				$('#hbPointCNY').text(comm.formatMoneyNumber(res.data.currencyBlance));
			});
		}
	};
});