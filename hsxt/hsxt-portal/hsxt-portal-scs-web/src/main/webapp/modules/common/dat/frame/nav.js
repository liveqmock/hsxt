define(function() {
	var ajaxRequest = {
		loadMenu : function(callback) {
			comm.Request('loadMenu', {
				type : 'post',
				dataType : 'json',
				success : function(response) {
					callback(response);
				},
				error : function() {
					alert('请求数据出错！');
				}
			});
		},
		loadMenus : function(callback,param) {
			if(!param)param={}
			comm.Request('loadMenus', {
				type : 'post',
//				async : false,
				data : param ,
				success : function(response) {
					callback(response);
				},
				error : function() {
					alert('请求数据中，请勿频繁点击!');
				}
			});
		},
		companyInfo : function(callback,param) {
			if(!param)param={}
			comm.Request('companyInfo', {
				type : 'post',
				data : param ,
				success : function(response) {
					callback(response);
				},
				error : function() {
					alert('请求数据出错！');
				}
			});
		},
		//商品管理页面
		getItemManager:function(param,callback){
			comm.Request({url:'getItemManager',domain:'scs'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		},
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
				
			});
		},
		//查询商铺信息
		findVirtualShops : function(param, callback) {
			comm.Request({url:'findVirtualShops',domain:'scs'}, {
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('查询商铺信息出错！');
				}
			});
		},
		hasConfirmProtocol : function(param,callback){
			comm.Request({url:'hasConfirmProtocol',domain:'scs'}, {
				type : 'get',
				data : param ,
//				async : false,
				success : function(response) {
					callback(response);
				},
				error : function() {
					alert('请求数据出错！');
				}
			});
		},
		confirmProtocol : function(param,callback){
			comm.Request({url:'confirmProtocol',domain:'scs'}, {
				type : 'get',
				data : param ,
//				async : false,
				success : function(response) {
					callback(response);
				},
				error : function() {
					alert('请求数据出错！');
				}
			});
		},
		//服务公司协议内容
		companyServiceContent : function(id,callback){
			comm.Request({url:'companyServiceContent',domain:'scs'}, {
				type : 'POST',
				data : {"id":id} ,
				success : function(response) {
					callback(response);
				},
				error : function() {
					alert('请求服务公司协议内容出错！');
				}
			});
		}
	};
	return ajaxRequest;
});