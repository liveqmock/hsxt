define(["text!commTpl/frame/sideBar.html","commDat/common","text!commTpl/frame/subNav.html"],function(tpl,commonAjax,subNavTpl){
	
	//加载左边导航栏
	jQuery.extend({
		getQueryString:function(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
			var search =  encodeURI(window.location.search);
		    var r = search.substr(1).match(reg);
		    if (r != null) return unescape(r[2]); return null;
		}
	});	
	//封装传递参数查询最上级菜单
	var paramJson = {parentId:"0"};
	commonAjax.findPerontIdByPermission(paramJson,function(response){
		
		//加载中间菜单） 
		$('#sideBar').html(_.template(tpl, response));
		//console.info(res);
		/*companyAjax.getCompanyInfo(null,function(response){
			var welHtml = _.template(welcomeTpl, response);
			$('.operationsArea').html(welHtml);
		})*/
		
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
			$(this).addClass('menu_hover').siblings().removeClass('menu_hover');
			var parentId = $(this).children("span").attr("id");
			var objParam = {"parentId": parentId};
			
			
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
			commonAjax.findPerontIdByPermission(objParam,function(response){
				//加载中间菜单
				
				$('#subNav').html(_.template(subNavTpl, response));
				
				/*子菜单滚动 add by kuangrb 2016-04-27*/
    			comm.subMenu_scroll($("#subNav").find('ul'));
    			
    			/*子菜单滚动 add by kuangrb 2016-04-27*/
    		 	$('#subNav').resize(function(){
    		 		comm.subMenu_scroll($("#subNav").find('ul'));
    		 	});
    		 	/*end*/
				
				$("#subNav li").release().click(function(key){
					
					var childrenId = $(this).children().attr("id");
					
					$(this).parent().children().children().removeClass("s_hover");
					$(this).children().addClass("s_hover");
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
				})
			}) 
		});
		
	
	});
	
	
 
	//菜单上下移动效果结束
	var navObj = {};
	
	return tpl;	
});