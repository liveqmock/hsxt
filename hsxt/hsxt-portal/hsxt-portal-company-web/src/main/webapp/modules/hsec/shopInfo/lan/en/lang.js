define(["commSrc/comm"],function() {
	var langConfig = {
		 
		//登录文案
        UserNameIsEmpty : "Enter user name please" ,		 
        PasswordIsEmpty : "Enter password please"  ,
   
		  
	};

	comm.lang = function(name) {
		var str = langConfig[name] || '';
		return str;
	};
	return comm.lang ;
});
