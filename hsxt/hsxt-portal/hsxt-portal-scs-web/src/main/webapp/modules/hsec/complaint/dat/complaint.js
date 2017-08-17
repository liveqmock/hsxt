define(function() {
	var ajaxRequest = {
		//查询投诉与举报
		getComplainByParam : function(param,callback){
			comm.Request({url:'getComplainByParam',domain:'scs'},{
				type:'POST',
				dataType:"json",
				data:param,
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				}
				
			})	
		},//服务公司处理投诉
		processComplaints : function(param, callback){
			comm.Request({url:'processComplaints',domain:'scs'}, {
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('服务公司处理投诉异常！');
				}
			});
		},
		//根据类型查询数据字典
		getDictionarysByType : function(callback){
			comm.Request({url:'/saleSupport/getDictionarysByType',domain:'scs'}, {
				type : 'POST',
				data : null,
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('根据类型查询数据字典异常！');
				}
			});
		},
		//查询投诉/举报详情
		getComplainDetailById : function(complainId,callback){
			comm.Request({url:'getComplainDetailById',domain:'scs'},{
				type : 'POST',
				data : {"complainId":complainId},
				dataType : "json",
				success : function(response){
					callback(response);
				},
				error : function(){
					alert("查询投诉/举报详情error");
				}
			});
		}
	};
	return ajaxRequest;
});