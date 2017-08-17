define(function() {
    comm.domainList = {
		//生产环境 
    	"local":"/",
		//生产环境 
 //   	"apsWeb" : "/apsWeb/", //整合nginx及接入层
    	"apsWeb" : "http://192.168.41.16:8085/hsxt-access-web-aps/",  //本地开发
		"baiduMapApi": location.protocol.replace(':','') == 'http'?"async!http://api.map.baidu.com/api?v=2.0&ak=DE62882740f1ba3905be84a1d20a1e9f":"async!https://api.map.baidu.com/api?v=2.0&s=1&ak=DE62882740f1ba3905be84a1d20a1e9f",
        "xmpp":"http://192.168.41.193:5280",
		//"companyWeb" : "http://localhost:8089/hsxt-access-web-aps/", //本地开发
		"fsServerUrl" : "http://192.168.229.27:8080/hsi-fs-server/fs/download/",
		"quitUrl":"http://hsxt.dev.gy.com:9400", //用户登出页面
		"hsOperateUrl":"http://192.168.229.42:8380/hsxt-portal-os-web" //互生运营管理地址
	};
	return comm.domainList;
});
