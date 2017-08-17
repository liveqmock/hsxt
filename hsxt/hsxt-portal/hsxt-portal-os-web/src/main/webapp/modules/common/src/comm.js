define(["commSrc/commFunc"] , function() {		 
	 	$.extend(comm,{				
				//401出错跳转，402出错提示 
				retCode : ['401','402','403','404','500','','','','8888','9999'],
				
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
				 /*
				  *导航（链接）到指定的模块及页面路径
				  */
				 navUrl : function(moduleName,urlName) {
					 	location.href = comm.getUrl(moduleName,urlName) ; 
				 } ,
				  /*
				  *  切换三级菜单标签，
				  */
				 liActive :function(liObj){		
				 	
					  liObj.parent('ul').find('a').removeClass('active');
					  liObj.find('a').addClass('active');	
				 },
				 
				 
				 /*  下面的方法名等在此对象中被禁用
				  *  getCurrDate ， formatDate ，formatMoneyNumber ，setFocus ，setBlur ，
				  *  setCache ，getCache ， delCache ， Require ， Define ，emptyCurrPath，
				  *  langConfig ， lang ，formatStr  ，confirm ，  alert
				  *
				  */
				 
				 /**
					 * 请求后台方法封装
					 *  @param urlStr 请求的URL
					 *  @param params 参数信息
					 *  @param callback 回调函数
					 *  @param langObj 资源文件对象
					 */
					requestFun : function (urlStr, param, callback, langObj){
							
							if(!param){
								param = {};
							}
							//comm.showLoading();
							comm.Request({url:urlStr, domain:"osWeb"},{
								data:comm.getRequestParams(param),
								type:'POST',
								dataType:"json",
								success:function(response){
									comm.closeLoading();
									//非空验证
									if (response.retCode ==22000){
										callback(response);	
									}else{
										//获取code信息描述
										var errMgs = langObj[response.retCode];
										//判断描述信息是否存在
										if(errMgs != '' && errMgs !=undefined){
											comm.yes_alert_info("",errMgs);
										}else{
											comm.error_alert(langObj.requestFailedWithCode+response.retCode);
										}
									}
									
								},
								error: function(){
									comm.closeLoading();
									comm.error_alert(langObj.requestFailed);
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
					 *  验证数据是否存在数据库,并返回结果集
					 *  @param urlStr 请求的URL
					 *  @param params 参数信息
					 *  @param callback 回调函数
					 *  @param langObj 资源文件对象
					 */
					validateDatas : function (urlStr, params, callback, langObj){
							return comm.Request({url:urlStr, domain:"osWeb"},{
								data:params,
								type:'POST',
								dataType:"json",
								success:function(response){
									comm.closeLoading();
									//非空验证
									if (response.retCode == 22000){
										callback(response);
									}else{
										//获取code信息描述
										var errMgs = langObj[response.retCode];
										//判断描述信息是否存在
										if(errMgs != '' && errMgs !=undefined){
											comm.error_alert(errMgs);
										}else{
											comm.error_alert(langObj.requestFailedWithCode+response.retCode);
										}
									}
								},
								error: function(){
									comm.closeLoading();
									comm.error_alert(langObj.requestFailed);
								}
							});
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
					 * 隐藏等待效果
					 */
					closeLoading : function () {
						try{
							$("#showLoadIngDiv").dialog('destroy');
							$("#showLoadIngDiv").addClass('none');
						}catch(e){
						}
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
						//$("#alert_error").append(text);
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
					//yes
					yes_alert_info : function (title, content) {
						comm.alert({
							 imgFlat : true,
							 imgClass : 'tips_i',
							 title:title,
							 content:content
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
					/**
					 * 获取请求公共参数
					 * @param params 参数信息
					 */
					getRequestParams : function(params){
						if(!params){
							params = {};
						}
						params.custId = comm.getSysCookie('custId');//登陆用户客户号
						params.pointNo = comm.getSysCookie('pointNo');//登陆用户企业互生号
						params.token = comm.getSysCookie('token');//登陆token
						params.custName = comm.getSysCookie('custName');//登陆用户客户名称
						return params;
					},
					/**
					 * 获取Cookie信息
					 * @param Cookie对象ID
					 */
					getSysCookie:function (name){
				        var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
				        if(arr != null){
				        	return unescape(arr[2]);
				        }else{
				        	return null;
				        }  
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
						if(!params){
							params = {};
						}
						params.search_custId = $.cookie('custId');
						params.search_pointNo = "06000000000";//$.cookie('pointNo');
						params.search_token = $.cookie('token');
						params.search_custName = $.cookie('username');
						gridId = !gridId?"tableDetail":gridId;
						return $.fn.bsgrid.init(gridId, {
							url:comm.domainList.osWeb+comm.UrlList[url],
							otherParames : params,
							pageSize: 10,
							stripeRows: true,  //行色彩分 
							displayBlankRows: false,
							operate : {
								detail:detail,
								add:add,
								edit:edit,
								del:del,
								renderImg:renderImg,
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
						d1.setDate(d1.getDate() - 7); // 上一周
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
					//国际化文案配置
					langConfig : {},
					//获取文案字段
					lang : function (name) {
						var str = this.langConfig[name] || '';
						return str;
					},
					/**
					 * 获取工程名称
					 */
					 getProjectName : function () {
						return "osWeb";
					 },
					 /** 验证用户是否登录 */
			        checkUserLogin:function(){
			        	var custId=$.cookie("custId"); //客户操作号
			        	//用户数据不存在则跳转到登录页
			        	if(custId==undefined || custId==null || custId=="null"){
			        		window.location.replace(comm.domainList["quitUrl"]);
			        		return false;
			        	}
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
						var self=this;
						self.checkUserLogin();
						
						url = !url?comm.getUploadFilePath():url;
						if(!param){
							param = {};
						}
						var langObj=comm.lang('common');
						//comm.yes_alert_info("",langObj['comm']['uploading']);
						comm.ipro_alert(langObj['comm']['uploading']);
						$.ajaxFileUpload({
							// 处理文件上传操作的服务器端地址
							url : url,
							secureuri : false, // 是否启用安全提交,默认为false
							fileElementId : ids, // 文件选择框的id属性
							dataType : 'json', // 服务器返回的格式,可以是json或xml等
							data : comm.getRequestParams(param),
							type : "POST",
							success : function (response) { // 服务器响应成功时的处理函数
								var data = response.data;
								comm.ipro_alert_close();
								if(response.retCode==22000){
									callBack1(data);
								}else {
									callBack2();
									//上传异常提示
									comm.alert({
										content: langObj['errorCodes'][response.retCode],
										imgClass: 'tips_error',
									});
								}
							},
							error: function(response){
								callBack2();
								comm.error_alert("文件上传失败.");
								comm.ipro_alert_close();
							}
						});
					},
					ipro_alert : function (text) {
						$("#alert_i").html(text).dialog({
							dialogClass : "no-dialog-close",
							title : "提示信息",
							width : "400"
						});;
					},
					ipro_alert_close : function(){
						try{
							$('#alert_i').dialog("destroy");
						}catch(e){};
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
					/**
					  * 去掉首尾空格
					  */
					 trim : function (val){
						 return val.replace(/(^\s*)|(\s*$)/g,"");
					 }
		})
		return null ;
});

