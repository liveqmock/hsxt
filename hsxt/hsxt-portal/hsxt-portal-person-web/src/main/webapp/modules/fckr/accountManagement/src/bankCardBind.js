define(["text!fckr_accountManagementTpl/bankCardBind.html",
		"text!fckr_accountManagementTpl/bankCardBind_add.html",
		"text!fckr_accountManagementTpl/bankCardBind_delete.html",
		"text!fckr_accountManagementTpl/bankCardBind_details.html",
		"text!fckr_accountManagementTpl/bankCardBind_modify.html",
		"fckr_accountManagementDat/accountManagement"
	], function (tpl, tplAdd, tplDel, tplDetail, tplMod,accountManagementDataModel) {
	var bankCardBind = {
			_dataModule : null,
			_bankCountMax : 0,
			_currencyName : '',
			realName : null,	//真实姓名
			show : function(dataModule){
				this._dataModule = dataModule;
				
				this._currencyName = 'RMB';		//当前币种
				
				this._bankCountMax = 0; 		//添加银行卡最大个数
				
				//加载页面		
				$("#myhs_zhgl_box").html(tpl + tplAdd + tplDel + tplDetail + tplMod);
				
				//开户名称显示
				this.bankNameShow();
				
				//加载缓存查询币种信息
				cacheUtil.findCacheSystemInfo(function(systemInfo){
					bankCardBind._currencyName = systemInfo.currencyNameCn;		//当前币种
				});
				
				//默认加载消费者绑定用户卡
				dataModule.findBankBindList(null,function (response) {
					var defaultBank=[];
					var data = response.data.bankList;
					
					//保存可以保存的银行卡数量
					this._bankCountMax = response.data.maxNum;
					$('#detail_maxBindNum').text(this._bankCountMax);
					$('#add_maxBindNum').text(this._bankCountMax);
					$('#del_maxBingNum').text(this._bankCountMax);
					//可增加银行卡个数
					var count = this._bankCountMax,
					aBanks = [],
					//银行卡显示
					tplBankLi = '<li class="bank_bg pr"><%=obj.defaultFlag%><div class="bank_row bank_row_margin_1 f13">尾号：<%=obj.last4Number%></div><div class="bank_row tc"><i class="large-bank-logo large-bank-card-<%=obj.bankCode%>"></i></div><div class="bank_row bank_row_margin_2 clearfix"><div class="fl"><span class="gray">结算币种：</span><%=obj.currencyName%></div><div class="fr"><a id="view_bankInfo_delete" data-bankAccount-details="<%=obj.bankAccount%>" data-bankNameSub-details="<%=obj.bankName%>" data-countryNo-details="<%=obj.countryNo%>" data-provinceNo-details="<%=obj.provinceNo%>" data-cityNo-details="<%=obj.cityNo%>" data-bankAcctId-details="<%=obj.accId%>" data-bankAccName-details="<%=obj.bankAccName%>" data-defaultFlag-details="<%=obj.isDefaultAccount%>" >删除</a> | <a id="view_bankInfo_details" data-bankAccount-details="<%=obj.bankAccount%>" data-bankNameSub-details="<%=obj.bankName%>" data-countryNo-details="<%=obj.countryNo%>" data-provinceNo-details="<%=obj.provinceNo%>" data-cityNo-details="<%=obj.cityNo%>" data-bankAcctId-details="<%=obj.accId%>" data-defaultFlag-details="<%=obj.isDefaultAccount%>" data-bankAccName-details="<%=obj.bankAccName%>" class=\"<%=obj.defaultClass%>\">详情</a> <%=obj.btnDelte%></div></div></li>',
					tplBankAdd = '<li class="addBank_bg"><div class="addBank_content clearfix"><i class="icon add_icon cdef fl addAccountBt_yinghang"></i><div class="fl fb addBank_content_text_1 addAccountBt_yinghang"><a id="btn_addBankNo" data-id="<%=obj.count%>">添加银行账号</a></div></div><div class="tc gray f13">还可以添加<%=obj.count%>个银行账号</div></li>';
					//非空验证
					if(data)
					{
						
						for(var i = 0 ; i < data.length ;i++){
							count--;
							
							oDat = {
									bankName: data[i].bankName,
									bankCode: data[i].bankCode,
									bankNameSub: data[i].bankName.length>8?data[i].bankName.substr(0, 8)+"..":data[i].bankName,
									defaultFlag: data[i].isDefaultAccount == '1' ? ' <i class="default_bank"></i>' : '',
									isDefaultAccount:data[i].isDefaultAccount == '1' ? '是':'否',
									defaultClass:data[i].isDefaultAccount == '1' ? 'ml10 blue':'',
									bankAccount: data[i].bankAccNo.substr(0, 4) + " **** **** " + data[i].bankAccNo.substr(data[i].bankAccNo.length - 4, 4),
									last4Number: data[i].bankAccNo.substr(data[i].bankAccNo.length - 4, 4),
									accId: data[i].accId,
									countryNo: data[i].countryNo,
									provinceNo: data[i].provinceNo,
									cityNo: data[i].cityNo,
									bankAccName:bankCardBind.maskName(data[i].bankAccName),
									currencyName: bankCardBind._currencyName
								};
							
							if(data[i].isDefaultAccount == '1'){
								defaultBank.push(_.template(tplBankLi, oDat));
								continue;
							}
							aBanks.push(_.template(tplBankLi, oDat));
						}
					}
					
					//可添加的银行卡数量大于0  显示添加按钮
					if(count > 0) {
						aBanks.push(_.template(tplBankAdd, {count: count}));
					}
					//渲染数据到页面
					$("#info_bank_List").html(defaultBank.join('')).append(aBanks.join(''));
				});

				
				
				/**
				 * 绑定添加银行卡点击事件
				 */
				$('#bankInfoList').on('click', '#btn_addBankNo', function (eventObj) {
					comm.initOption('#bankArea2_bank_add', {} , null, null , true);
					//获取还可以添加的银行卡数量
					var dataId = $(eventObj.currentTarget).attr('data-id');
					
					//如果可添加银行卡大于0则显示添加内容
					if (dataId > 0) {
						//隐藏列表页面
						$("#bankInfoList").addClass("none");
						//显示增加银行卡页面
						$("#bankInfo_add").removeClass("none");
					} 
					else 
					{
						//提示银行卡绑定已达到最大数量
						comm.warn_alert(comm.lang("myHsCard")[30137]);
					}
					
					//获取平台基本信息 
					cacheUtil.findCacheSystemInfo(function(systemInfo){
						//界面数据加载
						$("#countryCodeHid").val(systemInfo.countryNo);				//保存当前国家
						$("#currencyName_bank_add").val(systemInfo.currencyNameCn);	//加载缓存中的结算货币
						bankCardBind._currencyName = systemInfo.currencyNameCn;		//当前币种
						
						//根据国家获取下属省份
						cacheUtil.findCacheProvinceByParent(systemInfo.countryNo,function(provinceList){
							//加载省份信息
							comm.initOption('#bankArea_bank_add', provinceList , 'provinceName','provinceNo',true);
							
						});

						//加载所有的银行列表
						cacheUtil.findCacheBankAll(function(bankList){
							comm.initCombox('#bankNo_bank_add', bankList , '' , true ,'bankName','bankNo');
						});
					});
					
					//加载个人名称
					bankCardBind.loadCustomerName();
					
					$('.custom-combobox-input').val('');
					//加载开户地区并绑定级联事件
					//bankCardBind.loadAndBindPC('bankArea_bank_add', 'bankArea2_bank_add');
					
				});
				
				
				$('#bankArea_bank_add').change(function(){
					var provinceNo = $(this).val();
					//加载城市的下拉框
					bankCardBind.loadCacheCityByParent($("#countryCodeHid").val(),provinceNo);
				});
				/**
				 * 添加银行卡提交点击事件
				 */
				$("#submit_bank_add").click(function () {
					
					//数据的验证
					if (!bankCardBind.validateAddData()) {
						return;
					};
					
					//判断客户名称是否为空
					if(comm.removeNull(comm.getCookie('custName')) == ""){
						comm.warn_alert(comm.lang("myHsCard").pleaseEnterRealName);
						return;
					}
					
					//确认提交选择框
					comm.i_confirm(comm.lang("myHsCard").bindBankConfirmInfo, function () {
						
						//添加银行卡参数封装
						var jsonData = {
								zName			:   bankCardBind.realName,//$("#companyName_bank_add").val(),						// 开户姓名
								bankAccNo 		:   $("#bankAcccount_bank_add").val(),						// 银行账号
								bankAccNoSure 	:   $("#bankAcccount_bank_add_sure").val(),					// 确认银行卡号
								bankCode 		:   $("#bankNo_bank_add option:selected").val(), 			// 开户银行code
								bankName    	:   $("#bankNo_bank_add option:selected").text(), 		    // 开户银行名称
								countryNo 		: 	$("#countryCodeHid").val(),								//国家代码
								provinceNo		:   $("#bankArea_bank_add option:selected").val(), 			//省份代码
								cityNo 			:   $("#bankArea2_bank_add option:selected").val(), 		//城市名称
								isDefaultAccount:   $("input[name='default_bank_add']:checked").val()   	//是否是默认银行卡
							};
						
						//执行添加操作
						dataModule.addBankCard(jsonData, function (response) {
							
							//提示操作成功
							comm.yes_alert(comm.lang("myHsCard").bindBankSuccessfully);
							//设置选中
							$('#ul_myhs_right_tab a[data-id="5"]').trigger('click');
							
						});
					});
				});
				
				 

				/**
				 *添加银行卡页面返回按钮事件 
				 */
				$("#return_bank_add").click(function () {
					//隐藏增加银行卡页面
					$("#bankInfo_add").addClass("none");
					//显示列表页面
					$("#bankInfoList").removeClass("none");
					//清除所有提示
					$(".ui-tooltip").remove();
				});

				
				/**
				 *查看银行卡详细按钮点击事件 
				 */
				$('#bankInfoList').on('click', '#view_bankInfo_details', function (e) {
					
					//设置显示详情div	
					$("#bankInfoList").addClass("none");
					$("#bankInfo_details").removeClass("none");
					
					//设置显示的数据
					var custName =  $("#companyName_bank_modify_after").val();
					
					$("#companyName_bank_details").val($(e.currentTarget).attr('data-bankAccName-details'));	//姓名
					$("#currency_bank_details").val(bankCardBind._currencyName);								//币种数据
					$("#bankNo_bank_details").val($(e.currentTarget).attr('data-bankNameSub-details'));			//银行名称
					var countryNo = $(e.currentTarget).attr('data-countryNo-details');
					var provinceNo = $(e.currentTarget).attr('data-provinceNo-details');
					var cityNo = $(e.currentTarget).attr('data-cityNo-details');
					
					cacheUtil.findCacheCityByParent(countryNo,provinceNo,function(cityList){
						
						if(cityList)
						{
							for(var i = 0 ; i < cityList.length ; i++)
							{
								if(cityList[i].cityNo == cityNo)
								{
									$("#bankArea_bank_details").val(cityList[i].cityNameCn);						//开户地区
								}
							}
						}
						
					});
					$("#bankAccount_bank_details").val($(e.currentTarget).attr('data-bankAccount-details'));	//银行卡号
					$("#defaultFlag_bank_details").val($(e.currentTarget).attr('data-defaultFlag-details'));	//是否默认
					
				});
				
				
				/**
				 *银行卡详情页面返回按钮事件
				 */
				$("#return_bank_details").click(function () {
					$("#bankInfo_details").addClass("none");
					$("#bankInfoList").removeClass("none");
				});
				

				

				//删除银行卡信息页面
				$('#bankInfoList').on('click', '#view_bankInfo_delete', function (e) {
					var dataId_delete = $(e.currentTarget).attr('data-bankacctid-details');
					
						$("#bankInfoList").addClass("none");
						$("#bankInfo_delete").removeClass("none");
					
						//设置显示的数据
					var custName =  $("#companyName_bank_modify_after").val();
					
					$("#companyName_bank_delete").val($(e.currentTarget).attr('data-bankAccName-details'));		//姓名
					$("#currency_bank_delete").val(bankCardBind._currencyName);									//币种数据
					$("#bankNo_bank_delete").val($(e.currentTarget).attr('data-bankNameSub-details'));			//银行名称
					var countryNo = $(e.currentTarget).attr('data-countryNo-details');
					var provinceNo = $(e.currentTarget).attr('data-provinceNo-details');
					var cityNo = $(e.currentTarget).attr('data-cityNo-details');
					
					cacheUtil.findCacheCityByParent(countryNo,provinceNo,function(cityList){
						
						if(cityList)
						{
							for(var i = 0 ; i < cityList.length ; i++)
							{
								if(cityList[i].cityNo == cityNo)
								{
									$("#bankArea_bank_delete").val(cityList[i].cityNameCn);						//开户地区
								}
							}
						}
						
					});
					
					$("#bankAccount_bank_delete").val($(e.currentTarget).attr('data-bankAccount-details'));		//银行卡号
					$("#defaultFlag_bank_delete").val($(e.currentTarget).attr('data-defaultFlag-details'));		//是否默认
					
					
					
					
					//删除银行卡页面，确认删除按钮事件
					$("#sure_bank_delete").click(function () {
						//提示确认
						comm.i_confirm(comm.lang("myHsCard").delBankConfirmInfo, function () {
							//封装删除数据参数
							var jsonParam = {
								bankId : dataId_delete
							};
							
							//执行删除
							dataModule.delBankCard(jsonParam, function (response) {
								//删除成功显示提示信息并且返回显示列表界面
								comm.yes_alert(comm.lang("myHsCard").delBankSuccessfully);
								$('#ul_myhs_right_tab a[data-id="5"]').trigger('click');
							});
						});
					});
				});
				
				
			
				/**
				 *删除银行卡页面返回按钮事件
				 */
				$("#return_bankList_delete").click(function () {
					//隐藏删除银行卡页面
					$("#bankInfo_delete").addClass("none");
					//显示列表页面
					$("#bankInfoList").removeClass("none");
				});
			},
			
			
			//加载客户名称
			loadCustomerName : function(){
				accountManagementDataModel.findNetworkInfoByCustId(null, function(response){
					//获取数据
					var custName =  response.data.name;			//真实姓名
					try{
						if(custName=="null"){
							custName="";
							//提醒
							comm.alert(comm.lang("myHsCard")[30169]);
						}
						/*else{
							custName = comm.plusXing(custName,1,0,true);
						}*/
					}catch(ex){}
					//加载当前用户名称 
					$("#companyName_bank_add, #companyName_bank_modify_after").val(bankCardBind.maskName(custName));
					bankCardBind.realName=custName;

				});
			},
			//姓名增加掩码
			maskName : function(custName){
				try{
					//如果是英文名实名注册  只保留首位，其余遮挡
					if(/^[A-Za-z]+$/.test(custName)){
		 				return comm.plusXing(custName,1,1,true);
		 			}else{
		 				//其他中文实名注册
			 			return comm.plusXing(custName,1,0,true);
		 			}
				}catch(ex){
					return name;
				}
			},
			
			//
			/**
			 *加载城市的下拉框
			 *@param  countryNo 	国家编码
			 *@param  provinceNo	省份编码
			 */
			loadCacheCityByParent : function(countryNo,provinceNo){
				$("#bankArea2_bank_add").find("option").remove();
				if(provinceNo==""){
					comm.initOption('#bankArea2_bank_add', {} , null, null , true);
					return false;
				}
				//根据国家获取下属省份  参数：国家编码、省份编码、回调函数
				cacheUtil.findCacheCityByParent(countryNo,provinceNo,function(cityList){
					//加载省份信息
					comm.initOption('#bankArea2_bank_add', cityList , 'cityName','cityNo',true);
				});
			},
			
			
			//增加银行卡数据验证
			validateAddData : function(){
				//开户行验证
				var bankNo = $.trim($('#bankNo_bank_add').val());
				
				if(comm.isEmpty(bankNo)) {
					$('.custom-combobox').attr("title", comm.lang("myHsCard")[30140]).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 2000,
						position : {
							my : "left+30 top+5",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
					return false;
				} else {
					$(".custom-combobox").tooltip().tooltip("destroy");
				}
				
				//省份验证
				var bankArea_bank_add = $("#bankArea_bank_add").val();
				if(comm.isEmpty(bankArea_bank_add)) {
					$('#bankArea_bank_add').attr("title", comm.lang("myHsCard")[30145]).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 2000,
						position : {
							my : "left+5 top+30",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "330px");
					return false;
				} else {
					$("#bankArea_bank_add").tooltip().tooltip("destroy");
				}
				
				//城市非空验证
				var bankArea2_bank_add = $("#bankArea2_bank_add").val();
				if(comm.isEmpty(bankArea2_bank_add)) {
					$('#bankArea2_bank_add').attr("title", comm.lang("myHsCard")[30146]).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 2000,
						position : {
							my : "left+5 top+30",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "330px");
					return false;
				} else {
					$("#bankArea2_bank_add").tooltip().tooltip("destroy");
				}
				
				//银行卡号验证
				var valid = $("#bankInfo_add_form").validate({
					//验证规则
					rules : {
						//银行卡号验证
						bankAcccount_bank_add : {
							required : true,	//非空验证
							bankNo : true,		//银行卡验证
							rangelength : [5,30]		//最大长度
						},
						
						bankAcccount_bank_add_sure : {
							required : true,
							equalTo : "#bankAcccount_bank_add"
						}
					},
					
					//提示信息
					messages : {
						bankAcccount_bank_add : {
							required : comm.lang("myHsCard")[30110],
							bankNo : comm.lang("myHsCard")[30138],
							rangelength : comm.lang("myHsCard").bindBankMaxlength
						},
						bankAcccount_bank_add_sure : {
							required : comm.lang("myHsCard")[30141],
							equalTo : comm.lang("myHsCard")[30139]
						}
					},
					
					//提示信息位置坐标设置
					errorPlacement : function (error, element) {
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 2000,
							position : {
								my : "left+80 top+5",
								at : "left top"
							}
						}).tooltip("open");
						$(".ui-tooltip").css("max-width", "230px");
					},
					success : function (element) {
						$(element).tooltip().tooltip("destroy");
					}
				});
				return valid.form();
			},
			//银行卡账户名显示
			bankNameShow:function(){
				var cerType=comm.getCache("myHsCard","realNameCerType");
				var name=comm.lang("myHsCard").userName;
				//根据类型判断名称显示
				if(cerType==3){
					name=comm.lang("myHsCard").enterpriseName;
				}
				$("label[rel='labAccountName']").text(name);
			}
		
		};
		return bankCardBind
});
