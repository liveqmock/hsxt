define([
	"text!jingyingTpl/goodslist.html" 
], function(goodslistTpl) {

	var goodslist = {
		show: function() {
			//加载中间内容 
			$(".operationsArea").html(goodslistTpl);
			
			var self  = this ;
			//列表			
			$('#sel_scmc').combobox();
			$('#sel_pp').combobox();
			$('#sel_sp').combobox();
			$('#sel_fl').combobox();
			 
			//状态列表
			$('#sel_zt').selectList({width:155,optionWidth:155});
			
			$('.search-cont').height(410 - $('.search-toolbar').height() - parseInt($('.search-toolbar')
				.css('padding-top')) - parseInt($('.search-toolbar').css('padding-bottom')));
			//切换

			function tabs(elem, tabs) {
				var num=$(elem).children('li').length;
				for(var i=0;i<num;i++){
					$(elem).children('li').eq(i).attr('id','tab0'+(i+1));
				}
				$(elem).children('li').click(function() {
					$(this).addClass("cur").siblings().removeClass("cur") ;
				})
			};
			tabs('.tabs ul');

			var goodsTable = $(".search-table tr").children();

			function hideCol(colnum) {
				goodsTable.show();
				for (var i = 0; i < colnum.length; i++) {
					$(".search-table tr").each(function() {
						$(this).children().eq(colnum[i] - 1).hide()
					})
				}
			};


			//hideCol([7, 8, 10, 11, 12, 13, 14, 15]);
			self.load_dsh() ;
			
			$("#tab01").click(function() {
				$(this).addClass("cur").siblings().removeClass("cur") ;
				//hideCol([7, 8, 10, 11, 12, 13, 14, 15]);
				self.load_dsh() ;
			});
			$("#tab02").click(function() {				 
				$(this).addClass("cur").siblings().removeClass("cur") ;
				//hideCol([4, 5, 6, 7, 8, 11, 10, 18]);
				self.load_dcljb();
			});
			$("#tab03").click(function() {
				$(this).addClass("cur").siblings().removeClass("cur") ;
				//hideCol([6, 8, 10, 11, 12, 13, 14, 15]);
				self.load_shbtg();
			});
			$("#tab04").click(function() {
				$(this).addClass("cur").siblings().removeClass("cur") ;
				//hideCol([4, 5, 6, 7, 8, 11, 10]);
				self.load_jbclxj();
			});
			$("#tab05").click(function() {
				$(this).addClass("cur").siblings().removeClass("cur") ;
				//hideCol([4, 5, 6, 7, 8, 11, 10]);
				self.load_jbclbwg();
			});
			$("#tab06").click(function() {
				$(this).addClass("cur").siblings().removeClass("cur") ;
				//hideCol([5, 6, 7, 11, 10, 11, 12, 13, 14, 15]);
				self.load_zcsjsp();
			});
 
			//图片左右浏览 
			//图片切换	
			$(".tr_img").hover(
				function() {
					$(this).find('.arrow_left, .arrow_right').stop(false, true).show()
				},
				function() {
					$(this).find('.arrow_left, .arrow_right').stop(false, true).hide()
				}
			);
			$('.arrow_left, .arrow_right').css("userSelect", "none");
			$(".arrow_right").click(function() {
				var imgWarp = $(this).siblings(".td_img").children(".inner_img"),
					imgNum = imgWarp.children("img").size();
				imgWarp.width(imgNum * 77 + 'px');
				var n = imgNum - 3;
				if (parseInt(imgWarp.css("left")) > -77 * n) {
					imgWarp.animate({
						left: "-=77px"
					}, 200)
				};
			});
			$(".arrow_left").click(function() {
				var imgWarp = $(this).siblings(".td_img").children(".inner_img");
				if (parseInt(imgWarp.css("left")) < 0) {
					imgWarp.animate({
						left: "+=77px"
					}, 200);
				}
			});
			
			function dropBox(inputbox, dropwarp) {
				inputbox.click(function(event) {
					$('.popupmenu').hide();
					dropwarp.show();
					dropwarp.children().children('li').click(function() {
						var inputVal = $(this).text();
						inputbox.val(inputVal)
						dropwarp.hide();
					})
					event.stopPropagation();
				})
				$('body').click(function() {
					dropwarp.hide();
				})
			}
			dropBox($('#mallname'), $('#popupmenu01'));
			dropBox($('#brand'), $('#popupmenu02'));
			dropBox($('#fir'), $('#popupmenu03'));
			dropBox($('#sec'), $('#popupmenu04'));
			
			
			//输入框下拉列 
			$(".review").click(function() {
				$("#review").dialog({
					show: true,
					modal: true,
					title: "审核商品",
					width: 350,
					buttons: {
						'审核通过': function() {
							$(this).dialog("close");
						},
						'审核不通过': function() {
							$(this).dialog("close");
						}
					}
				});
			});
			$(".review_record").click(function() {
				$("#review_record").dialog({
					show: true,
					modal: true,
					title: "审核记录",
					width: 500,
					buttons: {
						'关闭': function() {
							$(this).dialog("close");
						}
					}
				});
			});
			$(".report_detail").click(function() {
				$("#report_detail").dialog({
					show: true,
					modal: true,
					title: "审核记录",
					width: 550,
					buttons: {
						'关闭': function() {
							$(this).dialog("close");
						}
					}
				});
			});
		
			$(".stdsh").click(function(){
				$("#subNav_2_02").trigger("click");	
			})
			$(".wsscsh").click(function(){
				$("#subNav_2_06").trigger("click");	
			})
			
			
			
			
			
			 
			/* 
			var gridObj , self =this ;
			 
			gridObj = $.fn.bsgrid.init('tableDetail_dsh', {				 
				//url : comm.domainList['local']+ comm.UrlList["jfzhmxcx"] , 
				pageSize: 5 , 
				stripeRows: true,  //行色彩分 
				displayBlankRows: false ,   //显示空白行
			  	localData: testData,
			  	renderImg:function(record, rowIndex, colIndex, options){
			  		if (colIndex==1){
			  			var idInt = parseInt($.trim(gridObj.getRecordIndexValue(record, 'sn')));
			  			var dlHtml = '<dl class="imgWord table-msg">' +
						                    '<dt><img src='+idInt+'.jpg style="width:70px;height:70px;"/></dt>' +
						                  	'<dd>' +  gridObj.getRecordIndexValue(record, 'spmc') +  ' </dd>' +
						                    '<dd>颜色：'+ gridObj.getRecordIndexValue(record, 'ys') +'</dd>' +
						                    '<dd>尺码：'+ gridObj.getRecordIndexValue(record, 'cm') +'</dd>' + 
						                  '</dl>' ; 
        				return dlHtml ; 
			  		} else if(colIndex==2){
			  			var idInt = parseInt($.trim(gridObj.getRecordIndexValue(record, 'sn')));
        				return '<img src="../images/' + ((idInt % 3) == 0 ? 3 : (idInt % 3)) + '.jpg" style="width:70px;height:70px;" />'; 
			  		}
			  		
			  	} ,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a data-type="'+ gridObj.getCellRecordValue(rowIndex,2) +'" >申请重开</a>').click(function(e) { \
							
						});
						return   link1 ;
					}  
				} 			
			} );	
			 */
			
			

		},
		loadTable : function(options){	
			$('#tableBox > table').addClass('none');
			$('#'+options.tableID).removeClass('none');
						 
			this.gridObj = $.fn.bsgrid.init( options.tableID , {				 
				//url : options.url, 
				pageSize: 5 , 
				stripeRows: true,  //行色彩分 
				displayBlankRows: false ,   //显示空白行
			  	localData:   options.testData ,
			  	renderImg: options.renderImgCall ,
				operate :    options.operateObj 
			} );	
			
		},
		load_dsh : function(){
			var self = this;
			//待审核分页表格
			var testData =[
				{sn:1,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',tjsj:'2015-01-20\n12:10:00',clsj:'2015-01-20\n12:10:00' ,shsj:'2015-01-20\n12:10:00',shcz:'审核',cljg:'已处理',sm:'说明' } ,
				{sn:1,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',tjsj:'2015-01-20\n12:10:00',clsj:'2015-01-20\n12:10:00' ,shsj:'2015-01-20\n12:10:00',shcz:'审核',cljg:'已处理',sm:'说明' } 
				 
			];			
			
			var options= {
				tableID:'tableDetail_dsh',
				url:'',
				testData:testData,
				renderImgCall : function(record, rowIndex, colIndex, options){
			  		if (colIndex==1){
			  			var idInt = parseInt($.trim(self.gridObj.getRecordIndexValue(record, 'sn')));
			  			var dlHtml = '<dl class="imgWord table-msg">' +
						                    '<dt><img src='+idInt+'.jpg style="width:70px;height:70px;"/></dt>' +
						                  	'<dd>' +  self.gridObj.getRecordIndexValue(record, 'spmc') +  ' </dd>' +
						                    '<dd>颜色：'+ self.gridObj.getRecordIndexValue(record, 'ys') +'</dd>' +
						                    '<dd>尺码：'+ self.gridObj.getRecordIndexValue(record, 'cm') +'</dd>' + 
						                  '</dl>' ; 
        				return dlHtml ; 
			  		}
			  		
			  	} ,
			  	operateObj: {	
					
					add : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a data-type="'+ self.gridObj.getCellRecordValue(rowIndex,2) +'" >审核</a>').click(function(e) {  
							$("#review_dialog").dialog({
								show: true,
								modal: true,
								title: "审核商品",
								width: 350,
								buttons: {
									'审核通过': function() {
										$(this).dialog("destroy");
									},
									'审核不通过': function() {
										$(this).dialog("destroy");
									},
									'暂不处理':function() {
										$(this).dialog("destroy");
									}
								}
							});
							
							
						});
						return   link1 ;
					}  ,
					detail : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a data-type="'+ self.gridObj.getCellRecordValue(rowIndex,2) +'" >查看详情</a>').click(function(e) {  
							
						});
						return   link1 ;
					}  
				} 
			  	
				
			}			
			this.loadTable(options);				
		},
		
		//待处理举报		
		load_dcljb : function(){
			var self = this;
			//待审核分页表格
			var testData =[
				{sn:1,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',cljg:'已处理',jbnr:'违规品' ,jbtp:'1.jpg',jbr:'张三',jbsj:'2015-01-20\n12:10:00',sm:'说明' } ,
				{sn:1,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',cljg:'已处理',jbnr:'违规品' ,jbtp:'1.jpg',jbr:'张三',jbsj:'2015-01-20\n12:10:00',sm:'说明' } ,
				{sn:1,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',cljg:'已处理',jbnr:'违规品' ,jbtp:'1.jpg',jbr:'张三',jbsj:'2015-01-20\n12:10:00',sm:'说明' } ,
				{sn:1,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',cljg:'已处理',jbnr:'违规品' ,jbtp:'1.jpg',jbr:'张三',jbsj:'2015-01-20\n12:10:00',sm:'说明' } ,
				{sn:1,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',cljg:'已处理',jbnr:'违规品' ,jbtp:'1.jpg',jbr:'张三',jbsj:'2015-01-20\n12:10:00',sm:'说明' }  
				     
			];			
			
			var options= {
				tableID:'tableDetail_dcljb',
				url:'',
				testData:testData,
				renderImgCall : function(record, rowIndex, colIndex, options){
			  		if (colIndex==1){
			  			var idInt = parseInt($.trim(self.gridObj.getRecordIndexValue(record, 'sn')));
			  			var dlHtml = '<dl class="imgWord table-msg">' +
						                    '<dt><img src='+idInt+'.jpg style="width:70px;height:70px;"/></dt>' +
						                  	'<dd>' +  self.gridObj.getRecordIndexValue(record, 'spmc') +  ' </dd>' +
						                    '<dd>颜色：'+ self.gridObj.getRecordIndexValue(record, 'ys') +'</dd>' +
						                    '<dd>尺码：'+ self.gridObj.getRecordIndexValue(record, 'cm') +'</dd>' + 
						                  '</dl>' ; 
        				return dlHtml ; 
			  		} else if(colIndex==5){
			  			var idInt = parseInt($.trim(self.gridObj.getRecordIndexValue(record, 'sn')));
        				return '<img src="../images/' + ((idInt % 3) == 0 ? 3 : (idInt % 3)) + '.jpg" style="width:70px;height:70px;" />'; 
			  		}
			  		
			  	} ,
			  	operateObj: {	
					
					add : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a data-type="'+ self.gridObj.getCellRecordValue(rowIndex,2) +'" >审核记录</a>').click(function(e) {  
							 $("#review_record").dialog({
								show: true,
								modal: true,
								title: "审核记录",
								width: 500,
								buttons: {
									'关闭': function() {
										$(this).dialog("destroy");
									}
								}
							});
							
							
						});
						return   link1 ;
					}  ,
					detail : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a data-type="'+ self.gridObj.getCellRecordValue(rowIndex,2) +'" >举报详情</a>').click(function(e) {  
							$("#report_detail").dialog({
								show: true,
								modal: true,
								title: "审核记录",
								width: 550,
								buttons: {
									'关闭': function() {
										$(this).dialog("destroy");
									}
								}
							});
						});
						return   link1 ;
					}  
				} 
			  	
				
			}			
			this.loadTable(options);				
		},
		
		//审核不通过
		load_shbtg : function(){
			var self = this;
			//待审核分页表格
			var testData =[
				{sn:1,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',tjsj:'2015-01-20\n12:10:00',clsj:'2015-01-20\n12:10:00' ,shsj:'2015-01-20\n12:10:00',shcz:'审核',shr:'张三',cljg:'已处理',sm:'说明' } ,
				{sn:1,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',tjsj:'2015-01-20\n12:10:00',clsj:'2015-01-20\n12:10:00' ,shsj:'2015-01-20\n12:10:00',shcz:'审核',shr:'张三',cljg:'已处理',sm:'说明' } 
				 
			];			
				
			
			var options= {
				tableID:'tableDetail_shbtg',
				url:'',
				testData:testData,
				renderImgCall : function(record, rowIndex, colIndex, options){
			  		if (colIndex==1){
			  			var idInt = parseInt($.trim(self.gridObj.getRecordIndexValue(record, 'sn')));
			  			var dlHtml = '<dl class="imgWord table-msg">' +
						                    '<dt><img src='+idInt+'.jpg style="width:70px;height:70px;"/></dt>' +
						                  	'<dd>' +  self.gridObj.getRecordIndexValue(record, 'spmc') +  ' </dd>' +
						                    '<dd>颜色：'+ self.gridObj.getRecordIndexValue(record, 'ys') +'</dd>' +
						                    '<dd>尺码：'+ self.gridObj.getRecordIndexValue(record, 'cm') +'</dd>' + 
						                  '</dl>' ; 
        				return dlHtml ; 
			  		} else if(colIndex==2){
			  			var idInt = parseInt($.trim(self.gridObj.getRecordIndexValue(record, 'sn')));
        				return '<img src="../images/' + ((idInt % 3) == 0 ? 3 : (idInt % 3)) + '.jpg" style="width:70px;height:70px;" />'; 
			  		}
			  		
			  	} ,
			  	operateObj: {	
					
					add : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a data-type="'+ self.gridObj.getCellRecordValue(rowIndex,2) +'" >审核</a>').click(function(e) {  
							$("#review_dialog").dialog({
								show: true,
								modal: true,
								title: "审核商品",
								width: 350,
								buttons: {
									'审核通过': function() {
										$(this).dialog("destroy");
									},
									'审核不通过': function() {
										$(this).dialog("destroy");
									},
									'暂不处理':function() {
										$(this).dialog("destroy");
									}
								}
							});
							
							
						});
						return   link1 ;
					}  ,
					detail : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a data-type="'+ self.gridObj.getCellRecordValue(rowIndex,2) +'" >查看详情</a>').click(function(e) {  
							
						});
						return   link1 ;
					}  
				} 
			}			
			this.loadTable(options);				
		} ,
		
		//举报处理下架
		load_jbclxj : function(){
			var self = this;
			//待审核分页表格
			var testData =[
				{sn:1,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',cljg:'已处理',jbnr:'违规品' ,jbtp:'1.jpg',jbr:'张三',jbsj:'2015-01-20\n12:10:00',sm:'说明' } , 
				{sn:2,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',cljg:'已处理',jbnr:'违规品' ,jbtp:'1.jpg',jbr:'张三',jbsj:'2015-01-20\n12:10:00',sm:'说明' } ,
				{sn:3,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',cljg:'已处理',jbnr:'违规品' ,jbtp:'1.jpg',jbr:'张三',jbsj:'2015-01-20\n12:10:00',sm:'说明' } ,
				{sn:4,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',cljg:'已处理',jbnr:'违规品' ,jbtp:'1.jpg',jbr:'张三',jbsj:'2015-01-20\n12:10:00',sm:'说明' } ,
				{sn:5,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',cljg:'已处理',jbnr:'违规品' ,jbtp:'1.jpg',jbr:'张三',jbsj:'2015-01-20\n12:10:00',sm:'说明' } 
			
			];	 
			var options= {
				tableID:'tableDetail_jbclxj',
				url:'',
				testData:testData,
				renderImgCall : function(record, rowIndex, colIndex, options){
			  		if (colIndex==1){
			  			var idInt = parseInt($.trim(self.gridObj.getRecordIndexValue(record, 'sn')));
			  			var dlHtml = '<dl class="imgWord table-msg">' +
						                    '<dt><img src='+idInt+'.jpg style="width:70px;height:70px;"/></dt>' +
						                  	'<dd>' +  self.gridObj.getRecordIndexValue(record, 'spmc') +  ' </dd>' +
						                    '<dd>颜色：'+ self.gridObj.getRecordIndexValue(record, 'ys') +'</dd>' +
						                    '<dd>尺码：'+ self.gridObj.getRecordIndexValue(record, 'cm') +'</dd>' + 
						                  '</dl>' ; 
        				return dlHtml ; 
			  		} else if(colIndex==5){
			  			var idInt = parseInt($.trim(self.gridObj.getRecordIndexValue(record, 'sn')));
        				return '<img src="../images/' + ((idInt % 3) == 0 ? 3 : (idInt % 3)) + '.jpg" style="width:70px;height:70px;" />'; 
			  		}
			  		
			  	} ,
			  	operateObj: {	
					
					add : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a data-type="'+ self.gridObj.getCellRecordValue(rowIndex,2) +'" >审核记录</a>').click(function(e) {  
							 $("#review_record").dialog({
								show: true,
								modal: true,
								title: "审核记录",
								width: 500,
								buttons: {
									'关闭': function() {
										$(this).dialog("destroy");
									}
								}
							});
							
							
						});
						return   link1 ;
					}  ,
					detail : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a data-type="'+ self.gridObj.getCellRecordValue(rowIndex,2) +'" >举报详情</a>').click(function(e) {  
							$("#report_detail").dialog({
								show: true,
								modal: true,
								title: "审核记录",
								width: 550,
								buttons: {
									'关闭': function() {
										$(this).dialog("destroy");
									}
								}
							});
						});
						return   link1 ;
					}  
				} 
			}			
			this.loadTable(options);		
			
		} ,
		
		//举报处理不违规 
		load_jbclbwg : function(){
			var self = this;
			//待审核分页表格
			var testData =[
				{sn:1,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',cljg:'已处理',jbnr:'违规品' ,jbtp:'1.jpg',jbr:'张三',jbsj:'2015-01-20\n12:10:00',sm:'说明' } , 
				{sn:2,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',cljg:'已处理',jbnr:'违规品' ,jbtp:'1.jpg',jbr:'张三',jbsj:'2015-01-20\n12:10:00',sm:'说明' } ,
				{sn:3,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',cljg:'已处理',jbnr:'违规品' ,jbtp:'1.jpg',jbr:'张三',jbsj:'2015-01-20\n12:10:00',sm:'说明' } ,
				{sn:4,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',cljg:'已处理',jbnr:'违规品' ,jbtp:'1.jpg',jbr:'张三',jbsj:'2015-01-20\n12:10:00',sm:'说明' } ,
				{sn:5,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',cljg:'已处理',jbnr:'违规品' ,jbtp:'1.jpg',jbr:'张三',jbsj:'2015-01-20\n12:10:00',sm:'说明' } 
			
			];	 
			var options= {
				tableID:'tableDetail_jbclbwg',
				url:'',
				testData:testData,
				renderImgCall : function(record, rowIndex, colIndex, options){
			  		if (colIndex==1){
			  			var idInt = parseInt($.trim(self.gridObj.getRecordIndexValue(record, 'sn')));
			  			var dlHtml = '<dl class="imgWord table-msg">' +
						                    '<dt><img src='+idInt+'.jpg style="width:70px;height:70px;"/></dt>' +
						                  	'<dd>' +  self.gridObj.getRecordIndexValue(record, 'spmc') +  ' </dd>' +
						                    '<dd>颜色：'+ self.gridObj.getRecordIndexValue(record, 'ys') +'</dd>' +
						                    '<dd>尺码：'+ self.gridObj.getRecordIndexValue(record, 'cm') +'</dd>' + 
						                  '</dl>' ; 
        				return dlHtml ; 
			  		} else if(colIndex==5){
			  			var idInt = parseInt($.trim(self.gridObj.getRecordIndexValue(record, 'sn')));
        				return '<img src="../images/' + ((idInt % 3) == 0 ? 3 : (idInt % 3)) + '.jpg" style="width:70px;height:70px;" />'; 
			  		}
			  		
			  	} ,
			  	operateObj: {	
					
					add : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a data-type="'+ self.gridObj.getCellRecordValue(rowIndex,2) +'" >审核记录</a>').click(function(e) {  
							 $("#review_record").dialog({
								show: true,
								modal: true,
								title: "审核记录",
								width: 500,
								buttons: {
									'关闭': function() {
										$(this).dialog("destroy");
									}
								}
							});
							
							
						});
						return   link1 ;
					}  ,
					detail : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a data-type="'+ self.gridObj.getCellRecordValue(rowIndex,2) +'" >举报详情</a>').click(function(e) {  
							$("#report_detail").dialog({
								show: true,
								modal: true,
								title: "审核记录",
								width: 550,
								buttons: {
									'关闭': function() {
										$(this).dialog("destroy");
									}
								}
							});
						});
						return   link1 ;
					}  
				} 
			}			
			this.loadTable(options);		
			
		} ,
		
		//正常上架商品
		load_zcsjsp : function(){
			var self = this;
			//待审核分页表格
			var testData =[
				{sn:1,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',tjsj:'2015-01-20\n12:10:00',clsj:'2015-01-20\n12:10:00',cljg:'已处理',sm:'说明' } , 
				{sn:1,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',tjsj:'2015-01-20\n12:10:00',clsj:'2015-01-20\n12:10:00',cljg:'已处理',sm:'说明' },
				{sn:1,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',tjsj:'2015-01-20\n12:10:00',clsj:'2015-01-20\n12:10:00',cljg:'已处理',sm:'说明' },
				{sn:1,spmc:'今日特价！吉普战车三合一冲锋衣，……【交易快照】',ys:'蓝色',cm:'XXL 170/175',scmc:'商城1',tjsj:'2015-01-20\n12:10:00',clsj:'2015-01-20\n12:10:00',cljg:'已处理',sm:'说明' }
				
			];	 
			var options= {
				tableID:'tableDetail_zcsjsp',
				url:'',
				testData:testData,
				renderImgCall : function(record, rowIndex, colIndex, options){
			  		if (colIndex==1){
			  			var idInt = parseInt($.trim(self.gridObj.getRecordIndexValue(record, 'sn')));
			  			var dlHtml = '<dl class="imgWord table-msg">' +
						                    '<dt><img src='+idInt+'.jpg style="width:70px;height:70px;"/></dt>' +
						                  	'<dd>' +  self.gridObj.getRecordIndexValue(record, 'spmc') +  ' </dd>' +
						                    '<dd>颜色：'+ self.gridObj.getRecordIndexValue(record, 'ys') +'</dd>' +
						                    '<dd>尺码：'+ self.gridObj.getRecordIndexValue(record, 'cm') +'</dd>' + 
						                  '</dl>' ; 
        				return dlHtml ; 
			  		} else if(colIndex==2){
			  			var idInt = parseInt($.trim(self.gridObj.getRecordIndexValue(record, 'sn')));
        				return '<img src="../images/' + ((idInt % 3) == 0 ? 3 : (idInt % 3)) + '.jpg" style="width:70px;height:70px;" />'; 
			  		}
			  		
			  	} ,
			  	operateObj: {	
					
					add : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a data-type="'+ self.gridObj.getCellRecordValue(rowIndex,2) +'" >审核记录</a>').click(function(e) {  
							 $("#review_record").dialog({
								show: true,
								modal: true,
								title: "审核记录",
								width: 500,
								buttons: {
									'关闭': function() {
										$(this).dialog("destroy");
									}
								}
							});
							
							
						});
						return   link1 ;
					}  ,
					detail : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a data-type="'+ self.gridObj.getCellRecordValue(rowIndex,2) +'" >查看详情</a>').click(function(e) {  
							 
						});
						return   link1 ;
					}  
				} 
			}			
			this.loadTable(options);		
			
		} ,
		
		hide: function() {
			//清空中间内容） 
			$(".operationsArea").empty();
		}

	};


	return goodslist;


});