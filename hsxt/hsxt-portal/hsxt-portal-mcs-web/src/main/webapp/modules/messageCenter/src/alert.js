define(function() {
	
	//添加接收单位
	$( ".addmsg" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"选择接收单位",width:"400"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 $( this ).dialog( "close" )
	  },
	  "取消":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#addmsg" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
    });
	//$("#msgType").change(function(e){
//				var currentValue = $(e.currentTarget).val();
//				var sub_msgType = $("#sub_msgType");
//				var receivingUnit_1 = $("#receivingUnit_1");
//				var receivingUnit_2 = $("#receivingUnit_2");
//				if(currentValue == 1){
//					sub_msgType.removeClass("none");
//					receivingUnit_1.removeClass("none");	
//					receivingUnit_2.addClass("none");
//				}
//				else if(currentValue == 2){
//					sub_msgType.addClass("none");
//					receivingUnit_2.removeClass("none");
//					receivingUnit_1.addClass("none");	
//				}
//			});
           //消息类型单选框事件
			$("input[name = msgType]").click(function(e){
				var currentValue = $(e.currentTarget).val();
				var receivingUnit_1 = $("#receivingUnit_1");
				var receivingUnit_2 = $("#receivingUnit_2");
				if(currentValue == 1){
					receivingUnit_1.removeClass("none");	
					receivingUnit_2.addClass("none");
				}
				else if(currentValue == 2){
					receivingUnit_2.removeClass("none");
					receivingUnit_1.addClass("none");	
				}
			});
});