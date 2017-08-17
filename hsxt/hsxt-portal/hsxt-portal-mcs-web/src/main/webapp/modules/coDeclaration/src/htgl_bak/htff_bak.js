define(['text!coDeclarationTpl/htgl/htff.html',
		'text!coDeclarationTpl/htgl/htff_ffht.html',
		'text!coDeclarationTpl/htgl/htff_htffls.html'
		], function(htffTpl, htff_ffhtTpl, htff_htfflsTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(htffTpl));	
			
			/*下拉列表*/
			$("#ff_state").selectList({
				width: 130,
				options:[
					{name:'已发放',value:'100'},
					{name:'未发放',value:'101'}
				]
			});
			/*end*/
			
			/*表格数据模拟*/
			var json= [{
							"th_1":"05003240000",
							"th_2":"苏宁电器华强北店",
							"th_3":"张三",
							"th_4":"13888888888",
							"th_5":"托管企业",
							"th_6":"2015-06-27 10:40:05",
							"th_7":"已发放"
						},
						{
							"th_1":"05003240000",
							"th_2":"国美电器华强北店",
							"th_3":"李四",
							"th_4":"13888888888",
							"th_5":"成员企业",
							"th_6":"2015-08-77 16:20:05",
							"th_7":"未发放"
						}];	

			var gridObj;
			 
			gridObj = $.fn.bsgrid.init('searchTable', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json ,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						var obj = gridObj.getRecord(rowIndex);
						var link1 =  $('<a>合同发放历史</a>').click(function(e) {
	
							this.htffls(obj);
							
						}.bind(this) ) ;
						return   link1 ;
					}.bind(this),
					
					edit : function(record, rowIndex, colIndex, options){
						var obj = gridObj.getRecord(rowIndex);
						if(gridObj.getColumnValue(rowIndex, 'th_7') == '未发放'){
							var link2 =  $('<a>发放合同</a>').click(function(e) {
	
								this.ffht(obj);
								
							}.bind(this) ) ;
						}
						
						return   link2 ;
					}.bind(this)
					
				} 			
			});
			
			/*end*/	
		},
		htffls : function(obj){
			comm.liActive_add($('#htffls'));
			$('#busibox').html(_.template(htff_htfflsTpl, obj));
			
			/*表格数据模拟*/
			var json= [{
							"th_1":"张三",
							"th_2":"2015-08-31 16:40:00"
						},
						{
							"th_1":"李四",
							"th_2":"2015-08-31 16:40:00"
						}];	
	
			var gridObj;
			
			gridObj = $.fn.bsgrid.init('searchTable', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json ,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						var obj = gridObj.getRecord(rowIndex); 
						var link1 = $('<a>查看</a>').click(function(e) {
							if(colIndex == 3){
								this.chaKan_1(obj);
							}
							else if(colIndex == 4){
								this.chaKan_2(obj);	
							}
							
						}.bind(this) ) ;
						
						return link1;
					}.bind(this)
				}
				
			});
			
			/*end*/	
			
			$('#back_ffht').triggerWith('#htff');
		},
		ffht : function(obj){
			comm.liActive_add($('#ffht'));
			$('#busibox').html(_.template(htff_ffhtTpl, obj));
			$('#ffht_cancel').triggerWith('#htff');
		},
		chaKan_1 : function(){},
		chaKan_2 : function(){}	
	}	
});