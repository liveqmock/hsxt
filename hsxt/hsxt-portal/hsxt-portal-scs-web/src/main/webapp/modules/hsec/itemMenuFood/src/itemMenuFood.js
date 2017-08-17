define(["hsec_tablePointSrc/tablePoint"
        ,"text!hsec_itemMenuFoodTpl/itemMenuHead.html"
        ,"hsec_itemMenuFoodDat/itemMenuFood"]
		,function(tablePoint,itemMenuHead,ajaxRequest){
    var gridObj;
	var itemMenuFood = {
		//构建查询参数
		queryParam : function(){
			var param = {"eachPageSize":20};
			param.companyResourceNo = $("#companyResourceNO").val();
			param.name = $("#itemName").val()=="全部"?"all":$("#itemName").val();
			return param;
		},
		//绑定表格
		bindData : function(){
			$("#busibox").html(itemMenuHead);
			var param = itemMenuFood.queryParam();
			itemMenuFood.initTableData(param);
		},
		//初始化表格数据
		initTableData : function(param){
			gridObj = $.fn.bsgrid.init('itemData',{
				url:{url:comm.UrlList['listItemMenuFood'],domain:'scs'},
				otherParames:$.param(param),
				pageSizeSelect:true,
				pageSize:20,
				displayBlankRows:false,
				stripeRows:true,
				operate:{
					add : function(record, rowIndex, colIndex, options){
						if(colIndex==3){
							if(record.name == "all"){
								return '全部'
							}else{
								return record.name;
							}
						}
						/*if(colIndex==5){
							if(record.status==1){ 
								return '<span>启用</span>'
							}else{ 
								return '<span>禁用</span>'
							} 
						}*/
					}
				},
				complete:function(o, e, c){
					itemMenuFood.btnSearchClick();
				}
			});
		},
		//查询事件
		btnSearchClick : function(){
			//编号输入验证
			$("#companyResourceNO").on('focus',function(){
				$(this).on('keyup',function(){
					var num = $(this).val();
					var p1=/^[\d]+$/;
					if(!p1.test(num)){
						$(this).val("");
					}
				})
			});
			$("#btnQueryItem").unbind().on("click",function(){
				var param = itemMenuFood.queryParam();
				itemMenuFood.initTableData(param);
			});
		}
	};
	return itemMenuFood;
});