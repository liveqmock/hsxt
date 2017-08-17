define(['text!callCenterTpl/zxztjk/zxsz.html'], function(zxszTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(zxszTpl));	
			
			/*按钮事件*/
			$('#addSeat_btn').click(function(){
				/*弹出框*/
				$( "#addSeat_content" ).dialog({
					title:"增加坐席",
					modal:true,
					width:"480",
					height:"290",
					buttons:{ 
						"确定":function(){
							$( "#addSeat_content" ).dialog( "close" );
						},
						"取消":function(){
							 $( "#addSeat_content" ).dialog( "close" );
						}
					}
			
				  });
				/*end*/	
			});
			/*end*/
			
		}
	}	
});