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
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var self = this;
			dataModoule.findBanksByCustId(null, function(res){
				var list = res.data;
				$("#add_bank").show();
				if(list){
					if(list.length > 4){
						$("#add_bank").hide();
						$("#left_bank_count").html(0);
					}else{
						$("#left_bank_count").html(5-list.length);
					}
					cacheUtil.findCacheSystemInfo(function(sysRes){
						//币种
						var currencyNameCn = sysRes.currencyNameCn;
						//初始化银行列表
						cacheUtil.findCacheBankAll(function(bankRes){
							for(var key in list){
								if(!isNaN(key)){
									var bankName = comm.getBankNameByCode(list[key].bankCode, bankRes);
									self.addBank(currencyNameCn, list[key], bankName);
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
			var li = $("#bank_ul").html();
			var fullname = comm.removeNull(bankName);
			var shortName = this.getShortName(fullname,24);
			li = li.replace("bankName", shortName);
			
			/** by wangjg 2016-03-10  作用：显示银行log*/
			//li = li.replace("bankAccNo", comm.hideCard(bank.bankAccNo));
			li = li.replace("bankCode",bank.bankCode);
			
			//默认银行
			li = li.replace("defaultBankHtml",
							(bank.isDefaultAccount == 1) ? "<i class=\"default_bank\"></i>": "");
			//截取末尾四位
			li = li.replace("bankAccNo", bank.bankAccNo.substring(bank.bankAccNo.length-4));
			
			/** by wangjg 2016-03-10  作用：显示银行log*/
			
			li = li.replace("currencyName", comm.removeNull(currencyNameCn));
			
			//需要更改，默认银行账户可以删除 by likui
			/*if((bank.isDefaultAccount == 1)){
				li = li.replace("option", '<a id="'+detailId+'" class="detailBt_yinghang">详情</a> | <a class="deleteBt_yinghang">删除</a>');
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
				comm.delCache("companyInfo", "detailBank");
				comm.setCache("companyInfo", "detailBank", bank);
				$("#qyxx_yhzhxxsc").click();	
				
				/*comm.i_confirm(comm.lang("companyInfo").confirmDelBank, function(){
					dataModoule.delBank({accId:bank.accId}, function(res){
						comm.alert({content:comm.lang("companyInfo")[22000], callOk:function(){
							$('#qyxx_yhzhxx').click();
						}});
					});
				}, 320);*/
			});
		}
	}
}); 

 