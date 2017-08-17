define(['text!callCenterTpl/ldgl/zzthld.html'], function(zzthldTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(zzthldTpl));	
			if (navigator.userAgent.indexOf("MSIE 8.0") > 0) {
				//正在通话列表
				Test.attachEvent("JS_Evt_GetTalkList_Complete",GetTalkList_Complete);
			}
			var gridObj;
			//正在通话
			function GetTalkList_Complete(ret,id,json){
				if(!location.host.indexOf("192.168.41.54")) {
					//console.info("正在通话列表数据：" + json);
				}
				if(ret==1) {
					if($.parseJSON(json).resultCode==0){
						$("#searchTable").find("tbody").children().remove();
						$("#searchTable_pt_outTab").find('td').remove();
						$("#searchTable_pt_outTab").attr('style','min-width: 666px; width: 1030px;');
						$("#searchTable_pt_outTab").find('tr').append("<td style='text-align:center'>未找到符合条件的数据</td>");
					}else{
						gridObj = $.fn.bsgrid.init('searchTable', {
							//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
							// autoLoad: false,
							pageSizeSelect: true,
							pageSize: 10,
							stripeRows: true,  //行色彩分隔
							displayBlankRows: false,   //显示空白行
							localData: comm.isEmpty($.parseJSON(json).TalkList) ? null : $.parseJSON(json).TalkList,
							operate: {
								detail: function (record, rowIndex, colIndex, options) {
									if (colIndex == 4) {
										return "正在通话";
									}
									//座席无法监听自己的通话
									var operName = comm.getCookie('operName');
									if(record.Agent_Name!=operName){
										var link1 = $('<a data-type="play">监听</a>').click(function (e) {
											//开始监听
											var result = Test.JS_MonitorUser(record.Agent_Num, Test.JS_GetUserNumByMac(Test.JS_GetMac()));
											if (result == 1) {
												comm.alert("开始监听");
												$("#searchTable a[data-type=play]").addClass("none");
												$("#searchTable a[data-type=stop]").removeClass("none");
											} else {
												comm.alert("监听失败");
											}
										});
										return link1;
									 }	
								},
								add: function (record, rowIndex, colIndex, options) {
									//座席无法监听自己的通话
									var operName = comm.getCookie('operName');
									if(record.Agent_Name!=operName){
										var link1 = $('<a class="none" data-type="stop">停止</a>').click(function (e) {
											//结束监听
											result = Test.JS_StopMonitorUser(record.Agent_Num, Test.JS_GetUserNumByMac(Test.JS_GetMac()));
											if (result == 1) {
												comm.alert("监听结束");
												$("#searchTable a[data-type=stop]").addClass("none");
												$("#searchTable a[data-type=play]").removeClass("none");
											} else {
												comm.alert("停止监听失败");
											}
										});
										return link1;
									}
								}
							}
						});
						//移除排序字段栏出现的无效箭头
						$("#zzthld_startTime").find("a").remove();
				}
				}
			}
			$.fn.bsgrid.init('searchTable', {
				localData: null
			});
			//移除排序字段栏出现的无效箭头
			$("#zzthld_startTime").find("a").remove();

			$("#zzth_search_btn").click(function(){
				Test.JS_GetTalkList($("#zzth_zx_input").val());
			});
			/*end*/
		}
	}	
});