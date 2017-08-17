define(['text!eshopBusinessTpl/ddglwm/wmddgl.html',
		'text!eshopBusinessTpl/ddglwm/ddxq.html',
		'text!eshopBusinessTpl/ddglwm/sc.html',
		'text!eshopBusinessTpl/ddglcy/jsdc.html'
		], function(tpl, ddxqTpl, scTpl, jsdcTpl){
	return {
		searchTable : null,
		showPage : function(){
			$('#busibox').html(tpl);
			
			var self = this,
			searchTable = null;
			
			/*下拉列表*/
			$("#wmddgl_yyd").selectList({
				options:[
					{name:'全部',value:''},
					{name:'营业点1',value:'1'},
					{name:'营业点2',value:'2'},
					{name:'营业点3',value:'3'}
				]	
			});
			$("#wmddgl_fkxx").selectList({
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
			$("#wmddgl_zt").selectList({
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
			$("#wmddgl_beginDate").datepicker({dateFormat:'yy-mm-dd'});
			$("#wmddgl_endDate").datepicker({dateFormat:'yy-mm-dd'});
			
			//查询单击事件
			$('#btn_cx').click(function(){
				if (!self.validateData()) {
					return;
				}
				
				self.searchTable = searchTable = $.fn.bsgrid.init('wmddgl_table', {
					//url: '',
					localData: [{ddbh:'12477678089651200',sjhm:'13800138000',khxm:'张三',fkxx:'客户已确认',zt:'正常'},{ddbh:'12477678089651200',sjhm:'13800138000',khxm:'张三',fkxx:'待付款',zt:'正常'}],
					otherParames: {
						beginDate : $("#wmddgl_beginDate").val(),
						endDate : $("#wmddgl_endDate").val()
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
							return $('<a class="ml10" title="送餐">送餐</a>').click(function(e){
								self.doEdit(searchTable.getRecord(rowIndex));
							});
						},
						edit: function(record, rowIndex, colIndex, options){
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
				formID : '#wmddgl_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		//修改、送餐、结算
		doEdit : function(obj){
			var self = this;
			
			//根据订单从后台查询数据
			var response = {
				ddly: '手机订单',
				sfk: '200.00',
				yjsdsj: '2015-01-01 16:25',
				zffs: '在线支付(或者货到付款)',
				psf: '0.00',
				dz: 'xxxxxxxxxxxxxx',
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
			comm.liActive_add($('#ddgl_wmddgl_xq'));
			//切换工作区
			$('#busibox').addClass('none');
			//加载信息、按钮区、详情
			$('#busibox_xq').removeClass('none').html(_.template(ddxqTpl, data));

			//加载商品清单数据表格
			var wmddgl_spcdTable= $.fn.bsgrid.init('wmddgl_spcd_table', {
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
							return '<img src="resources/img/price.png" width="18" height="18" style="vertical-align:middle;"/><b class="red">' + wmddgl_spcdTable.getRecordIndexValue(record, colIndex === 3 ? 'dj' : 'spzj') + '</b>';
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
							$obj.append($('<input class="cntxt" type="text" readonly="readonly" value="' + wmddgl_spcdTable.getRecordIndexValue(record, 'sl') + '"/>'));
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
							return '<img src="resources/img/pv.png" width="25" height="14" style="vertical-align:middle;"/><span class="blue">' + wmddgl_spcdTable.getRecordIndexValue(record, colIndex === 5 ? 'xfjf' : 'xfzjf') + '</span>';
						}

						return $('<a title="删除">删除</a>').click(function(e){
							
						});
					}
				},
				renderImg : function(record, rowIndex, colIndex, options){
					return _.template('<div class="img-group-detail2"><img src="<%=obj.spzp%>" class="fl" width="50" height="50"/><div class="img-group-content"><p><a href="#" class="blue"><%=obj.spmc%></a></p></div></div>', {
						spzp: wmddgl_spcdTable.getRecordIndexValue(record, 'spzp'),
						spmc: wmddgl_spcdTable.getRecordIndexValue(record, 'spmc')
					});
				}
			});
			
			//取消按钮
			$('#btn_wmddgl_xq_qx').click(function(){
				comm.liActive($('#ddgl_wmddgl'), '#ddgl_wmddgl_xq', '#busibox_xq, #busibox_xq_ddxx');
			});
			//确认按钮
			$('#btn_wmddgl_xq_qr').click(function(){
				comm.liActive($('#ddgl_wmddgl'), '#ddgl_wmddgl_xq', '#busibox_xq, #busibox_xq_ddxx');
				
				
			});
			//送餐按钮
			$('#wmddgl_xq_btn_sc').click(function(){
				$('#wmddgl-dialog > p').html(_.template(scTpl, data));
				$('#wmddgl-dialog').dialog({
					title: '送餐',
					width: 400,
					resizable: 0,
					open: function (event, ui) {
						$(".ui-dialog-titlebar-close", $(this).parent()).show();
					},
					buttons: {
						"送餐": function(){
							$(this).dialog("destroy");
							
							
						},
						"取消": function(){
							$(this).dialog("destroy");
						}
					}
				});
			});
			//继续点菜按钮
			$('#wmddgl_xq_btn_jsdc').click(function(){
				$('#wmddgl-dialog > p').html(_.template(jsdcTpl, data));
				$('#wmddgl-dialog').dialog({
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
			$('#wmddgl_xq_btn_qjs').click(function(){
				
			});
		}
	};
});