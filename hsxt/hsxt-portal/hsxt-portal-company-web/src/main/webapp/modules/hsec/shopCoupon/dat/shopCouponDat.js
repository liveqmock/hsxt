define(function(){
	var ajaxRequest = {
			/*查询抵扣劵列表*/
			listCouponShop : function(param,callback){
				comm.Request({url:'/shopcoupon/listCouponShop',domain:'sportal'},{
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
			/*查看抵扣劵关联的实体店*/
			listshop : function(param,callback){
				comm.Request({url:'/shopcoupon/listShop',domain:'sportal'},{
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
			/*更新抵扣劵关联的实体店*/
			updateShopCoupon : function(param,callback){
				comm.Request({url:'/shopcoupon/updateShopCoupon',domain:'sportal'},{
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
			/*取消关联抵扣劵*/
		  couponUnRelateVshop : function(param,callback){
				comm.Request({url:'/shopcoupon/couponUnRelateVshop',domain:'sportal'},{
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
			/*关联抵扣劵*/
		  couponRelateVshop : function(param,callback){
				comm.Request({url:'/shopcoupon/couponRelateVshop',domain:'sportal'},{
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
			/*更新抵扣劵使用条件*/
		  updateCounponBylstCouponId : function(param,callback){
				comm.Request({url:'/shopcoupon/updateCounponBylstCouponId',domain:'sportal'},{
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
		  /**
		   * 查询商城抵扣券规则
		   */	
		  listShopCouponPartition : function(param,callback){
				comm.Request({url:'/shopcoupon/listShopCouponPartition',domain:'sportal'},{
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
		  
		  addCouponRule : function(param,callback){
				comm.Request({url:'/shopcoupon/addCouponRule',domain:'sportal'},{
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
		  
		  //删除抵扣券 David 2016-1-11
		  delCouponRule : function(param,callback){
				comm.Request({url:'/shopcoupon/delCouponRule',domain:'sportal'},{
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
		  
		  listCoupon : function(param,callback){
				comm.Request({url:'/shopcoupon/listCoupon',domain:'sportal'},{
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
		  }
		    
	}
	return ajaxRequest;
});