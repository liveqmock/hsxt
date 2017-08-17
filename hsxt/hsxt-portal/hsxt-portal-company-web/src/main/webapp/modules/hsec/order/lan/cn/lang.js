define(["commSrc/comm"],function() {
	var langConfig = {
		
 
		
		//登录文案
        UserNameIsEmpty : "请输入账号" ,		 
        PasswordIsEmpty : "请输入密码"  ,
  
		
		
		
		  
	};

	comm.lang = function(name) {
		var str = langConfig[name] || '';
		return str;
	};
	return comm.lang ;
});
