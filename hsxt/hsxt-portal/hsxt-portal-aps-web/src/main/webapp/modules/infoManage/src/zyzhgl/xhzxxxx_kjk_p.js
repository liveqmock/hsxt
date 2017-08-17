define(['text!infoManageTpl/zyzhgl/xhzxxxx_kjk_p.html' ,
        'infoManageDat/infoManage',
		'infoManageLan'
], function(xhzxxxx_kjkTpl, dataModoule){
	return {
		showPage : function(obj){
			this.initData(obj);
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data){
			data = !data?new Array():data;
			//for(var key in data){
			for(var key = 0;key<data.length;key++){
				var bankCardNo = data[key].bankCardNo;
				bankCardNo = bankCardNo.substring(bankCardNo.length-4, bankCardNo.length)
				data[key].bankCardNo = bankCardNo;
				//data[key].bankCardNo = comm.hideCard(data[key].bankCardNo);
			}
			$('#consumerBox').html(_.template(xhzxxxx_kjkTpl, {record:data}));
			$('#back_xfzzy').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#xfzzy'),'#ckqyxxxx, #ck');
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(obj){
			var self = this;
			dataModoule.queryConsumerQkBanks({custID:obj.baseInfo.perCustId}, function(res){
				self.initForm(res.data);
			});
		}
	}	
});