define([
"text!shouhouTpl/ddlb.html",
"text!shouhouTpl/ddxq.html" 
 
],function(ddlbTpl,ddxqTpl){
    var mainArguments = arguments;
	var ddlb = {
		show:function(){
			//加载中间内容
			$(".operationsArea").html(ddlbTpl);
			
			$(".ddlb_ckxq_btn").click(function(){
				$(".operationsArea").html(ddxqTpl);
				
				$(".qypjgl").click(function(){
					$("#subNav_2_04").trigger("click");	
				});
				$(".ddshgl").click(function(){
					$("#subNav_2_05").trigger("click");	
				});
			});
			$(".table_no_hover tbody").first().show().siblings("tbody").hide();
			$(".setdiy100").html("交易状态");
			function tabs(elem,tabs){
				$(elem).children().click(function(){
					$(this).addClass("active").parent().siblings().children().removeClass("active");
					$(tabs).hide().eq($(elem).children().index(this)).show();
                    var text= $(".tabList li .active").text();
					if(text!="全部")
					{
						$(".setdiy100").html("交易状态");
					}
					else
					{
						var sel="<select id=\"yStatues1\"><option>交易状态</option>";
						 sel+="<option>未支付</option>"
						 sel+="<option>已支付</option>"
						 sel+="<option>已完成</option>"
						 sel+="<option>已取消</option>"
						 sel+="<option>退款退货订单</option></select>";
						$(".setdiy100").html(sel);
					}
//					
//			        $(".table_no_hover .order_status span").text(statu_text);
					})
					
				};
			tabs('.tabList li','.table_no_hover tbody');
			
			$(".qypjgl").click(function(){
				$("#subNav_2_04").trigger("click");	
			});
			$(".ddshgl").click(function(){
				$("#subNav_2_05").trigger("click");	
			});
			
			
		
			
		},
		hide:function(){
			//清空中间内容） 
			$(".operationsArea").empty();	
		}
		
	};
	
		
	return ddlb;

	 

});
