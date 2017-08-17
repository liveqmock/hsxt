define(function() {
	var ajaxRequest = {
			/**  获得服务公司评价列表 */
			getFoodItemCommentList : function(param, callback) {
				comm.Request({url:'/order/getFoodItemCommentList',domain:'scs'},{
					type : 'POST',
					data : param,
					dataType : "json",
					success : function(response) {
						callback(response)
					},
					error : function() {
						alert('请求企业商品评价列表出错！');
					}
				});
			},
			//根据网上商城ID查询评价列表接口
			getCommentsByShop : function(param, callback) {
				comm.Request({url:'/comment/getCommentsByShop',domain:'sportal'},{
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
			//回复评价接口
			replyBuyerComment : function(param, callback) {
				comm.Request({url:'/comment/replyBuyerComment',domain:'sportal'},{
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
	}
	return ajaxRequest;
});