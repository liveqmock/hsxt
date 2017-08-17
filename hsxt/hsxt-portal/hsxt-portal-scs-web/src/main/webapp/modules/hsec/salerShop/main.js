/*
 *加载全局对象
 * 1.含有jquery ,jquery plugins , underscore,  项目的公用方法
 * 2.使用公用方法：comm.functionName(XXX)...
 * 3.引用公用的路径 
       公用src :   'commSrc'
	   公用css:    'commCss'
	   公用模板：'commTpl'
	   公用数据：'commDat'
 * 4.模板自动填充数据，局部刷新
 */

reqConfig.setLocalPath("salerShop"); 

require.config(reqConfig);



define(['GY'], function(GY) {
	
	/*
	 *  加载实体店列表
	 *
	 */
	require(["hsec_salerShopSrc/salerShopSrc"], function(ajaxRequest){	
	});

	
});
