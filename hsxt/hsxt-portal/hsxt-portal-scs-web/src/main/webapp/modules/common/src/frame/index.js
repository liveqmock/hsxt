define(["commDat/common","commonLan"],function(common) {
	var self= {
		/** 加载首页页面数据 */
		loadIndxData:function(){
			//var self=this;
			self.getLoginDtail(function(){
				var lastLoginDate = comm.removeNull(comm.getCookie("lastLoginDate"));
				lastLoginDate = lastLoginDate == "null"?"":lastLoginDate;
				var lastLoginIp = comm.removeNull(comm.getCookie("lastLoginIp"));
				lastLoginIp = lastLoginIp == "null"?"":lastLoginIp;
				$("#sLastDate").html(lastLoginDate);//最后登录时间
				$("#sLastIp").html(lastLoginIp);//上一次iP
				$("#sReserveInfo").html(comm.getCookie("reserveInfo"));//预留信息
				//$("#sEntName").html(comm.getCookie("entName"));//企业名称
				self.setEntName(comm.getCookie("entName"));
				//是否设置交易密码、预留信息
				self.isSetPwdSet();
			});
			self.initEntData();
		}
		,
		/** 加载操作员明细 */
		loadRightData:function(){
			//var self=this;
			self.getLoginDtail(function(){
				self.setEntName(comm.getCookie("entName"));
				//$("#bEntName").html(comm.getCookie("entName"));//企业名称
				$("#sEntResNo").html(comm.getCookie("entResNo"));//企业互生号
				$("#sUserAccount").html(comm.getCookie("userName"));//用户帐号
				$("#sEntRegTime").html(comm.formatDate(comm.getCookie("openDate"),"yyyy-MM-dd"));//  注册日期
				$("#sEntExpireDate").html(comm.formatDate(comm.getCookie("expireDate"),"yyyy-MM-dd"));//年费缴 扣日期
				$("#sEntTypeName").html(comm.lang("common").entTypeEnum[comm.getCookie("entCustType")]);//企业类型
			});
		},
		/**
		 * 初始化数据
		 */
		initEntData : function(){
			common.findEntAllInfo(null, function(res){
				comm.setCache("companyInfo", "entAllInfo", res.data);
				//$("#sEntName").html(res.data.entCustName);//企业名称
				//$("#bEntName").html(res.data.entCustName);//企业名称
				self.setEntName(res.data.entCustName);
				comm.setCookie('entName',res.data.entCustName);
				comm.setCookie('custEntName',res.data.entCustName);
			});
		},
		/** 是否设置交易密码、预留信息 */
		isSetPwdSet:function(){
			//控制系统安全模块菜单显示
			var reserveInfo = comm.getCookie("reserveInfo");
			var tradPwd = comm.getCookie("tradPwd");
			
			//判断是否已设置交易密码
			if(tradPwd=="false"){	
				$("#subNav_6_05,#subNav_6_06").parent().hide();//隐藏修改交易密码、重置交易密码菜单
			}else{
				$("#subNav_6_02").parent().hide();//隐藏设置交易密码菜单 
			}
			
			//判断是否设置预留信息
			if(reserveInfo==""){	
				$("#subNav_6_03").parent().hide(); //隐藏修改预留信息菜单
			}else{
				$("#subNav_6_01").parent().hide();//隐藏设置预留信息菜单
			}
		},
		/** 获取登录用户明细 */
		getLoginDtail:function(callBack){
			//var self=this;
			var isLoadLoginDtail = comm.getCookie("isLoadLoginDtail");
			if (isLoadLoginDtail != "true") {
				comm.setCookie("isLoadLoginDtail", true);	// 已设置登录用户明细，下次无需再次请求后台
				
				//是否设置交易密码、预留信息
				common.querjymmylxxsz({},function(res){
					comm.setCookie("reserveInfo", res.data.reserveInfo);
					$("#sReserveInfo").html(comm.getCookie("reserveInfo"));//预留信息
					comm.setCookie("tradPwd", res.data.tradPwd);
				});
				
				//操作员明细
				common.operatorDetail(function(rsp) {
					var detail = rsp.data;
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
		 *设置企业名称
		 */
		setEntName : function(entName){
			if(entName){
				$("#sEntName").attr('title',entName);
				var bEntName = entName.length>11?(entName.substr(0,11)+"..."):entName;
				var sEntName = entName.length>19?(entName.substr(0,19)+"..."):entName;
				$("#bEntName").html("<label title='"+entName+"'>"+bEntName+"<label>");//企业名称
				$("#sEntName").html(sEntName);
			}
		}
	};
	return self;
});