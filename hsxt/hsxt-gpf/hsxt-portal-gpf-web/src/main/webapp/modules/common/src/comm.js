define(["commSrc/commFunc"], function () {
    $.extend(comm, {
        //401出错跳转，402出错提示
        errorCode: ['401', '402', '403', '404', '500', '501', '502', '503', 47154, 47155],

        /*
         * 设置全局缓存
         */
        cache: {},

        /*
         *   取模块首页路径
         */
        getIndex: function (moduleName) {
            return globalPath + "modules/" + moduleName + "/tpl/index.html";
        },
        /*
         *导航（链接）到模块的首页
         */
        navIndex: function (moduleName) {
            location.href = comm.getIndex(moduleName);
        },

        /*
         * 取任何指定的页面路径
         */
        getUrl: function (moduleName, urlName) {
            return globalPath + "modules/" + moduleName + "/tpl/" + urlName;
        },
        /*
         *导航（链接）到指定的模块及页面路径
         */
        navUrl: function (moduleName, urlName) {
            location.href = comm.getUrl(moduleName, urlName);
        },
        /*
         *  切换三级菜单标签，
         */
        liActive: function (liObj) {

            liObj.parent('ul').find('a').removeClass('active');
            liObj.find('a').addClass('active');
        },
        /*  下面的方法名等在此对象中被禁用
         *  getCurrDate ， formatDate ，formatMoneyNumber ，setFocus ，setBlur ，
         *  setCache ，getCache ， delCache ， Require ， Define ，emptyCurrPath，
         *  langConfig ， lang ，formatStr  ，confirm ，  alert
         *
         */
        /**
         * 同步请求后台方法封装
         *  @param url || {domain:'',url:''}请求的URL
         *  @param params 参数信息
         *  @param callback 成功回调函数
         *  @param langName 资源文件对象
         *  @param failback 失败回调函数
         */
        syncRequest: function (url, params, callback, langName,failback) {
            //comm.showLoading();
            comm.Request(url, {
                data: params,
                type: 'POST',
                dataType: "json",
                async: false,
                success: function (response) {
                    comm.closeLoading();
                    //非空验证
                    if (response.success) {
                        callback(response.data);
                    } else {
                        //获取code信息描述
                        var message = comm.lang(langName).errorCodes[response.errorCode];
                        //判断描述信息是否存在
                        if (message) {
                            comm.alert({
                                imgClass: 'tips_error',
                                content: message
                            });
                        } else {
                            comm.alert({
                                imgClass: 'tips_error',
                                content: comm.lang("common").errorCodeTip + response.errorCode
                            });
                        }
                        if(failback&& typeof failback == 'function'){
                            failback();
                        }
                    }
                },
                error: function () {
                    comm.closeLoading();
                    comm.alert({
                        imgClass: 'tips_error',
                        content: comm.lang('common').defaultErrorTip
                    });
                }
            });
        },
        /**
         * 异步请求后台方法封装
         *  @param urlStr 请求的URL
         *  @param params 参数信息
         *  @param langName 资源文件对象
         */
        asyncRequest: function (urlStr, params, langName) {
            var resData = null;
            comm.Request(urlStr, {
                data: params,
                type: 'POST',
                dataType: "json",
                async: true,
                success: function (response) {
                    comm.closeLoading();
                    //非空验证
                    if (response.success) {
                        resData = response.data;
                    } else {
                        //获取code信息描述
                        var message = comm.lang(langName).errorCodes[response.errorCode];
                        //判断描述信息是否存在
                        if (message) {
                            comm.alert({
                                imgClass: 'tips_error',
                                content: message
                            });
                        } else {
                            comm.alert({
                                imgClass: 'tips_error',
                                content: comm.lang("common").errorCodeTip + response.errorCode
                            });
                        }
                    }
                },
                error: function () {
                    comm.closeLoading();
                    comm.alert({
                        imgClass: 'tips_error',
                        content: comm.lang('common').defaultErrorTip
                    });
                }
            });
            return resData;
        },
        /**
         * 初始化下拉框
         * @param objId 需要绑定的下拉框元素
         * @param objArray 枚举内容（来源于国际化枚举对象）
         * @param width 选择框宽度（可选参数）
         * @param defaultVal 默认值（可选参数）
         * @param defaultOptions 默认选项
         */
        initSelect: function (objId, objArray, width, defaultVal, defaultOptions) {
            var options = [];
            if (defaultOptions) {
                options.push(defaultOptions);
            }
            for (key in objArray) {
                options.push({name: objArray[key].entCustName, value: objArray[key].entResNo});
            }
            var content = {options: options};
            if (width) {
                content.width = width;
                content.optionWidth = width;
            }
            var select = $(objId).selectList(content);
            if (defaultVal != null && defaultVal != undefined) {
                select.selectListValue(defaultVal);
            }
            return select;
        },
        /**
         * 构建常用BsGrid
         * @param gridId 要绑定的tableId
         * @param url 请求数据url
         * @param params json类型参数
         * @param detail 自定义函数detail（可选参数）
         * @param del 自定义函数del（可选参数）
         * @param add 自定义函数add（可选参数）
         * @param edit 自定义函数edit（可选参数）
         * @param renderImg 自定义函数renderImg（可选参数）
         */
        getCommBsGrid: function (gridId, url, params, detail, del, add, edit, renderImg) {

            return $.fn.bsgrid.init(gridId || 'tableDetail', {
                url: url,
                otherParames: params||{},
                pageSize: 10,
                stripeRows: true,  //行色彩分
                displayBlankRows: false,
                operate: {
                    detail: detail,
                    del: del,
                    add: add,
                    edit: edit,
                    renderImg: renderImg
                }
            });
        },
        /**
         * 隐藏等待效果
         */
        closeLoading: function () {
            try {
                $("#showLoadIngDiv").dialog('destroy');
                $("#showLoadIngDiv").addClass('none');
            } catch (e) {
            }
        },
        i_confirm: function (text, callback, width) {
            $("#ques_content").html(text);
            $("#alert_ques").dialog({
                title: "提示信息",
                width: width || "400",
                /*此处根据文字内容多少进行调整！*/
                modal: true,
                buttons: {
                    "确定": function () {
                        if (callback)
                            callback();
                        $(this).dialog("destroy");
                    },
                    "取消": function () {
                        $(this).dialog("destroy");
                    }
                }
            });
        },
        //弹窗
        i_alert: function (text, width) {
            $("#i_content").html(text);
            $("#alert_i").dialog({
                title: "提示信息",
                width: width || "400",
                /*此处根据文字内容多少进行调整！*/
                modal: true,
                buttons: {
                    "关闭": function () {
                        $(this).dialog("destroy");
                    }
                }
            });
        },
        //关闭提示信息
        i_close: function () {
            $("#alert_i").dialog("destroy");
        },
        ipro_alert: function (text) {
            $("#i_content").html(text);
            $("#alert_i").dialog({
                dialogClass: "no-dialog-close",
                title: "提示信息",
                width: "400"
            });
        },
        ipro_alert_close: function () {
            try {
                $('#alert_i').dialog("destroy");
            } catch (e) {
            }
        },
        //弹窗
        Thickdiv: function (title, text, width) {
            $("#i_content2").html(text);
            $("#alert_i2").dialog({
                title: title,
                width: width || "400",
                /*此处根据文字内容多少进行调整！*/
                modal: true
            });
        },
        //弹窗
        Thickdiv2: function (text, width) {
            $("#i_content").html(text);
            $("#alert_i").dialog({
                width: width || "400",
                /*此处根据文字内容多少进行调整！*/
                modal: true
            });
        },
        //yes
        yes_alert: function (text) {
            comm.alert({
                imgClass: 'tips_yes',
                content: text
            });
        },
        getLoginInfo: function () {
            return comm.getCache("home", "loginInfo");
        }
    });
    return null;
});