define([
"text!infoManageTpl/zyxxbgsp/zyxxbgsp.html",
"text!infoManageTpl/zyxxbgsp/cyqy_zyxxbgsp.html",
"text!infoManageTpl/zyxxbgsp/fwgs_zyxxbgsp.html",
"text!infoManageTpl/zyxxbgsp/tgqy_zyxxbgsp.html",
"text!infoManageTpl/zyxxbgsp/zyxxbgsp_blztxx.html",
"text!infoManageTpl/zyxxbgsp/zyxxbgsp_ywblxx.html",
"text!infoManageTpl/zyxxbgsp/zyxxbgsp_ywblxx_modify.html",
"text!infoManageTpl/zyxxbgsp/xfz_zyxxbgsp_blztxx.html",
"text!infoManageTpl/zyxxbgsp/xfz_zyxxbgsp_ywblxx.html",
"text!infoManageTpl/zyxxbgsp/xfz_zyxxbgsp_ywblxx_modify.html"
],function(zyxxbgspTpl,cyqyTpl,fwgsTpl,tgqyTpl,blztxxTpl,ywblxxTpl,ywblxxMTpl,xfzblztxxTpl,xfzywblxxTpl,xfzywblxxMTpl){
 
	var dTable;
	var config = {
		show:function(){
			
			//加载中间内容 
			$(".operationsArea").html(zyxxbgspTpl).append(cyqyTpl).append(fwgsTpl).append(tgqyTpl).append(blztxxTpl).append(ywblxxTpl).append(ywblxxMTpl).append(xfzblztxxTpl).append(xfzywblxxTpl).append(xfzywblxxMTpl);
			
			$("#iStatues").selectbox({width:100});	
			//点击成员企业重要信息变更审批，显示
			$(".back_cyqy_zyxxbgsp").click(function(){
				$("#cyqy_zyxxbgsp").removeClass("none");
				$("#zyxxbgsp").addClass("none");
				$("#fwgs_zyxxbgsp").addClass("none");
				$("#tgqy_zyxxbgsp").addClass("none");
				$("#xfz_zyxx_ywblxx").addClass("none");
				$("#xfz_zyxx_ywblxx_modify").addClass("none");
				$("#xfz_zyxx_blztxx").addClass("none");
				$("#zyxx_blztxx").addClass("none");
				$("#zyxx_ywblxx").addClass("none");
				$("#zyxx_ywblxx_modify").addClass("none");
				
				$("#iStatues_2").selectbox({width:100});	
			});
			//点击服务公司企业重要信息变更审批，显示
			$(".back_fwgs_zyxxbgsp").click(function(){
				$("#fwgs_zyxxbgsp").removeClass("none");
				$("#zyxxbgsp").addClass("none");
				$("#cyqy_zyxxbgsp").addClass("none");
				$("#tgqy_zyxxbgsp").addClass("none");
				$("#xfz_zyxx_ywblxx").addClass("none");
				$("#xfz_zyxx_ywblxx_modify").addClass("none");
				$("#xfz_zyxx_blztxx").addClass("none");
				$("#zyxx_blztxx").addClass("none");
				$("#zyxx_ywblxx").addClass("none");
				$("#zyxx_ywblxx_modify").addClass("none");
				
				$("#iStatues_3").selectbox({width:100});	
			});
			//点击托管企业重要信息变更审批，显示
			$(".back_tgqy_zyxxbgsp").click(function(){
				$("#tgqy_zyxxbgsp").removeClass("none");
				$("#zyxxbgsp").addClass("none");
				$("#cyqy_zyxxbgsp").addClass("none");
				$("#fwgs_zyxxbgsp").addClass("none");
				$("#xfz_zyxx_ywblxx").addClass("none");
				$("#xfz_zyxx_ywblxx_modify").addClass("none");
				$("#xfz_zyxx_blztxx").addClass("none");
				$("#zyxx_blztxx").addClass("none");
				$("#zyxx_ywblxx").addClass("none");
				$("#zyxx_ywblxx_modify").addClass("none");
				
				$("#iStatues_1").selectbox({width:100});	
			});
			//点击消费者重要信息变更审批，显示
			$(".back_xfz_zyxxbgsp").click(function(){
				$("#zyxxbgsp").removeClass("none");
				$("#cyqy_zyxxbgsp").addClass("none");
				$("#fwgs_zyxxbgsp").addClass("none");
				$("#tgqy_zyxxbgsp").addClass("none");
				$("#xfz_zyxx_ywblxx").addClass("none");
				$("#xfz_zyxx_ywblxx_modify").addClass("none");
				$("#xfz_zyxx_blztxx").addClass("none");
				$("#zyxx_blztxx").addClass("none");
				$("#zyxx_ywblxx").addClass("none");
				$("#zyxx_ywblxx_modify").addClass("none");
			});
			//点击消费者审批，显示业务办理信息
			$(".xfz_sp").click(function(){
				$("#xfz_zyxx_ywblxx").removeClass("none");
				$("#zyxxbgsp").addClass("none");
				$("#cyqy_zyxxbgsp").addClass("none");
				$("#fwgs_zyxxbgsp").addClass("none");
				$("#tgqy_zyxxbgsp").addClass("none");
				$("#xfz_zyxx_ywblxx_modify").addClass("none");
				$("#xfz_zyxx_blztxx").addClass("none");
				$("#zyxx_blztxx").addClass("none");
				$("#zyxx_ywblxx").addClass("none");
				$("#zyxx_ywblxx_modify").addClass("none");
				$(".xfz_zyxx_shtj").removeClass("none");
			});
			//点击消费者查看，显示业务办理信息
			$(".xfz_ck").click(function(){
				$("#xfz_zyxx_ywblxx").removeClass("none");
				$("#zyxxbgsp").addClass("none");
				$("#cyqy_zyxxbgsp").addClass("none");
				$("#fwgs_zyxxbgsp").addClass("none");
				$("#tgqy_zyxxbgsp").addClass("none");
				$("#xfz_zyxx_ywblxx_modify").addClass("none");
				$("#xfz_zyxx_blztxx").addClass("none");
				$("#zyxx_blztxx").addClass("none");
				$("#zyxx_ywblxx").addClass("none");
				$("#zyxx_ywblxx_modify").addClass("none");
				$(".xfz_zyxx_shtj").addClass("none");
			});
			//点击审批，显示业务办理信息
			$(".sp").click(function(){
				$("#zyxx_ywblxx").removeClass("none");
				$("#tgqy_zyxxbgsp").addClass("none");
				$("#cyqy_zyxxbgsp").addClass("none");
				$("#fwgs_zyxxbgsp").addClass("none");
				$("#zyxxbgsp").addClass("none");
				$(".zyxx_shtj").removeClass("none");
				$("#zyxx_ywblxxM").show();
			});
			//点击查看，显示业务办理信息
			$(".ck").click(function(){
				$("#zyxx_ywblxx").removeClass("none");
				$("#tgqy_zyxxbgsp").addClass("none");
				$("#cyqy_zyxxbgsp").addClass("none");
				$("#fwgs_zyxxbgsp").addClass("none");
				$("#zyxxbgsp").addClass("none");
				$(".zyxx_shtj").addClass("none");
				$("#zyxx_ywblxxM").hide();
			});
			//点击办理状态信息,切换业务办理和办理状态（非消费者）
			$(".back_zyxx_blztxx").click(function(){
				$("#zyxx_blztxx").removeClass("none");
				$("#zyxx_ywblxx").addClass("none");
				$("#zyxx_ywblxx_modify").addClass("none");
			});
			//点击业务办理,切换业务办理和办理状态（非消费者）
			$(".back_zyxx_ywblxx").click(function(){
				$("#zyxx_ywblxx").removeClass("none");
				$("#zyxx_blztxx").addClass("none");
			});
			//点击办理状态信息,切换业务办理和办理状态（消费者）
			$(".back_xfz_blztxx").click(function(){
				$("#xfz_zyxx_blztxx").removeClass("none");
				$("#xfz_zyxx_ywblxx").addClass("none");
				$("#xfz_zyxx_ywblxx_modify").addClass("none");
			});
			//点击业务办理信息,切换业务办理和办理状态（消费者）
			$(".back_xfz_ywblxx").click(function(){
				$("#xfz_zyxx_ywblxx").removeClass("none");
				$("#xfz_zyxx_blztxx").addClass("none");
			});
			//点击业务办理信息下的修改按钮，出现修改页面
			$("#zyxx_ywblxxM").click(function(){
				$("#zyxx_ywblxx_modify").removeClass("none");
				$("#zyxx_ywblxx").addClass("none");
				
				$("#certificates_type").selectbox({width:300});
			});
			//点击业务办理信息下的修改按钮，出现修改页面(消费者)
			$("#xfz_zyxx_ywblxxM").click(function(){
				$("#xfz_zyxx_ywblxx_modify").removeClass("none");
				$("#xfz_zyxx_ywblxx").addClass("none");
			});
			//点击业务办理信息下的取消按钮，返回到重要信息变更页面（消费者）
			$("#xfz_zyxx_ywbl_cancel").click(function(){
				$("#zyxxbgsp").removeClass("none");
				$("#xfz_zyxx_ywblxx").addClass("none");
			});
			//点击办理状态信息下的取消，返回重要信息变更页面(消费者）
			$("#xfz_zyxx_cancel").click(function(){
				$("#zyxxbgsp").removeClass("none");
				$("#xfz_zyxx_blztxx").addClass("none");
			});
			//点击业务办理信息下的取消按钮，返回到重要信息变更页面
			$("#zyxx_ywbl_cancel").click(function(){
				$("#tgqy_zyxxbgsp").removeClass("none");
				$("#zyxx_ywblxx").addClass("none");
			});
			//点击办理状态信息下的取消按钮，返回到重要信息变更页面
			$("#zyxx_cancel").click(function(){
				$("#tgqy_zyxxbgsp").removeClass("none");
				$("#zyxx_blztxx").addClass("none");
			});
			//点击修改下面的取消按钮，返回到业务办理页面（消费者）
			$("#xfz_zyxx_modify_cancel").click(function(){
				$("#xfz_zyxx_ywblxx").removeClass("none");
				$("#xfz_zyxx_ywblxx_modify").addClass("none");
			});
			//点击修改下面的取消按钮，返回到业务办理页面
			$("#zyxx_modify_cancel").click(function(){
				$("#zyxx_ywblxx").removeClass("none");
				$("#zyxx_ywblxx_modify").addClass("none");
			});
			//点击修改下的保存按钮，弹出框
			$(".spzyxxbgqr").click(function(){
				$( "#spzyxxbgqr" ).dialog({
					title:"重要信息变更修改确认",
					modal:true,
					width:"800",
					buttons:{ 
						"确定":function(){
							$( this ).dialog( "close" );
						},
						"取消":function(){
							$(this).dialog("close");
						}
					}
				  });
				  
				  $(".fhypwd").focus(function(){
					  $(this).attr("type","password");
					}).blur(function(){
						if(($(this).val() == "输入复核员登录密码") || $(this).val() == " "){
							$(this).attr("type","text");	
						}
					});
			});
			//点击修改下的保存按钮，弹出框（消费者）
			$(".xfz_spzyxxbgqr").click(function(){
				$( "#xfz_spzyxxbgqr" ).dialog({
					title:"重要信息变更修改确认",
					modal:true,
					width:"800",
					buttons:{ 
						"确定":function(){
							$( this ).dialog( "close" );
						},
						"取消":function(){
							$(this).dialog("close");
						}
					}
				  });
				  
				  $(".fhypwd").focus(function(){
					  $(this).attr("type","password");
					}).blur(function(){
						if(($(this).val() == "输入复核员登录密码") || $(this).val() == " "){
							$(this).attr("type","text");	
						}
					});
			});
			//点击办理状态信息中的查看,弹出框（消费者）
			$(".xfz_zyxx_ck").click(function() {
				  $( "#xfz_zyxx_ck" ).dialog({
					title:"重要信息变更申请提交",
					modal:true,
					width:"700"
//					buttons:{ 
//						"确定":function(){
//							$( this ).dialog( "close" );
//						}
//					}
				  });
				});
			//点击办理状态信息中的查看,弹出框（非消费者）
			$(".zyxx_ck").click(function() {
				  $( "#zyxx_ck" ).dialog({
					title:"重要信息变更申请提交",
					modal:true,
					width:"700"
//					buttons:{ 
//						"确定":function(){
//							$( this ).dialog( "close" );
//						}
//					}
				  });
				});
			//点击审核提交按钮
			$(".sbshqr").click(function() {
				  $( "#sbshqr" ).dialog({
					title:"审核确认",
					modal:true,
					width:"600",
					buttons:{ 
						"确定":function(){
							$( this ).dialog( "close" );
						},
						"取消":function(){
							$( this ).dialog( "close" );
						}
					}
				  });
				});
		},
		hide:function(){
			//清空中间内容） 
			$(".operationsArea").empty();	
		},
		dataList:function() {
		}
	};
	return config;
});