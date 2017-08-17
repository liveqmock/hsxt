(function ($) {

    getSession();

    var busId = Util.param.getBusId();
    var postId = -1;
    fetchBus();
    // fetchPostBus();

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
                	fetchPostBus();
                	
                    var result = data.result;
                    $("#serviceName").val(result.serviceName);
                    $("#serviceGroup").val(result.serviceGroup);
                    $("#version").val(result.version);
                    $("#servicePara").val(result.servicePara);
                    $("#desc").val(result.desc);
                    
                    var serviceGroup = result.fontJobServiceGroup;

                    if(serviceGroup) {
                       $("#frontIds").html('');
                    	
                       serviceGroup = serviceGroup.replace(/(\s*)/g, "");
                       
                       var serviceGroupArry = serviceGroup.split(',');
                                              
                       for(var index in serviceGroupArry) {
                    	   // 去空格
                    	   var value = serviceGroupArry[index];
                    	   
                           // $("#"+value).remove();

                           var opts = '<option id="'+value+'" value="' + value + '">'+ value + '</option>';
                           
                           $("#frontIds").html($("#frontIds").html()+opts);
                       }
                    }
                    
                    fetchItems(result.busId);
                }
            });
    }
    
    //
    // 获取前置配置项
    //
    function fetchPostBus() {
    	
    	var flag = false;

        //
        // 获取此配置项的数据
        //
        $.ajax({
            type: "GET",
            url: "api/bus/list",
            async: false
        }).done(
            function (data) {
                if (data.success === "true") {
                    var html = '';
                    var result = data.page.result;
                    $.each(result, function (index, item) {
                    	if(item.busId!=busId){                   		
	                    	var frontKey = item.serviceName;
	                        html += '<option id="'+frontKey+'" value="' + frontKey + '">'+ frontKey + '</option>';
                    	}
                    });
                    
                    $("#select2").html(html);
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
	         var link = 'frontService.html?busId=' + item.busId;
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
        var serviceName = $("#serviceName").val();
        var serviceGroup = $("#serviceGroup").val();
        var frontIds = '';
                
        $("#frontIds option").each(function() {
        	frontIds += $(this).attr("value")+",";
        });
        
		if (0 < frontIds.length) {
			frontIds = frontIds.substring(0, frontIds.length - 1);
		}
                        
        if (!confirm("确认设置前置业务？")) {
            return;
        }
        
        $.ajax({
            type: "POST",
            dataType:"json",
            url: "api/bus/setFront",
            data: {
                "serviceName": serviceName,
                "serviceGroup": serviceGroup,
                "frontIds":frontIds
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
    
    // 失去焦点
    $("#frontIds").on("blur", function (e) {              
        $("#frontIds option").each(function() {
        	$("#frontIds").val('');
        });
    });

})(jQuery);

$(function(){	
	//移到右边
	$('#add').click(function(){
		//先判断是否有选中
		if(!$("#select2 option").is(":selected")){			
			alert("请选择需要添加的选项!")
		}
		//获取选中的选项，删除并追加给对方
		else{
			$('#select2 option:selected').appendTo('#frontIds');
		}	
	});
	
	//移到左边
	$('#remove').click(function(){
		//先判断是否有选中
		if(!$("#frontIds option").is(":selected")){			
			alert("请选择需要移除的选项!")
		}
		else{
			$('#frontIds option:selected').appendTo('#select2');
		}
	});
	
	//全部移到右边
	$('#add_all').click(function(){
		//获取全部的选项,删除并追加给对方
		$('#select2 option').appendTo('#frontIds');
	});
	
	//全部移到左边
	$('#remove_all').click(function(){
		$('#frontIds option').appendTo('#select2');
	});
	
	//双击选项
	$('#frontIds').dblclick(function(){ //绑定双击事件
		//获取全部的选项,删除并追加给对方
		$("option:selected",this).appendTo('#select2'); //追加给对方
	});
	
	//双击选项
	$('#select2').dblclick(function(){
		$("option:selected",this).appendTo('#frontIds');
	});
	
});
