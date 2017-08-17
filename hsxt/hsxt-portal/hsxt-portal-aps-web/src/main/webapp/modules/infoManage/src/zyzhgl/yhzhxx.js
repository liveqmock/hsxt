define(['text!infoManageTpl/zyzhgl/yhzhxx.html',
        'infoManageDat/infoManage',
		'infoManageLan'], function(yhzhxxTpl, dataModoule){
	return {
		showPage : function(num, custID){
			this.initData(custID, num);
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data, num){
			data = !data?new Array():data;
			var temp_1 = [];//默认
			var temp_2 = [];//普通
			//for(var key in data){
			for(var key = 0;key<data.length;key++){
				var bankAccNo = data[key].bankAccNo;
				bankAccNo = bankAccNo.substring(bankAccNo.length-4, bankAccNo.length)
				data[key].bankAccNo = bankAccNo;
				
				if(data[key].isDefaultAccount == 1){
					temp_1.push(data[key]);
				}else{
					temp_2.push(data[key]);
				}
			}
			temp_1 = temp_1.concat(temp_2);
			$('#infobox').html(_.template(yhzhxxTpl, {record:temp_1, num : num}));
			$('#back_tgqyzy').click(function(){
				comm.goBackListPage("ent_list","busibox",$('#tgqyzy'),'#ckqyxxxx, #ck');
			});
			$('#back_cyqyzy').click(function(){
				comm.goBackListPage("ent_list","busibox",$('#cyqyzy'),'#ckqyxxxx, #ck');
			});
			$('#back_fwgszy').click(function(){
				comm.goBackListPage("ent_list","busibox",$('#fwgszy'),'#ckqyxxxx, #ck');
				
			});
			//联系人授权委托书
			if(data && data.contactProxy){
				comm.initPicPreview("#img", data.contactProxy, "");
				$("#img").attr("src", comm.getFsServerUrl(data.contactProxy));
			}
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
					self.initForm(res.data.bakInfo, num);
				});
			}else{
				self.initForm(entAllInfo.bakInfo, num);
			}
		}
	}	
});