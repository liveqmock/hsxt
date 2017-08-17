define([], function() {
	return {
		/**初始化省份*/
		initArea : function(callback){
			var self = this;
			cacheUtil.findCacheSystemInfo(function(sysRes){
				//初始化省份
				cacheUtil.findCacheProvinceByParent(sysRes.countryNo, function(provArray){
					comm.initProvSelect('#area', provArray, 70, provArray[0].provinceNo);
					callback();
				});
			});
		},
		
		/** 初始化城市 */
		initCity:function(provCode){
			comm.initCitySelect('#city', {}, 70, null);
			$("#city").attr({"optionvalue":"","value":""})
			
			cacheUtil.findCacheSystemInfo(function(sysRes){
				cacheUtil.findCacheCityByParent(sysRes.countryNo, provCode, function(cityArray){
					 comm.initCitySelect('#city', cityArray, 70, null, {name:'', value:''});
				});
			});
		},
		/** 获取平台货币 */
		getCurrencyName:function(callBack){ 
			cacheUtil.findCacheSystemInfo(function(rsp){
				callBack(rsp.currencyNameCn); 
			});
		}
	}
});