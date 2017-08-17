define(function() {
	    comm.domainList = {
				//互生企业后台域名
				"local":"/",
                                //互动配置服务------------------begin
				'hsim':'/hsim',
				'xmpp':'/xmpp',
                'xmppTs':'/xmppTs',
				//互动配置服务------------------end
				//电商企业后台域名
				//'sportal':'http://192.168.41.29:8989/',
				'sportal':'/onlineWeb/',
				//电商tfs服务器地址
				'tfs':"http://192.168.229.31:9099/v1/tfs/",
				
				//互生文件系统地址
				"fsServerUrl" : "http://192.168.229.27:8080/hsi-fs-server/fs/download/",
				//互生退出跳转官网路径
				"quitUrl":"http://hsxt.dev.gy.com:9100",
			//	"companyWeb":"/companyWeb/" , //整合nginx及接入层
				"baiduApi":  location.protocol.replace(':','') =='http' ? 
						'async!http://api.map.baidu.com/api?v=2.0&ak=DE62882740f1ba3905be84a1d20a1e9f': 
							'async!https://api.map.baidu.com/api?v=2.0&s=1&ak=DE62882740f1ba3905be84a1d20a1e9f',
				 //本地开发
				"companyWeb" : "http://192.168.41.16:8080/hsxt-access-web-company/"
				
		};
		comm.domainList['homePage'] = comm.domainList.quitUrl;
		return comm.domainList;
});

