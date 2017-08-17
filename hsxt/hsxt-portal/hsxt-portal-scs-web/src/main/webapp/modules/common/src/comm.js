define(["text!commTpl/frame/subNav.html","commDat/common",'commSrc/commFunc','commSrc/cacheUtil'] , function(subNavTpl,common) {
		//数字转货币
		Number.prototype.formatMoney = function (places, symbol, thousand, decimal) {
	        places = !isNaN(places = Math.abs(places)) ? places : 2;
	        symbol = symbol !== undefined ? symbol : "$";
	        thousand = thousand || ",";
	        decimal = decimal || ".";
	        var number = this,
	            negative = number < 0 ? "-" : "",
	            i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
	            j = (j = i.length) > 3 ? j % 3 : 0;
	        return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
	    };
	 	$.extend(comm,{				
				//401出错跳转，402出错提示 
				retCode : ['401','402','403','404','500','','','','8888','9999','160373'],
				//国际化文案
				langConfig : {},
				//获取相应模块下的字段文案
				lang : function(name) {
					var str = this.langConfig[name] || '';
					return str;
				},
				/*
				 * 设置全局缓存
				 */
				cache :  {},				
				
			    /*
				 *   取模块首页路径
				 */
				 getIndex : function(moduleName){
					 	return globalPath+"modules/"+ moduleName +"/tpl/index.html" ;
				 },
				 /*
				  *导航（链接）到模块的首页
				  */
				 navIndex : function(moduleName) {
						location.href = comm.getIndex(moduleName) ; 
				 },
				 
				 /*
				  * 取任何指定的页面路径
				  */
				 getUrl : function(moduleName,urlName){					 
						 return globalPath+"modules/"+ moduleName +"/tpl/"+urlName ;
				 },
				 valToLongShow : function(val,length){
						if(comm.isEmpty(val)||length<=0){
							return '';
						}
						return (val.length >= length)?'<span title="'+val+'">'+val.substring(0,length)+'...</span>':val;
					},
				 /*
				  *导航（链接）到指定的模块及页面路径
				  */
				 navUrl : function(moduleName,urlName) {
					 	location.href = comm.getUrl(moduleName,urlName) ; 
				 } ,
				 
				i_confirm : function (text, callback, width) {
					$("#i_c_content,#ques_content").text(text);
					$("#alert_i_c,#alert_ques").dialog({
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
				i_alert : function (text, width,callback) {
					$("#i_content").html(text);
					$("#alert_i").dialog({
						title : "提示信息",
						width : width || "400",
						/*此处根据文字内容多少进行调整！*/
						modal : true,
						buttons : {
							"关闭" : function () {
								$(this).dialog("destroy");
								if(callback)callback();
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
					try{$('#alert_i').dialog("destroy");}catch(e){} 
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
				error_alert : function (text, width,position) {
					$("#error_content").html(text);
					$("#alert_error").dialog({
						title : "提示信息",
						width : width || "400",
						position:position||"center",
						modal : true,
						buttons : {
							"关闭" : function () {
								$(this).dialog("destroy");
							}
						}
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
				/**
				 * 补充月份，日期位数
				 */
				fillNumber : function(num){
					return num<10?"0"+num:num;
				},
				 /*  下面的方法名等在此对象中被禁用
				  *  getCurrDate ， formatDate ，formatMoneyNumber ，setFocus ，setBlur ，
				  *  setCache ，getCache ， delCache ， Require ， Define ，emptyCurrPath，
				  *  langConfig ， lang ，formatStr  ，confirm ，  alert
				  *
				  */
				/***
				 * 获取随机token码
				 */
				getToken:function(callback){
					comm.Request({url:"findCardHolderToken", domain:"scsWeb"},{
						data:comm.getRequestParams(),
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
				 * 获取工程名称
				 */
				getProjectName : function () {
					return "scsWeb";
				},
				/**
				 * 获取操作信息
				 */
				getOperNo : function(){
					return this.removeNull($.cookie('custName'));
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
				 * 获取操作员账户名称
				 */
				getCookieOperNoName : function() {
					return comm.getCookie('custName')+"("+comm.getCookie('operName')+")";
				},
				/**
				 * 获取登陆企业互生号
				 */
				getPointNo : function(){
					return this.removeNull($.cookie('pointNo'));
				},
				/**
		          * 获取请求公共参数
		          * @param params 参数信息
		          */
		        getRequestParams : function(params){
	                 if(!params){
	                	 params = {};
	                 }
	                 params.channelType=1;//渠道类型 必传参数不然loginToken验证失败
	                 params.custId = $.cookie('custId');//登陆操作用户客户号
	                 params.pointNo = $.cookie('pointNo');//登陆用户企业互生号
	                 params.hsResNo = $.cookie('pointNo');//登陆用户企业互生号
	                 params.entResNo = $.cookie('pointNo');//登陆用户企业互生号
	                 params.token = $.cookie('token');//登陆token
	                 params.entCustId = $.cookie('entCustId');//登陆企业客户号
	                 params.custName = comm.getCookie('custName');//登陆用户客户名称 (0000)
	                 params.custEntName = comm.getCookie('entName');//企业名称
	                 params.entResType = $.cookie('entResType');//登陆用户企业类型
	                 params.operName = comm.getCookie('operName');//登陆用户客户名称 (张三)
	                 params.cookieOperNoName = this.getCookieOperNoName();//操作员信息 ( 0000(张三) )
	                 return params;
		         },
		         /**
				 * 获取查询公共参数
				 * @param params 参数信息
				 */
				getQueryParams : function(params){
					if(!params){
						params = {};
					}
					params.token = $.cookie('token');
					params.custId=$.cookie('custId');
					params.channelType=1;//渠道类型 必传参数不然loginToken验证失败
					params.search_custId = $.cookie('custId');//登陆用户客户号
					params.search_pointNo = $.cookie('pointNo');//登陆用户企业互生号
					params.search_hsResNo = $.cookie('pointNo');//登陆用户企业互生号
					params.search_token = $.cookie('token');//登陆token
					params.search_custName = comm.getCookie('custName');//登陆用户客户名称
	                params.search_entCustId = $.cookie('entCustId');//登陆用户企业客户号
					return params;
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
		 		 * 文字转换*
		 		 * @param str 字符串
		 		 * @param frontLen 前面保留位数
		 		 * @param endLen 后面保留位数
		 		 * @param flag   如果是两位后面不保留
		 		 */
		 		 plusXing : function (str,frontLen,endLen,flag) {
		 			if(flag)
		 			{
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
						comm.error_alert(comm.lang('common').requestFailedWithCode+retCode);
					}else{
						var errMgs = comm.removeNull(langObj[retCode]);
						if(errMgs != ""){
							comm.error_alert(errMgs);
						}else{
							errMgs = comm.removeNull(comm.lang('common')[retCode]);
							if(errMgs != ""){
								comm.error_alert(errMgs);
							}else{
								comm.error_alert(comm.lang('common').requestFailedWithCode+retCode);
							}
						}
					}
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
				/**
				 * 双签用户校验，先获取随机token，然后对密码加密，最后传入到后台校验，如果通过返回用户ID，反之抛出异常
				 * @param userName 用户名称
				 * @param pwd 密码
				 * @param checkType 校验类型
				 */
				verifyDoublePwd : function(userName, pwd, checkType, callBack){
					var langObj = comm.lang('common');//资源文件
					comm.requestFun("findCardHolderToken", null, function(res){
						var params = {};
						params.userName = userName;
						params.passWord = comm.encrypt(pwd, res.data);
						params.checkType = checkType;
						params.randomToken = res.data;
						comm.requestFun("verifyDoublePwd", params, callBack, langObj);
					}, langObj);
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
								self.closeLoading();
								//非空验证
								if (response.retCode ==22000){
									callback(response);	
								}else{
									comm.alertMessageByErrorCode(langObj, response.retCode);
								}
							},
							error: function(){
								self.closeLoading();
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
				 * 同步请求后台方法封装
				 *  @param urlStr 请求的URL
				 *  @param params 参数信息
				 *  @param langObj 资源文件对象
				 */
				asyncRequestFun : function (urlStr, params, langObj){
					if(comm.checkUserLogin()){
						comm.showLoading();
						var resData = null;
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
						return resData;
					}
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
					var select = $(objId).selectList(content);
					if(defaultVal != null && defaultVal != undefined){
						select.val("");
						select.attr('optionValue', "");
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
					if(null == defaultOptions){
						defaultOptions = false;
					}
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
								if(!(undefined == obj.cityNameCn ||undefined == obj.cityNo)){
									options.push({name:obj.cityNameCn, value:obj.cityNo});
								}
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
						select.val("");
						select.attr('optionValue', "");
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
				 * tips:未找到时提示信息
				 */
				initBankCombox : function(objId, bankArray, selValue, allowBlank, tips){
					var aHtml = allowBlank?'<option value=""></option>':'';
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
						"margin-top": "2px",
						"border":"1px solid #CCC"
					});
					if(selValue != null && selValue != undefined){
						$(objId).selectListValue(selValue);
					}
					$(".combobox_style").find("a").attr("title","");
					if(tips){
						$(objId).combobox({tips:tips});
					}
					if(allowBlank){
						$('.ui-autocomplete-input').attr('placeholder','--请选择--');
					}
				},
				/***
				 * 初始化组合类型的下拉框
				 * objId:下拉框ID
				 * optList:下拉框的值
				 * selValue:选中项目value(可选)
				 * allowBlank:允许第一项显示为空
				 */
				initSelect1 : function(objId, optList, selValue, allowBlank){
					var aHtml = allowBlank?'<option value=""></option>':'';
					if(optList){
						for(var key in optList){
							aHtml += '<option value="' + optList[key] + '">' + optList[key] + '</option>';
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
					var	desc = objArray[enumId];
					return (desc == undefined)?"":desc;
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
				/**
				 * 控制数据加载BsGrid
				 */
				controlLoadBsGrid:function(autoLoad,gridId, url, params, langObj, detail, del, add, edit, renderImg, selectRowEvent, unselectRowEvent){
					return comm.bulidCommBsGrid(autoLoad,gridId, url, params, langObj, detail, del, add, edit, renderImg, selectRowEvent, unselectRowEvent);
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
				 * @param selectRowEvent 自定义函数selectRowEvent（可选参数）
				 * @param unselectRowEvent 自定义函数unselectRowEvent（可选参数）
				 */
				getCommBsGrid : function (gridId, url, params, langObj, detail, del, add, edit, renderImg, selectRowEvent, unselectRowEvent){
					return comm.bulidCommBsGrid(true,gridId, url, params, langObj, detail, del, add, edit, renderImg, selectRowEvent, unselectRowEvent);
				},
				/**
				 * 构建常用BsGrid
				 * @param autoLoad 自动加载数据
				 * @param gridId 要绑定的tableId
				 * @param url 请求数据url
				 * @param params json类型参数
				 * @param langObj 资源文件对象
				 * @param detail 自定义函数detail（可选参数）
				 * @param del 自定义函数del（可选参数）
				 * @param add 自定义函数add（可选参数）
				 * @param edit 自定义函数edit（可选参数）
				 * @param renderImg 自定义函数renderImg（可选参数）
				 * @param selectRowEvent 自定义函数selectRowEvent（可选参数）
				 * @param unselectRowEvent 自定义函数unselectRowEvent（可选参数）
				 */
				bulidCommBsGrid:function(autoLoad,gridId, url, params, langObj, detail, del, add, edit, renderImg, selectRowEvent, unselectRowEvent){
					if(comm.checkUserLogin()){
						var isAutoLoad=true;
						if(!autoLoad){isAutoLoad=false;}
						
						params = comm.getQueryParams(params),
						gridId = !gridId?"tableDetail":gridId;
						return $.fn.bsgrid.init(gridId, {
							url:comm.domainList[comm.getProjectName()]+comm.UrlList[url],
							otherParames : params,
							pageSize: 10,
							stripeRows: true,  //行色彩分 
							displayBlankRows: false,
							autoLoad:isAutoLoad,
							operate : {
								detail:detail,
								del:del,
								add:add,
								edit:edit,
								renderImg:renderImg
							},
							event:{
								selectRowEvent:selectRowEvent,
								unselectRowEvent:unselectRowEvent
							}
						});
					}
				}
				,
				/**
				 * 获得服务器地址
				 * @param fileId 文件ID
				 */
				getFsServerUrl : function(fileId) {
					if(comm.isEmpty(fileId) || fileId == "NO_FILE"){
						return "./resources/img/noImg.gif";
					}else{
						var custId = $.cookie('custId');//登陆用户客户号
						var token = $.cookie('token');//登陆token
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
						if (comm.isNotEmpty(url) && "./resources/img/noImg.gif" != url) {
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
						$(divId).attr('src', (url==''||url==null)?$(this).attr('src'):url);
						$(logId).dialog({
							title : title,
							width : width,
							height : height,
							modal : true,
							buttons : buttons
						});
					})
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
				 * 获取服务公司类型
				 */
				getCookieBusinessType : function(){
					return $.cookie('cookieBusinessType');
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
						return ocard;
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
							imgType: ["bmp", "jpeg", "jpg", "png"],
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
				/**
				 * 获取上传文件地址
				 */
				getUploadFilePath : function() {
					return comm.domainList[comm.getProjectName()]+comm.UrlList["fileupload"];
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
				/***
				 * 支持多文件上传
				 * url:上传地址
				 * ids:文件的ID,多文件格式['id1','id2']
				 * callBack1:成功后的回调方法
				 * callBack2:失败后的回调方法
				 * param 参数
				 */
				uploadFile : function(url, ids, callBack1, callBack2, param){
					url = !url?comm.getUploadFilePath():url;
					if(!param){
						param = {};
					}
					param = comm.getRequestParams(param);
					//文件上传token custid找不到
					url = url+"?loginToken="+param.token+"&custId="+param.custId+"&channelType=1";
					var self=this;
					var langObj=comm.lang('common');
					
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
								//上传异常提示
								comm.alert({
									content: langObj['errorCodes'][response.retCode],
									imgClass: 'tips_error',
								});
								callBack2();
							}
						},
						error: function(response){
							comm.error_alert("文件上传失败,可能是文件过大");
							comm.ipro_alert_close();
							callBack2();
						}
					});
					
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
		        //设置cookie
		        setCookie:function(name,value){
		            var Days = 30*12;   //cookie 将被保存一年  
		            var exp  = new Date();  //获得当前时间  
		            exp.setTime(exp.getTime() + Days*24*60*60*1000);  //换成毫秒  
		            document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+";path=/";  
		        },
				/** 获取所有cookie值 */
				getCookieValue:function(){
					var cookieParams=comm.getRequestParams(null);
					cookieParams.lastLoginDate=$.cookie('lastLoginDate');//上一次登录时间
					cookieParams.lastLoginIp=$.cookie('lastLoginIp');//上一次登录ip		
					return cookieParams;
				},
				/**
				 * 得到字符串格式的日期
				 */
				getDateStr:function(date) {
					if(!date || date =="0"){
						return "";
					}
					if(typeof date == "string"){
						return date;
					}else{
						return new Date(date).format();
					}
				},
				 
				 //格式化货币
				formatMoney:function(num){
					return Number(num).formatMoney(2,'',',','.');
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
				/**
				 * 替换字符串的null，undefined 'undefined'为''
				 */
				navNull:function(str,str2){
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
				 * 判断是空
				 */
				isEmpty:function(obj){
					if(typeof(obj) == 'string'){
						obj = comm.trim(obj);
					}
					if(obj==null || obj==undefined || obj=='undefined' ||obj==''||obj=='null'){
						return true;
					}
					return false;
				},
				/**
				 * 判断非空
				 */
				isNotEmpty:function(obj){
					if(typeof(obj) == 'string'){
						obj = comm.trim(obj);
					}
					if(obj==null || obj==undefined || obj=='undefined' ||obj==''||obj=='null'){
						return false;
					}
					return true;
				},
				/*
				  * 过滤特殊字符,如
				  * <,>,',",(,),[,],{,},%,\,/,^,@,&,_
				  *
				  * 过滤特殊字符，并去首尾空格
				  */
				 formatStr :function(str){
					var string =  str;
					if(string) {
						string = string.replace(/</gm,'＜');
						string = string.replace(/>/gm,'＞');
						string = string.replace(/\'/gm,'＇');
						string = string.replace(/\"/gm,'＂');
						string = string.replace(/\(/gm,'（');
						string = string.replace(/\)/gm,'）');
						string = string.replace(/\[/gm,'［');
						string = string.replace(/\]/gm,'］');
						string = string.replace(/\{/gm,'｛');
						string = string.replace(/\}/gm,'｝');
						string = string.replace(/\%/gm,'％');
						string = string.replace(/\\/gm,'＼');
						string = string.replace(/\//gm,'／');
						string = string.replace(/\^/gm,'＾');
						string = string.replace(/@/gm,'＠');
						string = string.replace(/&/gm,'＆');
						string = string.replace(/_/gm,'＿');
						string = string.replace(/,/gm,'，');
						string = $.trim(string);
					}
					 return string;
				 },
				 /**
				  * 过滤div里的特殊字符
				  */
				 filterVal : function (divId){
					 $('#'+divId).find('input[type="text"],textarea').each(function(){
						var val=$(this).val();
						$(this).val(comm.formatStr(val));
					});
				 },
				 /**
				  * 系统文档下载(大陆)
				  */
				 sysDocDownloadLand : function (){
					 var data = [
					       {'fileType':'DOC','fileName':'互生系统应用合同(成员企业)','describe':'成员企业应用系统使用','downLoad':'/template/china/MemberContact.doc'},
					       {'fileType':'DOC','fileName':'互生系统应用合同(托管企业)','describe':'托管企业应用系统使用','downLoad':'/template/china/TrustedContact.doc'},
					       {'fileType':'DOC','fileName':'互生系统应用合同(老托管企业)','describe':'托管企业应用系统使用','downLoad':'/template/china/TrustedContact_old.doc'},
					       {'fileType':'DOC','fileName':'互生系统应用合同(服务公司)','describe':'服务公司应用系统使用','downLoad':'/template/china/ServiceContact.doc'},
					       {'fileType':'DOC','fileName':'互生系统应用合同(老服务公司)','describe':'服务公司应用系统使用','downLoad':'/template/china/ServiceContact_old.doc'},
					       {'fileType':'DOC','fileName':'企业参与“消费100以上送5000消费抵扣券”活动协议书','describe':'互生托管企业系统免费使用协议','downLoad':'/template/china/ConsumptionTicketProtocol.doc'},
					       {'fileType':'DOC','fileName':'企业应用互生系统申报信息登记表','describe':'所有应用系统企业使用','downLoad':'/template/china/CompanyDeclareInfoReg.doc'},
					       {'fileType':'DOC','fileName':'互生系统区域服务公司与创业人员（创业帮扶）协议','describe':'所有应用系统企业使用','downLoad':'/template/china/InfoApplicationA.doc'}
					   ];
					 return data;
				 },
				 /**
				  * 系统文档下载(香港)
				  */
				 sysDocDownloadHK : function(){
					 var data = [
							{'fileType':'DOC','fileName':'互生系统应用合同(成员企业)','describe':'成员企业应用系统使用','downLoad':'/template/hk/MemberContact_HK.doc'},
							{'fileType':'DOC','fileName':'互生系统应用合同(托管企业)','describe':'托管企业应用系统使用','downLoad':'/template/hk/TrustedContact_HK.doc'},
							{'fileType':'DOC','fileName':'互生系统应用合同(服务公司)','describe':'服务公司应用系统使用','downLoad':'/template/hk/ServiceContact_HK.doc'},
							{'fileType':'DOC','fileName':'互生系统应用合同(老服务公司)','describe':'服务公司应用系统使用','downLoad':'/template/hk/ServiceContact_old_HK.doc'},
							{'fileType':'DOC','fileName':'互生系统区域服务公司与创业人员（创业帮扶）协议','describe':'所有应用系统企业使用','downLoad':'/template/hk/InfoApplicationA_HK.doc'} 
					    ];
					 return data;
				 },
				 /**
				  * 系统文档下载(澳门)
				  */
				 sysDocDownloadMA : function(){
					 var data = [
							{'fileType':'DOC','fileName':'互生系统应用合同(成员企业)','describe':'成员企业应用系统使用','downLoad':'/template/mac/MemberContact_MA.doc'},
							{'fileType':'DOC','fileName':'互生系统应用合同(托管企业)','describe':'托管企业应用系统使用','downLoad':'/template/mac/TrustedContact_MA.doc'},
							{'fileType':'DOC','fileName':'互生系统应用合同(服务公司)','describe':'服务公司应用系统使用','downLoad':'/template/mac/ServiceContact_MA.doc'},
							{'fileType':'DOC','fileName':'互生系统应用合同(老服务公司)','describe':'服务公司应用系统使用','downLoad':'/template/mac/ServiceContact_old_MA.doc'},
							{'fileType':'DOC','fileName':'互生系统区域服务公司与创业人员（创业帮扶）协议','describe':'所有应用系统企业使用','downLoad':'/template/mac/InfoApplicationA_MA.doc'} 
					    ];
					 return data;
				 },
				 /**
				  * 系统文档下载(台湾)
				  */
				 sysDocDownloadTW : function(){
					 var data = [
							{'fileType':'DOC','fileName':'互生系统应用合同(成员企业)','describe':'成员企业应用系统使用','downLoad':'/template/tw/MemberContact_TW.doc'},
							{'fileType':'DOC','fileName':'互生系统应用合同(托管企业)','describe':'托管企业应用系统使用','downLoad':'/template/tw/TrustedContact_TW.doc'},
							{'fileType':'DOC','fileName':'互生系统应用合同(服务公司)','describe':'服务公司应用系统使用','downLoad':'/template/tw/ServiceContact_TW.doc'},
							{'fileType':'DOC','fileName':'互生系统区域服务公司与创业人员（创业帮扶）协议','describe':'所有应用系统企业使用','downLoad':'/template/tw/InfoApplicationA_TW.doc'} 
					    ];
					 return data;
				 },
				 /**
				  * 去掉首尾空格
				  */
				 trim : function (val){
					 return val.replace(/(^\s*)|(\s*$)/g,"");
				 },
				 /**
				  * 验证创业人员
				  */
				 vailOneT : function(val){
					 var corp = /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)([0][1-9]|[1][0])([0]{4})$/;
					 return (corp.test(val));
				 },
				subMenu_scroll : function(currentUl){
						$('.sub_arrow_right, .sub_arrow_left').unbind('click');
			
						var ul_width = 0,
							click_num = 0,
							move_width = 0,
							currentUl_left = 0,
							/*max_width = 1110,*/
							subNavLi = [],
							div_width = currentUl.parent('div').width();
							currentUl_length = currentUl.children("li").length;
							
						currentUl.children("li").each(function(i){
								
							subNavLi[i] = $(this).width() + 20;
							ul_width += subNavLi[i];
							
						});
						
						currentUl.css("left", 0)
							.siblings('ul').css("left", 0);
						
						
						if(ul_width > div_width){
							currentUl.css("width", ul_width);
							$(".sub_arrow_right, .sub_arrow_left").removeClass('tabNone');	
						}
						else{
							$(".sub_arrow_right, .sub_arrow_left").addClass('tabNone');
						}
						
						$(".sub_arrow_right").click(function(){
			
							if(Math.abs(currentUl_left) < (ul_width - div_width)){
								move_width = currentUl_left - subNavLi[click_num];
								currentUl.css("left", move_width + "px");
								currentUl_left = currentUl.position().left;
								click_num += 1;
							}
			
						});
						
						$(".sub_arrow_left").click(function(){
							
							if(currentUl_left < 0){
								move_width = currentUl_left + subNavLi[click_num - 1];
								currentUl.css("left",move_width + "px");
								currentUl_left = currentUl.position().left;
								click_num -= 1;
							}
							
						});		
							
					},
						/*
					取当前日期
				 */
				getCurrDate : function(){
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
					格式化金额
				 */
				  formatMoneyNumber : function(moneyNum) {
						var result = isNaN((1 * moneyNum).toFixed(2)) ? (new Number(0).toFixed(2)) : (1 * moneyNum).toFixed(2);
						return /\./.test(result) ? result.replace(/(\d{1,3})(?=(\d{3})+\.)/g, "$1,") : result.replace(/(\d{1,3})(?=(\d{3})+\b)/g, "$1,");
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
				  showDialog : function(){
					   
					   require(["commSrc/dialog"],function(tpl){						   
						   $(document.body).append(tpl);
							   		$('.comm-dialog-box').bind('click',function(e) {										
											$('.comm-dialog-box').remove();
											$('.comm-dialog-bg').remove();
									});
					   });
						
				  },
				  //自动宽度
				  autoWidth:function(){
						var container = document.getElementById("wrap");
						var contentWidth_1 = document.getElementById("contentWidth_1");//宽度为500px所定义的ID
						var contentWidth_2 = document.getElementById("contentWidth_2");//宽度为320px所定义的ID
						var contentWidth_3 = document.getElementById("contentWidth_3");//宽度为800px所定义的ID
						var contentWidth_4 = document.getElementById("contentWidth_4");//宽度为700px所定义的ID
						var width = document.documentElement.clientWidth;//获取浏览器可见区域的宽度
							
						if(width <= 1200){
							container.style.width  = "1200px";
						}else{
							container.style.width  = "100%";	
						}
						if(width <= 830){
							if(contentWidth_1){
								contentWidth_1.style.width = "500px";
							}
							if(contentWidth_2){
								contentWidth_2.style.width= "320px";
							}
							if(contentWidth_3){
								contentWidth_3.style.width= "800px";
							}
							if(contentWidth_4){
								contentWidth_4.style.width= "700px";
							}
							
						}else{
							if(contentWidth_1){
								contentWidth_1.style.width = "100%";
							}
							if(contentWidth_2){
								contentWidth_2.style.width = "100%";
							}
							if(contentWidth_3){
								contentWidth_3.style.width = "100%";
							}
							if(contentWidth_4){
								contentWidth_4.style.width = "100%";
						}
						
					}
							
				} ,
				
				setFocus:function(obj,text){//输入框获得焦点时时触发
	
					obj.style.color="black";
					if(obj.value == text){
						obj.style.color="black";
						obj.value="";
						}
				} ,
					
				setBlur: function(obj,text){//输入框失去焦点时触发
					
					if(obj.value == ""){
						obj.style.color="#999997";
						obj.value=text;
					}
				} ,
				
				/*
				 * 设置，获取，删除全局缓存
				 */
				setCache : function(module, name ,data){
					if (!this.cache[module]){
						this.cache[module] = {};
					} 
					if (!this.cache[module][name]){
						this.cache[module][name] = data;
					} 
					
				} ,
				
				getCache : function(module, name){
					if (!this.cache[module]){
						return null ;
					}
						
					if (!this.cache[module][name]){
						return null;
					} 
					return this.cache[module][name];
				} ,
				
				delCache : function(module, name){
					if (!this.cache[module]){
						return  ;
					}  else {
						delete this.cache[module][name];
					}
				},

				//配置datatable参数
				datatableCommon : function(){
                    var dataTSetting = {    
                            //"bJQueryUI" : true,
                            "bFilter" : false,
                            "sPaginationType" : "full_numbers",
                            "sDom" : '<""l>t<"F"fp>',
                            "processing" : true,
                            "serverSide" : true,
                            "bAutoWidth" : false, // 自适应宽度
                            "bLengthChange" : false,
                            "bSort" : false,
                            "bScrollAutoCss": true,
                            "sScrollY":'300',
                            "iDisplayLength":10,
                            "bRetrieve":true,//用于指明当执行dataTable绑定时，是否返回DataTable对象
                            "oLanguage" : {
                                "sZeroRecords" : "没有找到符合条件的数据",
                                "sInfo" : "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
                                "sInfoEmpty" : "没有记录",
                                "sInfoFiltered" : "(从 _MAX_ 条记录中过滤)",
                                "sSearch" : "搜索：",
                                "oPaginate" : {
                                    "sFirst" : "首页",
                                    "sPrevious" : "上一页",
                                    "sNext" : "下一页",
                                    "sLast" : "尾页"
                                }
                            },
                            "ajax":'',
                            "columnDefs":null,
                            "columns":null,
                            "fnInitComplete":null,
                            "fnDrawCallback":null
                        };
                    return dataTSetting;
                },
              //读取缓存基础数据
				loadCache:function (callback){
					comm.Request('loadCache',{
						type:'get',
						dataType:'json',
						async : false,
						success:function(response){
							callback(response);	
						},
						error: function(){
							$("#error_content").text(_dat.result);
							$("#alert_error").dialog({
								title:"提示信息",
								width:"400",/*此处根据文字内容多少进行调整！*/
								modal:true,
								buttons:{ 
								"确定":function(){
									$("#alert_error").dialog( "destroy" );
								}
								}
							});
						}
					});	
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
				
                //获取对应的地区名称
                getArea:function(areaNo,type){
                    var area=null;
                    if(type=="0"){//国家
                        $.each(comm.getCache("scs","cache").countryList,function(i,o){
                            if(o.areaNo==areaNo){   
                                area= o;
                            }
                        });
                    }
                    if(type=="1"){//省份
                        $.each(comm.getCache("scs","cache").countryList,function(i,o){
                            $.each(o.childs,function(j,o2){//alert(o2.areaNo+":"+areaNo);
                                if(o2.areaNo==areaNo){//alert(o2.areaName);
                                    area= o2;
                                }
                            });
                        });
                    }
                    if(type=="2"){//城市
                        $.each(comm.getCache("scs","cache").countryList,function(i,o){
                            $.each(o.childs,function(j,o2){
                                $.each(o2.childs,function(k,o3){
                                    if(o3.areaNo==areaNo){
                                        area= o3;
                                    }
                                });
                            });
                        });
                    }
                    return area;
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
				 * 设置按钮的点击时间
				 */
				confBtnClickTime : function(obj,time){
					var t = time;
					var interval = setInterval(function () {
						obj.addClass("ongsd");
						t -= 1;
						obj.html("剩余(" + t + ")");
						if (t == 1) {
							clearInterval(interval);
							obj.html("验证");
							obj.removeClass("ongsd");
							t = time;
						}
					}, 1000);
				},
				/**
				 * 获取验证码地址
				 * @param type 验证码类型
				 * @returns
				 */
				generateSecuritCode : function(type){
					return comm.domainList[comm.getProjectName()]+comm.UrlList["generateSecuritCode"]+'?custId='+$.cookie('custId')+'&type='+type+'&date='+new Date().getTime();
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
				/** 全角转半角 */
				toCDB:function(str){
					var tmp = ""; 
					for(var i=0;i<str.length;i++) 
					{ 
						if(str.charCodeAt(i)>65248&&str.charCodeAt(i)<65375) 
						{ 
							tmp += String.fromCharCode(str.charCodeAt(i)-65248); 
						} 
						else 
						{ 
							tmp += String.fromCharCode(str.charCodeAt(i)); 
						} 
					} 
					return tmp 
				},
				/** 是否包含全角字符，True 没有，False有  */
				isFullWidth:function(str)
				{
				    for (var i = 0; i < str.length; i++)
				    {
				        strCode = str.charCodeAt(i);
				        if ((strCode > 65248) || (strCode == 12288))
				        {
				            return false;
				        }
				    }
				    return true;
				},
				/**
				 * 根据菜单id获取下级菜单
				 * @param parentId 菜单id
				 * @return 返回菜单实例ArrayJson
				 */
				findPermissionJsonByParentId : function(parentId){
					//获取缓存中的菜单
					var mens = comm.getCache("scs", "ListMenu");
					//jsonFilter过滤
					return mens.filter(function(e) {
				        return e.parentId == parentId;
				    });
				},
				
				resetMenu : function(objParam,callback){
					
					common.findPerontIdByPermission(objParam,function(response){
						//加载中间菜单
						
						$('#subNav').html(_.template(subNavTpl, response));
						
						$("#subNav li").release().click(function(key){
							
							var childrenId = $(this).children().attr("id");
							
							$(this).parent().children().children().removeClass("s_hover");
							$(this).children().addClass("s_hover");
							var childrenId = $(this).children().attr("id");
							var strMenuUrl =$(this).children().attr("menuUrl");
							var lstAuthUrl;
							var arr = new Array();
							if(""!=strMenuUrl&&null!=strMenuUrl){
								lstMenuUrl=strMenuUrl.split(",")
								for(var i=0;i<lstMenuUrl.length;i++){
									arr[i]=lstMenuUrl[i];
								}
							}
							require(arr,function(src){	
								src.showPage();
							})
						});
						callback();
					}) 
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
              //20160503110236485
				subStringDate : function(date, length) {
					return date.substr(0, length);
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
    			}
		})
		return null ;
});