/*
 *
 * Copyright 2014 Wanhuacheng
 * 1、表单系列化功能
 * 2、combobox功能
 * 3、释放click和ajax请求功能
 * 4、ie8 placeholder兼容
 */
;
(function ($) {
	/*
	 *  表单序列化成json
	 */
	$.fn.serializeJson = function () {
		var serializeObj = {};
		var array = this.serializeArray();
		var str = this.serialize();
		$(array).each(function () {
			if (serializeObj[this.name]) {
				if ($.isArray(serializeObj[this.name])) {
					serializeObj[this.name].push(this.value);
				} else {
					serializeObj[this.name] = [serializeObj[this.name], this.value];
				}
			} else {
				serializeObj[this.name] = this.value;
			}
		});
		return serializeObj;
	};

	/*
	 *  自定combobox
	 *  实现即可输入，也可下拉列表选择
	 *
	 */
	$.widget("custom.combobox", {
		_create : function () {
			this.wrapper = $("<span>")
				.addClass("custom-combobox")
				.insertAfter(this.element);

			this.element.hide();
			this._createAutocomplete();
			this._createShowAllButton();
		},

		_createAutocomplete : function () {
			var selected = this.element.children(":selected"),
			value = selected.val() ? selected.text() : "";

			this.input = $("<input>")
				.appendTo(this.wrapper)
				.val(value)
				.attr("title", "")
				.addClass("custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left")
				.autocomplete({
					delay : 0,
					minLength : 0,
					source : $.proxy(this, "_source")
				})
				.tooltip({
					tooltipClass : "ui-state-highlight"
				});

			this._on(this.input, {
				autocompleteselect : function (event, ui) {
					ui.item.option.selected = true;
					this._trigger("select", event, {
						item : ui.item.option
					});
				},

				autocompletechange : "_removeIfInvalid"
			});
		},

		_createShowAllButton : function () {
			var input = this.input,
			wasOpen = false;

			$("<a>")
			.attr("tabIndex", -1)
			.attr("title", "Show All Items")
			.tooltip()
			.appendTo(this.wrapper)
			.button({
				icons : {
					primary : "ui-icon-triangle-1-s"
				},
				text : false
			})
			.removeClass("ui-corner-all")
			.addClass("custom-combobox-toggle ui-corner-right")
			.mousedown(function () {
				wasOpen = input.autocomplete("widget").is(":visible");
			})
			.click(function () {
				input.focus();

				// 如果已经可见则关闭
				if (wasOpen) {
					return;
				}

				// 传递空字符串作为搜索的值，显示所有的结果
				input.autocomplete("search", "");
			});
		},

		_source : function (request, response) {
			var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
			response(this.element.children("option").map(function () {
					var text = $(this).text();
					if (this.value && (!request.term || matcher.test(text)))
						return {
							label : text,
							value : text,
							option : this
						};
				}));
		},

		_removeIfInvalid : function (event, ui) {

			// 选择一项，不执行其他动作
			if (ui.item) {
				return;
			}

			// 搜索一个匹配（不区分大小写）
			var value = this.input.val(),
			valueLowerCase = value.toLowerCase(),
			valid = false;
			this.element.children("option").each(function () {
				if ($(this).text().toLowerCase() === valueLowerCase) {
					this.selected = valid = true;
					return false;
				}
			});

			// 找到一个匹配，不执行其他动作
			if (valid) {
				return;
			}

			// 移除无效的值
			this.input
			.val("")
			.attr("title", value + " didn't match any item")
			.tooltip("open");
			this.element.val("");
			this._delay(function () {
				this.input.tooltip("close").attr("title", "");
			}, 2500);
			this.input.data("ui-autocomplete").term = "";
		},

		_destroy : function () {
			this.wrapper.remove();
			this.element.show();
		}
	});

	/*
	 * 解绑所有事件（若有）
	 * 取消正在请求的接口
	 */
	$.fn.release = function (events, xhrKeys) {
		//解绑已绑定的事件
		if (events) {
			$(this).unbind(events);
		} else {
			$(this).unbind();
		}
		//取消已请求的接口
		if (xhrKeys) {
			comm.killRequest(xhrKeys);
		} else {
			comm.killRequest();
		}
		return $(this);
	}

	/*
	 * 中文日期默认格式(2015/6/5)
	 * 开发者直接$( "#dateID" ).datepicker();
	 *
	 */
	$.datepicker.regional['zh-CN'] = {
		clearText : '清除',
		clearStatus : '清除已选日期',
		closeText : '关闭',
		closeStatus : '不改变当前选择',
		prevText : '<上月',
		prevStatus : '显示上月',
		prevBigText : '<<',
		prevBigStatus : '显示上一年',
		nextText : '下月>',
		nextStatus : '显示下月',
		nextBigText : '>>',
		nextBigStatus : '显示下一年',
		currentText : '今天',
		currentStatus : '显示本月',
		monthNames : ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
		monthNamesShort : ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
		monthStatus : '选择月份',
		yearStatus : '选择年份',
		weekHeader : '周',
		weekStatus : '年内周次',
		dayNames : ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
		dayNamesShort : ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
		dayNamesMin : ['日', '一', '二', '三', '四', '五', '六'],
		dayStatus : '设置 DD 为一周起始',
		dateStatus : '选择 m月 d日, DD',
		dateFormat : 'yy-mm-dd',
		changeMonth : true,
		changeYear : true,
		firstDay : 1,
		initStatus : '请选择日期',
		isRTL : false
	};
	$.datepicker.setDefaults($.datepicker.regional['zh-CN']);

	/*
	 * 判断是否是IE及版
	 *
	 */
	$.extend({
		isIE : function () {
			if (window.navigator.userAgent.indexOf("MSIE") >= 1) {
				return true;
			}
			return false;
		},
		lessIE : function (version) {
			if (this.isIE()) {
				return $.browser.version < version;
			} else {
				return false;
			}
		}
	});

	/**
	 * input placeHolder for IE8
	 * left:左边距离参数
	 * top :
	 */
	$.fn.placeholder = function (options) {
		if (!this.attr('placeholder'))
			return;
		var self = this;
		if ($.isIE() && $.lessIE(10.0)) {
			var div = $('<div style="position:relative;display:inline-block;"></div>');
			this.wrap(div);

			var left = options && options.left || 0;
			var top = options && options.top || 2;
			//create  placeholder span
			var span = $('<span style="float: left;  margin-left: -' + (this.width() - left) + 'px;margin-top: ' + top + 'px;  z-index: 121;  color: gray;  position:absolute;">' + this.attr('placeholder') + '</span>');
			span.click(function (e) {
				$(self).focus();
			});

			this.after(span);
			this.off('keyup keydown').live({
				keyup : function (e) {
					e.preventDefault();
					if (self.val() && self.val().length > 0) {
						self.next('span').hide();
					} else {
						self.next('span').show();
					}
					return false;
				}

			}).keydown(function (e) {
				self.next('span').hide();
			});
		}

	}

	/*
	 * select plugin （兼容ie8或以上）
	 * 初始化及装载数据
	 *
	 */
	$.fn.selectList = function (settings) {
		var settings = settings || {};
		var width = settings.width || 100, //选择框宽度
		height = (!settings.height || settings.height < 24) ? 30 : settings.height,
		optionWidth = settings.optionWidth || 120, //列表宽度
		optionHeight = settings.optionHeight, //列表高度
		overflowX = settings.overflowX || 'hidden',
		overflowY = settings.overflowY || 'auto',
		fontSize = settings.fontSize || 12,
		borderWidth = settings.borderWidth || 0,
		borderColor = settings.borderColor || '#66cc66',
		autoList = settings.autoList || false,
		//ajax 请求数据（因后端数据结构不一，待确认）
		ajaxUrl = settings.ajaxUrl, //请求url地址
		ajaxData = settings.ajaxData || {}, //提交参数
		optionName = settings.optionName || 'name', //name 对应的数据字段名
		optionValue = settings.optionValue || 'value', //value对应的数据字段名
		writable = (settings.writable == true) ? false : 'readonly',
		olList = null,
		options = [];

		var self = this;

		if (settings.options && $.isArray(settings.options) && settings.options.length > 0) {
			options = settings.options;
		} else if ($(this).attr('options') && $.isArray(eval('(' + $(this).attr('options') + ')'))) {
			options = eval('(' + $(this).attr('options') + ')');
		}
		if (optionValue == 'value') {
			//var v = $(this).attr('optionValue') ;
			optionValue = $(this).attr('optionValue');
		}

		/* 待后端数据结构
		if (ajaxUrl &&  $.isFunction(ajaxCall)) {
		comm.Request(ajaxUrl,{
		data:ajaxData ||{},
		success : function(data){
		}
		})
		}
		 */

		function createOptions(olList, options) {
			var ol = $(self).next('ol[data-tag="ol-input-select"]');
			$('ol[data-tag="ol-input-select"]').live('mouseleave', function () {
				$(this).addClass('none');
			});
			ol.empty();

			for (var i = 0; i < options.length; i++) {
				var li = $('<li data-value="' + options[i].value + '">' + options[i].name + '</li>').appendTo(ol);
				li.addClass('').css({
					'font-size' : fontSize + 'px'
				}).off();
				if (options[i].selected || optionValue == options[i].value) {
					//选中默认值
					if (!ol.find('li').hasClass('selectList-active')) {
						self.val(options[i].name).attr('optionValue', options[i].value);
						li.addClass('selectList-active').attr('selected', 'true');
					}

					//ie 8 问题
					if ($('span[data-tag="placeholder"]')) {
						$('span[data-tag="placeholder"]').addClass('none');
					}

				}
			}
		}

		function createOl(options) {
			if (!$(self).next('ol')[0] && options.length) {
				olList = $('<ol data-tag="ol-input-select" class="selectList-ol none"></ol>').insertAfter(self).css({
						'width' : optionWidth + 'px',
						'height' : optionHeight + 'px',
						'overflow-x' : overflowX,
						'overflow-y' : overflowY,
						'z-index' : 1000000
					});
				if (autoList) {
					olList.removeClass('none');
				} else {
					olList.addClass('none');
				}
			} else {
				olList = $(self).next('ol');
			}
		}

		function placeholder(settings) {
			if (self.attr('placeholder') && $.isIE() && $.lessIE(10.0)) {
				var left = options.placeholderLeft || 5;
				var top = options.placeholderTop ||  - (self.height() / 2 + fontSize / 2);
				//create  placeholder span
				var span = $('<span data-tag="placeholder" style=" margin-left:  ' + left + 'px;margin-top: ' + top + 'px;  z-index: 221;  color: gray; font-size:' + fontSize + 'px ; position:absolute;">' + self.attr('placeholder') + '</span>');
				span.click(function (e) {
					if (self.attr('readonly')) {
						self.trigger('click');
					} else {
						self.focus();
					}
				});

				self.after(span);
				self.off('keyup keydown').live({
					keyup : function (e) {
						e.preventDefault();
						if (self.val() && self.val().length > 0) {
							self.nextAll('span[data-tag="placeholder"]').hide();
						} else {
							self.nextAll('span[data-tag="placeholder"]').show();
						}
						return false;
					}

				}).keydown(function (e) {
					self.nextAll('span[data-tag="placeholder"]').hide();
				});

			}
		}

		//$(this).val('').attr('optionValue','').nextAll().remove();
		var selectDiv = this.parent('div[data-tag="div-input-select"]')[0];
		if (selectDiv) {

			createOl(options);
			createOptions(olList, options);

			//return this;
		} else {
			self.addClass('selectList-input').css({
				//'position': 'relative' ,
				'width' : width + 'px',
				'height' : height + 'px',
				//'display': 'block',
				'font-size' : fontSize + 'px',
				//'border': 'solid 1px #6C6',
				'line-height' : height + 'px',
				'border' : 'solid ' + borderWidth + 'px ' + borderColor
			}).attr('readonly', writable);

			$(this).wrap('<div data-tag="div-input-select" style="width:' + (width + (5 - (1 - borderWidth))) + 'px;display:inline-block;"></div');

			$(this).parent('div').on('mouseleave', function () {
				if (!$(self).next('ol[data-tag="ol-input-select"]').hasClass('none')) {
					$(self).next('ol[data-tag="ol-input-select"]').addClass('none');
				};
			});

			var spanIcon = $('<span data-tag="span-icon-select" tabindex="0" hidefocus="true" ></span>');
			spanIcon.addClass('selectList-span').css({
				'margin-top' : '-' + (25 + (height - (25 - borderWidth)) / 2) + 'px'
			});
			//spanIcon.insertAfter(this).css('margin-left', (thisWidth*1+10)+'px').click($.proxy(function(e){
			spanIcon.insertAfter(this).click($.proxy(function (e) {
					!$(this).attr('disabled') && $(this).trigger('click');
				}, this)).mouseover(function (e) {
				$(this).addClass('selectList-span-active');
			}).mouseout(function (e) {
				$(this).removeClass('selectList-span-active');
			});/*.on('blur',function(e){
			if ( !olList.hasClass('none')  ) { return; } ;
			options.length && setTimeout(function(){olList.addClass('none');}, 50 ) ;
			});*/

			placeholder();
			createOl(options);
			createOptions(olList, options);
		}

		this.on('blur', function (e) {
			options.length && setTimeout(function () {
				olList.addClass('none');
			}, 200);
			//屏蔽校验时的提示
			$(this).tooltip();
			$(this).tooltip("destroy");
		}).click(function (e) {
			if ($(this).attr('disabled')) {
				return;
			};
			if (options.length && olList[0]) {
				if (olList.hasClass('none')) {
					olList.removeClass('none');
				}
				/*else {
				olList.addClass('none');
				} */
				//屏蔽校验时的提示
				$(this).tooltip();
				$(this).tooltip("destroy");
			}
		});

		options.length && olList.off().on('mouseleave', function (e) {
			//$(this).addClass('none');
		}).on('mouseover', 'li', function (e) {
			$(this).addClass('selectList-active');
		}).on('mouseout', 'li', function (e) {
			if (!$(this).attr('selected')) {
				$(this).removeClass('selectList-active');
			}
		}).on('click', 'li', function (e) {
			e.preventDefault();
			e.stopPropagation();
			olList.find('li').each(function (key, li) {
				//清除选中状态
				$(li).removeClass('selectList-active').removeAttr('selected');
			});
			//设置新的选中状态
			$(this).addClass('selectList-active').attr('selected', 'true');

			olList.prev('input').val($(this).text());

			//隐藏placeholder
			if (self.attr('placeholder') && $.isIE() && $.lessIE(10.0)) {
				self.nextAll('span[data-tag="placeholder"]').hide();
			}
			olList.prev('input').attr('optionValue', $(this).data('value'));

			olList.addClass('none');

			self.trigger('change');
		});

		return this;
	}

	/*
	 * select plugin （兼容ie8或以上）
	 * disabled和enabled 方法
	 *
	 */
	$.fn.selectEnabled = function (disabledFlag) {

		var _self = this,
		borderWidth = _self.css('border-width').replace('px', '');
		if (disabledFlag) {
			_self.css({
				'color' : '#000',
				'border' : 'solid ' + borderWidth + 'px #6C6'
			}).
			removeAttr('disabled').
			nextAll('span[data-tag="span-icon-select"]').removeClass('selectList-span-disabled');
			return this;
		}
		_self.css({
			'color' : '#a1a1a1',
			'border' : 'solid ' + borderWidth + 'px #a1a1a1'
		}).
		attr('disabled', 'disabled').
		nextAll('span[data-tag="span-icon-select"]').addClass('selectList-span-disabled')
		return this;
	};

	/*
	 *   两个元素事件，串行触发某事件
	 *   如click等
	 */
	$.fn.triggerWith = function (domObj, event) {	
		
		if ($(domObj) && $(domObj).length) {
			event && this.off(event);
			if (!event) {
				this.click(function (e) {	
					e.stopPropagation();									
					$(domObj).click();					 
				})
			} else {
				this.bind(event, function (e) {	
					e.stopPropagation();					
					$(domObj).trigger(event);
				})
			}
		}
	};
	
	/*
	 *  初始化四级菜单标签
	 *  必须经初始化才能响应点击事件和切换
	 */
	$.fn.initTab = function(){
		this.children('li').click(function(e){	
			//绑定标签li之间的切换行换事件
		 	if ($(this).hasClass('menu-tab-line')){return;};		 	
		 	var tabid =  $(this).data('tabid'),
		 		 content =$('#main-content >div[data-contentid="'+tabid+'"]') ;
		 	
		 	if ( $(this).hasClass('none')){
				$(this).prev().removeClass('none');
				$(this).removeClass('none');
			}
			$(this).addClass('menu-tab-hover');
			$(this).siblings().removeClass('menu-tab-hover');			
			
			if (!tabid){
				comm.alert({content:'标签未设置data-tabid'});
				return;
			}
			
			if ( !content ||  !content.length ){
				comm.alert({content:'未找到data-contentid的容器'});
				return;
			}
		 	//切换内容	 
		 	$('#main-content >div[data-contentid="'+tabid+'"]').siblings().addClass('none');
		 	$('#main-content >div[data-contentid="'+tabid+'"]').removeClass('none');
	 		 
		 });
		
		this.find('li > i').click(function(e){
			//标签关闭按钮事件			
			e.stopPropagation();				
			var tabid= $(this).parent().data('tabid'),
				  pid= $(this).parent().data('pid');
			
			if (!tabid){
				comm.alert({content:'标签未设置data-tabid'});
				return;
			}	  
			if ( !pid ){
				comm.alert({content:'此子标签未设置父容器data-pid'});
				return;
			}	  
			
			//关闭其它标签的子标签（若存在）
			
			
			//隐藏关闭按键li和上一个相邻的分隔线li	  
			$(this).parent('li').prev('li').addClass('none');
			$(this).parent('li').addClass('none');		
			//隐藏标签对应的容器	 
			$('#main-content >div[data-contentid="'+tabid+'"]').addClass('none') ;
			
			//切换并显示父标签
			$(this).parents().find('li[data-tabid="'+pid+'"]').trigger('click');
			
		});
			 
	};
	
	
	/*
	 *   通过某一按钮，触发切换到某tab
	 *   
	 */
	$.fn.triggerTab = function (tabid,callback) {
			if (!tabid){
				comm.alert({content:'标签未设置tabid'});
				return;
			}
			this.click(function (e) {	
				e.stopPropagation();	
				var newTab = $('#menu-tab > ul > li[data-tabid="'+tabid+'"]');					 
				if (!newTab || !newTab.length){
					comm.alert({content:'未找到tabid="'+tabid+'"的子标签'});
					return;
				}
				$('#menu-tab > ul > li[data-tabid="'+tabid+'"]').trigger('click');		
				if ( typeof callback =='function' ){
					callback();	
				} 
			})
	};
	 
	
	/*
	 *  四级菜单标签关闭，并切换至新的标签
	 *  若不指定目标标签，并只关闭原标签
	 */
	$.fn.closeTab = function () {	
		var self = this ;	
		if ($(domObj) && $(domObj).length) {
			event && this.off(event);
			if (!event) {
				this.click(function (e) {	
					e.stopPropagation();	
					$(self).parent('li').prev('li').addClass('none');
					$(self).parent('li').addClass('none');		
					$(domObj).click();					 
				})
			} else {
				this.bind(event, function (e) {	
					e.stopPropagation();			
					$(self).parent('li').prev('li').addClass('none');
					$(self).parent('li').addClass('none');				
					$(domObj).trigger(event);
				})
			}		 
		}
	}
	

	/*
	 *  ie8 bind 上下文兼容
	 *
	 */
	if (!Function.prototype.bind) {
		Function.prototype.bind = function (obj) {
			var self = this;
			return function () {
				return self.apply(obj);
			}
		};
	}

})(jQuery);


/*给div 加resize 方法*/
(function($,h,c){var a=$([]),e=$.resize=$.extend($.resize,{}),i,k="setTimeout",j="resize",d=j+"-special-event",b="delay",f="throttleWindow";e[b]=250;e[f]=true;$.event.special[j]={setup:function(){if(!e[f]&&this[k]){return false}var l=$(this);a=a.add(l);$.data(this,d,{w:l.width(),h:l.height()});if(a.length===1){g()}},teardown:function(){if(!e[f]&&this[k]){return false}var l=$(this);a=a.not(l);l.removeData(d);if(!a.length){clearTimeout(i)}},add:function(l){if(!e[f]&&this[k]){return false}var n;function m(s,o,p){var q=$(this),r=$.data(this,d);r.w=o!==c?o:q.width();r.h=p!==c?p:q.height();n.apply(this,arguments)}if($.isFunction(l)){n=l;return m}else{n=l.handler;l.handler=m}}};function g(){i=h[k](function(){a.each(function(){var n=$(this),m=n.width(),l=n.height(),o=$.data(this,d);if(m!==o.w||l!==o.h){n.trigger(j,[o.w=m,o.h=l])}});g()},e[b])}})(jQuery,this);
