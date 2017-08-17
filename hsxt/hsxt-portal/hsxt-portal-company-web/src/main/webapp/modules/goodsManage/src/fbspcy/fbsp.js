define(['text!goodsManageTpl/fbspcy/fbsp.html'], function(tpl){
	return {
		showPage : function(){
			var self = this;
			
			//从后台返回数据
			var response = {
				//口味
				kwlb: [{id:"kw01",name:"清淡"},{id:"kw02",name:"麻辣"},{id:"kw03",name:"微辣"},{id:"kw04",name:"香辣"},{id:"kw05",name:"清淡"},{id:"kw06",name:"麻辣"},{id:"kw07",name:"香辣"}],
				//商城自定义分类
				sczdyfl: [{id:'',name:'服装',data:[{id:'',name:'男装',data:[]},{id:'',name:'女装',data:[]},{id:'',name:'童装',data:[]}]},{id:'',name:'美食',data:[{id:'',name:'湘菜',data:[]},{id:'',name:'粤菜',data:[]},{id:'',name:'川菜',data:[]}]},{id:'',name:'休闲娱乐',data:[{id:'',name:'电影',data:[]},{id:'',name:'KTV',data:[]},{id:'',name:'旅游',data:[{id:'',name:'国内游'},{id:'',name:'国外游'}]}]}],
				//商品类目
				splm: [{id:'1',name:'男装',pid:'0',letter:'N'},{id:'2',name:'女装',pid:'0',letter:''},{id:'3',name:'数码、电脑',pid:'0',letter:'S'},{id:'4',name:'办公、文教',pid:'0',letter:'B'},{id:'5',name:'汽车用品',pid:'0',letter:'Q'},{id:'6',name:'电工电气',pid:'0',letter:'D'},{id:'7',name:'纺织、皮革',pid:'0',letter:'F'},{id:'100',name:'男式T恤',pid:'1',letter:'N'},{id:'101',name:'男式Polo衫',pid:'1',letter:''},{id:'102',name:'男式衬衫',pid:'1',letter:''},{id:'103',name:'男式毛衫',pid:'1',letter:''},{id:'104',name:'男式马甲',pid:'1',letter:''},{id:'',name:'男式休闲套装',pid:'1',letter:''},{id:'',name:'男式背心',pid:'1',letter:''},{id:'',name:'男式夹克',pid:'1',letter:''},{id:'',name:'男式皮衣',pid:'1',letter:''},{id:'',name:'男式西装',pid:'1',letter:''},{id:'',name:'男式卫衣',pid:'1',letter:''},{id:'',name:'男式西裤',pid:'1',letter:''},{id:'',name:'男式西服套装',pid:'1',letter:''},{id:'',name:'男式风衣',pid:'1',letter:''},{id:'',name:'男式大衣',pid:'1',letter:''},{id:'',name:'圆领T恤',pid:'100',letter:'Y'},{id:'',name:'DIY图片T恤',pid:'100',letter:'D'},{id:'',name:'男式休闲马甲',pid:'104',letter:'N'},{id:'',name:'男式西装马甲',pid:'104',letter:''}]
			};
			//扩展json数据
			var data = $.extend(true, {}, response);
			$('#busibox').html(_.template(tpl, data));
			
			//商品名称提示输入30字
			$('#fbsp_cpmc').charcount({
				maxLength: 30,
				preventOverage: true,
				position : 'after'
			});
			//商品图片显示删除图片按钮
			$('#fbsp_cptp > li').live('mouseover', function(){
				var o = $(this);
				if(o.attr('data-flag') == '1') {
					o.children(".delImg").show();
				}
			}).live('mouseout', function(){
				$(this).children(".delImg").hide();	
			});
			//商品图片删除图片
			$("#fbsp_cptp .delImg").live('click', function(){
				var o = $(this),
				oP = o.parent("li");
				oP.attr('data-flag', '0').find('img').attr('src', oP.index() == 0 ? 'resources/img/store_main_img_1.gif' : 'resources/img/store_noimg_1.gif');
				o.hide();
			});
			//显示隐藏复选下拉列表
			$('.feellist_div').hover(function () {
				$(this).children(".feellist").show();
			}, function () {
				$(this).children(".feellist").hide();
			}).find('.feellist_chk').click(function(){
				//复选框选中事件
				var a = {a:[],b:[]},
				o = $(this),
				oP = o.parents('.feellist'),
				oChks = oP.find('.feellist_chk:checked'),
				len = oChks.length;
				//口味最多可选择5个
				if(o.hasClass('kw_ckh') && len > 5) {
					o.prop('checked', 0);
					return;
				}
				$.each(oChks, function(k, v) {
					var o = $(v);
					a.a.push(o.attr('data-text'));
					a.b.push(o.attr('data-value'));
				});
				oP.next().val(a.a.join(',')).attr('data-value', a.b.join(','));
			});
			
			//商品类目
			$('#fbsp_splm').click(function(){
				//显示商品类目层
				$('#fbsp_splm_list').toggle();
			});
			//商品类目确定按钮
			$('#fbsp_splm_btn_qd').click(function(){
				//隐藏商品类目层
				$('#fbsp_splm_list').hide();
				
				//$('#fbsp_splm').val();
			});
			//商品类目取消按钮
			$('#fbsp_splm_btn_qx').click(function(){
				//隐藏商品类目层
				$('#fbsp_splm_list').hide();
			});
			$('#fbsp_splm_list .list_sc:eq(0) a').click(function(){
				var sTpl = '<%$.each(obj.splm, function(k, v) { if(v.pid === "' + $(this).data('id') + '") {%><li><a class="clearfix" data-id="<%=v.id%>"><%if(v.letter) {%><span class="list_sc_letter"><%=v.letter%></span><%}%><span class="gct"><%=v.name%></span><i class="list_sc_arrow"></i></a></li><%}})%>';
				$('#fbsp_splm_list .list_sc:eq(1)').html(_.template(sTpl, data));
				$('#fbsp_splm_list .list_sc:eq(2)').html('');
				
				//绑定事件
				$('#fbsp_splm_list .list_sc:eq(1) a').unbind().bind('click', function(){
					var sTpl = '<%$.each(obj.splm, function(k, v) { if(v.pid === "' + $(this).data('id') + '") {%><li><a class="clearfix" data-id="<%=v.id%>"><%if(v.letter) {%><span class="list_sc_letter"><%=v.letter%></span><%}%><span class="gct"><%=v.name%></span></a></li><%}})%>';
					$('#fbsp_splm_list .list_sc:eq(2)').html(_.template(sTpl, data));
				});
			});
			
			//份量隐藏显示删除及确定按钮
			$('.editval li').live('mouseover', function(){
				$(this).find("i").css("display", "block");
			}).live('mouseout', function(){
				$(this).find("i").hide();
			});
			//份量删除按钮
			$('.editval li a.editval-del i').live('click', function(){
				var o = $(this),
				oP = o.parents("li");
				if(o.hasClass('edipt')){
					oP.hide();
				} else {
					oP.remove();
				}
			});
			//份量确定按钮
			$('.editval li a.editval-yes i').live('click', function(){
				var o = $(this),
				oP = o.parents("li"),
				sTpl = '<li class="clearfix"><span class="fl"><label><input type="checkbox"<%= obj.checked ? \'checked="checked"\' : ""%> class="m03vm"/><%=obj.name%></label></span><a class="fl editval-del" title="删除"><i></i></a></li>',
				oTxt = oP.find('.edipt-input'),
				v = oTxt.val(),
				oChk = oTxt.prev();
				
				if(!v){
					return;	
				}
				
				oP.hide().before(_.template(sTpl, {
					name: v,
					checked: oChk.is(':checked')
				}));
				//初始化输入值及复选框
				oTxt.val('');
				oChk.attr('checked', 'checked');
			});
			//份量增加按钮
			$('.editval-add').live('click', function(){
				$(this).parent().prev().find('.editval li.edipt').show();
			});
			
			//弹出设置
			$('#fbsp_gg_fl_table .popup_set i').click(function(){
				$(this).next().show();
			}).next().mouseover(function(){
				$(this).show();
			}).mouseout(function(){
				$(this).hide();
			}).find('li').click(function(){
				var o = $(this);
				o.parent().hide();
				//获取input输入值
				var v = o.parents('th').find('.fl_th_input').val();
				alert(v);
			});
			
			//取消按钮
			$('#fbsp_btn_qx').triggerWith('#fbspcy_fbsp');
			//下一步按钮
			$('#fbsp_btn_xyb').click(function(){
				if (!self.validateData()) {
					return;
				}
				
				//显示隐藏页面
				$('#fbsp_content1').addClass('none');
				$('#fbsp_content2').removeClass('none');
				$('#busibox').scrollTop(0);
			});
			//下一页中取消按钮
			$('#fbsp_next_btn_qx').triggerWith('#fbspcy_fbsp');
			//下一页中上一步按钮
			$('#fbsp_btn_syb').click(function(){
				//显示隐藏页面
				$('#fbsp_content1').removeClass('none');
				$('#fbsp_content2').addClass('none');
				$('#busibox').scrollTop(0);
			});
			//下一页中保存按钮
			$('#fbsp_btn_bc').click(function(){
				if (!self.validateNextData()) {
					return;
				}
				
				comm.confirm({
					content: "是否发布？",
					imgFlag: 1,
					callOk: function () {
						
						
						$('#fbspcy_fbsp').trigger('click');
					}
				});
			});
			
			$('#busibox').scrollTop(0);
		},
		validateData : function(){
			return comm.valid({
				formID : '#fbsp_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		validateNextData : function(){
			return comm.valid({
				formID : '#fbsp_next_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		}
	};
});