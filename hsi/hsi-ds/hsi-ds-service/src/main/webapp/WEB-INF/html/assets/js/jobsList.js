
(function ($) {	
   getSession();
   
   fetchJobList();
   
   bindInputKepress(['#triggerName','#triggerGroup']);

   $("#query_submit").on("click", function (e) {
    	fetchJobList();
   });
    
 //
 // 渲染主列表
 //
 function fetchJobList() {
 	
     var triggerName = $("#triggerName").val();
     var triggerGroup = $("#triggerGroup").val();
     var triggerState = $("#triggerState").val();
     
     if(triggerName) {
    	 triggerName = triggerName.replace(/,$/, "");
     }
     
     $.ajax({
         type: "GET",
         url: "api/trigger/list",
         data: {
             "triggerName": triggerName,
             "triggerGroup": triggerGroup,
             "triggerState": triggerState
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
             
             bindDetailEvent(result);
         } else {
             $("#accountBody").html("");
         }

     });
     
     var mainTpl = $("#tbodyTpl").html();
     
     // 渲染主列表
     function renderItem(item, i) {

        var del_link = '<a id="itemDel' + item.randomId
             + '" style="cursor: pointer; cursor: hand; " ><i title="删除" class="icon-remove"></i></a>';
         
        var modify_link = '<a target="_self" href="modifyJob.html?triggerName='
             + item.triggerName + '&triggerGroup='+item.triggerGroup
             + '"><i title="修改" class="icon-edit"></i></a>';
         
        var pause_link = '<a id="itemPause' + item.randomId
             + '" style="cursor: pointer; cursor: hand; " ><i title="暂停" class="icon-off"></i></a>';
         
        var resume_link = '';
         
     	var state = item.triggerState;
                 	
        if("执行中" == state) {
     		state = "<font style='BACKGROUND-COLOR:#33FF33;' style='font-weight:bold;'>"+ state +"</font>";
     	} else if("等待中" == state) {
     		state = "<font style='BACKGROUND-COLOR:#FFFFCC;'>"+ state +"</font>";
     	} else if("暂停中" == state) {
     		state = "<font style='BACKGROUND-COLOR:#F3F3F3;' color='black'>"+ state +"</font>";
     		
     		pause_link = '';
     		resume_link = '<a id="itemResume' + item.randomId
                + '" style="cursor: pointer; cursor: hand; " ><i title="恢复" class="icon-ok"></i></a>';
     	} else if("阻塞中" == state) {
     		state = "<font style='BACKGROUND-COLOR:#FFCC00;'>"+ state +"</font>";
     	} else if("错误" == state) {
     		state = "<font style='BACKGROUND-COLOR:#FFCAA6;'>"+ state +"</font>";
     	}
        
        return Util.string.format(mainTpl,'', i + 1,item.triggerGroup,
             item.triggerName, item.desc, state, item.nextFireTime, 
             item.prevFireTime, item.startTime,item.endTime, modify_link,
             del_link, pause_link, resume_link,item.cron);
     }
 }

 // 详细列表绑定事件
 function bindDetailEvent(result) {
     if (result == null) {
         return;
     }
     $.each(result, function (index, item) {
    	 var randomId = item.randomId;// 避免中文问题
         var id = item.triggerName;
         var group=item.triggerGroup;

         // 绑定删除事件
         $("#itemDel" + randomId).on("click", function (e) {
             deleteTrigger(id, group);
         });
         
         // 绑定暂停事件
         $("#itemPause" + randomId).on("click", function (e) {
             pauseTrigger(id, group);
         });
         
         // 绑定恢复事件
         $("#itemResume" + randomId).on("click", function (e) {
             resumeTrigger(id, group);
         });

     });
 }

 // 删除
 function deleteTrigger(triggerName, triggerGroup) {
     var ret = window.confirm("你确定要删除任务‘" + triggerName + "’吗 ?");
     if (!ret) {
         return false;
     }

     $.ajax({
         type: "POST",
         url: "api/trigger/delete",
         data: {
                "triggerName": triggerName,
                "triggerGroup": triggerGroup
                }
     }).done(function (data) {
         if (data.success === "true") {
         	fetchJobList();
         } else {
             Util.input.whiteError($("#error"), data);
         }
     });
 }

 //暂停
 function pauseTrigger(triggerName, triggerGroup) {

     var ret = window.confirm("你确定要暂停任务‘" + triggerName+  "’吗 ?");
     if (!ret) {
         return false;
     }

     $.ajax({
         type: "POST",
         dataType:"json",
         url: "api/trigger/pause",
         data: {
                "triggerName": triggerName,
                "triggerGroup": triggerGroup
                }
     }).done(function (data) {
         if (data.success === "true") {
         	fetchJobList();
         } else {
             Util.input.whiteError($("#error"), data);
         }
     });
 }

 //恢复
 function resumeTrigger(triggerName, triggerGroup) {

     var ret = window.confirm("你确定要恢复任务‘" + triggerName + "’吗 ?");
     
     if (!ret) {
         return false;
     }

     $.ajax({
         type: "POST",
         dataType:"json",
         url: "api/trigger/resume",
         data: {
                "triggerName": triggerName,
                "triggerGroup": triggerGroup
               }
     }).done(function (data) {
         if (data.success === "true") {
         	fetchJobList();
         } else {
             Util.input.whiteError($("#error"), data);
         }
     });
 }
 
 //
 //绑定输入框的回车事件
 //
 function bindInputKepress(inputFieldIds) {		
	 $.each(inputFieldIds, function (index, inputFieldId) {    	    	
	 	// 绑定回车事件
	 	$(inputFieldId).bind('keypress', function(event){
	 		if(13 == event.keyCode){ 
	 			fetchJobList();
	 		}
	 	})
	 });
  }

})(jQuery);