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
/*********持卡人****************/
//积分账户
reqConfig.setLocalPath("pointAccount");
//互生币账户
reqConfig.setLocalPath("hsbAccount");
//货币账户
reqConfig.setLocalPath("cashAccount");
//投资账户
reqConfig.setLocalPath("investAccount");
//我的互生卡
reqConfig.setLocalPath("myHsCard");
//积分福利
reqConfig.setLocalPath("pointWelfare");
//安全设置
reqConfig.setLocalPath("safetySet");
//支付
reqConfig.setLocalPath("pay");
//补卡支付
reqConfig.setLocalPath("cardReapplyPay");
//系统服务
reqConfig.setLocalPath("systemService");

/*********非持卡人****************/
//互生币账户
reqConfig.setLocalPath("hsbAccount","fckr");
//货币账户
reqConfig.setLocalPath("cashAccount","fckr");
//账号管理
reqConfig.setLocalPath("accountManagement","fckr");
//安全设置
reqConfig.setLocalPath("safetySet","fckr");
//支付
reqConfig.setLocalPath("pay","fckr");

//系统服务
reqConfig.setLocalPath("systemService","fckr");



require.config(reqConfig);

define(['GY', 'pointAccountLan', 'hsbAccountLan', 'cashAccountLan', 'investAccountLan', 'myHsCardLan', 'pointWelfareLan', 'safetySetLan', 'systemServiceLan','commLan'], function(GY) {
	
	jQuery.extend({
		getQueryString:function(name) {
		    var input = "";
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
			var search =  encodeURI(window.location.search);
		    var r = search.substr(1).match(reg);
		    if (r == null){
		    	return null;
		    }
		    input= unescape(r[2]);
		    
				var output = "";
			   var chr1, chr2, chr3 = "";
			   var enc1, enc2, enc3, enc4 = "";
			   var i = 0;
			   var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
			   // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
			   var base64test = /[^A-Za-z0-9\+\/\=]/g;
			   if (base64test.exec(input )) {
			      alert("There were invalid base64 characters in the input text.\n" +
			            "Valid base64 characters are A-Z, a-z, 0-9, '+', '/', and '='\n" +
			            "Expect errors in decoding.");
			   }
			   input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

			   do {
			      enc1 = keyStr.indexOf(input.charAt(i++));
			      enc2 = keyStr.indexOf(input.charAt(i++));
			      enc3 = keyStr.indexOf(input.charAt(i++));
			      enc4 = keyStr.indexOf(input.charAt(i++));

			      chr1 = (enc1 << 2) | (enc2 >> 4);
			      chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
			      chr3 = ((enc3 & 3) << 6) | enc4;

			      output = output + String.fromCharCode(chr1);

			      if (enc3 != 64) {
			         output = output + String.fromCharCode(chr2);
			      }
			      if (enc4 != 64) {
			         output = output + String.fromCharCode(chr3);
			      }

			      chr1 = chr2 = chr3 = "";
			      enc1 = enc2 = enc3 = enc4 = "";

			   } while (i < input.length);

			   return unescape(output);
			   
		}
	});
	
	var result = $.getQueryString("hsxtPre");
	if(result != null && result != ""){
		var objParam = eval('(' + result + ')');
		
		$.cookie("custId",objParam.custId,{path:'/'});
		$.cookie("custName",objParam.custName,{path:'/'});
		$.cookie("email",objParam.email,{path:'/'});
		$.cookie("entResNo",objParam.entResNo,{path:'/'});
		$.cookie("isBindBank",objParam.isBindBank,{path:'/'});
		$.cookie("isLocal",objParam.isLocal,{path:'/'});
		$.cookie("isRealnameAuth",objParam.isRealnameAuth,{path:'/'});
		$.cookie("isBindBank",objParam.isBindBank,{path:'/'});
		$.cookie("isAuthMobile",objParam.isAuthMobile,{path:'/'});
		$.cookie("resNo",objParam.resNo,{path:'/'});
		$.cookie("token",objParam.token,{path:'/'});
		$.cookie("userName",objParam.userName,{path:'/'});
	}
		
	
	//加载头部
	require(["commSrc/frame/header"],function(header){});
	//加载顶部菜单
	//加载左边菜单
	//加载主体框架
	require(["commSrc/frame/nav", "commSrc/frame/sideBar", "commSrc/frame/center"], function (nav, sideBar, center) {});
	//加载页脚
	require(["commSrc/frame/footer"],function(footer){});
	
	//保存登录token
	$("#oldToken").val(comm.getRequestParams()["token"]);
});