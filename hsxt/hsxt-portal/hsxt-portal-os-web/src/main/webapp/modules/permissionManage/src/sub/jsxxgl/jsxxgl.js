define([ 'text!permissionManageTpl/sub/jsxxgl/jsxxgl.html', 'text!permissionManageTpl/sub/jsxxgl/jsxxgl_opt.html',
		'text!permissionManageTpl/sub/jsxxgl/jsxxgl_opt_tree.html', 'jqueryztree',"permissionManageLan" ], function(jsxxglTpl, jsxxgl_optTpl,
		jsxxgl_opt_treeTpl) {
	var self = null;
	var gridObj = null;
	return {
		showPage : function(tabid) {
			
			
			$('#main-content > div[data-contentid="' + tabid + '"]').html(_.template(jsxxglTpl));

			self = this;

			/* 下拉列表 */
			$("#jsxxgl_tb_jslx").selectList({
				borderWidth : 1,
				borderColor : '#CCC',
				options : [ {
					name : '所有',
					value : '',
					selected : true
				}, {
					name : '全局',
					value : '1'
				}, {
					name : '平台',
					value : '2'
				}, {
					name : '私有',
					value : '3'
				} ]
			}).change(function(e) {
				console.log($(this).val());
			});
			/* end */

//			var ptbm = $("#jsxxgl_tb_ptbm").val();
//			var zxtbm = $("#jsxxgl_tb_zxtbm").val();
//			var jslx = $("#jsxxgl_tb_jslx").attr("optionValue");
//			var qykhh = $("#jsxxgl_tb_qykhh").val();
//			var queryConditon = {"platformCode":ptbm,"subSystemCode":zxtbm,"roleType":jslx,"entCustId":qykhh};
//			
//			gridObj = comm.getCommBsGrid('queryList_jsxxgl', "jsxxglSearch", queryConditon, null, null, null, null,
//					self.btn_opt_edit, null);
			/* end */

			$('#jsxxgl_tb_addBtn').click(function() {
				self.jsxxgl_opt_xz();
			});
			
			
			//查询按钮
			$('#jsxxgl_tb_queryBtn').click(function(){
				//debugger ;
				var ptbm = $("#jsxxgl_tb_ptbm").val();
				var zxtbm = $("#jsxxgl_tb_zxtbm").val();
				var jslx = $("#jsxxgl_tb_jslx").attr("optionValue");
				var qykhh = $("#jsxxgl_tb_qykhh").val();
				var queryConditon = {"platformCode":ptbm,"subSystemCode":zxtbm,"roleType":jslx,"entCustId":qykhh};
				//comm.getCommBsGrid(gridId, url, params, langObj, detail, del, add, edit, renderImg)
				gridObj = comm.getCommBsGrid('queryList_jsxxgl', "jsxxglSearch", queryConditon, null, 
						self.btn_opt_detail, null, self.btn_opt_add,self.btn_opt_edit, null);
			});
			
//			debugger ;
			$('#jsxxgl_tb_queryBtn').click();

		},
		btn_opt_detail : function(record, rowIndex, colIndex, options) {
			if (colIndex == 6) {
				var obj = gridObj.getRecord(rowIndex);
				if (obj.roleType == 1) {
					return '全局'
				}
				if (obj.roleType == 2) {
					return '平台'
				}
				if (obj.roleType == 3) {
					return '私有'
				}
				return obj['roleType'];
			}
		}.bind(this),
		btn_opt_add : function(record, rowIndex, colIndex, options) {
			if (colIndex == 6) {
				return null;
			}
			var link1 = null,
			obj = gridObj.getRecord(rowIndex);
				link1 = $('<a>授权</a>').click(function(e) {
					self.jsxxgl_opt_sq(record);
				}.bind(this) ) ;	
			
			return link1;
		}.bind(this),
		btn_opt_edit : function(record, rowIndex, colIndex, options) {
			if (colIndex == 6) {
				return null;
			}
			var link1 = null,
			obj = gridObj.getRecord(rowIndex);
		
	
				link1 = $('<a>编辑</a>').click(function(e) {
					self.jsxxgl_opt_bj(obj);
				}.bind(this) ) ;	
			
			return link1;
		}.bind(this),

		jsxxgl_opt_xz : function() {

			var that = this;

			$('#jsxxgl_dlg').html(jsxxgl_optTpl);

			/* 弹出框 */
			$("#jsxxgl_dlg").dialog({
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
							var jslx= $("#jsxxgl_opt_jslx").attr('optionValue');
							var jszt = $("#jsxxgl_opt_zt").attr('optionValue');
							var datas = $("#jsxxgl_optForm").serializeArray();
							datas[2].value = jslx;
							datas[6].value = jszt;
							comm.requestFun("jsxxglAdd",datas,function(obj){
								$(elthis).dialog("destroy");
								$('#queryList_jsxxgl_pt_refreshPage').click();
							},comm.lang("permissionManage"));
						}
					},
					"取消" : function() {
						$(this).dialog("destroy");
					}
				}
			});
			/* end */

			/* 表单值初始化 */

			/* 下拉列表 */

			$("#jsxxgl_opt_jslx").selectList({
				width : 250,
				optionWidth : 255,
				options : [ {
					name : '全局',
					value : '1'
				}, {
					name : '平台',
					value : '2',
					selected : true
				}, {
					name : '私有',
					value : '3'
				} ]
			}).change(function(e) {
				console.log($(this).val());
			});

			$("#jsxxgl_opt_zt").selectList({
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

			/* end */

			$('#jsxxgl_opt_jsid, #jsxxgl_opt_jsmc, #jsxxgl_opt_zxxbm, #jsxxgl_opt_ptbm, #jsxxgl_opt_qykhh, #jsxxgl_opt_jsms')
					.val('');

			/* end */

		},
		jsxxgl_opt_sq : function(obj) {
			var that = this;
			$('#jsxxgl_dlg').html(jsxxgl_opt_treeTpl);
			that.jsAuth(obj);

		},
		jsxxgl_opt_bj : function(obj) {
			var that = this;

			$('#jsxxgl_dlg').html(jsxxgl_optTpl);

			/* 弹出框 */
			$("#jsxxgl_dlg").dialog({
				title : "编辑",
				width : "1000",
				modal : true,
				closeIcon : true,
				buttons : {
					"保存修改" : function() {
						var elthis=this;
						if (!that.validate()) {
							return;
						} else {
							var jslx= $("#jsxxgl_opt_jslx").attr('optionValue');
							var jszt = $("#jsxxgl_opt_zt").attr('optionValue');
							var datas = $("#jsxxgl_optForm").serializeArray();
							datas[2].value = jslx;
							datas[6].value = jszt;
							comm.requestFun("jsxxglUpdate",datas,function(obj){
								$(elthis).dialog("destroy");
								$('#queryList_jsxxgl_pt_refreshPage').click();
							},comm.lang("permissionManage"));
						}
					},
					"取消" : function() {
						$(this).dialog("destroy");
					}
				}
			});
			/* end */

			/* 表单值初始化 */

			/* 下拉列表 */

			$("#jsxxgl_opt_jslx").selectList({
				width : 250,
				optionWidth : 255,
				options : [ {
					name : '全局',
					value : '1'
				}, {
					name : '平台',
					value : '2',
					selected : true
				}, {
					name : '私有',
					value : '3'
				} ]
			}).change(function(e) {
				console.log($(this).val());
			});
			
			var roleType = obj.roleType;
			if(roleType == 1){
				$('#jsxxgl_opt_jslx').val("全局");
			}
			if(roleType == 2){
				$('#jsxxgl_opt_jslx').val("平台");
			}
			if(roleType == 3){
				$('#jsxxgl_opt_jslx').val("私有");
			}
			

			$("#jsxxgl_opt_zt").selectList({
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

			/* end */

			$('#jsxxgl_opt_jsid').val(obj.roleId).attr('readonly', 'readonly');
			$('#jsxxgl_opt_jsmc').val(obj.roleName);
			$('#jsxxgl_opt_zxxbm').val(obj.subSystemCode);
			$('#jsxxgl_opt_ptbm').val(obj.platformCode);
			$('#jsxxgl_opt_qykhh').val(obj.entCustId);
			$('#jsxxgl_opt_jsms').val(obj.roleDesc);

			/* end */

		},
		validate : function() {
			return comm.valid({
				formID : '#jsxxgl_optForm',
				rules : {
					roleId : {
						required : true
					},
					roleName : {
						required : true
					},
					subSystemCode : {
						required : true
					}
				},
				messages : {
					roleId : {
						required : '必填'
					},
					roleName : {
						required : '必填'
					},
					subSystemCode : {
						required : '必填'
					}
				}
			});
		},
		// 角色授权
		// flag：调用标识，从新增修改的提交按钮点击调用
		jsAuth : function(record, flag) {
			// 树型 角色 展示
			var setting = {
				check : {
					enable : true,
					chkDisabledInherit : true
				},
				data : {
					simpleData : {
						enable : true
					}
				}
			};
			
			self.roleId=record.roleId;
			self.roleName=record.roleName;
//			debugger;
			var params ={roleId:self.roleId};
//			params.roleId=roleId;
			comm.requestFun("jsxxglListPerm",params,function(obj){
				var ret=obj;
				var data=ret.data;
//				data.params=params;
//				debugger;
				$.fn.zTree.init($("#jxxxgl_sqTree"), setting, data);
				var aa=1;
			},comm.lang("permissionManage"));

			

			$("#jsxxgl_dlg").dialog({
				title : '授权To:'+self.roleName+"("+self.roleId+")",
				width : 680,
				closeIcon : true,
				buttons : {
					"保存授权" : function() {
						var elthis = this;
						var treeObj = $.fn.zTree.getZTreeObj("jxxxgl_sqTree")
						var nodes = treeObj.getCheckedNodes(true), v = '';
						var ids=[];
						for ( var i = 0; i < nodes.length; i++) {
							ids.push(nodes[i].id);// 获取选中节点的值
							v += nodes[i].name + ",";
						}
						console.log(ids);
						var params ={roleId:self.roleId,
								ids:ids
						
						};
//						debugger;
						comm.requestFun("jsxxglSetPerm",params,function(obj){
							var ret=obj;
//							debugger;
							$(elthis).dialog("destroy");
//							$(this).dialog('destroy');
						},comm.lang("permissionManage"));
						var test=1;

					},
					"关闭" : function() {
						$(this).dialog('destroy');

					}
				}
			});

		}
	}
});