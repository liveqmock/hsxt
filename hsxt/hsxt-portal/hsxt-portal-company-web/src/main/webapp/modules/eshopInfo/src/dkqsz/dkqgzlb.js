define(['text!eshopInfoTpl/dkqsz/dkqgzlb.html',
		'text!eshopInfoTpl/dkqsz/dkqgzlb_tj.html'
		], function(tpl, tjTpl){
	return {
		showPage : function(){
			var self = this;
			
			$('#busibox').html(_.template(tpl));
				
			var searchTable = $.fn.bsgrid.init('dkqgzlb_table', {
				//url: '',
				localData: [{id:'xx01',dnsygz:'每满100元使用1张',dkqmc:'首次赠送',mz:10.00},{id:'xx02',dnsygz:'每满200元使用3张',dkqmc:'喜迎国庆',mz:10.00}],
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
							self.doDel(searchTable.getRecord(rowIndex));
						});
					}
				}
			});
			
			//添加按钮
			$('#btn_dkqgzlb_add').click(function(){
				//从后台返回
				var response = {
					xfyslb: "[{name:100,value:100},{name:200,value:200},{name:300,value:300},{name:400,value:400},{name:500,value:500},{name:600,value:600}]",
					syzslb: "[{name:1,value:1},{name:2,value:2},{name:3,value:3},{name:4,value:4},{name:5,value:5}]",
					dkqlb: "[{name:'首次赠送 面值10.00',value:'首次赠送 面值10.00'},{name:'首次赠送 面值20.00',value:'首次赠送 面值20.00'},{name:'喜迎国庆 面值10.00',value:'喜迎国庆 面值10.00'},{name:'喜迎国庆 面值10.00',value:'喜迎国庆 面值20.00'}]"
				};
				var data = $.extend({}, response);
				$('#dkqgzlb-dialog > p').html(_.template(tjTpl, data));
				$('#dkqgzlb-dialog').dialog({
					title: '新增规则',
					width: 550,
					height: 280,
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
							$('#dkqgzlb').trigger('click');
						}
					}
				});
				//下拉列表框
				//消费元数
				$('#dkqgzlb_xfys').selectList({
					height: 28,
					width: 100,
					optionWidth: 100,
					optionHeight: 140
				});
				//使用张数
				$('#dkqgzlb_syzs').selectList({
					height: 28,
					width: 100,
					optionWidth: 100,
					optionHeight: 140
				});
				//选择抵扣券
				$('#dkqgzlb_xzdkq').selectList({
					height: 28,
					width: 200,
					optionWidth: 200,
					optionHeight: 100
				});
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#dkqgzlb_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		doDel : function(obj){
			comm.confirm({
				content: "删除后，相关商品不能使用抵扣券，您确认删除吗？",
				width: 'auto',
				imgFlag: 1,
				callOk: function () {
					
					//重新加载页面
					$('#dkqgzlb').trigger('click');
				}
			});
		}
	};
});