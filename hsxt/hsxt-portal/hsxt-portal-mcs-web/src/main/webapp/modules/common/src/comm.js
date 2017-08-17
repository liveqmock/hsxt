 define(["commSrc/commFunc", 'commSrc/cacheUtil'] , function() {
	 	$.extend(comm,{				
				envModel:"prod",	//环境类型配置  dev/test/prod
				//401出错跳转，402出错提示 
				retCode : ['401','402','403','404','500','','','','8888','9999','160373'],
				/**
				 * 账户类型定义
				 * 积分10110 互生币（流通币20110 定向消费币20120 慈善救助金20130） 货币30110
				 */
				accType :{"POINT":"10110","LTB":"20110","DXXFB":"20120","CSJZJ":"20130","CURRENCY":"30110"},


			/**
			 * 呼叫中心右上角 银行卡列表页面渲染公用方法
			 * @param id   父元素id
			 * @param data 银行卡列表数据
			 */
			call_bank_List_print:function(id,data){
				var tplBankLi = '<li class="bank_bg pr"><%=obj.defaultFlag%><div class="bank_row bank_row_margin_1 f13">尾号：<%=obj.last4Number%></div><div class="bank_row tc"><i class="large-bank-logo large-bank-card-<%=obj.bankCode%>"></i></div><div class="bank_row bank_row_margin_2 clearfix"><div class="fl"><span class="gray">结算币种：</span><%=obj.currencyName%></div></div></li>';
				var Qk_bank_list='<li class="bank_bg pr"> <div class="bank_row bank_row_margin_1"> <span class="f13">尾号：<%=obj.last4Number%></span> <span class="ml5 f13 red"><%=obj.bankCardType%></span> </div> <div class="bank_row tc"><i class="large-bank-logo large-bank-<%=obj.bankCode%>"></i></div> <div class="bank_row bank_row_margin_2 tr"> </div> </li>';
				var aBanks = [];
				var defaultBank=[];
				//非空验证
				if(data) {
					if(id.indexOf("-5 ul")>0){	//快捷卡
						for(var i = 0 ; i < data.length ;i++){
							var oDat = {
								bankCode: data[i].bankCode,//ok
								last4Number: data[i].bankCardNo.substr(data[i].bankCardNo.length - 4, 4),//尾号 ok
								bankCardType:data[i].bankCardType==1?"储蓄卡":"信用卡"
							};
							aBanks.push(_.template(Qk_bank_list, oDat));
						}
					}else{//银行卡
						for(var i = 0 ; i < data.length ;i++){
							var oDat = {
								bankCode: data[i].bankCode,// ok
								defaultFlag: data[i].isDefaultAccount == '1' ? ' <i class="default_bank"></i>' : '',// ok
								defaultClass:data[i].isDefaultAccount == '1' ? 'ml10 blue':'',
								last4Number: data[i].bankAccNo.substr(data[i].bankAccNo.length - 4, 4),	//ok
								currencyName: data[i].countryNo==156?"人民币": data[i].countryNo
							};
							if(data[i].isDefaultAccount == '1'){
								defaultBank.push(_.template(tplBankLi, oDat));
								continue;
							}
							aBanks.push(_.template(tplBankLi, oDat));
						}
					}
				}
				$("#"+id).html(defaultBank.join('')).append(aBanks.join(''));
			},

			/**
			 * 呼叫中心，电话号码呼外处理
			 * @param mobile
			 * @returns {*}
			 */
				getPrefix :function(mobile){
					if(comm.envModel!="prod"){
						return "9,,"+mobile;
					}else{
						return mobile;
					}
				},
			/**
			 * 短号呼叫
			 * @param num
			 */
			call_grant_num:function(num){
				var zx=Test.JS_GetUserNumByMac(Test.JS_GetMac());
				if(num==zx){
					comm.alert("请勿自我呼叫");
				}
				var ret=Test.JS_CallOtherAgent(zx,num);
				if(ret!=1){
					comm.callCenter_error(ret);
				}else{
					comm.alert(num+"正在呼叫");
				}
			},

			//呼叫中心错误码转换
			callCenter_error:function(ret){
				comm.alert(comm.langConfig.common.callCenterErrorMenu[ret]);
			},
			//坐席分组手风琴效果
			group_accordion:function(){
				$(".serviceItem").accordion();//申缩菜单效果
				$('.listScroll').jScrollPane();//滚动条
			},

			/**
			 * 数字转换百分比
			 * @param number
			 * @returns {string}
			 */
				percentage:function(number){
					if(/^\d+(\.?\d+)?$/.test(number)){
						return (Math.round(number * 10000)/100).toFixed(2) + '%';
					}else{
						return number;
					}
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
				 * 格式化金额
				 */
				formatMoneyNumberAps : function(moneyNum) {
					var result = isNaN((1 * moneyNum).toFixed(2)) ? (new Number(
							0).toFixed(2))
							: (1 * moneyNum).toFixed(2);
					return /\./.test(result) ? result.replace(
							/(\d{1,3})(?=(\d{3})+\.)/g, "$1,") : result
							.replace(/(\d{1,3})(?=(\d{3})+\b)/g, "$1,");
				},
				/*
				 * 格式化金额
				 */
				formatMoneyNumberAps2 : function(moneyNum) {
					var result = isNaN((1 * moneyNum).toFixed(2)) ? (new Number(
							0).toFixed(2))
							: (1 * moneyNum).toFixed(2);
					return result;
				},
				/**
				 * 根据资源号判断资源类型
				 * param resNo 资源号
				 * return 资源类型   P消费者 B成员企业 T托管企业 S 服务公司 M 管理公司
				 */
				getResNoType:function(resNo){
					var type="";
					if(comm.isNotEmpty(resNo)&&resNo.length==11){
						var str1=resNo.substring(0,2);
						var str2=resNo.substring(2,5);
						var str3=resNo.substring(5,7);
						var str4=resNo.substring(7,11);
						if(str1>0 && str2>0 && str3>0 && str4>0){
							type="P";
						}
						if(str1>0 && str2>0 && str3==0 && str4>0){
							type="B";
						}
						if(str1>0 && str2>0 && str3>0 && str4==0){
							type="T";
						}
						if(str1>0 && str2>0 && str3==0 && str4==0){
							type="S";
						}
						if(str1>0 && str2==0 && str3==0 && str4==0){
							type="M";
						}
					}
					return type;
				},
				/**
				 * 判断字符串不为空
				 */
				isNotEmpty:function (obj){
					if(typeof(obj) == 'string'){
						obj = comm.trim(obj);
					}
					if(obj==null || obj==undefined || obj=='undefined' ||obj==''||obj=='null'){
						return false;
					}
					return true;
				},
				/**
				  * 去掉首尾空格
				  */
				 trim : function (val){
					 return val.replace(/(^\s*)|(\s*$)/g,"");
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
				 /*
				  *导航（链接）到指定的模块及页面路径
				  */
				 navUrl : function(moduleName,urlName) {
					 	location.href = comm.getUrl(moduleName,urlName) ; 
				 },
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
						$('#alert_i').dialog("destroy");
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
					// 获取今天开始和结束
					getTodaySE : function () {
						var d1 = comm.getCurrDate();
						return [d1, d1];
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
			             return [s, e];
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
					// 获取今天开始和结束
					getTodaySE : function () {
						var d1 = comm.getCurrDate();
						return [d1, d1];
					},
					// 获取最近一周开始和结束
					getWeekSE : function () {
						var d1 = new Date();
						var e = d1.getFullYear() + "-" +this.fillNumber(d1.getMonth()+1) + "-" + this.fillNumber(d1.getDate());
						d1.setDate(d1.getDate() - 6); // 上一周
						var s = d1.getFullYear() + "-" +this.fillNumber(d1.getMonth()+1) + "-" + this.fillNumber(d1.getDate());
						return [s, e];
					},
					// 获取最近一月开始和结束
					getMonthSE : function () {
						var today = new Date();
						var day = today.getDate();
						var e = today.getFullYear() + "-" +this.fillNumber(today.getMonth()+1) + "-" + this.fillNumber(today.getDate());
						today.setDate(0);
						var maxDay = today.getDate();
						if (day <= maxDay) {
							today.setDate(day);
						}
						today.setDate(today.getDate() + 1);
						var s = today.getFullYear() + "-" +this.fillNumber(today.getMonth()+1) + "-" + this.fillNumber(today.getDate());
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
					 * 获取登陆企业互生号
					 */
					getPointNo : function(){
						return comm.getSysCookie('pointNo');
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
						return comm.getCookie('custName')+"（"+comm.getCookie('operName')+"）";
					},
					/**
					 * 获取请求公共参数
					 * @param params 参数信息
					 */
					getRequestParams : function(params){
						if(!params){
							params = {};
						}
						params.channelType = '1';//渠道号
						params.custId = comm.getSysCookie('custId');//登陆用户客户号
						params.pointNo = comm.getSysCookie('pointNo');//登陆用户企业互生号
						params.token = comm.getSysCookie('token');//登陆token
						params.entCustId = comm.getSysCookie('entCustId');//登陆用户企业客户号
						params.custName = comm.getSysCookie('custName');//登陆用户客户名称
						params.custEntName = comm.getCookie('entName');//企业名称
						params.entResNo = comm.getSysCookie('entResNo');//登陆用户企业互生号
						params.hsResNo = comm.getSysCookie('pointNo');//登陆用户企业互生号
		                params.entCustId = comm.getSysCookie('entCustId');//登陆用户企业客户号
		                params.entResType = 5;//登陆用户企业类型
		                params.operName = comm.getSysCookie('operName');//登陆用户姓名
		                params.cookieOperNoName = this.getCookieOperNoName();//操作员信息
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
						params.channelType = '1';//渠道号
						params.search_custId = comm.getSysCookie('custId');//登陆用户客户号
						params.search_pointNo = comm.getSysCookie('pointNo');//登陆用户企业互生号
						params.search_token = comm.getSysCookie('token');//登陆token
						params.search_custName = comm.getSysCookie('custName');//登陆用户客户名称
						params.search_entResNo = comm.getSysCookie('entResNo');//登陆用户企业互生号
						params.search_hsResNo = comm.getSysCookie('pointNo');//登陆用户企业互生号
		                params.search_entCustId = comm.getSysCookie('entCustId');//登陆用户企业客户号
						return params;
					},
					/**
					 * 获取工程名称
					 */
					getProjectName : function () {
						return "mcsWeb";
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
							comm.Request({url:urlStr, domain:"mcsWeb"},{
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
					//呼叫中心访问地区平台后台专用  leiyt2016-03-25
					requestFun_CallCenter : function (urlStr, params, callback, langObj){
						if(comm.checkUserLogin()){
							comm.showLoading();
							comm.Request({url:urlStr, domain:"apsWeb"},{
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
					/***
					 * 获取随机token码
					 */
					getToken:function(callback){
						var self=this;
						var params= {};
						params.custId = "46186000000164123662798848";
						comm.Request({url:"getToken", domain:"mcsWeb"},{
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
					 * 双签用户校验，先获取随机token，然后对密码加密，最后传入到后台校验，如果通过返回用户ID，反之抛出异常
					 * @param userName 用户名称
					 * @param pwd 密码
					 * @param checkType 校验类型
					 */
					verifyDoublePwd : function(userName, pwd, checkType, callBack){
						var langObj = comm.lang('common');//资源文件
						comm.requestFun("getToken", null, function(res){
							var params = comm.getRequestParams();
							params.doubleUserName = userName;
							params.doublePwd = comm.encrypt(pwd, res.data);
							params.checkType = checkType;
							params.randomToken = res.data;
							comm.requestFun("verifyDoublePwd", params, callBack, langObj);
						}, langObj);
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
							comm.Request({url:urlStr, domain:"mcsWeb"},{
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
					 * 同步请求后台方法封装
					 *  @param urlStr 请求的URL
					 *  @param params 参数信息
					 *  @param langObj 资源文件对象
					 */
					asyncRequestFun : function (urlStr, params, langObj){
						if(comm.checkUserLogin()){
							comm.showLoading();
							var resData = null;
							comm.Request({url:urlStr, domain:"mcsWeb"},{
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
					initProvSelect : function(objId, provArray, width, defaultVal, defaultOptions,height){
						var options = [];
						if(defaultOptions){
							options.push(defaultOptions);
						}else{
							options.push({name:'',value:''});
						}
						if(comm.isNotEmpty(provArray)&&provArray.length > 0){
							for(var i = 0 ; i < provArray.length;i++){
								var obj = provArray[i];
								options.push({name:obj.provinceNameCn, value:obj.provinceNo});
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
						
						if(height){
							content.optionHeight=height;
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
					initCitySelect : function(objId, cityArray, width, defaultVal, defaultOptions,height){
						var options = [];
						if(defaultOptions){
							options.push(defaultOptions);
						}else{
							options.push({name:'',value:''});
						}
						if(comm.isNotEmpty(cityArray)&&cityArray.length > 0){
							for(var i = 0 ; i < cityArray.length;i++){
								var obj = cityArray[i];
								options.push({name:obj.cityNameCn, value:obj.cityNo});
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
						
						if(height){
							content.optionHeight=height;
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
								aHtml += '<option value="' + optList[key] + '"' + (optList[key] == selValue ? ' selected="selected"' : '')  + '>' + optList[key] + '</option>';
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
						if(!localData || localData.length == 0){
							localData = {};
						}
						return $.fn.bsgrid.init(gridId, {
							pageSize: 10 ,
							stripeRows: true,  //行色彩分 
							displayBlankRows: false ,   //显示空白行
						  	localData: localData,
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
					 * @param selectRowEvent 自定义函数selectRowEvent（可选参数）
					 * @param unselectRowEvent 自定义函数unselectRowEvent（可选参数）
					 */
					getCommBsGrid : function (gridId, url, params, langObj, detail, del, add, edit, renderImg, selectRowEvent, unselectRowEvent){
						if(comm.checkUserLogin()){
							gridId = !gridId?"tableDetail":gridId;
							return $.fn.bsgrid.init(gridId, {
								url:(/callCenter\/[\w]+$/.test(comm.UrlList[url])? comm.domainList.apsWeb:comm.domainList.mcsWeb)+comm.UrlList[url],
								otherParames : comm.getQueryParams(params),
								pageSize: 10,
								stripeRows: true,  //行色彩分 
								displayBlankRows: false,
								autoLoad:true,
								operate : {
									detail:detail,
									del:del,
									add:add,
									edit:edit,
									renderImg:renderImg,
								},
								event:{
									selectRowEvent:selectRowEvent,
									unselectRowEvent:unselectRowEvent
								}
							});
						}
					},
					/**
					 * 获得服务器地址
					 * @param fileId 文件ID
					 */
					getFsServerUrl : function(fileId) {
						if(comm.isEmpty(fileId) || fileId == "NO_FILE"){
							return "./resources/img/noImg.gif";
						}else{
							var custId = comm.getSysCookie('custId');// 读取 cookie 客户号
							var token = comm.getSysCookie('token');
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
					 * 依据地区代码获取城市名称
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
							if (comm.isNotEmpyt(url) && "./resources/img/noImg.gif" != url) {
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
					 * 获取省份名称
					 * @param cityCode 省份代码
					 * @param cityArray 省份列表
					 */
					getProvName : function(provCode, provArray){
						if(comm.removeNull(provCode) == "" || !provArray){
							return "";
						}
						for(var key in provArray){
							if(provArray[key].provinceNo == provCode){
								return provArray[key].provinceNameCn;
							}
						}
						return "";
					},
					/**
					 * 获取城市名称
					 * @param cityCode 城市代码
					 * @param cityArray 城市列表
					 */
					getCityName : function(cityCode, cityArray){
						if(comm.removeNull(cityCode) == "" || !cityArray){
							return "";
						}
						for(var key in cityArray){
							if(cityArray[key].cityNo == cityCode){
								return cityArray[key].cityNameCn;
							}
						}
						return "";
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
					/***
					 * 上传图片预览功能需要先初始化上传按钮，
					 * 
					 * btnIds：上传按钮的id,多个ID格式为数据['#id1','#id2']  
					 * 
					 * labelIds:预览图片显示的标签的ID,与上传按键的顺序要对应,格为数据['#id1','#id2']
					 */
					initUploadBtn : function(btnIds, labelIds, width, height, maxSize){
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
						return comm.domainList['mcsWeb']+comm.UrlList["fileupload"];
					},
					/**
					 * 去掉null
					 * @param val 值
					 */
					removeNull : function(val) {
						if(val==undefined || val==null ||val=='undefined'||val=='null'){
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
					 * params 参数
					 */
					uploadFile : function(url, ids, callBack1, callBack2, params){
						url = !url?comm.getUploadFilePath():url;
						var langObj=comm.lang('common');
						var requestParams = comm.getRequestParams(params);
						url = url+"?loginToken="+requestParams.token+"&custId="+requestParams.custId+"&channelType=1";
						comm.ipro_alert(langObj['comm']['uploading']);
						$.ajaxFileUpload({
							// 处理文件上传操作的服务器端地址
							url : url,
							secureuri : false, // 是否启用安全提交,默认为false
							fileElementId : ids, // 文件选择框的id属性
							dataType : 'text', // 服务器返回的格式,可以是json或xml等
							data : requestParams,
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
								comm.ipro_alert_close();
								comm.error_alert("文件上传失败,可能是文件过大");
								callBack2();
							}
						});
						
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
	                /** 验证用户是否登录 */
	                checkUserLogin:function(){
	                	var custId=$.cookie("custId"); //客户操作号
	                	
	                	//登录身份失效请重新登录 或 用户数据不存在则跳转到登录页
	                	if(!comm.validTokenCK() || (custId==undefined || custId==null || custId=="null"))
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
					 * 依据地区代码获取城市名称
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
					 * 去掉首尾空格
					 */
					trim : function (val) {
						return val.replace(/(^\s*)|(\s*$)/g, "");
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
					/**
					 * 字符串截取
					 * @param str 要截取的字符串
					 * @param subLen 截取长度
					 * @param symbol 追加符号
					 */
					subString:function(str,subLen,symbol){
						var symbolStr="...";
						
						//追加符号
						if(comm.isNotEmpty(symbol)){
							symbolStr=symbol;
						}
						
						//去除空字符串
						var content=comm.navNull(str);
						
						//截取
						if(content.length>=subLen){
							return content.substr(0,subLen)+symbolStr;
						}
						return content;
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
					/**
					 * 获取验证码地址
					 * @param type 验证码类型
					 * @returns
					 */
					generateSecuritCode : function(type){
						return comm.domainList["mcsWeb"]+comm.UrlList["generateSecuritCode"]+'?custId='+$.cookie('custId')+'&type='+type+'&date='+new Date().getTime();
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
					 * 依据省份代码获取城市列表
					 * @param provCode 省份代码
					 */
					getCityByproCityCode : function(provCode){
						var provCity = comm.getCache("commCache", "provCity");
						if(provCity.provMap){
							return provCity.cityMap[provCode];
						}
						return null;
					},
			/*
			 *工单操作
			 *参数：
			 *btn_txt：操作按钮文本；
			 *bsn_type：业务类型；
			 *fn：操作函数
			 */
			workflow_operate : function(btn_txt, bsn_type, fn){

				return  $('<a>' + btn_txt + '</a>').click(function(e) {

					comm.confirm({
						imgFlag : true,
						width:500,
						imgClass : 'tips_i',
						content : '您确认' + btn_txt + bsn_type + '的工单！',
						callOk : fn
					});

				}.bind(this) ) ;

			},
			/**
			 * 请求后台方法封装
			 *  @param urlStr 请求的URL
			 *  @param params 参数信息
			 *  @param callback 回调函数
			 *  @param langObj 资源文件对象
			 */
			requestFunForHandFail : function (urlStr, params, callback, langObj){
				var self=this;
				self.checkUserLogin();
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
			/** 快捷日期控制 */
			quickDateChange:function(type){
				// 默认近一日
				var qd = comm.getTodaySE();

				if (type == "2") {
					// 近一周
					qd = comm.getWeekSE();
				}else if (type == "3") {
					// 近一月
					qd = comm.getMonthSE();
				}
				if (type == "4") {
					// 近三月
					qd = comm.get3MonthSE();
				}

				$("#search_startDate").val(qd[0]);
				$("#search_endDate").val(qd[1]);
			}
		});
		return null ;
});