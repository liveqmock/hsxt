define(['text!safeSetTpl/czjymm/czjymm.html',"safeSetDat/safeSet" ],function(czjymmTpl,safeSet ){
	return {
		showPage : function(){
			var self = this;
			
			//绑定下载按钮
			cacheUtil.findDocList(function(map){
				comm.initDownload("#aTmpDownload", map.busList, '1002');
			});
			
			//获取重置交易密码申请记录
			safeSet.queryTradPwdLastApply(function(rsp){
				$('#contentWidth_4').html(_.template(czjymmTpl,rsp.data)) ;	

				var result=rsp.data;
				if(result==null || result==undefined || result.status==1 ){
					$("#czjymm_normal").removeClass('none');
					$("#czjymm_result").addClass('none');
					$("#tips_2").addClass('none');
				}else if(result.status == 0){
					$("#czjymm_normal").addClass('none');
					$("#czjymm_result").removeClass('none');
					$("#tips_2").addClass('none');
					//控制按钮隐藏
					$('#btn_sqcz,#btn_csjymm').hide();
				}else if(result.status == 2){
					$("#czjymm_normal").addClass('none');
					$("#czjymm_result").addClass('none');
					$("#tips_2").removeClass('none');
					$("#btn_sqcz").text("重新申请重置");
					$("#apprDate").text(result.updatedDate);
					$("#apprRemarkMessage").text(result.apprRemark);
				}
			 	
			 	//申请重置 
				$('#btn_sqcz').triggerWith('#xtaq_czjymm2');
				
				//交易密码重置
				$('#btn_csjymm').triggerWith('#xtaq_czjymm3');
			});
		}
	}
}); 

 