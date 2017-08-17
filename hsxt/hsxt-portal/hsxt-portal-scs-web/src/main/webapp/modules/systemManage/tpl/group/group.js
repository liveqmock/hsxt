define([
"text!systemManageTpl/group/group.html",
"text!systemManageTpl/group/modGroup.html",
"text!systemManageTpl/group/modMember.html",
 
],function(groupTpl,modGroupTpl,modMemberTpl){
 
	var group = {
		show:function(){
			//加载中间内容 
			$(".operationsArea").html(groupTpl).append(modGroupTpl).append(modMemberTpl);	
			
			function allHide(){
				$("#userGroup,#modUserGroup,#modGroupMember").addClass("none");
			};
			
			$(".a_modGroup").click(function(){
				allHide();
				$("#modUserGroup").remove("none");
				$(".a_group_back").click(function(){
					allHide();
					$("#userGroup").remove("none");
				});	
				//弹出框
				$( ".a_modGroup_sure" ).click(function() { 
					  $( "#modGroupTip" ).dialog({
						title:"提示",
						width:"500",
						modal:true,
						buttons:{ 
							"完成":function(){
								$( this ).dialog( "destroy" );
							},
							"继续添加用户组":function(){
								$( this ).dialog( "destroy" );
							},
							"添加用户组成员":function(){
								$( this ).dialog( "destroy" );
							},
						}
					});
				});
			});
			$(".a_group_back").click(function(){
				allHide();
				$("#userGroup").remove("none");
			});	
			$(".a_modMember").click(function(){
				allHide();
				$("#modGroupMember").remove("none");
				$(".a_group_back").click(function(){
					allHide();
					$("#userGroup").remove("none");
				});
				function GroupMemberAddSure(){
					$( "#modGroupMemberAddSure" ).dialog({
					  title:"提示",
					  width:"400",
					  modal:true,
					  buttons:{ 
						  "确定":function(){
							  $( this ).dialog( "destroy" );
						  },
						  "取消":function(){
							  $( this ).dialog( "destroy" );
						  }
					  }
				  });
				};
				//弹出框
				$( ".a_modGroupMember_add" ).click(function() { 
					  $( "#modGroupMemberAdd" ).dialog({
						title:"提示",
						width:"600",
						modal:true,
						buttons:{ 
							"确定":function(){
								GroupMemberAddSure();
								$( this ).dialog( "destroy" );
							},
							"取消":function(){
								$( this ).dialog( "destroy" );
							}
						}
					});
				});
				$( ".a_modGroupMember_del" ).click(function() { 
					  $( "#modGroupMemberDel" ).dialog({
						title:"提示",
						width:"400",
						modal:true,
						buttons:{ 
							"确定":function(){
								$( this ).dialog( "destroy" );
							},
							"取消":function(){
								$( this ).dialog( "destroy" );
							}
						}
					});
				});
			});	
			//弹出框
			$( ".a_group_del" ).click(function() { 
				  $( "#groupDel" ).dialog({
					title:"提示",
					width:"500",
					modal:true,
					buttons:{ 
						"确定":function(){
							$( this ).dialog( "destroy" );
						},
						"取消":function(){
							$( this ).dialog( "destroy" );
						}
					}
				});
			});
			
			
			
			
			
		},
		
		hide:function(){
			//清空中间内容） 
			$(".operationsArea").empty();	
		}
		
	};
	
		
	return group;
	 

});