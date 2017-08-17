define(['text!callCenterTpl/zxztjk/rcap.html'], function(rcapTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(rcapTpl));
			
			/*下拉列表事件*/
			$("#workingGroupName").selectList({
				width:120,
				options:[
					{name:'业务组1',value:'1'},
					{name:'业务组2',value:'2'},
					{name:'业务组3',value:'3'}
				]
			});
			/*end*/	
			
			/*日期控件*/
			$( "#sb_time" ).datepicker({dateFormat:'yy-mm-dd'});
			/*end*/
			
			/*表格请求数据*/
			var json= [{
							"th_1":"业务组1",
							"th_2":"张三",
							"th_3":"早班（8:00-17:00）",
							"th_4":"2015-08-13",
							"th_5":"否"
						},
						{
							"th_1":"业务组2",
							"th_2":"李四",
							"th_3":"晚班（17:00-22:00）",
							"th_4":"2015-08-14",
							"th_5":"否"
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
					edit: function(record, rowIndex, colIndex, options){
						var link2 =  $('<a index="'+ gridObj.getRecordIndexValue(record, 'ID') +'" >修改</a>').click(function(e) {							 
							//$('#dialog').dialog({autoOpen: true }) ;
							this.modify();
						}.bind(this)); 						
						return  link2  ;
					}.bind(this)
					
					
				} 			
			});
			
			/*end*/
		}	,
		
		modify : function(option){
			
			/*弹出框*/
			$( "#modify_content" ).dialog({
				title:"坐席人员信息修改",
				modal:true,
				width:"400",
				height:"320",
				buttons:{ 
					"确定":function(){
						$( "#modify_content" ).dialog( "destroy" );
					},
					"取消":function(){
						 $( "#modify_content" ).dialog( "destroy" );
					}
				}
		
			  });
			/*end*/	
			
			/*下拉列表事件*/
			$("#workingGroupName_pop").selectList({
				width:180,
				options:[
					{name:'业务组1',value:'1'},
					{name:'业务组2',value:'2'},
					{name:'业务组3',value:'3'}
				]
			});
			
			$("#bcmc").selectList({
				width:180,
				options:[
					{name:'早班（8:00-17:00）',value:'1'},
					{name:'晚班（17:00-22:00）',value:'2'}
				]
			});
			
			/*end*/	
			
			/*日期控件*/
			$( "#sbsj" ).datepicker({dateFormat:'yy-mm-dd'});
			/*end*/

		}	
	}	
});