define(function(){
	var ajaxRequest = {
		searchCompanyCoupon : function(param,callback){
			comm.Request({url:'searchCompanyCoupon',domain:'scs'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('企业查询抵扣券请求数据出错！');
				}
			});
		},
		getShopCouponPartitionList : function(shopCouponId,callback){
			comm.Request({url:'getShopCouponPartitionList',domain:'scs'},{
				type:'POST',
				data:{"shopCouponId":shopCouponId},
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('根据商城抵扣卷id查询金额分段设置请求数据出错！');
				}
			});
		}
	}
	return ajaxRequest;
});