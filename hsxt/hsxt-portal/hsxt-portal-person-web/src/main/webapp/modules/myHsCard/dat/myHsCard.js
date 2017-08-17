define(function () {
	return {
		
		
		//实名注册-获取用户信息
		queryConsumerInfo : function (jsonParam,callback) {
			comm.requestFun("queryConsumerInfo" , jsonParam, callback,comm.lang("myHsCard"));
		},
		
		//实名注册
		registration : function (jsonParam, callback) {
			comm.requestFun("registration" , jsonParam, callback,comm.lang("myHsCard"));
		},
		
		//初始化实名认证
		initAuthentication : function (jsonParam, callback) {
			comm.requestFun("initAuthentication" , jsonParam, callback,comm.lang("myHsCard"));
		},
		//实名注册
		authentication: function (jsonParam, callback) {
			comm.requestFun("authentication" , jsonParam, callback,comm.lang("myHsCard"));
		},
		//获取银行列表数据
		findBankBindList : function (jsonParam,callback) { 
			comm.requestFun("findBankBindList" , jsonParam, callback,comm.lang("myHsCard"));
		},
		
		//增加银行卡信息
		addBankCard : function (jsonParam, callback) {
			comm.requestFun("addBankBind" , jsonParam, callback,comm.lang("myHsCard"));
		},
		//删除银行卡信息
		delBankCard : function (jsonParam, callback) {
			comm.requestFun("delBankCard" , jsonParam, callback,comm.lang("myHsCard"));
		},


		//获取绑定的手机号码
		findMobileByCustId : function (jsonParam, callback) {
			comm.requestFun("findMobileByCustId" , jsonParam, callback,comm.lang("myHsCard"));
		},
		//发送短信验证码
		mobileSendCode: function (jsonParam, callback) {
			comm.requestFun("mobileSendCode" , jsonParam, callback,comm.lang("myHsCard"));
		},
		
		//修改绑定手机号码
		editBindMobile : function (jsonParam, callback) {
			comm.requestFun("editBindMobile" , jsonParam, callback,comm.lang("myHsCard"));
		},
		//添加绑定手机号
		addBindMobile : function (jsonParam, callback) {
			comm.requestFun("addBindMobile" , jsonParam, callback,comm.lang("myHsCard"));
		},
		
		//添加绑定手机号
		findEamilByCustId : function (jsonParam, callback) {
			comm.requestFun("findEamilByCustId" , jsonParam, callback,comm.lang("myHsCard"));
		},
		//添加绑定手机号
		addBindEmail : function (jsonParam, callback) {
			comm.requestFun("addBindEmail" , jsonParam, callback,comm.lang("myHsCard"));
		},
		
		//查询互生卡挂失状态
		findHsCardStatusInfoBycustId : function (jsonParam, callback) {
			comm.requestFun("findHsCardStatusInfoBycustId" , jsonParam, callback,comm.lang("myHsCard"));
		},
		
		//互生卡挂失
		hscReportLoss : function (jsonParam, callback) {
//			comm.requestFun("hscReportLoss" , jsonParam, callback,comm.lang("myHsCard"));
			comm.requestFunForHandFail("hscReportLoss", jsonParam, callback, comm.lang("myHsCard"));
		},
		
		//互生卡解挂
		hscSolutionLinked : function (jsonParam, callback) {
		//	comm.requestFun("hscSolutionLinked" , jsonParam, callback,comm.lang("myHsCard"));
			comm.requestFunForHandFail("hscSolutionLinked", jsonParam, callback, comm.lang("myHsCard"));
		},
		
		//获取预留信息
		findReservationInfo: function (jsonParam, callback) {
			comm.requestFun("findReservationInfo",jsonParam,callback,comm.lang("myHsCard"));
		},
		/**
		 * 查询重要信息变更状态
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		findImportantInfoChangeStatus : function(params, callBack){
			comm.requestFun("findImportantInfoChangeStatus" , params, callback, comm.lang("myHsCard"));
		},
		/**
		 * 重要信息变更-初始化界面
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		initPerChange : function(params, callBack){
			comm.requestFun("initPerChange", params, callBack, comm.lang("myHsCard"));
		},
		/**
		 * 重要信息变更-创建重要信息变更
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		createPerChange : function(params, callBack){
			comm.requestFun("createPerChange" , params, callBack, comm.lang("myHsCard"));
		},
		/**
		 * 重要信息变更-修改重要信息变更
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		modifyPerChange : function(params, callBack){
			comm.requestFun("modifyPerChange" , params, callBack, comm.lang("myHsCard"));
		},
		/**
		 * 查询快捷支付银行
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		findPayBankAll : function(params, callBack){
			comm.requestFun("queryPayBankAll" , params, callback, comm.lang("myHsCard"));
		},
		/**
		 * 查询绑定的快捷支付银行
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		findPayBanksByCustId : function(params, callBack){
			comm.requestFun("findPayBanksByCustId" , params, callBack, comm.lang("myHsCard"));
		},
		/**
		 * 添加快捷支付银行
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		addPayBank : function(params, callBack){
			comm.requestFun("addPayBank", params, callBack, comm.lang("myHsCard"));
		},
		/**
		 * 删除快捷支付银行
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		delPayBank : function(params, callBack){
			comm.requestFun("delPayBank" , params, callBack, comm.lang("myHsCard"));
		},
		
		//获取用户重要信息
		getPersonImportantInfoByResNo : function (data, callback) {
//			$.isFunction(data) && (callback = data, data = null);
			comm.Request({url:'getPersonImportantInfoByResNo',domain:'personWeb'},{
			data:data,
			type:'POST',
			dataType:"json",
			success:function(response){
				callback(response);	
			},
			error: function(){
				alert('请求数据出错！');
			}
		});
		},
		//获取验证码
		getValidateCode : function(data, callback) {
			var param=comm.getRequestParams();
			return comm.domainList['personWeb']+comm.UrlList["generateSecuritCode"]+"?custId="+param.custId+"&type=realNameAuth&"+(new Date()).valueOf();
		},
		//获取重要信息变更验证码
		getImportantValidateCode : function(data, callback) {
			var param=comm.getRequestParams();
			return comm.domainList['personWeb']+comm.UrlList["generateSecuritCode"]+"?custId="+param.custId+"&type=importantInfoChange&"+(new Date()).valueOf();
		},
		
		//更新设置用户实名认证信息
		setVerification : function (data, callback) {
			
			comm.Request({url:'setVerification',domain:'personWeb'},{
				data:data,
				type:'POST',
				dataType:"json",
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		},
		
		//上传文件地址
		getUploadFilePath : function() {
			return comm.domainList.personWeb + 'account/fileUpload?type=webhead&fileType=image';
		},
		
		
		
		
		//获取绑定银行卡详细信息
		getBankDetails :  function (data, callback) {
			
			comm.Request({url:'findBankBindById',domain:'personWeb'},{
				data:data,
				type:'POST',
				dataType:"json",
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
			//callback({"acctType":null,"bankAccount":"1234567890123456","bankAreaNo":"440104","defaultFlag":"N","bankAcctId":"77150aec-ff60-4f72-bb74-788fcf75a8ec","custResNo":"01003010728","custResType":"M","bankBranch":null,"usedFlag":"N","custId":"0100301072820150519","bankCode":"111","provinceCode":"44","cityName":"深圳","bankAcctName":"张三"});
		},
		
		
		
		//修改银行卡信息
		bankModify : function (data, callback) {
			
			comm.Request({url:'editBankBind',domain:'personWeb'},{
				data:data,
				type:'POST',
				dataType:"json",
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
			
			//callback({"code":0,"msg":""});
		},
		
		
		//获取地区
		getArea : function (areaNo, type) {
			var area = null;
			if (type == "0") { //国家
				$.each(comm.getCache("personWeb", "cache").countryList, function (i, o) {
					if (o.areaNo == areaNo) {
						area = o;
						return false;
					}
				});
			}
			if (type == "1") { //省份
				$.each(comm.getCache("personWeb", "cache").countryList, function (i, o) {
					$.each(o.childs, function (j, o2) {
						if (o2.areaNo == areaNo) {
							area = o2;
							return false;
						}
					});
				});
			}
			if (type == "2") {
				//城市
				$.each(comm.getCache("personWeb", "cache").countryList, function (i, o) {
					$.each(o.childs, function (j, o2) {
						$.each(o2.childs, function (k, o3) {
							if (o3.areaNo == areaNo) {
								area = o3;
								return false;
							}
						});
					});
				});
			}
			return area;
		},
		
		//更新互生卡用户信息
		updatePersonInfoCustName : function (data, callback) {
			//comm.requestPost('', data, callback)
			callback({"code":0,"msg":""});
		},
		
		
		
		//获取银行名称
		getBankName : function (bankNo) {
			var bankName = "";
			$.each(comm.getCache("personWeb", "cache").bankList, function (i, o) {
				if (o.bankCode == bankNo) {
					bankName = o.bankName;
					return false;
				}
			});
			return bankName;
		},
		
		
		
		//获取互生卡状态信息
		getHsCardStatus : function (data, callback) {
			$.isFunction(data) && (callback = data, data = null);
			comm.Request({url:'getHsCardStatus',domain:'personWeb'},{
				data:data,
				type:'POST',
				dataType:"json",
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
//			callback({"code":0,"cardId":"xxxxxx-xxxx-xxxx-xxxxx","cardStatus":"N"});
		},
		
		
		
		//获取地区列表，用于三级联动
		getAreaLists : function (data, callback) {
			comm.Request({url:'getAreaLists',domain:'personWeb'},{
				data:data,
				type:'POST',
				dataType:"json",
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
//			callback([{"areaNo":"11","areaName":"北京"}]);
		},
		
		//绑定邮箱
		mailBind : function (data, callback) {
			//comm.requestPost('', data, callback)
			callback({"code":0,"msg":""});
		},
		//修改邮箱
		mailModify : function (data, callback) {
//			comm.requestFun("mailModify",data,callback,comm.lang("myHsCard"));
			comm.requestFunForHandFail("mailModify", data, callback, comm.lang("myHsCard"))
		},
		//验证用户id信息
		validatePersonIDInfo : function (data, callback) {
			//comm.requestPost('', data, callback)
			callback({"code":0,"msg":""});
		},
		
		//获取用户实名认证状态
		getAuthVerification : function (data, callback) {
			$.isFunction(data) && (callback = data, data = null);
			//comm.requestPost('', data, callback)
			callback({"status":""});
			//callback({"status":"Y","sex":1,"profession":"职员","birthAddress":"广东省深圳市","valid_date":"2015-08-15","licenceIssuing":"深圳市南山区公局","postscript":"认证留言","cerPica":"1.png","cerPicb":"2.png","cerPich":"3.png"});
			//callback({"status":"W","sex":1,"profession":"职员","birthAddress":"广东省深圳市","valid_date":"2015-08-15","licenceIssuing":"深圳市南山区公局","postscript":"认证留言","cerPica":"1.png","cerPicb":"2.png","cerPich":"3.png"});
			//callback({"status":"N","cerPica":"1.png","cerPicb":"2.png","cerPich":"3.png","appReason":"照片不清晰"});
		},
		
		
		//获取验证码验证地址
		getcheckCodePath : function(){
			return comm.domainList.local + "checkCode"
		},
		
		
		//设置互生卡状态信息
		setHsCardStatusLock : function (data, callback) {
			comm.Request({url:'setHsCardStatusLock',domain:'personWeb'},{
				data:data,
				type:'POST',
				dataType:"json",
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
//			callback({"code":0,"msg":""});
		},
		//设置互生卡状态信
		setHsCardStatusUnLock : function (data, callback) {
			comm.Request({url:'setHsCardStatusUnLock',domain:'personWeb'},{
				data:data,
				type:'POST',
				dataType:"json",
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
//			callback({"code":0,"msg":""});
		},
		//获取用户互生币余额
		getUserBalace : function (data, callback) {
			$.isFunction(data) && (callback = data, data = null);
			comm.Request({url:'getUserBalace',domain:'personWeb'},{
				data:data,
				type:'POST',
				dataType:"json",
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
//			callback({"code":0,"msg":"","hsbBalance":"100"});
		},
		//收货地址查询
		searchAddress : function (data, callback) {
			comm.requestFun('searchAddress', data, callback,comm.lang("myHsCard"));
		},
		//添加收货地址
		addAddress : function (data, callback) {
			comm.requestFun('addAddress', data, callback,comm.lang("myHsCard"));
		},
		//更新地址查询
		updateAddress : function (data, callback) {
			comm.requestFun('updateAddress', data, callback,comm.lang("myHsCard"));
		},
		//删除收货地址
		deleteAddress : function (data, callback) {
			comm.requestFun('deleteAddress', data, callback,comm.lang("myHsCard"));
		},
		//设置默认收货地址
		setDefaultAddress : function (data, callback) {
			comm.requestFun('setDefaultAddress', data, callback,comm.lang("myHsCard"));
		},
		//补卡下单
		mendCardOrder : function (data, callback) {
			comm.requestFun('mendCardOrder', data, callback,comm.lang("myHsCard"));
		},
		//获取用户收货地址
		getUserAddressList : function (data, callback) {
			$.isFunction(data) && (callback = data, data = null);
			comm.Request({url:'getUserAddressList',domain:'personWeb'},{
				data:data,
				type:'POST',
				dataType:"json",
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
//			callback({"code":0,"msg":"","data":[{"id":"xxx001","receiverName":"张三","phone":"13212341234","province":"广东","city":"深圳","area":"福田区","address":"AAAA大厦","postcode":"123456","isDefault":"0"}]});
		},
		//生成补办互生卡订单号信息
		addReMakeCardOrder : function (data, callback) {
			comm.Request({url:'addReMakeCardOrder',domain:'personWeb'},{
				data:data,
				type:'POST',
				dataType:"json",
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				} 
			});
//			callback({"code":0,"msg":"","order":{"orderNo":"r200508200001","orderAmount":20}});
		},
		//互生卡补办--在线支付
		addReMakeCardOrderByPayBtn : function(data, callback){
			comm.Request({url:'addReMakeCardOrderByPayBtn',domain:'personWeb'},{
				data:data,
				type:'POST',
				dataType:"json",
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				} 
			});
		},
		//添加收货地址
		addUserAddress : function (data, callback) {
			comm.Request({url:'addUserAddress',domain:'personWeb'},{
				data:data,
				type:'POST',
				dataType:"json",
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				} 
			});
//			callback({"code":0,"msg":""});
		},
		//补办互生卡通过互生币支付
		cardReapplyPayByHsb : function (data, callback) {
			//comm.requestPost('', data, callback)
			callback({"code":0,"msg":"","serialNo":"XXX000000000001"});
		},
		//获取用户重要信息变更状态
		getImportantInfoVerification : function (data, callback) {
			$.isFunction(data) && (callback = data, data = null);
			comm.Request({url:'getImportantInfoVerification',domain:'personWeb'},{
				data:data,
				type:'POST',
				dataType:"json",
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
//			callback({"status":"W"});
//			callback({"status":"W","newName":"张三丰","newSex":"1","newCountry":"156","newCerType":"1","newCredentials_no":"123456789123456789","newValid_date":"2018-02-25","newBirthAddress":"深圳市福田区福中路1号","newLicenceIssuing":"深圳市福田区分局","newProfession":"职员","appReason":"照片不清晰","newCerPica":"1.png","newCerPicb":"2.png","newCerPich":"3.png"});
		},
		//设置用户重要信息
		setUserImportantInfo : function (data, callback) {
			comm.Request({url:'changeUserImportantInfo',domain:'personWeb'},{
				data:data,
				type:'POST',
				dataType:"json",
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
//			callback({"code":0,"msg":""});
		},
		//查询客户id下是否有支付失败或未支付的补卡申请
		getCardReapplyByCustId : function(data, callback){
			comm.Request({url:'getCardReapplyByCustId',domain:'personWeb'},{
				data:data,
				type:'POST',
				dataType:"json",
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		}
	};
});