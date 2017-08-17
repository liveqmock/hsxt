define(['text!eshopInfoTpl/shy/shy.html',
		'text!eshopInfoTpl/shy/shy_tj_xg.html',
		'text!eshopInfoTpl/shy/glyed.html'
		], function(tpl, tj_xgTpl, glyedTpl){
	return {
		searchTable : null,
		showPage : function(){
			var self = this;
			
			$('#busibox').html(_.template(tpl));
				
			var searchTable = null;
			
			//查询单击事件
			$('#btn_cx').click(function(){
				if (!self.validateData()) {
					return;
				}
				
				self.searchTable = searchTable = $.fn.bsgrid.init('shy_table', {
					//url: '',
					localData: [{id:'XX01',zp:'resources/img/store_noimg.gif',shrmc:'王二小',xb:'男',lxdh:'13425588758',ssyed:'[王黄皇]',ssyedid:'yed01',bz:'123'},{id:'XX02',zp:'resources/img/store_noimg.gif',shrmc:'张三妞',xb:'女',lxdh:'13000008758',ssyed:'[王黄皇]',ssyedid:'yed02',bz:''}],
					//不显示空白行
					displayBlankRows: false,
					otherParames: {
						
					},
					//不显示无页可翻的提示
					pageIncorrectTurnAlert: false,
					//隔行变色
					stripeRows: true,
					//不显示选中行背景色
					rowSelectedColor: false,
					pageSize: 10,
					operate: {
						add: function(record, rowIndex, colIndex, options){
							return $('<a title="关联营业点">关联营业点</a>').click(function(e){
								self.relateBusinessPoint(searchTable.getRecord(rowIndex));
							});
						},
						del: function(record, rowIndex, colIndex, options){
							return $('<a class="ml10" title="修改">修改</a>').click(function(e){
								self.doEdit(searchTable.getRecord(rowIndex));
							});
						},
						edit: function(record, rowIndex, colIndex, options){
							return $('<a class="ml10" title="删除">删除</a>').click(function(e){
								self.doDel(searchTable.getRecord(rowIndex));
							});
						}
					},
					renderImg: function(record, rowIndex, colIndex, options){
						return '<img src="' + searchTable.getRecordIndexValue(record, 'zp') + '" width="70" height="50">'
					}
				});
			});
			//这一行需要去掉，此处用于显示数据
			$('#btn_cx').click();
			
			//批量删除按钮
			$('#btn_shy_del').click(function(){
				if(searchTable == null) return;
				var v = searchTable.getCheckedRowsRecords(),
				len = v.length;
				if(v == 0){
					comm.alert({
						content: '没有选中项',
						imgClass: 'tips_warn'
					});
					return;
				}
				
				comm.confirm({
					title: '批量删除',
					content: "确认删除吗？",
					imgFlag: 1,
					callOk: function () {
						
						if(searchTable)searchTable.refreshPage();
					}
				});
			});
			//添加按钮
			$('#btn_shy_add').click(function(){
				self.doEdit({});
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#shy_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		validateAddModData : function(){
			return comm.valid({
				formID : '#shy_tj_xg_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		//关联营业点
		relateBusinessPoint : function(obj){
			var self = this;
			
			//营业点列表从后台返回
			var response = {yedlb: [{id:'xx01',name:'王黄皇'},{id:'xx02',name:'王黄皇1'},{id:'xx03',name:'王黄皇2'},{id:'xx04',name:'王黄皇3'},{id:'xx05',name:'王黄皇4'},{id:'xx06',name:'王黄皇5'}]};
			var data = $.extend({}, obj, response);
			$('#shy_shy-dialog > p').html(_.template(glyedTpl, data));
			$('#shy_shy-dialog').dialog({
				title: '关联营业点',
				width: 500,
				open: function (event, ui) {
					$(".ui-dialog-titlebar-close", $(this).parent()).show();
				},
				buttons: {
					"保存": function(){
						$(this).dialog('destroy');
						//刷新表格
						if(self.searchTable) self.searchTable.refreshPage();
					},
					"关闭": function(){
						$(this).dialog('destroy');
					}
				}
			});
			//全选复选框
			$('.shy_chkAll').click(function(){
				$('.shy_chk').prop('checked', $(this).is(':checked'));
			});
		},
		doEdit : function(obj){
			var self = this,
			title = $.isEmptyObject(obj) ? '添加' : '修改';
			
			var response = {};
			//扩展json数据
			var data = $.extend({}, obj, response);
			$('#shy_shy-dialog > p').html(_.template(tj_xgTpl, data));
			$('#shy_shy-dialog').dialog({
				title: title,
				width: 400,
				open: function (event, ui) {
					$(".ui-dialog-titlebar-close", $(this).parent()).show();
				},
				buttons: {
					"保存": function(){
						if (!self.validateAddModData()) {
							return;
						}
						
						$(this).dialog('destroy');
						//刷新表格
						if(self.searchTable) self.searchTable.refreshPage();
					}
				}
			});
		},
		doDel : function(obj){
			var self = this;
			comm.confirm({
				content: "确认要删除吗？",
				imgFlag: 1,
				callOk: function () {
					
					//刷新表格
					if(self.searchTable) self.searchTable.refreshPage();
				}
			});
		}
	};
});