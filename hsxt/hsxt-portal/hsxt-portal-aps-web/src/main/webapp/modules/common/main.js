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

//if(document.cookie.match(new RegExp("(^| )custId=([^;]*)(;|$)")) == null){
	//window.location = "/hsxt-access-web-aps";
//}
 
reqConfig.setLocalPath("common");
reqConfig.setLocalPath("callCenter"); 
reqConfig.setLocalPath("debit"); 
reqConfig.setLocalPath("invoice"); 
reqConfig.setLocalPath("workflow"); //20150107 kuangrb add
reqConfig.setLocalPath("cashtransfer"); //20150108 kuangrb add
reqConfig.setLocalPath("tis"); //20150109 kuangrb add
reqConfig.setLocalPath("receivablesmanage"); //20150112 kuangrb add
 
reqConfig.setLocalPath("certificateManage"); //20150113 kuangrb add
reqConfig.setLocalPath("scoremanage"); //20150113 kuangrb add
reqConfig.setLocalPath("toolmanage"); //20150114 kuangrb add
reqConfig.setLocalPath("msgmanage"); //20150115 kuangrb add
reqConfig.setLocalPath("userpassword"); //20150115 kuangrb add
reqConfig.setLocalPath("companyInfo");
reqConfig.setLocalPath("safeSet");
reqConfig.setLocalPath("yufu");
reqConfig.setLocalPath("resouceManage");
reqConfig.setLocalPath("zypeManage");
reqConfig.setLocalPath("systemManage");
reqConfig.setLocalPath("coDeclaration");
reqConfig.setLocalPath("filemanage");//20150126 kuangrb add
reqConfig.setLocalPath("infoManage");
reqConfig.setLocalPath("pricingManage");
reqConfig.setLocalPath("toolorder");
reqConfig.setLocalPath("shutdownsystem");
reqConfig.setLocalPath("messageCenter");
reqConfig.setLocalPath("costdistribution");
reqConfig.setLocalPath("contractManage");
reqConfig.setLocalPath("serviceMsgManage");
reqConfig.setLocalPath("accountCompany");
reqConfig.setLocalPath("accountPerson");
reqConfig.setLocalPath("platformProxy");
reqConfig.setLocalPath("businessDocumentManage");
reqConfig.setLocalPath("serviceMsgManage");
reqConfig.setLocalPath("platformDebit");
reqConfig.setLocalPath("accountPersonFckr");
reqConfig.setLocalPath("workopt");


require.config(reqConfig);



define(['GY','commonLan'], function(GY) {
	 
/*
 *  加载头部 
 *
 */
require(["commSrc/frame/header"],function(tpl){	

});

 
/*
 *  加载左边导航栏
 *
 */
require(["commSrc/frame/sideBar"],function(tpl){	

});
 	 

/*
 *  加载右边服务模块
 *
 */
require(["commSrc/frame/rightBar"],function(tpl){	

});


/*
 *  加载中间菜单
 *
*/
require(["commSrc/frame/nav"],function(tpl){	

}); 


/*
 *  加载页脚
 *
 */
require(["commSrc/frame/footer"],function(src){
 
});

//保存登录token
$("#oldToken").val(comm.getRequestParams()["token"]);

});
