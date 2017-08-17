define(['text!resouceManageTpl/zype/zypeylb_qyzype.html',
        'resouceManageDat/zype/fwgszypeyl',
        'resouceManageLan'],function(zypeylbTpl, dataModoule){
	return {
		showPage : function(){
			this.initForm();
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#busibox').html(_.template(zypeylbTpl));
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			dataModoule.statResCompanyResM(null, function(res){
				$("#tg_total_num").html(comm.formatNumber(comm.removeNull(res.data.totalNumT)));
				$("#tg_used_num").html(comm.formatNumber(comm.removeNull(res.data.usedNumT)));
				$("#tg_unused_num").html(comm.formatNumber(comm.removeNull(res.data.mayUseNumT)));
				$("#cy_total_num").html(comm.formatNumber(comm.removeNull(res.data.totalNumB)));
				$("#cy_used_num").html(comm.formatNumber(comm.removeNull(res.data.usedNumB)));
				$("#cy_unused_num").html(comm.formatNumber(comm.removeNull(res.data.mayUseNumB)));
			});
			dataModoule.findCompanyResMList(null, null);
		}
	}
}); 

 