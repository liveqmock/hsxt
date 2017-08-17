define(['commSrc/commFunc',"commDat/common",'commSrc/cacheUtil'] , function() {	 
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
             * 用于呼叫中心电话工单时长转换
             * 秒数转时分秒  例如：10000s 转换  2:46:40
             * @param value
             * @returns {string}
             * @auther  leiyt
             */
            formatSeconds: function (value) {
                var theTime = parseInt(value);// 秒
                var theTime1 = 0;// 分
                var theTime2 = 0;// 小时

                if (theTime > 60) {
                    theTime1 = parseInt(theTime / 60);
                    theTime = parseInt(theTime % 60);

                    if (theTime1 > 60) {
                        theTime2 = parseInt(theTime1 / 60);
                        theTime1 = parseInt(theTime1 % 60);
                    }
                }
                var result = "" + parseInt(theTime) ;
                if (theTime1 > 0) {
                    result = "" + parseInt(theTime1) + ":" + result;
                }
                if (theTime2 > 0) {
                    result = "" + parseInt(theTime2) + ":" + result;
                }
                return result;
            },
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
				if(data) {//快捷卡
					if(id.indexOf("-5 ul")>0){
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
				 } ,
				 /*
				  *  切换三级菜单标签，
				  */

			liActive :function(liObj){
				liObj.parent('ul').find('a').removeClass('active');
				liObj.find('a').addClass('active');
			},
				liActive : function(liObj,idStr){
					if(idStr !=''){
						$(idStr).addClass('tabNone');
						/*$(idStr).addClass('tabNone').next().addClass('bgImgNone'); */
					}
					liObj.parent('ul').find('a').removeClass('active');
					liObj.find('a').addClass('active');
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
				yes_alert : function (text, width,callback) {
					$("#yes_content").html(text);
					$("#alert_yes").dialog({
						title : "提示信息",
						width : width || "400",
						modal : true,
						buttons : {
							"确定" : function () {
								if (callback){
									callback();
								}
								$(this).dialog("destroy");
							}
						}
					});
				},
				/*实现表格滚动的方法
				 * str : 表格的id
				 */
				scrollTable : function(str){
					var that = this;
					$('#' + str + ' tr:eq(0)').clone().prependTo('#' + str + ' thead');
					$('#' + str + ' tr:eq(0)').css({'z-index': 10, position: 'fixed'}).width($('#' + str + ' tr:eq(1)').width());
					$('#' + str + '_pt_outTab').remove();
					
					var thObjs = $('#' + str + ' tr:eq(1) th');
					thObjs.each(function (i) {
						var thObj = $(this);
						$('#' + str + ' tr:eq(0) th:eq(' + i + ')').height(thObj.height()).width(thObj.width() + that.getWidth(thObj.css('border-left-width')));
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
					try{$('#alert_i').dialog("destroy");}catch(e){};
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
                getNextYear :function(date){
                	var str = date.replace(/-/g,"/");
                	var d1 = new Date(str);
                	var s = d1.format();
                	d1.setFullYear(d1.getFullYear() +1);
                	var e=d1.format();
                	return [s,e];
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
				//获取token
				getToken : function (data, callback) {
					var jsonParam = cacheUtil.getParameters(data);
					comm.requestFun("findCardHolderToken" , jsonParam, callback,comm.lang("common"));
				},
				/**
				 * 获取工程名称
				 */
				getProjectName : function () {
					return "apsWeb";
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
				 * 获取登陆企业互生号
				 */
				getPointNo : function(){
					return comm.getSysCookie('pointNo');
				},
				/**
				 * 获取操作信息
				 */
				getoperNo : function(){
					return comm.getSysCookie('custName');
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
					params.channelType = '1';//渠道类型
					params.entCustId = comm.getSysCookie('entCustId');//登陆用户企业客户号
					params.entResNo = comm.getSysCookie('entResNo');//登陆用户企业互生号
					params.custName = comm.getSysCookie('custName');//登陆用户客户名称
					params.custEntName = comm.getCookie('entName');//企业名称
					params.operName = comm.getCookie('operName');//登陆用户名称
					params.entResType = 6;//企业类型
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
					params.search_custId = comm.getSysCookie('custId');//登陆用户客户号
					params.search_pointNo = comm.getSysCookie('pointNo');//登陆用户企业互生号
					params.channelType = '1';//渠道类型
					params.custId = comm.getSysCookie('custId');//登陆用户客户号
					params.token = comm.getSysCookie('token');//登陆token
					params.search_token = comm.getSysCookie('token');//登陆token
					params.search_entCustId = comm.getSysCookie('entCustId');//登陆用户企业客户号
					params.search_custName = comm.getSysCookie('custName');//登陆用户客户名称
					return params;
				},
				/**
				 * 请求后台方法封装
				 *  @param urlStr 请求的URL
				 *  @param params 参数信息
				 *  @param callback 回调函数
				 *  @param langObj 资源文件对象
				 */
				requestFun : function (urlStr, params, callback, langObj,callback1){
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
									if(callback1){
										callback1();
									}
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
					comm.requestFun("findCardHolderToken", null, function(res){
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
								if (response.retCode == 22000){
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
				 * @param objId 		需要绑定的下拉框元素
				 * @param objArray 		枚举内容（来源于国际化枚举对象）
				 * @param optionText 	实体中显示的字段
				 * @param optionValue	实体中保存的值
				 * @param showBlankOption	显示空值
				 * @param defaultVal 默认值
				 */
				initOption : function (objId, objArray,optionText, optionValue, showBlankOption,defaultVal){
					var item = null;
					if(showBlankOption){
						$(objId).append($("<option>").val("").text(comm.lang('common').pleaseSelect));
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
				 * 初始化下拉框
				 * @param objId 需要绑定的下拉框元素
				 * @param objArray 枚举内容（来源于国际化枚举对象）
				 * @param width 选择框宽度（可选参数）
				 * @param defaultVal 默认值（可选参数）
				 * @param defaultOptions 默认选项
				 * @param height 选择框高度（可选参数）
				 * @param keyFeild 对象key字段名称
				 * @param valFeild 对象val字段名称
				 */
				initSelect : function(objId, objArray, width, defaultVal, defaultOptions, height, keyFeild, valFeild){
					var options = [];
					if(defaultOptions){
						options.push(defaultOptions);
					}else{
						options.push({name:'',value:''});
					}
					if(keyFeild && valFeild){
						if(objArray){
							for(var i = 0; i < objArray.length; i++){
								if(undefined == objArray[i][valFeild] || undefined == objArray[i][keyFeild]){
									continue;
								}
								options.push({name:objArray[i][valFeild],value:objArray[i][keyFeild]});
							}
						}
//						for(key in objArray){
//							options.push({name:objArray[key][valFeild], value:objArray[key][keyFeild]});
//						}
					}else{
						for(key in objArray){
							if(undefined == objArray[key] || undefined == key){
								continue;
							}
							options.push({name:objArray[key], value:key});
						}
					}
					var content = {options : options};
					if(width){
						content.width = width;
						content.optionWidth = width;
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
				 * 初始化下拉框
				 * @param objId 需要绑定的下拉框元素
				 * @param objArray 枚举内容（来源于国际化枚举对象）
				 * @param width 选择框宽度（可选参数）
				 * @param defaultVal 默认值（可选参数）
				 * @param defaultOptions 默认选项
				 * @param height 选择框高度（可选参数）
				 * @param keyFeild 对象key字段名称
				 * @param valFeild 对象val字段名称
				 */
				initToolSelect : function(objId, objArray, width, defaultVal, defaultOptions, height, keyFeild, valFeild){
					var options = [];
					if(defaultOptions){
						options.push(defaultOptions);
					}else{
						options.push({name:'',value:''});
					}
					if(keyFeild && valFeild){
						if(objArray){
							for(var i = 0; i < objArray.length; i++){
								if(undefined == objArray[i][valFeild] || undefined == objArray[i][keyFeild]){
									continue;
								}
								if(objArray[i][valFeild].indexOf("仓库")!=-1|objArray[i][valFeild].indexOf("仓管")!=-1){
									options.push({name:objArray[i][valFeild],value:objArray[i][keyFeild]});
								}
							}
						}
//						for(key in objArray){
//							options.push({name:objArray[key][valFeild], value:objArray[key][keyFeild]});
//						}
					}else{
						for(key in objArray){
							if(undefined == objArray[key] || undefined == key){
								continue;
							}
							options.push({name:objArray[key], value:key});
						}
					}
					var content = {options : options};
					if(width){
						content.width = width;
						content.optionWidth = width;
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
				 * 初始化国家下拉框
				 * @param objId 需要绑定的下拉框元素
				 * @param countryArray 国家列表
				 * @param width 选择框宽度（可选参数）
				 * @param defaultVal 默认值（可选参数）
				 * @param defaultOptions 默认选项
				 */
				initCountrySelect : function(objId, countryArray, width, defaultVal, defaultOptions){
					var options = [];
					if(defaultOptions){
						options.push(defaultOptions);
					}else{
						options.push({name:'',value:''});
					}
					for(key in countryArray){
						var countryName = countryArray[key].countryName;
						var countryNo = countryArray[key].countryNo;
						if(undefined == countryNo || undefined == countryName){
							continue;
						}
						options.push({name:countryName, value:countryNo});
					}					
					var content = {options : options};
					content.width = width || 200;
					content.optionWidth = width; 
					if(countryArray.length > 10){
						content.optionHeight=300;
					}
					content.borderWidth = 1;
					content.borderColor = '#DDD';
					var select = $(objId).selectList(content);
					if(defaultVal != null && defaultVal != undefined){
						select.val("");
						select.attr('optionValue', "");
						select.selectListValue(defaultVal);
					}
					return select;
				},
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
					content.borderWidth = 1;
					content.borderColor = '#DDD';
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
					var options = [];
					if(defaultOptions){
						options.push(defaultOptions);
					}else{
						options.push({name:'',value:''});
					}
					if(comm.isNotEmpty(cityArray)&&cityArray.length > 0 && $.isArray(cityArray)){
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
				/**
				 * 根据属性名初始化combox
				 * @param objId : 页面绑定控件id
				 * @param optList : 数据list
				 * @param optionText : 取的list中text的属性名
				 * @param optionValue :取的list中value的属性名
				 * @param selValue: 默认值
				 * @param allowBlank : 是否有空选项
				 */
				initComboxByProperty : function(objId, optList,optionText, optionValue, selValue, allowBlank){
					var aHtml = allowBlank?'<option value=""></option>':"";
					if(optList != null){
						for(var key in optList){
							aHtml += '<option value="'+optList[key][optionValue]+'">' + optList[key][optionText] + '</option>';
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
				 * 控制自动加载数据BsGrid
				 */
				controlLoadBsGrid : function (autoLoad,gridId, url, params, langObj, detail, del, add, edit, renderImg, selectRowEvent, unselectRowEvent){
					return comm.buildCommBsGrid(autoLoad,gridId, url, params, langObj, detail, del, add, edit, renderImg, selectRowEvent, unselectRowEvent);
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
					return comm.buildCommBsGrid(true,gridId, url, params, langObj, detail, del, add, edit, renderImg, selectRowEvent, unselectRowEvent);
				},
				/**
				 * 构建常用BsGrid
				 * @param isAutoLoad 是否自动加载数据
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
				buildCommBsGrid:function(isAutoLoad,gridId, url, params, langObj, detail, del, add, edit, renderImg, selectRowEvent, unselectRowEvent){
					if(comm.checkUserLogin()){
						
						var autoLoad=true;
						if(!isAutoLoad){
							autoLoad=false;
						}
						
						gridId = !gridId?"tableDetail":gridId;
						return $.fn.bsgrid.init(gridId, {
							url:comm.domainList[comm.getProjectName()]+comm.UrlList[url],
							otherParames : comm.getQueryParams(params),
							pageSize: 10,
							stripeRows: true,  //行色彩分 
							displayBlankRows: false,
							autoLoad:autoLoad,
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
						//图片未上传则没有弹出框
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
						return "**** **** "+ocard.substring(ocard.length-4);
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
				initUploadBtn : function(btnIds,labelIds,width,height,maxSize){
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
				 * 获取上传文件地址
				 */
				getUploadFilePath : function() {
					return comm.domainList[comm.getProjectName()]+comm.UrlList["fileupload"];
				},
				/**
				 * 获取工具上传文件地址
				 */
				getToolUploadFilePath : function() {
					return comm.domainList[comm.getProjectName()]+comm.UrlList["toolfileupload"];
				},
				/**
				 * 去掉null
				 * @param val 值
				 */
				removeNull : function(val) {
					if(val == null || val == undefined || val == "null"){
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
				/**
				 * 依据省份代码获取省份名称--适用于省份多选
				 * @param provinceList 省份代码
				 * @param linkStr 连接字符
				 */
				getProvinceNames : function(provinceList, linkStr){
					var provCity = comm.getCache("commCache", "provCity");
					if(provCity.provMap == null || comm.removeNull(provinceList) == ""){
						return "";
					}
					linkStr = (linkStr != null)?linkStr:"/";
					var names = "";
					for(var key in provinceList){
						names+=comm.removeNull(provCity.provMap[provinceList[key].provinceNo])+linkStr;
					}
					if(names.length > 0){
						names = names.substring(0, names.length-1);
					}
					return names;
				},
				/**
				 * 组织省份数组--适用于省份多选
				 * @param objId 元素ID
				 * @param provinceList 选中的省
				 * @param width 宽度
				 * @param height 高度
				 * @param withCity 是否带城市
				 */
				initProvincePlugin : function(objId, provinceList, width, height, withCity){
					cacheUtil.findProvCity();
					var provCity = comm.getCache("commCache", "provCity");
					var provs = [];
					var temps = "";
					if(provinceList){
						for(var key in provinceList){
							temps+=provinceList[key].provinceNo+",";
						}
					}
				   var addAllArea={"0":"全部地区"} //创建copyPerson对象，将person中的属性包括方法copy给该对象
					for(var item in addAllArea){
						provCity.provMap[item]= addAllArea[item]; //这样循环就可以将person中的属性包括方法copy到copyPerson中了
					}
					if(provCity.provMap){
						for(var key in provCity.provMap){
							if(temps.indexOf(key) != -1){
								provs.push({name:provCity.provMap[key], fullName:provCity.provMap[key], value:key, town:[], selected:true});
							}else{
								provs.push({name:provCity.provMap[key], fullName:provCity.provMap[key], value:key, town:[]});
							}
						}
					}
					$(objId).selectProvinceCity ({
						width:width,
						optionHeight:height,
						withCity: withCity,
						options: provs
					});
					//选中默认值
					$(objId).attr("data-cityid", temps);
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
					url = !url?comm.getUploadFilePath():url;
					if(!param){
						param = {};
					}
					param.token = comm.getSysCookie('token');//登陆用户token
					param.custId = comm.getSysCookie('custId');//登陆用户客户号
					param = comm.getRequestParams(param);
					//文件上传token custid找不到
					url = url+"?loginToken="+param.token+"&custId="+param.custId+"&channelType=1";
					var langObj=this.lang("common");
					comm.ipro_alert(langObj['comm'].uploading);
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
				},
				
				/***
				 * 支持多文件上传，主要用于工具上传
				 * url:上传地址
				 * ids:文件的ID,多文件格式['id1','id2']
				 * callBack1:成功后的回调方法
				 * callBack2:失败后的回调方法
				 * param 参数
				 * 
				 */
				tooluploadFile : function(url, ids, callBack1, callBack2, param){
					url = !url?comm.getToolUploadFilePath():url;
					if(!param){
						param = {};
					}
					param.token = comm.getSysCookie('token');//登陆用户token
					param.custId = comm.getSysCookie('custId');//登陆用户客户号
					param = comm.getRequestParams(param);
					//文件上传token custid找不到
					url = url+"?loginToken="+param.token+"&custId="+param.custId+"&channelType=1";
					var langObj=this.lang("common");
					comm.ipro_alert(langObj['comm'].uploading);
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
				},

				/**
				 * 获取客户类型
				 * @param resNo
				 * @returns {Number}
				 */
				getCustType : function (resNo){
					if (isHsResNo(resNo))
					{
						if('00000000000'== resNo)
						{//总平台
							return 7;
						}else if(isAreaPlatResNo(resNo))
						{//地区平台
							return 6;
						}else if(isManageResNo(resNo))
						{//管理公司
							return 5;
						}else if (isServiceResNo(resNo))
						{// 服务公司
							return 4;
						} else if (isTrustResNo(resNo))
						{// 托管企业
							return 3;
						} else if (isMemberResNo(resNo))
						{// 成员企业
							return 2;
						} else if (isPersonResNo(resNo))
						{// 消费者
							return 1;
						}
					}
					return 0;
				},
				isAreaPlatResNo : function(resNo){
					var reg = /^([0]{8})(([1-9]\d\d)|(\d[1-9]\d)|(\d\d[1-9]))$/;
				},
				isManageResNo : function(resNo){
					var reg = /^(([1-9]\d){1}|(\d[1-9]){1})([0]{9})$/;
					return reg.test(resNo);
				},
				isServiceResNo : function (resNo){
					var reg = /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)([0]{6})$/;
					return reg.test(resNo);
				},
				isTrustResNo : function (resNo){
					var reg = /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)(([1-9]\d){1}|(\d[1-9]){1})([0]{4})$/;
					return reg.test(resNo);
				},
				isMemberResNo : function (resNo){
					var reg = /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)([0]{2})([1-9]\d{3}|\d{3}[1-9]|\d{2}[1-9]\d|\d[1-9]\d{2})$/;
					return reg.test(resNo);
				},
				isPersonResNo : function (resNo){
					var reg = /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{3}|\d{3}[1-9]|\d{2}[1-9]\d|\d[1-9]\d{2})$/;
					return reg.test(resNo);
				},
				isHsResNo : function (resNo){
					var reg = /^[0-9]{11}$/;
					return reg.test(resNo);
				},
				isEmpty : function(str){
					return !comm.isNotEmpty(str);
				},
				//替换字符串的 null ‘null’ undefined ‘undefined’
				navNull : function(str){
					if(str==undefined || str==null ||str=='undefined'||str=='null'){  
					    return ""; 
					}  
					return str;
				},
				//替换字符串的 null ‘null’ undefined ‘undefined’
				navNull : function(str,str2){
					if(str==undefined || str==null ||str=='undefined'||str=='null'){  
						if(str2==undefined || str2==null ||str2=='undefined'||str2=='null'){  
						    return ""; 
						}
						return str2;
						
					}  
					return str;
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
				/** 获取所有cookie值 */
				getCookieValue:function(){
					var cookieParams=comm.getRequestParams(null);
					return cookieParams;
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
								required : comm.lang("common")[10002]
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
					if(comm.isNotEmpty(operCustId)){
						cacheUtil.searchOperByCustId(operCustId,function(rsp){
							var retInfo="";
							
							if(rsp){
								retInfo=rsp.userName+"("+rsp.realName+")";
							}else{
								retInfo=operCustId;
							}
							callBack(retInfo);
						});
					}else{
						callBack(""); 
					}
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
					// return $(mens).filter(function(e) {
				     //    return e.parentId == parentId;
                    // });
					var x=[];
					for(var i=0;i<mens.length;i++ ){
						if(mens[i].parentId == parentId){
							x.push(mens[i]);
						}
					}
					return x;
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
				}
				,
                //获取表单条件
                getFormCondition:function(formId,conditionType){
                    var elementType="input,select";        //查找指定元素
                    var attrs=["value","optionvalue"];        //获取元素属性
                    var conditon={"type":conditionType};
                    $(formId).find(elementType).each(function(i,v){
                            var attrArray={};
                            var $element=$(this);
                            //循环属性值
                            for(var at in attrs){
                                if(comm.isNotEmpty($element.attr(attrs[at]))){
                                	attrArray[attrs[at]]=$element.attr(attrs[at]);
                                }
                            }
                            conditon[$element.attr("id")]=attrArray;
                    });
                    return conditon;
                },
                //显示表单条件
                showFormCondition:function(elementId,key,conditionType){
                    //获取表单查询条
                    var $conditon=$(elementId).data(key);
                    //匹配查询条件类型
                    if($conditon!=null && $conditon.type==conditionType){
                        for(var ele in $conditon){
                           $("#"+ele).attr($conditon[ele]);
                        }
                    }
                },
                //DIV切换 ，用于 列表页面 - 详细页面之间的互换
                goDetaiPage:function(hideDiv,showDiv){
                	$("#"+hideDiv).hide();
                	$("#"+showDiv).show();
                },
                //返回到list列表页面 ，保留之前查询条件的记录
                goBackListPage:function(hideDiv,showDiv,liObj,idStr){
                	$("#"+hideDiv).hide();
                	$("#"+showDiv).show();
                	if(comm.isNotEmpty(idStr)){
                		comm.liActive(liObj,idStr);
                	}else{
                		comm.liActive(liObj);
                	}
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
    			//设置自定义保留天数cookie
		        setSelfDefCookie:function(name,value,expDay){
		            var exp  = new Date();  //获得当前时间  
		            exp.setTime(exp.getTime() + expDay*24*60*60*1000);  //换成毫秒  
		            document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+";path=/";
		        },
		        //临时弹窗
		        temp_alert : function (title, text, width, second) {
					$("#i_content").html(text);
					$("#alert_i").dialog({
						title : title,
						width : width,
						modal : true,
					});
					setTimeout(function(){
						$("#alert_i").dialog("destroy");
					},second*1000)
				},
				/**
				 * 字符串截取
				 * @param str 要截取的字符串
				 * @param subLen 截取长度
				 */
				subStr:function(str,subLen){
					try{
						//去除空字符串
						var content=comm.navNull(str);
						
						//截取
						if(content.length>subLen){
							return content.substr(0,subLen);
						}
						
						return content;
					}catch(e){
						return str;
					}
					
				},
				//呼叫管理模块缓存
				callCache : {},
				getCallCache : function(key){
					return this.callCache[key];
				},
				setCallCache : function(key, val){
					this.callCache[key] = val;
				},
				/**
				 * 获取企业客户类型名称
				 * @param entCustType 企业客户类型
				 */
				getCustTypeName : function(entCustType){
				  var name = '';
				  if(null==entCustType || ''==entCustType){
					  entCustType = '52';
				  }
				  switch (parseInt(entCustType)) {
                     case 2: name = '成员企业'; break;
                     case 3: name = '托管企业'; break;
                     case 4: name = '服务公司'; break;
                     case 5: name = '管理公司'; break;
                     case 6: name = '地区平台'; break;
                     case 7: name = '总平台'; break;
                     case 8: name = '其它地区平台'; break;
                     case 52: name ='非互生格式化企业'; break;
                 }
				  return name;
				},
			/** 快捷日期控制 */
			quickDateChange:function(type,startDateId,endDateId){
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

				$(startDateId).val(qd[0]);
				$(endDateId).val(qd[1]);
			}
				
		});
		return null ;
});