define(['text!commTpl/frame/subNav.html',"commonDat/frame/companyInfo",'commSrc/commFunc','commonSrc/cacheUtil'] , function(subNavTpl,common) {
        // 数字转货币
	Number.prototype.formatMoney = function(places, symbol, thousand,
			decimal) {
		places = !isNaN(places = Math.abs(places)) ? places : 2;
		symbol = symbol !== undefined ? symbol : "$";
		thousand = thousand || ",";
		decimal = decimal || ".";
		var number = this, negative = number < 0 ? "-" : "", i = parseInt(
				number = Math.abs(+number || 0).toFixed(places), 10)
				+ "", j = (j = i.length) > 3 ? j % 3 : 0;
		return symbol
				+ negative
				+ (j ? i.substr(0, j) + thousand : "")
				+ i.substr(j)
						.replace(/(\d{3})(?=\d)/g, "$1" + thousand)
				+ (places ? decimal
						+ Math.abs(number - i).toFixed(places).slice(2)
						: "");
	};
	 
	$.extend(comm,{	
         	//401出错跳转，402出错提示 
		retCode : ['401','402','403','404','500','','','','8888','9999','302', '303','160383','160373','160355'],
		connection : null,

		/*
		 * 设置全局缓存
		 */
		cache : {},

		/*
		 * 取模块首页路径
		 */
		 getIndex : function(moduleName){
			 	return globalPath+"modules/"+ moduleName +"/tpl/index.html" ;
		 },
		/*
		 * 导航（链接）到模块的首页
		 */
		navIndex : function(moduleName) {
			location.href = comm.getIndex(moduleName);
		},

		/*
		 * 取任何指定的页面路径
		  */
		 getUrl : function(moduleName,urlName){					 
				 return globalPath+"modules/"+ moduleName +"/tpl/"+urlName ;
		 },
		/*
		 * 导航（链接）到指定的模块及页面路径
		 */
		navUrl : function(moduleName, urlName) {
			location.href = comm.getUrl(moduleName, urlName);
		},
			/*
		 * 取当前日期
		 */
		getCurrDate : function() {
			var date = new Date();
			var year = date.getFullYear();
			var month = date.getMonth()+1;
			var day = date.getDate();
			var hour = date.getHours();
			var minute = date.getMinutes();
			var second = date.getSeconds();
			return year +'-'+((month<10)?("0"+month):month) + '-' + ((day<10)?("0"+day):day);
		},
		/*
		 * 格式化金额
		 */
		formatMoneyNumber : function(moneyNum) {
			var result = isNaN((1 * moneyNum).toFixed(2)) ? (new Number(
					0).toFixed(2))
					: (1 * moneyNum).toFixed(2);
			return /\./.test(result) ? result.replace(
					/(\d{1,3})(?=(\d{3})+\.)/g, "$1,") : result
					.replace(/(\d{1,3})(?=(\d{3})+\b)/g, "$1,");
		},
		/*
		 * 格式化金额 不以,隔开
		 */
		formatMoneyNumber2 : function(moneyNum) {
			var result = isNaN((1 * moneyNum).toFixed(2)) ? (new Number(
					0).toFixed(2))
					: (1 * moneyNum).toFixed(2);
			return result;
		},
		/*
		 * 格式化金额向上取值
		 */
		formatMoneyNumber3 : function(moneyNum) {
			var result = isNaN((1 * moneyNum).toFixed(2)) ? (new Number(
					0).toFixed(2))
					: (Math.ceil(moneyNum*100)/100).toFixed(2);
			return /\./.test(result) ? result.replace(
					/(\d{1,3})(?=(\d{3})+\.)/g, "$1,") : result
					.replace(/(\d{1,3})(?=(\d{3})+\b)/g, "$1,");
		},
		/*
		格式转换比率
		 */
		formatTransRate : function (rate) {
			var result= new Number(rate).toFixed(4);
			var re = /^\d+$/;
			//验证是否为正整数
			if (re.test(result)) 
			{ 
				result=result+'.0000';
			} 
			return result;
		},
		/*
		 * 格式化日期
		 */
		formatDate : function(date, format) {
			if (!date || date == "0") {
				return "";
			}
			if (!format) {
				format = "yyyy-MM-dd hh:mm:ss";
			}
			if (typeof date == "string") {
				if (date.length == 8) {
					var arr = [ date.substr(0, 4), date.substr(4, 2),
							date.substr(6, 2) ];
				} else if (date.length == 14) {
					var arr = [ date.substr(0, 4), date.substr(4, 2),
							date.substr(6, 2), date.substr(8, 2),
							date.substr(10, 2), date.substr(12, 2) ];
				} else {
					var arr = date.split(/[^0-9]+/);
				}

				format = format.replace(/([a-z])\1+/ig, function(all,
						$1) {
					var result = {
						y : ~~arr[0],
						M : ~~arr[1],
						d : ~~arr[2],
						h : ~~arr[3],
						m : ~~arr[4],
						s : ~~arr[5]
					}[$1];
					if (result != undefined && result < 10) {
						result = "0" + result
					}
					return result || "";
				});
				return format;
			}
			format = format.replace(/([a-z])\1+/ig, function(all) {
				var first = all.charAt(0);
				if ("y M d h m s".indexOf(first) >= 0) {
					if (first == "y") {
						return all.length > 2 ? date.getFullYear()
								: (date.getFullYear() + "").substr(2);
					}
					var result = {
						M : date.getMonth() + 1,
						d : date.getDate(),
						h : date.getHours(),
						m : date.getMinutes(),
						s : date.getSeconds()
					}[first];
					result != undefined && result < 10
							&& (result = "0" + result);
					return result;
				} else {
					return all;
				}
			});
			return format;
		},
		//20160503110236485
		subStringDate : function(date, length) {
			return date.substr(0, length);
		},
		/*
		 * 显示对话框
		 */
		showDialog : function() {

			require([ "commSrc/dialog" ], function(tpl) {
				$(document.body).append(tpl);
				$('.comm-dialog-box').bind('click', function(e) {
					$('.comm-dialog-box').remove();
					$('.comm-dialog-bg').remove();
				});
			});

		},
		// 自动宽度
		autoWidth : function() {
			var container = document.getElementById("wrap");
			var contentWidth_1 = document
					.getElementById("contentWidth_1");// 宽度为500px所定义的ID
			var contentWidth_2 = document
					.getElementById("contentWidth_2");// 宽度为320px所定义的ID
			var contentWidth_3 = document
					.getElementById("contentWidth_3");// 宽度为800px所定义的ID
			var contentWidth_4 = document
					.getElementById("contentWidth_4");// 宽度为700px所定义的ID
			var width = document.documentElement.clientWidth;// 获取浏览器可见区域的宽度

			if (width <= 1200) {
				container.style.width = "1200px";
			} else {
				container.style.width = "100%";
			}
			if (width <= 830) {
				if (contentWidth_1) {
					contentWidth_1.style.width = "500px";
				}
				if (contentWidth_2) {
					contentWidth_2.style.width = "320px";
				}
				if (contentWidth_3) {
					contentWidth_3.style.width = "800px";
				}
				if (contentWidth_4) {
					contentWidth_4.style.width = "700px";
				}

			} else {
				if (contentWidth_1) {
					contentWidth_1.style.width = "100%";
				}
				if (contentWidth_2) {
					contentWidth_2.style.width = "100%";
				}
				if (contentWidth_3) {
					contentWidth_3.style.width = "100%";
				}
				if (contentWidth_4) {
					contentWidth_4.style.width = "100%";
				}

			}

		},

		setFocus : function(obj, text) {// 输入框获得焦点时时触发

			obj.style.color = "black";
			if (obj.value == text) {
				obj.style.color = "black";
				obj.value = "";
			}
		},

		setBlur : function(obj, text) {// 输入框失去焦点时触发

			if (obj.value == "") {
				obj.style.color = "#999997";
				obj.value = text;
			}
		},

		/*
		 * 设置，获取，删除全局缓存
		 */
		setCache : function(module, name, data) {
			if (!this.cache[module]) {
				this.cache[module] = {};
			}
			if (!this.cache[module][name]) {
				this.cache[module][name] = data;
			}

		},

		getCache : function(module, name) {
			if (!this.cache[module]) {
				return null;
			}

			if (!this.cache[module][name]) {
				return null;
			}
			return this.cache[module][name];
		},

		delCache : function(module, name) {
			if (!this.cache[module]) {
				return;
			} else {
				delete this.cache[module][name];
			}
		},

		/*
		 * require 更改，以适应多域操作
		 * 
		 */
		Require : function(urlNames, succback, errback, optional) {
			var urlArr = [];
			$.each(urlNames, function(key, urlObj) {
				var thisUrl = null;
				if ($.isPlainObject(urlObj)) {
					// thisUrl = comm.getDomainUrl( urlObj.url ,
					// comm.clearDomain(urlObj.domain) ) || '';
					thisUrl = comm.getDomainUrl(urlObj.url,
							urlObj.domain);
				} else {
					thisUrl = comm.getDomainUrl(urlObj, Config.domain);
				}
				urlArr.push(thisUrl);

			});
			require(urlArr, succback, errback, optional);
		},

		Define : function(urlNames, succback, optional) {
			var urlArr = [];
			$.each(urlNames, function(key, urlObj) {
				var thisUrl = null;
				if ($.isPlainObject(urlObj)) {
					// 有指定域名
					if (urlObj.url.indexOf('text!') > -1
							|| urlObj.url.indexOf('css!') > -1) {
						// 模板路径
						thisUrl = comm.formatTextUrl(urlObj.url,
								comm.domainList[urlObj.domain]);
					} else {
						// js 路径
						thisUrl = comm.getDomainUrl(urlObj.url,
								urlObj.domain);
					}

				} else {
					thisUrl = urlObj;
					/*
					 * //未指定域名 if ( urlObj.url.indexOf('text!') >-1 ||
					 * urlObj.url.indexOf('css!') >-1 ){ //模板路径 } else {
					 * //js 路径
					 *  }
					 */

					// thisUrl = comm.getDomainUrl( urlObj ,
					// Config.domain ) ;
				}
				urlArr.push(thisUrl);

			});
			define(urlArr, succback, optional);

		},
		// imgFtpServer : "http://192.168.229.31:9099/v1/tfs/",
		/*
		 * 替换当前的域名地址
		 */
		formatTextUrl : function(url, domain) {

			// 去掉域名地址：require无.js全路径有点问题
			var domainName = domain.replace('http://' + location.host
					+ '/', ''), urlName = null;
			if (!domain) {
				return "";
			} else {
				// 设置需要代理路径的url
				var urlArr = url.split("!");
				urlName = urlArr[0] + '!' + domainName + urlArr[1];
				return urlName;
			}
		},

		// 国际化文案配置
		langConfig : {},
		// 获取文案字段
		lang : function(name) {
			var str = this.langConfig[name] || '';
			return str;
		},

		// 弹窗
		i_alert : function(text, width, callback) {
			$("#i_content").text(text);
			$("#alert_i").dialog({
				title : "提示信息",
				width : width || "400",
				modal : true,
				buttons : {
					"确定" : function() {
						$(this).dialog("destroy");
						if (callback)
							callback();
					}
				}
			});
		},

		// yes
		yes_alert : function(text, width,callback) {
			$("#yes_content").text(text);
			$("#alert_yes").dialog({
				title : "提示信息",
				width : width || "400",
				modal : true,
				buttons : {
					"确定" : function() {
						$(this).dialog("destroy");
						if(callback){
							callback();
						}
					}
				}
			});
		},
		// warn
		warn_alert : function(text, width) {
			$("#warn_content").text(text);
			$("#alert_warn").dialog({
				title : "提示信息",
				width : width || "400",
				modal : true,
				buttons : {
					"关闭" : function() {
						$(this).dialog("destroy");
					}
				}
			});
		},
		error_alert : function(text, width,position) {
			$("#error_content").text(text);
			$("#alert_error").dialog({
				title : "提示信息",
				width : width || "400",
				position:position||'center',
				modal : true,
				buttons : {
					"关闭" : function() {
						$(this).dialog("destroy");
					}
				}
			});
		},

		// 弹窗确认框
		i_confirm : function (text, callback, width) {
			$("#ques_content,#i_c_content").html(text);
			$("#alert_ques,#alert_i_c").dialog({
				title : "提示信息",
				width : width || "400",
				/*此处根据文字内容多少进行调整！*/
				modal : true,
				buttons : {
					"确定" : function () {
						if (callback)
							callback();
						$(this).dialog("destroy");
					},
					"取消" : function () {
						$(this).dialog("destroy");
					}
				}
			});
		},
		//关闭提示信息
		i_close : function () {
			$("#alert_i").dialog("destroy");
		},
		ipro_alert : function (text) {
			$("#i_content").html(text);
			$("#alert_i").dialog({
				dialogClass : "no-dialog-close",
				title : "提示信息",
				width : "400"
			});
		},
		ipro_alert_close : function(){
			try {
				$('#alert_i').dialog("destroy");
			} catch (e) {}
		},
		//弹窗
		Thickdiv : function (title, text, width) {
			$("#i_content2").html(text);
			$("#alert_i2").dialog({
				title : title,
				width : width || "400",
				/*此处根据文字内容多少进行调整！*/
				modal : true
			});
		},
		//弹窗
		Thickdiv2 : function (text, width) {
			$("#i_content").html(text);
			$("#alert_i").dialog({
				width : width || "400",
				/*此处根据文字内容多少进行调整！*/
				modal : true
			});
		},
		// 获取今天开始和结束
		getTodaySE : function () {
			var d1 = comm.getCurrDate();
			return [d1, d1];
		},
		// 获取最近一周开始和结束
		getWeekSE : function () {
			var d1 = new Date();
			var e = d1.getFullYear() + "-" +(d1.getMonth()+1) + "-" + d1.getDate();
			d1.setDate(d1.getDate() - 6); // 上一周
			var s = d1.getFullYear() + "-" +(d1.getMonth()+1) + "-" + d1.getDate();
			return [s, e];
		},
		// 获取最近一月开始和结束
		getMonthSE : function () {			
			var today = new Date();
			var day = today.getDate();
			var e = today.getFullYear() + "-" +(today.getMonth()+1) + "-" + today.getDate();
			today.setDate(0);
			var maxDay = today.getDate();
			if (day <= maxDay) {
				today.setDate(day);
			}
			var s = today.getFullYear() + "-" +(today.getMonth()+1) + "-" + (today.getDate()+1);
			return [s, e];
		},
		//获取最近三个月开始和结束
		get3MonthSE:function (){
			var d1 = new Date();
			var e = d1.format();
			d1.setMonth(d1.getMonth() - 3);
			var s = d1.format();
			return [s, e];
		},
		//获取最近六个月开始和结束
		get6MonthSE:function (){
			var d1 = new Date();
			var e = d1.format();
			d1.setMonth(d1.getMonth() - 6);
			var s = d1.format();
			return [s,e];
		},
		//获取最近一年开始和结束
		get1YearsSE:function (){
			var d1 = new Date();
			var e = d1.format();
			d1.setFullYear(d1.getFullYear() - 1);
			var s = d1.format();
			return [s, e];
		},
		// 获取最近三年开始和结束
		get3YearsSE : function () {
			var d1 = new Date();
			var e = d1.format();
			d1.setFullYear(d1.getFullYear() - 3);
			var s = d1.format();
			return [s, e];
		},
		 /*  下面的方法名等在此对象中被禁用
		  *  getCurrDate ， formatDate ，formatMoneyNumber ，setFocus ，setBlur ，
		  *  setCache ，getCache ， delCache ， Require ， Define ，emptyCurrPath，
		  *  langConfig ， lang ，formatStr  ，confirm ，  alert
		  *
		  */
		/*
		格式化金额
		 */
		fmtMoney : function (moneyNum) {
			var result = isNaN((1 * moneyNum).toFixed(2)) ? (new Number(0).toFixed(2)) : (1 * moneyNum).toFixed(2);
			var re = /^\d+$/;
			//验证是否为正整数
			if (re.test(result)) 
			{ 
				result=result+'.00';
			}
			return /\./.test(result) ? result.replace(/(\d{1,3})(?=(\d{3})+\.)/g, "$1,") : result.replace(/(\d{1,3})(?=(\d{3})+\b)/g, "$1,");
		},
		/**
		 * 获取工程名称
		 */
		getProjectName : function () {
			return "companyWeb";
		},
		/**
		 * 获取操作员账户名称
		 * @param operInfo 操作员信息
		 */
		getOperNoName : function(operInfo) {
			if(!operInfo){
				return "";
			}
			var userName = operInfo.userName ? operInfo.userName : '';
			var realName = operInfo.realName ? operInfo.realName : '';
			return userName+"（"+realName+"）";
		},
		/**
		 * 请求后台方法封装
		 *  @param urlStr 请求的URL
		 *  @param params 参数信息
		 *  @param callback 回调函数
		 *  @param langObj 资源文件对象
		 *  @param showLoad 是否显示进度条 true ： 不显示， 默认调用该方法时不传该参数则显示进度条
		 */
		requestFun : function (urlStr, params, callback, langObj,showLoad){
			if(comm.checkUserLogin()){
			
				if(!showLoad){
					comm.showLoading();
				}
				
				comm.Request({url:urlStr, domain:comm.getProjectName()},{
					data:comm.getRequestParams(params),
					type:'POST',
					dataType:"json",
					success:function(response){
						comm.closeLoading();
						//非空验证
						if (response.retCode ==22000){
							callback(response);
						}else{
							comm.alertErrorMsg(langObj, response);
						//	comm.alertMessageByErrorCode(langObj, response.retCode);
						}
					},
					error: function(){
						comm.closeLoading();
						comm.error_alert(comm.lang('common').requestFailed);
					}
				});
			
			}
		},
		
		/**
		 * 提示错误信息
		 * @param langObj 资源文件
		 * @param retCode 异常代码
		 */
		alertErrorMsg : function(langObj, response){
			if(160360 == response.retCode) {
				var desc = response.resultDesc;
				var str= new Array();   
				str = desc.split(",");
				var currentFailTimes = 0;
				var maxFailTimes = 0;
				if(str.length > 1){
					currentFailTimes = str[0];
					maxFailTimes = str[1];
					if(isNaN(currentFailTimes) || isNaN(maxFailTimes)){
						comm.error_alert("原交易密码错误，请重新输入！");
						return ;
					}
				}
				var tryTimes = maxFailTimes - currentFailTimes;
				var msg = "交易密码验证失败，您今天还剩"+tryTimes+"次尝试机会"
				comm.error_alert(msg);
			}else if(160411 == response.retCode) {
				comm.error_alert(response.resultDesc);
			}else if(160415 == response.retCode){
				comm.error_alert(response.resultDesc);
			}else{
				comm.alertMessageByErrorCode(langObj, response.retCode);
			}	
		},
		
		
		/**
		 * 请求后台方法封装
		 *  @param urlStr 请求的URL
		 *  @param params 参数信息
		 *  @param callback 回调函数
		 *  @param failCallBack 失败回调函数
		 *  @param langObj 资源文件对象
		 */
		requestFunForHandFail : function (urlStr, params, callback, failCallBack,langObj){
			
			if(comm.checkUserLogin()){
			
				comm.showLoading();
				comm.Request({url:urlStr, domain:comm.getProjectName()},{
					data:comm.getRequestParams(params),
					type:'POST',
					dataType:"json",
					success:function(response){
						comm.closeLoading();
						//非空验证
						if (response.retCode ==22000){
							callback(response);
						}else{
							comm.alertMessageByErrorCode(langObj, response.retCode);
							if(failCallBack){
								failCallBack();
							}
						}
					},
					error: function(){
						comm.closeLoading();
						comm.error_alert(comm.lang('common').requestFailed);
						if(failCallBack){
							failCallBack();
						}
					}
				});
				
			}
		},
		
		
		
		/**
		 * 请求后台方法封装
		 *  @param urlStr 请求的URL
		 *  @param params 参数信息
		 *  @param callback 回调函数
		 *  @param langObj 资源文件对象
		 */
		requestCompanyFunForHandFail : function (urlStr, params, callback, langObj){
			
			if(comm.checkUserLogin()){
			
				comm.showLoading();
				comm.Request({url:urlStr, domain:comm.getProjectName()},{
					data:comm.getRequestParams(params),
					type:'POST',
					dataType:"json",
					success:function(response){
						comm.closeLoading();
						callback(response);
					},
					error: function(){
						comm.closeLoading();						
						comm.error_alert(comm.lang('common').requestFailed);
					}
				});
				
			}
		},
		/**
		 * 提示错误信息
		 * 原则：优先查找传入的资源文件，找到了就直接提示，否则在comm.lang文件里找，找到了就直接提示，否则提示默认信息
		 * @param langObj 资源文件
		 * @param retCode 异常代码
		 */
		alertMessageByErrorCode : function(langObj, retCode){
			if(comm.removeNull(langObj) == ""){
				comm.error_alert(comm.lang('common').requestFailedWithCode+retCode);
			}else{
				var errMgs = comm.removeNull(langObj[retCode]);
				if(comm.isNotEmpty(errMgs)){
					comm.error_alert(errMgs);
				}else{
					errMgs = comm.removeNull(comm.lang('common')[retCode]);
					if(comm.isNotEmpty(errMgs)){
						comm.error_alert(errMgs);
					}else{
						comm.error_alert(comm.lang('common').requestFailedWithCode+retCode);
					}
				}
			}
		},
		/**
		 * 同步请求后台方法封装
		 *  @param urlStr 请求的URL
		 *  @param params 参数信息
		 *  @param langObj 资源文件对象
		 */
		asyncRequestFun : function (urlStr, params, langObj){
			var resData = null;
			if(comm.checkUserLogin()){
				
				comm.showLoading();
				comm.Request({url:urlStr, domain:comm.getProjectName()},{
					data:comm.getRequestParams(params),
					type:'POST',
					dataType:"json",
					async:false,
					success:function(response){
						comm.closeLoading();
						//非空验证
						if (response.retCode == 22000){
							resData = {flag:true, data: response.data};
						}else{
							comm.alertMessageByErrorCode(langObj, response.retCode);
							resData = {flag:false, data: ""};
						}
					},
					error: function(){
						comm.closeLoading();
						comm.error_alert(comm.lang('common').requestFailed);
						resData = {flag:false, data: ""};
					}
				});
			}
			return resData;
		},
		/***
		 * 获取随机token码
		 */
		getToken:function(callback){
			var self=this;
			var params= {};
			params.custId = "0600102000020151215";
			
			/*if ($.cookie('custId') != undefined
									&& $.cookie('custId') != ""){
				params.custId = "1005";//$.cookie('custId');
			}else {
				params.custId = ""
			}*/
			
			
			comm.Request({url:"getToken", domain:comm.getProjectName()},{
				data:params,
				type:'POST',
				dataType:"json",
				success:function(response){
					//非空验证
					if (response.retCode ==22000){
						callback(response);	
					}else{
						self.error_alert(comm.lang("common").comm.token_error);
					}
				},
				error: function(){
					self.error_alert(comm.lang("common").comm.token_error);
				}
			});
		},
		/**
		 * 同步请求后台方法封装
		 *  @param urlStr 请求的URL
		 *  @param params 参数信息
		 *  @param callback 回调函数
		 *  @param langObj 资源文件对象
		 */
		syncRequestFun : function (urlStr, params, callback, langObj){
			if(comm.checkUserLogin()){
				comm.showLoading();
				comm.Request({url:urlStr, domain:comm.getProjectName()},{
					data:comm.getRequestParams(params),
					type:'POST',
					dataType:"json",
					async:false,
					success:function(response){
						comm.closeLoading();
						//非空验证
						if (response.retCode ==22000){
							callback(response);	
						}else{
							comm.alertMessageByErrorCode(langObj, response.retCode);
						}
					},
					error: function(){
						comm.closeLoading();
						comm.error_alert(comm.lang('common').requestFailed);
					}
				});
				
			}
		},
		/**
		 * 请求后台方法封装
		 *  @param urlStr 请求的URL
		 *  @param params 参数信息
		 *  @param callback 回调函数
		 *  @param langObj 资源文件对象
		 *  callback1异常的回调
		 */
		requestFun1 : function (urlStr, params, callback, langObj,callback1){
			if(comm.checkUserLogin()){
			
				if(!params){
					params = {};
				}
				params.custId = "1005";//$.cookie('custId');
				params.pointNo = "1005";//$.cookie('pointNo');
				params.token = "1005";//$.cookie('token');
				params.custName = "1005";//$.cookie('custName');
				//params.entCustId = "07186630000163969843354624";
				comm.showLoading();
				comm.Request({url:urlStr, domain:"scsWeb"},{
					data:params,
					type:'POST',
					dataType:"json",
					success:function(response){
						comm.closeLoading();
						//非空验证
						if (response.retCode ==22000){
							callback(response);	
						}else{
							comm.alertMessageByErrorCode(langObj, response.retCode);
						}
					},
					error: function(){
						comm.closeLoading();
						comm.error_alert(comm.lang('common').requestFailed);
					}
				});
				
			}
		},
	        /**
		 * 获取请求公共参数
		 * @param params 参数信息
		 */
		getRequestParams : function(params){
			if(!params){
				params = {};
			}
			params.custId = $.cookie('custId');//登陆用户客户号
			params.pointNo = $.cookie('pointNo');//登陆用户企业互生号
			params.hsResNo = $.cookie('pointNo');//登陆用户企业互生号
			params.token = $.cookie('token');//登陆token
			params.loginToken = params.token;//登录token
			params.channelType = 1;			 // 渠道类型
			params.entCustId = $.cookie('entCustId');//登陆用户企业客户号
			params.entResNo = $.cookie('entResNo');//登陆用户企业互生号
			params.custName = comm.getCookie('custName');//登陆用户客户名称
			params.entResType = $.cookie('entResType');//企业类型
			params.custEntName =comm.getCookie('custEntName');//登陆用户企业名称
			params.operName = comm.getCookie('operName');//登陆用户客户名称
			params.cookieOperNoName = this.getCookieOperNoName();//操作员信息 ( 0000(张三) )
			return params;
		},
		/**
		 * 获取操作员账户名称
		 */
		getCookieOperNoName : function() {
			return comm.getCookie('custName')+"("+comm.getCookie('operName')+")";
		},
		/**
		 * 初始化下拉框
		 * @param objId 需要绑定的下拉框元素
		 * @param objArray 枚举内容（来源于国际化枚举对象）
		 * @param width 选择框宽度（可选参数）
		 * @param defaultVal 默认值（可选参数）
		 * @param defaultOptions 默认选项
		 */
		initSelect : function(objId, objArray, width, defaultVal, defaultOptions){
			var options = [];
			if(defaultOptions){
				options.push(defaultOptions);
			}else{
				options.push({name:'',value:''});
			}
			for(key in objArray){
				options.push({name:objArray[key], value:key});
			}
			var content = {options : options};
			if(width){
				content.width = width;
				content.optionWidth = width;
			}
			var select = $(objId).selectList(content);
			if(defaultVal != null && defaultVal != undefined){
				select.val("");
				select.attr('optionValue', "");
				select.selectListValue(defaultVal);
			}
			return select;
		},
		/**
		 * ajax请求
		 */
		ajaxFun : function(url,param,callback){
			$.ajax({
				url: url,
		    type: 'POST',
		    dataType: 'json',
		    data: param,
		    cache: false,
		    success:function(resp){
			callback(resp);	
				},
				error: function(){
					comm.error_alert('请求异常,请稍后重试');
				}
			});
		},
		/**
		 * 初始化省份下拉框
		 * @param objId 需要绑定的下拉框元素
		 * @param provArray 省份列表
		 * @param width 选择框宽度（可选参数）
		 * @param defaultVal 默认值（可选参数）
		 * @param defaultOptions 默认选项
		 */
		initProvSelect : function(objId, provArray, width, defaultVal, defaultOptions){
			var options = [];
			if(defaultOptions){
				options.push(defaultOptions);
			}else{
				options.push({name:'',value:''});
			}
			if(comm.isNotEmpty(provArray)&&provArray.length > 0){
				for(var i = 0 ; i < provArray.length;i++){
					var obj = provArray[i];
					if(obj){
						options.push({name:obj.provinceNameCn, value:obj.provinceNo});
					}
				}
			}
			var content = {options : options};
			if(width){
				content.width = width;
				content.optionWidth = width; 
			}
			if(provArray.length > 10){
				content.optionHeight=300;
			}
			content.borderWidth = 1;
			content.borderColor = '#DDD';
			var select = $(objId).selectList(content);
			if(defaultVal != null && defaultVal != undefined){
				select.selectListValue(defaultVal);
			}
			return select;
		},
		/**
		 * 初始化城市下拉框
		 * @param objId 需要绑定的下拉框元素
		 * @param cityArray 城市列表
		 * @param width 选择框宽度（可选参数）
		 * @param defaultVal 默认值（可选参数）
		 * @param defaultOptions 默认选项
		 */
		initCitySelect : function(objId, cityArray, width, defaultVal, defaultOptions){
			var options = [];
			if(defaultOptions){
				options.push(defaultOptions);
			}else{
				options.push({name:'',value:''});
			}
			if(comm.isNotEmpty(cityArray)&&cityArray.length > 0){
				for(var i = 0 ; i < cityArray.length;i++){
					var obj = cityArray[i];
					if(obj){
						options.push({name:obj.cityNameCn, value:obj.cityNo});
					}
				}
			}
			var content = {options : options};
			if(width){
				content.width = width;
				content.optionWidth = width;
			}
			if(cityArray.length > 10){
				content.optionHeight=300;
			}
			content.borderWidth = 1;
			content.borderColor = '#DDD';
			var select = $(objId).selectList(content);
			if(defaultVal != null && defaultVal != undefined){
				select.selectListValue(defaultVal);
			}
			return select;
		},
		/***
		 * 初始化组合类型的下拉框
		 * objId:下拉框ID
		 * optList:下拉框的值
		 * selValue:选中项目value(可选)
		 * allowBlank:允许第一项显示为空
		 */
		initCombox : function(objId, optList, selValue, allowBlank){
			var aHtml = allowBlank?'<option value=""></option>':"";
			for(var key in optList){
				aHtml += '<option value="' + key + '">' + optList[key] + '</option>';
			}
			/*自动完成组合框*/
			$(objId).html(aHtml).combobox();
			$(".ui-autocomplete").css({
				"max-height":"250px",
				"overflow-y":"auto",
				"overflow-x":"hidden",
				"height":"250px",
				"border":"1px solid #CCC"
			});
			if(selValue != null && selValue != undefined){
				$(objId).selectListValue(selValue);
			}
			$(".combobox_style").find("a").attr("title","");
			if(allowBlank){
				$('.ui-autocomplete-input').attr('placeholder','--请选择--');
			}
		},
		/***
		 * 初始化银行组合类型的下拉框
		 * objId:下拉框ID
		 * bankArray: 银行列表
		 * selValue:选中项目value(可选)
		 * allowBlank:允许第一项显示为空
		 */
		initBankCombox : function(objId, bankArray, selValue, allowBlank){
	        var aHtml = allowBlank?'<option value=""></option>':"";
			if(bankArray != null){
				for(var key in bankArray){
					aHtml += '<option value="'+bankArray[key].bankNo+'">' + bankArray[key].bankName + '</option>';
				}
			}
			/*自动完成组合框*/
			$(objId).html(aHtml).combobox();
			$(".ui-autocomplete").css({
				"max-height":"250px",
				"overflow-y":"auto",
				"overflow-x":"hidden",
				"height":"250px",
				"border":"1px solid #CCC"
			});
			if(selValue != null && selValue != undefined){
				$(objId).selectListValue(selValue);
			}
			$(".combobox_style").find("a").attr("title","");
			if(allowBlank){
				$('.ui-autocomplete-input').attr('placeholder','--请选择--');
			}
		},
		/**
		 * 获取枚举表现值
		 * @param enumId 枚举内部值
		 * @param objArray 枚举内容（来源于国际化枚举对象）
		 */
		getNameByEnumId : function(enumId, objArray){
			try {
				return objArray[enumId];
			} catch (e) {
				return "";
			}
		},
		/**
		 * 构建简易BsGrid
		 * @param gridId 要绑定的tableId
		 * @param localData json类型数组
		 * @param detail detail自定义函数
		 * @param del del自定义函数
		 */
		getEasyBsGrid : function (gridId, localData, detail, del){
			var autoLoad=true;
			if(!localData || localData.length == 0){
				autoLoad=false;
			}
			return $.fn.bsgrid.init(gridId, {	 
				pageSize: 10 ,
				stripeRows: true,  //行色彩分 
				displayBlankRows: false ,   //显示空白行
				autoLoad:autoLoad,
			  	localData: localData ,
				operate : {
					detail:detail,
					del:del
				}
			});
		},
		getGridParams : function(params){
			if(!params){
				params = {};
			}
			params.search_custId = $.cookie('custId');//登陆用户客户号
			params.search_pointNo = $.cookie('pointNo');//登陆用户企业互生号
			params.search_hsResNo = $.cookie('pointNo');//登陆用户企业互生号
			params.search_token = $.cookie('token');//登陆token
			params.loginToken = params.token;//登录token
			params.custId = $.cookie('custId');//登陆用户客户号
			params.search_channelType = 1;			 // 渠道类型
			params.search_loginToken = params.token;//登录token
			params.channelType = 1;			 // 渠道类型
			params.search_entCustId = $.cookie('entCustId');//登陆用户企业客户号
			params.search_entResNo = $.cookie('entResNo');//登陆用户企业互生号
			params.search_custName = comm.getCookie('custName');//登陆用户客户名称
			params.search_custEntName = comm.getCookie('custEntName');//登陆用户企业名称
			params.search_entResType = $.cookie('entResType');//登陆用户企业类型
			return params;
	    },
		         
    	 /**
		 * 获取请求公共参数
		 * @param params 参数信息
		 */
		getQueryParams : function(params){
			if(!params){
				params = {};
			}
				
			params.search_custId = $.cookie('custId');//登陆用户客户号
			params.search_pointNo = $.cookie('pointNo');//登陆用户企业互生号
			params.search_hsResNo = $.cookie('pointNo');//登陆用户企业互生号
			params.search_token = $.cookie('token');//登陆token
			params.custId = $.cookie('custId');//登陆用户客户号
			params.channelType = 1;			 // 渠道类型
			params.loginToken = $.cookie('token');//登录token
			params.search_entCustId = $.cookie('entCustId');//登陆用户企业客户号
			params.search_entResNo = $.cookie('entResNo');//登陆用户企业互生号
			params.search_custName = $.cookie('custName');//登陆用户客户名称
			params.search_custEntName = comm.getCookie('custEntName');//登陆用户企业名称
			params.search_entResType = $.cookie('entResType');//登陆用户企业类型
				
			return params;
		},
			
		/**
		 * 构建常用BsGrid
		 * @param gridId 要绑定的tableId
		 * @param url 请求数据url
		 * @param params json类型参数
		 * @param detail 自定义函数detail（可选参数）
		 * @param del 自定义函数del（可选参数）
		 * @param add 自定义函数add（可选参数）
		 * @param edit 自定义函数edit（可选参数）
		 * @param renderImg 自定义函数renderImg（可选参数）
		 */
		getCommBsGrid : function (gridId, url, params, detail, del, add, edit, renderImg,button1){
			if(comm.checkUserLogin()){
			
				gridId = !gridId?"tableDetail":gridId;
				return $.fn.bsgrid.init(gridId, {
					url:comm.domainList[comm.getProjectName()]+comm.UrlList[url],
					otherParames : comm.getQueryParams(params),
					pageSize: 10,
					stripeRows: true,  //行色彩分 
					displayBlankRows: false,
					operate : {
						detail:detail,
						del:del,
						add:add,
						edit:edit,
						renderImg:renderImg,
						button1:button1
					}
				});
				
			}
		},
		//验证token是否一致
		validTokenCK : function (){
			
			var returnValue = true ;
			var oldToken = $("#oldToken").val();	
			var token = comm.getRequestParams()["token"];
			
			//验证token是否相等
			if(oldToken != token)
			{
				returnValue = false ;
			}
			
			return returnValue ;
		},
		/**
		 * 获得服务器地址
		 * @param fileId 文件ID
		 */
		getFsServerUrl : function(fileId) {
			if(comm.isEmpty(fileId) || fileId == "NO_FILE"){
				return "./resources/img/noImg.gif";
			}else{
				var custId = comm.getCookie('custId');;// 读取 cookie 客户号
				var token =comm.getCookie('token');
				return comm.domainList['fsServerUrl']+fileId+"?channel=1&userId="+custId+"&token="+token;
			}
		},
		/**
		 * 获得示例图片网络地址
		 * @param picList 文件列表
		 * @param docCode 文件代码
		 */
		getPicServerUrl : function(picList, docCode) {
			var fileId = null;
			try{
				fileId = picList[docCode];
			}catch(e){
			}
			return this.removeNull(fileId);
		},
		/**
		 * 获得下载文件网络地址
		 * @param busList 文件列表
		 * @param docCode 文件代码
		 */
		getDocServerUrl : function(busList, docCode) {
			var fileId = null;//文件ID
			var fileName = "";//文件名称
			try{
				fileId = busList[docCode].fileId;
				fileName = busList[docCode].docName;
			}catch(e){
			}
			if(this.removeNull(fileId) == ""){
				return "";
			}
			return this.getFsServerUrl(fileId)+"&useSavedFileName=true&fileName="+fileName;
		},
		/**
		 * 获取验证码
		 * @param inputId 验证码输入框ID
		 * @param imageId 验证码图片ID
		 * @param btnIds 刷新按钮
		 * @param codesType 校验类型
		 */
		getSecuritCode : function(inputId, imageId, btnIds, codesType){
			$.each(btnIds, function(i, o){
				$(o).click(function(){
					var custId = comm.getRequestParams().custId;
					var imageUrl = comm.domainList[comm.getProjectName()]+comm.UrlList["generateSecuritCode"]+"?custId="+custId+"&type="+codesType+'&loginToken='+$.cookie('token')+"&"+(new Date()).valueOf();
					$(imageId).attr("src", imageUrl);
				});
			});
			//过滤字符
			$(inputId).keyup(function(){
		        $(this).val($(this).val().replace(/\D|/g,''));
		    }).bind("paste",function(){
		        $(this).val($(this).val().replace(/\D|/g,''));
		    });
			$(inputId).attr("maxlength", 4);//限制输入4位
			$(btnIds[0]).click();//第一次加载
		},
		/**
		 * 下载文件
		 * @param objId 下载按钮ID
		 * @param busList 文件列表
		 * @param docCode 文件代码
		 */
		initDownload : function(objId, busList, docCode) {
			var url = comm.getDocServerUrl(busList, docCode);
			if(url == ""){
				$(objId).attr("href", "javascript:void(comm.error_alert('下载失败，原因：文件不存在!'));");
				return;
			}
			$(objId).attr("href", url);
		},
		/**
		 * 获取上传文件地址
		 */
		getUploadFilePath : function() {
			return comm.domainList[comm.getProjectName()]+comm.UrlList["fileupload"];
		},
		/**
		* 显示等待效果
		*/
		showLoading : function () {
			$("#showLoadIngDiv").dialog({
				draggable : false,
				resizable : false,
				modal : true,
				closeOnEscape : false
			});
			$("#showLoadIngDiv").siblings("div").remove();
			$(".ui-dialog").css("border", "0");
			$("#showLoadIngDiv").parent("div").removeClass("ui-widget ui-widget-content ui-corner-all");
			$("#showLoadIngDiv").removeAttr("class style");
		},
		/**
		 * 隐藏等待效果
		 */
		closeLoading : function () {
			try{
				$("#showLoadIngDiv").dialog('destroy');
				$("#showLoadIngDiv").addClass('none');
			}catch(e){
			}
		},
		/**
		 * 初始化图片div,查看大图
		 * @param objId 对象ID
		 * @param fileId 文件ID
		 * @param title 抬头（可选参数，默认图片查看）
		 * @param divId 绑定的Div（可选参数，默认showImage50）
		 * @param width 预览窗口宽度（可选参数，默认800）
		 * @param height 预览窗口高度（可选参数，默认600）
		 * @param position 定位弹出位置
		 */
		initPicPreview : function(objId, fileId, title, divId, width, height,position) {
			title = !title?"图片查看":title;
			width = !width?"800":width;
			height = !height?"600":height;
			divId = !divId?"#showImage50":divId;
			logId = divId+"Div";
			$(objId).attr("data-imgSrc", comm.getFsServerUrl(fileId));
			$(objId).click(function(e){
				var buttons={};
				 buttons['关闭'] =function(){
					$(this).dialog("destroy");
				};
				var url = $(e.currentTarget).attr('data-imgSrc');
				if ("./resources/img/noImg.gif" != url && comm.isNotEmpty(url)) {
					var imgHtml = "<img alt=\"图片加载失败...\" src=\""+url+"\" id=\"showImage50\" style=\"width:100%; height:100%;\">";
					$(logId).html(imgHtml);
					$(logId).dialog({
						title : title,
						width : width,
						height : height,
						modal : true,
						buttons : buttons
					});
					
					//显示图片原始比例
					$(logId).css("width","auto");
					$(logId).css("height","auto");
				}
				if(position){
					$(".ui-dialog").css("top",position.top);
					$(".ui-dialog").css("left",position.left);
				}
			});
		},
		/***
		 * 绑定图片查看器
		 */
		bindPicViewer : function(objId,url) {
			var title ="图片查看";
			var width = "800";
			var height = "600";
			var divId = "#showImage50";
			var logId = divId+"Div";
			//点击显示大图
			$(objId).click(function(e){
				var buttons={};
				buttons['关闭'] =function(){
					$(this).dialog("destroy");
				};
				if (comm.isNotEmpyt(url) && "./resources/img/noImg.gif" != url){
					
					$(divId).attr('src', url);
					$(logId).dialog({
						title : title,
						width : width,
						height : height,
						modal : true,
						buttons : buttons
					});
				}
			});
		},
		/**
		 * 初始化图片div,查看大图
		 * @param imgId 对象ID
		 * @param src 文件src
		 */
		initTmpPicPreview : function(imgId, src, title) {
			var title = !title?"图片预览":title;
			var width = !width?"800":width;
			var height = !height?"600":height;
			var divId = !divId?"#showImage50":divId;
			var logId = divId+"Div";
			$(imgId).attr("data-imgSrc", src);
			$(imgId).children().first().attr("width", "100%");
			$(imgId).children().first().attr("height", "100%");
			$(imgId).click(function(e){
				var buttons={};
				 buttons['关闭'] =function(){
					$(this).dialog("destroy");
				};
				var url = $(e.currentTarget).attr('data-imgSrc');
				if (null != url && "" != url) {
					$(divId).attr('src', url);
					$(logId).dialog({
						title : title,
						width : width,
						height : height,
						modal : true,
						buttons : buttons
					});
				}
			});
			$(imgId).show();
		},
		/**
		 * 依据地区代码获取地区名称
		 * @param countryCode 国家代码
		 * @param provinceCode 省份代码
		 * @param cityCode 城市代码
		 * @param linkStr 连接字符
		 */
		getRegionByCode : function(countryCode, provinceCode, cityCode, linkStr){
			if(comm.getCache("commCache", "provCity") == null){
				return "";
			}
			linkStr = (linkStr != null)?linkStr:"-";
			if(countryCode && provinceCode && cityCode){
				return this.getCtryNameByCode(countryCode)+linkStr+this.getProvNameByCode(provinceCode)+linkStr+this.getCityNameByCode(provinceCode, cityCode);
			}else if(countryCode && provinceCode){
				return this.getCtryNameByCode(countryCode)+linkStr+this.getProvNameByCode(provinceCode);
			}else if(provinceCode && cityCode){
				return this.getProvNameByCode(provinceCode)+linkStr+this.getCityNameByCode(provinceCode, cityCode);
			}else if(countryCode && !provinceCode){
				return this.getCtryNameByCode(countryCode);
			}else if(provinceCode && !cityCode){
				return this.getProvNameByCode(provinceCode);
			}else if(cityCode){
				return "";
			}else{
				return "";
			}
		},
		/**
		 * 依据国家代码获取国家名称
		 * @param ctryCode 国家代码
		 */
		getCtryNameByCode : function(ctryCode){
			var provCity = comm.getCache("commCache", "provCity");
			return comm.removeNull(provCity.countryName);
		},
		/**
		 * 依据省份代码获取省份名称
		 * @param provCode 省份代码
		 */
		getProvNameByCode : function(provCode){
			var provCity = comm.getCache("commCache", "provCity");
			if(provCity.provMap){
				return comm.removeNull(provCity.provMap[provCode]);
			}
			return "";
		},
		/**
		 * 依据省份城市代码获取城市名称
		 * @param provCode 省份代码
		 * @param cityCode 城市代码
		 */
		getCityNameByCode : function(provCode, cityCode){
			var provCity = comm.getCache("commCache", "provCity");
			if(provCity.cityMap && provCity.cityMap[provCode]){
				return comm.removeNull(provCity.cityMap[provCode][cityCode]);
			}
			return "";
		},
		/**
		 * 获取登陆用户国家身份城市代码
		 */
		getCustPlace : function(){
			var countryCode = $.cookie('countryCode');
			var provinceCode = $.cookie('provinceCode');
			var cityCode = $.cookie('cityCode');
			return [countryCode, provinceCode, cityCode];
		},
		/**
		 * 获取银行名称
		 * @param bankCode 银行代码
		 * @param bankArray 银行列表
		 */
		getBankNameByCode : function(bankCode, bankArray){
			if(bankArray == null || comm.removeNull(bankCode) == ""){
				return "";
			}
			for(var key in bankArray){
				if(bankArray[key].bankNo == bankCode){
					return bankArray[key].bankName;
				}
			}
			return "";
		},
		/**
		 * 隐藏银行卡号中间部分
		 * @param ocard 银行卡卡号
		 */
		hideCard : function(ocard){
			if(ocard == null){
				return "";
			}
			ocard = ocard.replace(/(\s*$)/g,"");
			if(ocard.length < 9){
				return  " **** "+ocard.substring(ocard.length-4,ocard.length);
			}
		    return ocard.substring(0, 4)+" **** **** "+ocard.substring(ocard.length-4, ocard.length);
		},
		/**
		 * 显示银行卡号尾号部分
		 * @param ocard 银行卡卡号
		 */
		showLastCard : function(ocard){
			if(!ocard){
				return "";
			}
			ocard = ocard.replace(/(\s*$)/g,"");
			if(ocard.length <= 4){
				return ocard;
			}
			return ocard.substring(ocard.length-4, ocard.length);
		},
		/***
		 * 上传图片预览功能需要先初始化上传按钮，
		 * 
		 * btnIds：上传按钮的id,多个ID格式为数据['#id1','#id2']  
		 * 
		 * labelIds:预览图片显示的标签的ID,与上传按键的顺序要对应,格为数据['#id1','#id2']  
		 */
		  initUploadBtn : function(btnIds, labelIds, width, height,maxSize){
	              $.each(btnIds,function(i,o){
	                  $(o).uploadPreview({ 
	                      width: width?width:107, 
	                      height: height?height:64, 
	                      imgDiv: labelIds[i], 
	                      imgType: ["bmp", "jpeg", "jpg","png"],
						  maxSize: maxSize?maxSize:2
	                  });
	              });
		  },
		  /***
			 * 上传图片预览功能需要先初始化上传按钮，
			 * 
			 * btnIds：上传按钮的id,多个ID格式为数据['#id1','#id2']  
			 * 
			 * labelIds:预览图片显示的标签的ID,与上传按键的顺序要对应,格为数据['#id1','#id2']
			 */
			initChatUploadBtn : function(btnIds, labelIds, width, height, maxSize){
				$.each(btnIds,function(i,o){
					$(o).uploadPreview({ 
						width: width?width:107, 
						height: height?height:64, 
						imgDiv: labelIds[i], 
						imgType: ["bmp", "jpeg", "jpg", "png"],
						maxSize: maxSize?maxSize:2
					});
				});
			},
		/***
		 * 支持多文件上传
		 * url:上传地址
		 * ids:文件的ID,多文件格式['id1','id2']
		 * callBack1:成功后的回调方法
		 * callBack2:失败后的回调方法
		 * param 参数
		 * 
		 */
		uploadFile : function(url, ids, callBack1, callBack2, param){
			if(comm.checkUserLogin()){
				
				url = !url?comm.getUploadFilePath():url;
				if(!param){
					param = {};
				}
				
				param = comm.getRequestParams(param);
				//文件上传token custid找不到
				url = url+"?loginToken="+param.token+"&custId="+param.custId+"&channelType=1";
				
				var langObj=this.lang("common");
				//comm.ipro_alert(langObj['comm'].uploading);
				$.ajaxFileUpload({
					// 处理文件上传操作的服务器端地址
					url : url,
					secureuri : false, // 是否启用安全提交,默认为false
					fileElementId : ids, // 文件选择框的id属性
					dataType : 'text', // 服务器返回的格式,可以是json或xml等
					data : param,
					type : "POST",
					success : function (response) { // 服务器响应成功时的处理函数
						response=JSON.parse(response.replace('</generated></generated>','')); 
						var data = response.data;
						comm.ipro_alert_close();
						if(response.retCode==22000){
							callBack1(data);
						}else {
							//上传异常提示
							comm.alert({
								content: langObj['errorCodes'][response.retCode],
								imgClass: 'tips_error'
							});
							if(callBack2){
								callBack2();
							}
						}
					},
					error: function(response){
						comm.ipro_alert_close();
						comm.error_alert("文件上传失败,可能是文件过大");
						if(callBack2){
							callBack2();
						}
					}
				});
			}	
		},
		/***
		 * 互动聊天-文件上传
		 * ids:文件的name
		 * callBack1:成功后的回调方法
		 * callBack2:失败后的回调方法
		 * param 参数
		 * 
		 */
		uploadChartFile : function(name, callBack1, callBack2){
			var url = comm.domainList['xmppImg']+"upload/imageUpload?type=default";
			url+="&fileType=image&channelType=1";
			url+="&custId="+comm.getRequestParams()['custId'];
			url+="&id="+comm.getRequestParams()['pointNo'];
			url+="&loginToken="+comm.getRequestParams()['token'];
			url+="&entResNo="+comm.getRequestParams()['pointNo'];
			$.ajaxFileUpload({
				// 处理文件上传操作的服务器端地址
				url : url,
				secureuri : false, // 是否启用安全提交,默认为false
				fileElementId : name, // 文件选择框的id属性
				dataType : 'json', // 服务器返回的格式,可以是json或xml等
				data : {},
				type : "POST",
				success : function (response) { // 服务器响应成功时的处理函数
					comm.ipro_alert_close();
					if(response.state == 200){
						callBack1(response);
					}else {
						//上传异常提示
						comm.alert({
							content: "图片上传失败.",
							imgClass: 'tips_error'
						});
						if(callBack2){
							callBack2();
						}
					}
				},
				error: function(response){
					comm.ipro_alert_close();
					comm.error_alert("图片上传失败.");
					if(callBack2){
						callBack2();
					}
				}
			});
		},
		//替换字符串的 null ‘null’ undefined ‘undefined’
		navNull : function(str,str2){
			if(typeof(str) == 'string'){
				str = comm.trim(str);
			}
			if(typeof(str2) == 'string'){
				str2 = comm.trim(str2);
			}
			if(str==undefined || str==null ||str=='undefined'||str=='null'){  
				if(str2==undefined || str2==null ||str2=='undefined'||str2=='null'){  
				    return ""; 
				}
				return str2;
				
			}  
			return str;
		},
		/**
         * 移除数组元素
         * @param array 数组
         * @param dx 删除的下标
         */
        arrayRemove : function(array, dx){
        	if(!array || isNaN(dx) || dx>array.length){
        		return;
        	}
        	array.splice(dx, 1);
        },
		/**
         * 移除特殊字符
         * @param e 字符串
         */
        valueReplace : function(e){ 
        	e=e.toString().replace(new RegExp('(["\"])', 'g'),"”"); 
        	return e; 
		},
		/**
		 * 去掉null
		 * @param val 值
		 */
		removeNull : function(val) {
			if(val==undefined || val==null ||val=='undefined'||val=='null'){
				return "";
			}
			if(typeof(val) == 'string'){
				return comm.trim(val);
			}
			return val;
		},			
		/**
		 * 判断是空
		 */
		isEmpty : function(obj) {
			if (typeof (obj) == 'string') {
				obj = comm.trim(obj);
			}
			if (obj == null || obj == undefined || obj == 'undefined' || obj == '' || obj == 'null') {
				return true;
			}
			return false;
		},
		/**
		 * 判断非空
		 */
		isNotEmpty : function(obj) {
			return !comm.isEmpty(obj);
		},
		// 输入框只能输入数字
		onlyNum : function (id){
			$("#"+id).keydown(function(event){ 
				var keyCode = event.which; 
                                    if(event.shiftKey) return false;
		         if (keyCode == 46 || keyCode == 8 || keyCode == 37 || keyCode == 39 || (keyCode >= 48 && keyCode <= 57) || (keyCode >= 96 && keyCode <= 105) ){
		        	 return true; 
		         }else { 
		        	 return false 
		        	 } 
		         }).focus(function(){this.style.imeMode = 'disabled';}
		        ); 
		},
		/**
		 * 字符串格式化占位符
		 * str 需要替换的字符串
		 * param 替换的值,数组--格式:[1,2,3]
		 */
		strFormat : function(str,param){
			if(param.length==0 || str.length==0){
				return str; 
			}
			for(var i=0; i<param.length; i++){
				str=str.replace(new RegExp("\\{"+i+"\\}","g"), param[i]);  
			}
			return str;  
		},
		/**
		 * 得到字符串格式的日期(只能格式化 yyyy-MM-dd)
		 */
		getDateStr : function(date) {
			if (!date || date == "0") {
				return "";
			}
			if (typeof date == "string") {
				return date;
			} else {
				return new Date(date).format();
			}
		},
		/**
		 * 格式化日期
		 */
		getDateFormatStr : function(date, format) {
			if (!date || date == "0") {
				return "";
			}
			if (typeof date == "string") {
				return date;
			} else {
				date = new Date(date);
				var o = {
					"M+" : date.getMonth() + 1,
					"d+" : date.getDate(),
					"h+" : date.getHours(),
					"m+" : date.getMinutes(),
					"s+" : date.getSeconds(),
					"q+" : Math.floor((date.getMonth() + 3) / 3),
					"S" : date.getMilliseconds()
				};
				if (/(y+)/.test(format)) {
					format = format.replace(RegExp.$1, (date
							.getFullYear() + "")
							.substr(4 - RegExp.$1.length));
				}
				for ( var k in o) {
					if (new RegExp("(" + k + ")").test(format)) {
						format = format
								.replace(
										RegExp.$1,
										RegExp.$1.length == 1 ? o[k]
												: ("00" + o[k])
														.substr(("" + o[k]).length));
					}
				}
				return format;
			}
		},

		// 格式化货币
		formatMoney : function(num) {
			return Number(num).formatMoney(2, '', ',', '.');
		},

		/**
		 * 解析资源号类型
		 * 
		 * @param inc
		 *            资源号
		 * @returns {String} 资源类型,M:管理公司、S:服务公司、T:托管企业、B:成员企业
		 */

		resolveInc : function(inc) {
			if (inc.substr(2) == "000000000") {
				return "M";
			} else {
				if (inc.substr(5) == "000000") {
					return "S";
				} else {
					if (inc.substr(5, 2) == "00") {
						return "B";
					} else {
						return "T";
					}
				}
			}
		},
		/**
		 * 是否有特殊字符
		 */
		specialCharStr : "[`|{}''\\[\\]<>/|{}:']",
		specialChar : function(divId) {
			var falg = true;
			var regexp = new RegExp(comm.specialCharStr);
			$('#' + divId).find('input[type="text"],textarea').each(
					function() {
						if (regexp.test($(this).val())) {
							falg = false;
							return;
						}
					});
			return falg;
		},

		/*
		 * 过滤特殊字符,如 <,>,',",(,),[,],{,},%,\,/,^,@,&,_
		 * 
		 * 
		 */
			
		formatStr : function(str) {
			var string = str;
			string = string.replace(/</gm, '＜');
			string = string.replace(/>/gm, '＞');
			string = string.replace(/\'/gm, '＇');
			string = string.replace(/\"/gm, '＂');
			string = string.replace(/\(/gm, '（');
			string = string.replace(/\)/gm, '）');
			string = string.replace(/\[/gm, '［');
			string = string.replace(/\]/gm, '］');
			string = string.replace(/\{/gm, '｛');
			string = string.replace(/\}/gm, '｝');
			string = string.replace(/\%/gm, '％');
			string = string.replace(/\\/gm, '＼');
			string = string.replace(/\//gm, '／');
			string = string.replace(/\^/gm, '＾');
			string = string.replace(/@/gm, '＠');
			string = string.replace(/&/gm, '＆');
			string = string.replace(/_/gm, '＿');
			return string;
		},
		/**
		 * 过滤div里的特殊字符
		 */
			
		filterVal : function(divId) {
			$('#' + divId).find('input[type="text"],textarea').each(
					function() {
						var val = $(this).val();
						$(this).val(comm.formatStr(val));
					});
		},
		/**
		 * 去掉首尾空格
		 */
		trim : function(val) {
			return val.replace(/(^\s*)|(\s*$)/g, '');
		},
		/**
		 * 验证创业人员
		 */
		vailOneT : function(val) {
			var corp = /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)([0][1-9]|[1][0])([0]{4})$/;
			return (corp.test(val));
		},
		/**
		 * 判断查询是否有查询条件
		 */
		filterSearch : function(formId) {
			var falg = true;
			$('#' + formId).find('input[type="text"],select').each(
					function() {
						var val = $(this).val();
						if (comm.isNotEmpty(val)) {
							falg = false;
						}
					});
			return falg;
		},
		/**
		 * 按钮定时
		 * 
		 * @param btn
		 * @param time
		 */
		timeOut : function(btn, btnName, time) {
			if (btn && time >= 0) {
				if (time == 0) {
					$(btn).empty().text(btnName);
					$(btn).attr('disabled', false);
				} else {
					$(btn).attr('disabled', true);
					$(btn).empty().text(btnName + '(' + time + ')');
					time--;
					window.setTimeout(function() {
						comm.timeOut(btn, btnName, time);
					}, 1000);
				}
			}
		},
		confirm : function(obj){
			 if (! $('#dialog-confirm')[0] ){
				$('<div id="dialog-confirm"  title="" class="none"><p></p></div>').appendTo($(document.body));	 
			 } 
			 $('#dialog-confirm').attr('title',obj.title || '温馨提示')	 ;
			 if (obj.imgFlag){					 	
			 	$('#dialog-confirm > p').html('<i class="icon_tips '+ ( obj.imgClass || "tips_ques" )  +'"></i><span class="fl fb f14 ml10 pt9">'+(obj.content || '您真的要删除吗？')+'</span>');
			 } else {
			 	$('#dialog-confirm > p').html(obj.content || '您真的要删除吗？');
			 }					 
			 var btnOkName = obj.ok || '确定',
			 	  btnOther = obj.del || '删除',
			 	  btnCancelName = obj.cancel || '取消';				     
			 var btnObj ={};
			 btnObj[btnOkName] =   function(){ obj.callOk && obj.callOk(); $(this).dialog("destroy");$("#dialog-confirm").remove();};
			 obj.del && (btnObj[btnOther] = function(){ obj.callOther && obj.callOther(); $(this).dialog("destroy");$("#dialog-confirm").remove();});
			 btnObj[btnCancelName] = function(){ obj.callCancel && obj.callCancel() ;$(this).dialog("destroy");$("#dialog-confirm").remove();}; 					 
			 if (obj.timeClose &&  /^\d+$/.test(obj.timeClose )  ){
			 	setTimeout(function(){
			 		if ($("#dialog-confirm").length ){
			 			$("#dialog-confirm").dialog('destroy');
			 			$("#dialog-confirm").remove();
			 		}
			 	},obj.timeClose*1000);
			 }					 
			 $("#dialog-confirm").dialog({  
			 	 width : obj.width  || 320 ,	 
				 height: obj.height || 160 , 
				 open: function (event, ui) {
					obj.closeButton && $(".ui-dialog-titlebar-close", $(this).parent()).show();
				 },
				 buttons: btnObj 
			 });	 
		 } ,
		
		
		 alert : function(content){
			 if (! $('#dialog-alert')[0] ){
					$('<div id="dialog-alert"  title="" class="none"><p></p></div>').appendTo($(document.body));	 
				 } 
				 $('#dialog-alert').attr('title', content.title || '温馨提示')	 ;
				 if  (typeof content =='object'){
				 	$('#dialog-alert > p').html('<table width="100%" border="0" cellspacing="0" cellpadding="0">'+
						'<tr>'+
						'<td height="50"><div class="clearfix">'+
							'<i class="icon_tips  '+ ( content.imgClass || "tips_yes" )  +'"></i>'+
 							'<span class="fl fb f14 ml10 pt9">'+(content.content || '' )+'</span>'+
							'</div></td>'+
								'</tr>'+
								'</tbody></table>'); 
				 } else{
				 	$('#dialog-alert > p').html(content || '操作!'); 
				 } 				 
				 if (content.timeClose &&  /^\d+$/.test(content.timeClose )  ){
				 	setTimeout(function(){
				 		if ($("#dialog-alert").length ){
				 			$("#dialog-alert").dialog('destroy');
				 			$("#dialog-alert").remove();
				 		}
				 	},content.timeClose*1000);
				 }
				 $("#dialog-alert").dialog({ 
					 width : content.width  || 360 ,
					 height: content.height || 160 , 	
					 open: function (event, ui) {
						content.closeButton && $(".ui-dialog-titlebar-close", $(this).parent()).show();
					 },			  
					 buttons: {
						'确定' :  function(){ 
							 if  (typeof content =='object' &&  content.callOk && typeof  content.callOk  == 'function'){
							 	  content.callOk();
							 }
							$(this).dialog("destroy");
							$("#dialog-alert").remove();
						} 	 
					 }
				 })	
		 },
		 /**
		 * 金额四舍五入最后保留两位小数点
		 * 例：1.16 返回1.20
		 */
		formatMoneyRound:function(myNumber){
		    number = new Number(myNumber);
        		    var result = Math.round(number*Math.pow(10, 1))/Math.pow(10, 1);
                        return Number(result).toFixed(2);
		},
		/**
     	* 进行AES进行加密(登录密码)
     	* @param word         加密的内容
     	* @param key        加密的秘钥
     	*/
        encrypt : function (word,key){                                                 
	         var key  = CryptoJS.enc.Utf8.parse(key) ;        
	         var iv    = CryptoJS.enc.Utf8.parse('9999999999999999') ;                         
	         var srcs = CryptoJS.enc.Utf8.parse(word) ;
	         var encrypted = CryptoJS.AES.encrypt(srcs, key, { iv: iv,mode:CryptoJS.mode.CBC});                                                
	         return encrypted.toString() ;
        },
        /** 验证用户是否登录 */
        checkUserLogin:function(){
        	var custId=$.cookie("custId"); //客户操作号
        	
        	//登录身份失效请重新登录 或 用户数据不存在则跳转到登录页
        	if(comm.validTokenCK() == false || (custId==undefined || custId==null || custId=="null"))
			{
        		$("#warn_content").html('登录身份失效请重新登录');
    			$("#alert_warn").dialog({
    				title : "提示信息",
    				width : "300",
    				modal : true,
    				buttons : {
    					"确定" : function () {
    						$(this).dialog("destroy");
    						window.location.replace(comm.domainList["quitUrl"]);
    					}
    				}
    			});
				return false;
			}
        	return true;
        },
        /* 功能：获取cookies函数   参数：name，cookie名字  */  
        getCookie : function (name){  
            var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));  
            if(arr != null){  
             return unescape(arr[2]); 
            }else{  
             return null;  
            }  
        },
        //设置cookie
        setCookie:function(name,value){
            var Days = 30*12;   //cookie 将被保存一年  
            var exp  = new Date();  //获得当前时间  
            exp.setTime(exp.getTime() + Days*24*60*60*1000);  //换成毫秒  
            document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+";path=/";  
        },
	        // jquery.validate扩展验证
		addMethodValidator : function () {
			/**
			 * 验证两个元素的值不相等
			 */
			jQuery.validator.addMethod("unequalTo", function(value, element, param) {
				 if($(param).val()==value){
					 return false;
				 }
				 return true;
			});
			$.validator.addMethod("huobi", function (value, element, params) {
				if (params) {
					var reg = /^(([1-9]{1}\d{0,8})|0)(\.\d{1,2})?$/;
					return reg.test($.trim(value));
				}
				return true;
			}, "请输入正确的货币格式");
			/*
             *电话号码格式:只能有+(加号)-(横杠)数字,中文字组合的电话号码<br>add by zhanghh :date 2016-05-09
             */
            $.validator.addMethod("phoneCN", function (value, element, params) {
                    if (params) {
                            var reg = /^(\+?\d{2}-)?(\d{3,4}-)?(\d{7,8})+(([\u4e00-\u9fa5]|-)?\d{3,4})?$/;
                            return reg.test($.trim(value));
                    }
                    return true;
            }, "请根据提示输入正确格式的电话号码");
            /*
             *持卡人互生号验证
             */
            $.validator.addMethod("personResNo", function (value, element, params) {
                    if (params) {
                            var reg =  /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{3}|\d{3}[1-9]|\d{2}[1-9]\d|\d[1-9]\d{2})$/;
                            return reg.test($.trim(value));
                    }
                    return true;
            }, "请输入持卡人互生号");
		},
		/** 获取所有cookie值 */
		getCookieValue:function(){
			var cookieParams=comm.getRequestParams(null);
			cookieParams.lastLoginDate=$.cookie('lastLoginDate');//上一次登录时间
			cookieParams.lastLoginIp=$.cookie('lastLoginIp');//上一次登录ip		
			return cookieParams;
		},
		/** 获取是否为持卡人号 */
		ispersonResNo:function(personResNo){
			var reg =  /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{3}|\d{3}[1-9]|\d{2}[1-9]\d|\d[1-9]\d{2})$/;
            return reg.test($.trim(personResNo));
		},
		/** 设置时间区间控件 */
		initBeginEndTime:function(beginId,endId){
			//时间控件
			$(beginId).datepicker({
				dateFormat:'yy-mm-dd',
				maxDate:new Date(),
		        onSelect: function( startDate ) {
		            var $startDate = $(beginId);
		            var $endDate = $(endId);
		            var endDate = $endDate.datepicker('getDate');
		            if(endDate < startDate){
		                $endDate.datepicker('setDate', startDate - 3600*1000*24);
		            }
		            $endDate.datepicker( "option", "minDate", startDate );
		        }
			});
			$(endId).datepicker({
				dateFormat:'yy-mm-dd',
				maxDate:new Date(),
		        onSelect: function( endDate ) {
		            var $startDate = $(beginId);
		            var $endDate = $(endId);
		            var startDate = $startDate.datepicker( "getDate" );
		            if(endDate < startDate){
		                $startDate.datepicker('setDate', startDate + 3600*1000*24);
		            }
		            $startDate.datepicker( "option", "maxDate", endDate );
		        }
			});
		},
		/**
		 * 
		 * @param val 值
		 * @param length 长度
		 * @returns {String}
		 */
		valToLongShow : function(val,length){
			if(comm.isEmpty(val)||length<=0){
				return '';
			}
			return (val.length >= length)?'<span title="'+val+'">'+val.substring(0,length)+'...</span>':val;
		},
		/**
		 * 重要信息显示
		 * @param val 值
		 * @param start 开始长度
		 * @param end 结束长度
		 * @param repVal 替换值
		 */
		importantInfoShow : function(val,start,end,repVal){
			if(comm.isEmpty(val)||start<=0){
				return '';
			}
			var retVal = val.substring(0,start) + repVal;
			if(end > 0){
				retVal = retVal + val.substring(val.length - end,val.length);
			}
			return retVal;
		},
	        /** 时间格式验证 */
		checkTimeFormat:function(timeVal) {
	        var date = timeVal;
	        var result = date.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);

	        if (result == null)
	            return false;
	        var d = new Date(result[1], result[3] - 1, result[4]);
	        return (d.getFullYear() == result[1] && (d.getMonth() + 1) == result[3] && d.getDate() == result[4]);
	    },
		/**
		 * 进行AES进行加密(交易密码)
		 * @param word 	加密的内容
		 * @param key	加密的秘钥
		 */
		tradePwdEncrypt : function(word,key){				 		
			 var key  = CryptoJS.enc.Utf8.parse(key) ;	
			 var iv    = CryptoJS.enc.Utf8.parse('9999999999999999') ;			 
			 var srcs = CryptoJS.enc.Utf8.parse(CryptoJS.enc.parse(word)) ;
			 var encrypted = CryptoJS.AES.encrypt(srcs, key, { iv: iv,mode:CryptoJS.mode.CBC});						
			 return encrypted.toString() ;
		},
		/**
		 * 获取验证码地址
		 * @param type 验证码类型
		 * @returns
		 */
		generateSecuritCode : function(type){
			return comm.domainList[comm.getProjectName()]+comm.UrlList["generateSecuritCode"]+'?custId='+$.cookie('custId')+'&type='+type+'&loginToken='+$.cookie('token')+'&date='+new Date().getTime();
		},
		
		/**
		 * 验证验证码
		 */
		verificationCode : function(params,langObj,callback){
			comm.requestFun("verificationCode", params, callback, langObj);
		},
		/**
		 * 根据菜单id获取下级菜单
		 * @param parentId 菜单id
		 * @return 返回菜单实例ArrayJson
		 */
		findPermissionJsonByParentId : function(parentId){
			//获取缓存中的菜单
			var mens = comm.getCache("commCache", "ListMenu");
			//jsonFilter过滤
			return mens.filter(function(e) {
		        return e.parentId == parentId;
		    });
		},
		
		/**
		 * 验证日期的查询(开始日期、结束日期的name写死)
		 * @param formId 表单ID
		 */
		queryDateVaild : function(formID){
			return $("#"+formID).validate({
				rules : {
					search_beginDate : {
						required : true,
						endDate : "#search_endDate",
						oneyear : "#search_endDate"
					},
					search_endDate : {
						required : true
					}
				},
				messages : {
					search_beginDate : {
						required : comm.lang("common")[10001],
						endDate : comm.lang("common")[10003],
						oneyear : comm.lang("common")[10004]
					},
					search_endDate : {
						required : comm.lang("common")[10002],
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+2 top+30",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
		},
		/** 组合操作员信息"×××××(×××××)" */
		groupOperInfo:function(operCustId,callBack){
			cacheUtil.searchOperByCustId(operCustId,function(rsp){
				var retInfo="";
				
				if(rsp){
					retInfo=rsp.userName+"("+rsp.realName+")";
				}else{
					retInfo=operCustId;
				}
				callBack(retInfo);
			});
		},
		/** 获取randomToken" */
		getRandomToken:function(callBack){
			//封装客户号
			var param ={};
			param.custId =comm.getRequestParams()['custId'];//登陆用户客户号
			comm.requestFun("getToken", param, callBack, comm.lang("common"));
		},
		/**
		 * 小数转成百分比
		 * @param val 值
		 * @param n 位数
		 */
		toPercent : function (val,n){
			if(comm.isEmpty(val)){
				return '';
			}
			n = comm.isNotEmpty(n) ? n : 2;
			return (Math.round(val * 10000)/100).toFixed(n) + '%';
		},
        //统计字符串长度汉字当成两个英文字符
        countStrLength : function(str){
        	if(str == null || str == ""){
        		return 0;
        	}
        	var sum = 0;
        	for (var i=0; i<str.length; i++){
        		var c = str.charCodeAt(i);
        		if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)){
        			sum++;
        		}else{
        			sum+=2;
        		}
        	}
        	return sum;
        }
	});
	 return null ;
});
		
