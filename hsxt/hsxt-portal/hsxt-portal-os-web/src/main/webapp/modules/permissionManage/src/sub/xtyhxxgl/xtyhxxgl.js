define(
		[ 'homeDat/login','text!permissionManageTpl/sub/xtyhxxgl/xtyhxxgl.html', 'text!permissionManageTpl/sub/xtyhxxgl/xtyhxxgl_opt.html',
				'text!permissionManageTpl/sub/xtyhxxgl/xtyhxxgl_opt_tree.html',
				'text!permissionManageTpl/sub/xtyhxxgl/xtyhxxgl_opt_czmm.html', 'jqueryztree' ],
		function(tokenRequest,xtyhxxglTpl, xtyhxxgl_optTpl, xtyhxxgl_opt_treeTpl, xtyhxxgl_opt_czmmTpl) {
			var self = null;
			var gridObj = null;
			var flag = true;
			return {

				showPage : function(tabid) {
					self = this;
					$('#main-content > div[data-contentid="' + tabid + '"]').html(_.template(xtyhxxglTpl));
					// 下拉列表
					$("#xtyhxxgl_tb_yhlx").selectList({
						borderWidth : 1,
						borderColor : '#CCC',
						options : [ {
							name : '所有',
							value : '',
							selected : true
						}, {
							name : '管理员',
							value : '1'
						}, {
							name : '非管理员',
							value : '0'
						} ]
					}).change(function(e) {
						console.log($(this).val());
					});
					// end

					// 按钮事件绑定
					$('#xtyhxxgl_tb_addBtn').click(function() {
						self.xtyhxxgl_opt_add();
					});

					$('#xtyhxxgl_tb_queryBtn').click(function() {
						self.xtyhxxgl_opt_query();
					});
					// 按钮事件绑定 end

					// 默认页面打开时点击查询显示数据
					$('#xtyhxxgl_tb_queryBtn').click();

				},
				// end showPage

				xtyhxxgl_opt_query : function() {
					var isAdmin = $("#xtyhxxgl_tb_yhlx").attr("optionValue");
					// var platformCode = $("#xtcsxgl_query_csxdm").val();
					var subSystemCode = $("#xtyhxxgl_tb_zxt").val();
					var operCustId = $("#xtyhxxgl_tb_khh").val();
					var userName = $("#xtyhxxgl_tb_dlzh").val();
					var realName = $("#xtyhxxgl_tb_xm").val();

					// 查询条件
					params = {
						custId : $.cookie('custId')
					};
					if (isAdmin != '')
						params['isAdmin'] = isAdmin;
					if (subSystemCode != '')
						params['subSystemCode'] = subSystemCode;
					if (operCustId != '')
						params['operCustId'] = operCustId;
					if (userName != '')
						params['userName'] = userName;
					if (realName != '')
						params['realName'] = realName;

					gridObj = $.fn.bsgrid.init('xtyhxxgl_ql', {
						url : comm.domainList.osWeb + comm.UrlList["authOperatorList"],
						otherParames : params,
						pageSize : 10,
						stripeRows : true, // 行色彩分
						displayBlankRows : false,
						operate : {
							detail : self.grid_resetPwd,
							add : self.grid_grant,
							edit : self.grid_edit,
							del : self.grid_renderIsAdmin,
							renderImg : null
						}
					})

				},
				
				// end xtyhxxgl_opt_query
				grid_resetPwd : function(record, rowIndex, colIndex, options) {
					if (colIndex != 6)
						return null;
					var link1 = null, obj = gridObj.getRecord(rowIndex);

					link1 = $('<a>重置密码</a>').click(function(e) {

						self.xtyhxxgl_opt_czmm(obj);

					}.bind(this));

					return link1;
				}.bind(this),
				
				// end grid_resetPwd
				grid_edit : function(record, rowIndex, colIndex, options) {
					if (colIndex != 6)
						return null;
					var link1 = null, obj = gridObj.getRecord(rowIndex);

					link1 = $('<a>编辑</a>').click(function(e) {

						self.xtyhxxgl_opt_bjsq(obj);

					}.bind(this));

					return link1;
				}.bind(this), 
				
				// end grid_edit
				grid_grant : function(record, rowIndex, colIndex, options) {
					if (colIndex != 6)
						return null;
					var link1 = null, obj = gridObj.getRecord(rowIndex);

					link1 = $('<a>授权</a>').click(function(e) {

						self.xtyhxxgl_opt_sq(obj);

					}.bind(this));

					return link1;
				}.bind(this),
				
				// end grid_grant
				grid_renderIsAdmin : function(record, rowIndex, colIndex, options) {
					var link1 = null;
					if (colIndex == 5) {
						// var obj = gridObj.getRecord(rowIndex);
						link1 = record['isAdmin'];
						if (link1 == 1) {
							link1 = '管理员';
						} else {
							link1 = '非管理员';
						}
					}
					return link1;
				}.bind(this),
				// end grid_renderIsAdmin

				xtyhxxgl_opt_add : function() {

					var that = this;

					$('#xtyhxxgl_dlg').html(xtyhxxgl_optTpl);
					// 获取随机token
					tokenRequest.getToken(function(response) {
						if (response.retCode == 22000) {
							//alert(response.data);
							$('#randomToken').val(response.data);
						} else {
							alert('请刷新页面后重试');
						}

					}, {});
					
					// 弹出框//
					$("#xtyhxxgl_dlg").dialog({
						title : "新增",
						width : "1000",
						modal : true,
						closeIcon : true,
						buttons : {
							"保存新增" : function() {
								var elthis=this;
								if (!that.validate()) {
									return;
								} else {
									var yhlx= $("#xtyhxxgl_opt_yhlx").attr('optionValue');
									var yhzt = $("#xtyhxxgl_opt_zt").attr('optionValue');
									var datas = $("#xtyhxxgl_optForm").serializeArray();
									
									 $.each(datas,function(n,value) { 
										 if(value.name == 'pwdLogin'){
											 if($('#xtyhxxgl_opt_dlmm').val() != "********"){
												 value.value = comm.encrypt($('#xtyhxxgl_opt_dlmm').val(), $('#randomToken').val());
											 }
										 }
										 else if(value.name == 'isAdmin'){
											 value.value = yhlx;
										 }
										 else if(value.name == 'isactive'){
											 value.value = yhzt;
										 }
									 }); 
									 
									comm.requestFun("authOperatorAdd",datas,function(obj){
										$(elthis).dialog("destroy");
										$('#xtyhxxgl_ql_pt_refreshPage').click();
									},comm.lang("permissionManage"));
								}
							},
							"关闭" : function() {
								$(this).dialog("destroy");
							}
						}
					});
					// end//
					
					// 表单值初始化//

					// 下拉列表//

					$("#xtyhxxgl_opt_yhlx").selectList({
						width : 250,
						optionWidth : 255,
						options : [ {
							name : '管理员',
							value : '1'
						}, {
							name : '非管理员',
							value : '0',
							selected : true
						} ]
					}).change(function(e) {
						console.log($(this).val());
					});

					$("#xtyhxxgl_opt_zt").selectList({
						width : 250,
						optionWidth : 255,
						options : [ {
							name : '有效',
							value : 'Y',
							selected : true
						}, {
							name : '无效',
							value : 'N'
						} ]
					}).change(function(e) {
						console.log($(this).val());
					});

					// end//

					$(
							'#xtyhxxgl_opt_khh, #xtyhxxgl_opt_dlzh, #xtyhxxgl_opt_dlmm, #xtyhxxgl_opt_mmbb, #xtyhxxgl_opt_mmyz, #xtyhxxgl_opt_xm, #xtyhxxgl_opt_zw, #xtyhxxgl_opt_sj, #xtyhxxgl_opt_yx, #xtyhxxgl_opt_pt, #xtyhxxgl_opt_zxt, #xtyhxxgl_opt_bzxx')
							.val('');

					// end//
					$('#xtyhxxgl_li_khh').addClass('none');

				},
				// end xtyhxxgl_opt_add
				
				jsxxgl_opt_sq : function(obj) {
					var that = this;
					$('#jsxxgl_dlg').html(xtyhxxgl_opt_treeTpl);
					that.jsAuth();
				},
				// end jsxxgl_opt_sq
				
				xtyhxxgl_opt_bjsq : function(obj) {

					var that = this;

					$('#xtyhxxgl_dlg').html(xtyhxxgl_optTpl);
					// 获取随机token
					tokenRequest.getToken(function(response) {
						if (response.retCode == 22000) {
							//alert(response.data);
							$('#randomToken').val(response.data);
						} else {
							alert('请刷新页面后重试');
						}

					}, {});
					// 弹出框//
					$("#xtyhxxgl_dlg").dialog({
						title : "编辑",
						width : "1000",
						modal : true,
						closeIcon : true,
						buttons : {
							"保存修改" : function(obj) {
								var elthis=this;
								if (!that.validate()) {
									return;
								} else {
									var yhlx= $("#xtyhxxgl_opt_yhlx").attr('optionValue');
									var yhzt = $("#xtyhxxgl_opt_zt").attr('optionValue');
									var datas = $("#xtyhxxgl_optForm").serializeArray();
									
									 $.each(datas,function(n,value) { 
										 if(value.name == 'pwdLogin'){
											 if($('#xtyhxxgl_opt_dlmm').val() != "********"){
												 value.value = comm.encrypt($('#xtyhxxgl_opt_dlmm').val(), $('#randomToken').val());
											 }
										 }
										 else if(value.name == 'isAdmin'){
											 value.value = yhlx;
										 }
										 else if(value.name == 'isactive'){
											 value.value = yhzt;
										 }
									 }); 
									comm.requestFun("authOperatorUpdate",datas,function(obj){
										$(elthis).dialog("destroy");
										$('#xtyhxxgl_ql_pt_refreshPage').click();
									},comm.lang("permissionManage"));
								}
							},
							"关闭" : function() {
								$(this).dialog("destroy");
							}
						}
					});
					// 授权
					var digsq = function() {

					}
					// end//

					// 表单值初始化//

					// 下拉列表//

					$("#xtyhxxgl_opt_yhlx").selectList({
						width : 250,
						optionWidth : 255,
						options : [ {
							name : '管理员',
							value : '1'
						}, {
							name : '非管理员',
							value : '0',
							selected : true
						} ]
					}).change(function(e) {
						console.log($(this).val());
					});
					var isAdmin = obj.isAdmin;
					if(isAdmin == 1){
						$('#xtyhxxgl_opt_yhlx').val("管理员");
						$("#xtyhxxgl_opt_yhlx").attr('optionValue', 1);
					}else{
						$('#xtyhxxgl_opt_yhlx').val("非管理员");
						$("#xtyhxxgl_opt_yhlx").attr('optionValue', 0);
					};

					$("#xtyhxxgl_opt_zt").selectList({
						width : 250,
						optionWidth : 255,
						options : [ {
							name : '有效',
							value : 'Y',
							selected : true
						}, {
							name : '无效',
							value : 'N'
						} ]
					}).change(function(e) {
						console.log($(this).val());
					});
					var isactive = obj.isactive;
					if(isactive == 'Y'){
						$('#xtyhxxgl_opt_zt').val("有效");
					}else{
						$('#xtyhxxgl_opt_zt').val("无效");
					};

					// end//

					$('#xtyhxxgl_opt_khh').val(obj.operCustId).attr('readonly', 'readonly');
					$('#xtyhxxgl_opt_dlzh').val(obj.userName).attr('readonly', 'readonly');
					$('#xtyhxxgl_opt_dlmm').val(obj.pwdLogin);//.attr('readonly', 'readonly');
					$('#xtyhxxgl_opt_mmbb').val(obj.pwdLoginVer);//.attr('readonly', 'readonly');
					$('#xtyhxxgl_opt_mmyz').val(obj.pwdLoginSaltValue);//.attr('readonly', 'readonly');
					
					$('#xtyhxxgl_opt_xm').val(obj.realName);
					$('#xtyhxxgl_opt_zw').val(obj.duty);
					$('#xtyhxxgl_opt_sj').val(obj.phone);
					$('#xtyhxxgl_opt_yx').val(obj.email);
					$('#xtyhxxgl_opt_pt').val(obj.platformCode);
					$('#xtyhxxgl_opt_zxt').val(obj.subSystemCode);
					$('#xtyhxxgl_opt_bzxx').val(obj.remark);
					
					

					// end//

				},
				
				xtyhxxgl_opt_czmm : function() {
					var that = this;
					$('#xtyhxxgl_dlg').html(xtyhxxgl_opt_czmmTpl);

					// 弹出框//
					$("#xtyhxxgl_dlg").dialog({
						title : "重置密码",
						width : "440",
						modal : true,
						closeIcon : true,
					    buttons : {
						"保存" : function(obj) {
							var elthis=this;
							if (!that.validate_czmm()) {
								return;
							} else {
								var yhlx= $("#xtyhxxgl_opt_yhlx").attr('optionValue');
								var yhzt = $("#xtyhxxgl_opt_zt").attr('optionValue');
								var datas = $("#xtyhxxgl_optForm").serializeArray();
								
								datas[6].value = yhlx;
								datas[7].value = yhzt;
								comm.requestFun("authOperatorUpdate",datas,function(obj){
									$(elthis).dialog("destroy");
									$('#xtyhxxgl_ql_pt_refreshPage').click();
								},comm.lang("permissionManage"));
							}
						},
						"关闭" : function() {
							$(this).dialog("destroy");
						}
					}
					});
					// end//

					// 表单值初始化//

					$('#xtyhxxgl_opt_dlmm, #xtyhxxgl_opt_mmqr').val('');

					// end//

				},
				
				xtyhxxgl_opt_sq : function(obj) {
					var that = this;
					$('#xtyhxxgl_dlg').html(xtyhxxgl_opt_treeTpl);
					that.jsAuth(obj);
				},
				validate : function() {
					return comm.valid({
						formID : '#xtyhxxgl_optForm',
						rules : {
							
//							platformCode : {
//								required : true
//							},
//							subSystemCode : {
//								required : true
//							},
							userName : {
								required : true
							},
							pwdLogin : {
								required : true
							}
						},
						messages : {
//							platformCode : {
//								required : '必填'
//							},
//							subSystemCode : {
//								required : '必填'
//							},
							userName : {
								required : '必填'
							},
							pwdLogin : {
								required : '必填'
							}
						}
					});
				},
				
				validate_czmm : function() {
					return comm.valid({
						formID : '#xtyhxxgl_opt_czmmForm',
						rules : {
							xtyhxxgl_opt_dlmm : {
								required : true
							},
							xtyhxxgl_opt_mmqr : {
								required : true
							}
						},
						messages : {
							xtyhxxgl_opt_dlmm : {
								required : '必填'
							},
							xtyhxxgl_opt_mmqr : {
								required : '必填'
							}
						}
					});
				},
				
				// 角色授权
				// flag：调用标识，从新增修改的提交按钮点击调用
				jsAuth : function(obj, flag) {

					// 树型 角色 展示
					var setting = {
						check : {
							enable : true,
							chkDisabledInherit : false,
							nocheckInherit :false
						},
						data : {
							simpleData : {
								enable : true
							}
						}
					};

					self.operCustId=obj.operCustId;
					self.userName=obj.userName;
//					debugger;
					var params ={operCustId:self.operCustId};
//					params.roleId=roleId;
					comm.requestFun("authOperatorListRole",params,function(obj){
						//
						var ret=obj;
						var data=ret.data;
//						data.params=params;
//						debugger;
						$.fn.zTree.init($("#xtyhxxgl_sqTree"), setting, data);
						var aa=1;
					},comm.lang("permissionManage"));
					
					// $.fn.zTree.init($("#xtyhxxgl_sqTree"), setting, data);
					$("#xtyhxxgl_dlg").dialog({
						title : '授权To:'+self.userName+"("+self.operCustId+")",
						width : "500",
						modal : true,
						closeIcon : true,
						buttons : {
							"保存授权" : function() {
								var elthis = this;
								var treeObj = $.fn.zTree.getZTreeObj("xtyhxxgl_sqTree")
								var nodes = treeObj.getCheckedNodes(true), v = '';
								var ids=[];
								for ( var i = 0; i < nodes.length; i++) {
									ids.push(nodes[i].id);// 获取选中节点的值
									v += nodes[i].name + ",";
								}
								console.log(ids);
//								debugger;
								var params ={operCustId:self.operCustId,
										ids:ids
								
								};
//								debugger;
								comm.requestFun("authOperatorSetRole",params,function(obj){
									var ret=obj;
//									debugger;
									$(elthis).dialog("destroy");
//									$(this).dialog('destroy');
								},comm.lang("permissionManage"));
								var test=1;
							},
							"关闭" : function() {
								$(this).dialog("destroy");
							}
						}
					});
					// end//
				}
			}
		});