define(function () {
	
	return {
		
		/***
		 * 参可为空，返回的是基本参数 ，
		 * 
		 * 参数格式必须为键值列表,如:{k1:v1,k2:v2}
		 */
		wrapParam : function (paraData){
			//测试cookie读取操作
			$.cookie('cookie_custId', 'cookie_custId');
			$.cookie('cookie_pointNo', 'cookie_pointNo');
			$.cookie('cookie_token', 'cookie_token');
			$.cookie('cookie_entCustId', 'cookie_entCustId');
			$.cookie('cookie_entResNo', 'cookie_entResNo');
			//读取
			//var custId = $.cookie('cookie_custId'); // 读取 cookie 客户号
			//var hsNo = $.cookie('cookie_pointNo'); // 读取 cookie pointNo
			//var token = $.cookie('cookie_token'); // 读取 cookie pointNo
			
			var custId = 'custId_p25_2015_10_16'; 	// 读取 cookie 客户号
			var hsNo ='custId_p25_2015_10_16'; 	// 读取 cookie pointNo
			var token ='token_custId_p25_2015_10_16';
			var entCustId ='token_entCustId_p25_2015_10_16';
			var entResNo ='token_entResNo_p25_2015_10_16';
			var param={
					custId  : custId,
					hsNo    : hsNo,
					token   : token,
					entCustId   : entCustId,
					entResNo   : entResNo
			};
			
			if(typeof(paraData) != undefined&&paraData!=""&&paraData!=null){
				 return $.extend(param,paraData);
			}else {
				return param;
			}
		},
		
		//获得服务器地址getFsServerUrl
		getFsServerUrl : function(pictureId) {
			var custId = 'custId_p25_2015_10_16'; 	// 读取 cookie 客户号
			var hsNo ='custId_p25_2015_10_16'; 	// 读取 cookie pointNo
			var token ='token_custId_p25_2015_10_16';
			
			return  comm.domainList['fsServerUrl']+pictureId+"?userId="+custId+"&token="+token;
		},
		
		//弹出异常信息
		errorInfoPop : function(errorCode){
			//通过错误码获得提示信息 
			comm.alert({
				content: "错误码"+errorCode,
				imgClass: 'tips_error'
			});
		},
		//查找列表 
		findOperatorList : function (data,callback){
			var that=this;
			comm.Request({url : 'findOperatorList', domain : 'companyWeb'},{
				data : that.wrapParam(data),
				success :function(response){
					if(response.retCode==22000){
						callback(response.data);
					}else if(response.retCode==22001){
						that.errorInfoPop(response.retCode);
					}else {
						that.errorInfoPop(response.retCode);
					}
				}
			});
	   },
	   
	   //新增操作员
	   addOperator : function (data,callback){
		   var that=this;
			comm.Request({url : 'addOperator', domain : 'companyWeb'},{
				data : that.wrapParam(data),
				success :function(response){
					if(response.retCode==22000){
						callback(response.data);
					}else if(response.retCode==22001){
						that.errorInfoPop(response.retCode);
					}else {
						that.errorInfoPop(response.retCode);
					}
				}
			});
	   },
	   //删除操作员
	   delOperator : function(data,callback){
		   var that=this;
			comm.Request({url : 'delOperator', domain : 'companyWeb'},{
				data : that.wrapParam(data),
				success :function(response){
					if(response.retCode==22000){
						callback(response.data);
					}else if(response.retCode==22001){
						that.errorInfoPop(response.retCode);
					}else {
						that.errorInfoPop(response.retCode);
					}
				}
			});
	   },
	   
	   //更新操作员
	   updataOperatorInfo : function(data,callback){
		   var that=this;
		   comm.Request({url : 'updataOperatorInfo', domain : 'companyWeb'},{
			   data : that.wrapParam(data),
			   success :function(response){
				   if(response.retCode==22000){
					   callback(response.data);
				   }else if(response.retCode==22001){
					   that.errorInfoPop(response.retCode);
				   }else {
					   that.errorInfoPop(response.retCode);
				   }
			   }
		   });
	   },
	   
	   //查找角色列表 
	   findRoleList : function(data,callback){
		   var that=this;
			comm.Request({url : 'findRoleList', domain : 'companyWeb'},{
				data : that.wrapParam(data),
				success :function(response){
					if(response.retCode==22000){
						callback(response.data);
					}else if(response.retCode==22001){
						that.errorInfoPop(response.retCode);
					}else {
						that.errorInfoPop(response.retCode);
					}
				}
			});
	   },
	   //解除绑定的互生卡 
	   removeHsCard : function(data,callback){
		   var that=this;
		   comm.Request({url : 'removeHsCard', domain : 'companyWeb'},{
			   data : that.wrapParam(data),
			   success :function(response){
				   if(response.retCode==22000){
					   callback(response.data);
				   }else if(response.retCode==22001){
					   that.errorInfoPop(response.retCode);
				   }else {
					   that.errorInfoPop(response.retCode);
				   }
			   }
		   });
	   },
	   
	   //绑定互生卡
	   bindHsCard : function(data,callback){
		   var that=this;
		   comm.Request({url : 'bindHsCard', domain : 'companyWeb'},{
			   data : that.wrapParam(data),
			   success :function(response){
				   if(response.retCode==22000){
					   callback(response.data);
				   }else if(response.retCode==22001){
					   that.errorInfoPop(response.retCode);
				   }else {
					   that.errorInfoPop(response.retCode);
				   }
			   }
		   });
	   },
	   //操作员变更，（增加，修改，设置角色）
	   operChange : function(data,callback){
		   var that=this;
		   comm.Request({url : 'operChange', domain : 'companyWeb'},{
			   data : that.wrapParam(data),
			   success :function(response){
				   if(response.retCode==22000){
					   callback(response.data);
				   }else if(response.retCode==22001){
					   that.errorInfoPop(response.retCode);
				   }else {
					   that.errorInfoPop(response.retCode);
				   }
			   }
		   });
	   }
	};
});