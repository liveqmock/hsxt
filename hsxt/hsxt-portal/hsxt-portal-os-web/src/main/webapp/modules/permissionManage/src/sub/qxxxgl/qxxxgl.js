define(
		[ 'text!permissionManageTpl/sub/qxxxgl/qxxxgl.html', 'text!permissionManageTpl/sub/qxxxgl/qxxxgl_opt.html',"permissionManageLan" ],
		function(qxxxglTpl, qxxxgl_optTpl) {

			var self = null;
			var gridObj = null;

			return {

				showPage : function(tabid) {
					$('#main-content > div[data-contentid="' + tabid + '"]').html(_.template(qxxxglTpl));

					self = this;

					/* 下拉列表 */
					$("#qxxxgl_tb_qxlx").selectList({
						borderWidth : 1,
						borderColor : '#CCC',
						options : [ {
							name : '所有',
							value : '',
							selected : true
						}, {
							name : '菜单',
							value : '0'
						}, {
							name : '权限',
							value : '1'
						} ]
					}).change(function(e) {
						console.log($(this).val());
					});
					/* end */

					// 查询条件
					var ptbm = $("#qxxxgl_tb_ptbm").val();
					var zxtbm = $("#qxxxgl_tb_zxtbm").val();
					var qxlx = $("#qxxxgl_tb_qxlx").attr("optionValue");
					var fqxid = $("#qxxxgl_tb_fqxid").val();
					var queryConditon = {"platformCode":ptbm,"subSystemCode":zxtbm,"permType":qxlx,"parentId":fqxid};
					/* end */
					
					gridObj = comm.getCommBsGrid('queryList_qxxxgl', "qxxxglSearch", queryConditon, null, self.btn_opt_detail,
							null, null, self.btn_opt_edit,null);

			

					$('#qxxxgl_tb_addBtn').click(function() {
						self.qxxxgl_opt_xz();
					});
					
					 //查询按钮
					$('#qxxxgl_tb_queryBtn').click(function(){
						var ptbm = $("#qxxxgl_tb_ptbm").val();
						var zxtbm = $("#qxxxgl_tb_zxtbm").val();
						var qxlx = $("#qxxxgl_tb_qxlx").attr("optionValue");
						var fqxid = $("#qxxxgl_tb_fqxid").val();
						// 查询条件
						var queryConditon = {"platformCode":ptbm,"subSystemCode":zxtbm,"permType":qxlx,"parentId":fqxid};
						gridObj = comm.getCommBsGrid('queryList_qxxxgl', "qxxxglSearch", queryConditon, null, self.btn_opt_detail,
								null, null, self.btn_opt_edit,null);
					});
					

				},

				btn_opt_detail : function(record, rowIndex, colIndex, options) {
					if (colIndex == 6) {
						var obj = gridObj.getRecord(rowIndex);
						if (obj.permType == 1) {
							return '权限'
						}
						return '菜单';
					}
					var link1 = $('<a>详情</a>').click(function(e) {
						var obj = gridObj.getRecord(rowIndex);
						self.qxxxgl_opt_xq(obj);

					}.bind(this));
					return link1;
				}.bind(this),

				btn_opt_edit : function(record, rowIndex, colIndex, options) {
					if (colIndex == 8) {
						var link1 = $('<a>编辑</a>').click(function(e) {
							var obj = gridObj.getRecord(rowIndex);
							self.qxxxgl_opt_bj(obj);

						}.bind(this));
						return link1;
					}
				}.bind(this),

				qxxxgl_opt_xz : function() {

					var that = this;

					$('#qxxxgl_dlg').html(qxxxgl_optTpl);

					/* 弹出框 */
					$("#qxxxgl_dlg").dialog({
						title : "新增",
						width : "1000",
						modal : true,
						closeIcon : true,
						buttons : {
							"增加" : function() {
								var elthis=this;
								if (!that.validate()) {
									return;
								} else {
									var qxlx= $("#qxxxgl_opt_qxlx").attr('optionValue');
									var qxzt = $("#qxxxgl_opt_zt").attr('optionValue');
									var datas = $("#qxxxgl_optForm").serializeArray();
									datas[3].value = qxlx;
									datas[7].value = qxzt;
									comm.requestFun("qxxxglAdd",datas,function(obj){
										$(elthis).dialog("destroy");
										$('#queryList_qxxxgl_pt_refreshPage').click();
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

					$("#qxxxgl_opt_qxlx").selectList({
						width : 250,
						optionWidth : 255,
						options : [ {
							name : '菜单',
							value : '0',
							selected : true
						}, {
							name : '权限',
							value : '1'
						} ]
					}).change(function(e) {
						console.log($(this).val());
					});

					$("#qxxxgl_opt_zt").selectList({
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

					$(
							'#qxxxgl_opt_qxid, #qxxxgl_opt_fqxid, #qxxxgl_opt_qxmc, #qxxxgl_opt_qxdm, #qxxxgl_opt_zxxbm, #qxxxgl_opt_ptbm, #qxxxgl_opt_cdqy, #qxxxgl_opt_cdpxh, #qxxxgl_opt_qxurl, #qxxxgl_opt_qxms')
							.val('');

					/* end */

				},
				qxxxgl_opt_xq : function(obj) {
					$('#qxxxgl_dlg').html(qxxxgl_optTpl);

					/* 弹出框 */
					$("#qxxxgl_dlg").dialog({
						title : "详情",
						width : "1000",
						modal : true,
						closeIcon : true,
						buttons : {
							"关闭" : function() {
								$(this).dialog("destroy");
							}
						}
					});
					/* end */
					
					$("#qxxxgl_opt_qxlx").selectList({
						width : 250,
						optionWidth : 255,
						options : [ {
							name : '菜单',
							value : '0',
							selected : true
						}, {
							name : '权限',
							value : '1'
						} ]
					}).change(function(e) {
						console.log($(this).val());
					});	
					
					var permType = obj.permType;
					if(permType == 0){
						$('#qxxxgl_opt_qxlx').val("菜单");
					}else{
						$('#qxxxgl_opt_qxlx').val("权限");
					};
					//设置权限类型为只读项，不能修改
					$('#qxxxgl_opt_qxlx').attr("readonly","readonly");
					
					
					$("#qxxxgl_opt_zt").selectList({
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
					var zt = obj.qxxxgl_opt_zt;
					if(zt == 'Y'){
						$('#qxxxgl_opt_zt').val("有效");
					}else{
						$('#qxxxgl_opt_zt').val("无效");
					};
					//设置权限类型为只读项，不能修改
					$('#qxxxgl_opt_zt').attr("readonly","readonly");

					/* 表单值初始化 */
					$('.required').addClass('none');
					$('.inputForm-ul').children('li').removeClass('pr');
					$('#qxxxgl_opt_qxid').val(obj.permId).attr('readonly', 'readonly');
					$('#qxxxgl_opt_fqxid').val(obj.parentId).attr('readonly', 'readonly');
					$('#qxxxgl_opt_qxmc').val(obj.permName).attr('readonly', 'readonly');
					$('#qxxxgl_opt_qxdm').val(obj.permCode).attr('readonly', 'readonly');
					$('#qxxxgl_opt_zxxbm').val(obj.subSystemCode).attr('readonly', 'readonly');
					$('#qxxxgl_opt_ptbm').val(obj.platformCode).attr('readonly', 'readonly');
					$('#qxxxgl_opt_cdqy').val(obj.layout).attr('readonly', 'readonly');
					$('#qxxxgl_opt_cdpxh').val(obj.sortNum).attr('readonly', 'readonly');
					$('#qxxxgl_opt_qxurl').val(obj.permUrl).attr('readonly', 'readonly');
					$('#qxxxgl_opt_qxms').val(obj.permDesc).attr('readonly', 'readonly');
					/* end */

				},
				qxxxgl_opt_bj : function(obj) {
					var that = this;

					$('#qxxxgl_dlg').html(qxxxgl_optTpl);

					/* 弹出框 */
					$("#qxxxgl_dlg").dialog({
						title : "编辑",
						width : "1000",
						modal : true,
						closeIcon : true,
						buttons : {
							"保存" : function() {
							    var elthis=this;
								if (!that.validate()) {
									return;
								} else {
									var qxlx= $("#qxxxgl_opt_qxlx").attr('optionValue');
									var qxzt = $("#qxxxgl_opt_zt").attr('optionValue');
									var datas = $("#qxxxgl_optForm").serializeArray();
									datas[3].value = qxlx;
									datas[7].value = qxzt;
									comm.requestFun("qxxxglUpdate",datas,function(obj){
										$(elthis).dialog("destroy");
										$('#queryList_qxxxgl_pt_refreshPage').click();
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

					$("#qxxxgl_opt_qxlx").selectList({
						width : 250,
						optionWidth : 255,
						options : [ {
							name : '菜单',
							value : '0',
							selected : true
						}, {
							name : '权限',
							value : '1'
						} ]
					}).change(function(e) {
						console.log($(this).val());
					});
					var permType = obj.permType;
					if(permType == 0){
						$('#qxxxgl_opt_qxlx').val("菜单");
					}else{
						$('#qxxxgl_opt_qxlx').val("权限");
					};
					//设置权限类型为只读项，不能修改
					$('#qxxxgl_opt_qxlx').attr("readonly","readonly");
					
					

					$("#qxxxgl_opt_zt").selectList({
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
					var zt = obj.qxxxgl_opt_zt;
					if(zt == 'Y'){
						$('#qxxxgl_opt_zt').val("有效");
					}else{
						$('#qxxxgl_opt_zt').val("无效");
					};
					//设置权限类型为只读项，不能修改
					$('#qxxxgl_opt_zt').attr("readonly","readonly");

					/* end */

					$('#qxxxgl_opt_qxid').val(obj.permId).attr('readonly', 'readonly');
					$('#qxxxgl_opt_fqxid').val(obj.parentId);
					$('#qxxxgl_opt_qxmc').val(obj.permName);
					$('#qxxxgl_opt_qxdm').val(obj.permCode);
					$('#qxxxgl_opt_zxxbm').val(obj.subSystemCode);
					$('#qxxxgl_opt_ptbm').val(obj.platformCode);
					$('#qxxxgl_opt_cdqy').val(obj.layout);
					$('#qxxxgl_opt_cdpxh').val(obj.sortNum);
					$('#qxxxgl_opt_qxurl').val(obj.permUrl);
					$('#qxxxgl_opt_qxms').val(obj.permDesc);

					/* end */

				},
				validate : function() {
					return comm.valid({
						formID : '#qxxxgl_optForm',
						rules : {
							permId : {
								required : true
							},
							parentId : {
								required : true
							},
							permName : {
								required : true
							},
							subSystemCode : {
								required : true
							},
							layout : {
								range :[0,100],
								required : true
							},
							sortNum : {
								range :[0,100],
								required : true
							}
						},
						messages : {
							permId : {
								required : '必填'
							},
							parentId : {
								required : '必填'
							},
							permName : {
								required : '必填'
							},
							subSystemCode : {
								required : '必填'
							},
							layout : {
								range : '请输入数字、范围[0-100]',
								required : '必填'
							},
							sortNum : {
								range : '请输入数字、范围[0-100]',
								required : '必填'
							}
						}
					});
				}
			}
		});