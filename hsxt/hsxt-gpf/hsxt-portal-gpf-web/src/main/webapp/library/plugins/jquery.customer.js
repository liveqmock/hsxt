/*
 *
 * Copyright 2014 Wanhuacheng
 * 1、表单系列化功能
 * 2、combobox功能
 * 3、释放click和ajax请求功能
 * 4、ie8 placeholder兼容
 * 5、加入selectListIndex,value选中功能
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
        _create: function () {
            this.wrapper = $("<span>")
                .addClass("custom-combobox")
                .insertAfter(this.element);

            this.element.hide();
            this._createAutocomplete();
            this._createShowAllButton();
        },

        _createAutocomplete: function () {
            var selected = this.element.children(":selected"),
                value = selected.val() ? selected.text() : "";

            this.input = $("<input>")
                .appendTo(this.wrapper)
                .val(value)
                .attr("title", "")
                .addClass("custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left")
                .autocomplete({
                    delay: 0,
                    minLength: 0,
                    source: $.proxy(this, "_source")
                })
                .tooltip({
                    tooltipClass: "ui-state-highlight"
                });

            this._on(this.input, {
                autocompleteselect: function (event, ui) {
                    ui.item.option.selected = true;
                    this._trigger("select", event, {
                        item: ui.item.option
                    });
                },

                autocompletechange: "_removeIfInvalid"
            });
        },

        _createShowAllButton: function () {
            var input = this.input,
                wasOpen = false;

            $("<a>")
                .attr("tabIndex", -1)
                .attr("title", "Show All Items")
                .tooltip()
                .appendTo(this.wrapper)
                .button({
                    icons: {
                        primary: "ui-icon-triangle-1-s"
                    },
                    text: false
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

        _source: function (request, response) {
            var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
            response(this.element.children("option").map(function () {
                var text = $(this).text();
                if (this.value && (!request.term || matcher.test(text)))
                    return {
                        label: text,
                        value: text,
                        option: this
                    };
            }));
        },

        _removeIfInvalid: function (event, ui) {

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

        _destroy: function () {
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
        clearText: '清除',
        clearStatus: '清除已选日期',
        closeText: '关闭',
        closeStatus: '不改变当前选择',
        prevText: '<上月',
        prevStatus: '显示上月',
        prevBigText: '<<',
        prevBigStatus: '显示上一年',
        nextText: '下月>',
        nextStatus: '显示下月',
        nextBigText: '>>',
        nextBigStatus: '显示下一年',
        currentText: '今天',
        currentStatus: '显示本月',
        monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
        monthNamesShort: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
        monthStatus: '选择月份',
        yearStatus: '选择年份',
        weekHeader: '周',
        weekStatus: '年内周次',
        dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
        dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
        dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
        dayStatus: '设置 DD 为一周起始',
        dateStatus: '选择 m月 d日, DD',
        dateFormat: 'yy-mm-dd',
        changeMonth: true,
        changeYear: true,
        firstDay: 1,
        initStatus: '请选择日期',
        isRTL: false
    };
    $.datepicker.setDefaults($.datepicker.regional['zh-CN']);

    /*
     * 判断是否是IE及版
     *
     */
    $.extend({
        isIE: function () {
            if (window.navigator.userAgent.indexOf("MSIE") >= 1) {
                return true;
            }
            return false;
        },
        lessIE: function (version) {
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
                keyup: function (e) {
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

    };

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
                    'font-size': fontSize + 'px'
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
                    'width': optionWidth + 'px',
                    'height': optionHeight + 'px',
                    'overflow-x': overflowX,
                    'overflow-y': overflowY,
                    'z-index': 1000000
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
                var top = options.placeholderTop || -(self.height() / 2 + fontSize / 2);
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
                    keyup: function (e) {
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
                'width': width + 'px',
                'height': height + 'px',
                //'display': 'block',
                'font-size': fontSize + 'px',
                //'border': 'solid 1px #6C6',
                'line-height': height + 'px',
                'border': 'solid ' + borderWidth + 'px ' + borderColor
            }).attr('readonly', writable);

            $(this).wrap('<div data-tag="div-input-select" style="width:' + (width + (5 - (1 - borderWidth))) + 'px;display:inline-block;"></div');

            $(this).parent('div').on('mouseleave', function () {
                if (!$(self).next('ol[data-tag="ol-input-select"]').hasClass('none')) {
                    $(self).next('ol[data-tag="ol-input-select"]').addClass('none');
                }
                ;
            });

            var spanIcon = $('<span data-tag="span-icon-select" tabindex="0" hidefocus="true" ></span>');
            spanIcon.addClass('selectList-span').css({
                'margin-top': '-' + (25 + (height - (25 - borderWidth)) / 2) + 'px'
            });
            //spanIcon.insertAfter(this).css('margin-left', (thisWidth*1+10)+'px').click($.proxy(function(e){
            spanIcon.insertAfter(this).click($.proxy(function (e) {
                !$(this).attr('disabled') && $(this).trigger('click');
            }, this)).mouseover(function (e) {
                $(this).addClass('selectList-span-active');
            }).mouseout(function (e) {
                $(this).removeClass('selectList-span-active');
            });
            /*.on('blur',function(e){
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
            }
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
    };

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
                'color': '#000',
                'border': 'solid ' + borderWidth + 'px #6C6'
            }).removeAttr('disabled').nextAll('span[data-tag="span-icon-select"]').removeClass('selectList-span-disabled');
            return this;
        }
        _self.css({
            'color': '#a1a1a1',
            'border': 'solid ' + borderWidth + 'px #a1a1a1'
        }).attr('disabled', 'disabled').nextAll('span[data-tag="span-icon-select"]').addClass('selectList-span-disabled')
        return this;
    };

    //设置默认选项
    $.fn.selectListIndex = function (index) {
        if (!/^\d+$/.test(index || 0)) return;
        var liObj = this.next('ol').find('li').eq(index || 0);
        liObj.attr('selected', 'selected');
        this.next('ol').find('li').removeAttr('selected');
        this.next('ol').find('li').removeClass('selectList-active');
        liObj.addClass('selectList-active');
        this.val(liObj.text());
        this.attr('optionValue', liObj.data('value'));//添加获取optionValue的值
    };
    $.fn.selectListValue = function (value) {
        if (!value) {
            return;
        }
        var liObj = this.next('ol').find('li'),
            self = this;
        self.val('');
        self.attr('optionValue', '');
        liObj.siblings().removeAttr('selected');
        liObj.siblings().removeClass('selectList-active');
        liObj.each(function (key, li) {
            if ($(li).data('value') == value) {
                $(li).addClass('selectList-active');
                self.val($(li).text());
                self.attr('optionValue', value);//添加获取optionValue的值
                return;
            }
        });
    };
    /*
     *   多个元素事件，串行触发
     */

    $.fn.triggerWith = function (domObj, event) {
        if ($(domObj) && $(domObj).length) {
            if (!event) {
                this.click(function () {
                    $(domObj).click();
                })
            } else {
                this.bind(event, function () {
                    $(domObj).trigger(event);
                })
            }
        }

    };

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


    /*
     *  初始化四级菜单标签
     *  必须经初始化才能响应点击事件和切换
     */
    $.fn.initTab = function () {
        //重设右边标签UL的宽度（li宽度的累加）

        var ulWidth = 100,
            self = this;
        this.find('li:visible').each(function (key, liObj) {
            ulWidth += $(liObj).width() + 20;
        });
        this.find('ul').css('width', ulWidth);


        //li绑定点击事件
        this.find('li').live('click', function (e) {
            var tabid = $(this).data('tabid'),
                theLi = $(this);
            var theContent = $(self).find('div[data-contentid="' + tabid + '"]');
            if (!tabid || !theContent.length) {
                console.log('Found no tabid or contentid');
                return;
            }
            //显示当前li并隐藏相邻的li
            theLi.addClass('menu-tab-hover');
            theLi.siblings().removeClass('menu-tab-hover');
            //显示当前content并隐藏相邻的content
            theContent.removeClass('none');
            theContent.siblings().addClass('none');
        });
        //关闭事件

        this.find('i').on('click', function (e) {
            e.stopPropagation();
            $(self).data('move', '');
            var theParent = $(this).parent(),
                tabid = $(this).parent().data('tabid');
            var theContent = $(self).find('div[data-contentid="' + tabid + '"]');
            if (!tabid || !theContent.length) {
                console.log('Found no tabid or contentid');
                return;
            }
            //隐藏当前
            theParent.addClass('none');
            theContent.addClass('none');

            if (theParent.hasClass('menu-tab-hover')) {
                //激活中tab,需把激活状态转移到上一个tab,如果不存在上一个tab，则转移到下一个tab
                var prevLiObj = theParent.prevAll(':visible'),
                    nextLiObj = theParent.nextAll(':visible'),
                    prevLength = prevLiObj.length,
                    nextLength = nextLiObj.length,
                    prevLi = null,
                    prevLiTabId = null,
                    nextLi = null,
                    nextLiTabId = null;
                if (prevLength) {
                    prevLi = $(prevLiObj[0]);
                    prevLiTabId = prevLi.data('tabid');
                    prevLi.addClass('menu-tab-hover');
                    $(self).find('div[data-contentid="' + prevLiTabId + '"]').removeClass('none');

                    //如果li  left<0 ，则需右移
                    if (prevLi.offset().left < $(self).offset().left) {


                        prevLi.parent().css('left', ( prevLi.parent().css('left').replace('px', '') * 1 + $(self).offset().left - prevLi.offset().left  ) + 'px');
                    }

                } else {
                    nextLi = $(nextLiObj[0]);
                    nextLiTabId = nextLi.data('tabid');
                    nextLi.addClass('menu-tab-hover');
                    $(self).find('div[data-contentid="' + nextLiTabId + '"]').removeClass('none');
                }
            }
            //重设ui的宽度
            //debugger
            $(self).find('ul').css('width', ( theParent.parent().width() - theParent.width()   ));

        });


        this.data('move', 0);
        this.data('posX', 0);
        this.data('left', 0);
        var self = this;
        this.mousedown(function (e) {
            $(self).data('move', '1');
            $(self).data('posX', e.pageX);
            $(self).data('left', $(self).find('ul').position().left);
        });

        this.mousemove(function (e) {
            //如果ul宽度小于父div宽度，则不
            if ($(self).find('ul').width() <= $(self).width() + 100) {
                return;
            }

            if ($(self).data('move') != "1") {
                return;
            }

            var dist = e.pageX - $(this).data('posX') * 1;
            //console.log(-($(this).find('ul').width()-$(this).find('ul').parent().width() -100) )
            if ($(self).find('ul').css('left').replace('px', '') > 0) {
                $(self).find('ul').css('left', ( $(self).data('left') + dist ) / 2 + 'px');
            } else {
                $(self).find('ul').css('left', ( $(self).data('left') + dist ) + 'px');
            }

        });
        this.bind(' mouseleave mouseup  ', function (e) {

            if ($(self).find('ul').css('left').replace('px', '') == '0') {
                return;
            }
            ;

            $(self).data('move', 0);
            $(self).data('posX', 0);
            $(self).data('left', 0);

            if ($(self).find('ul').css('left').replace('px', '') > 0) {
                $(self).find('ul').animate({left: '0px'}, 'fast');
                return;
            }
            if ($(self).find('ul').css('left').replace('px', '') < -($(self).find('ul').width() - $(self).width() - 100) && $(self).find('li:visible').length > 5) {
                $(self).find('ul').animate({left: -($(self).find('ul').width() - $(self).width() - 100) + 'px'}, 'fast');
                return;
            }
        });


    };


    /*
     *   通过某一按钮，触发切换到某tab
     *
     */
    $.fn.triggerTab = function (tabid, ulid, callback) {
        if (!tabid) {
            comm.alert({content: '标签未设置tabid'});
            return;
        }

        this.showTab = function (tabid, ulid, callback) {
            var newTab = $((ulid || '#main-tab' ) + '  > li[data-tabid="' + tabid + '"]');
            if (!newTab || !newTab.length) {
                //若tab容器中未找到，再从菜单查找
                var menuTab = $('#menu-box >h1[data-tabid="' + tabid + '"]').length && $('#menu-box >h1[data-tabid="' + tabid + '"]') ||
                    $('#menu-box  h2[data-tabid="' + tabid + '"]').length && $('#menu-box  h2[data-tabid="' + tabid + '"]') ||
                    $('#menu-box  li[data-tabid="' + tabid + '"]').length && $('#menu-box  li[data-tabid="' + tabid + '"]');
                if (!menuTab) {
                    comm.alert({content: '未找到tabid等于"' + tabid + '"的标签'});
                } else {
                    /*
                     //收缩其它菜单，展开当前菜单
                     menuTab.parent().parent().removeClass('none').removeAttr('style');
                     menuTab.parent().parent().siblings('div').addClass('none').removeAttr('style');
                     menuTab.parent().css('display','').removeClass('none').siblings().addClass('none');
                     */
                    menuTab.trigger('click');
                }
                return;
            }
            $((ulid || '#main-tab' ) + '  > li[data-tabid="' + tabid + '"]').trigger('click');
            if (typeof callback == 'function') {
                callback();
            }
        };

        switch (arguments.length) {
            case 1 :
                if (!isNaN(arguments[0])) {
                    this.click(function (e) {
                        this.showTab(tabid);
                    }.bind(this));
                } else {
                    comm.alert({imgClass: 'tips_i', content: '标签ID必须为数字！'});
                }
                break;
            case 2 :
                if (!!isNaN(arguments[0])) {
                    comm.alert({imgClass: 'tips_i', content: '标签ID必须为数字！'});
                    break;
                }
                if (typeof arguments[1] == 'string') {
                    //ulid
                    this.click(function (e) {
                        this.showTab(tabid, ulid);
                    }.bind(this));
                    break;
                } else if (typeof arguments[1] == 'function') {
                    //call
                    this.click(function (e) {
                        this.showTab(tabid, ulid);
                    }.bind(this));
                    break;
                } else {
                    comm.alert({imgClass: 'tips_i', content: '所传参数' + arguments[1] + '有误！'});
                }
                break;
            case 3 :
                if (!!isNaN(arguments[0])) {
                    comm.alert({imgClass: 'tips_i', content: '标签ID必须为数字！'});
                    break;
                }
                if (typeof arguments[1] != 'string') {
                    //ulid
                    comm.alert({imgClass: 'tips_i', content: '所传参数' + arguments[1] + '有误！'});
                    break;
                }
                if (typeof arguments[2] != 'function') {
                    //ulid
                    comm.alert({imgClass: 'tips_i', content: '所传参数' + arguments[2] + '有误！'});
                    break;
                }
                this.click(function (e) {
                    this.showTab(tabid, ulid, callback);
                }.bind(this));
                break;
        }
    };


})(jQuery);


/*给div 加resize 方法*/
(function ($, h, c) {
    var a = $([]), e = $.resize = $.extend($.resize, {}), i, k = "setTimeout", j = "resize", d = j + "-special-event", b = "delay", f = "throttleWindow";
    e[b] = 250;
    e[f] = true;
    $.event.special[j] = {
        setup: function () {
            if (!e[f] && this[k]) {
                return false
            }
            var l = $(this);
            a = a.add(l);
            $.data(this, d, {w: l.width(), h: l.height()});
            if (a.length === 1) {
                g()
            }
        }, teardown: function () {
            if (!e[f] && this[k]) {
                return false
            }
            var l = $(this);
            a = a.not(l);
            l.removeData(d);
            if (!a.length) {
                clearTimeout(i)
            }
        }, add: function (l) {
            if (!e[f] && this[k]) {
                return false
            }
            var n;

            function m(s, o, p) {
                var q = $(this), r = $.data(this, d);
                r.w = o !== c ? o : q.width();
                r.h = p !== c ? p : q.height();
                n.apply(this, arguments)
            }

            if ($.isFunction(l)) {
                n = l;
                return m
            } else {
                n = l.handler;
                l.handler = m
            }
        }
    };
    function g() {
        i = h[k](function () {
            a.each(function () {
                var n = $(this), m = n.width(), l = n.height(), o = $.data(this, d);
                if (m !== o.w || l !== o.h) {
                    n.trigger(j, [o.w = m, o.h = l])
                }
            });
            g()
        }, e[b])
    }
})(jQuery, this);
