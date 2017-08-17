define(['text!invoiceTpl/qyxx/yhzhxx.html',
        'invoiceDat/invoice',
		'invoiceLan'], function(yhzhxxTpl,dataModoule){
	return {
		showPage : function(){
			dataModoule.resourceFindEntAllInfo({custID:$("#customId").val()}, function(res){
				var data = !res.data.bakInfo?new Array():res.data.bakInfo;
				var temp_1 = [];//默认
				var temp_2 = [];//普通
				for(var key=0;key<data.length;key++){
					
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
				$('#infobox').html(_.template(yhzhxxTpl, {record:temp_1}));
			
			});		
		}	
	}	
});