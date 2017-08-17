define(["commDat/common","commonDat/frame/companyInfo","commonLan"],function(common,companyInfo) {
	return {
		/** 加载首页页面数据 */
		loadIndxData:function(){
			var self=this;
			self.getLoginDtail(function(){
				//
				var lastLoginDate=comm.getCookie("lastLoginDate");
				
				$("#sLastDate").html(comm.removeNull(lastLoginDate));//最后登录时间
				//最后登陆IP
				var lastLoginIp=comm.getCookie("lastLoginIp");
				
				$("#sLastIp").html(comm.removeNull(lastLoginIp));//上一次iP
				
				//显示预留信息
				$("#sReserveInfo").html(comm.getCookie("reserveInfo"));
				
				//$("#sEntName").html(comm.getCookie("entName"));//企业名称
				self.setEntName(comm.getCookie("entName"));
				self.loadRightData();
				self.initEntData();
			})
		}
		,
		/** 加载操作员明细 */
		loadRightData:function(){
			var self=this;
			//网上商城图片  add by zhanghh date:2016-04-26
			companyInfo.getMarketInfo(null,function(response){
			     var vShop = response.data.vShop.salerVirtualShop;
			     var httUrl = "resources/img/store_noimg.gif";
			     if(vShop.logo){
				 	 httUrl = response.data.httpUrl + vShop.logo;
				}
			    comm.setCache("chatCache", "companyIcon", comm.removeNull(vShop.logo));
				$("#rightBar_companyPic").attr("src",""+httUrl+"");
			})
			var entCustType=comm.getCookie("entCustType");//企业类型
			//$("#bEntName").html(comm.getCookie("entName"));//企业名称
			//var _bEntName = comm.getCookie("custEntName");
			//$("#bEntName").html("<label title='"+_bEntName+"'>"+(_bEntName.length>11?(_bEntName.substr(0,11)+"..."):_bEntName)+"<label>");//企业名称
			self.setEntName(comm.getCookie("entName"));
			$("#sEntResNo").html(comm.getCookie("entResNo"));//企业互生号
			$("#sUserAccount").html(comm.getCookie("userName"));//用户帐号
			$("#sEntRegTime").html(comm.formatDate(comm.getCookie("openDate"),"yyyy-MM-dd"));//  注册日期
			$("#sEntTypeName").html(comm.getNameByEnumId(entCustType,  comm.lang("common").entTypeEnum));//企业类型
			//成员企业类型，年费缴 扣日期不显示
			if(entCustType=="2"){
				$("#sEntExpireDateHtml,#sEntExpireDate").hide();
			}else{
				$("#sEntExpireDate").html(comm.formatDate(comm.getCookie("expireDate"),"yyyy-MM-dd"));//年费缴 扣日期
			}
		},
		/** 是否设置交易密码、预留信息 */
		isSetPwdSet:function(){
			//控制系统安全模块菜单显示
			common.querjymmylxxsz({},function(res){
				isSet=res.data; 
				comm.setCookie("reserveInfo", isSet.reserveInfo);
				comm.setCookie("tradPwd", isSet.tradPwd);
				
				$("#sReserveInfo").html(comm.getCookie("reserveInfo"));//预留信息
				//判断是否已设置交易密码
				/*if(isSet.tradPwd==false){	
					$("#subNav_7_03,#subNav_7_04").parent().hide();//隐藏修改交易密码、重置交易密码菜单
				}else{
					$("#subNav_7_02").parent().hide();//隐藏设置交易密码菜单 
				}
				
				//判断是否设置预留信息
				if(isSet.reserveInfo==""){	
					$("#subNav_7_07").parent().hide(); //隐藏修改预留信息菜单
				}else{
					$("#subNav_7_06").parent().hide();//隐藏设置预留信息菜单
				}*/
			});
			
			var entResType = comm.getCookie("entResType");
			//成员企业没有投资账户、缴纳系统使用年费、停止积分活动申请、参与积分活动申请
			if(entResType == "2"){
				$("#subNav_0_05").parent().hide();//隐藏 【投资账户】菜单
				$("#subNav_3_02").parent().hide();//隐藏 【缴纳系统使用年费】菜单
				$("#subNav_3_05").parent().hide();//隐藏 【停止积分活动申请】菜单
				$("#subNav_3_06").parent().hide();//隐藏 【参与积分活动申请】菜单
			}else if(entResType == "3"){
				//托管企业没有 成员企业资格维护 菜单
				$("#subNav_3_04").parent().hide();//隐藏 【成员企业资格维护】菜单
				
			}
		},
		/** 查询企业状态信息 **/
		searchEntStatusInfo:function(){
			common.searchEntStatusInfo({},function(res){
				var status = res.data.info.baseStatus;
				comm.setCookie("entStatus",status);
//				if(status == '7'){
//					$("#020200000000_subNav_020205000000").parent().hide();//当企业状态为“停止积分活动”状态时  ,隐藏 【停止积分活动申请】菜单
//				}else if(status == '1'){
//					$("#020200000000_subNav_020206000000").parent().hide();
//				}
			});
		},
		/** 获取登录用户明细 */
		getLoginDtail:function(callBack){
			var self=this;
			
			var isLoadLoginDtail = comm.getCookie("isLoadLoginDtail");
			if (isLoadLoginDtail != "true") {
				comm.setCookie("isLoadLoginDtail", true);// 已设置登录用户明细，下次无需再次请求后台
				
				//是否设置交易密码、预留信息
				self.isSetPwdSet();
				self.searchEntStatusInfo();
				
				common.operatorDetail(function(rsp) {
					var detail = rsp.data;
					// 循环设置cookie
					for ( var key in detail) {
						comm.setCookie(key, detail[key]);
					}
				},function(){
					comm.setCookie("isLoadLoginDtail", false);
				});
			}
			
			callBack();
		},
		/**
		 * 初始化数据
		 */
		initEntData : function(){
			var self=this;
			common.findEntAllInfo(null, function(res){
				if(null!=res&&null!=res.data){
					var entName = res.data.entCustName;
					self.setEntName(entName);
					comm.setCookie('entName',entName);
					comm.setCookie('custEntName',entName);
					comm.setCache("companyInfo", "entAllInfo", res.data);
				}
			});
		},
		/**
		 *设置企业名称
		 */
		setEntName : function(entName){
			if(entName){
				$("#sEntName").attr('title',entName);
				var stEntName = entName.length>20?(entName.substr(0,20)+"..."):entName;
				var bEntName = entName.length>11?(entName.substr(0,11)+"..."):entName;
				$("#bEntName").html("<label title='"+entName+"'>"+bEntName+"<label>");//企业名称
				$("#sEntName").html(stEntName);
			}
		}
	};
});