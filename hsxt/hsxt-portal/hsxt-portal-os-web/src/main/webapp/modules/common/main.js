/*  
 *   1.引用公用的路径 
 *     公用src :   'commSrc'
	   公用css:    'commCss'
	   公用模板：'commTpl'
	   公用数据：'commDat'
 *   2.设置短路径
 */

reqConfig.setLocalPath("common");
reqConfig.setLocalPath("accountManage");
reqConfig.setLocalPath("permissionManage");
reqConfig.setLocalPath("businessParaManage");
reqConfig.setLocalPath("home") ; 
reqConfig.setLocalPath("reporter") ;
reqConfig.setLocalPath("balanceAccount") ;
reqConfig.setLocalPath("checkBalance") ;
reqConfig.setLocalPath("posUpgradeManage") ;
reqConfig.setLocalPath("InvestmentBonus");

require.config( reqConfig ); 
define(["homeSrc/login/login",'GY',"accountManageLan"], function(login) {	
	 if(Date.format==undefined){
			Date.prototype.Format=function(fmt){var o={"M+":this.getMonth()+1,"d+":this.getDate(),"h+":this.getHours(),"m+":this.getMinutes(),"s+":this.getSeconds(),"q+":Math.floor((this.getMonth()+3)/3),"S":this.getMilliseconds()};if(/(y+)/.test(fmt))fmt=fmt.replace(RegExp.$1,(this.getFullYear()+"").substr(4-RegExp.$1.length));for(var k in o)if(new RegExp("("+k+")").test(fmt))fmt=fmt.replace(RegExp.$1,(RegExp.$1.length==1)?(o[k]):(("00"+o[k]).substr((""+o[k]).length)));return fmt};
			Date.prototype.format=function(){return this.Format('yyyy-MM-dd')};
			Date.prototype.formatTime=function(){return this.Format('yyyy-MM-dd hh:mm:ss')};
		} 
	/*
	 *  加载登录
	 *  */ 
	
	 login.showPage() ; 
	
});
