define(['text!shouhouTpl/ddshgl/ddshgl.html',
		'text!shouhouTpl/ddshgl/ddshgl_sqsh.html',
		'text!shouhouTpl/ddshgl/ddshgl_sjclz.html',
		'text!shouhouTpl/ddshgl/ddshgl_xfzss.html',
		'text!shouhouTpl/ddshgl/ddshgl_ssclz.html',
		'text!shouhouTpl/ddshgl/ddshgl_clwc.html',
		'text!shouhouTpl/ddshgl/ddshgl_qb.html',
		'text!shouhouTpl/ddshgl/ddshgl_ck.html'
		], function(ddshglTpl, ddshgl_sqshTpl, ddshgl_sjclzTpl, ddshgl_xfzssTpl, ddshgl_ssclzTpl, ddshgl_clwcTpl, ddshgl_qbTpl, ddshgl_ckTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(ddshglTpl)).append(ddshgl_ckTpl);	
			$('#contentArea').html(ddshgl_sqshTpl).append(ddshgl_sjclzTpl).append(ddshgl_xfzssTpl).append(ddshgl_ssclzTpl).append(ddshgl_clwcTpl).append(ddshgl_qbTpl);
			var self = this;
			
			/*日期控件*/
			$("#timeRange_start").datepicker({
				dateFormat : 'yy-mm-dd',
				onSelect : function(dateTxt, inst){
					var d = dateTxt.replace('-', '/');
					$("#timeRange_end").datepicker('option', 'minDate', new Date(d));	
				}
			});
			$("#timeRange_end").datepicker({
				dateFormat : 'yy-mm-dd',
				onSelect : function(dateTxt, inst){
					var d = dateTxt.replace('-', '/');
					$("#timeRange_start").datepicker('option', 'maxDate', new Date(d));	
				}
			});
			/*end*/	
			
			/*下拉选择框*/
			$('#province_slt').selectList({
				width:76,
				options:[
					{name:'广东省',value:'100'},
					{name:'江西省',value:'101'},
					{name:'湖南省',value:'102'}
				]
			}).change(function(e){
				//console.log($(this).val() );
				var v = $(this).attr('optionvalue');
				var dataArry = [];
				switch (v)
				{
					case '100' : 
						dataArry = [
							{name:'广州市',value:'100'},
							{name:'深圳市',value:'101'},
							{name:'东莞市',value:'102'}
						]
						break;	
					case '101' :
						dataArry = [
							{name:'南昌市',value:'100'},
							{name:'九江市',value:'101'},
							{name:'赣州市',value:'102'}
						]
						break;
					case '102' : 
						dataArry = [
							{name:'长沙市',value:'100'},
							{name:'衡阳市',value:'101'},
							{name:'怀化市',value:'102'}
						]
						break;
				}	
				
				$('#city_slt').val('').attr('optionvalue', '').selectList({
					width:76,
					options : dataArry
				});
			});
			
			$('#city_slt').selectList({
				width:76
			});
			
			/*end*/
			
			// 页面切换
			$('.tab01,.tab02,.tab03,.tab04,.tab05,.tab06').click(function(){
				var tabs=$('#ddshgl_sqshTpl, #ddshgl_sjclzTpl, #ddshgl_xfzssTpl, #ddshgl_ssclzTpl, #ddshgl_clwcTpl, #ddshgl_qbTpl');
				tabs.hide();
				var m = $(this).attr('class');				
				var n = m.charAt(m.length - 1);
				tabs.eq(n-1).show();
				$('#tabMenu').find('a').removeClass('active');
				$(this).addClass('active');	
			});
			
			/*表格数据模拟*/
			var json_sqsh = [{
				id:'xx01',
				ddbh:'9884544445664412236',
				yhm:'小鱼儿',
				yyd:'福田实体店',
				scmc:'商城名称1',
				sqlx:'退款退货',
				sqly:'xxx',
				tk:'200.00',
				jf:'20',
				hf:'同意退款',
				sqsj:'2015-10-10 12:30:10',
				zt:'待收货',
				kdgs:'申通快递',
				kddh:'13232564665',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'1111111111111111111111111111111111111',
					ysfl:'黑色',
					cc:'XXL 170/175'
					},
					{
					id:'sp002',
					spzp:'resources/img/product01.jpg',
					spmc:'222222222222222222222222222222222222222',
					ysfl:'黑色',
					cc:'XXL 170/175'
					}]
			}];	
				
			var gridObj_sqsh = $.fn.bsgrid.init('searchTable_sqsh', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				/*pageSizeSelect: true ,*/
				pageIncorrectTurnAlert: false,
				pageSize: 10 ,
				//stripeRows: true,  行色彩分隔 
				rowSelectedColor: false,
				displayBlankRows: false ,   //显示空白行
				localData:json_sqsh,
				additionalRenderPerColumn: function(record, rowIndex, colIndex, tdObj, trObj, options){
					var data = gridObj_sqsh.getRecord(rowIndex),
						sTpl = '<span class="mr15"><%=obj.ddsj%></span><span class="mr15">订单编号：<%=obj.ddbh%></span><span class="mr15"><%=obj.yhm%>(用户名)</span><span class="mr15"><%=obj.yyd%></span><span><%=obj.scmc%></span>';
					trObj.find('td').attr('colspan', '8').css({'background-color':'#6baf58', 'color':'#fff'}).html(_.template(sTpl, data));
					var splb = data.splb,
						len = splb.length,
						sTpl_1 = '<td style="text-align:left;"><div class="img-group-detail2"><img src="<%=obj.spzp%>" class="fl mr10" width="80" height="80"><div class="img-group-content"><p><a href="#" class="blue"><%=obj.spmc%></a></p><p>颜色分类：<%=obj.ysfl%></p><p>尺寸：<%=obj.cc%></p></div></div></td>',
						sTpl_2 = '<%var _rowspan = obj.splb.length > 1 ? \' rowspan="\' + obj.splb.length + \'"\' : \'\';%><td <%=_rowspan%>><%=obj.sqlx%></td><td <%=_rowspan%>><%=obj.sqly%></td><td <%=_rowspan%>><p class="red">￥<%=obj.tk%></p><p class="blue">PV <%=obj.jf%></p></td><td <%=_rowspan%>><%=obj.hf%></td><td <%=_rowspan%>><%=obj.sqsj%></td><td <%=_rowspan%>><p><%=obj.zt%></p><p class="mt10"><%=obj.kdgs%></p><p><%=obj.kddh%></p></td><td <%=_rowspan%>></td>';
						
						for(var i = len - 1; i >= 0; i--) {
							var $tr = $('<tr></tr>');
							$tr.append(_.template(sTpl_1, splb[i]));
							if(i === 0) {
								$tr.append(_.template(sTpl_2, data));
								$tr.find('td:last').append($('<a title="查看详情">查看详情</a>').click(function(e){
									//查看详情点击事件
									self.chaKan(gridObj_sqsh.getRecord(rowIndex));
								})).append($('<br /><a title="申请处理">申请处理</a>'))/*.click(function(e){
									self.shenQing(gridObj_sqsh.getRecord(rowIndex));
								})*/;
							}
							trObj.after($tr);
						}
				}
				
			});
			
			var json_sjclz = [{
				id:'xx01',
				ddbh:'9884544445664412236',
				yhm:'小鱼儿',
				yyd:'福田实体店',
				scmc:'商城名称1',
				sqlx:'退款退货',
				sqly:'xxx',
				tk:'200.00',
				jf:'20',
				hf:'同意退款',
				sqsj:'2015-10-10 12:30:10',
				zt:'待收货',
				kdgs:'申通快递',
				kddh:'13232564665',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'1111111111111111111111111111111111111',
					ysfl:'黑色',
					cc:'XXL 170/175'
					}]
			},
			{
				id:'xx01',
				ddbh:'9884544445664412236',
				yhm:'小鱼儿',
				yyd:'福田实体店',
				scmc:'商城名称1',
				sqlx:'退款退货',
				sqly:'xxx',
				tk:'200.00',
				jf:'20',
				hf:'同意退款',
				sqsj:'2015-10-10 12:30:10',
				zt:'待收货',
				kdgs:'申通快递',
				kddh:'13232564665',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'1111111111111111111111111111111111111',
					ysfl:'黑色',
					cc:'XXL 170/175'
					},
					{
					id:'sp002',
					spzp:'resources/img/product01.jpg',
					spmc:'222222222222222222222222222222222222222',
					ysfl:'黑色',
					cc:'XXL 170/175'
					}]
			}];	
				
			var gridObj_sjclz = $.fn.bsgrid.init('searchTable_sjclz', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				/*pageSizeSelect: true ,*/
				pageIncorrectTurnAlert: false,
				pageSize: 10 ,
				//stripeRows: true,  行色彩分隔 
				rowSelectedColor: false,
				displayBlankRows: false ,   //显示空白行
				localData:json_sjclz,
				additionalRenderPerColumn: function(record, rowIndex, colIndex, tdObj, trObj, options){
					var data = gridObj_sjclz.getRecord(rowIndex),
						sTpl = '<span class="mr15"><%=obj.ddsj%></span><span class="mr15">订单编号：<%=obj.ddbh%></span><span class="mr15"><%=obj.yhm%>(用户名)</span><span class="mr15"><%=obj.yyd%></span><span><%=obj.scmc%></span>';
					trObj.find('td').attr('colspan', '8').css({'background-color':'#6baf58', 'color':'#fff'}).html(_.template(sTpl, data));
					var splb = data.splb,
						len = splb.length,
						sTpl_1 = '<td style="text-align:left;"><div class="img-group-detail2"><img src="<%=obj.spzp%>" class="fl mr10" width="80" height="80"><div class="img-group-content"><p><a href="#" class="blue"><%=obj.spmc%></a></p><p>颜色分类：<%=obj.ysfl%></p><p>尺寸：<%=obj.cc%></p></div></div></td>',
						sTpl_2 = '<%var _rowspan = obj.splb.length > 1 ? \' rowspan="\' + obj.splb.length + \'"\' : \'\';%><td <%=_rowspan%>><%=obj.sqlx%></td><td <%=_rowspan%>><%=obj.sqly%></td><td <%=_rowspan%>><p class="red">￥<%=obj.tk%></p><p class="blue">PV <%=obj.jf%></p></td><td <%=_rowspan%>><%=obj.hf%></td><td <%=_rowspan%>><%=obj.sqsj%></td><td <%=_rowspan%>><p><%=obj.zt%></p><p class="mt10"><%=obj.kdgs%></p><p><%=obj.kddh%></p></td><td <%=_rowspan%>></td>';
						
						for(var i = len - 1; i >= 0; i--) {
							var $tr = $('<tr></tr>');
							$tr.append(_.template(sTpl_1, splb[i]));
							if(i === 0) {
								$tr.append(_.template(sTpl_2, data));
								$tr.find('td:last').append($('<a title="查看详情">查看详情</a>').click(function(e){
									//查看详情点击事件
									self.chaKan(gridObj_sjclz.getRecord(rowIndex));
								})).append($('<br /><a title="申请处理">申请处理</a>'))/*.click(function(e){
									self.shenQing(gridObj_sqsh.getRecord(rowIndex));
								})*/;
							}
							trObj.after($tr);
						}
				}
				
			});
			
			var json_xfzss = [{
				id:'xx01',
				ddbh:'9884544445664412236',
				yhm:'小鱼儿',
				yyd:'福田实体店',
				scmc:'商城名称1',
				sqlx:'退款退货',
				sqly:'xxx',
				tk:'200.00',
				jf:'20',
				hf:'同意退款',
				sqsj:'2015-10-10 12:30:10',
				zt:'待收货',
				kdgs:'申通快递',
				kddh:'13232564665',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'1111111111111111111111111111111111111',
					ysfl:'黑色',
					cc:'XXL 170/175'
					},
					{
					id:'sp002',
					spzp:'resources/img/product01.jpg',
					spmc:'222222222222222222222222222222222222222',
					ysfl:'黑色',
					cc:'XXL 170/175'
					}]
			},
			{
				id:'xx01',
				ddbh:'9884544445664412236',
				yhm:'小鱼儿',
				yyd:'福田实体店',
				scmc:'商城名称1',
				sqlx:'退款退货',
				sqly:'xxx',
				tk:'200.00',
				jf:'20',
				hf:'同意退款',
				sqsj:'2015-10-10 12:30:10',
				zt:'待收货',
				kdgs:'申通快递',
				kddh:'13232564665',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'1111111111111111111111111111111111111',
					ysfl:'黑色',
					cc:'XXL 170/175'
					}]
			}];	
				
			var gridObj_xfzss = $.fn.bsgrid.init('searchTable_xfzss', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				/*pageSizeSelect: true ,*/
				pageIncorrectTurnAlert: false,
				pageSize: 10 ,
				//stripeRows: true,  行色彩分隔 
				rowSelectedColor: false,
				displayBlankRows: false ,   //显示空白行
				localData:json_xfzss,
				additionalRenderPerColumn: function(record, rowIndex, colIndex, tdObj, trObj, options){
					var data = gridObj_xfzss.getRecord(rowIndex),
						sTpl = '<span class="mr15"><%=obj.ddsj%></span><span class="mr15">订单编号：<%=obj.ddbh%></span><span class="mr15"><%=obj.yhm%>(用户名)</span><span class="mr15"><%=obj.yyd%></span><span><%=obj.scmc%></span>';
					trObj.find('td').attr('colspan', '8').css({'background-color':'#6baf58', 'color':'#fff'}).html(_.template(sTpl, data));
					var splb = data.splb,
						len = splb.length,
						sTpl_1 = '<td style="text-align:left;"><div class="img-group-detail2"><img src="<%=obj.spzp%>" class="fl mr10" width="80" height="80"><div class="img-group-content"><p><a href="#" class="blue"><%=obj.spmc%></a></p><p>颜色分类：<%=obj.ysfl%></p><p>尺寸：<%=obj.cc%></p></div></div></td>',
						sTpl_2 = '<%var _rowspan = obj.splb.length > 1 ? \' rowspan="\' + obj.splb.length + \'"\' : \'\';%><td <%=_rowspan%>><%=obj.sqlx%></td><td <%=_rowspan%>><%=obj.sqly%></td><td <%=_rowspan%>><p class="red">￥<%=obj.tk%></p><p class="blue">PV <%=obj.jf%></p></td><td <%=_rowspan%>><%=obj.hf%></td><td <%=_rowspan%>><%=obj.sqsj%></td><td <%=_rowspan%>><p><%=obj.zt%></p><p class="mt10"><%=obj.kdgs%></p><p><%=obj.kddh%></p></td><td <%=_rowspan%>></td>';
						
						for(var i = len - 1; i >= 0; i--) {
							var $tr = $('<tr></tr>');
							$tr.append(_.template(sTpl_1, splb[i]));
							if(i === 0) {
								$tr.append(_.template(sTpl_2, data));
								$tr.find('td:last').append($('<a title="查看详情">查看详情</a>').click(function(e){
									//查看详情点击事件
									self.chaKan(gridObj_xfzss.getRecord(rowIndex));
								})).append($('<br /><a title="申请处理">申请处理</a>'))/*.click(function(e){
									self.shenQing(gridObj_sqsh.getRecord(rowIndex));
								})*/;
							}
							trObj.after($tr);
						}
				}
				
			});
			
			var json_ssclz = [{
				id:'xx01',
				ddbh:'9884544445664412236',
				yhm:'小鱼儿',
				yyd:'福田实体店',
				scmc:'商城名称1',
				sqlx:'退款退货',
				sqly:'xxx',
				tk:'200.00',
				jf:'20',
				hf:'同意退款',
				sqsj:'2015-10-10 12:30:10',
				zt:'待收货',
				kdgs:'申通快递',
				kddh:'13232564665',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'1111111111111111111111111111111111111',
					ysfl:'黑色',
					cc:'XXL 170/175'
					}]
			}];	
				
			var gridObj_ssclz = $.fn.bsgrid.init('searchTable_ssclz', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				/*pageSizeSelect: true ,*/
				pageIncorrectTurnAlert: false,
				pageSize: 10 ,
				//stripeRows: true,  行色彩分隔 
				rowSelectedColor: false,
				displayBlankRows: false ,   //显示空白行
				localData:json_ssclz,
				additionalRenderPerColumn: function(record, rowIndex, colIndex, tdObj, trObj, options){
					var data = gridObj_ssclz.getRecord(rowIndex),
						sTpl = '<span class="mr15"><%=obj.ddsj%></span><span class="mr15">订单编号：<%=obj.ddbh%></span><span class="mr15"><%=obj.yhm%>(用户名)</span><span class="mr15"><%=obj.yyd%></span><span><%=obj.scmc%></span>';
					trObj.find('td').attr('colspan', '8').css({'background-color':'#6baf58', 'color':'#fff'}).html(_.template(sTpl, data));
					var splb = data.splb,
						len = splb.length,
						sTpl_1 = '<td style="text-align:left;"><div class="img-group-detail2"><img src="<%=obj.spzp%>" class="fl mr10" width="80" height="80"><div class="img-group-content"><p><a href="#" class="blue"><%=obj.spmc%></a></p><p>颜色分类：<%=obj.ysfl%></p><p>尺寸：<%=obj.cc%></p></div></div></td>',
						sTpl_2 = '<%var _rowspan = obj.splb.length > 1 ? \' rowspan="\' + obj.splb.length + \'"\' : \'\';%><td <%=_rowspan%>><%=obj.sqlx%></td><td <%=_rowspan%>><%=obj.sqly%></td><td <%=_rowspan%>><p class="red">￥<%=obj.tk%></p><p class="blue">PV <%=obj.jf%></p></td><td <%=_rowspan%>><%=obj.hf%></td><td <%=_rowspan%>><%=obj.sqsj%></td><td <%=_rowspan%>><p><%=obj.zt%></p><p class="mt10"><%=obj.kdgs%></p><p><%=obj.kddh%></p></td><td <%=_rowspan%>></td>';
						
						for(var i = len - 1; i >= 0; i--) {
							var $tr = $('<tr></tr>');
							$tr.append(_.template(sTpl_1, splb[i]));
							if(i === 0) {
								$tr.append(_.template(sTpl_2, data));
								$tr.find('td:last').append($('<a title="查看详情">查看详情</a>').click(function(e){
									//查看详情点击事件
									self.chaKan(gridObj_ssclz.getRecord(rowIndex));
								})).append($('<br /><a title="申请处理">申请处理</a>'))/*.click(function(e){
									self.shenQing(gridObj_sqsh.getRecord(rowIndex));
								})*/;
							}
							trObj.after($tr);
						}
				}
				
			});
			
			var json_clwc = [{
				id:'xx01',
				ddbh:'9884544445664412236',
				yhm:'小鱼儿',
				yyd:'福田实体店',
				scmc:'商城名称1',
				sqlx:'退款退货',
				sqly:'xxx',
				tk:'200.00',
				jf:'20',
				hf:'同意退款',
				sqsj:'2015-10-10 12:30:10',
				zt:'待收货',
				kdgs:'申通快递',
				kddh:'13232564665',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'1111111111111111111111111111111111111',
					ysfl:'黑色',
					cc:'XXL 170/175'
					},
					{
					id:'sp002',
					spzp:'resources/img/product01.jpg',
					spmc:'222222222222222222222222222222222222222',
					ysfl:'黑色',
					cc:'XXL 170/175'
					}]
			},
			{
				id:'xx01',
				ddbh:'9884544445664412236',
				yhm:'小鱼儿',
				yyd:'福田实体店',
				scmc:'商城名称1',
				sqlx:'退款退货',
				sqly:'xxx',
				tk:'200.00',
				jf:'20',
				hf:'同意退款',
				sqsj:'2015-10-10 12:30:10',
				zt:'待收货',
				kdgs:'申通快递',
				kddh:'13232564665',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'1111111111111111111111111111111111111',
					ysfl:'黑色',
					cc:'XXL 170/175'
					}]
			}];	
				
			var gridObj_clwc = $.fn.bsgrid.init('searchTable_clwc', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				/*pageSizeSelect: true ,*/
				pageIncorrectTurnAlert: false,
				pageSize: 10 ,
				//stripeRows: true,  行色彩分隔 
				rowSelectedColor: false,
				displayBlankRows: false ,   //显示空白行
				localData:json_clwc,
				additionalRenderPerColumn: function(record, rowIndex, colIndex, tdObj, trObj, options){
					var data = gridObj_clwc.getRecord(rowIndex),
						sTpl = '<span class="mr15"><%=obj.ddsj%></span><span class="mr15">订单编号：<%=obj.ddbh%></span><span class="mr15"><%=obj.yhm%>(用户名)</span><span class="mr15"><%=obj.yyd%></span><span><%=obj.scmc%></span>';
					trObj.find('td').attr('colspan', '8').css({'background-color':'#6baf58', 'color':'#fff'}).html(_.template(sTpl, data));
					var splb = data.splb,
						len = splb.length,
						sTpl_1 = '<td style="text-align:left;"><div class="img-group-detail2"><img src="<%=obj.spzp%>" class="fl mr10" width="80" height="80"><div class="img-group-content"><p><a href="#" class="blue"><%=obj.spmc%></a></p><p>颜色分类：<%=obj.ysfl%></p><p>尺寸：<%=obj.cc%></p></div></div></td>',
						sTpl_2 = '<%var _rowspan = obj.splb.length > 1 ? \' rowspan="\' + obj.splb.length + \'"\' : \'\';%><td <%=_rowspan%>><%=obj.sqlx%></td><td <%=_rowspan%>><%=obj.sqly%></td><td <%=_rowspan%>><p class="red">￥<%=obj.tk%></p><p class="blue">PV <%=obj.jf%></p></td><td <%=_rowspan%>><%=obj.hf%></td><td <%=_rowspan%>><%=obj.sqsj%></td><td <%=_rowspan%>><p><%=obj.zt%></p><p class="mt10"><%=obj.kdgs%></p><p><%=obj.kddh%></p></td><td <%=_rowspan%>></td>';
						
						for(var i = len - 1; i >= 0; i--) {
							var $tr = $('<tr></tr>');
							$tr.append(_.template(sTpl_1, splb[i]));
							if(i === 0) {
								$tr.append(_.template(sTpl_2, data));
								$tr.find('td:last').append($('<a title="查看详情">查看详情</a>').click(function(e){
									//查看详情点击事件
									self.chaKan(gridObj_clwc.getRecord(rowIndex));
								})).append($('<br /><a title="申请处理">申请处理</a>'))/*.click(function(e){
									self.shenQing(gridObj_sqsh.getRecord(rowIndex));
								})*/;
							}
							trObj.after($tr);
						}
				}
				
			});
			
			var json_qb = [{
				id:'xx01',
				ddbh:'9884544445664412236',
				yhm:'小鱼儿',
				yyd:'福田实体店',
				scmc:'商城名称1',
				sqlx:'退款退货',
				sqly:'xxx',
				tk:'200.00',
				jf:'20',
				hf:'同意退款',
				sqsj:'2015-10-10 12:30:10',
				zt:'待收货',
				kdgs:'申通快递',
				kddh:'13232564665',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'1111111111111111111111111111111111111',
					ysfl:'黑色',
					cc:'XXL 170/175'
					},
					{
					id:'sp002',
					spzp:'resources/img/product01.jpg',
					spmc:'222222222222222222222222222222222222222',
					ysfl:'黑色',
					cc:'XXL 170/175'
					}]
			},
			{
				id:'xx01',
				ddbh:'9884544445664412236',
				yhm:'小鱼儿',
				yyd:'福田实体店',
				scmc:'商城名称1',
				sqlx:'退款退货',
				sqly:'xxx',
				tk:'200.00',
				jf:'20',
				hf:'同意退款',
				sqsj:'2015-10-10 12:30:10',
				zt:'待收货',
				kdgs:'申通快递',
				kddh:'13232564665',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'1111111111111111111111111111111111111',
					ysfl:'黑色',
					cc:'XXL 170/175'
					}]
			},
			{
				id:'xx01',
				ddbh:'9884544445664412236',
				yhm:'小鱼儿',
				yyd:'福田实体店',
				scmc:'商城名称1',
				sqlx:'退款退货',
				sqly:'xxx',
				tk:'200.00',
				jf:'20',
				hf:'同意退款',
				sqsj:'2015-10-10 12:30:10',
				zt:'待收货',
				kdgs:'申通快递',
				kddh:'13232564665',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'1111111111111111111111111111111111111',
					ysfl:'黑色',
					cc:'XXL 170/175'
					}]
			},
			{
				id:'xx01',
				ddbh:'9884544445664412236',
				yhm:'小鱼儿',
				yyd:'福田实体店',
				scmc:'商城名称1',
				sqlx:'退款退货',
				sqly:'xxx',
				tk:'200.00',
				jf:'20',
				hf:'同意退款',
				sqsj:'2015-10-10 12:30:10',
				zt:'待收货',
				kdgs:'申通快递',
				kddh:'13232564665',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'1111111111111111111111111111111111111',
					ysfl:'黑色',
					cc:'XXL 170/175'
					},
					{
					id:'sp002',
					spzp:'resources/img/product01.jpg',
					spmc:'222222222222222222222222222222222222222',
					ysfl:'黑色',
					cc:'XXL 170/175'
					}]
			}];	
				
			var gridObj_qb = $.fn.bsgrid.init('searchTable_qb', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				/*pageSizeSelect: true ,*/
				pageIncorrectTurnAlert: false,
				pageSize: 10 ,
				//stripeRows: true,  行色彩分隔 
				rowSelectedColor: false,
				displayBlankRows: false ,   //显示空白行
				localData:json_qb,
				additionalRenderPerColumn: function(record, rowIndex, colIndex, tdObj, trObj, options){
					var data = gridObj_qb.getRecord(rowIndex),
						sTpl = '<span class="mr15"><%=obj.ddsj%></span><span class="mr15">订单编号：<%=obj.ddbh%></span><span class="mr15"><%=obj.yhm%>(用户名)</span><span class="mr15"><%=obj.yyd%></span><span><%=obj.scmc%></span>';
					trObj.find('td').attr('colspan', '8').css({'background-color':'#6baf58', 'color':'#fff'}).html(_.template(sTpl, data));
					var splb = data.splb,
						len = splb.length,
						sTpl_1 = '<td style="text-align:left;"><div class="img-group-detail2"><img src="<%=obj.spzp%>" class="fl mr10" width="80" height="80"><div class="img-group-content"><p><a href="#" class="blue"><%=obj.spmc%></a></p><p>颜色分类：<%=obj.ysfl%></p><p>尺寸：<%=obj.cc%></p></div></div></td>',
						sTpl_2 = '<%var _rowspan = obj.splb.length > 1 ? \' rowspan="\' + obj.splb.length + \'"\' : \'\';%><td <%=_rowspan%>><%=obj.sqlx%></td><td <%=_rowspan%>><%=obj.sqly%></td><td <%=_rowspan%>><p class="red">￥<%=obj.tk%></p><p class="blue">PV <%=obj.jf%></p></td><td <%=_rowspan%>><%=obj.hf%></td><td <%=_rowspan%>><%=obj.sqsj%></td><td <%=_rowspan%>><p><%=obj.zt%></p><p class="mt10"><%=obj.kdgs%></p><p><%=obj.kddh%></p></td><td <%=_rowspan%>></td>';
						
						for(var i = len - 1; i >= 0; i--) {
							var $tr = $('<tr></tr>');
							$tr.append(_.template(sTpl_1, splb[i]));
							if(i === 0) {
								$tr.append(_.template(sTpl_2, data));
								$tr.find('td:last').append($('<a title="查看详情">查看详情</a>').click(function(e){
									//查看详情点击事件
									self.chaKan(gridObj_qb.getRecord(rowIndex));
								})).append($('<br /><a title="申请处理">申请处理</a>'))/*.click(function(e){
									self.shenQing(gridObj_sqsh.getRecord(rowIndex));
								})*/;
							}
							trObj.after($tr);
						}
				}
				
			});
			
		},
		chaKan : function(){
			$('#ddshglTpl').addClass('none');
			$('#ddshgl_ckTpl').removeClass('none');	
		}/*,
		shenQing : function(){
			alert("shenQing");
		}*/
	}	
});