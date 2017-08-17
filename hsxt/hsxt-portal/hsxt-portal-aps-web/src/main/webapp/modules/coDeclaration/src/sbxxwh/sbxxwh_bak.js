define([
/*"text!coDeclarationTpl/sbxxwh/sbxxwh.html",*/
"text!coDeclarationTpl/sbxxwh/xtdjxx.html",
"text!coDeclarationTpl/sbxxwh/gsdjxx.html",
"text!coDeclarationTpl/sbxxwh/lxxx.html",
"text!coDeclarationTpl/sbxxwh/yhzhxx.html",
"text!coDeclarationTpl/sbxxwh/qyxtzcxx.html",
"text!coDeclarationTpl/sbxxwh/qyxtzcxx_xg.html",
"text!coDeclarationTpl/sbxxwh/qygsdjxx.html",
"text!coDeclarationTpl/sbxxwh/qygsdjxx_xg.html",
"text!coDeclarationTpl/sbxxwh/qylxxx.html",
"text!coDeclarationTpl/sbxxwh/qylxxx_xg.html",
"text!coDeclarationTpl/sbxxwh/qyyhzhxx.html",
"text!coDeclarationTpl/sbxxwh/qyyhzhxx_xg.html",
"text!coDeclarationTpl/sbxxwh/qysczl.html",
"text!coDeclarationTpl/sbxxwh/qysczl_xg.html",
"text!coDeclarationTpl/sbxxwh/glqysbzzdwh.html",
"text!coDeclarationTpl/sbxxwh/glqysbzzdwh_xg.html",
"text!coDeclarationTpl/sbxxwh/glqysbzzdwh_xg_2.html",
"text!coDeclarationTpl/sbxxwh/glqysbzzdwh_xg_3.html"
],function(/*sbxxwhTpl,*/xtdjxxTpl,gsdjxxTpl,lxxxTpl,yhzhxxTpl,qyxtzcxxTpl,qyxtzcxx_xgTpl,qygsdjxxTpl,qygsdjxx_xgTpl,qylxxxTpl,qylxxx_xgTpl,qyyhzhxxTpl,qyyhzhxx_xgTpl,qysczlTpl,qysczl_xgTpl,glqysbzzdwhTpl,glqysbzzdwh_xgTpl,glqysbzzdwh_xg_2Tpl,glqysbzzdwh_xg_3Tpl){
 
	var sbxxwh = {
		show:function(){
			//加载中间内容 
			$(".operationsArea")/*.html(sbxxwhTpl)*/.html(glqysbzzdwhTpl).append(xtdjxxTpl).append(gsdjxxTpl).append(lxxxTpl).append(yhzhxxTpl).append(qyxtzcxxTpl).append(qyxtzcxx_xgTpl).append(qygsdjxxTpl).append(qygsdjxx_xgTpl).append(qylxxxTpl).append(qylxxx_xgTpl).append(qyyhzhxxTpl).append(qyyhzhxx_xgTpl).append(qysczlTpl).append(qysczl_xgTpl).append(glqysbzzdwh_xgTpl).append(glqysbzzdwh_xg_2Tpl).append(glqysbzzdwh_xg_3Tpl);
			
			$("#example").DataTable({
				"scrollY":        "300px",
				"scrollCollapse": true,
				//"bJQueryUI" : true,
				"bFilter" : false,
				"sPaginationType" : "full_numbers",
				"sDom" : '<""l>t<"F"fp>',
				"processing" : true,
				//"serverSide" : true,
				//"bAutoWidth" : false, // 自适应宽度
				"iDisplayLength":10,//每页显示10条数据
				"bLengthChange" : false,
				"bSort":false,
				'bStateSave' : true,
				"oLanguage" : {
					// "sLengthMenu" : "每页显示 _MENU_条",
					"sZeroRecords" : "没有找到符合条件的数据",
					"sInfo" : "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
					"sInfoEmpty" : "没有记录",
					"sInfoFiltered" : "(从 _MAX_ 条记录中过滤)",
					"sSearch" : "搜索：",
					"oPaginate" : {
						"sFirst" : "首页",
						"sPrevious" : "上一页",
						"sNext" : "下一页",
						"sLast" : "尾页"
					}
			}
			});
			
			function submitDialog(e){
				var currentID = $(e.currentTarget).attr("id");
				if(currentID){
					var IDNameArr = new Array();
					IDNameArr = currentID.split("-");
					var currentIDName = IDNameArr[0];
					var currentContent = IDNameArr[0] + "-submitContent";
				
					$( "#" + currentContent ).dialog({
						title:"系统注册信息修改确认",
						modal:true,
						width:"800",
						buttons:{ 
							"确定":function(){
								$( "#" + currentContent ).dialog( "close" );
							},
							"取消":function(){
								 $( "#" + currentContent ).dialog( "close" );
							}
						}
				
					  });	
				}
			}		
			
			function verificationMobileDialog(e){
				var currentID = $(e.currentTarget).attr("id");
				if(currentID){
					var IDNameArr = new Array();
					IDNameArr = currentID.split("-");
					var currentIDName = IDNameArr[0];
					var currentContent = IDNameArr[0] + "-content";
				
					$( "#" + currentContent ).dialog({
						title:"联系人手机验证",
						modal:true,
						width:"400",
						buttons:{ 
							"确定":function(){
								$( "#" + currentContent ).dialog( "close" );
							},
							"取消":function(){
								 $( "#" + currentContent ).dialog( "close" );
							}
						}
				
					  });	
				}
			}	
	
			function verificationEmailDialog(e){
				var currentID = $(e.currentTarget).attr("id");
				if(currentID){
					var IDNameArr = new Array();
					IDNameArr = currentID.split("-");
					var currentIDName = IDNameArr[0];
					var currentContent = IDNameArr[0] + "-content";
				
					$( "#" + currentContent ).dialog({
						title:"企业安全邮箱验证",
						modal:true,
						width:"400",
						buttons:{ 
							"确定":function(){
								$( "#" + currentContent ).dialog( "close" );
							},
							"取消":function(){
								 $( "#" + currentContent ).dialog( "close" );
							}
						}
				
					  });	
				}
			}
			
			$("#glqysbzzdwh_status").selectbox({width:120});
			
			$(".companyInfoView_btn").click(function(){
				/*$("#sbxxwh").addClass("none");*/
				$("#gsdjxx").addClass("none");
				$("#lxxx").addClass("none");
				$("#yhzhxx").addClass("none");
				$("#qyxtzcxx").addClass("none");
				$("#qyxtzcxx_xg").addClass("none");
				$("#qygsdjxx").addClass("none");
				$("#qygsdjxx_xg").addClass("none");
				$("#qylxxx").addClass("none");
				$("#qylxxx_xg").addClass("none");
				$("#qyyhzhxx").addClass("none");
				$("#qyyhzhxx_xg").addClass("none");
				$("#qysczl").addClass("none");
				$("#qysczl_xg").addClass("none");
				$("#glqysbzzdwh").addClass("none");
				$("#glqysbzzdwh_xg").addClass("none");
				$("#glqysbzzdwh_xg_2").addClass("none");
				$("#glqysbzzdwh_xg_3").addClass("none");
				
				$("#xtdjxx").removeClass("none");
			});
			
			/*$(".back_glqysbxxwh").click(function(){
				$("#xtdjxx").addClass("none");
				$("#gsdjxx").addClass("none");
				$("#lxxx").addClass("none");
				$("#yhzhxx").addClass("none");
				$("#qyxtzcxx").addClass("none");
				$("#qyxtzcxx_xg").addClass("none");
				$("#qygsdjxx").addClass("none");
				$("#qygsdjxx_xg").addClass("none");
				$("#qylxxx").addClass("none");
				$("#qylxxx_xg").addClass("none");
				$("#qyyhzhxx").addClass("none");
				$("#qyyhzhxx_xg").addClass("none");
				$("#qysczl").addClass("none");
				$("#qysczl_xg").addClass("none");
				$("#glqysbzzdwh").addClass("none");
				$("#glqysbzzdwh_xg").addClass("none");
				$("#glqysbzzdwh_xg_2").addClass("none");
				$("#glqysbzzdwh_xg_3").addClass("none");
				
				$("#sbxxwh").removeClass("none");
			});*/
			
			$(".back_gsdjxx").click(function(){
				$("#xtdjxx").addClass("none");
				/*$("#sbxxwh").addClass("none");*/
				$("#lxxx").addClass("none");
				$("#yhzhxx").addClass("none");
				$("#qyxtzcxx").addClass("none");
				$("#qyxtzcxx_xg").addClass("none");
				$("#qygsdjxx").addClass("none");
				$("#qygsdjxx_xg").addClass("none");
				$("#qylxxx").addClass("none");
				$("#qylxxx_xg").addClass("none");
				$("#qyyhzhxx").addClass("none");
				$("#qyyhzhxx_xg").addClass("none");
				$("#qysczl").addClass("none");
				$("#qysczl_xg").addClass("none");
				$("#glqysbzzdwh").addClass("none");
				$("#glqysbzzdwh_xg").addClass("none");
				$("#glqysbzzdwh_xg_2").addClass("none");
				$("#glqysbzzdwh_xg_3").addClass("none");
				
				$("#gsdjxx").removeClass("none");
			});
			
			$(".back_lxxx").click(function(){
				$("#xtdjxx").addClass("none");
				/*$("#sbxxwh").addClass("none");*/
				$("#gsdjxx").addClass("none");
				$("#yhzhxx").addClass("none");
				$("#qyxtzcxx").addClass("none");
				$("#qyxtzcxx_xg").addClass("none");
				$("#qygsdjxx").addClass("none");
				$("#qygsdjxx_xg").addClass("none");
				$("#qylxxx").addClass("none");
				$("#qylxxx_xg").addClass("none");
				$("#qyyhzhxx").addClass("none");
				$("#qyyhzhxx_xg").addClass("none");
				$("#qysczl").addClass("none");
				$("#qysczl_xg").addClass("none");
				$("#glqysbzzdwh").addClass("none");
				$("#glqysbzzdwh_xg").addClass("none");
				$("#glqysbzzdwh_xg_2").addClass("none");
				$("#glqysbzzdwh_xg_3").addClass("none");
				
				$("#lxxx").removeClass("none");
			});
			
			$(".back_yhzhxx").click(function(){
				$("#xtdjxx").addClass("none");
				/*$("#sbxxwh").addClass("none");*/
				$("#gsdjxx").addClass("none");
				$("#lxxx").addClass("none");
				$("#qyxtzcxx").addClass("none");
				$("#qyxtzcxx_xg").addClass("none");
				$("#qygsdjxx").addClass("none");
				$("#qygsdjxx_xg").addClass("none");
				$("#qylxxx").addClass("none");
				$("#qylxxx_xg").addClass("none");
				$("#qyyhzhxx").addClass("none");
				$("#qyyhzhxx_xg").addClass("none");
				$("#qysczl").addClass("none");
				$("#qysczl_xg").addClass("none");
				$("#glqysbzzdwh").addClass("none");
				$("#glqysbzzdwh_xg").addClass("none");
				$("#glqysbzzdwh_xg_2").addClass("none");
				$("#glqysbzzdwh_xg_3").addClass("none");
				
				$("#yhzhxx").removeClass("none");
			});
			
			$(".infoModify_btn").click(function(){
				$("#xtdjxx").addClass("none");
				/*$("#sbxxwh").addClass("none");*/
				$("#gsdjxx").addClass("none");
				$("#lxxx").addClass("none");
				$("#yhzhxx").addClass("none");
				$("#qyxtzcxx_xg").addClass("none");
				$("#qygsdjxx").addClass("none");
				$("#qygsdjxx_xg").addClass("none");
				$("#qylxxx").addClass("none");
				$("#qylxxx_xg").addClass("none");
				$("#qyyhzhxx").addClass("none");
				$("#qyyhzhxx_xg").addClass("none");
				$("#qysczl").addClass("none");
				$("#qysczl_xg").addClass("none");
				$("#glqysbzzdwh").addClass("none");
				$("#glqysbzzdwh_xg").addClass("none");
				$("#glqysbzzdwh_xg_2").addClass("none");
				$("#glqysbzzdwh_xg_3").addClass("none");
				
				$("#qyxtzcxx").removeClass("none");
			});
			
			$("#modify_qyxtzcxx_btn").click(function(){
				$("#xtdjxx").addClass("none");
				/*$("#sbxxwh").addClass("none");*/
				$("#gsdjxx").addClass("none");
				$("#lxxx").addClass("none");
				$("#yhzhxx").addClass("none");
				$("#qyxtzcxx").addClass("none");
				$("#qygsdjxx").addClass("none");
				$("#qygsdjxx_xg").addClass("none");
				$("#qylxxx").addClass("none");
				$("#qylxxx_xg").addClass("none");
				$("#qyyhzhxx").addClass("none");
				$("#qyyhzhxx_xg").addClass("none");
				$("#qysczl").addClass("none");
				$("#qysczl_xg").addClass("none");
				$("#glqysbzzdwh").addClass("none");
				$("#glqysbzzdwh_xg").addClass("none");
				$("#glqysbzzdwh_xg_2").addClass("none");
				$("#glqysbzzdwh_xg_3").addClass("none");
				
				$("#qyxtzcxx_xg").removeClass("none");
				
				$("#qyxtzcxx_xg-submitBtn").click(function(e){
					submitDialog(e);
				});
			});
			
			$(".back_qygsdjxx").click(function(){
				$("#xtdjxx").addClass("none");
				/*$("#sbxxwh").addClass("none");*/
				$("#gsdjxx").addClass("none");
				$("#lxxx").addClass("none");
				$("#yhzhxx").addClass("none");
				$("#qyxtzcxx").addClass("none");
				$("#qyxtzcxx_xg").addClass("none");
				$("#qygsdjxx_xg").addClass("none");
				$("#qylxxx").addClass("none");
				$("#qylxxx_xg").addClass("none");
				$("#qyyhzhxx").addClass("none");
				$("#qyyhzhxx_xg").addClass("none");
				$("#qysczl").addClass("none");
				$("#qysczl_xg").addClass("none");
				$("#glqysbzzdwh").addClass("none");
				$("#glqysbzzdwh_xg").addClass("none");
				$("#glqysbzzdwh_xg_2").addClass("none");
				$("#glqysbzzdwh_xg_3").addClass("none");
				
				$("#qygsdjxx").removeClass("none");
			});
			
			$("#modify_qygsdjxx_btn").click(function(){
				$("#xtdjxx").addClass("none");
				/*$("#sbxxwh").addClass("none");*/
				$("#gsdjxx").addClass("none");
				$("#lxxx").addClass("none");
				$("#yhzhxx").addClass("none");
				$("#qyxtzcxx").addClass("none");
				$("#qyxtzcxx_xg").addClass("none");
				$("#qygsdjxx").addClass("none");
				$("#qylxxx").addClass("none");
				$("#qylxxx_xg").addClass("none");
				$("#qyyhzhxx").addClass("none");
				$("#qyyhzhxx_xg").addClass("none");
				$("#qysczl").addClass("none");
				$("#qysczl_xg").addClass("none");
				$("#glqysbzzdwh").addClass("none");
				$("#glqysbzzdwh_xg").addClass("none");
				$("#glqysbzzdwh_xg_2").addClass("none");
				$("#glqysbzzdwh_xg_3").addClass("none");
				
				$("#qygsdjxx_xg").removeClass("none");
				
				$("#qygsdjxx_xg-submitBtn").click(function(e){
					submitDialog(e);
				});	
				
				//加载日期
				$( "#establishDate" ).datepicker({dateFormat:'yy-mm-dd'});	
				$( "#startDate" ).datepicker({dateFormat:'yy-mm-dd'});
				$( "#stopDate" ).datepicker({dateFormat:'yy-mm-dd'});
				
			});
			
			$(".back_qylxxx").click(function(){
				$("#xtdjxx").addClass("none");
				/*$("#sbxxwh").addClass("none");*/
				$("#gsdjxx").addClass("none");
				$("#lxxx").addClass("none");
				$("#yhzhxx").addClass("none");
				$("#qyxtzcxx").addClass("none");
				$("#qyxtzcxx_xg").addClass("none");
				$("#qygsdjxx").addClass("none");
				$("#qygsdjxx_xg").addClass("none");
				$("#qylxxx_xg").addClass("none");
				$("#qyyhzhxx").addClass("none");
				$("#qyyhzhxx_xg").addClass("none");
				$("#qysczl").addClass("none");
				$("#qysczl_xg").addClass("none");
				$("#glqysbzzdwh").addClass("none");
				$("#glqysbzzdwh_xg").addClass("none");
				$("#glqysbzzdwh_xg_2").addClass("none");
				$("#glqysbzzdwh_xg_3").addClass("none");
				
				$("#qylxxx").removeClass("none");
			});
			
			$("#modify_qylxxx_btn").click(function(){
				$("#xtdjxx").addClass("none");
				/*$("#sbxxwh").addClass("none");*/
				$("#gsdjxx").addClass("none");
				$("#lxxx").addClass("none");
				$("#yhzhxx").addClass("none");
				$("#qyxtzcxx").addClass("none");
				$("#qyxtzcxx_xg").addClass("none");
				$("#qygsdjxx").addClass("none");
				$("#qygsdjxx_xg").addClass("none");
				$("#qylxxx").addClass("none");
				$("#qyyhzhxx").addClass("none");
				$("#qyyhzhxx_xg").addClass("none");
				$("#qysczl").addClass("none");
				$("#qysczl_xg").addClass("none");
				$("#glqysbzzdwh").addClass("none");
				$("#glqysbzzdwh_xg").addClass("none");
				$("#glqysbzzdwh_xg_2").addClass("none");
				$("#glqysbzzdwh_xg_3").addClass("none");
				
				$("#qylxxx_xg").removeClass("none");
				
				/*$( "#qylxxx_xgMobile-btn" ).click(function(e) {
					verificationMobileDialog(e);
				});	
				
				$( "#qylxxx_xgEmail-btn" ).click(function(e) {
					verificationEmailDialog(e);
				});*/
				
				$("#qylxxx_xg-submitBtn").click(function(e){
					submitDialog(e);
				});
				
				$("#upload_btn").click(function(){
					ajaxFileUpload();
				});
				
				function ajaxFileUpload() {
					$.ajaxFileUpload
					(
						{
							url: '/upload.aspx', //用于文件上传的服务器端请求地址
							secureuri: false, //是否需要安全协议，一般设置为false
							fileElementId: 'file1', //文件上传域的ID
							dataType: 'JSON', //返回值类型 一般设置为json
							success: function (data, status)  //服务器成功响应处理函数
							{
								$("#img1").attr("src", data.imgurl);
								if (typeof (data.error) != 'undefined') {
									if (data.error != '') {
										alert(data.error);
									} else {
										alert(data.msg);
									}
								}
							},
							error: function (data, status, e)//服务器响应失败处理函数
							{
								alert(e);
							}
						}
					)
					return false;
				}
				
			});
			
			$(".back_qyyhzhxx").click(function(){
				$("#xtdjxx").addClass("none");
				/*$("#sbxxwh").addClass("none");*/
				$("#gsdjxx").addClass("none");
				$("#lxxx").addClass("none");
				$("#yhzhxx").addClass("none");
				$("#qyxtzcxx").addClass("none");
				$("#qyxtzcxx_xg").addClass("none");
				$("#qygsdjxx").addClass("none");
				$("#qygsdjxx_xg").addClass("none");
				$("#qylxxx").addClass("none");
				$("#qylxxx_xg").addClass("none");
				$("#qyyhzhxx_xg").addClass("none");
				$("#qysczl").addClass("none");
				$("#qysczl_xg").addClass("none");
				$("#glqysbzzdwh").addClass("none");
				$("#glqysbzzdwh_xg").addClass("none");
				$("#glqysbzzdwh_xg_2").addClass("none");
				$("#glqysbzzdwh_xg_3").addClass("none");
				
				$("#qyyhzhxx").removeClass("none");	
			});
			
			$("#modify_qyyhzhxx_btn").click(function(){
				$("#xtdjxx").addClass("none");
				/*$("#sbxxwh").addClass("none");*/
				$("#gsdjxx").addClass("none");
				$("#lxxx").addClass("none");
				$("#yhzhxx").addClass("none");
				$("#qyxtzcxx").addClass("none");
				$("#qyxtzcxx_xg").addClass("none");
				$("#qygsdjxx").addClass("none");
				$("#qygsdjxx_xg").addClass("none");
				$("#qylxxx").addClass("none");
				$("#qylxxx_xg").addClass("none");
				$("#qyyhzhxx").addClass("none");
				$("#qysczl").addClass("none");
				$("#qysczl_xg").addClass("none");
				$("#glqysbzzdwh").addClass("none");
				$("#glqysbzzdwh_xg").addClass("none");
				$("#glqysbzzdwh_xg_2").addClass("none");
				$("#glqysbzzdwh_xg_3").addClass("none");
				
				$("#qyyhzhxx_xg").removeClass("none");	
				
				/*$("#companyName").selectbox({width:190}).change(function(){
					console.log("Statues change");
				});
				$("#brandSelect").selectbox({width:190}).change(function(){
					console.log("Statues change");
				});*/
				
				//调用自动完成组合框
				$("#brandSelect").combobox();
				$(".ui-autocomplete").css({
					"max-height":"250px",
					"overflow-y":"auto",
					"overflow-x":"hidden",
					"height":"250px",
					"border":"1px solid #CCC"
				});
				$(".combobox_style").find("a").attr("title","显示所有选项");
				//end
				
				$("#area_1").selectbox({width:230});
				$("#area_2").selectbox({width:230});
				$("#qyyhzhxx_xg-submitBtn").click(function(e){
					submitDialog(e);
				});
			});
			
			$(".back_qysczl").click(function(){
				$("#xtdjxx").addClass("none");
				/*$("#sbxxwh").addClass("none");*/
				$("#gsdjxx").addClass("none");
				$("#lxxx").addClass("none");
				$("#yhzhxx").addClass("none");
				$("#qyxtzcxx").addClass("none");
				$("#qyxtzcxx_xg").addClass("none");
				$("#qygsdjxx").addClass("none");
				$("#qygsdjxx_xg").addClass("none");
				$("#qylxxx").addClass("none");
				$("#qylxxx_xg").addClass("none");
				$("#qyyhzhxx").addClass("none");
				$("#qyyhzhxx_xg").addClass("none");
				$("#qysczl_xg").addClass("none");
				$("#glqysbzzdwh").addClass("none");
				$("#glqysbzzdwh_xg").addClass("none");
				$("#glqysbzzdwh_xg_2").addClass("none");
				$("#glqysbzzdwh_xg_3").addClass("none");
				
				$("#qysczl").removeClass("none");
			});
			
			$("#modify_qysczl_btn").click(function(){
				$("#xtdjxx").addClass("none");
				/*$("#sbxxwh").addClass("none");*/
				$("#gsdjxx").addClass("none");
				$("#lxxx").addClass("none");
				$("#yhzhxx").addClass("none");
				$("#qyxtzcxx").addClass("none");
				$("#qyxtzcxx_xg").addClass("none");
				$("#qygsdjxx").addClass("none");
				$("#qygsdjxx_xg").addClass("none");
				$("#qylxxx").addClass("none");
				$("#qylxxx_xg").addClass("none");
				$("#qyyhzhxx").addClass("none");
				$("#qyyhzhxx_xg").addClass("none");
				$("#qysczl").addClass("none");
				$("#glqysbzzdwh").addClass("none");
				$("#glqysbzzdwh_xg").addClass("none");
				$("#glqysbzzdwh_xg_2").addClass("none");
				$("#glqysbzzdwh_xg_3").addClass("none");
				
				$("#qysczl_xg").removeClass("none");
				
				$("#qysczl_xg-submitBtn").click(function(e){
					submitDialog(e);
				});
			});
			
			$(".back_glqysbzzdwh").click(function(){
				$("#xtdjxx").addClass("none");
				/*$("#sbxxwh").addClass("none");*/
				$("#gsdjxx").addClass("none");
				$("#lxxx").addClass("none");
				$("#yhzhxx").addClass("none");
				$("#qyxtzcxx").addClass("none");
				$("#qyxtzcxx_xg").addClass("none");
				$("#qygsdjxx").addClass("none");
				$("#qygsdjxx_xg").addClass("none");
				$("#qylxxx").addClass("none");
				$("#qylxxx_xg").addClass("none");
				$("#qyyhzhxx").addClass("none");
				$("#qyyhzhxx_xg").addClass("none");
				$("#qysczl").addClass("none");
				$("#qysczl_xg").addClass("none");
				$("#glqysbzzdwh_xg").addClass("none");
				$("#glqysbzzdwh_xg_2").addClass("none");
				$("#glqysbzzdwh_xg_3").addClass("none");
				
				$("#glqysbzzdwh").removeClass("none");			
				
			});
			
			
			$(".zzdModify_btn").click(function(){
				$("#xtdjxx").addClass("none");
				/*$("#sbxxwh").addClass("none");*/
				$("#gsdjxx").addClass("none");
				$("#lxxx").addClass("none");
				$("#yhzhxx").addClass("none");
				$("#qyxtzcxx").addClass("none");
				$("#qyxtzcxx_xg").addClass("none");
				$("#qygsdjxx").addClass("none");
				$("#qygsdjxx_xg").addClass("none");
				$("#qylxxx").addClass("none");
				$("#qylxxx_xg").addClass("none");
				$("#qyyhzhxx").addClass("none");
				$("#qyyhzhxx_xg").addClass("none");
				$("#qysczl").addClass("none");
				$("#qysczl_xg").addClass("none");
				$("#glqysbzzdwh").addClass("none");
				$("#glqysbzzdwh_xg_2").addClass("none");
				$("#glqysbzzdwh_xg_3").addClass("none");
				
				$("#glqysbzzdwh_xg").removeClass("none");
				
				$("#glqysbzzdwhSubmit_btn").click(function(){
					$("#glqysbzzdwhSubmitContent").dialog({
						title:"积分增值点修改",
						modal:true,
						width:"800",
						buttons:{ 
							"确定":function(){
								$(this).dialog( "destroy" );
							},
							"取消":function(){
								 $(this).dialog( "destroy" );
							}
						}
					});
				});
			
			});
			
			$(".zzdModify_btn_2").click(function(){
				$("#xtdjxx").addClass("none");
				/*$("#sbxxwh").addClass("none");*/
				$("#gsdjxx").addClass("none");
				$("#lxxx").addClass("none");
				$("#yhzhxx").addClass("none");
				$("#qyxtzcxx").addClass("none");
				$("#qyxtzcxx_xg").addClass("none");
				$("#qygsdjxx").addClass("none");
				$("#qygsdjxx_xg").addClass("none");
				$("#qylxxx").addClass("none");
				$("#qylxxx_xg").addClass("none");
				$("#qyyhzhxx").addClass("none");
				$("#qyyhzhxx_xg").addClass("none");
				$("#qysczl").addClass("none");
				$("#qysczl_xg").addClass("none");
				$("#glqysbzzdwh").addClass("none");
				$("#glqysbzzdwh_xg").addClass("none");
				$("#glqysbzzdwh_xg_3").addClass("none");
				
				$("#glqysbzzdwh_xg_2").removeClass("none");
				
				$("#glqysbzzdwhSubmit_btn_2").click(function(){
					$("#glqysbzzdwhSubmitContent_2").dialog({
						title:"积分增值点修改",
						modal:true,
						width:"800",
						buttons:{ 
							"确定":function(){
								$(this).dialog( "destroy" );
							},
							"取消":function(){
								 $(this).dialog( "destroy" );
							}
						}
					});
				});
			});
			
			$(".zzdModify_btn_3").click(function(){
				$("#xtdjxx").addClass("none");
				/*$("#sbxxwh").addClass("none");*/
				$("#gsdjxx").addClass("none");
				$("#lxxx").addClass("none");
				$("#yhzhxx").addClass("none");
				$("#qyxtzcxx").addClass("none");
				$("#qyxtzcxx_xg").addClass("none");
				$("#qygsdjxx").addClass("none");
				$("#qygsdjxx_xg").addClass("none");
				$("#qylxxx").addClass("none");
				$("#qylxxx_xg").addClass("none");
				$("#qyyhzhxx").addClass("none");
				$("#qyyhzhxx_xg").addClass("none");
				$("#qysczl").addClass("none");
				$("#qysczl_xg").addClass("none");
				$("#glqysbzzdwh").addClass("none");
				$("#glqysbzzdwh_xg").addClass("none");
				$("#glqysbzzdwh_xg_2").addClass("none");
				
				$("#glqysbzzdwh_xg_3").removeClass("none");
				
				$("#glqysbzzdwhSubmit_btn_3").click(function(){
					$("#glqysbzzdwhSubmitContent_3").dialog({
						title:"积分增值点修改",
						modal:true,
						width:"800",
						buttons:{ 
							"确定":function(){
								$(this).dialog( "destroy" );
							},
							"取消":function(){
								 $(this).dialog( "destroy" );
							}
						}
					});
				});
			});
		},
		hide:function(){
			//清空中间内容） 
			$(".operationsArea").empty();	
		}
		
	};
	
		
	return sbxxwh;
	 

});