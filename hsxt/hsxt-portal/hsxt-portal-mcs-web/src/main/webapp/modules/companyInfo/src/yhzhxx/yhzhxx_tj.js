define(['text!companyInfoTpl/yhzhxx/yhzhxx_tj.html',
        'companyInfoDat/yhzhxx/yhzhxx',
		'companyInfoLan'],function(yhzhxxtjTpl, dataModoule){
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
			$('#contentWidth_4').html(_.template(yhzhxxtjTpl));
			comm.initProvSelect('#provinceNo', {}, 88, null);
			comm.initCitySelect('#cityNo', {}, 88, null);
			$("#accountName").val(comm.getRequestParams().custEntName);
			$('#btn_back').triggerWith('#qyxx_yhzhxx');
			$('#submitBt_addAccount').click(function(e) {
                self.saveBank();
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			//初始化省份
			cacheUtil.findCacheSystemInfo(function(sysRes){
				$('#countryCode').val(sysRes.countryNo);
				$('#currencyName').val(sysRes.currencyNameCn);
				cacheUtil.findCacheProvinceByParent(sysRes.countryNo, function(provArray){
					comm.initProvSelect('#provinceNo', provArray, 80, null).change(function(e){
						cacheUtil.findCacheCityByParent(sysRes.countryNo, $(this).attr('optionValue'), function(cityArray){
							comm.initCitySelect('#cityNo', cityArray, 80).selectListIndex(0);
						});
					});
				});
			});
			//初始化银行列表
			cacheUtil.findCacheBankAll(function(bankRes){
				comm.initBankCombox("#bankCode", bankRes, null, true);
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
						required : comm.lang("companyInfo")[33248],
					},
					accountNo : {
						required : comm.lang("companyInfo")[33251],
						bankNo : comm.lang("companyInfo")[33252],
					},
					accountNoCom : {
						required : comm.lang("companyInfo")[33253],
						bankNo : comm.lang("companyInfo")[33252],
						equalTo: comm.lang("companyInfo")[33254],
					},
					provinceNo : {
						required : comm.lang("companyInfo")[33249],
					},
					cityNo : {
						required : comm.lang("companyInfo")[33250],
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
								my : "left+30",
								at : "top+16"
							}
						}).tooltip("open");
					}else if($(element).attr('name') == 'cityNo'){
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+78",
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
								my : "left+128",
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

 