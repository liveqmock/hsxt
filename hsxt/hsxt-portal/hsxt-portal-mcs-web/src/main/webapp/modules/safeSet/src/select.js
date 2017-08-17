define(function() {

//自定义下拉列表样式
	$("#sctQue").selectbox({width:190});
	$("#sctQue111").selectbox({width:190});
//交易密码重置确认
	$( ".jymmczqr" ).click(function() {
	  $( ".jqp-dialog" ).dialog( {title:"提示",width:"550"});
	  $( ".jqp-dialog" ).dialog( {buttons: {
		"确定":function(){
		 location.href="安全设置-交易密码重置.html";
	  }
      }});
	  $( ".ui-widget-header" ).addClass("norwidth");
	  $( ".jqp-dialog p" ).html($( "#jymmczqr" ).html());
	  $( ".jqp-dialog" ).dialog( "open" ); 
    });
});

