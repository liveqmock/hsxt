define(['text!coDeclarationTpl/qyxz/qyyhzhxx.html',
        'coDeclarationDat/qyxz/qyyhzhxx',
        'coDeclarationLan'],function(qyyhzhxxTpl, dataModoule){
	return {
		showPage : function(){
			if(this.getApplyId() == null){
				$('#busibox').html(_.template(qyyhzhxxTpl));
				this.initForm("");
			}else{
				this.initData();
			}
		},
		/**
		 * 获取申请编号
		 */
		getApplyId : function(){
			return comm.getCache("coDeclaration", "entDeclare").applyId;
		},
		/**
		 * 初始化页面
		 */
		initForm : function(comboxVal, prov, city){
			var self = this;
			
			comm.initProvSelect('#provinceNo', {}, 224);
			comm.initCitySelect('#cityNo', {}, 224);
			
			//初始化省份
			cacheUtil.findCacheSystemInfo(function(sysRes){
				cacheUtil.findCacheProvinceByParent(sysRes.countryNo, function(provArray){
					comm.initProvSelect('#provinceNo', provArray, 224, prov).change(function(e){
						cacheUtil.findCacheCityByParent(sysRes.countryNo, $(this).attr('optionValue'), function(cityArray){
							comm.initCitySelect('#cityNo', cityArray, 224).selectListIndex(0);
						});
					});
				});
				//初始化城市
				if(prov){
					cacheUtil.findCacheCityByParent(sysRes.countryNo, prov, function(cityArray){
						comm.initCitySelect('#cityNo', cityArray, 224, city);
					});
				}
			});
			
			//下一步 
			$('#qyyhzhxx_next').click(function(){
				self.saveBankInfo(true);
			});
			
			//保存
			$('#qyyhzhxx_save').click(function(){
				self.saveBankInfo();
			});
			//判断页面是否有更新（编辑时候用）
			$("#qyyhzhxx_form :input").change(function() {
				comm.setCache("coDeclaration", "updateFlag", true);
			});
			//初始化银行列表
			cacheUtil.findCacheBankAll(function(bankRes){
				comm.initBankCombox("#bankCode", bankRes, comboxVal, true);
			});
			
			//处理返回按钮
			if ($('#050100000000_subNav_050101000000').hasClass('s_hover')){
				$('#qyyhzhxx_back').hide();
			} else {
				$('#qyyhzhxx_back').show();
			}
			$('#qyyhzhxx_back').click(function(){
				$('#050100000000_subNav_050103000000').click();
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var self = this;
			var params = {
				applyId: this.getApplyId()
			};
			dataModoule.findBankByApplyId(params, function(res){
				if(res.data){
					$('#busibox').html(_.template(qyyhzhxxTpl, res.data));
					if(res.data.bank){
						self.initForm(res.data.bank.bankCode, res.data.bank.provinceNo, res.data.bank.cityNo);
						if(res.data.bank.isDefault != null){
							if(!res.data.bank.isDefault){
								$("input[name=isdefault]:eq(0)").attr("checked", 'checked');
							}else{
								$("input[name=isdefault]:eq(1)").attr("checked", 'checked');
							}
						}
					}else{
						self.initForm("", "", "");
					}
					comm.delCache("coDeclaration", "updateFlag");
				}else{
					self.initForm("", "", "");
				}
			});
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			 var validate = $("#qyyhzhxx_form").validate({
				rules : {
					accountName : {
						required : true
					},
					currencyName : {
						required : true
					},
					bankCode : {
						required : true
					},
					provinceNo : {
						required : true
					},
					cityNo : {
						required : true
					},
					accountNo : {
						required : true,
						bankNo : true
					},
					accountNoCom : {
						required : true,
						bankNo : true,
						equalTo: "#accountNo"
					}
				},
				messages : {
					accountName : {
						required : comm.lang("coDeclaration")[36042]
					},
					currencyName : {
						required : comm.lang("coDeclaration")[36043]
					},
					bankCode : {
						required : comm.lang("coDeclaration")[36352]
					},
					provinceNo : {
						required : comm.lang("coDeclaration")[36094]
					},
					cityNo : {
						required : comm.lang("coDeclaration")[36095]
					},
					accountNo : {
						required : comm.lang("coDeclaration")[22043],
						bankNo : comm.lang("coDeclaration")[36044]
					},
					accountNoCom : {
						required : comm.lang("coDeclaration").accountNoComIsNull,
						bankNo : comm.lang("coDeclaration")[36044],
						equalTo: comm.lang("coDeclaration")[36045]
					},
				},
				errorPlacement : function (error, element) {
					if ($(element).attr('name') == 'bankCode') {
						element = element.parent();
						
					}
					if($(element).attr('name') == 'provinceNo' || $(element).attr('name') == 'cityNo'){
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+120",
								at : "top+15"
							}
						}).tooltip("open");
					}
					else{
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+240",
								at : "top+15"
							}
						}).tooltip("open");	
					}
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
			return validate;
		},
		/**
		 * 企业申报-保存银行账户信息
		 */
		saveBankInfo : function(autoNext){
			var slef = this;
			if(!this.validateData().form()){
				return;
			}
			var params = $("#qyyhzhxx_form"). serializeJson();
			params.applyId = this.getApplyId();
			params.bankCode = $("#bankCode").val();
			params.provinceNo = $("#provinceNo").attr('optionValue');
			params.cityNo = $("#cityNo").attr('optionValue');
			params.isDefault = params.isdefault=="1";
			dataModoule.saveBankInfo(params, function(res){
				slef.saveStep(4);
				comm.delCache("coDeclaration", "updateFlag");
				$("#accountId").val(res.data);
				if(autoNext){
					$('#qysczl').click();
				}else{
					comm.alert({content:comm.lang("coDeclaration")[22000], callOk:function(){
						
					}});
				}
			});
		},
		/**
		 * 控制步骤
		 * @param curStep 当前步骤
		 */
		saveStep : function(curStep){
			var entDeclare = comm.getCache("coDeclaration", "entDeclare");
			if((entDeclare.curStep) <= curStep){
				entDeclare.curStep = curStep;
			}
		}
	}
}); 

 