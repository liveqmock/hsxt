define(['text!coDeclarationTpl/csyw/ckxq/qyyhzhxx.html',
		'text!coDeclarationTpl/csyw/ckxq/qyyhzhxx_dialog.html',
		'coDeclarationDat/csyw/ckxq/qyyhzhxx',
		'coDeclarationLan'],function(qyyhzhxxTpl, qyyhzhxx_dialogTpl, dataModoule){
	return {	 	
		showPage : function(){
			this.initData();
			this.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			var self = this;
			$('#ckxq_xg').css('display','');
		 	//取消		 
		 	$('#ckxq_qx').click(function(){		 		
		 		if($('#ckxq_xg').text() == '保　存' ){	 			 			
		 			$('#ckxq_xg').text('修　改');
		 			$("#ckxq_qx").text('返　回');
		 			$('#qyyhzhxx_1').removeClass('none');
		 			$('#qyyhzhxx_2').addClass('none');
		 			$("#skqr_tj").show();
		 		} else {
		 			$('#cx_content_list').removeClass('none');
		 			$('#cx_content_detail').addClass('none');
		 		}		 		
		 	});
		 
		 	//修改
		 	$('#ckxq_xg').click(function(){	
		 		if ($(this).text() !='保　存'){
		 			$('#qyyhzhxx_1').addClass('none');
		 			$('#qyyhzhxx_2').removeClass('none');
		 			$(this).text('保　存');
		 			$("#ckxq_qx").text('取　消');
		 			$("#skqr_tj").hide();
		 		} else {
		 			self.initChangeData();
		 		}		 		
		 	}); 
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var self = this;
			dataModoule.findBankByApplyId({"applyId": $("#applyId").val()}, function(res){
				self.initShowForm(res.data, false);
			});
		},
		/**
		 * 初始化显示页面
		 * @param data 注册信息
		 * @param isCallBack 是否是回调成功
		 */
		initShowForm : function(data, isCallBack){
			comm.delCache('coDeclaration', 'csyw-qyyhzhxx-info');
			comm.setCache('coDeclaration', 'csyw-qyyhzhxx-info', data);
			$('#ckxq_view').html(_.template(qyyhzhxxTpl, data));
			
			comm.initProvSelect('#provinceNo', {}, 80, null);
			comm.initCitySelect('#cityNo', {}, 80, null,null,200);
			
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
				
				//获取开户地区
				cacheUtil.syncGetRegionByCode(null, data.bank.provinceNo, data.bank.cityNo, "", function(resdata){
					$("#placeName").html(resdata);
				});
				
				//获取开户行
				cacheUtil.findCacheBankAll(function(bankRes){
					$("#bankCodeText").html(comm.getBankNameByCode(data.bank.bankCode, bankRes));
				});
			}
			
			//初始化省份
			cacheUtil.findCacheSystemInfo(function(sysRes){
				cacheUtil.findCacheProvinceByParent(sysRes.countryNo, function(provArray){
					comm.initProvSelect('#provinceNo', provArray, 80, prov,null,200).change(function(e){
						cacheUtil.findCacheCityByParent(sysRes.countryNo, $(this).attr('optionValue'), function(cityArray){
							comm.initCitySelect('#cityNo', cityArray, 80,null,null,200).selectListIndex(0);
						});
					});
				});
			});
			
			//初始化城市
			if(prov){
				cacheUtil.findCacheSystemInfo(function(sysRes){
					cacheUtil.findCacheCityByParent(sysRes.countryNo, prov, function(cityArray){
						comm.initCitySelect('#cityNo', cityArray, 80, city,null,200);
					});
				});
			}
			
			//初始化银行列表
			cacheUtil.findCacheBankAll(function(bankRes){
				comm.initBankCombox("#bankCode", bankRes, comboxVal, true);
			});
			
			if(isCallBack){
				$('#ckxq_xg').text('修　改');
				$('#ckxq_xg').click();
				$("#skqr_tj").show();
			}
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			var validate = $("#qyyhzhxx_form").validate({
				rules : {
					accountName : {
						required : false
					},
					currencyName : {
						required : false
					},
					bankCode : {
						required : true
					},
					provinceNo : {
						required : true
					},
					cityNo : {
						required : true,
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
						required : comm.lang("coDeclaration")[33246]
					},
					currencyName : {
						required : comm.lang("coDeclaration")[33247]
					},
					bankCode : {
						required : comm.lang("coDeclaration")[33248]
					},
					provinceNo : {
						required : comm.lang("coDeclaration")[33249]
					},
					cityNo : {
						required : comm.lang("coDeclaration")[33250]
					},
					accountNo : {
						required : comm.lang("coDeclaration")[33251],
						bankNo : comm.lang("coDeclaration")[33252]
					},
					accountNoCom : {
						required : comm.lang("coDeclaration")[33253],
						bankNo : comm.lang("coDeclaration")[33252],
						equalTo: comm.lang("coDeclaration")[33254]
					}
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
			return $("#qyyhzhxx_dialog_form").validate({
				rules : {
					viewMark : {
						rangelength : [0, 300]
					},
					doubleUserName : {
						required : true
					},
					doublePassword : {
						required : true
					}
				},
				messages : {
					viewMark : {
						rangelength : comm.lang("coDeclaration")[33204]
					},
					doubleUserName : {
						required : comm.lang("coDeclaration")[33255]
					},
					doublePassword : {
						required : comm.lang("coDeclaration")[33256]
					}
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
		 * 初始化保存页面
		 */
		initChangeData : function(){
			var self = this;
			if(!self.validateData().form()){
				return;
			}
			cacheUtil.findCacheBankAll(function(bankRes){
				var odata = comm.getCache('coDeclaration', 'csyw-qyyhzhxx-info');
				var ndata = comm.cloneJSON(odata);
				var trs = "";
				var chg = {};
				
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
				//保存
				$('#qyyhzhxx_dialog > p').html(_.template(qyyhzhxx_dialogTpl));
				$('#copTable tr:eq(1)').before(trs);
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
								bank.dblOptCustId = verifyRes.data;
								bank.applyId = $("#applyId").val();
								bank.accountName = ndata.accountName;
								bank.countryNo = ndata.countryNo;
								bank.currencyCode = ndata.currencyCode;
								bank.optRemark = $("#viewMark").val();
								bank.changeContent = JSON.stringify(chg);
								dataModoule.saveBankInfo(bank, function(res){
									comm.alert({content:comm.lang("coDeclaration")[22000], callOk:function(){
										$('#qyyhzhxx_dialog').dialog('destroy');
										bank.accountId = res.data;
										self.initShowForm(ndata, true);
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
		}
	}
}); 
