(function ($) {

    getSession();
    
    var appId = getQueryString("appId");
    var envId = getQueryString("envId");
    
    var appIdInCookies = getCookie("ds_mainlist_sel_appId");    

	appId = appId ? appId : (appIdInCookies?appIdInCookies:2);
	envId = envId ? envId : 4; // 1-生产环境; 2-演示环境; 3-测试环境; 4-本地环境;
    
    var version = "#";
    var appName = '';
        
    //
    // 获取Env信息
    //
    $.ajax({
        type: "GET",
        url: "api/env/list"
    }).done(
        function (data) {
            if (data.success === "true") {
                var html = "";
                var result = data.page.result;
                
                html += '当前APP名称';
                
				// $.each(
				// result,
				// function (index, item) {
				// html += item.name + '环境';
				// envId=item.id;
				// });
                
                $("#envsDropdownMenuTitle").html(html);
                $("#env_info").html("");
            }            
        });

    //
    // 获取APP信息
    //

    $.ajax({
        type: "GET",
        url: "api/app/list"
    }).done(
        function (data) {
            if (data.success === "true") {
                var html = "";
                var result = data.page.result;
                $.each(result, function (index, item) {
                    if(index==0){
                        html += '<option value="' + item.id + '" selected> ' + item.name + '</option>';
                    }else{
                        html += '<option value="' + item.id + '"> ' + item.name + '</option>';
                    }
                    
                    if(appId == item.id) {
                        $("#appDesc").html(item.desc);
                        appName = item.name;
                    }
                });
                
                $("#appChoice").html(html);
                
                if(appId) {
                	$("#appChoice").val(appId);
                }
                
                $("#app_info").html($("#appChoice").find("option:selected").text());
                
                fetchVersion(appId, envId);
            }
        });
    
    $("#appChoice").on('change', function () {
        appId = $(this).val();
        
        version = "#";
        
        setCookie("ds_mainlist_sel_appId", appId);
        
        // fetchVersion(appId, envId);
        window.location="main.html?appId="+appId+"&envId="+envId;
    });
    
    //
    // 获取版本信息
    //
    function fetchVersion(appId, envId) {

        var base_url = "api/web/config/versionlist?appId=" + appId;
        url = base_url;
        if (envId != -1) {
            url = base_url + "&envId=" + envId;
        }

        $.ajax({
            type: "GET",
            url: url
        }).done(function (data) {
            if (data.success === "true") {
                var html = "";
                var result = data.page.result;
                
                $.each(result, function (index, item) {
                    html += '<li><a href="#">' + item + '</a></li>';
                });
                
                $("#versionChoice").html(html);

                if (html != "") {
                    $("#versionChoice li:first").addClass("active");
                    version = $("#versionChoice li:first a").text();
                }
                
                fetchMainList();
            }
        });
//        $("#versionChoice").unbind('click').on('click', 'li a', function (e) {
//            version = $(this).text();
//            $("#versionChoice li").removeClass("active");
//            $(this).parent().addClass("active");
//            fetchMainList();
//            e.stopPropagation();
//        });
    }

    fetchMainList();

    //
    // 渲染主列表
    //
    function fetchMainList() {

        // 参数不正确，清空列表
        if (appId == -1 || envId == -1) {
            $("#mainlist_error").text("请选择" + getTips()).show();
            $("#accountBody").html("");
            $("#mainlist").hide();
            $("#zk_deploy").hide();
            return;
        }

        if (version == "#") {
        }

        $("#zk_deploy").show().children().show();

        $("#batch_download").attr(
            'href', "api/web/config/downloadfilebatch?appId=" + appId + "&envId="
                + envId + "&version=" + version);

        $("#mainlist_error").hide();
        var parameter = ""

        url = "api/web/config/list";
        if (appId == null && envId == null && version == null) {

        } else {
            url += "?";
            if (appId != -1) {
                url += "appId=" + appId + "&";
            }
            if (envId != -1) {
                url += "envId=" + envId + "&";
            }
            if (version != "#") {
                url += "version=" + version + "&";
            }
        }

        $.ajax({
            type: "GET",
            url: url
        }).done(function (data) {
            if (data.success === "true") {
                var html = "";
                var result = data.page.result;
                $.each(result, function (index, item) {
                    html += renderItem(item, index);
                });
                if (html != "") {
                    $("#mainlist").show();
                    $("#accountBody").html(html);
                } else {
                    $("#accountBody").html("");
                }

            } else {
                $("#accountBody").html("");
                $("#mainlist").hide();
            }

            bindDetailEvent(result);

            // ZK绑定情况
            fetchZkDeploy();
        });
        var mainTpl = $("#tbodyTpl").html();
        // 渲染主列表
        function renderItem(item, i) {

            var link = "";
            del_link = '<a id="itemDel'
                + item.configId
                + '" style="cursor: pointer; cursor: hand; " ><i title="删除" class="icon-remove"></i></a>';
           
            if (item.type == "配置文件") {
                link = '<a target="_self" href="modifyFile.html?configId=' + item.configId
                    + '"><i title="修改" class="icon-edit"></i></a>';
            } else {
                link = '<a target="_self" href="modifyItem.html?configId=' + item.configId
                    + '"><i title="修改" class="icon-edit"></i></a>';
            }
            
            var downloadlink = '<a href="api/web/config/download/' + +item.configId
                + '"><i title="下载" class="icon-download-alt"></i></a>';

            var type = '<i title="配置项" class="icon-leaf"></i>';
            
            if (item.type == "配置文件") {
                type = '<i title="配置文件" class="icon-file"></i>';
            }

            var data_fetch_url = '<a href="javascript:void(0);" class="valuefetch'
                + item.configId + '" data-placement="left">点击查看</a>';
                
            var item_key = '<a href="javascript:void(0);" class="valuefetch'
                + item.configId + '" data-placement="left" title="点击查看">'+item.key+'</a>';

            var isRight = "OK";
            var style = "";
            
            if (item.errorNum > 0) {
                isRight = "; 其中" + item.errorNum + "台出现错误";
                style = "text-error";
            }
            
            var machine_url = '<a href="javascript:void(0);" class="' + style
                + ' machineinfo' + item.configId
                + '" data-placement="left">' + item.machineSize + '台 '
                + isRight + '</a>'

            return Util.string.format(mainTpl,'', item.appId,
                item.version, item.envId, item.envName, type, item_key,
                item.createTime, item.modifyTime, item.value, link,
                del_link, i + 1, downloadlink, data_fetch_url, machine_url,item.appName);
        }
    }

    /**
     * @param result
     * @returns {String}
     */
    function getMachineList(machinelist) {

        var tip;
        if (machinelist.length == 0) {
            tip = "";
        } else {
            tip = '<div style="margin:10px 10px 10px;"><table class="table-bordered" width="100%"><tr><th width="50%" height="25px">&nbsp;机器</th><th width="50%">&nbsp;状态</th></tr>';
            for (var i = 0; i < machinelist.length; i++) {
                var item = machinelist[i];

                var flag = "正常";
                var style = "";
                if (item.errorList.length != 0) {
                    flag = "错误: " + item.errorList.join(",");
                    style = "text-error";
                }

                tip += '<tr><td height="30px">&nbsp;' + item.machine + "</td>"
                    + '<td><span class="' + style + '">&nbsp;' + flag + "</span></td></tr>";
            }
            tip += '</table></div>';
        }
        return tip;
    }

    var popObjList = new Array();

    //
    // 渲染 配置 value
    //
    function fetchConfigValue(configId, object) {
        //
        // 获取APP信息
        //
        $.ajax({
            type: "GET",
            url: "api/web/config/" + configId
        }).done(
            function (data) {
                if (data.success === "true") {
                   var result = data.result;
                    
                   var close ='<div style="text-align:right; padding-right:10px; padding-bottom:0px;"><a href="javascript:fadeOut();" title="关闭对话框">关闭</a></div>';
                                       
                    $("#light").html(close+'<div style="padding-bottom:5px;">'+result.key+'配置信息：</div><div id="content" class="content"><pre>' + Util.input.escapeHtml(result.value) + '</pre></div>');
                                        
                    $("#light").fadeIn();
                    $("#fade").fadeIn();
                }
            });
    }

    //
    // 获取 ZK
    //
    function fetchZkInfo(configId, object) {
        //
        // 获取APP信息
        //
        $.ajax({
            type: "GET",
            url: "api/web/config/zk/" + configId
        }).done(
            function (data) {
                if (data.success === "true") {
                    var result = data.result;
                    
                    var close ='<div style="text-align:right; padding-right:10px; padding-bottom:0px;"><a href="javascript:fadeOut();" title="关闭对话框">关闭</a></div>';
                    
                    $("#light").html(close+'<div style="padding-bottom:5px;">实例列表信息：</div><div id="content" class="content">' + getMachineList(result.datalist) + '</div>');
                                        
                    $("#light").fadeIn();
                    $("#fade").fadeIn();
                }
            });
    }


    // 详细列表绑定事件
    function bindDetailEvent(result) {
        if (result == null) {
            return;
        }
        $.each(result, function (index, item) {
            var id = item.configId;

            // 绑定删除事件
            $("#itemDel" + id).on("click", function (e) {
                deleteDetailTable(id, item.key);
            });

            $(".valuefetch" + id).on('click', function () {
                var e = $(this);
                //e.unbind('click');
                fetchConfigValue(id, e);
            });

            $(".machineinfo" + id).on('click', function () {
                var e = $(this);
                // e.unbind('click');
                fetchZkInfo(id, e);
            });

        });

    }

    // 删除
    function deleteDetailTable(id, name) {

        var ret = confirm("你确定要删除 " + name + "吗?");
        if (ret == false) {
            return false;
        }

        $.ajax({
            type: "DELETE",
            url: "api/web/config/delete/" + id
        }).done(function (data) {
            if (data.success === "true") {
                fetchMainList();
            }
        });
    }

    //
    function getTips() {
        if (appId == -1) {
            return "APP";
        }
        if (envId == -1) {
            return "环境";
        }
        return "参数";
    }

    //
    function fetchZkDeploy() {
        if ($("#zk_deploy_info").is(':hidden')) {
            var cc = '';
        } else {
            fetchZkDeployInfo();
        }
    }

    //
    // 获取ZK数据信息
    //
    function fetchZkDeployInfo() {

        $("#zk_deploy_info_pre").html("正在获取ZK信息，请稍等......");

        // 参数不正确，清空列表
        if (appId == -1 || envId == -1 || version == "#") {
            $("#zk_deploy_info_pre").html("无ZK信息");
            return;
        }

        var base_url = "api/zoo/zkdeploy?appId=" + appId + "&envId=" + envId
            + "&version=" + version

        $.ajax({
            type: "GET",
            url: base_url
        }).done(function (data) {
            if (data.success === "true") {
                var html = data.result.hostInfo;
                if (html == "") {
                    $("#zk_deploy_info_pre").html("无ZK信息");
                } else {
                    $("#zk_deploy_info_pre").html(html);
                }
            }
        });
    }

    $("#zk_deploy_button").on('click', function () {
        $("#zk_deploy_info").toggle();
        fetchZkDeploy();
    });
    
    $("#del_app_button").on('click', function () {
    	if(window.confirm("确定要删除 '"+appName+"' 这个APP吗 ？")) {
    		$.ajax({
                type: "GET",
                url: "api/app/delete?appId="+appId
            }).done(function (data) {
                if (data.success === "true") {
                	window.alert("删除成功");
                    window.location="main.html";
                } else {
                	window.alert("删除失败！");
                }
            });
    	};
    });
    
    $("#new_conf_file").on('click', function () {
    	window.location="newconfig_file.html?appId="+appId;
    });

})(jQuery);

function fadeOut() {
	$("#light").fadeOut();
	$("#fade").fadeOut();
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

function getCookie(name) {
	var arr = document.cookie
			.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (arr != null)
		return unescape(arr[2]);
	return null;
}

center("#light");
center("#fade");
