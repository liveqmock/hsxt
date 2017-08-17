define(["commDat/common","commonLan"],function(common) {
	return {
		/** 加载首页页面数据 */
		loadIndxData:function(){
			var self=this;
			self.getLoginDtail(function(){
				$("#sLastDate").html(comm.removeNull(comm.getCookie("lastLoginDate")));//最后登录时间
				$("#sLastIp").html(comm.removeNull(comm.getCookie("lastLoginIp")));//上一次iP
				$("#sEntName").html(comm.getCookie("entName"));//企业名称
				$("#sUserAccount").html(comm.getCookie("userName"));//用户帐号
				$("#sEntResNo").html(comm.getCookie("pointNo"));//企业互生号
			})
		},
		/** 获取登录用户明细 */
		getLoginDtail:function(callBack){
			var self=this;
			var isLoadLoginDtail = comm.getCookie("isLoadLoginDtail");
			if (isLoadLoginDtail != "true") {
				comm.setCookie("isLoadLoginDtail", true);	// 已设置登录用户明细，下次无需再次请求后台
				
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
		}
	};
});