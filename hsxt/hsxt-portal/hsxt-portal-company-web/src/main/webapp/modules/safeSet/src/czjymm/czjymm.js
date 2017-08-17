define(["text!safeSetTpl/czjymm/czjymm.html",
		"safeSetSrc/czjymm/sqcz",
		"safeSetSrc/czjymm/jymmcs","safeSetDat/safeSet","safeSetLan"
		],function(tpl, sqcz, jymmcs,safeSet){
	return {
		showPage : function(){
			var self = this;
			
			//绑定下载按钮
			cacheUtil.findDocList(function(map){
				comm.initDownload("#aTmpDownload", map.busList, '1002');
			});

			//获取重置交易密码申请记录
			safeSet.queryTradPwdLastApply(function(rsp){
				$('#busibox').html(_.template(tpl,rsp.data));
				var result=rsp.data;
//				if(result==null || result==undefined || result.status==1 ){
//					$("#czjymm_normal").removeClass('none');
//					$("#czjymm_result").addClass('none');
//				}else{
//					$("#czjymm_normal").addClass('none');
//					$("#czjymm_result").removeClass('none');
//					
//					//控制按钮隐藏
//					if(result.status==0){
//						$('#btn_sqcz').parent().hide();
//					} 
//				}

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
					$('#tips_3').hide();
				}else if(result.status == 2){
					$("#czjymm_normal").addClass('none');
					$("#czjymm_result").addClass('none');
					$("#tips_2").removeClass('none');
					$("#btn_sqcz").text("重新申请重置");
					$("#apprDate").text(result.updatedDate);
					$("#apprRemarkMessage").text(result.apprRemark);
				}
				
				//申请重置
				$('#btn_sqcz').click(function(){
					sqcz.showPage();
				});
				
				//交易密码重设
				$('#btn_jymmcs').click(function(){
					jymmcs.showPage();
				});
			});
		}
	}
});