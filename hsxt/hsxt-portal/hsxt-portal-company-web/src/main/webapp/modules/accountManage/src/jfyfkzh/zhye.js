define(['text!accountManageTpl/jfyfkzh/zhye.html'], function(tpl){
	return jfyfkzh_zhye = {
		showPage : function(){
			this.bindData();
		},
		bindData : function(){
			
			var response = {
				pointAcct: comm.formatMoneyNumber(8200.00),
				pointNum: comm.formatMoneyNumber(8200.00)
			};
			$('#busibox').html(_.template(tpl, response));
			
			//刷新按钮单击事件
			$('#zhyy_flush').click(function(){
				jfyfkzh_zhye.bindData();
			});
		}
	}
});