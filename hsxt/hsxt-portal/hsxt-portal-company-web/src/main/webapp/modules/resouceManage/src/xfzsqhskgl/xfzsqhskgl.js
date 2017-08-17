define(['text!resouceManageTpl/xfzsqhskgl/xfzsqhskgl.html',
		'text!resouceManageTpl/xfzsqhskgl/fksqsz.html',
		'text!resouceManageTpl/xfzsqhskgl/xfzsqhskxq.html',
		'text!resouceManageTpl/xfzsqhskgl/xfzsqhskcl.html'
		], function(tpl, fksqszTpl, xfzsqhskxqTpl, xfzsqhskclTpl){
	return {
		//发卡申请设置
		fksqszTpl: fksqszTpl,
		//消费者申请互生卡详情
		xfzsqhskxqTpl: xfzsqhskxqTpl,
		//消费者申请互生卡处理
		xfzsqhskclTpl: xfzsqhskclTpl,
		showPage : function(){
			$('#busibox').html(tpl);
			
			var self = this,
			searchTable = null;
			
			/*下拉列表*/
			$("#scpoint_area_1").selectList({
				options:[
					{name:'请选择',value:''},
					{name:'xx',value:'0'},
					{name:'xxx',value:'1'},
					{name:'xxxx',value:'2'}
				]	
			}).change(function(e){
				var v = $(this).attr('optionValue');
				
			});
			$("#scpoint_area_2").selectList({
				options:[
					{name:'请选择',value:''},
					{name:'xx',value:'0'},
					{name:'xxx',value:'1'},
					{name:'xxxx',value:'2'}
				]	
			}).change(function(e){
				var v = $(this).attr('optionValue');
				
			});
			/*日期控件*/
			$("#scpoint_beginDate").datepicker({dateFormat:'yy-mm-dd'});
			$("#scpoint_endDate").datepicker({dateFormat:'yy-mm-dd'});
			
			//发卡申请设置单击事件
			$("#scpoint_settingBtn").click(function(){
				$('#xfzsqhskgl-dialog > p').html(_.template(self.fksqszTpl));
				$('#xfzsqhskgl-dialog').dialog({
					title: '发卡申请设置',
					width: 600,
					open: function (event, ui) {
						$(".ui-dialog-titlebar-close", $(this).parent()).show();
					},
					buttons: {
						"确定": function(){
							$(this).dialog("destroy");
						},
						"取消": function(){
							$(this).dialog("destroy");
						}
					}
				});
			});
			//查询单击事件
			$('#scpoint_searchBtn').click(function(){
				if (!self.validateData()) {
					return;
				}
				
				searchTable = $.fn.bsgrid.init('scpoint_result_table', {
					//url: '',
					localData: [{"id":"xx01","date":"2014-12-20","name":"习大大","area":"中国-北京-北京","mobile":"18888888888","address":"北京天安门旁边中南海","status":"已发卡"},{"id":"xx02","date":"2014-12-21","name":"习大大","area":"中国-北京-北京","mobile":"18888888888","address":"北京天安门旁边中南海","status":"资料无效"},{"id":"xx03","date":"2014-12-22","name":"习大大","area":"中国-北京-北京","mobile":"18888888888","address":"北京天安门旁边中南海","status":"不处理"}],
					otherParames: {
						beginDate : $("#scpoint_beginDate").val(),
						endDate : $("#scpoint_endDate").val(),
						area_1 : $("#scpoint_area_1").attr('optionValue'),
						area_2 : $("#scpoint_area_2").attr('optionValue')
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
							return $('<a title="查看详情">查看</a>').click(function(e){
								self.showDetail(searchTable.getRecord(rowIndex));
							});
						},
						edit: function(record, rowIndex, colIndex, options){
							return $('<a class="ml10" title="处理申请">处理</a>').click(function(e){
								self.handle(searchTable.getRecord(rowIndex));
							});
						}
					}
				});
			});
			//这一行需要去掉，此处用于显示数据
			$('#scpoint_searchBtn').click();
		},
		validateData : function(){
			return comm.valid({
				formID : '#scpoint_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		//查看详情
		showDetail : function(obj){
			var id = obj.id,
			self = this;
			var response = {};
			var data = $.extend({}, obj, response);
			$('#xfzsqhskgl-dialog > p').html(_.template(self.xfzsqhskxqTpl, data));
			$('#xfzsqhskgl-dialog').dialog({
				title: '消费者申请互生卡详情',
				width: 800,
				open: function (event, ui) {
					$(".ui-dialog-titlebar-close", $(this).parent()).show();
				},
				buttons: {
					"确定": function(){
						$(this).dialog("destroy");
					}
				}
			});
		},
		//处理申请
		handle : function(obj){
			var id = obj.id,
			self = this;
			var response = {};
			var data = $.extend({}, obj, response);
			$('#xfzsqhskgl-dialog > p').html(_.template(self.xfzsqhskclTpl, data));
			$('#xfzsqhskgl-dialog').dialog({
				title: '消费者申请互生卡处理',
				width: 800,
				open: function (event, ui) {
					$(".ui-dialog-titlebar-close", $(this).parent()).show();
				},
				buttons: {
					"确定": function(){
						
						$(this).dialog("destroy");
					},
					"取消": function(){
						$(this).dialog("destroy");
					}
				}
			});
		}
	};
});