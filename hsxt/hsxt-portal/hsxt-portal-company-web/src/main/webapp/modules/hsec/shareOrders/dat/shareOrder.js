define(function() {
	var ajaxRequest = {
		/** 根据商城ID查询实体店 */
		listSalerShopByVShopId : function(param, callback) {
			comm.Request({url:'/shareOrder/listSalerShopByVShopId',domain:'sportal'}, {
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response) {
					callback(response)
				},
				error : function() {
					alert('请求数据出错！');
				}
			});
		},
		/** 根据查询条件查询晒单信息*/
		listShareOrderByParam : function(param, callback) {
			comm.Request({url:'/shareOrder/listShareOrderByParam',domain:'sportal'}, {
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response) {
					callback(response)
				},
				error : function() {
					alert('请求数据出错！');
				}
			});
		}
	};
	return ajaxRequest;
});