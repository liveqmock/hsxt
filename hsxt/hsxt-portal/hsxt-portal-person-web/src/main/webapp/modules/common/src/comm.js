define(["jquery", "underscore","commDat/common",'commSrc/cacheUtil'], function ($, _,commData) {
	window.comm = {
		//401出错跳转，402出错提示
		retCode : [210, 220, 215,160373],
		/*
		 * 设置全局缓存
		 */
		cache : {},
		/*
		 * 取任何指定的页面路径
		 */
		getUrl : function (moduleName, urlName) {
			return comm.domainList["ecWeb"] + "modules/" + moduleName + "/tpl/" + urlName;
		},
		firstDomain : function () {
			var url = document.domain;
			if (!comm.validateIp()) {
				var arr = url.split(".");
				url = "." + arr[arr.length - 2] + "." + arr[arr.length - 1];
			}
			return url;
		},
		validateIp : 　function() {
			var url = document.domain;
			var re = /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/;
			return re.test(url);
		},
		/*
		 *导航（链接）到指定的模块及页面路径
		 */
		navUrl : function (moduleName, urlName) {
			location.href = comm.getUrl(moduleName, urlName);
		},
		winOpen : function (moduleName, urlName) {
			window.open(comm.getUrl(moduleName, urlName));
		},
		/*
		取当前日期或时间
		 */
		getCurrDate : function (flag) {
			var d = new Date();
			return (flag == null || flag == undefined) ? d.format() : d.formatTime()
		},
		/**
		 * 时间戳转换为日期字符串
		 */
		getDateForTimestamp : function (timestamp, flag) {
			var d = new Date(parseFloat(timestamp));
			return (flag == null || flag == undefined) ? d.format() : d.formatTime()
		},
		// 获取今天开始和结束
		getTodaySE : function () {
			var d1 = comm.getCurrDate();
			return [d1, d1];
		},
		// 获取最近一周开始和结束
		getWeekSE : function () {
			var d1 = new Date();
			var e = d1.format();
			d1.setDate(d1.getDate() - 6); // 上一周
			var s = d1.format();
			return [s, e];
		},
		// 获取最近一月开始和结束
		getMonthSE : function () {
			var today = new Date();
			var day = today.getDate();
			var e = new Date().format();
			today.setDate(0);
			var maxDay = today.getDate();
			if (day <= maxDay) {
				today.setDate(day);
			}
			today.setDate(today.getDate() + 1);
			var s = today.format();
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
		/*
		格式化金额
		 */
		formatMoneyNumber : function (moneyNum) {
			//var result = isNaN((1 * moneyNum).toFixed(2)) ? (new Number(0).toFixed(2)) : (1 * moneyNum).toFixed(2);
			var result = isNaN(moneyNum)?new Number(0) :　moneyNum;
			//判断数字是否为正数还是负数
			if(parseFloat(result) > 0){
				result = (1 * moneyNum).toFixed(2);
			}
			if(parseFloat(result) < 0){
				result = (-(-1 * moneyNum).toFixed(2)).toString();
			}
			
//			var re = /^\d+$/;
//			//验证是否为正整数
//			if (re.test(result)) 
//			{ 
//				result=result+'.00';
//			}
			var re = /^-?\d+$/;
			//验证是否为整数
			if (re.test(result)) 
			{ 
				result=result+'.00';
			}
			
			//对于(+/-)150.5种处理
			var tempResult=result.split(".");
			if(tempResult[1].length==1){
				result=result+'0';
			}
			return /\./.test(result) ? result.replace(/(\d{1,3})(?=(\d{3})+\.)/g, "$1,") : result.replace(/(\d{1,3})(?=(\d{3})+\b)/g, "$1,");
		},
		//将时间由毫秒数转化成日期格式
		formatDate : function(date, format) {
			if(!date || date =="0"){
				return "";
			}
			if (!format) {
				format = "yyyy-MM-dd hh:mm:ss";
			}
			if(typeof date == "string"){
				if(date.length == 8){
					var arr = [date.substr(0,4),date.substr(4,2),date.substr(6,2)];
				}else if(date.length == 14){
					var arr = [date.substr(0,4),date.substr(4,2),date.substr(6,2),date.substr(8,2),date.substr(10,2),date.substr(12,2)];
				}else{
					var arr = date.split(/[^0-9]+/);
				}
				
				format = format.replace(/([a-z])\1+/ig,function(all,$1){
					var result = {
						y : ~~arr[0],
						M : ~~arr[1],
						d : ~~arr[2],
						h : ~~arr[3],
						m : ~~arr[4],
						s : ~~arr[5]  			
					}[$1];
					if(result!=undefined&&result<10){
						result = "0"+result
					}
					return result || "";
				});
				return format;
			}
			format = format.replace(/([a-z])\1+/ig,function(all){
				var first = all.charAt(0);
				if("y M d h m s".indexOf(first)>=0){
					if(first=="y"){
						return all.length>2
							? date.getFullYear()
							: (date.getFullYear()+"").substr(2);
					}
					var result = {
						M : date.getMonth() + 1,
						d : date.getDate(),
						h : date.getHours(),
						m : date.getMinutes(),
						s : date.getSeconds()    			
					}[first];
					result!=undefined&&result<10
						&&(result = "0"+result);
					return result;
				}else{
					return all;
				}
			});
			return format;
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
		显示对话框
		 */
		showDialog : function () {
			require(["commSrc/dialog"], function (tpl) {
				$(document.body).append(tpl);
				$('.comm-dialog-box').bind('click', function (e) {
					$('.comm-dialog-box').remove();
					$('.comm-dialog-bg').remove();
				});
			});
		},
		//自动宽度
		autoWidth : function () {
			var container = document.getElementById("wrap");
			var contentWidth_1 = document.getElementById("contentWidth_1"); //宽度为500px所定义的ID
			var contentWidth_2 = document.getElementById("contentWidth_2"); //宽度为320px所定义的ID
			var contentWidth_3 = document.getElementById("contentWidth_3"); //宽度为800px所定义的ID
			var contentWidth_4 = document.getElementById("contentWidth_4"); //宽度为700px所定义的ID
			var width = document.documentElement.clientWidth; //获取浏览器可见区域的宽度
			container.style.width = width <= 1200 ? "1200px" : "100%";
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
		/*
		 * 设置，获取，删除全局缓存
		 */
		setCache : function (module, name, data) {
			if (!this.cache[module]) {
				this.cache[module] = {};
			}
			if (!this.cache[module][name]) {
				this.cache[module][name] = data;
			}
		},

		getCache : function (module, name) {
			if (!this.cache[module]) {
				return null;
			}
			if (!this.cache[module][name]) {
				return null;
			}
			return this.cache[module][name];
		},

		delCache : function (module, name) {
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
		Require : function (urlNames, succback, errback, optional) {
			var urlArr = [];
			$.each(urlNames, function (key, urlObj) {
				var thisUrl = null;
				if ($.isPlainObject(urlObj)) {
					//thisUrl = comm.getDomainUrl( urlObj.url  , comm.clearDomain(urlObj.domain)   ) || '';
					thisUrl = comm.getDomainUrl(urlObj.url, urlObj.domain);
				} else {
					thisUrl = comm.getDomainUrl(urlObj, Config.domain);
				}
				urlArr.push(thisUrl);
			});
			require(urlArr, succback, errback, optional);
		},
		Define : function (urlNames, succback, optional) {
			var urlArr = [];
			$.each(urlNames, function (key, urlObj) {
				var thisUrl = null;
				if ($.isPlainObject(urlObj)) {
					//有指定域名
					if (urlObj.url.indexOf('text!') > -1 || urlObj.url.indexOf('css!') > -1) {
						//模板路径
						thisUrl = comm.formatTextUrl(urlObj.url, comm.domainList[urlObj.domain]);
					} else {
						//js 路径
						thisUrl = comm.getDomainUrl(urlObj.url, urlObj.domain);
					}
				} else {
					thisUrl = urlObj;
				}
				urlArr.push(thisUrl);
			});
			define(urlArr, succback, optional);
		},
		/*
		 * 替换当前的域名地址
		 */
		formatTextUrl : function (url, domain) {
			//去掉域名地址：require无.js全路径有点问题
			var domainName = domain.replace('http://' + location.host + '/', ''),
			urlName = null;
			if (!domain) {
				return "";
			} else {
				//设置需要代理路径的url
				var urlArr = url.split("!");
				urlName = urlArr[0] + '!' + domainName + urlArr[1];
				return urlName;
			}
		},
		i_confirm : function (text, callback, width) {
			$("#ques_content").html(text);
			$("#alert_ques").dialog({
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
		//弹窗
		i_alert : function (text, width) {
			$("#i_content").html(text);
			$("#alert_i").dialog({
				title : "提示信息",
				width : width || "400",
				/*此处根据文字内容多少进行调整！*/
				modal : true,
				buttons : {
					"关闭" : function () {
						$(this).dialog("destroy");
					}
				}
			});
		},
		
		/**
		  * 提示对话框
		  *
		  */
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
			 btnObj[btnOkName] =   function(){ obj.callOk && obj.callOk(); $(this).dialog("destroy");$('#dialog-confirm').remove();};
			 obj.del && (btnObj[btnOther] = function(){ obj.callOther && obj.callOther(); $(this).dialog("destroy");$('#dialog-confirm').remove();});
			 btnObj[btnCancelName] = function(){ obj.callCancel && obj.callCancel() ;$(this).dialog("destroy");$('#dialog-confirm').remove();}; 					 
			 if (obj.timeClose &&  /^\d+$/.test(obj.timeClose )  ){
			 	setTimeout(function(){
			 		if($("#dialog-confirm").length ){
						$("#dialog-confirm").dialog('destroy');
						$('#dialog-confirm').remove();
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
			try{
				$('#alert_i').dialog("destroy");
			}catch(e){};
		},
		//弹窗
		Thickdiv : function (title, text, width) {
			$("#i_content2").html(text);
			$("#alert_i2").dialog({
				title : title,
				width : width || "400",
				/*此处根据文字内容多少进行调整！*/
				modal : true,
				closeIcon : true
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
		//yes
		yes_alert : function (text, width) {
			$("#yes_content").html(text);
			$("#alert_yes").dialog({
				title : "提示信息",
				width : width || "400",
				modal : true,
				buttons : {
					"确定" : function () {
						$(this).dialog("destroy");
					}
				}
			});
		},
		//warn
		warn_alert : function (text, width) {
			$("#warn_content").html(text);
			$("#alert_warn").dialog({
				title : "提示信息",
				width : width || "400",
				modal : true,
				buttons : {
					"关闭" : function () {
						$(this).dialog("destroy");
					}
				}
			});
		},
		error_alert : function (text, width) {
			$("#error_content").html(text);
			$("#alert_error").dialog({
				title : "提示信息",
				width : width || "400",
				modal : true,
				buttons : {
					"关闭" : function () {
						$(this).dialog("destroy");
					}
				}
			});
		},
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
		closeLoading : function () {
			try{
				$("#showLoadIngDiv").dialog('destroy');
				$("#showLoadIngDiv").addClass('none');
			}catch(e){
			}
		},
		//图片服务器地址
		//imgFtpServer : "http://192.168.229.109"
		//imgFtpServer : "http://192.168.229.31:9099/v1/tfs/",
		//国际化文案配置
		langConfig : {},
		//获取文案字段
		lang : function (name) {
			var str = this.langConfig[name] || '';
			return str;
		},
		/**
		 * 替换字符串的null，undefined 'undefined'为''
		 */
		navNull : function (obj) {
			if (typeof(obj) == 'string') {
				obj = comm.trim(obj);
			}
			if (obj == null || obj == 'null' || obj == undefined || obj == 'undefined') {
				return '';
			}
			return obj;
		},
		//替换字符串的 null ‘null’ undefined ‘undefined’
		navNull : function(str,str2){
			if (typeof(str) == 'string') {
				str = comm.trim(str);
			}
			if (typeof(str2) == 'string') {
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
		 * 判断是空
		 */
		isEmpty : function (obj) {
			if (typeof(obj) == 'string') {
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
		isNotEmpty : function (obj) {
			if (typeof(obj) == 'string') {
				obj = comm.trim(obj);
			}
			if (obj == null || obj == undefined || obj == 'undefined' || obj == '' || obj == 'null') {
				return false;
			}
			return true;
		},
		/*
		 * 过滤特殊字符,如
		 * <,>,',",(,),[,],{,},%,\,/,^,@,&,_
		 *
		 *
		 */
		formatStr : function (str) {
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
		filterVal : function (divId) {
			$('#' + divId).find('input[type="text"],textarea').each(function () {
				var val = $(this).val();
				$(this).val(comm.formatStr(val));
			});
		},
		/**
		 * 去掉首尾空格
		 */
		trim : function (val) {
			return val.replace(/(^\s*)|(\s*$)/g, "");
		},
		/**
		 * 汉化时间控件
		 */
		datepicker : function (el) {
			$.datepicker.regional["zh-CN"] = {
				closeText : "关闭",
				prevText : "&#x3c;上月",
				nextText : "下月&#x3e;",
				currentText : "今天",
				monthNames : ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
				monthNamesShort : ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"],
				dayNames : ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
				dayNamesShort : ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
				dayNamesMin : ["日", "一", "二", "三", "四", "五", "六"],
				weekHeader : "周",
				dateFormat : "yy-mm-dd",
				firstDay : 1,
				isRTL : !1,
				showMonthAfterYear : !0,
				yearSuffix : "年"
			}
			$.datepicker.setDefaults($.datepicker.regional["zh-CN"]);
			$(el).datepicker({
				"dateFormat" : "yy-mm-dd",
				"maxDate" : new Date()
			});
		},
		/*
		*公共request post方法
		*url: string 或者 json {url:'', domain:''}
		*data: 可省略
		*callback: 回调函数
		*/
		requestPost : function (url, data, callback) {
			if(comm.checkUserLogin()){
				comm.showLoading();
				$.isFunction(data) && (callback = data, data = null), comm.Request(url, {
					type : 'POST',
					data : data,
					dataType : "json",
					success : function (response) {
						comm.closeLoading();
						callback(response);
					},
					error : function () {
						comm.closeLoading();
						comm.error_alert('请求数据出错！');
					}
				});
			}
		},
		// jquery.validate扩展验证
		addMethodValidator : function () {
			// 验证表单自定义方法,用于对比两个日期，第一个日期不能大于第二个日期（用于对比结束日期）
			$.validator.addMethod("endDate", function (value, element, params) {
				var startDate = value;
				var endDate = $(params).val(); // 参数是结束日期的ID
				if (startDate == "" || endDate == "") {
					return true;
				}
				var reg = new RegExp('-', 'g');
				startDate = new Date(Date.parse(startDate.replace(reg, "/")));
				endDate = new Date(Date.parse(endDate.replace(reg, "/")));
				return startDate <= endDate;
			}, "开始日期必须小于结束日期");
			// 验证表单自定义方法,用于对比两个日期，第一个日期不能大于第二个日期（用于对比开始日期）
			$.validator.addMethod("beginDate", function (value, element, params) {
				var startDate = $(params).val(); // 参数是开始日期的ID
				var endDate = value;
				if (startDate == "" || endDate == "") {
					return true;
				}
				var reg = new RegExp('-', 'g');
				startDate = new Date(Date.parse(startDate.replace(reg, "/")));
				endDate = new Date(Date.parse(endDate.replace(reg, "/")));
				return startDate <= endDate;
			}, "结束日期必须大于开始日期");
			// 验证表单自定义方法,用于对比对比文本框的值不能大于某span或div里的值
			$.validator.addMethod("greater", function (value, element, params) {
				var val = $(params).text(); // 某span或div里的值
				if (val == "") {
					val = $(params).val();
				}
				if (value == "" || val == "" || isNaN(value) || isNaN(val)) {
					return true;
				}
				return parseInt(value) <= parseInt(val);
			}, "数值不能大于指定范围");
			// 验证表单自定义方法,用于对比对比文本框的值不能小于某span或div里的值
			$.validator.addMethod("less", function (value, element, params) {
				var val = $(params).text(); // 某span或div里的值
				if (val == "") {
					val = $(params).val();
				}
				if (value == "" || val == "" || isNaN(value) || isNaN(val)) {
					return true;
				}
				return parseInt(value) >= parseInt(val);
			}, "数值不能小于指定范围");
			// 100的整数验证
			$.validator.addMethod("isNumTimes", function (value, element) {
				if (value % 100 == 0) {
					return true;
				} else {
					return false;
				}
			}, "请填写100的倍数");
			// 验证表单自定义方法,用于对比对比文本框的值是否合法的货币格式(小数点左面最多9位，小数点右面最多2位)
			$.validator.addMethod("huobi", function (value, element, params) {
				if (params) {
					var reg = /^(([1-9]{1}\d{0,8})|0)(\.\d{1,2})?$/;
					return reg.test($.trim(value));
				}
				return true;
			}, "请输入正确的货币格式");
			// 手机号码验证
			$.validator.addMethod("mobile", function (value, element) {
				var length = value.length;
				var mobile = /^1[3|4|5|8][0-9]\d{4,8}$/;
				return this.optional(element) || (length == 11 && mobile.test(value));
			}, "手机号码格式错误");
			// 手机号码验证
			jQuery.validator.addMethod("phoneCN", function(value, element) { 
				var tel = /^\d{3,5}-?\d{7,9}$/; 
				return this.optional(element) || (tel.test(value)); 
			}, "电话号码格式错误"); 
			//邮政编码验证
			jQuery.validator.addMethod("zipCode", function(value, element) { 
				var tel = /^[0-9]{6}$/; 
				return this.optional(element) || (tel.test(value)); 
			}, "邮政编码格式错误");
			jQuery.validator.addMethod("realName", function(value, element) { 
				var realname = /[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*/; 
				return this.optional(element) || (realname.test(value)); 
			}, "邮政编码格式错误");
			//验证表单自定义方法,用于对比对比文本框的值不能等于某span或div里的值
			$.validator.addMethod("equalContent", function (value, element, params) {
				var val = $(params).text(); //某span或div里的值
				if (val == "")
					val = $(params).val();
				if (value == "" || val == "")
					return true;
				return !(value === val);
			}, "您的变更申请内容相同，不能提交申请！");
			/**
			 * 验证两个元素的值不相等
			 */
			jQuery.validator.addMethod("unequalTo", function(value, element, param) {
				 if($(param).val()==value){
					 return false;
				 }
				 return true;
			});
		},
		// 交易类型转换描述
		transCodeRender : function (transCode) {
			return {
				'L_L_ACCU_POINT' : '消费积分',
				'L_E_ACCU_POINT' : '消费积分',
				'E_L_ACCU_POINT' : '消费积分',
				'L_L_CANCEL_ACCU_POINT' : '消费积分撤单',
				'L_E_CANCEL_ACCU_POINT' : '消费积分撤单',
				'E_L_CANCEL_ACCU_POINT' : '消费积分撤单',
				'P_POINT_TO_CASH' : '积分转货币',
				'P_POINT_INVEST' : '积分投资',
				'P_HSB_TO_CASH' : '互生币转货币',
				'P_INET_HSB_RECHARGE' : '网银兑换互生币',
				'P_INAL_HSB_RECHARGE' : '货币兑换互生币',
				'P_HSB_TO_CONSUME' : '互生币转消费',
				'P_POINT_TO_HSB' : '积分转互生币',
				'P_INVEST_BONUS' : '投资分红',
				'HSEC_HSB_CCY_STORE_REFUND' : '互生币支付撤单',
				'P_PRETRANS_CASH' : '货币转银行',
				'P_TRANS_CASH' : '货币转银行',
				'P_TRANS_REFUND' : '货币转银行失败转入',
				'P_INAL_PAY_CASH' : '货币账户支付',
				'P_INET_SALES_PAY' : '补办互生卡',
				'P_HSB_SALES_PAY' : '补办互生卡',
				'P_TEMP_SALES_PAY' : '补办互生卡',
				'HSEC_S_HSB_CCY_EMALL_REFUND' : '网上订单支付退货',
				'HSEC_PS_HSB_CCY_EMALL_REFUND' : '网上订单支付退货',
				'HSEC_S_HSB_DC_EMALL_REFUND' : '网上订单支付退货',
				'HSEC_PS_HSB_DC_EMALL_REFUND' : '网上订单支付退货',
				'HSEC_EBANK_EMALL_P_PAY' : '网上订单支付',
				'HSEC_EBANK_EMALL_V_PAY' : '网上订单支付',
				'HSEC_S_HSB_CCY_STORE_REFUND' : '互生币支付退货',
				'HSEC_PS_HSB_CCY_STORE_REFUND' : '互生币支付退货',
				'HSEC_S_HSB_DC_STORE_REFUND' : '互生币支付退货',
				'HSEC_PS_HSB_DC_STORE_REFUND' : '互生币支付退货',
				'HSEC_S_HSB_CCY_CANCEL_PAY' : '互生币支付撤单',
				'HSEC_PS_HSB_CCY_CANCEL_PAY' : '互生币支付撤单',
				'HSEC_S_HSB_DC_CANCEL_PAY' : '互生币支付撤单',
				'HSEC_PS_HSB_DC_CANCEL_PAY' : '互生币支付撤单',
				'HSEC_HSB_CCY_STORE_PAY' : '互生币支付',
				'HSEC_HSB_DC_STORE_PAY' : '互生币支付',
				'C_HSB_P_HSB_RECHARGE' : '企业代兑互生币',
				'HSEC_HSB_CCY_EMALL_PAY' : '网上订单支付',
				'HSEC_HSB_DC_EMALL_PAY' : '网上订单支付',
				'HSEC_PS_EBANK_EMALL_P_REFUND' : '网上订单网银支付退货',
				'HSEC_S_EBANK_EMALL_P_REFUND' : '网上订单网银支付退货',
				'HSEC_EBANK_EMALL_P_CANCEL_PAY' : '网上订单网银支付撤单',
				'HSEC_HSB_CCY_EMALL_CANCEL_PAY' : '网上订单支付撤单',
				'HSEC_HSB_DC_EMALL_CANCEL_PAY' : '网上订单支付撤单',
				'P_ACCIDENT_INSURANCE_SUBSIDIES' : '意外伤害补贴',
				'P_FREE_MEDICAL_SUBSIDIES' : '互生医疗补贴计划',
				'P_DEATH_SUBSIDIES' : '他人身故补贴',
				'P_CANCEL_PRETRANS_CASH' : '货币转银行撤单'
			}[transCode] || transCode;
		},
		// 渠道代码转换
		channelCodeRender : function (channelCode) {
			return {
				'WEB' : "网页终端",
				'POS' : "POS终端",
				'MCR' : "刷卡器终端",
				'MOBILE' : "移动终端",
				'HSPAD' : "互生平板",
				'SYS' : "系统终端",
				'IVR' : "语音终端",
				'SHOP' : "经营平台"
			}[channelCode] || channelCode;
		},
		 /**
		  * 提示对话框
		  *
		  */
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
						if ($("#dialog-alert").length){					 		 
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
						$("#dialog-alert").dialog("destroy");
					} 	 
				 }
			 })	 
		 },
		 /**
		 * 获取工程名称
		 */
		 getProjectName : function () {
			return "personWeb";
		 },
		 /**
          * 获取请求公共参数
          * @param params 参数信息
          */
         getRequestParams : function(params){
             if(!params){
                 params = {};
             }
             params.custId = $.cookie('custId');
             params.channelType=1;//渠道类型 必传参数不然loginToken验证失败
             params.pointNo =$.cookie('resNo');
             params.hsResNo =$.cookie('resNo');
             params.token = $.cookie('token');
             params.userName= comm.getCookie('custName');
             params.custName = comm.getCookie('custName');
             params.custEntName = comm.getCookie('entCustName');
             params.hs_isCard = comm.getCookie('hs_isCard');
             return params;
         },
         /**
 		 * 文字转换*
 		 * @param str 字符串
 		 * @param frontLen 前面保留位数
 		 * @param endLen 后面保留位数
 		 * @param flag   如果是两位后面不保留
 		 */
 		 plusXing : function (str,frontLen,endLen,flag) {
 			if(flag)
 			{
 				//非空验证
 				if(comm.isEmpty(str))
 				{
 					str = '';
 				}
 				
 				if(str.length <=2)
 				{
 					endLen = 0 ;
 				}
 				
 			}
 			var len = str.length-frontLen-endLen;    
 			var xing = '';    
 			for (var i=0;i<len;i++) 
 			{        
 				xing+='*';    
 			}
 			return str.substr(0,frontLen)+xing+str.substr(str.length-endLen);
 		},
 		
 		 /**
 		 * 获取遮挡的消费者名称*
 		 */
 		getHiddenCustName : function () {
 			var custName = comm.getCookie('custName');
 			if(custName=='null'||custName==null){
 				return '';
 			}
 			//1：身份证 2：护照:3：营业执照
 			var creType = comm.getCookie('creType');
 			if(creType ==3){//如果是营业执照 实名注册，custName为企业名称，不需要遮挡
 				return custName;
 			}
 			//如果是英文名实名注册  只保留首位，其余遮挡
 			if(/^[A-Za-z]+$/.test(custName)){
 				 return comm.plusXing(custName,1,0,true);
 			}
 			
 			//其他中文实名注册
 			return comm.plusXing(custName,1,0,true);
 		},
 		
         /**
		 * 获取查询公共参数
		 * @param params 参数信息
		 */
		 getQueryParams : function(params){
			if(!params){
				params = {};
			}
			params.channelType=1;//渠道类型 必传参数不然loginToken验证失败
			params.token = $.cookie('token');
			params.custId=$.cookie('custId');
			params.search_custId = $.cookie('custId');
			params.search_pointNo =$.cookie('pointNo');
			params.search_hsResNo =$.cookie('resNo');
			params.search_token = $.cookie('token');
			params.search_userName= comm.getCookie('custName');
			params.search_custName = comm.getCookie('custName');
			params.search_custEntName = comm.getCookie('entCustName');
			params.search_hs_isCard = comm.getCookie('hs_isCard');
	             
			return params;
		 },
         /* 
		         功能：获取cookies函数  
		         参数：name，cookie名字 
         */  
         getCookie : function (name){  
        	 return $.cookie(name);
         } ,
         /**
          * 获取国家名称
          */
         getCountryName : function(countryList, countryNo){
        	 if(!countryList || !countryNo){
        		 return "";
        	 }
        	 for(var key in countryList){
        		 if(countryNo == countryList[key].countryNo){
        			 return countryList[key].countryName;
        		 }
        	 }
        	 return "";
         },
		/**
		 * 请求后台方法封装
		 *  @param urlStr 请求的URL
		 *  @param params 参数信息
		 *  @param callback 回调函数
		 *  @param langObj 资源文件对象
		 */
		requestFun : function (urlStr, params, callback, langObj){
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
							comm.alertErrorMsg(langObj,response);
						  //comm.alertMessageByErrorCode(langObj, response.retCode);
						}
					},
					error: function(){
						comm.closeLoading();
						comm.error_alert(comm.lang('errorCodeMgs').requestFailed);
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
		requestFunForHandFail : function (urlStr, params, callback, langObj){
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
						comm.error_alert(comm.lang('errorCodeMgs').requestFailed);
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
		 * 提示错误信息
		 * 原则：优先查找传入的资源文件，找到了就直接提示，否则在comm.lang文件里找，找到了就直接提示，否则提示默认信息
		 * @param langObj 资源文件
		 * @param retCode 异常代码
		 */
		alertMessageByErrorCode : function(langObj, retCode){
			if(comm.removeNull(langObj) == ""){
				comm.error_alert(comm.lang('errorCodeMgs').requestFailedWithCode+retCode);
			}else{
				var errMgs = comm.removeNull(langObj[retCode]);
				if(errMgs != ""){
					comm.error_alert(errMgs);
				}else{
					errMgs = comm.removeNull(comm.lang('errorCodeMgs')[retCode]);
					if(errMgs != ""){
						comm.error_alert(errMgs);
					}else{
						comm.error_alert(comm.lang('errorCodeMgs').requestFailedWithCode+retCode);
					}
				}
			}
		},
		//删除银行卡信息
		/**
		 * 获取随机token方法
		 * @param  data 	请求参数json格式
		 * @param  callback	回调函数
		 */
		getToken : function (data, callback) {
			var jsonParam = cacheUtil.getParameters(data);
			comm.requestFun("findCardHolderToken" , jsonParam, callback,comm.lang("myHsCard"));
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
		 * 进行AES进行加密(登录密码)
		 * @param word 	加密的内容
		 * @param key	加密的秘钥
		 */
		encrypt :function(word,key){				 		
			 var key  = CryptoJS.enc.Utf8.parse(key) ;	
			 var iv    = CryptoJS.enc.Utf8.parse('9999999999999999') ;			 
			 var srcs = CryptoJS.enc.Utf8.parse(word) ;
			 var encrypted = CryptoJS.AES.encrypt(srcs, key, { iv: iv,mode:CryptoJS.mode.CBC});						
			 return encrypted.toString() ;
		} ,	
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
			var select = $(objId).selectList(content);
			if(defaultVal != null && defaultVal != undefined){
				select.selectListValue(defaultVal);
			}
			return select;
		},
		
		/**
		 * 初始化下拉框
		 * @param objId 需要绑定的下拉框元素
		 * @param objArray 枚举内容（来源于国际化枚举对象）
		 */
		initSelect : function(objId, objArray){
			var options = [];
			for(key in objArray){
				options.push({name:objArray[key], value:key});
			}
			$(objId).selectList({options : options});
		},
		/**
		 * 初始化下拉框
		 * @param objId 需要绑定的下拉框元素
		 * @param objArray 枚举内容（来源于国际化枚举对象）
		 */
		initSelectOption : function (objId, objArray){
			
			for(key in objArray){
				var option = $("<option>").val(key).text(objArray[key]); 
				$(objId).append(option); 
			}
			
		},
		
		/**
		 * 初始化下拉框
		 * @param objId 		需要绑定的下拉框元素
		 * @param objArray 		枚举内容（来源于国际化枚举对象）
		 * @param optionText 	实体中显示的字段
		 * @param optionValue	实体中保存的值
		 * @param showBlankOption	显示空值
		 */
		initOption : function (objId, objArray,optionText, optionValue, showBlankOption,defaultVal){
			var item = null;
			if(showBlankOption){
				$(objId).append($("<option>").val("").text(comm.lang('errorCodeMgs').pleaseSelect));
			}
			for(var i = 0; i <objArray.length ; i++ ){
				item = objArray[i];
				var option = $("<option>").val(item[optionValue]).text(item[optionText]); 
				$(objId).append(option);
			}
			if(defaultVal){
				$(objId).val(defaultVal);
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
					comm.error_alert(comm.lang('errorCodeMgs').requestFailed);
					resData = {flag:false, data: ""};
				}
			});
			return resData;
		},
		/***
		 * 初始化组合类型的下拉框
		 * @param objId			:下拉框ID
		 * @param optList		:下拉框的值
		 * @param selValue		:选中项目value(可选)
		 * @param allowBlank	:允许第一项显示为空 true false
		 * @param optionText 	:体中显示的字段
		 * @param optionValue	:实体中保存的值
		 */
		initCombox : function(objId, optList, selValue, allowBlank,optionText,optionValue){
			
			//临时变量
			var item = null;
			
			//是否支持第一项显示为空
			var aHtml = allowBlank?'<option value=" ">'+comm.lang('errorCodeMgs').pleaseSelect+'</option>':"";
			
			//遍历查询素有的结果
			for(var i = 0; i <optList.length ; i++ ){
				
				//获取第一个实体
				item = optList[i];
				
				//根据遍历的实体拼装显示的选项
				aHtml += '<option value="' + item[optionValue] + '"' + (item[optionValue] === selValue ? ' selected="selected"' : '')  + '>' + item[optionText] + '</option>';
				
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
			//$(".combobox_style").find("a").attr("title","");
			$(".custom-combobox").find("a").attr("title", "显示所有选项");
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
			var	desc = objArray[enumId];
			return (desc == undefined)?"":desc;
		},
		/**
		 * 获得服务器地址
		 * @param fileId 文件ID
		 */
		getFsServerUrl : function(fileId) {
			if(comm.isEmpty(fileId) || fileId == "NO_FILE"){
				return "./resources/images/noImg.gif";
			}else{
				var custId = $.cookie('custId');// 读取 cookie 客户号
				var token = $.cookie('token');//读取 cookie token
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
		 * 初始化图片div,查看大图
		 * @param objId 对象ID
		 * @param fileId 文件ID
		 * @param title 抬头（可选参数，默认图片查看）
		 * @param divId 绑定的Div（可选参数，默认showImage50）
		 * @param width 预览窗口宽度（可选参数，默认800）
		 * @param height 预览窗口高度（可选参数，默认600）
		 */
		initPicPreview : function(objId, fileId, title, divId, width, height) {
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
				if (comm.isNotEmpty(url) && "./resources/images/noImg.gif" != url) {
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
			})
		},
		/**
		 * 初始化图片div,查看大图
		 * @param objId 对象ID
		 * @param fileId 文件ID
		 * @param title 抬头（可选参数，默认图片查看）
		 * @param divId 绑定的Div（可选参数，默认showImage50）
		 * @param width 预览窗口宽度（可选参数，默认800）
		 * @param height 预览窗口高度（可选参数，默认600）
		 */
		initSamplePreview : function(objId, fileId, title, divId, width, height) {
			title = !title?"图片查看":title;
			width = !width?"800":width;
			height = !height?"600":height;
			divId = !divId?"#showImage50":divId;
			logId = divId+"Div";
			$(objId).attr("data-imgSrc", fileId);
			$(objId).click(function(e){
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
			})
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
					var imageUrl = comm.domainList[comm.getProjectName()]+comm.UrlList["generateSecuritCode"]+"?custId="+custId+"&type="+codesType+"&"+(new Date()).valueOf();
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
		 * 验证验证码
		 */
		verificationCode : function(params,langObj,callback){
			comm.requestFun("verificationCode", params, callback, langObj);
		},
		/***
		 * 上传图片预览功能需要先初始化上传按钮，
		 * 
		 * btnIds：上传按钮的id,多个ID格式为数据['#id1','#id2']  
		 * 
		 * labelIds:预览图片显示的标签的ID,与上传按键的顺序要对应,格为数据['#id1','#id2']
		 */
		initUploadBtn : function(btnIds, labelIds, width, height, defImg, maxSize){
			$.each(btnIds,function(i,o){
				$(o).uploadPreview({
					width: width?width:107, 
					height: height?height:64, 
					imgDiv: labelIds[i], 
					imgType: ["bmp", "jpeg", "jpg", "png"],
					maxSize: maxSize?maxSize:2
				});
				
			/*	$(o).change(function(){
					if($(o).val()==null || $(o).val()==''){
						var id=labelIds[i];
						$(id).attr("src",defImg!=null?defImg:"resources/images/noImg2.gif");
						$(id).children('img').attr("src",defImg!=null?defImg:"resources/images/noImg2.gif");
					}
				});*/
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
			$(imgId).attr("src", src);
			$(imgId).children().first().attr("width", "100%");
			$(imgId).children().first().attr("height", "100%");
			$(imgId).click(function(e){
				var buttons={};
				 buttons['关闭'] =function(){
					$(this).dialog("destroy");
				};
				var url = $(e.currentTarget).attr('src');
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
			})
		},
		/**
		 * 获取上传文件地址
		 */
		getUploadFilePath : function() {
			return comm.domainList[comm.getProjectName()]+comm.UrlList["fileupload"];
		},
		/***
		 * 支持多文件上传
		 * url:上传地址
		 * ids:文件的ID,多文件格式['id1','id2']
		 * callBack1:成功后的回调方法
		 * callBack2:失败后的回调方法
		 * param 参数
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
				var langObj=comm.lang('errorCodeMgs');
				comm.ipro_alert(langObj['comm']['uploading']);
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
							callBack2();
							//上传异常提示
							comm.alert({
								content: langObj['errorCodes'][response.retCode],
								imgClass: 'tips_error'
							});
						}
					},
					error: function(response){
						callBack2();
						comm.error_alert("文件上传失败,可能是文件过大");
						comm.ipro_alert_close();
					}
				});
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
			if(!localData || localData.length == 0){
				return;
			}
			return $.fn.bsgrid.init(gridId, {	 
				pageSize: 10 ,
				stripeRows: true,  //行色彩分 
				displayBlankRows: false ,   //显示空白行
			  	localData: localData ,
				operate : {
					detail:detail,
					del:del
				}
			});
		},
		/**
		 * 构建常用BsGrid
		 * @param gridId 要绑定的tableId
		 * @param url 请求数据url
		 * @param params json类型参数
		 * @param langObj 资源文件对象
		 * @param detail 自定义函数detail（可选参数）
		 * @param del 自定义函数del（可选参数）
		 * @param add 自定义函数add（可选参数）
		 * @param edit 自定义函数edit（可选参数）
		 * @param renderImg 自定义函数renderImg（可选参数）
		 */
		getCommBsGrid : function (gridId, url, params, langObj, detail, del, add, edit, renderImg){
			if(comm.checkUserLogin()){
				params = comm.getQueryParams(params),
				gridId = !gridId?"tableDetail":gridId;
				return $.fn.bsgrid.init(gridId, {
					url:comm.domainList[comm.getProjectName()]+comm.UrlList[url],
					otherParames : params,
					pageSize: 10,
					stripeRows: true,  //行色彩分 
					displayBlankRows: false,
					operate : {
						detail:detail,
						del:del,
						add:add,
						edit:edit,
						renderImg:renderImg
					}
				});
			}
		},
		/**
		 * 去掉null
		 * @param val 值
		 */
		removeNull : function(val) {
			if(val == null || val == undefined || val == 'undefined' || val == 'null'){
				return "";
			}
			return val;
		},
		/**
		 * 克隆json对象
		 * @param para json对象
		 */
		cloneJSON : function(para){
            var rePara = null;
            var type = Object.prototype.toString.call(para);
            if(type.indexOf("Object") > -1){
                rePara = jQuery.extend(true, {}, para);
            }else if(type.indexOf("Array") > 0){
                rePara = [];
                jQuery.each(para, function(index, obj){
                    rePara.push(jQuery.cloneJSON(obj));
                });
            }else{
                rePara = para;
            }
            return rePara;
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
        //设置cookie
        setCookie:function(name,value){
//            var Days = 30*12;   //cookie 将被保存一年  
//            var exp  = new Date();  //获得当前时间  
//            exp.setTime(exp.getTime() + Days*24*60*60*1000);  //换成毫秒  
//            document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+";path=/";
            //document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+";path="+comm.firstDomain();
        	$.cookie(name,value, { path: '/',domain:comm.firstDomain()});
        },
      //设置cookie
        setCookieForRootPath:function(name,value){
//            var Days = 30*12;   //cookie 将被保存一年  
//            var exp  = new Date();  //获得当前时间  
//            exp.setTime(exp.getTime() + Days*24*60*60*1000);  //换成毫秒  
//            document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+";path=/";
            //document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+";path="+comm.firstDomain();
        	$.cookie(name,value, { path: '/',domain:comm.firstDomain()});
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
		/**
		 * 验证日期的查询(开始日期、结束日期的name写死)
		 * @param formId 表单ID
		 */
		queryDateVaild : function(formID){
			return $("#"+formID).validate({
				rules : {
					search_startDate : {
						required : true,
						endDate : "#search_endDate",
						oneyear : "#search_endDate"
					},
					search_endDate : {
						required : true
					}
				},
				messages : {
					search_startDate : {
						required : comm.lang("errorCodeMgs")[10001],
						endDate : comm.lang("errorCodeMgs")[10003],
						oneyear : comm.lang("errorCodeMgs")[10004]
					},
					search_endDate : {
						required : comm.lang("errorCodeMgs")[10002]
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
		//base64编码
		base64Encrypt:function(s){
            var keyStr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=',
             chr1,chr2,chr3,enc1,enc2,enc3,enc4,output='';
            var i = 0;
            do{
                     chr1 = s.charCodeAt(i++);
                     chr2 = s.charCodeAt(i++);
                     chr3 = s.charCodeAt(i++);
                     enc1 = chr1 >> 2;
                     enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                     enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                     enc4 = chr3 & 63;
                     if (isNaN(chr2)){
                             enc3 = enc4 = 64;
                     }else if (isNaN(chr3)){
                             enc4 = 64;
                     }
                     output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2) + keyStr.charAt(enc3) + keyStr.charAt(enc4);
                     chr1 = chr2 = chr3 = enc1 = enc2 = enc3 = enc4 = '';
             } while (i < s.length);
             return output;
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
        },
		//验证token是否一致
		validTokenCK : function (){
			
			var returnValue = true ;
			var oldToken = $("#oldToken").val();	
			var token = comm.getRequestParams()["token"];
			
			//验证token是否相等
			if(oldToken != token) {
				returnValue = false ;
			}
			
			return returnValue ;
		},
		
		/*
		 *  解决运算精度问题
		 *  
		 */		
		accDiv : function(arg1,arg2){
			//两数相除
		    var t1=0,t2=0,r1,r2;
		    try{t1=arg1.toString().split(".")[1].length}catch(e){}
		    try{t2=arg2.toString().split(".")[1].length}catch(e){}
		    with(Math){
		        r1=Number(arg1.toString().replace(".",""))
		        r2=Number(arg2.toString().replace(".",""))
		        return (r1/r2)*pow(10,t2-t1);
		    }
		} ,
		accMul : function(arg1,arg2)	{
			//两数相乘
		    var m=0,s1=arg1.toString(),s2=arg2.toString();
		    try{m+=s1.split(".")[1].length}catch(e){}
		    try{m+=s2.split(".")[1].length}catch(e){}
		    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
		} ,
		accAdd : function(arg1,arg2){
			//两数相加
		    var r1,r2,m;
		    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
		    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
		    m=Math.pow(10,Math.max(r1,r2))
		    return (arg1*m+arg2*m)/m
		} 

	};
	
	return window.comm;
});
