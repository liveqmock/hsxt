define(["hsec_goodslistDat/goodslistdat",
        "text!hsec_goodslistTpl/category.html"], 
        function(ajaxRequest,categoryTpl) {
	var category = {
		bindData : function(callblack){
			ajaxRequest.queryCategory({},function(response){
				console.info(response);
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
	
		}
	}
	return category;
});