/**
 * 互动及企业通讯录信息ajax
 * 注释：zhanghh 2016-01-18
 */
define(function(){
	var ajaxRequest = {
			/**
			 * 获取企业信息
			 */
			getCompanyContacts : function(callback){
					comm.Request({url:'/companyLogin/getCompanyContacts',domain:'sportal'},{
					type:'POST',
					success:function(response){
						callback(response)	
					},
					error: function(){
						alert('请求数据出错！');
					}
				})	
			},
			/**
			 * 查询关键字
			 */
			isContainSensitiveWord : function(param,callback){
				comm.Request({url:'/companyLogin/isContainSensitiveWord',domain:'sportal'},{
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