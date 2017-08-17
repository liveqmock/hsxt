define(["hsec_itemInfoFoodDat/item",
        "hsec_itemInfoFoodSrc/sku",
        "text!hsec_itemInfoFoodTpl/prop.html",
        "text!hsec_itemInfoFoodTpl/category.html"], 
        function(itemDat,skuSrc,propTpl,categoryTpl) {
	var category = {
		bindData : function(callblack){
			itemDat.queryCategory({},function(response){
				var html = _.template(categoryTpl, response);
				$("#category_content").html(html);
				if(callblack){
					callblack();
				}
				category.open();
				category.bindFun();    
			});
		},
		bindFun : function(){
			$(".child_ul a").click(function(){
				$(".child_ul a").removeClass("active");
				$(this).addClass("active");
			}).dblclick(function(){
				category.confirm();
			});
			
			$("#confirm_btn").click(function(){
				category.confirm();
			});
			$("#cancel_btn").click(function(){
				category.close();
			});
		},
		close : function(){
			$(".goodsCatalogue").hide();
		},
		open : function(){
			function active_fun(obj){
				obj.parent().siblings().find(".active").removeClass("active");
				$(".child_ul a").removeClass("active");
				obj.addClass("active");
				
				var did = obj.attr("data-id");
				$(".child_ul[data-id="+did+"]").removeClass("none").siblings().addClass("none");
				$(".child_ul[data-id="+did+"]").find("a:eq(0)").addClass("active");
			}
			active_fun($(".category:eq(0)"));
			$(".category").click(function(){
				active_fun($(this));
			});
			
			$(".goodsCatalogue").show();
		},
		confirm : function(){
			var nameArr = new Array();
			var idArr = new Array();
			$(".goodsCatalogue .active").each(function(index){
				nameArr[index] = $(this).text();
				idArr[index] = $(this).attr("data-id");
			});
			$("#categoryName").val(nameArr.join(">"));
			$("#categoryId").val(idArr.join(">"));
			
			category.close();
			
			var catId = $(".child_ul .active").attr("data-id");
			category.queryPropBycategoryId(catId,new Array());
			
		},
		queryPropBycategoryId : function(catId,arr,price,pv){
			itemDat.queryPropBycategoryId({"categoryId":catId},function(res){
				var html = _.template(propTpl, res);
				$("#propTable").html(html);
				$("#propTable").unbind(); 
				category.setPorpChecked(arr);
				
				if(catId=="05010"){
					$(".integral").hide();
					$(".priceTr").show();
					$("#flag").val(1);
					$('#tab1').find('tbody > tr.integral:first').next().hide();
				}else{
					if(arr==null){
						arr = [{"price":price,"auction":pv}];
					}else{
						skuSrc.bindData(res.data);
					}
					$(".integral").show();
					$(".priceTr").hide();
					$("#flag").val(0);
					$('#tab1').find('tbody > tr.integral:first').next().show();
					
					skuSrc.setSku(arr);
				}
			})
		},
		setPorpChecked : function(arr){
			if(arr){
				$.each(arr,function(key,o){
					$("#prop_tbody .prop input[value="+o.pVId+"]").prop("checked",true);
				});
			}
		}
	}
	return category;
});