define(['text!accountManageTpl/jfyfkzh/yfzfkctj.html'], function(tpl){
	return yfzfkctj = {
		showPage : function(){
			$('#busibox').html(tpl);
			
			searchTable = null;
			
			/*日期控件*/
			$("#scpoint_dj_beginDate").datepicker({dateFormat:'yy-mm-dd'});
			$("#scpoint_dj_endDate").datepicker({dateFormat:'yy-mm-dd'});
			
			//按钮事件
			$('#scpoint_dj_searchBtn').click(function(){
				if (!yfzfkctj.validateData()) {
					return;
				}
				
				searchTable = $.fn.bsgrid.init('scpoint_dj_result_table', {
					//url: '',
					localData: [{"rq":"2014-08","kcs":"100.00","cds":"100.00","sjkcs":"194.20"},{"rq":"2014-08","kcs":"100.00","cds":"100.00","sjkcs":"194.20"},{"rq":"2014-08","kcs":"100.00","cds":"100.00","sjkcs":"194.20"},{"rq":"2014-08","kcs":"100.00","cds":"100.00","sjkcs":"194.20"}],
					otherParames: {
						beginDate : $("#scpoint_dj_beginDate").val(),
						endDate : $("#scpoint_dj_endDate").val()
					},
					//不显示空白行
					displayBlankRows: false,
					//不显示无页可翻的提示
					pageIncorrectTurnAlert: false,
					//隔行变色
					stripeRows: true,
					//不显示选中行背景色
					rowSelectedColor: false,
					pageSize: 10
				});
			});
			//这一行需要去掉，此处用于显示数据
			$('#scpoint_dj_searchBtn').click();
		},
		validateData : function(){
			return comm.valid({
				formID : '#scpoint_dj_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		}
	};
});