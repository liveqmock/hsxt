define(function(){
	var ajaxRequest ={
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
			//审核商品
			itemCheck:function(param,callback){
				comm.Request({url:'itemCheck',domain:'scs'},{
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
			//审核记录数据
			itemCheckHistory:function(param,callback){
				comm.Request({url:'itemCheckHistory',domain:'scs'},{
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
			//批量审核商品
			bitchItemCheck:function(param,callback){
				comm.Request({url:'bitchItemCheck',domain:'scs'},{
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
			//查询类目下拉框数据
			getCategorySelectData:function(param,callback){
				comm.Request({url:'getCategorySelectData',domain:'scs'},{
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
			//查询商品表格数据
			searchItemList:function(param,callback){
				comm.Request({url:'searchItemList',domain:'scs'},{
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
			//查询举报列表
			getComplainsByService:function(param,callback){
				comm.Request({url:'getComplainsByService',domain:'scs'},{
					type:'POST',
					data:{"complain":param},
					dataType:"json",
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},
			//处理举报
			processComplaints:function(param,callback){
				comm.Request({url:'processComplaints',domain:'scs'},{
					type:'POST',
					data:{"complain":param},
					dataType:"json",
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},
			searchComplainItemList:function(param,callback){
				comm.Request({url:'searchComplainItemList',domain:'scs'},{
					type:'POST',
					data:{"complain":param},
					dataType:"json",
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},
			soldOut:function(itemId,virtualShopId,remark,complainID,callback){
				comm.Request({url:'soldOut',domain:'scs'},{
					type:'POST',
					data:{"itemId":itemId,"virtualShopId":virtualShopId,"remark":remark,"complainID":complainID},
					dataType:"json",
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},
			itemPutOn:function(itemId,virtualShopId,callback){
				comm.Request({url:'itemPutOn',domain:'scs'},{
					type:'POST',
					data:{"itemId":itemId,"virtualShopId":virtualShopId},
					dataType:"json",
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},
			itemPutOff:function(itemId,virtualShopId,callback){
				comm.Request({url:'itemPutOff',domain:'scs'},{
					type:'POST',
					data:{"itemId":itemId,"virtualShopId":virtualShopId},
					dataType:"json",
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			}
			/**********************************餐饮商品开始********************************/
			,
			/**
			 * 查询商品信息（餐饮）
			 */
			listItemInfoFood : function(param,callback){
				comm.Request({url:'listItemInfoFood',domain:'scs'},{
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
			 * 删除商品（餐饮）
			 */
			deleteItemInfoFood : function(param,callback){
				comm.Request({url:'deleteItemInfoFood',domain:'scs'},{
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
			 * 批量删除商品（餐饮）
			 */
			deleteItemFoodByIds : function(param,callback){
				comm.Request({url:'deleteItemFoodByIds',domain:'scs'},{
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
			/** 查询类目 */
			queryCategory:function(param,callback){
				comm.Request({url:'queryCategory',domain:'scs'},{
					type:'POST',
					data:param,
					dataType:"json",
					success:function(response){
						callback(response)	
					},
					error: function(){
						//alert('请求数据出错！');
					}
				});
			},
			/** 上架 */
			itemPutOnFood : function(param,callback){
				comm.Request({url:'itemPutOnFood',domain:'scs'},{
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
			/** 下架 */
			itemPutOffFood : function(param,callback){
				comm.Request({url:'itemPutOffFood',domain:'scs'},{
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
			/** 批量上架 */
			batchItemPutOn : function(param,callback){
				comm.Request({url:'batchItemPutOn',domain:'scs'},{
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
			/** 批量下架 */
			batchItemPutOff : function(param,callback){
				comm.Request({url:'batchItemPutOff',domain:'scs'},{
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
})