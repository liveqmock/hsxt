define([
"text!jingyingTpl/scck.html" 
],function(scckTpl){
   var pages=arguments;
	var mainFn = {
		show:function(){
			//加载中间内容
			$(".operationsArea").html(pages[0])
			for(var i=1;i<pages.length-1;i++){
				$(".operationsArea").append(pages[i])
				$(".operationsInner").eq(i).hide();
				}
			
			$('#sel_sf').selectList({width:60,optionWidth:60});
			$('#sel_cs').selectList({width:60,optionWidth:60});
			$('#sel_qy').selectList({width:60,optionWidth:60});
			
			$('#sel_qylx').selectList({width:230,optionWidth:230});
			
			function tabs(elem,tabs){
				$(elem).children().click(function(){
					$(this).addClass("cur").siblings().removeClass("cur");
					$(tabs).hide().eq($(elem).children().index(this)).show();
					})
				};
			tabs('.tabs ul');
			
			//分页表格
			var testData =[
				{sn:1,dm:'哒哒服饰旗舰店',gs:'义乌市昶鸣贸易有限公司',zy:'服饰鞋包',dq:'广东深圳',yyzz:'图片',qylx:'服务公司',stdsl:'8',ccrq:'2014-12-12',sqsj:'2014-12-12 12:20',zt:'申请重开' } ,
				{sn:2,dm:'哒哒服饰旗舰店',gs:'义乌市昶鸣贸易有限公司',zy:'服饰鞋包',dq:'广东深圳',yyzz:'图片',qylx:'服务公司',stdsl:'8',ccrq:'2014-12-12',sqsj:'2014-12-12 12:20',zt:'申请重开' } ,
				{sn:1,dm:'哒哒服饰旗舰店',gs:'义乌市昶鸣贸易有限公司',zy:'服饰鞋包',dq:'广东深圳',yyzz:'图片',qylx:'服务公司',stdsl:'8',ccrq:'2014-12-12',sqsj:'2014-12-12 12:20',zt:'申请重开' } ,
				{sn:2,dm:'哒哒服饰旗舰店',gs:'义乌市昶鸣贸易有限公司',zy:'服饰鞋包',dq:'广东深圳',yyzz:'图片',qylx:'服务公司',stdsl:'8',ccrq:'2014-12-12',sqsj:'2014-12-12 12:20',zt:'申请重开' } ,
				{sn:1,dm:'哒哒服饰旗舰店',gs:'义乌市昶鸣贸易有限公司',zy:'服饰鞋包',dq:'广东深圳',yyzz:'图片',qylx:'服务公司',stdsl:'8',ccrq:'2014-12-12',sqsj:'2014-12-12 12:20',zt:'申请重开' } ,
				{sn:2,dm:'哒哒服饰旗舰店',gs:'义乌市昶鸣贸易有限公司',zy:'服饰鞋包',dq:'广东深圳',yyzz:'图片',qylx:'服务公司',stdsl:'8',ccrq:'2014-12-12',sqsj:'2014-12-12 12:20',zt:'申请重开' } ,
				{sn:1,dm:'哒哒服饰旗舰店',gs:'义乌市昶鸣贸易有限公司',zy:'服饰鞋包',dq:'广东深圳',yyzz:'图片',qylx:'服务公司',stdsl:'8',ccrq:'2014-12-12',sqsj:'2014-12-12 12:20',zt:'申请重开' } ,
				{sn:2,dm:'哒哒服饰旗舰店',gs:'义乌市昶鸣贸易有限公司',zy:'服饰鞋包',dq:'广东深圳',yyzz:'图片',qylx:'服务公司',stdsl:'8',ccrq:'2014-12-12',sqsj:'2014-12-12 12:20',zt:'申请重开' } 
				
			];
			
			var gridObj , self =this;
			 
			gridObj = $.fn.bsgrid.init('tableDetail', {				 
				//url : comm.domainList['local']+ comm.UrlList["jfzhmxcx"] , 
				pageSize: 10 , 
				stripeRows: true,  //行色彩分 
				displayBlankRows: false ,   //显示空白行
			  	localData: testData,
			  	renderImg:function(record, rowIndex, colIndex, options){
			  		if (colIndex==1){
			  			var idInt = parseInt($.trim(gridObj.getRecordIndexValue(record, 'sn')));
			  			var dlHtml = '<dl class="imgWord table-msg">' +
						                    '<dt><img src='+idInt+'.jpg style="width:70px;height:70px;"/></dt>' +
						                  	'<dd>' +  gridObj.getRecordIndexValue(record, 'dm') +  ' </dd>' +
						                    '<dd>名称：'+ gridObj.getRecordIndexValue(record, 'gs') +'</dd>' +
						                    '<dd>主营：'+ gridObj.getRecordIndexValue(record, 'zy') +'</dd>' +
						                    '<dd>地区：'+ gridObj.getRecordIndexValue(record, 'dq') +'</dd>' +
						                  '</dl>' ;
			  			
			  			
        				return dlHtml ; 
			  		} else if(colIndex==2){
			  			var idInt = parseInt($.trim(gridObj.getRecordIndexValue(record, 'sn')));
        				return '<img src="../images/' + ((idInt % 3) == 0 ? 3 : (idInt % 3)) + '.jpg" style="width:70px;height:70px;" />'; 
			  		}
			  		
			  	} ,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a data-type="'+ gridObj.getCellRecordValue(rowIndex,2) +'" >申请重开</a>').click(function(e) {
				 		
							 
						    
						});
						return   link1 ;
					}  
				} 			
			} );	
			
			
			/*--------------弹出框------------------*/
			$( ".dlg4" ).click(function() {
			   $( "#dlg4" ).dialog({
				  show: true,
				  modal: true,
				  title:"申请开通/暂停",
				  width:550,	  
				  buttons: {
					同意: function() {$( this ).dialog( "destroy" );},
					拒绝: function() {$( this ).dialog( "destroy" );}		
					}
				});	
			$(".mallMsg .fl").height($(".mallMsg .fl").parent(':visible').height());
			if($("#step").length==0){
				var stepHtml="<div id='step' style='width:550px; overflow:hidden; position:absolute; top:0; left:0'><div style='float:left; background-color:#333; width:50px; height:50px; line-height:50px; text-align:center; color:#fff; opacity:0.4; font:22px/50px 宋体;'><</div><div style='float:right; background-color:#333; width:50px; height:50px; line-height:50px; text-align:center; color:#fff; opacity:0.4; font:22px/50px 宋体'>></div></div>"
				$(".popoup_warp:visible").parent().append(stepHtml).children('#step').hide();		
				}
				
			$('#step').css('top',($('#dlg4').height()-$('#step').height())/2);
			
			$(".popoup_warp:visible").parent().hover(
			function(){$('#step').show()},
			function(){$('#step').hide()}
			)
			
			detailSwitch();	
			});
			function detailSwitch(){
								
			var itemNum=$(".popoup_warp").size(),
				activeID=$(".popoup_warp").index($(".popoup_warp:visible")),
				nextID=0,
				previousID=itemNum;
				function stepID(){
				nextID=activeID<itemNum-1?activeID+1:activeID;
				previousID=activeID>0?activeID-1:activeID;
								
				}
				$('#step div:last').click(function(event){
					
					stepID();			
					$(".popoup_warp").eq(activeID).hide();
					$(".popoup_warp").eq(nextID).show();
					activeID=nextID;
					event.stopPropagation();
					
					})
				$('#step div:first').click(function(event){
					stepID();
					$(".popoup_warp").eq(activeID).hide();
					$(".popoup_warp").eq(previousID).show();
					activeID=previousID;
					event.stopPropagation();
					})				
			}
			
			/*--------------弹出框结束------------------*/
			
			$(".stdsh").click(function(){
				$("#subNav_2_02").trigger("click");	
			})
			$(".spsh").click(function(){
				$("#subNav_2_07").trigger("click");	
			})
			},
		hide:function(){
			//清空中间内容） 
			$(".operationsArea").empty();	
		}
		
	};		
	return mainFn;
});
