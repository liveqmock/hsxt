define(['text!eshopBusinessTpl/ddglcy/cyddgl.html',
		'text!eshopBusinessTpl/ddglcy/ddxq.html',
		'text!eshopBusinessTpl/ddglcy/szzk.html',
		'text!eshopBusinessTpl/ddglcy/jsdc.html'
		], function(tpl, ddxqTpl, szzkTpl, jsdcTpl){
	return {
		searchTable : null,
		showPage : function(){
			$('#busibox').html(tpl);
			
			var self = this,
			searchTable = null;
			
			/*下拉列表*/
			$("#cyddgl_yyd").selectList({
				options:[
					{name:'全部',value:''},
					{name:'营业点1',value:'1'},
					{name:'营业点2',value:'2'},
					{name:'营业点3',value:'3'}
				]	
			});
			//付款信息下拉列表
			$("#cyddgl_fkxx").selectList({
				height: 24,
				options:[
					{name:'全部',value:''},
					{name:'客户已确认',value:'1'},
					{name:'待商家确认',value:'2'},
					{name:'待付款',value:'3'},
					{name:'已结算',value:'4'},
					{name:'申请取消待确认',value:'5'}
				]	
			}).change(function(e){
				var v = $(this).attr('optionValue');
				
			});
			//状态下拉列表
			$("#cyddgl_zt").selectList({
				height: 24,
				options:[
					{name:'全部',value:''},
					{name:'状态1',value:'1'},
					{name:'状态2',value:'2'}
				]	
			}).change(function(e){
				var v = $(this).attr('optionValue');
				
			});
			/*日期控件*/
			$("#cyddgl_beginDate").datepicker({dateFormat:'yy-mm-dd'});
			$("#cyddgl_endDate").datepicker({dateFormat:'yy-mm-dd'});
			
			//查询单击事件
			$('#btn_cx').click(function(){
				if (!self.validateData()) {
					return;
				}
				
				self.searchTable = searchTable = $.fn.bsgrid.init('cyddgl_table', {
					//url: '',
					localData: [{ddbh:'12477678089651200',sjhm:'13800138000',khxm:'张三',fkxx:'客户已确认',zt:'正常'},{ddbh:'12477678089651201',sjhm:'13800138000',khxm:'张三',fkxx:'待付款',zt:'正常'}],
					otherParames: {
						beginDate : $("#cyddgl_beginDate").val(),
						endDate : $("#cyddgl_endDate").val()
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
							return $('<a title="修改">修改</a>').click(function(e){
								self.doEdit(searchTable.getRecord(rowIndex));
							});
						},
						del: function(record, rowIndex, colIndex, options){
							return $('<a class="ml10" title="结算">结算</a>').click(function(e){
								self.doEdit(searchTable.getRecord(rowIndex));
							});
						}
					}
				});
			});
			//这一行需要去掉，此处用于显示数据
			$('#btn_cx').click();
		},
		validateData : function(){
			return comm.valid({
				formID : '#cyddgl_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		//修改、结算
		doEdit : function(obj){
			var self = this;
			
			//根据订单从后台查询数据
			var response = {
				ddly: '手机订单',
				zkl: 0,
				sfk: '200.00',
				yjddsj: '2015-01-01 16:25',
				jcrs: 5,
				yjrjxf: '50',
				yyfsjdj: '100.00',
				gkly: '少辣，准备一个儿童凳子',
				spxx: [{id:'xx01',spzp:'resources/img/food01.jpg',spmc:'青椒肉丝',gg:'份',cpzt:'客户下单',dj:'15.00',sl:'1',xfjf:'1.00',spzj:'15.00',xfzjf:'1.00'},{id:'xx02',spzp:'resources/img/food01.jpg',spmc:'青椒肉丝',gg:'份',cpzt:'客户下单',dj:'15.00',sl:'1',xfjf:'1.00',spzj:'15.00',xfzjf:'1.00'},{id:'xx03',spzp:'resources/img/food01.jpg',spmc:'青椒肉丝',gg:'份',cpzt:'客户下单',dj:'15.00',sl:'1',xfjf:'1.00',spzj:'15.00',xfzjf:'1.00'}],
				xfjfzj: '3.00',
				cwf: '30.00',
				fwf: '25.00',
				yfk: '100.00',
				ddje: '45.00',
				//菜单分类列表
				cdfllb: [{id:'cd01',name:'新品上市'},{id:'cd02',name:'招牌菜'},{id:'cd03',name:'刺身'},{id:'cd04',name:'中厨'},{id:'cd05',name:'烧味'},{id:'cd06',name:'汤'},{id:'cd07',name:'主食'},{id:'cd08',name:'酒水'},{id:'cd09',name:'甜品'},{id:'cd10',name:'点心'},{id:'cd11',name:'果盘'},{id:'cd12',name:'深海鱼'}],
				//菜单列表
				cdlb: [{id:'c001',catId:'cd01',name:'川味腊肉+排骨煲仔饭',price:'22.00',py:'CWLRPGBZF'},{id:'c002',catId:'cd03',name:'荷包蛋',price:'3.00',py:'HBD'},{id:'c003',catId:'cd02',name:'广式腊味煲仔饭',price:'25.00',py:'GSLWBZF'},{id:'c004',catId:'cd01',name:'农家小炒肉饭',price:'22.00',py:'NJXCRF'},{id:'c005',catId:'cd01',name:'川味腊肉+排骨煲仔饭',price:'22.00',py:'CWLRPGBZF'},{id:'c006',catId:'cd03',name:'荷包蛋',price:'3.00',py:'HBD'},{id:'c007',catId:'cd02',name:'广式腊味煲仔饭',price:'25.00',py:'GSLWBZF'},{id:'c008',catId:'cd01',name:'农家小炒肉饭',price:'22.00',py:'NJXCRF'},{id:'c009',catId:'cd01',name:'川味腊肉+排骨煲仔饭',price:'22.00',py:'CWLRPGBZF'},{id:'c010',catId:'cd01',name:'荷包蛋',price:'3.00',py:'HBD'},{id:'c011',catId:'cd02',name:'广式腊味煲仔饭',price:'25.00',py:'GSLWBZF'},{id:'c012',catId:'cd02',name:'农家小炒肉饭',price:'22.00',py:'NJXCRF'}]
			};
			//扩展json数据
			var data = $.extend(true, {}, obj, response);
			//显示菜单
			comm.liActive_add($('#ddgl_cyddgl_xq'));
			//切换工作区
			$('#busibox').addClass('none');
			//加载订单信息、详情、按钮区
			$('#busibox_xq').removeClass('none').html(_.template(ddxqTpl, data));

			//加载商品清单数据表格
			var cyddgl_spcdTable= $.fn.bsgrid.init('cyddgl_spcd_table', {
				//url: '',
				localData: data.spxx,
				//不显示空白行
				displayBlankRows: false,
				//不显示无页可翻的提示
				pageIncorrectTurnAlert: false,
				//隔行变色
				stripeRows: true,
				//不显示选中行背景色
				rowSelectedColor: false,
				pageAll: 1,
				displayPagingToolbarOnlyMultiPages: 1,
				operate: {
					add: function(record, rowIndex, colIndex, options){
						//单价、商品总价
						if(colIndex === 3 || colIndex === 6){
							return '<img src="resources/img/price.png" width="18" height="18" style="vertical-align:middle;"/><b class="red">' + cyddgl_spcdTable.getRecordIndexValue(record, colIndex === 3 ? 'dj' : 'spzj') + '</b>';
						}
						//数量
						if(colIndex === 4){
							var $obj = $('<span class="changNum"></span>');
							//减少按钮
							$obj.append($('<input class="cnbtn cnbtn_minus" type="button" value=""/>').click(function(){
								var oTxt = $(this).next(),
								v = parseInt(oTxt.val());
								if(!isNaN(v) && v > 1){
									v -= 1;
									oTxt.val(v);
									
								}
							}));
							//文本框
							$obj.append($('<input class="cntxt" type="text" readonly="readonly" value="' + cyddgl_spcdTable.getRecordIndexValue(record, 'sl') + '"/>'));
							//增加按钮
							$obj.append($('<input class="cnbtn cnbtn_add" type="button" value=""/>').click(function(){
								var oTxt = $(this).prev(),
								v = parseInt(oTxt.val());
								if(!isNaN(v)){
									v += 1;
									oTxt.val(v);
									
								}
							}));
							return $obj;
						}
						//消费积分、消费总积分
						if(colIndex === 5 || colIndex === 7){
							return '<img src="resources/img/pv.png" width="25" height="14" style="vertical-align:middle;"/><span class="blue">' + cyddgl_spcdTable.getRecordIndexValue(record, colIndex === 5 ? 'xfjf' : 'xfzjf') + '</span>';
						}
						return $('<a title="删除">删除</a>').click(function(e){
							
						});
					}
				},
				renderImg : function(record, rowIndex, colIndex, options){
					return _.template('<div class="img-group-detail2"><img src="<%=obj.spzp%>" class="fl" width="50" height="50"/><div class="img-group-content"><p><a href="#" class="blue"><%=obj.spmc%></a></p></div></div>', {
						spzp: cyddgl_spcdTable.getRecordIndexValue(record, 'spzp'),
						spmc: cyddgl_spcdTable.getRecordIndexValue(record, 'spmc')
					});
				}
			});
			
			//取消按钮
			$('#btn_cyddgl_xq_qx').click(function(){
				comm.liActive($('#ddgl_cyddgl'), '#ddgl_cyddgl_xq', '#busibox_xq, #busibox_xq_ddxx');
			});
			//确认按钮
			$('#btn_cyddgl_xq_qr').click(function(){
				comm.liActive($('#ddgl_cyddgl'), '#ddgl_cyddgl_xq', '#busibox_xq, #busibox_xq_ddxx');
				
				
			});
			//折扣按钮
			$('#cyddgl_xq_btn_zk').click(function(){
				$('#cyddgl-dialog > p').html(_.template(szzkTpl, data));
				$('#cyddgl-dialog').dialog({
					title: '设置折扣',
					width: 400,
					resizable: 0,
					open: function (event, ui) {
						$(".ui-dialog-titlebar-close", $(this).parent()).show();
					},
					buttons: {
						"修改": function(){
							$(this).dialog("destroy");
							
							
						},
						"保持": function(){
							$(this).dialog("destroy");
						}
					}
				});
				//折扣率计算
				$('#szzk_zkl').keyup(function(){
					var zkl = comm.convertFloat($(this).val());
					if(!isNaN(zkl) && zkl >=70 && zkl <= 100){
						zkl = 100 - zkl;
						var ddje = comm.convertFloat($('#szzk_ddje').text()),
						zhje = (zkl * ddje * 100 / 10000).toFixed(2),
						yfje = ddje - zhje;
						$('#szzk_zhje').val(zhje);
						$('#szzk_yfje').text(yfje.toFixed(2));
					}
				});
			});
			//继续点菜按钮
			$('#cyddgl_xq_btn_jsdc').click(function(){
				$('#cyddgl-dialog > p').html(_.template(jsdcTpl, data));
				$('#cyddgl-dialog').dialog({
					title: '点菜',
					width: 800,
					height: 'auto',
					resizable: 0,
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
				//菜单分类单击
				$('#food_tabs li a').click(function(){
					$(this).addClass('active').parent().siblings().find('a').removeClass('active');
					
					$('#btn_jsdc_cx').trigger('click');
				});
				//查询按钮单击
				$('#btn_jsdc_cx').click(function(){
					var catId = $('#food_tabs li a.active').data('id'),
					cppy = $('#btn_jsdc_cppy').val(),
					//组装条件
					condition = (catId ? 'v.catId === "' + catId + '"' : '1') + (cppy ? ' && (v.py.indexOf("' + cppy.toUpperCase() + '") != -1 || v.name.indexOf("' + cppy + '") != -1)' : ''),
					//模板
					sTpl = '<%$.each(obj.cdlb, function(k, v){ if (' + condition + ') {%><li><a data-id="<%=v.id%>" title="<%=v.name%>"><span class="fl"><%=v.name%></span><span class="fr"><img src="resources/img/price.png" width="18" height="18" style="vertical-align:middle;"><%=v.price%></span></a></li><%}})%>';
					//填充内容及绑定事件
					$('#food_lists').html(_.template(sTpl, data)).find('li a').unbind().bind('click', function(){
						$(this).toggleClass("active");
					});
				});
				//查询菜品输入框按下回车键
				$('#btn_jsdc_cppy').bind('keydown',function(evt){
					var ev = evt || window.event;
					if(ev.keyCode == 13){
						$('#btn_jsdc_cx').trigger('click');
					}
				});
			});
			//去结算按钮
			$('#cyddgl_xq_btn_qjs').click(function(){
				
			});
		}
	};
});