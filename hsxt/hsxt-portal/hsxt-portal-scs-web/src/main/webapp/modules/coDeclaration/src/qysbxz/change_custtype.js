define(['coDeclarationDat/qysbxz/change_custtype',
        'coDeclarationLan'],function(dataModoule){
	return {
		showPage : function(){
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initData : function(){
			var slef = this;
			var applyId = this.getApplyId();
			if(applyId == null){
				require(['coDeclarationSrc/qysbxz/qyxtzcxx_tgqy'], function(qyxtzcxx_tgqy){
					qyxtzcxx_tgqy.showPage();
				});
			}else{
				var custType = this.getCustType();
				if(custType == null){
					dataModoule.findCustTypeByApplyId({applyId:applyId}, function(res){
						var entDeclare = comm.getCache("coDeclaration", "entDeclare");
						entDeclare.custType = res.custType;
						slef.loadPage(res.custType);
					});
				}else{
					this.loadPage(custType);
				}
			}
		},
		/**
		 * 依据申报类别加载页面
		 * @param custType 申报类别
		 */
		loadPage : function(custType){
			if(custType == 2){//如果是成员企业显示成员企业页面
				require(['coDeclarationSrc/qysbxz/qyxtzcxx_cyqy'], function(qyxtzcxx_cyqy){
					qyxtzcxx_cyqy.showPage();
				});
			}else if(custType == 3){//如果是托管企业显示托管企业页面
				require(['coDeclarationSrc/qysbxz/qyxtzcxx_tgqy'], function(qyxtzcxx_tgqy){
					qyxtzcxx_tgqy.showPage();
				});
			}else if(custType == 4){//如果是服务公司显示服务公司页面
				require(['coDeclarationSrc/qysbxz/qyxtzcxx_fwgs'], function(qyxtzcxx_fwgs){
					qyxtzcxx_fwgs.showPage();
				});
			}
		},
		/**
		 * 获取企业申请编号
		 */
		getApplyId : function(){
			return comm.getCache("coDeclaration", "entDeclare").applyId;
		},
		/**
		 * 获取申请类别
		 */
		getCustType : function(){
			return comm.getCache("coDeclaration", "entDeclare").custType;
		}
	}
}); 
