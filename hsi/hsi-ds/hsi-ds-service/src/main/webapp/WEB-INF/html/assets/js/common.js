// 初始入口
(function () {
    window.VISITOR = {};
})();

//
// 头部显示初始化
//
function headShowInit() {
    if (VISITOR.id) {
        $(".login-no").hide();
        $(".login-yes").show();
        $("#username").show();
        $("#username").html(VISITOR.name);
    } else {
        $(".login-no").show();
        $(".login-yes").hide();
        $("#username").hide();
    }
}

//
// 登录其它的控制
//
function loginActions() {
    if (VISITOR.id) {
        $("#brand_url").attr("href", "main.html");
    } else {
        $("#brand_url").attr("href", "login.html");
    }
}

function getContextPath() {
	var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var result = pathName.substr(0, index + 1);

	return result;
}

//
// 获取Session信息
//
function getSession() {
    $.ajax({
        type: "GET",
        url: "api/account/session",
        timeout: 3000 // 3s timeout
    }).done(function (data) {
        if (data.success === "true") {
            window.VISITOR = data.result.visitor;
            headShowInit();
        } else {
            window.location.href = "login.html";
        }
    }).fail(function (xmlHttpRequest, textStatus) {
        window.location.href = "login.html";
    });
}

// 获取是否登录并且进行跳转
function getSession2Redirect() {
    $.ajax({
        type: "GET",
        url: "api/account/session"
    }).done(function (data) {
        if (data.success === "true") {
            window.location.href = "main.html";
        } else {
        	window.location.href = "login.html";
        }
    });
    
    loginActions();
}

(function ($) {	
	// 定向到首页
    $("#toHome").on("click", function (e) {
    	window.location.href = "main.html";
    });
})(jQuery);

//居中  
function center(divId) {
	var obj = $(divId);
	
	//当前浏览器窗口的宽高
	var screenWidth = $(window).width();
	var screenHeight = $(window).height();
	
	//获取当前窗口距离页面顶部高度
	var scrolltop = $(document).scrollTop();
	var scrollleft = $(document).scrollLeft();	
	var objLeft = (screenWidth - obj.width()) / 2;
	var objTop = (screenHeight - obj.height()) / 2 + scrolltop;
	
	obj.css({
		left : objLeft + 'px',
		top : objTop + 'px'
	});

	//浏览器窗口大小改变时  
	$(window).resize(function() {
		screenWidth = $(window).width();
		screenHeight = $(window).height();
		scrolltop = $(document).scrollTop();
		scrollleft = $(document).scrollLeft();
		objLeft = (screenWidth - obj.width()) / 2 + scrollleft;
		objTop = (screenHeight - obj.height()) / 2 + scrolltop;
		obj.css({
			left : objLeft + 'px',
			top : objTop + 'px'
		});
	});

	//浏览器有滚动条时的操作
	$(window).scroll(function() {
		screenWidth = $(window).width();
		screenHeight = $(window).height();
		scrolltop = $(document).scrollTop();
		scrollleft = $(document).scrollLeft();
		
		objLeft = (screenWidth - obj.width()) / 2 + scrollleft;
		objTop = (screenHeight - obj.height()) / 2 + scrolltop;
		obj.css({
			left : objLeft + 'px',
			top : objTop + 'px'
		});
	});  
}

function listNextFireTimes(cronFieldId) { 	
    var cronExpression = $("#"+cronFieldId).val();
    
    $.ajax({
        type: "GET",
        url: "api/trigger/listNextFireTimes",
        data: {
            "cronExpression": cronExpression
        }
    }).done(function (data) {
        if (data.success === "true") {
            var html = '<br/><span style="font-weight:bold;color:blue;">最近10次将要运行的时间：</span>';
            var result = data.result;
            
            $.each(result, function (index, item) {
            	if(10 > index) {
            		html += '<br/> <span style="color:blue;background-color:#66FFCC; padding-left:20px; padding-right:20px;">'+item+'</span>';
            	}
            });

            $("#nextFireTimesDiv").html(html+'<br/>...<br/>');
        } else {
            $("#nextFireTimesDiv").html("");
        }
    });
}

var defaultColor="#ffffff";
var overColor="#ffffff";
var clickColor="yellow";
var chooseRow=null;

function over_color(obj){
    if(obj.style.backgroundColor!=clickColor) {
        obj.style.backgroundColor=overColor;
    }
}

function remove_color(obj){
    if(obj.style.backgroundColor!=clickColor) {
        obj.style.backgroundColor=defaultColor;
    }	
}

function click_color(obj){
    var tb=obj.parentNode;//获得父节点对象
    
    if(null != chooseRow) {
    	chooseRow.style.backgroundColor=defaultColor;
    }
    
    chooseRow=obj;//获得当前行在表格中的序数
    obj.style.backgroundColor=clickColor;
}

