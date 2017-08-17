reqConfig.setLocalPath("mail");
reqConfig.setLocalPath("common");

require.config(reqConfig);

define(['GY',  'commLan'], function(GY) {
	//var reg = new RegExp("(^|&)param=([^&]*)(&|$)");
    //var param = window.location.search.substr(1).match(reg);
    //$.post("http://192.168.41.171:8083/hsxt-access-web-person/cardHolder/checkEmailCode",{param:param[2]},function(result){
    //    alert(result);
    //});
	var params = location.href.split('param=');
	comm.Request({domain:'companyWeb',url:'checkMailCode'},{
		type:'POST',
		data: {param: params[1] },
		dataType:'json',
		success:function(response){
			if(response.retCode=='22003'){
				//更新缓存数据
				var datas = comm.getCache("companyInfo", "entAllInfo");
				datas.asEntStatusInfo.isAuthEmail=1;
				comm.setCache("companyInfo", "entAllInfo", datas);
				comm.alert(response.resultDesc);
			}else{
				if('160136' == response.retCode){
					comm.alert('邮件已过期');
				}else{
					comm.alert(response.data);
				}
			}
		},
		error: function(e){
			comm.alert(e);
		}
		
	})
});
