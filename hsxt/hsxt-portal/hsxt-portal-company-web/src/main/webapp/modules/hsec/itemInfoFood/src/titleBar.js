define(["text!hsec_itemInfoFoodTpl/titleBar.html",         
         "hsec_itemInfoFoodSrc/itemList/itemListInfoHead",  
         "hsec_itemInfoSrc/itemList/itemListInfoHead"
         ], function(titleBar,food,item) {
		var shop = {
			bindData : function() {
				var html = _.template(titleBar);
				$('.operationsArea').html(html);
				loadItem();	//默认加载零售
				//加载事件
				$("#cy_bar").unbind().on('click',function(){
					$("#ls_bar").removeClass("active");
					$(this).addClass("active");
					loadFood();	
				});
				
				$("#ls_bar").unbind().on('click',function(){
					$("#cy_bar").removeClass("active");
					$(this).addClass("active");
					loadItem();
				});
			},
			bindFoodData : function(){

				var html = _.template(titleBar);
				$('.operationsArea').html(html);
				loadFood();	
				$("#ls_bar").removeClass("active");
				$("#cy_bar").addClass("active");
				//加载事件
				$("#cy_bar").unbind().on('click',function(){
					$("#ls_bar").removeClass("active");
					$(this).addClass("active");
					loadFood();	
				});
				
				$("#ls_bar").unbind().on('click',function(){
					$("#cy_bar").removeClass("active");
					$(this).addClass("active");
					loadItem();
				});
			
			}
	
		}	
		//加载零售
		function loadItem(){		
			item.bindData();
		}
		
		//加载餐饮的列表
		function loadFood(){			
			food.bindData();	
		}				
		return shop;
});