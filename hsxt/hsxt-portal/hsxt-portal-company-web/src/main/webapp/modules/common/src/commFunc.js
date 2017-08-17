define(["jquery", "underscore"], function ($, _) {
	return window.comm = {
		/*
		取当前日期或时间
		 */
		getCurrDate : function () {
			var d = new Date();
			return this.formatDate(d, 'yyyy-MM-dd');
		},
		getCurrDateYMDHMS : function () {
			var d = new Date();
			return this.formatDate(d, 'yyyy-MM-dd hh:mm:ss');
		},
		// 获取今天开始和结束
		getTodaySE : function () {
			var d1 = comm.getCurrDate();
			return [d1, d1];
		},
		// 获取最近一周开始和结束
		getWeekSE : function () {
			var d1 = new Date();
			var e = this.formatDate(d1, 'yyyy-MM-dd');
			d1.setDate(d1.getDate() - 7); // 上一周
			var s = this.formatDate(d1, 'yyyy-MM-dd');
			return [s, e];
		},
		// 获取最近一月开始和结束
		getMonthSE : function () {
			var today = new Date();
			var day = today.getDate();
			var e = this.formatDate(new Date(), 'yyyy-MM-dd');
			today.setDate(0);
			var maxDay = today.getDate();
			if (day <= maxDay) {
				today.setDate(day);
			}
			var s = this.formatDate(today, 'yyyy-MM-dd');
			return [s, e];
		},
		//获取最近三个月开始和结束
		get3MonthSE:function (){
			var d1 = new Date();
			var e = this.formatDate(d1, 'yyyy-MM-dd');
			d1.setMonth(d1.getMonth() - 3);
			var s = this.formatDate(d1, 'yyyy-MM-dd');
			return [s, e];
		},
		//获取最近六个月开始和结束
		get6MonthSE:function (){
			var d1 = new Date();
			var e = this.formatDate(d1, 'yyyy-MM-dd');
			d1.setMonth(d1.getMonth() - 6);
			var s = this.formatDate(d1, 'yyyy-MM-dd');
			return [s,e];
		},
		//获取最近一年开始和结束
		get1YearsSE:function (){
			var d1 = new Date();
			var e = this.formatDate(d1, 'yyyy-MM-dd');
			d1.setFullYear(d1.getFullYear() - 1);
			var s = this.formatDate(d1, 'yyyy-MM-dd');
			return [s, e];
		},
		// 获取最近三年开始和结束
		get3YearsSE : function () {
			var d1 = new Date();
			var e = this.formatDate(d1, 'yyyy-MM-dd');
			d1.setFullYear(d1.getFullYear() - 3);
			var s = this.formatDate(d1, 'yyyy-MM-dd');
			return [s, e];
		},
		//将时间由毫秒数转化成日期格式
		formatDate : function (date, format) {
			if (!date || date == "0") {
				return "";
			}
			if (!format) {
				format = "yyyy-MM-dd hh:mm:ss";
			}
			if (typeof date == "string") {
				if (date.length == 8) {
					var arr = [date.substr(0, 4), date.substr(4, 2), date.substr(6, 2)];
				} else if (date.length == 14) {
					var arr = [date.substr(0, 4), date.substr(4, 2), date.substr(6, 2), date.substr(8, 2), date.substr(10, 2), date.substr(12, 2)];
				} else {
					var arr = date.split(/[^0-9]+/);
				}
				format = format.replace(/([a-z])\1+/ig, function (all, $1) {
						var result = {
							y : ~~arr[0],
							M : ~~arr[1],
							d : ~~arr[2],
							h : ~~arr[3],
							m : ~~arr[4],
							s : ~~arr[5]
						}
						[$1];
						if (result != undefined && result < 10) {
							result = "0" + result
						}
						return result || "";
					});
				return format;
			}
			format = format.replace(/([a-z])\1+/ig, function (all) {
					var first = all.charAt(0);
					if ("y M d h m s".indexOf(first) >= 0) {
						if (first == "y") {
							return all.length > 2
							 ? date.getFullYear()
							 : (date.getFullYear() + "").substr(2);
						}
						var result = {
							M : date.getMonth() + 1,
							d : date.getDate(),
							h : date.getHours(),
							m : date.getMinutes(),
							s : date.getSeconds()
						}
						[first];
						result != undefined && result < 10
						 && (result = "0" + result);
						return result;
					} else {
						return all;
					}
				});
			return format;
		},
		/*
		 * 替换特殊符号
		 *
		 */
		/*
		格式化金额
		 */
		formatMoneyNumber : function (moneyNum) {
			var result = isNaN((1 * moneyNum).toFixed(2)) ? (new Number(0).toFixed(2)) : (1 * moneyNum).toFixed(2);
			return /\./.test(result) ? result.replace(/(\d{1,3})(?=(\d{3})+\.)/g, "$1,") : result.replace(/(\d{1,3})(?=(\d{3})+\b)/g, "$1,");
		},
		//自动宽度
		autoWidth : function () {
			var container = document.getElementById("wrap");
			var contentWidth_1 = document.getElementById("contentWidth_1"); //宽度为500px所定义的ID
			var contentWidth_2 = document.getElementById("contentWidth_2"); //宽度为320px所定义的ID
			var contentWidth_3 = document.getElementById("contentWidth_3"); //宽度为800px所定义的ID
			var contentWidth_4 = document.getElementById("contentWidth_4"); //宽度为700px所定义的ID
			var width = document.documentElement.clientWidth; //获取浏览器可见区域的宽度
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
		setFocus : function (obj, text) { //输入框获得焦点时时触发
			obj.style.color = "black";
			if (obj.value == text) {
				obj.style.color = "black";
				obj.value = "";
			}
		},
		setBlur : function (obj, text) { //输入框失去焦点时触发
			if (obj.value == "") {
				obj.style.color = "#999997";
				obj.value = text;
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
		emptyCurrPath : function (path) {
			var rr = /http([s]?):\/\/(.*)\//g;
			var mathArr = rr.exec(path);
			return mathArr[0];
		},
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
		//国际化文案
		langConfig : {},
		//获取相应模块下的字段文案
		lang : function (name) {
			var str = this.langConfig[name] || '';
			return str;
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
		/***
				  *  确认对话框
				  *   
				  *
				  ***/				 
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
				 }  ,				 	
		/**
		 *  校验处理
		 *
		 *
		 */
		valid : function (validObj) {
			//"表单中form的id
			return $(validObj.formID).validate({
				rules : validObj.rules,
				messages : validObj.messages,
				success : function (element) {
					if (validObj.success) {
						validObj.success(element);
						return true;
					}
					$(element).tooltip();
					$(element).tooltip("destroy");
					if ($(element)[0] ){								    	 
						$('#' + $(element)[0].htmlFor).tooltip().tooltip("destroy");								     
					}
				},
				errorPlacement : function (error, element) { //此项配置可以省略，则使用默认
					if (validObj.error) {
						validObj.error(error, element);
						return false;
					}
					$(element).attr("title", $(error).text()).tooltip({
						position : {
							my : "left+" + (validObj.left || '100') + " top+" + (validObj.top || '5'),
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
					//error.appendTo(element.parent().next());  //把错误提示插入到当前元素中的父元素的同辈元素的下一个元素中
				}
			}).form();
		},
		/*
		  * 页面标签状态切换，默认显示标签状态切换
		  * 参数：
		  *	liObj: 当前的标签对象，如：$('#xx')
		  * idStr: 所有新增的标签对像的id，如：'#xx,#xx'
		  * idStr2: 用于切换详情div层
		  */
		 liActive : function(liObj, idStr, idStr2){
			if(idStr !=''){
				var o = $(idStr);
				o.addClass('tabNone');
				if (o.length == 1) {
					o.siblings('.tabNone').first().addClass('bgImgNone');
				}
				if(idStr2){
					$('#busibox').removeClass('none');
					$(idStr2).addClass('none').html('');
				}
			}	
			liObj.parent('ul').find('a').removeClass('active');
			liObj.removeClass('tabNone').find('a').addClass('active');	
		 },
		 /*
		  * 页面标签状态切换，切换到新增的页面标签
		  * 参数：
		  * liObj: 当前的标签对象，如：$('#xx')
		  */
		liActive_add : function(liObj){
			liObj.removeClass('tabNone');
			liObj.parent('ul').find('a').removeClass('active');
			liObj.find('a').addClass('active');
			liObj.next().removeClass('bgImgNone');
		},
		convertFloat : function (n) {
			var r = parseFloat(n || '0');
			return isNaN(r) ? 0 : r;
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
});