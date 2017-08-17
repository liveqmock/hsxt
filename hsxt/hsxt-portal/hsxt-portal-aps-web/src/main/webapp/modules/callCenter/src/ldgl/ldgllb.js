//未接来电
define(['text!callCenterTpl/ldgl/ldgllb.html'], function(ldgllbTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(ldgllbTpl));
			var self=this;
			/*下拉列表事件*/
			/*$("#callState").selectList({
				options:[
					{name:'已接来电',value:'1'},
					{name:'未接来电',value:'2'},
					{name:'正在通话',value:'3'}
				]
			});*/
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
			if (navigator.userAgent.indexOf("MSIE 8.0") > 0) {
				//未接来电列表
				Test.attachEvent("JS_Evt_GetMissedCallsList_Complete",GetMissedCallsList_Complete);
			}
			//未接来电列表
			function GetMissedCallsList_Complete(ret,id,jsons){
				if(ret==1){
					//console.info("未接来电 "+jsons);
					if($.parseJSON(jsons).resultCode==0){
						$("#searchTable").find("tbody").children().remove();
						$("#searchTable_pt_outTab").attr('style','min-width: 666px; width: 1030px;');
						$("#searchTable_pt_outTab").find('td').remove();
						$("#searchTable_pt_outTab").find('tr').append("<td style='text-align:center'>未找到符合条件的数据</td>");
					}else{
						var gridObj = $.fn.bsgrid.init('searchTable', {
							pageSizeSelect: true ,
							pageSize: 10 ,
							stripeRows: true,  //行色彩分隔
							displayBlankRows: false ,   //显示空白行
							localData:comm.isEmpty($.parseJSON(jsons).MissedCallsList)?null:$.parseJSON(jsons).MissedCallsList
						});
						//移除排序字段栏出现的无效箭头
						$("#ldgllb_startTime").find("a").remove();
					}
				}
			}

			$.fn.bsgrid.init('searchTable', {
				localData: null
			});
			//移除排序字段栏出现的无效箭头
			$("#ldgllb_startTime").find("a").remove();
			
			$("#wjld_search_btn").click(function(){
				var startTime=$("#timeRange_start").val();
				var endTime=$("#timeRange_end").val();
				var zx=$("#wjld_zx_input").val();
				Test.JS_GetMissedCallsList(startTime,endTime,zx);
			});


			/*end*/
		}
	}
});