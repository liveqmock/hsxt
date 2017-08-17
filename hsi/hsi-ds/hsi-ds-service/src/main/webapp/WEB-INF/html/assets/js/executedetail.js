
(function ($) {
    getSession();
    
    bindInputKepress(['#serviceName','#serviceGroup','#startDate','#endDate']);
    
    function today(){
        var today=new Date();
        var h=today.getFullYear();
        var m=today.getMonth()+1;
        var d=today.getDate();
        
        if(10 > m) {
        	m = '0'+m;
        }
        
        if(10 > d) {
        	d = '0'+d;
        }
        
        return h+"-"+m+"-"+d;
    }
    
    document.getElementById("startDate").value = today()+' 00:01';
    document.getElementById("endDate").value = today()+' 23:59';
    
    fetchDetailList();
    
	$('#startDate').datetimepicker({
		language:  'zh-CN',
		autoclose : true,
		todayBtn:  1,
		todayHighlight: 1,
		format :"yyyy-mm-dd hh:mm",
		startView: 2,
		minView: 2
	});
	$('#endDate').datetimepicker({
		language:  'zh-CN',
		autoclose : true,
		todayBtn:  1,
		todayHighlight: 1,
		format :"yyyy-mm-dd 23:59",
		startView: 2,
		minView: 2
	});
	
	{
		var inputedStartDate = $("#startDate").val();
		var inputedEndDate = $("#endDate").val();
		
		$("#startDate").on("input", function (e) {
			inputedStartDate = $("#startDate").val();
		});
		
		$("#startDate").on("blur", function (e) {
			$("#startDate").val(inputedStartDate);
		});
		
		$("#endDate").on("input", function (e) {
			inputedEndDate = $("#endDate").val();
		});
		
		$("#endDate").on("blur", function (e) {
			$("#endDate").val(inputedEndDate);
		});
	}
	
    $("#query_submit").on("click", function (e) {
    	doQuery();
    });    
})(jQuery);

//
//绑定输入框的回车事件
//
function bindInputKepress(inputFieldIds) {		
    $.each(inputFieldIds, function (index, inputFieldId) {    	    	
    	// 绑定回车事件
    	$(inputFieldId).bind('keypress', function(event){
    		if(13 == event.keyCode){ 
    			doQuery();
    		}
    	})
    });
}

//
// 渲染主列表
//
function fetchDetailList() {
    var serviceName = $("#serviceName").val();
    var serviceGroup = $("#serviceGroup").val();
    var executeState = $("#executeState").val();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    var maxCounts = $("#maxCounts").val();
    
    if(!startDate){
    	alert("开始日期不能够为空!");
    	return;
    }
    
    if(!endDate){
    	alert("结束日期不能够为空!");
    	return;
    }
    
    if(serviceName) {
    	serviceName = serviceName.replace(/,$/, "");
    }
    
    $.ajax({
        type: "GET",
        url: "api/detail/list",
        data: {
            "serviceName": serviceName,
            "serviceGroup": serviceGroup,
            "executeState": executeState,
            "startDate": startDate,
            "endDate": endDate,
            "maxCounts": maxCounts
        }
    }).done(function (data) {
        if (data.success === "true") {
            var html = "";
            var result = data.page.result;
            $.each(result, function (index, item) {
                html += renderItem(item, index);
            });
            if (html != "") {
                $("#accountBody").html(html);
            } else {
                $("#accountBody").html("");
            }

        } else {
            $("#accountBody").html("");
        }
        
        $("#noteInfo").hide();
        $("#query_submit").show();
    });
    
    var mainTpl = $("#tbodyTpl").html();
    // 渲染主列表
    function renderItem(item, i) {
    	
    	var executeId = item.executeId;
    	var index = executeId.indexOf('#');

		if (0 < index) {
			executeId = ' ***' + executeId.substring(index - 6, executeId.length);
		} else {
			executeId = ' ***' + executeId.substring(executeId.length - 6, executeId.length);
		}    	
    	
    	var state = item.serviceState;
    	
    	var desc = item.desc;
    	var reportDate = item.reportDate;
    	var serviceGroup = item.serviceGroup;
    	var serviceName = item.serviceName;
    	var executeMethod = item.executeMethod;
    	var isFullDesc = item.fullDesc;
    	var timeEclipsed = item.timeEclipsed;
    	var executeParam = item.executeParam;
    	
    	if("成功" == state) {
    		state = "<font style='BACKGROUND-COLOR:#B9FFDC;' color='black'>"+ state +"</font>";
    	} else if("失败" == state) {
    		state = "<font color='red' style='font-weight:bold;'>"+ state +"</font>";
    		desc = "<font color='red'>"+ desc +"</font>";
    		reportDate = "<font color='red'>"+ reportDate +"</font>";
    		
    		serviceGroup = "<font color='red'>"+ serviceGroup +"</font>";
    		serviceName = "<font color='red'>"+ serviceName +"</font>";
    		
    		executeId = "<font color='red'>"+ executeId +"</font>";
    		executeParam = "<font color='red'>"+ executeParam +"</font>";
    	} else if("未上报" == state) {
    		state = "<font color='red'>"+ state +"</font>";
    		desc = "<font color='red'>"+"如果超过24小时仍未上报, 请检查DS调用的Service提供方程序处理是否正常！"+"</font>";
    	} else if("未执行" == state) {
    		timeEclipsed = "-";
    		reportDate = (!reportDate) ? "-" : reportDate;
			desc = (!desc) ? "-" : desc;
    	}
    	
    	if('1' == executeMethod) {
    		executeMethod = "<font style='font-weight:bold;'>手工</font>";
    	} else {
    		executeMethod = "自动";
    	}
    	        	
    	if(!isFullDesc) {
    		desc = '<a id="'+item.executeId+'" href="javascript:void(0);" onClick="javascript:openDetailErrDesc(\''+item.executeId+'\');" data-placement="left" title="点击查看更详细信息">'+desc+'</a>';
    	}
    	
    	return Util.string.format(mainTpl, '', i + 1, serviceGroup,
				serviceName, state, reportDate, desc, executeId,
				executeMethod, timeEclipsed, executeParam);
    }
}

function doQuery() {
	if($("#startDate").val() > $("#endDate").val()) {
		window.alert("开始日期不能大于结束日期！");
		
		return;
	}    	
	
	$("#query_submit").hide();
	$("#noteInfo").show();
	
	setTimeout(fetchDetailList(), 200);
}

function fadeOut() {
	$("#light").fadeOut();
	$("#fade").fadeOut();
}

function openDetailErrDesc(executeId) {
	// 为了规避ajax无法传递#井号问题
	executeId = executeId.replace("#","@").replace("\.","@@");
		
    $.ajax({
        type: "GET",
        url: "api/detail/get_detail_err_desc/" + executeId
    }).done(
        function (data) {
            if (data.success === "true") {
               var result = Util.input.escapeHtml(data.result);
                
               var close ='<div style="text-align:right; padding-right:10px; padding-bottom:0px;"><a href="javascript:fadeOut();" title="关闭对话框">关闭</a></div>';
                                   
                $("#light").html(close+'<div style="padding-bottom:5px;font-weight:bold;">错误详情：</div><div id="content" class="content"><pre style="color:red;">' + result + '</pre></div>');
                                                 
                $("#light").fadeIn();
                $("#fade").fadeIn();
            }
        });
}

center("#light");
center("#fade");
