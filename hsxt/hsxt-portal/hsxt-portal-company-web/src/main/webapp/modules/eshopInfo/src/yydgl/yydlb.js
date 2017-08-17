define(['text!eshopInfoTpl/yydgl/yydlb.html',
		'text!eshopInfoTpl/yydgl/xz_xg.html'
		], function(tpl, xzxgTpl){
	return {
		searchTable : null,
		showPage : function(){
			var self = this;
			
			$('#busibox').html(_.template(tpl));
				
			var searchTable = null;
			
			//下拉框
			$('#yydlb_yydlx').selectList({
				options: [
					{name:'全部',value:''},
					{name:'餐饮',value:'1'},
					{name:'零售',value:'2'}
				]
			});
			
			//查询单击事件
			$('#btn_cx').click(function(){
				if (!self.validateData()) {
					return;
				}
				
				self.searchTable = searchTable = $.fn.bsgrid.init('yydlb_table', {
					//url: '',
					localData: [{id:'xx01',yydmc:'北站店',yydlx:'餐饮',yydlxid:'1',yyddz:'深圳市福田区绒花路长宝大厦D段一楼D101',sq:'北区',zt:'正常'},{id:'xx02',yydmc:'北站店',yydlx:'餐饮',yydlxid:'1',yyddz:'深圳市福田区绒花路长宝大厦D段一楼D101',sq:'北区',zt:'正常'},{id:'xx03',yydmc:'北站店',yydlx:'零售',yydlxid:'2',yyddz:'深圳市福田区绒花路长宝大厦D段一楼D101',sq:'北区',zt:'正常'}],
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
							if(colIndex == 3){
								//显示地图定位
								return $('<a>' + searchTable.getRecordIndexValue(record, 'yyddz') + '</a>').click(function(e){
									self.showMap(searchTable.getRecord(rowIndex));
								});
							} else if(colIndex == 6){
								var $buttons = $('<p></p>');
								$buttons.append($('<a title="修改">修改</a>').click(function(e){
									self.doEdit(searchTable.getRecord(rowIndex));
								}));
								$buttons.append($('<a class="ml10" title="删除">删除</a>').click(function(e){
									self.doDel(searchTable.getRecord(rowIndex));
								}));
								if(searchTable.getRecordIndexValue(record, 'yydlx') === '餐饮'){
									$buttons.append($('<a class="ml10" title="设置菜单">设置菜单</a>').click(function(e){
										self.setMenu(searchTable.getRecord(rowIndex));
									}));
								}
								return $buttons;
							}
						}
					}
				});
			});
			//这一行需要去掉，此处用于显示数据
			$('#btn_cx').click();
			
			//批量删除按钮
			$('#btn_yydlb_del').click(function(){
				if (searchTable == null) return;
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
						//刷新表格
						if(searchTable)searchTable.refreshPage();
					}
				});
			});
			//添加按钮
			$('#btn_yydlb_add').click(function(){
				self.doEdit({});
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#yydlb_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		validateAddModData : function(){
			return comm.valid({
				formID : '#yydgl_xzxg_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		//显示地图定位
		showMap :  function(obj){
			var self = this;
			
			$('#shy_shy-dialog > p').html('');
			$('#shy_shy-dialog').dialog({
				title: '地图定位',
				width: 800,
				height: 400,
				open: function (event, ui) {
					$(".ui-dialog-titlebar-close", $(this).parent()).show();
				},
				buttons: {
					"确定": function(){
						$(this).dialog('destroy');
						//刷新表格
						if(self.searchTable)self.searchTable.refreshPage();
					}
				}
			});
		},
		//设置菜单
		setMenu : function(obj){
			var self = this;
			
			
		},
		doEdit : function(obj){
			var self = this,
			title = $.isEmptyObject(obj) ? '添加' : '修改';
			
			//从后台返回数据
			var response = {
				city: '深圳市',
				arealist: '[{name:"福田区",value:"1"},{name:"罗湖区",value:"2"},{name:"南山区",value:"3"},{name:"盐田区",value:"4"},{name:"宝安区",value:"5"},{name:"龙岗区",value:"6"},{name:"光明新区",value:"7"},{name:"龙华新区",value:"8"},{name:"坪山新区",value:"9"},{name:"大鹏新区",value:"10"}]',
				area: '1',
				address: '绒花路长宝大厦D段一楼D101',
				jd: '10.8547',
				wd: '18.4769',
				sssq: '14',
				sssqlist:'[{name:"八卦岭",value:"1"},{name:"保税区",value:"2"},{name:"笔架山",value:"3"},{name:"彩田村",value:"4"},{name:"车公庙",value:"5"},{name:"赤尾",value:"6"},{name:"福华新村",value:"7"},{name:"福民新村",value:"8"},{name:"福田区委",value:"9"},{name:"福田周边",value:"10"},{name:"岗厦",value:"11"},{name:"购物公园",value:"12"},{name:"皇岗",value:"13"},{name:"华强北",value:"14"},{name:"华强南",value:"15"},{name:"景田",value:"16"},{name:"莲花北",value:"17"}]',
				zt: 'resources/img/hslt_cpzs2.gif',
				xjt1: '',
				xjt2: '',
				xjt3: '',
				xjt4: '',
				xjt5: '',
				zjtp: [{id:'zjtp01',url:''}],
				ctfl: [{id:"fl01",name:"酒楼",checked:1},{id:"fl02",name:"餐厅"},{id:"fl03",name:"饭店"},{id:"fl04",name:"菜馆"},{id:"fl05",name:"快餐店"},{id:"fl06",name:"火锅店"},{id:"fl07",name:"茶馆"},{id:"fl08",name:"茶楼",checked: 1}],
				jyts: [{id:"jy01",name:"西餐"},{id:"jy02",name:"中餐",checked:1},{id:"jy03",name:"日式",checked:1},{id:"jy04",name:"意式"},{id:"jy05",name:"法式"},{id:"jy06",name:"韩式"},{id:"jy07",name:"海鲜"}],
				gclb: [{id:"gc01",name:"早餐",checked:1},{id:"gc02",name:"午餐",checked:1},{id:"gc03",name:"晚餐"},{id:"gc04",name:"宴会"}],
				jyms: '1',
				jymslist: '[{name:"加盟店",value:"1"},{name:"直营店",value:"2"}]',
				yysjlist: '[{name:"请选择",value:""},{name:"周一至周五",value:"1"},{name:"周一至周六",value:"2"},{name:"周六至周日",value:"3"},{name:"每天",value:"4"}]',
				yysj: [],
				jdsjlist: '[{name:"请选择",value:""},{name:"周一至周五",value:"1"},{name:"周一至周六",value:"2"},{name:"周六至周日",value:"3"},{name:"每天",value:"4"}]',
				jdsj: [],
				zxfg: [{id:"zx01",name:"欧式",checked:1},{id:"zx02",name:"韩式"},{id:"zx03",name:"中式"}],
				mj: [{id:"zx01",name:"<50",checked:1},{id:"zx02",name:"50-100"},{id:"zx03",name:"100-300"}],
				zwlxjsl: [{id:"zx01",name:"4人桌，40",checked:1},{id:"zx02",name:"5人桌，20"}],
				bjlxjsl: [{id:"zx01",name:"大包间（50-80）人，5个",checked:1},{id:"zx02",name:"中包间（20-50）人，10个"}],
				zbhj: [{id:"zx01",name:"停车场",checked:1},{id:"zx02",name:"购物中心"},{id:"zx03",name:"洗浴中心"}]
			};
			//扩展json数据
			var data = $.extend(true, {}, obj, response);
			
			//显示菜单
			comm.liActive_add($('#yydgl_xzxgyyd'));
			$('#busibox').addClass('none');
			$('#busibox_xzxg').removeClass('none').html(_.template(xzxgTpl, data));
			
			//下拉框
			//营业点类型
			$('#yydlb_xzxg_yydlx').selectList({
				options: [
					{name:'餐饮',value:'1'},
					{name:'零售',value:'2'}
				]
			});
			//选择区域
			$('#yydlb_xzxg_area').selectList({
				width: 210,
				height: 32,
				optionWidth: 210,
				optionHeight: 200
			}).change(function(){
				var v = $(this).attr('optionValue');
			});
			//所属商圈
			$('#yydlb_xzxg_sssq').selectList({
				width: 300,
				optionWidth: 300,
				optionHeight: 200
			});
			//经营模式
			$('#yydlb_xzxg_jyms').selectList({
				width: 300,
				optionWidth: 300
			});
			//营业点时间
			$('.yydlb_xzxg_yysj').each(function(){
				$(this).selectList({
					width: 115,
					optionWidth: 115
				});
			});
			//接单时间
			$('.yydlb_xzxg_jdsj').each(function(){
				$(this).selectList({
					width: 115,
					optionWidth: 115
				});
			});
			
			//营业点照片显示删除图片按钮
			$('#yydlb_xzxg_yydzp > li').mouseover(function(){
				var o = $(this);
				if(o.data('flag') == '1') {
					o.children(".delImg").show();
				}
			}).mouseout(function(){
				$(this).children(".delImg").hide();	
			});
			//营业点照片删除图片
			$("#yydlb_xzxg_yydzp .delImg").live('click', function(){
				var o = $(this),
				oP = o.parent("li");
				oP.data('flag', '0').find('img').attr('src', oP.index() == 0 ? 'resources/img/store_main_img_1.gif' : 'resources/img/store_noimg_1.gif');
				o.hide();
			});
			
			//证件图片显示删除图片按钮
			$('#yydlb_xzxg_zjtp > li:not(".zjtpAdd")').hover(function(){
				$(this).children(".delImg").show();
			}, function(){
				$(this).children(".delImg").hide();
			});
			//证件图片删除图片
			$("#yydlb_xzxg_zjtp .delImg").on('click', function(){
				$(this).parent("li").remove();
			});
			
			//营业时间添加
			$('#yydlb_xzxg_yysj_tj').click(function(){
				if($('.new_yysj_tr').length < 2){
					var o = $(".add_yysj_tr").clone(true).removeClass("add_yysj_tr").addClass("new_yysj_tr");
					o.find('input[name="yydlb_xzxg_yysj"]').addClass('yydlb_xzxg_yysj').selectList({
						width: 115,
						optionWidth: 115
					});
					o.insertAfter($('.yysj_tr')).show();
				}
			});
			//营业时间删除
			$(".yydlb_xzxg_yysj_del").click(function(){
				$(this).parent().parent("tr").remove();
			});
			
			//接单时间添加
			$('#yydlb_xzxg_jdsj_tj').click(function(){
				if($('.new_jdsj_tr').length < 2){
					var o = $(".add_jdsj_tr").clone(true).removeClass("add_jdsj_tr").addClass("new_jdsj_tr");
					o.find('input[name="yydlb_xzxg_jdsj"]').addClass('yydlb_xzxg_jdsj').selectList({
						width: 115,
						optionWidth: 115
					});
					o.insertAfter($('.jdsj_tr')).show();
				}
			});
			//接单时间删除
			$(".yydlb_xzxg_jdsj_del").click(function(){
				$(this).parent().parent("tr").remove();
			});
			
			//显示隐藏复选下拉列表
			$('.feellist_div').hover(function () {
				$(this).children(".feellist").show();
			}, function () {
				$(this).children(".feellist").hide();
			}).find('.feellist_chk').click(function(){
				//复选框选中事件
				var a = [],
				oP = $(this).parents('.feellist');
				$.each(oP.find('.feellist_chk'), function(k, v){
					if(v.checked) {
						a.push($(v).data('text'));
					}
				});
				oP.next().val(a.join(','));
			});
			
			//取消按钮
			$('#yydlb_xzxg_btn_qx').click(function(){
				//显示菜单
				comm.liActive($('#yydgl_yydlb'), '#yydgl_xzxgyyd', '#busibox_xzxg');
			});
			//保存按钮
			$('#yydlb_xzxg_btn_bc').click(function(){
				if (!self.validateAddModData()) {
					return;
				}
				//显示菜单
				comm.liActive($('#yydgl_yydlb'), '#yydgl_xzxgyyd', '#busibox_xzxg');
				
				//刷新表格
				if(self.searchTable) self.searchTable.refreshPage();
			});
		},
		doDel : function(obj){
			var self = this;
			comm.confirm({
				content: "确认要删除吗？",
				imgFlag: 1,
				callOk: function () {
					
					//刷新表格
					if(self.searchTable)self.searchTable.refreshPage();
				}
			});
		}
	};
});