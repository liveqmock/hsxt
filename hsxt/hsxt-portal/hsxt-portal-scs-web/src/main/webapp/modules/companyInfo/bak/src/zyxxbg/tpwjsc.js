define(['text!companyInfoTpl/zyxxbg/tpwjsc.html' ],function(tpwjscTpl ){
	return {
		 
		showPage : function(){
			$('#contentWidth_3').html(_.template(tpwjscTpl)) ;			 
			//Todo...
		   $('#btn_syb').triggerWith('#zyxxbg_bgsq');
		   
		    
		   $('#btn_tj').click(function(){
		   		
		   		comm.confirm({
		   			imgFlag:true,
		   			width:680,
		   			content:'重要信息变更申请提交处理期间，货币账户转银行账户业务暂无法受理，确认要提交申请？',
		   			ok:'确定',
		   			cancel:'取消',
		   			callOk : function(){
		   				
		   				
		   			},
		   			callCancel:function(){
		   				
		   				
		   			} 	 
		   		});
		   }); 	
				
		}
	}
}); 

 