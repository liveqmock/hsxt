define(["commDat/common","commonLan"],function(common) {
	return {
		/** 加载首页页面数据 */
		loadIndxData:function(){
			var self=this;
			self.getLoginDtail(function(){
				
				$("#sLastDate").html(comm.removeNull(comm.getCookie("lastLoginDate")));//最后登录时间
				$("#sLastIp").html(comm.removeNull(comm.getCookie("lastLoginIp")));//上一次iP
				$("#sReserveInfo").html(comm.getCookie("reserveInfo"));//预留信息
				$("#sEntName").html(comm.getCookie("entName"));//企业名称
				$("#sUserAccount").html(comm.getCookie("userName"));//用户帐号
				$("#sEntResNo").html(comm.getCookie("entResNo"));//企业互生号
				
				
			})
		},
		
		/** 获取登录用户明细 */
		getLoginDtail:function(callBack){
			var self=this;
			var isLoadLoginDtail = comm.getCookie("isLoadLoginDtail");
			if (isLoadLoginDtail != "true") {
				comm.setCookie("isLoadLoginDtail", true);	// 已设置登录用户明细，下次无需再次请求后台
				
				//同步请求
				common.operatorDetail(function(rsp) {
					//操作员信息
					var detail = rsp.data;
					for ( var key in detail) {
						comm.setCookie(key, detail[key]);
					}
					
					//预留信息
					common.querjymmylxxsz(function(rsp2) {
						comm.setCookie("reserveInfo", rsp2.data.reserveInfo);
						callBack();
					});
				},function(error){
					comm.setCookie("isLoadLoginDtail", false);
				});
			}else{
				callBack();
			}
		}
	};
});