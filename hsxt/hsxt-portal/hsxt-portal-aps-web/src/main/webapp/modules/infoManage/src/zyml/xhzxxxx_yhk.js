define(['text!infoManageTpl/zyml/xhzxxxx_yhk.html',
        'infoManageDat/infoManage'
], function(xhzxxxx_yhkTpl,infoManage){
	return {
		showPage : function(obj){
			infoManage.queryConsumerBanks({'custID':obj.baseInfo.perCustId},function(res){
				var record = res.data;
				if(record==undefined){
					record= new Array();
				}
				
				for(var key=0; key<record.length;key++){
					//data[key].bankAccNo = comm.hideCard(data[key].bankAccNo);
					var bankAccNo = record[key].bankAccNo;
					bankAccNo = bankAccNo.substring(bankAccNo.length-4, bankAccNo.length)
					record[key].bankAccNo = bankAccNo;
					/*
					if(record[key].isDefaultAccount == 1){
						temp_1.push(record[key]);
					}else{
						temp_2.push(record[key]);
					}*/
				}
				
				$('#infobox').html(_.template(xhzxxxx_yhkTpl ,{record:record}));
				//back
				$('#back_xfzzyyl').click(function(){
					comm.goBackListPage("busibox2","busibox",$('#xfzzyyl'),'#ckqyxxxx, #ck');
				});
			});
		}	
	}	
});