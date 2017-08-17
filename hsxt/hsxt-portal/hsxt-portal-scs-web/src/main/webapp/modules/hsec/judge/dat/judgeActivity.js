define(function(){
	var ajaxRequest = {
		//查询评价信息
		findjudges : function(param, callback) {
			comm.Request({url:'findJudges',domain:'scs'}, {
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('查询评价信息数据出错！');
				}
			});
		},
		//删除评价信息
		deleteJudge : function(param, callback){
			comm.Request({url:'deleteJudge',domain:'scs'}, {
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('删除评价信息数据出错！');
				}
			});
		},
		//删除追评信息
		deleteAppendComment : function(param, callback){
			comm.Request({url:'deleteAppendComment',domain:'scs'}, {
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('删除追评信息数据出错！');
				}
			});
		},
		//查询实体店信息
		findSalerShops : function(param, callback){
			comm.Request({url:'findSalerShops',domain:'scs'}, {
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('查询实体店信息数据出错！');
				}
			});
		}
		
	}
	return ajaxRequest;
})