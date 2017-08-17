define(['text!companyInfoTpl/yhzhxx/yhzhxx.html',
        'companyInfoDat/yhzhxx/yhzhxx',
		'companyInfoLan'],function(yhzhxxTpl, dataModoule){
	return {
		showPage : function(){
			this.initForm();
			this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			var self = this;
			$('#contentWidth_4').html(_.template(yhzhxxTpl));
			$('.addBt_yinghang').triggerWith('#qyxx_yhzhxxtj');
			var entAllInfo = comm.getCache("companyInfo", "entAllInfo");
			if(entAllInfo == null){
				dataModoule.findEntAllInfo(null, function(res){
					comm.setCache("companyInfo", "entAllInfo", res.data);
				});
			}
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var self = this;
			dataModoule.findBanksByCustId(null, function(res){
				var list = res.data;
				$("#add_bank").show();
				if(list && list.length > 0){
					if(list.length > 4){
						$("#add_bank").hide();
						$("#left_bank_count").html(0);
					}else{
						$("#left_bank_count").html(5-list.length);
					}
					cacheUtil.findCacheLocalInfo(function(sysRes){
						//币种
						var currencyNameCn = sysRes.currencyNameCn;
						//初始化银行列表
						cacheUtil.findCacheBankAll(function(bankRes){
							for(var i = 0; i <list.length;i++ ){
								var obj = list[i];
								if(obj){
									var bankName = comm.getBankNameByCode(obj.bankCode, bankRes);
									self.addBank(currencyNameCn, obj, bankName);
								}
							}
						});
					});
				}else{
					$("#left_bank_count").html(5);
				}
			});
		},
		/**
		 * 银行名字过长，缩略显示
		 */
		getShortName : function (name,length){
			if( name == null || name == "" ){
				return "";
			}else if( length == null || length == "" || name.length <= length ){
				return name;
			}else{
				return name.substring(0,length)+"...";
			}
			
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
			var fullname = comm.removeNull(bankName);
			var shortName = this.getShortName(fullname,24);
			li = li.replace("bankAccNo", comm.showLastCard(bank.bankAccNo));
			li = li.replace("bankCode", comm.showLastCard(bank.bankCode));
			li = li.replace("currencyName", comm.removeNull(currencyNameCn));
			//需求变更，默认银行账户可以删除 by likui bankCode
			/*if((bank.isDefaultAccount == 1)){
				li = li.replace("option", '<a id="'+detailId+'" class="detailBt_yinghang">详情</a> | 删除');
				$("#banks").prepend(li);
			}else{
				li = li.replace("option", '<a id="'+detailId+'" class="detailBt_yinghang">详情</a> | <a id="'+deleteId+'" class="deleteBt_yinghang">删除</a>');
				$("#add_bank").before(li);
			}*/
			
			li = li.replace("option", '<a id="'+detailId+'" class="detailBt_yinghang">详情</a> | <a id="'+deleteId+'" class="deleteBt_yinghang">删除</a>');
			$("#add_bank").before(li);
			bank.currencyName = currencyNameCn;
			bank.sdefaultText = (bank.isDefaultAccount == 1)?"是":"否";
			$("#"+detailId).click(function(){
				comm.delCache("companyInfo", "detailBank");
				comm.setCache("companyInfo", "detailBank", bank);
				$("#qyxx_yhzhxxxq").click();
			});
			$("#"+deleteId).click(function(){
				comm.i_confirm(comm.lang("companyInfo").confirmDelBank, function(){
					dataModoule.delBank({accId:bank.accId}, function(res){
						comm.alert({content:comm.lang("companyInfo")[22000], callOk:function(){
							$('#qyxx_yhzhxx').click();
						}});
					});
				}, 320);
			});
		}
	}
}); 

 