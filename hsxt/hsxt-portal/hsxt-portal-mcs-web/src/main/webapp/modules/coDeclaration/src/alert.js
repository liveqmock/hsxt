define(function() {

//审批备注弹出框
$( ".spxxqr" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"备注详情",width:"400"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#spxxqr" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
});

//申报审批系统登记信息弹出框
$( ".sbxtdjxxxg" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"系统注册信息修改确认",width:"600"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 location.href="企业申报-托管企业申报内审-系统登记信息.html"
	  },
		"取消":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#sbxtdjxxxg" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
});

//申报地区平台审批系统登记信息弹出框
$( ".sbdqxtdjxxxg" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"系统注册信息修改确认",width:"800"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 location.href="企业申报-地区平台-托管企业申报内审-系统登记信息.html"
	  },
		"取消":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#sbdqxtdjxxxg" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
});

//申报审批工商登记信息弹出框
$( ".sbgsdjxxxg" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"工商登记信息修改确认",width:"800"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 location.href="企业申报-托管企业申报内审-工商登记信息.html"
	  },
		"取消":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#sbgsdjxxxg" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
});

//申报审批企业联系信息弹出框
$( ".sbqylxxxxg" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"企业联系信息修改确认",width:"800"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 location.href="企业申报-托管企业申报内审-企业联系信息.html"
	  },
		"取消":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#sbqylxxxxg" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
});

//申报审批银行账户信息弹出框
$( ".sbyhzhxxxg" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"银行账户信息修改确认",width:"400"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 location.href="企业申报-托管企业申报内审-银行账户信息.html"
	  },
		"取消":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#sbyhzhxxxg" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
});

//申报审批企业上传资料弹出框
$( ".sbqysczlxg" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"企业上传资料修改确认",width:"1000"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 location.href="企业申报-托管企业申报内审-企业上传资料.html"
	  },
		"取消":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#sbqysczlxg" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
});

//审批重要信息变更弹出框
$( ".spzyxxbgqr" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"重要信息变更修改确认",width:"1000"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 location.href="业务办理-重要信息变更审批-业务办理信息.html"
	  },
		"取消":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#spzyxxbgqr" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
});

//审批提交确认弹出框
$( ".sbshqr" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"审核确认",width:"400"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 location.href="企业申报-托管企业申报内审.html"
	  },
		"取消":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#sbshqr" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
});

//验证手机弹出框
$( ".dlgPhone" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"联系人手机验证",width:"400"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 location.href="企业申报-企业联系信息-已验证.html"
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#dlgPhone" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
});


//申报修改手机验证弹出框
function mtips(){
	$( ".jqp-dialog" ).dialog( {title:"联系人手机验证",width:"400"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#dlgPhone" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
}
$( ".dlgMobile" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"联系人手机验证",width:"400"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
			mtips();
	  },
		"取消":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#dlgMobile" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
});

//验证邮箱弹出框
$( ".dlgEmail" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"企业安全邮箱验证",width:"400"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#dlgEmail" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
});

//意向客户详情弹出框
$( ".sbyxkhxd" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"意向客户详情",width:"800"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#sbyxkhxd" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
});

//修改意向客户状态弹出框
$( ".sbyxkhtj" ).click(function() {
	$( ".jqp-dialog" ).dialog( {title:"意向客户详情",width:"800"});
	$( ".jqp-dialog" ).dialog( {buttons: { 
		"确定":function(){
		 $( this ).dialog( "close" )
	  }
      }});
	$( ".ui-widget-header" ).addClass("norwidth");
	$( ".jqp-dialog p" ).html($( "#sbyxkhtj" ).html());
	$( ".jqp-dialog" ).dialog( "open" ); 
});

});

