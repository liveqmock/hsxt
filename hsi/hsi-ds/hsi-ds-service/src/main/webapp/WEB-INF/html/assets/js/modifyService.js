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
                    $("#serviceName").val(result.serviceName);
                    $("#serviceGroup").val(result.serviceGroup);
                    $("#servicePara").val(result.servicePara);
                    $("#version").val(result.version);
                    $("#desc").val(result.desc);
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
	         url: "api/bus/list"
	     }).done(function (data) {
	         if (data.success === "true") {
	             var html = '<li style="margin-bottom:10px">业务列表</li>';
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
	         var link = 'modifyService.html?busId=' + item.busId;
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
        var servicePara = $("#servicePara").val();
        var version = $("#version").val();
        var desc = $("#desc").val();
        // 验证
        if (!version) {
            $("#error").removeClass("hide");
            $("#error").html("DUBBO服务版本不能为空！");
            return;
        }
        
        // 验证
        if (!desc) {
            $("#error").removeClass("hide");
            $("#error").html("业务描述不能为空！");
            return;
        }
        
    	var reg =/^\d+(\.\d+){2}$/;
    	var r = version.match(reg);  
        if(r==null){
        	$("#error").show();
        	$("#error").html("对不起，您输入的DUBBO服务版本格式不正确！正确格式形如：1.0.0");
        	return false;
        }  
        
        if (servicePara != null && servicePara != undefined && servicePara != '') {
    	    // var reg =/^([A-Za-z0-9\_]{1,}[=]{1}[A-Za-z0-9\_]{1,}){1}([;]{1}[A-Za-z0-9\_]{1,}[=]{1}[A-Za-z0-9\_]{1,}){0,}$/;
        	// var reg =/^([A-Za-z0-9\_]{1,}[=]{1}[^\f\n\r\t\v;]{0,}){1}([;]{1}[A-Za-z0-9\_]{1,}[=]{1}[^\f\n\r\t\v;]{1,}){0,}$/;
        	var reg =/^([A-Za-z0-9\_]{1,}[=]{1}[^\f\n\r\t\v;]{0,}[;]{0,1}){1}([;]{1}[A-Za-z0-9\_]{1,}[=]{1}[^\f\n\r\t\v;]{0,}[;]{0,1}){0,}$/;
        	var r = servicePara.match(reg);  
    	    if(r==null){
    	        $("#error").show();
    	        $("#error").html("对不起，参数格式：key=value;key=value... key只能是字母、数字、下划线, value可以是除了英文分号之外的任何字符。");
    	        return;
    	    }  
        }
        
        if (!confirm("确认修改？")) {
            return;
        }
        $.ajax({
            type: "POST",
            dataType:"json",
            url: "api/bus/update/" + busId,
            data: {
                "servicePara": servicePara,
                "version": version,
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

})(jQuery);
