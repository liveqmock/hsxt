String.prototype.format=function(a){var t=this;if(arguments.length>0){if(arguments.length==1&&typeof(a)=="object"){for(var key in a){if(a[key]!=undefined){var r=new RegExp("({"+key+"})","g");t=t.replace(r,a[key]);}}}else{for(var i=0;i<arguments.length;i++){if(arguments[i]!=undefined){var r=new RegExp("({["+i+"]})","g");t=t.replace(r,arguments[i]);}}}}return t;}
var OjbMain = {
	Height : 0,
	UserType : "8",
	AutoHeight : function() {
		this.Height = $(document).height() - 55;
		$('#tabs-1').height(this.Height - 35);
	},
	Init: function(){
		//获取XML文件生成树
		$.ajax({
			url : "../js/layout.xml",
			dataType : "xml",
			type : 'GET',
			async : false,
			success : function(xml) {
				var htm = [];
				// 读取一级菜单
				$(xml).find("tree").each(function(i) {
					var obj = $(this);
					var treehtml = Traversal(obj.children(), 1);
					if (treehtml >= 1) {
						htm.push('<h3>{0}</h3><div class="st_tree"><ul>{1}</ul></div>'.format(obj[0].getAttribute("name"), itemList));
						itemList = "";
					}
				});
				$('#accordion').html(htm.join(''));
				$("#accordion").accordion();
				$('#accordion div').css({'height':'auto', 'background':'#fff', 'border':0});
			},
			error : function(xml) {
				alert("加载XML 文件出错！");
			}
		});
		//初始化高度
		this.AutoHeight();
	}
};
// 递归子节点
var itemList = "";
var Traversal = function(nodes, t) {
	var it = 0;
	$.each(nodes, function() {
		var objs = $(this);
		var obj = objs[0];
		var prid = obj.getAttribute("id");
		if (obj.hasChildNodes()) {
			var h = Traversal(objs.children(), 0);
			if (h >= 1) {
				// itemList += '<li data-options="state:\'closed\'"><span>'+
				// obj.getAttribute("name") + '</span>"';
				itemList += '<li>' + obj.getAttribute("name") + '</li>';
				itemList += '<ul>';
				Traversal(objs.children(), 1);
				itemList += '</ul>';
			}
			it = it + 1;
		} else {
			if (OjbMain.UserType == "8" || Permissions.indexOf(prid) >= 0) {
				if (t == 1) {
					itemList += '<li><a rel="{0}">{1}</a></li>'.format(obj.getAttribute("url"), obj.getAttribute("name"));
				}
				it = it + 1;
			}
		}
	});
	return it;
};
// ***********************************通过XML生成菜单
// ---结束---***********************************
// *********************************JQUERY UI
// tabs操作***************************************
var tabs = "";
var tabCounter = 1;

// 添加一个标签页
var obj_Tab = {
	addTab : function(name, url) {
		if (!this.tabExit(name,url)) {
			tabCounter++;
			var titb = '<li><a href="#tabs-{0}">{1}</a><span title="关闭当前窗口" class="ui-icon ui-icon-close">Remove Tab</span></li>'.format(tabCounter, name);
			tabs.find(".ui-tabs-nav").append(titb);
			var tb = '<div id="tabs-{0}"><iframe scrolling="yes" src="{1}" frameborder="0" style="width:100%;height:100%;"></iframe></div>'.format(tabCounter, url);
			tabs.append(tb);
			tabs.tabs("refresh");
			$('#tabs-' + tabCounter).height(OjbMain.Height - 35);
			// tabs.tabs('option', 'active', tabCounter-1);
			// 添tabs后默认选中
			this.RetainTab();
		}
	},
	// 判断标签是否存在
	tabExit : function(name,url) {
		var y = 0;
		tabs.find(".ui-tabs-nav").find('a').each(function() {
		var th=$(this);
			if (name == th.text()) {
				y = 1;
				th.click();
				var panelId =th.closest("li").attr("aria-controls");
				var frame = $("#" + panelId).find("IFRAME");
				frame.attr('src', url);
				return false;
			}
		});
		if (y == 1)
			return true;
		return false;
	},// 保留几个tab
	RetainTab : function() {
		var ths = tabs.find(".ui-tabs-nav").find('a');
		if (ths.size() > 11) {
			ths.each(function(i) {
				if (i == 1) {
					$(this).parent().find('span').click();
					return false;
				}
			});
		}
		ths.each(function(i) {
			if (i == (ths.size() - 1)) {
				$(this).click();
				return false;
			}
		});
	},
	// 点击关闭标签页
	closeTabs : function(obj) {
		var panelId = $(obj).closest("li").attr("aria-controls");
		var frame = $("#" + panelId).find("IFRAME");
		frame.attr('src', '');
		frame.remove();
		$(obj).closest("LI").remove()
		$(obj).remove();
		$("#" + panelId).remove();
		//tabCounter--
		tabs.tabs("refresh");
	}
}
// *********************************JQUERY UI tabs操作
// ---结束---***************************************

$(document).ready(function() {
	// 获取菜单文件xml
	OjbMain.Init();
	
	// 初始化树型菜单
	$(".st_tree").SimpleTree({
		// 点击菜单连接
		click : function(a) {
			var url = $(a).attr("rel");
			var name = $(a).text();
			if (name.indexOf(".") >= 0)
				name = name.substring(name.indexOf(".") + 1);
			name = name.replace(' ', '');
			obj_Tab.addTab(name, url);
		}
	});
	// 加载tabs
	tabs = $("#tabs").tabs();
	// 点击关闭标签页
	tabs.delegate("span.ui-icon-close", "click", function() {
		obj_Tab.closeTabs(this);
		if (CollectGarbage) {
			CollectGarbage(); // IE 特有 释放内存
		}
	});
	//点击切换左侧菜单
	$("#btn-switch").click(function () {
		var o = $(this),
		f = o.hasClass('btn-switch2');
		o.toggleClass('btn-switch2').attr('title', (f ? '隐藏' : '显示') + '左侧菜单');
		$('.main-left').css({'display': f ? 'block' : 'none'});
		$('.main-split').css({'left': f ? 200 : 0});
		$('.main-right').css({'left': f ? 215 : 15});
	});
});
$(window).resize(function(){
	OjbMain.AutoHeight();
});