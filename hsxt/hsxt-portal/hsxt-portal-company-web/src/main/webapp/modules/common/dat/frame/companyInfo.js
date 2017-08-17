define(function(){
	var ajaxRequest = {
			//获得参数列表
			getParameters : function (paraData){
				//json用户身份参数构造
				var jsonParam= comm.getRequestParams();
				
				//传递过来的参数继承JsonParam
				if(typeof(paraData) != "undefined" && paraData != null )
				{
					 return $.extend(jsonParam,paraData);
				}
				else 
				{
					return jsonParam;
				}
			},	
			/**
			 * 获取企业信息
			 */
			getCompanyInfo : function(param,callback){
			/*comm.Request({url:'/commController/getCompanyInfo',domain:'companyWeb'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});*/
			},
			/**
			 * 获取网上商城信息
			 */
			getVirtualShop : function(param,callback){
				comm.Request({url:'/companyLogin/getVirtualShop',domain:'sportal'},{
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
			 * 获取营业点信息
			 */
			shopFoodInfoHuDong : function(param,callback){
				comm.Request({url:'/salerShopFood/shopFoodInfoHuDong',domain:'sportal'},{
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
			 * 根据enKey返回一级菜单
			 */
			appGetUserResult : function(param,callback){
				comm.Request({url:'/commController/appGetUserResult',domain:'companyWeb'},{
					type:'POST',
					data:comm.getRequestParams(),
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
			 * 从session中获取一级菜单
			 */
			getListMenu : function(param,callback){
				comm.Request({url:'/commController/getListMenu',domain:'companyWeb'},{
					type:'POST',
					data:comm.getRequestParams(),
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
			 * 用户退出系统
			 */
			companyWebLogout : function(param,callback){
				comm.Request({url:'/companyLogin/companyWebLogout',domain:'sportal'},{
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
			 * 查询是否设置过交易密码   未使用，先注释 modify by zhucy 2016-03-01
			dealPswQuery:function(callback){
				comm.Request('dealPswQuery',{
				type:'POST',	
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				}
				
			})	}*/
	
			/**
			 * 根据当前登录账号和企业资源号，获取当前用户实体对象
			 */
			getEmployeeAccount : function(param,callback){
				comm.Request({url:'/companyLogin/getEmployeeAccount',domain:'sportal'},{
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
			 * 获取网上商城图片信息 add by zhanghh date:2016-04-16
			 */
		 getMarketInfo : function(param,callback){
			//获取企业信息
			comm.Request({url:'/eShop/initMarketInfo',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('获取网上商城图片信息出错！');
				}
			});
		}
	};	
	return ajaxRequest;
});