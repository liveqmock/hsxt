define([
"text!shouhouTpl/pjlb.html",
"jqueryalert"
],function(pjlbTpl){
 
	var pjlb = {
		show:function(){
			//加载中间内容 
			$(".operationsArea").html(pjlbTpl);	
			
			/*		
			$( ".popup03" ).click(function(){
			  $( "#ex_deal" ).dialog( {
				  show: true,
				  modal: true,
				  title:"回复申请",
				  width:"400",
				  buttons: {
					同意退款: function() {$( this ).dialog( "close" );},
					同意换货: function() {$( this ).dialog( "close" );},
					拒绝: function() {$( this ).dialog( "close" );}		
					}
					});	 
			  });*/
		
		/*评价列表*/
		function appraiseReply(){
			
			$(".btn_reply").click(function(){
				var warp=$(this).parents(".pllb_content > ul");
				if(warp.find("#pllb_textareabox").length==0){
					warp.find(".reply_cont").append($("#pllb_textareabox"));
					}
				$("#pllb_textareabox").hide()
				var ulHeight=warp.height();
				//warp.height(ulHeight);
				$("#pllb_textareabox").show(500);
				$('textarea').val('');
				})
			 $("#send").click(function(){
				 var ulHeight=$(this).parents(".pllb_content > ul").height();
				 $("#pllb_textareabox").hide(500);
				 })
				 
		}
		appraiseReply();	
		
		$(".qyddgl").click(function(){
			$("#subNav_2_03").trigger("click");	
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
	
		
	return pjlb;
	 

});