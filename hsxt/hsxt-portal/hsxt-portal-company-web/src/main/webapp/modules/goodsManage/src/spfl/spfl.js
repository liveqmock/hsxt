define(['text!goodsManageTpl/spfl/spfl.html',
		'text!goodsManageTpl/spfl/spxx.html',
		'text!goodsManageTpl/spfl/xz_xg.html',
		'text!goodsManageTpl/spfl/glsp.html',
		'goodsManageDat/goodsManage',
		'jqueryztree'
		], function(tpl, spxxTpl, xzxgTpl, glspTpl,goodsManage){
	return {
		searchTable : null,
		showPage : function(){
			$('#busibox').html(tpl);
			
			var self = this,
			searchTable = null,
			glspTable = null;
			
			var setting = {
				view: {
					addDiyDom: function (treeId, treeNode) {
						var sObj = $("#" + treeNode.tId + "_span");
						if ($("#addBtn_" + treeNode.tId).length>0) return;
						var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='新增' onfocus='this.blur();'></span>";
						var editStr = "<span class='button edit' id='editBtn_" + treeNode.tId + "' title='修改' onfocus='this.blur();'></span>";
						var delStr = "<span class='button remove' id='delBtn_" + treeNode.tId + "' title='删除' onfocus='this.blur();'></span>";
						sObj.after('<span class="diy_buttons none" style="margin-left:10px;">' + (treeNode.level == 3 ? '' : addStr) + (treeNode.level == 0 ? '' : (editStr + delStr)) + '</span>');
						
						var btn = $("#addBtn_" + treeNode.tId);
						if (btn) btn.bind("click", function(){
							//添加
							self.doAdd({pId:treeNode.id, pName: treeNode.name}, function(data){
								var zTree = $.fn.zTree.getZTreeObj("tree_spfl");
								zTree.addNodes(treeNode, data);
							});
							return false;
						});
						
						var btn = $("#editBtn_" + treeNode.tId);
						if (btn) btn.bind("click", function(){
							//编辑
							//获取父结点
							var parentNode = treeNode.getParentNode();
							self.doEdit({id:treeNode.id, name:treeNode.name, pId:treeNode.pId, pName:parentNode.name});
							return false;
						});
						
						var btn = $("#delBtn_" + treeNode.tId);
						if (btn) btn.bind("click", function(){
							//删除
							alert('删除');
						});
					}
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: function (event, treeId, treeNode, clickFlag){
						$("#goodsTypeId").val(treeNode.id);
						$("#goodsTypeName").val(treeNode.name);
						
						//单击查询
						$('#btn_cx').click();
						
						return false;
					}
				}
			};
	
			var zNodes =[
				{ id:0, pId:null, name:"商品分类", open:true},
				{ id:1, pId:0, name:"一级目录1", open:true},
				{ id:11, pId:1, name:"二级目录1", open:true},
				{ id:111, pId:11, name:"三级目录1"},
				{ id:112, pId:11, name:"三级目录2"},
				{ id:113, pId:11, name:"三级目录3"},
				{ id:12, pId:1, name:"二级目录2", open:true},
				{ id:121, pId:12, name:"三级目录1"},
				{ id:122, pId:12, name:"三级目录2"},
				{ id:2, pId:0, name:"一级目录2", open:true},
				{ id:21, pId:2, name:"二级目录1", open:true},
				{ id:22, pId:2, name:"二级目录2", open:true},
				{ id:23, pId:2, name:"二级目录3", open:true},
				{ id:3, pId:0, name:"一级目录3", open:true},
				{ id:31, pId:3, name:"二级目录1", open:true},
				{ id:4, pId:0, name:"一级目录4", open:true},
				{ id:41, pId:4, name:"二级目录1", open:true},
				{ id:5, pId:0, name:"一级目录5", open:true},
				{ id:51, pId:5, name:"二级目录1", open:true},
				{ id:6, pId:0, name:"一级目录6", open:true},
				{ id:61, pId:6, name:"二级目录1", open:true},
				{ id:7, pId:0, name:"一级目录7", open:true},
				{ id:71, pId:7, name:"二级目录1", open:true},
				{ id:8, pId:0, name:"一级目录8", open:true},
				{ id:81, pId:8, name:"二级目录1", open:true}
			];
			$.fn.zTree.init($("#tree_spfl"), setting, zNodes);
			
//			goodsManage.seachSpflTree({custId:'11',token:'222'},function(res){
//				if(res.resultCode == 22000){
//					$.fn.zTree.init($("#tree_spfl"), setting, res.data);
//				}else{
//					comm.error_alert(comm.lang("goodsManage").requestError);
//				}
//			});
			
			$('#tree_spfl a').live('mouseover', function(){
				$(this).find('.diy_buttons').removeClass('none');
			}).live('mouseout', function(){
				$(this).find('.diy_buttons').addClass('none');
			});
			
			
			//查询单击事件
			$('#btn_cx').click(function(){
				if (!self.validateData()) {
					return;
				}
				var jsonData = {
						custId:'11',
						token:'222',
						goodsName : $("#goodsName").val(),
						goodsType : $("#goodsTypeId").val()
				};
//				goodsManage.seachAssociateGoods(jsonData,function(res){
					self.searchTable = searchTable = $.fn.bsgrid.init('spfl_table', {
						//url: '',
						localData: [{id:'xx01',pp:'H&M',lm:'服装 > 西装',spxx:{zp:'resources/img/hslt_cpzs1.gif',spbh:'13254585685',mc:'英伦西装',hh:'12584985252'}},{id:'xx02',pp:'H&M',lm:'服装 > 女装',spxx:{zp:'resources/img/product02.jpg',spbh:'13254585685',mc:'英伦女装',hh:'12584985252'}},{id:'xx03',pp:'H&M',lm:'服装 > 女装',spxx:{zp:'resources/img/product03.jpg',spbh:'13254585685',mc:'英伦女装',hh:'12584985252'}},{id:'xx04',pp:'H&M',lm:'服装 > 女装',spxx:{zp:'resources/img/product02.jpg',spbh:'13254585685',mc:'英伦女装',hh:'12584985252'}},{id:'xx05',pp:'H&M',lm:'服装 > 女装',spxx:{zp:'resources/img/product03.jpg',spbh:'13254585685',mc:'英伦女装',hh:'12584985252'}},{id:'xx06',pp:'H&M',lm:'服装 > 女装',spxx:{zp:'resources/img/product02.jpg',spbh:'13254585685',mc:'英伦女装',hh:'12584985252'}},{id:'xx07',pp:'H&M',lm:'服装 > 女装',spxx:{zp:'resources/img/product03.jpg',spbh:'13254585685',mc:'英伦女装',hh:'12584985252'}},{id:'xx08',pp:'H&M',lm:'服装 > 女装',spxx:{zp:'resources/img/product02.jpg',spbh:'13254585685',mc:'英伦女装',hh:'12584985252'}},{id:'xx09',pp:'H&M',lm:'服装 > 女装',spxx:{zp:'resources/img/product03.jpg',spbh:'13254585685',mc:'英伦女装',hh:'12584985252'}},{id:'xx10',pp:'H&M',lm:'服装 > 女装',spxx:{zp:'resources/img/product02.jpg',spbh:'13254585685',mc:'英伦女装',hh:'12584985252'}}],
						otherParames: {
							
						},
						//不显示空白行
						displayBlankRows: false,
						//不显示无页可翻的提示
						pageIncorrectTurnAlert: false,
						//隔行变色
						stripeRows: true,
						//不显示选中行背景色
						rowSelectedColor: false,
						lineWrap: 1,
						pageSize: 10,
						operate: {
							add: function(record, rowIndex, colIndex, options){
								return $('<a title="删除关联">删除关联</a>').click(function(e){
									self.doDel(searchTable.getRecord(rowIndex));
								});
							}
						},
						renderImg: function(record, rowIndex, colIndex, options){
							return _.template(spxxTpl, record.spxx)
						}
					});
//				});
			});
			
			//添加关联商品
			$('#spfl_btn_add').click(function(){
				$('#spfl_spfl_content1').addClass('none');
				$('#spfl_spfl_content2').removeClass('none').html(glspTpl);
				
				//表格中状态下拉列表
				$("#glsp_zt").selectList({
					height: 24,
					options:[
						{name:'状态',value:''},
						{name:'待发布',value:'1'},
						{name:'审核中',value:'2'},
						{name:'已上架',value:'3'},
						{name:'审核失败',value:'4'},
						{name:'已下架',value:'5'}
					]	
				}).change(function(e){
					var v = $(this).attr('optionValue');
					
				});
				//查询单击事件
				$('#glsp_btn_cx').click(function(){
					glspTable = $.fn.bsgrid.init('glsp_table', {
						//url: '',
						localData: [{id:'xx01',pp:'H&M',jg:'168.00',jf:'8.00',sjsj:'2015-08-19 17:22:30',zt:'已上架',spxx:{zp:'resources/img/hslt_cpzs1.gif',spbh:'13254585685',mc:'英伦西装',hh:'12584985252'}},{id:'xx02',pp:'H&M',jg:'168.00',jf:'8.00',sjsj:'2015-08-19 17:22:30',zt:'已上架',spxx:{zp:'resources/img/product02.jpg',spbh:'13254585685',mc:'英伦女装',hh:'12584985252'}},{id:'xx03',pp:'H&M',jg:'168.00',jf:'8.00',sjsj:'2015-08-19 17:22:30',zt:'已上架',spxx:{zp:'resources/img/product03.jpg',spbh:'13254585685',mc:'英伦女装',hh:'12584985252'}},{id:'xx04',pp:'H&M',jg:'168.00',jf:'8.00',sjsj:'2015-08-19 17:22:30',zt:'已上架',spxx:{zp:'resources/img/product02.jpg',spbh:'13254585685',mc:'英伦女装',hh:'12584985252'}},{id:'xx05',pp:'H&M',jg:'168.00',jf:'8.00',sjsj:'2015-08-19 17:22:30',zt:'已上架',spxx:{zp:'resources/img/product03.jpg',spbh:'13254585685',mc:'英伦女装',hh:'12584985252'}},{id:'xx06',pp:'H&M',jg:'168.00',jf:'8.00',sjsj:'2015-08-19 17:22:30',zt:'已上架',spxx:{zp:'resources/img/product02.jpg',spbh:'13254585685',mc:'英伦女装',hh:'12584985252'}},{id:'xx07',pp:'H&M',jg:'168.00',jf:'8.00',sjsj:'2015-08-19 17:22:30',zt:'已上架',spxx:{zp:'resources/img/product03.jpg',spbh:'13254585685',mc:'英伦女装',hh:'12584985252'}},{id:'xx08',pp:'H&M',jg:'168.00',jf:'8.00',sjsj:'2015-08-19 17:22:30',zt:'已上架',spxx:{zp:'resources/img/product02.jpg',spbh:'13254585685',mc:'英伦女装',hh:'12584985252'}},{id:'xx09',pp:'H&M',jg:'168.00',jf:'8.00',sjsj:'2015-08-19 17:22:30',zt:'已上架',spxx:{zp:'resources/img/product03.jpg',spbh:'13254585685',mc:'英伦女装',hh:'12584985252'}},{id:'xx10',pp:'H&M',jg:'168.00',jf:'8.00',sjsj:'2015-08-19 17:22:30',zt:'已上架',spxx:{zp:'resources/img/product02.jpg',spbh:'13254585685',mc:'英伦女装',hh:'12584985252'}}],
						otherParames: {
							
						},
						//不显示空白行
						displayBlankRows: false,
						//不显示无页可翻的提示
						pageIncorrectTurnAlert: false,
						//隔行变色
						stripeRows: true,
						//不显示选中行背景色
						rowSelectedColor: false,
						lineWrap: 1,
						pageAll: 1,
						displayPagingToolbarOnlyMultiPages: 1,
						renderImg: function(record, rowIndex, colIndex, options){
							return _.template(spxxTpl, record.spxx)
						}
					});
				});
				//取消按钮
				$('#glsp_btn_qx').click(function(){
					$('#spfl_spfl_content1').removeClass('none');
					$('#spfl_spfl_content2').addClass('none').html('');
					glspTable = null;
				});
				//建立关联按钮
				$('#glsp_btn_jlgl').click(function(){
					var v = glspTable ? glspTable.getCheckedRowsRecords() : [],
					len = v.length;
					if(len == 0){
						comm.alert({
							content: '没有选中项',
							imgClass: 'tips_warn'
						});
						return;
					}
					
					
					
					//调用取消按钮，返回主列表页面
					$('#glsp_btn_qx').trigger('click');
				});
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#spfl_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		//添加
		doAdd : function(obj, callback){
			var self = this;
			
			var response = {
				//商品类目
				splm: [{id:'1',name:'男装',pid:'0',letter:'N'},{id:'2',name:'女装',pid:'0',letter:''},{id:'3',name:'数码、电脑',pid:'0',letter:'S'},{id:'4',name:'办公、文教',pid:'0',letter:'B'},{id:'5',name:'汽车用品',pid:'0',letter:'Q'},{id:'6',name:'电工电气',pid:'0',letter:'D'},{id:'7',name:'纺织、皮革',pid:'0',letter:'F'},{id:'100',name:'男式T恤',pid:'1',letter:'N'},{id:'101',name:'男式Polo衫',pid:'1',letter:''},{id:'102',name:'男式衬衫',pid:'1',letter:''},{id:'103',name:'男式毛衫',pid:'1',letter:''},{id:'104',name:'男式马甲',pid:'1',letter:''},{id:'',name:'男式休闲套装',pid:'1',letter:''},{id:'',name:'男式背心',pid:'1',letter:''},{id:'',name:'男式夹克',pid:'1',letter:''},{id:'',name:'男式皮衣',pid:'1',letter:''},{id:'',name:'男式西装',pid:'1',letter:''},{id:'',name:'男式卫衣',pid:'1',letter:''},{id:'',name:'男式西裤',pid:'1',letter:''},{id:'',name:'男式西服套装',pid:'1',letter:''},{id:'',name:'男式风衣',pid:'1',letter:''},{id:'',name:'男式大衣',pid:'1',letter:''},{id:'',name:'圆领T恤',pid:'100',letter:'Y'},{id:'',name:'DIY图片T恤',pid:'100',letter:'D'},{id:'',name:'男式休闲马甲',pid:'104',letter:'N'},{id:'',name:'男式西装马甲',pid:'104',letter:''}]
			};
			var data = $.extend(true, {}, response, obj);
			$("#spfl_spfl-dialog > p").html(_.template(xzxgTpl, data));
			$("#spfl_spfl-dialog").dialog({
				title : '添加商品分类',
				width : 'auto',
				height : 310,
				position: [300,195],
				resizable : false,
				open: function (event, ui) {
					var oP = $(this).parent();
					$(".ui-dialog-titlebar-close", oP).show();
					oP.css('overflow', 'inherit');
					var oL = $('#spfl_splm_list', oP);
					$('#spfl_spfl-dialog', oP).after(oL.clone(true).css({left:139,top:121}));
					oL.remove();
				},
				buttons : {
					"保存": function(){
						callback($.extend({id:'xxxxx', name:'新增示例'}, obj));
						$(this).dialog("destroy");
					}
				}
			});
			
			self.bindXZXGEvent(data);
		},
		//编辑
		doEdit : function(obj, callback){
			var self = this;
			
			var response = {
				//商品类目
				splm: [{id:'1',name:'男装',pid:'0',letter:'N'},{id:'2',name:'女装',pid:'0',letter:''},{id:'3',name:'数码、电脑',pid:'0',letter:'S'},{id:'4',name:'办公、文教',pid:'0',letter:'B'},{id:'5',name:'汽车用品',pid:'0',letter:'Q'},{id:'6',name:'电工电气',pid:'0',letter:'D'},{id:'7',name:'纺织、皮革',pid:'0',letter:'F'},{id:'100',name:'男式T恤',pid:'1',letter:'N'},{id:'101',name:'男式Polo衫',pid:'1',letter:''},{id:'102',name:'男式衬衫',pid:'1',letter:''},{id:'103',name:'男式毛衫',pid:'1',letter:''},{id:'104',name:'男式马甲',pid:'1',letter:''},{id:'',name:'男式休闲套装',pid:'1',letter:''},{id:'',name:'男式背心',pid:'1',letter:''},{id:'',name:'男式夹克',pid:'1',letter:''},{id:'',name:'男式皮衣',pid:'1',letter:''},{id:'',name:'男式西装',pid:'1',letter:''},{id:'',name:'男式卫衣',pid:'1',letter:''},{id:'',name:'男式西裤',pid:'1',letter:''},{id:'',name:'男式西服套装',pid:'1',letter:''},{id:'',name:'男式风衣',pid:'1',letter:''},{id:'',name:'男式大衣',pid:'1',letter:''},{id:'',name:'圆领T恤',pid:'100',letter:'Y'},{id:'',name:'DIY图片T恤',pid:'100',letter:'D'},{id:'',name:'男式休闲马甲',pid:'104',letter:'N'},{id:'',name:'男式西装马甲',pid:'104',letter:''}]
			};
			var data = $.extend(true, {}, response, obj);
			$("#spfl_spfl-dialog > p").html(_.template(xzxgTpl, data));
			$("#spfl_spfl-dialog").dialog({
				title : '修改商品分类',
				width : 'auto',
				height : 310,
				position: [300,195],
				resizable : false,
				open: function (event, ui) {
					var oP = $(this).parent();
					$(".ui-dialog-titlebar-close", oP).show();
					oP.css('overflow', 'inherit');
					var oL = $('#spfl_splm_list', oP);
					$('#spfl_spfl-dialog', oP).after(oL.clone(true).css({left:139,top:121}));
					oL.remove();
				},
				buttons : {
					"保存": function(){
						$(this).dialog("destroy");
					}
				}
			});
			
			self.bindXZXGEvent(data);
		},
		bindXZXGEvent : function(data){
			//商品图片显示删除图片按钮
			$('.pImgList > li').live('mouseover', function(){
				var o = $(this);
				if(o.attr('data-flag') == '1') {
					o.children(".delImg").show();
				}
			}).live('mouseout', function(){
				$(this).children(".delImg").hide();	
			});
			//商品图片删除图片
			$(".pImgList li .delImg").live('click', function(){
				var o = $(this),
				oP = o.parent("li");
				oP.attr('data-flag', '0').find('img').attr('src', oP.index() == 0 ? 'resources/img/store_main_img_1.gif' : 'resources/img/store_noimg_1.gif');
				o.hide();
			});
			
			//商品类目
			$('#spfl_splm').click(function(){
				//显示商品类目层
				$('#spfl_splm_list').toggle();
			});
			//商品类目确定按钮
			$('#spfl_splm_btn_qd').click(function(){
				//隐藏商品类目层
				$('#spfl_splm_list').hide();
				
				//$('#spfl_splm').val();
			});
			//商品类目取消按钮
			$('#spfl_splm_btn_qx').click(function(){
				//隐藏商品类目层
				$('#spfl_splm_list').hide();
			});
			$('#spfl_splm_list .list_sc:eq(0) a').click(function(){
				var sTpl = '<%$.each(obj.splm, function(k, v) { if(v.pid === "' + $(this).data('id') + '") {%><li><a class="clearfix" data-id="<%=v.id%>"><%if(v.letter) {%><span class="list_sc_letter"><%=v.letter%></span><%}%><span class="gct"><%=v.name%></span><i class="list_sc_arrow"></i></a></li><%}})%>';
				$('#spfl_splm_list .list_sc:eq(1)').html(_.template(sTpl, data));
				$('#spfl_splm_list .list_sc:eq(2)').html('');
				
				//绑定事件
				$('#spfl_splm_list .list_sc:eq(1) a').unbind().bind('click', function(){
					var sTpl = '<%$.each(obj.splm, function(k, v) { if(v.pid === "' + $(this).data('id') + '") {%><li><a class="clearfix" data-id="<%=v.id%>"><%if(v.letter) {%><span class="list_sc_letter"><%=v.letter%></span><%}%><span class="gct"><%=v.name%></span></a></li><%}})%>';
					$('#spfl_splm_list .list_sc:eq(2)').html(_.template(sTpl, data));
				});
			});
		},
		//删除关联
		doDel : function(obj){
			var id = obj.id;
			alert(id + '删除关联');
			
			if(self.searchTable)self.searchTable.refreshPage();
		}
	};
});