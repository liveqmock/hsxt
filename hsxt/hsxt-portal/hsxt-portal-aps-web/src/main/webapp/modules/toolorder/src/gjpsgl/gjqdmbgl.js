define(['text!toolorderTpl/gjpsgl/gjqdmbgl.html',
		'text!toolorderTpl/gjpsgl/gjqdmbgl_xzmb_dialog.html',
		'text!toolorderTpl/gjpsgl/gjqdmbgl_ck_dialog.html',
		'text!toolorderTpl/gjpsgl/gjqdmbgl_xg_dialog.html'
		], function(gjqdmbglTpl, gjqdmbgl_xzmb_dialogTpl, gjqdmbgl_ck_dialogTpl, gjqdmbgl_xg_dialogTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(gjqdmbglTpl));
			
			/*下拉列表*/
			$("#templetState").selectList({
				options:[
					{name:'已启用',value:'1'},
					{name:'待启用',value:'2'}
				]
			});
			/*end*/	
			
			/*表格数据模拟*/
			var json= [{
							"th_1":"xxx",
							"th_2":"新增申购",
							"th_3":"已启用"
						},
						{
							"th_1":"xxx",
							"th_2":"申报申购",
							"th_3":"待启用"
						}];	
	
			var gridObj;
			
			gridObj = $.fn.bsgrid.init('searchTable', {				 
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json ,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						var link1 = $('<a>查看</a>').click(function(e) {
							var obj = gridObj.getRecord(rowIndex);
							this.chaKan(obj);
							
						}.bind(this) ) ;
						
						return link1;
					}.bind(this),
					
					edit : function(record, rowIndex, colIndex, options){
						var link2 = $('<a>修改</a>').click(function(e) {
							var obj = gridObj.getRecord(rowIndex);
							this.xiuGai(obj);
							
						}.bind(this) ) ;
						
						return link2;
					}.bind(this),
					
					del : function(record, rowIndex, colIndex, options){
						var link3 = null;
						var obj = gridObj.getRecord(rowIndex);
						if(gridObj.getColumnValue(rowIndex, 'th_3') == '已启用'){
							link3 = $('<a>停用</a>').click(function(e) {
								this.tingYong(obj);
								
							}.bind(this) ) ;	
						}
						else if(gridObj.getColumnValue(rowIndex, 'th_3') == '待启用'){
							link3 = $('<a>启用</a>').click(function(e) {
								this.qiYong(obj);
								
							}.bind(this) ) ;	
						}
						
						return link3;
					}.bind(this)
				}
				
			});
			
			/*end*/	
			
			$('#xzmb_btn').click(function(){
				$('#dialogBox_xz').html(_.template(gjqdmbgl_xzmb_dialogTpl));
				
				/*弹出框*/
				$( "#dialogBox_xz" ).dialog({
					title:"新增模版",
					width:"1000",
					modal:true,
					buttons:{ 
						"确定":function(){
							$( this ).dialog( "destroy" );
						},
						"取消":function(){
							 $( this ).dialog( "destroy" );
						}
					}
				});
				/*end*/	
				
				/*下拉列表*/
				$("#sysglx_xz").selectList({
					width:280,
					optionWidth:280, 
					options:[
						{name:'申报申购',value:'1'},
						{name:'新增申购',value:'2'}
					]
				});
				/*end*/
				
				/*日期控件*/
				$( "#time" ).datepicker({dateFormat:'yy-mm-dd'});
				/*end*/
	
			});
		},
		chaKan : function(obj){
			$('#dialogBox_ck').html(_.template(gjqdmbgl_ck_dialogTpl, obj));
			
			/*弹出框*/
			$( "#dialogBox_ck" ).dialog({
				title:"查看模版",
				width:"1000",
				modal:true,
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
				
		},
		xiuGai : function(obj){
			$('#dialogBox_xg').html(_.template(gjqdmbgl_xg_dialogTpl, obj));
			
			/*弹出框*/
			$( "#dialogBox_xg" ).dialog({
				title:"修改模版",
				width:"1000",
				modal:true,
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
			
			/*下拉列表*/
			$("#sysglx_xg").selectList({
				width:280,
				optionWidth:280, 
				options:[
					{name:'申报申购',value:'1',selected:true},
					{name:'新增申购',value:'2'}
				]
			});
			/*end*/
			
			/*日期控件*/
			$( "#time" ).datepicker({dateFormat:'yy-mm-dd'});
			/*end*/	
		},
		tingYong : function(){
			comm.alert({
				imgClass: 'tips_ques' ,
				content : '您确定停用此模版？'	
			});	
		}	,
		qiYong : function(){
			comm.alert({
				imgClass: 'tips_ques' ,
				content : '您确定启用此模版？'	
			});		
		}
	}	
});