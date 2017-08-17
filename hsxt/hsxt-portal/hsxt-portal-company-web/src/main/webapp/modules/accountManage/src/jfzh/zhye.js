define(['text!accountManageTpl/jfzh/zhye.html',
        'accountManageDat/accountManage'
        ],function(tpl,accountManage){
	return zfzh_zhye = {
		showPage : function(){
			this.bindData();
		},
		bindData : function(){
			//获取randomToken
			comm.getRandomToken(function(response){
				//查询积分账户余额
				accountManage.findzhye({randomToken:response.data},function(res){
					var response = {
							baodi:comm.formatMoneyNumber(res.data.securityPointNumber),
							pointAcct: comm.formatMoneyNumber(res.data.pointBlance),
							usablePointNum: comm.formatMoneyNumber(res.data.usablePointNum),
							yesterdayNewPoint: res.data.yesterdayNewPoint
					};
					$('#busibox').html(_.template(tpl, response));
					//刷新按钮单击事件
					$('#pointFlush').click(function(){
						zfzh_zhye.bindData();
					});
				});
			});
			
		}
			
	}
});