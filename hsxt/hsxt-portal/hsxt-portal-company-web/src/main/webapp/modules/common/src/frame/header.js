define(["text!commTpl/frame/header.html",
        "commDat/common",
        "commonDat/frame/companyInfo",
        "text!commTpl/frame/welcome.html"
        ],function(tpl,common,companyInfoAjax,welcomeTpl){

	$('#header').html(tpl);
        $(".logo, .icon_i_home, #a-header-home").click(function () {
		location.href = "index.html";
	});
	
	$("#home_Page").click(function(){
		companyInfoAjax.getCompanyInfo(null,function(response){
			var welHtml = _.template(welcomeTpl, response);
			$('.operationsArea').html(welHtml);
			$('#subNav').html("");
		})
	});
	
        $("#loginOff").click(function () {
		comm.confirm({
			title: "安全退出提示",
			content: "是否要退出？",
			imgFlag: 1,
			callOk: function () {
				//清除cookies
				var cookies=document.cookie;  //获取所有cookie
				var cookieArray=cookies.split(";");
				
				for(var i =0;i<cookieArray.length;i++){
					var key=cookieArray[i].split("="); //获取cookie键
					//清除cookis，设置过期时间
					$.cookie(key[0].replace(" ",""),null,{expires:-1,path:"/"});
				}
				window.location.replace(comm.domainList["quitUrl"]);
			}
		});
	});

	/*
	 *  加载右边服务模块
	 *
	 
	require(["commSrc/frame/HuDong/rightBar"],function(rightBar){	
		rightBar.init();
	});
	*/	
	return tpl;	
	
});
