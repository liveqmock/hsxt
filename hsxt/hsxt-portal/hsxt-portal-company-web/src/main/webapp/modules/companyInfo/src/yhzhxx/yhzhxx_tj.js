define(['text!companyInfoTpl/yhzhxx/yhzhxx_tj.html',
        'companyInfoDat/yhzhxx/yhzhxx',
		'companyInfoLan'],function(yhzhxxtjTpl, dataModoule){
	return {
		showPage : function(){
			this.initForm();
			this.initData();
			this.initEntData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			var self = this;
			$('#contentWidth_4').html(_.template(yhzhxxtjTpl));
			comm.initProvSelect('#provinceNo', {}, 225, null);
			comm.initCitySelect('#cityNo', {}, 225, null);
			$('#btn_back').triggerWith('#qyxx_yhzhxx');
			$('#submitBt_addAccount').click(function(e) {
                self.saveBank();
			});
		},
		/**
		 * 初始化企业名称数据
		 */
		initEntData : function(){
			dataModoule.findEntAllInfo(null, function(res){
				comm.setCache("companyInfo", "entAllInfo", res.data);
				comm.setCookie('entName',res.data.entCustName);
				comm.setCookie('custEntName',res.data.entCustName);
				$("#accountName").val(res.data.entCustName);
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			//初始化省份
			cacheUtil.findCacheLocalInfo(function(sysRes){
				$('#countryCode').val(sysRes.countryNo);
				$('#currencyName').val(sysRes.currencyNameCn);
				cacheUtil.findCacheProvinceByParent(sysRes.countryNo, function(provArray){
					comm.initProvSelect('#provinceNo', provArray, 225, null).change(function(e){
						cacheUtil.findCacheCityByParent(sysRes.countryNo, $(this).attr('optionValue'), function(cityArray){
							comm.initCitySelect('#cityNo', cityArray, 225, null).selectListIndex(0);
						});
					});
				});
			});
			//初始化银行列表
			cacheUtil.findCacheBankAll(function(bankRes){
				comm.initBankCombox("#bankCode", bankRes, "", true);
			});
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			var validate = $("#qyyhzhxx_form").validate({
				rules : {
					bankCode : {
						required : true,
					},
					accountNo : {
						required : true,
						bankNo : true,
						rangelength : [5, 30]		//最大长度
					},
					accountNoCom : {
						required : true,
						bankNo : true,
						equalTo: "#accountNo",
					},
					provinceNo : {
						required : true
					},
					cityNo : {
						required : true
					},
				},
				messages : {
					bankCode : {
						required : comm.lang("companyInfo")[31059],
					},
					accountNo : {
						required : comm.lang("companyInfo")[31060],
						bankNo : comm.lang("companyInfo")[31061],
						rangelength : comm.lang("companyInfo").BankRangeLength
					},
					accountNoCom : {
						required : comm.lang("companyInfo")[31062],
						bankNo : comm.lang("companyInfo")[31061],
						equalTo: comm.lang("companyInfo")[31063],
					},
					provinceNo : {
						required : comm.lang("companyInfo")[33251],
					},
					cityNo : {
						required : comm.lang("companyInfo")[33252],
					},
				},
				
				errorPlacement : function (error, element) {
					if ($(element).attr('name') == 'bankCode') {
						element = element.parent();
					}
					if($(element).attr('name') == 'provinceNo'){
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+4",
								at : "top+16"
							}
						}).tooltip("open");
					}else if($(element).attr('name') == 'cityNo'){
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+136",
								at : "top+16"
							}
						}).tooltip("open");
					}
					else{
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+256",
								at : "top+16"
							}
						}).tooltip("open");	
					}
					
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					if ($(element).attr('name') == 'brandSelect_add') {
						$(element.parent()).tooltip();
						$(element.parent()).tooltip("destroy");
					} else {
						$(element).tooltip();
						$(element).tooltip("destroy");
					}
				}
				
			});
			return validate;
		},
		/**
		 * 保存银行卡
		 */
		saveBank : function(){
			var self = this;
			if(!self.validateData().form()){
				return;
			}
			var params = {};
			params.bankCode = $("#bankCode").val();
			params.bankAccNo = $("#accountNo").val();
			params.isDefaultAccount = comm.removeNull($('input[name="isdefault"]:checked').val());
			params.countryNo = $("#countryCode").val();
			params.provinceNo = comm.removeNull($("#provinceNo").attr('optionValue'));
			params.cityNo = comm.removeNull($("#cityNo").attr('optionValue'));
			params.bankAccName = comm.getRequestParams().custEntName;
			params.resNo = $.cookie('pointNo');
			//获取银行名称
			cacheUtil.findCacheBankAll(function(bankRes){
				params.bankName = comm.getBankNameByCode(params.bankCode, bankRes);
				dataModoule.addBank(params, function(){
					comm.alert({content:comm.lang("companyInfo")[22000], callOk:function(){
						$('#qyxx_yhzhxx').click();
					}});
				});
			});
		}
	}
}); 

 