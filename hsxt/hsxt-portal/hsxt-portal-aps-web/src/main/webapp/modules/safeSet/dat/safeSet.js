define(['safeSetLan'],function () {
	return {
	
		findLoginInfo : function (data, callback) {
			comm.requestFun("findLoginInfo" , data, callback,comm.lang("safeSet"));
			
		},
		
		updatePwd : function (data, callback) {
		//	comm.requestFun("updatePwd" , data, callback,comm.lang("safeSet"));
			comm.requestFunForHandFail("updatePwd" , data, callback,comm.lang("safeSet"));
		}
		
	};
});