(function ($) {

    getSession();

    //var triggerName = Util.param.getTriggerName();    
    var triggerName = getQueryString("triggerName");
    var triggerGroup = Util.param.getTriggerGroup();
        
    fetchJob();

    //
    // 获取配置项
    //
    function fetchJob() {

        //
        // 获取此配置项的数据
        //
        $.ajax({
            type: "GET",
            url: "api/trigger/get",
            data: {
                "triggerName": triggerName,
                "triggerGroup": triggerGroup
            }
        }).done(
            function (data) {
                if (data.success === "true") {
                    var result = data.result;
                    $("#triggerName").val(result.triggerName);
                    $("#triggerGroup").val(result.triggerGroup);
                    $("#version").val(result.version);
                    $("#cron").val(result.cron);
                    $("#desc").val(result.desc);
                    fetchItems(result.triggerName,result.triggerGroup);
                    
                    listNextFireTimes('cron');
                }
            });
    }
    
     //
	 // 获取指定所有业务数据列表
	 //
	 function fetchItems(triggerName,triggerGroup) {	
	     $.ajax({
	         type: "GET",
	         url: "api/trigger/list"
	     }).done(function (data) {
	         if (data.success === "true") {
	             var html = '<li style="margin-bottom:10px">任务列表</li>';
	             var result = data.page.result;
	             $.each(result, function (index, item) {
	                 html += renderItem(item);
	             });
	             
	             $("#sidebarcur").html(html);
	         }
	     });
	     var mainTpl = $("#tItemTpl").html();
	     // 渲染主列表
	     function renderItem(item) {
	    	 var name=item.triggerName;
	    	 var group=item.triggerGroup;
	    	 
			 var link = 'modifyJob.html?triggerName=' + name + '&triggerGroup='
					+ group;
//			 var key = '<i title="任务名称" class="icon-file"></i>' + name
//					+ '<i title="任务组" class="icon-leaf"></i>' + group;
			 
			 var key = '<i title="任务组" class="icon-leaf"></i>' + name;
		         
	         var style = "";
	         if (name == triggerName&&group==triggerGroup) {
	             style = "active";
	         }
	         return Util.string.format(mainTpl, key, link, style);
	     }
	 }

    // 提交
    $("#submit").on("click", function (e) {
        $("#error").addClass("hide");
        var me = this;
        var triggerName = $("#triggerName").val();
        var triggerGroup = $("#triggerGroup").val();
        var cron = $("#cron").val();
        var desc = $("#desc").val();
        // 验证
        if (!cron) {
            $("#error").removeClass("hide");
            $("#error").html("Cron表达式不能为空！");
            return;
        }
        
        // 验证
//        if (!desc) {
//            $("#error").removeClass("hide");
//            $("#error").html("任务功能描述不能为空！");
//            return;
//        }        
        
        if (!confirm("确认修改？")) {
            return;
        }
        $.ajax({
            type: "POST",
            dataType:"json",
            url: "api/trigger/update",
            data: {
                "triggerName": triggerName,
                "triggerGroup": triggerGroup,
                "cron": cron,
                "desc": desc
            }
        }).done(function (data) {
            $("#error").removeClass("hide");
            if (data.success === "true") {
                $("#error").html(data.result);
            } else {
                Util.input.whiteError($("#error"), data);
            }
        });
    });
    
    $("#cron").on("blur", function (e) {
    	listNextFireTimes('cron');
    });
    
    $("#cron").on("input", function (e) {
    	listNextFireTimes('cron');
    });
    
    $("#cron").on("keyup", function (e) {
    	listNextFireTimes('cron');
    });

})(jQuery);

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
