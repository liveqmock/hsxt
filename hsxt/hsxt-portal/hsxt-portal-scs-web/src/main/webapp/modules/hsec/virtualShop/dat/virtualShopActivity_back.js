define(function(){
	var ajaxRequest = {
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
		//行政区选择
		selectArea : function(param, callback){
			comm.Request({url:'selectArea',domain:'scs'}, {
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('查询行政区信息出错！');
				}
			});
		},
		//网上商城开启
		openVs : function(param, callback){
			comm.Request({url:'openVirtualShop',domain:'scs'}, {
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('查询行政区信息出错！');
				}
			});
		},
		//网上商城暂停
		stopVs : function(param, callback){
			comm.Request({url:'stopVirtualShop',domain:'scs'}, {
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('查询行政区信息出错！');
				}
			});
		},
		//清除网上商城logo
		cleanVsLogo : function(param, callback){
			comm.Request({url:'cleanVsLogo',domain:'scs'}, {
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('查询行政区信息出错！');
				}
			});
		},
		////////////////////////////////////////////////////////////////
		editVirtualShop : function(param, callback){
			comm.Request({url:'editVirtualShop',domain:'scs'}, {
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('根据ID获取信息出错！');
				}
			});
		},
		//根据ID获取网上商城单条记录
		getVirtualShopById : function(param, callback) {
			comm.Request({url:'getVirtualShopById',domain:'scs'}, {
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('根据ID获取网上商城单条记录出错！');
				}
			});
		},
		//根据ID获取网上商城单条记录
		getVirtualShopByIdNew : function(virtualShopId, callback) {
			comm.Request({url:'getVirtualShopById',domain:'scs'}, {
				type : 'POST',
				data : {"virtualShopId":virtualShopId},
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('根据ID获取网上商城单条记录出错！');
				}
			});
		}
		//////////////////////////////////////////////////////////////////
	}
	return ajaxRequest;
})