define(['text!resourceQuotaTpl/sub/yjzypepz/yjzypepz.html',
		'text!resourceQuotaTpl/sub/yjzypepz/yjzypepz_view.html',
		'text!resourceQuotaTpl/sub/yjzypepz/yjzypepz_opt.html',
		'text!resourceQuotaTpl/sub/yjzypepz/yjzypepz_xh.html',
		'zrender', 'zrender/shape/Rectangle' ,'zrender/shape/Line','zrender/shape/Text',
		],function( yjzypepzTpl, yjzypepz_viewTpl, yjzypepz_optTpl,yjzypepz_xhTpl, zrender, RectangleShape,LineShape,TextSharp){
	return {
 		offset:0.5,
 		canvasWidth:842,
 		canvasHeight: 580,
 		textArr : [],
 		textObject:[],
 		rectangle : {} ,	
 		
		showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(yjzypepzTpl )) ;
			
			var self = this ;
		    
		    /*表格数据模拟*/
			var json = [{
							"glgshsh":"010000000000",
							"qymc":"互生科技",
							"jhnpezs":"200",
							"ysypezs":"190",
							"dsppezs":"20",
							"wsypezs":"10"
						},
						{
							"glgshsh":"020000000000",
							"qymc":"互生科技",
							"jhnpezs":"200",
							"ysypezs":"190",
							"dsppezs":"20",
							"wsypezs":"10"
						},
						{
							"glgshsh":"030000000000",
							"qymc":"互生科技",
							"jhnpezs":"200",
							"ysypezs":"190",
							"dsppezs":"20",
							"wsypezs":"10"
						}];	
			
			var gridObj = $.fn.bsgrid.init('yjzypepz_ql', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json ,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						var opt_btn = $('<a>查看</a>').click(function(e) {
							
							var obj = gridObj.getRecord(rowIndex);
							this.opt_view(obj);
							
						}.bind(this) ) ;
						
						return opt_btn;
					}.bind(this),
					
					edit : function(record, rowIndex, colIndex, options){
						var opt_btn = $('<a>配额申请</a>').click(function(e) {
							
							var obj = gridObj.getRecord(rowIndex);
							this.opt_apply(obj);
							
						}.bind(this) ) ;
						
						return opt_btn;
					}.bind(this)
				}
				
			});
			
			/*end*/			   	
			
			/*  
			 *  0、未分配
			 *  1、申请额
			 *  2、中国大陆
			 *  3、香港 
			 *  4、台湾
			 *  5、澳门  
			 */
			this.textObject = [				
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 
				3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 
				3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 
				3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 
				4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 
				4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 
				4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 
				4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,
				
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 5 , 5 , 5 , 5 , 
				
				5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 ,
				5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 ,
				5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 ,
				5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 ,
				5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 ,
				5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 ,
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,
				 
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 
				0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 
			]  
			
			
			
				
		},
		opt_view : function(obj){
			var that = this;
			$('#yjzypepzDlg').html(yjzypepz_viewTpl);
			
			/*弹出框*/
			$( "#yjzypepzDlg" ).dialog({
				title:"查看一级资源配额",
				width:"1000",
				height:"500",
				modal:true,
				closeIcon : true,
				buttons:{ 
					"配额申请":function(){

						$( this ).dialog( "destroy" );
						that.opt_apply(obj);

					},
					"关闭":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
			
			
			/*表格数据模拟*/
			
			var json = [{
							"dqpt":"中国台湾",
							"ysypes":"14",
							"wsypes":"1",
							"dsppes":"15",
							"sqhksypes":"16"
						},
						{
							"dqpt":"中国香港",
							"ysypes":"25",
							"wsypes":"20",
							"dsppes":"10",
							"sqhksypes":"30"
						},
						{
							"dqpt":"中国澳门",
							"ysypes":"10",
							"wsypes":"9",
							"dsppes":"10",
							"sqhksypes":"19"
						}];	
			
			var gridObj = $.fn.bsgrid.init('yjzypepz_view_ql', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json 
				
			});
			
			/*end*/	
			
			
			/*表单值初始化*/
			
			$('#yjzypepz_view_glgshsh').text(obj.glgshsh);
			$('#yjzypepz_view_glgsmc').text(obj.qymc);
			$('#yjzypepz_view_jhnpezs').text(obj.jhnpezs);
			$('#yjzypepz_view_dsppezs').text(obj.dsppezs);
			
			
			/*end*/	
			
			
			
		},
		opt_apply : function(obj){
			var that = this;
			$('#yjzypepzDlg').html(yjzypepz_optTpl);
			 
			/*弹出框*/
			$( "#yjzypepzDlg" ).dialog({
				title:"一级资源配额申请",
				width:"1000",
				height:"600",
				modal:true,
				closeIcon : true,
				buttons:{ 
					"确定":function(){
						
						if(!that.validate()){
							return;
						}
						else{
							$( this ).dialog( "destroy" );	
						}
						
					},
					"取消":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
			
			
			/*表格数据模拟*/
			
			var json = [{
							"dqpt":"中国台湾",
							"ysypes":"14",
							"wsypes":"1",
							"dsppes":"15",
							"sqhksypes":"16"
						},
						{
							"dqpt":"中国香港",
							"ysypes":"25",
							"wsypes":"20",
							"dsppes":"10",
							"sqhksypes":"30"
						},
						{
							"dqpt":"中国澳门",
							"ysypes":"10",
							"wsypes":"9",
							"dsppes":"10",
							"sqhksypes":"19"
						}];	
			
			var gridObj = $.fn.bsgrid.init('yjzypepz_opt_ql', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json 
				
			});
			
			/*end*/	
			
			
			/*表单值初始化*/
			
			$('#yjzypepz_opt_glgshsh').text(obj.glgshsh);
			$('#yjzypepz_opt_glgsmc').text(obj.qymc);
			$('#yjzypepz_opt_jhnpezs').text(obj.jhnpezs);
			$('#yjzypepz_opt_dsppezs').text(obj.dsppezs);
			
			/*下拉列表*/
			
			var optionData = [];
			$.each(json, function(n, value){
				optionData[n] = {name : value.dqpt, value : n, ysypes : value.ysypes, wsypes : value.wsypes};
			});
			
			$("#yjzypepz_opt_dqpt").selectList({
				width:250,
				optionWidth:255,
				options:optionData
			}).change(function(e){
				var optionValue = $(this).attr('optionValue');

				$('#yjzypepz_opt_ysypes').val(optionData[optionValue].ysypes);
				$('#yjzypepz_opt_wsypes').val(optionData[optionValue].wsypes);
				
			});
			
			/*end*/
			
			
			/*end*/		
			
			$('#yjzypepz_opt_bcsqpes').blur(function(){
				
				var wsypes = $('#yjzypepz_opt_wsypes').val() - 0,
					bcsqpes = $(this).val() - 0;
					
				if(wsypes == ''){
					comm.alert({
						imgFlag : true,
						imgClass : 'tips_i',
						content : '请先选择地区平台！'	
					});
					return;	
				}
				
				if(bcsqpes != ''){
					
					if(bcsqpes <= wsypes){
						$('#yjzypepz_opt_sqhksypes').val(bcsqpes);
					}
					else{
						comm.alert({
							imgFlag : true,
							imgClass : 'tips_i',
							content : '您本次申请的配额数超过了未使用配额数！'	
						});
						return;	
					}
					
						
				}	
				else{
					$('#yjzypepz_opt_sqhksypes').val('');	
				}
			});
			
			//加载选号页面
			$('#zypesq_xh').off().click(function(e){
				$('#yjzypepz_xhDlg').html(yjzypepz_xhTpl);			 
				/*弹出框*/
				$( "#yjzypepz_xhDlg" ).dialog({
					title:"申请配额号段",
					width:"1000",
					height:"680",
					modal:true,
					closeIcon : true,
					buttons:{ 
						"确定":function(){
							 
							$( this ).dialog( "destroy" );	
						},
						"取消":function(){
							$( this ).dialog( "destroy" );
						}
					}
				});
				
				//初始画面
				$('#zr_number').css({'height': 580, 'width': 840}) ; 
		    	that.zr_number = zrender.init( document.getElementById('zr_number') ) ;   
		    	//加载表格
		    	that.createGrid();
				that.createText();
				that.createRectangle();
				
			});
			
		},
		createGrid : function(){
			  this.zr_number.clear();		
			  //创建表格
			  //rows
			  for (var i= 0 ; i<= 34 ; i++){						  	  	  
				  this.zr_number.addShape(new LineShape({
				      style: {
				         	xStart :  this.offset  ,  
				  			yStart : 17*i +this.offset  ,
				  			xEnd  : this.canvasWidth +this.offset  ,
				  			yEnd  : 17*i +this.offset  ,
				  			lineType: 'solid' ,
				  			strokeColor: '#000' ,
				            lineWidth: 1  
				      }
				  }));	
			  } 
			  //cols
			  for (var i = 0; i <=30 ; i++){
			  		var xStart ,  lineType = 'solid';
			  			 
			  			 xStart =(i!=30)? i*28 +this.offset: i*28 -this.offset; 
	 
			  		this.zr_number.addShape(new LineShape({
				      style: {
				         	xStart :  xStart ,   
				  			yStart :  this.offset  ,
				  			xEnd  :  xStart ,
				  			yEnd  : this.canvasHeight-2-this.offset ,
				  			lineType:  lineType,
				  			strokeColor: '#000' ,
				            lineWidth : 1  
				      }
				  }));					  	
			  } 
			 
		} ,
		
		//创建表格内文本框
		createText : function(){
	    	var self = this ;
	    	for ( var i = 0 ; i<= 33  ; i++ ){
	    		// 编辑区域
				var rectArr2 = [];
				for ( var j = 0 ; j <=  30 ; j++ ){
					   if (( j+1)+i*30 >999) {break;};	 
					   					
						var rect2 = new RectangleShape({
	                            //zlevel:0,
	                            style : {
	                                x : j*28+1 ,
	                                y : i*17+1 ,
	                                width : 26,
	                                height : 15,
	                                brushType : 'stroke',
	                                color : '#fff' ,
	                                strokeColor : '#fff',
	                                lineWidth : 1     ,
	                                text :( j+1)+i*30 ,
	                                textFont: '12px 宋体',
	                                textPosition:'inside',
	                                textAlign: 'center',
	                                textBaseline: 'middle',
	                                textColor: '#000'  
	                            },
					       		hoverable:false, 
					       		rowIndex : i ,
		                    	colIndex  : j  					       						       		 			       		
	                      });
	                    rectArr2.push(rect2); 
						this.zr_number.addShape( rect2); 
						
						
					}
					this.textArr[i] = rectArr2; 
			} 
	    } ,
		
		//	创建选择框 	
		createRectangle : function(){
			//创建选择框
			var self = this;
			self.rectangle.rect =new RectangleShape({
		            zlevel:100 ,
		            style : {
		                x : 0 ,
		                y : 0 ,
		                width : 0,
		                height :0,
		                brushType : 'stroke',  
		                strokeColor : 'blue' ,    
		                lineWidth : 1+self.offset                
		            },
		       		hoverable:false  		       		
	            }) ;	  	     
				self.zr_number.addShape(self.rectangle.rect);  
			    
			 
			 	$('#zr_number canvas:eq(0)').mousedown(function(e){			 	 
			 		//如果已选择，且鼠标在所选的某一矩形内				 	  
			 		var currPos = self.getPos( $('#zr_number canvas:eq(0)') , e) ;
			 		
			 			for(var i =0 ; i<= 33 ; i++){
		       				for (var j = 0 ; j <= 30 ; j++){ 	
		       						 if (! self.textArr[i][j] ) { break; };      						 
		       						 var  classP1 = { x : self.textArr[i][j].style.x , y : self.textArr[i][j].style.y  } ,
		       						 classP2 = { x : self.textArr[i][j].style.x +self.textArr[i][j].style.width , y : self.textArr[i][j].style.y  } ,		       						 
		       						 classP3 = { x : self.textArr[i][j].style.x  +self.textArr[i][j].style.width  ,y : self.textArr[i][j].style.y  +self.textArr[i][j].style.height } ,
		       						 classP4 = { x : self.textArr[i][j].style. x , y : self.textArr[i][j].style.y+self.textArr[i][j].style.height  } ,		       						 
		       						 oldColor  = 	self.textArr[i][j].style.color  ;	 
			       					 
			       				    if ( self.pointInPoly( currPos , [classP1,classP2,classP3,classP4 ] )  ) {	
			       				    	self.rectangle.moveFlag= 2 ;
			       				    	/*			       				    	 		       				     
			       				    	if ( self.textArr[i][j].selected ){
			       				    		//可拖动
			       				    		self.rectangle.moveFlag= 1   ;	 
			       				    	} else {
			       				    		//可框选
			       				    		self.rectangle.moveFlag= 2   ;
			       				    	}	*/		       				    	
			       						self.selectedClassRect =  self.textArr[i][j]  ;
			       						self.textArr[i][j].style.strokeColor = "red" ;			       						 
			       						$.extend(self.rectangle.rect.style ,{ 
						       				x: self.textArr[i][j].style.x ,
								            y: self.textArr[i][j].style.y ,
								            width: self.textArr[i][j].style.width  ,
								            height: self.textArr[i][j].style.height,
								            opacity : 1	 		       				
						       			}) ;			       						
			       					} else {	
			       						if ( e.button != 2 ){
			       							self.textArr[i][j].style.strokeColor  = '#fff' ;
			       							self.textArr[i][j].selected = false ;
			       						}	
			       					}	       				      						
		       				}         				
		       			}  	
		       			self.rectangle.x = currPos.x  ;
						self.rectangle.y = currPos.y   ;	
						if (e.button!=2){ 
			       			self.rectangle.selectCount = 0; 
			       		}
			 			self.zr_number.render(); 
			 	  
			 	}) ;
			 	
			 	$('#zr_number canvas:eq(0)').mousemove(function(e,o){
		 			if (!self.rectangle.moveFlag || e.button == 2){ return; }; 
		 			var currPos = self.getPos( $('#zr_number canvas:eq(0)') , e) ;
			 		var posX = currPos.x , posY = currPos.y  ,
			 			 deltaX =posX -  self.rectangle.x, deltaY =posY - self.rectangle.y ;				 		
			 		   if (self.rectangle.moveFlag == 2 ){
			 			//框选
			 			$.extend(self.rectangle.rect.style,{ 
		       				x: self.rectangle.x,
				            y: self.rectangle.y,
				            width: posX - self.rectangle.x  ,
				            height: posY - 	self.rectangle.y				       				
		       			});		       			
		       			//框选，改变选中的背景色
		       			for(var i =0 ; i<= 33 ; i++){
		       				for (var j = 0 ; j <= 30 ; j++){		
		       					if (! self.textArr[i][j] ) { break; };    				 
		       					var rectangleStart = { x: self.rectangle.rect.style.x  ,y:self.rectangle.rect.style.y    } ,
		       						 rectangleEnd   = { x: self.rectangle.rect.style.x + self.rectangle.rect.style.width ,y:self.rectangle.rect.style.y+ self.rectangle.rect.style.height } ,
		       						 classStart = { x : self.textArr[i][j].style.x ,y : self.textArr[i][j].style.y  } ,
		       						 classEnd  = { x : self.textArr[i][j].style.x  +self.textArr[i][j].style.width  ,y : self.textArr[i][j].style.y  +self.textArr[i][j].style.height } ,
		       						 oldColor = 	self.textArr[i][j].style.color;	
		       						
		       					var rectStyle =  self.textArr[i][j].style   ;		       					
		       					//var firstRect = { x : self.rectangle.rect.style.x  , y: self.rectangle.rect.style.y  ,width :  self.rectangle.rect.style.width , heigth : self.rectangle.rect.style.height } ;
		       					//var secondRect = {  x : self.textArr[i][j].style.x ,y: self.textArr[i][j].style.y, width:self.textArr[i][j].style.width ,height : self.rectangle.rect.style.height    } ;
		       					
		       				   if (self.isOverLap( rectangleStart , rectangleEnd , classStart ,  classEnd)     ) {	
		       						if (!self.textArr[i][j].selected){		       						 						 
			       						self.textArr[i][j].style.strokeColor = "red";
			       						self.textArr[i][j].selected = true;		
		       						}		       						       						 
		       					} else {
		       						if (self.textArr[i][j].selected){
		       							self.textArr[i][j].style.strokeColor  = '#fff' ;
		       							self.textArr[i][j].selected = false ;
		       						}
		       					}	       				      						
		       				}	       				
		       			}   
			 		}			 		
			 		self.zr_number.render();	
			 	});
			 	$('#zr_number canvas:eq(0)').mouseup(function(e){	
			 	 	
			 	 	var currPos = self.getPos( $('#zr_number canvas:eq(0)') , e)  ,
			 	 		  classP1 ,classP2 ,classP3, classP4	; 
			 	 		 console.log(self.rectangle.moveFlag);
		 
			 	 		for(var i =0 ; i< self.personCount ; i++){
		       				for (var j = 0 ; j < 31 ; j++){ 		       						 
		       						 classP1 =  { x : self.textArr[i][j].style.x , y : self.textArr[i][j].style.y  } ,
		       						 classP2 =  { x : self.textArr[i][j].style.x +self.textArr[i][j].style.width , y : self.textArr[i][j].style.y  } ,		       						 
		       						 classP3  = { x : self.textArr[i][j].style.x +self.textArr[i][j].style.width  ,y : self.textArr[i][j].style.y  +self.textArr[i][j].style.height } ,
		       						 classP4  = { x : self.textArr[i][j].style.x , y : self.textArr[i][j].style.y+self.textArr[i][j].style.height  }  ;
		       						//判断选中的数量
		       						if ( self.textArr[i][j].selected  ){
		       							self.rectangle.selectCount += 1 ;
		       						}
		       						 
			       				    if ( self.pointInPoly( currPos , [classP1,classP2,classP3,classP4 ] )  ) {	 
			       						var dd  = self.selectedClassRect ==  self.textArr[i][j]  ;
			       						if ( self.rectangle.moveFlag == 2 ){
			       							if (  self.selectedClassRect == self.textArr[i][j]  ) {
			       								//可框选标志
				       							self.textArr[i][j].selected = true ;			       							
				       						} ; 
			       						}       						
			       					} else {
			       						//self.textArr[i][j].selected = undefined ;
			       					}       				      						
		       				}         				
		       			}
  
			 		self.rectangle.moveFlag = 0 ;
			 		$.extend(self.rectangle.rect.style,{
	       				x: 0,
			            y: 0,
			            width: 0  ,
			            height: 0	,
			            opacity:0			       				
	       			});
	       				 
	       			self.zr_number.render();	       			 
	       			 
			 	});
		} ,
		//获取点
		getPos : function(canvas, evt) { 
		   	   var rect = canvas[0].getBoundingClientRect();  
			   return { 
				     x: evt.clientX - rect.left * (canvas[0].width / rect.width),
				     y: evt.clientY - rect.top * (canvas[0].height / rect.height)
			   };
		},
		//点在框内
		pointInPoly : function(pt, poly) {
		    for (var c = false, i = -1, l = poly.length, j = l - 1; ++i < l; j = i)
		        ((poly[i].y <= pt.y && pt.y < poly[j].y) || (poly[j].y <= pt.y && pt.y < poly[i].y))
		        && (pt.x < (poly[j].x - poly[i].x) * (pt.y - poly[i].y) / (poly[j].y - poly[i].y) + poly[i].x)
		        && (c = !c);
		    return c;
		},
		//框相交
		isOverLap : function(p1, p2, q1, q2) {
			var pMaxX = Math.max(p1.x, p2.x);
			var pMaxY = Math.max(p1.y, p2.y);
			var pMinX = Math.min(p1.x, p2.x);
			var pMinY = Math.min(p1.y, p2.y);
			var qMinX = Math.min(q1.x, q2.x);
			var qMinY = Math.min(q1.y, q2.y);
			var qMaxX = Math.max(q1.x, q2.x);
			var qMaxY = Math.max(q1.y, q2.y);
			return !(pMaxX<qMinX || pMaxY<qMinY || qMaxX<pMinX || qMaxY<pMinY);
			//如果矩形1中最大的X轴点都小于矩形2的最小的X轴，那肯定两个矩形不重叠，后同...
		},
		validate : function(){
			return comm.valid({
				formID : '#yjzypepz_optForm',
				left:200,
				rules : {
					yjzypepz_opt_bcsqpes : {
						required : true	
					},
					yjzypepz_opt_sqpehd : {
						required : true	
					}
				},
				messages : {
					yjzypepz_opt_bcsqpes : {
						required : '必填'
					},
					yjzypepz_opt_sqpehd : {
						required : '必填'
					}
				}
			});
		}
	}
}); 

 