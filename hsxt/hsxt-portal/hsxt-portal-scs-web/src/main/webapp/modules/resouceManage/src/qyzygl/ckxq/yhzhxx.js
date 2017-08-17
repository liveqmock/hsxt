define(['text!resouceManageTpl/qyzygl/ckxq/yhzhxx.html',
        'resouceManageDat/resouceManage',
		'resouceManageLan'],function(yhzhxxTpl, dataModoule){
	return {
		showPage : function(){
			this.initForm();
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#ckxq_view').html(_.template(yhzhxxTpl));
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var self = this;
			dataModoule.findCompanyBanksByCustId({companyCustId:$("#resEntCustId").val()}, function(res){
				var list = res.data;
				if(list){
					cacheUtil.findCacheSystemInfo(function(sysRes){
						//币种
						var currencyNameCn = sysRes.currencyNameCn;
						//初始化银行列表
						cacheUtil.findCacheBankAll(function(bankRes){
							for(var key in list){
								var bankName = comm.getBankNameByCode(list[key].bankCode, bankRes);
								self.addBank(currencyNameCn, list[key], bankName);
							}
						});
					});
				}
			});
		},
		/**
		 * 添加银行卡li并绑定事件
		 * @param currencyNameCn 币种
		 * @param bank 银行账户
		 * @param bankName 开户行
		 */
		addBank : function(currencyNameCn, bank, bankName){
			var detailId = "detail_"+bank.accId;
			var deleteId = "delete_"+bank.accId;
			var li = (bank.isDefaultAccount == 1)?$("#default_bank_ul").html():$("#bank_ul").html();
			li = li.replace("bankName", comm.removeNull(bankName));
			
			/** by wangjg 2016-3-15 作用:显示银行logo*/
			li = li.replace("bankAccNo", comm.showLastCard(bank.bankAccNo));
			li = li.replace("bankCode", bank.bankCode);
			/** by wangjg 2016-3-15 作用:显示银行logo*/
			
			li = li.replace("currencyName", comm.removeNull(currencyNameCn));
			if((bank.isDefaultAccount == 1)){
				$("#banks").prepend(li);
			}else{
				$("#add_bank").before(li);
			}
			bank.currencyName = currencyNameCn;
			bank.sdefaultText = (bank.isDefaultAccount == 1)?"是":"否";
			$("#"+detailId).click(function(){
				comm.delCache("companyInfo", "detailBank");
				comm.setCache("companyInfo", "detailBank", bank);
				$("#qyxx_yhzhxxxq").click();
			});
		}
	}
}); 

 