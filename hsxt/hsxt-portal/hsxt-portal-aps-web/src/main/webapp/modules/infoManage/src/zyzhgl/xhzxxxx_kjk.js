define(['text!infoManageTpl/zyzhgl/xhzxxxx_kjk.html',
        'infoManageDat/infoManage',
		'infoManageLan'  
], function(xhzxxxx_kjkTpl, dataModoule){
	return {
		showPage : function(num, custID){
			this.initData(custID, num);
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data, num){
			data = !data?new Array():data;
			//for(var key in data){
			for(var key = 0;key<data.length;key++){
				var bankCardNo = data[key].bankCardNo;
				bankCardNo = bankCardNo.substring(bankCardNo.length-4, bankCardNo.length)
				data[key].bankCardNo = bankCardNo;
				//data[key].bankCardNo = comm.hideCard(data[key].bankCardNo);
			}
			$('#infobox').html(_.template(xhzxxxx_kjkTpl, {record:data, num:num}));
			
			$('#back_tgqyzy').click(function(){
				comm.goBackListPage("ent_list","busibox",$('#tgqyzy'),'#ckqyxxxx, #ck');
			});
			$('#back_cyqyzy').click(function(){
				comm.goBackListPage("ent_list","busibox",$('#cyqyzy'),'#ckqyxxxx, #ck');
			});
			$('#back_fwgszy').click(function(){
				comm.goBackListPage("ent_list","busibox",$('#fwgszy'),'#ckqyxxxx, #ck');
				
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(custID, num){
			var self = this;
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
});