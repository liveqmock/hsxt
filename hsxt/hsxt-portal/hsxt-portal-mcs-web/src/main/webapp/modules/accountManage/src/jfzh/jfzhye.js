define(['text!accountManageTpl/jfzh/jfzhye.html',
        "accountManageDat/jfzh/jfzh",
        "accountManageLan"
        ],function(jfzhyeTpl,jfzh){
	return {
		showPage : function(){
			var self=this;
			$('#busibox').html(_.template(jfzhyeTpl)) ;
			
			//加载余额
			self.getIntergral();
			//刷新余额
			$("#jfzhye_sx").click(function(){
				self.getIntergral();
			});
		   	
		  
		},
		/** 获取积分余额 */
		getIntergral:function(){
			//获取积分余额
			jfzh.findIntegralBalance(function(rsp){
				var balance=rsp.data;
				var title; //积分标题
				//平台所在区域
				switch (balance.countryNo) {
				case "156":
					title="中国大陆平台积分总数"; 
					break;
				case "344":
					title="中国香港平台积分总数";   
					break;
				case "446":
					title="中国澳门平台积分总数";
					break;
				}
				
				$("#sSecurityPointNumber").html(balance.securityPointNumber); //保底积分
				$("#tUsablePointNum").html(balance.usablePointNum); //可用积分
				$("#tYesterdayPoint").html(balance.yesterdayNewPoint); //昨日积分
				$("#hPointTitle").html(title); //所在平台
			});
		}
	}
}); 

 