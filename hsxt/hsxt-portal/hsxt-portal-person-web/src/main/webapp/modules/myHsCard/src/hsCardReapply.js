define(["text!myHsCardTpl/hsCardReapply.html", 
		"text!myHsCardTpl/addressManage/addressManage.html",
		"text!myHsCardTpl/addressManage/default_address.html",
		"text!myHsCardTpl/addressManage/spare_address.html",
		"text!myHsCardTpl/addressManage/modifyAddress.html",
		"text!myHsCardTpl/addressManage/addAddress.html",
		"text!myHsCardTpl/addressManage/manageAddress.html",
		"text!myHsCardTpl/addressManage/deliveryAddress.html",
		"text!myHsCardTpl/payment.html",
		"myHsCardDat/myHsCard",
		"myHsCardLan"
		/*"text!myHsCardTpl/hsCardReapply2.html"*/
		], function (tpl, addressManageTpl, default_addressTpl, spare_addressTpl, modifyAddressTpl, addAddressTpl, manageAddressTpl, deliveryAddressTpl, paymentTpl, myHsCardAjax) {
	//var gridObj;
	var addMaxAddress = 10;
	var hsCardReapply = {
		_dataModule : null,
		show : function(dataModule){
			//加载页面
			$("#myhs_zhgl_box").html(tpl /*+ tpl2*/);

			myHsCardAjax.initAuthentication(null,function (resp) {
				if(resp){
					var authStatus =  resp.data.authStatus;
					if(comm.isEmpty(authStatus)||authStatus == '1'){
						$('#noRealNameReg').removeClass("none");
						return ;
					}else{
						$('#hsCardReapply_apply_div').removeClass("none");
					}
				}
			})
			this._dataModule = dataModule;
			
			var self = this,
				money = $('#hsCardReapply_money').val(),
				obj = {
					paymentMethod : 'hsbzf'	,
					paymentAmount : money,
					paymentName_1 : '互生币账户支付'
				};

			cacheUtil.findProvCity();
			
			//互生卡号
			$('#hsCardReapply_cardNo').val($.cookie('resNo'));
			$('#hsCardReapply_custId').val($.cookie('custId'));
			$('#hsCardReapply_custName').val($.cookie('custName'));
					
			//点击加载地址列表
			$('#hsCardReapply_submitBtn').click(function(){
				var remark = $("#hsCardReapply_reason").val();
				self.loadAddress(remark);
			});
			
			//支付成功或失败跳转
			$("#hb_ebank_cg_sure,#hb_ebank_cg_lose").click(function(){
				$('#card_reapply_bank_dialog').dialog('destroy');
				//跳转到查到日志列表
				$('#hs_side_list > #side_systemService').trigger('click');
			});
			$("#cardPayShowMsgClose").click(function(){
				$('#cardPayShowMsg').dialog('destroy');
				//跳转到查到日志列表
				if ($('#returnStatus').val() == 'success') {
					//跳转到查到日志列表
					$('#hs_side_list > #side_systemService').trigger('click');
				}
			});
		},
		
		infoAddressProvince : function(provinceId,cityId,prov, city){
			var self = this;
			
			//初始化省份
			cacheUtil.findCacheSystemInfo(function(sysRes){
				cacheUtil.findCacheProvinceByParent(sysRes.countryNo, function(provArray){
					comm.initProvSelect(provinceId, provArray, 224, prov).change(function(e){
						cacheUtil.findCacheCityByParent(sysRes.countryNo, $(this).attr('optionValue'), function(cityArray){
							comm.initCitySelect(cityId, cityArray, 224).selectListIndex(0);
							$("#city_slt_add").val("").removeClass('placeholder');     
						});
					});
				});
				//初始化城市
				if(prov){
					cacheUtil.findCacheCityByParent(sysRes.countryNo, prov, function(cityArray){
						comm.initCitySelect(cityId, cityArray, 224, city);
					});
				}
			});
			
		},
		
		/** 加载地址列表 */
		loadAddress:function(remark){
			var self=this;
			//请求后台获取地址
			myHsCardAjax.searchAddress(null,function(response){
				var addList = response.data;
				$('#myhs_zhgl_box').html(_.template(addressManageTpl,addList)).append(paymentTpl);
				
				var addressList = $('#addressList');
				addressList.find('span[data-tag="setDefault"]').hide();
				//循环地址列表，绑定事件
				addressList.children('li').each(function(){
				$(this).bind('click', function(){
					var currentLi = $(this);
					if($(this).hasClass('spare-address')){
						currentLi.find('form').children('input[name="currentAddress"]').val('1');
						var defaultObj = currentLi.find('form').serializeJson();

						currentLi.removeClass('spare-address').addClass('default-address')
							.html(_.template(default_addressTpl, defaultObj));

						$('#deliveryAddress').html(_.template(deliveryAddressTpl, defaultObj));

						currentLi.siblings('li').each(function(){
							$(this).find('form').children('input[name="currentAddress"]').val('0');
							var spareObj = $(this).find('form').serializeJson();

							$(this).removeClass('default-address').addClass('spare-address')
								.html(_.template(spare_addressTpl, spareObj));

							addressList.find('span[data-tag="setDefault"]').hide();
						});

						/*去掉选择地址的提示  by lk 2016-05-19  */
						/*comm.confirm({
							width : 400,
							imgFlag : true ,
							imgClass : 'tips_ques',
							content : '您正在变更收货地址,请确认。',			 
							callOk :function(){	
								currentLi.find('form').children('input[name="currentAddress"]').val('1');						
								var defaultObj = currentLi.find('form').serializeJson();
								
								currentLi.removeClass('spare-address').addClass('default-address')
									.html(_.template(default_addressTpl, defaultObj));
								
								$('#deliveryAddress').html(_.template(deliveryAddressTpl, defaultObj));
								
								currentLi.siblings('li').each(function(){
									$(this).find('form').children('input[name="currentAddress"]').val('0');
									var spareObj = $(this).find('form').serializeJson();
									
									$(this).removeClass('default-address').addClass('spare-address')
										.html(_.template(spare_addressTpl, spareObj));
										
									addressList.find('span[data-tag="setDefault"]').hide();
								});
							},
							 callCancel : function(){
                                 var currentVal = addressList.find('input[type="radio"][name="toAddress"][checked="checked"]').val();
                                 addressList.find('input[type="radio"][name="toAddress"]').attr("checked",false);
                                 addressList.find('input[type="radio"][name="toAddress"][value="'+ currentVal +'"]').attr("checked",true);
                         }
						});*/
					}
				})
				.bind('mouseover', function(){
					$(this).addClass('address-list-bg');
					$(this).find('span[data-tag="setDefault"]').show();	
					
					/*设为默认地址操作*/
					$(this).find('span[data-tag="setDefault"]').unbind('click').bind('click', function(e){
						var addressId = $("#addressId").val();
							
							e.stopPropagation();
							
							$(this).parents('li').find('form').children('input[name="defaultAddress"]').val('1');
							
							var obj = $(this).parents('li').find('form').serializeJson();
							$(this).parents('li').html(_.template(obj.currentAddress == '1' ? default_addressTpl : spare_addressTpl, obj))
								.siblings('li').each(function(){
									$(this).find('form').children('input[name="defaultAddress"]').val('0');
									var obj_2 = $(this).find('form').serializeJson();
									$(this).html(_.template(obj_2.currentAddress == '1' ? default_addressTpl : spare_addressTpl, obj_2))
										.find('span[data-tag="setDefault"]').hide();
								});	
							var defaultData = {
									id : obj.addrId
							};
						myHsCardAjax.setDefaultAddress(defaultData,function(response){
							addressList.find('span[data-tag="default"]').after('<span id="set_success" class="set-success"><i class="icon_tips tips_yes_s set-success-icon"></i><span class="set-success-font">设置成功！</span></span>');	
							
							setTimeout("$('#set_success').remove()", 4000);	
						});
									
					});
					/*end*/
				})
				.bind('mouseout', function(){
					$(this).removeClass('address-list-bg');	
					$(this).find('span[data-tag="setDefault"]').hide();	
					
				});	
			});
				
			//重新绑定元素事件
			self.bindAddressEvent();
			
			/*补卡提交订单去付款*/
			$('#goPay_btn').click(function(){
				
				var n = $("input[name='toAddress']:checked").val();
				
				var datas = $("#addressList form:eq("+n+")").serializeArray();
				if(datas==null||datas.length<1){
					comm.alert("请设置收货地址!");
					return false;
				}
				//$("#form_"+n);
				var jsonParam = {
								remark : remark,
								receiverName : datas[6].value,
								phone : datas[7].value,
								province : comm.getProvNameByCode(datas[3].value),
								provinceCode : datas[3].province,
								city : comm.getCityNameByCode(datas[3].value,datas[4].value),
								cityCode : datas[4].city,
								address : datas[5].value
				}


				//补卡提交订单去付款
				myHsCardAjax.mendCardOrder(jsonParam, function (response) { 
					var orderNumber = response.data.orderNo;
					var currency = response.data.currencyNameCn;
					var exchangeRate = response.data.exchangeRate;
					var hsbAmount = response.data.hsbAmount;
					var obj = {
							paymentMethod : "hsbzf",
							currency      : currency,
							buyHsbNum     : hsbAmount,
							paymentAmount : hsbAmount,
							hbRate        : exchangeRate,
							paymentName_1 : '互生币账户支付',
							isFrist:'true',
							orderNumber   : orderNumber
						};
						//隐藏申请页面
						$('#addressManageTpl').addClass('none');
						$('#pay_div').removeClass('none');
						
						require(['cardReapplyPaySrc/pay'], function(pay){
							pay.showPage(obj);	
						});
				});
				
			});
		});
	},
	//重新绑定地址元素事件
	bindAddressEvent:function(){
		var self=this;
		/*使用新地址*/
		$('#addNewAddress_btn').click(function(){
			
			var addressList = $('#addressList li');
			if(addressList.length >=10){
				comm.yes_alert(comm.lang("myHsCard").userAddress.maxAddress);
				return;
			}
			
			$('#addAddress_dialog').html(_.template(addAddressTpl))	
			
			/*弹出框*/
			$( "#addAddress_dialog" ).dialog({
				title:"添加新地址",
				width:"800",
				height:"580",
				modal:true,
				buttons:{ 
					"关闭":function(){
						$('input').tooltip().tooltip('destroy');
						$('textarea').tooltip().tooltip('destroy');
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
			
			//下拉选择框
			$('#province_slt_add').selectList({
				borderWidth:1,
				borderColor:'#DDD',
				optionWidth:155,
				width:150,
			});
			
			$('#city_slt_add').selectList({
				borderWidth:1,
				borderColor:'#DDD',
				optionWidth:155,
				width:150
			});
			
			self.infoAddressProvince("#province_slt_add","#city_slt_add");
			
			$('#saveAddress_btn_add').click(function(){
				var valid = self.validateAddAddress()
				if(!valid.form()){
					return;	
				}
				/*if($("#phone").val().trim()=="" && $("#fixedTelephone").val().trim()==""){
					comm.alert("手机号码和电话号码不能都为空！");
					return ;
				}*/
				
				var datas = $("#addForm").serializeArray();
				var province = $("#province_slt_add").attr('optionValue');
				var city = $("#city_slt_add").attr('optionValue');
				var isDefault = 0;
				if($("#isDefault").attr("checked")){
					isDefault = 1;
				}
				var data = {
						receiverName : datas[4].value,
						phone : datas[5].value,
						fixedTelephone: datas[6].value, 
						province : datas[0].value,
						provinceCode : province,
						city : datas[1].value,
						cityCode : city,
						area : city,
						areaCode : datas[1].value,
						address : datas[2].value,
						postcode : datas[3].value,
						defaultAddr : isDefault
				};
				
				myHsCardAjax.addAddress(data,function(){
					$('#hsCardReapply_submitBtn').trigger('click');
					comm.yes_alert(comm.lang("myHsCard").userAddress.addAddressSuccess);
					//重新加载地址
					self.loadAddress();
				});
				
				$("#addAddress_dialog").dialog('destroy');	
			});
			
		});
		
		/*修改本地址*/
		$('#modifyAddress_btn').live('click', function(){
			var obj = $(this).parents('li').find('form').serializeJson();
			$('#modifyAddress_dialog').html(_.template(modifyAddressTpl, obj));	
			/*弹出框*/
			$( "#modifyAddress_dialog" ).dialog({
				title:"修改当前地址",
				width:"800",
				height:"580",
				modal:true,
				buttons:{ 
					"关闭":function(){
						$('input').tooltip().tooltip('destroy');
						$('textarea').tooltip().tooltip('destroy');
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
			
			/*下拉选择框*/
			$('#province_slt').selectList({
				borderWidth:1,
				borderColor:'#DDD',
				optionWidth:155,
				width:150,
				options:""
			});
			
			$('#city_slt').selectList({
				borderWidth:1,
				borderColor:'#DDD',
				optionWidth:155,
				width:150
			});
			
			self.infoAddressProvince("#province_slt","#city_slt");
			
			$('#saveAddress_btn').click(function(){
				if(!self.validateModifyAddress().form()){
					return;	
				}
				var datas = $("#modifyForm").serializeArray();
				var province = $("#province_slt").attr('optionValue');
				var city = $("#city_slt").attr('optionValue');
				var isDefault = 0;
				if($("#isDefault").attr("checked")){
					isDefault = 1;
				}
				var addressId = $("#addressId").val();
				var data = {
						receiverName : datas[4].value,
						phone : datas[5].value,
						fixedTelephone: datas[6].value, 
						province : datas[0].value,
						provinceCode : province,
						city : datas[1].value,
						cityCode : city,
						area : city,
						areaCode : datas[1].value,
						address : datas[2].value,
						postcode : datas[3].value,
						defaultAddr : isDefault,
						id : addressId
				};
				myHsCardAjax.updateAddress(data,function(){
					comm.yes_alert(comm.lang("myHsCard").userAddress.updateAddressSuccess);
					//重新加载地址
					self.loadAddress();
					$("#modifyAddress_dialog").dialog('destroy');	
				});
			});
		});
		
		/*管理收货地址*/
		$('#manageAddress_btn').click(function(){
			$('#manageAddress_dialog').html(manageAddressTpl);	
			/*弹出框*/
			$( "#manageAddress_dialog" ).dialog({
				title:"管理收货地址",
				width:"1000",
				modal:true,
				buttons:{ 
					"关闭":function(){
						//重新加载地址
						self.loadAddress();
						$('input').tooltip().tooltip('destroy');
						$('textarea').tooltip().tooltip('destroy');
						$( this ).dialog( "destroy" );
					}
				}
			});
			
			
			/*下拉选择框*/
			$('#province_slt_manage').selectList({
				borderWidth:1,
				borderColor:'#DDD',
				optionWidth:155,
				width:150,
				options:""
			});
			
			$('#city_slt_manage').selectList({
				borderWidth:1,
				borderColor:'#DDD',
				optionWidth:155,
				width:150
			});
			
			self.infoAddressProvince("#province_slt_manage","#city_slt_manage");
			
			// 加载管理地址页面表格List
			//var queryConditon = {};
			//gridObj = comm.getCommBsGrid('addressTable', "queryAddressList", queryConditon,null,self.opt_edit,null,self.opt_delete);
			
			var gridObj = $.fn.bsgrid.init('addressTable', {				 
				url : comm.domainList[comm.getProjectName()]+comm.UrlList['queryAddressList'],
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: false,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行吧                                                                                                                              
				otherParames:comm.getQueryParams(),
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						var obj = gridObj.getRecord(rowIndex);
						var link1 = null;
						if(colIndex == '5'){
							link1 = $('<a>修改</a>').click(function(e) {
								
								self.infoAddressProvince("#province_slt_manage","#city_slt_manage",obj.provinceNo,obj.cityNo);
								$("#fullAddress_manage").val(obj.address);
								$("#postCodeManage").val(obj.postCode);
								$("#consigneeName_manage").val(obj.receiver);
								$("#telephoneManage").val(obj.mobile);
								$("#mobileManage").val(obj.telphone);
								if(obj.isDefault == '1'){
									$("#isDefaultMange").attr("checked","checked");
								}else{
									$("#isDefaultMange").attr("checked",false);
								}
								$("#addressId").val(obj.addrId);
								$("#addressTitle").text("修改收货地址");
								
							}) ;
						}
						else if(colIndex == '1'){
							link1 = comm.getRegionByCode(null,obj.provinceNo,obj.cityNo)
						}
						else if(colIndex == '4'){
							if(obj.telphone){
								link1 = obj.telphone + "/" + obj.mobile;
							}else{
								link1 = obj.mobile;
							}
						}
						else if(colIndex == '6'){
							var defaultFlag = obj.isDefault;
							if(defaultFlag == 1){
								link1 = $('<a>是</a>').click(function(e) {
								}.bind(this) ) ;
							}else{
								link1 = $('<a>否</a>').click(function(e) {
								}.bind(this) ) ;
							}
						}
						$("#hadSaveAddress").text(gridObj.options.totalRows);
						if(gridObj.options.totalRows){
							var totalRows = Number(gridObj.options.totalRows);
							$("#addressAvailable").text(addMaxAddress - totalRows);
						}
						
						return link1;
					},
					
					del : function(record, rowIndex, colIndex, options){

						
						var opt_btn = null;
						if(colIndex == '5'){
							opt_btn = $('<a>删除</a>').click(function(e) {
								
								var obj = gridObj.getRecord(rowIndex);
								var flag = comm.confirm({
									imgFlag : true,
									imgClass : 'tips_ques',
									content : '确定删除该条记录？',
									callOk : function(){
										var curPage = gridObj.options.curPage;
										var pageSize = gridObj.options.settings.pageSize;
										var addressId = obj.addrId;
										var datas = {
												curPage : curPage,
												pageSize : pageSize,
												id :addressId
										};
										myHsCardAjax.deleteAddress(datas,function(obj){
											$("#manageForm")[0].reset();
											$('#addressTable_pt_refreshPage').click();
										});
									}
								});
							}) ;
						}
						
						return opt_btn;
					
					}
				}
				
			});
			
			$('#saveAddress_btn_manage').click(function(){
				if(!self.validateManageAddress().form()){
					return;	
				}
				
				var datas = $("#manageForm").serializeArray();
				var province = $("#province_slt_manage").attr('optionValue');
				var city = $("#city_slt_manage").attr('optionValue');
				var isDefault = 0;
				if($("#isDefaultMange").attr("checked")){
					isDefault = 1;
				}
				var data = {
						receiverName : datas[4].value,
						phone : datas[5].value,
						fixedTelephone: datas[6].value, 
						province : datas[0].value,
						provinceCode : province,
						city : datas[1].value,
						cityCode : city,
						area : city,
						areaCode : datas[1].value,
						address : datas[2].value,
						postcode : datas[3].value,
						defaultAddr : isDefault
				};
				
				var addressId = $("#addressId").val();
				if(addressId && addressId != "" && addressId != null){
					data.id = addressId;
					myHsCardAjax.updateAddress(data,function(){
						comm.yes_alert(comm.lang("myHsCard").userAddress.updateAddressSuccess);
						$("#addressTitle").text("新增收货地址");
						$('#addressTable_pt_refreshPage').click();
						$("#manageForm")[0].reset();
					});
				}else{
					myHsCardAjax.addAddress(data,function(){
						comm.yes_alert(comm.lang("myHsCard").userAddress.addAddressSuccess);
						$('#addressTable_pt_refreshPage').click();
						$("#manageForm")[0].reset();
					});
				}
				
				
			});
			
			
		});
		
	  },
		validateModifyAddress : function(){
			return $("#modifyForm").validate({
				rules : {
					province_slt : {
						required : true	
					},
					city_slt : {
						required : true		
					},
					fullAddress : {
						required : true	,
						maxlength : 128
					},
					consigneeName : {
						required : true		
					},
					postCode : {
						zipCode : true
					},
					mobile : {
						required : true,
						mobileNo : true
					},
					telphone : {
						telphone : true
					}
				},
				messages : {
					province_slt : {
						required : comm.lang("myHsCard").pleaseSelectProvince
					},
					city_slt : {
						required : comm.lang("myHsCard").pleaseSelectCity
					},
					fullAddress : {
						required : comm.lang("myHsCard").pleaseInputAddr,
						maxlength:comm.lang("myHsCard").address_length
					},
					consigneeName : {
						required : comm.lang("myHsCard").pleaseInputConsigneeName
					},
					postCode : {
						zipCode : comm.lang("myHsCard").pleaseInputRightProvinceCode
					},
					mobile : {
						required : comm.lang("myHsCard").pleaseInputMobile,
						mobileNo : comm.lang("myHsCard").pleaseInputRightMobile
					},
					telphone : {
						telphone : comm.lang("myHsCard").pleaseInputRightTel
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						position : {
							my : "left+160 top+5",
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
		validateAddAddress : function(){
		  return $("#addForm").validate({
				rules : {
					province_slt_add : {
						required : true	
					},
					city_slt_add : {
						required : true		
					},
					fullAddress_add : {
						required : true,
						maxlength: 128
					},
					consigneeName_add : {
						required : true		
					},
					provinceCode : {
						zipCode : true
					},
					phone : {
						required : true,
						mobileNo : true
					},
					fixedTelephone : {
						telphone : true
					}
					
				},
				messages : {
					province_slt_add : {
						required : comm.lang("myHsCard").pleaseSelectProvince		
					},
					city_slt_add : {
						required : comm.lang("myHsCard").pleaseSelectCity	
					},
					fullAddress_add : {
						required : comm.lang("myHsCard").pleaseInputAddr,
						maxlength:comm.lang("myHsCard").address_length
					},
					consigneeName_add : {
						required : comm.lang("myHsCard").pleaseInputConsigneeName
					},
					provinceCode : {
						zipCode : comm.lang("myHsCard").pleaseInputRightProvinceCode
					},
					phone : {
						required : comm.lang("myHsCard").pleaseInputMobile,
						mobileNo : comm.lang("myHsCard").pleaseInputRightMobile
					},
					fixedTelephone : {
						telphone : comm.lang("myHsCard").pleaseInputRightTel
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						position : {
							my : "left+180 top+5",
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
		
		validateManageAddress : function(){
			return $("#manageForm").validate({
				rules : {
					province_slt_manage : {
						required : true	
					},
					city_slt_manage : {
						required : true		
					},
					fullAddress_manage : {
						required : true,
						maxlength: 128
					},
					consigneeName_manage : {
						required : true		
					},
					postCodeManage : {
						zipCode : true
					},
					telephoneManage : {
						required : true,
						mobileNo : true
					},
					mobileManage : {
						telphone : true
					}
				},
				messages : {
					province_slt_manage : {
						required : comm.lang("myHsCard").pleaseSelectProvince
					},
					city_slt_manage : {
						required :comm.lang("myHsCard").pleaseSelectCity
					},
					fullAddress_manage : {
						required : comm.lang("myHsCard").pleaseInputAddr,
						maxlength:comm.lang("myHsCard").address_length
					},
					consigneeName_manage : {
						required : comm.lang("myHsCard").pleaseInputConsigneeName
					},
					postCodeManage : {
						zipCode : comm.lang("myHsCard").pleaseInputRightProvinceCode
					},
					telephoneManage : {
						required : comm.lang("myHsCard").pleaseInputMobile,
						mobileNo : comm.lang("myHsCard").pleaseInputRightMobile
					},
					mobileManage : {
						telphone : comm.lang("myHsCard").pleaseInputRightTel
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						position : {
							my : "left+180 top+5",
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
		opt_edit : function(record, rowIndex, colIndex, options){
			
			var opt_btn = null;
			if(colIndex == '5'){
				
				opt_btn = $('<a>修改</a>').click(function(e) {
					
					var obj = gridObj.getRecord(rowIndex);
					//self.option_edit(obj);
					
				}.bind(this) ) ;
				
			}
			else if(colIndex == '6'){
				if(gridObj.getColumnValue(rowIndex, 'consignee') == '张三'){
					link1 = '默认地址';
				}
				else{
					link1 = '';	
				}	
			}
			
			return opt_btn;
		}.bind(this),
		
		opt_delete : function(record, rowIndex, colIndex, options){
			
			var opt_btn = null;
			if(colIndex == '5'){
				opt_btn = $('<a>删除</a>').click(function(e) {
					
					var obj = gridObj.getRecord(rowIndex);
					var flag = comm.confirm({
						imgFlag : true,
						imgClass : 'tips_ques',
						content : '确定删除该条记录？',
						callOk : function(){
							var curPage = gridObj.options.curPage;
							var pageSize = gridObj.options.settings.pageSize;
							var datas = [
							    ];
							comm.requestFun("xtcsxDelete",datas,function(obj){
								$('#queryList_xtcsxgl_pt_refreshPage').click();
							},"");
						}
					});
				}.bind(this) ) ;
			}
			
			return opt_btn;
		}.bind(this),
	
	};
	return hsCardReapply
});
