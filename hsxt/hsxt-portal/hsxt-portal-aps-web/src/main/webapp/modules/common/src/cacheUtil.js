define(["commDat/common"], function (commData) {
	var countryCache=null;
	window.cacheUtil = {
			//获得参数列表
			getParameters : function (paraData){
				
				//json用户身份参数构造
				var jsonParam={
						custId 	:$.cookie('custId'),
						token 	:$.cookie('token')
				};
				
				//传递过来的参数继承JsonParam
				if(typeof(paraData) != "undefined"){
					 return $.extend(jsonParam,paraData);
				}else {
					return jsonParam;
				}
				
			},	
		/**
		 * 获取缓存中国家信息
		 * 如缓存中不存在查询后台方法放入到缓存中并返回
		 * @return 返回当前系统的国家信息
		 */
		findCacheContryAll : function (callback){
			//获取缓存中国家信息
			var contryList = comm.getCache("commCache","contryList");
			
			//非空验证
			if( contryList == null || contryList == 'null')
			{
				//获取用户登录身份参数
				var jsonParam = this.getParameters();
				
				//执行获取国家
				commData.findContryAll(jsonParam,function(response){
					
					//非空验证
					if(response.data)
					{
						//数据缓存
						comm.setCache("commCache","contryList",response.data);
						countryCache = response.data;
						callback(response.data);
					}else
					{
						comm.error_alert(comm.lang("common")[30018]);
					}
				});
			}
			else	//缓存中存在数据直接调用回调函数
			{
				//执行回调函数
				callback(contryList) ;
			}
			
		},
		getCountryNameByNo: function (countryNo){
			 $.each(countryCache,function(i,v) {
		         if(v.countryNo==countryNo){
		        	 countryName = v.countryNameCn;
		        	 return false;
		         }
		     });
			return countryName;
		},
		/**
		 * 很据国家获取缓存国家下面省份
		 * 如缓存中不存在查询后台方法放入到缓存中并返回
		 * @param countryNo 国家编码
		 * @pram  callback 回调函数
		 * @return 返回传入国家下省份
		 */
		findCacheProvinceByParent : function (countryNo,callback)
		{
			var provinceList =  comm.getCache("commCache","contry_"+countryNo);
			//非空验证
			if( provinceList == null || provinceList == 'null')
			{
				
				//讲传递的参数封装成json根式
				var param ={"countryNo":countryNo};
				
				//根据当前用户登录状态封装获取国家的参数
				var jsonParam = this.getParameters(param);
				
				//执行获取国家
				commData.findProvinceByParent(jsonParam,function(response){
					//非空验证
					if(response.data)
					{
						//数据缓存
						comm.setCache("commCache","contry_"+countryNo,response.data);
						
						//执行回调函数
						callback(response.data) ;
					}
					else
					{
						comm.error_alert(comm.lang("common")[30019]);
					}
				});
			}
			else	//缓存中存在数据直接调用回调函数
			{
				//执行回调函数
				callback(provinceList) ;
			}
		},
		
		/**
		 * 获取所有的银行列表
		 * @return 返回传入国家下省份
		 */
		findCacheBankAll : function (callback)
		{
			var bankList =  comm.getCache("commCache","bankList");
			//非空验证
			if( bankList == null || bankList == 'null')
			{
				//根据当前用户登录状态封装获取国家的参数
				var jsonParam = this.getParameters();
				
				//执行获取国家
				commData.findBankAll(jsonParam,function(response){
					//非空验证
					if(response.data)
					{
						//数据缓存
						comm.setCache("commCache","bankList",response.data);
						
						callback(response.data);
					}else
					{
						comm.error_alert(comm.lang("common")[30019]);
					}
				});
			}
			else	//缓存中存在数据直接调用回调函数
			{
				//执行回调函数
				callback(bankList) ;
			}
			
		},
		
		/**
		 * 获取本平台代码
		 * @param callback 回调函数
		 * @return 本平台基本信息
		 */
		findCacheSystemInfo : function (callback)
		{
			var systemInfo =  comm.getCache("commCache","systemInfo");
			
			//缓存中如果不存在数据则调用后台方法 设置到缓存中
			if( systemInfo == null || systemInfo == 'null')
			{
				//获取当前用户登录信息
				var jsonParam = this.getParameters(null);
				
				//执行获取国家
				commData.findSystemInfo(jsonParam ,function(response){
					
					//非空验证
					if(response.data)
					{
						comm.setCache("commCache","systemInfo",response.data);
						callback(response.data);
					}
					else
					{
						comm.error_alert(comm.lang("common")[30019]);
					}
				});
			}
			else	//缓存中存在数据直接调用回调函数
			{
				//执行回调函数
				callback(systemInfo) ;
			}
			
		},
		
		/**
		 * 根据省份代码获取下属城市
		 * cache中下属城市名称规则：国家编码_省份编码_cityList
		 * @param currencyNo  国家编码
		 * @param provinceNo 省份编码
		 * @return 返回传入省份的下属城市
		 */
		findCacheCityByParent : function (currencyNo,provinceNo,callback)
		{
			//获取缓存中的国家省份的下属成熟
			var cityList =  comm.getCache("commCache",currencyNo+"_"+provinceNo+"_cityList");
			
			//缓存中如果不存在数据则调用后台方法 设置到缓存中
			if( cityList == null || cityList == 'null')
			{
				//构造国家与省份编码json
				var param ={
						countryNo  : currencyNo, 	//国家编码
						provinceNo : provinceNo		//省份编码
				}
				
				//根据当前用户登录状态封装获取国家的参数
				var jsonParam = this.getParameters(param);
				
				//获取国家省份的下属城市
				commData.findCityByParent(jsonParam,function(response){
					//非空验证
					if(response.data)
					{
						//保存省份下的城市缓存名称 国家编码_省份编码_cityList
						comm.setCache("commCache",currencyNo+"_"+provinceNo+"_cityList",response.data);
						callback(response.data);
					    
					}else
					{
						comm.error_alert(comm.lang("common")[30019]);
					}
					
				});
			}
			else
			{
				//执行回调函数
				callback(cityList) ;
			}

			
		},
		/**
		 * 拼接地区名称-同步(优先从缓存js缓存中获取，如果缓存中没有再请求后台)
		 * @param countryCode 国家代码
		 * @param provinceCode 省份代码
		 * @param cityCode 城市代码
		 * @param linkStr 连接字符
		 */
		getRegionByCode : function(countryCode, provinceCode, cityCode, linkStr){
			var res = comm.getRegionByCode(countryCode, provinceCode, cityCode, linkStr);
			if(comm.removeNull(res) == ""){
				var params = {};
				params.linkStr = (linkStr != null)?linkStr:"-";
				params.cityCode = comm.removeNull(cityCode);
				params.countryCode = comm.removeNull(countryCode);
				params.provinceCode = comm.removeNull(provinceCode);
				return commData.getRegionByCode(params);
			}else{
				return {flag:true, data: res};
			}
		},
		/**
		 * 拼接地区名称-异步(优先从缓存js缓存中获取，如果缓存中没有再请求后台)
		 * @param countryCode 国家代码
		 * @param provinceCode 省份代码
		 * @param cityCode 城市代码
		 * @param linkStr 连接字符
		 */
		syncGetRegionByCode : function(countryCode, provinceCode, cityCode, linkStr, callBack){
			var res = comm.getRegionByCode(countryCode, provinceCode, cityCode, linkStr);
			if(comm.removeNull(res) == ""){
				var params = {};
				params.linkStr = (linkStr != null)?linkStr:"-";
				params.cityCode = comm.removeNull(cityCode);
				params.countryCode = comm.removeNull(countryCode);
				params.provinceCode = comm.removeNull(provinceCode);
				commData.syncGetRegionByCode(params, function(res){
					callBack(res.data);
				});
			}else{
				callBack(res);
			}
		},
		/**
		 * 获取平台工单业务类型
		 * @param callback 回调函数
		 * @return 业务类型
		 */
		getBizAuthList : function (callback)
		{
			var bizAuthList =  comm.getCache("commCache","bizAuthList");
			
			//缓存中如果不存在数据则调用后台方法 设置到缓存中
			if( bizAuthList == null || bizAuthList == 'null')
			{
				//获取当前用户登录信息
				var jsonParam = this.getParameters(null);
				
				//获取平台工单业务类型
				commData.getBizAuthList(jsonParam ,function(response){
				
				//非空验证
				if(response.data)
				{
					comm.setCache("commCache","bizAuthList",response.data);
					callback(response.data);
				}
				else
				{
					comm.error_alert(comm.lang("common")[30019]);
				}
				});
			}
			else	//缓存中存在数据直接调用回调函数
			{
				//执行回调函数
				callback(bizAuthList);
			}
		},
		/**
		 * 根据国家代码-根据国家代码获取省份城市信息--同步
		 * @param countyNo 国家代码
		 * @return 省份城市缓存
		 */
		findProvCity : function (countyNo){
			var provCity =  comm.getCache("commCache", "provCity");
			//缓存中如果不存在数据则调用后台方法 设置到缓存中
			if(provCity == null || provCity == 'null'){
				var resData = commData.findProvCity(comm.removeNull(countyNo));
				if(resData.flag){
					comm.setCache("commCache", "provCity", resData.data);
				}
			}
		},
		/**
		 * 获取示例图片、常用业务文档、办理业务文档列表
		 * @param callBack 回调函数
		 */
		findDocList : function(callBack){
			var docMap = comm.getCache("commCache", "docMap");
			if(comm.removeNull(docMap) == ""){
				commData.findDocList(function(res){
					comm.setCache("commCache", "docMap", res.data);
					callBack(res.data);
				});
			}else{
				callBack(docMap);
			}
		},
		/**
		 * 根据货币代码查询货币信息
		 * @param callBack 回调函数
		 */
		findCurrencyByCode : function(currencyCode, callBack){
			if(!currencyCode){
				callBack(null);
			}
			commData.findCurrencyByCode(currencyCode, function(res){
				callBack(res.data);
			});
		},
		/**
		 * 获取地区平台信息
		 */
		findCacheLocalInfo : function(callback){
			//获取缓存地区平台信息
			var localInfo = comm.getCache("commCache","localInfo");
			//非空验证
			if( comm.isEmpty(localInfo))
			{
				//执行获取国家
				commData.getLocalInfo(function(resp){
					var obj = resp.data;
					//非空验证
					if(comm.isNotEmpty(obj))
					{
						//数据缓存
						comm.setCache("commCache","localInfo",obj);
						if(callback)
						{
							callback(obj);
						}
					}else
					{
						comm.error_alert(comm.lang("common")[30018]);
					}
				});
			}
			else	//缓存中存在数据直接调用回调函数
			{
				//执行回调函数
				if(callback)
				{
					callback(localInfo) ;
				}
			}
		},
		/**
		 * 根据客户号查询操作员信息
		 * @param operCustId 客户号
		 * @param callBack 回调函数
		 */
		searchOperByCustId : function(operCustId, callBack){
			if(!operCustId){
				callBack(null);
			}
			var operInfo = comm.getCache("commCache", "operInfo_"+operCustId);
			if(operInfo == null || operInfo == 'null'){
				commData.searchOperByCustId({operCustId:operCustId}, function(res){
					if(res && res.data){
						comm.setCache("commCache", "operInfo_"+operCustId, res.data);
						callBack(res.data);
					}else{
						callBack(null);
					}
				});
			}else{
				callBack(operInfo);
			}
		},
		
		/**
		 * 根据客户号查询操作员信息,用于主页面查询显示
		 * @param operCustId 客户号
		 */
		searchOperByCustIdPage : function(operCustId){
			var operInfo = comm.getCache("commCache", "operInfo_"+operCustId);
			if(operInfo == null || operInfo == 'null'){
				commData.searchOperByCustId({operCustId:operCustId}, function(res){
					if(res.data){
						comm.setCache("commCache", "operInfo_"+operCustId, res.data);
						return res.data;
					}
				});
			}else{
				return operInfo;
			}
		},
		/**
		 * 根据客户号获取当前用户下的所有菜单
		 * @param callBack 回调函数
		 */
		findPermissionByCustidList : function (callBack){
			//将菜单存入到缓存中
			var docMap = comm.getCache("commCache", "ListMenu");
			if(comm.removeNull(docMap) == ""){
				commData.findPermissionByCustidList(function(res){
					comm.setCache("commCache", "ListMenu", res.data);
					callBack(res.data);
				});
			}else{
				callBack(docMap);
			}
		}
	};
	return window.cacheUtil;
});
