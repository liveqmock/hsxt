define(["text!commTpl/frame/header.html","commSrc/frame/index"], function (tpl,index) {
	//加载头部
	$("#headerContent").html(_.template(tpl, {
		today: comm.getCurrDate(),
		week: ("日一二三四五六".split(""))[new Date().getDay()]
	}));

	//地址栏参数中带有互生卡的话，则直接取得
	var id = $.getQueryString("id");
	id && $("#card_id em").text(id);

	//加载头部数据
	index.loadHeadData();
	
	//安全退出单击事件
	$("#loginOff").click(function () {
		$.confirmAlert("安全退出提示", "是否要退出？", function () {
			//清除cookies
			var cookies=document.cookie;  //获取所有cookie
			var cookieArray=cookies.split(";");
			
			for(var i =0;i<cookieArray.length;i++){
				var key=cookieArray[i].split("="); //获取cookie键
				//清除cookis，设置过期时间
				$.cookie(key[0].replace(" ",""),null,{expires: -1, path: '/',domain:comm.firstDomain()});
				//$.cookie(key[0].replace(" ",""),null,{expires:new Date().getTime()-10000,path:"/"});
			}
			window.location.replace(comm.domainList["quitUrl"]);
		});
	});
});
