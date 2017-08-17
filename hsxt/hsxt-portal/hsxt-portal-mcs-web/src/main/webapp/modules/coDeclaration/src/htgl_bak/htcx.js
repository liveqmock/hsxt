define(['text!coDeclarationTpl/htgl/htcx.html'], function(htcxTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(htcxTpl));
			
			/*表格数据模拟*/
			var json= [{
							"th_1":"05003240000",
							"th_2":"苏宁电器华强北店",
							"th_3":"张三",
							"th_4":"13800138000",
							"th_5":"托管企业",
							"th_6":"2015-06-27 10:40:05",
							"th_7":"未打印"
						},
						{
							"th_1":"05003240000",
							"th_2":"苏宁电器华强北店",
							"th_3":"李四",
							"th_4":"13888138000",
							"th_5":"成员企业",
							"th_6":"2015-08-17 09:42:45",
							"th_7":"未打印"
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
						var link1 =  $('<a id="'+ gridObj.getRecordIndexValue(record, 'ID') +'" >打印</a>').click(function(e) {
							//$('#dialog').dialog({autoOpen: true }) ;
							this.daYin();
							
						}.bind(this) ) ;
						return   link1 ;
					}.bind(this)
					
				} 			
			});
			
			/*end*/	
		},
		daYin : function(){
			
		}	
	}	
});