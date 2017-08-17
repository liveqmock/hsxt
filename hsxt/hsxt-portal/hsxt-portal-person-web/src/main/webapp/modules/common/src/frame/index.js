define(["commDat/common","fckr_accountManagementDat/accountManagement"],function(common,accountManagementDataModel) {
	return {
		/** 加载头数据 */
		loadHeadData:function(){
			var self=this;
			self.getLoginDtail(function(){
				var loginResNo=$.cookie('resNo');//互生卡号
				$("#card_id em").text(loginResNo);
				var hs_isCard = comm.getCookie('hs_isCard');
				//非持卡人判断
				if(comm.isNotEmpty(hs_isCard) && hs_isCard =='1')
				{
					//非空验证
					try{
						//查询非持卡人网络信息
						accountManagementDataModel.findNetworkInfoByCustId(null, function(response){
							//持卡人设置用户名
							$("#card_username em").text(comm.plusXing(response.data.name,1,0,true));
							comm.setCookie('custName',response.data.name);
						});
					}catch(ex){
						alert(ex);
					}
					
					
				}else
				{try{
					//持卡人设置用户名
					common.findConsumerInfo(null,function(resp){
						if(resp.data.certype == '3'){
							$("#card_username em").text(resp.data.entName);
						}else{
							$("#card_username em").text(comm.removeNull(resp.data.realName));
						}
					});
				}catch(ex){
					alert(ex);
				}
				}
				
			})
		},
		/** 加载中部数据 */
		loadcentrData:function(){
			var self=this;
			self.getLoginDtail(function(){
				var ensureInfo=$.cookie('ensureInfo');//预留信息
				$("#nav_reserver_info").text(ensureInfo);
			})
		},
		/** 加载首页页面数据 */
		loadIndxData:function(isNav){
			var self=this;
			self.getLoginDtail(function(){
				//加载用户状态
				var custId=$.cookie("custId"); //客户操作号
				var loginIp=$.cookie('lastLoginIp');//上次登录IP
				var lastLoginDate=$.cookie('lastLoginDate');//上次登录时间
				var nickName=$.cookie('custName');
				var loginResNo=$.cookie('resNo');//互生卡号
				var isRealnameAuth=$.cookie('isRealnameAuth');//1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证
				var isAuthMobile=$.cookie("isAuthMobile"); //是否验证手机	1:已验证 0: 未验证
				var isAuthEmail=$.cookie("isAuthEmail"); //是否验证邮件	1:已验证 0: 未验证
				var isBindBank=$.cookie("isBindBank"); //是否绑定银行卡	1:已验证 0: 未验证
				
				var cardType = comm.getCookie("hs_isCard");	//1：非持卡人  2：持卡人
				
				//非持卡人电话号加密
				if(cardType == '1')
				{
					//防止报错导致界面加载不出来 
					try{
						loginResNo = loginResNo.substring(0, 3)+"****"+loginResNo.substring(7, loginResNo.length);
					}catch(ex){
					}
					
				}
				$("#card_id em, #pointNo_index").text(loginResNo);
				$("#lastLogin_index").text(comm.removeNull(lastLoginDate));
				$("#LoginIP_index").text(comm.removeNull(loginIp));
				
				//已认证状态
				var cerLogo="<i class=\"icon_tips tips_2\" style=\"top:-5px;left:0;height:14px;width:14px;\"></i>";
				
				//实名状态
				switch (isRealnameAuth) {
				case "2":
					$("#userRegister_index").append(cerLogo);
					break;
				case "3":
					$("#userRegister_index,#verification_index").append(cerLogo);
					break;
				}
				
				//手机验证
				if(isAuthMobile=="1"){
					$("#phone_bind_index").append(cerLogo);
				}
				//邮箱验证
				if(isAuthEmail=="1"){
					$("#mailBind_index").append(cerLogo);
				}
				//银行卡验证
				if(isBindBank=="1"){
					$("#bankBind_index").append(cerLogo);
				}
				//重要信息验证提示
				if(comm.isNotEmpty(isNav)){
					self.importantAuthPrompt();
				}
			})
		},
		/** 获取登录用户明细 */
		getLoginDtail:function(callBack){
			var self=this;
			
			var isLoadLoginDtail =comm.getCache("person","loginCookie");
			
			if (!isLoadLoginDtail) {
				
				// 已设置登录用户明细，下次无需再次请求后台
				comm.setCache("person","loginCookie",true);
				
				//获取操作员信息
				common.operatorDetail(function(rsp) {
					var detail = rsp.data;
					
					for ( var key in detail) {
						//document.cookie.indexOf("username3=")
						// 设置cookie
						comm.setCookieForRootPath(key, detail[key]);
					}
				},function(rsp){
					comm.delCache("person","loginCookie");
				});
			}
			
			callBack();
		},
		/** 重要信息验证提示 */
		importantAuthPrompt:function(){
			var isAuth;  //重要信息是否已验证  
			var authTitle;//重要信息提示  
			
			var token = $.cookie('token');//随机token
			var importantAuthPrompt = token + "1";
			var isiImportantAuthPrompt=comm.getCookie("importantAuthPrompt");  //是否已提示过
			
			if(isiImportantAuthPrompt!=importantAuthPrompt)
			{
				var isRealnameAuth=$.cookie('isRealnameAuth');//1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证
				var isAuthMobile=$.cookie("isAuthMobile"); //是否验证手机	1:已验证 0: 未验证
				//var isAuthEmail=$.cookie("isAuthEmail"); //是否验证邮件	1:已验证 0: 未验证
				var isAuthEmail=$.cookie("email"); //是否验证邮件	1:已验证 0: 未验证
				var isBindBank=$.cookie("isBindBank"); //是否绑定银行卡	1:已验证 0: 未验证
				
				if(isRealnameAuth=="1"){
					isAuth="realnameReg";
					authTitle="实名注册";
				}else if(isAuthMobile=="0"){
					isAuth="mobileBind";
					authTitle="手机绑定";
				}else if(isAuthEmail==""){
					isAuth="emailBind";
					authTitle="邮箱绑定";
				}else if(isRealnameAuth=="2"){
					isAuth="realnameAuth";
					authTitle="实名认证";
				}else if(isBindBank=="0"){
					isAuth="bankBind";
					authTitle="银行卡绑定";
				}
				
				if(comm.isNotEmpty(isAuth)){
					$("#promptContent").html("您尚未进行"+authTitle+"，为保障您在平台的权益，请尽快完善！<a rel=\""+isAuth+"\" href=\"javascript:;\" id=\"a_smzc\">点我进行"+authTitle+"</a>。");
					$("#nav_alert_div_4,#nav_alert_div_3").show();
					//提示后写入标识，重新登录后再提示
					comm.setCookieForRootPath("importantAuthPrompt",importantAuthPrompt);
				}
			}
		}
	};
});