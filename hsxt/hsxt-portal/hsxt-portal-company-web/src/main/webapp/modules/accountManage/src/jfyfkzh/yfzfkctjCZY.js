define(['text!accountManageTpl/jfyfkzh/yfzfkctjCZY.html'], function(tpl){
	return yfzfkctjCZY = {
		showPage : function(){
			$('#busibox').html(tpl);
			
			
			searchTable = null;
			
			//加载操作员编号
			var aHtml = ['<option value="">全部</option>'];
			var data = [
				{name:'全部',value:'全部'},
				{name:'111',value:'111'},
				{name:'222',value:'222'},
				{name:'333',value:'333'},
				{name:'444',value:'444'}
			];
			$.each(data, function (k, v) {
				aHtml.push('<option value="' + v.value + '">' + v.name + '</option>');
			});
			$('#scpoint_dsCZY_code').html(aHtml.join('')).combobox();
			$(".ui-autocomplete").css({
				"max-height" : "200px",
				"overflow-y" : "auto",
				"overflow-x" : "hidden",
				"height" : "200px",
				"border" : "1px solid #ccc"
			});
			$(".custom-combobox").find("a").attr("title", "显示所有选项");
			/*日期控件*/
			$("#scpoint_dsCZY_beginDate").datepicker({dateFormat:'yy-mm-dd'});
			$("#scpoint_dsCZY_endDate").datepicker({dateFormat:'yy-mm-dd'});
			
			//按钮事件
			$('#scpoint_dsCZY_searchBtn').click(function(){
				if (!yfzfkctjCZY.validateData()) {
					return;
				}
				
				searchTable = $.fn.bsgrid.init('scpoint_dsCZY_result_table', {
					//url: '',
					localData: [{"rq":"2014-08","bx":"0000000012","czy":"0001","kcs":"100.00","cds":"100.00","sjkcs":"194.20"},{"rq":"2014-08","bx":"0000000012","czy":"0002","kcs":"100.00","cds":"100.00","sjkcs":"194.20"},{"rq":"2014-08","bx":"0000000012","czy":"0003","kcs":"100.00","cds":"100.00","sjkcs":"194.20"},{"rq":"2014-08","bx":"0000000012","czy":"0004","kcs":"100.00","cds":"100.00","sjkcs":"194.20"},{"rq":"2014-08","bx":"0000000012","czy":"0005","kcs":"100.00","cds":"100.00","sjkcs":"194.20"}],
					otherParames: {
						beginDate : $("#scpoint_dsCZY_beginDate").val(),
						endDate : $("#scpoint_dsCZY_endDate").val(),
						code : $("#scpoint_dsCZY_code").val()
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
			$('#scpoint_dsCZY_searchBtn').click();
		},
		validateData : function(){
			return comm.valid({
				formID : '#scpoint_dsCZY_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		}
	};
});