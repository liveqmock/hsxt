define(['text!infoManageTpl/zyzhgl/xhzxxxx_yhk.html',
        'infoManageDat/infoManage',
		'infoManageLan' 
], function(xhzxxxx_yhkTpl, dataModoule){
	return {
		showPage : function(num, custID){
			this.initData(custID, num);
		},
		/**
		 * 初始化界面
		 */
		initForm : function(record, num){
			record = !record?new Array():record;
			for(var key = 0;key<record.length;key++){
				var bankAccNo = record[key].bankAccNo;
				bankAccNo = bankAccNo.substring(bankAccNo.length-4, bankAccNo.length)
				record[key].bankAccNo = bankAccNo;
			}
			if('xfz' == num){
				$('#consumerBox').html(_.template(xhzxxxx_yhkTpl, {record:record, num:num}));
				$('#back_xfzzy').click(function(){
					comm.goBackListPage("busibox2","busibox",$('#xfzzy'),'#ckqyxxxx, #ck');
				});
			}else{
				$('#infobox').html(_.template(xhzxxxx_kjkTpl, {record:data, num:num}));
				$('#back_tgqyzy').triggerWith('#tgqyzy');
				$('#back_cyqyzy').triggerWith('#cyqyzy');
				$('#back_fwgszy').triggerWith('#fwgszy');
			}
			
		},
		/**
		 * 初始化数据
		 */
		initData : function(custID, num){
			var self = this;
			if('xfz' == num){
				dataModoule.listConsumerBanks({'custID':custID},function(res){
					var record = res.data;
					if(record==undefined){
						record= new Array();
					}
					self.initForm(record, num);

		//				var bankAccNo = record[key].bankAccNo;
		//				bankAccNo = bankAccNo.substring(bankAccNo.length-4, bankAccNo.length)
		//				record[key].bankAccNo = bankAccNo;

				});
				
			}else{
				var entAllInfo = comm.getCache("infoManage", "entAllInfo");
				if(entAllInfo == null){
					dataModoule.resourceFindEntAllInfo({custID:custID}, function(res){
						comm.setCache("infoManage", "entAllInfo", res.data);
						self.initForm(res.data.payInfo, num);
					});
				}else{
					self.initForm(entAllInfo.payInfo, num);
				}
			}
		}
	}	
});