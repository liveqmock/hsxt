define(['text!coDeclarationTpl/htgl/htgz.html'], function(htgzTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(htgzTpl));	
			
			/*下拉列表*/
			$("#gz_state").selectList({
				width: 130,
				options:[
					{name:'已盖章',value:'100'},
					{name:'未盖章',value:'101'},
					{name:'须重新盖章',value:'102'}
				]
			});
			/*end*/
			
			/*表格数据模拟*/
			var json= [{
							"th_1":"05003240001",
							"th_2":"服务公司",
							"th_3":"TCL集团",
							"th_4":"张三",
							"th_5":"13800138001",
							"th_6":"2015-06-27 10:40:05",
							"th_7":"已盖章"
						},
						{
							"th_1":"05003240002",
							"th_2":"托管企业",
							"th_3":"海尔集团",
							"th_4":"李四",
							"th_5":"13800138002",
							"th_6":"2015-06-27 10:40:05",
							"th_7":"未盖章"
						},
						{
							"th_1":"05003240003",
							"th_2":"成员企业",
							"th_3":"创维集团",
							"th_4":"王五",
							"th_5":"13800138003",
							"th_6":"2015-06-27 10:40:05",
							"th_7":"已盖章"
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
						var link1 =  $('<a>预览</a>').click(function(e) {
							this.yuLan();
							
						}.bind(this) ) ;
						return   link1 ;
					}.bind(this),
					
					edit : function(record, rowIndex, colIndex, options){
						var link2 =  null;
						if(gridObj.getColumnValue(rowIndex, 'th_7') == '未盖章'){
							link2 = $('<a>盖章</a>').click(function(e) {
								this.gaiZhang();
								
							}.bind(this) ) ;
						}
						else if(gridObj.getColumnValue(rowIndex, 'th_7') == '已盖章'){
							link2 = $('<a>重新盖章</a>').click(function(e) {
								this.cxgz();
								
							}.bind(this) ) ;
						}
						
						return   link2;
					}.bind(this)
					
				} 			
			});
			
			/*end*/	
		},
		yuLan : function(){
			
		},
		gaiZhang : function(){
			
		},
		cxgz : function(){
			
		}
	}	
});