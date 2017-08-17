define(['text!infoManageTpl/glqyxxgl/qyyhzhxx.html',
		/*'text!infoManageTpl/glqyxxgl/qyyhzhxx_xg.html',
		'text!infoManageTpl/glqyxxgl/qyyhzhxx_xg_dialog.html',*/
		'text!infoManageTpl/glqyxxgl/qyyhzhxx_xq.html',
		'text!infoManageTpl/glqyxxgl/qyyhzhxx_tj.html',
		'text!infoManageTpl/glqyxxgl/qyyhzhxx_tj_dialog.html',
		'text!infoManageTpl/glqyxxgl/qyyxzhxx_del_double_sign_dialog.html',
		'infoManageDat/glqyxxgl/yhzhxx'
		], function(qyyhzhxxTpl, qyyhzhxx_xqTpl, qyyhzhxx_tjTpl, qyyhzhxx_tj_dialogTpl,qyyxzhxx_del_double_sign_dialogTpl,dataModoule){
	var self = {
		showPage : function(obj){
			this.initForm(obj);
			this.initData(obj);
		},
		/**
		 * 初始化界面
		 */
		initForm : function(obj){
			//var self = this;
			$('#infobox').html(_.template(qyyhzhxxTpl));
			
			$('#qyyhzhxx_cancel').click(function(){
				$("#glqyxxwhTpl").removeClass("none");
				$('#glqyxxwh_operation').addClass("none").html("");
				comm.liActive($('#glqyxxwh'),'#xgqyxx');
			});
			//添加银行按钮
			$('#addBt_yinghang').click(function(){
				self.initShowForm(obj);
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(obj){
			//var self = this;
			dataModoule.findBanksByBelongCustId({"belongEntCustId": obj.entCustId}, function(res){
				var list = res.data;
				$("#add_bank").show();
				if(list){
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
							//for(var key in list){
							for(var key=0;key<list.length;key++){
								var bankName = comm.getBankNameByCode(list[key].bankCode, bankRes);
								self.addBank(currencyNameCn, list[key], bankName,obj);
							}
						});
					});
				}else{
					$("#left_bank_count").html(5);
				}
			});
		},
		/**
		 * 初始化显示页面
		 * @param data 注册信息
		 */
		initShowForm : function(obj){
			//self = this;
			$('#infobox').html(qyyhzhxx_tjTpl);	
			
			//点击修改页面提交按钮
			$('#qyyhzhxx_xg_btn').click(function(){
				self.initChangeData(obj);
			});
			//点击修改页面取消按钮
			$('#back_qyyhzhxx').click(function(){
				$("#qyyhzhxx").click();
				$("#qyyhzhxxTpl2").hide();
			});
			
			var prov = null;
			var city = null;
			var comboxVal = null;
			//账户名称
			$('#accountName').val(obj.entName);
			//平台信息
			cacheUtil.findCacheSystemInfo(function(sysRes){
				$('#currencyName').val(sysRes.currencyNameCn);
			});
			
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
			//初始化银行列表
			cacheUtil.findCacheBankAll(function(bankRes){
				comm.initBankCombox("#bankCode", bankRes, comboxVal, true);
			});	
			//初始化城市
			cacheUtil.findCacheSystemInfo(function(sysRes){
				cacheUtil.findCacheCityByParent(sysRes.countryNo, null, function(cityArray){
					comm.initCitySelect('#cityNo', cityArray, 224, city);
				});
			});
//			this.initForm();
		},
		/**
		 * 初始化提交页面
		 */
		initChangeData : function(obj){
			//var self = this;
			if(!self.validateData().form()){
				return;
			}
			var ndata = {};
			var trs = "";	
			var chg = [];
			
			//初始化银行列表
			cacheUtil.findCacheBankAll(function(bankRes){
				
				//账号名称
				var accountName = $("#accountName").val();
				trs+="<tr><td class=\"view_item\">账号名称</td><td class=\"view_text\">"+""+"</td><td class=\"view_text\">"+accountName+"</td></tr>";
				ndata.accountName = accountName;
				//银行账号
				var accountNo = $("#accountNo").val();
				trs+="<tr><td class=\"view_item\">银行账号</td><td class=\"view_text\">"+""+"</td><td class=\"view_text\">"+accountNo+"</td></tr>";
				ndata.bankAccNo = accountNo;
				//开户银行
				var bankCode = $("#bankCode").val();
				var oBankName = "";
				var nBankName = comm.getBankNameByCode(bankCode, bankRes); 
				trs+="<tr><td class=\"view_item\">开户银行</td><td class=\"view_text\">"+oBankName+"</td><td class=\"view_text\">"+nBankName+"</td></tr>";
				ndata.bankCode = bankCode;
				ndata.bankName = nBankName;
				
				//开户地区
				var provNo = $("#provinceNo").attr('optionValue');
				var cityNo = $("#cityNo").attr('optionValue');
				//获取开户地区
				var oResData = "";
				var nResData = cacheUtil.getRegionByCode(null, provNo, cityNo, "");
				if(!nResData.flag){
					return;
				}
				var oPlaceName = oResData;
				var nPlaceName = nResData.data;
				
				trs+="<tr><td class=\"view_item\">开户地区</td><td class=\"view_text\">"+oPlaceName+"</td><td class=\"view_text\">"+nPlaceName+"</td></tr>";
				ndata.provinceNo = provNo;
				ndata.cityNo = cityNo;
				
				//默认银行账号
				var isdefault = $('input[name="isdefault"]:checked').val();
				var oIsDefault = "";
				var nIsDefault = comm.getNameByEnumId(isdefault, comm.lang("infoManage").whetherEnum);
				trs+="<tr><td class=\"view_item\">默认银行账号</td><td class=\"view_text\">"+oIsDefault+"</td><td class=\"view_text\">"+nIsDefault+"</td></tr>";
				ndata.isDefaultAccount = isdefault;
				chg.push({"logType":1,"updateField":'bankAccNo',"updateFieldName":'银行账户',"updateValueOld":null, "updateValueNew":accountNo});
				if(trs == ""){
					comm.warn_alert(comm.lang("infoManage").noUpdate);
					return;
				}
				//提交
				$('#qyyhzhxx_dialog > p').html(_.template(qyyhzhxx_tj_dialogTpl));
				$('#copTable tr:eq(1)').before(trs);
				self.initVerificationMode();
				$('#qyyhzhxx_dialog').dialog({
					width:800,
					height:600,
					buttons:{
						'确定':function(){
							if(!self.validateViewMarkData().form()){
								return;
							}
							var verificationMode = $("#verificationMode").attr('optionValue');
							if(verificationMode != "1"){
								comm.warn_alert(comm.lang("infoManage").notSupportValidateMode);
								return ;
							}
							comm.getToken(null,function(resp){
								if(resp){
									ndata.dbUserName = $("#doubleUserName").val();
									ndata.loginPwd = comm.encrypt($('#doublePassword').val(),resp.data);
									ndata.secretKey = resp.data;
									ndata.optRemark = $("#viewMark").val();
									ndata.belongEntCustId = obj.entCustId;
									ndata.entResNo = obj.hsResNo;
									ndata.bankAccName = obj.entName;
									ndata.changeContent = JSON.stringify(chg);
									ndata.typeEum = "1";
									dataModoule.addBankInfo(ndata, function(res){
										comm.alert({content:comm.lang("infoManage")[22000], callOk:function(){
											$('#qyyhzhxx_dialog').dialog('destroy');
											$("#qyyhzhxx").click();
										}});
									});
								}
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
						required : comm.lang("infoManage")[36042]
					},
					currencyName : {
						required : comm.lang("infoManage")[36043]
					},
					bankCode : {
						required : comm.lang("infoManage")[36352]
					},
					provinceNo : {
						required : comm.lang("infoManage")[36094]
					},
					cityNo : {
						required : comm.lang("infoManage")[36095]
					},
					accountNo : {
						required : comm.lang("infoManage")[22043],
						bankNo : comm.lang("infoManage")[36044],
					},
					accountNoCom : {
						required : comm.lang("infoManage").accountNoComIsNull,
						bankNo : comm.lang("infoManage")[36044],
						equalTo: comm.lang("infoManage")[36045],
					},
				},
				errorPlacement : function (error, element) {
					 //银行卡号验证
					 if($(element).attr('name') == 'bankCode'){
						 $("#bankCode").next().find(".custom-combobox-input").attr("title", $(error).text()).tooltip({
                                 tooltipClass: "ui-tooltip-error",
                                 destroyFlag : true,
                                 destroyTime : 3000,
                                 position : {
                                	my : "left+2 top+30",
 									at : "left top"
                                 }
                         }).tooltip("open");
					 }else{
						 $(element).attr("title", $(error).text()).tooltip({
								tooltipClass: "ui-tooltip-error",
								destroyFlag : true,
								destroyTime : 3000,
								position : {
									my : "left+2 top+30",
									at : "left top"
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
		 * 表单校验
		 */
		validateViewMarkData : function(){
			var validate = $("#confirm_dialog_form").validate({
				rules : {
					verificationMode : {
						required : true
					},
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
					verificationMode : {
						required : comm.lang("infoManage")[36050]
					},
					viewMark : {
						rangelength : comm.lang("infoManage")[36006]
					},
					doubleUserName : {
						required : comm.lang("infoManage")[36047]
					},
					doublePassword : {
						required : comm.lang("infoManage")[36048]
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
			
			var type = $("#verificationMode").attr("optionvalue");
			if(type == '1'){
				validate.settings.rules.doubleUserName = {required : true};
				validate.settings.rules.doublePassword = {required : true};
			}else{
				validate.settings.rules.doubleUserName = {required : false};
				validate.settings.rules.doublePassword = {required : false};
			}
			
			return validate;
		},
		/**
		 * 初始化验证方式
		 */
		initVerificationMode : function(){
			comm.initSelect("#verificationMode", comm.lang("infoManage").verificationMode, null, '1').change(function(e){
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
			
			window.setTimeout(function(){
				$("#doubleUserName").attr("value","");
				$("#doublePassword").val("");	
			},100); 
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
		addBank : function(currencyNameCn, bank, bankName,obj){
			var detailId = "detail_"+bank.accId;
			var deleteId = "delete_"+bank.accId;
			var li = (bank.isDefaultAccount == 1)?$("#default_bank_ul").html():$("#bank_ul").html();
			var fullname = comm.removeNull(bankName);
			var shortName = this.getShortName(fullname,24);
			var bankAccNo = bank.bankAccNo;
			bankAccNo = bankAccNo.substring(bankAccNo.length-4, bankAccNo.length);
			li = li.replace("bankName", shortName);
			li = li.replace("bankAccNo", bankAccNo);
			li = li.replace("currencyName", comm.removeNull(currencyNameCn));
			li = li.replace("bankCode", comm.removeNull(bank.bankCode));
			if((bank.isDefaultAccount == 1)){
				li = li.replace("option", '<a id="'+detailId+'" class="detailBt_yinghang">详情</a> | <a class="tc gray f13">删除</a>');
				$("#banks").prepend(li);
			}else{
				li = li.replace("option", '<a id="'+detailId+'" class="detailBt_yinghang">详情</a> | <a id="'+deleteId+'" class="tc gray f13">删除</a>');
				$("#add_bank").before(li);
			}
			bank.currencyName = currencyNameCn;
			bank.sdefaultText = (bank.isDefaultAccount == 1)?"是":"否";
			
			$("#"+detailId).click(function(){
				comm.delCache("infoManage", "glqyxxgl-qyyhzhxx-detailBank");
				comm.setCache("infoManage", "glqyxxgl-qyyhzhxx-detailBank", bank);
				$('#infobox').html(_.template(qyyhzhxx_xqTpl,bank));
				//获取地区信息
				cacheUtil.syncGetRegionByCode(bank.countryNo, bank.provinceNo, bank.cityNo, "", function(resdata){
					$("#addressInfo").html(resdata);
					$("#entCustName").html(bank.bankAccName);
				});
				$('#qyyhzhxx_xq_back').triggerWith('#qyyhzhxx');		
			});
			$("#"+deleteId).click(function(){
				
				$('#qyyxzhxx_double_sign_dialog > p').html(_.template(qyyxzhxx_del_double_sign_dialogTpl));
				self.initVerificationMode();
				$('#qyyxzhxx_double_sign_dialog').dialog({
					width:800,
					height:500,
					buttons:{
						'确定':function(){
							if(!self.validateViewMarkData().form()){
								return;
							}
							var verificationMode = $("#verificationMode").attr('optionValue');
							if(verificationMode != "1"){
								comm.warn_alert(comm.lang("infoManage").notSupportValidateMode);
								return ;
							} 
							var win = $(this);
							comm.getToken(null,function(resp){
								if(resp){
									
									var ndata = {};
									var chg = [];
									chg.push({"logType":0,"updateField":'bankAccNo',"updateFieldName":'银行账户',"updateValueOld":bank.bankAccNo, "updateValueNew":null});
									ndata.changeContent = JSON.stringify(chg);
									ndata.dbUserName = $("#doubleUserName").val();
									ndata.loginPwd = comm.encrypt($('#doublePassword').val(),resp.data);
									ndata.secretKey = resp.data;
									ndata.optRemark = $("#viewMark").val();
									ndata.belongEntCustId = obj.entCustId;
									ndata.typeEum = "0";
									ndata.accId = bank.accId;
									dataModoule.delBankInfo(ndata, function(res){
										
										comm.alert({content:comm.lang("infoManage")[22000], callOk:function(){
											$("#qyyhzhxx").click();
										}});
										win.dialog('destroy');
									});
									
								}
							});
						},
						'取消':function(){
							$(this).dialog('destroy');
						}
					}
				});
				
			});
		}
	};
	return self
});