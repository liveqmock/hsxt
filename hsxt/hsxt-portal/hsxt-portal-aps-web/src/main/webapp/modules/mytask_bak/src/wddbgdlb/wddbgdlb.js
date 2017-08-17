define(['text!mytaskTpl/wddbgdlb/wddbgdlb.html',
		'text!mytaskTpl/wddbgdlb/wddbgdlb_ck.html'
		], function(wddbgdlbTpl, wddbgdlb_ckTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(wddbgdlbTpl)).append(wddbgdlb_ckTpl);	
			
			var self = this;
			
			/*下拉列表*/
			$("#quickDate").selectList({
				options:[
					{name:'今天',value:'100'},
					{name:'本周',value:'101'},
					{name:'本月',value:'102'}
				]
			}); 
			$("#businessType").selectList({
				options:[
					{name:'xxx',value:'001'},
					{name:'xxx',value:'002'},
					{name:'xxx',value:'003'}
				]	
			});
			/*end*/
			
			/*日期控件*/
			$("#timeRange_start").datepicker({dateFormat:'yy-mm-dd'});
			$("#timeRange_end").datepicker({dateFormat:'yy-mm-dd'});
			/*end*/
			
			/*表格请求数据*/
			var json= [{
							"th_1":"11111111",
							"th_2":"2015-08-12 12:17:28",
							"th_3":"xxx",
							"th_4":"待办中",
							"th_5":"xxx",
							"th_6":"购买工具"
						},
						{
							"th_1":"11111111",
							"th_2":"2015-08-12 12:17:28",
							"th_3":"xxx",
							"th_4":"待办中",
							"th_5":"xxx",
							"th_6":"申报服务公司"
						}];
						
			var gridObj, self=this;
			 
			gridObj = $.fn.bsgrid.init('searchTable', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔
				displayBlankRows: false ,   //显示空白行 
				localData:json ,
				operate : {	
					edit : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a id="'+ gridObj.getRecordIndexValue(record, 'ID') +'" >查看</a>').click(function(e) {
							//$('#dialog').dialog({autoOpen: true }) ;
							this.chakang();
							
						}.bind(this) ) ;
						return   link1 ;
					}.bind(this),
					
					detail: function(record, rowIndex, colIndex, options){
						var link1 =  $('<a index="'+ gridObj.getRecordIndexValue(record, 'ID') +'" >办理</a>').click(function(e) {							 
							//$('#dialog').dialog({autoOpen: true }) ;
							this.banli();
						}.bind(this)); 						
						return   link1  ;
					}.bind(this)

					
				} 			
			});
			
			/*end*/
			
			
			
		},
		
		chakang : function(){
			$('#wddbgdlbTpl').addClass('none');
			$('#wddbgdlb_ckTpl').removeClass('none');
			comm.liActive_add($('#cknr'));
			$('#back_wddbgdlb').triggerWith('#wddbgdlb');
		},
		banli : function(){
			alert('办理');
		}
	}	
});