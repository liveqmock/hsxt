define(["text!commTpl/frame/sideBar.html",
        "commonSrc/cacheUtil",
        "text!commTpl/frame/subNav.html",
        "text!commTpl/frame/welcome.html"
        ],function(tpl,cacheUtil,subNavTpl,welcomeTpl){
	//加载左边导航栏
	jQuery.extend({
		getQueryString:function(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
			var search =  encodeURI(window.location.search);
		    var r = search.substr(1).match(reg);
		    if (r != null) return unescape(r[2]); return null;
		}
	});	
	cacheUtil.findPermissionByCustidList(function(response){
		
		//获取缓存中一级菜单
		var match = comm.findPermissionJsonByParentId("0");
			
		//加载中间菜单） 
		$('#sideBar').html(_.template(tpl, match));
		
		
		//菜单上下移动效果开始
		var menuLength = $("#sideBarMenu li").length;
		var currentTop = 0;
		var liHeight = 84;
		$("#sideBarBtn_up").click(function(){
			if(currentTop < 0){
				$("#sideBarMenu").css("top",(parseInt(currentTop) + liHeight) + "px");
				}
			currentTop = $("#sideBarMenu").css("top").replace("px","");
			});
			
		$("#sideBarBtn_down").click(function(){		
			if(currentTop > -(menuLength - 7)*liHeight){
				$("#sideBarMenu").css("top",(parseInt(currentTop) - liHeight) + "px");
				}
			currentTop = $("#sideBarMenu").css("top").replace("px","");
			});
		//菜单事件
		$("#sideBarMenu li").click(function(e){
			$(this).addClass('sideBar_hover').siblings().removeClass('sideBar_hover');
			var parentId= $(this).children("span").attr("id")
			var objParam={"parentId":parentId}
			
			
			$(".subNav1 ul").hide();
			currentLiId = parentId;
			if (currentLiId){
				var idArry = [];
				//idArry = currentLiId.split("_");
				var idNumber = currentLiId;
				$("#subNav_" + idNumber).show();

				$("#subNav_" + idNumber).children("li:first").children("a")
				.click();//点击一级菜单默认显示第一个子菜单页面
				//子菜单滚动 20150310 add
			}
			var levelMenu = comm.findPermissionJsonByParentId(parentId);
			
			//加载中间菜单
			$('#subNav').html(_.template(subNavTpl, levelMenu));
			
			//设置，修改预留信息显示和隐藏
			$("#subNav li").release().click(function(key){
				
				var childrenId = $(this).children().attr("id");
				
				$(this).parent().children().children().removeClass("active");
				$(this).children().addClass("active");
				var childrenId = $(this).children().attr("id");
				var strMenuUrl =$(this).children().attr("menuUrl");
				var lstAuthUrl;
					var arr = new Array();
					if(""!=strMenuUrl&&null!=strMenuUrl){
						lstMenuUrl=strMenuUrl.split(",")
						for(var i=0;i<lstMenuUrl.length;i++){
							arr[i]=lstMenuUrl[i];
						}
					}
					require(arr,function(src){	
						src.showPage();
					})
			});
		});
		
	
	});
	//菜单上下移动效果结束
	var navObj = {};
	return tpl;	
});