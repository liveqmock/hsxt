define(["commSrc/domainList" , "commSrc/urlList" ,"commSrc/base" ], function(domain,url) { 
		comm.getDomainUrl =  function(urlName,domainName){
			//domain设置域名  , url 设置接口路径
 			
 
			var reg = /\//g ,   
				   regHttp = /(?:http|https):\/\//g ; 
	 
			//标准化domain("http[s]://***.***/")和url("***/***")的写法
			if (domainName && typeof(domain[domainName])=='object' && domain[domainName].length != domain[domainName].lastIndexOf('/') + 1){
				//如果最后无"/"，则补加
				domain[domainName].concat('/');
			}
			
				   
			if ( regHttp.test(urlName) ){
				return urlName ;
			}else  if ( reg.test(urlName) ){
				if (urlName.indexOf('/') == 0){
					//如果开始有“/”,则删除
					urlName = urlName.substring(1,urlName.length);
				}
				//开发者自写的路径
				if ( domainName ) {
					//指定域名，则用指定的域名地址
					return 	domain[domainName] + urlName   ;
				} else {
					//未指定域名，用base.js里配的域名地址
					return 	domain[Config.domain] ?  domain[Config.domain] + urlName : urlName;
				}
				
			} else if ( url[urlName]  )  {
				if (url[urlName].indexOf('/') == 0){
					//如果开始有“/”,则删除
					url[urlName] = url[urlName].substring(1,url[urlName].length);
				};
				
				if ( domainName ) {
					return   domain[domainName]+url[urlName] ;
				} else {
					return   domain[Config.domain]+url[urlName] ;
				}
				
			} else {
				
			}
			
			
		};		
		return comm.getDomainUrl;
});