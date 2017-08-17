define(["text!commTpl/frame/nav.html", "commDat/common", "commSrc/frame/index", "text!commTpl/frame/subNav.html"], function (tpl, common, index, subNavTpl) {



    //加载中间菜单）

    $('#nav').html(tpl);

    //菜单按钮事件开始
    var currentClick;
    common.findPermissionByCustidList(null, function (response) {
        //将菜单存入到缓存中
        comm.setCache("scs", "ListMenu", response.data);

        //加载中间菜单）
        $('#nav').html(_.template(tpl, response));


        //菜单按钮事件开始
        $("#navUl li").click(function (e) {
            //alert('top_menu');
            var parentId = $(this).children().children("span").attr("id")
            $(this).siblings().children().removeClass("navMenu_hover");
            $("#sideBarMenu li").addClass('sideBar_hover').siblings().removeClass('sideBar_hover');
            $(this).children().addClass("navMenu_hover");

            var jsonParam = {"parentId": parentId};

            //设置隐藏
            $(".subNav1 ul").hide();

            //设置
            if (parentId) {
                var idArry = [];
                var idNumber = parentId;
                $("#subNav_" + idNumber).show();

                //点击一级菜单默认显示第一个子菜单页面
                $("#subNav_" + idNumber).children("li:first").children("a").click();
                

                //子菜单滚动
                /*var ul_width = 0,
                    click_num = 0,
                    move_width = 0,
                    currentUl_left = 0,
                    max_width = 1110,
                    subNavLi = [],
                    currentUl = $("#subNav_" + idNumber),
                    currentUl_length = currentUl.children("li").length;*/

            }

            common.findPerontIdByPermission(jsonParam, function (resp) {
                //加载中间菜单
                $('#subNav').html(_.template(subNavTpl, resp));
                
                /*子菜单滚动 add by kuangrb 2016-04-27*/
    			comm.subMenu_scroll($("#subNav").find('ul'));
    			
    			 /*子菜单滚动 add by kuangrb 2016-04-27*/
    		 	$('#subNav').resize(function(){
    		 		comm.subMenu_scroll($("#subNav").find('ul'));
    		 	});
    		 	/*end*/
    			
                $("#subNav li").release().click(function (key) {
                    $(this).parent().children().children().removeClass("s_hover");
                    $(this).children().addClass("s_hover");
                    var childrenId = $(this).children().attr("id");
                    var strMenuUrl = $(this).children().attr("menuUrl");
                    var lstMenuUrl;
                    var arr = [];
                    if (strMenuUrl&&$.trim(strMenuUrl).length>0) {
                        lstMenuUrl = strMenuUrl.split(",");
                        for (var i = 0; i < lstMenuUrl.length; i++) {
                            arr[i] = lstMenuUrl[i];
                        }
                    }
                    require(arr, function (src) {
                        src.showPage();
                    });
                })
            })
        });

    });
    /*	$("#navUl li").click(function(e){
     currentClick = $(e.currentTarget).index();
     $(this).siblings().children().removeClass("navMenu_hover");
     $(this).siblings().children().children("span").css("color","white");

     20150211 清除侧边栏菜单选中状态
     $(".sideBar_menu_inner").children("li").removeClass("menu_hover");
     end

     $(this).children().addClass("navMenu_hover");
     $(this).children().children("span").css("color","#3d8028");
     $(".subNav1 ul").hide();
     $("#subNav_" + currentClick).show();
     });


     function liActive( liObj ){
     $('.subNav1 ul a').removeClass('s_hover');
     liObj.addClass('s_hover');
     liObj.parent().siblings('li').find('a').removeClass('s_hover');
     };
     */
    //子菜单事件集合
    var jsonPrams = {};

    //子菜单事件触发方法
    function triggerClick(moduleSrc, fileName) {
        require([moduleSrc], function (src) {
            jsonPrams["fileName"] = src;
            jsonPrams["fileName"].show();
        });
        if (jsonPrams["fileName"]) {
            jsonPrams["fileName"].show();
        }
    };


    //清除文本框全角字符
    $("input[type='text'],textarea").live("change", function () {
        var $textVal = $(this).val();
        //console.log("文本值："+$textVal);
        //首先判断存在全角字符
        if (!comm.isFullWidth($textVal)) {
            $(this).val(comm.toCDB($textVal));
        }
    });
    //文本框去掉前后扣个
    $("input[type='text']").live("change", function () {
        var $textVal = $(this).val();
        $(this).val(comm.trim($textVal));
    });


    //子菜单按钮链接结束


    //首页登录信息显示
    index.loadIndxData();
    return tpl;
});