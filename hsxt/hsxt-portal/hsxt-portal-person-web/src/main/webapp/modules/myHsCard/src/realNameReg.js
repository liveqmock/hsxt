define(
		[ "text!myHsCardTpl/realNameReg.html",
		  "text!myHsCardTpl/realNameReg2.html" ],
		function(tpl, tpl2) {
			var realNameReg = {
				show : function(dataModule) {
					// 加载页面
					$("#myhs_zhgl_box").html(tpl + tpl2);
					
					// 初始化正价类型下拉框
					comm.initSelectOption('#trueNameReg_creType', comm.lang("myHsCard").credenceEnum);
					
					//加载国家信息从缓存中读取
					cacheUtil.findCacheContryAll(function(contryList){
						
						comm.initOption('#trueNameReg_country', contryList , 'countryName','countryNo',null,"156");
						
						realNameReg.setDivContext(null);
						// 加载数据
						dataModule.queryConsumerInfo(null,function(response) {
							//证件类型
							var creType = null;
							// 有数据代表已注册
							if (response.data && response.data.realNameStatus != 1) {
								// 设置值并将文本框设置不可编辑
								$('#trueNameReg_name').val(response.data.realName);
								$('#trueNameReg_name2').val(response.data.realName);					// 真实姓名
								$('#trueNameReg_address').val(comm.removeNull(response.data.entRegAddr));//注册地址
								$('#trueNameReg_enterpriseName').val(comm.removeNull(response.data.entName));//企业名称
								$("#trueNameReg_name").attr("disabled","disabled").parent().addClass('bg_EFEFEF'); 		// 姓名
								$("#trueNameReg_country").val(response.data.countryCode).attr("disabled", "disabled").parent().addClass('bg_EFEFEF');	// 国家
								$("#trueNameReg_creType").val(response.data.certype).attr("disabled", "disabled").parent().addClass('bg_EFEFEF');		// 证件类型
								$("#trueNameReg_creNo").val(response.data.cerNo).attr("disabled", "disabled").parent().addClass('bg_EFEFEF');			// 证件编号
								$("#trueNameReg_enterpriseName").attr("disabled","disabled").parent().addClass('bg_EFEFEF'); 		// 姓名
								$("#trueNameReg_address").attr("disabled","disabled").parent().addClass('bg_EFEFEF'); 		// 姓名
								creType = response.data.certype;
								// 设置按钮隐藏
								$("#trueNameReg_submitBtn").remove();
								//添加实名证件类型缓存
								comm.setCache("myHsCard","realNameCerType",response.data.certype);
								
							} else// 无数据代表未注册
							{
								// 设置提交按钮隐藏
								$("#trueNameReg_submitBtn").show();
							}
							//控制界面显示的字段属性
							realNameReg.setDivContext(creType);
						});
						
						$('#trueNameReg_creType').change(function(){
							realNameReg.setDivContext($(this).val());
						});
						// 立即注册单击事件
						$('#trueNameReg_submitBtn').click(function() {
							
							//表单验证
							var valid = realNameReg.validateData();
							if (!valid.form()) {
								return;
							}
							
							//企业类型
							if($("#trueNameReg_creType option:selected").val()== 3)
							{
								
								$("#trueNameReg_name2").val($("#trueNameReg_enterpriseName").val());	//企业名称
								$("#trueNameReg_country2").val($("#trueNameReg_address").val());		//企业地址
							}
							else//等与身份证、护照
							{
								$("#trueNameReg_name2").val($("#trueNameReg_name").val());							//姓名
								$("#trueNameReg_country2").val($("#trueNameReg_country option:selected").text());	//国家文本
							}
							
							//证件类型
							$("#trueNameReg_creType2").val($("#trueNameReg_creType option:selected").text());
							//证件号码
							$("#trueNameReg_creNo2").val($("#trueNameReg_creNo").val().toUpperCase());
							
							//设置div隐藏
							$('#trueNameReg_view_div').removeClass('none');
							$('#trueNameReg_add_div').addClass('none');
							
						});
						
						// 上一步返回按钮
						$("#trueNameReg2_lastBtn").click(function() {
							$('#trueNameReg_view_div').addClass('none');
							$('#trueNameReg_add_div').removeClass('none');
						});
						
						
						// 提交按钮
						$("#trueNameReg2_submitBtn").click(function() {
							var jsonParam ={
									certype : $("#trueNameReg_creType option:selected").val(), // 证件类型	
									cerNo : $("#trueNameReg_creNo").val().toUpperCase(), // 证件号码
							};
							
							if($("#trueNameReg_creType option:selected").val()== 3)
							{
								jsonParam.entName = $("#trueNameReg_enterpriseName").val(); //企业名称
								jsonParam.entRegAddr = $("#trueNameReg_address").val();		//企业地址
								jsonParam.realName = $("#trueNameReg_enterpriseName").val();
							}
							else
							{
								jsonParam.realName 		=  $("#trueNameReg_name").val();		// 姓名
								jsonParam.countryCode	=  $("#trueNameReg_country option:selected").val();		// 姓名
							}
							

							dataModule.registration(jsonParam,function(response) {
								comm.setCookieForRootPath("isRealnameAuth", 2);  
								comm.setCookieForRootPath("custName", jsonParam.realName);  
								comm.setCookieForRootPath("creType", jsonParam.certype);  
								$("#card_username em").text(comm.getHiddenCustName());
								comm.yes_alert(comm.lang("myHsCard")["relNameSuccessfully"]);
								$('#ul_myhs_right_tab li a[data-id="2"]').trigger('click');
							});
							
						});
					});
				},
				
				/**
				 * 设置界面显示的字段信息
				 * @param creType 3:显示企业名称、注册地址 或者 真实姓名、国籍
				 */ 
				setDivContext : function(creType){
					//企业类型
					if(creType==3)
					{
						$('#personUl').addClass('none');
						$("#trueNameReg_name").val("");
						$("#trueNameReg_country").val("");
						$('#enterpriseUl').removeClass('none');
						$("#lbl_trueNameReg_name2").text(comm.lang("myHsCard").enterpriseName);
						$("#lbl_trueNameReg_country2").text(comm.lang("myHsCard").registeredAddress);
					}
					else	//身份证 与护照
					{
						$('#personUl').removeClass('none');
						$('#enterpriseUl').addClass('none');
						$("#trueNameReg_enterpriseName").val("");
						$("#trueNameReg_address").val("");
						$("#lbl_trueNameReg_name2").text(comm.lang("myHsCard").userName);
						$("#lbl_trueNameReg_country2").text(comm.lang("myHsCard").country);
					}
				},
				/**
				 * 表单校验
				 */
				validateData : function(){
					
					 var validate = $("#trueNameReg_form").validate({
						rules : {},
						messages : {},
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
					 
					 //重置验证文本框为非必填
					validate.settings.rules.trueNameReg_creNo = {required : false};
				    validate.settings.rules.trueNameReg_name = {required: false};	
				    validate.settings.rules.trueNameReg_enterpriseName = {required: false};
				    validate.settings.rules.trueNameReg_address = {required: false };
					 
					//更具选择的证件类型判断
					var creType = $('#trueNameReg_creType option:selected').val();
					if(creType == "1"){
						//真实姓名
						validate.settings.rules.trueNameReg_name = {
								required: true , 
								rangelength:[2, 20],
								custname:true
							};	
						validate.settings.messages.trueNameReg_name = {
								required : comm.lang("myHsCard")[30130],
								rangelength:comm.lang("myHsCard").realNameReg.nameLength,
								custname:comm.lang("myHsCard").realNameReg.realName
								};
						
						//身份证验证
						validate.settings.rules.trueNameReg_creNo = {required : true, IDCard : true};
						validate.settings.messages.trueNameReg_creNo = {required : comm.lang("myHsCard")[30132], IDCard : comm.lang("myHsCard")[30134]};
					}else if(creType == "2"){
						//真实姓名
						validate.settings.rules.trueNameReg_name = {
								required: true , 
								rangelength:[2, 20],
								custname:true
						};	
						validate.settings.messages.trueNameReg_name = {
								required : comm.lang("myHsCard")[30130],
								rangelength:comm.lang("myHsCard").realNameReg.nameLength,
								custname:comm.lang("myHsCard").realNameReg.realName
						};	
						
						//护照验证
						validate.settings.rules.trueNameReg_creNo = {required : true, passport : true};
						validate.settings.messages.trueNameReg_creNo = {required : comm.lang("myHsCard")[30132], passport : comm.lang("myHsCard")[30135]};  
					}else if(creType == "3"){
						//企业名称
						validate.settings.rules.trueNameReg_enterpriseName = {required: true , rangelength:[2, 64]};
						validate.settings.messages.trueNameReg_enterpriseName = {required : comm.lang("myHsCard")[30155],rangelength:comm.lang("myHsCard")[30157]};
						
						validate.settings.rules.trueNameReg_address = {required: true , rangelength:[2, 128]};
						validate.settings.messages.trueNameReg_address = {required : comm.lang("myHsCard")[30156],rangelength:comm.lang("myHsCard")[30158]};
						
						//营业执照
						validate.settings.rules.trueNameReg_creNo = {required : true, businessLicence : true};
						validate.settings.messages.trueNameReg_creNo = {required : comm.lang("myHsCard")[30132], businessLicence : comm.lang("myHsCard")[30135]};
					}else{
						validate.settings.rules.trueNameReg_creNo = {required : true};
						validate.settings.messages.trueNameReg_creNo = {required : comm.lang("myHsCard")[30132]};
					}
					return validate;
				}
			};
			return realNameReg
		});
