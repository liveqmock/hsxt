define(['text!eshopBusinessTpl/shfw/shfwlb.html',
		'text!eshopBusinessTpl/shfw/clsq.html',
		'text!eshopBusinessTpl/shfw/shxq.html',
		'text!eshopBusinessTpl/shfw/clgc.html',
		'text!eshopBusinessTpl/shfw/fhsq.html'
		], function(tpl, clsqTpl, shxqTpl, clgcTpl, fhsqTpl){
	return {
		searchTable : null,
		//重新加载数据标志
		loadFlag: null,
		showPage : function(){
			$('#busibox').html(tpl);
			
			var self = this,
			searchTable = null,
			//记录状态页签值
			s_shfwlb_zt = "";
			
			//状态页签切换
			$('#shfwlb_zt_tab > li').click(function(){
				var o = $(this),
				s_shfwlb_zt = o.data('value');
				o.addClass('cur').siblings().removeClass('cur');
			});
			
			//查询单击事件
			$('#btn_cx').click(function(){
				if (!self.validateData()) {
					return;
				}
				
				self.searchTable = searchTable = $.fn.bsgrid.init('shfwlb_table', {
					//url: '',
					localData: [{xh:1,ddbh:'1234567',yldmc:'天字一号',mjnc_hskh_sjhm:'06112121212',sqlx:'退货',tkje:20.00,thjf:10,sqsj:'2014-12-12',zt:'未处理',spmc:'秋冬新款长袖风衣',sqly:'尺码不对，请卖家核对后再发货',sm:'退货地址,广东深圳福田华强北'},{xh:2,ddbh:'1234568',yldmc:'天字二号',mjnc_hskh_sjhm:'06112121213',sqlx:'退货',tkje:30.00,thjf:20,sqsj:'2014-12-11',zt:'未处理',spmc:'秋冬新款长袖风衣',sqly:'尺码不对，请卖家核对后再发货',sm:'退货地址,广东深圳福田华强北'}],
					otherParames: {
						shfwlb_zt: s_shfwlb_zt
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
							if(colIndex == 5){
								return '<b class="red">' + searchTable.getRecordIndexValue(record, 'tkje') + '</b>';
							}
							if(colIndex == 6){
								return '<b class="blue">PV' + searchTable.getRecordIndexValue(record, 'thjf') + '</b>';
							}
							return $('<a title="查看详情">查看详情</a>').click(function(e){
								self.showDetail(searchTable.getRecord(rowIndex));
							});
						},
						edit: function(record, rowIndex, colIndex, options){
							if(colIndex == 5 || colIndex == 6){
								return;
							}
							return $('<a class="ml10" title="处理申请">处理申请</a>').click(function(e){
								self.handleApp(searchTable.getRecord(rowIndex));
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
				formID : '#ddxstj_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		showDetail: function(obj){
			var self = this;
			var ddh = obj.ddh;
			//显示菜单
			$('#shfw_shxq').trigger('click');
			
			//根据订单编号，数据从后台获取
			var response = {
				nc: '小鱼',
				kd: '申通快递',
				wlgsid: '5',
				kddh: '123456789',
				//订单商品列表
				ddsplb: [{zp:'resources/img/product02.jpg',mc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XL 160/165'},{zp:'resources/img/product01.jpg',mc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'黑色',cm:'XXL 170/175'},{zp:'resources/img/product03.jpg',mc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'白色',cm:'XXL 170/175'},{zp:'resources/img/product02.jpg',mc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 160/165'}],
				//申请照片列表
				sqzplb: [{url:'resources/img/store_noimg.gif'},{url:'resources/img/store_noimg.gif'},{url:'resources/img/store_noimg.gif'},{url:'resources/img/store_noimg.gif'},{url:'resources/img/store_noimg.gif'}],
				//处理过程信息
				clgcxx: [{rq:'2015-01-10',sq:'平台处理中',sm:'申请换货'},{rq:'2015-01-10',sq:'小鱼申请平台介入',sm:'申请换货'},{rq:'2015-01-10',sq:'买家拒绝换货',sm:'申请换货'},{rq:'2015-01-10',sq:'小鱼申请换货',sm:'申请换货'}],
				//物流名称列表
				wlmclb: "[{name:'顺丰快递',value:'1'},{name:'邮政快递',value:'2'},{name:'韵达快递',value:'3'},{name:'中通快递',value:'4'},{name:'申通快递',value:'5'},{name:'天天快递',value:'6'},{name:'如风达快递',value:'7'},{name:'日日顺快递',value:'8'}]"
			};
			//扩展json数据
			var data = $.extend(true, {}, obj, response);
			$('#shfwlb_content2').removeClass('none').html(_.template(shxqTpl, data));
			$('#shfwlb_content3').html(_.template(clgcTpl));
			var oGrid = $.fn.bsgrid.init('shfwlb_clgc_table', {
				localData: response.clgcxx,
				//不显示空白行
				displayBlankRows: false,
				//隔行变色
				stripeRows: true,
				//不显示选中行背景色
				rowSelectedColor: false,
				//不分页
				pageAll: true,
				//不显示分页工具条
				displayPagingToolbarOnlyMultiPages: true
			});
			
			//订单商品图片切换
			$(".goods_detail .page i").click(function () {
				var o = $(this);
				o.addClass('cur').siblings().removeClass('cur');
				$(".goods_detail dl").hide().eq(o.index()).show();
			});
			//图片切换
			$(".tr_img").hover(function () {
				$(this).find('.td_img_btn_left, .td_img_btn_right').stop(false, true).show()
			}, function () {
				$(this).find('.td_img_btn_left, .td_img_btn_right').stop(false, true).hide()
			});
			//图片切换-左箭头
			$(".td_img_btn_left").click(function () {
				var imgWarp = $(this).siblings(".td_img").children(".inner_img");
				var l = parseInt(imgWarp.css("left"));
				imgWarp.animate({
					left : l < 0 ? "+=77px" : 0
				}, 200);
			});
			//图片切换-右箭头
			$(".td_img_btn_right").click(function () {
				var imgWarp = $(this).siblings(".td_img").children(".inner_img"),
				imgNum = imgWarp.children("img").size();
				imgWarp.width(imgNum * 77 + 'px');
				var n = imgNum - 4;
				if (parseInt(imgWarp.css("left")) > -77 * n) {
					imgWarp.animate({
						left : "-=77px"
					}, 200)
				};
			});
			//回复申请按钮
			$('#btn_shfwlb_fhsq').click(function(){
				//调用处理申请的函数，通过加shxq进行标识
				self.handleApp($.extend(data, {shxq_fhsq:1}));
			});
			//发货按钮
			$('#btn_shfwlb_spfh').click(function(){
				$('#shfwlb-dialog p').html(_.template(fhsqTpl, data));
				$('#shfwlb-dialog').dialog({
					title: '发货',
					width: 400,
					height: 260,
					open: function (event, ui) {
						$(".ui-dialog-titlebar-close", $(this).parent()).show();
					},
					buttons: {
						"保存": function(){
							$(this).dialog("destroy");
							//刷新表格
							self.backAndRefresh()
						},
						"取消": function(){
							$(this).dialog("destroy");
						}
					}
				});
				//下拉列表框
				$('#shfwlb_fh_kd').selectList({
					height: 28,
					width: 205,
					optionWidth: 205,
					optionHeight: 130
				});
			});
			//确认收货按钮
			$('#btn_shfwlb_qrsh').click(function(){
				//返回并刷新
				self.backAndRefresh();
			});
			//返回按钮
			$('#btn_shfwlb_fh, #btn_shfwlb_fh1').click(function(){
				//返回并刷新
				self.backAndRefresh();
			});
		},
		//返回并刷新
		backAndRefresh : function(flag){
			if(flag !== 'SKIP'){
				//重新加载数据标志
				this.loadFlag = 0;
				//调用菜单处理
				$('#shfw_shfwlb').trigger('click');
			}
			
			//局部刷新表格
			if(this.searchTable) this.searchTable.refreshPage();
		},
		//处理申请
		handleApp: function(obj){
			var self = this;
			var data = $.extend(true, {}, obj);
			
			var flag = data.shxq_fhsq ? '' : 'SKIP';
			
			$('#shfwlb-dialog p').html(_.template(clsqTpl, data));
			$('#shfwlb-dialog').dialog({
				title: data.shxq_fhsq ? '回复申请' : '处理申请',
				width: data.shxq_fhsq ? 400 : 600,
				open: function (event, ui) {
					$(".ui-dialog-titlebar-close", $(this).parent()).show();
				},
				buttons: {
					"同意退款": function(){
						$(this).dialog("destroy");
						//刷新表格
						self.backAndRefresh(flag);
					},
					"同意退货": function(){
						$(this).dialog("destroy");
						//刷新表格
						self.backAndRefresh(flag);
					},
					"拒绝": function(){
						$(this).dialog("destroy");
						//刷新表格
						self.backAndRefresh(flag);
					}
				}
			});
		}
	};
});