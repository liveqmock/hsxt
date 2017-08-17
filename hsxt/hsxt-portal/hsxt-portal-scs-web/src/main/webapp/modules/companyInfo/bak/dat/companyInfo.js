define(['companyInfoLan'],function () {
	
	return {
		
		/***
		 * 参可为空，返回的是基本参数 ，
		 * 
		 * 参数格式必须为键值列表,如:{k1:v1,k2:v2}
		 */
		wrapParam : function (paraData){
			//测试cookie读取操作
			/*$.cookie('cookie_custId', 'cookie_custId');
			$.cookie('cookie_pointNo', 'cookie_pointNo');
			$.cookie('cookie_token', 'cookie_token');*/
			//读取
			//var custId = $.cookie('cookie_custId'); // 读取 cookie 客户号
			//var hsNo = $.cookie('cookie_pointNo'); // 读取 cookie pointNo
			//var token = $.cookie('cookie_token'); // 读取 cookie pointNo
			
			var custId = 'custId_p25_2015_10_16'; 	// 读取 cookie 客户号
			var hsNo ='custId_p25_2015_10_16'; 	// 读取 cookie pointNo
			var token ='token_custId_p25_2015_10_16';
			var entCustId ='token_entcustId_p25_2015_10_16';
			var entResNo ='token_entResNo_p25_2015_10_16';
			var param={
					custId  : custId,
					hsNo    : hsNo,
					token   : token,
					entCustId : entCustId,
					entResNo : entResNo
			};
			
			if(typeof(paraData) != undefined &&paraData!=""&&paraData!=null){
				 return $.extend(param,paraData);
			}else {
				return param;
			}
		},
		
		getSystemInfo : function(){
			return {"platform_currency":"rmb",
				"custName":"杨过",
				"custId":"001231",
				"entCustId":"09186630000162706727708672",
				"entResNo":"513216513213",
				"resNo":"216513213",
				"entCustName":"深圳XXX公司",
				"entResType":"3",
				"entCustType":"3",
				"custType":"1",
				"countryCode" : "001",
				"countryName" : "中国",
				"isRealnameAuth" : "3"
				};
		},
		
		//获得服务器地址getFsServerUrl
		getFsServerUrl : function(pictureId) {
			var custId = 'custId_p25_2015_10_16'; 	// 读取 cookie 客户号
			var hsNo ='custId_p25_2015_10_16'; 	// 读取 cookie pointNo
			var token ='token_custId_p25_2015_10_16';
			
			return  comm.domainList['fsServerUrl']+pictureId+"?userId="+custId+"&token="+token;
		},
		
		
		//弹出异常信息
		errorInfoPop : function(statusCode){
			//通过错误码获得提示信息 
			comm.alert({
				content: comm.lang("companyInfo")['statusCodes'][statusCode],
				imgClass: 'tips_error'
			});
		},
		
		isRealNameAuth : function(){
			//登录信息中获得，实名认证状态
			var isRealnameAuth="3";
			return isRealnameAuth=="3"?true:false;
		},
		//样例图片
		findSamplePictureId : function (data, callback) {
			comm.requestFun("findSamplePictureId" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		
		//注册信息查询
		findSysRegisterInfo : function (data, callback) {
			comm.requestFun("findSysRegisterInfo" ,data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		//注册信息查询
		findSysRegisterInfo1 : function (data, callback) {
			comm.syncRequestFun("findSysRegisterInfo" ,data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		//查看工商登记信息
		findSaicRegisterInfo : function (data, callback) {
			comm.requestFun("findSaicRegisterInfo" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		//查看联系人信息
		findContactInfo : function (data, callback) {
			comm.requestFun("findContactInfo" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		
		//查看联系人信息
		updateContactInfo : function (data, callback) {
			comm.requestFun("updateContactInfo" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		
		//验证邮箱地址
		validationEmail : function (data, callback) {
			comm.requestFun("validationEmail" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		
		//更新工商注册信息
		updateEntBaseInfo : function (data, callback,errCallBack){
			comm.requestFun("updateEntBaseInfo" , data, callback,comm.lang("companyInfo")["errorCodes"],errCallBack);
		},
		
		//查看银行帐户列表
		findCardList : function (data, callback) {
			comm.requestFun("findCardList" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		//查找我银行列表 
		findApplyBankList : function(data,callback){
			comm.requestFun("findApplyBankList" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		//修改银行账户信息
		updateBankAccountInfo : function(data,callback){
			comm.requestFun("updateBankAccountInfo" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		//绑定银行卡
		addBankCard : function(data,callback){
			comm.requestFun("addBankCard" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		//解除
		delBankCard : function(data,callback){
			comm.requestFun("delBankCard" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		
		//查找省份列表 
		findProvinceList : function(data,callback){
			var list=[{'name':'广东','value' : 1},{'name':'湖南','value':2}];
			callback(list);
			//comm.requestFun("findProvinceList" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		
		
		//查找城市列表 
		findCityList : function(data,callback){
			var list=[{'name':'广州','value':'1','province':'1'},{'name':'深圳','value':'2','province':'1'},{'name':'长沙','value':'3','province':'2'}]
			var array=[];
			$.each(list,function(i,v){
				if(data==v.province){
					array.push(this);
				}
			});
			
			callback(array);
			//comm.requestFun("findCityList" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		
		
		
		//查看实名认证信息
		findRealNameAuthInfo : function (data, callback) {
			comm.requestFun("findRealNameAuthInfo" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		
		//提交实名认证
		submitRealNameAuthData : function (data, callback,callback1) {
			comm.requestFun1("submitRealNameAuthData" , data, callback,comm.lang("companyInfo")["errorCodes"],callback1);
		},
		
		
		//查看企业重要信息
		findImportantInfo : function (data, callback) {
			comm.requestFun("findImportantInfo" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		
		//查找变更记录
		findChangeRecord : function (data, callback) {
			comm.requestFun("findChangeRecord" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		
		//查看重要信息变更的申请单信息
		findChangeApplyOrder : function (data, callback) {
			comm.requestFun("findChangeApplyOrder" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		
		//更新企业重要信息
		submitChangeApply : function (data, callback) {
			comm.requestFun("submitChangeApply" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		
		//更新企业重要信息
		submitChangeApply1 : function (data, callback,callback1) {
			comm.requestFun("submitChangeApply" , data, callback,comm.lang("companyInfo")["errorCodes"],callback1);
		},
		
		//查实名认证
		findRealNameAuthApply : function (data, callback) {
			comm.requestFun("findRealNameAuthApply" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		
		
		//查看企业重要信息
		findEntInfoChangedDetail : function (data, callback) {
			comm.requestFun("findEntInfoChangedDetail" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		
		//验证码校验 
		checkVerifiedCode : function (data, callback) {
			comm.requestFun("checkVerifiedCode" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		
		//加载验证码
		loadVerifiedCode : function () {
			return Math.floor(Math.random()*9999+1000);
		},
		
		//加载图片
		loadPicture : function (data, callback) {
			comm.requestFun("loadPicture" , data, callback,comm.lang("companyInfo")["errorCodes"]);
		},
		
		//上传文件地址
		getUploadFilePath : function() {
			return  comm['domainList'].fsServerUrl+comm['UrlList'].fileupload;
		},
	};
});