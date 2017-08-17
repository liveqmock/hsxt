define(["text!hsec_judgeTpl/evaluateShop.html",
		"text!hsec_judgeTpl/evaluateInfo.html",
		"hsec_judgeDat/foodItemEvaluate"],
		function(evaluateShopTpl,sTpl,foodItemEvaluateDataAjax){
    var mainArguments = arguments;
	var objParam = {};
	var param;
	var complaint = {
		queryParam : function(currentPageIndex){
			objParam = {};
			var deliciousSelect=$("#delisionsEvaluateSelect  option:selected").val();
			var enviromentSelect=$("#evorimentEvaluateSelect  option:selected").val();
			if(deliciousSelect==""){
				deliciousSelect=0;
			}
			if(enviromentSelect==""){
				enviromentSelect=0;
			}
			objParam["score"]=deliciousSelect;
			objParam["surroundingsScore"]=enviromentSelect;
			objParam["eachPageSize"] = 10;
			return objParam;
		},
		foodShopsListData:function(){
			$("#busibox").html(evaluateShopTpl);
			var param = complaint.queryParam(1);
			complaint.loadGridData(param);
		},
		loadGridData:function(param){
			gridObj = $.fn.bsgrid.init('cy_rate_table',{
				url : {url:comm.UrlList['getFoodItemCommentList'],domain:"scs"},//
	   			otherParames:$.param(param),
	   			pageSize : 10,
	   			pageSizeSelect:true,
				displayBlankRows:false,
				lineWrap:true,
				stripeRows:true,
				rowSelectedColor:true,
	   			additionalRenderPerColumn: function(record, rowIndex, colIndex, tdObj, trObj, options){
					trObj.find('td').attr('colspan', '5').html(_.template(sTpl,record));
	   			},
				complete : function(e,o,s){
					//当没有数据时合并自定义单元格
					if(gridObj.getAllRecords()){
						$("#cy_rate_table").find("tbody tr td").attr('colspan', '5');
					}
					complaint.kouweiSelectClick();
				    complaint.huanjingSelectClick();
				    complaint.changeShousuoClik();
				}
			});
		},
		//口味选择
		kouweiSelectClick:function(){
			$("#delisionsEvaluateSelect").unbind().on("change",function(){
				complaint.loadGridData(complaint.queryParam(1));
			});
		},
		//环境选择
		huanjingSelectClick:function(){
			$("#evorimentEvaluateSelect").unbind().on("change",function(){
				complaint.loadGridData(complaint.queryParam(1));
			});
		},
		changeShousuoClik:function(){
				//展开收缩功能
				$(".t2").click(function(){
					var span = $(this).children("span");
					var i = $(this).children("i");
					var txt = $(this).parent().siblings().children(".ftst");
					span.text(txt.hasClass("eps")?"收起":"更多");
					i.toggleClass("arw-up");
					txt.toggleClass("eps");
			});
		}
	};
		
	return complaint;
});

		