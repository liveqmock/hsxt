(function ($) {

    getSession();

    var busId = Util.param.getBusId();
    var postId = -1;
    fetchBus();
    fetchPostBus();

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
                    $("#version").val(result.version);
                    $("#servicePara").val(result.servicePara);
                    $("#desc").val(result.desc);
                    
                    fetchItems(result.busId);
                }
            });
    }
    
    //
    // 获取后置配置项
    //
    function fetchPostBus() {

        //
        // 获取此配置项的数据
        //
        $.ajax({
            type: "GET",
            url: "api/bus/list"
        }).done(
            function (data) {
                if (data.success === "true") {
                    var html = '';
                    var result = data.page.result;
                    $.each(result, function (index, item) {
                    	if(item.busId!=busId){
	                    	var postKey='业务名称:' + item.serviceName;                   
	                        html += '<option value="' + item.busId + '"> '
	                        		+ postKey + '</option>';
                    	}
                    });
                    $("#postServiceChoice").html(html);
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
	         var link = 'postService.html?busId=' + item.busId;
//	         var key = '<i title="业务名称" class="icon-file"></i>' + item.serviceName+'<i title="业务组" class="icon-leaf"></i>' + item.serviceGroup;
	         var key = '<i title="业务组" class="icon-leaf"></i>' + item.serviceGroup;

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
        var serviceName = $("#serviceName").val();
        var serviceGroup = $("#serviceGroup").val();
        
        if (!confirm("确认设置后置业务？")) {
            return;
        }
        
        var postId = $("#postServiceChoice").val();
        
        $.ajax({
            type: "POST",
            dataType:"json",
            url: "api/bus/setPost",
            data: {
                "serviceName": serviceName,
                "serviceGroup": serviceGroup,
                "postId":postId
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
