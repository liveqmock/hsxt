define(['text!cashtransferTpl/yhzzcx/yhzzcx_ck1.html',
        'cashtransferSrc/yhzzcx/bankDetail'
	],function(detailTpl,bankDetail){
	return {
		 
		showPage : function(record){
			$("#yhzzcx_operation_tpl").removeClass("none");
			$("#yhzzcxTpl").addClass("none");
			
			$('#yhzzcx_operation_tpl').html(_.template(detailTpl,record));
//			$('#yhzzcx').addClass("none");
			$('#yhzzcx_ck1').removeClass("tabNone");
			/*页面内容切换*/
			$('#yhzzcxTpl').addClass('none');
			$('#yhzzcx_ck1Tp2').addClass('none');
			$('#yhzzcx_ck1Tpl').removeClass('none');
			/*end*/
			
			//返回事件
			$('#ck1_back').click(function(){
				$("#yhzzcxTpl").removeClass("none");
				$('#yhzzcx_ck1').addClass("tabNone");
				$("#yhzzcx_operation_tpl").addClass("none").html("");
				comm.liActive_add($('#yhzzcx'),'#yhzzcx_ck1, #yhzzcx_ck2');
			});
			
			$('#companyDetails_btn').click(function(){
				bankDetail.showPage(record);
			});
			
			comm.liActive($('#yhzzcx_ck1'));
		}
	}
}); 

 