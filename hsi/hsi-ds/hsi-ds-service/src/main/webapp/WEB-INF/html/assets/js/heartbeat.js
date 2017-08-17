
var counter = 0;

// 初始入口
(function () {
    // 检验Session是否有效
    setInterval(function() {
		 $.ajax({
		     type: "GET",
		     url: "api/account/checksession",
		     timeout: 3000 // 3s timeout
		 }).done(function (data) {
		     if (data.success === "true") {
		     } else {
		    	 if(2 == counter) {
		    		 window.location.href = "login.html";
		    	 }
		    	 
		    	 counter++;
		     }
		 }).fail(function (xmlHttpRequest, textStatus) {
			 if(2 == counter) {
				 window.location.href = "login.html";
			 }
			 
			 counter++;
		 });
	}, 30*1000);
})();