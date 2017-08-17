define(["hsec_tablePointSrc/tablePoint"
        ,"hsec_roleDat/roleAjax"
        ,"text!hsec_roleTpl/tab.html"
        ,"text!hsec_roleTpl/gridMain.html"
        ,"text!hsec_roleTpl/role.html"
        ,"text!hsec_roleTpl/roleList.html"
        ,"text!hsec_roleTpl/roleAddUpdate.html"
        ,"text!hsec_roleTpl/power.html"
        ,"text!hsec_roleTpl/powerReadonly.html"
        ,"hsec_roleSrc/ztree"
        ,"hsec_roleSrc/jquery.ztree.core-3.5"
        ],function(tablePoint,ajaxRequest,tabTpl,gridMainTpl,role,roleList,roleAddUpdate,power,powerReadonly,ztree){
    var queryParam = {"currentPageIndex":1};
	var shop ={
			bindData : function() {
				$("#busibox").html(_.template(gridMainTpl));
				shop.loadBsGridData(queryParam);
				//$('.operationsArea').html(role);
				//shop.bindDataClick();
				//shop.bindTable();
			},
			loadBsGridData : function(queryParam){
				var gridObj;
				$(function(){
					gridObj = $.fn.bsgrid.init('gridMain',{
						url:{url:comm.UrlList["getEnterperiseRoleList"],domain:"sportal"},//comm.domainList["sportal"]+comm.UrlList["getEnterperiseRoleList"],
						pageSize:20,
						displayBlankRows:false,
						stripeRows:true,
						operate:{
							add : function(record, rowIndex, colIdex, options){
								var sTpl = "";
								var id = record.id;
								var roleName = record.roleName;
								var remark = record.remark;
								var isDefault = record.isDefault;
								switch (colIdex) {
									case 0 :
										sTpl = rowIndex + 1;
										break;
									case 1 :
										sTpl = roleName;
										break;
									case 2 :
										sTpl = remark; 
										break;
									case 3 :
										if(isDefault != 1){
											sTpl = '否';
										}else{
											sTpl = '是';
										}
										break;
									case 4 :
										sTpl = '<a href="javascript:;"  class="dlg33 roleLook" roleId="'+id+'">查看权限</a>';
										if(isDefault != 1){
									       sTpl += ' | <a href="javascript:;"  class="dlg33 roleUpdate" roleId="'+id+'">编辑</a>'; 
									   	   sTpl += ' | <a href="javascript:;"  class="dlg33 rolePower" roleId="'+id+'">菜单授权</a>';
									       sTpl += ' | <a href="javascript:;" class="dlg33 roleDelete" roleId="'+id+'">删除</a>'; 
									    }
										break;
									default :
										break;
								}
								return sTpl;
							}//end add;
						},//end operate;
						complete : function(options, XMLHttpRequest, textStatus){
							//添加事件
							shop.bindDataClick();
							//表格中事件
							shop.bindTableClick();
						}
					});
				});//end $;
			},
			bindDataClick : function(){
				//添加角色
				$("#addRole").unbind().on('click',function(){
					var html = _.template(roleAddUpdate, null);
//					$('.operationsArea').html(html);
//					shop.bindAddUpdate();
					$('.operationsArea').html(_.template(tabTpl));
					$("#xtyhgl_jsgl").find("a").removeClass("active").addClass("returnRole");
					$(".tabList").append('<li><span class="active">新增/修改角色</span></li>');
					$("#busibox").html(html);
					shop.bindAddUpdate();
				})
				
			},
			bindTable : function(){
				ajaxRequest.getEnterperiseRoleList(null,function(response){
					var html = _.template(roleList, response);
					$("#roleTable").html(html);
					$("#tableRole").DataTable({
						"scrollY":"338px",//垂直限制高度，需根据页面当前情况进行设置
						"scrollCollapse": true,//垂直限制高度
						"bFilter" : false, 
						"bPaginate": false,
						"bLengthChange" : false, 
						"bSort":false,
						"sDom" : '<""l>t<"F"fp>',
						"oLanguage" : {//改变语言
							"sZeroRecords" : "没有找到符合条件的数据"
						}
					});
					shop.bindTableClick();
				})
			},
			bindTableClick : function(){
				$(".rolePower").release().on('click',function(){
					var id = $(this).parents("tr").attr("roleId");
					shop.bindPower(id,0);
				})
				$(".roleLook").release().on('click',function(){
					//var id = $(this).parents("tr").attr("roleId");
					var id = $(this).attr("roleId");
					shop.bindPowerReadonly(id);
				})
				/**
				 * 批量删除事件
				 */
				$(".roleDelete").release().on('click',function(){
							var obj = $(this);
							var param = new Array();
							//param[0] = $(obj).parents("tr").attr("roleId");
							param[0] = $(obj).attr("roleId");
							$( "#store2" ).dialog({
								title:'删除操作',
								show: true,
								modal: true,
								close: function() { 
							        $(this).dialog('destroy');
								},
								buttons: {
									"确定": function() {
										ajaxRequest.batchDeleteRole("roleIdList="+JSON.stringify(param),function(disResponse){
											if(disResponse.retCode == 200){
													tablePoint.tablePoint("删除成功!",function(){
														$(obj).parents("tr").remove();
													});
											}else{
												tablePoint.tablePoint("删除失败,请稍后重试!");
											}
										})
									    $( this ).dialog( "destroy" );
									},
									"取消": function() {
									    $( this ).dialog( "destroy" );
									}
								}
							});
					})	
					//角色编辑
					$(".roleUpdate").release().on('click',function(){
						//var id = $(this).parents("tr").attr("roleId");
						var id = $(this).attr("roleId");
						ajaxRequest.getSysRoleByroleId("roleId="+id,function(response){
							var html = _.template(roleAddUpdate, response.data);
							//$('.operationsArea').html(html);
							$('.operationsArea').html(_.template(tabTpl));
							$("#xtyhgl_jsgl").find("a").removeClass("active").addClass("returnRole");
							$(".tabList").append('<li><span class="active">新增/修改角色</span></li>');
							$("#busibox").html(html);
							shop.bindAddUpdate();
						})
				   })
			},
			bindAddUpdate : function(){
				$(".returnRole").unbind().on('click',function(){
					$(this).addClass("active");
					$("#xtyhgl_jsgl").next().remove();
					shop.bindData();
				})
				$("#addRole").unbind().on('click',function(){
					var roleName = $.trim($("#roleName").val());
					var remark = $.trim($("#remark").val());
					if(roleName.length > 30 || roleName == null || roleName == ""){
						tablePoint.tablePoint("角色名称不能为空,不能大于30个字符!");
						return false;
					}
					if(remark.length > 150 || remark == null || remark == ""){
						tablePoint.tablePoint("描述不能为空,不能大于150个字符!");
						return false;
					}
					var param = $("#formRole").serializeJson();
					ajaxRequest.addRole(param,function(response){
						if(response.retCode == 200){
							shop.bindPower(response.data,1);
						}else{
							tablePoint.tablePoint("添加角色信息失败!原因:已存在相同角色名称或网络超时!");
						}
					})
				})
				
				$("#updateRole").unbind().on('click',function(){
					var roleId = $.trim($("#roleId").val());
					var roleName = $.trim($("#roleName").val());
					var remark = $.trim($("#remark").val());
					if(roleName.length > 30 || roleName == null || roleName == ""){
						tablePoint.tablePoint("角色名称不能为空,不能大于30个字符!");
						return false;
					}
					if(remark.length > 150 || remark == null || remark == ""){
						tablePoint.tablePoint("描述不能为空,不能大于150个字符!");
						return false;
					}
					var param = $("#formRole").serializeJson();
					ajaxRequest.updateRole(param,function(response){
						if(response.retCode == 200){
							tablePoint.tablePoint("修改角色信息成功!",function(){
								//shop.bindData();
								$(".returnRole").click();
							});
						}else{
							tablePoint.tablePoint("修改角色信息失败!原因:已存在相同角色名称或网络超时!");
						}
					})
				})
			},
			bindPower : function(id,type){
					var booltree = false;
					$("#dlg33").dialog({
								title : "菜单管理",
								width : "500",
								modal : true,
								open : function(){
									$('#dlg33').children().html(power);
									ztree.bindParam(id,ajaxRequest,"add",function(isbool){
										booltree = isbool;
									});

									$("#openTree").on("click",function(){
										var treeObj = $.fn.zTree.getZTreeObj("treeDemo"); 
										if($(this).prop('checked')){
											treeObj.expandAll(true); 
										}else{
											treeObj.expandAll(false); 
										}
									})
									
									$("#allTree").on("click",function(){
										if($(this).prop('checked')){
											$.fn.zTree.getZTreeObj("treeDemo").expandAll(true); 
											$(".roleCheck").prop('checked',true);
											$("#openTree").prop('checked',true);
										}else{
											$(".roleCheck").prop('checked',false);
										}
									})
								},
								close: function() { 
								        $(this).dialog('destroy');
								},
								buttons : {
									'立即设置' : function() {
										var param = {};
											param["roleId"] = id;
										var authId ="";
											  $("input[name='authCheckBox']:checked").each(function(){
												  authId += $(this).val()+",";
											  })
										if(type == 1){	  
											if(authId == "" || authId == null){
												tablePoint.tablePoint("请选择菜单!");
												return false;
											}  
										}else{
											if(booltree != true && (authId == "" || authId == null)){
												tablePoint.tablePoint("请选择菜单!");
												return false;
											}
										}	
											param["authId"] = authId;
										ajaxRequest.addRoleAuth("raList="+JSON.stringify(param),function(response){
											if(response.retCode == 200){
												var src;
												if(authId != "" && authId != null){
													src = "添加权限成功!";
												}else{
													src = "取消权限成功!";
												}
												tablePoint.tablePoint(src,function(){
													$("#dlg33").dialog("destroy");
													if(type == 1){
														//shop.bindData();
														$(".returnRole").click();
													}
												});
											}else{
												tablePoint.tablePoint("添加权限失败!请稍后重试!",function(){
													$("#dlg33").dialog("destroy");
													if(type == 1){
														//shop.bindData();
														$(".returnRole").click();
													}
												});
											}
										})
									},
									'下次再说' : function() {
										$(this).dialog("destroy");
										if(type == 1){
											//shop.bindData();
											$(".returnRole").click();
										}
									}
								}
					});
			},
			bindPowerReadonly : function(id){
				$('#dlg33').children().html(powerReadonly);
				ztree.bindParam(id,ajaxRequest,"look");
				$("#dlg33").dialog({
							title : "菜单查看",
							width : "500",
							modal : true,
							open : function(){
								$("#openTree").on("click",function(){
									var treeObj = $.fn.zTree.getZTreeObj("treeDemo"); 
									if($(this).prop('checked')){
										treeObj.expandAll(true); 
									}else{
										treeObj.expandAll(false); 
									}
								})
							},
							close: function() { 
							        $(this).dialog('destroy');
							},
							buttons : {
								'关闭' : function() {
									$(this).dialog('destroy');
								}
							}
				});
		}
	}
	return shop;
});