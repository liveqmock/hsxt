define(function() {
	
	//角色管理权限设置详单
	$( ".dlg33" ).click(function() {
	  $( ".jqp-dialog" ).dialog( {title:"角色创建成功，是否进行菜单授权？",width:"700"});
	  $( ".jqp-dialog" ).dialog( {buttons: {
		"继续新增角色":function(){
		 $(this).dialog("close");
	  },
		"下次再说":function(){
		 location.href="系统管理-角色管理.html"
	  },
	  "立即设置":function(){
		 location.href="系统管理-角色管理.html"
	  }
      }});
	  $( ".ui-widget-header" ).addClass("fixwidth");
	  $( ".ui-widget-header" ).removeClass("norwidth");
	  $( ".jqp-dialog p" ).html($( "#dlg33" ).html());
	  $( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	$( ".dlg3399" ).click(function() {
	  $( ".jqp-dialog" ).dialog( {title:"角色创建成功",width:"700"});
	  $( ".jqp-dialog" ).dialog( {buttons: {
		"下次再说":function(){
		 location.href="系统管理-角色管理.html"
	  },
	  "立即设置":function(){
		 location.href="系统管理-角色管理.html"
	  }
      }});
	  $( ".ui-widget-header" ).addClass("fixwidth");
	  $( ".ui-widget-header" ).removeClass("norwidth");
	  $( ".jqp-dialog p" ).html($( "#dlg3399" ).html());
	  $( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	//新增/修改用户组确认
	$( ".dlg44" ).click(function() {
	  $( ".jqp-dialog" ).dialog( {title:"提示",width:"500"});
	  $( ".jqp-dialog" ).dialog( {buttons: {
		"完成":function(){
		 location.href="系统管理-用户组管理.html"
	  },
		"继续添加用户组":function(){
		 $(this).dialog("close");
	  },
	  "添加用户组成员":function(){
		 location.href="系统管理-用户组管理-添加维护用户组成员.html"
	  }
      }});
	  $( ".ui-widget-header" ).addClass("norwidth");
	  $( ".jqp-dialog p" ).html($( "#dlg44" ).html());
	  $( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	//用户管理设置角色详单
	$( ".dlg34" ).click(function() {
	  $( ".jqp-dialog" ).dialog( {title:"操作员创建成功，是否进行角色设置？",width:"700"});
	  $( ".jqp-dialog" ).dialog( {buttons: {
		"继续新增角色":function(){
		 $(this).dialog("close");
	  },
		"下次再说":function(){
		 location.href="系统管理-用户管理.html"
	  },
	  "立即设置":function(){
		 location.href="系统管理-用户管理.html"
	  }
      }});
	  $( ".ui-widget-header" ).addClass("fixwidth");
	  $( ".ui-widget-header" ).removeClass("norwidth");
	  $( ".jqp-dialog p" ).html($( "#dlg34" ).html());
	  $( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	//选择用户组确认
	$( ".sltusergrounp" ).click(function() {
	  $( ".jqp-dialog" ).dialog( {title:"选择用户组",width:"400"});
	  $( ".jqp-dialog" ).dialog( {buttons: {
		"确定":function(){
		 $(this).dialog("close")
	  },
	  "取消":function(){
		 $(this).dialog("close")
	  }
      }});
	  $( ".ui-widget-header" ).addClass("norwidth");
	  $( ".jqp-dialog p" ).html($( "#sltusergrounp" ).html());
	  $( ".jqp-dialog" ).dialog( "open" ); 
    });
	$( ".sltusergrounp1" ).click(function() {
	  $( ".jqp-dialog" ).dialog( {title:"选择用户组",width:"400"});
	  $( ".jqp-dialog" ).dialog( {buttons: {
		"确定":function(){
		 $(this).dialog("close")
	  },
	  "取消":function(){
		 $(this).dialog("close")
	  }
      }});
	  $( ".ui-widget-header" ).addClass("norwidth");
	  $( ".jqp-dialog p" ).html($( "#sltusergrounp1" ).html());
	  $( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	//删除操作员确认
	$( ".scczyqr" ).click(function() {
	  $( ".jqp-dialog" ).dialog( {title:"提示",width:"400"});
	  $( ".jqp-dialog" ).dialog( {buttons: {
		"确定":function(){
		 $(this).dialog("close");
	  },
	  "取消":function(){
		 $(this).dialog("close");
	  }
      }});
	  $( ".ui-widget-header" ).addClass("norwidth");
	  $( ".jqp-dialog p" ).html($( "#scczyqr" ).html());
	  $( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	//删除角色确认
	$( ".scjsqr" ).click(function() {
	  $( ".jqp-dialog" ).dialog( {title:"提示",width:"400"});
	  $( ".jqp-dialog" ).dialog( {buttons: {
		"确定":function(){
		 $(this).dialog("close");
	  },
	  "取消":function(){
		 $(this).dialog("close");
	  }
      }});
	  $( ".ui-widget-header" ).addClass("norwidth");
	  $( ".jqp-dialog p" ).html($( "#scjsqr" ).html());
	  $( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	//绑定互生卡确认详单
	$( ".dlg35" ).click(function() {
	  $( ".jqp-dialog" ).dialog( {title:"互生卡登录设置成功！",width:"400"});
	  $( ".jqp-dialog" ).dialog( {buttons: {
		"确定":function(){
		 location.href="系统管理-用户管理.html"
	  }
      }});
	  $( ".ui-widget-header" ).addClass("norwidth");
	  $( ".jqp-dialog p" ).html($( "#dlg35" ).html());
	  $( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	//解绑互生卡确认详单
	$( ".dlg36" ).click(function() {
	  $( ".jqp-dialog" ).dialog( {title:"互生卡登录解绑成功！",width:"400"});
	  $( ".jqp-dialog" ).dialog( {buttons: {
        
		"确定":function(){
		 location.href="系统管理-用户管理.html"
	  }
      }});
	  $( ".ui-widget-header" ).addClass("norwidth");
	  $( ".jqp-dialog p" ).html($( "#dlg36" ).html());
	  $( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	//删除用户组确认
	$( ".scyhzqr" ).click(function() {
	  $( ".jqp-dialog" ).dialog( {title:"提示",width:"400"});
	  $( ".jqp-dialog" ).dialog( {buttons: {
		"确定":function(){
		 $(this).dialog("close");
	  },
	  "取消":function(){
		 $(this).dialog("close");
	  }
      }});
	  $( ".ui-widget-header" ).addClass("norwidth");
	  $( ".jqp-dialog p" ).html($( "#scyhzqr" ).html());
	  $( ".jqp-dialog" ).dialog( "open" ); 
    });
	
	//添加维护组员
	function tjjsqr(){
		$( ".jqp-dialog" ).dialog( {title:"提示",width:"400"});
		$( ".jqp-dialog" ).dialog( {buttons: {
		  "确定":function(){
		   $(this).dialog("close");
		},
		"取消":function(){
		   $(this).dialog("close");
		}
		}});
		$( ".ui-widget-header" ).addClass("norwidth");
		$( ".jqp-dialog p" ).html($( "#tjjs1" ).html());
		$( ".jqp-dialog" ).dialog( "open" ); 
	}
	$( ".tjjs" ).click(function() {
	  $( ".jqp-dialog" ).dialog( {title:"提示",width:"600"});
	  $( ".jqp-dialog" ).dialog( {buttons: {
		"确定":function(){
		  tjjsqr();
	  },
	  "取消":function(){
		 $(this).dialog("close");
	  }
      }});
	  $( ".ui-widget-header" ).addClass("norwidth");
	  $( ".jqp-dialog p" ).html($( "#tjjs" ).html());
	  $( ".jqp-dialog" ).dialog( "open" ); 
    });
	$( ".scjs" ).click(function() {
	  $( ".jqp-dialog" ).dialog( {title:"提示",width:"400"});
	  $( ".jqp-dialog" ).dialog( {buttons: {
		"确定":function(){
		 $(this).dialog("close");
	  },
	  "取消":function(){
		 $(this).dialog("close");
	  }
      }});
	  $( ".ui-widget-header" ).addClass("norwidth");
	  $( ".jqp-dialog p" ).html($( "#scjs" ).html());
	  $( ".jqp-dialog" ).dialog( "open" ); 
    });
  });