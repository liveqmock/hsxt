define(["text!commTpl/frame/footer.html"], function (tpl) {
	//加载页脚
	$("#footerContent").html(_.template(tpl));
	
	//获取窗口宽高
	var ww = $(window).width(),
	wh = $(window).height(),
	msgs_box = {
		//用于窗体拖动
		x : 0, 
		y : 0,
		drag : false
	},
	news_box = {
		//用于窗体拖动
		x : 0, 
		y : 0,
		drag : false,
		//用于数据分页
		page : {
			//数据
			data : null,
			//总页数
			count : 0,
			//分页条数
			size : 4,
			//页数索引
			current : 0,
			//初始化
			init : function(d){
				var t = this;
				t.data = d;
				t.current = 0;
				t.count = parseInt(d.id.length / t.size);
				t.show();
			},
			//加载数据列表及控制分页按钮
			show : function(){
				var t = this;
				if (t.current == 0) {
					$("#hsmsg_home_first").addClass("pageFirst2 pageDisabled");
					$("#hsmsg_home_prev").addClass("pagePrev2 pageDisabled");
				} else {
					$("#hsmsg_home_first").removeClass("pageFirst2 pageDisabled");
					$("#hsmsg_home_prev").removeClass("pagePrev2 pageDisabled");
				}
				if (t.current == t.count) {
					$("#hsmsg_home_next").addClass("pageNext2 pageDisabled");
					$("#hsmsg_home_last").addClass("pageLast2 pageDisabled");
				} else {
					$("#hsmsg_home_next").removeClass("pageNext2 pageDisabled");
					$("#hsmsg_home_last").removeClass("pageLast2 pageDisabled");
				}

				var aHtml = [];
				for (var i = t.current * t.size; i < t.current * t.size + t.size; i++) {
					if (t.data.id[i]) {
						aHtml.push('<li><a href="javascript:;" data-id="' + t.data.id[i] + '">' + t.data.title[i] + '</a></li>');
					}
				}
				$("#HsNewList ul").html(aHtml.join(''));
				
				//新闻列表单击事件
				$("#HsNewList ul li a").unbind('click').bind('click', function(){
					var o = $(this);
					o.addClass("now_news").parent().siblings("li").children("a").removeClass("now_news");
					$("#HsNew_title").text(o.text());
				});
			}
		}
	};

	//数据 分页
	var newlist1 = {
		id : Array("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"),
		title : Array("1新闻标题", "2新闻标题", "3新闻标题", "4新闻标题", "5新闻标题", "6新闻标题", "7新闻标题", "8新闻标题", "9新闻标题", "10新闻标题", "11新闻标题", "12新闻标题", "13新闻标题", "14新闻标题", "15新闻标题"),
		content : Array("1内容", "2内容", "3内容", "4内容", "5内容", "6内容", "7内容", "8内容", "9内容", "10内容", "11内容", "12内容", "13内容", "14内容", "15内容")
	};
	var newlist2 = {
		id : Array("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"),
		title : Array("1互生消息", "2互生消息", "3互生消息", "4互生消息", "5互生消息", "6互生消息", "7互生消息", "8互生消息", "9互生消息", "10互生消息", "11互生消息", "12互生消息", "13互生消息", "14互生消息", "15互生消息"),
		content : Array("1内容", "2内容", "3内容", "4内容", "5内容", "6内容", "7内容", "8内容", "9内容", "10内容", "11内容", "12内容", "13内容", "14内容", "15内容")
	};
	
	//新闻列表数据处理
	news_box.page.init(newlist1);
	
	
	//首页右下角图标单击事件
	//聊天图标，弹出聊天窗体
	$("#person_show_btn").click(function(){
		if ($(".speak_person_list_box").is(":hidden")) {
			$(".speak_person_list_box").show();
			$("#speak_person_hd").show();
			$("#speak_person_hd").animate({
				"bottom" : 0,
				"left" : 0
			}, 400);
		} else {
			$("#speak_person_hd").animate({
				"bottom" : -302 + "px",
				"left" : 0
			}, 400, function () {
				$(".speak_person_list_box").hide();
			});
		}
	});
	//返回顶部
	$("#gototop_btn").click(function(){
		$("html,body").animate({
			scrollTop : "0px"
		}, 400);
	});
	//查看新闻，弹出新闻窗体
	$("#hsmsg_home_shownews").click(function(){
		var lt = ww * 0.5 - 490; //326为聊天框宽度的一半
		var tp = wh * 0.5 - 215; //210为聊天框高度的一半
		if (tp < 0) {
			tp = 0
		}
		$("#hsmsg_home_newslist").show().css({
			"left" : lt + "px",
			"top" : tp + "px"
		}).trigger('click');
		$("#HsNewsType").text($("#HsNewType_ul li a:eq(0)").text() + "-");
		$("#HsNew_title").text($("#HsNewList ul li:eq(0) a:eq(0)").text());
	});
	//隐藏关闭聊天窗体
	$("#close_personlist_btn").click(function () {
		$("#speak_person_hd").animate({
			"bottom" : -302 + "px",
			"left" : 0
		}, 400, function () {
			$(".speak_person_list_box").hide();
		});
	});
	
	//窗体拖动
	document.onmousemove = function (event) {
		event = event || window.event;
		if (news_box.drag) {
			var xx = event.clientX - news_box.x;
			var yy = event.clientY - news_box.y;
			if (xx < -960) {
				xx = -960;
			}
			if (xx > ww - 30) {
				xx = ww - 30;
			}
			if (yy < 0) {
				yy = 0;
			}
			if (yy > wh - 30) {
				yy = wh - 30;
			}
			$("#hsmsg_home_newslist").css({
				"left" : xx + "px",
				"top" : yy + "px"
			});
			return false;
		}
		if (msgs_box.drag) {
			var xx = event.clientX - msgs_box.x;
			var yy = event.clientY - msgs_box.y;
			if (xx < -562) {
				xx = -562;
			}
			if (xx > ww - 30) {
				xx = ww - 30;
			}
			if (yy < 0) {
				yy = 0;
			}
			if (yy > wh - 30) {
				yy = wh - 30;
			}
			$(".tool_speak_box").css({
				"left" : xx + "px",
				"top" : yy + "px"
			});
			return false;
		}
	};
	$(document).mouseup(function (event) {
		news_box.drag = false;
		msgs_box.drag = false;
		try {
			$(".tool_speak_box").get(0).releaseCapture();
			$("#hsmsg_home_newslist").get(0).releaseCapture();
		} catch(e){}
		event.cancelBubble = true;
	});
	/*---新闻窗体相关按钮事件----*/
	//拖动窗体
	$("#HsNew_title_top").mousedown(function (event) {
		news_box.drag = true;
		event = event || window.event;
		var pos = $("#hsmsg_home_newslist").position();
		news_box.x = event.clientX - pos.left;
		news_box.y = event.clientY - pos.top;
		return false;
	});
	//关闭新闻窗体
	$("#HsNewlist_box_close").click(function(){
		$("#hsmsg_home_newslist").hide();
	});
	//新闻窗体全屏
	$("#HsNewlist_quanbing_btn").click(function(){
		$("#hsmsg_home_newslist").css({
			"width" : ww - 2,
			"height" : wh,
			"top" : 0,
			"left" : 0
		});
		$(".hs_new_content").width(ww - 4 - 141 - 230);
		$(".hs_new_type").height(wh - 32);
		$(".hs_new_list").height(wh - 32);
		$(".hs_new_content_box").height(wh - 32);
	});
	//新闻窗体恢复原状
	$("#HsNewlist_fangxiao_btn").click(function(){
		var lt = ww * 0.5 - 490; //326为聊天框宽度的一半
		var tp = wh * 0.5 - 215; //210为聊天框高度的一半
		if (tp < 0) {
			tp = 0
		}
		$("#hsmsg_home_newslist").css({
			"width" : 980,
			"height" : 432,
			"top" : lt,
			"left" : tp
		});
		$(".hs_new_content").width(608);
		$(".hs_new_type").height(400);
		$(".hs_new_list").height(400);
		$(".hs_new_content_box").height(380);
	});
	//新闻窗体置顶显示
	$("#hsmsg_home_newslist").click(function () {
		$(this).css({
			"z-index" : 99
		});
		$(".tool_speak_box").css({
			"z-index" : 98
		});
	});
	//切换消息类型 - 业务消息、互生消息
	$("#HsNewType_ul li a").click(function () {
		var o = $(this),
		text = o.text(),
		index = o.parent("li").index();
		news_box.page.init(index == 0 ? newlist1 : newlist2);
		
		o.addClass("select_a").parent().siblings("li").children("a").removeClass("select_a");
		var num = $(this).parent("li").index();
		$("#HsNewList .type_title").text(text + "列表");
		$("#HsNewsType").text(text + "-");
		$("#HsNew_title").text($("#HsNewList ul li:eq(0) a:eq(0)").text());
	});
	//首页
	$("#hsmsg_home_first").click(function(){
		if($(this).hasClass('pageDisabled')) return;
		news_box.page.current = 0;
		news_box.page.show();
	});
	//上一页
	$("#hsmsg_home_prev").click(function(){
		if($(this).hasClass('pageDisabled')) return;
		news_box.page.current--;
		if (news_box.page.current < 0) {
			news_box.page.current = 0;
		}
		news_box.page.show();
	});
	//下一页
	$("#hsmsg_home_next").click(function(){
		if($(this).hasClass('pageDisabled')) return;
		news_box.page.current++;
		if (news_box.page.current > news_box.page.count) {
			news_box.page.current = news_box.page.count;
		}
		news_box.page.show();
	});
	//尾页
	$("#hsmsg_home_last").click(function(){
		if($(this).hasClass('pageDisabled')) return;
		news_box.page.current = news_box.page.count;
		news_box.page.show();
	});
	/*---新闻窗体相关按钮事件----*/
	
	/*---聊天窗体相关按钮事件----*/
	//好友列表单击事件，弹出聊天窗体
	$(".speak_personlist ul li").click(function () {
		var person = $(this).find("span").text();
		$("#person_name_id").text(person);
		var lt = ww * 0.5 - 326; //326为聊天框宽度的一半
		var tp = wh * 0.5 - 210; //210为聊天框高度的一半
		if (tp < 0) {
			tp = 0
		}
		$(".tool_speak_box").show().css({
			"left" : lt + "px",
			"top" : tp + "px"
		}).trigger('click');
	});
	//聊天窗体拖动
	$("#personlist_title_top").mousedown(function(e){
		msgs_box.drag = true;
		e = e || window.event;
		var pos = $(".tool_speak_box").position();
		msgs_box.x = e.clientX - pos.left;
		msgs_box.y = e.clientY - pos.top;
		return false;
	});
	//隐藏关闭聊天窗体
	$("#tool_speak_box_close").click(function () {
		$(this).parents(".tool_speak_box").hide();
	});
	//聊天窗体置顶显示
	$(".tool_speak_box").click(function () {
		$(this).css({
			"z-index" : 99
		});
		$("#hsmsg_home_newslist").css({
			"z-index" : 98
		});
	});
	//表情按钮，弹出表格窗体
	$("#biaoqing_more_btn").click(function () {
		$("#biaoqing_box").show();
	});
	//消息输入框单击事件，隐藏表格窗体
	$("#tool_talkinput .textarea").click(function () {
		$("#biaoqing_box").hide();
	});
	//表情图标单击事件
	$("#biaoqing_box i").click(function () {
		var num = $(this).index() + 1;
		var str = $(this).html();
		var textareastr = $("#tool_talkinput .textarea").html();

		$("#tool_talkinput .textarea").html(textareastr + str);
		$("#biaoqing_box").hide();
	});
	//聊天记录
	$("#hsmsg_home_msghistory").click(function () {
		alert("聊天记录");
	});
	//发送消息按钮
	$("#goto_talk_btn").click(function () {
		var imgnum;
		var talktxt = $("#tool_talkinput").find(".textarea").html();
		var myname = $("#person_name_id").text();
		var zz = /<IMG src=\"([^\"]*?)\">/gi;
		var arr = talktxt.match(zz);

		if (arr != null) {
			for (i = 0; i < arr.length; i++) {
				var src = "src=\"";
				var name = ".png";
				var s = arr[i].indexOf(src) + 5;
				var s2 = arr[i].indexOf(name) + 4;
				var fullpath = arr[i].substring(s, s2); //talktxt2.replace(arr[i],"["+i+"]");
				var filename = fullpath.match(/\/(\w+\.(?:png|jpg|gif|bmp))$/i)[1];
				//var test = /\/([^\/]*?\.(jpg|bmp|gif))"\/>/;
				var num = filename.replace(".png", "");
				if (num.toString().length == 1) {
					num = "00" + num;
				} else if (num.toString().length == 2) {
					num = "0" + num;
				}
				var talktxt2 = "[" + num + "]";
				talktxt = talktxt.replace(arr[i], talktxt2);
			}

			alert(talktxt); //图片转成数字数据
		} else {
			alert(talktxt);
		}

		if ($("#tool_talkinput .textarea").html() != "") {
			talktxt = $("#tool_talkinput").find(".textarea").html();
			var talk_li = '<li class="item_li"><p class="p_tit"><span class="talk_name">' + myname + '</span><span class="talk_time">(' + comm.getCurrDate(1) + ')</span></p><p class="p_content">' + talktxt + "</p></li>";

			$(".tool_talkhistory ul").append(talk_li);
			$("#tool_talkinput .textarea").html("");
		}
		//滚动条到最后一行
		$(".tool_talkhistory").scrollTop($(".tool_talkhistory ul").height());
	});
	/*---聊天窗体相关按钮事件----*/
});