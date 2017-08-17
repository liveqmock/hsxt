define(['text!coDeclarationTpl/kqxtyw/qyyhzhxx.html',
        'text!coDeclarationTpl/kqxtyw/confirm_dialog.html',
        'coDeclarationDat/kqxtyw/qyyhzhxx',
		'coDeclarationLan'], function(qyyhzhxxTpl, confirm_dialogTpl, dataModoule){
	return{
		showPage : function(){
		 	this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			var self = this;
			
			$("#qyyhzhxxTpl1").show();
			$("#qyyhzhxxTpl2").hide();
			
			//点击查看页面修改按钮
			$('#qyyhzhxx_modify').click(function(){
				$("#qyyhzhxxTpl1").hide();
				$("#qyyhzhxxTpl2").show();
			});
			//点击查看页面取消按钮
			$('#qyyhzhxx_cancel').triggerWith('#'+$("#menuName").val());
			//点击修改页面提交按钮
			$('#qyyhzhxx_xg_btn').click(function(){
				self.initChangeData();
			});
			//点击修改页面取消按钮
			$('#back_qyyhzhxx').click(function(){
				$("#qyyhzhxxTpl1").show();
				$("#qyyhzhxxTpl2").hide();
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var self = this;
			dataModoule.findBankByApplyId({"applyId": $("#applyId").val()}, function(res){
				self.initShowForm(res.data);
			});
		},
		/**
		 * 初始化显示页面
		 * @param data 注册信息
		 */
		initShowForm : function(data){
			comm.delCache('coDeclaration', 'kqxtyw-qyyhzhxx-info');
			comm.setCache('coDeclaration', 'kqxtyw-qyyhzhxx-info', data);
			$('#infobox').html(_.template(qyyhzhxxTpl, data));
			var prov = null;
			var city = null;
			var comboxVal = null;
			if(data.bank){
				if(data.bank.isDefault == null){
					$("#isdefaultText").html("");
				}else if(!data.bank.isDefault){
					$("input[name=isdefault]:eq(0)").attr("checked",'checked');
					$("#isdefaultText").html("否");
				}else{
					$("input[name=isdefault]:eq(1)").attr("checked",'checked');
					$("#isdefaultText").html("是");
				}
				prov = data.bank.provinceNo;
				city = data.bank.cityNo;
				comboxVal = data.bank.bankCode;
				$("#accountNoText").html(comm.hideCard(data.bank.accountNo));
				
				//平台信息
				cacheUtil.findCacheSystemInfo(function(sysRes){
					$('#currencyText-1').html(sysRes.currencyNameCn);
					$('#currencyText-2').html(sysRes.currencyNameCn);
					//获取地区信息
					cacheUtil.syncGetRegionByCode(null, data.bank.provinceNo, data.bank.cityNo, "", function(resdata){
						$("#placeName").html(resdata);
					});
				});
				
				//初始化银行列表
				cacheUtil.findCacheBankAll(function(bankRes){
					$("#bankCodeText").html(comm.getBankNameByCode(data.bank.bankCode, bankRes));
				});	
			}
			
			//初始化省份
			cacheUtil.findCacheSystemInfo(function(sysRes){
				cacheUtil.findCacheProvinceByParent(sysRes.countryNo, function(provArray){
					comm.initProvSelect('#provinceNo', provArray, 224, prov).change(function(e){
						cacheUtil.findCacheCityByParent(sysRes.countryNo, $(this).attr('optionValue'), function(cityArray){
							comm.initCitySelect('#cityNo', cityArray, 224).selectListIndex(0);
						});
					});
				});
			});

			//初始化城市
			if(prov){
				cacheUtil.findCacheSystemInfo(function(sysRes){
					cacheUtil.findCacheCityByParent(sysRes.countryNo, prov, function(cityArray){
						comm.initCitySelect('#cityNo', cityArray, 224, city);
					});
				});
			}
			
			//初始化银行列表
			cacheUtil.findCacheBankAll(function(bankRes){
				comm.initBankCombox("#bankCode", bankRes, comboxVal, true);
			});	
			
			this.initForm();
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			var validate = $("#qyyhzhxx_form").validate({
				rules : {
					accountName : {
						required : true,
					},
					currencyName : {
						required : true,
					},
					bankCode : {
						required : true,
					},
					provinceNo : {
						required : true,
					},
					cityNo : {
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
						bankNo : comm.lang("coDeclaration")[36044],
					},
					accountNoCom : {
						required : comm.lang("coDeclaration").accountNoComIsNull,
						bankNo : comm.lang("coDeclaration")[36044],
						equalTo: comm.lang("coDeclaration")[36045],
					},
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+2 top+30",
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
		 * 表单校验
		 */
		validateViewMarkData : function(){
			return $("#confirm_dialog_form").validate({
				rules : {
					viewMark : {
						rangelength : [0, 300]
					},
					doubleUserName : {
						required : true,
					},
					doublePassword : {
						required : true,
					},
				},
				messages : {
					viewMark : {
						rangelength : comm.lang("coDeclaration")[36006]
					},
					doubleUserName : {
						required : comm.lang("coDeclaration")[36047]
					},
					doublePassword : {
						required : comm.lang("coDeclaration")[36048]
					},
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+2 top+30",
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
		},
		/**
		 * 初始化提交页面
		 */
		initChangeData : function(){
			var self = this;
			if(!self.validateData().form()){
				return;
			}
			var odata = comm.getCache('coDeclaration', 'kqxtyw-qyyhzhxx-info');
			var ndata = comm.cloneJSON(odata);
			var trs = "";
			var chg = {};
			
			//初始化银行列表
			cacheUtil.findCacheBankAll(function(bankRes){
				//银行账号
				var accountNo = $("#accountNo").val();
				if(accountNo != comm.removeNull(odata.bank.accountNo)){
					trs+="<tr><td class=\"view_item\">银行账号</td><td class=\"view_text\">"+comm.removeNull(odata.bank.accountNo)+"</td><td class=\"view_text\">"+accountNo+"</td></tr>";
					if(odata.bank.accountId == null){//为空是新增，反之为修改
						chg.accountNo = {"new":accountNo};
					}else{
						chg.accountNo = {"old":comm.removeNull(odata.bank.accountNo), "new":accountNo};
					}
					ndata.bank.accountNo = accountNo;
				}
				
				//开户银行
				var bankCode = $("#bankCode").val();
				if(bankCode != comm.removeNull(odata.bank.bankCode)){
					var oBankName = comm.getBankNameByCode(odata.bank.bankCode, bankRes);
					var nBankName = comm.getBankNameByCode(bankCode, bankRes); 
					trs+="<tr><td class=\"view_item\">开户银行</td><td class=\"view_text\">"+oBankName+"</td><td class=\"view_text\">"+nBankName+"</td></tr>";
					if(odata.bank.accountId == null){//为空是新增，反之为修改
						chg.bankCode = {"new":bankCode};
					}else{
						chg.bankCode = {"old":comm.removeNull(odata.bank.bankCode), "new":bankCode};
					}
					ndata.bank.bankCode = bankCode;
				}
				
				//开户地区
				var provNo = $("#provinceNo").attr('optionValue');
				var cityNo = $("#cityNo").attr('optionValue');
				if((provNo+cityNo) != (comm.removeNull(odata.bank.provinceNo)+comm.removeNull(odata.bank.cityNo))){
					//获取开户地区
					var oResData = cacheUtil.getRegionByCode(null, odata.bank.provinceNo, odata.bank.cityNo, "");
					if(!oResData.flag){
						return;
					}
					var nResData = cacheUtil.getRegionByCode(null, provNo, cityNo, "");
					if(!nResData.flag){
						return;
					}
					var oPlaceName = oResData.data;
					var nPlaceName = nResData.data;
					
					trs+="<tr><td class=\"view_item\">开户地区</td><td class=\"view_text\">"+oPlaceName+"</td><td class=\"view_text\">"+nPlaceName+"</td></tr>";
					if(ndata.bank.provinceNo != provNo){
						if(odata.bank.accountId == null){//为空是新增，反之为修改
							chg.provinceNo = {"new":provNo};
						}else{
							chg.provinceNo = {"old":comm.removeNull(odata.bank.provinceNo), "new":provNo};
						}
					}
					if(ndata.bank.cityNo != cityNo){
						if(odata.bank.accountId == null){//为空是新增，反之为修改
							chg.cityNo = {"new":cityNo};
						}else{
							chg.cityNo = {"old":comm.removeNull(ndata.bank.cityNo), "new":cityNo};
						}
					}
					ndata.bank.provinceNo = provNo;
					ndata.bank.cityNo = cityNo;
				}
				
				//默认银行账号
				var isdefault = $('input[name="isdefault"]:checked').val();
				isdefault = (isdefault == undefined)?"":isdefault;
				if(isdefault != ""){
					var itemNum = (odata.bank.isDefault == null)?"":(odata.bank.isDefault?"0":"1");
					var oIsDefault = comm.getNameByEnumId(itemNum, comm.lang("coDeclaration").whetherEnum);
					var nIsDefault = comm.getNameByEnumId(isdefault, comm.lang("coDeclaration").whetherEnum);
					if(oIsDefault != nIsDefault){
						trs+="<tr><td class=\"view_item\">默认银行账号</td><td class=\"view_text\">"+oIsDefault+"</td><td class=\"view_text\">"+nIsDefault+"</td></tr>";
						if(odata.bank.accountId == null){//为空是新增，反之为修改
							chg.isDefault = {"new": isdefault=="0"};
						}else{
							chg.isDefault = {"old":odata.bank.isDefault, "new": isdefault=="0"};
						}
						ndata.bank.isDefault = isdefault=="0";
					}
				}
				if(trs == ""){
					comm.warn_alert(comm.lang("coDeclaration").noUpdate);
					return;
				}
				//提交
				$('#qyyhzhxx_dialog > p').html(_.template(confirm_dialogTpl));
				$('#copTable tr:eq(1)').before(trs);
				self.initVerificationMode();
				$('#qyyhzhxx_dialog').dialog({
					width:800,
					buttons:{
						'确定':function(){
							if(!self.validateViewMarkData().form()){
								return;
							}
							//验证双签
							comm.verifyDoublePwd($("#doubleUserName").val(), $("#doublePassword").val(), 1, function(verifyRes){
								var bank = ndata.bank;
								bank.applyId = $("#applyId").val();
								bank.accountName = ndata.accountName;
								bank.countryNo = ndata.countryNo;
								bank.currencyCode = ndata.currencyCode;
								bank.verifyDouble = "true";
								bank.dblOptCustId = verifyRes.data;
								bank.optRemark = $("#viewMark").val();
								bank.changeContent = JSON.stringify(chg);
								dataModoule.saveBankInfo(bank, function(res){
									comm.alert({content:comm.lang("coDeclaration")[22000], callOk:function(){
										$('#qyyhzhxx_dialog').dialog('destroy');
										bank.accountId = res.data;
										self.initShowForm(ndata);
									}});
								});
							});
						},
						'取消':function(){
							$(this).dialog('destroy');
						}
					}
				});
			});	
		},
		/**
		 * 初始化验证方式
		 */
		initVerificationMode : function(){
			comm.initSelect("#verificationMode", comm.lang("coDeclaration").verificationMode, null, '1').change(function(e){
				var val = $(this).attr('optionValue');
				switch(val){
					case '1':
						$('#fhy_userName, #fhy_password').removeClass('none');
						$('#verificationMode_prompt').addClass('none');
						break;	
					case '2':
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').removeClass('none');
						break;
					case '3':
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').removeClass('none');
						break;
				}
			});
		}
	}
});