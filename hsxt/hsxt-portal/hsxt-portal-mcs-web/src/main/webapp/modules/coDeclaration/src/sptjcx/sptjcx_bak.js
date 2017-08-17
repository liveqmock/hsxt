define([
"text!coDeclarationTpl/sptjcx/tgqysptj.html",
"text!coDeclarationTpl/sptjcx/cyqysptj.html",
"text!coDeclarationTpl/sptjcx/fwgssptj.html"
],function(tgqysptjTpl,cyqysptjTpl,fwgssptjTpl){
 
	var sptjcx = {
		show:function(){
			//加载中间内容 
			$(".operationsArea").html(tgqysptjTpl).append(cyqysptjTpl).append(fwgssptjTpl);	
			
			//下拉选择框
			$("#cApplyState").selectList({
				width: 130,
				options:[
					{name:'待服务公司复核',value:'100'},
					{name:'服务公司复核驳回',value:'101'},
					{name:'待地区平台初审',value:'102'},
					{name:'地区平台初审驳回',value:'102'},
					{name:'待地区平台复核',value:'102'},
					{name:'办理期中',value:'102'},
					{name:'地区平台复核驳回',value:'102'},
					{name:'待开启系统',value:'102'},
					{name:'开启系统成功',value:'102'}
				]
			}); 
			
			$("#timeRange_start_tg").datepicker({dateFormat:'yy-mm-dd'});
			$("#timeRange_end_tg").datepicker({dateFormat:'yy-mm-dd'});
		
			$(".back_cyqysptj").click(function(){
				$("#tgqysptj").addClass("none");
				$("#fwgssptj").addClass("none");
				
				$("#cyqysptj").removeClass("none");
				
				$("#cApplyState_2").selectList({
					width: 130,
					options:[
						{name:'待服务公司复核',value:'100'},
						{name:'服务公司复核驳回',value:'101'},
						{name:'待地区平台初审',value:'102'},
						{name:'地区平台初审驳回',value:'102'},
						{name:'待地区平台复核',value:'102'},
						{name:'办理期中',value:'102'},
						{name:'地区平台复核驳回',value:'102'},
						{name:'待开启系统',value:'102'},
						{name:'开启系统成功',value:'102'}
					]
				});
				
				$("#timeRange_start_cy").datepicker({dateFormat:'yy-mm-dd'});
				$("#timeRange_end_cy").datepicker({dateFormat:'yy-mm-dd'});
			});
			
			$(".back_tgqysptj").click(function(){
				$("#cyqysptj").addClass("none");
				$("#fwgssptj").addClass("none");
				
				$("#tgqysptj").removeClass("none");
				
			});
			
			$(".back_fwgssptj").click(function(){
				$("#cyqysptj").addClass("none");
				$("#tgqysptj").addClass("none");
				
				$("#fwgssptj").removeClass("none");
				
				$("#cApplyState_3").selectList({
					width: 130,
					options:[
						{name:'待服务公司复核',value:'100'},
						{name:'服务公司复核驳回',value:'101'},
						{name:'待地区平台初审',value:'102'},
						{name:'地区平台初审驳回',value:'102'},
						{name:'待地区平台复核',value:'102'},
						{name:'办理期中',value:'102'},
						{name:'地区平台复核驳回',value:'102'},
						{name:'待开启系统',value:'102'},
						{name:'开启系统成功',value:'102'}
					]
				});
				
				$("#timeRange_start_fw").datepicker({dateFormat:'yy-mm-dd'});
				$("#timeRange_end_fw").datepicker({dateFormat:'yy-mm-dd'});
			});
			
			//弹出框事件
			$( ".sbyxkhxd" ).click(function() {
			  $( "#sbyxkhxdContent" ).dialog({
				title:"意向客户详情",
				modal:true,
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
			});

		},
		hide:function(){
			//清空中间内容） 
			$(".operationsArea").empty();	
		}
		
	};
	
		
	return sptjcx;
	 

});