define(['text!resouceManageTpl/qyzygl/qyxx/qyxx_tab.html',
		'resouceManageSrc/qyzygl/qyxx/qyxtzcxx',
		'resouceManageSrc/qyzygl/qyxx/qygsdjxx',
		'resouceManageSrc/qyzygl/qyxx/qylxxx',
		'resouceManageSrc/qyzygl/qyxx/qyyhzhxx',
		'resouceManageSrc/qyzygl/qyxx/qysczl',
		"resouceManageDat/qyzygl/qyzygl"
		],function(tab, qyxtzcxx, qygsdjxx, qylxxx, qyyhzhxx,qysczl,qyzygl){
	return {	 
		showPage : function(obj){		
			$('#ckqyxxTpl').html(_.template(tab)) ;
			
			/*企业系统注册信息*/
			$('#qyxtzcxx').click(function(e) {
                qyxtzcxx.showPage(obj);
				comm.liActive($('#qyxtzcxx'));
            }.bind(this)).click();
			
			/*企业工商登记信息*/
			$('#qygsdjxx').click(function(e) {
                qygsdjxx.showPage(obj);
				comm.liActive($('#qygsdjxx'));
            }.bind(this)); 
			
			/*企业联系信息*/
			$('#qylxxx').click(function(e) {
                qylxxx.showPage(obj);
				comm.liActive($('#qylxxx'));
            }.bind(this)); 
			
			/*企业银行账户信息*/
			$('#qyyhzhxx').click(function(e) {
                qyyhzhxx.showPage(obj);
				comm.liActive($('#qyyhzhxx'));
            }.bind(this)); 

	        /*企业上传资料*/
			$('#qysczl').click(function(e) {
                qysczl.showPage(obj);
				comm.liActive($('#qysczl'));
            }.bind(this)); 
		},
		/** 获取企业明细 */
		getEntDetail:function(entCustId,entType,callBack){
			var param={"operateEntCustId":entCustId}; //查询参数
			//获取企业信息
			qyzygl.getEntAllInfo(param,function(rsp){
				var entDetail=rsp.data; //企业信息(不包含银行卡信息)
				//获取企业银行卡
				qyzygl.getEntBankList(param,function(rsp2){
					var bankDetail=rsp2.data;
					//平台信息货币
					cacheUtil.findCacheSystemInfo(function(data){
						callBack({"entDetail":entDetail,"bankDetail":bankDetail,"entType":entType,"currencyName":data.currencyNameCn});
					});
					
				});
			});
		}
	}
}); 

