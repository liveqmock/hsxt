define([
"text!coDeclarationTpl/csyw/tgqycsyw.html",
"text!coDeclarationTpl/csyw/tgqycsyw_sbxx.html",
"text!coDeclarationTpl/csyw/tgqycsyw_qyxtzcxx.html",
"text!coDeclarationTpl/csyw/tgqycsyw_qyxtzcxx_xg.html",
"text!coDeclarationTpl/csyw/tgqycsyw_qygsdjxx.html",
"text!coDeclarationTpl/csyw/tgqycsyw_qygsdjxx_xg.html",
"text!coDeclarationTpl/csyw/tgqycsyw_qylxxx.html",
"text!coDeclarationTpl/csyw/tgqycsyw_qylxxx_xg.html",
"text!coDeclarationTpl/csyw/tgqycsyw_qyyhzhxx.html",
"text!coDeclarationTpl/csyw/tgqycsyw_qyyhzhxx_xg.html",
"text!coDeclarationTpl/csyw/tgqycsyw_qysczl.html",
"text!coDeclarationTpl/csyw/tgqycsyw_qysczl_xg.html",
"text!coDeclarationTpl/csyw/tgqycsyw_blztxx.html",
"text!coDeclarationTpl/csyw/cyqycsyw.html",
"text!coDeclarationTpl/csyw/cyqycsyw_sbxx.html",
"text!coDeclarationTpl/csyw/fwgscsyw.html",
"text!coDeclarationTpl/csyw/fwgscsyw_sbxx.html"

],function(tgqycsywTpl,tgqycsyw_sbxxTpl,tgqycsyw_qyxtzcxxTpl,tgqycsyw_qyxtzcxx_xgTpl,tgqycsyw_qygsdjxxTpl,tgqycsyw_qygsdjxx_xgTpl,tgqycsyw_qylxxxTpl,tgqycsyw_qylxxx_xgTpl,tgqycsyw_qyyhzhxxTpl,tgqycsyw_qyyhzhxx_xgTpl,tgqycsyw_qysczlTpl,tgqycsyw_qysczl_xgTpl,tgqycsyw_blztxxTpl,cyqycsywTpl,cyqycsyw_sbxxTpl,fwgscsywTpl,fwgscsyw_sbxxTpl){
 
	var csyw = {
		show:function(){
			//加载中间内容 
			$(".operationsArea").html(tgqycsywTpl).append(tgqycsyw_sbxxTpl).append(tgqycsyw_qyxtzcxxTpl).append(tgqycsyw_qyxtzcxx_xgTpl).append(tgqycsyw_qygsdjxxTpl).append(tgqycsyw_qygsdjxx_xgTpl).append(tgqycsyw_qylxxxTpl).append(tgqycsyw_qylxxx_xgTpl).append(tgqycsyw_qyyhzhxxTpl).append(tgqycsyw_qyyhzhxx_xgTpl).append(tgqycsyw_qysczlTpl).append(tgqycsyw_qysczl_xgTpl).append(tgqycsyw_blztxxTpl).append(cyqycsywTpl).append(cyqycsyw_sbxxTpl).append(fwgscsywTpl).append(fwgscsyw_sbxxTpl);
			$("#timeRange_1").datepicker({dateFormat:'yy-mm-dd'});
			$("#timeRange_2").datepicker({dateFormat:'yy-mm-dd'});
			
			function shtjDialog(e){
				var currentID = $(e.currentTarget).attr("id");
				if(currentID){
					var IDNameArr = new Array();
					IDNameArr = currentID.split("-");
					var currentIDName = IDNameArr[0];
					var currentContent = IDNameArr[0] + "-content";
				
					$( "#" + currentContent ).dialog({
						title:"审核确认",
						modal:true,
						width:"400",
						height:"230",
						buttons:{ 
							"确定":function(){
								$( "#" + currentContent ).dialog( "destroy" );
							},
							"取消":function(){
								 $( "#" + currentContent ).dialog( "destroy" );
							}
						}
				
					  });	
				}
			}	
			
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
								$( "#" + currentContent ).dialog( "destroy" );
							},
							"取消":function(){
								 $( "#" + currentContent ).dialog( "destroy" );
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
								$( "#" + currentContent ).dialog( "destroy" );
							},
							"取消":function(){
								 $( "#" + currentContent ).dialog( "destroy" );
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
								$( "#" + currentContent ).dialog( "destroy" );
							},
							"取消":function(){
								 $( "#" + currentContent ).dialog( "destroy" );
							}
						}
				
					  });	
				}
			}	
			
			$( "#sbxxShtj-btn" ).click(function(e) {
				shtjDialog(e);
			});
					
			
			$(".tgsp_btn").click(function(){
				$("#tgqycsyw").addClass("none");
				$("#tgqycsyw_qyxtzcxx").addClass("none");
				$("#tgqycsyw_qyxtzcxx_xg").addClass("none");
				$("#tgqycsyw_qygsdjxx").addClass("none");
				$("#tgqycsyw_qygsdjxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx").addClass("none");
				$("#tgqycsyw_qylxxx_xg").addClass("none");
				$("#tgqycsyw_qyyhzhxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx_xg").addClass("none");
				$("#tgqycsyw_qysczl").addClass("none");
				$("#tgqycsyw_qysczl_xg").addClass("none");
				$("#tgqycsyw_blztxx").addClass("none");
				$("#cyqycsyw").addClass("none");
				$("#cyqycsyw_sbxx").addClass("none");
				$("#fwgscsyw").addClass("none");
				$("#fwgscsyw_sbxx").addClass("none");
				
				$("#tgqycsyw_sbxx").removeClass("none");
			});
			
			$(".back_tgqycsyw").click(function(){
				$("#tgqycsyw_sbxx").addClass("none");
				$("#tgqycsyw_qyxtzcxx").addClass("none");
				$("#tgqycsyw_qyxtzcxx_xg").addClass("none");
				$("#tgqycsyw_qygsdjxx").addClass("none");
				$("#tgqycsyw_qygsdjxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx").addClass("none");
				$("#tgqycsyw_qylxxx_xg").addClass("none");
				$("#tgqycsyw_qyyhzhxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx_xg").addClass("none");
				$("#tgqycsyw_qysczl").addClass("none");
				$("#tgqycsyw_qysczl_xg").addClass("none");
				$("#tgqycsyw_blztxx").addClass("none");
				$("#cyqycsyw").addClass("none");
				$("#cyqycsyw_sbxx").addClass("none");
				$("#fwgscsyw").addClass("none");
				$("#fwgscsyw_sbxx").addClass("none");
				
				$("#tgqycsyw").removeClass("none");
			});
			
			
			$( ".back_tgqycsyw_qyxtzcxx" ).click(function() {
				$("#tgqycsyw_sbxx").addClass("none");
				$("#tgqycsyw").addClass("none");
				$("#tgqycsyw_qyxtzcxx_xg").addClass("none");
				$("#tgqycsyw_qygsdjxx").addClass("none");
				$("#tgqycsyw_qygsdjxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx").addClass("none");
				$("#tgqycsyw_qylxxx_xg").addClass("none");
				$("#tgqycsyw_qyyhzhxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx_xg").addClass("none");
				$("#tgqycsyw_qysczl").addClass("none");
				$("#tgqycsyw_qysczl_xg").addClass("none");
				$("#tgqycsyw_blztxx").addClass("none");
				$("#cyqycsyw").addClass("none");
				$("#cyqycsyw_sbxx").addClass("none");
				$("#fwgscsyw").addClass("none");
				$("#fwgscsyw_sbxx").addClass("none");
				
				$("#tgqycsyw_qyxtzcxx").removeClass("none");
				
				$( "#qyxtzcxxShtj-btn" ).click(function(e) {
					shtjDialog(e);
				});
			});
			
			
			$(".back_tgqycsyw_sbxx").click(function(){
				$("#tgqycsyw_qyxtzcxx").addClass("none");
				$("#tgqycsyw").addClass("none");
				$("#tgqycsyw_qyxtzcxx_xg").addClass("none");
				$("#tgqycsyw_qygsdjxx").addClass("none");
				$("#tgqycsyw_qygsdjxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx").addClass("none");
				$("#tgqycsyw_qylxxx_xg").addClass("none");
				$("#tgqycsyw_qyyhzhxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx_xg").addClass("none");
				$("#tgqycsyw_qysczl").addClass("none");
				$("#tgqycsyw_qysczl_xg").addClass("none");
				$("#tgqycsyw_blztxx").addClass("none");
				$("#cyqycsyw").addClass("none");
				$("#cyqycsyw_sbxx").addClass("none");
				$("#fwgscsyw").addClass("none");
				$("#fwgscsyw_sbxx").addClass("none");
				
				$("#tgqycsyw_sbxx").removeClass("none");			
			});
			
			$("#modify_qyxtzcxx_btn").click(function(){
				$("#tgqycsyw_qyxtzcxx").addClass("none");
				$("#tgqycsyw").addClass("none");
				$("#tgqycsyw_sbxx").addClass("none");
				$("#tgqycsyw_qygsdjxx").addClass("none");
				$("#tgqycsyw_qygsdjxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx").addClass("none");
				$("#tgqycsyw_qylxxx_xg").addClass("none");
				$("#tgqycsyw_qyyhzhxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx_xg").addClass("none");
				$("#tgqycsyw_qysczl").addClass("none");
				$("#tgqycsyw_qysczl_xg").addClass("none");
				$("#tgqycsyw_blztxx").addClass("none");
				$("#cyqycsyw").addClass("none");
				$("#cyqycsyw_sbxx").addClass("none");
				$("#fwgscsyw").addClass("none");
				$("#fwgscsyw_sbxx").addClass("none");
				
				$("#tgqycsyw_qyxtzcxx_xg").removeClass("none");	
				
				$( "#qyxtzcxx_xg-btn" ).click(function(e) {
					shtjDialog(e);
				});
				
				$("#qyxtzcxx_xg-submitBtn").click(function(e){
					submitDialog(e);
				});
							
			});
			
			$(".back_tgqycsyw_qygsdjxx").click(function(){
				$("#tgqycsyw_qyxtzcxx").addClass("none");
				$("#tgqycsyw").addClass("none");
				$("#tgqycsyw_qyxtzcxx_xg").addClass("none");
				$("#tgqycsyw_sbxx").addClass("none");
				$("#tgqycsyw_qygsdjxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx").addClass("none");
				$("#tgqycsyw_qylxxx_xg").addClass("none");
				$("#tgqycsyw_qyyhzhxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx_xg").addClass("none");
				$("#tgqycsyw_qysczl").addClass("none");
				$("#tgqycsyw_qysczl_xg").addClass("none");
				$("#tgqycsyw_blztxx").addClass("none");
				$("#cyqycsyw").addClass("none");
				$("#cyqycsyw_sbxx").addClass("none");
				$("#fwgscsyw").addClass("none");
				$("#fwgscsyw_sbxx").addClass("none");
				
				$("#tgqycsyw_qygsdjxx").removeClass("none");
				
				$( "#qygsdjxxShtj-btn" ).click(function(e) {
					shtjDialog(e);
				});
			});
			
			$("#modify_qygsdjxx_btn").click(function(){
				$("#tgqycsyw_qyxtzcxx").addClass("none");
				$("#tgqycsyw").addClass("none");
				$("#tgqycsyw_qyxtzcxx_xg").addClass("none");
				$("#tgqycsyw_qygsdjxx").addClass("none");
				$("#tgqycsyw_sbxx").addClass("none");
				$("#tgqycsyw_qylxxx").addClass("none");
				$("#tgqycsyw_qylxxx_xg").addClass("none");
				$("#tgqycsyw_qyyhzhxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx_xg").addClass("none");
				$("#tgqycsyw_qysczl").addClass("none");
				$("#tgqycsyw_qysczl_xg").addClass("none");
				$("#tgqycsyw_blztxx").addClass("none");
				$("#cyqycsyw").addClass("none");
				$("#cyqycsyw_sbxx").addClass("none");
				$("#fwgscsyw").addClass("none");
				$("#fwgscsyw_sbxx").addClass("none");
				
				$("#tgqycsyw_qygsdjxx_xg").removeClass("none");
				
				//加载日期
				$( "#establishDate" ).datepicker({dateFormat:'yy-mm-dd'});	
				$( "#startDate" ).datepicker({dateFormat:'yy-mm-dd'});
				$( "#stopDate" ).datepicker({dateFormat:'yy-mm-dd'});
				
				$( "#qygsdjxx_xgShtj-btn" ).click(function(e) {
					shtjDialog(e);
				});
				
				$("#qygsdjxx_xg-submitBtn").click(function(e){
					submitDialog(e);
				});
					
			});
			$(".back_tgqycsyw_qylxxx").click(function(){
				$("#tgqycsyw_qyxtzcxx").addClass("none");
				$("#tgqycsyw").addClass("none");
				$("#tgqycsyw_sbxx").addClass("none");
				$("#tgqycsyw_qygsdjxx").addClass("none");
				$("#tgqycsyw_qygsdjxx_xg").addClass("none");
				$("#tgqycsyw_qyxtzcxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx_xg").addClass("none");
				$("#tgqycsyw_qyyhzhxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx_xg").addClass("none");
				$("#tgqycsyw_qysczl").addClass("none");
				$("#tgqycsyw_qysczl_xg").addClass("none");
				$("#tgqycsyw_blztxx").addClass("none");
				$("#cyqycsyw").addClass("none");
				$("#cyqycsyw_sbxx").addClass("none");
				$("#fwgscsyw").addClass("none");
				$("#fwgscsyw_sbxx").addClass("none");
				
				$("#tgqycsyw_qylxxx").removeClass("none");
				
				$( "#qylxxxShtj-btn" ).click(function(e) {
					shtjDialog(e);
				});	
			});
			
			$("#modify_qylxxx_btn").click(function(){
				$("#tgqycsyw_qyxtzcxx").addClass("none");
				$("#tgqycsyw").addClass("none");
				$("#tgqycsyw_sbxx").addClass("none");
				$("#tgqycsyw_qygsdjxx").addClass("none");
				$("#tgqycsyw_qygsdjxx_xg").addClass("none");
				$("#tgqycsyw_qyxtzcxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx_xg").addClass("none");
				$("#tgqycsyw_qysczl").addClass("none");
				$("#tgqycsyw_qysczl_xg").addClass("none");
				$("#tgqycsyw_blztxx").addClass("none");
				$("#cyqycsyw").addClass("none");
				$("#cyqycsyw_sbxx").addClass("none");
				$("#fwgscsyw").addClass("none");
				$("#fwgscsyw_sbxx").addClass("none");
				
				$("#tgqycsyw_qylxxx_xg").removeClass("none");
				
				$( "#qylxxx_xgShtj-btn" ).click(function(e) {
					shtjDialog(e);
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
				
				/*$( "#qylxxx_xgMobile-btn" ).click(function(e) {
					verificationMobileDialog(e);
				});	
				
				$( "#qylxxx_xgEmail-btn" ).click(function(e) {
					verificationEmailDialog(e);
				});	*/
				
				$("#qylxxx_xg-submitBtn").click(function(e){
					submitDialog(e);
				});
			});
			
			
			$(".back_tgqycsyw_qyyhzhxx").click(function(){
				$("#tgqycsyw_qyxtzcxx").addClass("none");
				$("#tgqycsyw").addClass("none");
				$("#tgqycsyw_sbxx").addClass("none");
				$("#tgqycsyw_qygsdjxx").addClass("none");
				$("#tgqycsyw_qygsdjxx_xg").addClass("none");
				$("#tgqycsyw_qyxtzcxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx_xg").addClass("none");
				$("#tgqycsyw_qysczl").addClass("none");
				$("#tgqycsyw_qysczl_xg").addClass("none");
				$("#tgqycsyw_blztxx").addClass("none");
				$("#cyqycsyw").addClass("none");
				$("#cyqycsyw_sbxx").addClass("none");
				$("#fwgscsyw").addClass("none");
				$("#fwgscsyw_sbxx").addClass("none");
				
				$("#tgqycsyw_qyyhzhxx").removeClass("none");
				
				$( "#qyyhzhxxShtj-btn" ).click(function(e) {
					shtjDialog(e);
				});	
			});
			
			$("#modify_qyyhzhxx_btn").click(function(){
				$("#tgqycsyw_qyxtzcxx").addClass("none");
				$("#tgqycsyw").addClass("none");
				$("#tgqycsyw_sbxx").addClass("none");
				$("#tgqycsyw_qygsdjxx").addClass("none");
				$("#tgqycsyw_qygsdjxx_xg").addClass("none");
				$("#tgqycsyw_qyxtzcxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx").addClass("none");
				$("#tgqycsyw_qysczl").addClass("none");
				$("#tgqycsyw_qysczl_xg").addClass("none");
				$("#tgqycsyw_blztxx").addClass("none");
				$("#cyqycsyw").addClass("none");
				$("#cyqycsyw_sbxx").addClass("none");
				$("#fwgscsyw").addClass("none");
				$("#fwgscsyw_sbxx").addClass("none");
				
				$("#tgqycsyw_qyyhzhxx_xg").removeClass("none");
				
				$( "#qyyhzhxx_xgShtj-btn" ).click(function(e) {
					shtjDialog(e);
				});	
				
				$("#qyyhzhxx_xg-submitBtn").click(function(e){
					submitDialog(e);
				});
				
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
				
				$("#province_slt").selectList({
					width:220,
					options:[
						{name:'广东省',value:'100'},
						{name:'江西省',value:'101'},
						{name:'湖南省',value:'102'}
					]
				}).change(function(e){
				});
				
				$("#city_slt").selectList({
					width:220,
					options:[
						{name:'深圳市',value:'100'},
						{name:'南昌市',value:'101'},
						{name:'长沙市',value:'102'}
					]
				}).change(function(e){
				});
				
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
			});
			
			$(".back_tgqycsyw_qysczl").click(function(){
				$("#tgqycsyw_qyxtzcxx").addClass("none");
				$("#tgqycsyw").addClass("none");
				$("#tgqycsyw_sbxx").addClass("none");
				$("#tgqycsyw_qygsdjxx").addClass("none");
				$("#tgqycsyw_qygsdjxx_xg").addClass("none");
				$("#tgqycsyw_qyxtzcxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx_xg").addClass("none");
				$("#tgqycsyw_qyyhzhxx").addClass("none");
				$("#tgqycsyw_qysczl_xg").addClass("none");
				$("#tgqycsyw_blztxx").addClass("none");
				$("#cyqycsyw").addClass("none");
				$("#cyqycsyw_sbxx").addClass("none");
				$("#fwgscsyw").addClass("none");
				$("#fwgscsyw_sbxx").addClass("none");
				
				$("#tgqycsyw_qysczl").removeClass("none");
				
				$( "#qysczlShtj-btn" ).click(function(e) {
					shtjDialog(e);
				});	
			});
			
			$("#modify_qysczl_btn").click(function(){
				$("#tgqycsyw_qyxtzcxx").addClass("none");
				$("#tgqycsyw").addClass("none");
				$("#tgqycsyw_sbxx").addClass("none");
				$("#tgqycsyw_qygsdjxx").addClass("none");
				$("#tgqycsyw_qygsdjxx_xg").addClass("none");
				$("#tgqycsyw_qyxtzcxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx_xg").addClass("none");
				$("#tgqycsyw_qysczl").addClass("none");
				$("#tgqycsyw_qyyhzhxx").addClass("none");
				$("#tgqycsyw_blztxx").addClass("none");
				$("#cyqycsyw").addClass("none");
				$("#cyqycsyw_sbxx").addClass("none");
				$("#fwgscsyw").addClass("none");
				$("#fwgscsyw_sbxx").addClass("none");
				
				$("#tgqycsyw_qysczl_xg").removeClass("none");
				
				$( "#qysczl_xgShtj-btn" ).click(function(e) {
					shtjDialog(e);
				});	
				$("#qysczl_xg-submitBtn").click(function(e){
					submitDialog(e);
				});
			});
			
			$(".back_tgqycsyw_blztxx").click(function(){
				$("#tgqycsyw_qyxtzcxx").addClass("none");
				$("#tgqycsyw").addClass("none");
				$("#tgqycsyw_sbxx").addClass("none");
				$("#tgqycsyw_qygsdjxx").addClass("none");
				$("#tgqycsyw_qygsdjxx_xg").addClass("none");
				$("#tgqycsyw_qyxtzcxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx_xg").addClass("none");
				$("#tgqycsyw_qysczl").addClass("none");
				$("#tgqycsyw_qyyhzhxx").addClass("none");
				$("#tgqycsyw_qysczl_xg").addClass("none");
				$("#cyqycsyw").addClass("none");
				$("#cyqycsyw_sbxx").addClass("none");
				$("#fwgscsyw").addClass("none");
				$("#fwgscsyw_sbxx").addClass("none");
				
				$("#tgqycsyw_blztxx").removeClass("none");
				
				$( "#blztxxShtj-btn" ).click(function(e) {
					shtjDialog(e);
				});	
				$("#blztxx_btn").click(function(e){
					$( "#blztxxViewContent").dialog({
						title:"备注详情",
						modal:true,
						width:"400",
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
			
			$(".back_tgqycsyw").click(function(){
				$("#tgqycsyw_qyxtzcxx").addClass("none");
				$("#tgqycsyw_blztxx").addClass("none");
				$("#tgqycsyw_sbxx").addClass("none");
				$("#tgqycsyw_qygsdjxx").addClass("none");
				$("#tgqycsyw_qygsdjxx_xg").addClass("none");
				$("#tgqycsyw_qyxtzcxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx_xg").addClass("none");
				$("#tgqycsyw_qysczl").addClass("none");
				$("#tgqycsyw_qyyhzhxx").addClass("none");
				$("#tgqycsyw_qysczl_xg").addClass("none");
				$("#cyqycsyw").addClass("none");
				$("#cyqycsyw_sbxx").addClass("none");
				$("#fwgscsyw").addClass("none");
				$("#fwgscsyw_sbxx").addClass("none");
				
				$("#tgqycsyw").removeClass("none");	
			});
			
			$(".back_cyqycsyw").click(function(){
				$("#tgqycsyw_qyxtzcxx").addClass("none");
				$("#tgqycsyw_blztxx").addClass("none");
				$("#tgqycsyw_sbxx").addClass("none");
				$("#tgqycsyw_qygsdjxx").addClass("none");
				$("#tgqycsyw_qygsdjxx_xg").addClass("none");
				$("#tgqycsyw_qyxtzcxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx_xg").addClass("none");
				$("#tgqycsyw_qysczl").addClass("none");
				$("#tgqycsyw_qyyhzhxx").addClass("none");
				$("#tgqycsyw_qysczl_xg").addClass("none");
				$("#tgqycsyw").addClass("none");
				$("#cyqycsyw_sbxx").addClass("none");
				$("#fwgscsyw").addClass("none");
				$("#fwgscsyw_sbxx").addClass("none");
				
				$("#cyqycsyw").removeClass("none");	
				
				$("#timeRange_3").datepicker({dateFormat:'yy-mm-dd'});
				$("#timeRange_4").datepicker({dateFormat:'yy-mm-dd'});
			});

			
			$(".cysp_btn").click(function(){
				$("#tgqycsyw_qyxtzcxx").addClass("none");
				$("#tgqycsyw_blztxx").addClass("none");
				$("#tgqycsyw_sbxx").addClass("none");
				$("#tgqycsyw_qygsdjxx").addClass("none");
				$("#tgqycsyw_qygsdjxx_xg").addClass("none");
				$("#tgqycsyw_qyxtzcxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx_xg").addClass("none");
				$("#tgqycsyw_qysczl").addClass("none");
				$("#tgqycsyw_qyyhzhxx").addClass("none");
				$("#tgqycsyw_qysczl_xg").addClass("none");
				$("#tgqycsyw").addClass("none");
				$("#cyqycsyw").addClass("none");
				$("#fwgscsyw").addClass("none");
				$("#fwgscsyw_sbxx").addClass("none");
				
				$("#cyqycsyw_sbxx").removeClass("none");	
				
				$( "#cyqycsyw_sbxxShtj-btn" ).click(function(e) {
					shtjDialog(e);
				});	
				
			});
			
			$(".back_fwgscsyw").click(function(){
				$("#tgqycsyw_qyxtzcxx").addClass("none");
				$("#tgqycsyw_blztxx").addClass("none");
				$("#tgqycsyw_sbxx").addClass("none");
				$("#tgqycsyw_qygsdjxx").addClass("none");
				$("#tgqycsyw_qygsdjxx_xg").addClass("none");
				$("#tgqycsyw_qyxtzcxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx_xg").addClass("none");
				$("#tgqycsyw_qysczl").addClass("none");
				$("#tgqycsyw_qyyhzhxx").addClass("none");
				$("#tgqycsyw_qysczl_xg").addClass("none");
				$("#tgqycsyw").addClass("none");
				$("#cyqycsyw").addClass("none");
				$("#cyqycsyw_sbxx").addClass("none");
				$("#fwgscsyw_sbxx").addClass("none");
				
				$("#fwgscsyw").removeClass("none");	
				
				$("#timeRange_5").datepicker({dateFormat:'yy-mm-dd'});
				$("#timeRange_6").datepicker({dateFormat:'yy-mm-dd'});
				
			});
			
			$(".fwsp_btn").click(function(){
				$("#tgqycsyw_qyxtzcxx").addClass("none");
				$("#tgqycsyw_blztxx").addClass("none");
				$("#tgqycsyw_sbxx").addClass("none");
				$("#tgqycsyw_qygsdjxx").addClass("none");
				$("#tgqycsyw_qygsdjxx_xg").addClass("none");
				$("#tgqycsyw_qyxtzcxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx_xg").addClass("none");
				$("#tgqycsyw_qylxxx").addClass("none");
				$("#tgqycsyw_qyyhzhxx_xg").addClass("none");
				$("#tgqycsyw_qysczl").addClass("none");
				$("#tgqycsyw_qyyhzhxx").addClass("none");
				$("#tgqycsyw_qysczl_xg").addClass("none");
				$("#tgqycsyw").addClass("none");
				$("#cyqycsyw").addClass("none");
				$("#fwgscsyw").addClass("none");
				$("#cyqycsyw_sbxx").addClass("none");
				
				$("#fwgscsyw_sbxx").removeClass("none");	
				
				$( "#fwgscsyw_sbxxShtj-btn" ).click(function(e) {
					shtjDialog(e);
				});	
				
			});
			
			
		},
		hide:function(){
			//清空中间内容） 
			$(".operationsArea").empty();	
		}
		
	};
	
		
	return csyw;
	 

});