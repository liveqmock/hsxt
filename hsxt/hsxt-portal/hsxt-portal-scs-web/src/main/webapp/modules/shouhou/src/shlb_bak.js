define([
"text!shouhouTpl/shlb.html",
"text!shouhouTpl/sqsh.html" 
],function(shlbTpl,sqshTpl){
    var mainArguments = arguments;
	var shlb = {
		show:function(){
			//加载中间内容
			
			
			$(".operationsArea").html(shlbTpl);
			
			$(".shlb_ckxq_btn").click(function(){
				$(".operationsArea").html(sqshTpl);
				
				$(".qyddgl").click(function(){
					$("#subNav_2_03").trigger("click");	
				});
				$(".qypjgl").click(function(){
					$("#subNav_2_04").trigger("click");	
				});
			});
			
			$(".table_no_hover tbody").first().show().siblings("tbody").hide();
			$(".setdiy100").html("交易状态");
			function tabs(elem,tabs){
				$(elem).children().click(function(){
					$(this).addClass("active").parent().siblings().children().removeClass("active");
					$(tabs).hide().eq($(elem).children().index(this)).show();
                   
//			        $(".table_no_hover .order_status span").text(statu_text);
					})
					
				};
			tabs('.tabList li','.table_no_hover tbody');
			
			
			$(".qyddgl").click(function(){
				$("#subNav_2_03").trigger("click");	
			});
			$(".qypjgl").click(function(){
				$("#subNav_2_04").trigger("click");	
			});
		
			
		},
		hide:function(){
			//清空中间内容） 
			$(".operationsArea").empty();	
		}
		
	};
	
		
	return shlb;

	 

});

            


		