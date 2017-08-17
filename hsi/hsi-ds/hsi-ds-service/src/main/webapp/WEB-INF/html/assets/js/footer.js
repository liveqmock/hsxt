(function ($) {
    // 登出
    $("#signout").on("click", function () {
    	signout();
    });

})(jQuery);

function signout() {
    $.ajax({
        type: "GET",
        url: "api/account/signout"
    }).done(function (data) {
    	    	
        if (data.success === "true") {
            VISITOR = {};
            
            getSession();
        }
        
        window.location.href = "login.html";
        
//        else {
//            VISITOR = {};
//            getSession();
//            
//        	window.location="/";
//        }        
    });
}