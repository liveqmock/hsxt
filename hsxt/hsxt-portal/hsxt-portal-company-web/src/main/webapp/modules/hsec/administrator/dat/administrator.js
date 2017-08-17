define(function(){
	var ajaxRequest = {
			getEmployeeaccounList : function(param,callback){
				comm.Request({url:'/employee/queryEmployeeaccountList',domain:'sportal'},{
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
			},disableEmployeeaccoun : function(param,callback){
				comm.Request({url:'/employee/disableEmployee',domain:'sportal'},{
					type:'POST',
					dataType:"json",
					data:param,
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},startEmployeeaccounList : function(param,callback){
				comm.Request({url:'/employee/startEmployee',domain:'sportal'},{
					type:'POST',
					dataType:"json",
					data:param,
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},deleteEmployeeaccounList : function(param,callback){
				comm.Request({url:'/employee/deleteEmployee',domain:'sportal'},{
					type:'POST',
					dataType:"json",
					data:param,
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},getSalerShop : function(callback){
				comm.Request({url:'/employee/getSalerShop',domain:'sportal'},{
					type:'POST',
					dataType:"json",
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},bindSalerShop : function(param,callback){
				comm.Request({url:'/employee/bindSalerShop',domain:'sportal'},{
					type:'POST',
					dataType:"json",
					data:param,
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},getEmployeeaccount : function(param,callback){
				comm.Request({url:'/employee/getEmployeeaccount',domain:'sportal'},{
					type:'POST',
					dataType:"json",
					data:param,
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},updateEmployeeaccount : function(param,callback){
				comm.Request({url:'/employee/updateEmployeeaccount',domain:'sportal'},{
					type:'POST',
					dataType:"json",
					data:param,
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},updateEmployeeNetworkInfo : function(param,callback){
				comm.Request({url:'/employee/updateEmployeeNetworkInfo',domain:'sportal'},{
					type:'POST',
					dataType:"json",
					data:param,
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},addEmployeeNetworkInfo : function(param,callback){
				comm.Request({url:'/employee/addEmployeeNetworkInfo',domain:'sportal'},{
					type:'POST',
					dataType:"json",
					data:param,
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},addEmployeeaccount : function(param,callback){
				comm.Request({url:'/employee/addEmployeeaccount',domain:'sportal'},{
					type:'POST',
					dataType:"json",
					data:param,
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},bindResourceNo : function(param,callback){
				comm.Request({url:'/employee/bindResourceNo',domain:'sportal'},{
					type:'POST',
					dataType:"json",
					data:param,
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},ubindResourceNo : function(param,callback){
				comm.Request({url:'/employee/ubindResourceNo',domain:'sportal'},{
					type:'POST',
					dataType:"json",
					data:param,
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},resetAccountPwd : function(param,callback){
				comm.Request({url:'/employee/resetAccountPwd',domain:'sportal'},{
					type:'POST',
					dataType:"json",
					data:param,
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},getSalerShopCcount : function(param,callback){
				comm.Request({url:'/employee/getSalerShopCcount',domain:'sportal'},{
					type:'POST',
					dataType:"json",
					data:param,
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				});
			},
			/*获取企业下角色和默认角色列表接口*/
			getEnterperiseRoleList : function(param,callback){
				comm.Request({url:'/role/getEnterperiseRoleList',domain:'sportal'},{
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
			/*批量添加用户角色关系*/
			addUserRole : function(param,callback){
				comm.Request({url:'/role/addUserRole',domain:'sportal'},{
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
			/*根据用户ID查询角色列表*/
			queryRoleListByUserId : function(param,callback){
				comm.Request({url:'/role/queryRoleListByUserId',domain:'sportal'},{
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
	};	
	return ajaxRequest;
});