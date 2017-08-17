	define(["text!commTpl/frame/nav.html",
	        "commonSrc/cacheUtil",
	        "text!commTpl/frame/subNav.html",
		"commSrc/frame/index"
	        ],function(tpl,cacheUtil,subNavTpl,index){
		
		//执行调用缓存方法查询用户权限
		cacheUtil.findPermissionByCustidList(function(response){
			
			//获取缓存中一级菜单
			var match = comm.findPermissionJsonByParentId("0");
			
			//加载中间菜单） 
			$('#nav').html(_.template(tpl,match));
			
			//菜单按钮事件开始 
			$("#navUl li").click(function(e){
				//alert('top_menu');
				var parentId= $(this).children().children("span").attr("id");
				$(this).siblings().children().removeClass("navMenu_hover");
				$("#sideBarMenu li").addClass('sideBar_hover').siblings().removeClass('sideBar_hover');
				$(this).children().addClass("navMenu_hover");
				
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
					
					var ul_width = 0,
						click_num = 0,
						move_width = 0,
						currentUl_left = 0,
						max_width = 1110,
						subNavLi = [],
						currentUl = $("#subNav_" + idNumber),
						currentUl_length = currentUl.children("li").length;
				}
				
				//查询三级菜单
				var levelMenu = comm.findPermissionJsonByParentId(parentId);
				//加载中间菜单
				$('#subNav').html(_.template(subNavTpl, levelMenu));
				
				
				$("#subNav li").release().click(function(key){
					$(this).parent().children().children().removeClass("active");
					$(this).children().addClass("active");
					var childrenId = $(this).children().attr("id");
					var strMenuUrl =$(this).children().attr("menuUrl");
					var lstMenuUrl;
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
		//菜单按钮事件结束
		
		//子菜单事件集合
		var navObj = {};
		//子菜单事件触发方法
		function triggerClick(moduleSrc,fileName){
			require([moduleSrc],function(src){
				navObj["fileName"] = src;
				navObj["fileName"].show();	
			})
			if (navObj["fileName"]){
				navObj["fileName"].show();			
			}	
		}
		
		
		
	       //jquery.validate扩展验证 add by zhucy 2016-02-03 互生代码合并
	       comm.addMethodValidator();
	
	       //首页登录信息显示  add by zhucy 2016-02-03 互生代码合并
	       index.loadIndxData();
	       
		
		return tpl;	
	});