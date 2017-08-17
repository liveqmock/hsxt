(function ($) {

    getSession();

    var busId = Util.param.getBusId();

    fetchBus();

    //
    // 获取配置项
    //
    function fetchBus() {

        //
        // 获取此配置项的数据
        //
        $.ajax({
            type: "GET",
            url: "api/bus/get/" + busId
        }).done(
            function (data) {
                if (data.success === "true") {
                    var result = data.result;
                    $("#triggerName").val(result.serviceName);
                    $("#triggerGroup").val(result.serviceGroup);
                    $("#triggerPara").val(result.servicePara);
                    $("#desc").val(result.desc);
                    $("#version").val(result.version);
                    fetchItems(result.busId);
                }
            });
    }
    
    
	 //
	 // 获取指定所有业务数据列表
	 //
	 function fetchItems(busId) {	
	     $.ajax({
	         type: "GET",
	         url: "api/bus/list",
	         data: {
	        	 "hasTaskFlag": 1
	         }
	     }).done(function (data) {
	         if (data.success === "true") {
	             var html = '<li style="margin-bottom:10px">未设置定时任务的业务列表</li>';
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
	         var link = 'newjob.html?busId=' + item.busId;
//	         var key = '<i title="业务名称" class="icon-file"></i>' + item.serviceName+'<i title="业务组" class="icon-leaf"></i>' + item.serviceGroup;
	
	         var key = '<i title="业务名称" class="icon-leaf"></i>' + item.serviceName;
	         
	         var style = "";
	         if (item.busId == busId) {
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
        var desc = $("#descJob").val();
        var cron = $("#cron").val();
        // 验证
        if (!cron) {
            $("#error").removeClass("hide");
            $("#error").html("cron表达式不能为空！");
            return;
        }
        
        $.ajax({
            type: "POST",
            dataType:"json",
            url: "api/trigger/",
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
                
                window.alert(data.result);
                window.location.href="jobs.html";
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
