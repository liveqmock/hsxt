define(["commDat/common"], function (commAjax) {
	window.cacheUtil = {
		
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
				commAjax.getLocalInfo(function(resp){
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
		 * 获取缓存中国家信息
		 * 如缓存中不存在查询后台方法放入到缓存中并返回
		 * @return 返回当前系统的国家信息
		 */
		findCacheContryAll : function (callback){
			//获取缓存中国家信息
			var contryList = comm.getCache("commCache","contry");
			//非空验证
			if( comm.isEmpty(contryList))
			{
				//执行获取国家
				commAjax.getCountryAll(null,function(resp){
					var obj = resp.data;
					//非空验证
					if(comm.isNotEmpty(obj))
					{
						//数据缓存
						comm.setCache("commCache","contryList",obj);
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
					callback(contryList) ;
				}
			}
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
			if( comm.isEmpty(provinceList))
			{
				if(comm.isEmpty(countryNo))
				{
					countryNo = comm.isEmpty(countryNo)?$.cookie('countryCode'):countryNo;
				}
				//讲传递的参数封装成json根式
				var param ={"countryNo":countryNo};
				//执行获取国家
				commAjax.getProvinceList(param,function(resp){
					var obj = resp.data;
					//非空验证
					if(comm.isNotEmpty(obj))
					{
						//数据缓存
						comm.setCache("commCache","contry_"+countryNo,obj);
						
						//执行回调函数
						if(callback)
						{
							callback(obj) ;
						}
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
				if(callback)
				{
					callback(provinceList) ;
				}
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
			if( comm.isEmpty(bankList))
			{
				//执行获取银行列表
				commAjax.getBankList(function(resp){
					var obj = resp.data;
					//非空验证
					if(comm.isNotEmpty(obj))
					{
						//数据缓存
						comm.setCache("commCache","bankList",obj);
						if(callback)
						{
							callback(obj);
						}
					}else
					{
						comm.error_alert(comm.lang("common")[30019]);
					}
				});
			}
			else	//缓存中存在数据直接调用回调函数
			{
				//执行回调函数
				if(callback)
				{
					callback(bankList) ;
				}
			}
		},
		
		
		/**
		 * 根据省份代码获取下属城市
		 * cache中下属城市名称规则：国家编码_省份编码_cityList
		 * @param countryNo  国家编码
		 * @param provinceNo 省份编码
		 * @return 返回传入省份的下属城市
		 */
		findCacheCityByParent : function (countryNo,provinceNo,callback)
		{
			//获取缓存中的国家省份的下属城市
			var cityList =  comm.getCache("commCache",countryNo+"_"+provinceNo+"_cityList");
			//缓存中如果不存在数据则调用后台方法 设置到缓存中
			if( comm.isEmpty(cityList))
			{
				if(comm.isEmpty(countryNo))
				{
					countryNo = comm.isEmpty(countryNo)?$.cookie('countryCode'):countryNo;
				}
				//构造国家与省份编码json
				var param ={
						countryNo  : countryNo, 	//国家编码
						provinceNo : provinceNo		//省份编码
				}				
				//获取省份的下属城市
				commAjax.getCityList(param,function(resp){
					var obj = resp.data;
					//非空验证
					if(comm.isNotEmpty(obj))
					{
						//保存省份下的城市缓存名称 国家编码_省份编码_cityList
						comm.setCache("commCache",countryNo+"_"+provinceNo+"_cityList",obj);
						if(callback)
						{
							callback(obj);
						}
					    
					}else
					{
						comm.error_alert(comm.lang("common")[30019]);
					}
				});
			}
			else
			{
				//执行回调函数
				if(callback)
				{
					callback(cityList) ;
				}
			}					
		},
		/**
		 * 根据编号获取城市信息
		 */
		getCityById : function(countryNo, provinceNo, cityNo,callback){
			//获取缓存中的国家省份的下属城市
			var city =  comm.getCache("commCache",countryNo+"_"+provinceNo+"_"+cityNo+"_city");
			//缓存中如果不存在数据则调用后台方法 设置到缓存中
			if( comm.isEmpty(city))
			{
				if(comm.isEmpty(countryNo))
				{
					countryNo = comm.isEmpty(countryNo)?$.cookie('countryCode'):countryNo;
				}
				//构造国家与省份编码json
				var param ={
						countryNo  : countryNo, 	//国家编码
						provinceNo : provinceNo,	//省份编码
						cityNo : cityNo				//城市编码
				}				
				//获取省份的下属城市
				commAjax.getCityList(this.packageAskParam(param),function(resp){
					var obj = resp.data;
					//非空验证
					if(comm.isNotEmpty(obj))
					{
						//保存省份下的城市缓存名称 国家编码_省份编码_城市编号_city
						comm.setCache("commCache","commCache",countryNo+"_"+provinceNo+"_"+cityNo+"_city",obj);
						if(callback)
						{
							callback(obj);
						}
					    
					}else
					{
						comm.error_alert(comm.lang("common")[30019]);
					}
				});
			}
			else
			{
				//执行回调函数
				if(callback)
				{
					callback(city) ;
				}
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
				return commAjax.getRegionByCode(params);
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
				commAjax.syncGetRegionByCode(params, function(res){
					callBack(res.data);
				});
			}else{
				callBack(res);
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
				var resData = commAjax.findProvCity(comm.removeNull(countyNo));
				if(resData.flag){
					comm.setCache("commCache", "provCity", resData.data);
				}
			}
		},
		/**
		 * 查询快捷支付银行列表
		 * @param params 查询参数
		 * @param callBack 回调函数
		 * @return 省份城市缓存
		 */
		findPayBankAll : function (params, callBack){
			var payBankAll = comm.getCache("commCache", "payBankAll");
			//缓存中如果不存在数据则调用后台方法 设置到缓存中
			if(payBankAll == null || payBankAll == 'null'){
				commAjax.findPayBankAll(null, function(res){
					if(res.data){
						comm.setCache("commCache", "payBankAll", res.data);
						callBack(res.data);
					}
				});
			}else{
				callBack(payBankAll);
			}
		},
		/**
		 * 获取示例图片、常用业务文档、办理业务文档列表
		 * @param callBack 回调函数
		 */
		findDocList : function(callBack){
			var docMap = comm.getCache("commCache", "docMap");
			if(comm.removeNull(docMap) == ""){
				commAjax.findDocList(function(res){
					comm.setCache("commCache", "docMap", res.data);
					callBack(res.data);
				});
			}else{
				callBack(docMap);
			}
		},
		/**
		 * 地区信息
		 * @param countryCode
		 * @param provinceCode
		 * @param cityCode
		 * @returns
		 */
		getAreaSplitJoint : function(countryCode, provinceCode, cityCode){
			var areaInfo = comm.getCache("commCache", "areaInfo"+comm.navNull(countryCode)+comm.navNull(provinceCode)+comm.navNull(cityCode));
			if(comm.isEmpty(areaInfo)){
				var params = {};
				params.cityCode = comm.removeNull(cityCode);
				params.countryCode = comm.removeNull(countryCode);
				params.provinceCode = comm.removeNull(provinceCode);
				areaInfo = commAjax.getAreaSplitJoint(params);
				comm.setCache("commCache", "areaInfo"+comm.navNull(countryCode)+comm.navNull(provinceCode)+comm.navNull(cityCode),areaInfo);
			}
			return areaInfo;
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
				commAjax.searchOperByCustId({operCustId:operCustId}, function(res){
					if(res.data){
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
		 * 根据客户号获取当前用户下的所有菜单
		 * @param callBack 回调函数
		 */
		findPermissionByCustidList : function (callBack){
			//将菜单存入到缓存中
			var docMap = comm.getCache("commCache", "ListMenu");
			if(comm.removeNull(docMap) == ""){
				commAjax.findPermissionByCustidList(function(res){
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
