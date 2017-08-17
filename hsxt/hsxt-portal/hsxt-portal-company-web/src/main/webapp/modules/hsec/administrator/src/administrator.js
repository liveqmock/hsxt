define([ "text!hsec_administratorTpl/addrole.html",
         "text!hsec_administratorTpl/tab.html",
         "text!hsec_administratorTpl/gridMain.html",
         "text!hsec_administratorTpl/marketEmployee.html",
         "text!hsec_administratorTpl/marketEmployeeList.html",
         "text!hsec_administratorTpl/employeeAccount_frm.html",
         "text!hsec_administratorTpl/add_employee.html",
         "text!hsec_administratorTpl/shop_dlg.html",
         "hsec_administratorDat/administrator",
         "hsec_administratorSrc/administratorCheck"
         ,"hsec_tablePointSrc/tablePoint"
         ,"hsec_tablePointSrc/jquery.charcount"
         ], function(addrole,tabTpl,gridMainTpl,marketEmployee,tplList,frmtpl,add_tpl,shop_dlg,ajaxRequest,administratorCheck,tablePoint,charcount) {
	var param = {};
	var currentNo;
	var shopType;
	var shop ={
			queryParm : function(num){
				var aAccountNo = $("#aAccountNo").val();
				var aName = $("#aName").val();
				//param["currentPageIndex"] = num!=null?num:1;
				param["eachPageSize"] = 20;
				param["accountNo"] = aAccountNo;
				param["name"] = aName;
				return param;
			},
			bindData : function() {
				$("#busibox").html(_.template(gridMainTpl));
				shop.loadBsGridData(shop.queryParm(1));
//				if(param != null && param != ""){
//					param = {};
//				}
//				$(".operationsArea").html(marketEmployee);
//				shop.bindDataClick();
//				var param = shop.queryParm(1);
//				shop.bindTable(param);
			},
			loadBsGridData : function(queryParam){
				var gridObj;
				$(function(){
					gridObj = $.fn.bsgrid.init('gridMain',{
						url:{url:comm.UrlList["getEmployeeaccounList"],domain:"sportal"},//comm.domainList["sportal"]+comm.UrlList["getEmployeeaccounList"],
						otherParames:queryParam,
						pageSize:20,
						pageSizeSelect:true,
						displayBlankRows:false,
						stripeRows:true,
						operate:{
							add:function(record, rowIndex, colIndex, options){
								var sTpl = "";
								var id = record.id;
								var status = record.status;
								var accountNo = record.accountNo;
								var name = record.name;
								var job = record.job;
								var isBindName = record.isBindName;
								var userResourceNo = record.userResourceNo;
								var phone = record.phone;
								switch (colIndex) {
									case 0 :
										if(status != 3){
											if(accountNo != currentNo && accountNo!='0000'){
												sTpl = '<input type="checkbox" class="selected" data-id="'+id+'"/>';
											}
										}
										break;
									case 1 :
										if(status != 3){
											sTpl = accountNo;
										}
										break;
									case 2 :
										if(status != 3){
											sTpl = name;
										}
										break;
									case 3 :
										if(status != 3){
											sTpl = job;
										}
										break;
									case 4 :
										if(status != 3){
											if(accountNo!='0000'){
												if(isBindName!=""){
	          										sTpl ='('+isBindName+')';
												}else{
													sTpl = '';
												}
	          								if(userResourceNo != null && userResourceNo != ""){
										        sTpl +='<p>'+userResourceNo+'</p>';
								          		sTpl +='<p><a class="noBindCard" data-id="'+id+'">解除绑定互生卡</a></p>';
									          }else{
									          	sTpl +='<p><a class="yesBindCard" data-id="'+id+'">申请绑定互生卡</a></p>';
									          }
									        }
										}
										break;
									case 5 :
										if(status != 3){
											sTpl = phone;
										}
										break;
									case 6 :
										if(status != 3){
											if(status==0){
												sTpl = '启用';
											}else{
												sTpl = '禁用';
											}
										}
										break;
									case 7 :
										if(status != 3){
											if(accountNo!='0000'){
									          sTpl = '<a class="updateshop" data-id="'+id+'">编辑</a>';
									          if(currentNo != accountNo){
									          	sTpl += ' | ' ;
										          if(status == 0){ 
										          sTpl += ' <a class="disable_emp" data-id="'+id+'">禁用</a>';
										          }else if(status == 1){
										          sTpl += ' <a class="start_emp" data-id="'+id+'">启用</a>';
										          }
									          	sTpl += '   | <a class="delete_emp" data-id="'+id+'">删除</a>';
									           }
									           	if(shopType != 1){
									           sTpl += '  | <a class="store" data-id="'+id+'">所属营业点</a>'; 
									            }
									          sTpl += '  | <a class="role_emp" data-id="'+id+'">角色设置</a>';
									       }
										}
										break;
									default : 
										break;
								}
								return sTpl;
							}
						},//end operate;
						complete : function(options, XMLHttpRequest, textStatus){
							var other = eval("("+XMLHttpRequest.responseText+")").orther;
							 	currentNo = other.currentNo;
							 	shopType = other.shopType;
							//查询事件
							shop.bindDataClick();
							shop.bindTableClick();
							shop.bindJifenClick();
						}
					});//end gridObj;
				});//end $;
			},
			bindDataClick : function(){
			 $("#btnSearch").unbind().on('click',function(){
					param = shop.queryParm(1);
					//shop.bindTable(param);
					shop.loadBsGridData(param);
				})
			},
			bindTable : function(param){
					tablePoint.bindJiazai("#employeeList", true);
				ajaxRequest.getEmployeeaccounList(param,function(response){
					tablePoint.bindJiazai("#employeeList", false);
					//加载中间内容
					var html = _.template(tplList,response.data);
					$("#employeeList").html(html);
					$("#employeeTbl").DataTable({
						"scrollY":"325px",//垂直限制高度，需根据页面当前情况进行设置
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
					shop.bindJifenClick();
				})
			
			},
			bindTableClick : function(){
//				//分页
//				   $(".pageOrder").unbind().on('click',function(){
//				    	var num = $(this).attr("page");
//				    	if(num == '' || num == null){
//				    		return false;
//				    	}
//				    	var param = shop.queryParm(num);
//				    	shop.bindTable(param);
//				    });
//						 //分页输入
//				    	 $('#pageInput').unbind().on('keypress',function(event){
//				            if(event.keyCode == "13")    
//				            {
//				            	var num = $(this).val();
//				            	var totalPage = Number($(this).attr("totalPage"));
//					            	if(num > totalPage  || num <= 0 || isNaN(num)){
//					            		tablePoint.tablePoint("输入范围错误!请再次输入!");
//					            		return false;
//					            	}else{
//					            		var param = shop.queryParm(num);
//								    	shop.bindTable(param);
//					            	}
//				            }
//				       });
					
					/**
					 * 编辑用户信息事件
					 */
					$(".updateshop").unbind().on('click',function(){
						shop.updateEmployee(this);
					});
					
					/**
					 * 添加用户信息事件
					 */
					$("#addEmployee").unbind().on('click',function(){
							$("#paramList").html(add_tpl);
							shop.bindInputCheck();
							/**
							 * 确定添加用户事件
							 */
							$("#add_submit").unbind().on('click',function(){
								$("#add_employee_form").submit();
								shop.bindData();
							})
							/**
							 * 取消添加用户事件
							 */
							$("#add_cancle").unbind().on('click',function(){
								shop.bindData();
							})
							//添加验证
							administratorCheck.validate(shop);
					})
					
					/*--------------弹出框------------------*/
					$(".store").unbind().on('click',function(){
					  // var accountId = $(this).parents("tr").attr("data-id");
					  var accountId = $(this).attr("data-id");
					ajaxRequest.getSalerShopCcount("accountId="+accountId,function(shopResponse){
					   if(shopResponse.retCode == 200){
						  // var myguanlian =  shopResponse.data.account.shopId;
					   $("#dlg34").dialog({
							title:'所属营业点',
							show: true,
							modal: true,
							width : 600,
							close: function() { 
						        $(this).dialog('destroy');
							},
							open:function(){
											var shop_dlg_html = _.template(shop_dlg,shopResponse.data);
											$("#dlg34").html(shop_dlg_html);
											//tablePoint.checkBoxAll($("#allshop"),$(".search-table .popup_ckb[type='checkbox']"));
							},
							buttons: {
								"保存": function() {
									var shopIds = "";
									var shopType = "";
									var shopName = "";
									var shop = $('input:radio[name="shop"]');
									if(!shop.is(":checked")){
										$(this).dialog( "destroy" );
									}else{
										shopIds = $(shop).filter(":checked").val();
										//shopType = $.trim($(shop).filter(":checked").attr("valueType"));//屏蔽设置营业点时存入营业点类型字段
										shopName = $.trim($(shop).filter(":checked").attr("valueName"));
									}
//									var shopTps = "";
//									var num = 0;
//									$.each($("#dlg34").find("input:checked"),function(i,shop){
//										if(i == 0){
//											shopIds = $(shop).attr("data-id");
//											shopTps = $(shop).attr("data-type");
//										}else{
//											shopIds = shopIds + "," + $(shop).attr("data-id");	
//											shopTps = shopTps + "," + $(shop).attr("data-type");	
//										}
//										num=i;
//									})
//									if((myguanlian == null || myguanlian == "")&&(shopIds == null || shopIds == "")){
//										tablePoint.tablePoint("请选择关联的营业点！");
//										return false;
//									}
//									if(num>0){
//										tablePoint.tablePoint("实体店只能绑定一个！");
//										return false;
//									}
									
									var param  = {"accountId":accountId,"shopIds":shopIds,"shopType":shopType,"shopName":shopName};
									ajaxRequest.bindSalerShop(param,function(resp){
										if(resp.retCode == 200){
												if(shopIds != null && shopIds != ""){
													tablePoint.tablePoint("所属营业点成功！");
												}else if(shopIds == null || shopIds == ""){
													tablePoint.tablePoint("取消所属营业点成功！");
												}
										}else{
											tablePoint.tablePoint("所属营业点失败！");
										}
									})
									$(this).dialog( "destroy" );
								},
								"取消": function(){
									$(this).dialog( "destroy" );
								}
							}
					   });	
					  }
					})
				});
					
			
					/*--------------弹出框结束------------------*/
					tablePoint.checkBoxAll($("#all"),$(".selected[type='checkbox']"));
					
					/**
					 * 禁用用户事件
					 */
					//$("#employeeTbl").on("click",".disable_emp",function(){
					$("#gridMain").on("click",".disable_emp",function(){
						var $this = $(this);
						//var id = {"id":$(this).parents("tr").attr("data-id")};
						var id = {"id":$(this).attr("data-id")};
						$("#dlg34").html("<p class='table-del'>确定禁用吗?</p>");
						$( "#dlg34" ).dialog({
							title:'禁用',
							show: true,
							modal: true,
							close: function() { 
						        $(this).dialog('destroy');
							},
							buttons: {
								"确定": function() {
									ajaxRequest.disableEmployeeaccoun(id,function(disResponse){
										if(disResponse.retCode == 200){
											$this.html("启用");
											$this.parent().prev().html("禁用");
											$this.addClass("start_emp").removeClass("disable_emp");
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
					/**
					 * 启用用户事件
					 */
 					//$("#employeeTbl").on("click",".start_emp",function(){
					$("#gridMain").on("click",".start_emp",function(){
						var $this = $(this);
						//var id = {"id":$(this).parents("tr").attr("data-id")};
						var id = {"id":$(this).attr("data-id")};
						ajaxRequest.startEmployeeaccounList(id,function(disResponse){
							if(disResponse.retCode == 200){
									$this.html("禁用");
									$this.parent().prev().html("启用");
									$this.addClass("disable_emp").removeClass("start_emp");
							}
						})
					})
					
					
					/**
					 * 删除企业用户
					 */
					$(".delete_emp").unbind().on('click',function(){
						var $this = $(this);
						//var id = {"id":$(this).parents("tr").attr("data-id")};
						var id = {"id":$(this).attr("data-id")};
						$( "#store2" ).dialog({
							title:'删除',
							show: true,
							modal: true,
							buttons: {
								"确定": function() {
									ajaxRequest.deleteEmployeeaccounList(id,function(disResponse){
										if(disResponse.retCode == 200){
											tablePoint.tablePoint("删除企业用户成功！",function(){
												$this.parents("tr").remove();
											});
										}else{
											tablePoint.tablePoint("删除企业用户失败！");
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
					
					/**
					 * 批量删除事件
					 */
					$(".batch_del").unbind().on('click',function(){
						var ids = "";
						 var tbl = $("#gridMain").find(".selected:checked");
						if(tbl.length < 1){
							tablePoint.tablePoint("请选择要删除的行");
							return;
						}
						$.each(tbl,function(i,valeu){
							if(i == 0){
								//ids = $(valeu).parents("tr").attr("data-id");
								ids = $(valeu).attr("data-id");
							}else{
								//ids = ids + "," + $(valeu).parents("tr").attr("data-id");
								ids = ids + "," + $(valeu).attr("data-id");
							}
						})
						if( ids != ""){
							var id = {"id":ids}
							$( "#store2" ).dialog({
								title:'批量删除',
								show: true,
								modal: true,
								buttons: {
									"确定": function() {
										ajaxRequest.deleteEmployeeaccounList(id,function(disResponse){
											if(disResponse.retCode == 200){
												tablePoint.tablePoint("批量删除成功！",function(){
													$("#all").removeAttr("checked");
													//$("#employeeTbl").find(".selected:checked").parents("tr").remove();
													shop.bindData();
												});
											}else{
												tablePoint.tablePoint("批量删除失败！");
											}
										})
									    $( this ).dialog( "destroy" );
									},
									"取消": function() {
									    $( this ).dialog( "destroy" );
									}
								}
							});
						}
					})
				//绑定角色
				$(".role_emp").unbind().on('click',function(){
					//var id = $(this).parents("tr").attr("data-id");
					var id = $(this).attr("data-id");
					shop.bindRole(id);
				})
			},
			updateEmployee : function(obj){
				//var param = {"accountId":$(obj).parents("tr").attr("data-id")};
				var param = {"accountId":$(obj).attr("data-id")};
				ajaxRequest.getEmployeeaccount(param,function(resp){
					var html = _.template(frmtpl,resp);
					$("#paramList").html(html);
					shop.bindInputCheck();
					/**
					 * 确定修改事件
					 */
					$("#updata_submit").unbind().on('click',function(){
						$("#updata_employee_form").submit();
						shop.bindData();
					})
					/**
					 * 取消修改事件
					 */
					$("#cancle_update").unbind().on('click',function(){
						shop.bindData();
					})
					/**
					 * 重置新密码事件
					 */
					$("#restPwd_a").unbind().on('click',function(){
						var userId = $(this).attr("data-id");
						$("#PwdInput,#PwdInputTwo").html("");
						$("#newAccountPwd,#newAccountPwdTwo").val("");
						$("#restAccountPwd_dlg").dialog({
							title:'密码重置',
							show: true,
							modal: true,
							width:456,
							close: function() { 
						        $(this).dialog('destroy');
							},
							buttons: {
								"保存": function() {
									var newAccountPwd = $("#newAccountPwd").val();
									var newAccountPwdTwo = $("#newAccountPwdTwo").val();
									if(newAccountPwd.length != 6){
										$("#PwdInput").html("密码只能为6位!");
										return;
									}else{
										$("#PwdInput").html("");
									}
									if(newAccountPwd != newAccountPwdTwo){
										$("#PwdInputTwo").html("两次密码不相等,请重新输入!");
										return;
									}else{
										$("#PwdInputTwo").html("");
									}
									var param = {"userId":userId,"newAccountPwd":newAccountPwd}
									ajaxRequest.resetAccountPwd(param,function(resp){
										if(resp.retCode == 200){
											tablePoint.tablePoint("修改密码成功！");
										}else if(resp.retCode == 811){
											tablePoint.tablePoint("新密码与老密码相同,无效修改!");
										}else{
											tablePoint.tablePoint("修改密码失败！请稍后重试!");
										}
									})
									$(this).dialog( "destroy" );
								}
							}
					   });
					})
					administratorCheck.validateUpdate(shop);		
				})
			$("#paramList").show();
			},
			bindJifenClick : function(){
				/**
				 * 绑定互生卡事件
				 */
				$(".yesBindCard").unbind().on('click',function(){
					var $this = $(this);
					//var param = {"accountId":$($this).parents("tr").attr("data-id")};
					var param = {"accountId":$($this).attr("data-id")};
					$("#resourceNoColor").html("");
					var re = /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{3}|\d{3}[1-9]|\d{2}[1-9]\d|\d[1-9]\d{2})$/;
					$( "#score_card" ).dialog({
						title:'绑定互生卡',
						show: true,
						modal:true,
						width:490,
						close: function() { 
						        $(this).dialog('destroy');
						},
						open:function(){
							ajaxRequest.getEmployeeaccount(param,function(resp){
								var account = resp.data["account"];
								$("#card_name").val(account.accountNo);
								$("#card_fullName").val(account.name);
								$("#card_resourceNo").val(account.userResourceNo);
								$("#card_resourceNo").attr("disabled",false);
								$("#card_resourceNo").removeClass("disabled");
							})
						},
						buttons: {
							"申请绑定": function() {
								//var id = $($this).parents("tr").attr("data-id");
								var id = $($this).attr("data-id");
								var resourceNo = $("#card_resourceNo").val();
								var card_name = $("#card_name").val();
								var bindParam = {};
								bindParam["userId"] = id;
								bindParam["loginId"] = card_name;
								bindParam["personResNo"] = resourceNo;
								if(resourceNo.length == 11 && re.test(resourceNo)){
									$("#resourceNoColor").html("");
								}else{
									$("#resourceNoColor").html("<p style='color: red;width:360px'>输入规则不合理,请更换互生卡号!</p>");
									return;
								}
								ajaxRequest.bindResourceNo(bindParam,function(resp){
									if(resp.retCode == 200){
											var num = $("#thisPage").html();
											var param = shop.queryParm(num);
											//shop.bindTable(param);
											shop.loadBsGridData(param);
											$("#score_card").dialog("destroy");
									}else if(resp.retCode == 813){
										tablePoint.tablePoint("绑定互生卡失败!原因：已经存在绑定用户,请更换!");
									}else{
										tablePoint.tablePoint("绑定互生卡失败!请稍后重试!");
									}
								})
						   },
						   "取消": function(){
							   $(this).dialog('destroy');
						   }
					  }
				});
			});
				
				/**
				 * 解除绑定互生卡事件
				 */
				$(".noBindCard").unbind().on('click',function(){
					var $this = $(this);
					//var param = {"accountId":$($this).parents("tr").attr("data-id")};
					var param = {"accountId":$($this).attr("data-id")};
					$("#resourceNoColor").html("");
					var re = /^\d+$/;
					$( "#score_card" ).dialog({
						title:'解绑互生卡',
						show: true,
						modal:true,
						width:500,
						close: function() { 
						        $(this).dialog('destroy');
						},
						open:function(){
							ajaxRequest.getEmployeeaccount(param,function(resp){
								var account = resp.data["account"];
								$("#card_name").val(account.accountNo);
								$("#card_fullName").val(account.name);
								$("#card_resourceNo").val(account.userResourceNo);
								$("#card_resourceNo").attr("disabled","disabled");
								$("#card_resourceNo").addClass("disabled");
							})
						},
						buttons: {
							"解除绑定": function() {
								//var id = $($this).parents("tr").attr("data-id");
								var id = $($this).attr("data-id");
								var resourceNo = $("#card_resourceNo").val();
								var card_name = $("#card_name").val();
								var bindParam = {};
								bindParam["userId"] = id;
								ajaxRequest.ubindResourceNo(bindParam,function(resp){
									if(resp.retCode == 200){
											var num = $("#thisPage").html();
											var param = shop.queryParm(num);
											//shop.bindTable(param);
											shop.loadBsGridData(param);
											$("#score_card").dialog("destroy");
									}else{
										tablePoint.tablePoint("解绑互生卡失败!请稍后重试!");
									}
								})
						   },
						   "取消": function(){
							   $(this).dialog('destroy');
						   }
					  }
				});
			});
		},
			bindAdd : function(){
				var accountParam = $("#add_employee_form").serializeJson();
					tablePoint.bindJiazai("#paramList", true);
				ajaxRequest.addEmployeeaccount(accountParam,function(resp){
					tablePoint.bindJiazai("#paramList", false);
					if(resp.retCode == 200){
						tablePoint.tablePoint("添加企业用户信息成功！",function(){
							//shop.bindData();
							shop.loadBsGridData(shop.queryParm(1));
						});
					}else if(resp.retCode == 812){
						tablePoint.tablePoint("添加企业用户信息失败！原因:登录用户名已存在！请更换！");
					}else if(resp.retCode == 206){
						tablePoint.tablePoint("操作员信息不符合规范，包含违禁字:<br>"+resp.data+"<br>请修改之后,添加操作员！");
					}else{
						tablePoint.tablePoint("添加企业用户信息失败！请稍后重试!");
					}
				})
			},
			bindUpdate : function(){
				var accountParam = $("#updata_employee_form").serializeJson();
					tablePoint.bindJiazai("#paramList", true);
				ajaxRequest.updateEmployeeaccount(accountParam,function(resp){
					tablePoint.bindJiazai("#paramList", false);
					if(resp.retCode == 200){
						tablePoint.tablePoint("修改企业用户信息成功！",function(){
							//shop.bindData();
							shop.loadBsGridData(shop.queryParm(1));
						});
					}else if(resp.retCode == 812){
						tablePoint.tablePoint("修改企业用户信息失败！原因:登录用户名已存在！请更换！");
					}else if(resp.retCode == 206){
						tablePoint.tablePoint("操作员信息不符合规范，包含违禁字:<br>"+resp.data+"<br>请修改之后,修改操作员！");
					}else{
						tablePoint.tablePoint("修改企业用户信息失败！请稍后重试!");
					}
				})
			},
			bindRole : function(id){
				ajaxRequest.getEnterperiseRoleList(null, function(response) {
					ajaxRequest.queryRoleListByUserId("id="+id,function(responses){
						response["responses2"] = responses;
						var guanlian = responses.data.length;
					var html = _.template(addrole, response);
					$('#dlg34').html(html);
					$("#dlg34").dialog({
								title : "角色关联",
								width : "600",
								show: true,
								modal : true,
								close: function() { 
								        $(this).dialog('destroy');
								},
								buttons : {
									'立即设置' : function() {
										var param = {};
										param["userId"] = id;
										var roleId ="";
										  $("input[name='authCheckBox']:checked").each(function(){
											  roleId += $(this).val()+",";
										  })
										if((roleId == null || roleId == "")&& guanlian == 0){
												tablePoint.tablePoint("请选择绑定的角色！");
												return false;
										}  
										param["roleId"] = roleId;
										//start 判断 【0001-0010】10个特定角色只能绑定一个  xiongzx add  
										var rolenum = 0;
										if(roleId.indexOf("0001") > -1){
											rolenum++;
										}
										if(roleId.indexOf("0002") > -1){
											rolenum++;
										}
										if(roleId.indexOf("0003") > -1){
											rolenum++;
										}
										if(roleId.indexOf("0004") > -1){
											rolenum++;
										}
										if(roleId.indexOf("0005") > -1){
											rolenum++;
										}
										if(roleId.indexOf("0006") > -1){
											rolenum++;
										}
										if(roleId.indexOf("0007") > -1){
											rolenum++;
										}
										if(roleId.indexOf("0008") > -1){
											rolenum++;
										}
										if(roleId.indexOf("0009") > -1){
											rolenum++;
										}
										if(roleId.indexOf("0010") > -1){
											rolenum++;
										}
										if(rolenum > 1){
											tablePoint.tablePoint("特定角色只能绑定一个！");
											return false;
										}  
										//end 判断 【0001-0010】10个特定角色只能绑定一个  xiongzx add 
									ajaxRequest.addUserRole("raList="+JSON.stringify(param),function(response){
										if(response.retCode == 200){
											
											if(roleId != null && roleId != ""){
												tablePoint.tablePoint("添加角色成功!",function(){
													$("#dlg34").dialog("destroy");
												});
											}else if(roleId == null || roleId == ""){
												tablePoint.tablePoint("取消角色成功!",function(){
													$("#dlg34").dialog("destroy");
												});
											}
										}else{
											tablePoint.tablePoint("添加角色失败!请稍后重试!",function(){
												$("#dlg34").dialog("destroy");
											});
										}
									})
									
									},
									'下次再说' : function() {
										$(this).dialog("destroy");
									}
								}
						});
					//全选
					tablePoint.checkBoxAll($("#allrole"),$(".roleBox input[type='checkbox']"));
				});
			});
		},
		bindInputCheck : function(){
				$('#adName').charcount({
					maxLength: 25,
					preventOverage: true,
					position : 'after'
				});
				$('#adJob').charcount({
					maxLength: 50,
					preventOverage: true,
					position : 'after'
			});
		}
	}
	return shop;
});
