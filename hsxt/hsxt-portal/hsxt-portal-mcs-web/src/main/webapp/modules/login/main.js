 /*
  * 定义本模块的路径
  */  
reqConfig.setLocalPath("login");
reqConfig.setLocalPath("top"); 
 
require.config(reqConfig);

define(['GY'], function(GY) { 
	require(["topSrc/ec/top"],function(ajaxRequest){
		 
	});
	
	require(["loginSrc/login"],function(ajaxRequest){
		
	});
	
});
