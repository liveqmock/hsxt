define(function() {
	    comm.domainList = {
	    "local":"/",
	    // 电商服务公司后台服务		
        "scs" : "/ecScs/",
	   // "scsWeb" : "./scsWeb/",  //整合nginx及接入层
	    "scsWeb" : "http://192.168.41.16:8080/hsxt-access-web-scs/",   //本地开发
		"fsServerUrl" : "http://192.168.229.27:8080/hsi-fs-server/fs/download/",
		"quitUrl":"http://hsxt.dev.gy.com:9100",
		
		//百度api 地址
		"baiduApi":  location.protocol.replace(':','') =='http' ?                                                 
				'async!http://api.map.baidu.com/api?v=2.0&ak=DE62882740f1ba3905be84a1d20a1e9f': 
            'async!https://api.map.baidu.com/api?v=2.0&s=1&ak=DE62882740f1ba3905be84a1d20a1e9f'
		}; 
	        comm.domainList['homePage'] = comm.domainList.quitUrl;
		return comm.domainList;
});

