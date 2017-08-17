define(['text!cashtransferTpl/yhzzcx/yhzzcx_ck2.html'
	],function(detailTpl){
	return {
		 
		showPage : function(record){
			$("#yhzzcx_operation_tpl").removeClass("none");
			$("#yhzzcxTpl").addClass("none");
			$('#yhzzcx_operation_tpl').html(_.template(detailTpl,record));
//			$('#yhzzcx').addClass("none");
			$('#yhzzcx_ck1').addClass("none");
			$('#yhzzcx_ck2').removeClass("tabNone");
			/*页面内容切换*/
			$('#yhzzcxTpl').addClass('none');
			$('#yhzzcx_ck1Tpl').addClass('none');
			$('#yhzzcx_ck2Tpl').removeClass('none');
			/*end*/
			
			/*按钮事件*/
			$('#ck2_back').click(function(){
				$("#yhzzcxTpl").removeClass("none");
				$('#yhzzcx_ck2,#yhzzcx_ck1').addClass("tabNone");
				$("#yhzzcx_operation_tpl").addClass("none").html("");
				comm.liActive_add($('#yhzzcx'),'#yhzzcx_ck2, #yhzzcx_ck1');
			});
			
			comm.liActive($('#yhzzcx_ck2'));
		}
	}
}); 

 