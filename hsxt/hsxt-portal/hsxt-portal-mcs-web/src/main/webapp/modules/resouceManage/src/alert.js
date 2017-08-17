define(function() {
	
	//城市资源详情
	$( ".cszylb" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"城市资源状态详情",width:"600"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#cszylb" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	
	//企业报停详单
	$( ".qyzyztck4" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"企业报停详情",width:"600"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#qyzyztck2" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	$( ".qyzyztck5" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"企业报停详情",width:"600"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#qyzyztck3" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	//企业报停详单
	$( ".qyxmbt" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"企业报停",width:"600"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#qyxmbt" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	//企业资源状态详单
	$( ".qyzyztck1" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"企业资源状态详情",width:"600"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#qyzyztck1" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	$( ".qyzyztck2" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"企业资源状态详情",width:"600"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 $( this ).dialog( "close" )
	  },
	  	"报停":function(){
		 rStop();
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#qyzyztck2" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	$( ".qyzyztck3" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"企业资源状态详情",width:"600"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 $( this ).dialog( "close" )
	  },
	  	"报停":function(){
		 rStop();
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#qyzyztck3" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	//企业资源状态查询后报停详单
	function rStop() {
	$( ".jqp-dialog" ).dialog( {title:"企业报停",width:"600"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#qyzyztbt1" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
    };
	
	
	//托管企业停止积分活动查看详单
	$( ".qyxmck2" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"托管企业停止积分活动申请详情",width:"600"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#qyxmck2" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	//成员企业停止积分活动查看详单
	$( ".qyxmck3" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"成员企业停止积分活动申请详情",width:"600"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#qyxmck2" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	//托管企业停止积分活动审批详单
	$( ".qyxmsp2" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"托管企业停止积分活动申请审批详情",width:"600"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"审批通过":function(){
		 $( this ).dialog( "close" )
	  },
	  "审批驳回":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#qyxmsp2" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	//成员企业停止积分活动驳回确认详单
	$( ".qybh" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"成员企业停止积分活动申请驳回详情",width:"400"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"驳回":function(){
		 $( this ).dialog( "close" )
	  },
	  "取消":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#qybh" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	//托管企业参与积分活动查看详单
	$( ".qyxmck4" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"托管企业参与积分活动申请详情",width:"600"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#qyxmck4" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	//托管企业参与积分活动审批详单
	$( ".qyxmsp4" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"托管企业参与积分活动申请审批详情",width:"600"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"审批通过":function(){
		 $( this ).dialog( "close" )
	  },
	  "审批驳回":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#qyxmsp4" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
    });
	
});