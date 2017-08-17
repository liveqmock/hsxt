define([
"text!mytaskTpl/wdcbgdlb/wdcbgdlb.html",
"text!mytaskTpl/wdcbgdlb/wdcbgdlb_chakang.html"
],function(wdcbgdlbTpl,wdcbgdlb_chakangTpl){
 
	var wdcbgdlb = {
		show:function(){
			//加载中间内容 
			$(".operationsArea").html(wdcbgdlbTpl).append(wdcbgdlb_chakangTpl);	
			
			//下拉列表事件
			$("#quickDate").selectList({
				options:[
					{name:'今天',value:'100'},
					{name:'本周',value:'101'},
					{name:'本月',value:'102'}
				]
			});
			$("#businessType").selectList({
				options:[
					{name:'xxx',value:'001'},
					{name:'xxx',value:'002'},
					{name:'xxx',value:'003'}
				]	
			});
			
			$("#timeRange_1").datepicker({dateFormat:'yy-mm-dd'});
			$("#timeRange_2").datepicker({dateFormat:'yy-mm-dd'});
			//end
			
			//按钮事件			
			$(".detailsView_btn").click(function(){
				$("#wdcbgdlb").addClass("none");
				
				$("#wdcbgdlb_chakang").removeClass("none");	
			});
			
			$(".back_wdcbgdlb").click(function(){
				$("#wdcbgdlb_chakang").addClass("none");
				
				$("#wdcbgdlb").removeClass("none");
			});
			
			/*$(".back_clzgdlb_operator").click(function(){
				$("#clzgdlb_administrator_chakang").addClass("none");
				$("#clzgdlb_administrator_paidan").addClass("none");
				$("#clzgdlb_administrator").addClass("none");
				$("#clzgdlb_operator_chakang").addClass("none");
				
				$("#clzgdlb_operator").removeClass("none");
			});
			
			$(".delivery_btn_administrator").click(function(){
				$("#clzgdlb_administrator").addClass("none");
				$("#clzgdlb_administrator_chakang").addClass("none");
				$("#clzgdlb_operator").addClass("none");
				$("#clzgdlb_operator_chakang").addClass("none");
				
				$("#clzgdlb_administrator_paidan").removeClass("none");	
			});
			
			$(".detailsView_btn_operator").click(function(){
				$("#clzgdlb_administrator").addClass("none");
				$("#clzgdlb_operator").addClass("none");
				$("#clzgdlb_administrator_chakang").addClass("none");
				$("#clzgdlb_administrator_paidan").addClass("none");
				
				$("#clzgdlb_operator_chakang").removeClass("none");	
			});*/
			
			
			//end
			
			//弹出框事件
			/*$( ".deliveryPop-up" ).click(function() {
				  $( ".jqp-dialog" ).dialog({
					title:"派单信息",
					width:"800",
					buttons:{ 
						"确定":function(){
							$( this ).dialog( "close" );
						},
						"取消":function(){
							 $( this ).dialog( "close" );
						}
					}
			
				  });
				  $( ".ui-widget-header" ).addClass("norwidth");
				  $( ".jqp-dialog p" ).html($( "#deliveryPop-up" ).html());
				  $( ".jqp-dialog" ).dialog( "open" ); 
				});*/
			
			//end
				
		},
		hide:function(){
			//清空中间内容） 
			$(".operationsArea").empty();	
		}
		
	};
	
		
	return wdcbgdlb;
	 

});