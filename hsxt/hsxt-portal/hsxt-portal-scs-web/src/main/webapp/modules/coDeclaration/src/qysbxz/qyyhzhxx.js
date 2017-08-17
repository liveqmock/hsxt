define(['text!coDeclarationTpl/qysbxz/qyyhzhxx.html',
        'coDeclarationDat/qysbxz/qyyhzhxx',
        'coDeclarationLan'],function(qyyhzhxxTpl, dataModoule){
	return { 	
		showPage : function(){
			if(this.getApplyId() == null){
				$('#contentWidth_2').html(_.template(qyyhzhxxTpl));
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
			//处理返回按钮
			if ($('#030200000000_subNav_030201000000').hasClass('s_hover')){
				$('#qyyhzhxx_fh').hide();
			} else {
				$('#qyyhzhxx_fh').show();
			}
			$('#qyyhzhxx_fh').click(function(){
				$('#030200000000_subNav_030202000000').click();
			});
			
			comm.initProvSelect('#provinceNo', {}, 220);
			comm.initCitySelect('#cityNo', {}, 220);
		
			//初始化省份
			cacheUtil.findCacheSystemInfo(function(sysRes){
				cacheUtil.findCacheProvinceByParent(sysRes.countryNo, function(provArray){
					comm.initProvSelect('#provinceNo', provArray, 220, prov).change(function(e){
						cacheUtil.findCacheCityByParent(sysRes.countryNo, $(this).attr('optionValue'), function(cityArray){
							comm.initCitySelect('#cityNo', cityArray, 220).selectListIndex(0);
						});
					});
				});
				//初始化城市
				if(prov){
					cacheUtil.findCacheSystemInfo(function(sysRes){
						cacheUtil.findCacheCityByParent(sysRes.countryNo, prov, function(cityArray){
							comm.initCitySelect('#cityNo', cityArray, 220, city);
						});
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
			
			//初始化银行列表
			cacheUtil.findCacheBankAll(function(bankRes){
				comm.initBankCombox("#bankCode", bankRes, comboxVal, true, "您输入的开户银行不存在");
				
				//判断页面是否有更新（编辑时候用）
				//$("#qyyhzhxx_form :input").change(function() {
					//comm.setCache("coDeclaration", "updateFlag", true);
				//});
				
//				self.initBankSelect("#bankCode", bankRes, 180, null);
			});
			
			
			
			$(".custom-combobox-input").live("focus",function(){
				var value = $("#bankCode").val();
				if(value == " "){
					
					$(".custom-combobox-input").val("");
				}
			});
			
			$(".custom-combobox-input").live("focusout",function(){
				var value = $("#bankCode").val();
				if(value == " "){
					$("#bankCode").selectListValue(" ");
				}
				if(value != comboxVal){
					comm.setCache("coDeclaration", "updateFlag", true);
				}
			});
			
		},
		initBankSelect : function(objId, bankArray, width, defaultVal){
			var options = [];
			if(bankArray != null){
				for(var key in bankArray){
					options.push({name:bankArray[key].bankName, value:bankArray[key].bankNo});
				}
			}
			var content = {options : options};
			if(width){
				content.width = width;
				content.optionWidth = width;
			}
			var select = $(objId).selectList(content);
			if(defaultVal != null && defaultVal != undefined){
				select.selectListValue(defaultVal);
			}
			return select;
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
					$('#contentWidth_2').html(_.template(qyyhzhxxTpl, res.data));
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
				}else{
					self.initForm("", "", "");
				}
			});
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			$.validator.addMethod("bankCodeRequired",function(value,element){
                var score = /^\w+$/;
                return this.optional(element) || (score.test(value));
            },"");
			 var validate = $("#qyyhzhxx_form").validate({
				rules : {
					accountName : {
						required : false
					},
					currencyName : {
						required : false
					},
					bankCode : {
						required : true,
						bankCodeRequired : true
					},
					provinceNo : {
						required : true
					},
					cityNo : {
						required : function(){
							return comm.isNotEmpty($("#provinceNo").val());
						}
					},
					accountNo : {
						required : true,
						bankNo : true,
					},
					accountNoCom : {
						required : true,
						bankNo : true,
						equalTo: "#accountNo"
					},
				},
				messages : {
					accountName : {
						required : comm.lang("coDeclaration")[32518]
					},
					currencyName : {
						required : comm.lang("coDeclaration")[32520]
					},
					bankCode : {
						required : comm.lang("coDeclaration")[32667],
						bankCodeRequired : comm.lang("coDeclaration")[32667]
					},
					provinceNo : {
						required : comm.lang("coDeclaration")[32664]
					},
					cityNo : {
						required : comm.lang("coDeclaration")[32665]
					},
					accountNo : {
						required : comm.lang("coDeclaration")[32516],
						bankNo : comm.lang("coDeclaration")[32517]
					},
					accountNoCom : {
						required : comm.lang("coDeclaration")[32668],
						bankNo : comm.lang("coDeclaration")[32517],
						equalTo: comm.lang("coDeclaration")[32669]
					}
				},
				errorPlacement : function (error, element) {
					$(element).parent().attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+500 top+0",
							at : "left top"
						}
					}).tooltip("open");
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
					$('#qysbxz_qysczl').click();
				}else{
					comm.alert({content:comm.lang("coDeclaration")[21000], callOk:function(){
						
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
