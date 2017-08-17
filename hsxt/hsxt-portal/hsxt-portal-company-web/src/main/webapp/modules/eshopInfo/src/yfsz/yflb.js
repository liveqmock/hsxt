define(['text!eshopInfoTpl/yfsz/yflb.html',
		'text!eshopInfoTpl/yfsz/yflb_tj_xg.html'
		], function(tpl, tj_xgTpl){
	return {
		showPage : function(){
			var self = this;
			
			$('#busibox').html(_.template(tpl));
				
			var searchTable = $.fn.bsgrid.init('ddxstj_table', {
				//url: '',
				localData: [{mbmc:'顺丰快递',wlgs:'顺丰快递',wlgsid:'1',lx:'包邮',byj:100,yf:8,ms:'发货'},{mbmc:'邮政快递',wlgs:'邮政快递',wlgsid:'2',lx:'不包邮',byj:0,yf:12,ms:'发货'},{mbmc:'顺丰快递',wlgs:'顺丰快递',wlgsid:'1',lx:'包邮',byj:100,yf:8,ms:'发货'},{mbmc:'韵达快递',wlgs:'韵达快递',wlgsid:'3',lx:'包邮',byj:100,yf:8,ms:'发货'}],
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
						return $('<a title="修改">修改</a>').click(function(e){
							self.doEdit(searchTable.getRecord(rowIndex));
						});
					},
					del: function(record, rowIndex, colIndex, options){
						return $('<a class="ml10" title="删除">删除</a>').click(function(e){
							self.doDel(searchTable.getRecord(rowIndex));
						});
					}
				}
			});
			
			//添加按钮
			$('#btn_yflb_add').click(function(){
				self.doEdit({});
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#yflb_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		doEdit : function(obj){
			var self = this,
			title = $.isEmptyObject(obj) ? '添加' : '修改';
			
			var response = {
				//物流名称列表，从后台获取
				wlmclb : "[{name:'顺丰快递',value:'1'},{name:'邮政快递',value:'2'},{name:'韵达快递',value:'3'},{name:'中通快递',value:'4'},{name:'申通快递',value:'5'},{name:'天天快递',value:'6'},{name:'如风达快递',value:'7'},{name:'日日顺快递',value:'8'}]"
			};
			//扩展json数据
			var data = $.extend({}, obj, response);
			$('#yfsz_yflb-dialog > p').html(_.template(tj_xgTpl, data));
			$('#yfsz_yflb-dialog').dialog({
				title: title,
				width: 400,
				open: function (event, ui) {
					$(".ui-dialog-titlebar-close", $(this).parent()).show();
				},
				buttons: {
					"保存": function(){
						if (!self.validateData()) {
							return;
						}
						
						$(this).dialog('destroy');
						//重新加载页面
						$('#yfsz_yflb').trigger('click');
					}
				}
			});
			//下拉列表框
			$('#yflb_wlmc').selectList({
				height: 28,
				width: 205,
				optionWidth: 205,
				optionHeight: 130
			});
			//包邮复选框
			$('#yflb_lx').click(function(){
				$('#yflb_form .yflb_by_td').toggleClass('none', !$(this).is(':checked'));
			});
		},
		doDel : function(obj){
			comm.confirm({
				content: "确认要删除吗？",
				imgFlag: 1,
				callOk: function () {
					
					//重新加载页面
					$('#yfsz_yflb').trigger('click');
				}
			});
		}
	};
});