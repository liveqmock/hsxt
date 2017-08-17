define([ "commSrc/domainList", "commSrc/urlList", "commSrc/base" ], function(
		domain, url) {
	comm.getDomainUrl = function(urlName, domainName) {
		// domain设置域名 , url 设置接口路径
		var reg = /\//g, regHttp = /(?:http|https):\/\//g;
		if (regHttp.test(urlName)) {
			return urlName;
		} else if (reg.test(urlName)) {
			// 开发者自写的路径
			if (domainName) {
				// 指定域名，则用指定的域名地址
				return domain[domainName] + urlName;
			} else {
				// 未指定域名，用base.js里配的域名地址
				return domain[Config.domain] ? domain[Config.domain] + urlName : urlName;
			}
		} else if (url[urlName]) {
			if (domainName) {
				return domain[domainName] + url[urlName];
			} else {
				return domain[Config.domain] + url[urlName];
			}
		} else {
			window.console && console.log('url is undefined!');
		}
	};
	return comm.getDomainUrl;
});