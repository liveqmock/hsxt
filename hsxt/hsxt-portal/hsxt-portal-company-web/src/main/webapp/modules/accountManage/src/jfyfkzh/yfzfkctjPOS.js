define(['text!accountManageTpl/jfyfkzh/yfzfkctjPOS.html'], function(tpl){
	return yfzfkctjPOS = {
		showPage : function(){
			$('#busibox').html(tpl);
			
			
			searchTable = null;
			
			//加载终端编号
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
			$('#scpoint_dsPOS_code').html(aHtml.join('')).combobox();
			$(".ui-autocomplete").css({
				"max-height" : "200px",
				"overflow-y" : "auto",
				"overflow-x" : "hidden",
				"height" : "200px",
				"border" : "1px solid #ccc"
			});
			$(".custom-combobox").find("a").attr("title", "显示所有选项");
			/*下拉列表*/
			$("#scpoint_dsPOS_subject").selectList({
				options:[
					{name:'全部',value:''},
					{name:'POS终端',value:''},
					{name:'刷卡器终端',value:''},
					{name:'网页终端',value:''},
					{name:'经营平台',value:''}
				]
			}).change(function(e){
				var v = $(this).val();
				
			});
			/*日期控件*/
			$("#scpoint_dsPOS_beginDate").datepicker({dateFormat:'yy-mm-dd'});
			$("#scpoint_dsPOS_endDate").datepicker({dateFormat:'yy-mm-dd'});
			
			//按钮事件
			$('#scpoint_dsPOS_searchBtn').click(function(){
				if (!yfzfkctjPOS.validateData()) {
					return;
				}
				
				searchTable = $.fn.bsgrid.init('scpoint_dsPOS_result_table', {
					//url: '',
					localData: [{"rq":"2014-08","lx":"刷卡器终端","bx":"0000000012","kcs":"100.00","cds":"100.00","sjkcs":"194.20"},{"rq":"2014-08","lx":"POS终端","bx":"0000000012","kcs":"100.00","cds":"100.00","sjkcs":"194.20"},{"rq":"2014-08","lx":"网页终端","bx":"0000000012","kcs":"100.00","cds":"100.00","sjkcs":"194.20"},{"rq":"2014-08","lx":"经营平台","bx":"0000000012","kcs":"100.00","cds":"100.00","sjkcs":"194.20"},{"rq":"2014-08","lx":"网页终端","bx":"0000000012","kcs":"100.00","cds":"100.00","sjkcs":"194.20"}],
					otherParames: {
						beginDate : $("#scpoint_dsPOS_beginDate").val(),
						endDate : $("#scpoint_dsPOS_endDate").val(),
						code : $("#scpoint_dsPOS_code").val(),
						subject : $("#scpoint_dsPOS_subject").val()
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
			$('#scpoint_dsPOS_searchBtn').click();
		},
		validateData : function(){
			return comm.valid({
				formID : '#scpoint_dsPOS_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		}
	};
});