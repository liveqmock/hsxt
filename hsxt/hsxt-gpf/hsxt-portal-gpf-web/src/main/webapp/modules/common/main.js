 
reqConfig.setLocalPath("accountManage");
reqConfig.setLocalPath("home") ; 
reqConfig.setLocalPath("reporter") ;
reqConfig.setLocalPath("safetySettings");
reqConfig.setLocalPath("appreciationInfo");
reqConfig.setLocalPath("areaPlatform");
reqConfig.setLocalPath("manageCompany");
reqConfig.setLocalPath("resourceQuota");
reqConfig.setLocalPath("systemManage"); 
  
require.config( reqConfig ); 
define(["homeSrc/login/login",'commSrc/index', 'homeDat/login/login','GY'], function(login,index,loginAjax) {

	//获取当前用户信息
	loginAjax.checkToken(function(respInfo) {
		if(respInfo.success&&respInfo.data) {
			index.showPage();
			//将登录信息设置到缓存中
			comm.setCache("home", "loginInfo", respInfo.data);
		}else {
			login.showPage() ;
		}
	});
	//login.showPage() ;
});
