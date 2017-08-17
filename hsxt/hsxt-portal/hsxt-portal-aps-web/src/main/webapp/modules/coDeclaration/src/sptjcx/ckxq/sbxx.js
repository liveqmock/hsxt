define(['text!coDeclarationTpl/sptjcx/ckxq/sbxx.html',
        'coDeclarationDat/sptjcx/ckxq/sbxx',
		'coDeclarationLan'],function(sbxxTpl, dataModoule){
	return {	 	
		showPage : function(){
			this.initForm();
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#ckxq_view').html(_.template(sbxxTpl));
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var applyId = $("#applyId").val();//申请编号
			if(applyId == null || applyId == ""){
				return;
			}
			var custType = $("#custType").val();//申请类别
			var params = {"applyId": applyId};
			dataModoule.findDeclareAppInfoByApplyId(params, function(res){
				if(res.data){
					$('#ckxq_view').html(_.template(sbxxTpl, res.data));
					if(custType == "3"){//托管企业-显示启用资源类型
						$("#tgText").html("启用资源类型");
						$("#toBuyResRange").html(comm.getNameByEnumId(res.data.toBuyResRange, comm.lang("coDeclaration").toBuyResRangeEnum));
					}else if(custType == "2"){//成员企业-显示启用资源类型
						$("#tgText").html("启用资源类型");
						$("#toBuyResRange").html(comm.getNameByEnumId(res.data.toBuyResRange, comm.lang("coDeclaration").cyToBuyResRangeEnum));
					}else{//服务公司-隐藏启用资源类型
						$("#tgText").html("经营类型");
						$("#toBuyResRange").html(comm.getNameByEnumId(res.data.toBusinessType, comm.lang("common").toBusinessTypes));
					}
					//获取操作员
					cacheUtil.searchOperByCustId(res.data.applyOperator, function(res){
						$("#applyOperator").html(comm.getOperNoName(res));
					});
				}
			});
		}
	}
}); 
