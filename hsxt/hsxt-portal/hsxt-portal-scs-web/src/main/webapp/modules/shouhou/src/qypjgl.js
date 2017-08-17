define(['text!shouhouTpl/qypjgl.html'], function(qypjglTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(qypjglTpl));	
			
			/*下拉列表*/
			$("#physicalStore").selectList({
				width : 130,
				optionWidth : 135,
				options:[
					{name:'维也纳华强店',value:'1'},
					{name:'维也纳华富店',value:'2'}
				]
			}).change(function(e){
			});
			/*end*/
			
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
			
			/*表格数据模拟*/
			var json = [{
							"th_1":"好评",
							"th_2":"面料不错",
							"th_3":"评价方未及时作出评价时，系统默认好评！",
							"th_4":"2015.01.12  12:00:02",
							"th_5":"您可以回复真实感受！",
							"th_6":"2015.01.12  12:00:02",
							"th_7":"你们的产品外观真不是很美观！",
							"th_8":"2015.01.12  12:00:02",
							"th_9":"男童帽子-男童套头帽-全棉儿童帽子……",
							"th_10":"79元",
							"th_11":"123456789",
							"th_12":"2015.01.10",
							"th_13":"会下蛋的小公鸡"
						},
						{
							"th_1":"差评",
							"th_2":"面料不错",
							"th_3":"评价方未及时作出评价时，系统默认好评！",
							"th_4":"2015.01.12  12:00:02",
							"th_5":"",
							"th_6":"",
							"th_7":"",
							"th_8":"",
							"th_9":"男童帽子-男童套头帽-全棉儿童帽子……",
							"th_10":"79元",
							"th_11":"123456789",
							"th_12":"2015.01.10",
							"th_13":"会下蛋的小公鸡"
						},
						{
							"th_1":"好评",
							"th_2":"面料不错",
							"th_3":"评价方未及时作出评价时，系统默认好评！",
							"th_4":"2015.01.12  12:00:02",
							"th_5":"您可以回复真实感受！",
							"th_6":"2015.01.12  12:00:02",
							"th_7":"",
							"th_8":"",
							"th_9":"男童帽子-男童套头帽-全棉儿童帽子……",
							"th_10":"79元",
							"th_11":"123456789",
							"th_12":"2015.01.10",
							"th_13":"会下蛋的小公鸡"
						},
						{
							"th_1":"好评",
							"th_2":"面料不错",
							"th_3":"评价方未及时作出评价时，系统默认好评！",
							"th_4":"2015.01.12  12:00:02",
							"th_5":"您可以回复真实感受！",
							"th_6":"2015.01.12  12:00:02",
							"th_7":"你们的产品外观真不是很美观！",
							"th_8":"2015.01.12  12:00:02",
							"th_9":"男童帽子-男童套头帽-全棉儿童帽子……",
							"th_10":"79元",
							"th_11":"123456789",
							"th_12":"2015.01.10",
							"th_13":"会下蛋的小公鸡"
						},
						{
							"th_1":"好评",
							"th_2":"面料不错",
							"th_3":"评价方未及时作出评价时，系统默认好评！",
							"th_4":"2015.01.12  12:00:02",
							"th_5":"您可以回复真实感受！",
							"th_6":"2015.01.12  12:00:02",
							"th_7":"你们的产品外观真不是很美观！",
							"th_8":"2015.01.12  12:00:02",
							"th_9":"男童帽子-男童套头帽-全棉儿童帽子……",
							"th_10":"79元",
							"th_11":"123456789",
							"th_12":"2015.01.10",
							"th_13":"会下蛋的小公鸡"
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
						var tdHtml = null;
						if(colIndex == 4){
							tdHtml = $('<a><span class="btn_reply btn_bg submit">删除</span></a>').click(function(e){
								this.shanChu();	
							}.bind(this));
						}
						else if(colIndex == 2){
							if(gridObj.getColumnValue(rowIndex, 'th_3') != '' && gridObj.getColumnValue(rowIndex, 'th_4') != ''){
								tdHtml = '<div class="pllb_liboeder tl ml20">【评价】' + gridObj.getColumnValue(rowIndex, 'th_3') + '<br />【' + gridObj.getColumnValue(rowIndex, 'th_4') + '】</div>';
							}
							if(gridObj.getColumnValue(rowIndex, 'th_5') != '' && gridObj.getColumnValue(rowIndex, 'th_6') != ''){
								tdHtml = tdHtml + '<div class="pllb_liboeder red tl ml20">【回复】' + gridObj.getColumnValue(rowIndex, 'th_5') + '<br />【' + gridObj.getColumnValue(rowIndex, 'th_6') + '】</div>';
							}
							if(gridObj.getColumnValue(rowIndex, 'th_7') != '' && gridObj.getColumnValue(rowIndex, 'th_8') != ''){
							tdHtml = tdHtml + '<div class="pllb_liboeder tl ml20">【追评】' + gridObj.getColumnValue(rowIndex, 'th_7') + '<br />【' + gridObj.getColumnValue(rowIndex, 'th_8') + '】</div>';	
							}
						}
						else if(colIndex == 3){
							tdHtml = '<div class="tl lh200 ml20">' + gridObj.getColumnValue(rowIndex, 'th_9') + '<br /><span class="red">' + gridObj.getColumnValue(rowIndex, 'th_10') + '</span><br />订单编号：' + gridObj.getColumnValue(rowIndex, 'th_11') + '<br />订单日期：' + gridObj.getColumnValue(rowIndex, 'th_12') + '<br />昵称：' + gridObj.getColumnValue(rowIndex, 'th_13') + '</div>';	
						}
						
						return tdHtml;
					}.bind(this)
				}
				
			});
			
			/*end*/	
		},
		shanChu : function(){
				
		}
	}
		
})