define(["hsec_tablePointSrc/tablePoint",
        "hsec_itemCatChooseDat/itemCatChoose",
        "hsec_itemCatChooseSrc/prop",
        "text!hsec_itemCatChooseTpl/itemAdd/category.html",
        "text!hsec_itemCatChooseTpl/itemAdd/categoryLi.html"], 
        function(tablePoint,ajaxChoose,prop,categoryTpl,categoryLiTpl) {
	var cateGoryList;
	var category = {
		bindData : function(){
			cateGoryList = "";
			ajaxChoose.getNextByPid({},function(response){
				cateGoryList = response.data;
				$("#category_content").html(_.template(categoryTpl, response));
				//category.open();
				//category.bindFun();
				category.bindCateGoryClick();
			});
			
		},
		bindCateGoryClick : function(){
			$("#cateGory").show();
			$("#cateGory").on("click","ul>li>div>ul>li",function(){	
				$(this).parents("li").eq(0).find("li").removeClass("active");
				$(this).addClass("active");
				var cateId = $(this).attr("data-id");
				var param = {};
				param["category"] = cateGoryList;
				param["cateId"] = cateId;
				var html = _.template(categoryLiTpl, param);
				$(this).parents("li").eq(0).next().html(html);
			})
			$("#cancel_btn").click(function(){
				category.close();
			});
			$("#confirm_btn").click(function(){
				var count = 0;
				$.each($("#cateGory .list_ft"),function(k,v){
					if($.trim($(v).find(".list_sc").html()) != null && $.trim($(v).find(".list_sc").html()) != ""){
						count++;
					} 
				})
				if(count == $("#cateGory .active").length){
					category.confirm();
				}else{
					tablePoint.tablePoint("选择类目不够完整!");
				}
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
		skuImgList : function(){
			return prop.skuImgList();
		},
		skuImgUpdateList : function(){
			return prop.skuImgUpdateList();
		},
		skuImgDeleteList : function(){
			return prop.skuImgDeleteList();
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
			var arr = new Array();
			$(".goodsCatalogue .active").each(function(index){
				arr[index] = $(this).text();
			});
			$("#goodsCc").val(arr.join(">"));
			category.close();
			var catId = $("#cateGory .active").eq($("#cateGory .active").length - 1).attr("data-id");
			$("#goodsCc").attr("itemCatChooseId",catId);
			prop.bindData(catId);
		}
	}
	return category;
});