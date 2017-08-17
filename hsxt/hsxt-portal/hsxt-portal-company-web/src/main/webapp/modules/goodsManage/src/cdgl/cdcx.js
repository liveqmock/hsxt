define(['text!goodsManageTpl/cdgl/cdcx.html',
		'text!goodsManageTpl/cdgl/glcplb.html',
		'text!goodsManageTpl/cdgl/xzcp.html'
		], function(tpl, glcplbTpl, xzcpTpl){
	return {
		showPage : function(){
			$('#busibox').html(tpl);
			
			var self = this;
			
			var searchTable = $.fn.bsgrid.init('cdcx_table', {
				//url: '',
				localData: [{id:'xx01',cdmc:'全部',bz:'全部菜品',zt:'启用'},{id:'xx01',cdmc:'菜单2',bz:'备注2',zt:'启用'}],
				otherParames: {},
				//不显示空白行
				displayBlankRows: false,
				//不显示无页可翻的提示
				pageIncorrectTurnAlert: false,
				//隔行变色
				stripeRows: true,
				//不显示选中行背景色
				rowSelectedColor: false,
				pageSize: 10,
				operate: {
					add: function(record, rowIndex, colIndex, options){
						return $('<a title="关联菜品">关联菜品</a>').click(function(e){
							self.relatedMenu(searchTable.getRecord(rowIndex));
						});
					}
				}
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#glcplb_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		//关联菜品
		relatedMenu : function(obj){
			var self = this;
			//显示菜单
			var oTabMenu = $('#cdgl_glcp');
			oTabMenu.find('a').text(obj.cdmc + ' 关联菜品')
			comm.liActive_add(oTabMenu);
			
			//切换工作区
			$('#cdcx_content1').addClass('none');
			$('#cdcx_content2').removeClass('none').html(_.template(glcplbTpl));
			
			var glcplbTable = null;
			
			//查询单击事件
			$('#btn_cx').click(function(){
				if (!self.validateData()) {
					return;
				}
				
				glcplbTable = $.fn.bsgrid.init('glcplb_table', {
					//url: '',
					localData: [{id:'xx01',cpmc:'普罗旺斯风情鸡肉炒饭',cpfl:'主食',jg:'19.00',jf:'1.00'},{id:'xx02',cpmc:'普罗旺斯风情鸡肉炒饭',cpfl:'主食',jg:'19.00',jf:'1.00'},{id:'xx03',cpmc:'普罗旺斯风情鸡肉炒饭',cpfl:'主食',jg:'19.00',jf:'1.00'},{id:'xx04',cpmc:'普罗旺斯风情鸡肉炒饭',cpfl:'主食',jg:'19.00',jf:'1.00'},{id:'xx05',cpmc:'普罗旺斯风情鸡肉炒饭',cpfl:'主食',jg:'19.00',jf:'1.00'},{id:'xx06',cpmc:'普罗旺斯风情鸡肉炒饭',cpfl:'主食',jg:'19.00',jf:'1.00'}],
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
					pageSize: 10,
					operate: {
						add: function(record, rowIndex, colIndex, options){
							return $('<a title="删除">删除</a>').click(function(e){
								self.doDel(glcplbTable.getRecord(rowIndex));
							});
						}
					}
				});
			});
			//这一行需要去掉，此处用于显示数据
			$('#btn_cx').click();
			
			//选择菜品按钮
			$('#btn_glcplb_select').click(function(){
				$('#glcplb-dialog > p').html(xzcpTpl);
				$('#glcplb-dialog').dialog({
					title: '选择菜品',
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
				var xzcpTable = null;
				
				//确定查询按钮
				$('#btn_xzcp_cx').click(function(){
					tjzhspTable = $.fn.bsgrid.init('xzcp_table', {
						//url: '',
						localData: [{id:'xx01',cpmc:'普罗旺斯风情鸡肉炒饭',cpfl:'主食',jg:'19.00',jf:'1.00'},{id:'xx02',cpmc:'普罗旺斯风情鸡肉炒饭',cpfl:'主食',jg:'19.00',jf:'1.00'},{id:'xx03',cpmc:'普罗旺斯风情鸡肉炒饭',cpfl:'主食',jg:'19.00',jf:'1.00'},{id:'xx04',cpmc:'普罗旺斯风情鸡肉炒饭',cpfl:'主食',jg:'19.00',jf:'1.00'},{id:'xx05',cpmc:'普罗旺斯风情鸡肉炒饭',cpfl:'主食',jg:'19.00',jf:'1.00'},{id:'xx06',cpmc:'普罗旺斯风情鸡肉炒饭',cpfl:'主食',jg:'19.00',jf:'1.00'}],
						//不显示空白行
						displayBlankRows: false,
						//不显示无页可翻的提示
						pageIncorrectTurnAlert: false,
						//隔行变色
						stripeRows: true,
						//不显示选中行背景色
						rowSelectedColor: false,
						pageSize: 10
					});
				});
				//这一行需要去掉，此处用于显示数据
				$('#btn_xzcp_cx').click();
			});
			//批量删除按钮
			$('#btn_glcplb_del').click(function(){
				var rowsData = glcplbTable.getCheckedRowsRecords(),
				len = rowsData.length;
				if(len == 0){
					comm.alert({
						content: '没有选中项',
						imgClass: 'tips_warn'
					});
					return;
				}
				var a = [];
				$.each(rowsData, function(k, v){
					a.push(v.id);
				});
				alert('批量删除:' + a.join(','));
			});
		},
		doDel : function(obj){
			var id = obj.id;
			alert(id + '删除');
		}
	};
});