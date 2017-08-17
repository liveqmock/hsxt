define([
"text!hsec_itemCommentTpl/evgoods.html",
"text!hsec_itemCommentTpl/evshop.html",
"text!hsec_itemCommentTpl/comment/itemFoodComment.html",
"text!hsec_itemCommentTpl/comment/itemFoodRender.html",
"text!hsec_itemCommentTpl/comment/itemFoodInfo.html",
"hsec_itemCommentDat/foodItemEvaluate",
"hsec_itemCommentSrc/itemComment"
],function(evgoodsTpl,evshopTpl,itemFoodCommentTpl,ifRenderTpl,sTpl,foodItemEvaluateDataAjax,itemComment){
    var mainArguments = arguments;
	var objParam = {"currentPageIndex":1};
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
			param = objParam;
		},
		foodShopsListData:function(){
			 $("#busibox").html(_.template(ifRenderTpl));
			 complaint.loadGridData(param);
		},
		loadGridData : function(param){
			var gridObj = $.fn.bsgrid.init('gridItem',{
			  		url : {url:comm.UrlList['queryItemListCommentGrid'],domain:"sportal"},//comm.domainList['sportal'] + comm.UrlList['queryItemListCommentGrid'],
		   			otherParames:param,
		   			pageSize : 20,
		   			displayBlankRows:false,//不显示空白行
		   			stripeRows:true,
		   			lineWrap:true,//自动换行
		   			//渲染单元格数据执行方法, 不论此单元格是否为空单元格(即无数据渲染)
		   			additionalRenderPerColumn: function(record, rowIndex, colIndex, tdObj, trObj, options){
					    trObj.find('td').attr('colspan', '5').html(_.template(sTpl,record));
					},
					complete : function(e,o,s){
						//当没有数据时合并自定义单元格
						if(gridObj.getAllRecords()){
							$("#gridItem").find("tbody tr td").attr('colspan', '5');
						}
						complaint.kouweiSelectClick();
					    complaint.huanjingSelectClick();
					    complaint.changeShousuoClik();
					    complaint.evshopTabClick();
					}
			  }); 
		},
		//口味选择
		kouweiSelectClick:function(){
			$("#delisionsEvaluateSelect").unbind().on("change",function(){
				complaint.queryParam(1);
				complaint.foodShopsListData();
			});
		},
		//环境选择
		huanjingSelectClick:function(){
			$("#evorimentEvaluateSelect").unbind().on("change",function(){
				complaint.queryParam(1);
				complaint.foodShopsListData();
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
		},
		evshopTabClick:function(){
			param="";
			$(".evshop").click(function(){
				complaint.queryParam(1);
				complaint.foodShopsListData();
			});
		}
	};
	return complaint;
});

		