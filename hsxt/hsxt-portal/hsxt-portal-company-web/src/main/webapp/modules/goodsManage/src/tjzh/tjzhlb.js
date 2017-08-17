define(['text!goodsManageTpl/tjzh/tjzhlb.html',
		'text!goodsManageTpl/tjzh/spxx.html',
		'text!goodsManageTpl/tjzh/xz_xg.html',
		'text!goodsManageTpl/tjzh/spxx2.html',
		'text!goodsManageTpl/tjzh/tjzhsp.html'
		], function(tpl, spxxTpl, xzxgTpl, spxx2Tpl, tjzhspTpl){
	return {
		searchTable : null,
		showPage : function(){
			$('#busibox').html(tpl);
			
			var self = this,
			searchTable = null;
			
			/*下拉列表*/
			$("#tjzhlb_zt").selectList({
				options:[
					{name:'启用',value:'1'},
					{name:'停用',value:'2'}
				]	
			});
			
			//查询单击事件
			$('#btn_cx').click(function(){
				if (!self.validateData()) {
					return;
				}
				
				self.searchTable = searchTable = $.fn.bsgrid.init('tjzhlb_table', {
					//url: '',
					localData: [{id:'xx01',zhmc:'组合商品1',zjf:'90.00',zjg:'600.00',zt:'启用',ms:'组合商品1',spxx:[{id:'xq01',zp:'resources/img/product01.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'黑色',cc:'L',sl:1},{id:'xq02',zp:'resources/img/product02.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'棕色',cc:'L',sl:1},{id:'xq03',zp:'resources/img/product03.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'白色',cc:'L',sl:1},{id:'xq04',zp:'resources/img/product01.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'黑色',cc:'L',sl:1},{id:'xq05',zp:'resources/img/product02.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'棕色',cc:'L',sl:1},{id:'xq06',zp:'resources/img/product03.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'白色',cc:'L',sl:1}]},{id:'xx02',zhmc:'组合商品2',zjf:'45.00',zjg:'300.00',zt:'启用',ms:'组合商品2',spxx:[{id:'xq01',zp:'resources/img/product01.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'黑色',cc:'L',sl:1},{id:'xq02',zp:'resources/img/product02.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'棕色',cc:'L',sl:1},{id:'xq03',zp:'resources/img/product03.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'白色',cc:'L',sl:1}]},{id:'xx03',zhmc:'组合商品3',zjf:'30.00',zjg:'200.00',zt:'停用',ms:'组合商品3',spxx:[{id:'xq01',zp:'resources/img/product01.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'黑色',cc:'L',sl:1},{id:'xq02',zp:'resources/img/product02.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'棕色',cc:'L',sl:1}]},{id:'xx04',zhmc:'组合商品4',zjf:'60.00',zjg:'400.00',zt:'启用',ms:'组合商品4',spxx:[{id:'xq01',zp:'resources/img/product01.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'黑色',cc:'L',sl:1},{id:'xq02',zp:'resources/img/product02.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'棕色',cc:'L',sl:1},{id:'xq03',zp:'resources/img/product03.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'白色',cc:'L',sl:1},{id:'xq04',zp:'resources/img/product01.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'黑色',cc:'L',sl:1}]}],
					otherParames: {
						
					},
					//不显示空白行
					displayBlankRows: false,
					//不显示无页可翻的提示
					pageIncorrectTurnAlert: false,
					//隔行变色
					stripeRows: true,
					//不显示选中行背景色
					rowSelectedColor: false,
					lineWrap: 1,
					pageSize: 10,
					operate: {
						add: function(record, rowIndex, colIndex, options){
							return $('<a title="修改">修改</a>').click(function(e){
								self.doEdit(searchTable.getRecord(rowIndex));
							});
						},
						del: function(record, rowIndex, colIndex, options){
							var f = searchTable.getRecordIndexValue(record, 'zt') === '停用'
							t = f ? '启用' : '停用';
							return $('<a class="ml10" title="' + t + '">' + t + '</a>').click(function(e){
								self[f ? 'doEnabled' : 'doDisabled'](searchTable.getRecord(rowIndex));
							});
						},
						edit: function(record, rowIndex, colIndex, options){
							return $('<a class="ml10" title="删除">删除</a>').click(function(e){
								self.doDel(searchTable.getRecord(rowIndex));
							});
						}
					},
					renderImg : function(record, rowIndex, colIndex, options){
						return $(_.template(spxxTpl, record.spxx)).on('mouseover', 'li', function () {
							$(this).children(".img-group-detail").show();
						}).on('mouseout', 'li', function () {
							$(this).children(".img-group-detail").hide();
						});
					}
				});
			});
			//这一行需要去掉，此处用于显示数据
			$('#btn_cx').click();
			
			//删除按钮
			$('#btn_tjzhlb_del').click(function(){
				self.getCheckedRowsRecords(function(rowsData){
					comm.confirm({
						title: '批量删除',
						content: "确认删除吗？",
						imgFlag: 1,
						callOk: function () {
							var a = [];
							$.each(rowsData, function(k, v){
								a.push(v.id);
							});
							alert('批量删除:' + a.join(','));
							if(searchTable)searchTable.refreshPage();
						}
					});
				});
			});
			
			//停用按钮
			$('#btn_tjzhlb_stop').click(function(){
				self.getCheckedRowsRecords(function(rowsData){
					var a = [];
					$.each(rowsData, function(k, v){
						a.push(v.id);
					});
					alert('批量停用:' + a.join(','));
				});
			});
			
			//新增组合按钮
			$('#btn_tjzhlb_add').click(function(){
				//调用修改
				self.doEdit({});
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#tjzhlb_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		//获取表格选中项
		getCheckedRowsRecords : function(callback, objTable){
			var v = !objTable ? this.searchTable.getCheckedRowsRecords() : objTable.getCheckedRowsRecords(),
			len = v.length;
			if(len == 0){
				comm.alert({
					content: '没有选中项',
					imgClass: 'tips_warn'
				});
				return;
			}
			callback && $.isFunction(callback) && callback(v);
		},
		//修改
		doEdit : function(obj){
			var self = this,
			//新增标识
			xzFlag = $.isEmptyObject(obj),
			xzxgTable = null;
			
			//显示菜单
			comm.liActive_add($('#tjzh_xzxgzh'));
			$('#tjzhlb_content1').addClass('none');
			
			//从后台获取数据，此处为示例
			var response = {
				spxx : {"xx01":[{id:'xq01',dj:'100.00',jf:'8.00',zhjf:'15.00'},{id:'xq02',dj:'100.00',jf:'8.00',zhjf:'15.00'},{id:'xq03',dj:'100.00',jf:'8.00',zhjf:'15.00'},{id:'xq04',dj:'100.00',jf:'8.00',zhjf:'15.00'},{id:'xq05',dj:'100.00',jf:'8.00',zhjf:'15.00'},{id:'xq06',dj:'100.00',jf:'8.00',zhjf:'15.00'}],"xx02":[{id:'xq01',dj:'100.00',jf:'8.00',zhjf:'15.00'},{id:'xq02',dj:'100.00',jf:'8.00',zhjf:'15.00'},{id:'xq03',dj:'100.00',jf:'8.00',zhjf:'15.00'}],"xx03":[{id:'xq01',dj:'100.00',jf:'8.00',zhjf:'15.00'},{id:'xq02',dj:'100.00',jf:'8.00',zhjf:'15.00'}],"xx04":[{id:'xq01',dj:'100.00',jf:'8.00',zhjf:'15.00'},{id:'xq02',dj:'100.00',jf:'8.00',zhjf:'15.00'},{id:'xq03',dj:'100.00',jf:'8.00',zhjf:'15.00'},{id:'xq04',dj:'100.00',jf:'8.00',zhjf:'15.00'}]}[obj.id]
			};
			var data = $.extend(true, {}, obj, response);
			
			$('#tjzhlb_content2').removeClass('none').html(_.template(xzxgTpl, data));
			
			//加载表格数据
			xzxgTable = $.fn.bsgrid.init('tjzh_xz_xg_table', {
				//url: '',
				localData: xzFlag ? null : data.spxx,
				//不显示空白行
				displayBlankRows: false,
				//不显示无页可翻的提示
				pageIncorrectTurnAlert: false,
				//隔行变色
				stripeRows: true,
				//不显示选中行背景色
				rowSelectedColor: false,
				//不分页
				pageAll: true,
				//不显示分页工具条
				displayPagingToolbarOnlyMultiPages: true,
				operate: {
					add: function(record, rowIndex, colIndex, options){
						if(colIndex == 4){
							//计算单价汇总、组合积分汇总
							var o = $('#tjzh_xz_xg_dj_sum');
							o.text((comm.convertFloat(o.text()) + comm.convertFloat(xzxgTable.getRecordIndexValue(record, 'dj'))).toFixed(2));
							var o = $('#tjzh_xz_xg_zhjf_sum');
							o.text((comm.convertFloat(o.text()) + comm.convertFloat(xzxgTable.getRecordIndexValue(record, 'zhjf'))).toFixed(2));
							
							//组合积分文本框及事件绑定
							return $('<input type="text" class="w100 p5 infobox2 tjzh_xz_xg_zhjf" value="' + xzxgTable.getRecordIndexValue(record, 'zhjf') + '" name=""/>').keyup(function(){
								var t = 0;
								$.each($('.tjzh_xz_xg_zhjf'), function(k, v){
									var m = parseFloat($(v).val() || '0.00');
									t += isNaN(m) ? 0 : m;
								});
								$('#tjzh_xz_xg_zhjf_sum').text(t.toFixed(2));
							});
						}
						return $('<a title="删除">删除</a>').click(function(e){
							//删除单击事件
							
						});
					}
				},
				renderImg : function(record, rowIndex, colIndex, options){
					return _.template(spxx2Tpl, record);
				}
			});
			
			//取消按钮
			$('#btn_tjzh_xz_xg_qx').click(function(){
				//显示菜单
				comm.liActive($('#tjzh_tjzhlb'), '#tjzh_xzxgzh');
				$('#tjzhlb_content1').removeClass('none');
				$('#tjzhlb_content2').addClass('none').html('');
			});
			//保存按钮
			$('#btn_tjzh_xz_xg_bc').click(function(){
				//调用取消按钮返回页面
				$('#btn_tjzh_xz_xg_qx').trigger('click');
				
				//刷新表格
				if(self.searchTable)self.searchTable.refreshPage();
			});
			
			//批量删除按钮
			$('#tjzh_xz_xg_del').click(function(){
				self.getCheckedRowsRecords(function(rowsData){
					var a = [];
					$.each(rowsData, function(k, v){
						a.push(v.id);
					});
					alert('批量删除:' + a.join(','));
				}, xzxgTable);
			});
			//添加组合商品按钮
			$('#tjzh_xz_xg_add').click(function(){
				$('#tjzhlb-dialog > p').html(tjzhspTpl);
				$('#tjzhlb-dialog').dialog({
					title: '添加组合商品信息',
					position: ['center', 50],
					width: 800,
					open: function (event, ui) {
						$(".ui-dialog-titlebar-close", $(this).parent()).show();
					},
					buttons: {
						'确定': function(){
							
							$(this).dialog("destroy");
						},
						'取消': function(){
							$(this).dialog("destroy");
						}
					}
				});
				
				//表格
				var tjzhspTable = null;
				
				//确定查询按钮
				$('#btn_tjzhsp_cx').click(function(){
					tjzhspTable = $.fn.bsgrid.init('tjzhsp_table', {
						//url: '',
						localData: [{id:'sp01',zp:'resources/img/product01.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'黑色',cc:'L',sl:1,pp:'美特斯邦威',jg:'100.00',zf:'8.00',sjsj:'2015-09-07 11:50:11'},{id:'sp02',zp:'resources/img/product02.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'棕色',cc:'L',sl:1,pp:'美特斯邦威',jg:'100.00',zf:'8.00',sjsj:'2015-09-07 11:50:11'},{id:'sp03',zp:'resources/img/product03.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'白色',cc:'L',sl:1,pp:'美特斯邦威',jg:'100.00',zf:'8.00',sjsj:'2015-09-07 11:50:11'},{id:'sp04',zp:'resources/img/product01.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'黑色',cc:'L',sl:1,pp:'美特斯邦威',jg:'100.00',zf:'8.00',sjsj:'2015-09-07 11:50:11'},{id:'sp05',zp:'resources/img/product02.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'棕色',cc:'L',sl:1,pp:'美特斯邦威',jg:'100.00',zf:'8.00',sjsj:'2015-09-07 11:50:11'},{id:'sp06',zp:'resources/img/product03.jpg',mc:'图片标题标题图片标题标题图片标题标题图片标题标题',ys:'白色',cc:'L',sl:1,pp:'美特斯邦威',jg:'100.00',zf:'8.00',sjsj:'2015-09-07 11:50:11'}],
						//不显示空白行
						displayBlankRows: false,
						//不显示无页可翻的提示
						pageIncorrectTurnAlert: false,
						//隔行变色
						stripeRows: true,
						//不显示选中行背景色
						rowSelectedColor: false,
						pageSize: 5,
						renderImg : function(record, rowIndex, colIndex, options){
							return _.template(spxx2Tpl, record);
						}
					});
				});
				//这一行需要去掉，此处用于显示数据
				$('#btn_tjzhsp_cx').click();
			});
		},
		//启用
		doEnabled : function(obj){
			var id = obj.id;
			alert(id + '启用');
		},
		//停用
		doDisabled : function(obj){
			var id = obj.id;
			alert(id + '停用');
		},
		//删除
		doDel : function(obj){
			var id = obj.id;
			alert(id + '删除');
		}
	};
});