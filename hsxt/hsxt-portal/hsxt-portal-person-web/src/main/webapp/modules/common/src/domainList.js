define(function() {
	comm.domainList = {
		//消费者web访问域名
		//"personWeb" : "../personWeb/",  //整合nginx及接入层
		"personWeb" : "http://192.168.41.16:8080/hsxt-access-web-person/",    		//本地开发
		"hsecWeb" : "http://192.168.229.27:8080/hsi-fs-server/fs/download/",		
		"fsServerUrl" : "http://192.168.229.27:8080/hsi-fs-server/fs/download/",
		"hsecWeb" : "http://192.168.229.27:8080/hsi-fs-server/fs/download/",
		"quitUrl":"http://hsxt.dev.gy.com:9200",
		"hsxtWebsite":"http://www.hsxt.com/",//互生官网地址 实名注册协议的时候需要连接官网
		"jumpUrl": "http://p.dev.gy.com/hsxt-access-web-person/",//网银支付跳回地址  本址开发
		"shopUrl":"http://192.168.41.29/cgpweb/main.html" //电商平台地址
	};
 
	return comm.domainList;
});