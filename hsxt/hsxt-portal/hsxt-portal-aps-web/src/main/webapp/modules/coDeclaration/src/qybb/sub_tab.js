define(['text!coDeclarationTpl/qybb/sub_tab.html',
		'coDeclarationSrc/qybb/qyjbxx',
		'coDeclarationSrc/qybb/gdxx',
		'coDeclarationSrc/qybb/fjxx'
		],function(tab, qyjbxx, gdxx, fjxx){
	return {
		showPage : function(applyId, menuName){
			$('#busibox').html(_.template(tab));
			
			$("#applyId").val(applyId);
			$("#menuName").val(menuName);
			this.initData(applyId);
			
			/*企业基本信息*/
			$('#qyjbxx').click(function(e) {
                qyjbxx.showPage();
				comm.liActive($('#qyjbxx'));
            }.bind(this)).click(); 
			
			/*股东信息*/
			$('#gdxx').click(function(e) {
                gdxx.showPage();
				comm.liActive($('#gdxx'));
            }.bind(this));
			
			/*附件信息*/
			$('#fjxx').click(function(e) {
                fjxx.showPage();
				comm.liActive($('#fjxx'));
            }.bind(this)); 
		},
		/**
		 * 初始化缓存数据
		 */
		initData : function(applyId){
			comm.delCache("coDeclaration", "entFilling");
			comm.setCache("coDeclaration", "entFilling", {"applyId": applyId});
		}
	}
}); 

