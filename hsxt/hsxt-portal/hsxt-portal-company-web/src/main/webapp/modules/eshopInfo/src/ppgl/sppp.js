define(['text!eshopInfoTpl/ppgl/sppp.html',
		'text!eshopInfoTpl/ppgl/tj_xg.html'
		], function(tpl, tj_xgTpl){
	return {
		searchTable : null,
		showPage : function(){
			var self = this;
			
			$('#busibox').html(_.template(tpl));
				
			var searchTable = null;
			
			//下拉框
			$('#ppgl_lm').selectList({
				options: [
					{name:'全部',value:''},
					{name:'男装',value:'1'},
					{name:'女装',value:'2'},
					{name:'鞋子',value:'3'}
				]
			});
			
			//查询单击事件
			$('#btn_cx').click(function(){
				if (!self.validateData()) {
					return;
				}
				
				self.searchTable = searchTable = $.fn.bsgrid.init('ppgl_table', {
					//url: '',
					localData: [{id:'XX01',zp:'resources/img/xinbaili.gif',ppzwmc:'百丽',ppywmc:'ball',lm:'鞋子',lmid:'lm01',ppgs:'百丽品牌故事',ljurl:''},{id:'XX02',zp:'resources/img/xinbaili.gif',ppzwmc:'百丽',ppywmc:'ball',lm:'鞋子',lmid:'lm01',ppgs:'百丽品牌故事',ljurl:''},{id:'XX03',zp:'resources/img/xinbaili.gif',ppzwmc:'百丽',ppywmc:'ball',lm:'鞋子',lmid:'lm01',ppgs:'',ljurl:''},{id:'XX04',zp:'resources/img/xinbaili.gif',ppzwmc:'百丽',ppywmc:'ball',lm:'鞋子',lmid:'lm01',ppgs:'',ljurl:''},{id:'XX05',zp:'resources/img/xinbaili.gif',ppzwmc:'百丽',ppywmc:'ball',lm:'鞋子',lmid:'lm01',ppgs:'',ljurl:''},{id:'XX06',zp:'resources/img/xinbaili.gif',ppzwmc:'百丽',ppywmc:'ball',lm:'鞋子',lmid:'lm01',ppgs:'',ljurl:''},{id:'XX07',zp:'resources/img/xinbaili.gif',ppzwmc:'百丽',ppywmc:'ball',lm:'鞋子',lmid:'lm01',ppgs:'',ljurl:''},{id:'XX08',zp:'resources/img/xinbaili.gif',ppzwmc:'百丽',ppywmc:'ball',lm:'鞋子',lmid:'lm01',ppgs:'百丽品牌故事',ljurl:''},{id:'XX09',zp:'resources/img/xinbaili.gif',ppzwmc:'百丽',ppywmc:'ball',lm:'鞋子',lmid:'lm01',ppgs:'',ljurl:''}],
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
							return $('<a title="修改">修改</a>').click(function(e){
								self.doEdit(searchTable.getRecord(rowIndex));
							});
						},
						del: function(record, rowIndex, colIndex, options){
							return $('<a class="ml10" title="删除">删除</a>').click(function(e){
								self.doDel(searchTable.getRecord(rowIndex));
							});
						}
					},
					renderImg: function(record, rowIndex, colIndex, options){
						return '<img src="' + searchTable.getRecordIndexValue(record, 'zp') + '"/>'
					}
				});
			});
			//这一行需要去掉，此处用于显示数据
			$('#btn_cx').click();
			
			//添加按钮
			$('#btn_ppgl_add').click(function(){
				self.doEdit({});
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#ppgl_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		validateAddModData : function(){
			return comm.valid({
				formID : '#ppgl_tj_xg_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		doEdit : function(obj){
			var self = this,
			title = $.isEmptyObject(obj) ? '添加' : '修改';
			
			//从后台获取类目信息
			var response = {
				lmlist:[{id:'lm001',name:'服装',data:[{id:'lm010',name:'男装',data:[{id:'lm010',name:'衬衫'},{id:'lm010',name:'鞋子'},{id:'lm010',name:'裤子'}]},{id:'lm011',name:'女装',data:[{id:'lm010',name:'裙子'}]}]},{id:'lm002',name:'家具',data:[{id:'lm020',name:'木家具',data:[]},{id:'lm021',name:'真皮家具',data:[]}]}]
			};
			//扩展json数据
			var data = $.extend(true, {}, obj, response);
			$('#ppgl-dialog > p').html(_.template(tj_xgTpl, data));
			$('#ppgl-dialog').dialog({
				title: title + '商品品牌',
				width: 850,
				height: 'auto',
				resizable: 0,
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
			
			$('#lm_lists .sppp_item').click(function(){
				$(this).toggleClass('sppp_item1').parent().find('.sppp_sub').toggle();
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