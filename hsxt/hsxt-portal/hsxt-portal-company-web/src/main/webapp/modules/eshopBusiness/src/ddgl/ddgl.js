define(['text!eshopBusinessTpl/ddgl/ddgl.html',
	'text!eshopBusinessTpl/ddgl/fh.html',
	'text!eshopBusinessTpl/ddgl/xgyf.html',
	'text!eshopBusinessTpl/ddgl/sh.html',
	'text!eshopBusinessTpl/ddgl/ddxq.html'
	], function(tpl, fhTpl, xgyfTpl, shTpl, ddxqTpl){
	return {
		searchTable : null,
		//重新加载数据标志
		loadFlag: null,
		showPage : function(){
			$('#busibox').html(tpl);
			
			var self = this,
			searchTable = null,
			//记录状态页签值
			s_ddgl_zt = "";
			
			//状态页签切换
			$('#ddgl_zt_tab > li > a').click(function(){
				var o = $(this),
				s_ddgl_zt = o.attr('data-value');
				o.addClass('active').parent().siblings().find('a').removeClass('active');
				$('#ddgl_content1').removeClass('none');
				$('#ddgl_content2').addClass('none').html('');
			});

			/*日期控件*/
			$("#ddgl_beginDate").datepicker({dateFormat:'yy-mm-dd'});
			$("#ddgl_endDate").datepicker({dateFormat:'yy-mm-dd'});
			/*营业点下拉列表*/
			$("#ddgl_yyd").selectList({
				options:[
					{name:'全部',value:''},
					{name:'营业点1',value:'1'},
					{name:'营业点2',value:'2'},
					{name:'营业点3',value:'3'}
				]	
			});
			
			//查询单击事件
			$('#btn_cx').click(function(){
				if (!self.validateData()) {
					return;
				}
				

				self.searchTable = searchTable = $.fn.bsgrid.init('ddgl_table', {
					//url: '',
					localData: [{id:'xx01',ddsj:'2014-12-12 09:10:00',ddbh:'1234567',yhm:'小鱼儿',yyd:'南山营业点',scmc:'商城名称1',sfk:'200.00',yf:'10.00',xfjf:'20.00',psfs:'快递',xffs:'互生币',jyzt:'待商家发货',splb:[{id:'sp001',spzp:'resources/img/product01.jpg',spmc:'222222222222222222222222',ysfl:'白色',dj:'100.00',sl:'2'}]},{id:'xx02',ddsj:'2014-12-11 11:10:00',ddbh:'1234566',yhm:'老鱼儿',yyd:'福田营业点',scmc:'商城名称2',sfk:'490.00',yf:'0.00',xfjf:'49.00',psfs:'快递',xffs:'互生币',jyzt:'商家待送货',splb:[{id:'sp001',spzp:'resources/img/product01.jpg',spmc:'222222222222222222222222',ysfl:'白色',dj:'100.00',sl:'1'},{id:'sp002',spzp:'resources/img/product03.jpg',spmc:'11111111111111111111111',ysfl:'白色',dj:'100.00',sl:'1'},{id:'sp003',spzp:'resources/img/product02.jpg',spmc:'比菲力冬款男士休闲裤 加绒裤',ysfl:'灰色',dj:'290.00',sl:'1'}]},{id:'xx03',ddsj:'2014-12-10 10:10:00',ddbh:'1234565',yhm:'鱼儿',yyd:'南山营业点1',scmc:'商城名称3',sfk:'290.00',yf:'0.00',xfjf:'29.00',psfs:'快递',xffs:'互生币',jyzt:'待商家发货',splb:[{id:'sp001',spzp:'resources/img/product01.jpg',spmc:'222222222222222222222222',ysfl:'白色',dj:'100.00',sl:'1'},{id:'sp002',spzp:'resources/img/product02.jpg',spmc:'比菲力冬款男士休闲裤 加绒裤',ysfl:'灰色',dj:'190.00',sl:'1'}]},{id:'xx04',ddsj:'2014-12-09 10:10:00',ddbh:'1234564',yhm:'鱼儿1',yyd:'南山营业点2',scmc:'商城名称4',sfk:'540.00',yf:'0.00',xfjf:'54.00',psfs:'快递',xffs:'互生币',jyzt:'待商家发货',splb:[{id:'sp001',spzp:'resources/img/product01.jpg',spmc:'222222222222222222222222',ysfl:'白色',dj:'90.00',sl:'1'},{id:'sp002',spzp:'resources/img/product02.jpg',spmc:'比菲力冬款男士休闲裤 加绒裤',ysfl:'灰色',dj:'90.00',sl:'1'},{id:'sp003',spzp:'resources/img/product03.jpg',spmc:'比菲力冬款男士休闲裤 加绒裤',ysfl:'灰色',dj:'90.00',sl:'1'},{id:'sp004',spzp:'resources/img/product01.jpg',spmc:'比菲力冬款男士休闲裤 加绒裤',ysfl:'灰色',dj:'90.00',sl:'1'},{id:'sp005',spzp:'resources/img/product02.jpg',spmc:'比菲力冬款男士休闲裤 加绒裤',ysfl:'灰色',dj:'90.00',sl:'1'},{id:'sp006',spzp:'resources/img/product03.jpg',spmc:'比菲力冬款男士休闲裤 加绒裤',ysfl:'灰色',dj:'90.00',sl:'1'}]},{id:'xx05',ddsj:'2014-12-08 10:10:20',ddbh:'1234563',yhm:'小鱼儿',yyd:'南山营业点1',scmc:'商城名称3',sfk:'290.00',yf:'0.00',xfjf:'29.00',psfs:'快递',xffs:'互生币',jyzt:'待商家发货',splb:[{id:'sp001',spzp:'resources/img/product01.jpg',spmc:'222222222222222222222222',ysfl:'白色',dj:'100.00',sl:'1'},{id:'sp002',spzp:'resources/img/product02.jpg',spmc:'比菲力冬款男士休闲裤 加绒裤',ysfl:'灰色',dj:'190.00',sl:'1'}]},{id:'xx06',ddsj:'2014-12-07 10:10:20',ddbh:'1234563',yhm:'小鱼儿',yyd:'南山营业点1',scmc:'商城名称2',sfk:'290.00',yf:'0.00',xfjf:'29.00',psfs:'快递',xffs:'互生币',jyzt:'待商家发货',splb:[{id:'sp001',spzp:'resources/img/product01.jpg',spmc:'222222222222222222222222',ysfl:'白色',dj:'100.00',sl:'1'},{id:'sp002',spzp:'resources/img/product02.jpg',spmc:'比菲力冬款男士休闲裤 加绒裤',ysfl:'灰色',dj:'190.00',sl:'1'}]},{id:'xx07',ddsj:'2014-12-06 10:10:20',ddbh:'1234562',yhm:'小鱼儿',yyd:'南山营业点1',scmc:'商城名称2',sfk:'0.00',yf:'0.00',xfjf:'0.00',psfs:'快递',xffs:'互生币',jyzt:'待商家发货',splb:[]}],
					otherParames: {
						ddgl_zt: s_ddgl_zt
					},
					//不显示空白行
					displayBlankRows: false,
					//不显示无页可翻的提示
					pageIncorrectTurnAlert: false,
					//不显示选中行背景色
					rowSelectedColor: false,
					pageSize: 5,
					additionalRenderPerColumn: function(record, rowIndex, colIndex, tdObj, trObj, options){
						var data = searchTable.getRecord(rowIndex),
						sTpl = '<span class="mr15"><%=obj.ddsj%></span><span class="mr15">订单编号：<%=obj.ddbh%></span><span class="mr15"><%=obj.yhm%></span><span class="mr15"><%=obj.yyd%></span><span><%=obj.scmc%></span>';
						trObj.find('td').attr('colspan', '9').css({'background-color':'#6baf58', 'color':'#fff'}).html(_.template(sTpl, data));

						var splb = data.splb,
						len = splb.length,
						sTpl_1 = '<td style="text-align:left;"><div class="img-group-detail2"><img src="<%=obj.spzp%>" class="fl" width="80" height="80"><div class="img-group-content"><p><a href="#" class="blue"><%=obj.spmc%></a></p><p>颜色分类：<%=obj.ysfl%></p></div></div></td><td style="text-align:left;"><%=obj.dj%></td><td style="text-align:left;"><%=obj.sl%></td>',
						sTpl_2 = '<%var _rowspan = obj.splb.length > 1 ? \' rowspan="\' + obj.splb.length + \'"\' : \'\';%><td style="text-align:left;"<%=_rowspan%>><p class="red fb">￥<%=obj.sfk%></p><p>含运费（<%=obj.yf%>）</p></td><td style="text-align:left;"<%=_rowspan%>><p class="blue">PV<%=obj.xfjf%></p></td><td style="text-align:left;"<%=_rowspan%>><%=obj.psfs%></td><td style="text-align:left;"<%=_rowspan%>><%=obj.xffs%></td><td style="text-align:left;"<%=_rowspan%>><%=obj.jyzt%></td><td style="text-align:left;"<%=_rowspan%>></td>',
						jyzt = data.jyzt;
						//用的是after追加方式，所以从最后一个开始
						for(var i = len - 1; i >= 0; i--) {
							var $tr = $('<tr></tr>');
							$tr.append(_.template(sTpl_1, splb[i]));
							if(i === 0) {
								$tr.append(_.template(sTpl_2, data));
								var $buttons = $('<div></div>');
								//查看详情
								$buttons.append($('<a title="查看详情">查看详情</a>').click(function(e){
									self.showDetail(searchTable.getRecord(rowIndex));
								}));
								//根据交易状态加载不同的按钮
								if (jyzt === '待商家发货') {
									$buttons.append($('<a class="ml10" title="发货">发货</a>').click(function(e){
										self.doSendGoods(searchTable.getRecord(rowIndex));
									})).append($('<a class="ml10" title="修改运费">修改运费</a>').click(function(e){
										self.doModFreight(searchTable.getRecord(rowIndex));
									}));

								} else if (jyzt === '商家待送货') {
									$buttons.append($('<a class="ml10" title="收货">收货</a>').click(function(e){
										self.doReceiveGoods(searchTable.getRecord(rowIndex));
									}));
								}
								$tr.find('td:last').append($buttons);
							}
							trObj.after($tr);
						}
					}
				});
			});
			//这一行需要去掉，此处用于显示数据
			$('#btn_cx').click();
		},
		validateData : function(){
			return comm.valid({
				formID : '#ddgl_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		//详细
		showDetail: function(obj){
			var self = this;
			var ddbh = obj.ddbh;
			
			//根据订单编号，数据从后台获取
			var response = {
				qyhsh: '126548755333',
				xfz: '126548755',
				shr: '126548755',
				sjhm: '13800000',
				dz: '广东省深圳市福田区华新群星广场32',
				yb: '518000',
				sffs: '送货上门',
				psyyd: 'xxxxx',
				zffs: '货到付款',
				mjbz: 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx',
				fptt: 'xxxxx公司',
				fpnr: '办公用品',
				xfzjf: '36.00',
				sphj: '209.00'
			};
			//扩展json数据
			var data = $.extend(true, {}, obj, response);

			$('#ddgl_content1').addClass('none');
			$('#ddgl_content2').removeClass('none').html(_.template(ddxqTpl, data));

			var splbTable = $.fn.bsgrid.init('ddgl_splb_table', {
				//url: '',
				localData: data.splb,
				//不显示空白行
				displayBlankRows: false,
				//不显示无页可翻的提示
				pageIncorrectTurnAlert: false,
				//不显示选中行背景色
				rowSelectedColor: false,
				pageAll: 1,
				stripeRows: 1,
				displayPagingToolbarOnlyMultiPages: 1,
				operate: {
					add: function(record, rowIndex, colIndex, options){
						var data2 = splbTable.getRecord(rowIndex);
						if(colIndex === 0) {
							return _.template('<div class="img-group-detail2"><img src="<%=obj.spzp%>" class="fl" width="80" height="80"><div class="img-group-content"><p><a href="#" class="blue"><%=obj.spmc%></a></p><p>颜色分类：<%=obj.ysfl%></p>', data2);
						} else if(colIndex === 1) {
							return '<span class="red fb">￥' + data2.dj + '</span>'
						} else if(colIndex === 3) {
							return '<p class="red fb">￥' + data.sfk + '</p><p>含运费（' + data.yf + '）</p>'
						} else if(colIndex === 4) {
							return '<span class="blue">PV' + data.xfjf + '</span>'
						}
					}
				}
			});

			//返回按钮
			$('#btn_ddgl_xq_fh').click(function() {
				$('#ddgl_content1').removeClass('none');
				$('#ddgl_content2').addClass('none').html('');
			});
		},
		//发货
		doSendGoods: function(obj){
			var self = this;
			var ddbh = obj.ddbh;

			//扩展json数据
			var data = $.extend(true, {}, obj);
			
			$('#ddgl-dialog > p').html(_.template(fhTpl, data));
			$('#ddgl-dialog').dialog({
				title: '填写物流信息',
				width: 600,
				height: 350,
				resizable: false,	
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

			//快递公司下拉列表
			$('#ddgl_fh_kdgs').selectList({
				width: 300,
				optionWidth: 300,
				optionHeight: 130,
				options: [{name:"快递公司1",value:"1"},{name:"快递公司2",value:"2"},{name:"快递公司3",value:"3"}]
			});
		},
		//修改运费
		doModFreight: function(obj){
			var self = this;
			var ddbh = obj.ddbh;

			//扩展json数据
			var data = $.extend(true, {}, obj);
			
			$('#ddgl-dialog > p').html(_.template(xgyfTpl, data));
			$('#ddgl-dialog').dialog({
				title: '修改运费',
				width: 350,
				resizable: false,	
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
		},
		//收货
		doReceiveGoods: function(obj){
			var self = this;
			var ddbh = obj.ddbh;

			//扩展json数据
			var data = $.extend(true, {}, obj);
			
			$('#ddgl-dialog > p').html(_.template(shTpl, data));
			$('#ddgl-dialog').dialog({
				title: '填写送货员信息',
				width: 600,
				height: 300,
				resizable: false,	
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

			//送货员下拉列表
			$('#ddgl_sh_fhy').selectList({
				width: 300,
				optionWidth: 300,
				optionHeight: 130,
				options: [{name:"送货员1",value:"1"},{name:"送货员2",value:"2"},{name:"送货员3",value:"3"}]
			});
		}
	};
});