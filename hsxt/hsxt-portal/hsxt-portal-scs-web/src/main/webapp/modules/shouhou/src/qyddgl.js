define(['text!shouhouTpl/qyddgl/qyddgl.html',
		'text!shouhouTpl/qyddgl/qyddgl_wzf.html',
		'text!shouhouTpl/qyddgl/qyddgl_yzf.html',
		'text!shouhouTpl/qyddgl/qyddgl_ywc.html',
		'text!shouhouTpl/qyddgl/qyddgl_yqs.html',
		'text!shouhouTpl/qyddgl/qyddgl_tkthdd.html',
		'text!shouhouTpl/qyddgl/qyddgl_qb.html',
		'text!shouhouTpl/qyddgl/qyddgl_ck.html'
		],function(qyddglTpl, qyddgl_wzfTpl, qyddgl_yzfTpl, qyddgl_ywcTpl, qyddgl_yqsTpl, qyddgl_tkthddTpl, qyddgl_qbTpl, qyddgl_ckTpl){
	return {
		 
		showPage : function(){
			$('#busibox').html(_.template(qyddglTpl)).append(qyddgl_ckTpl);
			$('#contentArea').html(qyddgl_wzfTpl).append(qyddgl_yzfTpl).append(qyddgl_ywcTpl).append(qyddgl_yqsTpl).append(qyddgl_tkthddTpl).append(qyddgl_qbTpl)			 			
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
			
			$('#city_slt').selectList({
				width:76
			});
			
			/*下拉选择框*/
			$('#province_slt').selectList({
				width:76,
				options:[
					{name:'广东省',value:'100'},
					{name:'江西省',value:'101'},
					{name:'湖南省',value:'102'}
				]
			}).change(function(e){
				 
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
				//debugger
				$('#city_slt').val('');
				$('#city_slt').selectList({
					options : dataArry
				});
			});
			
			
			
			/*end*/
			
			// 页面切换
			$('.tab01,.tab02,.tab03,.tab04,.tab05,.tab06').click(function(){
				var tabs=$('#qyddgl_wzfTpl, #qyddgl_yzfTpl, #qyddgl_ywcTpl, #qyddgl_yqsTpl, #qyddgl_tkthddTpl, #qyddgl_qbTpl');
				tabs.hide();
				var m = $(this).attr('class');				
				var n = m.charAt(m.length - 1);
				tabs.eq(n-1).show();
				$('#tabMenu').find('a').removeClass('active');
				$(this).addClass('active');	
			});
			
			/*表格数据模拟*/
			var json_wzf = [{
				id:'xx01',
				ddsj:'2014-12-12 09:10:00',
				ddbh:'9884544445664412236',
				yhm:'小鱼儿',
				yyd:'福田实体店',
				scmc:'商城名称1',
				sfk:'210.00',
				yf:'10.00',
				xfjf:'20.00',
				jyzt:'未支付',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'比菲力冬款男士休闲裤 加绒裤 加绒加厚款纯棉保暖直筒休闲长裤',
					ysfl:'黑色',
					dj:'100.00',
					sl:'2'
					}]
				},
				{
				id:'xx02',
				ddsj:'2014-12-11 11:10:00',
				ddbh:'9884544445664412236',
				yhm:'老鱼儿',
				yyd:'福田营业点',
				scmc:'商城名称2',
				sfk:'900.00',
				yf:'10.00',
				xfjf:'90.00',
				jyzt:'未支付',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'222222222222222222222222',
					ysfl:'黑色',
					dj:'100.00',
					sl:'2'
				},
				{
					id:'sp002',
					spzp:'resources/img/product03.jpg',
					spmc:'11111111111111111111111',
					ysfl:'白色',
					dj:'500.00',
					sl:'1'
				},
				{
					id:'sp003',
					spzp:'resources/img/product02.jpg',
					spmc:'比菲力冬款男士休闲裤 加绒裤',
					ysfl:'灰色',
					dj:'190.00',
					sl:'1'
				}]
			}];	
				
			var gridObj_wzf = $.fn.bsgrid.init('searchTable_wzf', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				/*pageSizeSelect: true ,*/
				pageIncorrectTurnAlert: false,
				pageSize: 10 ,
				//stripeRows: true,  行色彩分隔 
				rowSelectedColor: false,
				displayBlankRows: false ,   //显示空白行
				localData:json_wzf,
				additionalRenderPerColumn: function(record, rowIndex, colIndex, tdObj, trObj, options){
					var data = gridObj_wzf.getRecord(rowIndex),
						sTpl = '<span class="mr15"><%=obj.ddsj%></span><span class="mr15">订单编号：<%=obj.ddbh%></span><span class="mr15"><%=obj.yhm%>(用户名)</span><span class="mr15"><%=obj.yyd%></span><span><%=obj.scmc%></span>';
					trObj.find('td').attr('colspan', '7').css({'background-color':'#6baf58', 'color':'#fff'}).html(_.template(sTpl, data));
					var splb = data.splb,
						len = splb.length,
						sTpl_1 = '<td style="text-align:left;"><div class="img-group-detail2"><img src="<%=obj.spzp%>" class="fl mr10" width="80" height="80"><div class="img-group-content"><p><a href="#" class="blue"><%=obj.spmc%></a></p><p>颜色分类：<%=obj.ysfl%></p></div></div></td><td><%=obj.dj%></td><td><%=obj.sl%></td>',
						sTpl_2 = '<%var _rowspan = obj.splb.length > 1 ? \' rowspan="\' + obj.splb.length + \'"\' : \'\';%><td <%=_rowspan%>><p class="red fb">￥<%=obj.sfk%></p><p>含运费（<%=obj.yf%>）</p></td><td <%=_rowspan%>><p class="blue">PV<%=obj.xfjf%></p></td><td <%=_rowspan%>><%=obj.jyzt%></td><td <%=_rowspan%>></td>';
						
						for(var i = len - 1; i >= 0; i--) {
							var $tr = $('<tr></tr>');
							$tr.append(_.template(sTpl_1, splb[i]));
							if(i === 0) {
								$tr.append(_.template(sTpl_2, data));
								$tr.find('td:last').append($('<a title="查看详情">查看详情</a>').click(function(e){
									//查看详情点击事件
									self.chaKan(gridObj_wzf.getRecord(rowIndex));
								}));
							}
							trObj.after($tr);
						}
				}
				
			});
			
			var json_yzf = [{
				id:'xx01',
				ddsj:'2014-12-12 09:10:00',
				ddbh:'9884544445664412236',
				yhm:'小鱼儿',
				yyd:'福田实体店',
				scmc:'商城名称1',
				sfk:'210.00',
				yf:'10.00',
				xfjf:'20.00',
				jyzt:'已支付',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'比菲力冬款男士休闲裤 加绒裤 加绒加厚款纯棉保暖直筒休闲长裤',
					ysfl:'黑色',
					dj:'100.00',
					sl:'2'
					}]
			}];	
			
			var gridObj_yzf = $.fn.bsgrid.init('searchTable_yzf', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				/*pageSizeSelect: true ,*/
				pageIncorrectTurnAlert: false,
				pageSize: 10 ,
				//stripeRows: true,  行色彩分隔 
				rowSelectedColor: false,
				displayBlankRows: false ,   //显示空白行
				localData:json_yzf,
				additionalRenderPerColumn: function(record, rowIndex, colIndex, tdObj, trObj, options){
					var data = gridObj_yzf.getRecord(rowIndex),
						sTpl = '<span class="mr15"><%=obj.ddsj%></span><span class="mr15">订单编号：<%=obj.ddbh%></span><span class="mr15"><%=obj.yhm%>(用户名)</span><span class="mr15"><%=obj.yyd%></span><span><%=obj.scmc%></span>';
					trObj.find('td').attr('colspan', '7').css({'background-color':'#6baf58', 'color':'#fff'}).html(_.template(sTpl, data));
					var splb = data.splb,
						len = splb.length,
						sTpl_1 = '<td style="text-align:left;"><div class="img-group-detail2"><img src="<%=obj.spzp%>" class="fl mr10" width="80" height="80"><div class="img-group-content"><p><a href="#" class="blue"><%=obj.spmc%></a></p><p>颜色分类：<%=obj.ysfl%></p></div></div></td><td><%=obj.dj%></td><td><%=obj.sl%></td>',
						sTpl_2 = '<%var _rowspan = obj.splb.length > 1 ? \' rowspan="\' + obj.splb.length + \'"\' : \'\';%><td <%=_rowspan%>><p class="red fb">￥<%=obj.sfk%></p><p>含运费（<%=obj.yf%>）</p></td><td <%=_rowspan%>><p class="blue">PV<%=obj.xfjf%></p></td><td <%=_rowspan%>><%=obj.jyzt%></td><td <%=_rowspan%>></td>';
						
						for(var i = len - 1; i >= 0; i--) {
							var $tr = $('<tr></tr>');
							$tr.append(_.template(sTpl_1, splb[i]));
							if(i === 0) {
								$tr.append(_.template(sTpl_2, data));
								$tr.find('td:last').append($('<a title="查看详情">查看详情</a>').click(function(e){
									//查看详情点击事件
									self.chaKan(gridObj_yzf.getRecord(rowIndex));
								}));
							}
							trObj.after($tr);
						}
				}
				
			});
			
			var json_ywc = [{
				id:'xx02',
				ddsj:'2014-12-11 11:10:00',
				ddbh:'9884544445664412236',
				yhm:'老鱼儿',
				yyd:'福田营业点',
				scmc:'商城名称2',
				sfk:'900.00',
				yf:'10.00',
				xfjf:'90.00',
				jyzt:'已完成',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'222222222222222222222222',
					ysfl:'黑色',
					dj:'100.00',
					sl:'2'
				},
				{
					id:'sp002',
					spzp:'resources/img/product03.jpg',
					spmc:'11111111111111111111111',
					ysfl:'白色',
					dj:'500.00',
					sl:'1'
				},
				{
					id:'sp003',
					spzp:'resources/img/product02.jpg',
					spmc:'比菲力冬款男士休闲裤 加绒裤',
					ysfl:'灰色',
					dj:'190.00',
					sl:'1'
				}]
			}];	
			
			var gridObj_ywc = $.fn.bsgrid.init('searchTable_ywc', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				/*pageSizeSelect: true ,*/
				pageIncorrectTurnAlert: false,
				pageSize: 10 ,
				//stripeRows: true,  行色彩分隔 
				rowSelectedColor: false,
				displayBlankRows: false ,   //显示空白行
				localData:json_ywc,
				additionalRenderPerColumn: function(record, rowIndex, colIndex, tdObj, trObj, options){
					var data = gridObj_ywc.getRecord(rowIndex),
						sTpl = '<span class="mr15"><%=obj.ddsj%></span><span class="mr15">订单编号：<%=obj.ddbh%></span><span class="mr15"><%=obj.yhm%>(用户名)</span><span class="mr15"><%=obj.yyd%></span><span><%=obj.scmc%></span>';
					trObj.find('td').attr('colspan', '7').css({'background-color':'#6baf58', 'color':'#fff'}).html(_.template(sTpl, data));
					var splb = data.splb,
						len = splb.length,
						sTpl_1 = '<td style="text-align:left;"><div class="img-group-detail2"><img src="<%=obj.spzp%>" class="fl mr10" width="80" height="80"><div class="img-group-content"><p><a href="#" class="blue"><%=obj.spmc%></a></p><p>颜色分类：<%=obj.ysfl%></p></div></div></td><td><%=obj.dj%></td><td><%=obj.sl%></td>',
						sTpl_2 = '<%var _rowspan = obj.splb.length > 1 ? \' rowspan="\' + obj.splb.length + \'"\' : \'\';%><td <%=_rowspan%>><p class="red fb">￥<%=obj.sfk%></p><p>含运费（<%=obj.yf%>）</p></td><td <%=_rowspan%>><p class="blue">PV<%=obj.xfjf%></p></td><td <%=_rowspan%>><%=obj.jyzt%></td><td <%=_rowspan%>></td>';
						
						for(var i = len - 1; i >= 0; i--) {
							var $tr = $('<tr></tr>');
							$tr.append(_.template(sTpl_1, splb[i]));
							if(i === 0) {
								$tr.append(_.template(sTpl_2, data));
								$tr.find('td:last').append($('<a title="查看详情">查看详情</a>').click(function(e){
									//查看详情点击事件
									self.chaKan(gridObj_ywc.getRecord(rowIndex));
								}));
							}
							trObj.after($tr);
						}
				}
				
			});
			
			var json_yqs = [{
				id:'xx02',
				ddsj:'2014-12-11 11:10:00',
				ddbh:'9884544445664412236',
				yhm:'老鱼儿',
				yyd:'福田营业点',
				scmc:'商城名称2',
				sfk:'900.00',
				yf:'10.00',
				xfjf:'90.00',
				jyzt:'已取消',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'222222222222222222222222',
					ysfl:'黑色',
					dj:'100.00',
					sl:'2'
				},
				{
					id:'sp002',
					spzp:'resources/img/product03.jpg',
					spmc:'11111111111111111111111',
					ysfl:'白色',
					dj:'500.00',
					sl:'1'
				},
				{
					id:'sp003',
					spzp:'resources/img/product02.jpg',
					spmc:'比菲力冬款男士休闲裤 加绒裤',
					ysfl:'灰色',
					dj:'190.00',
					sl:'1'
				}]
			}];	
			
			var gridObj_yqs = $.fn.bsgrid.init('searchTable_yqs', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				/*pageSizeSelect: true ,*/
				pageIncorrectTurnAlert: false,
				pageSize: 10 ,
				//stripeRows: true,  行色彩分隔 
				rowSelectedColor: false,
				displayBlankRows: false ,   //显示空白行
				localData:json_yqs,
				additionalRenderPerColumn: function(record, rowIndex, colIndex, tdObj, trObj, options){
					var data = gridObj_yqs.getRecord(rowIndex),
						sTpl = '<span class="mr15"><%=obj.ddsj%></span><span class="mr15">订单编号：<%=obj.ddbh%></span><span class="mr15"><%=obj.yhm%>(用户名)</span><span class="mr15"><%=obj.yyd%></span><span><%=obj.scmc%></span>';
					trObj.find('td').attr('colspan', '7').css({'background-color':'#6baf58', 'color':'#fff'}).html(_.template(sTpl, data));
					var splb = data.splb,
						len = splb.length,
						sTpl_1 = '<td style="text-align:left;"><div class="img-group-detail2"><img src="<%=obj.spzp%>" class="fl mr10" width="80" height="80"><div class="img-group-content"><p><a href="#" class="blue"><%=obj.spmc%></a></p><p>颜色分类：<%=obj.ysfl%></p></div></div></td><td><%=obj.dj%></td><td><%=obj.sl%></td>',
						sTpl_2 = '<%var _rowspan = obj.splb.length > 1 ? \' rowspan="\' + obj.splb.length + \'"\' : \'\';%><td <%=_rowspan%>><p class="red fb">￥<%=obj.sfk%></p><p>含运费（<%=obj.yf%>）</p></td><td <%=_rowspan%>><p class="blue">PV<%=obj.xfjf%></p></td><td <%=_rowspan%>><%=obj.jyzt%></td><td <%=_rowspan%>></td>';
						
						for(var i = len - 1; i >= 0; i--) {
							var $tr = $('<tr></tr>');
							$tr.append(_.template(sTpl_1, splb[i]));
							if(i === 0) {
								$tr.append(_.template(sTpl_2, data));
								$tr.find('td:last').append($('<a title="查看详情">查看详情</a>').click(function(e){
									//查看详情点击事件
									self.chaKan(gridObj_yqs.getRecord(rowIndex));
								}));
							}
							trObj.after($tr);
						}
				}
				
			});
			
			var json_tkthdd = [{
				id:'xx01',
				ddsj:'2014-12-12 09:10:00',
				ddbh:'9884544445664412236',
				yhm:'小鱼儿',
				yyd:'福田实体店',
				scmc:'商城名称1',
				sfk:'210.00',
				yf:'10.00',
				xfjf:'20.00',
				jyzt:'未支付',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'比菲力冬款男士休闲裤 加绒裤 加绒加厚款纯棉保暖直筒休闲长裤',
					ysfl:'黑色',
					dj:'100.00',
					sl:'2'
				}]
			}];	
				
			var gridObj_tkthdd = $.fn.bsgrid.init('searchTable_tkthdd', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				/*pageSizeSelect: true ,*/
				pageIncorrectTurnAlert: false,
				pageSize: 10 ,
				//stripeRows: true,  行色彩分隔 
				rowSelectedColor: false,
				displayBlankRows: false ,   //显示空白行
				localData:json_tkthdd,
				additionalRenderPerColumn: function(record, rowIndex, colIndex, tdObj, trObj, options){
					var data = gridObj_tkthdd.getRecord(rowIndex),
						sTpl = '<span class="mr15"><%=obj.ddsj%></span><span class="mr15">订单编号：<%=obj.ddbh%></span><span class="mr15"><%=obj.yhm%>(用户名)</span><span class="mr15"><%=obj.yyd%></span><span><%=obj.scmc%></span>';
					trObj.find('td').attr('colspan', '7').css({'background-color':'#6baf58', 'color':'#fff'}).html(_.template(sTpl, data));
					var splb = data.splb,
						len = splb.length,
						sTpl_1 = '<td style="text-align:left;"><div class="img-group-detail2"><img src="<%=obj.spzp%>" class="fl mr10" width="80" height="80"><div class="img-group-content"><p><a href="#" class="blue"><%=obj.spmc%></a></p><p>颜色分类：<%=obj.ysfl%></p></div></div></td><td><%=obj.dj%></td><td><%=obj.sl%></td>',
						sTpl_2 = '<%var _rowspan = obj.splb.length > 1 ? \' rowspan="\' + obj.splb.length + \'"\' : \'\';%><td <%=_rowspan%>><p class="red fb">￥<%=obj.sfk%></p><p>含运费（<%=obj.yf%>）</p></td><td <%=_rowspan%>><p class="blue">PV<%=obj.xfjf%></p></td><td <%=_rowspan%>><%=obj.jyzt%></td><td <%=_rowspan%>></td>';
						
						for(var i = len - 1; i >= 0; i--) {
							var $tr = $('<tr></tr>');
							$tr.append(_.template(sTpl_1, splb[i]));
							if(i === 0) {
								$tr.append(_.template(sTpl_2, data));
								$tr.find('td:last').append($('<a title="查看详情">查看详情</a>').click(function(e){
									//查看详情点击事件
									self.chaKan(gridObj_tkthdd.getRecord(rowIndex));
								}));
							}
							trObj.after($tr);
						}
				}
				
			});
			
			var json_qb = [{
				id:'xx01',
				ddsj:'2014-12-12 09:10:00',
				ddbh:'9884544445664412236',
				yhm:'小鱼儿',
				yyd:'福田实体店',
				scmc:'商城名称1',
				sfk:'210.00',
				yf:'10.00',
				xfjf:'20.00',
				jyzt:'未支付',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'比菲力冬款男士休闲裤 加绒裤 加绒加厚款纯棉保暖直筒休闲长裤',
					ysfl:'黑色',
					dj:'100.00',
					sl:'2'
					}]
				},
				{
				id:'xx02',
				ddsj:'2014-12-11 11:10:00',
				ddbh:'9884544445664412236',
				yhm:'老鱼儿',
				yyd:'福田营业点',
				scmc:'商城名称2',
				sfk:'900.00',
				yf:'10.00',
				xfjf:'90.00',
				jyzt:'未支付',
				splb:[{
					id:'sp001',
					spzp:'resources/img/product01.jpg',
					spmc:'222222222222222222222222',
					ysfl:'黑色',
					dj:'100.00',
					sl:'2'
				},
				{
					id:'sp002',
					spzp:'resources/img/product03.jpg',
					spmc:'11111111111111111111111',
					ysfl:'白色',
					dj:'500.00',
					sl:'1'
				},
				{
					id:'sp003',
					spzp:'resources/img/product02.jpg',
					spmc:'比菲力冬款男士休闲裤 加绒裤',
					ysfl:'灰色',
					dj:'190.00',
					sl:'1'
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
					trObj.find('td').attr('colspan', '7').css({'background-color':'#6baf58', 'color':'#fff'}).html(_.template(sTpl, data));
					var splb = data.splb,
						len = splb.length,
						sTpl_1 = '<td style="text-align:left;"><div class="img-group-detail2"><img src="<%=obj.spzp%>" class="fl mr10" width="80" height="80"><div class="img-group-content"><p><a href="#" class="blue"><%=obj.spmc%></a></p><p>颜色分类：<%=obj.ysfl%></p></div></div></td><td><%=obj.dj%></td><td><%=obj.sl%></td>',
						sTpl_2 = '<%var _rowspan = obj.splb.length > 1 ? \' rowspan="\' + obj.splb.length + \'"\' : \'\';%><td <%=_rowspan%>><p class="red fb">￥<%=obj.sfk%></p><p>含运费（<%=obj.yf%>）</p></td><td <%=_rowspan%>><p class="blue">PV<%=obj.xfjf%></p></td><td <%=_rowspan%>><%=obj.jyzt%></td><td <%=_rowspan%>></td>';
						
						for(var i = len - 1; i >= 0; i--) {
							var $tr = $('<tr></tr>');
							$tr.append(_.template(sTpl_1, splb[i]));
							if(i === 0) {
								$tr.append(_.template(sTpl_2, data));
								$tr.find('td:last').append($('<a title="查看详情">查看详情</a>').click(function(e){
									//查看详情点击事件
									self.chaKan(gridObj_qb.getRecord(rowIndex));
								}));
							}
							trObj.after($tr);
						}
				}
				
			});
			
			/*end*/	
		 	 
		},
		chaKan : function(){
		 	$('#qyddglTpl').addClass('none');
			$('#qyddgl_ckTpl').removeClass('none');
			
			/*表格数据模拟*/
			var json_xq = [{
							"th_1":"比菲力冬款男士休闲裤 加绒裤 加绒加厚款纯棉保暖直筒休闲长裤",
							"th_2":"￥390.00",
							"th_3":"1",
							"th_4":"￥400.00",
							"th_5":"PV25",
							"th_6":"￥10.00"
						},
						{
							"th_1":"比菲力冬款男士休闲裤 加绒裤 加绒加厚款纯棉保暖直筒休闲长裤",
							"th_2":"￥390.00",
							"th_3":"1",
							"th_4":"￥400.00",
							"th_5":"PV25",
							"th_6":"￥10.00"
						},
						{
							"th_1":"比菲力冬款男士休闲裤 加绒裤 加绒加厚款纯棉保暖直筒休闲长裤",
							"th_2":"￥390.00",
							"th_3":"1",
							"th_4":"￥400.00",
							"th_5":"PV25",
							"th_6":"￥10.00"
						},
						{
							"th_1":"比菲力冬款男士休闲裤 加绒裤 加绒加厚款纯棉保暖直筒休闲长裤",
							"th_2":"￥390.00",
							"th_3":"1",
							"th_4":"￥400.00",
							"th_5":"PV25",
							"th_6":"￥10.00"
						},
						{
							"th_1":"比菲力冬款男士休闲裤 加绒裤 加绒加厚款纯棉保暖直筒休闲长裤",
							"th_2":"￥390.00",
							"th_3":"1",
							"th_4":"￥400.00",
							"th_5":"PV25",
							"th_6":"￥10.00"
						},
						{
							"th_1":"比菲力冬款男士休闲裤 加绒裤 加绒加厚款纯棉保暖直筒休闲长裤",
							"th_2":"￥390.00",
							"th_3":"1",
							"th_4":"￥400.00",
							"th_5":"PV25",
							"th_6":"￥10.00"
						}];	
	
			var gridObj;
			
			gridObj = $.fn.bsgrid.init('searchTable_xq', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				/*pageSizeSelect: true ,
				pageSize: 10 ,*/
				pageAll: true,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json_xq ,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						var tdHtml = null;
						if(colIndex == 1){
							tdHtml = '<span class="red fb">' + gridObj.getColumnValue(rowIndex, 'th_2') + '</span>';	
						}
						else if(colIndex == 3){
							tdHtml =  '<span class="red fb">' + gridObj.getColumnValue(rowIndex, 'th_4') + '</span><br />含运费(' + gridObj.getColumnValue(rowIndex, 'th_6') + ')';		
						}
						else if(colIndex == 4){
							tdHtml = '<span class="blue">' + gridObj.getColumnValue(rowIndex, 'th_5') + '</span>';		
						}
						return tdHtml;
					}.bind(this)
				},
				renderImg :  function(record, rowIndex, colIndex, options){	
					/*var idInt = parseInt($.trim(gridObj.getRecordIndexValue(record, 'ID')));*/
					var dlHtml = '<div class="pr h80 lh200"><div class="w100 pa"><a href="#"><img src="resources/img/product02.jpg" width="80" height="80" /></a></div><h4 class="pl100 tl"><a href="#">' + gridObj.getColumnValue(rowIndex, 'th_1') + '</a></h4><p class="tl pl100">颜色分类：<i class="pr15" style="background:#0CF;"></i></p></div>';
					return dlHtml;
				}
				
			});
			$('#searchTable_xq_pt_outTab').remove();
			/*end*/	
				
		}
	}
}); 

 