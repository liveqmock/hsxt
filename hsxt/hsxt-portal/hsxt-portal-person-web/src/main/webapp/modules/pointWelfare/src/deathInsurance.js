/**
 * 代他人申请死亡保障金
 */
define(["text!pointWelfareTpl/deathInsurance.html"], function (tpl) {
	var deathInsurance = {
		show : function(dataModule){
			var mobile;	//手机号码
			var cerNo;	//证件号码
			var realName;	//客户真实姓名
			var realNameStatus;	//客户实名认证状态
			//加载页面
			$("#myhs_zhgl_box").html(tpl);
			$("#otherProve1").change(function(){
				$("#otherProveSpan2").show();
			});
			$("#otherProve2").change(function(){
				$("#otherProveSpan3").show();
			});
			$("#otherProve3").change(function(){
				$("#otherProveSpan4").show();
			});
			$("#otherProve4").change(function(){
				$("#otherProveSpan5").show();
			});
			
			//加载数据
			dataModule.queryConsumerInfo(null,function(response){
				//互生卡号、用户id
				cerNo=response.data.cerNo;		
				//realName=response.data.realName;
				realName=comm.getCookie('custName');
				realNameStatus=response.data.realNameStatus;
			});
			//获取用户绑定手机号码
			dataModule.findMobileByCustId(null,function (response) {
				mobile=response.data.mobile;	//手机号赋值	目前没查出来
			});
			
			//加载样例图片
			cacheUtil.findDocList(function(map){
				comm.initPicPreview("#deathSecurity_demo_href_1", comm.getPicServerUrl(map.picList, '1018'), null);
				comm.initPicPreview("#deathSecurity_demo_href_2", comm.getPicServerUrl(map.picList, '1011'), null);
				comm.initPicPreview("#deathSecurity_demo_href_3", comm.getPicServerUrl(map.picList, '1012'), null);
				comm.initPicPreview("#deathSecurity_demo_href_4", comm.getPicServerUrl(map.picList, '1024'), null);
			});

			//确认提交单击事件
			$('#submit_death').click(function(){
				
				//判断是否绑定手机
				if(comm.isEmpty(mobile)){
					comm.warn_alert(comm.lang("pointWelfare").mobileNotBind);
					return false;
				}
				/*
				//判断是否实名认证
				if(3!=realNameStatus){
					comm.warn_alert(comm.lang("pointWelfare").noRealNameAuth);
					return false;
				}
				*/
				//申请人只需要实名注册
				if(1==realNameStatus){
					comm.warn_alert(comm.lang("pointWelfare").noRealNameReg);
					return false;
				}
				//判断身故人跟申请人是否为同一人
				var deathResNo = $.trim($("#deathResNo").val());
				var resNo = comm.getCookie('resNo');
				if(comm.isNotEmpty(deathResNo)&&deathResNo==resNo){
					comm.warn_alert(comm.lang("pointWelfare").deathResNoIsSameOfApplyer);
					return false;
				}
				
				var valid = deathInsurance.validateData();
				if (!valid.form()) {
					return;
				}
				comm.i_confirm('确认提交申请吗？', function () {
					var ids = ['deathProve', 		'hrc',		'diePeopleCer', 	'ifp',		'aip', 		'otherProve1', 	'otherProve2',		'otherProve3',		'otherProve4',		'otherProve5'];
					//图片上传
					comm.uploadFile(null, ids, function (response) {
						if(response!=null){
							deathInsurance.setInputImgPath(response);
							deathInsurance.initTmpPicPreview();
							var paths=response;
							var params = $("#applyDeathForm").serializeJson();
							params.custId=$.cookie('custId');						// 客户ID
							params.hsResNo=$.cookie('resNo');						// 互生号
							params.proposerName=realName;				// 申请人姓名
							params.proposerPhone=mobile;	//String	是	申请人电话
							params.proposerPapersNo=cerNo;	//String	是	申请人证件号码
							//params.applyDate="2016-01-07";	//String	是	申请日期
							params.explain="代理身故保障金申请";	//String	否	说明
							params.deathResNo=params.deathResNo;	//身故用户（被保障人）互生号
							params.diePeopleName=params.diePeopleName;	//身故用户（被保障人）姓名
							
							dataModule.applyDieSecurity(params,function (response) {
								if (response.retCode == 22000) {
									//跳转到积分福利查询
									$('#ul_myhs_right_tab li a[data-id="4"]').trigger('click');
									comm.yes_alert(comm.lang("pointWelfare").deathInsurance.applySuccess);
								}else if(response.retCode == 24008){
									comm.error_alert("被保障人互生卡号不具备申请身故保障金的福利资格!");
								}else {
									comm.alertMessageByErrorCode(comm.lang("pointWelfare"),response.retCode);
								}
							});
						}
						
					}, function(res){
					}, null);
					
				});		
			});
			

			//代理身故保障金证明材料说明
			$("#death_Thick_btn2").bind('click', function () {
				var title = "申请意外伤害身故保障金所需证明和资料";
				var content = "<div style='width:340px;line-height:24px;' class='fn f12'><p>1. 注册并经实名认证过的互生积分卡；</p>"
				 + "<p>2. 申请人的法定身份证明；</p>"
				 + "<p>3. 公安部门或二级以上(含二级)医院出具的被保障人死亡证明书；</p>"
				 + "<p>4. 如被保障人为宣告死亡，申请人须提供公安局出具的宣告死亡证明文件；</p>"
				 + "<p>5. 被保障人的户籍注销证明；</p>"
				 + "<p>6. 本平台要求的申请人所能提供的与确认保障事故的性质、原因、伤害程度等有关的其他证明和资料；</p>"
				 + "<p>7. 保障金作为被保障人遗产时，必须提供可证明合法继承权的相关权利文件。</p>"
				 + "<p style='text-align:right;line-height:30px;'class='cl-red'>[互生平台系统]</p></div>";
				comm.Thickdiv(title, content);
			});
			
			this.initTmpPicPreview();
		},
		setInputImgPath:function(imgIds){
			for(var key in imgIds){  
				$('#'+key+'Path').val(imgIds[key]);
			}  
		},
		/**
		 * 绑定图片预览
		 */
		initTmpPicPreview : function(){
			var btnIds = ['#deathProve', 	'#hrc',		'#diePeopleCer', 	'#ifp',		'#aip', 	'#otherProve1', 	'#otherProve2',		'#otherProve3',		'#otherProve4',		'#otherProve5'];
			var imgIds = ['#deathProveImg', '#hrcImg',	'#diePeopleCerImg', '#ifpImg',	'#aipImg', 	'#otherProveImg1',	'#otherProveImg2',	'#otherProveImg3',	'#otherProveImg4',	'#otherProveImg5'];
			
			
			$("body").on("change", "", function(){
				for(var k in imgIds){
					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
				}
			});
			comm.initUploadBtn(btnIds, imgIds, 81, 52);
		},
		validateData : function(){
			var validate = $("#applyDeathForm").validate({
				rules : {
					/*resNo_death : {
						required : true,
						unequalTo : "#resNo_hidden"
					},*/
					/*被保障人互生卡号*/
					deathResNo : {
						required : true
					},
					/*被保障人姓名*/
					diePeopleName : {
						required : true
					},
					/*被保障人死亡证明附件*/
					deathProve : {
						required : $("#deathProveImg").is(":visible")
					},
					/*户籍注销证明*/
					hrc : {
						required : $("#hrcImg").is(":visible")
					},
					/*与被保障人关系证明书*/
					ifp : {
						required : $("#ifpImg").is(":visible")
					},
					/*代办人法定身份证明*/
					aip : {
						required : $("#aipImg").is(":visible")
					}
					
				},
				messages : {
					/*resNo_death : {
						required : comm.lang("pointWelfare").deathInsurance.resNo,
						unequalTo : comm.lang("pointWelfare").deathInsurance.unequal
					},*/
					/*被保障人互生卡号*/
					deathResNo : {
						required : comm.lang("pointWelfare").deathInsurance.deathResNo
					},
					/*被保障人姓名*/
					diePeopleName : {
						required : comm.lang("pointWelfare").deathInsurance.diePeopleName
					},
					/*被保障人死亡证明附件*/
					deathProve : {
						required : comm.lang("pointWelfare").deathInsurance.deathProve
					},
					/*户籍注销证明*/
					hrc : {
						required : comm.lang("pointWelfare").deathInsurance.hrc
					},
					/*与被保障人关系证明书*/
					ifp : {
						required : comm.lang("pointWelfare").deathInsurance.ifp
					},
					/*代办人法定身份证明*/
					aip : {
						required : comm.lang("pointWelfare").deathInsurance.aip
					}
				},
				errorPlacement : function (error, element) {
					if ($(element).is(":text")) {
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+150 top",
								at : "left top"
							}
						}).tooltip("open");
						$(".ui-tooltip").css("max-width", "230px");
					} else {
						$(element.parent()).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+60 top+15",
								at : "left top"
							}
						}).tooltip("open");
					}
				},
				success : function (element) {
					if (!$(element).is(":text")) {
						element = element.parent();
					}
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
			
			/**
			 * 如果是修改则无需对上传文件进行必填校验
			 */
			if($("#deathProve").val() != ""){
				validate.settings.rules.deathProve = {required : false};
			}
			if($("#hrc").val() != ""){
				validate.settings.rules.hrc = {required : false};
			}
			if($("#ifp").val() != ""){
				validate.settings.rules.ifp = {required : false};
			}
			if($("#aip").val() != ""){
				validate.settings.rules.aip = {required : false};
			}
			
			return validate;
		}
	};
	return deathInsurance
});
